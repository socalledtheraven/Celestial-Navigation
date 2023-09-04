package com.ia.javafx;

import com.ia.Degree;
import com.ia.Latitude;
import com.ia.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PolarScreen extends Application {
    static PolarController controller;
    private double LONRATIO;
    private double CIRCLERADIUS;
	private static final Logger logger = LogManager.getLogger();

    @Override
    public void start(Stage stage) throws IOException {
		// main method

	    double numOfStars = Main.numberOfStars();
	    double[] aLonDegrees = new double[3];
	    double[] azimuths = new double[3];
	    double[] aValues = new double[3];
	    double[] DRLatitudes = new double[3];

	    for (int i = 0; i < numOfStars; i++) {
			double[] data = Main.collectData();
			aLonDegrees[i] = data[0];
			azimuths[i] = data[1];
			aValues[i] = data[2];
			DRLatitudes[i] = data[3];
	    }

        FXMLLoader fxmlLoader = new FXMLLoader(PolarScreen.class.getResource("plotScreen.fxml"));
        Parent root = fxmlLoader.load();
        controller = (PolarController) fxmlLoader.getController();

		// declarations bunched up bc for loops are annoying
	    // TODO: make window size configurable


		// renders the actual screen
	    stage.setScene(new Scene(root));
        stage.setTitle("JFreeChart: polarplot");
        stage.setWidth(655);
        stage.setHeight(510);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}