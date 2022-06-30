package com.swing.panels;

import com.pending.game3.Game3;
import com.pending.game3.InputParser;
import com.pending.game3.Item;
import com.swing.ButtonFactory;
import com.swing.WrapLayout;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class RoomItemsPanel {

    public static JPanel currentRoomItems;
    private static JPanel roomItemsScrollPanel;
    public static ArrayList<JRadioButton> roomItemsList = new ArrayList<JRadioButton>();

    public static JPanel create(Border border) {

        roomItemsScrollPanel = new JPanel(new BorderLayout());
        roomItemsScrollPanel.setBorder(BorderFactory.createTitledBorder(border, "Room Items (Pick Up)", 0,2, null, Color.green));
        roomItemsScrollPanel.setBackground(Color.black);

        JScrollPane roomScrollPane = createRoomItemsScrollPane(border);

        roomItemsScrollPanel.add(roomScrollPane);

        return roomItemsScrollPanel;
    }

    private static JScrollPane createRoomItemsScrollPane(Border border) {
        currentRoomItems = new JPanel(new WrapLayout(WrapLayout.LEADING));
        currentRoomItems.setBackground(Color.black);
        JScrollPane roomScrollPane = new JScrollPane(currentRoomItems);
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

    public static void renderRoomItems(HashMap<String, Item> roomItems){
        currentRoomItems.removeAll();
        roomItemsList.clear();
        for (String item : roomItems.keySet()) {
            JRadioButton newBtn = ButtonFactory.createRadioButton(item);
            roomItemsList.add(newBtn);
            currentRoomItems.add(newBtn);
        }
        roomItemsScrollPanel.validate();
        roomItemsScrollPanel.repaint();
    }

    static void takeSelectedItems() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        List<JRadioButton> filteredItemsList = roomItemsList.stream().filter(btn -> btn.isSelected()).collect(Collectors.toList());

        System.out.println(roomItemsList);

        for (JRadioButton btn : filteredItemsList) {
            System.out.println(btn.getActionCommand());
            InputParser.getGUIInput("take " + btn.getActionCommand());
        }
        roomItemsList.removeAll(filteredItemsList);

        Game3.displayRoomGUI();
    }

}