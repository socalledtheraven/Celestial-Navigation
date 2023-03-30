package com.IA;

import static java.lang.Math.*;

public class StarSight {
    private final double observedHeight;
    private final String name;

    public StarSight(String name, double angularHeight, double indexCorrection, Boolean ICon, double eyeHeight) {
        this.name = name;
        GeographicPosition GP = new GeographicPosition(name);
        observedHeight = angularHeightToObservedHeight(angularHeight, indexCorrection, ICon, eyeHeight);
    }

    private double angularHeightToObservedHeight(double angularHeight, double indexCorrection, Boolean ICon,
                                                 double eyeHeight) {
        // use altitude correction tables in almanac
        return 35.141;
        // REMOVE ONCE TESTING IS DONE!!!!!!!!!!!!!!!
//        double dip = Utilities.round(sqrt(eyeHeight), 1);
//        if (ICon) {
//            return angularHeight - indexCorrection - dip;
//        }
//        return angularHeight + indexCorrection - dip;
    }

    public double calculateAzimuth(double dec, AssumedPosition AP) {
        double aLat = AP.getAlat();
        double Z = acos((sin(dec) - sin(aLat) * sin(AP.getHc())) / (cos(aLat) * cos(AP.getHc())));
        if (Z < 0) {
            return Z+180;
        }
        return Z;
    }
    
    private double cos(double deg) {
        return Math.toDegrees(Math.cos(Math.toRadians(deg)));
    }

    private double sin(double deg) {
        return Math.toDegrees(Math.sin(Math.toRadians(deg)));
    }

    private double acos(double deg) {
        return Math.toDegrees(Math.acos(Math.toRadians(deg)));
    }

    public double getObservedHeight() {
        return observedHeight;
    }

    public String getName() {
        return name;
    }
}