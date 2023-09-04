package com.ia;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FileHandler {
    private static LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    // separate now for testing w/ 2016 almanac
//    private static LocalDateTime now = LocalDateTime.of(2016, 9, 22, 1, 20, 15);
    private static final Logger logger = LogManager.getLogger();

    public static void savePlot(Plot[] p, String path) {
        // saves a plot to a file using a special notation
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < p.length; i++) {
            lines.add("star" + (i+1) + "=");
            lines.add("    s:" + p[i].getStar() + ",");
            lines.add("    a:" + p[i].getAValue().toString() + ",");
            lines.add("    az:" + p[i].getAzimuth() + ",");
            lines.add("    ap=alat:" + p[i].getAssumedLatitude().toString() + ",alon:" + p[i].getAssumedLongitude().toString() + ",");
        }
        clearFile(path);
        for (String l : lines) {
            appendToFile(path, l);
        }
        logger.info("Saved plot to " + path);
    }

    public static ArrayList<Plot> loadPlot(String path) {
        // inverse of the above
        ArrayList<String> lines = wholeFileRead(path);
        ArrayList<Plot> plots = new ArrayList<>();
        String star;
        AValue aVal;
        Degree azimuth;
        Latitude aLat;
        Longitude aLon;
        int numStars = 0;

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("star")) {
                numStars++;
            }
        }

        for (int i = 0; i < numStars; i++) {
            star = lines.get((5*i)+1).replace("    ", "").split(":")[1].replace(",", "").strip();
            aVal = new AValue(lines.get((5*i)+2).replace("    ", "").split(":")[1].replace(",", "").strip());
            azimuth =
                    new Degree(lines.get((5*i)+3).replace("    ", "").split(":")[1].replace(",", "").strip());
            aLat =
                    new Latitude(lines.get((5*i)+4).replace("    ", "").split("=")[1].split(",")[0].split(":")[1]);
            aLon =
                    new Longitude(lines.get((5*i)+4).replace("    ", "").split("=")[1].split(",")[1].replace(",", "").split(":")[1]);
            plots.add(new Plot(star, aLat, aLon, aVal, azimuth));
        }

        logger.info("Loaded plot from " + path);
        return plots;
    }

    private static ArrayList<String> wholeFileRead(String path) {
        // reads the whole thing (line by line obv) with a trycatch and RandomAccessFile
        try (RandomAccessFile rf = new RandomAccessFile(path, "rws")) {
            ArrayList<String> lines = new ArrayList<>();
            String line = rf.readLine();
            lines.add(line);
            while (line != null) {
                line = rf.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
            return lines;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static void clearFile(String path) {
        // clears a file
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("");
        } catch (IOException e) {
            logger.error("Error in clearFile: " + e.getMessage());
        }
    }

    private static void appendToFile(String filename, String text) {
        // APPENDS text to a file
        // TODO: add real error handling
        try (PrintWriter pr = new PrintWriter(new FileWriter(filename, true))) {
            pr.println(text);
        }
        catch (IOException e) {
            logger.error("Error in wholeFileRead: " + e.getMessage());
        }
    }

    public static Degree getDeclination(String name) {
        // public getter for the star details specifically declination
        return new Degree(starDetails(name, almanacPageText(dateToPageNum()))[0][1]);
    }

    public static Degree getSHA(String name) {
        // public getter for the star details specifically SHA
        return new Degree(starDetails(name, almanacPageText(dateToPageNum()))[0][0]);
    }

    private static String almanacPageText(int page) {
        // uses pdfbox to scrape text from the almanac pdfs. much less hassle than reading them myself
        try (PDDocument document = PDDocument.load(new File(FileHandler.class.getResource("almanac.pdf").toURI()))) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(page); // Start extracting text from the current page
            stripper.setEndPage(page); // Extract text only from the current page
            return stripper.getText(document);
        } catch (Exception e) {
            // TODO: do some proper error handling after testing this ie invalid page numbers due to invalid dates etc
            logger.error("Error in almanacPageText: " + e.getMessage());
            System.out.println("Sorry, the date is invalid. Please try again.");
        }
        return null;
    }

    private static String[][] starDetails(String star, String pageText) {
        // gets the first part of the table (which is 16 characters after the word Stars)
        String extractedText = pageText.substring(pageText.indexOf("Stars") + 16);
        // the table ends 20 characters before pass
        extractedText = extractedText.substring(0, extractedText.indexOf("pass")-20);

        // probably not the best way to handle this but I can't really detect official pdf tables

        try (Scanner scanner = new Scanner(extractedText)) {
            String[] parts = new String[2];
            // total of 57 commonly sighted stars, plus Polaris for the northern hemisphere and Alkaid for the southern
            String[] stars = new String[59];
            int counter = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String starOccurrence = line.split(" ")[0].strip();
                stars[counter] = starOccurrence;

                if (line.contains(star)) {
                    parts = line.replace(star, "").strip().split(" ");
                }
                counter++;
            }
            return new String[][]{parts, stars};
        } catch (Exception e) {
            // TODO: again, error handling
            logger.error("Error in starDetails: " + e.getMessage());
            System.out.println("Getting star details failed. Please try again.");
        }
        return null;
    }

    private static Double[][] altitudeCorrectionArrs(String[] pageTexts) {
        // there are a lot of altitude corrections
        Double[] apparentAltitudes = new Double[163];
        Double[] corrections = new Double[163];

        String pageText = pageTexts[0];
        // finding the start of the table
        String extractedText = pageText.substring(pageText.indexOf("0 00"));
        // reformatting the +- symbols to reduce whitespace inconsistency
        extractedText = extractedText.substring(0, extractedText.indexOf("For")-1).replace("‒ ", "-").replace("+  ",
                "+").replace("+ ", "+");

        try (Scanner scanner = new Scanner(extractedText)) {
            int counter = 0;
            // the last lines are duplicates so I skip them
            while (scanner.hasNextLine() && counter != (extractedText.split("\n").length - 1)) {
                String line = scanner.nextLine();

                // convert to char[] to do direct char manipulation
                char[] temp = line.toCharArray();
                temp[1] = '.';
                String[] ls = line.split(" ");

                if (line.contains("-")) {
                    // if the line is a negative one, it has some extra characters
                    temp[36 - (5-ls[2].length()) - (5-ls[4].length())] = '.';
                } else {
                    // putting a decimal place in at an _almost_ fixed point
                    temp[31 - (4-ls[2].length()) - (4-ls[4].length())] = '.';
                }

                line = new String(temp);
                String[] parts = line.split(" ");

                // each line includes the appararent altitudes for the much later one as well
                apparentAltitudes[counter] = Double.parseDouble(parts[0]);
                apparentAltitudes[54+counter] = Double.parseDouble(parts[6]);

                // same with corrections
                corrections[counter] = Double.parseDouble(parts[5]);
                corrections[54+counter] = Double.parseDouble(parts[11]);

                counter++;
            }
        } catch (Exception e) {
            logger.error("Error in the first page of altitude correction: " + e.getMessage());
        }

        pageText = pageTexts[1];

        // the _other_ altitude correction table (completely different, ofc)
        extractedText = pageText.substring(pageText.indexOf("meters ' feet meters '")+24);
        extractedText = extractedText.substring(0, extractedText.indexOf("App. Alt.")-1).replace("‒ ", "-");

        try (Scanner scanner = new Scanner(extractedText)) {
            int counter = 0;
            String nextLine = "";
            String line;

            while (scanner.hasNextLine()) {
                // this whole section (and the next if statement) is for the stupid asf sections where occasionally 2
                // parts are on one line for no reason and I have to allow for that even though I'm doing
                // autoindexing >:(
                if (nextLine.equals("")) {
                    line =
                            scanner.nextLine().strip().replace("'", "").replace("←", "").replace(" See table", "").replace(" meters", "").replace("         ", " ").replace("       ", " ").replace("  ", " ");
                } else {
                    line = nextLine;
                    nextLine = "";
                }

                if (line.contains("feet")) {
                    String[] lines = line.split(" feet ");
                    line = lines[0];
                    nextLine = lines[1];
                }

                // every other line has corrections vs apparent altitudes
                if (counter % 2 == 0) {
                    line = replaceIfOdd(line);
                    apparentAltitudes[108+(counter/2)] = Double.parseDouble(line.split(" ")[2]);
                } else {
                    corrections[108+(counter/2)] = Double.parseDouble(line.split(" ")[4]);
                }

                counter++;
            }
        } catch (Exception e) {
            logger.error("Error in the second page of altitude correction: " + e.getMessage());
        }

        return new Double[][]{apparentAltitudes, corrections};
    }

    public static Degree altitudeCorrection(Degree apparentAltitude) {
        // finds the correction for the given apparent altitude
        // the pages are hardcoded for the modern almanac
        Double[] apparentAltitudes = altitudeCorrectionArrs(new String[]{almanacPageText(281), almanacPageText(282)})[0];
        Double[] corrections = altitudeCorrectionArrs(new String[]{almanacPageText(281), almanacPageText(282)})[1];

        // finds the correction in between the values bc that's how it works for some reason
        for (int i = 1; i < apparentAltitudes.length; i++) {
            if ((apparentAltitude.toDouble() > apparentAltitudes[i-1]) && (apparentAltitude.toDouble() < apparentAltitudes[i])) {
                return new Degree(0, corrections[i-1]);
            }
        }
        logger.warn("No correction found for " + apparentAltitude);
        return null;
    }

    private static String ariesCorr(String pageText) {
        // choosing the right table for the minute
        String extractedText = pageText.split("d corr")[(now.getMinute() % 3)+1].strip();

        String line = extractedText.split("\n")[now.getSecond()];
        String[] parts = line.split(" ");

        return parts[2];
    }

    private static String replaceIfOdd(String stringToChange) {
        // a function that adds a decimal point in certain places where it's needed
        final String separator = "#######";
        String splittingString = stringToChange.replaceAll(" ", separator + " ");
        String[] splitArray = splittingString.split(separator);
        String result = "";
        for (int i = 0; i < splitArray.length; i++) {
            if ((i % 2 == 1) && (i < 7)) {
                splitArray[i] = splitArray[i].replace(" ", ".");
            }
            result += splitArray[i];
        }
        return result;
    }

    public static Degree getAriesGHA() {
        String pageText = almanacPageText(dateToPageNum());
        // there's a weekday on the page which I'm using to identify the correct table
        String shortWeekday = now.getDayOfWeek().getDisplayName(TextStyle.valueOf("SHORT"), Locale.ENGLISH);
        String extractedText = pageText.split("\\b" + shortWeekday + "\\b")[2].split("Mer")[0].strip();
        String[] lines = extractedText.split("\\n");

        Degree[] hourlyDetails = new Degree[lines.length];
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (i != 0) {
                Degree GHA = new Degree(line.split(" ")[1]);
                hourlyDetails[i] = GHA;
            }
        }

        // adding the degrees and minutes with correction
        Degree aries = Degree.add(hourlyDetails[now.getHour()+1], new Degree(ariesCorr(almanacPageText(timeToPageNum()))));

        if (aries.toDouble() > 360) {
            aries = Degree.subtract(aries, new Degree(360));
        }

        return aries;
    }

    public static int dateToPageNum() {
        // -1 for off-by-one, /3 bc 3 per page, x2 to skip the second page on each set
        return ((now.getDayOfYear()-1)/3)*2 + 16;
    }

    public static int timeToPageNum() {
        // -1 for off-by-one, /3 bc 3 per page
        return ((now.getMinute()-1)/3) + 260;
    }
}