package com.example.scouting.settings;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Player;
import com.example.scouting.src.Team;
import com.example.testproj1.R;

public class SettingsNewTeam extends PreferenceFragment {

	private static ScoutingService scoutingService;
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
			name.setTitle(teamCopy.getName());
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
				
				showFragment(settingsOverview);
				
				return false;
			}
        });
		
		delete.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				
			    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
			    
			    alertDialog.setNegativeButton("Nee", null);

			    alertDialog.setMessage("Wil je team verwijderen?");
			    alertDialog.setTitle("Verwijderen?");

			    alertDialog.setPositiveButton("Ja", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						scoutingService.removeTeam(team);
						
						// Refresh the settings overview after edit
						SettingsOverview settingsOverview = new SettingsOverview();
						
						showFragment(settingsOverview);
					}
				});

			    alertDialog.show();
				
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
			settingsNewTeam.setTeam(team, teamCopy);

			showDetailsFragment(settingsNewTeam);
			return false;
		}
	};
	
	private OnPreferenceClickListener myOtherPlayerListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			teamCopy.addPlayer(scoutingService.findPlayerById(Integer.parseInt(preference.getKey())));
			teamCopy.setName(((EditTextPreference) findPreference("Name")).getText());
			
			SettingsNewTeam settingsNewTeam = new SettingsNewTeam();
			settingsNewTeam.setTeam(team, teamCopy);

			showDetailsFragment(settingsNewTeam);
			return false;
		}
	};

	public void setScoutingService(ScoutingService scoutingService) {
		SettingsNewTeam.scoutingService = scoutingService;
	}

	public void setTeam(Team team) {
		this.teamCopy = new Team(team);
		this.team = team;
	}
	
	public void setTeam(Team team, Team teamCopy) {
		this.teamCopy = teamCopy;
		this.team = team;
	}
	
	private void showDetailsFragment(Fragment fragment){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		if(getActivity().findViewById(R.id.frameSettingsDetail) != null){
			transaction.replace(R.id.frameSettingsDetail, fragment);
		}
		else{
			transaction.replace(R.id.SettingsLayout, fragment);
		}
		
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private void showFragment(Fragment fragment){
		FragmentManager fragMan = getFragmentManager();
		FragmentTransaction transaction = fragMan.beginTransaction();
		
		if(getActivity().findViewById(R.id.frameSettingsDetail) != null){
			transaction.remove(this);
		}

		transaction.replace(R.id.frameSettingsOverview, fragment, "SettingsOverview");
		transaction.commit();
	}
}
