package com.example.scouting.server;

import java.util.ArrayList;
import java.util.List;

import com.example.scouting.src.Match;
import com.example.scouting.src.Team;

public class MatchLocalDB implements MatchDBInterface{
	List<Match> matchesDB = new ArrayList<Match>();
	
	@Override
	public Match createNewMatch(Team team, String opponent) {
		Match newMatch = new Match(team, opponent);
		matchesDB.add(newMatch);
		return newMatch;
	}

	@Override
	public Match findMatchById(int id) {
		return matchesDB.get(id);
	}

	@Override
	public List<Match> getAllMatches() {
		return matchesDB;
	}

	@Override
	public Match saveMatch(Match match) {
		return match;
	}
}
