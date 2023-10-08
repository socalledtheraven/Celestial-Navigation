package com.ia.javafx;

import com.ia.Direction;
import com.ia.Latitude;
import com.ia.Longitude;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
	@FXML
	private Label errorLabel;
	private MainScreenController main;

	public void loadHemispheres() {
		longitudeHemisphereBox.setItems(FXCollections.observableList(List.of(new String[]{"East", "West"})));
		latitudeHemisphereBox.setItems(FXCollections.observableList(List.of(new String[]{"North", "South"})));
		latitudeDegrees.requestFocus();
	}

	public void getData() throws IOException {
		if (latitudeDegrees.getText().isEmpty()) {
			errorLabel.setVisible(true);
			latitudeDegrees.setStyle("-fx-border-color: red");
			return;
		} else if (!(latitudeDegrees.getText().matches("^[0-9]+$")) || latitudeDegrees.getText().contains(" ")) {
			errorLabel.setVisible(true);
			latitudeDegrees.setStyle("-fx-border-color: red");
			return;
		} else if (Integer.parseInt(latitudeDegrees.getText()) > 360) {
			errorLabel.setVisible(true);
			latitudeDegrees.setStyle("-fx-border-color: red");
			return;
		}

		if (latitudeMinutes.getText().isEmpty()) {
			errorLabel.setVisible(true);
			latitudeMinutes.setStyle("-fx-border-color: red");
			return;
		} else if (!(latitudeMinutes.getText().matches("^[0-9.]+$")) || latitudeMinutes.getText().contains(" ")) {
			errorLabel.setVisible(true);
			latitudeMinutes.setStyle("-fx-border-color: red");
			return;
		} else if (Integer.parseInt(latitudeMinutes.getText()) > 60) {
			errorLabel.setVisible(true);
			latitudeMinutes.setStyle("-fx-border-color: red");
			return;
		}

		if (latitudeHemisphereBox.getValue() == null) {
			errorLabel.setVisible(true);
			latitudeHemisphereBox.setStyle("-fx-border-color: red");
			return;
		}

		Latitude latitude = new Latitude(Integer.parseInt(latitudeDegrees.getText()),
				Double.parseDouble(latitudeMinutes.getText()), Direction.valueOf(latitudeHemisphereBox.getValue().toUpperCase()));

		if (longitudeDegrees.getText().isEmpty()) {
			errorLabel.setVisible(true);
			longitudeDegrees.setStyle("-fx-border-color: red");
			return;
		} else if (!(longitudeDegrees.getText().matches("^[0-9]+$")) || longitudeDegrees.getText().contains(" ")) {
			errorLabel.setVisible(true);
			longitudeDegrees.setStyle("-fx-border-color: red");
			return;
		} else if (Integer.parseInt(longitudeDegrees.getText()) > 360) {
			errorLabel.setVisible(true);
			longitudeDegrees.setStyle("-fx-border-color: red");
			return;
		}

		if (longitudeMinutes.getText().isEmpty()) {
			errorLabel.setVisible(true);
			longitudeMinutes.setStyle("-fx-border-color: red");
			return;
		} else if (!(longitudeMinutes.getText().matches("^[0-9.]+$")) || longitudeMinutes.getText().contains(" ")) {
			errorLabel.setVisible(true);
			longitudeMinutes.setStyle("-fx-border-color: red");
			return;
		} else if (Integer.parseInt(longitudeMinutes.getText()) > 60) {
			errorLabel.setVisible(true);
			longitudeMinutes.setStyle("-fx-border-color: red");
			return;
		}

		if (longitudeHemisphereBox.getValue() == null) {
			errorLabel.setVisible(true);
			longitudeHemisphereBox.setStyle("-fx-border-color: red");
			return;
		}

		Longitude longitude = new Longitude(Integer.parseInt(longitudeDegrees.getText()),
				Double.parseDouble(longitudeMinutes.getText()), Direction.valueOf(longitudeHemisphereBox.getValue().toUpperCase()));

		if (eyeHeightBox.getText().isEmpty()) {
			errorLabel.setVisible(true);
			eyeHeightBox.setStyle("-fx-border-color: red");
			return;
		}

		double eyeHeight = Double.parseDouble(eyeHeightBox.getText());

		main.finalCalculation(latitude, longitude, eyeHeight);
		Stage temp = (Stage) continueButton.getScene().getWindow();
		temp.close();
	}

	public void deErrorLatitudeDegrees() {
		if (latitudeDegrees.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			latitudeDegrees.setStyle(null);
		}
	}

	public void deErrorLatitudeMinutes() {
		if (latitudeMinutes.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			latitudeMinutes.setStyle(null);
		}
	}

	public void deErrorLatitudeHemisphere() {
		if (latitudeHemisphereBox.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			latitudeHemisphereBox.setStyle(null);
		}
	}

	public void deErrorLongitudeDegrees() {
		if (longitudeDegrees.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			longitudeDegrees.setStyle(null);
		}
	}

	public void deErrorLongitudeMinutes() {
		if (longitudeMinutes.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			longitudeMinutes.setStyle(null);
		}
	}

	public void deErrorLongitudeHemisphere() {
		if (longitudeHemisphereBox.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			longitudeHemisphereBox.setStyle(null);
		}
	}

	public void deErrorEyeHeight() {
		if (eyeHeightBox.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			eyeHeightBox.setStyle(null);
		}
	}

	public void setMain(MainScreenController main) {
		this.main = main;
	}
}