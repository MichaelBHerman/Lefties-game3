package com.pending.game3;

import com.pending.game3.sound.ItemSound;
import com.pending.game3.sound.TalkSound;
import com.pending.game3.sound.WalkSound;
import com.swing.panels.GamePanel;
import com.swing.panels.InventoryPanel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import static com.swing.panels.MapPanel.updateMapGUI;

public class InputParser {
    private static ArrayList<String> selectedItemsList = new ArrayList<>();
    static Random random = new Random();
    private static ItemSound itemSound;
    private static WalkSound walkSound;
    private static TalkSound talkSound;

    static {
        try {
            itemSound = new ItemSound();
            walkSound = new WalkSound();
            talkSound = new TalkSound();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public InputParser() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    }

    public static void getGUIInput(String userInput) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //System.out.print("Enter Command\n> "); // allows input command to be on next line
        String input = userInput.toLowerCase(); // Reads user input

        String[] inputSplit = input.split(" ", 2); // splits array in 2 after 1st space
        SynonymDictionary command = null;
        try{
            command = SynonymDictionary.valueOf(inputSplit[0].toUpperCase()); //command = verb
        } catch (Exception e){
            if (SynonymDictionary.GO.synonyms.contains(inputSplit[0])) { //if a synonym of "go" in syn dict use go
                command = SynonymDictionary.GO;
                //if a synonym of "inspect" in syn dict use inspect....
            } else if (SynonymDictionary.INSPECT.synonyms.contains(inputSplit[0])) {
                command = SynonymDictionary.INSPECT;
            } else if (SynonymDictionary.TAKE.synonyms.contains(inputSplit[0])) {
                command = SynonymDictionary.TAKE;
            } else if (SynonymDictionary.OPEN.synonyms.contains(inputSplit[0])) {
                command = SynonymDictionary.OPEN;
            } else if (SynonymDictionary.CRAFT.synonyms.contains(inputSplit[0])) {
                command = SynonymDictionary.CRAFT;
            } else if (SynonymDictionary.INTERACT.synonyms.contains(inputSplit[0])) {
                command = SynonymDictionary.INTERACT;
            } else if (SynonymDictionary.QUIT.synonyms.contains(inputSplit[0])) {
                command = SynonymDictionary.QUIT;
            } else if (SynonymDictionary.REPLAY.synonyms.contains(inputSplit[0])) {
                command = SynonymDictionary.REPLAY;
            } else if (SynonymDictionary.INFO.synonyms.contains(inputSplit[0])) {
                command = SynonymDictionary.INFO;
            } else if (SynonymDictionary.DROP.synonyms.contains(inputSplit[0])) {
                command = SynonymDictionary.DROP;
            } else {
                GamePanel.updateOutputTextArea("\nERROR: command \"" + input +
                        "\" not recognized. enter \"Info\" for a list of valid commands.");
            }
        }
        switch (command){
            case REPLAY:
                Game3.runProgram();
            case GO:
                HashMap<String,  String> connectionsMap = Game3.getCurrentRoom().getConnections();
                if (connectionsMap.containsKey(inputSplit[1])) {
                    goToRoom(connectionsMap.get(inputSplit[1]));
                    Game3.displayRoomGUI();
                } else {
                    GamePanel.updateOutputTextArea("\nWARNING: There's nowhere to go that direction.");
                }
                break;
            case INSPECT:
                if("room".equalsIgnoreCase(inputSplit[1])){
                    GamePanel.updateOutputTextArea(Game3.getCurrentRoom().description);
                } else if (Game3.getCurrentRoom().getItems().containsKey(inputSplit[1])) {
                    GamePanel.updateOutputTextArea(Game3.getItems().get(inputSplit[1]).description);
                } else {
                    GamePanel.updateOutputTextArea("\nWARNING: Item \"" + inputSplit[1] + "\" does not exist in the current room");
                }
                break;
            case TAKE:
                if (Game3.getItems().containsKey(inputSplit[1])) {
                    Game3.getCurrentRoom().takeItem(inputSplit[1]);
                    itemSound.playSound();
                    Game3.displayRoomGUI();
                    Game3.displayConsoleGUI();
                } else {
                    GamePanel.updateOutputTextArea("\nWARNING: Item \"" + inputSplit[1] + "\" does not exist in the current room.");
                }
                break;
            case DROP:
                if (Game3.getItems().containsKey(inputSplit[1])) {
                    Game3.getCurrentRoom().dropItem(Game3.getItems().get(inputSplit[1]));
                    Game3.displayRoomGUI();
                    Game3.displayConsoleGUI();
                } else {
                    GamePanel.updateOutputTextArea("\nWARNING: Item \"" + inputSplit[1] + "\" does not exist in your inventory.");
                }
                break;
            case QUIT:
                System.exit(1);
                break;
            case INTERACT:
                interact(inputSplit[1]);
                break;
            case INFO:
                for (SynonymDictionary synDict: SynonymDictionary.values()) {
                    GamePanel.updateOutputTextArea("\nCommand: " + synDict.name() + " valid aliases: " + synDict.synonyms);
                }
                break;
            case CRAFT:
                confirmSelected(InventoryPanel.getSelectedList());
                craftingGUI(selectedItemsList);
                break;
            default:
                GamePanel.updateOutputTextArea("\nERROR: \"" + command + "\" Command not yet supported");
        }
        Game3.checkEndCondition();
    }

    // Allows the user to craft items using the GUI command line instead of the GUI buttons.
    public static void confirmSelected(ArrayList<JRadioButton> selectItems) {
        for (JRadioButton btn: selectItems) {
            selectedItemsList.add(btn.getActionCommand());
        }
    }

    private static void interact(String target) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //if the NPC exists and is in this room
        if(Game3.getNpcs().containsKey(target) && Game3.getCurrentRoom().getNpcs().contains(target)) {
            Npc npc = Game3.getNpcs().get(target);
            talkSound.playSound();
            //if the NPC has the "Translator" tag
            if (npc.getFlags().containsKey("Random") && npc.getFlags().get("Random").contains("Translator")) {
                List<String> translatorFlagData = npc.getFlags().get("Random");
                //if the player has an item with the translator tag and the translator tag has a matching ID
                for (String itemName : Game3.getInventory().keySet()) {
                    HashMap<String, List<String>> itemFlags = Game3.getItems().get(itemName).getFlags();
                    if (itemFlags.containsKey("Translator")
                            && itemFlags.get("Translator").contains(translatorFlagData.get(0))) {
                        if (npc.getFlags().containsKey("Random")) {
                            GamePanel.updateOutputTextArea("\n" + npc.name + ": " + npc.getAlternativeDialogue()
                                    .get(random.nextInt(npc.getAlternativeDialogue().size())));
                        } else {
                            GamePanel.updateOutputTextArea("\n" + npc.name + ": " + npc.getAlternativeDialogue().get(0));
                        }
                        return;
                    }
                }
            }
            if(npc.getFlags().containsValue("Random")){
                GamePanel.updateOutputTextArea("\n" + npc.name + ": " + npc.getAlternativeDialogue()
                        .get(random.nextInt(npc.getAlternativeDialogue().size())));
            } else {
                GamePanel.updateOutputTextArea("\n" + npc.name + ": " + npc.dialogue);

            }
        } else{
            GamePanel.updateOutputTextArea("\nWARNING: \"" + target + "\" is not in this room!");
        }
    }

    private static void craftingGUI(ArrayList<String> selectedList) {
        Collections.sort(selectedList);
        if(Game3.getCurrentRoom().getFlags().containsKey("Crafting")){
            for (CraftingRecipe recipe : Game3.getCraftingRecipes()) {
                Collections.sort(recipe.ingredients);
                if(recipe.ingredients.equals(selectedList)) {
                    for (String item : recipe.ingredients){
                        Game3.getInventory().remove(item);
                    }
                    Item translator = Game3.getItems().get(recipe.result);
                    Game3.getInventory().put(translator.name, translator);
                    GamePanel.updateOutputTextArea("\nSuccessfully crafted " + recipe.result);
                    } else {
                        GamePanel.updateOutputTextArea("\nWARNING: You have not selected the required items to craft anything.");
                    }
                }
            } else {
                GamePanel.updateOutputTextArea("\nWARNING: You are not in a room that has crafting capabilities.");
        }
        selectedList.clear();
    }

    private static void goToRoom(String destination) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (Game3.getRooms().containsKey(destination)) {
            Room destinationRoom = Game3.getRooms().get(destination);
            if (destinationRoom.getFlags().containsKey("Locked")) {
                //get the lock and check inventory for matching key
                List<String> keyData = destinationRoom.getFlags().get("Locked");
                if (keyData.size() > 0){
                    if(keyCheck(keyData.get(0))){
                        destinationRoom.editFlags().remove("Locked");
                        Game3.setCurrentRoom(destinationRoom);
                        Game3.displayConsoleGUI();
                        updateMapGUI();
                    } else {
                        GamePanel.updateOutputTextArea("\nWARNING:The door is locked.");
                    }
                }else{
                    GamePanel.updateOutputTextArea("Locked flag on Room " + destination +
                            " has no data to identify the correct key with.");
                }
                //then remove "Locked" flag, move to new room, and return true;
            } else { //move to destination room and confirm move completed.
                walkSound.playSound();
                Game3.setCurrentRoom(destinationRoom);
                Game3.displayConsoleGUI();
                updateMapGUI();
            }
        } else {
            GamePanel.updateOutputTextArea("\nRoom " + destination + " does not exist.");
        }
    }

    private static boolean keyCheck(String keyId) {
        for (String itemName : Game3.getInventory().keySet()){
            if(Game3.getItems().containsKey(itemName)){
                Item item = Game3.getItems().get(itemName);
                HashMap<String, List<String>> itemFlags = item.getFlags();
                if(itemFlags.containsKey("Key") && itemFlags.get("Key").contains(keyId)){
                    return true;
                }
            }
        }
        return false;
    }

}