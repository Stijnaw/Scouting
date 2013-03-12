package com.example.scouting.server;

import java.util.List;

import com.example.scouting.src.Match;
import com.example.scouting.src.Team;

public class ScoutingImpl implements ScoutingService {

	private final MatchDBInterface matchDBInterface;
	
	public ScoutingImpl(MatchDBInterface matchDBInterface) {
		this.matchDBInterface = matchDBInterface;
	}

	@Override
	public List<Match> getAllMatches() {
		return matchDBInterface.getAllMatches();
	}

	@Override
	public Match findMatchById(int id) {
		return matchDBInterface.findMatchById(id);
	}

	@Override
	public Match createNewMatch(Team team, String opponent) {
		return matchDBInterface.saveMatch(new Match(team, opponent));
	}

	@Override
	public Match saveMatch(Match match) {
		return matchDBInterface.saveMatch(match);
	}
}
