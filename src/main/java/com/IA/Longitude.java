package com.IA;

public class Longitude extends Degree {
    public Longitude(int degrees, double minutes) {
        super(degrees, minutes);
    }

    public Longitude(int degrees) {
        super(degrees);
    }

    public Longitude(String strDegrees) {
        super(strDegrees);
    }

    public Longitude(double doubDeg) {
        super(doubDeg);
    }

    public int getDegrees() {
        return super.getDegrees();
    }

    public double getMinutes() {
        return super.getMinutes();
    }
}