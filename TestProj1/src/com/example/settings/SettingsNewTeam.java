package com.example.settings;

import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.example.scouting.ViewHelper;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Player;
import com.example.scouting.src.Team;
import com.example.testproj1.R;

public class SettingsNewTeam extends PreferenceFragment {

	@SuppressWarnings("unused")
	private OnSettingsNewTeamFragmentInteractionListener mListener;
	private ScoutingService scoutingService;
	private Team team = null;
	private Team teamCopy = null;

	public SettingsNewTeam() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings_team);
		
		Preference save = (Preference) findPreference("Save");
		Preference delete = (Preference) findPreference("Delete");
		
		EditTextPreference name = (EditTextPreference) findPreference("Name");
		
		if(teamCopy == null){
			teamCopy = new Team();
		}
		
		if(teamCopy.getName() != ""){
			delete.setEnabled(true);
		}
		
		name.setText(teamCopy.getName());
		
		// Populate player list
		PreferenceCategory prefCat = (PreferenceCategory) findPreference("Players");
		List<Player> players = teamCopy.getPlayers();
		
		PreferenceScreen prefScreen;
		for (Player player : players) {
			prefScreen = getPreferenceManager().createPreferenceScreen(getActivity());
			prefScreen.setTitle(player.getName() + " (" + player.getNumber() + ")");
			prefScreen.setKey(Integer.toString(players.indexOf(player)));
			prefScreen.setOnPreferenceClickListener(myPlayerListener);
			prefCat.addPreference(prefScreen);
		}
		
		// populate other players
		prefCat = (PreferenceCategory) findPreference("OtherPlayers");
		players = scoutingService.getAllPlayers();
		
		for (Player player : players) {
			if(teamCopy.getPlayers().contains(player) == false){
				prefScreen = getPreferenceManager().createPreferenceScreen(getActivity());
				prefScreen.setTitle(player.getName() + " (" + player.getNumber() + ")");
				prefScreen.setKey(Integer.toString(players.indexOf(player)));
				prefScreen.setOnPreferenceClickListener(myOtherPlayerListener);
				prefCat.addPreference(prefScreen);	
			}
		}
		
		save.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				EditTextPreference name = (EditTextPreference) findPreference("Name");
				
				if(team == null){
					team = scoutingService.createNewTeam(name.getText());
				}
				else{
					team.setName(name.getText());
				}
				
				team.setPlayers(teamCopy.getPlayers());
				
				// Refresh the settings overview after edit
				SettingsOverview settingsOverview = new SettingsOverview();
				settingsOverview.setScoutingService(scoutingService);
				settingsOverview.setViewHelper(viewHelper);
				
				FragmentManager fragMan = getFragmentManager();
				FragmentTransaction transaction = fragMan.beginTransaction();
				transaction.remove(fragMan.findFragmentByTag("SettingsNewTeam"));
				transaction.replace(R.id.frameSettingsOverview, settingsOverview, "SettingsOverview");
				transaction.commit();
				
				return false;
			}
        });
		
		delete.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				scoutingService.removeTeam(team);
				
				// Refresh the settings overview after edit
				SettingsOverview settingsOverview = new SettingsOverview();
				settingsOverview.setScoutingService(scoutingService);
				settingsOverview.setViewHelper(viewHelper);
				
				FragmentManager fragMan = getFragmentManager();
				FragmentTransaction transaction = fragMan.beginTransaction();
				transaction.remove(fragMan.findFragmentByTag("SettingsNewTeam"));
				transaction.replace(R.id.frameSettingsOverview, settingsOverview, "SettingsOverview");
				transaction.commit();
				return false;
			}
        });
	}
	
	private OnPreferenceClickListener myPlayerListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			teamCopy.removePlayer(teamCopy.getPlayers().remove(Integer.parseInt(preference.getKey())));
			teamCopy.setName(((EditTextPreference) findPreference("Name")).getText());
			
			SettingsNewTeam settingsNewTeam = new SettingsNewTeam();
			settingsNewTeam.setScoutingService(scoutingService);
			settingsNewTeam.setViewHelper(viewHelper);
			settingsNewTeam.setTeam(team, teamCopy);

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.frameSettingsDetail, settingsNewTeam, "SettingsNewTeam");
			transaction.commit();
			return false;
		}
	};
	
	private OnPreferenceClickListener myOtherPlayerListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			teamCopy.addPlayer(scoutingService.findPlayerById(Integer.parseInt(preference.getKey())));
			teamCopy.setName(((EditTextPreference) findPreference("Name")).getText());
			
			SettingsNewTeam settingsNewTeam = new SettingsNewTeam();
			settingsNewTeam.setScoutingService(scoutingService);
			settingsNewTeam.setViewHelper(viewHelper);
			settingsNewTeam.setTeam(team, teamCopy);

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.frameSettingsDetail, settingsNewTeam, "SettingsNewTeam");
			transaction.commit();
			return false;
		}
	};
	private ViewHelper viewHelper;
	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnSettingsNewTeamFragmentInteractionListener) activity;
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

	public interface OnSettingsNewTeamFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onSettingsNewTeamFragmentInteraction();
	}

	public void setScoutingService(ScoutingService scoutingService) {
		this.scoutingService = scoutingService;
	}

	public void setTeam(Team team) {
		this.teamCopy = new Team(team);
		this.team = team;
	}
	
	public void setTeam(Team team, Team teamCopy) {
		this.teamCopy = teamCopy;
		this.team = team;
	}

	public void setViewHelper(ViewHelper viewHelper) {
		this.viewHelper = viewHelper;
	}
}
