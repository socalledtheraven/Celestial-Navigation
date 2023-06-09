package com.IA;

public class Degree {
    private int degrees;
    private int minutes;
    private int seconds;

    public Degree(int degrees, int minutes, int seconds) {
        this.degrees = degrees;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public Degree(int degrees, int minutes) {
        this.degrees = degrees;
        this.minutes = minutes;
    }

    public Degree(int degrees) {
        this.degrees = degrees;
    }
}
