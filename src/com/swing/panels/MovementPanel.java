package com.swing.panels;

import com.pending.game3.InputParser;
import com.swing.ButtonFactory;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

public class MovementPanel {

    MovementPanel() {

    }

    public static JPanel create(Border border) {
        JPanel movementPanel = new JPanel(new GridLayout(3,1,10,5));
        movementPanel.setPreferredSize(new Dimension(0,150));
        movementPanel.add(ButtonFactory.createButton("Clockwise", border));
        movementPanel.add(ButtonFactory.createButton("Counter-clockwise", border));
        movementPanel.add(ButtonFactory.createButton("Outward", border));
        movementPanel.add(ButtonFactory.createButton("Inward", border));
        movementPanel.add(ButtonFactory.createButton("Outward right", border));
        movementPanel.add(ButtonFactory.createButton("Outward left", border));
        movementPanel.setBackground(Color.black);
        movementPanel.setBorder(BorderFactory.createTitledBorder(border, "Movements", 0,2, null, Color.green));
        return movementPanel;
    }

    public static void setMovementPanel(String option) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(option.equalsIgnoreCase("clockwise")) {
            InputParser.getGUIInput("go clockwise");
        } else if (option.equalsIgnoreCase("counter-clockwise")) {
            InputParser.getGUIInput("go counter-clockwise");
        } else if (option.equalsIgnoreCase("inward")) {
            InputParser.getGUIInput("go inward");
        } else if (option.equalsIgnoreCase("outward")) {
            InputParser.getGUIInput("go outward");
        } else if (option.equalsIgnoreCase("outward right")) {
            InputParser.getGUIInput("go outward right");
        } else if (option.equalsIgnoreCase("outward left")) {
            InputParser.getGUIInput("go outward left");
        }

    }

}