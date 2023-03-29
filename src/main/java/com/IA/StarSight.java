package com.IA;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.acos;

public class StarSight {
    private double observedHeight;
    private GeographicPosition GP;
    private String name;

    public StarSight(String name, double angularHeight) {
        GP = new GeographicPosition(name);
        observedHeight = angularHeightToObservedHeight(angularHeight);
    }

    private double angularHeightToObservedHeight(double angularHeight) {
        // use altitude correction tables in almanac
        return angularHeight;
    }

    public double azimuth(double dec, double aLat, AssumedPosition AP) {
       double Z = acos((sin(dec)-sin(aLat)*sin(AP.getHc())) / (cos(aLat)*cos(AP.getHc())));
       if (Z < 0) {
           return Z+180;
       }
       return Z;
    }
}
