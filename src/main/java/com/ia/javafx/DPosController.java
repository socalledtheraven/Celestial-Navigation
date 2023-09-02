package com.ia.javafx;

import com.ia.Degree;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class DPosController {
	@FXML
	private Button continueButton;
	@FXML
	private TextField latitudeDegrees;
	@FXML
	private TextField latitudeMinutes;
	@FXML
	private TextField longitudeDegrees;
	@FXML
	private TextField longitudeMinutes;
	private MainScreenController main;

	public void getData() {
		Degree latitude = new Degree(Integer.parseInt(latitudeDegrees.getText()),
				Double.parseDouble(latitudeMinutes.getText()));
		Degree longitude = new Degree(Integer.parseInt(longitudeDegrees.getText()),
				Double.parseDouble(longitudeMinutes.getText()));

		main.finalCalculation(latitude, longitude);
		Stage temp = (Stage) continueButton.getScene().getWindow();
		temp.close();
	}

	public void setMain(MainScreenController main) {
		this.main = main;
	}
}