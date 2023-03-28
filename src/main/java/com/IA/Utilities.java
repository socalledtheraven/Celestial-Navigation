package com.IA;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    public static double angleToNauticalMiles(double angles) {
        return angles*60;
    }

    public static void processAlmanac() {
        // use a try with resources
        try (PDDocument document = PDDocument.load(new File("src/main/resources/data/almanac.pdf"))) {
            PDFTextStripper stripper = new PDFTextStripper();
            for (int i = 18; i <= 258; i += 2) {
                stripper.setStartPage(i); // Start extracting text from the current page
                stripper.setEndPage(i); // Extract text only from the current page
                String pageText = stripper.getText(document); // Extracts the text from the current page
                // Process the extracted text from the current page as needed
                String extractedText = pageText.substring(pageText.indexOf("Stars") + 16);
                extractedText = extractedText.substring(0, extractedText.indexOf("pass")-20);
                System.out.println(extractedText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            String[] parts;
            try (Scanner scanner = new Scanner(extractedText)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains(star)) {
                        line.replace(star, "");
                        parts = line.split(" ");
                        break;
                    }
                }
            }

            return parts;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}