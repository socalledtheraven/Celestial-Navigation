package com.IA;

import java.time.LocalDate;

public class GeographicPosition {
    private double GHA;
    private double declination;

    public GeographicPosition(String star) {
        this.declination = declinationFromAlmanac(star);
        this.GHA = GHAFromAlmanac(star);
    }

    private double declinationFromAlmanac(String star) {
        LocalDate today = LocalDate.now();
        int dayOfYear = today.getDayOfYear();
        int page = 2*(dayOfYear+17);
        String[] parts = Utilities.processAlmanac(page, star);
        return Utilities.strAngleToDegrees(parts[1]);
    }

    private double GHAFromAlmanac(String star) {
        LocalDate today = LocalDate.now();
        int dayOfYear = today.getDayOfYear();
        int page = 2*(dayOfYear+17);
        String[] parts = Utilities.processAlmanac(page, star);
        return Utilities.strAngleToDegrees(parts[0]);
    }

    public double getDeclination() {
        return declination;
    }

    public double getGHA() {
        return GHA;
    }
}
