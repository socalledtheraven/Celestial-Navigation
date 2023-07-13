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
import java.io.IOException;

public class HelloApplication extends Application {
    private static XYDataset createDataset(double[][][] data) {
        DefaultXYDataset dataset = new DefaultXYDataset();
        // it works like {y, x} where x is how far you are along clockwise, and y is how high up you are - only go to
        // 60!
        for (int i = 0; i < data.length; i++) {
            dataset.addSeries("sight" + (i+1), data[i]);
        }
        return dataset;
    }

    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart c = ChartFactory.createPolarChart("Polar plot", dataset, true, true, false);
        PolarPlot p = (PolarPlot) c.getPlot();
        p.setAngleTickUnit(new NumberTickUnit(10));
        p.setRadiusGridlinesVisible(false);
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
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            for (int j = 0; j < dataset.getItemCount(i); j++) {
                Line2D l1 = new Line2D.Double(dataset.getXValue(j, i)., dataset.getYValue(j, i));

            }
            
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));


        double[][][] d = {{{2, 5}, {60, 31}}, {{56, 23}, {43, 47}}};
        JFreeChart chart = createChart(createDataset(d));
        ChartViewer viewer = new ChartViewer(chart);
        stage.setScene(new Scene(viewer));
        stage.setTitle("JFreeChart: polarplot");
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}