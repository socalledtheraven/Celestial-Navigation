package com.IA;

public class Latitude extends Degree {
    public Latitude(int degrees, int minutes, int seconds) {
        super(degrees, minutes, seconds);
    }

    public Latitude(int degrees, int minutes) {
        super(degrees, minutes);
    }

    public Latitude(int degrees) {
        super(degrees);
    }

    public Latitude(String strDegrees) {
        super(strDegrees);
    }

    public int getDegrees() {
        return super.getDegrees();
    }

    public int getMinutes() {
        return super.getMinutes();
    }
}