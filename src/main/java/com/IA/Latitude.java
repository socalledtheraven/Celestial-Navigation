package com.IA;

public class Latitude extends Degree {
    public Latitude(int degrees, double minutes) {
        super(degrees, minutes);
    }

    public Latitude(int degrees) {
        super(degrees);
    }

    public Latitude(String strDegrees) {
        super(strDegrees);
    }

    public Latitude(double doubDeg) {
        super(doubDeg);
    }

    public int getDegrees() {
        return super.getDegrees();
    }

    public double getMinutes() {
        return super.getMinutes();
    }

    @Override
    public double toDouble() {
        return super.toDouble();
    }
}