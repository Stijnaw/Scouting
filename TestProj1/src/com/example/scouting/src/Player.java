package com.example.scouting.src;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
    private int number;
    private String picture;

    /**
     *
     * @param name the name
     * @param number the number
     */
    public Player(String name, int number){
        this.name = name;
        this.number = number;
    }
    
    public Player(Player player){
    	this.name = player.getName();
    	this.number = player.getNumber();
    	this.picture = player.getPicture();
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
     * @return the number
     */
    public Integer getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
		this.number = number;
	}
    
    /**
     * @param picture the picture to set
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * @return the picture
     */
    public String getPicture() {
        return picture;
    }
    
    @Override
    public String toString(){
    	return name;
    }
}
