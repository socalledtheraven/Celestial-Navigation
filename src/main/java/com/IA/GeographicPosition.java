package com.IA;

import java.time.LocalDate;

import static com.IA.Utilities.processAlmanac;

public class GeographicPosition {
    private double GHA;
    private double declination;
    private double latitude;
    private double longitude;

    public GeographicPosition(String star) {
        this.declination = declinationFromAlmanac(star);
        this.GHA = GHAFromAlmanac(star);
    }

    private String declinationFromAlmanac(String star) {
        LocalDate today = LocalDate.now();
        int dayOfYear = today.getDayOfYear();
        int page = 2*(dayOfYear+17);
        String[] parts = processAlmanac(page, star);
        return parts[1];
    }

    private String GHAFromAlmanac(String star) {
        LocalDate today = LocalDate.now();
        int dayOfYear = today.getDayOfYear();
        int page = 2*(dayOfYear+17);
        String[] parts = processAlmanac(page, star);
        return parts[0];
    }

    public double getDeclination() {
        return declination;
    }

    public double getGHA() {
        return GHA;
    }
}
