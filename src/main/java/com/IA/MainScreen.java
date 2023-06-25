package com.IA;

import javax.swing.*;

public class MainScreen {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test menu");
        frame.setSize(500, 600); // 400 width and 500 height
        frame.setLayout(null); // using no layout managers
        frame.setVisible(true); // making the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        JLabel lbl = new JLabel("Select one of the possible choices and click OK");
        lbl.setVisible(true);
        panel.add(lbl);

        String[] stars = FileHandler.getStars();
        JComboBox<String> comboBox = new JComboBox<String>(stars);
        comboBox.setVisible(true);
        panel.add(comboBox);
    }
}