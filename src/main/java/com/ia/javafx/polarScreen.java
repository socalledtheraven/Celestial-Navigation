package com.ia.javafx;

import com.ia.Degree;
import com.ia.Latitude;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

public class polarScreen extends Application {
    static polarController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(polarScreen.class.getResource("plotScreen.fxml"));
        Parent root = fxmlLoader.load();

        controller = (polarController) fxmlLoader.getController();
        controller.setLabels(27);
        controller.plotLine(50, 275);
        controller.drawLongitudeLines(new Latitude(27));

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