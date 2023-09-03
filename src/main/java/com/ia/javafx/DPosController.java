package com.ia.javafx;

import com.ia.Direction;
import com.ia.Latitude;
import com.ia.Longitude;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
	@FXML
	private TextField eyeHeightBox;
	@FXML
	private ChoiceBox<String> latitudeHemisphereBox;
	@FXML
	private ChoiceBox<String> longitudeHemisphereBox;
	private MainScreenController main;

	public void loadHemispheres() {
		latitudeHemisphereBox.setItems(FXCollections.observableList(List.of(new String[]{"East", "West"})));
		latitudeHemisphereBox.setItems(FXCollections.observableList(List.of(new String[]{"North", "South"})));
	}

	public void getData() throws IOException {
		Latitude latitude = new Latitude(Integer.parseInt(latitudeDegrees.getText()),
				Double.parseDouble(latitudeMinutes.getText()), Direction.valueOf(latitudeHemisphereBox.getValue().toUpperCase()));
		Longitude longitude = new Longitude(Integer.parseInt(longitudeDegrees.getText()),
				Double.parseDouble(longitudeMinutes.getText()), Direction.valueOf(longitudeHemisphereBox.getValue().toUpperCase()));
		double eyeHeight = Double.parseDouble(eyeHeightBox.getText());

		main.finalCalculation(latitude, longitude, eyeHeight);
		Stage temp = (Stage) continueButton.getScene().getWindow();
		temp.close();
	}

	public void setMain(MainScreenController main) {
		this.main = main;
	}
}