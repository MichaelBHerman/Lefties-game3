package com.pending.game3;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Item {
    final String description;
    final String name;
    private HashMap<String, List<String>> flags;

    Item(String name, String description) {
        this.name = name.toLowerCase();
        this.description = description;
    }

    Item(String name, String description, HashMap<String, List<String>> flags) {
        this.description = description;
        this.flags = flags;
        this.name = name.toLowerCase();
    }

    HashMap<String, List<String>> getFlags() {
        return new HashMap<>(flags);
    }

    HashMap<String, List<String>> editFlags() {
        return flags;
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", flags=" + flags +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}