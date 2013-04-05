package com.example.scouting.settings;

import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

import com.example.scouting.helpers.ViewHelper;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Match;
import com.example.scouting.src.Team;
import com.example.testproj1.R;

public class SettingsNewMatch extends PreferenceFragment {

	private static ScoutingService scoutingService;
	private Match match;
	private static ViewHelper viewHelper;

	public SettingsNewMatch() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings_match);
		
		viewHelper = new ViewHelper();
		
		Preference save = findPreference("Save");
		Preference delete = findPreference("Delete");
		
		ListPreference teamList = (ListPreference) findPreference("TeamList");
		EditTextPreference opponent = (EditTextPreference) findPreference("Opponent");
		CheckBoxPreference visitor = (CheckBoxPreference) findPreference("Visitor");
	
		// Populate teamList
		int i = 0;
		List<Team> teams = scoutingService.getAllTeams();
		String entries[] = new String[teams.size()];
		String entryValues[] = new String[teams.size()];
		
        for (Team team: teams) {
            entries[i] = team.getName();
            entryValues[i] = Integer.toString(teams.indexOf(team));
            i++;
        }
        
        teamList.setEntries(entries);
		teamList.setEntryValues(entryValues);
		
		if(match != null){
			teamList.setTitle(match.getTeam().getName());
			teamList.setValueIndex(teams.indexOf(match.getTeam()));
			opponent.setTitle(match.getOpponent());
			opponent.setText(match.getOpponent());
			delete.setEnabled(true);
			visitor.setChecked(match.isVisitor());
		}
		else{
			opponent.setText("");
			visitor.setChecked(false);
		}
		
		save.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				ListPreference teamList = (ListPreference) findPreference("TeamList");
				EditTextPreference opponent = (EditTextPreference) findPreference("Opponent");
				CheckBoxPreference visitor = (CheckBoxPreference) findPreference("Visitor");
				
				if(match == null){
					Match myMatch = scoutingService.createNewMatch(scoutingService.findTeamById(Integer.parseInt(teamList.getValue())), opponent.getText(), visitor.isChecked());
					viewHelper.setSelectedMatch(scoutingService.getAllMatches().indexOf(myMatch));
				}
				else{
					match.setTeam(scoutingService.findTeamById(Integer.parseInt(teamList.getValue())));
					match.setOpponent(opponent.getText());
					match.setVisitor(visitor.isChecked());
					viewHelper.setSelectedMatch(scoutingService.getAllMatches().indexOf(match));
				}
				
				getActivity().invalidateOptionsMenu();
				
				ActionBar actionBar = getActivity().getActionBar();
				actionBar.setSelectedNavigationItem(0);
				
				return false;
			}
        });
		
		delete.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				
			    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
			    
			    alertDialog.setNegativeButton("Nee", null);

			    alertDialog.setMessage("Wil je deze match verwijderen?");
			    alertDialog.setTitle("Verwijderen?");

			    alertDialog.setPositiveButton("Ja", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						scoutingService.removeMatch(match);
						
						Match myMatch = scoutingService.findMatchById(viewHelper.getSelectedMatch());
						
						if(match == myMatch){
							viewHelper = null;
							getActivity().invalidateOptionsMenu();
						}
						
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

	public void setScoutingService(ScoutingService scoutingService) {
		SettingsNewMatch.scoutingService = scoutingService;
	}
	
	public void setMatch(Match match){
		this.match = match;
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
