package com.swing.panels;

import com.swing.ButtonFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MenuPanel {

    MenuPanel() {

    }

    public static JPanel create(Border border) {
        JPanel menuPanel = new JPanel(new GridLayout(5,1, 0,5));
        menuPanel.setPreferredSize(new Dimension(100, 0));
        menuPanel.add(ButtonFactory.createButton("Map", border));
        menuPanel.add(ButtonFactory.createButton("Save Game", border));
        menuPanel.add(ButtonFactory.createButton("Load Game", border));
        menuPanel.add(ButtonFactory.createButton("Options", border));
        menuPanel.add(ButtonFactory.createButton("Exit Game", border));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
        menuPanel.setBackground(Color.black);
        menuPanel.setBorder(BorderFactory.createTitledBorder(border, "Menu", 0,2, null, Color.green));

        return menuPanel;
    }

}