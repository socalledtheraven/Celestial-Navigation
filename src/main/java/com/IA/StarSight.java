package com.IA;

import static java.lang.Math.sqrt;

public class StarSight {
    private Degree observedHeight;
    private Direction hemisphere;

    public StarSight(String name, Degree angularHeight, double indexCorrection, boolean indexCorrectionOn, double eyeHeight, Direction hemisphere) {
        this.observedHeight = angularHeightToObservedHeight(angularHeight, indexCorrection, indexCorrectionOn, eyeHeight);
        this.hemisphere = hemisphere;
    }

    private Degree angularHeightToObservedHeight(Degree angularHeight, double indexCorrection, Boolean ICon,
                                                 double eyeHeight) {
        // use altitude correction tables in almanac
        Degree dip = new Degree(Utilities.round(sqrt(eyeHeight), 1));
        Degree precorrected;
        if (ICon) {
            precorrected = Degree.subtract(angularHeight, new Degree(indexCorrection));
        } else {
            precorrected = Degree.add(angularHeight, new Degree(indexCorrection));
        }

        return Degree.subtract(precorrected, dip);
    }

    public Degree getObservedHeight() {
        return observedHeight;
    }
}