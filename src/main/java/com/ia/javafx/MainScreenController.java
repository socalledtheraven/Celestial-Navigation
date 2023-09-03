package com.ia.javafx;

import com.ia.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainScreenController {
	@FXML
	private Button addStarButton;
	@FXML
	private Button loadPlotButton;
	@FXML
	private Pane pane;
	private int starsNum = 0;
	private Line firstVertical;
	private String[] stars;
	private Degree[] angularHeights;
	private Degree[] indexCorrections;
	private boolean[] indexCorrectionOnValues;

	@FXML
	public void addStar() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("starSelectScreen.fxml"));
		Parent root = fxmlLoader.load();
		StarAddController controller = (StarAddController) fxmlLoader.getController();
		controller.loadStars();
		controller.setMain(this);

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Star Input");
		stage.setWidth(530);
		stage.setHeight(430);
		stage.getIcons().add(new Image("file:src/main/resources/com/ia/javafx/images/icon.png"));
		stage.show();
	}

	public void addDeterminedPosition() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("determinedPositionInputScreen.fxml"));
		Parent root = fxmlLoader.load();
		DPosController controller = (DPosController) fxmlLoader.getController();
		controller.loadHemispheres();
		controller.setMain(this);

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Determined Position Input");
		stage.setWidth(530);
		stage.setHeight(430);
		stage.getIcons().add(new Image("file:src/main/resources/com/ia/javafx/images/icon.png"));
		stage.show();
	}

	public void switchToFinalDisplay(int numStars, AValue[] aValues, Degree[] azimuths,
	                                 Latitude[] assumedLatitudes, Longitude[] assumedLongitudes) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("finalFixDisplayScreen.fxml"));
		Parent root = fxmlLoader.load();
		FinalFixDisplayController controller = (FinalFixDisplayController) fxmlLoader.getController();

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Star Data Display");
		stage.setWidth(550);
		stage.setHeight(470);
		stage.getIcons().add(new Image("file:src/main/resources/com/ia/javafx/images/icon.png"));
		pane.getScene().getWindow().hide();
		controller.addStarDisplay(numStars, stars, aValues, azimuths, assumedLatitudes, assumedLongitudes);
		stage.show();
	}

	@FXML
	public void loadPlot() throws IOException {
		FileChooser fileChooser = new FileChooser();
		File f = fileChooser.showOpenDialog(loadPlotButton.getScene().getWindow());
		Plot[] plots = FileHandler.loadPlot(f.getAbsolutePath());

	}

	public void addStarDisplay(String star, String angularHeight, String indexCorrection, boolean indexCorrectionOn) {
		// use double int arr for each position, with all the magic numbers contained within
		int[][] xPositions = {{20, 40, 30, 14}, {20, 40, 30, 14}, {200, 220, 210, -1}};
		int[][] yPositions = {{20, 70, 110, 150, 200}, {200, 250, 290, 330, 380}, {100, 150, 190, 230, -1}};

		stars[starsNum] = star;
		Label starLabel = new Label(star);
		starLabel.setFont(new Font("System", 26));
		starLabel.setStyle("-fx-border-color: black");
		starLabel.setLayoutX(xPositions[starsNum][0]);
		starLabel.setLayoutY(yPositions[starsNum][0]);

		pane.getChildren().add(starLabel);

		angularHeights[starsNum] = new Degree(angularHeight);
		Label angularHeightLabel = new Label(angularHeight);
		angularHeightLabel.setFont(new Font("System", 21));
		angularHeightLabel.setStyle("-fx-border-color: black");
		angularHeightLabel.setLayoutX(xPositions[starsNum][1]);
		angularHeightLabel.setLayoutY(yPositions[starsNum][1]);

		pane.getChildren().add(angularHeightLabel);

		indexCorrections[starsNum] = new Degree(indexCorrection);
		Label indexCorrectionLabel = new Label(indexCorrection);
		indexCorrectionLabel.setFont(new Font("System", 21));
		indexCorrectionLabel.setStyle("-fx-border-color: black");
		indexCorrectionLabel.setLayoutX(xPositions[starsNum][1]);
		indexCorrectionLabel.setLayoutY(yPositions[starsNum][2]);

		pane.getChildren().add(indexCorrectionLabel);

		indexCorrectionOnValues[starsNum] = indexCorrectionOn;
		Label indexCorrectionOnLabel = new Label(indexCorrectionOn ? "On" : "Off");
		indexCorrectionOnLabel.setFont(new Font("System", 21));
		indexCorrectionOnLabel.setStyle("-fx-border-color: black");
		indexCorrectionOnLabel.setLayoutX(xPositions[starsNum][1]);
		indexCorrectionOnLabel.setLayoutY(yPositions[starsNum][3]);

		pane.getChildren().add(indexCorrectionOnLabel);

		// use vertical and horizontal lines

		if (starsNum == 0) {
			firstVertical = new Line(xPositions[starsNum][2], starLabel.getLayoutY()+40, xPositions[starsNum][2],
					indexCorrectionOnLabel.getLayoutY()+14);
			pane.getChildren().add(firstVertical);
		} else {
			Line mainVerticalLine = new Line(xPositions[starsNum][2], starLabel.getLayoutY()+40, xPositions[starsNum][2],
					indexCorrectionOnLabel.getLayoutY()+14);
			pane.getChildren().add(mainVerticalLine);

			if (starsNum == 1) {
				firstVertical.setEndY(starLabel.getLayoutY());
			}
		}

		Line smallHorizontalLine1 = new Line(xPositions[starsNum][2], angularHeightLabel.getLayoutY()+14,
				angularHeightLabel.getLayoutX(), angularHeightLabel.getLayoutY()+14);
		pane.getChildren().add(smallHorizontalLine1);

		Line smallHorizontalLine2 = new Line(xPositions[starsNum][2], indexCorrectionLabel.getLayoutY()+14,
				indexCorrectionLabel.getLayoutX(), indexCorrectionLabel.getLayoutY()+14);
		pane.getChildren().add(smallHorizontalLine2);

		Line smallHorizontalLine3 = new Line(xPositions[starsNum][2], indexCorrectionOnLabel.getLayoutY()+14,
				indexCorrectionOnLabel.getLayoutX(), indexCorrectionOnLabel.getLayoutY()+14);
		pane.getChildren().add(smallHorizontalLine3);

		// be sure to resize the smaller window popup
		if (starsNum != 0) {
			Button continueButton = getButton(yPositions);
			pane.getChildren().add(continueButton);
		}

		if (starsNum == 2) {
			pane.getChildren().remove(addStarButton);
		} else {
			addStarButton.setLayoutX(xPositions[starsNum][3]);
			addStarButton.setLayoutY(yPositions[starsNum][4]);
		}

		starsNum++;
	}

	private Button getButton(int[][] yPositions) {
		Button continueButton = new Button("Continue");
		continueButton.setFont(new Font("System", 18));
		continueButton.setStyle("-fx-background-radius: 11");
		continueButton.setPrefHeight(20);
		continueButton.setPrefWidth(120);

		continueButton.setOnAction(e -> {
			try {
				addDeterminedPosition();
			} catch (IOException ex) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setContentText(ex.getMessage());
				a.show();
			}
		});

		continueButton.setLayoutX(loadPlotButton.getLayoutX());
		continueButton.setLayoutY(yPositions[1][4]);
		return continueButton;
	}

	public void finalCalculation(Latitude latitude, Longitude longitude, double eyeHeight) throws IOException {
		AValue[] aValues = new AValue[3];
		Degree[] azimuths = new Degree[3];
		Latitude[] assumedLatitudes = new Latitude[3];
		Longitude[] assumedLongitudes = new Longitude[3];
		for (int i = 0; i < starsNum; i++) {
			Star star = new Star(stars[i]);
			DRPosition dr = new DRPosition(latitude, longitude);
			AssumedPosition ap = new AssumedPosition(dr, star);
			assumedLatitudes[i] = ap.getAssumedLatitude();
			assumedLongitudes[i] = ap.getAssumedLongitude();

			StarSight st = new StarSight(angularHeights[i], indexCorrections[i], indexCorrectionOnValues[i], eyeHeight);
			Degree Ho = st.getObservedHeight();
			double Hc = ap.getExpectedHeight();
			AValue a = new AValue(Hc, Ho);
			Degree az = ap.getAzimuth();

			aValues[i] = a;
			azimuths[i] = az;
		}

		switchToFinalDisplay(starsNum, aValues, azimuths, assumedLatitudes, assumedLongitudes);
	}
}