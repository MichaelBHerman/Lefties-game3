package com.pending.game3;

import java.util.HashMap;
import java.util.List;

public class CraftingRecipe extends Item {
    String result;
    public List<String> ingredients;

    CraftingRecipe(String name, String description, List<String> ingredients, String result) {
        super(name, description);
        this.result = result;
        this.ingredients = ingredients;
    }
}