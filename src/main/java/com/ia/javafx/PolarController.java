package com.ia.javafx;

import com.ia.Degree;
import com.ia.Latitude;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static java.lang.Math.sqrt;

public class PolarController {
	private static final Logger logger = LogManager.getLogger();
	@FXML
	private Pane pane;
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
	private double CIRCLERADIUS;
	private double LONRATIO;
	private FinalFixDisplayController controller;

    protected void setLabels(double longitude) {
		// called at the start to make the labels correct
		topLabel.setText((longitude+1) + "°");
        midLabel.setText(longitude + "°");
        bottomLabel.setText((longitude-1) + "°");
    }

    protected Point circleCentre() {
        return new Point(compassRose.getLayoutX(), compassRose.getLayoutY());
    }

	protected Line extendLine(Line line) {
		// extends a line both for display purposes and because default [Line]s are more vectors, so finding
		// intercepts is pain
		double extensionRatio = (double) 1 /5;
		line.setStartX(line.getStartX() - (extensionRatio*(line.getEndX()-line.getStartX())));
		line.setEndX(line.getEndX() + (extensionRatio*(line.getEndX()-line.getStartX())));
		line.setStartY(line.getStartY() - (extensionRatio*(line.getEndY()-line.getStartY())));
		line.setEndY(line.getEndY() + (extensionRatio*(line.getEndY()-line.getStartY())));
		return line;
	}

	protected Point getIntercept(Line line, double distance, Point start) {
		// this is not the point of intersection, this is the intercept
		// that means that this is [distance] distance away from [start] along the vector [line]
		double xDistance = line.getEndX() - start.getX() - 320;
		double yDistance = line.getEndY() - start.getY() - 240;
		// finds the amount of the hypotenuse that the distance takes up and therefore how much the other sides need
		// to shift by
		double ratio = distance / sqrt(xDistance*xDistance + yDistance*yDistance);
		logger.info("Proportion of distance vs azimuth line past the azimuth point: " + ratio);

		return new Point(start.getX() + (xDistance * ratio), start.getY() + (yDistance * ratio));
	}

	protected void addLine(Line line) {
		// 320 & 240 are accounting for the compass rose not being at (0,0)
		line.setStartX(line.getStartX()+320);
		line.setEndX(line.getEndX()+320);
		line.setStartY(line.getStartY()+240);
		line.setEndY(line.getEndY()+240);
		pane.getChildren().add(line);
	}

	protected void plotLine(double radius, double angle) {
		// useful for debugging and displaying polar coordinates
		Point centre = circleCentre();
		Point point = new Point(radius, new Degree(angle));
		Line line = new Line(centre.getX(), centre.getY(),
				320+point.getX(), 240+point.getY());
		line.setStrokeWidth(2);
		pane.getChildren().add(line);
	}

	protected void plotLine(Point point) {
		// useful for debugging and displaying polar coordinates (overloaded of the above)
		Point centre = circleCentre();
		Line line = new Line(centre.getX(), centre.getY(),
				320+point.getX(), 240+point.getY());
		line.setStrokeWidth(2);
		pane.getChildren().add(line);
	}

	protected double drawLongitudeLines(Latitude DRLatitude) {
		// finds the correct x and then draws a line from the top line to the middle, and the modified line from
		// middle to bottom
		DRLatitude = new Latitude(Degree.subtract(new Degree(90), DRLatitude).getDegrees());
		Point upperLinePoint = new Point(compassRose.getRadius(), DRLatitude);
		Point lowerLinePoint = new Point(compassRose.getRadius(), Degree.add(DRLatitude,
				new Degree(1)));

		double topPoint = circleCentre().getX() + upperLinePoint.getX();
		double lowerPoint = circleCentre().getX() + lowerLinePoint.getX();
		Line upperLine = new Line(topPoint, topLine.getLayoutY(), topPoint, midLine.getLayoutY());
		Line lowerLine = new Line(lowerPoint, midLine.getLayoutY(), lowerPoint, bottomLine.getLayoutY());

		pane.getChildren().add(upperLine);
		pane.getChildren().add(lowerLine);
		return topPoint-320;
	}

	protected double longitudeScale(double y) {
		// the accounting for the fact that the earth is a sphere and longitudes aren't consistent across that
		// circle radius is 90, but we need it to match with the 60 degrees compass rose circle
		return (((double) 2 /3)*sqrt(8100 - 2*y*y)/60);
	}
	
	public void plot(int numOfStars, double DRLatitude, double[] azimuths, double[] aValues, double[] aLonDegrees,
	                 FinalFixDisplayController c) {
		LONRATIO = longitudeScale(DRLatitude);
		CIRCLERADIUS = compassRose.getRadius();
		controller = c;

		double[] lonPoints = new double[numOfStars];
		Point[] assumedLongitudePoints = new Point[numOfStars];
		Point[] azimuthPoints = new Point[numOfStars];
		Line[] azimuthLines = new Line[numOfStars];
		Point[] interceptPoints = new Point[numOfStars];
		Point[] degreePoints = new Point[numOfStars];
		Line[] linesOfPosition = new Line[numOfStars];

//		for (int i = 0; i < numOfStars; i++) {
//			setLabels(DRLatitude);
//			lonPoints[i] = drawLongitudeLines(new Latitude((int) DRLatitude));
//
//			// locates and draws the point of assumed longitude
//			// divides by 60 bc degrees vs minutes, * by lonratio and circle radius to make sure it's the correct length
//			assumedLongitudePoints[i] = new Point(lonPoints[i]-((aLonDegrees[i]/60)*LONRATIO*CIRCLERADIUS),
//					new Degree(90));
//			logger.info("Number " + i + " alon is " + assumedLongitudePoints[i]);
//			azimuthPoints[i] = new Point(CIRCLERADIUS, new Degree(azimuths[i]));
//			azimuthLines[i] = extendLine(new Line(azimuthPoints[i].getX(), azimuthPoints[i].getY(), assumedLongitudePoints[i].getX(), assumedLongitudePoints[i].getY()));
//
//			interceptPoints[i] = getIntercept(azimuthLines[i], (aValues[i]/60)*LONRATIO*CIRCLERADIUS, assumedLongitudePoints[i]);
//			logger.info("Number " + i + " intercept is " + interceptPoints[i]);
//			degreePoints[i] = new Point(CIRCLERADIUS, new Degree(azimuths[i]-270));
//			linesOfPosition[i] = extendLine(new Line(degreePoints[i].getX(), degreePoints[i].getY(), interceptPoints[i].getX(),
//					interceptPoints[i].getY()));
//			linesOfPosition[i].setStroke(Color.RED);
//			linesOfPosition[i].setStrokeWidth(2);
//		}
//
//		for (int i = 0; i < numOfStars; i++) {
//			addLine(azimuthLines[i]);
//			addLine(linesOfPosition[i]);
//		}
//
//		Point intersectionPoint = MathematicalLine.getIntercept(new MathematicalLine(azimuthLines[0]),
//				new MathematicalLine(azimuthLines[1]),
//				new MathematicalLine(azimuthLines[2]));
//		logger.info("Intersection point is " + intersectionPoint);
//
//		double finalLon = (60*(intersectionPoint.getX() - 320))/LONRATIO/CIRCLERADIUS;
//		double finalLat = (intersectionPoint.getY() - 240)*60/CIRCLERADIUS;
//
//		System.out.println("You are at " + finalLat + ", " + finalLon);
	}

	public void displayValues() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("finalFixDisplayScreen.fxml"));
		Parent root = fxmlLoader.load();

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Star Data Display");
		stage.setWidth(550);
		stage.setHeight(450);
		stage.getIcons().add(new Image("file:src/main/resources/com/ia/javafx/images/icon.png"));
		stage.show();
		pane.getScene().getWindow().hide();

		controller.reapplyStarDisplay();
	}
}