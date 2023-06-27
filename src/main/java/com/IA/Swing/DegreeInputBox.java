package com.IA.Swing;

import javax.swing.JTextField;
import java.awt.Graphics;

public class DegreeInputBox extends JTextField {
    private static final String DEGREE_SYMBOL = "\u00B0"; // Unicode character for the degree sign

    public DegreeInputBox() {
        super(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the text entered in the input box
        String text = getText();

        // Calculate the x-coordinate where the degree sign should be rendered
        int degreeX = getWidth() - 2; // Add some padding

        // Render the degree sign at the calculated position
        g.drawString(DEGREE_SYMBOL, degreeX, getHeight() - 2);
    }
}