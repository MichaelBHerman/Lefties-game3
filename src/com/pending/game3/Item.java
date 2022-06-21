package com.pending.game3;

import java.util.HashMap;
import java.util.List;

class Item {
    final String description;
    final String name;
    private HashMap<String, List<String>> flags;

    Item(String name, String description, HashMap<String, List<String>> flags) {
        this.description = description;
        this.flags = flags;
        this.name = name;
    }

    HashMap<String, List<String>> getFlags() {
        return new HashMap<>(flags);
    }

    HashMap<String, List<String>> editFlags() {
        return flags;
    }
}