package com.ia.javafx;

import com.ia.Degree;
import com.ia.Utilities;

import static java.lang.Math.sqrt;

public class Point {
	// allows for easy handling both of individual points and cartesian/polar coordinates
	private final double cartesianX;
	private final double cartesianY;
	private final double radius;
	private double angle;

	public Point(double cartesianX, double cartesianY) {
		this.cartesianX = cartesianX;
		this.cartesianY = cartesianY;
		// calculates using pythagoras
		radius = sqrt((cartesianX * cartesianX) + (cartesianY * cartesianY));
		angle = Math.atan2(cartesianY, cartesianX);
	}

	public Point(double r, Degree a) {
		radius = r;
		angle = a.toDouble();
		angle -= 90;
		// calculates back using SOH CAH TOA
		cartesianX = radius * Utilities.cos(angle);
		cartesianY = radius * Utilities.sin(angle);
	}

	public double getX() {
		return cartesianX;
	}

	public double getY() {
		return cartesianY;
	}

	@Override
	public String toString() {
		return "(" + cartesianX + "," + cartesianY + ")";
	}
}