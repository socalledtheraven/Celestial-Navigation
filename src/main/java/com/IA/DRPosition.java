package com.ia;

public class DRPosition {
    private Latitude lat;
    private Longitude lon;

    public DRPosition(Latitude lat, Longitude lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Latitude getLatitude() {
        return lat;
    }

    public Longitude getLongitude() {
        return lon;
    }
}