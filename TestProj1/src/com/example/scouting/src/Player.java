package com.example.scouting.src;

public class Player {
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
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
}
