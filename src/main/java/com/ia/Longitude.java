package com.ia;

public class Longitude extends Degree {
    private Direction direction;

    public Longitude(int degrees, double minutes) {
        super(degrees, minutes);
    }

    public Longitude(int degrees, double minutes, Direction direction) {
        super(degrees, minutes);
        this.direction = direction;
    }

    public Longitude(String strDegrees) {
        super(strDegrees);
    }

    public int getDegrees() {
        return super.getDegrees();
    }

    public double getMinutes() {
        return super.getMinutes();
    }

    public Direction getDirection() {
        return direction;
    }
}