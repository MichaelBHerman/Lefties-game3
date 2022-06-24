package com.swing.panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryPanel {

    private static JPanel inventoryPanel;

    InventoryPanel() {

    }

    public static JPanel create(Border border) {
        inventoryPanel = new JPanel(new GridLayout(2,2, 5,5));;
        inventoryPanel.setPreferredSize(new Dimension(0, 350));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder(border, "Inventory", 0,2, null, Color.green));
        inventoryPanel.setBackground(Color.black);
        return inventoryPanel;
    }

    public static void updateInventoryGUI(List<String> inventory) {
        System.out.println(inventory);
//        for (String item : inventory) {
//            new JPanel()
//        }
        //JLabel image = new JLabel()
    }

}