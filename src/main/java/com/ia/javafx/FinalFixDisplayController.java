package com.ia.javafx;

import com.ia.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static java.lang.Math.ceil;

public class FinalFixDisplayController {
	@FXML
	private Pane pane;
	@FXML
	private Button saveFixButton;
	@FXML
	private Button loadFixButton;
	@FXML
	private Button plotButton;
	private ArrayList<String> s;
	private AValue[] a;
	private Degree[] az;
	private Latitude[] alat;
	private Longitude[] alon;
	private Latitude drlat;
	private final String tempPath = "temp/";


	public void loadFixes() {
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

			// clear the existing stars or they'll overlap and look bad
			removeStarDisplay();
			addStarDisplay(fixes.size(), s, a, az, alat, alon, new Latitude(-1));
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid File");
			alert.setContentText("Please select a .fix file");
			alert.showAndWait();
		}
	}

	public void saveFixes() {
		Plot[] p = new Plot[s.size()];
		for (int i = 0; i < s.size(); i++) {
			p[i] = new Plot(s.get(i), alat[i], alon[i], a[i], az[i]);
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save fix file");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Star fix data(*.fix)", "*.fix"));

		File selectedFile = fileChooser.showSaveDialog(saveFixButton.getScene().getWindow());

		// handles errors where the user doesn't name the file or deletes the extension etc
		if (selectedFile != null) {
			if (selectedFile.getName().endsWith(".fix")) {
				FileHandler.savePlot(p, selectedFile.getAbsolutePath());
			}
		}
	}

	private void removeStarDisplay() {
		pane.getChildren().subList(3, pane.getChildren().size()).clear();
	}

	public void addStarDisplay(int numStars, ArrayList<String> stars, AValue[] aValues, Degree[] azimuths,
	                           Latitude[] assumedLatitudes, Longitude[] assumedLongitudes, Latitude DRLatitude) {
		// use double int arr for each position, with all the magic numbers contained within
		drlat = DRLatitude;
		int[][] xPositions;
		int[][] yPositions;

		// numbers worked out through trial and error, completely unworkable for resizing the window
		if (numStars == 2) {
			xPositions = new int[][]{{40, 60, 50}, {290, 310, 300}};
			yPositions = new int[][]{{120, 170, 210, 250, 290}, {120, 170, 210, 250, 290}};
		} else {
			xPositions = new int[][]{{40, 60, 50}, {290, 310, 300}, {136, 160, 150}};
			yPositions = new int[][]{{20, 70, 110, 150, 190}, {20, 70, 110, 150, 190}, {297, 240, 280, 320, 360}};
		}

		s = stars;
		a = aValues;
		az = azimuths;
		alat = assumedLatitudes;
		alon = assumedLongitudes;

		for (int i = 0; i < numStars; i++) {
			Label starLabel = new Label(stars.get(i));
			starLabel.setFont(new Font("System", 22));
			starLabel.setStyle("-fx-border-color: black");
			starLabel.setLayoutX(xPositions[i][0]);
			starLabel.setLayoutY(yPositions[i][0]);

			pane.getChildren().add(starLabel);

			Label aValueLabel = new Label(aValues[i].toFormattedString());
			aValueLabel.setFont(new Font("System", 18));
			aValueLabel.setStyle("-fx-border-color: black");
			aValueLabel.setLayoutX(xPositions[i][1]);
			aValueLabel.setLayoutY(yPositions[i][1]);

			pane.getChildren().add(aValueLabel);

			Label azimuthLabel = new Label(azimuths[i].toString());
			azimuthLabel.setFont(new Font("System", 18));
			azimuthLabel.setStyle("-fx-border-color: black");
			azimuthLabel.setLayoutX(xPositions[i][1]);
			azimuthLabel.setLayoutY(yPositions[i][2]);

			pane.getChildren().add(azimuthLabel);

			Label assumedLatitudeLabel = new Label(assumedLatitudes[i].toString());
			assumedLatitudeLabel.setFont(new Font("System", 18));
			assumedLatitudeLabel.setStyle("-fx-border-color: black");
			assumedLatitudeLabel.setLayoutX(xPositions[i][1]);
			assumedLatitudeLabel.setLayoutY(yPositions[i][3]);

			pane.getChildren().add(assumedLatitudeLabel);

			Label assumedLongitudeLabel = new Label(assumedLongitudes[i].toString());
			assumedLongitudeLabel.setFont(new Font("System", 18));
			assumedLongitudeLabel.setStyle("-fx-border-color: black");
			assumedLongitudeLabel.setLayoutX(xPositions[i][1]);
			assumedLongitudeLabel.setLayoutY(yPositions[i][4]);

			pane.getChildren().add(assumedLongitudeLabel);


			// 34 and 14 are hardcoded approximates to the middle of the labels
			Line mainVerticalLine = new Line(xPositions[i][2], starLabel.getLayoutY()+34, xPositions[i][2],
				assumedLongitudeLabel.getLayoutY()+14);

			pane.getChildren().add(mainVerticalLine);

			Line smallHorizontalLine1 = new Line(xPositions[i][2], aValueLabel.getLayoutY()+14,
					aValueLabel.getLayoutX(), aValueLabel.getLayoutY()+14);
			pane.getChildren().add(smallHorizontalLine1);

			Line smallHorizontalLine2 = new Line(xPositions[i][2], azimuthLabel.getLayoutY()+14,
					azimuthLabel.getLayoutX(), azimuthLabel.getLayoutY()+14);
			pane.getChildren().add(smallHorizontalLine2);

			Line smallHorizontalLine3 = new Line(xPositions[i][2], assumedLatitudeLabel.getLayoutY()+14,
					assumedLatitudeLabel.getLayoutX(), assumedLatitudeLabel.getLayoutY()+14);
			pane.getChildren().add(smallHorizontalLine3);

			Line smallHorizontalLine4 = new Line(xPositions[i][2], assumedLongitudeLabel.getLayoutY()+14,
					assumedLongitudeLabel.getLayoutX(), assumedLongitudeLabel.getLayoutY()+14);
			pane.getChildren().add(smallHorizontalLine4);

			if (i == 2) {
				// finds the width with this elaborate workaround and moves the label to account for it
				final Text text = new Text(stars.get(i));
				text.setFont(new Font("System", 22));
				text.setStyle("-fx-border-color: black");
				new Scene(new Group(text));
				text.applyCss();
				// 1 is a fudge to account for the border
				final double width = ceil(text.getLayoutBounds().getWidth()) + 1;

				starLabel.setLayoutX(starLabel.getLayoutX()-width);
				mainVerticalLine.setStartY(aValueLabel.getLayoutY()+14);

				Line smallHorizontalLine5 = new Line(xPositions[i][0], starLabel.getLayoutY()+17,
						xPositions[i][2], starLabel.getLayoutY()+17);
				pane.getChildren().add(smallHorizontalLine5);

				saveFixButton.setLayoutX(plotButton.getLayoutX());
				loadFixButton.setLayoutX(plotButton.getLayoutX());

				saveFixButton.setLayoutY(plotButton.getLayoutY()-100);
				loadFixButton.setLayoutY(plotButton.getLayoutY()-50);
			}
		}

		// this whole temp fix business is to allow for reloading the same data from the plotting panel
		saveTempFix();
	}

	public void reapplyStarDisplay() {
		String recentFile = FileHandler.getLatestFilefromDir(tempPath).getPath();
		ArrayList<Plot> fixes = FileHandler.loadPlot(recentFile);
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

		removeStarDisplay();
		addStarDisplay(fixes.size(), s, a, az, alat, alon, new Latitude(-1));
	}

	public void saveTempFix() {
		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

		Plot[] p = new Plot[s.size()];
		for (int i = 0; i < s.size(); i++) {
			p[i] = new Plot(s.get(i), alat[i], alon[i], a[i], az[i]);
		}

		// uses unix time as a unique identifier because it doesn't repeat and an actual UUID felt like overkill
		FileHandler.savePlot(p, tempPath + now.toInstant(ZoneOffset.UTC).toEpochMilli());
	}

	public void switchToPlotScreen() throws IOException, InterruptedException {
		FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:javafx/plotScreen.fxml"));
		Parent root = fxmlLoader.load();
		PolarController controller = (PolarController) fxmlLoader.getController();
		int numStars = s.size();
		double[] azimuths = new double[numStars];
		double[] aValues = new double[numStars];

		for (int i = 0; i < numStars; i++) {
			azimuths[i] = az[i].toDouble();
			aValues[i] = a[i].toDouble() * 100;
		}

		controller.plot(numStars, alat[0].toDouble(), azimuths, aValues, alon);

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Star Data Display");
		stage.setWidth(655);
		stage.setHeight(500);
		stage.getIcons().add(new Image("file:javafx/images/icon.png"));
		stage.show();
		pane.getScene().getWindow().hide();
	}
}