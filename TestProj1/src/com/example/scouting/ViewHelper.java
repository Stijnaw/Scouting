package com.example.scouting;

import java.io.Serializable;

import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Match;

public class ViewHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer selectedPlayer = null;
	private ActionType selectedActionType = null;
	private ActionScore selectedActionScore = null;
	private Integer selectedTab = null;
	private Integer selectedMatch = null;
	
	public Integer getSelectedPlayer() {
		return selectedPlayer;
	}
	public void setSelectedPlayer(Integer selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}
	
	public ActionType getSelectedActionType() {
		return selectedActionType;
	}
	public void setSelectedActionType(ActionType selectedActionType) {
		this.selectedActionType = selectedActionType;
	}
	
	public ActionScore getSelectedActionScore() {
		return selectedActionScore;
	}
	public void setSelectedActionScore(ActionScore selectedActionScore) {
		this.selectedActionScore = selectedActionScore;
	}
	public void resetSelected() {
		this.selectedPlayer = null;
		this.selectedActionType = null;
		this.selectedActionScore = null;
	}
	
	public void setSelectedTab(Integer i) {
		this.selectedTab = i;
	}
	
	public Integer getSelectedTab() {
		return selectedTab;
	}
	
	public void setSelectedMatch(Integer selectedMatch) {
		this.selectedMatch = selectedMatch;
	}
	
	public Integer getSelectedMatch() {
		return selectedMatch;
	}
}
