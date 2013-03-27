package com.example.scouting.server;

import java.io.Serializable;
import java.util.List;

import android.preference.Preference;

import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Team;

public interface ScoutingService extends Serializable {
	List<Match> getAllMatches();
	Match saveMatch(Match match);
	Match createNewMatch(Team team, String opponent, Boolean visitor);
	Match findMatchById(Integer id);
	void removeMatch(Match match);
	
	List<Team> getAllTeams();
	Team saveTeam(Team team);
	Team createNewTeam(String name);
	Team findTeamById(Integer id);
	void removeTeam(Team team);
	
	List<Player> getAllPlayers();
	Player savePlayer(Player player);
	Player createNewPlayer(String name, int number, String image);
	Player findPlayerById(Integer id);
	void removePlayer(Player player);
}
