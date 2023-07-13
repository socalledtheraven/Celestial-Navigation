package com.ia;

public class Longitude extends Degree {
    private Direction direction;
    public Longitude(int degrees, double minutes) {
        super(degrees, minutes);
    }

    public Longitude(int degrees) {
        super(degrees);
    }

    public Longitude(String strDegrees) {
        super(strDegrees);
    }

    public Longitude(double doubDeg, Direction dir) {
        super(doubDeg);
        direction = dir;
    }

    public int getDegrees() {
        return super.getDegrees();
    }

    public double getMinutes() {
        return super.getMinutes();
    }
}