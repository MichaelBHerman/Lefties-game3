package com.swing.panels;

import com.pending.game3.Game3;
import com.pending.game3.InputParser;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.IOException;

public class GamePanel {
    private static JTextArea outputTextArea;
    private static JScrollPane outputScrollWindow;
    private static JPanel roomItemsPanel;
    private static JPanel roomNpcsPanel;
    private static JPanel recipesPanel;
    private static JPanel gameInfoPanel;
    private static JPanel mainGamePanel;

    GamePanel() {

    }

    public static JPanel create(Border border) {
        outputTextArea = createOutputTextArea();
        JTextField textInputField = createTextInputField(border, outputTextArea);
        JScrollPane outputScrollWindow = createOutputScrollWindow(border, outputTextArea);
        roomItemsPanel = RoomItemsPanel.create(border);
        roomNpcsPanel = NPCPanel.create(border);
        recipesPanel = CraftingPanel.create(border);
        gameInfoPanel = createGameInfoPanel(border,textInputField, outputScrollWindow);
        mainGamePanel = new JPanel(new CardLayout());
        mainGamePanel.add(gameInfoPanel);
        mainGamePanel.add(roomItemsPanel);
        mainGamePanel.add(roomNpcsPanel);
        mainGamePanel.add(recipesPanel);

        return mainGamePanel;
    }

    private static JTextField createTextInputField(Border border, JTextArea outputTextArea) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(0, 40));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.green);
        textField.setCaretColor(Color.green);
        textField.setBorder(border);

        // Adding event listener to save the output text within the text field to the outputTextArea
        textField.addActionListener(e -> {
            outputTextArea.append("\n>" + e.getActionCommand());
            outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
            outputTextArea.revalidate();
            textField.setText("");
            try {
                InputParser.getGUIInput(e.getActionCommand());
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
            Game3.displayRoomGUI();
        });

        return textField;
    }

    private static JPanel createGameInfoPanel(Border border, JTextField textField, JScrollPane outputScrollWindow) {
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(textField, BorderLayout.PAGE_END);
        gamePanel.setBackground(Color.black);
        gamePanel.setBorder(BorderFactory.createTitledBorder(border, "Game Window", 0, 2, null, Color.green));
        gamePanel.add(outputScrollWindow);
        return gamePanel;
    }

    private static JTextArea createOutputTextArea() {
        JTextArea outputTextArea = new JTextArea();
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
        outputTextArea.setBackground(Color.black);
        outputTextArea.setForeground(Color.green);
        outputTextArea.setEditable(false);
        return outputTextArea;
    }

    public static void updateOutputTextArea(String roomInfo) {
        outputTextArea.append(roomInfo);
        // Set the scroll bar to the bottom for the user.
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());

    }

    // Reset the game window
    public static void clearOutputTextArea() {
        outputTextArea.setText("");
        gameInfoPanel.validate();
        gameInfoPanel.repaint();
        //outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
    }

    private static JScrollPane createOutputScrollWindow(Border border, JTextArea outputTextArea) {
        outputScrollWindow = new JScrollPane(outputTextArea);
        outputScrollWindow.setBorder(null);
        outputScrollWindow.getVerticalScrollBar().setBackground(Color.BLACK);
        outputScrollWindow.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.GREEN;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(Color.black);
                button.setForeground(Color.black);
                button.setBorder(border);
                return button;
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(Color.black);
                button.setForeground(Color.black);
                button.setBorder(border);
                return button;
            }
        });
        return outputScrollWindow;
    }

    public static void setMainGamePanel(String option) {
        if(option.equalsIgnoreCase("take")) {
            gameInfoPanel.setVisible(false);
            roomItemsPanel.setVisible(true);
            roomNpcsPanel.setVisible(false);
            recipesPanel.setVisible(false);
        } else if (option.equalsIgnoreCase("talk")) {
            gameInfoPanel.setVisible(false);
            roomItemsPanel.setVisible(false);
            roomNpcsPanel.setVisible(true);
            recipesPanel.setVisible(false);
        } else if (option.equalsIgnoreCase("craft")) {
            gameInfoPanel.setVisible(false);
            roomItemsPanel.setVisible(false);
            roomNpcsPanel.setVisible(false);
            recipesPanel.setVisible(true);
        } else if (option.equalsIgnoreCase("back")) {
            gameInfoPanel.setVisible(true);
            roomItemsPanel.setVisible(false);
            roomNpcsPanel.setVisible(false);
            recipesPanel.setVisible(false);
        } else if (option.equalsIgnoreCase("confirm selected")) {
            gameInfoPanel.setVisible(true);
            roomItemsPanel.setVisible(false);
            roomNpcsPanel.setVisible(false);
            recipesPanel.setVisible(false);
        }

    }

}