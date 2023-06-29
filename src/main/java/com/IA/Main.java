package com.IA;

public class Main {
    public static void main(String[] args) {
        String starName = "Altair";
        GeographicPosition gp = new GeographicPosition(starName);
        DRPosition dr = new DRPosition(new Latitude(45.30), new Longitude(126.27));
        AssumedPosition ap = new AssumedPosition(dr, gp);
        AValue a = new AValue();
        Plot p = new Plot(ap.getAssumedLatitude(), ap.getAssumedLongitude(), )
    }
}