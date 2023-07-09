package com.IA;

public class Main {
    public static void main(String[] args) {
        /* Hs = angular height
        * */

        String starName = "Alpheratz";
        Star star = new Star(starName);
        DRPosition dr = new DRPosition(new Latitude(40, Direction.NORTH), new Longitude(75, Direction.WEST));
        AssumedPosition ap = new AssumedPosition(dr, star);
        StarSight s = new StarSight(new Degree(84.43), new Degree(0, 1), true, 0.0, Direction.NORTH);
        Degree Ho = s.getObservedHeight();
        double Hc = ap.getExpectedHeight();
        AValue a = new AValue(Hc, Ho);
        Degree az = ap.getAzimuth();
        System.out.println("Azimuth: " + az);
        Plot p = new Plot(ap.getAssumedLatitude(), ap.getAssumedLongitude(), a, az);
        System.out.println(p);
    }
}