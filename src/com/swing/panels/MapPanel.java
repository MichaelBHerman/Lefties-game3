package com.swing.panels;

import com.pending.game3.Game3;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MapPanel {

    MapPanel() {

    }

    private static JLabel map = new JLabel();
    private static ImageIcon mapIcon = new ImageIcon("resources/Examination Room.png");

    public static JPanel create(Border border) {
        JPanel mapPanel = new JPanel(new BorderLayout());
        map.setSize(new Dimension(425, 425));
        mapIcon.setImage(mapIcon.getImage().getScaledInstance(map.getWidth(),map.getHeight(), Image.SCALE_SMOOTH));
        map.setIcon((mapIcon));
        map.setBackground(Color.green);
        mapPanel.add(map, BorderLayout.CENTER);
        mapPanel.add(MovementPanel.create(border), BorderLayout.PAGE_END);
        mapPanel.setBackground(Color.black);
        mapPanel.setBorder(BorderFactory.createTitledBorder(border, "Map", 0,2, null, Color.green));
        return mapPanel;
    }

    public static void updateMapGUI() {
        mapIcon = new ImageIcon("resources/" + Game3.getCurrentRoom().getName() + ".png");
        mapIcon.setImage(mapIcon.getImage().getScaledInstance(map.getWidth(),map.getHeight(), Image.SCALE_SMOOTH));
        map.setIcon((mapIcon));
    }

}