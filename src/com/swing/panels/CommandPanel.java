package com.swing.panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CommandPanel {

    CommandPanel() {

    }

    public static JPanel create(Border border) {
        JPanel commandPanel = new JPanel(new GridLayout(2,1));
        commandPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        commandPanel.setPreferredSize(new Dimension(350, 0));
        commandPanel.add(MovementPanel.create(border), BorderLayout.PAGE_START);
        commandPanel.add(ActionPanel.create(border), BorderLayout.PAGE_END);
        commandPanel.setBackground(Color.black);
        commandPanel.setBorder(BorderFactory.createTitledBorder(border, "Commands", 0,2, null, Color.green));
        return commandPanel;
    }

}