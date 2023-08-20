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
import javafx.scene.layout.Pane;
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
		stage.setWidth(660);
		stage.setHeight(500);
		stage.show();
	}

	@FXML
	public Plot loadPlot() throws IOException {
		FileChooser fileChooser = new FileChooser();
		File f = fileChooser.showOpenDialog(loadPlotButton.getScene().getWindow());
		return FileHandler.loadPlot(f.getAbsolutePath());
	}

	public void addStarDisplay(String star) {
		// use double int arr for each position, with all the magic numbers contained within
		Label starLabel = new Label(star);
		starLabel.setFont(new Font("System", 21));
		starLabel.setStyle("-fx-border-color: black");

		pane.getChildren().add(starLabel);

		// here I added labels for the individual bits, font size 17 for the smaller ones

		// use vertical and horizontal lines
		// use label.getHeight + label.getPosition or whatever for fine positioning

		// be sure to resize the smaller window popup
	}
}
