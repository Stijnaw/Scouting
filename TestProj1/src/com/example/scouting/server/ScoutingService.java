package com.example.scouting.server;

import java.util.List;

import com.example.scouting.src.Match;
import com.example.scouting.src.Team;

/*
 * die ScoutingService moet ge een beetje zien als een verzameling van alle operaties die uw systeem aan kan
bv createNewPlayer
en daar zal ook een functie komen getStatistics
en de logica om die statistics op te bouwen zouden daar bv ook in komen
 */

public interface ScoutingService {
	List<Match> getAllMatches();
	Match findMatchById(int id);
	Match saveMatch(Match match);
	Match createNewMatch(Team team, String opponent);
}
