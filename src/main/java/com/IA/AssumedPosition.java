package com.IA;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.asin;

public class AssumedPosition {
    private final int Alat;
    private final double Alon;
    private final double Hc;

    public AssumedPosition(double DRlat, double DRlon, GeographicPosition GP) {
        Alat = (int) Math.round(DRlat);
        Alon = calcAlon(DRlon, GP.getGHA());
        double LHA = GP.getGHA() - DRlon;
        this.Hc = asin((sin(GP.getDeclination()) * sin(DRlat)) + (cos(GP.getDeclination()) * cos(DRlat) * cos(LHA)));
    }

    private double calcAlon(double DRlon, double GHA) {
        double GHAMins = Utilities.getMinutes(GHA);
        double angleMins = Utilities.getMinutes(DRlon);
        double distanceToY = Math.abs(angleMins - GHAMins);
        double distanceToYPlusOne = Math.abs(angleMins - (GHAMins + 1));

        // make alon within 0.3 of DR-lon, ignoring whole differences.
        if (distanceToY > distanceToYPlusOne) {
            return (Math.round(DRlon) + GHAMins);
        } else {
            return (Math.round(DRlon) + 1 + GHAMins);
        }
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
