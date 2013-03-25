package com.example.scouting.src;

public class Stats {
	Player player;
	Integer stats[][];
	
	public Stats(Player player) {
		this.player = player;
		stats = new Integer[ActionType.values().length][ActionScore.values().length];
	}
	
	public void add(ActionType type, ActionScore score){
		stats[type.ordinal()][score.ordinal()] = (stats[type.ordinal()][score.ordinal()] == null ? 1 : stats[type.ordinal()][score.ordinal()]+1);
	}
	
	public Integer getPoints(){
		Integer attack = stats[ActionType.Attack.ordinal()][ActionScore.PlusPlus.ordinal()];
		Integer service = stats[ActionType.Service.ordinal()][ActionScore.PlusPlus.ordinal()];
		Integer block = stats[ActionType.Block.ordinal()][ActionScore.PlusPlus.ordinal()];
		return (attack == null ? 0 : attack) + (service == null ? 0 : service) + (block == null ? 0 : block);
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
