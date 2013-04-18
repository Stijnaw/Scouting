package com.awouters.scouting.directions;

import com.awouters.scouting.R;
import com.awouters.scouting.R.layout;
import com.awouters.scouting.helpers.ViewHelper;
import com.awouters.scouting.scouting.ScoutingField;
import com.awouters.scouting.server.ScoutingService;
import com.awouters.scouting.src.Match;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class DirectionsOverview extends Fragment {

	private static ScoutingService scoutingService;
	private ViewHelper viewHelper;

	public DirectionsOverview() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		viewHelper = new ViewHelper();
		
		View V = inflater.inflate(R.layout.directions_overview, container, false);
		
		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
		
	    GridView gridview = (GridView) V.findViewById(R.id.fragmentDirectionsGridview);
	    gridview.setAdapter(new PlayerGridAdapter(getActivity(), match.getTeam().getPlayers()));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	DirectionsFieldPager fragment = new DirectionsFieldPager();
	        	fragment.setPlayer(position);
	        	
	            showFieldFragment(fragment);
	        }
	    });
		
		return V;
	}
	
	private void showFieldFragment(Fragment fragment){
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		if(getActivity().findViewById(R.id.frameDirectionsDetails) != null){
			transaction.replace(R.id.frameDirectionsDetails, fragment, "FragmentDirectionsField");
		}
		else{
			transaction.replace(R.id.frameDirectionsOverview, fragment, "FragmentDirectionsField");
		}
		
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		DirectionsOverview.scoutingService = scoutingService;
	}
}
