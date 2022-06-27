package com.swing;

import com.swing.panels.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.BorderLayout;

public class MyFrame {

    private static JFrame frame;
    private static JPanel gameWindow;
    private JPanel inventoryPanel;
    public static JPanel gamePanel;
    public static JPanel roomItemsPanel;
    private static JPanel roomNpcsPanel;

    private JPanel commandPanel;
    private JPanel menuPanel;
    private JPanel actionPanel;
    private JPanel movementPanel;
    private JPanel southPanel = new JPanel(new BorderLayout());

    public MyFrame() {
        initializePanels();
        this.frame = new JFrame("Game Frame");
        Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        frame.setCursor(cursor);
        frame.setLayout(new BorderLayout());
        frame.add(gameWindow, BorderLayout.CENTER);
        //frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(commandPanel, BorderLayout.LINE_START);
        frame.add(southPanel, BorderLayout.PAGE_END);
        frame.add(menuPanel, BorderLayout.LINE_END);
        //frame.add(inventoryPanel, BorderLayout.PAGE_END);

        frame.setPreferredSize(new Dimension(1300, 800));
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initializePanels() {
        Border border = new LineBorder(Color.GREEN, 1, false);
        this.inventoryPanel = InventoryPanel.create(border);
        this.gamePanel = GamePanel.create(border);
        this.commandPanel = MapPanel.create(border);
        this.menuPanel = MenuPanel.create(border);
        this.actionPanel = ActionPanel.create(border);
        this.roomItemsPanel = PickUpPanel.create(border);
        this.roomNpcsPanel = TalkPanel.create(border);

        //this.roomItemsPanel.setVisible(false);
//        southPanel.add(menuPanel, BorderLayout.LINE_END);
        gameWindow = new JPanel(new CardLayout());
        gameWindow.add(gamePanel);
        gameWindow.add(roomItemsPanel);
        gameWindow.add(roomNpcsPanel);
        southPanel.add(inventoryPanel, BorderLayout.CENTER);
        southPanel.add(actionPanel, BorderLayout.LINE_END);
    }

    public static void setGameWindow(String option) {
        Border border = new LineBorder(Color.GREEN, 1, false);
        if (option.equals("take")) {
            gamePanel.setVisible(false);
            roomItemsPanel.setVisible(true);
            roomNpcsPanel.setVisible(false);
            System.out.println("Changing to take mode.");
        } else if (option.equals("talk")) {
            gamePanel.setVisible(false);
            roomItemsPanel.setVisible(false);
            roomNpcsPanel.setVisible(true);
            System.out.println("Changing to talk mode.");
        }

    }

    public static void createCardLayout() {

    }

}