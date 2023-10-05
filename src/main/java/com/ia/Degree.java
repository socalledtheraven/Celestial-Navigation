package com.ia;

public class Degree {
    private final int degrees;
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
        if (strDegrees.contains("◦") && !(strDegrees.contains(" "))) {
            strDegrees =  strDegrees.replace("◦", " ");
        }

        String[] parts = strDegrees.split(" ");
        // handles the 3 different ways we can be recieving string input
        if (parts.length == 2) {
            // almanac
            this.degrees = Integer.parseInt(parts[0].replace("°", "").replace("◦", "").replace("Ã", "").replace("Â", ""));
            this.minutes = Utilities.round(Double.parseDouble(parts[1].replace("'", "")), 2);
        } else if ((parts.length == 1) && (parts[0].contains("°"))) {
            // user input, degrees box
            this.degrees = Integer.parseInt(parts[0].replace("°", "").replace("◦", "").replace("Ã", "").replace("Â", ""));
            this.minutes = 0;
        } else {
            // user input, minutes box
            this.degrees = 0;
            this.minutes = Utilities.round(Double.parseDouble(parts[0].replace("'", "")), 2);
        }
    }

    public Degree(double doubleDegree) {
        // takes whole numbers for degrees and decimal parts for minutes
        this.degrees = (int) Math.floor(doubleDegree);
        this.minutes = 100 * (Utilities.round(doubleDegree - this.degrees, 2));
    }

    @Override
    public String toString() {
        return degrees + "° " + minutes + "'";
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