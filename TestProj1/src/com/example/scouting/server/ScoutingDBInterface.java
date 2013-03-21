package com.example.scouting.server;

import java.io.Serializable;
import java.util.List;

import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Team;

public interface ScoutingDBInterface extends Serializable{
	Match saveMatch(Match match);
	List<Match> getAllMatches();
	Team saveTeam(Team team);
	List<Team> getAllTeams();
	Player savePlayer(Player player);
	List<Player> getAllPlayers();
	Match findMatchById(Integer id);
	Player findPlayerById(Integer id);
	Team findTeamById(Integer id);
	void removeMatch(Match match);
	void removeTeam(Team team);
	void removePlayer(Player player);
}
