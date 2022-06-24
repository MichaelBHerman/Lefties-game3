package com.swing.panels;

import com.swing.ButtonFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ActionPanel {

    ActionPanel() {

    }

    public static JPanel create(Border border) {
        JPanel actionPanel = new JPanel(new GridLayout(3,2,5,5));

        actionPanel.add(ButtonFactory.createButton("Pick Up", border));
        actionPanel.add(ButtonFactory.createButton("Talk", border));
        actionPanel.add(ButtonFactory.createButton("Craft", border));
        actionPanel.add(ButtonFactory.createButton("Drop", border));
        actionPanel.add(ButtonFactory.createButton("Use", border));
        actionPanel.add(ButtonFactory.createButton("Inspect", border));
        actionPanel.setBackground(Color.black);
        actionPanel.setBorder(BorderFactory.createTitledBorder(border, "Actions", 0,2, null, Color.green));

        return actionPanel;
    }

}