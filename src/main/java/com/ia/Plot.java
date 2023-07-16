package com.ia;

public class Plot {
    // a final data class for handling file inputs/outputs and collecting the important info for a sight together
    private Latitude aLat;
    private Longitude aLon;
    private AValue a;
    private Degree azimuth;

    public Plot(Latitude aLat, Longitude aLon, AValue a, Degree azimuth) {
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

    @Override
    public String toString() {
        return "Plot{" +
                "aLat=" + aLat +
                ", aLon=" + aLon +
                ", a=" + a.toString() +
                ", azimuth=" + azimuth +
                '}';
    }
}