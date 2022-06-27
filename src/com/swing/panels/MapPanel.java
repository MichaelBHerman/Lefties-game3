package com.swing.panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MapPanel {

    MapPanel() {

    }

    public static JPanel create(Border border) {
        JPanel mapPanel = new JPanel(new BorderLayout());
        mapPanel.setPreferredSize(new Dimension(400, 400));
        JPanel map = new JPanel();
        mapPanel.add(map, BorderLayout.CENTER);
        mapPanel.add(MovementPanel.create(border), BorderLayout.PAGE_END);
        mapPanel.setBackground(Color.black);
        mapPanel.setBorder(BorderFactory.createTitledBorder(border, "Map", 0,2, null, Color.green));
        return mapPanel;
    }

}