package com.swing.panels;

import com.swing.ButtonFactory;
import com.swing.WrapLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.List;


public class PickUpPanel {

    private static JPanel roomItems;
    private static JPanel roomItemsScrollPanel;

    public static JPanel create(Border border) {

        roomItemsScrollPanel = new JPanel(new BorderLayout());
        roomItemsScrollPanel.setBorder(BorderFactory.createTitledBorder(border, "Room Items (Pick Up)", 0,2, null, Color.green));
        roomItemsScrollPanel.setBackground(Color.black);

        JScrollPane roomScrollPane = createRoomItemsScrollPane(border);

        roomItemsScrollPanel.add(roomScrollPane);

        return roomItemsScrollPanel;
    }

    private static JScrollPane createRoomItemsScrollPane(Border border) {
        roomItems = new JPanel(new WrapLayout(WrapLayout.LEADING));
        roomItems.setBackground(Color.black);
        JScrollPane roomScrollPane = new JScrollPane(roomItems);
        roomScrollPane.setBackground(Color.black);
        roomScrollPane.setBorder(null);

        roomScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
        roomScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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

        return roomScrollPane;
    }

    public static void renderRoomItems(List<String> currentRoomItems){
        roomItems.removeAll();
        for (String item : currentRoomItems) {
            roomItems.add(ButtonFactory.createRadioButton(item));
        }

        roomItemsScrollPanel.validate();
//        InventoryScrollPanel.repaint();
    }

}