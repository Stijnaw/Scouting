package com.example.scouting.src;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Player> players;

    /**
     * 
     * @param name
     */
    public Team(String name) {
        this.name = name;
        players = new ArrayList<Player>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param players the players to set
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
