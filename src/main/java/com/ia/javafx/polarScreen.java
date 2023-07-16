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

import java.io.IOException;

public class polarScreen extends Application {
    static polarController controller;
    private double LONRATIO;
    private double CIRCLERADIUS;

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

        FXMLLoader fxmlLoader = new FXMLLoader(polarScreen.class.getResource("plotScreen.fxml"));
        Parent root = fxmlLoader.load();
        controller = (polarController) fxmlLoader.getController();

		// declarations bunched up bc for loops are annoying
        CIRCLERADIUS = controller.compassRoseRadius();
	    double[] lonPoint = new double[3];
	    Point[] aLon = new Point[3];
	    Point[] azimuthPoint = new Point[3];
	    Line[] azimuthLine = new Line[3];
	    Point[] interceptPoint = new Point[3];
	    Point[] degreePoint = new Point[3];
	    Line[] lineOfPosition = new Line[3];

	    for (int i = 0; i < numOfStars; i++) {
		    LONRATIO = controller.longitudeScale(DRLatitudes[i]);
		    controller.setLabels(DRLatitudes[i]);
		    lonPoint[i] = controller.drawLongitudeLines(new Latitude((int) DRLatitudes[i]));

			// locates and draws the point of assumed longitude
		    aLon[i] = new Point(lonPoint[i]-((aLonDegrees[i]/60)*LONRATIO*CIRCLERADIUS),
				    new Degree(90));
		    azimuthPoint[i] = new Point(CIRCLERADIUS, new Degree(azimuths[i]));
		    azimuthLine[i] = controller.extendLine(new Line(azimuthPoint[i].getX(), azimuthPoint[i].getY(), aLon[i].getX(), aLon[i].getY()));

		    interceptPoint[i] = controller.getIntercept(azimuthLine[i], (aValues[i]/60)*LONRATIO*CIRCLERADIUS, aLon[i]);
		    degreePoint[i] = new Point(CIRCLERADIUS, new Degree(azimuths[i]-270));
		    lineOfPosition[i] = controller.extendLine(new Line(degreePoint[i].getX(), degreePoint[i].getY(), interceptPoint[i].getX(),
				    interceptPoint[i].getY()));
		    lineOfPosition[i].setStroke(Color.RED);
		    lineOfPosition[i].setStrokeWidth(2);
	    }

	    for (int i = 0; i < numOfStars; i++) {
		    controller.addLine(azimuthLine[i]);
		    controller.addLine(lineOfPosition[i]);
	    }

		Point intersectionPoint = MathematicalLine.getIntercept(new MathematicalLine(azimuthLine[0]),
			    new MathematicalLine(azimuthLine[1]),
				new MathematicalLine(azimuthLine[2]));

		double finalLon = (60*(intersectionPoint.getX() - 320))/LONRATIO/CIRCLERADIUS;
		double finalLat = (intersectionPoint.getY() - 240)*60/controller.compassRoseRadius();

	    System.out.println("You are at " + finalLat + ", " + finalLon);

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