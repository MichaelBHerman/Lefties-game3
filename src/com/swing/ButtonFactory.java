package com.swing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ButtonFactory {

    ButtonFactory() {

    }

    public static JButton createButton(String btnName, Border border) {
        JButton button = new JButton(btnName);

        // Setting up the button color/border
        button.setFocusPainted( false );
        button.setBackground(Color.green);
        button.setForeground(Color.black);
        button.setBorder(border);

        // Adding event listeners to button
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.black);
                button.setForeground(Color.green);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.green);
                button.setForeground(Color.black);
            }
        });

        return button;
    }

}