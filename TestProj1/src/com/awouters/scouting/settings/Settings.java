package com.awouters.scouting.settings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.awouters.scouting.R;
import com.awouters.scouting.server.ScoutingService;

public class Settings extends Fragment {

	private static ScoutingService scoutingService;

	public Settings() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View V = inflater.inflate(R.layout.settings, container, false);
		
		getActivity().invalidateOptionsMenu();
		
		return V;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		SettingsOverview settingsOverview = new SettingsOverview();
		settingsOverview.setScoutingService(scoutingService);

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.frameSettingsOverview, settingsOverview, "FragmentSettingsOverview");
		transaction.commit();
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		Settings.scoutingService = scoutingService;
	}
}
