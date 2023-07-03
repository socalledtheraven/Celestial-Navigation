package com.IA;

public class AssumedPosition {
    private Latitude assumedLatitude;
    private Longitude assumedLongitude;
    private Degree expectedHeight;
    private Degree LHA;

    public AssumedPosition(DRPosition dPos, Star star) {
        assumedLatitude = new Latitude(dPos.getLatitude().getDegrees());
        assumedLongitude = calculateAssumedLongitude(dPos, star);
        expectedHeight = calculateExpectedHeight(dPos, star);
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

    private Degree calculateExpectedHeight(DRPosition drPos, Star star) {
        Latitude DRlat = drPos.getLatitude();
        Longitude DRLon = drPos.getLongitude();
        Degree LHA = star.getLHA();

        return Utilities.asin((Utilities.sin(geographicPosition.toDouble()) * Utilities.sin(DRlat.toDouble())) + (Utilities.cos(geographicPosition.toDouble()) * Utilities.cos(DRlat.toDouble()) * Utilities.cos(LHA)));
    }

    private Degree calculateLHA(Star star) {
        // also handle with east and west stuff
        double LHA = star. - Alon;
        if (LHA < 0) {
            LHA += 360;
        } else if (LHA > 360) {
            LHA -= 360;
        }
        return LHA;
    }

    public Latitude getAssumedLatitude() {
        return assumedLatitude;
    }

    public Longitude getAssumedLongitude() {
        return assumedLongitude;
    }

    public Degree getExpectedHeight() {
        return expectedHeight;
    }
}