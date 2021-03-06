package com.swing.panels;

import com.pending.game3.Game3;
import com.swing.ButtonFactory;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

import static com.swing.ButtonFactory.updateRecipeRadioButton;

public class ActionPanel {

    private static JPanel takeRoomItemPanel;
    private static JPanel mainPanel;
    private static JPanel talkRoomNPCPanel;
    private static JPanel dropItemPanel;
    public static JPanel craftItemPanel;

    ActionPanel() {

    }

    public static JPanel create(Border border) {
        JPanel actionPanel = new JPanel(new CardLayout());
        JPanel mainPanel = createMainPanel(border);
        JPanel takeRoomItemPanel = createTakeRoomItemPanel(border);
        JPanel craftItemPanel = createCraftItemPanel(border);
        JPanel talkRoomNPCPanel = createTalkRoomNPCPanel(border);
        JPanel dropItemPanel = createDropItemPanel(border);
        actionPanel.setPreferredSize(new Dimension(400,0));
        actionPanel.add(mainPanel);
        actionPanel.add(takeRoomItemPanel);
        actionPanel.add(craftItemPanel);
        actionPanel.add(talkRoomNPCPanel);
        actionPanel.add(dropItemPanel);

        return actionPanel;
    }

    private static JPanel createMainPanel(Border border) {
        mainPanel = new JPanel(new GridLayout(3,2,7,7));
        mainPanel.add(ButtonFactory.createButton("Take", border));
        mainPanel.add(ButtonFactory.createButton("Talk", border));
        mainPanel.add(ButtonFactory.createButton("Craft", border));
        mainPanel.add(ButtonFactory.createButton("Drop", border));
        mainPanel.add(ButtonFactory.createButton("Use", border));
        mainPanel.add(ButtonFactory.createButton("Inspect", border));
        mainPanel.setBackground(Color.black);
        mainPanel.setBorder(BorderFactory.createTitledBorder(border, "Actions", 0,2, null, Color.green));

        return mainPanel;
    }

    private static JPanel createTakeRoomItemPanel(Border border) {
        takeRoomItemPanel = new JPanel(new GridLayout(2,1,7,7));
        takeRoomItemPanel.add(ButtonFactory.createButton("Confirm Selected", border));
        takeRoomItemPanel.add(ButtonFactory.createButton("Back", border));
        takeRoomItemPanel.setBackground(Color.black);
        takeRoomItemPanel.setBorder(BorderFactory.createTitledBorder(border, "Take selected item(s)", 0,2, null, Color.green));

        return takeRoomItemPanel;
    }

    private static JPanel createCraftItemPanel(Border border) {
        craftItemPanel = new JPanel(new GridLayout(2,1,7,7));
        craftItemPanel.add(ButtonFactory.createButton("Confirm Selected", border));
        craftItemPanel.add(ButtonFactory.createButton("Back", border));
        craftItemPanel.setBackground(Color.black);
        craftItemPanel.setBorder(BorderFactory.createTitledBorder(border, "Craft with selected item(s)", 0,2, null, Color.green));

        return craftItemPanel;
    }

    private static JPanel createTalkRoomNPCPanel(Border border) {
        talkRoomNPCPanel = new JPanel(new GridLayout(2,1,7,7));
        talkRoomNPCPanel.add(ButtonFactory.createButton("Confirm Selected", border));
        talkRoomNPCPanel.add(ButtonFactory.createButton("Back", border));
        talkRoomNPCPanel.setBackground(Color.black);
        talkRoomNPCPanel.setBorder(BorderFactory.createTitledBorder(border, "Talk to selected NPC", 0,2, null, Color.green));

        return talkRoomNPCPanel;
    }

    private static JPanel createDropItemPanel(Border border) {
        dropItemPanel = new JPanel(new GridLayout(2,1,7,7));
        dropItemPanel.add(ButtonFactory.createButton("Confirm Selected", border));
        dropItemPanel.add(ButtonFactory.createButton("Back", border));
        dropItemPanel.setBackground(Color.black);
        dropItemPanel.setBorder(BorderFactory.createTitledBorder(border, "Drop selected item(s)", 0,2, null, Color.green));

        return dropItemPanel;
    }

    public static void setActionPanel(String option) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(option.equalsIgnoreCase("take")) {
            mainPanel.setVisible(false);
            takeRoomItemPanel.setVisible(true);
            talkRoomNPCPanel.setVisible(false);
            dropItemPanel.setVisible(false);
            craftItemPanel.setVisible(false);
            //Game3.displayRoomGUI();
        } else if (option.equalsIgnoreCase("craft")) {
            mainPanel.setVisible(false);
            takeRoomItemPanel.setVisible(false);
            talkRoomNPCPanel.setVisible(false);
            dropItemPanel.setVisible(false);
            craftItemPanel.setVisible(true);
        } else if (option.equalsIgnoreCase("talk")) {
            mainPanel.setVisible(false);
            takeRoomItemPanel.setVisible(false);
            talkRoomNPCPanel.setVisible(true);
            dropItemPanel.setVisible(false);
            craftItemPanel.setVisible(false);
            Game3.displayRoomGUI();
        } else if (option.equalsIgnoreCase("drop")) {
            mainPanel.setVisible(false);
            takeRoomItemPanel.setVisible(false);
            talkRoomNPCPanel.setVisible(false);
            dropItemPanel.setVisible(true);
            craftItemPanel.setVisible(false);
        } else if (option.equalsIgnoreCase("back")) {
            mainPanel.setVisible(true);
            takeRoomItemPanel.setVisible(false);
            talkRoomNPCPanel.setVisible(false);
            dropItemPanel.setVisible(false);
            craftItemPanel.setVisible(false);
            Game3.displayRoomGUI();
        } else if (option.equalsIgnoreCase("confirm selected")) {
            if (takeRoomItemPanel.isVisible()) {
                RoomItemsPanel.takeSelectedItems();
            } else if (dropItemPanel.isVisible()) {
                InventoryPanel.dropSelectedItems();
            } else if (craftItemPanel.isVisible()) {
                InventoryPanel.craftSelectedItems();
            } else if (talkRoomNPCPanel.isVisible()) {
                NPCPanel.talkToSelectedNPC();
            }
            mainPanel.setVisible(true);
            takeRoomItemPanel.setVisible(false);
            talkRoomNPCPanel.setVisible(false);
            dropItemPanel.setVisible(false);
            craftItemPanel.setVisible(false);
        }
    }

}