package com.pending.game3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Room {
    private HashMap<String, List<String>> flags;
    private List<String> items;
    private List<String> npcs;
    private HashMap<String, String> connections;
    final String description;
    final String name;
    final String displayName;

    Room(HashMap<String, List<String>> flags, List<String> items, List<String> npcs,
         HashMap<String, String> connections, String description, String name, String displayName){

        this.flags = flags;
        this.items = items;
        this.npcs = npcs;
        this.connections = connections;
        this.description = description;
        this.name = name;
        this.displayName = displayName;
    }

    HashMap<String, List<String>> getFlags() {
        return new HashMap<>(flags);
    }

    HashMap<String, List<String>> editFlags() {
        return flags;
    }

    HashMap<String, String> getConnections() {
        return new HashMap<>(connections);
    }

    HashMap<String, String> editConnections() {
        return connections;
    }

    List<String> getItems(){
        return new ArrayList<>(items);
    }

    List<String> editItems(){
        return items;
    }

    List<String> getNpcs(){
        return new ArrayList<>(npcs);
    }

    List<String> editNpcs(){
        return npcs;
    }


    void takeItem(String itemToTake) {
        if (items.contains(itemToTake)) {
            Game3.getInventory().add(itemToTake);
            items.remove(itemToTake);
        }
    }

    void dropItem(String itemToDrop) {
        if (Game3.getInventory().contains(itemToDrop)) {
            Game3.getInventory().remove(itemToDrop);
            items.add(itemToDrop);
        }
    }

    void look(){
    }

    void enter(){
    }

    void craft(){
    }

}