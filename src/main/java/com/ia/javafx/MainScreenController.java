package com.ia.javafx;

import com.ia.Degree;
import com.ia.FileHandler;
import com.ia.Plot;
import com.ia.StarSight;
import javafx.event.ActionEvent;
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
	private int stars = 0;
	private Line firstVertical;

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
		controller.setMain(this);

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Determined Position Input");
		stage.setWidth(530);
		stage.setHeight(430);
		stage.getIcons().add(new Image("file:src/main/resources/com/ia/javafx/images/icon.png"));
		stage.show();
	}

	@FXML
	public Plot loadPlot() throws IOException {
		FileChooser fileChooser = new FileChooser();
		File f = fileChooser.showOpenDialog(loadPlotButton.getScene().getWindow());
		return FileHandler.loadPlot(f.getAbsolutePath());
	}

	public void addStarDisplay(String star, String angularHeight, String indexCorrection, boolean indexCorrectionOn) {
		// use double int arr for each position, with all the magic numbers contained within
		int[][] xPositions = {{20, 40, 30, 14}, {20, 40, 30, 14}, {200, 220, 210, -1}};
		int[][] yPositions = {{20, 70, 110, 150, 200}, {200, 250, 290, 330, 380}, {100, 150, 190, 230, -1}};

		Label starLabel = new Label(star);
		starLabel.setFont(new Font("System", 26));
		starLabel.setStyle("-fx-border-color: black");
		starLabel.setLayoutX(xPositions[stars][0]);
		starLabel.setLayoutY(yPositions[stars][0]);

		pane.getChildren().add(starLabel);

		Label angularHeightLabel = new Label(angularHeight);
		angularHeightLabel.setFont(new Font("System", 21));
		angularHeightLabel.setStyle("-fx-border-color: black");
		angularHeightLabel.setLayoutX(xPositions[stars][1]);
		angularHeightLabel.setLayoutY(yPositions[stars][1]);

		pane.getChildren().add(angularHeightLabel);

		Label indexCorrectionLabel = new Label(indexCorrection);
		indexCorrectionLabel.setFont(new Font("System", 21));
		indexCorrectionLabel.setStyle("-fx-border-color: black");
		indexCorrectionLabel.setLayoutX(xPositions[stars][1]);
		indexCorrectionLabel.setLayoutY(yPositions[stars][2]);

		pane.getChildren().add(indexCorrectionLabel);

		Label indexCorrectionOnLabel = new Label(indexCorrectionOn ? "On" : "Off");
		indexCorrectionOnLabel.setFont(new Font("System", 21));
		indexCorrectionOnLabel.setStyle("-fx-border-color: black");
		indexCorrectionOnLabel.setLayoutX(xPositions[stars][1]);
		indexCorrectionOnLabel.setLayoutY(yPositions[stars][3]);

		pane.getChildren().add(indexCorrectionOnLabel);

		// use vertical and horizontal lines

		if (stars == 0) {
			firstVertical = new Line(xPositions[stars][2], starLabel.getLayoutY()+40, xPositions[stars][2],
					indexCorrectionOnLabel.getLayoutY()+14);
			pane.getChildren().add(firstVertical);
		} else {
			Line mainVerticalLine = new Line(xPositions[stars][2], starLabel.getLayoutY()+40, xPositions[stars][2],
					indexCorrectionOnLabel.getLayoutY()+14);
			pane.getChildren().add(mainVerticalLine);

			if (stars == 1) {
				firstVertical.setEndY(starLabel.getLayoutY());
			}
		}

		Line smallHorizontalLine1 = new Line(xPositions[stars][2], angularHeightLabel.getLayoutY()+14,
				angularHeightLabel.getLayoutX(), angularHeightLabel.getLayoutY()+14);
		pane.getChildren().add(smallHorizontalLine1);

		Line smallHorizontalLine2 = new Line(xPositions[stars][2], indexCorrectionLabel.getLayoutY()+14,
				indexCorrectionLabel.getLayoutX(), indexCorrectionLabel.getLayoutY()+14);
		pane.getChildren().add(smallHorizontalLine2);

		Line smallHorizontalLine3 = new Line(xPositions[stars][2], indexCorrectionOnLabel.getLayoutY()+14,
				indexCorrectionOnLabel.getLayoutX(), indexCorrectionOnLabel.getLayoutY()+14);
		pane.getChildren().add(smallHorizontalLine3);

		// be sure to resize the smaller window popup
		if (stars != 0) {
			Button continueButton = getButton(yPositions);
			pane.getChildren().add(continueButton);
		}

		if (stars == 2) {
			pane.getChildren().remove(addStarButton);
		} else {
			addStarButton.setLayoutX(xPositions[stars][3]);
			addStarButton.setLayoutY(yPositions[stars][4]);
		}

		stars++;
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


	public void finalCalculation(Degree latitude, Degree longitude) {
		// .
	}
}