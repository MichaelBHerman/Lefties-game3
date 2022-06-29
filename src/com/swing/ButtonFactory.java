package com.swing;

import com.pending.game3.Item;
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
            button.addActionListener(e -> MyFrame.updateFrameWindow("take"));

        } else if (btnName.equalsIgnoreCase("talk")) {
            button.addActionListener(e -> MyFrame.updateFrameWindow("talk"));

        } else if (btnName.equalsIgnoreCase("drop")) {
            button.addActionListener(e -> MyFrame.updateFrameWindow("drop"));

        } else if (btnName.equalsIgnoreCase("back")) {
            button.addActionListener(e -> MyFrame.updateFrameWindow("back"));

        } else if (btnName.equalsIgnoreCase("confirm selected")) {
            button.addActionListener(e -> MyFrame.updateFrameWindow("confirm selected"));

        } else if (btnName.equalsIgnoreCase("clockwise")) {
            System.out.println("Going clockwise");
            button.addActionListener(e -> MyFrame.updateFrameWindow("clockwise"));

        } else if (btnName.equalsIgnoreCase("counter-clockwise")) {
            System.out.println("Going counter-clockwise");
            button.addActionListener(e -> MyFrame.updateFrameWindow("counter-clockwise"));

        } else if (btnName.equalsIgnoreCase("outward")) {
            System.out.println("Going outward");
            button.addActionListener(e -> MyFrame.updateFrameWindow("outward"));

        } else if (btnName.equalsIgnoreCase("inward")) {
            System.out.println("Going inward");
            button.addActionListener(e -> MyFrame.updateFrameWindow("inward"));

        } else if (btnName.equalsIgnoreCase("outward right")) {
            System.out.println("Going outward right");
            button.addActionListener(e -> MyFrame.updateFrameWindow("outward right"));

        }  else if (btnName.equalsIgnoreCase("outward left")) {
            System.out.println("Going outward left");
            button.addActionListener(e -> MyFrame.updateFrameWindow("outward left"));

        }
        return button;
    }

    public static JRadioButton createRadioButton(String item) {
        ImageIcon healingIcon = new ImageIcon("resources/" + item + ".png");
        healingIcon.setImage(healingIcon.getImage().getScaledInstance(100,85, Image.SCALE_DEFAULT));
        JRadioButton imgButton = new JRadioButton();
        imgButton.setIcon(healingIcon);
        imgButton.setText(item);
        imgButton.setFocusPainted(false);
        imgButton.setForeground(Color.green);
        imgButton.setVerticalTextPosition(JRadioButton.BOTTOM);
        imgButton.setHorizontalTextPosition(JRadioButton.CENTER);
        imgButton.setIconTextGap(-5);
        imgButton.addActionListener(e -> {
            System.out.println(imgButton.isSelected());

            if (imgButton.isSelected()) {
                imgButton.setBackground(Color.GREEN);
                imgButton.setForeground(Color.black);
                System.out.println(imgButton.getActionCommand());
                // TODO: System.out.println(imgButton.setActionCommand( item name + " " + itemId)); Set the btn to use the item id instead.
            } else {
                imgButton.setBackground(Color.black);
                imgButton.setForeground(Color.green);
            }
        });
        imgButton.setBackground(Color.black);
        return imgButton;
    }

}