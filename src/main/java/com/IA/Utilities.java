package com.IA;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.Scanner;

public class Utilities {
    public static double angleToNauticalMiles(double angles) {
        return angles*60;
    }

    public static double strAngleToDegrees(String strAngle) {
        return Double.parseDouble(strAngle.substring(0, strAngle.indexOf(" ")));
    }

    public static String[] processAlmanac(int page, String star) {
        try (PDDocument document = PDDocument.load(new File("src/main/resources/data/almanac.pdf"))) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(page); // Start extracting text from the current page
            stripper.setEndPage(page); // Extract text only from the current page
            String pageText = stripper.getText(document); // Extracts the text from the current page
            // Process the extracted text from the current page as needed
            String extractedText = pageText.substring(pageText.indexOf("Stars") + 16);
            extractedText = extractedText.substring(0, extractedText.indexOf("pass")-20);

            String[] parts = new String[0];
            try (Scanner scanner = new Scanner(extractedText)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains(star)) {
                        parts = line.replace(star, "").split(" ");
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return parts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}
