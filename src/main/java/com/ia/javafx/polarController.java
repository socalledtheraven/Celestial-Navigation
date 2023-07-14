package com.ia.javafx;

import com.ia.Degree;
import com.ia.Latitude;
import com.ia.Longitude;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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

    @FXML
    protected void setLabels(double longitude) {
        topLabel.setText((longitude+1) + "°");
        midLabel.setText(longitude + "°");
        bottomLabel.setText((longitude-1) + "°");
    }

    @FXML
    protected Coordinate circleCentre() {
        return new Coordinate(compassRose.getLayoutX(), compassRose.getLayoutY());
    }

	@FXML
	protected void addLine(Line line) {
		mainPane.getChildren().add(line);
	}

	@FXML
	protected void drawPoint(Coordinate c, Color color) {
		Circle circle = new Circle(25, c.getCartesianCoordinates()[0], c.getCartesianCoordinates()[1]);
		circle.setFill(color);
		mainPane.getChildren().add(circle);
	}

	@FXML
	protected void plotLine(double radius, double angle) {
		Coordinate centre = circleCentre();
		Coordinate point = new Coordinate(radius, new Degree(angle));
		Line l1 = new Line(centre.getCartesianCoordinates()[0], centre.getCartesianCoordinates()[1],
				320+point.getCartesianCoordinates()[0], 240+point.getCartesianCoordinates()[1]);
		l1.setStrokeWidth(2);
		addLine(l1);
	}

	@FXML
	protected void drawLongitudeLines(Latitude DRLatitude) {
		DRLatitude = new Latitude(Degree.subtract(new Degree(90), DRLatitude).getDegrees());
		Coordinate upperLinePoint = new Coordinate(compassRose.getRadius(), DRLatitude);
		plotLine(compassRose.getRadius(), DRLatitude.getDegrees());
		Coordinate lowerLinePoint = new Coordinate(compassRose.getRadius(), Degree.subtract(DRLatitude,
				new Degree(1)));
		plotLine(compassRose.getRadius(), Degree.subtract(DRLatitude,
				new Degree(1)).toDouble());

		Line l = new Line(100, 100, upperLinePoint.getCartesianCoordinates()[0], midLine.getEndY());
		addLine(l);
		// this one makes the weird line at the top

		Line upperLine = new Line(upperLinePoint.getCartesianCoordinates()[0],
				topLine.getEndY(), upperLinePoint.getCartesianCoordinates()[0], midLine.getEndY());
		Line lowerLine = new Line(lowerLinePoint.getCartesianCoordinates()[0], midLine.getEndY(), lowerLinePoint.getCartesianCoordinates()[0], bottomLine.getEndY());
		addLine(upperLine);
		addLine(lowerLine);
	}
}