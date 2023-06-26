package com.IA;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
/*
        System.out.println(FileHandler.getDeclination("Polaris"));
        FileHandler.dateToPageNum();
*/
        JFrame frame = new JFrame("Test menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI myGUI = new GUI(600, 400);
        frame.add(myGUI);
        frame.pack();
        frame.setVisible(true);
    }
}