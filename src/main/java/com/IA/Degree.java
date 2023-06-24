package com.IA;

public class Degree {
    private int degrees;
    private int minutes;
    private int seconds;

    public Degree(int degrees, int minutes, int seconds) {
        this.degrees = degrees;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public Degree(int degrees, int minutes) {
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
        this.seconds = Integer.parseInt(parts2[1].replace("\"", ""));
    }

    @Override
    public String toString() {
        return degrees + "° " + minutes + "' " + seconds + "\"";
    }

    public int getDegrees() {
        return degrees;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public static Degree add(Degree d1, Degree d2) {
        return new Degree(d1.getDegrees() + d2.getDegrees(), d1.getMinutes() + d2.getMinutes(), d1.getSeconds() + d2.getSeconds());
    }
}