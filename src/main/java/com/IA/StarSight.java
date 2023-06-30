package com.IA;

import static java.lang.Math.sqrt;

public class StarSight {
    private Star star;
    private Degree observedHeight;
    private Degree indexCorrection;
    private boolean indexCorrectionOn;
    private Direction hemisphere;

    public StarSight(String name, Degree angularHeight, Degree indexCorrection, boolean indexCorrectionOn, double eyeHeight, Direction hemisphere) {
        star = new Star(name);
        this.observedHeight = angularHeightToObservedHeight(angularHeight, indexCorrection, indexCorrectionOn, eyeHeight);
        this.indexCorrection = indexCorrection;
        this.indexCorrectionOn = indexCorrectionOn;
        this.hemisphere = hemisphere;
    }

    private Degree angularHeightToObservedHeight(Degree angularHeight, Degree indexCorrection, Boolean ICon,
                                                 double eyeHeight) {
        // use altitude correction tables in almanac
        Degree dip = new Degree(Utilities.round(sqrt(eyeHeight), 1));
        Degree precorrected;
        if (ICon) {
            precorrected = Degree.subtract(angularHeight, indexCorrection);
        } else {
            precorrected = Degree.add(angularHeight, indexCorrection);
        }

        return Degree.subtract(precorrected, dip);
    }


}