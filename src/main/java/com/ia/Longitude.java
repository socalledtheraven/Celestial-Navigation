package com.ia;

public class Longitude extends Degree {
    // a Degree instance but with a direction - E or W
    // TODO: actually make the direction matter
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