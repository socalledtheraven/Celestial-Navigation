package com.socalledtheraven;

public class User {
    private StarSight[] stars;
    private Time UTC;

    public User(StarSight[] stars, Time UTC) {
        this.stars = stars;
        this.UTC = UTC;
    }

    // VERY IMPORTANT NOTE: {lat, lon}, not {lon, lat}
    public double[] truePosition(double zenithDistance, double declination, ) {

    }
}