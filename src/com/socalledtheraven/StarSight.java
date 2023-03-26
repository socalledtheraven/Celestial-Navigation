package com.socalledtheraven;

public class StarSight {
    private double observedHeight;
    private GeographicPosition GP;
    private String name;

    public StarSight(String name, double angularHeight) {
        GP = new GeographicPosition(name);
        observedHeight = angularHeightToObservedHeight(angularHeight);
    }

    private double angularHeightToObservedHeight(double angularHeight) {
        // perform adjustments, treat ah as Ho for now
        return angularHeight;
    }
}
