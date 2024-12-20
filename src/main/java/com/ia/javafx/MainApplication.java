package com.ia.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
		// main method
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:javafx/mainScreen.fxml"));
        Parent root = fxmlLoader.load();

	    stage.setScene(new Scene(root));
        stage.setTitle("IA");
        stage.setWidth(550);
        stage.setHeight(470);
        stage.getIcons().add(new Image("file:javafx/images/icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}