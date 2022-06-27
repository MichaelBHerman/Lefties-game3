package com.swing.panels;

import com.swing.ButtonFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MovementPanel {

    MovementPanel() {

    }

    public static JPanel create(Border border) {
        JPanel movementPanel = new JPanel(new GridLayout(3,1,10,5));
        movementPanel.setPreferredSize(new Dimension(0,150));
        movementPanel.add(ButtonFactory.createButton("clockwise", border));
        movementPanel.add(ButtonFactory.createButton("counter-clock", border));
        movementPanel.add(ButtonFactory.createButton("outward", border));
        movementPanel.add(ButtonFactory.createButton("inward", border));
        movementPanel.add(ButtonFactory.createButton("outward-right", border));
        movementPanel.add(ButtonFactory.createButton("outward-left", border));
        movementPanel.setBackground(Color.black);
        movementPanel.setBorder(BorderFactory.createTitledBorder(border, "Movements", 0,2, null, Color.green));
        return movementPanel;
    }

}