package com.IA;

import static java.lang.Math;

public class AssumedPosition {
    private final int Alat;
    private final double Alon;
    private final double Hc;

    public AssumedPosition(double DRlat, double DRlon, GeographicPosition GP) {
        Alat = (int) Math.round(DRlat);
        Alon = calcAlon(DRlon, GP.getGHA());
        double LHA = calculateLHA(GP.getGHA());
        System.out.println("LHA: " + LHA);
        this.Hc = asin((sin(GP.getDeclination()) * sin(DRlat)) + (cos(GP.getDeclination()) * cos(DRlat) * cos(LHA)));
        System.out.println(Hc);
    }

    private double calcAlon(double DRlon, double GHA) {
        System.out.println("DRlon: " + DRlon + " GHA: " + GHA);
        double GHAMins = Utilities.getMinutes(GHA);
        double angleMins = Utilities.getMinutes(DRlon);
        System.out.println("angleMins: " + angleMins + " GHAMins: " + GHAMins);
        double distanceToY = Math.abs(angleMins - GHAMins);
        double distanceToYPlusOne = Math.abs(angleMins - (GHAMins + 1));

        // make alon within 0.3 of DR-lon, ignoring whole differences.
        if (distanceToY > distanceToYPlusOne) {
            return (Math.round(DRlon) + GHAMins);
        } else {
            return (Math.round(DRlon) + 1 + GHAMins);
        }
    }

    private double calculateLHA(double GHA) {
        // also handle with east and west stuff once I implement lat/lon classes
        double LHA = GHA - Alon;
        if (LHA < 0) {
            LHA += 360;
        } else if (LHA > 360) {
            LHA -= 360;
        }
        return LHA;
    }

    private double cos(double deg) {
        return Math.toDegrees(Math.cos(Math.toRadians(deg)));
    }

    private double sin(double deg) {
        return Math.toDegrees(Math.sin(Math.toRadians(deg)));
    }

    private double asin(double deg) {
        return Math.toDegrees(Math.asin(Math.toRadians(deg)));
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
