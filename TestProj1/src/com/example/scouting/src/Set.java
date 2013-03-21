package com.example.scouting.src;

import java.io.Serializable;
import java.util.ArrayList;

public class Set implements Serializable{
	private static final long serialVersionUID = 1L;
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
