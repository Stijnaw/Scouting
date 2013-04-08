package com.awouters.scouting.settings;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awouters.scouting.R;
import com.awouters.scouting.server.ScoutingService;
import com.awouters.scouting.src.Player;
import com.awouters.scouting.src.Team;

public class SettingsTeam extends PreferenceFragment {

	private static ScoutingService scoutingService;
	private Team team = null;
	private Team teamCopy = null;

	public SettingsTeam() {
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
				showFragment();
				
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
						showFragment();
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
			
			showDetailsFragment();
			return false;
		}
	};
	
	private OnPreferenceClickListener myOtherPlayerListener = new OnPreferenceClickListener(){
		@Override
		public boolean onPreferenceClick(Preference preference) {
			teamCopy.addPlayer(scoutingService.findPlayerById(Integer.parseInt(preference.getKey())));
			teamCopy.setName(((EditTextPreference) findPreference("Name")).getText());
			
			showDetailsFragment();
			return false;
		}
	};

	public void setScoutingService(ScoutingService scoutingService) {
		SettingsTeam.scoutingService = scoutingService;
	}

	public void setTeam(Team team) {
		this.teamCopy = new Team(team);
		this.team = team;
	}
	
	public void setTeam(Team team, Team teamCopy) {
		this.teamCopy = teamCopy;
		this.team = team;
	}
	
	private void showDetailsFragment(){
		SettingsTeam fragment = new SettingsTeam();
		fragment.setTeam(team, teamCopy);
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		if(getActivity().findViewById(R.id.frameSettingsDetail) != null){
			transaction.replace(R.id.frameSettingsDetail, fragment, "FragmentNewTeam");
		}
		else{
			transaction.replace(R.id.frameSettingsOverview, fragment);
		}
		
		transaction.commit();
	}

	private void showFragment(){
		SettingsOverview fragment = new SettingsOverview();
		
		FragmentManager fragMan = getFragmentManager();
		FragmentTransaction transaction = fragMan.beginTransaction();
		
		if(getActivity().findViewById(R.id.frameSettingsDetail) != null){
			transaction.remove(this);
		}

		transaction.replace(R.id.frameSettingsOverview, fragment, "FragmentSettingsOverview");
		transaction.commit();
	}
}
