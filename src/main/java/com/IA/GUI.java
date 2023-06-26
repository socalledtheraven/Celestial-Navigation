package com.IA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JPanel implements ActionListener {
    JComboBox<String> comboBox;

    public GUI(int width, int height) {
        System.out.println("gui constructor go");
        this.setPreferredSize(new Dimension(width, height));
        setLayout(null);
        String[] stars = FileHandler.getStars();
        comboBox = new JComboBox<>();
        comboBox.setVisible(true);
        comboBox.setBounds(0, 0, 100, 40);
        String NOT_SELECTABLE_OPTION = "Star: ";
        comboBox.setModel(new DefaultComboBoxModel<String>() {
            private static final long serialVersionUID = 1L;
            boolean selectionAllowed = true;

            @Override
            public void setSelectedItem(Object anObject) {
                if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
                    super.setSelectedItem(anObject);
                } else if (selectionAllowed) {
                    // Allow this just once
                    selectionAllowed = false;
                    super.setSelectedItem(anObject);
                }
            }
        });
        comboBox.addItem(NOT_SELECTABLE_OPTION);
        for (String star : stars) {
            comboBox.addItem(star);
        }
        add(comboBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("View graph")) {
//            Graph myGraph = new Graph(300, 300);
//        } else {
//            System.out.println("othjer");
//        }
    }
}
