package com.IA;

public class Main {
    public static void main(String[] args) {
        String starName = "Altair";
        Star star = new Star(starName);
        DRPosition dr = new DRPosition(new Latitude(45.30), new Longitude(126.27));
        AssumedPosition ap = new AssumedPosition(dr, star);
        StarSight s = new StarSight(starName, new Degree(35.189),0, false, 12.0, Direction.NORTH);
        Degree Ho = s.getObservedHeight();
        Degree Hc = ap.getExpectedHeight();
        AValue a = new AValue(Hc, Ho);
        Plot p = new Plot(ap.getAssumedLatitude(), ap.getAssumedLongitude(), a, )
    }
}