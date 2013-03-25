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

import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Action;
import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Stats;
import com.example.testproj1.R;

public class StatisticsDetail extends Fragment {

	@SuppressWarnings("unused")
	private OnStatisticsDetailFragmentInteractionListener mListener;
	private static ViewHelper viewHelper;
	private static ScoutingService scoutingService;
	private Stats stats;

	public StatisticsDetail() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		container.removeAllViews();
		
		View V = inflater.inflate(R.layout.fragment_statisticsdetail, container, false);
		
		if(stats != null){
            TextView tv;
            
            tv = (TextView) V.findViewById(R.id.stats_name);
            tv.setText(stats.getPlayer().getName());
            
            tv = (TextView) V.findViewById(R.id.stats_number);
            tv.setText("#" + Integer.toString(stats.getPlayer().getNumber()));

            
            tv = (TextView) V.findViewById(R.id.stats_receptionAmount);
            tv.setText(Integer.toString(stats.getCount(ActionType.Reception)));
            
            tv = (TextView) V.findViewById(R.id.stats_receptionPP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Reception, ActionScore.PlusPlus)));
            
            tv = (TextView) V.findViewById(R.id.stats_receptionP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Reception, ActionScore.Plus)));
            
            tv = (TextView) V.findViewById(R.id.stats_receptionN);
            tv.setText(Integer.toString(stats.getCount(ActionType.Reception, ActionScore.Null)));
            
            tv = (TextView) V.findViewById(R.id.stats_receptionM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Reception, ActionScore.Minus)));
            
            tv = (TextView) V.findViewById(R.id.stats_receptionMM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Reception, ActionScore.MinusMinus)));
            
            
            tv = (TextView) V.findViewById(R.id.stats_digAmount);
            tv.setText(Integer.toString(stats.getCount(ActionType.Dig)));
            
            tv = (TextView) V.findViewById(R.id.stats_digPP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Dig, ActionScore.PlusPlus)));
            
            tv = (TextView) V.findViewById(R.id.stats_digP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Dig, ActionScore.Plus)));
            
            tv = (TextView) V.findViewById(R.id.stats_digN);
            tv.setText(Integer.toString(stats.getCount(ActionType.Dig, ActionScore.Null)));
            
            tv = (TextView) V.findViewById(R.id.stats_digM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Dig, ActionScore.Minus)));
            
            tv = (TextView) V.findViewById(R.id.stats_digMM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Dig, ActionScore.MinusMinus)));
            
            
            tv = (TextView) V.findViewById(R.id.stats_attackAmount);
            tv.setText(Integer.toString(stats.getCount(ActionType.Attack)));
            
            tv = (TextView) V.findViewById(R.id.stats_attackPP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Attack, ActionScore.PlusPlus)));	
            
            tv = (TextView) V.findViewById(R.id.stats_attackP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Attack, ActionScore.Plus)));	
            
            tv = (TextView) V.findViewById(R.id.stats_attackN);
            tv.setText(Integer.toString(stats.getCount(ActionType.Attack, ActionScore.Null)));	
            
            tv = (TextView) V.findViewById(R.id.stats_attackM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Attack, ActionScore.Minus)));	
            
            tv = (TextView) V.findViewById(R.id.stats_attackMM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Attack, ActionScore.MinusMinus)));
            
            
            tv = (TextView) V.findViewById(R.id.stats_serveAmount);
            tv.setText(Integer.toString(stats.getCount(ActionType.Service)));

            tv = (TextView) V.findViewById(R.id.stats_servePP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Service, ActionScore.PlusPlus)));

            tv = (TextView) V.findViewById(R.id.stats_serveP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Service, ActionScore.Plus)));

            tv = (TextView) V.findViewById(R.id.stats_serveN);
            tv.setText(Integer.toString(stats.getCount(ActionType.Service, ActionScore.Null)));

            tv = (TextView) V.findViewById(R.id.stats_serveM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Service, ActionScore.Minus)));
            
            tv = (TextView) V.findViewById(R.id.stats_serveMM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Service, ActionScore.MinusMinus)));
            
            
            tv = (TextView) V.findViewById(R.id.stats_blockAmount);
            tv.setText(Integer.toString(stats.getCount(ActionType.Block)));
            
            tv = (TextView) V.findViewById(R.id.stats_blockPP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Block, ActionScore.PlusPlus)));
            
            tv = (TextView) V.findViewById(R.id.stats_blockP);
            tv.setText(Integer.toString(stats.getCount(ActionType.Block, ActionScore.Plus)));

            tv = (TextView) V.findViewById(R.id.stats_blockN);
            tv.setText(Integer.toString(stats.getCount(ActionType.Block, ActionScore.Null)));

            tv = (TextView) V.findViewById(R.id.stats_blockM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Block, ActionScore.Minus)));
            
            tv = (TextView) V.findViewById(R.id.stats_blockMM);
            tv.setText(Integer.toString(stats.getCount(ActionType.Block, ActionScore.MinusMinus)));
		}
		
		return V;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnStatisticsDetailFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnStatisticsDetailFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnStatisticsDetailFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onStatisticsDetailFragmentInteraction();
	}

	public void setViewHelper(ViewHelper viewHelper) {
		StatisticsDetail.viewHelper = viewHelper;
	}

	public void setScoutingService(ScoutingService scoutingService) {
		StatisticsDetail.scoutingService = scoutingService;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}
}
