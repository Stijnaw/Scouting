package com.example.scouting.src;

import java.util.ArrayList;

public class Match {
    private Team team;
    private String opponent;
    private ArrayList<Set> sets;
    private ArrayList<Player> activePlayers;
    private Set currentSet;

    public Match(Team team, String opponent) {
        this.team = team;
        this.opponent = opponent;
        sets = new ArrayList<Set>();
        
        activePlayers = new ArrayList<Player>();
        activePlayers.add(null);
        activePlayers.add(null);
        activePlayers.add(null);
        activePlayers.add(null);
        activePlayers.add(null);
        activePlayers.add(null);

        // Create first set
        newSet();
    }

    public Team getTeam() {
        return team;
    }
    
    public Set getSetByNumber(int set){
    	return sets.get(set);
    }
    
    public int getSetNumber(Set set){
    	return sets.indexOf(set)+1;
    }
    
    public int getCurrentSetNumber(){
        return sets.indexOf(currentSet)+1;
    }
    
    public void setCurrentSet(Set set){
    	currentSet = set;
    }
    
    public ArrayList<Set> getSetList(){
    	return sets;
    }

    public Set newSet(){
        Set set = new Set();

        // Add new set to sets
        sets.add(set);

        // Set it as the current set
        currentSet = set;
        
        return set;
    }

    public void addAction(Action action){
        // Add action to current set
        sets.get(getCurrentSetNumber()-1).addAction(action);
    }
    
    public boolean setPlayerActive(Player player, int position){
    	if(position <= 6 && position >= 1){
    		// remove old player
    		activePlayers.remove(position-1);
    		// add new player
    		activePlayers.add((position-1), player);
    		
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public Player getActivePlayerByPosition(Integer position){
    	if(position != null){
    		return activePlayers.get((position-1));
    	}
    	else{
    		return null;
    	}
    }
    
    public Integer getActivePositionByPlayer(Player player){
    	return (activePlayers.indexOf(player)+1);
    }
    
    public ArrayList<Player> getActivePlayers(){
    	return activePlayers;
    }

	public String getOpponent() {
		return opponent;
	}
}
