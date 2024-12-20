package com.ia.javafx;

import javafx.scene.shape.Line;

import static java.lang.Math.abs;

public class MathematicalLine {
	// a class for lines in format y=mx+c for distances, gradients and so on
	// most importantly, allows for intercepts - absolutely crucial for finding actual positions
	private final double gradient;
	private final double yIntercept;

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

	public static Point getIntercept(MathematicalLine line1, MathematicalLine line2) {
		// I use this even for triple line intersections, because due to floating-point errors, inadequately precise
		// almanac entries, etc., the three lines will almost certainly not align perfectly
		double combinedIntercept = abs(line1.yIntercept - line2.yIntercept);
		double combinedGradient = abs(line1.gradient - line2.gradient);
		double x = combinedIntercept / combinedGradient;
		return new Point(x, line1.getY(x));
	}
}