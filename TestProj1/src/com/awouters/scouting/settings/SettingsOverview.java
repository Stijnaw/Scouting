package com.awouters.scouting.settings;

import java.util.List;

import android.R.anim;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.Toast;

import com.awouters.scouting.R;
import com.awouters.scouting.helpers.ViewHelper;
import com.awouters.scouting.server.ScoutingService;
import com.awouters.scouting.src.Match;
import com.awouters.scouting.src.Player;
import com.awouters.scouting.src.Team;

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
		
		SettingsNewTeam settingsNewTeam = new SettingsNewTeam();
		settingsNewTeam.setScoutingService(scoutingService);

		SettingsNewPlayer settingsNewPlayer = new SettingsNewPlayer();
		settingsNewPlayer.setScoutingService(scoutingService);
		
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

				showDetailsFragment(settingsNewMatch, "FragmentNewMatch");
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

				showDetailsFragment(settingsNewTeam, "FragmentNewTeam");
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

				showDetailsFragment(settingsNewPlayer, "FragmentNewPlayer");
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

			showDetailsFragment(settingsNewMatch, "FragmentNewMatch");
			return false;
		}
	};
	
	private OnPreferenceClickListener myTeamListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			SettingsNewTeam settingsNewTeam = new SettingsNewTeam();
			settingsNewTeam.setTeam(scoutingService.findTeamById(Integer.parseInt(preference.getKey())));

			showDetailsFragment(settingsNewTeam, "FragmentNewTeam");
			return false;
		}
	};
	
	private OnPreferenceClickListener myPlayerListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			SettingsNewPlayer settingsNewPlayer = new SettingsNewPlayer();
			settingsNewPlayer.setPlayer(scoutingService.findPlayerById(Integer.parseInt(preference.getKey())));

			showDetailsFragment(settingsNewPlayer, "FragmentNewPlayer");
			return false;
		}
	};
	
	private void showDetailsFragment(Fragment fragment, String tag){
		FragmentManager fragMan =  getFragmentManager();
		FragmentTransaction transaction = fragMan.beginTransaction();
		
		if(getActivity().findViewById(R.id.frameSettingsDetail) != null){
			transaction.replace(R.id.frameSettingsDetail, fragment, tag);
		}
		else{
			transaction.replace(R.id.frameSettingsOverview, fragment, tag);
		}
		
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void setScoutingService(ScoutingService scoutingService) {
		SettingsOverview.scoutingService = scoutingService;
	}
}
