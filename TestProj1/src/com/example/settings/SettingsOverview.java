package com.example.settings;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.scouting.ViewHelper;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Set;
import com.example.scouting.src.Team;
import com.example.testproj1.R;

public class SettingsOverview extends PreferenceFragment {

	private OnSettingsOverviewFragmentInteractionListener mListener;
	private ScoutingService scoutingService;
	private ViewHelper viewHelper;

	public SettingsOverview() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		Editor editor = sharedPrefs.edit();
		editor.clear();
		editor.commit();
		
		addPreferencesFromResource(R.xml.settings_overview);
		
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
		
		if(viewHelper.getSelectedMatch() != null){
			Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
			matchList.setValueIndex(matches.indexOf(viewHelper.getSelectedMatch()));
		}
		
		matchList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {    
		    @Override
		    public boolean onPreferenceChange(Preference preference, Object newValue) {
		    	
		    	Match match = scoutingService.findMatchById(Integer.parseInt((String) newValue));
		    	
				viewHelper.setSelectedMatch(match);
				
				getActivity().invalidateOptionsMenu();
				
				// Refresh the settings overview after edit
				SettingsOverview settingsOverview = new SettingsOverview();
				settingsOverview.setScoutingService(scoutingService);
				settingsOverview.setViewHelper(viewHelper);
				
				FragmentManager fragMan = getFragmentManager();
				FragmentTransaction transaction = fragMan.beginTransaction();
				transaction.replace(R.id.frameSettingsOverview, settingsOverview, "SettingsOverview");
				transaction.commit();
		        return false;
		    }
		});
		
		// Handle new match
		Preference pref = (Preference) findPreference("NewMatch");
		pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				SettingsNewMatch settingsNewMatch = new SettingsNewMatch();
				settingsNewMatch.setScoutingService(scoutingService);
				settingsNewMatch.setViewHelper(viewHelper);

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.frameSettingsDetail, settingsNewMatch, "SettingsNewMatch");
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
				settingsNewTeam.setScoutingService(scoutingService);
				settingsNewTeam.setViewHelper(viewHelper);

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.frameSettingsDetail, settingsNewTeam, "SettingsNewTeam");
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
				settingsNewPlayer.setScoutingService(scoutingService);
				settingsNewPlayer.setViewHelper(viewHelper);

				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.frameSettingsDetail, settingsNewPlayer, "SettingsNewPlayer");
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
			settingsNewMatch.setScoutingService(scoutingService);
			settingsNewMatch.setViewHelper(viewHelper);
			settingsNewMatch.setMatch(scoutingService.findMatchById(Integer.parseInt(preference.getKey())));

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.frameSettingsDetail, settingsNewMatch, "SettingsNewMatch");
			transaction.commit();
			return false;
		}
	};
	
	private OnPreferenceClickListener myTeamListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			SettingsNewTeam settingsNewTeam = new SettingsNewTeam();
			settingsNewTeam.setScoutingService(scoutingService);
			settingsNewTeam.setViewHelper(viewHelper);
			settingsNewTeam.setTeam(scoutingService.findTeamById(Integer.parseInt(preference.getKey())));

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.frameSettingsDetail, settingsNewTeam, "SettingsNewTeam");
			transaction.commit();
			return false;
		}
	};
	
	private OnPreferenceClickListener myPlayerListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			SettingsNewPlayer settingsNewPlayer = new SettingsNewPlayer();
			settingsNewPlayer.setScoutingService(scoutingService);
			settingsNewPlayer.setViewHelper(viewHelper);
			settingsNewPlayer.setPlayer(scoutingService.findPlayerById(Integer.parseInt(preference.getKey())));

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.frameSettingsDetail, settingsNewPlayer, "SettingsNewPlayer");
			transaction.commit();
			return false;
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnSettingsOverviewFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnSettingsOverviewFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onSettingsOverviewFragmentInteraction();
	}

	public void setScoutingService(ScoutingService scoutingService) {
		this.scoutingService = scoutingService;
	}

	public void setViewHelper(ViewHelper viewHelper) {
		this.viewHelper = viewHelper;
	}
}
