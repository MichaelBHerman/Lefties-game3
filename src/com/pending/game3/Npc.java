package com.pending.game3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Npc {
    final String name;
    private HashMap<String, List<String>> flags;
    final String dialogue;
    private List<String> alternativeDialogue;

    Npc(String name, String dialogue,HashMap<String, List<String>> flags, List<String> alternativeDialogue ){
        this.name = name;
        this.dialogue = dialogue;
        this.flags = flags;
        this.alternativeDialogue = alternativeDialogue;
    }

    HashMap<String, List<String>> getFlags() {
        return new HashMap<>(flags);
    }

    HashMap<String, List<String>> editFlags() {
        return flags;
    }

    List<String> getAlternativeDialogue(){
        return new ArrayList<>(alternativeDialogue);
    }

    List<String> editAlternativeDialogue(){
        return alternativeDialogue;
    }

    void interact(){

    }
}