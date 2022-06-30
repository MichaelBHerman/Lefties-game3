package com.swing.panels;

import com.pending.game3.CraftingRecipe;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.pending.game3.InputParser.confirmSelected;

public class CraftingPanel {

    public static JPanel recipes;
    private static JPanel recipesScrollPanel;
    public static ArrayList<JRadioButton> recipeItemsList = new ArrayList<JRadioButton>();

    public static JPanel create(Border border) {

        recipesScrollPanel = new JPanel(new BorderLayout());
        recipesScrollPanel.setBorder(BorderFactory.createTitledBorder(border, "Crafting (Available recipes)", 0,2, null, Color.green));
        recipesScrollPanel.setBackground(Color.black);

        JScrollPane roomScrollPane = createRoomItemsScrollPane(border);

        recipesScrollPanel.add(roomScrollPane);

        return recipesScrollPanel;
    }

    private static JScrollPane createRoomItemsScrollPane(Border border) {
        recipes = new JPanel(new WrapLayout(WrapLayout.LEADING));
        recipes.setBackground(Color.black);
        JScrollPane recipeScrollPane = new JScrollPane(recipes);
        recipeScrollPane.setBackground(Color.black);
        recipeScrollPane.setBorder(null);

        recipeScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
        recipeScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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

        return recipeScrollPane;
    }

    public static void renderRecipeItems(){
        recipes.removeAll();
        recipeItemsList.clear();
        for (CraftingRecipe recipe : Game3.getCraftingRecipes()) {
            JRadioButton newBtn = ButtonFactory.createRecipeRadioButton(recipe);
            recipeItemsList.add(newBtn);
            recipes.add(newBtn);
            for (String itemName : recipe.ingredients) {
                newBtn = ButtonFactory.createRecipeRadioButton(itemName);
                recipeItemsList.add(newBtn);
                recipes.add(newBtn);
            }
        }
        recipesScrollPanel.validate();
        recipesScrollPanel.repaint();
    }

}