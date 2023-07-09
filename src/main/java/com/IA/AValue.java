package com.IA;

public class AValue {
    private final Degree value;
    private final Towards towards;
    public AValue(double Hc, Degree Ho) {
        super();
        if (Hc > Ho.getMinutes()) {
            towards = Towards.AWAY;
            value = new Degree(Hc - Ho.toDouble());
        } else {
            towards = Towards.TOWARDS;
            value = new Degree(Ho.toDouble() - Hc);
        }

        System.out.println(this);
    }

    public AValue(String a) {
        String[] parts = a.split("=");
        value = new Degree(parts[0]);
        towards = Towards.valueOf(parts[1]);
    }

    public Degree getValue() {
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