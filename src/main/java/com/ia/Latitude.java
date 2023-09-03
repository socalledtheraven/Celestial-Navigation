package com.ia;

public class Latitude extends Degree {
    // a Degree instance but with a direction - E or W
    private Direction direction;
    public Latitude(int degrees, double minutes) {
        super(degrees, minutes);
    }

    public Latitude(int degrees, double minutes, Direction d) {
        super(degrees, minutes);
        direction = d;
    }

    public Latitude(int degrees) {
        super(degrees);
    }

    public Latitude(String strDegrees) {
        super(strDegrees);
    }

    public Latitude(int degrees, Direction d) {
        super(degrees);
        direction = d;
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

    public Direction getDirection() {
        return direction;
    }
}