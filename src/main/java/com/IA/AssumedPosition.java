package com.IA;

public class AssumedPosition {
    private Latitude assumedLatitude;
    private Longitude assumedLongitude;

    public AssumedPosition(DRPosition dPos, GeographicPosition GP) {
        assumedLatitude = new Latitude(dPos.getLatitude().getDegrees());
    }

    private Degree calculateAssumedLongitude(DRPosition dPos, GeographicPosition GP) {
        double dLonMins = dPos.getLongitude().getMinutes();
        double GHAMins = GP.getMinutes();

        
    }
}