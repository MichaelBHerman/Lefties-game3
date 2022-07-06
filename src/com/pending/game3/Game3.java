package com.pending.game3;

import com.pending.game3.sound.GameMusic;
import com.pending.game3.sound.ItemSound;
import com.swing.MyFrame;
import com.swing.panels.*;
import org.json.simple.JSONValue;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game3 {
    public static final String jsonDir = "resources/json";
    public static final String mainSplash = "So, you want to play an adventure game?\n" +
            "Too bad, this is just an engine for adventure games.\n" +
            "After selecting New Game, you will be prompted to choose a .json file to load the game from.\n" +
            "Yes, you read that correctly: This engine can read in the entire game from a .json file, so you and \n" +
            "your friends can modify or even create new game worlds to play in this engine.\n" +
            "The sky is the limit!\n\n" +
            "Or is it?";
    private static FileParser fileParser;
    private InputParser inputParser;
    private static HashMap<String, Item> inventory;
    private static Room currentRoom;
    private HashMap<String, Room> rooms;
    private HashMap<String, Item> items;
    private HashMap<String, Npc> npcs;
    private final GameMusic gameMusic = new GameMusic();
    public static MyFrame frame;

    //singleton
    private static Game3 instance;

    // logic for running a new instance of a game
    public static void runProgram() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (instance == null) {
            instance = new Game3();
        }
        instance.run();
    }

    private Game3() throws UnsupportedAudioFileException, LineUnavailableException, IOException {}
    //end singleton


    static void setCurrentRoom(Room newCurrentRoom){
        instance.currentRoom = newCurrentRoom;
    }

    static void setRooms(HashMap<String, Room> newRooms){
        instance.rooms = newRooms;
    }

    static void setItems(HashMap<String, Item> newItems){
        instance.items = newItems;
    }

    static void setNpcs(HashMap<String, Npc> newNpcs){
        instance.npcs = newNpcs;
    }

    static HashMap<String, Item> getInventory(){
        return instance.inventory;
    }

    public static Room getCurrentRoom(){
        return instance.currentRoom;
    }

    static HashMap<String, Room> getRooms(){
        return instance.rooms;
    }

    static HashMap<String, Item> getItems(){
        return instance.items;
    }

    static HashMap<String, Npc> getNpcs(){
        return instance.npcs;
    }

    public static List<CraftingRecipe> getCraftingRecipes() {
        return instance.fileParser.recipes;
    }

    //method for starting the game
    private void run() throws FileNotFoundException {
        frame = new MyFrame();
        //if(mainMenu()) return;
        String userChoice = JOptionPane.showInputDialog(mainSplash + "\n[1]: Start new game\n[4]: quit program");
        if(userChoice.equalsIgnoreCase("1")) {
            FileParser fileParser1 = new FileParser();
            fileParser =fileParser1.loadFile();
            inventory = fileParser.startingInventory;
            rooms = fileParser.roomsAtStart;
            items = fileParser.itemsAtStart;
            currentRoom = rooms.get(fileParser.startingRoom);
            npcs = fileParser.npcsAtStart;
            for (String line : fileParser.splashText){
                System.out.println(line);
            }
            try {
                gameMusic.playMusic();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            displayConsoleGUI();
            displayRoomGUI();
        } else {
            System.exit(1);
        }
    }

    static void checkEndCondition() {
        for(EndCondition ec : fileParser.endConditions) {
            //if in the correct room, true
            boolean roomCheck = false;
            if(ec.roomReq == null || currentRoom.name.equalsIgnoreCase(ec.roomReq)){
                roomCheck = true;
            }
            //if any NPC doesn't match required tags, false
            boolean npcsCheck = true;
            if(ec.npcReq != null) {
                for (Npc npc : ec.npcReq) {
                    for (String tagName : npc.getFlags().keySet()) {
                        if (!npc.getFlags().containsKey(tagName)) {
                            npcsCheck = false;
                            break;
                        }
                    }
                }
            }
            //if any item not in inventory, false
            boolean itemsCheck = true;
            if(ec.itemReq != null) {
                for (String item : ec.itemReq) {
                    if (!inventory.containsKey(item)) {
                        itemsCheck = false;
                    }
                }
            }
            if (roomCheck && npcsCheck && itemsCheck){
                if(ec.win){
                    GamePanel.updateOutputTextArea("\nYou convinced the Aliens to take you home! YOU WIN! ");
                } else {
                    GamePanel.updateOutputTextArea("\nYou Lost... ");
                }
            }
        }
    }

    public static void displayRoomGUI() {
        InventoryPanel.updateInventoryGUI(getInventory());
        RoomItemsPanel.renderRoomItems(getCurrentRoom().getItems());
        NPCPanel.renderRoomNPC(getCurrentRoom().getNpcs());
        CraftingPanel.renderRecipeItems();
    }

    static void displayConsoleGUI() {
        GamePanel.clearOutputTextArea();
        GamePanel.updateOutputTextArea("\nCURRENT ROOM: " + getCurrentRoom().name);
        GamePanel.updateOutputTextArea("\nDESCRIPTION: " + getCurrentRoom().description);
        GamePanel.updateOutputTextArea("\nITEMS: " + getCurrentRoom().getItems().keySet());
        GamePanel.updateOutputTextArea("\nNPCs: " + getCurrentRoom().getNpcs());
        GamePanel.updateOutputTextArea("\nMOVEMENT OPTIONS: ");
        for(String direction : currentRoom.getConnections().keySet()) {
            GamePanel.updateOutputTextArea("\"" + direction + "\" ");
        }
        GamePanel.updateOutputTextArea("\n-----");
    }

//    private boolean promptUserForFileGUI(List<Path> files) {
        private boolean promptUserForFileGUI() {

        while (true){
            //printFiles(files);
//            String userInput = JOptionPane.showInputDialog("Enter a number to select a json file to load: " + printFilesGUI(files));
            String userInput = JOptionPane.showInputDialog("Enter a number to select a json file to load: [1] test1.json");
//            String userInput = JOptionPane.showInputDialog("Enter a number to select a json file to load: " + printFilesGUI(files));
            if ("quit".equals(userInput.toLowerCase())) return true;
            try{
                int inputIndex = Integer.parseInt(userInput) - 1;
//                fileParser = FileParser.loadFile();


//                Object obj = JSONValue.parse(new FileReader(String.valueOf(FileParser.class.getResourceAsStream("resources/json/test1.json"))));
                if(fileParser == null) return true;
                else return false;
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Invalid input, please try again.");
            }
        }
    }

    private String printFilesGUI(List<Path> files) {
        StringBuilder options = new StringBuilder();
        for (int i = 0; i < files.size(); i++){
//            options.append("\n[").append(1 + i).append("]: ").append(files.get(i).getFileName());
            options.append("\n[").append(1 + i).append("]: ").append(FileParser.class.getResourceAsStream("/resources/test1.json"));
        }
        return options.toString();

    }
    // gets JSON files from the list
    private List<Path> getJsonList(Stream<Path> stream) {
        return stream.filter(file -> (!Files.isDirectory(file))
                && file.getFileName().toString().contains(".json"))
                .collect(Collectors.toList());
    }

}