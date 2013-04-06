package com.awouters.scouting.src;

import java.io.Serializable;

public class Action implements Serializable  {
	private static final long serialVersionUID = 1L;
	private Player player;
    private ActionType actionType;
    private ActionScore actionScore;
	private float startX = 0;
	private float startY = 0;
	private float endX = 0;
	private float endY = 0;
	private int orientation = 0;
    
    public Action(Player player, ActionType actionType, ActionScore actionScore) {
		this.player = player;
		this.actionType = actionType;
		this.actionScore = actionScore;
	}
    
    public float getStartX() {
		return startX;
	}

	public float getStartY() {
		return startY;
	}

	public float getEndX() {
		return endX;
	}

	public float getEndY() {
		return endY;
	}

	public int getOrientation() {
		return orientation;
	}

	public Action(Player player, ActionType actionType, ActionScore actionScore, float startX, float startY, float endX, float endY, int orientation) {
		this.player = player;
		this.actionType = actionType;
		this.actionScore = actionScore;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.orientation = orientation;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
    
    public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
    
    public void setActionScore(ActionScore actionScore) {
		this.actionScore = actionScore;
	}
    
    public Player getPlayer() {
		return player;
	}
    
    public ActionType getActionType() {
		return actionType;
	}
    
    public ActionScore getActionScore() {
		return actionScore;
	}
    
    @Override
    public String toString() {
    	return player.getNumber() + ": " + actionType.toString() + "-" + actionScore.toString();
    }
}
