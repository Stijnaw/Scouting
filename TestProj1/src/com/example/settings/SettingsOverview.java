package com.example.settings;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scouting.ViewHelper;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Team;
import com.example.testproj1.R;

public class SettingsOverview extends PreferenceFragment {

	private static ScoutingService scoutingService;
	private static ViewHelper viewHelper;

	public SettingsOverview() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		viewHelper = new ViewHelper();
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		Editor editor = sharedPrefs.edit();
		editor.clear();
		editor.commit();
		
		addPreferencesFromResource(R.xml.settings_overview);
		
		SettingsNewMatch settingsNewMatch = new SettingsNewMatch();
		settingsNewMatch.setScoutingService(scoutingService);
		settingsNewMatch.setFragmentTag("SettingsNewMatch");
		settingsNewMatch.setFragmentId(R.id.frameSettingsDetail);
		
		SettingsNewTeam settingsNewTeam = new SettingsNewTeam();
		settingsNewTeam.setScoutingService(scoutingService);
		settingsNewTeam.setFragmentTag("SettingsNewTeam");
		settingsNewTeam.setFragmentId(R.id.frameSettingsDetail);

		SettingsNewPlayer settingsNewPlayer = new SettingsNewPlayer();
		settingsNewPlayer.setScoutingService(scoutingService);
		settingsNewPlayer.setFragmentTag("SettingsNewPlayer");
		settingsNewPlayer.setFragmentId(R.id.frameSettingsDetail);
		
		ListPreference matchList = (ListPreference) findPreference("SelectMatch");
		
		// Populate matchList
		int i = 0;
		List<Match> matches = scoutingService.getAllMatches();
		String entries[] = new String[matches.size()];
		String entryValues[] = new String[matches.size()];
		
        for (Match match: matches) {
            entries[i] = match.toString();
            entryValues[i] = Integer.toString(matches.indexOf(match));
            i++;
        }
        
        matchList.setEntries(entries);
		matchList.setEntryValues(entryValues);
		
		Match myMatch = scoutingService.findMatchById(viewHelper.getSelectedMatch());
		
		if(myMatch != null){
			if(entries.length > matches.indexOf(myMatch) && matches.indexOf(myMatch) >= 0){
				matchList.setValueIndex(matches.indexOf(myMatch));
			}
		}
		
		matchList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {    
		    @Override
		    public boolean onPreferenceChange(Preference preference, Object newValue) {
		    	
		    	Match match = scoutingService.findMatchById(Integer.parseInt((String) newValue));
		    	
				viewHelper.setSelectedMatch(scoutingService.getAllMatches().indexOf(match));
				
				getActivity().invalidateOptionsMenu();
				
				ActionBar actionBar = getActivity().getActionBar();
				actionBar.setSelectedNavigationItem(0);
				
		        return false;
		    }
		});
		
		// Handle new match
		Preference pref = (Preference) findPreference("NewMatch");
		pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				SettingsNewMatch settingsNewMatch = new SettingsNewMatch();

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(settingsNewMatch.getFragmentId(), settingsNewMatch, settingsNewMatch.getFragmentTag());
				transaction.addToBackStack(null);
				transaction.commit();
				return false;
			}
        });
		
		// Populate match list
		PreferenceCategory prefCat = (PreferenceCategory) findPreference("Matches");
		PreferenceScreen prefScreen;
		matches = scoutingService.getAllMatches();
		for (Match match : matches) {
			prefScreen = getPreferenceManager().createPreferenceScreen(getActivity());
			String home;
			String visitor;
			if(match.isVisitor() == false){
				home = match.getTeam().getName();
				visitor = match.getOpponent();
			}
			else{
				home = match.getOpponent();
				visitor = match.getTeam().getName();
			}
			prefScreen.setTitle(home);
			prefScreen.setSummary(visitor);
			prefScreen.setKey(Integer.toString(matches.indexOf(match)));
			prefScreen.setOnPreferenceClickListener(myMatchListener);
			prefCat.addPreference(prefScreen);
		}
		
		// Handle new team
		pref = (Preference) findPreference("NewTeam");
		pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				SettingsNewTeam settingsNewTeam = new SettingsNewTeam();

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(settingsNewTeam.getFragmentId(), settingsNewTeam, settingsNewTeam.getFragmentTag());
				transaction.addToBackStack(null);
				transaction.commit();
				return false;
			}
        });
		
		// Populate team list
		prefCat = (PreferenceCategory) findPreference("Teams");
		List<Team> teams = scoutingService.getAllTeams();
		
		for (Team team : scoutingService.getAllTeams()) {
			prefScreen = getPreferenceManager().createPreferenceScreen(getActivity());
			prefScreen.setTitle(team.getName());
			prefScreen.setKey(Integer.toString(teams.indexOf(team)));
			prefScreen.setOnPreferenceClickListener(myTeamListener);
			prefCat.addPreference(prefScreen);
		}
		
		// Handle new player
		pref = (Preference) findPreference("NewPlayer");
		pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				SettingsNewPlayer settingsNewPlayer = new SettingsNewPlayer();

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(settingsNewPlayer.getFragmentId(), settingsNewPlayer, settingsNewPlayer.getFragmentTag());
				transaction.addToBackStack(null);
				transaction.commit();
				return false;
			}
        });
		
		// Populate player list
		prefCat = (PreferenceCategory) findPreference("Players");
		List<Player> players = scoutingService.getAllPlayers();
		
		for (Player player : players) {
			prefScreen = getPreferenceManager().createPreferenceScreen(getActivity());
			prefScreen.setTitle(player.getName() + " (" + player.getNumber() + ")");
			prefScreen.setKey(Integer.toString(players.indexOf(player)));
			prefScreen.setOnPreferenceClickListener(myPlayerListener);
			prefCat.addPreference(prefScreen);
		}
	}
	
	private OnPreferenceClickListener myMatchListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			SettingsNewMatch settingsNewMatch = new SettingsNewMatch();
			settingsNewMatch.setMatch(scoutingService.findMatchById(Integer.parseInt(preference.getKey())));

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(settingsNewMatch.getFragmentId(), settingsNewMatch, settingsNewMatch.getFragmentTag());
			transaction.addToBackStack(null);
			transaction.commit();
			return false;
		}
	};
	
	private OnPreferenceClickListener myTeamListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			SettingsNewTeam settingsNewTeam = new SettingsNewTeam();
			settingsNewTeam.setTeam(scoutingService.findTeamById(Integer.parseInt(preference.getKey())));

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(settingsNewTeam.getFragmentId(), settingsNewTeam, settingsNewTeam.getFragmentTag());
			transaction.addToBackStack(null);
			transaction.commit();
			return false;
		}
	};
	
	private OnPreferenceClickListener myPlayerListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			SettingsNewPlayer settingsNewPlayer = new SettingsNewPlayer();
			settingsNewPlayer.setPlayer(scoutingService.findPlayerById(Integer.parseInt(preference.getKey())));

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(settingsNewPlayer.getFragmentId(), settingsNewPlayer, settingsNewPlayer.getFragmentTag());
			transaction.addToBackStack(null);
			transaction.commit();
			return false;
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void setScoutingService(ScoutingService scoutingService) {
		SettingsOverview.scoutingService = scoutingService;
	}
}
