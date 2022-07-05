package com.swing;

import com.pending.game3.CraftingRecipe;
import com.pending.game3.Game3;
import com.pending.game3.Item;
import com.swing.panels.CraftingPanel;
import com.swing.panels.MapPanel;
import com.swing.panels.RoomItemsPanel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.swing.panels.ActionPanel.craftItemPanel;

public class ButtonFactory {

    static ArrayList<JRadioButton> recipeButtonsList = new ArrayList<>();
    static ArrayList<JRadioButton> recipeResultItemList = new ArrayList<>();

    ButtonFactory() {

    }

    public static JButton createButton(String btnName, Border border) {
        JButton button = new JButton(btnName);

        // Setting up the button color/border
        button.setFocusPainted(false);
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
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("take");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("craft")) {
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("craft");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("talk")) {
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("talk");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("drop")) {
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("drop");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("back")) {
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("back");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("confirm selected")) {
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("confirm selected");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("clockwise")) {
            System.out.println("Going clockwise");
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("clockwise");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("counter-clockwise")) {
            System.out.println("Going counter-clockwise");
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("counter-clockwise");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("outward")) {
            System.out.println("Going outward");
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("outward");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("inward")) {
            System.out.println("Going inward");
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("inward");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("outward right")) {
            System.out.println("Going outward right");
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("outward right");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (btnName.equalsIgnoreCase("outward left")) {
            System.out.println("Going outward left");
            button.addActionListener(e -> {
                try {
                    MyFrame.updateFrameWindow("outward left");
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return button;
    }

    public static JRadioButton createRadioButton(Item item) {
        ImageIcon healingIcon = new ImageIcon(ButtonFactory.class.getResource("/resources/" + item.getName() + ".png"));
//        ImageIcon healingIcon = new ImageIcon("resources/" + item + ".png");
        healingIcon.setImage(healingIcon.getImage().getScaledInstance(100,85, Image.SCALE_DEFAULT));
        JRadioButton imgButton = new JRadioButton();
        imgButton.setIcon(healingIcon);
        imgButton.setText(item.getName());
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
                updateRecipeRadioButton(imgButton);
                System.out.println(imgButton.getActionCommand());
                // TODO: System.out.println(imgButton.setActionCommand( item name + " " + itemId)); Set the btn to use the item id instead.
            } else {
                updateRecipeRadioButton(imgButton);
                imgButton.setBackground(Color.black);
                imgButton.setForeground(Color.green);
            }
        });
        // TODO add tooltip on hover of item description
        imgButton.setToolTipText(item.getDescription());

        imgButton.setBackground(Color.black);
        return imgButton;
    }

    public static JRadioButton createRadioButton(String npc) {
        //ImageIcon healingIcon = new ImageIcon(ButtonFactory.class.getResource("/resources/" + npc + ".png"));
        ImageIcon healingIcon = new ImageIcon("resources/Medicine.png");
        healingIcon.setImage(healingIcon.getImage().getScaledInstance(100,85, Image.SCALE_DEFAULT));
        JRadioButton imgButton = new JRadioButton();
        imgButton.setIcon(healingIcon);
        imgButton.setText(npc);
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
                updateRecipeRadioButton(imgButton);
                System.out.println(imgButton.getActionCommand());
                // TODO: System.out.println(imgButton.setActionCommand( item name + " " + itemId)); Set the btn to use the item id instead.
            } else {
                updateRecipeRadioButton(imgButton);
                imgButton.setBackground(Color.black);
                imgButton.setForeground(Color.green);
            }
        });
        // TODO add tooltip on hover of item description
        //imgButton.setToolTipText(npc.getDescription());

        imgButton.setBackground(Color.black);
        return imgButton;
    }

    public static JRadioButton createRecipeRadioButton(String item) {
        ImageIcon healingIcon = new ImageIcon("resources/" + item + ".png");
        healingIcon.setImage(healingIcon.getImage().getScaledInstance(100, 85, Image.SCALE_DEFAULT));
        JRadioButton imgButton = new JRadioButton();
        imgButton.setIcon(healingIcon);
        imgButton.setText(item);
        imgButton.setFocusPainted(false);
        imgButton.setForeground(Color.green);
        imgButton.setVerticalTextPosition(JRadioButton.BOTTOM);
        imgButton.setHorizontalTextPosition(JRadioButton.CENTER);
        imgButton.setIconTextGap(-5);
        imgButton.setBackground(Color.black);
        recipeButtonsList.add(imgButton);
        return imgButton;
    }

    public static JRadioButton createRecipeRadioButton(CraftingRecipe recipe) {
        ImageIcon healingIcon = new ImageIcon("resources/" + recipe.getName() + ".png");
        healingIcon.setImage(healingIcon.getImage().getScaledInstance(100, 85, Image.SCALE_DEFAULT));
        JRadioButton imgButton = new JRadioButton();
        imgButton.setIcon(healingIcon);
        imgButton.setText(recipe.getName());
        imgButton.setFocusPainted(false);
        imgButton.setForeground(Color.lightGray);
        imgButton.setVerticalTextPosition(JRadioButton.BOTTOM);
        imgButton.setHorizontalTextPosition(JRadioButton.CENTER);
        imgButton.setIconTextGap(-5);
        imgButton.setBackground(Color.black);
        recipeResultItemList.add(imgButton);
        return imgButton;
    }

    public static void updateRecipeRadioButton(JRadioButton item) {
        for (JRadioButton btn : recipeButtonsList) {
            if (btn.getActionCommand().equalsIgnoreCase(item.getActionCommand())) {
                if (item.isSelected()) {
                    btn.setBackground(Color.GREEN);
                    btn.setForeground(Color.black);
                } else {
                    btn.setBackground(Color.black);
                    btn.setForeground(Color.green);
                }
            }
        }
    }

}