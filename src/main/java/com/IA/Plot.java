package com.IA;

public class Plot {
    private AssumedPosition AP;
    private AValue a;
    private double azimuth;

    public Plot(AssumedPosition AP, AValue a, double azimuth) {
        this.AP = AP;
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
