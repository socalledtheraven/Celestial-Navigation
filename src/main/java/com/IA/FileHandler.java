package com.IA;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class FileHandler {
    public static void savePlot(Plot p) {

    }

    public static Plot loadPlot(String path) {
        ArrayList<String> lines = wholeFileRead(path);
        String aVal = lines.get(0).split(":")[1].replace(",", "").strip();
        AValue a = new AValue(val);
        String az = lines.get(1).split(":")[1].replace(",", "").strip();
        double azimuth = Double.parseDouble(val);
        String aLon = lines.get(2).split("=")[1].split(",")
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
        return null;
    }

    public static Degree getSHA(String name) {
        return null;
    }

    public static Degree getAriesGHA() {
        return null;
    }


}
