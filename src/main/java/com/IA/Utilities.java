package com.IA;

public class Utilities {
    public static double cos(double deg) {
        return Math.toDegrees(Math.cos(Math.toRadians(deg)));
    }

    public static double sin(double deg) {
        return Math.toDegrees(Math.sin(Math.toRadians(deg)));
    }

    public static double asin(double deg) {
        return Math.toDegrees(Math.asin(Math.toRadians(deg)));
    }

    public static double acos(double deg) {
        return Math.toDegrees(Math.acos(Math.toRadians(deg)));
    }

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}