package com.IA;

public class Star {
    private String name;
    private Degree declination;
    private Degree greenwichHourAngle;

    public Star(String n) {
        name = n;
        declination = FileHandler.getDeclination(name);
        greenwichHourAngle = Degree.add(FileHandler.getSHA(name), FileHandler.getAriesGHA());
    }

    public Degree getGreenwichHourAngle() {
        return greenwichHourAngle;
    }

    public Degree getDeclination() {
        return declination;
    }
}