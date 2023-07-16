package com.ia;

public class Latitude extends Degree {
    // a Degree instance but with a direction - E or W
    // TODO: actually make the direction matter
    private Direction direction;
    public Latitude(int degrees, double minutes) {
        super(degrees, minutes);
    }

    public Latitude(int degrees) {
        super(degrees);
    }

    public Latitude(String strDegrees) {
        super(strDegrees);
    }

    public Latitude(double doubDeg, Direction dir) {
        super(doubDeg);
        direction = dir;
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