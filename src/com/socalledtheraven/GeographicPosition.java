package com.socalledtheraven;

public class GeographicPosition {
    private double GHA;
    private double declination;
    private double latitude;
    private double longitude;

    public GeographicPosition(String star) {
        this.declination = declinationFromAlmanac(star);
        this.GHA = GHAFromAlmanac(star);
    }

    private double declinationFromAlmanac(String star) {
        // acquire from almanac, consider using the python library or the pdf thing
    }

    private double GHAFromAlmanac(String star) {
        // acquire from almanac, consider using the python library or the pdf thing
    }

    public double getDeclination() {
        return declination;
    }

    public double getGHA() {
        return GHA;
    }
}
