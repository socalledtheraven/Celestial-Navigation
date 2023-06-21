package com.IA;

public class AValue extends Degree {
    public AValue(int degrees, int minutes, int seconds) {
        super(degrees, minutes, seconds);
    }

    public AValue(int degrees, int minutes) {
        super(degrees, minutes);
    }

    public AValue(int degrees) {
        super(degrees);
    }

    public AValue(String strDegrees) {
        super(strDegrees);
    }
}
