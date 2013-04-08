package com.awouters.scouting.scouting;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awouters.scouting.R;
import com.awouters.scouting.server.ScoutingFileDB;
import com.awouters.scouting.server.ScoutingService;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class Scouting extends Fragment {

	private static ScoutingService scoutingService;
	private static ScoutingFileDB scoutingFileDB;

	public Scouting() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		getActivity().invalidateOptionsMenu();
		
        ScoutingField scoutingField = new ScoutingField();
        scoutingField.setScoutingService(scoutingService);
        
		ScoutingButtons scoutingButtons = new ScoutingButtons();
        scoutingButtons.setScoutingService(scoutingService);
        scoutingButtons.setScoutingFileDB(scoutingFileDB);
		
		View V = inflater.inflate(R.layout.scouting, container, false);
		
		return V;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		ScoutingButtons scoutingButtons = new ScoutingButtons();
        
        if(getActivity().findViewById(R.id.frameScoutingButtons) != null){
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.frameScoutingButtons, scoutingButtons, "FragmentScoutingButtons");
			transaction.commit();
        }
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		Scouting.scoutingService = scoutingService;
	}
	
	public void setScoutingFileDB(ScoutingFileDB scoutingFileDB) {
		Scouting.scoutingFileDB = scoutingFileDB;
	}
}
