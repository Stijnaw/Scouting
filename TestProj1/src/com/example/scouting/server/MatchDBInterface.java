package com.example.scouting.server;

import java.util.List;

import com.example.scouting.src.Match;
import com.example.scouting.src.Team;

public interface MatchDBInterface {
	Match saveMatch(Match match);
	Match findMatchById(int id);
	List<Match> getAllMatches();
	Match createNewMatch(Team team, String opponent);
}
