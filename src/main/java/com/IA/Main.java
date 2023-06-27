package com.IA;

import javax.swing.*;
import com.IA.Swing.*;

public class Main {
    public static void main(String[] args) {
/*
        System.out.println(FileHandler.getDeclination("Polaris"));
        FileHandler.dateToPageNum();
*/
        JFrame frame = new JFrame("Test menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AddStarPage myGUI = new AddStarPage(600, 400);
        frame.add(myGUI);
        frame.pack();
        frame.setVisible(true);
    }
}