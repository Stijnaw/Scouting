package com.example.scouting;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scouting.server.ScoutingService;
import com.example.settings.SettingsOverview;
import com.example.testproj1.R;

public class SettingsFragment extends Fragment {

	private static ScoutingService scoutingService;

	public SettingsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View V = inflater.inflate(R.layout.fragment_settings, container, false);
		
		SettingsOverview settingsOverview = new SettingsOverview();
		settingsOverview.setScoutingService(scoutingService);

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.frameSettingsOverview, settingsOverview, "SettingsOverview");
		transaction.commit();
		
		return V;
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		SettingsFragment.scoutingService = scoutingService;
	}
}
