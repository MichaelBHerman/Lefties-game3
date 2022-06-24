package com.swing.panels;

import com.swing.ButtonFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MovementPanel {

    MovementPanel() {

    }

    public static JPanel create(Border border) {
        JPanel movementPanel = new JPanel(new GridLayout(4,1,5,5));
        movementPanel.add(ButtonFactory.createButton("North", border));
        movementPanel.add(ButtonFactory.createButton("East", border));
        movementPanel.add(ButtonFactory.createButton("South", border));
        movementPanel.add(ButtonFactory.createButton("West", border));
        movementPanel.setBackground(Color.black);
        movementPanel.setBorder(BorderFactory.createTitledBorder(border, "Movements", 0,2, null, Color.green));
        return movementPanel;
    }

}