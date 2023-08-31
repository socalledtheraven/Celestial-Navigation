package com.ia.javafx;

import javafx.scene.shape.Line;

import static java.lang.Math.abs;

public class MathematicalLine {
	// a class for lines in format y=mx+c for distances, gradients and so on
	// most importantly, allows for intercepts - absolutely crucial for finding actual positions
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

	public static Point getIntercept(MathematicalLine line1, MathematicalLine line2) {
		double combinedIntercept = abs(line1.yIntercept - line2.yIntercept);
		double combinedGradient = abs(line1.gradient - line2.gradient);
		double x = combinedIntercept / combinedGradient;
		return new Point(x, line1.getY(x));
	}

	public static Point getIntercept(MathematicalLine line1, MathematicalLine line2, MathematicalLine line3) {
		// yes, I know this only uses two for the intercept. this is because there's enough floating-point
		// imperfections that the lines are unlikely to really intersect perfectly
		double combinedIntercept = abs(line1.yIntercept - line2.yIntercept);
		double combinedGradient = abs(line1.gradient - line2.gradient);
		double x = combinedIntercept / combinedGradient;
		return new Point(x, line1.getY(x));
	}
}