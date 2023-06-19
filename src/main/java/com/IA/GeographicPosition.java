package com.IA;

public class GeographicPosition extends Latitude {
    private Direction hemisphere;

    public GeographicPosition(int degrees, int minutes, int seconds) {
        super(degrees, minutes, seconds);
    }

    public GeographicPosition(int degrees, int minutes) {
        super(degrees, minutes);
    }

    public GeographicPosition(int degrees) {
        super(degrees);
    }

    public GeographicPosition(String strDegrees) {
        super(strDegrees);
    }

    public int getDegrees() {
        return super.getDegrees();
    }

    public int getMinutes() {
        return super.getMinutes();
    }
}