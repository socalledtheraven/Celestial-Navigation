package com.ia;

import static java.lang.Math.sqrt;

public class StarSight {
    private Degree observedHeight;

    public StarSight(Degree angularHeight, Degree indexCorrection, boolean indexCorrectionOn, double eyeHeight) {
        this.observedHeight = angularHeightToObservedHeight(angularHeight, indexCorrection, indexCorrectionOn, eyeHeight);
    }

    private Degree angularHeightToObservedHeight(Degree angularHeight, Degree indexCorrection, Boolean ICon, double eyeHeight) {
        // use altitude correction tables in almanac
        // dip is the correction needed to account for the lensing affect of the atmosphere
        Degree precorrected;

        // index error is just how much your sextant is off by
        if (ICon) {
            precorrected = Degree.subtract(angularHeight, indexCorrection);
        } else {
            precorrected = Degree.add(angularHeight, indexCorrection);
        }

        Degree dip = new Degree(Utilities.round(sqrt(eyeHeight)*0.97, 1));
        Degree apparentHeight = Degree.divide(Degree.subtract(precorrected, dip), 2);
        Degree observedHeight = Degree.add(apparentHeight, FileHandler.altitudeCorrection(apparentHeight));
        return observedHeight;
    }

    public Degree getObservedHeight() {
        return observedHeight;
    }
}