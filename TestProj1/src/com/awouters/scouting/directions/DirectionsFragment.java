package com.awouters.scouting.directions;

import com.awouters.scouting.R;
import com.awouters.scouting.R.layout;
import com.awouters.scouting.helpers.ViewHelper;
import com.awouters.scouting.scouting.ScoutingFragment;
import com.awouters.scouting.server.ScoutingService;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DirectionsFragment extends Fragment {
	private static ScoutingService scoutingService;
	static private ViewHelper viewHelper;

	public DirectionsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		viewHelper = new ViewHelper();
		
		getActivity().invalidateOptionsMenu();
		
		return inflater.inflate(R.layout.fragment_directions, container, false);
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		DirectionsFragment.scoutingService = scoutingService;
	}
}
