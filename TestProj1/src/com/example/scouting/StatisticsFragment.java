package com.example.scouting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Action;
import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Set;
import com.example.scouting.src.Stats;
import com.example.testproj1.R;

public class StatisticsFragment extends Fragment {

	@SuppressWarnings("unused")
	private OnStatisticsFragmentInteractionListener mListener;
	private boolean totalOverview = false;
	private static ViewHelper viewHelper;
	private static ScoutingService scoutingService;

	public StatisticsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		viewHelper = new ViewHelper();
		
		StatisticsDetail statisticsDetail = new StatisticsDetail();
		statisticsDetail.setScoutingService(scoutingService);
		statisticsDetail.setViewHelper(viewHelper);

		if(viewHelper.getSelectedFragment() != null && statisticsDetail.isStatsSet() == true){
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(android.R.id.content, statisticsDetail);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
		View V;
		
		if(match != null){
			V = inflater.inflate(R.layout.fragment_statistics, null);
			
			ListView listView = (ListView) V.findViewById(R.id.stats_listview);
			
			final ArrayList<Stats> stats = new ArrayList<Stats>();
			ArrayList<Player> players = match.getTeam().getPlayers();
			
			Stats total = new Stats(null);
			
			for(Player player: players){
				stats.add(new Stats(player));
			}
			
			if(totalOverview == true){
				for(Set set: match.getSets()){
					for(Action action: set.getActions()){
						stats.get(players.indexOf(action.getPlayer())).add(action.getActionType(), action.getActionScore());
						
						total.add(action.getActionType(), action.getActionScore());
		        	}
		        	
				}
			}
			else{
				for(Action action: match.getCurrentSet().getActions()){
					stats.get(players.indexOf(action.getPlayer())).add(action.getActionType(), action.getActionScore());
					
					total.add(action.getActionType(), action.getActionScore());
	        	}
			}
        	
			
        	Collections.sort(stats, new Comparator<Stats>() {
				@Override
				public int compare(Stats s1, Stats s2) {
					Integer points = s2.getComparePoints().compareTo(s1.getComparePoints());
					Integer service = s2.getCount(ActionType.Service).compareTo(s1.getCount(ActionType.Service));
					Integer reception = s2.getCount(ActionType.Reception).compareTo(s1.getCount(ActionType.Reception));
					Integer attack = s2.getCount(ActionType.Attack).compareTo(s1.getCount(ActionType.Attack));
					Integer block = s2.getCount(ActionType.Block).compareTo(s1.getCount(ActionType.Block));
					return (points == 0 ? (service == 0 ? (reception == 0 ? (attack == 0 ? block : attack) : reception) : service) : points);
				}
        	});
			
			StatisticsArrayAdapter adapter = new StatisticsArrayAdapter(getActivity(), stats);
			
            TextView tv;
            
            tv = (TextView) V.findViewById(R.id.stats_name);
            tv.setText("Totaal");
            tv.setOnClickListener(new OnClickListener() {
				
            	@Override
				public void onClick(View v) {
            		StatisticsFragment fragment = new StatisticsFragment();
            		fragment.setTotal(true);
        			FragmentTransaction transaction = getFragmentManager().beginTransaction();
        			transaction.replace(android.R.id.content, fragment);
        			transaction.addToBackStack(null);
        			transaction.commit();
				}
			});
            
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

			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
					StatisticsDetail statisticsDetail = new StatisticsDetail();
					statisticsDetail.setStats((Stats) v.getTag());

					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.replace(android.R.id.content, statisticsDetail);
					transaction.addToBackStack(null);
					transaction.commit();
					
					viewHelper.setSelectedFragment(1);
				}
			});
		}
		else{
			V = new View(getActivity());
		}
		
		return V;
	}

	protected void setTotal(boolean total) {
		this.totalOverview  = total;
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
	
	public void setScoutingService(ScoutingService scoutingService) {
		StatisticsFragment.scoutingService = scoutingService;
	}

}
