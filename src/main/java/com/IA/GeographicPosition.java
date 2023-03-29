package com.IA;

import java.time.LocalDate;

public class GeographicPosition {
    private final double GHA;
    private final double declination;

    public GeographicPosition(String star) {
        this.declination = declinationFromAlmanac(star);
        this.GHA = GHAFromAlmanac(star);
    }

    private double declinationFromAlmanac(String star) {
        return 8.489;
//        LocalDate today = LocalDate.now();
//        int dayOfYear = today.getDayOfYear();
//        int page = 2*(dayOfYear+17);
//        String[] parts = Utilities.processAlmanac(page, star);
//        System.out.println("declination: " + parts[1]);
//        return Utilities.strAngleToDegrees(parts[1]);
    }

    private double GHAFromAlmanac(String star) {
        return 78.581;
        // v. important - REMEBER TO MAKE FUNCTION FOR USING ARIES TO GET GHA INSTEAD OF SHA
//        LocalDate today = LocalDate.now();
//        int dayOfYear = today.getDayOfYear();
//        int page = 2*(dayOfYear+17);
//        String[] parts = Utilities.processAlmanac(page, star);
//        System.out.println("GHA: " + parts[0]);
//        return Utilities.strAngleToDegrees(parts[0]);
    }

    public double getDeclination() {
        return declination;
    }

    public double getGHA() {
        return GHA;
    }
}
