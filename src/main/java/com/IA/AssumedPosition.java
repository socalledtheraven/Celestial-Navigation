package com.IA;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.asin;

public class AssumedPosition {
    private int Alat;
    private String[] Alon;
    private double Hc;
    private double azimuthAngle;

    private AssumedPosition(double DRlat, double DRlon, GeographicPosition GP) {
        Alat = (int) Math.round(DRlat);
        Alon = ( + String.valueOf(GP.getGHA()));
        double LHA = GP.getGHA() - DRlon;
        this.Hc = asin((sin(GP.getDeclination()) * sin(DRlat)) + (cos(GP.getDeclination()) * cos(DRlat) * cos(LHA)));
    }
}
