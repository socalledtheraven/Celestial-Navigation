package com.IA;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.asin;

public class AssumedPosition {
    private int Alat;
    private double Alon;
    private double Hc;

    private AssumedPosition(double DRlat, double DRlon, GeographicPosition GP) {
        Alat = (int) Math.round(DRlat);
        Alon = Utilities.calcAlon(DRlon, GP.getGHA());
        double LHA = GP.getGHA() - DRlon;
        this.Hc = asin((sin(GP.getDeclination()) * sin(DRlat)) + (cos(GP.getDeclination()) * cos(DRlat) * cos(LHA)));
    }

    public int getAlat() {
        return Alat;
    }

    public double getAlon() {
        return Alon;
    }

    public double getHc() {
        return Hc;
    }
}
