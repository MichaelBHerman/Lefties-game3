package com.swing.panels;

import com.pending.game3.Game3;
import com.pending.game3.InputParser;
import com.pending.game3.Item;
import com.swing.ButtonFactory;
import com.swing.WrapLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.pending.game3.InputParser.confirmSelected;
import static com.swing.panels.CraftingPanel.recipeItemsList;

public class InventoryPanel {

    private static JPanel inventoryScrollPanel;
    private static JPanel inventory;

    public static ArrayList<JRadioButton> inventoryItemsList = new ArrayList<JRadioButton>();

    InventoryPanel() {

    }

    public static JPanel create(Border border) {
        inventoryScrollPanel = new JPanel(new BorderLayout());
        inventoryScrollPanel.setPreferredSize(new Dimension(0, 250));
        inventoryScrollPanel.setBorder(BorderFactory.createTitledBorder(border, "Inventory", 0,2, null, Color.green));
        inventoryScrollPanel.setBackground(Color.black);

        JScrollPane inventoryScrollPane = createInventoryScrollPane(border);

        inventoryScrollPanel.add(inventoryScrollPane);

        return inventoryScrollPanel;
    }

    public static void updateInventoryGUI(HashMap<String, Item> playerInventory) {
        inventory.removeAll();
        inventoryItemsList.clear();
        for (String item : playerInventory.keySet()) {
            JRadioButton newBtn = ButtonFactory.createRadioButton(item);
            inventory.add(newBtn);
            inventoryItemsList.add(newBtn);
        }

        inventoryScrollPanel.validate();
        inventoryScrollPanel.repaint();
    }

    private static JScrollPane createInventoryScrollPane(Border border) {
        inventory = new JPanel(new WrapLayout(WrapLayout.LEADING));
        inventory.setBackground(Color.black);
        JScrollPane inventoryScrollPane = new JScrollPane(inventory);
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

    static void dropSelectedItems() {
        List<JRadioButton> filteredItemsList = getSelectedList();

        System.out.println(inventoryItemsList);

        for (JRadioButton btn : filteredItemsList) {
            System.out.println(btn.getActionCommand());
            InputParser.getGUIInput("drop " + btn.getActionCommand());
        }

        inventoryItemsList.removeAll(filteredItemsList);
        Game3.displayRoomGUI();
    }

    static void craftSelectedItems() {
        ArrayList<JRadioButton> filteredItemsList = getSelectedList();

        System.out.println(inventoryItemsList);

        InputParser.getGUIInput("craft");
        inventoryItemsList.removeAll(filteredItemsList);

        Game3.displayRoomGUI();
    }

    public static ArrayList<JRadioButton> getSelectedList() {
        return (ArrayList<JRadioButton>) inventoryItemsList.stream().filter(btn -> btn.isSelected()).collect(Collectors.toList());
    }

}