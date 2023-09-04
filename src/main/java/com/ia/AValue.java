package com.ia;

public class AValue {
    private final Degree value;
    private final Towards towards;
    public AValue(double Hc, Degree Ho) {
        super();
        // calculates the difference between Hc and Ho (ie the difference between the sight at the Assumed position
        // and the sight at the real position) so the distance between the two
        if (Hc > Ho.getMinutes()) {
            towards = Towards.AWAY;
            value = new Degree(Hc - Ho.toDouble());
        } else {
            towards = Towards.TOWARDS;
            value = new Degree(Ho.toDouble() - Hc);
        }
    }

    public AValue(String a) {
        String[] parts = a.split(";");
        value = new Degree(Double.parseDouble(parts[0]));
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
        return value.toDouble() + ";" + towards;
    }

    public String toFormattedString() {
        return value + " degrees " + towards.toString().toLowerCase();
    }
}