package com.pending.game3;

import com.swing.panels.GamePanel;

import java.util.*;

import static com.swing.panels.MapPanel.updateMapGUI;

public class InputParser {
    static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);

//    boolean getInput(Scanner userInput){
//        System.out.print("Enter Command\n> "); // allows input command to be on next line
//        String input = userInput.nextLine().toLowerCase(); // Reads user input
//
//        String[] inputSplit = input.split(" ", 2); // splits array in 2 after 1st space
//        SynonymDictionary command = null;
//        try{
//            command = SynonymDictionary.valueOf(inputSplit[0].toUpperCase()); //command = verb
//        } catch (Exception e){
//            if (SynonymDictionary.GO.synonyms.contains(inputSplit[0])) { //if a synonym of "go" in syn dict use go
//                command = SynonymDictionary.GO;
//                //if a synonym of "inspect" in syn dict use inspect....
//            } else if (SynonymDictionary.INSPECT.synonyms.contains(inputSplit[0])) {
//                command = SynonymDictionary.INSPECT;
//            } else if (SynonymDictionary.TAKE.synonyms.contains(inputSplit[0])) {
//                command = SynonymDictionary.TAKE;
//            } else if (SynonymDictionary.OPEN.synonyms.contains(inputSplit[0])) {
//                command = SynonymDictionary.OPEN;
//            } else if (SynonymDictionary.CRAFT.synonyms.contains(inputSplit[0])) {
//                command = SynonymDictionary.CRAFT;
//            } else if (SynonymDictionary.INTERACT.synonyms.contains(inputSplit[0])) {
//                command = SynonymDictionary.INTERACT;
//            } else if (SynonymDictionary.QUIT.synonyms.contains(inputSplit[0])) {
//                command = SynonymDictionary.QUIT;
//            } else if (SynonymDictionary.REPLAY.synonyms.contains(inputSplit[0])) {
//                command = SynonymDictionary.REPLAY;
//            } else if (SynonymDictionary.INFO.synonyms.contains(inputSplit[0])) {
//                command = SynonymDictionary.INFO;
//            } else if (SynonymDictionary.DROP.synonyms.contains(inputSplit[0])) {
//                command = SynonymDictionary.DROP;
//            } else {
//                System.out.println("command " + input +
//                        " not recognized. enter \"Info\" for a list of valid commands.");
//                return false;
//            }
//        }
//        switch (command){
//            case REPLAY:
//                Game3.runProgram();
//                return true;
//            case GO:
//                HashMap<String,  String> connectionsMap = Game3.getCurrentRoom().getConnections();
//                if (connectionsMap.containsKey(inputSplit[1])) {
//                    goToRoom(connectionsMap.get(inputSplit[1]));
//                } else {
//                    System.out.println("There's nowhere to go that direction.");
//                }
//                break;
//            case INSPECT:
//                if("room".equalsIgnoreCase(inputSplit[1])){
//                System.out.println(Game3.getCurrentRoom().description);
//                } else if (Game3.getCurrentRoom().getItems().contains(inputSplit[1])) {
//                        System.out.println(Game3.getItems().get(inputSplit[1]).description);
//                    } else {
//                        System.out.println("Item does not exist");
//                    }
//                break;
//            case TAKE:
//                    if (Game3.getItems().containsKey(inputSplit[1])) {
//                    Game3.getCurrentRoom().takeItem(inputSplit[1]);
//                    }
//                break;
//            case DROP:
//                if (Game3.getItems().containsKey(inputSplit[1])) {
//                    Game3.getCurrentRoom().dropItem(inputSplit[1]);
//                }
//                break;
//            case QUIT:
//                return true;
//            case INTERACT:
//                interact(inputSplit[1]);
//                break;
//            case INFO:
//                for (SynonymDictionary synDict: SynonymDictionary.values()) {
//                    System.out.println("Command: " + synDict.name() + " valid aliases: " + synDict.synonyms);
//                }
//                break;
//            case CRAFT:
//                crafting(userInput);
//                break;
//            default:
//                System.out.println("Command not yet supported");
//       }
//                return false;
//    }

    public static void getGUIInput(String userInput){
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
                //return false;
            }
        }
        switch (command){
            case REPLAY:
                Game3.runProgram();
                //return true;
            case GO:
                HashMap<String,  String> connectionsMap = Game3.getCurrentRoom().getConnections();
                if (connectionsMap.containsKey(inputSplit[1])) {
                    goToRoom(connectionsMap.get(inputSplit[1]));
                } else {
                    GamePanel.updateOutputTextArea("\nWARNING: There's nowhere to go that direction.");
                }
                break;
            case INSPECT:
                if("room".equalsIgnoreCase(inputSplit[1])){
                    GamePanel.updateOutputTextArea(Game3.getCurrentRoom().description);
                } else if (Game3.getCurrentRoom().getItems().contains(inputSplit[1])) {
                    GamePanel.updateOutputTextArea(Game3.getItems().get(inputSplit[1]).description);
                } else {
                    GamePanel.updateOutputTextArea("\nWARNING: Item \"" + inputSplit[1] + "\" does not exist in the current room");
                }
                break;
            case TAKE:
                if (Game3.getItems().containsKey(inputSplit[1])) {
                    Game3.getCurrentRoom().takeItem(inputSplit[1]);
                    GamePanel.updateOutputTextArea("\nYou grabbed " + inputSplit[1]);
                } else {
                    GamePanel.updateOutputTextArea("\nWARNING: Item \"" + inputSplit[1] + "\" does not exist in the current room.");
                }
                break;
            case DROP:
                if (Game3.getItems().containsKey(inputSplit[1])) {
                    Game3.getCurrentRoom().dropItem(inputSplit[1]);
                    GamePanel.updateOutputTextArea("\nYou dropped " + inputSplit[1]);
                } else {
                    GamePanel.updateOutputTextArea("\nWARNING: Item \"" + inputSplit[1] + "\" does not exist in your inventory.");
                }
                break;
            case QUIT:
                //TODO exit game
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
                crafting();
                break;
            default:
                GamePanel.updateOutputTextArea("\nERROR: \"" + command + "\" Command not yet supported");
        }
    }

    private static void interact(String target) {
        //if the NPC exists and is in this room
        if(Game3.getNpcs().containsKey(target) && Game3.getCurrentRoom().getNpcs().contains(target)) {
            System.out.println("So far so good........");
            Npc npc = Game3.getNpcs().get(target);
            //if the NPC has the "Translator" tag
            if (npc.getFlags().containsKey("Translator")) {
                List<String> translatorFlagData = npc.getFlags().get("Translator");
                //if the player has an item with the translator tag and the translator tag has a matching ID
                for (String itemName : Game3.getInventory().keySet()) {
                    HashMap<String, List<String>> itemFlags = Game3.getItems().get(itemName).getFlags();
                    if (itemFlags.containsKey("Translator")
                            && itemFlags.get("Translator").contains(translatorFlagData.get(0))) {
                        if (npc.getFlags().containsKey("Random")) {
                            System.out.println(npc.getAlternativeDialogue()
                                    .get(random.nextInt(npc.getAlternativeDialogue().size())));
                        } else {
                            System.out.println(npc.getAlternativeDialogue().get(0));
                        }
                        return;
                    }
                }
            }
            if(npc.getFlags().containsKey("Random")){
                System.out.println(npc.getAlternativeDialogue()
                        .get(random.nextInt(npc.getAlternativeDialogue().size())));
            } else {

                System.out.println(npc.dialogue);
            }
        } else{
            System.out.println("They're not in this room!");
        }
    }

//    private void crafting(Scanner userInput) {
//        if(Game3.getCurrentRoom().getFlags().containsKey("Crafting")){
//            List<CraftingRecipe> availableRecipes = new ArrayList<>();
//            for (CraftingRecipe recipe : Game3.getCraftingRecipes()) {
//                for(String item : recipe.ingredients){
//                    if(!Game3.getInventory().keySet().contains(item)){
//                        break;
//                    }
//                }
//                availableRecipes.add(recipe);
//            }
//            while (availableRecipes.size() > 0){
//                System.out.println("Select an item to craft: ");
//                for (int i = 0; i < availableRecipes.size(); i++){
//                    System.out.print("[" + (i + 1) + "]: " + availableRecipes.get(i).result + ": materials: ");
//                    for(String ingredient : availableRecipes.get(i).ingredients){
//                        System.out.print(ingredient + ", ");
//                    }
//                    System.out.println();
//                }
//                String input = userInput.nextLine();
//                try{
//                    int inputIndex = Integer.parseInt(input) - 1;
//                    CraftingRecipe selectedRecipe = availableRecipes.get(inputIndex);
//                    for (String item : selectedRecipe.ingredients){
//                        Game3.getInventory().remove(item);
//                    }
//                    Game3.getInventory().put(selectedRecipe.name, selectedRecipe);
//                    System.out.println("Successfully crafted " + selectedRecipe.result);
//                    break;
//                } catch (Exception e) {
//                    System.out.println("Invalid Input, try again.");
//                }
//            }
//        }
//    }

    private static void crafting() {
        if(Game3.getCurrentRoom().getFlags().containsKey("Crafting")){
            List<CraftingRecipe> availableRecipes = new ArrayList<>();
            for (CraftingRecipe recipe : Game3.getCraftingRecipes()) {
                for(String item : recipe.ingredients){
                    if(!Game3.getInventory().keySet().contains(item)){
                        break;
                    }
                }
                availableRecipes.add(recipe);
            }
            while (availableRecipes.size() > 0){
                System.out.println("Select an item to craft: ");
                for (int i = 0; i < availableRecipes.size(); i++){
                    System.out.print("[" + (i + 1) + "]: " + availableRecipes.get(i).result + ": materials: ");
                    for(String ingredient : availableRecipes.get(i).ingredients){
                        System.out.print(ingredient + ", ");
                    }
                    System.out.println();
                }
                String input = scanner.nextLine();
                try{
                    int inputIndex = Integer.parseInt(input) - 1;
                    CraftingRecipe selectedRecipe = availableRecipes.get(inputIndex);
                    for (String item : selectedRecipe.ingredients){
                        Game3.getInventory().remove(item);
                    }
                    Game3.getInventory().put(selectedRecipe.name, selectedRecipe);
                    System.out.println("Successfully crafted " + selectedRecipe.result);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid Input, try again.");
                }
            }
        }
    }

    private static void goToRoom(String destination) {
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
                        GamePanel.updateOutputTextArea("The door is locked.");
                    }
                }else{
                    GamePanel.updateOutputTextArea("Locked flag on Room " + destination +
                            " has no data to identify the correct key with.");
                }
                //then remove "Locked" flag, move to new room, and return true;
            } else { //move to destination room and confirm move completed.
                Game3.setCurrentRoom(destinationRoom);
                Game3.displayConsoleGUI();
                updateMapGUI();
            }
        } else {
            GamePanel.updateOutputTextArea("Room " + destination + " does not exist.");
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