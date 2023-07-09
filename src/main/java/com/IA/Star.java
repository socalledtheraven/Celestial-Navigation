package com.IA;

public class Star {
    private Degree declination;
    private Degree greenwichHourAngle;

    public Star(String n) {
        declination = FileHandler.getDeclination(n);
        System.out.println("GHA: " + FileHandler.getAriesGHA());
        greenwichHourAngle = Degree.add(FileHandler.getSHA(n), FileHandler.getAriesGHA());
    }

    public Degree getGreenwichHourAngle() {
        return greenwichHourAngle;
    }

    public Degree getDeclination() {
        return declination;
    }
}