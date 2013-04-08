package com.awouters.scouting.directions;

import com.awouters.scouting.R;
import com.awouters.scouting.R.layout;
import com.awouters.scouting.helpers.ViewHelper;
import com.awouters.scouting.scouting.Scouting;
import com.awouters.scouting.scouting.ScoutingButtons;
import com.awouters.scouting.server.ScoutingService;
import com.awouters.scouting.src.Match;
import com.awouters.scouting.src.Stats;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Directions extends Fragment {
	private static ScoutingService scoutingService;
	static private ViewHelper viewHelper;
	private Stats stats;

	public Directions() {
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
		
		DirectionsOverview directionsOverview = new DirectionsOverview();
		directionsOverview.setScoutingService(scoutingService);
		
		DirectionsFieldPager directionsFieldPager = new DirectionsFieldPager();
		directionsFieldPager.setScoutingService(scoutingService);
		
		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
		View V;
		
		if(match != null){
			V = inflater.inflate(R.layout.directions, container, false);
		}
		else{
			V = new View(getActivity());
		}
		
		return V;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		DirectionsOverview directionsOverview = new DirectionsOverview();
        
        if(getActivity().findViewById(R.id.frameDirectionsOverview) != null){
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.frameDirectionsOverview, directionsOverview, "FragmentDirectionsOverview");
			transaction.commit();
        }
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		Directions.scoutingService = scoutingService;
	}
	
	public void setStats(Stats stats) {
		this.stats = stats;
	}
}
