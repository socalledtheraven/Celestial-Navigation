package com.socalledtheraven;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.asin;

public class AssumedPosition {
    private double Alat;
    private double Alon;
    private double Hc;
    private double azimuthAngle;

    private AssumedPosition(double aLat, double aLon, GeographicPosition GP) {
        this.Alat = aLat;
        this.Alon = aLon;
        double LHA = GP.getGHA() - Alon;
        this.Hc = asin((sin(GP.getDeclination()) * sin(Alat)) + (cos(GP.getDeclination()) * cos(Alat) * cos(LHA)));
    }
}
