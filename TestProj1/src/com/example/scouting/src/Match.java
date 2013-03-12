package com.example.scouting.src;

import java.util.ArrayList;

public class Match {
    private Team team;
    private String opponent;
    private ArrayList<Set> sets;
    private Set currentSet;

    public Match(Team team, String opponent) {
        this.team = team;
        this.opponent = opponent;
        sets = new ArrayList<Set>();

        // Create first set
        newSet();
    }

    public Team getTeam() {
        return team;
    }
    
    public int getSetNumber(){
        return sets.indexOf(currentSet)+1;
    }

    public void newSet(){
        Set set = new Set();

        // Add new set to sets
        sets.add(set);

        // Set it as the current set
        currentSet = set;
    }

    public void addAction(Action action){
        // Add action to current set
        sets.get(getSetNumber()-1).addAction(action);
    }

	public String getOpponent() {
		return opponent;
	}
}
