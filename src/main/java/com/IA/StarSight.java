package com.ia;

import static java.lang.Math.sqrt;

public class StarSight {
    private Degree observedHeight;
    private Direction hemisphere;

    public StarSight(Degree angularHeight, Degree indexCorrection, boolean indexCorrectionOn, double eyeHeight, Direction hemisphere) {
        this.observedHeight = angularHeightToObservedHeight(angularHeight, indexCorrection, indexCorrectionOn, eyeHeight);
        this.hemisphere = hemisphere;
    }

    private Degree angularHeightToObservedHeight(Degree angularHeight, Degree indexCorrection, Boolean ICon, double eyeHeight) {
        // use altitude correction tables in almanac
        Degree dip = new Degree(Utilities.round(sqrt(eyeHeight)*0.97, 1));
        Degree precorrected;
        if (ICon) {
            precorrected = Degree.subtract(angularHeight, indexCorrection);
        } else {
            precorrected = Degree.add(angularHeight, indexCorrection);
        }

        Degree apparentHeight = Degree.divide(Degree.subtract(precorrected, dip), 2);
        System.out.println("apparentHeight: " + apparentHeight);
        Degree observedHeight = Degree.add(apparentHeight, FileHandler.altitudeCorrection(apparentHeight));
        System.out.println("observedHeight: " + observedHeight);
        return observedHeight;
    }

    public Degree getObservedHeight() {
        return observedHeight;
    }
}