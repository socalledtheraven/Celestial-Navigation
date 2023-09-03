package com.ia.javafx;

import com.ia.AValue;
import com.ia.Degree;
import com.ia.Latitude;
import com.ia.Longitude;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class FinalFixDisplayController {
	@FXML
	private Pane pane;
	@FXML
	private Button saveFixButton;
	@FXML
	private Button loadFixButton;
	@FXML
	private Button plotButton;


	public void addStarDisplay(int numStars, String[] stars, AValue[] aValues, Degree[] azimuths,
	                           Latitude[] assumedLatitudes, Longitude[] assumedLongitudes) {
		// use double int arr for each position, with all the magic numbers contained within
		int[][] xPositions;
		int[][] yPositions;

		if (numStars == 2) {
			xPositions = new int[][]{{40, 60, 50}, {290, 310, 300}};
			yPositions = new int[][]{{120, 170, 210, 250, 290}, {120, 170, 210, 250, 290}};
		} else {
			xPositions = new int[][]{{40, 60, 50}, {290, 310, 300}, {70, 160, 150, 136}};
			yPositions = new int[][]{{20, 70, 110, 150, 190}, {20, 70, 110, 150, 190}, {297, 240, 280, 320, 360}};
		}

		for (int i = 0; i < numStars; i++) {
			Label starLabel = new Label(stars[i]);
			starLabel.setFont(new Font("System", 22));
			starLabel.setStyle("-fx-border-color: black");
			starLabel.setLayoutX(xPositions[i][0]);
			starLabel.setLayoutY(yPositions[i][0]);

			pane.getChildren().add(starLabel);

			Label aValueLabel = new Label(aValues[i].toString());
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

			// use vertical and horizontal lines

			Line mainVerticalLine;
			if (i == 2) {
				mainVerticalLine = new Line(xPositions[i][2], aValueLabel.getLayoutY()+14, xPositions[i][2],
						assumedLongitudeLabel.getLayoutY()+14);
			} else {
				mainVerticalLine = new Line(xPositions[i][2], starLabel.getLayoutY()+34, xPositions[i][2],
						assumedLongitudeLabel.getLayoutY()+14);
			}
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
				saveFixButton.setLayoutX(plotButton.getLayoutX());
				loadFixButton.setLayoutX(plotButton.getLayoutX());

				saveFixButton.setLayoutY(plotButton.getLayoutY()-100);
				loadFixButton.setLayoutY(plotButton.getLayoutY()-50);

				Line smallHorizontalLine5 = new Line(xPositions[i][3], starLabel.getLayoutY()+17,
						xPositions[i][2], starLabel.getLayoutY()+17);
				pane.getChildren().add(smallHorizontalLine5);
			}
		}
	}
}