package com.IA;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /** IMPORTANT: {lat, lon}, not {lon, lat} **/
        Scanner s = new Scanner(System.in);
//        System.out.println("Enter star name: ");
//        String name = s.nextLine();
//        System.out.println("Enter angular height: ");
//        double angularHeight = Double.parseDouble(s.nextLine());
//        System.out.println("Enter index correction: ");
//        double indexCorrection = Double.parseDouble(s.nextLine());
//        System.out.println("On or off? "); // validate
//        boolean ICon;
//        if (Objects.equals(s.nextLine(), "on")) {
//            ICon = true;
//        } else {
//            ICon = false;
//        }
//        System.out.println("Enter eye height (in feet): ");
//        double eyeHeight = Double.parseDouble(s.nextLine());
//        System.out.println("Enter best guess at previous latitude: ");
//        double DRlat = Double.parseDouble(s.nextLine());
//        System.out.println("Enter best guess at previous longitude: ");
//        double DRlon = Double.parseDouble(s.nextLine());

        String name = "Altair";
        double angularHeight = 35.189;
        double indexCorrection = 0.0;
        boolean ICon = true;
        double eyeHeight = 12.0;
        double DRlat = 45.30;
        double DRlon = 126.27;

        StarSight star1 = new StarSight(name, angularHeight, indexCorrection, ICon, eyeHeight);
        GeographicPosition GP = new GeographicPosition(name);
        AssumedPosition AP = new AssumedPosition(DRlat, DRlon, GP);
        AValue aValue = new AValue(AP.getHc(), star1.getObservedHeight());
        double azimuth = star1.calculateAzimuth(GP.getDeclination(), AP);

        if (aValue.getTowards()) {
            System.out.println("Your a-value is: " + aValue.getValue() + ", and it is towards.");
        } else {
            System.out.println("Your a-value is: " + aValue.getValue() + ", and it is away.");
        }
        System.out.println("Your calculateAzimuth is: " + azimuth + " degrees.");
        System.out.println("Your a-lat is: " + AP.getAlat() + " degrees.");
        System.out.println("Your a-lon is: " + AP.getAlon() + " degrees.");
    }
}
