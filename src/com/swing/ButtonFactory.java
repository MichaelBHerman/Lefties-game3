package com.swing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        if (btnName.equalsIgnoreCase("Pick up")) {
            return pickUp(button);
        } else if (btnName.equalsIgnoreCase("talk")) {
            return talk(button);
        }

        return button;
    }

    private static JButton pickUp(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.setGameWindow("take");
            }
        });
        return button;
    }

    private static JButton talk(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.setGameWindow("talk");
            }
        });
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
        imgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(imgButton.isSelected());
                if (imgButton.isSelected()) {
                    imgButton.setBackground(Color.GREEN);
                } else {
                    imgButton.setBackground(Color.black);
                }
            }
        });
        imgButton.setBackground(Color.black);
        return imgButton;
    }
}