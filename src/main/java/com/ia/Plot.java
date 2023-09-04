package com.ia;

public class Plot {
    // a final data class for handling file inputs/outputs and collecting the important info for a sight together
    private String star;
    private Latitude aLat;
    private Longitude aLon;
    private AValue a;
    private Degree azimuth;

    public Plot(String star, Latitude aLat, Longitude aLon, AValue a, Degree azimuth) {
        this.star = star;
        this.aLat = aLat;
        this.aLon = aLon;
        this.a = a;
        this.azimuth = azimuth;
    }

    public Latitude getAssumedLatitude() {
        return aLat;
    }

    public Longitude getAssumedLongitude() {
        return aLon;
    }

    public AValue getAValue() {
        return a;
    }

    public Degree getAzimuth() {
        return azimuth;
    }

    public String getStar() {
        return star;
    }
}