package com.awouters.scouting.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.awouters.scouting.src.Match;
import com.awouters.scouting.src.Player;
import com.awouters.scouting.src.Team;

public class ScoutingLocalDB implements ScoutingDBInterface, Serializable{
	private static final long serialVersionUID = 1L;
	List<Match> matchesDB = new ArrayList<Match>();
	List<Team> teamsDB = new ArrayList<Team>();
	List<Player> playersDB = new ArrayList<Player>();

	@Override
	public List<Match> getAllMatches() {
		return matchesDB;
	}

	@Override
	public Match saveMatch(Match match) {
		return match;
	}

	@Override
	public List<Team> getAllTeams() {
		return teamsDB;
	}

	@Override
	public Team saveTeam(Team team) {
		return team;
	}

	@Override
	public List<Player> getAllPlayers() {
		return playersDB;
	}

	@Override
	public Player savePlayer(Player player) {
		return player;
	}

	@Override
	public Match findMatchById(Integer id) {
		if(id != null && id >= 0 && id < matchesDB.size()){
			return matchesDB.get(id);
		}
		else{
			return null;
		}
	}

	@Override
	public Player findPlayerById(Integer id) {
		if(id != null && id >= 0 && id < playersDB.size()){
			return playersDB.get(id);
		}
		else{
			return null;
		}
	}

	@Override
	public Team findTeamById(Integer id) {
		if(id != null && id >= 0 && id < teamsDB.size()){
			return teamsDB.get(id);
		}
		else{
			return null;
		}
	}

	@Override
	public void removeMatch(Match match) {
		matchesDB.remove(match);
	}

	@Override
	public void removeTeam(Team team) {
		teamsDB.remove(team);
	}

	@Override
	public void removePlayer(Player player) {
		playersDB.remove(player);
	}

	@Override
	public Match createNewMatch(Match match) {
		matchesDB.add(match);
		return match;
	}

	@Override
	public Team createNewTeam(Team team) {
		teamsDB.add(team);
		return team;
	}

	@Override
	public Player createNewPlayer(Player player) {
		playersDB.add(player);
		return player;
	}
}
