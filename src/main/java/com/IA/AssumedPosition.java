package com.IA;

public class AssumedPosition {
    private Latitude assumedLatitude;
    private Longitude assumedLongitude;
    private double expectedHeight;
    private Degree azimuth;

    public AssumedPosition(DRPosition dPos, Star star) {
        assumedLatitude = new Latitude(dPos.getLatitude().getDegrees());
        assumedLongitude = calculateAssumedLongitude(dPos, star);
        expectedHeight = calculateExpectedHeight(dPos, star);
        azimuth = calculateAzimuth(star);
    }

    private Longitude calculateAssumedLongitude(DRPosition dPos, Star star) {
        double dLonMins = dPos.getLongitude().getMinutes();
        double GHAMins = star.getGreenwichHourAngle().getMinutes();

        double distanceToY = Math.abs(dLonMins-GHAMins);
        double distanceToYPlusOne = Math.abs(dLonMins - (GHAMins + 1));
        // make alon within 0.3 of DR-lon, ignoring whole-number differences.
        int dLonDegrees = dPos.getLongitude().getDegrees();
        if (distanceToY > distanceToYPlusOne) {
            return new Longitude(dLonDegrees, GHAMins);
        } else {
            return new Longitude(dLonDegrees + 1, GHAMins);
        }
    }

    private double calculateExpectedHeight(DRPosition drPos, Star star) {
        Latitude DRlat = drPos.getLatitude();
        Degree LHA = calculateLHA(star);
        System.out.println("LHA " + LHA);
        System.out.println("Utilities.cos(LHA.toDouble()) " + Utilities.cos(LHA.toDouble()));

        System.out.println("star.getDeclination().toDouble() " + star.getDeclination().toDouble());
        System.out.println("Utilities.sin(star.getDeclination().toDouble()) " + Utilities.sin(star.getDeclination().toDouble()));
        System.out.println("Utilities.cos(star.getDeclination().toDouble()) " + Utilities.cos(star.getDeclination().toDouble()));
        System.out.println("DRlat.toDouble() " + DRlat.toDouble());
        System.out.println("Utilities.sin/cos(DRlat.toDouble()) " + Utilities.sin(DRlat.toDouble()));
        System.out.println("(Utilities.cos(star.getDeclination().toDouble()) * Utilities.cos(DRlat.toDouble()) * " +
                "Utilities.cos(LHA.toDouble())) " + (Utilities.cos(star.getDeclination().toDouble()) * Utilities.cos(DRlat.toDouble()) * Utilities.cos(LHA.toDouble())));
        System.out.println("(Utilities.sin(star.getDeclination().toDouble()) * Utilities.sin(DRlat.toDouble())) " + (Utilities.sin(star.getDeclination().toDouble()) * Utilities.sin(DRlat.toDouble())));
        return Utilities.asin((Utilities.sin(star.getDeclination().toDouble()) * Utilities.sin(DRlat.toDouble())) + (Utilities.cos(star.getDeclination().toDouble()) * Utilities.cos(DRlat.toDouble()) * Utilities.cos(LHA.toDouble())));
    }

    private Degree calculateLHA(Star star) {
        // also handle with east and west stuff
        Degree LHA = Degree.subtract(star.getGreenwichHourAngle(), assumedLongitude);
        if (LHA.toDouble() < 0) {
            LHA = Degree.add(LHA, new Degree(360));
        } else if (LHA.toDouble() > 360) {
            LHA = Degree.subtract(LHA, new Degree(360));
        }
        return LHA;
    }

    private Degree calculateAzimuth(Star star) {
        double Z = Utilities.acos((Utilities.sin(star.getDeclination().toDouble())) - Utilities.sin(assumedLatitude.toDouble()) * Utilities.sin(expectedHeight)) / (Utilities.cos(assumedLatitude.toDouble()) * Utilities.cos(expectedHeight));
        if (Z < 0) {
            Z += 360;
        }
        return new Degree(Z);
    }

    public Latitude getAssumedLatitude() {
        return assumedLatitude;
    }

    public Longitude getAssumedLongitude() {
        return assumedLongitude;
    }

    public double getExpectedHeight() {
        return expectedHeight;
    }

    public Degree getAzimuth() {
        return azimuth;
    }
}