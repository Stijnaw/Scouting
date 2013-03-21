package com.example.scouting.src;

import java.io.Serializable;

public class Action implements Serializable  {
	private static final long serialVersionUID = 1L;
	private Player player;
    private ActionType actionType;
    private ActionScore actionScore;
    
    public Action(Player player, ActionType actionType, ActionScore actionScore) {
		this.player = player;
		this.actionType = actionType;
		this.actionScore = actionScore;
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
}
