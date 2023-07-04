package com.IA;

public class AValue {
    private final double value;
    private final Towards towards;
    public AValue(double Hc, Degree Ho) {
        super();
        System.out.println(Hc + " " + Ho);
        if (Hc > Ho.getMinutes()) {
            towards = Towards.AWAY;
            value = Hc - Ho.getMinutes();
        } else {
            towards = Towards.TOWARDS;
            value = Ho.getMinutes() - Hc;
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

    @Override
    public String toString() {
        return value + " degrees " + towards;
    }
}