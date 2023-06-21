package com.IA;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class FileHandler {
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
        return null;
    }

    public static Degree getSHA(String name) {
        return null;
    }

    public static Degree getAriesGHA() {
        return null;
    }


}