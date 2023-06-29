package com.IA;

import static java.lang.Math.sqrt;

public class StarSight {
    private Star star;
    private Degree angularHeight;
    private Degree indexCorrection;
    private boolean indexCorrectionOn;
    private Direction hemisphere;

    public StarSight(String name, Degree angularHeight, Degree indexCorrection, boolean indexCorrectionOn, Direction hemisphere) {
        star = new Star(name);
        this.angularHeight = angularHeight;
        this.indexCorrection = indexCorrection;
        this.indexCorrectionOn = indexCorrectionOn;
        this.hemisphere = hemisphere;
    }

    private double angularHeightToObservedHeight(double angularHeight, double indexCorrection, Boolean ICon,
                                                 double eyeHeight) {
        // use altitude correction tables in almanac
        double dip = Utilities.round(sqrt(eyeHeight), 1);
        if (ICon) {
            return angularHeight - indexCorrection - dip;
        }
        return angularHeight + indexCorrection - dip;
    }


}