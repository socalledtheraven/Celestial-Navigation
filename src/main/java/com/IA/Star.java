package com.IA;

public class Star {
    private String name;
    private GeographicPosition declination;
    private Degree siderealHourAngle;

    public Star(String name) {
        this.name = name;
        declination = FileHandler.getDeclination(name);
        siderealHourAngle = FileHandler.getSHA(name);
    }

    public Degree getGreenwichHourAngle() {
        return siderealHourAngle + FileHandler.getAriesGHA();
    }
}