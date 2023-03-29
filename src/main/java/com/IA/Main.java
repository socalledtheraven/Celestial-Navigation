package com.IA;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // VERY IMPORTANT NOTE: {lat, lon}, not {lon, lat}
        Scanner s = new Scanner(System.in);
        System.out.println("Enter star name: ");
        String name = s.nextLine();
        System.out.println("Enter angular height: ");
        double angularHeight = s.nextDouble();
        System.out.println("Enter index correction: ");
        double indexCorrection = s.nextDouble();
        System.out.println("On or off? ");
        Boolean ICon = s.nextBoolean();
        System.out.println("Enter eye height (in feet): ");
        double eyeHeight = s.nextDouble();
        System.out.println("Enter best guess at previous latitude: ");
        double DRlat = s.nextDouble();
        System.out.println("Enter best guess at previous longitude: ");
        double DRlon = s.nextDouble();

        StarSight star1 = new StarSight(name, angularHeight, indexCorrection, ICon, eyeHeight);
        GeographicPosition GP = new GeographicPosition(name);
        AssumedPosition AP = new AssumedPosition(DRlat, DRlon, GP);
        AValue aValue = new AValue(AP.getHc(), star1.getObservedHeight());
        double azimuth = star1.azimuth(GP.getDeclination(), AP);

        if (aValue.getTowards()) {
            System.out.println("Your a-value is: " + aValue.getValue() + ", and it is towards.");
        } else {
            System.out.println("Your a-value is: " + aValue.getValue() + ", and it is away.");
        }
        System.out.println("Your zenith is: " + azimuth + " degrees.");
        System.out.println("Your a-lat is: " + AP.getAlat() + " degrees.");
        System.out.println("Your a-lon is: " + AP.getAlon() + " degrees.");
    }
}
