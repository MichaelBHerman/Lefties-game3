package com.swing;

import com.swing.panels.RoomItemsPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ButtonFactory {

    ButtonFactory() {

    }

    public static JButton createButton(String btnName, Border border) {
        JButton button = new JButton(btnName);

        // Setting up the button color/border
        button.setFocusPainted( false );
        button.setBackground(Color.green);
        button.setForeground(Color.black);
        button.setBorder(border);

        // Adding event listeners to button
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.black);
                button.setForeground(Color.green);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.green);
                button.setForeground(Color.black);
            }
        });

        if (btnName.equalsIgnoreCase("take")) {
            return take(button);
        } else if (btnName.equalsIgnoreCase("talk")) {
            return talk(button);
        } else if (btnName.equalsIgnoreCase("drop")) {
            return drop(button);
        } else if (btnName.equalsIgnoreCase("back")) {
            return back(button);
        } else if (btnName.equalsIgnoreCase("confirm selected")) {
            System.out.println("Confirmed selected items.");
            return confirm(button);
        }
        return button;
    }

    public static JRadioButton createRadioButton(String itemName) {
        ImageIcon healingIcon = new ImageIcon("resources/redPotion.png");
        healingIcon.setImage(healingIcon.getImage().getScaledInstance(100,85, Image.SCALE_DEFAULT));
        JRadioButton imgButton = new JRadioButton();
        imgButton.setIcon(healingIcon);
        imgButton.setText(itemName);
        imgButton.setVerticalTextPosition(JRadioButton.BOTTOM);
        imgButton.setHorizontalTextPosition(JRadioButton.CENTER);
        imgButton.setIconTextGap(-5);
        imgButton.addActionListener(e -> {
            System.out.println(imgButton.isSelected());

            if (imgButton.isSelected()) {
                imgButton.setBackground(Color.GREEN);
                System.out.println(imgButton.getActionCommand());
                // TODO: System.out.println(imgButton.setActionCommand( item name + " " + itemId)); Set the btn to use the item id instead.
            } else {
                imgButton.setBackground(Color.black);
            }
        });
        imgButton.setBackground(Color.black);
        return imgButton;
    }

    private static JButton take(JButton button) {
        button.addActionListener(e -> MyFrame.updateFrameWindow("take"));
        return button;
    }

    private static JButton talk(JButton button) {
        button.addActionListener(e -> MyFrame.updateFrameWindow("talk"));
        return button;
    }

    private static JButton drop(JButton button) {
        button.addActionListener(e -> MyFrame.updateFrameWindow("drop"));
        return button;
    }

    private static JButton back(JButton button) {
        button.addActionListener(e -> MyFrame.updateFrameWindow("back"));
        return button;
    }

    private static JButton confirm(JButton button) {
        button.addActionListener(e -> MyFrame.updateFrameWindow("confirm selected"));
        return button;
    }

}
