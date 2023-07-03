package com.IA;

public class Degree {
    private int degrees;
    private double minutes;

    public Degree(int degrees, double minutes) {
        this.degrees = degrees;
        this.minutes = minutes;
    }

    public Degree(int degrees) {
        this.degrees = degrees;
    }

    public Degree(String strDegrees) {
        String[] parts;
        if (strDegrees.contains("°")) {
            parts = strDegrees.split("°");
        } else {
            parts = strDegrees.split("◦");
        }
        this.degrees = Integer.parseInt(parts[0]);
        String[] parts2;
        if (parts[1].contains("'")) {
            parts2 = parts[1].split("'");
        } else {
            parts2 = parts[1].split("\\.");
        }
        this.minutes = Integer.parseInt(parts2[0]);
    }

    public Degree(double doubDeg) {
        this.minutes = (int) (doubDeg % 1);
        this.degrees = (int) (doubDeg - minutes);
    }

    @Override
    public String toString() {
        return degrees + "° " + minutes + "' ";
    }

    public double toDouble() {
        return degrees + (minutes/100);
    }

    public int getDegrees() {
        return degrees;
    }

    public double getMinutes() {
        return minutes;
    }

    public static Degree add(Degree d1, Degree d2) {
        return new Degree(d1.getDegrees() + d2.getDegrees(), d1.getMinutes() + d2.getMinutes());
    }

    public static Degree subtract(Degree d1, Degree d2) {
        return new Degree(d1.getDegrees() - d2.getDegrees(), d1.getMinutes() - d2.getMinutes());
    }
}