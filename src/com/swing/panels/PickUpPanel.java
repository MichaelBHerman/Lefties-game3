package com.swing.panels;

import com.swing.ButtonFactory;
import com.swing.WrapLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.List;

import static com.swing.MyFrame.gamePanel;

public class PickUpPanel {

    private static JPanel roomItemsScrollPanel;
    private static JPanel roomItems;

    public static JPanel create(Border border) {

        roomItemsScrollPanel = new JPanel(new BorderLayout());
        //RoomItemsScrollPanel.setPreferredSize(new Dimension(0, 250));
        roomItemsScrollPanel.setBorder(BorderFactory.createTitledBorder(border, "Room Items (Pick Up)", 0,2, null, Color.green));
        roomItemsScrollPanel.setBackground(Color.black);

        JScrollPane roomScrollPane = createRoomItemsScrollPane(border);

        roomItemsScrollPanel.add(roomScrollPane);

        return roomItemsScrollPanel;
    }

//    public static void updateInventoryGUI(List<String> playerInventory) {
//        inventory.removeAll();
//        for (String item : playerInventory) {
//            inventory.add(ButtonFactory.createRadioButton(item));
//        }
//
//        InventoryScrollPanel.validate();
//    }

    private static JScrollPane createRoomItemsScrollPane(Border border) {
        roomItems = new JPanel(new WrapLayout(WrapLayout.LEADING));
        roomItems.setBackground(Color.black);
        JScrollPane inventoryScrollPane = new JScrollPane(roomItems);
        inventoryScrollPane.setBackground(Color.black);
        inventoryScrollPane.setBorder(null);

        inventoryScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
        inventoryScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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

        return inventoryScrollPane;
    }

}