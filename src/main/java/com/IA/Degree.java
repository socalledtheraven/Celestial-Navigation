package com.IA;

public class Degree {
    private int degrees;
    private double minutes;

    public Degree(int degrees, double minutes) {
        while (minutes > 60) {
            minutes -= 60;
            degrees++;
        }
        while (minutes < 0) {
            minutes += 60;
            degrees--;
        }
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
        this.minutes = Utilities.round(Double.parseDouble(parts[1]), 2);
    }

    public Degree(double doubDeg) {
        this.degrees = (int) Math.floor(doubDeg);
        this.minutes = 100 * (Utilities.round(doubDeg - this.degrees, 2));
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
        return new Degree((int) Utilities.round(d1.getDegrees() + d2.getDegrees(), 2),
                Utilities.round(d1.getMinutes() + d2.getMinutes(), 2));
    }

    public static Degree subtract(Degree d1, Degree d2) {
        return new Degree((int) Utilities.round(d1.getDegrees() - d2.getDegrees(), 2),
                Utilities.round((d1.getMinutes() - d2.getMinutes()), 2));
    }

    public static Degree divide(Degree d1, int denom) {
        return new Degree(d1.getDegrees() / denom, d1.getMinutes() / denom);
    }
}