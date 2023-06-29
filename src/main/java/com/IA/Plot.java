package com.IA;

public class Plot {
    private Latitude aLat;
    private Longitude aLon;
    private AValue a;
    private double azimuth;

    public Plot(Latitude aLat, Longitude aLon, AValue a, double azimuth) {
        this.aLat = aLat;
        this.aLon = aLon;
        this.a = a;
        this.azimuth = azimuth;
    }

    public AssumedPosition getAP() {
        return AP;
    }

    public AValue getA() {
        return a;
    }

    public double getAzimuth() {
        return azimuth;
    }
}