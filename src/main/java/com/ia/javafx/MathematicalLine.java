package com.ia.javafx;

import javafx.scene.shape.Line;

import static java.lang.Math.abs;

public class MathematicalLine {
	private double gradient;
	private double yIntercept;

	public MathematicalLine(double gradient, double yIntercept) {
		this.gradient = gradient;
		this.yIntercept = yIntercept;
	}

	public MathematicalLine(Line line) {
		this.gradient = (line.getStartY() - line.getEndY()) / (line.getStartX() - line.getEndX());
		this.yIntercept = line.getEndY() - (gradient * line.getEndX());
	}

	public double getY(double x) {
		return (gradient * x) + yIntercept;
	}

	public double getX(double y) {
		return (y - yIntercept) / gradient;
	}

	public Coordinate getCoordinateWithX(double x) {
		return new Coordinate(x, getY(x));
	}

	public Coordinate getCoordinateWithY(double y) {
		return new Coordinate(getX(y), y);
	}

	public static Coordinate getIntercept(MathematicalLine line1, MathematicalLine line2) {
		double combinedIntercept = abs(line1.yIntercept - line2.yIntercept);
		double combinedGradient = abs(line1.gradient - line2.gradient);
		double x = combinedIntercept / combinedGradient;
		return new Coordinate(x, line1.getY(x));
	}
}
