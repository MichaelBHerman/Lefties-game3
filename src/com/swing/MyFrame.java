package com.swing;

import com.swing.panels.CommandPanel;
import com.swing.panels.GamePanel;
import com.swing.panels.InventoryPanel;
import com.swing.panels.MenuPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.BorderLayout;

public class MyFrame {

    JFrame frame;
    JPanel inventoryPanel;
    JPanel gamePanel;
    JPanel commandPanel;
    JPanel menuPanel;

    public MyFrame() {
        initializePanels();
        this.frame = new JFrame("Game Frame");
        Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        frame.setCursor(cursor);
        frame.setLayout(new BorderLayout());
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(menuPanel, BorderLayout.LINE_END);
        frame.add(commandPanel, BorderLayout.LINE_START);
        frame.add(inventoryPanel, BorderLayout.PAGE_END);
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
    }

    private void initializePanels() {
        Border border = new LineBorder(Color.GREEN, 1, false);
        this.inventoryPanel = InventoryPanel.create(border);
        this.gamePanel = GamePanel.create(border);
        this.commandPanel = CommandPanel.create(border);
        this.menuPanel = MenuPanel.create(border);
    }

}