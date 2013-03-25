package com.example.scouting.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Team;

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
		return matchesDB.get(id);
	}

	@Override
	public Player findPlayerById(Integer id) {
		return playersDB.get(id);
	}

	@Override
	public Team findTeamById(Integer id) {
		return teamsDB.get(id);
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
