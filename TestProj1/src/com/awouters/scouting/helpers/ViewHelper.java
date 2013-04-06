package com.awouters.scouting.helpers;

import java.io.Serializable;

import com.awouters.scouting.src.ActionScore;
import com.awouters.scouting.src.ActionType;

public class ViewHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Integer selectedPlayer = null;
	private static ActionType selectedActionType = null;
	private static ActionScore selectedActionScore = null;
	private static Integer selectedTab = null;
	private static Integer selectedMatch = null;
	private static Integer selectedFragment = null;
	private static int directionOrientation;
	private static float directionEndY;
	private static float directionEndX;
	private static float directionStartY;
	private static float directionStartX;
	
	public Integer getSelectedPlayer() {
		return selectedPlayer;
	}
	public void setSelectedPlayer(Integer selectedPlayer) {
		ViewHelper.selectedPlayer = selectedPlayer;
	}
	
	public ActionType getSelectedActionType() {
		return selectedActionType;
	}
	public void setSelectedActionType(ActionType selectedActionType) {
		ViewHelper.selectedActionType = selectedActionType;
	}
	
	public ActionScore getSelectedActionScore() {
		return selectedActionScore;
	}
	public void setSelectedActionScore(ActionScore selectedActionScore) {
		ViewHelper.selectedActionScore = selectedActionScore;
	}
	public void resetSelected() {
		ViewHelper.selectedPlayer = null;
		ViewHelper.selectedActionType = null;
		ViewHelper.selectedActionScore = null;
	}
	
	public void setSelectedTab(Integer i) {
		ViewHelper.selectedTab = i;
	}
	
	public Integer getSelectedTab() {
		return selectedTab;
	}
	
	public void setSelectedMatch(Integer selectedMatch) {
		ViewHelper.selectedMatch = selectedMatch;
	}
	
	public Integer getSelectedMatch() {
		return selectedMatch;
	}
	
	public void setSelectedFragment(Integer selectedFragment){
		ViewHelper.selectedFragment = selectedFragment;
	}
	
	public Integer getSelectedFragment(){
		return selectedFragment;
	}
	public void setDirection(float startX, float startY, float endX, float endY, int orientation) {
		ViewHelper.directionStartX = startX;
		ViewHelper.directionStartY = startY;
		ViewHelper.directionEndX = endX;
		ViewHelper.directionEndY = endY;
		ViewHelper.directionOrientation = orientation;
	}
	public int getDirectionOrientation() {
		return directionOrientation;
	}
	
	public float getDirectionEndY() {
		return directionEndY;
	}
	
	public float getDirectionEndX() {
		return directionEndX;
	}
	
	public float getDirectionStartY() {
		return directionStartY;
	}
	
	public float getDirectionStartX() {
		return directionStartX;
	}
	
	
}
