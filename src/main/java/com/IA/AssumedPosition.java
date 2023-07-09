package com.IA;

public class AssumedPosition {
    private Latitude assumedLatitude;
    private Longitude assumedLongitude;
    private double expectedHeight;
    private Degree azimuth;

    public AssumedPosition(DRPosition dPos, Star star) {
        assumedLatitude = new Latitude(dPos.getLatitude().getDegrees());
        assumedLongitude = calculateAssumedLongitude(dPos);
        System.out.println("alon: " + assumedLongitude);
        expectedHeight = calculateExpectedHeight(dPos, star);
        azimuth = calculateAzimuth(star);
    }

    private Longitude calculateAssumedLongitude(DRPosition dPos) {
        double dLonDegs = dPos.getLongitude().getDegrees();
        double GHAMins = FileHandler.getAriesGHA().getMinutes();

        return new Longitude((int) dLonDegs, GHAMins);
    }

    private double calculateExpectedHeight(DRPosition drPos, Star star) {
        double DRlat = drPos.getLatitude().toDouble();
        double LHA = calculateLHA(star).toDouble();
        double dec = star.getDeclination().toDouble();

        double realValue = (Utilities.sin(dec) * Utilities.sin(DRlat)) + (Utilities.cos(dec) * Utilities.cos(DRlat) * Utilities.cos(LHA));
        System.out.println("Hc: " + Utilities.asin(realValue));
        return Utilities.asin(realValue);
    }

    private Degree calculateLHA(Star star) {
        // also handle with east and west stuff
        Degree LHA = Degree.subtract(star.getGreenwichHourAngle(), assumedLongitude);
        if (LHA.toDouble() < 0) {
            LHA = Degree.add(LHA, new Degree(360));
        } else if (LHA.toDouble() > 360) {
            LHA = Degree.subtract(LHA, new Degree(360));
        }
        System.out.println("LHA: " + LHA);
        return LHA;
    }

    private Degree calculateAzimuth(Star star) {
        double dec = star.getDeclination().toDouble();
        double alat = assumedLatitude.toDouble();

        double Z =
                Utilities.acos((Utilities.sin(dec) - Utilities.sin(alat) * Utilities.sin(expectedHeight)) / (Utilities.cos(alat) * Utilities.cos(expectedHeight)));
        if (calculateLHA(star).getDegrees() < 180) {
            Z -= 360;
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