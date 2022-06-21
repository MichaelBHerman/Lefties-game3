package com.pending.game3;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class FileParser {
        String startingRoom;
        List<String> startingInventory;
        HashMap<String, Room> roomsAtStart;
        HashMap<String, Item> itemsAtStart;
        HashMap<String, Npc> npcsAtStart;
        List<CraftingRecipe> recipes;
        List<EndCondition> endConditions;
        List<String> splashText;
        private static JSONObject jsonObject;

        //ctor
        private FileParser(){
        }

        static FileParser loadFile(Path test1) throws FileNotFoundException {
                FileParser toReturn = new FileParser();
                // created parser
                Object obj = JSONValue.parse(new FileReader(String.valueOf(test1)));
                if(obj == null) {
                        System.out.println("Selected file does not contain valid JSON data.");
                        return null;
                }
                if (JSONObject.class == obj.getClass()) {
                        jsonObject = (JSONObject) obj;
                        if (toReturn.parseSplashText()) return null;
                        if (toReturn.parseItems()) return null;
                        if (toReturn.parseNpcs()) return null;
                        if (toReturn.parseRooms()) return null;
                        if (toReturn.parseCraftingRecipes()) return null;
                        if (toReturn.parseEndCondition()) return null;
                        if (toReturn.parseStartingRoom()) return null;
                        if (toReturn.parseStartingInventory()) return null;
                        if (toReturn.parseSynonyms()) return null;

                } else{
                        System.out.println("Base entity in selected file is not a JSON Object.");
                        return null;
                }


//
//
//
//
//
                return toReturn;
        }

        private boolean parseSplashText() {
                if (!jsonObject.containsKey("Splash Text")){
                        System.out.println("Key value \"Splash Text\" does not exist in base JSON Object.");
                        return true;
                }
                splashText = parseStringList(jsonObject.get("Splash Text"));
                if (splashText == null){
                        System.out.println("Splash Text.");
                        return true;
                }
                return false;
        }

        private boolean parseStartingInventory() {
                if (!jsonObject.containsKey("Starting Inventory")){
                        System.out.println("Key value \"Starting Inventory\" does not exist in base JSON Object.");
                        return true;
                }
                startingInventory = parseStringList(jsonObject.get("Starting Inventory"));
                if (startingInventory == null){
                        System.out.println("Starting Inventory.");
                        return true;
                }
                for (String item : startingInventory){
                        if(itemsAtStart.containsKey(item)) continue;
                        System.out.println("Starting Inventory item " + item + " does not exist in Items.");
                        return true;
                }
                return false;
        }

        private boolean parseStartingRoom() {
                if (!jsonObject.containsKey("Starting Room")){
                        System.out.println("Key value \"Starting Room\" does not exist in base JSON Object.");
                        return true;
                }
                startingRoom = parseString(jsonObject.get("Starting Room"));
                if (startingRoom == null){
                        System.out.println("Starting Room.");
                        return true;
                }
                startingRoom = startingRoom.toLowerCase();
                if(roomsAtStart.containsKey(startingRoom)) return false;
                System.out.println("Starting Room " + startingRoom + " does not exist in Rooms.");
                return true;
        }

        private boolean parseSynonyms() {
                if (!jsonObject.containsKey("Synonym Dictionary")){
                        System.out.println("Key value \"Synonym Dictionary\" does not exist in base JSON Object.");
                        return true;
                }
                Object obj = jsonObject.get("Synonym Dictionary");
                if (obj == null || !JSONObject.class.equals(obj.getClass())){
                        System.out.println("Synonym Dictionary must be a JSON object");
                        return true;
                }
                JSONObject dictionaryJson = (JSONObject) obj;
                for (SynonymDictionary dict : SynonymDictionary.values()){
                        String key = dict.name().toLowerCase();
                        char[] chars = key.toCharArray();
                        chars[0] = key.toUpperCase().charAt(0);
                        key = String.valueOf(chars);
                        if (!dictionaryJson.containsKey(key)){
                                System.out.println("Synonyms for \"" + key + "\" are not available.");
                                continue;
                        }
                        dict.synonyms = parseStringList(dictionaryJson.get(key));
                        if (dict.synonyms == null){
                                System.out.println("Synonym Dictionary key " + key + ".");
                                return true;
                        }
                        for (int i = 0; i < dict.synonyms.size(); i++){
                                dict.synonyms.set(i, dict.synonyms.get(i).toLowerCase());
                        }
                }
                return false;
        }

        private boolean parseRooms() {
                roomsAtStart = new HashMap<>();
                if (!jsonObject.containsKey("Rooms")){
                        System.out.println("Key value \"Rooms\" does not exist in base JSON Object.");
                        return true;
                }
                Object obj = jsonObject.get("Rooms");

                if (obj != null && JSONArray.class.equals(obj.getClass())) {
                        JSONArray jsonRooms = (JSONArray) obj;
                        if(jsonRooms.size() == 0){
                                System.out.println("Rooms array must not be empty.");
                                return true;
                        }
                        for (Object roomObj : jsonRooms) {
                                //programmer.tryNotToCry();
                                //programmer.cryALot();
                                if (roomObj == null || !JSONObject.class.equals(roomObj.getClass()))
                                {
                                        System.out.println("All entries in Rooms must be JSON Objects");
                                        return true;
                                }
                                JSONObject roomJsonObj = (JSONObject) roomObj;
                                if (!roomJsonObj.containsKey("Name")){
                                        System.out.println("Key value \"Name\" does not exist in Room JSON Object.");
                                        return true;
                                }String name = parseString(roomJsonObj.get("Name"));
                                if (name == null) {
                                        System.out.println("Room Name.");
                                        return true;
                                }
                                if (name.equals("")){
                                        System.out.println("Room name cannot be an empty string.");
                                        return true;
                                }
                                if (!roomJsonObj.containsKey("Display Name")){
                                        System.out.println("Key value \"Display Name\" does not exist in Room " +
                                                name + " JSON Object.");
                                        return true;
                                }
                                String displayName = parseString(roomJsonObj.get("Display Name"));
                                if (displayName == null){
                                        System.out.println("Room " + name + " Display Name.");
                                        return true;
                                }

                                //parse flags
                                HashMap<String, List<String>> flags;
                                if (roomJsonObj.keySet().contains("Effect Tags")) {
                                        obj = roomJsonObj.get("Effect Tags");
                                        if (JSONArray.class.equals(obj.getClass())) {
                                                JSONArray flagsJson = (JSONArray) obj;
                                                flags = parseFlags(flagsJson);
                                                if (flags == null) {
                                                        System.out.println(name + " Effect Tags.");
                                                        return true;
                                                }
                                        } else {
                                                System.out.println("Effect Tags in room " + name + " must be a JSON" +
                                                        " Array.");
                                                return true;
                                        }
                                } else flags = new HashMap<>();

                                //parse items
                                List<String> items = new ArrayList<>();
                                if (roomJsonObj.keySet().contains("Items")) {
                                        List<String> items2 = parseStringList(roomJsonObj.get("Items"));
                                        if (items2 == null) {
                                                System.out.println("Room " + name + " Items.");
                                                return true;
                                        }
                                        for (int i = 0; i < items2.size(); i++){
                                                items.add(items2.get(i).toLowerCase());
                                        }
                                } else items = new ArrayList<>();
                                for(String item : items){
                                        if(!itemsAtStart.containsKey(item)){
                                                System.out.println("Item " + item + " in room " + name + " is not " +
                                                        "defined in Items.");
                                                return true;
                                        }
                                }

                                //parse connected rooms
                                HashMap<String, String> connections;
                                if(!roomJsonObj.containsKey("Connected Rooms")){
                                        System.out.println("Key Value \"Connected Rooms\" not found in Room " + name
                                        + ".");
                                        return true;
                                }
                                obj = roomJsonObj.get("Connected Rooms");


                                connections = new HashMap<>();
                                if(obj != null && JSONArray.class.equals(obj.getClass())){
                                        JSONArray connectionArray = (JSONArray)obj;
                                        if (connectionArray.size() == 0){
                                                System.out.println("Connections array at " + name + " cannot be " +
                                                        "empty.");
                                                return true;
                                        }
                                        for (Object derp : connectionArray) {
                                                if (derp != null && JSONObject.class.equals(derp.getClass())){
                                                        JSONObject connection = (JSONObject)derp;
                                                        if(connection.keySet().size() == 1){
                                                                obj = connection.keySet().toArray()[0];
                                                                String direction = parseString(obj);
                                                                if (direction == null){
                                                                        System.out.println("Room " + name +
                                                                                " Connection Direction.");
                                                                        return true;
                                                                }
                                                                obj = connection.get(direction);
                                                                String destination = parseString(obj);
                                                                if (destination == null) {
                                                                        System.out.println("Room " + name +
                                                                                " Connection Destination.");
                                                                return true;
                                                                }
                                                                connections.put(direction.toLowerCase(),
                                                                        destination.toLowerCase());
                                                        } else {
                                                                System.out.println("Invalid Room " + name +
                                                                        " Connection Object " +
                                                                        "Size, each direction:destination pair must " +
                                                                        "be a separate object and cannot be empty.");
                                                                return true;
                                                        }
                                                } else {
                                                        System.out.println("Invalid Data Type when Object was " +
                                                                "expected at Room " + name + " Connected Rooms.");
                                                        return true;
                                                }
                                        }
                                } else {
                                        System.out.println("Connected Rooms in room " + name + " must be a JSON array");
                                        return true;
                                }
                                if(!roomJsonObj.containsKey("Description")){
                                        System.out.println("Key Value \"Description\" not found in Room " + name
                                                + ".");
                                        return true;
                                }
                                String description = parseString(roomJsonObj.get("Description"));
                                if (description == null) {
                                        System.out.println("Room " + name + " Description.");
                                        return true;
                                }
                                List<String> npcs ;
                                if(roomJsonObj.containsKey("Npcs")) {
                                        npcs = parseStringList(roomJsonObj.get("Npcs"));
                                        if (npcs == null) {
                                                System.out.println("Room " + name + " Npcs.");
                                                return true;
                                        }
                                        for (String npc : npcs){
                                                if (!npcsAtStart.containsKey(npc)){
                                                        System.out.println(npc + " in room " + name + " Npcs is not " +
                                                                "defined in NPCs.");
                                                        return true;
                                                }
                                        }
                                }else npcs = new ArrayList<>();

                                Room room = new Room(flags, items, npcs, connections, description, name, displayName);
                                roomsAtStart.put(room.name.toLowerCase(), room);

                        }
                        for (String roomKey : roomsAtStart.keySet()){
                                Room room = roomsAtStart.get(roomKey);
                                for(String direction : room.getConnections().keySet()){
                                        String destination = room.getConnections().get(direction);
                                        if(!roomsAtStart.containsKey(destination)){
                                                System.out.println("Room " + destination + " in connections in room " +
                                                        roomKey + " does not exist.");
                                                return true;
                                        }
                                }
                        }
                }
                else{
                        System.out.println("Rooms must be a JSON array.");
                        return true;
                }
                return false;
        }

        private String parseString(Object obj) {

                if (obj == null) {
                        System.out.print("Null when String was expected at: ");
                        return null;
                }
                if (String.class.equals(obj.getClass())){
                        return (String)obj;
                } else {
                        System.out.print("Invalid Data Type when String was expected at: ");
                        return null;
                }
        }

        private HashMap<String, List<String>> parseFlags(JSONArray flagsJson) {
                HashMap<String, List<String>> toReturn = new HashMap<>();
                if (flagsJson == null) {
                        System.out.print("Null when Flags were expected at: ");
                        return null;
                }
                for (Object loopObj : flagsJson) {
                        if (loopObj != null && JSONObject.class.equals(loopObj.getClass())){
                                JSONObject flagJson = (JSONObject) loopObj;
                                if(!flagJson.containsKey("Tag Name")){
                                        System.out.println("Key Value \"Tag Name\" not found in Effects Tag in ");
                                }
                                String name = parseString(flagJson.get("Tag Name"));
                                if (name == null) {
                                        System.out.print("Tag Name in: ");
                                        return null;
                                }
                                if(!flagJson.containsKey("Tag Data")){
                                        System.out.println("Key Value \"Tag Data\" not found in Effects Tag in ");
                                }
                                List<String> dataList = parseStringList(flagJson.get("Tag Data"));
                                if(dataList == null) {
                                        System.out.print("Tag Data in: ");
                                        return null;
                                }
                                toReturn.put(name, dataList);

                        } else {
                                System.out.print("Contents of array must be JSON objects in ");
                                return null;
                        }
                }
                return toReturn;
        }

        private List<String> parseStringList(Object input) {
                List<String> toReturn = new ArrayList<>();
                if (input == null) {
                        System.out.print("Null when Array of String was expected at: ");
                        return null;
                }
                if(input.getClass().equals(JSONArray.class)) {
                        JSONArray data = (JSONArray) input;
                        for(Object obj : data) {
                                String string = parseString(obj);
                                if (string == null) {
                                        System.out.print("Array of String in: ");
                                        return null;
                                }
                                else toReturn.add(string);
                        }
                }else {
                        System.out.print("Incorrect Data Type when Array of String was expected at: ");
                        return null;
                }
                return toReturn;
        }

        private boolean parseItems(){
                itemsAtStart = new HashMap<>();
                if(!jsonObject.containsKey("Items")){
                        System.out.println("Key \"Items\" does not exist in base JSON object.");
                        return true;
                }
                Object obj= jsonObject.get("Items");
                if(obj != null && JSONArray.class.equals(obj.getClass())) { //if the item in the array = obj
                        JSONArray jsonItems = (JSONArray) obj;  // downcast the item to a JSON simple obj
                        if(jsonItems.size() == 0){
                                System.out.println("Items array must not be empty.");
                                return true;
                        }
                        for (Object itemObj : jsonItems) { // for each item in jsonItems (JSON simple obj)
                                if(itemObj == null || !JSONObject.class.equals(itemObj.getClass())){
                                        System.out.println("Contents of Items array must be JSON objects");
                                        return true;
                                }
                                JSONObject itemJsonObj = (JSONObject) itemObj; //set itemJsonObj = JSON simple Obj

                                HashMap<String, List<String>> flags = new HashMap<>();
                                if(!itemJsonObj.containsKey("Name")){
                                        System.out.println("Key \"Name\" does not exist in an Item object.");
                                        return true;
                                }

                                String name = parseString(itemJsonObj.get("Name"));// item.name equals the parsed JSON simple item name
                                if (name == null) {    // if the item name is null print
                                        System.out.println("Item Name."); // print item name
                                        return true; //exits parsing method
                                }
                                if (itemJsonObj.keySet().contains("Effect Tags")) { //if itemsJson object keys (from map) contain effect tags
                                        obj = itemJsonObj.get("Effect Tags"); // set obj variable = to the effect tag
                                        if (JSONArray.class.equals(obj.getClass())) { // if JSON simple class obj = obj class
                                                JSONArray flagsJson = (JSONArray) obj; //set flagsjson = obj
                                                flags = parseFlags(flagsJson); // set item.flags equal to parsed version of flags
                                                if (flags == null) {  // if the item flag is null
                                                        System.out.println(name + " Effect Tags."); // print out item name concat effect tag
                                                        return true; // exits parsing
                                                }
                                        } else {
                                                System.out.println("Effect Tags in item " + name + " must be a JSON " +
                                                        "array.");
                                                return true;
                                        }
                                }
                                if(!itemJsonObj.containsKey("Description")){
                                        System.out.println("Key \"Description\" does not exist in item " + name + ".");
                                        return true;
                                }

                                String description = parseString(itemJsonObj.get("Description")); // set item.description to parsed JSON simple object
                                Item item = new Item(name.toLowerCase(), description, flags); // this item can now be made a new item in Items
                                if (item.description == null) {   // if item description is null
                                        System.out.println("Item " + item.name + " Description.");
                                return true;
                                }
                                itemsAtStart.put(item.name, item);
                        }
                }else {
                        System.out.println("Items must be a JSON array.");
                        return true;
                }
                return false;
        }

        private boolean parseNpcs() {
                npcsAtStart = new HashMap<>();
                if(!jsonObject.containsKey("NPCs")){
                        System.out.println("Key \"NPCs\" does not exist in base JSON object.");
                        return true;
                }
                Object obj = jsonObject.get("NPCs");
                if (JSONArray.class.equals(obj.getClass())) {
                        JSONArray jsonNpcs = (JSONArray) obj;
                        if(jsonNpcs.size() == 0){
                                System.out.println("NPCs JSON array must not be empty.");
                        }
                        for (Object npcObj : jsonNpcs) {
                                if(npcObj == null || !JSONObject.class.equals(npcObj.getClass())){
                                        System.out.println("All items in NPCs array must be JSON objects.");
                                        return true;
                                }

                                JSONObject npcJsonObj = (JSONObject) npcObj;
                                if(!npcJsonObj.containsKey("Name")){
                                        System.out.println("Key \"Name\" does not exist in an object in NPCs.");
                                        return true;
                                }

                                String name = parseString(npcJsonObj.get("Name"));
                                if (name == null) {
                                        System.out.println("NPC Name");
                                        return true;
                                }
                                HashMap<String, List<String>> flags;
                                if (npcJsonObj.keySet().contains("Effect Tags")) { //if npcsJson object keys (from map) contain effect tags
                                        obj = npcJsonObj.get("Effect Tags"); // set obj variable = to the effect tag
                                        if (obj != null && JSONArray.class.equals(obj.getClass())) { // if JSON simple class obj = obj class
                                                JSONArray flagsJson = (JSONArray) obj; //set flagsjson = obj
                                                flags = parseFlags(flagsJson); // set item.flags equal to parsed version of flags
                                                if (flags == null) {  // if the item flag is null
                                                        System.out.println(name + " Effect Tags."); // print out item name concat effect tag
                                                        return true; // exits parsing
                                                }
                                        } else {
                                                System.out.println("Effect Tags in NPC " + name + " must be a JSON " +
                                                        "array.");
                                                return true;
                                        }
                                } else flags = new HashMap<>();
                                if(!npcJsonObj.containsKey("Dialogue")){
                                        System.out.println("Key \"Dialogue\" does not exist in NPC "+ name + ".");
                                        return true;
                                }

                                String dialogue = parseString(npcJsonObj.get("Dialogue")); // set npc.dialogue to parsed JSON simple object
                                if (dialogue == null) {   // if npc dialogue is null
                                        System.out.println("NPC " + name + " Dialogue.");
                                        return true;
                                }
                                List<String> alternativeDialogue = new ArrayList<>();
                                if(npcJsonObj.containsKey("Alternative Dialogue")){


                                        alternativeDialogue =
                                                parseStringList(npcJsonObj.get("Alternate Dialogue")); // set to parsed JSON simple object
                                        if (alternativeDialogue == null) {   // if npc  alt. dialogue is null
                                                System.out.println("NPC " + name + " Alternate Dialogue.");
                                                return true;
                                        }
                                }
                        Npc npc = new Npc(name, dialogue, flags, alternativeDialogue);
                        npcsAtStart.put(npc.name, npc);
                        }
                } else {
                        System.out.println("NPCs must be a JSON array.");
                        return true;
                }
                return false;
        }

        private boolean parseCraftingRecipes(){
                recipes = new ArrayList<>();
                if(!jsonObject.containsKey("Crafting Recipes")){
                        return false;
                }
                Object obj = jsonObject.get("Crafting Recipes");
                if(obj != null && JSONArray.class.equals(obj.getClass())){
                        JSONArray jsonCraftingRecipes = (JSONArray) obj;
                        for(Object craftingRecipeObj : jsonCraftingRecipes){
                                if (craftingRecipeObj == null
                                        || !JSONObject.class.equals(craftingRecipeObj.getClass())){
                                        System.out.println("Contents of Crafting Recipes array must be JSON objects");
                                        return true;
                                }

                                JSONObject craftingRecipeJsonObj = (JSONObject) craftingRecipeObj;
                                if (!craftingRecipeJsonObj.containsKey("Result")){
                                        System.out.println("Key \"Result\" does not exist in a Crafting Recipes " +
                                                "entry.");
                                        return true;
                                }



                                CraftingRecipe craftingRecipe = new CraftingRecipe();

                                craftingRecipe.result = parseString(craftingRecipeJsonObj.get("Result"));
                                if (craftingRecipe.result == null) {
                                        System.out.println("Crafting Recipe Result.");
                                        return true;
                                }
                                craftingRecipe.result = craftingRecipe.result.toLowerCase();
                                if(!itemsAtStart.containsKey(craftingRecipe.result)){
                                        System.out.println("Item " + craftingRecipe.result + " in Crafting Recipe " +
                                                "Result is not defined in Items.");
                                        return true;
                                }

                                if (!craftingRecipeJsonObj.containsKey("Ingredients")){
                                        System.out.println("Key \"Ingredients\" does not exist in " +
                                                craftingRecipe.result + " Crafting Recipe.");
                                        return true;
                                }
                                craftingRecipe.ingredients = parseStringList(craftingRecipeJsonObj.get("Ingredients"));
                                if(craftingRecipe.ingredients == null) {
                                        System.out.println("Crafting Recipe Ingredients");
                                        return true;
                                }

                                for (int i = 0; i < craftingRecipe.ingredients.size(); i++) {
                                        String item = craftingRecipe.ingredients.get(i).toLowerCase();
                                        craftingRecipe.ingredients.set(i, item);
                                        if(!itemsAtStart.containsKey(item)){
                                                System.out.println("Item " + item + " in Crafting Recipe Ingredients " +
                                                        "is not defined in Items.");
                                                return true;
                                        }
                                }
                                recipes.add(craftingRecipe);
                        }
                } else {
                        System.out.println("Crafting Recipes must be a JSON array.");
                        return true;
                }
                return false;
        }

        private boolean parseEndCondition(){
                if(!jsonObject.containsKey("End Conditions")){
                        System.out.println("Key \"End Conditions\" does not exist in base JSON object.");
                        return true;
                }
                endConditions = new ArrayList<>();
                Object obj = jsonObject.get("End Conditions");
                if(obj != null && JSONArray.class.equals(obj.getClass())){
                        JSONArray jsonEndConditions = (JSONArray) obj;
                        if(jsonEndConditions.size() == 0){
                                System.out.println("End Conditions array cannot be empty.");
                                return true;
                        }
                        for(Object endConditionsObj : jsonEndConditions){
                                if (endConditionsObj == null || !JSONObject.class.equals(endConditionsObj.getClass())){
                                        System.out.println("Contents of End Conditions array must all be JSON " +
                                                "objects.");
                                        return true;
                                }
                                JSONObject endConditionsJsonObj = (JSONObject) endConditionsObj;
                                EndCondition endCondition = new EndCondition();
                                if (!endConditionsJsonObj.containsKey("Win")){
                                        System.out.println("Key \"Win\" does not exist in an End Condition object.");
                                        return true;
                                }
                                obj = endConditionsJsonObj.get("Win");
                                if (obj == null || !Boolean.class.equals(obj.getClass())){
                                        System.out.println("Win must be a boolean value.");
                                        return true;
                                }
                                endCondition.win = (boolean) obj;
                                if(endConditionsJsonObj.containsKey("Room Requirement")) {

                                        endCondition.roomReq = parseString(endConditionsJsonObj.get("Room Requirement"));
                                        if (endCondition.roomReq == null) {
                                                System.out.println("End Condition Room Requirement.");
                                                return true;
                                        }
                                        endCondition.roomReq = endCondition.roomReq.toLowerCase();
                                        if(!roomsAtStart.containsKey(endCondition.roomReq)){
                                                System.out.println("Room " + endCondition.roomReq + " in End " +
                                                        "Conditions is not defined in Rooms.");
                                                return true;
                                        }
                                }
                                if(endConditionsJsonObj.containsKey("Item Requirements")) {
                                        endCondition.itemReq = parseStringList(endConditionsJsonObj.get("Item Requirements"));
                                        if (endCondition.itemReq == null) {
                                                System.out.println("End Condition Item Requirements");
                                                return true;
                                        }
                                        for (int i = 0; i < endCondition.itemReq.size(); i++){
                                                String item = endCondition.itemReq.get(i).toLowerCase();
                                                endCondition.itemReq.set(i, item);
                                                if(!itemsAtStart.containsKey(item)){
                                                        System.out.println("Item " + item + " in End " +
                                                                "Conditions is not defined in Items.");
                                                        return true;
                                                }
                                        }
                                }
                                if(endConditionsJsonObj.containsKey("NPC Status Requirements")){
                                        endCondition.npcReq = new ArrayList<>();
                                        obj = endConditionsJsonObj.get("NPC Status Requirements");
                                        if(obj != null && obj.getClass().equals(JSONArray.class)) {
                                                JSONArray jsonNpcs = (JSONArray) obj;
                                                for (Object npcObj : jsonNpcs) {
                                                        if(npcObj == null
                                                                || !JSONObject.class.equals(npcObj.getClass())) {
                                                                System.out.println("NPC Status Requirements must " +
                                                                        "contain only JSON Objects.");
                                                                return true;
                                                        }
                                                        JSONObject npcJsonObj = (JSONObject) npcObj;
                                                        if(!npcJsonObj.containsKey("Name")) {
                                                                System.out.println("Key \"Name\" does not exist in " +
                                                                        "an NPC object in End Conditions.");
                                                                return true;
                                                        }
                                                        String name = parseString(npcJsonObj.get("Name"));
                                                        if (name == null) {
                                                                System.out.println("NPC Name in End Conditions");
                                                                return true;
                                                        }
                                                        if (!npcsAtStart.containsKey(name)){
                                                                System.out.println("NPC " + name + " in NPC Status " +
                                                                        "Requirements is not defined in Npcs.");
                                                                return true;
                                                        }
                                                        HashMap<String, List<String>> flags;
                                                        if(!npcJsonObj.containsKey("Effect Tags")) {
                                                                System.out.println("Key \"Effect Tags\" does not " +
                                                                        "exist in NPC " + name +
                                                                        " in End Conditions.");
                                                                return true;
                                                        }
                                                        if (npcJsonObj.keySet().contains("Effect Tags")) { //if npcsJson object keys (from map) contain effect tags
                                                                obj = npcJsonObj.get("Effect Tags"); // set obj variable = to the effect tag
                                                                if (obj != null
                                                                        && JSONArray.class.equals(obj.getClass())) { // if JSON simple class obj = obj class
                                                                        JSONArray flagsJson = (JSONArray) obj; //set flagsjson = obj
                                                                        flags = parseFlags(flagsJson); // set item.flags equal to parsed version of flags
                                                                        if (flags == null) {  // if the item flag is null
                                                                                System.out.println(name
                                                                                        + " Effect Tags."); // print out item name concat effect tag
                                                                                return true; // exits parsing
                                                                        }
                                                                } else {
                                                                        System.out.println("Effect Tags in NPC " + name
                                                                                + " in End Conditions must be a JSON " +
                                                                                "array.");
                                                                        return true;
                                                                }
                                                        } else flags = new HashMap<>();
                                                        String dialogue = "";
                                                        List<String> alternativeDialogue = new ArrayList<>();
                                                        Npc npc = new Npc(name, dialogue, flags, alternativeDialogue);
                                                        endCondition.npcReq.add(npc);
                                                }
                                        } else {
                                                System.out.println("NPC Status Requirements must be a JSON array.");
                                                return true;
                                        }
                                }
                                if ((endCondition.itemReq == null || endCondition.itemReq.size() == 0)
                                        && (endCondition.npcReq == null || endCondition.npcReq.size() == 0)
                                        && null == endCondition.roomReq) {
                                        System.out.println("All End Conditions must have at least one of: " +
                                                "Room Requirement, Item Requirements, or NPC Status Requirements.");
                                        return true;
                                }
                                endConditions.add(endCondition);
                        }
                } else {
                        System.out.println("End Conditions must be a JSON array.");
                        return true;
                }
                return false;
        }
}