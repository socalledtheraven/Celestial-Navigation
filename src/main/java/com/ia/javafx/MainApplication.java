package com.ia.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        stage.setTitle("IA ");
        stage.setWidth(660);
        stage.setHeight(500);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}