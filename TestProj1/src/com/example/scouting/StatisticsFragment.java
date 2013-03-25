package com.example.scouting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Action;
import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Player;
import com.example.scouting.src.Stats;
import com.example.testproj1.R;

public class StatisticsFragment extends Fragment {

	@SuppressWarnings("unused")
	private OnStatisticsFragmentInteractionListener mListener;
	private ViewHelper viewHelper;
	private ScoutingService scoutingService;

	public StatisticsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View V = inflater.inflate(R.layout.fragment_statistics, container, false);
		
		if(viewHelper.getSelectedMatch() != null){
			ListView listView = (ListView) V.findViewById(R.id.stats_listview);
			
			ArrayList<Stats> stats = new ArrayList<Stats>();
			ArrayList<Player> players = viewHelper.getSelectedMatch().getTeam().getPlayers();
			
			Stats total = new Stats(null);
			
			for(Player player: players){
				stats.add(new Stats(player));
			}
			
			for(Action action: viewHelper.getSelectedMatch().getCurrentSet().getActions()){
				stats.get(players.indexOf(action.getPlayer())).add(action.getActionType(), action.getActionScore());
				
				total.add(action.getActionType(), action.getActionScore());
        	}
        	
        	Collections.sort(stats, new Comparator<Stats>() {
				@Override
				public int compare(Stats s1, Stats s2) {
					Integer points = s2.getPoints().compareTo(s1.getPoints());
					Integer service = s2.getCount(ActionType.Service).compareTo(s1.getCount(ActionType.Service));
					Integer reception = s2.getCount(ActionType.Reception).compareTo(s1.getCount(ActionType.Reception));
					Integer attack = s2.getCount(ActionType.Attack).compareTo(s1.getCount(ActionType.Attack));
					Integer block = s2.getCount(ActionType.Block).compareTo(s1.getCount(ActionType.Block));
					return (points == 0 ? (service == 0 ? (reception == 0 ? (attack == 0 ? block : attack) : reception) : service) : points);
				}
        	});
			
			StatisticsArrayAdapter adapter = new StatisticsArrayAdapter(getActivity(), stats);
			
			//View header = inflater.inflate(R.layout.statistics_listrow, null);
			
            TextView tv;
            
            tv = (TextView) V.findViewById(R.id.stats_name);
            tv.setText("Totaal");
            
            tv = (TextView) V.findViewById(R.id.stats_totalPoints);
            tv.setText(Integer.toString(total.getPoints()));

            tv = (TextView) V.findViewById(R.id.stats_receptionAmount);
            tv.setText(Integer.toString(total.getCount(ActionType.Reception)));
            
            tv = (TextView) V.findViewById(R.id.stats_receptionPP);
            tv.setText(Integer.toString(total.getCount(ActionType.Reception, ActionScore.PlusPlus)+total.getCount(ActionType.Dig, ActionScore.PlusPlus)));
            
            tv = (TextView) V.findViewById(R.id.stats_receptionMM);
            tv.setText(Integer.toString(total.getCount(ActionType.Reception, ActionScore.MinusMinus)+total.getCount(ActionType.Dig, ActionScore.MinusMinus)));
            
            tv = (TextView) V.findViewById(R.id.stats_attackAmount);
            tv.setText(Integer.toString(total.getCount(ActionType.Attack)));
            
            tv = (TextView) V.findViewById(R.id.stats_attackPP);
            tv.setText(Integer.toString(total.getCount(ActionType.Attack, ActionScore.PlusPlus)));	
            
            tv = (TextView) V.findViewById(R.id.stats_attackMM);
            tv.setText(Integer.toString(total.getCount(ActionType.Attack, ActionScore.MinusMinus)));
            
            tv = (TextView) V.findViewById(R.id.stats_serveAmount);
            tv.setText(Integer.toString(total.getCount(ActionType.Service)));
            
            tv = (TextView) V.findViewById(R.id.stats_servePP);
            tv.setText(Integer.toString(total.getCount(ActionType.Service, ActionScore.PlusPlus)));
            
            tv = (TextView) V.findViewById(R.id.stats_serveMM);
            tv.setText(Integer.toString(total.getCount(ActionType.Service, ActionScore.MinusMinus)));
            
            tv = (TextView) V.findViewById(R.id.stats_blockAmount);
            tv.setText(Integer.toString(total.getCount(ActionType.Block)));
            
            tv = (TextView) V.findViewById(R.id.stats_blockPP);
            tv.setText(Integer.toString(total.getCount(ActionType.Block, ActionScore.PlusPlus)));
            
            tv = (TextView) V.findViewById(R.id.stats_blockMM);
            tv.setText(Integer.toString(total.getCount(ActionType.Block, ActionScore.MinusMinus)));

	        //listView.addFooterView(header);
	
			listView.setAdapter(adapter);
		}
		
		return V;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnStatisticsFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnStatisticsFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnStatisticsFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onStatisticsFragmentInteraction();
	}

	public void setViewHelper(ViewHelper viewHelper) {
		this.viewHelper = viewHelper;
	}

	public void setScoutingService(ScoutingService scoutingService) {
		this.scoutingService = scoutingService;
	}

}
