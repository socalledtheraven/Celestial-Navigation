package com.ia.javafx;

import com.ia.Degree;
import com.ia.Latitude;
import com.ia.Longitude;
import com.ia.Utilities;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class polarController {
	@FXML
	private Pane mainPane;
    @FXML
    private Label topLabel;
	@FXML
	private Line topLine;
    @FXML
    private Label midLabel;
	@FXML
	private Line midLine;
    @FXML
    private Label bottomLabel;
	@FXML
	private Line bottomLine;
    @FXML
    private Circle compassRose;

    protected void setLabels(double longitude) {
        topLabel.setText((longitude+1) + "°");
        midLabel.setText(longitude + "°");
        bottomLabel.setText((longitude-1) + "°");
    }

    protected Coordinate circleCentre() {
        return new Coordinate(compassRose.getLayoutX(), compassRose.getLayoutY());
    }

	protected double compassRoseRadius() {
		return compassRose.getRadius();
	}

	protected Line extendLine(Line line) {
		double extensionRatio = (double) 1 /5;
		line.setStartX(line.getStartX() - (extensionRatio*(line.getEndX()-line.getStartX())));
		line.setEndX(line.getEndX() + (extensionRatio*(line.getEndX()-line.getStartX())));
		line.setStartY(line.getStartY() - (extensionRatio*(line.getEndY()-line.getStartY())));
		line.setEndY(line.getEndY() + (extensionRatio*(line.getEndY()-line.getStartY())));
		return line;
	}

	protected Coordinate getIntercept(Line line, double distance, Coordinate start) {
		double xDistance = line.getEndX() - start.getX() - 320;
		double yDistance = line.getEndY() - start.getY() - 240;
		double ratio = distance / sqrt(xDistance*xDistance + yDistance*yDistance);

		return new Coordinate(xDistance * ratio, yDistance * ratio);
	}

	protected void addLine(Line line) {
		line.setStartX(line.getStartX()+320);
		line.setEndX(line.getEndX()+320);
		line.setStartY(line.getStartY()+240);
		line.setEndY(line.getEndY()+240);
		mainPane.getChildren().add(line);
	}

	protected void drawPoint(Coordinate c, Color color) {
		Circle circle = new Circle(25, c.getX(), c.getY());
		circle.setFill(color);
		mainPane.getChildren().add(circle);
	}

	protected void plotLine(double radius, double angle) {
		Coordinate centre = circleCentre();
		Coordinate point = new Coordinate(radius, new Degree(angle));
		Line line = new Line(centre.getX(), centre.getY(),
				320+point.getX(), 240+point.getY());
		line.setStrokeWidth(2);
		mainPane.getChildren().add(line);
	}

	protected void plotLine(Coordinate point) {
		Coordinate centre = circleCentre();
		Line line = new Line(centre.getX(), centre.getY(),
				320+point.getX(), 240+point.getY());
		line.setStrokeWidth(2);
		mainPane.getChildren().add(line);
	}

	protected double drawLongitudeLines(Latitude DRLatitude) {
		DRLatitude = new Latitude(Degree.subtract(new Degree(90), DRLatitude).getDegrees());
		Coordinate upperLinePoint = new Coordinate(compassRose.getRadius(), DRLatitude);
		Coordinate lowerLinePoint = new Coordinate(compassRose.getRadius(), Degree.add(DRLatitude,
				new Degree(1)));

		double topPoint = circleCentre().getX() + upperLinePoint.getX();
		double lowerPoint = circleCentre().getX() + lowerLinePoint.getX();
		Line upperLine = new Line(topPoint, topLine.getLayoutY(), topPoint, midLine.getLayoutY());
		Line lowerLine = new Line(lowerPoint, midLine.getLayoutY(), lowerPoint, bottomLine.getLayoutY());

		mainPane.getChildren().add(upperLine);
		mainPane.getChildren().add(lowerLine);
		return topPoint;
	}

	protected double longitudeScale(double y) {
		// circle radius is 90 but we need it to match with the 60 degrees compass rose circle
		return (((double) 2 /3)*sqrt(8100 - 2*y*y)/60);
	}
}