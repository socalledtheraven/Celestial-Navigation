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

    public double toDouble() {
        // since this is effectively a wrapper for a vector from a point, it can be converted to a scalar +/- difference
        return value.toDouble() * (towards==Towards.TOWARDS ? 1 : -1);
    }

    public String toFormattedString() {
        // for display
        return value + " degrees " + towards.toString().toLowerCase();
    }

    @Override
    public String toString() {
        // for files
        return value.toDouble() + ";" + towards;
    }


}