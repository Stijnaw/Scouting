package com.awouters.scouting.src;

import java.io.Serializable;
import java.util.ArrayList;

public class Set implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Action> actions;
	private ArrayList<Player> activePlayers;

    public Set() {
        actions = new ArrayList<Action>();
        activePlayers = new ArrayList<Player>();
        activePlayers.add(null);
        activePlayers.add(null);
        activePlayers.add(null);
        activePlayers.add(null);
        activePlayers.add(null);
        activePlayers.add(null);
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public ArrayList<Action> getActions() {
        return actions;
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
    	if(activePlayers.indexOf(player) != -1){
    		return (activePlayers.indexOf(player)+1);
    	}
    	else{
    		return null;
    	}
    }
    
    public ArrayList<Player> getActivePlayers(){
    	if(activePlayers == null){
            activePlayers = new ArrayList<Player>();
            activePlayers.add(null);
            activePlayers.add(null);
            activePlayers.add(null);
            activePlayers.add(null);
            activePlayers.add(null);
            activePlayers.add(null);
    	}
    	return activePlayers;
    }
    
    public Action getLastAction(){
		if(actions.size() >= 1){
			return actions.get(actions.size()-1);
		}
		else{
			return null;
		}
    }

	public Action undoAction() {
		if(actions.size() >= 1){
			return actions.remove(actions.size()-1);
		}
		else{
			return null;
		}
	}
}
