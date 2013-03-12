package com.example.scouting.src;

import java.util.ArrayList;

public class Set {
    private ArrayList<Action> actions;

    public Set() {
        actions = new ArrayList<Action>();
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public ArrayList<Action> getActions() {
        return actions;
    }
}
