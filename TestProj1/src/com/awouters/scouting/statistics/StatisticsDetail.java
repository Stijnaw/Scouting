package com.awouters.scouting.statistics;

import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awouters.scouting.R;
import com.awouters.scouting.server.ScoutingService;
import com.awouters.scouting.src.ActionScore;
import com.awouters.scouting.src.ActionType;
import com.awouters.scouting.src.Stats;

public class StatisticsDetail extends Fragment {
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
		
		View V = inflater.inflate(R.layout.fragment_statisticsdetail, container, false);
		
		if(stats != null){
            TextView tv;
            
            ImageView imageView = (ImageView) V.findViewById(R.id.stats_picture);
            BitmapDrawable d = new BitmapDrawable(getResources(), stats.getPlayer().getPicture());
            imageView.setImageDrawable(d);
            
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

	public void setScoutingService(ScoutingService scoutingService) {
		StatisticsDetail.scoutingService = scoutingService;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	public boolean isStatsSet(){
		return (stats != null);
	}
}
