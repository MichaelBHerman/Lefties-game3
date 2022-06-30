package com.pending.game3;

import com.pending.game3.sound.GameMusic;
import com.pending.game3.sound.ItemSound;
import com.swing.MyFrame;
import com.swing.panels.CraftingPanel;
import com.swing.panels.GamePanel;
import com.swing.panels.InventoryPanel;
import com.swing.panels.RoomItemsPanel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
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
    private Scanner reader;
    private final GameMusic gameMusic = new GameMusic();
    // Temporary method that only runs the UI
    public static void runGUI() {
        MyFrame frame = new MyFrame();
    }

    //singleton
    private static Game3 instance;

    // logic for running a new instance of a game
    public static void runProgram() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (instance == null) {
            instance = new Game3();
            instance.inputParser = new InputParser();
            instance.reader = new Scanner(System.in);
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
    private void run() {
        //if(mainMenu()) return;
        String userChoice = JOptionPane.showInputDialog(mainSplash + "\n[1]: Start new game\n[4]: quit program");
        try (Stream<Path> stream = Files.list(Path.of(jsonDir))) {
            List<Path> files = getJsonList(stream);
            if (promptUserForFileGUI( files)) return;
        } catch (Exception e) {
            System.out.println("Unable to locate resources\\json folder.");
            return;
        }
        inventory = fileParser.startingInventory;
        rooms = fileParser.roomsAtStart;
        items = fileParser.itemsAtStart;
        currentRoom = rooms.get(fileParser.startingRoom);
        npcs = fileParser.npcsAtStart;
        for (String line : fileParser.splashText){
            System.out.println(line);
        }
        // mainLoop();
        gameMusic.playMusic();
        displayConsoleGUI();
        displayRoomGUI();
    }

    private static boolean checkEndCondition() {
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
                    System.out.print("YOU WIN! ");
                } else {
                    System.out.print("You Lost... ");
                }
                //TODO: add EndCondition text here.
                System.out.println();
                return true;
            }
        }
        return false;
    }

    public static void displayRoomGUI() {
        checkEndCondition();
        InventoryPanel.updateInventoryGUI(getInventory());
        RoomItemsPanel.renderRoomItems(getCurrentRoom().getItems());
        CraftingPanel.renderRecipeItems();
    }

    static void displayConsoleGUI() {
        checkEndCondition();
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

    private boolean promptUserForFileGUI(List<Path> files) {

        while (true){
            //printFiles(files);
            String userInput = JOptionPane.showInputDialog("Enter a number to select a json file to load: " + printFilesGUI(files));
            if ("quit".equals(userInput.toLowerCase())) return true;
            try{
                int inputIndex = Integer.parseInt(userInput) - 1;
                fileParser = FileParser.loadFile(files.get(inputIndex));
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
          options.append("\n[").append(1 + i).append("]: ").append(files.get(i).getFileName());
        }
        return options.toString();
    }
    // gets JSON files from the list
    private List<Path> getJsonList(Stream<Path> stream) {
        return stream.filter(file -> (!Files.isDirectory(file))
                && file.getFileName().toString().contains(".json"))
                .collect(Collectors.toList());
    }

    // method to run the main menu
//    private boolean mainMenu() {
//        System.out.println("[1]: Start new game\n[4]: quit program");
//        while (true){
//            String input = reader.nextLine();
//            switch (input){
//                case "1":
//                    return false;
//                case "4":
//                    return true;
//            }
//            System.out.println("Invalid input, please enter the number for your menu selection.");
//
//        }
//    }

    //method that handles the game logic of taking in a command and displaying a room
//    private void mainLoop() {
//        while (true) {
//            displayRoom();
//            displayRoomGUI();
//            if(inputParser.getInput(reader)) {
//                break;
//            }
//            if(checkEndCondition()) {
//                break;
//            }
//        }
//    }

    // method that show the user the current room, items in the room, and their inventory
//    private void displayRoom() {
//        System.out.println("--------------");
//        System.out.println(getCurrentRoom().name);
//        System.out.println("--------------");
//        System.out.println(getCurrentRoom().description);
//        System.out.println("--------------");
//        System.out.println("Items: " + getCurrentRoom().getItems());
//        System.out.println("--------------");
//        System.out.println("Inventory: " + getInventory());
//        System.out.println("--------------");
//        System.out.println("NPCs: " + getCurrentRoom().getNpcs());
//        System.out.println("--------------");
//        System.out.print("Movement options: ");
//        for(String direction : currentRoom.getConnections().keySet()) {
//            System.out.print("\"" + direction + " -> " +  currentRoom.getConnections().get(direction) + "\"");
//        }
//        System.out.println();
//    }

    // method to prompt user to load JSON file
//    private boolean promptUserForFile(Scanner reader, List<Path> files) {
//
//        while (true){
//            printFiles(files);
//            System.out.print("Enter a number to select a json file to load: ");
//            String input = reader.nextLine();
//            if ("quit".equals(input.toLowerCase())) return true;
//            try{
//                int inputIndex = Integer.parseInt(input) - 1;
//                fileParser = FileParser.loadFile(files.get(inputIndex));
//                if(fileParser == null) return true;
//                else return false;
//            } catch (Exception e) {
//                System.out.println(e);
//                System.out.println("Invalid input, please try again.");
//            }
//        }
//    }

    // displays file names
//    private void printFiles(List<Path> files) {
//        for (int i = 0; i < files.size(); i++){
//            System.out.println("[" + (1 + i) + "]: " + files.get(i).getFileName());
//        }
//    }
}