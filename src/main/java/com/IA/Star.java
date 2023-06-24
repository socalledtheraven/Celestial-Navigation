package com.IA;

public class Star {
    private String name;
    private GeographicPosition declination;
    private Degree siderealHourAngle;

    public Star(String n) {
        name = n;
        declination = FileHandler.getDeclination(name);
        siderealHourAngle = FileHandler.getSHA(name);
    }

    public Degree getGreenwichHourAngle() {
        return Degree.add(siderealHourAngle, FileHandler.getAriesGHA());
    }
}