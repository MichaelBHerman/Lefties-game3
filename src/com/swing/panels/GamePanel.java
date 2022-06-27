package com.swing.panels;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class GamePanel {
    private static JTextArea outputTextArea;
    private static JScrollPane outputScrollWindow;

    GamePanel() {

    }

    public static JPanel create(Border border) {
        outputTextArea = createOutputTextArea();
        JTextField textInputField = createTextInputField(border, outputTextArea);
        JScrollPane outputScrollWindow = createOutputScrollWindow(border, outputTextArea);

        return createGamePanel(border, textInputField, outputScrollWindow);
    }

    private static JTextField createTextInputField(Border border, JTextArea outputTextArea) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 40));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.green);
        textField.setCaretColor(Color.green);
        textField.setBorder(border);

        // Adding event listener to save the output text within the text field to the outputTextArea
        textField.addActionListener(e -> {
            outputTextArea.append("\n>" + e.getActionCommand());
            outputTextArea.revalidate();
            textField.setText("");
        });

        return textField;
    }

    private static JPanel createGamePanel(Border border, JTextField textField, JScrollPane outputScrollWindow) {
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

}