package com.ia.javafx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class StarAddController {
	@FXML
	private ChoiceBox<String> starDropdown;
	@FXML
	private RadioButton indexCorrOn;
	@FXML
	private RadioButton indexCorrOff;
	@FXML
	private Button continueButton;
	@FXML
	private TextField angularHeightDegrees;
	@FXML
	private TextField angularHeightMinutes;
	@FXML
	private TextField indexCorr;
	@FXML
	private Label errorLabel;
	private MainScreenController main;


	public void loadStars() {
		// list taken directly from the almanac, unaltered
		String[] stars = {"Alpheratz", "Ankaa", "Schedar", "Diphda", "Achernar", "Hamal", "Polaris", "Acamar", "Menkar", "Mirfak", "Aldebaran", "Rigel", "Capella", "Bellatrix", "Elnath", "Alnilam", "Betelgeuse", "Canopus", "Sirius", "Adhara", "Procyon", "Pollux", "Avior", "Suhail", "Miaplacidus", "Alphard", "Regulus", "Dubhe", "Denebola", "Gienah", "Acrux", "Gacrux", "Alioth", "Spica", "Alkaid", "Hadar", "Menkent", "Arcturus", "Rigil Kent.", "Kochab", "Zuben'ubi", "Alphecca", "Antares", "Atria", "Sabik", "Shaula", "Rasalhague", "Eltanin", "Kaus Aust.", "Vega", "Nunki", "Altair", "Peacock", "Deneb", "Enif", "Al Na’ir", "Fomalhaut", "Scheat", "Markab"};
		starDropdown.setItems(FXCollections.observableList(List.of(stars)));
	}

	public void getData() {
		String star = starDropdown.getValue();

		if (star == null) {
			errorLabel.setVisible(true);
			starDropdown.setStyle("-fx-border-color: red");
			return;
		}

		if (angularHeightDegrees.getText().isEmpty()) {
			errorLabel.setVisible(true);
			angularHeightDegrees.setStyle("-fx-border-color: red");
			return;
		} else if (!(angularHeightDegrees.getText().matches("^[0-9.]+$")) || angularHeightDegrees.getText().contains(
				" ")) {
			errorLabel.setVisible(true);
			angularHeightDegrees.setStyle("-fx-border-color: red");
			return;
		} else if (Double.parseDouble(angularHeightDegrees.getText()) > 360.0) {
			errorLabel.setVisible(true);
			angularHeightDegrees.setStyle("-fx-border-color: red");
			return;
		}

		if (angularHeightMinutes.getText().isEmpty()) {
			errorLabel.setVisible(true);
			angularHeightMinutes.setStyle("-fx-border-color: red");
			return;
		} else if (!(angularHeightMinutes.getText().matches("^[0-9.]+$")) || angularHeightMinutes.getText().contains(
				" ")) {
			errorLabel.setVisible(true);
			angularHeightMinutes.setStyle("-fx-border-color: red");
			return;
		} else if (Double.parseDouble(angularHeightMinutes.getText()) > 60.0) {
			errorLabel.setVisible(true);
			angularHeightMinutes.setStyle("-fx-border-color: red");
			return;
		}

		String angularHeight =
				angularHeightDegrees.getText() + "° " + angularHeightMinutes.getText() + "'";

		if (indexCorr.getText().isEmpty()) {
			errorLabel.setVisible(true);
			indexCorr.setStyle("-fx-border-color: red");
			return;
		} else if (!(indexCorr.getText().matches("^[0-9]+$")) || indexCorr.getText().contains(" ")) {
			errorLabel.setVisible(true);
			indexCorr.setStyle("-fx-border-color: red");
			return;
		}

		String indexCorrection = indexCorr.getText() + "°";


		if (!(indexCorrOn.isSelected()) && !(indexCorrOff.isSelected())) {
			errorLabel.setVisible(true);
			indexCorrOn.setStyle("-fx-border-color: red");
			return;
		}

		boolean indexCorrectionOn = indexCorrOn.isPressed();

		main.addStarDisplay(star, angularHeight, indexCorrection, indexCorrectionOn);
		Stage temp = (Stage) continueButton.getScene().getWindow();
		temp.close();
	}

	public void deErrorStar() {
		if (starDropdown.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			starDropdown.setStyle(null);
		}
	}

	public void deErrorDegrees() {
		if (angularHeightDegrees.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			angularHeightDegrees.setStyle(null);
		}
	}

	public void deErrorMinutes() {
		if (angularHeightMinutes.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			angularHeightMinutes.setStyle(null);
		}
	}

	public void deErrorIndexCorr() {
		if (indexCorr.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			indexCorr.setStyle(null);
		}
	}

	public void deErrorIndexCorrOn() {
		if (indexCorrOn.getStyle().equals("-fx-border-color: red")) {
			errorLabel.setVisible(false);
			indexCorrOn.setStyle(null);
		}
	}

	public void setMain(MainScreenController main) {
		this.main = main;
	}
}