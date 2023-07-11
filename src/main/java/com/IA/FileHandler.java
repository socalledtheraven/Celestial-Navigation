package com.IA;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class FileHandler {
//    private static LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    // IMPORTANT: switch back to real time from 2016
    private static LocalDateTime now = LocalDateTime.of(2016, 9, 22, 1, 20, 15);
    public static void savePlot(Plot p, String path) {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("a:" + p.getAValue().toString() + ",");
        lines.add("az:" + p.getAzimuth() + ",");
        lines.add("ap=alat:" + p.getAssumedLatitude().toString() + ",alon:" + p.getAssumedLongitude().toString() + ",");
        for (String l : lines) {
            appendToFile(l, path);
        }
    }

    public static Plot loadPlot(String path) {
        ArrayList<String> lines = wholeFileRead(path);
        String a = lines.get(0).split(":")[1].replace(",", "").strip();
        AValue aVal = new AValue(a);
        String az = lines.get(1).split(":")[1].replace(",", "").strip();
        Degree azimuth = new Degree(az);
        String lat = lines.get(2).split("=")[1].split(",")[0].split(":")[1];
        Latitude aLat = new Latitude(lat);
        String lon = lines.get(2).split("=")[1].split(",")[1].replace(",", "").split(":")[1];
        Longitude aLon = new Longitude(lon);
        return new Plot(aLat, aLon, aVal, azimuth);
    }

    private static ArrayList<String> wholeFileRead(String path) {
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
            System.out.println(e);
        }
        return null;
    }

    private static void appendToFile(String filename, String text) {
        try (PrintWriter pr = new PrintWriter(new FileWriter(filename, true))) {
            pr.println(text);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Degree getDeclination(String name) {
        return new Degree(starDetails(name, almanacPageText(dateToPageNum()))[0][1]);
    }

    public static Degree getSHA(String name) {
        return new Degree(starDetails(name, almanacPageText(dateToPageNum()))[0][0]);
    }

    private static String almanacPageText(int page) {
        try (PDDocument document = PDDocument.load(new File("src/main/resources/data/2016_Nautical_Almanac.pdf"))) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(page); // Start extracting text from the current page
            stripper.setEndPage(page); // Extract text only from the current page
            return stripper.getText(document);
        } catch (Exception e) {
            // note: do some proper error handling after testing this ie invalid page numbers due to invalid dates etc
            e.printStackTrace();
        }
        return null;
    }

    private static String[][] starDetails(String star, String pageText) {
        String extractedText = pageText.substring(pageText.indexOf("Stars") + 16);
        extractedText = extractedText.substring(0, extractedText.indexOf("pass")-20);
        try (Scanner scanner = new Scanner(extractedText)) {
            String[] parts = new String[2];
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
            e.printStackTrace();
        }
        return null;
    }

    private static Double[][] altitudeCorrectionArrs(String[] pageTexts) {
        Double[] apparentAltitudes = new Double[163];
        Double[] corrections = new Double[163];

        String pageText = pageTexts[0];
        String extractedText = pageText.substring(pageText.indexOf("0 00"));
        extractedText = extractedText.substring(0, extractedText.indexOf("For")-1).replace("‒ ", "-").replace("+  ",
                "+").replace("+ ", "+");
        try (Scanner scanner = new Scanner(extractedText)) {
            int counter = 0;
            while (scanner.hasNextLine() && counter != extractedText.split("\n").length - 1) {
                String line = scanner.nextLine();
                char[] temp = line.toCharArray();
                temp[1] = '.';
                String[] ls = line.split(" ");
                if (!line.contains("-")) {
                    // putting a decimal place in at an _almost_ fixed point
                    temp[31 - (4-ls[2].length()) - (4-ls[4].length())] = '.';
                } else {
                    temp[36 - (5-ls[2].length()) - (5-ls[4].length())] = '.';
                }

                line = new String(temp);
                String[] parts = line.split(" ");
                apparentAltitudes[counter] = Double.parseDouble(parts[0]);
                apparentAltitudes[54+counter] = Double.parseDouble(parts[6]);
                corrections[counter] = Double.parseDouble(parts[5]);
                corrections[54+counter] = Double.parseDouble(parts[11]);
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pageText = pageTexts[1];
        extractedText = pageText.substring(pageText.indexOf("meters ' feet meters '")+24);
        extractedText = extractedText.substring(0, extractedText.indexOf("App. Alt.")-1).replace("‒ ", "-");
        try (Scanner scanner = new Scanner(extractedText)) {
            int counter = 0;
            String nextLine = "";
            String line;
            while (scanner.hasNextLine()) {
                if (Objects.equals(nextLine, "")) {
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

                if (counter % 2 == 0) {
                    line = replaceIfOdd(line);
                    apparentAltitudes[108+(counter/2)] = Double.parseDouble(line.split(" ")[2]);
                } else {
                    corrections[108+(counter/2)] = Double.parseDouble(line.split(" ")[4]);
                }

                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Double[][]{apparentAltitudes, corrections};
    }

    public static Degree altitudeCorrection(Degree apparentAltitude) {
        Double[] apparentAltitudes = altitudeCorrectionArrs(new String[]{almanacPageText(280), almanacPageText(279)})[0];
        Double[] corrections = altitudeCorrectionArrs(new String[]{almanacPageText(280), almanacPageText(279)})[1];
        for (int i = 1; i < apparentAltitudes.length; i++) {
            if ((apparentAltitude.toDouble() > apparentAltitudes[i-1]) && (apparentAltitude.toDouble() < apparentAltitudes[i])) {
                return new Degree(0, corrections[i-1]);
            }
        }
        return null;
    }

    private static String ariesCorr(String pageText) {
        String extractedText = pageText.split("d corr")[(now.getMinute() % 3)+1].strip();

        String line = extractedText.split("\n")[now.getSecond()];
        String[] parts = line.split(" ");

        return parts[2];
    }

    private static String replaceIfOdd(String stringToChange) {
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

        return Degree.add(hourlyDetails[now.getHour()+1],
                new Degree(ariesCorr(almanacPageText(timeToPageNum()))));
    }

    public static int dateToPageNum() {
        // -1 for off-by-one, /3 bc 3 per page, x2 to skip the second page on each set
        // IMPORTANT: reset page number offset for 2023 almanac
        return ((now.getDayOfYear()-1)/3)*2 + 14;
    }

    public static int timeToPageNum() {
        // -1 for off-by-one, /3 bc 3 per page
        // IMPORTANT: reset page number offset for 2023 almanac
        return ((now.getMinute()-1)/3) + 258;
    }
}