package com.ia.javafx;

import com.ia.FileHandler;
import com.ia.Plot;
import com.ia.StarSight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

	@FXML
	public void addStar() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("starSelectScreen.fxml"));
		Parent root = fxmlLoader.load();
		StarAddController controller = (StarAddController) fxmlLoader.getController();
		controller.loadStars();
		controller.setMain(this);

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("IA ");
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

	public void addStarDisplay(String star, String angularHeight, String indexCorrection, boolean indexCorrectionOn,
	                           int numStars) {
		// use double int arr for each position, with all the magic numbers contained within
		int[][] xPositions = {{20, 40, 30, 14}, {20, 40, 30, 14}, {200, 220, 210, -1}};
		int[][] yPositions = {{20, 70, 110, 150, 200}, {200, 250, 290, 330, 380}, {100, 150, 190, 230, -1}};

		for (int i = 0; i < numStars; i++) {
			Label starLabel = new Label(star);
			starLabel.setFont(new Font("System", 26));
			starLabel.setStyle("-fx-border-color: black");
			starLabel.setLayoutX(xPositions[i][0]);
			starLabel.setLayoutY(yPositions[i][0]);

			pane.getChildren().add(starLabel);

			Label angularHeightLabel = new Label(angularHeight);
			angularHeightLabel.setFont(new Font("System", 21));
			angularHeightLabel.setStyle("-fx-border-color: black");
			angularHeightLabel.setLayoutX(xPositions[i][1]);
			angularHeightLabel.setLayoutY(yPositions[i][1]);

			pane.getChildren().add(angularHeightLabel);

			Label indexCorrectionLabel = new Label(indexCorrection);
			indexCorrectionLabel.setFont(new Font("System", 21));
			indexCorrectionLabel.setStyle("-fx-border-color: black");
			indexCorrectionLabel.setLayoutX(xPositions[i][1]);
			indexCorrectionLabel.setLayoutY(yPositions[i][2]);

			pane.getChildren().add(indexCorrectionLabel);

			Label indexCorrectionOnLabel = new Label(indexCorrectionOn ? "On" : "Off");
			indexCorrectionOnLabel.setFont(new Font("System", 21));
			indexCorrectionOnLabel.setStyle("-fx-border-color: black");
			indexCorrectionOnLabel.setLayoutX(xPositions[i][1]);
			indexCorrectionOnLabel.setLayoutY(yPositions[i][3]);

			pane.getChildren().add(indexCorrectionOnLabel);

			// use vertical and horizontal lines

			Line mainVerticalLine = new Line(xPositions[i][2], starLabel.getLayoutY()+40, xPositions[i][2],
					indexCorrectionOnLabel.getLayoutY()+14);
			pane.getChildren().add(mainVerticalLine);

			Line smallHorizontalLine1 = new Line(xPositions[i][2], angularHeightLabel.getLayoutY()+14,
					angularHeightLabel.getLayoutX(), angularHeightLabel.getLayoutY()+14);
			pane.getChildren().add(smallHorizontalLine1);

			Line smallHorizontalLine2 = new Line(xPositions[i][2], indexCorrectionLabel.getLayoutY()+14,
					indexCorrectionLabel.getLayoutX(), indexCorrectionLabel.getLayoutY()+14);
			pane.getChildren().add(smallHorizontalLine2);

			Line smallHorizontalLine3 = new Line(xPositions[i][2], indexCorrectionOnLabel.getLayoutY()+14,
					indexCorrectionOnLabel.getLayoutX(), indexCorrectionOnLabel.getLayoutY()+14);
			pane.getChildren().add(smallHorizontalLine3);

			// be sure to resize the smaller window popup

			if (i == 2) {
				pane.getChildren().remove(addStarButton);
			} else {
				addStarButton.setLayoutX(xPositions[i][3]);
				addStarButton.setLayoutY(yPositions[i][4]);
			}
		}
	}
}