package com.socalledtheraven;
import

public class Utilities {
    public static double angleToNauticalMiles(double angles) {
        return angles*60;
    }

    public static void processAlmanac() {
        PDDocument document = PDDocument.load(new File("example.pdf"));

    }
}
