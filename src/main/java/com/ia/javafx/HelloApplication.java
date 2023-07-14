package com.ia.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.*;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PolarAxisLocation;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.data.Range;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import java.awt.*;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;

public class HelloApplication extends Application {
    private static XYDataset createDataset(double[][][] data) {
        DefaultXYDataset dataset = new DefaultXYDataset();
        // it works like {y, x} where x is how far you are along clockwise, and y is how high up you are - only go to
        // 60!
        for (int i = 0; i < data.length; i++) {
            dataset.addSeries("Sight " + (i+1), data[i]);
        }
        return dataset;
    }

    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart c = ChartFactory.createPolarChart("Polar plot", dataset, true, true, false);
        PolarPlot p = (PolarPlot) c.getPlot();
        p.setAngleTickUnit(new NumberTickUnit(10));
//        p.setRadiusGridlinesVisible(false);
        p.setOutlineVisible(true);

        p.setAxisLocation(0, PolarAxisLocation.NORTH_RIGHT);
        ValueAxis a1 = p.getAxis(0);
        a1.setTickLabelsVisible(true);
        a1.setMinorTickMarksVisible(true);
        a1.setRange(new Range(0, 60));

        p.setAxis(1, new NumberAxis());
        p.setAxisLocation(1, PolarAxisLocation.SOUTH_RIGHT);
        ValueAxis a2 = p.getAxis(1);
        a2.setTickLabelsVisible(true);
        a2.setMinorTickMarksVisible(true);
        a2.setInverted(true);
        a2.setRange(new Range(0, 60));
        return c;
    }

    private static double[] findIntersection(XYDataset dataset) {
        double x1 = dataset.getXValue(0, 0);
        double y1 = dataset.getYValue(0, 0);
        double x2 = dataset.getXValue(0, 1);
        double y2 = dataset.getYValue(0, 1);
        Point2D p1 = new Point2D.Double(x1, y1);
        Point2D p2 = new Point2D.Double(x2, y2);
        Line2D l1 = new Line2D.Double(p1, p2);
        double m1 = (y2 - y1) / (x2 - x1);
        double c1 = y1 - m1 * x1;
        System.out.println("y = " + m1 + "x + " + c1);

        double x3 = dataset.getXValue(1, 0);
        double y3 = dataset.getYValue(1, 0);
        double x4 = dataset.getXValue(1, 1);
        double y4 = dataset.getYValue(1, 1);
	    p1 = new Point2D.Double(x3, y3);
        System.out.println(p1);
	    p2 = new Point2D.Double(x4, y4);
        System.out.println(p2);
	    Line2D l2 = new Line2D.Double(p1, p2);
        double m2 = (y4 - y3) / (x4 - x3);
        double c2 = y3 - m2 * x3;
        System.out.println("y = " + m2 + "x + " + c2);

	    if (l1.intersectsLine(l2)) {
	        return new double[]{(c2-c1) / (m1-m2), (m1 * (c2-c1) / (m1-m2)) + c1};
	    } else {
            return null;
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

// {x, x}, {y, y}
        double[][][] d = {{{207, 90}, {60, 5}}, {{306, 109}, {60, 60}}};
        JFreeChart chart = createChart(createDataset(d));
        ChartViewer viewer = new ChartViewer(chart);
        stage.setScene(new Scene(viewer));
        stage.setTitle("JFreeChart: polarplot");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();

        double[] coords = findIntersection(createDataset(d));
        if (coords != null) {
            System.out.println(coords[0] + " " + coords[1]);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}