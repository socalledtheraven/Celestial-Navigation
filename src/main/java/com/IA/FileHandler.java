package com.IA;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FileHandler {
    private static LocalDateTime now = LocalDateTime.now();
//    private static LocalDateTime now = LocalDateTime.of(2023, 6, 25, 2, 4);
    public static void savePlot(Plot p, String path) {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("a:" + p.getA().toString() + ",");
        lines.add("az:" + p.getAzimuth() + ",");
        lines.add("ap=alat:" + p.getAP().getAssumedLatitude().toString() + ",alon:" + p.getAP().getAssumedLongitude().toString() + ",");
        for (String l : lines) {
            appendToFile(l, path);
        }
    }

    public static Plot loadPlot(String path) {
        ArrayList<String> lines = wholeFileRead(path);
        String a = lines.get(0).split(":")[1].replace(",", "").strip();
        AValue aVal = new AValue(a);
        String az = lines.get(1).split(":")[1].replace(",", "").strip();
        double azimuth = Double.parseDouble(az);
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

    public static GeographicPosition getDeclination(String name) {
        return new GeographicPosition(starDetails(name, almanacPageText(dateToPageNum()))[0][1]);
    }

    public static Degree getSHA(String name) {
        return new Degree(starDetails(name, almanacPageText(dateToPageNum()))[0][0]);
    }

    public static String[] getStars() {
        return starDetails("", almanacPageText(dateToPageNum()))[1];
    }

    private static String almanacPageText(int page) {
        try (PDDocument document = PDDocument.load(new File("src/main/resources/data/almanac.pdf"))) {
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
                System.out.println(GHA);
            }
        }

        return hourlyDetails[now.getHour()];
    }

    public static int dateToPageNum() {
        // -1 for off-by-one, /3 bc 3 per page, x2 to skip the second page on each set
        return ((now.getDayOfYear()-1)/3)*2 + 16;
    }
}