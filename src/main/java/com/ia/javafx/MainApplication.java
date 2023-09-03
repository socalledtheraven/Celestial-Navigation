package com.ia.javafx;

import com.ia.AValue;
import com.ia.Degree;
import com.ia.Latitude;
import com.ia.Longitude;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
		// main method
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainScreen.fxml"));
        Parent root = fxmlLoader.load();
        MainScreenController controller = (MainScreenController) fxmlLoader.getController();

		// renders the actual screen
	    stage.setScene(new Scene(root));
        stage.setTitle("IA");
        stage.setWidth(550);
        stage.setHeight(470);
        stage.getIcons().add(new Image("file:src/main/resources/com/ia/javafx/images/icon.png"));
        stage.show();

        double hc = 12;
        Degree ho = new Degree(10);

        AValue[] aValues = {new AValue(hc, ho), new AValue(hc, ho), new AValue(hc, ho)};
        Degree[] azimuths = {new Degree(5), new Degree(5), new Degree(5)};
        Latitude[] latitudes = {new Latitude(3), new Latitude(3), new Latitude(3)};
        Longitude[] longitudes = {new Longitude(6, 0), new Longitude(4, 0), new Longitude(4, 0)};
        controller.switchToFinalDisplay(3, aValues, azimuths, latitudes, longitudes);
    }

    public static void main(String[] args) {
        launch();
    }
}