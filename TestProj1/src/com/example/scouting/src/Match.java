package com.example.scouting.src;

import java.io.Serializable;
import java.util.ArrayList;

public class Match implements Serializable {
	private static final long serialVersionUID = 1L;
	private Team team;
    private String opponent;
    private Boolean visitor = false;
    
    private ArrayList<Set> sets;
    private Set currentSet;

    public Match(Team team, String opponent, Boolean visitor) {
        this.team = team;
        this.opponent = opponent;
        this.visitor = visitor;
        sets = new ArrayList<Set>();

        // Create first set
        newSet();
    }

    public Team getTeam() {
        return team;
    }
    
    public ArrayList<Set> getSets() {
		return sets;
	}
    
    public Set getSetByNumber(int set){
    	return sets.get(set-1);
    }
    
    public int getSetNumber(Set set){
    	return sets.indexOf(set)+1;
    }
    
    public Set removeSetByNumber(int set){
    	return sets.remove(set-1);
    }
    
    public int getCurrentSetNumber(){
        return sets.indexOf(currentSet)+1;
    }
    
    public Set getCurrentSet(){
    	return currentSet;
    }
    
    public void setCurrentSet(Set set){
    	currentSet = set;
    }
    
    public void setVisitor(Boolean visitor) {
		this.visitor = visitor;
	}
    
    public void setTeam(Team team) {
		this.team = team;
	}
    
    public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

    public Set newSet(){
        Set set = new Set();

        // Add new set to sets
        sets.add(set);

        // Set it as the current set
        currentSet = set;
        
        return set;
    }
    
    public boolean setPlayerActive(Player player, int position){
    	return currentSet.setPlayerActive(player, position);
    }
    
    public Player getActivePlayerByPosition(Integer position){
    	return currentSet.getActivePlayerByPosition(position);
    }
    
    public Integer getActivePositionByPlayer(Player player){
    	return currentSet.getActivePositionByPlayer(player);
    }
    
    public ArrayList<Player> getActivePlayers(){
    	return currentSet.getActivePlayers();
    }

    public void addAction(Action action){
        // Add action to current set
        sets.get(getCurrentSetNumber()-1).addAction(action);
    }

	public String getOpponent() {
		return opponent;
	}
	
    @Override
    public String toString(){
    	if(visitor == false){
    		return team.getName() + " - " + opponent;
    	}
    	else{
    		return opponent + " - " + team.getName();
    	}
    }

	public boolean isVisitor() {
		return visitor;
	}

	public Action undoAction() {
		if(getCurrentSet() != null){
			return getCurrentSet().undoAction();
		}
		else{
			return null;
		}
	}
}
