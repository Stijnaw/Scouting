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
public class ScoutingFragment extends Fragment {

	private static ScoutingService scoutingService;
	private static ScoutingFileDB scoutingFileDB;

	public ScoutingFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		getActivity().invalidateOptionsMenu();
		
        ScoutingFragmentField scoutingFragmentField = new ScoutingFragmentField();
        scoutingFragmentField.setScoutingService(scoutingService);
        
		ScoutingFragmentButtons scoutingFragmentButtons = new ScoutingFragmentButtons();
        scoutingFragmentButtons.setScoutingService(scoutingService);
        scoutingFragmentButtons.setScoutingFileDB(scoutingFileDB);
		
		View V = inflater.inflate(R.layout.fragment_scouting, container, false);
		
		return V;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		ScoutingFragmentButtons scoutingFragmentButtons = new ScoutingFragmentButtons();
        
        if(getActivity().findViewById(R.id.frameScoutingButtons) != null){
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.frameScoutingButtons, scoutingFragmentButtons, "FragmentScoutingButtons");
			transaction.commit();
        }
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		ScoutingFragment.scoutingService = scoutingService;
	}
	
	public void setScoutingFileDB(ScoutingFileDB scoutingFileDB) {
		ScoutingFragment.scoutingFileDB = scoutingFileDB;
	}
}
