package com.ia;

public class Utilities {
    public static double cos(double deg) {
        // all of my code assumes degrees, so this converts to radians first
        return Math.cos(Math.toRadians(deg));
    }

    public static double sin(double deg) {
        // all of my code assumes degrees, so this converts to radians first
        return Math.sin(Math.toRadians(deg));
    }

    public static double asin(double deg) {
        // all of my code assumes degrees, so this converts to degrees after (it'll only be getting radian inputs
        // from the other 2 above)
        // also, avoids errors by correcting for massive numbers with this mod
        if (deg > 1) {
            deg %= Math.PI;
        }
        return Math.toDegrees(Math.asin(deg));
    }

    public static double acos(double deg) {
        // all of my code assumes degrees, so this converts to degrees after (it'll only be getting radian inputs
        // from the other 2 above)
        return Math.toDegrees(Math.acos(deg));
    }

    public static double round(double value, int precision) {
        // custom round function stolen from stackoverflow
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}