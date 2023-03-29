package com.IA;

public class AValue {
	private final double value;
	private final Boolean isTowards;

	public AValue(double Hc, double Ho) {
		if (Hc > Ho) {
			isTowards = false;
			value = Utilities.getMinutes(Hc) - Utilities.getMinutes(Ho);
		} else {
			isTowards = true;
			value = Utilities.getMinutes(Ho) - Utilities.getMinutes(Hc);
		}
	}

	public double getValue() {
		return value;
	}

	public Boolean getTowards() {
		return isTowards;
	}
}
