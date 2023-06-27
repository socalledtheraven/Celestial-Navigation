package com.IA.Swing;

import javax.swing.*;
import java.awt.*;

public class MinuteInputBox extends JTextField {
    private static final String DEGREE_SYMBOL = "\"";

    public MinuteInputBox(int columns) {
        super(columns);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the text entered in the input box
        String text = getText();

        // Calculate the x-coordinate where the degree sign should be rendered
        int degreeX = getFontMetrics(getFont()).stringWidth(text) + 2; // Add some padding

        // Render the degree sign at the calculated position
        g.drawString(DEGREE_SYMBOL, degreeX, getHeight() - 2);
    }
}