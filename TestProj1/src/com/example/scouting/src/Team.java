package com.example.scouting.src;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
    private ArrayList<Player> players;
    private long id;

    /**
     * 
     * @param name
     */
    public Team(String name) {
        this.name = name;
        players = new ArrayList<Player>();
    }
    
    public Team(Team team){
    	this.name = team.getName();
    	this.players = new ArrayList<Player>(team.getPlayers());
    }
    
    public Team(){
    	this.name = "";
    	this.players = new ArrayList<Player>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
		this.name = name;
	}
    
    /**
     * @param players the players to set
     */
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    public void removePlayer(Player player){
    	players.remove(player);
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
}
