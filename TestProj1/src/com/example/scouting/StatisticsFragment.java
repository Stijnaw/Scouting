package com.example.scouting;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Stats;
import com.example.testproj1.R;

public class StatisticsFragment extends Fragment {

	private ArrayList<Stats> stats;
	private Stats total;
	private static ViewHelper viewHelper;
	private static ScoutingService scoutingService;

	public StatisticsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		viewHelper = new ViewHelper();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View V = inflater.inflate(R.layout.fragment_statistics, container, false);
		
		ListView listView = (ListView) V.findViewById(R.id.stats_listview);
		
		StatisticsArrayAdapter adapter = new StatisticsArrayAdapter(getActivity(), stats);
		
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

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
				
				viewHelper.setSelectedFragment(1);
				
				StatisticsPager fragment = new StatisticsPager();
				fragment.setPlayer((Integer) v.getTag());
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(android.R.id.content, fragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		
		return V;
	}
	
	public void setStats(ArrayList<Stats> stats, Stats total){
		this.stats = stats;
		this.total = total;
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		StatisticsFragment.scoutingService = scoutingService;
	}

}
