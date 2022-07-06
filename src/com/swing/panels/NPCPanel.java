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


public class NPCPanel {

    private static JPanel roomNPCScrollPanel;
    public static JPanel currentRoomNPC;
    public static ArrayList<JRadioButton> roomNPCList = new ArrayList<JRadioButton>();

    public static JPanel create(Border border) {

        roomNPCScrollPanel = new JPanel(new BorderLayout());
        //RoomItemsScrollPanel.setPreferredSize(new Dimension(0, 250));
        roomNPCScrollPanel.setBorder(BorderFactory.createTitledBorder(border, "Room NPCs (Talk)", 0,2, null, Color.green));
        roomNPCScrollPanel.setBackground(Color.black);

        JScrollPane roomScrollPane = createRoomNPCScrollPane(border);

        roomNPCScrollPanel.add(roomScrollPane);

        return roomNPCScrollPanel;
    }

    private static JScrollPane createRoomNPCScrollPane(Border border) {
        currentRoomNPC = new JPanel(new WrapLayout(WrapLayout.LEADING));
        currentRoomNPC.setBackground(Color.black);
        JScrollPane npcScrollPane = new JScrollPane(currentRoomNPC);
        npcScrollPane.setBackground(Color.black);
        npcScrollPane.setBorder(null);

        npcScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
        npcScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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

        return npcScrollPane;
    }

    public static void renderRoomNPC(List<String> roomNPCs){
        currentRoomNPC.removeAll();
        roomNPCList.clear();
        for (String npc : roomNPCs) {
            JRadioButton newBtn = ButtonFactory.createRadioButton(npc);
            roomNPCList.add(newBtn);
            currentRoomNPC.add(newBtn);
        }
        roomNPCScrollPanel.validate();
        roomNPCScrollPanel.repaint();
    }

    static void talkToSelectedNPC() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        List<JRadioButton> filteredItemsList = roomNPCList.stream().filter(btn -> btn.isSelected()).collect(Collectors.toList());
        for (JRadioButton btn : filteredItemsList) {
            InputParser.getGUIInput("talk " + btn.getActionCommand());
        }
        roomNPCList.removeAll(filteredItemsList);
        Game3.displayRoomGUI();
    }

}