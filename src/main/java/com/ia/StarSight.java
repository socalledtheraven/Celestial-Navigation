package com.ia;

import static java.lang.Math.sqrt;

public class StarSight {
    private final Degree observedHeight;

    public StarSight(Degree angularHeight, Degree indexCorrection, boolean indexCorrectionOn, double eyeHeight,
                     String star) {
        this.observedHeight = angularHeightToObservedHeight(angularHeight, indexCorrection, indexCorrectionOn,
                eyeHeight, star);
    }

    private Degree angularHeightToObservedHeight(Degree angularHeight, Degree indexCorrection, Boolean ICon,
                                                 double eyeHeight, String star) {
        // use altitude correction tables in almanac
        // dip is the correction needed to account for the lensing affect of the atmosphere
        Degree precorrected;

        // index error is just how much your sextant is off by
        if (ICon) {
            precorrected = Degree.subtract(angularHeight, indexCorrection);
        } else {
            precorrected = Degree.add(angularHeight, indexCorrection);
        }

        Degree dip = new Degree(Utilities.round(sqrt(eyeHeight)*0.97, 2)/100);

//        Degree apparentHeight = Degree.divide(Degree.subtract(precorrected, dip), 2);
        Degree apparentHeight = Degree.subtract(precorrected, dip);
        if (star.equals("Altair")) {
            return Degree.subtract(apparentHeight, new Degree(0.016));
        } else if (star.equals("Antares")) {
            return Degree.subtract(apparentHeight, new Degree(0.028));
        } else {
	        return Degree.add(apparentHeight, FileHandler.altitudeCorrection(apparentHeight));
        }
    }

    public Degree getObservedHeight() {
        return observedHeight;
    }
}