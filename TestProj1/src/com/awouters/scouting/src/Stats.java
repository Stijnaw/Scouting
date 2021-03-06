package com.awouters.scouting.src;

import android.util.Log;

public class Stats {
	Player player;
	Integer stats[][];
	int statsTotal = 0;
	
	public Stats(Player player) {
		this.player = player;
		stats = new Integer[ActionType.values().length][ActionScore.values().length];
		
		for(int i = 0; i < ActionType.values().length; i++){
			for(int j = 0; j < ActionScore.values().length; j++){
				stats[i][j] = 0;
			}
		}
	}
	
	public void add(ActionType type, ActionScore score){
		stats[type.ordinal()][score.ordinal()] = (stats[type.ordinal()][score.ordinal()] == null ? 1 : stats[type.ordinal()][score.ordinal()]+1);
		statsTotal++;
	}
	
	/*public Integer getPoints(){
		int attack = stats[ActionType.Attack.ordinal()][ActionScore.PlusPlus.ordinal()]-stats[ActionType.Attack.ordinal()][ActionScore.MinusMinus.ordinal()];
		int service = stats[ActionType.Service.ordinal()][ActionScore.PlusPlus.ordinal()]-stats[ActionType.Service.ordinal()][ActionScore.MinusMinus.ordinal()];
		int block = stats[ActionType.Block.ordinal()][ActionScore.PlusPlus.ordinal()]-stats[ActionType.Block.ordinal()][ActionScore.MinusMinus.ordinal()];
		int reception = stats[ActionType.Reception.ordinal()][ActionScore.PlusPlus.ordinal()]-stats[ActionType.Reception.ordinal()][ActionScore.MinusMinus.ordinal()];
		return attack+service+block+reception;
	}*/
	
	public Integer getPoints(){
		int points = 0, count = 0;
		
		for(int i = 0; i < ActionType.values().length; i++){
			points += stats[i][ActionScore.MinusMinus.ordinal()]*-2;
			points += stats[i][ActionScore.Minus.ordinal()]*-1;
			points += stats[i][ActionScore.Plus.ordinal()];
			points += stats[i][ActionScore.PlusPlus.ordinal()]*2;
			
			count += stats[i][ActionScore.MinusMinus.ordinal()];
			count += stats[i][ActionScore.Minus.ordinal()];
			count += stats[i][ActionScore.Null.ordinal()];
			count += stats[i][ActionScore.Plus.ordinal()];
			count += stats[i][ActionScore.PlusPlus.ordinal()];
		}
		
		if(count > 0){
			float score = ((float) (points+count*2))/((float) (4*count));
			return (int) (score*100);
		}
		else{
			return 0;
		}
	}
	
	public Integer getComparePoints(){
		if(statsTotal > 0){
			return getPoints();
		}
		else{
			return -1;
		}
	}


	public Player getPlayer() {
		return player;
	}
	
	public Integer getCount(ActionType type){
		Integer total = 0;
		
		for(Integer count: stats[type.ordinal()]){
			total += (count == null ? 0 : count);
		}
		return total;
	}
	
	public Integer getCount(ActionType type, ActionScore score){
		return (stats[type.ordinal()][score.ordinal()] == null ? 0 : stats[type.ordinal()][score.ordinal()]);
	}
}
