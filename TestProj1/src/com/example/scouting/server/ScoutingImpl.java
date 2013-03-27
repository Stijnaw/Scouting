package com.example.scouting.server;

import java.io.Serializable;
import java.util.List;

import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Team;

public class ScoutingImpl implements ScoutingService, Serializable {
	private static final long serialVersionUID = 1L;
	private final ScoutingDBInterface scoutingDBInterface;
	
	public ScoutingImpl(ScoutingDBInterface scoutingDBInterface) {
		this.scoutingDBInterface = scoutingDBInterface;
	}
	
	@Override
	public Match findMatchById(Integer id) {
		return this.scoutingDBInterface.findMatchById(id);
	}

	@Override
	public List<Match> getAllMatches() {
		return scoutingDBInterface.getAllMatches();
	}

	@Override
	public Match createNewMatch(Team team, String opponent, Boolean visitor) {
		return scoutingDBInterface.createNewMatch(new Match(team, opponent, visitor));
	}

	@Override
	public Match saveMatch(Match match) {
		return scoutingDBInterface.saveMatch(match);
	}
	
	@Override
	public Team findTeamById(Integer id) {
		return this.scoutingDBInterface.findTeamById(id);
	}

	@Override
	public List<Team> getAllTeams() {
		return scoutingDBInterface.getAllTeams();
	}

	@Override
	public Team saveTeam(Team team) {
		return scoutingDBInterface.saveTeam(team);
	}

	@Override
	public Team createNewTeam(String name) {
		return scoutingDBInterface.createNewTeam(new Team(name));
	}
	
	@Override
	public Player findPlayerById(Integer id) {
		return this.scoutingDBInterface.findPlayerById(id);
	}

	@Override
	public List<Player> getAllPlayers() {
		return scoutingDBInterface.getAllPlayers();
	}

	@Override
	public Player savePlayer(Player player) {
		return scoutingDBInterface.savePlayer(player);
	}

	@Override
	public Player createNewPlayer(String name, int number, String picture) {
		return scoutingDBInterface.createNewPlayer(new Player(name, number, picture));
	}

	@Override
	public void removeMatch(Match match) {
		scoutingDBInterface.removeMatch(match);
	}

	@Override
	public void removeTeam(Team team) {
		scoutingDBInterface.removeTeam(team);
	}

	@Override
	public void removePlayer(Player player) {
		scoutingDBInterface.removePlayer(player);
	}
}
