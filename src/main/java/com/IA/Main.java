package com.IA;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter star name: ");
        String name = s.nextLine();
        System.out.println("Enter angular height: ");
        double angularHeight = s.nextDouble();
        StarSight star1 = new StarSight(name, angularHeight);


    }
}
