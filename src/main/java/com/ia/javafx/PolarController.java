package com.ia.javafx;

import com.ia.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class PolarController {
//	private static final Logger logger = LogManager.getLogger();
	@FXML
	private Pane pane;
	@FXML
	private Label topLabel;
	@FXML
	private Label leftLonLabel;
	@FXML
	private Label midLabel;
	@FXML
	private Label midLonLabel;
	@FXML
	private Label bottomLabel;
	@FXML
	private Label rightLonLabel;
	@FXML
	private Circle compassRose;
	@FXML
	private Button loadFixButton;
	@FXML
	private Button saveFixButton;

	public void loadFixes() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open fix file");
		File f = fileChooser.showOpenDialog(loadFixButton.getScene().getWindow());
		if (f.getName().contains(".fix")) {
			ArrayList<Plot> fixes = FileHandler.loadPlot(f.getAbsolutePath());
			ArrayList<String> s = new ArrayList<>();

			AValue[] a = new AValue[fixes.size()];
			Degree[] az = new Degree[fixes.size()];
			Latitude[] alat = new Latitude[fixes.size()];
			Longitude[] alon = new Longitude[fixes.size()];

			for (int i = 0; i < fixes.size(); i++) {
				s.add(i, fixes.get(i).getStar());
				a[i] = fixes.get(i).getAValue();
				az[i] = fixes.get(i).getAzimuth();
				alat[i] = fixes.get(i).getAssumedLatitude();
				alon[i] = fixes.get(i).getAssumedLongitude();
			}

			// displays the data instead of plotting it right away - this is mostly because of the temp files, which will
			// break if not properly called through addStarDisplay, so the user will get an error when clicking on the
			// back button
			FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:javafx/finalFixDisplayScreen.fxml"));
			Parent root = fxmlLoader.load();
			FinalFixDisplayController controller = (FinalFixDisplayController) fxmlLoader.getController();

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Star Data Display");
			stage.setWidth(550);
			stage.setHeight(450);
			stage.getIcons().add(new Image("file:javafx/images/icon.png"));
			stage.show();
			pane.getScene().getWindow().hide();

			controller.addStarDisplay(fixes.size(), s, a, az, alat, alon, new Latitude(-1));
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid File");
			alert.setContentText("Please select a .fix file");
			alert.showAndWait();
		}
	}

	public void saveFixes() {
		String tempPath = "temp/";
		File recentFile = FileHandler.getLatestFilefromDir(tempPath);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save fix file");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Star fix data(*.fix)", "*.fix"));

		File selectedFile = fileChooser.showSaveDialog(saveFixButton.getScene().getWindow());

		// rather than go through the bother of recollecting the information and storing it, I just take the last
		// temp file, which is guaranteed to have the data in
		if (selectedFile != null) {
			if (selectedFile.getName().endsWith(".fix")) {
				FileHandler.replaceFile(selectedFile, recentFile);
			}
		}
	}

    protected void setLabels(double latitude, double longitude) {
		// called at the start to make the labels correct
		topLabel.setText((latitude+1) + "°");
        midLabel.setText(latitude + "°");
        bottomLabel.setText((latitude-1) + "°");
		leftLonLabel.setText((longitude+1) + "°");
	    midLonLabel.setText(longitude + "°");
	    rightLonLabel.setText((longitude-1) + "°");
    }

	protected Line line(Point p, double angle) {
		Point point = new Point(compassRose.getRadius(), new Degree(angle));
		return new Line(p.getX(), p.getY(), p.getX()+point.getX(), p.getY()+point.getY());
	}

    protected Point circleCentre() {
        return new Point(compassRose.getLayoutX(), compassRose.getLayoutY());
    }

	protected Line extendLine(Line line) {
		// extends a line both for display purposes and because default [Line]s are more vectors, so finding
		// intercepts is pain
		double extensionRatio = (double) 1/5;
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
//		logger.info("Proportion of distance vs azimuth line past the azimuth point: " + ratio);

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

	protected double longitudeScale(double lat, double lonMins) {
		// the accounting for the fact that the earth is a sphere and longitudes aren't consistent across that
		// divide by 90 to get distance/minute
		return (sqrt(8100 - 2*lat*lat)/90) * lonMins;
	}

	protected double reverseLongitudeScale(double lat, double length) {
		return length / (sqrt(8100 - 2*lat*lat)/90);
	}

	protected void plotLine(Point p) {
		// useful for debugging and displaying polar coordinates
		Point centre = circleCentre();
		Line line = new Line(centre.getX(), centre.getY(),
				320+p.getX(), 240+p.getY());
		line.setStrokeWidth(2);
		line.setStroke(Color.BLUE);
		pane.getChildren().add(line);
	}
	
	public void plot(int numOfStars, double aLat, double[] azimuths, double[] aValues, Degree[] aLons) {
		final double CIRCLERADIUS = compassRose.getRadius();
		final double LATRATIO = CIRCLERADIUS/60;

		int smallest = 361;
		for (int i = 0; i < numOfStars; i++) {
			if (aLons[i].getDegrees() < smallest) {
				smallest = aLons[i].getDegrees();
			}
		}
		setLabels(aLat, smallest+1);

		Point[] assumedLongitudePoints = new Point[numOfStars];
		Line[] azimuthLines = new Line[numOfStars];
		Point[] interceptPoints = new Point[numOfStars];
		Line[] linesOfPosition = new Line[numOfStars];

		for (int i = 0; i < numOfStars; i++) {
			// locates and draws the point of assumed longitude
			// divides by 60 bc degrees vs minutes, * by lonratio and circle radius to make sure it's the correct length
			if (aLons[i].getDegrees() < smallest+1) {
				assumedLongitudePoints[i] = new Point((rightLonLabel.getLayoutX()-320) - (LATRATIO * longitudeScale(aLat,
						aLons[i].getMinutes())), 0);
			} else {
				assumedLongitudePoints[i] = new Point((midLonLabel.getLayoutX()-320) - (LATRATIO * longitudeScale(aLat,
						aLons[i].getMinutes())), 0);
			}
			azimuthLines[i] = extendLine(line(assumedLongitudePoints[i], azimuths[i]));

			interceptPoints[i] = getIntercept(azimuthLines[i], aValues[i]*LATRATIO, assumedLongitudePoints[i]);
			plotLine(interceptPoints[i]);
			System.out.println("Number " + (i+1) + " intercept is " + interceptPoints[i]);
			linesOfPosition[i] = extendLine(line(interceptPoints[i],azimuths[i]+270));
			linesOfPosition[i].setStroke(Color.RED);
			linesOfPosition[i].setStrokeWidth(2);
		}

		for (int i = 0; i < numOfStars; i++) {
			addLine(azimuthLines[i]);
			addLine(linesOfPosition[i]);
		}

		// as mentioned inside, we only use 2 lines
		Point intersectionPoint = MathematicalLine.getIntercept(new MathematicalLine(linesOfPosition[0]),
				new MathematicalLine(linesOfPosition[1]));
//		logger.info("Intersection point is " + intersectionPoint);

		double finalLon;
		if (intersectionPoint.getX() > midLonLabel.getLayoutX()) {
			finalLon =
					reverseLongitudeScale(aLat, rightLonLabel.getLayoutX() - intersectionPoint.getX()) / LATRATIO;
		} else {
			finalLon =
					reverseLongitudeScale(aLat, midLonLabel.getLayoutX() - intersectionPoint.getX()) / LATRATIO;
		}
		double finalLat = (intersectionPoint.getY() - 240) / LATRATIO;

		Alert a = new Alert(Alert.AlertType.INFORMATION,
				"You are at " + Utilities.round(finalLat, 2) + "°, " + Utilities.round(finalLon, 2) +
				"°",
				ButtonType.OK);
		Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:javafx/images/icon.png"));
		a.show();
	}

	public void displayValues() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:javafx/finalFixDisplayScreen.fxml"));
		Parent root = fxmlLoader.load();
		FinalFixDisplayController controller = (FinalFixDisplayController) fxmlLoader.getController();

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Star Data Display");
		stage.setWidth(550);
		stage.setHeight(450);
		stage.getIcons().add(new Image("file:javafx/images/icon.png"));
		stage.show();
		pane.getScene().getWindow().hide();

		controller.reapplyStarDisplay();
	}
}