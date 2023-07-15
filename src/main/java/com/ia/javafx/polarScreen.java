package com.ia.javafx;

import com.ia.Degree;
import com.ia.Latitude;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

public class polarScreen extends Application {
    static polarController controller;
    private double LONRATIO;
    private double CIRCLERADIUS;

    @Override
    public void start(Stage stage) throws IOException {
        double aLonDeg = 57.5;
        double azimuth = 207;
        double DRLatitude = 27;
        double aValue = 10;
        FXMLLoader fxmlLoader = new FXMLLoader(polarScreen.class.getResource("plotScreen.fxml"));
        Parent root = fxmlLoader.load();

        controller = (polarController) fxmlLoader.getController();
        LONRATIO = controller.longitudeScale(DRLatitude);
        CIRCLERADIUS = controller.compassRoseRadius();

        controller.setLabels(DRLatitude);
        double lonPoint = controller.drawLongitudeLines(new Latitude((int) DRLatitude));

        Coordinate aLon = new Coordinate((lonPoint-320)-((aLonDeg/60)*LONRATIO*CIRCLERADIUS), new Degree(90));
        Coordinate azimuthPoint = new Coordinate(CIRCLERADIUS, new Degree(azimuth));
        Line azimuthLine = controller.extendLine(new Line(azimuthPoint.getX(), azimuthPoint.getY(), aLon.getX(), aLon.getY()));
        controller.addLine(azimuthLine);
        Coordinate interceptPoint = controller.getIntercept(azimuthLine, (aValue/60)*LONRATIO*CIRCLERADIUS, aLon);
        controller.plotLine(interceptPoint);
        Coordinate degreePoint = new Coordinate(CIRCLERADIUS, new Degree(azimuth-270));
        Line lineOfPosition = new Line(degreePoint.getX(), degreePoint.getY(), interceptPoint.getX(), interceptPoint.getY());
//        controller.addLine(lineOfPosition);

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