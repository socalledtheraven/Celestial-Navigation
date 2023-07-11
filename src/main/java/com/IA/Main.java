package com.IA;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /* Hs = angular height
        * */

        Scanner s = new Scanner(System.in);
        System.out.println("Enter star name: ");
        String starName = s.nextLine();
        Star star = new Star(starName);
        System.out.println("Enter dead reckoning latitude (format x° y.z' N): ");
        Latitude drlat = new Latitude(s.nextLine());
        System.out.println("Enter dead reckoning longitude (format x° y.z' W): ");
        Longitude drlon = new Longitude(s.nextLine());
        DRPosition dr = new DRPosition(drlat, drlon);
        AssumedPosition ap = new AssumedPosition(dr, star);
        System.out.println("Enter angular height: ");
        Degree angHeight = new Degree(s.nextLine());
        System.out.println("Enter index correction: ");
        Degree ic = new Degree(s.nextLine());
        System.out.println("is it on? ");
        boolean icon = s.nextBoolean();
        System.out.println("Enter eye height: ");
        double eyeheight = s.nextDouble();
        System.out.println("What hemisphere are you in? ");
        Direction hemisphere = Direction.valueOf(s.nextLine());
        StarSight st = new StarSight(angHeight, ic, icon, eyeheight, hemisphere);
        Degree Ho = st.getObservedHeight();
        double Hc = ap.getExpectedHeight();
        AValue a = new AValue(Hc, Ho);
        Degree az = ap.getAzimuth();
        System.out.println("Azimuth: " + az);
        Plot p = new Plot(ap.getAssumedLatitude(), ap.getAssumedLongitude(), a, az);
        System.out.println(p);
    }
}