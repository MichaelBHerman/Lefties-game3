package com.swing;

import com.swing.panels.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.BorderLayout;

import static com.swing.panels.ActionPanel.setActionPanel;
import static com.swing.panels.GamePanel.setMainGamePanel;

public class MyFrame {

    private JPanel gamePanel;
    private JPanel mapPanel;
    private JPanel menuPanel;
    private final JPanel southPanel = new JPanel(new BorderLayout());

    public MyFrame() {
        initializePanels();
        JFrame frame = new JFrame("Game Frame");
        Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        frame.setCursor(cursor);

        frame.setLayout(new BorderLayout());
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(mapPanel, BorderLayout.LINE_START);
        frame.add(southPanel, BorderLayout.PAGE_END);
        frame.add(menuPanel, BorderLayout.LINE_END);

        frame.setPreferredSize(new Dimension(1400, 900));
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private void initializePanels() {
        Border border = new LineBorder(Color.GREEN, 1, false);
        this.gamePanel = GamePanel.create(border);
        this.mapPanel = MapPanel.create(border);
        this.menuPanel = MenuPanel.create(border);
        JPanel inventoryPanel = InventoryPanel.create(border);
        JPanel actionPanel = ActionPanel.create(border);
        southPanel.add(inventoryPanel, BorderLayout.CENTER);
        southPanel.add(actionPanel, BorderLayout.LINE_END);
    }

    public static void updateFrameWindow(String option) {
        setActionPanel(option);
        setMainGamePanel(option);
    }

    public static void createCardLayout() {

    }

}