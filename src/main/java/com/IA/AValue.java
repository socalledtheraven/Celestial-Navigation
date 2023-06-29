package com.IA;

public class AValue {
    private final double value;
    private final Towards towards;
    public AValue(Degree Hc, Degree Ho) {
        super();
        if (Hc.getMinutes() > Ho.getMinutes()) {
            towards = Towards.AWAY;
            value = Hc.getMinutes() - Ho.getMinutes();
        } else {
            towards = Towards.TOWARDS;
            value = Ho.getMinutes() - Hc.getMinutes();
        }
    }

    public AValue(String a) {
        String[] parts = a.split("=");
        value = Double.parseDouble(parts[0]);
        towards = Towards.valueOf(parts[1]);
    }

    public double getValue() {
        return value;
    }

    public Towards getTowards() {
        return towards;
    }
}
