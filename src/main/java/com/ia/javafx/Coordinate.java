package com.ia.javafx;

import com.ia.Degree;
import com.ia.Utilities;

import static java.lang.Math.sqrt;

public class Coordinate {
	private double cartesianX;
	private double cartesianY;
	private double radius;
	private double angle;

	public Coordinate(double cartesianX, double cartesianY) {
		this.cartesianX = cartesianX;
		this.cartesianY = cartesianY;
		radius = sqrt((cartesianX * cartesianX) + (cartesianY * cartesianY));
		angle = Math.atan2(cartesianY, cartesianX);
	}

	public Coordinate(double r, Degree a) {
		radius = r;
		angle = a.toDouble();
		angle -= 90;
		cartesianX = radius * Utilities.cos(angle);
		cartesianY = radius * Utilities.sin(angle);
	}

	public double[] getCartesianCoordinates() {
		return new double[] {cartesianX, cartesianY};
	}

	public double[] getPolarCoordinates() {
		return new double[] {radius, angle};
	}
}
