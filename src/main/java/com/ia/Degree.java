package com.ia;

public class Degree {
    private int degrees;
    private double minutes;

    public Degree(int degrees, double minutes) {
        // error handling for minute overflow
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
        System.out.println(strDegrees);
        String[] parts = strDegrees.split(" ");
        // deals with standard degree symbols and the weird ones the almanac uses
        this.degrees = Integer.parseInt(parts[0].replace("°", "").replace("◦", ""));
        this.minutes = Utilities.round(Double.parseDouble(parts[1].replace("'", "")), 2);
    }

    public Degree(double doubleDegree) {
        // takes whole numbers for degrees and decimal parts for minutes
        this.degrees = (int) Math.floor(doubleDegree);
        this.minutes = 100 * (Utilities.round(doubleDegree - this.degrees, 2));
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
        // adds each individual part and returns a new Degree
        return new Degree((int) Utilities.round(d1.getDegrees() + d2.getDegrees(), 2),
                Utilities.round(d1.getMinutes() + d2.getMinutes(), 2));
    }

    public static Degree subtract(Degree d1, Degree d2) {
        // see above
        return new Degree((int) Utilities.round(d1.getDegrees() - d2.getDegrees(), 2),
                Utilities.round((d1.getMinutes() - d2.getMinutes()), 2));
    }

    public static Degree divide(Degree d1, int denominator) {
        return new Degree(d1.getDegrees() / denominator, d1.getMinutes() / denominator);
    }
}