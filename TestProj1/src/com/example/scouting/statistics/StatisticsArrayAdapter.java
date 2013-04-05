package com.example.scouting.statistics;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Stats;
import com.example.testproj1.R;

public class StatisticsArrayAdapter extends ArrayAdapter<Stats> {

	private LayoutInflater mInflater;
	private ArrayList<Stats> stats;

	public StatisticsArrayAdapter(Context context, ArrayList<Stats> stats) {
		super(context, R.layout.statistics_listrow, stats);
		
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.stats = stats;
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.statistics_listrow, null);
            }
            
        	Stats stat = stats.get(position);
        	v.setTag(stat.getPlayer());
        
            TextView tv;
            tv = (TextView) v.findViewById(R.id.stats_number);
            tv.setText(Integer.toString(stat.getPlayer().getNumber()));
            
            tv = (TextView) v.findViewById(R.id.stats_name);
            tv.setText(stat.getPlayer().getName());
            
            tv = (TextView) v.findViewById(R.id.stats_totalPoints);
            tv.setText(Integer.toString(stat.getPoints()));

            tv = (TextView) v.findViewById(R.id.stats_receptionAmount);
            tv.setText(Integer.toString(stat.getCount(ActionType.Reception)));
            
            tv = (TextView) v.findViewById(R.id.stats_receptionPP);
            tv.setText(Integer.toString(stat.getCount(ActionType.Reception, ActionScore.PlusPlus)+stat.getCount(ActionType.Dig, ActionScore.PlusPlus)));
            
            tv = (TextView) v.findViewById(R.id.stats_receptionMM);
            tv.setText(Integer.toString(stat.getCount(ActionType.Reception, ActionScore.MinusMinus)+stat.getCount(ActionType.Dig, ActionScore.MinusMinus)));
            
            tv = (TextView) v.findViewById(R.id.stats_attackAmount);
            tv.setText(Integer.toString(stat.getCount(ActionType.Attack)));
            
            tv = (TextView) v.findViewById(R.id.stats_attackPP);
            tv.setText(Integer.toString(stat.getCount(ActionType.Attack, ActionScore.PlusPlus)));	
            
            tv = (TextView) v.findViewById(R.id.stats_attackMM);
            tv.setText(Integer.toString(stat.getCount(ActionType.Attack, ActionScore.MinusMinus)));
            
            tv = (TextView) v.findViewById(R.id.stats_serveAmount);
            tv.setText(Integer.toString(stat.getCount(ActionType.Service)));
            
            tv = (TextView) v.findViewById(R.id.stats_servePP);
            tv.setText(Integer.toString(stat.getCount(ActionType.Service, ActionScore.PlusPlus)));
            
            tv = (TextView) v.findViewById(R.id.stats_serveMM);
            tv.setText(Integer.toString(stat.getCount(ActionType.Service, ActionScore.MinusMinus)));
            
            tv = (TextView) v.findViewById(R.id.stats_blockAmount);
            tv.setText(Integer.toString(stat.getCount(ActionType.Block)));
            
            tv = (TextView) v.findViewById(R.id.stats_blockPP);
            tv.setText(Integer.toString(stat.getCount(ActionType.Block, ActionScore.PlusPlus)));
            
            tv = (TextView) v.findViewById(R.id.stats_blockMM);
            tv.setText(Integer.toString(stat.getCount(ActionType.Block, ActionScore.MinusMinus)));
            
            return v;
    }
}
