package com.ia;

public class Star {
    // a data class containing data for a star received from the Almanac
    private final Degree declination;
    private Degree greenwichHourAngle;

    public Star(String n) {
        declination = FileHandler.getDeclination(n);
        greenwichHourAngle = Degree.add(FileHandler.getSHA(n), FileHandler.getAriesGHA());

        if (greenwichHourAngle.toDouble() > 360) {
            greenwichHourAngle = Degree.subtract(greenwichHourAngle, new Degree(360));
        }
    }

    public Degree getGreenwichHourAngle() {
        return greenwichHourAngle;
    }

    public Degree getDeclination() {
        return declination;
    }
}