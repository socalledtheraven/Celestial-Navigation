package com.IA;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
        StarSight star1 = new StarSight(name, angularHeight, indexCorrection, ICon, eyeHeight);
        System.out.println("Enter best guess at previous latitude: ");
        double DRlat = s.nextDouble();
        System.out.println("Enter best guess at previous longitude: ");
        double DRlon = s.nextDouble();
        GeographicPosition GP = new GeographicPosition(name);
        AssumedPosition AP = new AssumedPosition(DRlat, DRlon, GP);

    }
}
