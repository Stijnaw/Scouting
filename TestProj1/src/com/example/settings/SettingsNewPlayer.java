package com.example.settings;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

import com.example.scouting.ViewHelper;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Player;
import com.example.testproj1.R;

public class SettingsNewPlayer extends PreferenceFragment {

	@SuppressWarnings("unused")
	private OnSettingsNewPlayerFragmentInteractionListener mListener;
	private ScoutingService scoutingService;
	private Player player = null;
	private ViewHelper viewHelper;

	public SettingsNewPlayer() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings_player);
		
		Preference save = (Preference) findPreference("Save");
		Preference delete = (Preference) findPreference("Delete");
		
		EditTextPreference name = (EditTextPreference) findPreference("Name");
		EditTextPreference number = (EditTextPreference) findPreference("Number");
		
		if(player != null){
			name.setText(player.getName());
			number.setText(Integer.toString(player.getNumber()));
			delete.setEnabled(true);
		}
		else{
			name.setText("");
			number.setText("");
		}
		
		save.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				EditTextPreference name = (EditTextPreference) findPreference("Name");
				EditTextPreference number = (EditTextPreference) findPreference("Number");
				
				if(player == null){
					scoutingService.createNewPlayer(name.getText(), Integer.parseInt(number.getText()));
				}
				else{
					player.setName(name.getText());
					player.setNumber(Integer.parseInt(number.getText()));
				}
				
				// Refresh the settings overview after edit
				SettingsOverview settingsOverview = new SettingsOverview();
				settingsOverview.setScoutingService(scoutingService);
				settingsOverview.setViewHelper(viewHelper);
				
				FragmentManager fragMan = getFragmentManager();
				FragmentTransaction transaction = fragMan.beginTransaction();
				transaction.remove(fragMan.findFragmentByTag("SettingsNewPlayer"));
				transaction.replace(R.id.frameSettingsOverview, settingsOverview, "SettingsOverview");
				transaction.commit();
				
				return false;
			}
        });
		
		delete.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				scoutingService.removePlayer(player);
				
				// Refresh the settings overview after edit
				SettingsOverview settingsOverview = new SettingsOverview();
				settingsOverview.setScoutingService(scoutingService);
				settingsOverview.setViewHelper(viewHelper);
				
				FragmentManager fragMan = getFragmentManager();
				FragmentTransaction transaction = fragMan.beginTransaction();
				transaction.remove(fragMan.findFragmentByTag("SettingsNewPlayer"));
				transaction.replace(R.id.frameSettingsOverview, settingsOverview, "SettingsOverview");
				transaction.commit();
				
				return false;
			}
        });
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnSettingsNewPlayerFragmentInteractionListener) activity;
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

	public interface OnSettingsNewPlayerFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onSettingsNewPlayerFragmentInteraction();
	}

	public void setScoutingService(ScoutingService scoutingService) {
		this.scoutingService = scoutingService;
	}

	public void setPlayer(Player player) {
		this.player  = player;
	}

	public void setViewHelper(ViewHelper viewHelper) {
		this.viewHelper = viewHelper;
	}
}
