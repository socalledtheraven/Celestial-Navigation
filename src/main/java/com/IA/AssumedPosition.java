package com.IA;

public class AssumedPosition {
    private Latitude assumedLatitude;
    private Longitude assumedLongitude;
    private Degree expectedHeight;

    public AssumedPosition(DRPosition dPos, GeographicPosition GP) {
        assumedLatitude = new Latitude(dPos.getLatitude().getDegrees());
        assumedLongitude = calculateAssumedLongitude(dPos, GP);
        expectedHeight = calculateExpectedHeight();
    }

    private Longitude calculateAssumedLongitude(DRPosition dPos, GeographicPosition GP) {
        int dLonMins = dPos.getLongitude().getMinutes();
        int GHAMins = GP.getMinutes();

        int distanceToY = Math.abs(dLonMins-GHAMins);
        int distanceToYPlusOne = Math.abs(dLonMins - (GHAMins + 1));
        // make alon within 0.3 of DR-lon, ignoring whole-number differences.
        int dLonDegrees = dPos.getLongitude().getDegrees();
        if (distanceToY > distanceToYPlusOne) {
            return new Longitude(dLonDegrees, GHAMins);
        } else {
            return new Longitude(dLonDegrees + 1, GHAMins);
        }
    }

    private Degree calculateExpectedHeight(GeographicPosition geographicPosition, DRPosition drPos) {
        Latitude DRlat = drPos.getLatitude();
        Longitude DRLon = drPos.getLongitude();

        return Utilities.asin((Utilities.sin(geographicPosition) * Utilities.sin(DRlat)) + (Utilities.cos(geographicPosition) * Utilities.cos(DRlat) * Utilities.cos(LHA)));
    }

    public Latitude getAssumedLatitude() {
        return assumedLatitude;
    }

    public Longitude getAssumedLongitude() {
        return assumedLongitude;
    }
}