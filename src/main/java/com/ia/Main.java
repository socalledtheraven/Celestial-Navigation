package com.ia;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // placeholder
    }

    public static double[] collectData() {
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

        StarSight st = new StarSight(angHeight, ic, icon, eyeheight);
        Degree Ho = st.getObservedHeight();
        double Hc = ap.getExpectedHeight();
        AValue a = new AValue(Hc, Ho);
        Degree az = ap.getAzimuth();

        return new double[] {ap.getAssumedLongitude().getDegrees(), az.toDouble(), a.getValue().toDouble(), drlat.toDouble()};
    }

	public static int numberOfStars() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter number of stars sighted: ");
		return s.nextInt();
	}
}