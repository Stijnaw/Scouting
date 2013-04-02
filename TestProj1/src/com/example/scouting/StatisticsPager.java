package com.example.scouting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Action;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Set;
import com.example.scouting.src.Stats;
import com.example.testproj1.R;

public class StatisticsPager extends Fragment {
	
	private static ScoutingService scoutingService;
	private static ViewHelper viewHelper;
	private static Integer selectedSet = null;

	public StatisticsPager() {
		// Required empty public constructor
	}

	SetCollectionPagerAdapter mSetCollectionPagerAdapter;
	
    /**
     * The {@link android.support.v4.view.ViewPager} that will display the object collection.
     */
    ViewPager mViewPager;
	private static Integer player = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        viewHelper = new ViewHelper();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
		View V;
		
		Toast.makeText(getActivity(), "Oncreate viewpager", Toast.LENGTH_SHORT).show();
		
		if(match != null){
	    	V = inflater.inflate(R.layout.fragment_statistics_pager, container, false);
	    	
	        mSetCollectionPagerAdapter = new SetCollectionPagerAdapter(getFragmentManager());
	        
	        mViewPager = (ViewPager) V.findViewById(R.id.stats_pager);
	        mViewPager.setAdapter(mSetCollectionPagerAdapter);
	        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int i) {
					selectedSet = i;
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        
	        if(selectedSet == null){
	        	selectedSet = match.getCurrentSetNumber()-1;
	        }
	        
	        mViewPager.setCurrentItem(selectedSet);
		}
		else{
			V = new View(getActivity());
		}
		
    	return V;
    };
    
    public static class SetCollectionPagerAdapter extends FragmentStatePagerAdapter{

        private ArrayList<ArrayList<Stats>> stats;
		private ArrayList<Stats> total;

		public SetCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
            
        	Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
        	
        	ArrayList<Player> players = match.getTeam().getPlayers();
        	
        	stats = new ArrayList<ArrayList<Stats>>();
			total = new ArrayList<Stats>();
			
			for(int i = 0; i <= match.getSets().size(); i++){
				stats.add(new ArrayList<Stats>());
				total.add(new Stats(null));
				
				for(Player player: players){
					stats.get(i).add(new Stats(player));
				}
			}		
			
			for(Set set: match.getSets()){
				for(Action action: set.getActions()){
					stats.get(match.getSets().indexOf(set)).get(players.indexOf(action.getPlayer())).add(action.getActionType(), action.getActionScore());
					stats.get(match.getSets().size()).get(players.indexOf(action.getPlayer())).add(action.getActionType(), action.getActionScore());
					
					total.get(match.getSets().indexOf(set)).add(action.getActionType(), action.getActionScore());
					total.get(match.getSets().size()).add(action.getActionType(), action.getActionScore());
	        	}
	        	
			}
			
			for(int i = 0; i <= match.getSets().size(); i++){
				Collections.sort(stats.get(i), new mySort());
			}
			
			Collections.sort(total, new mySort());
        }
		
        public class mySort implements Comparator<Stats>{

			@Override
			public int compare(Stats s1, Stats s2) {
				Integer points = s2.getComparePoints().compareTo(s1.getComparePoints());
				Integer service = s2.getCount(ActionType.Service).compareTo(s1.getCount(ActionType.Service));
				Integer reception = s2.getCount(ActionType.Reception).compareTo(s1.getCount(ActionType.Reception));
				Integer attack = s2.getCount(ActionType.Attack).compareTo(s1.getCount(ActionType.Attack));
				Integer block = s2.getCount(ActionType.Block).compareTo(s1.getCount(ActionType.Block));
				return (points == 0 ? (service == 0 ? (reception == 0 ? (attack == 0 ? block : attack) : reception) : service) : points);
			}
        }

        @Override
        public Fragment getItem(int i) {
        	
        	Log.i("Scouting", "Position " + i);        	
        	
        	if(viewHelper.getSelectedFragment() != null){
        		StatisticsDetail fragment = new StatisticsDetail();
        		fragment.setStats(stats.get(i).get(player));
        		return fragment;
        	}
        	else{
	            StatisticsFragment fragment = new StatisticsFragment();
	            fragment.setScoutingService(scoutingService);
	            fragment.setStats(stats.get(i), total.get(i));
	            return fragment;
        	}
        }

        @Override
        public int getCount() {
    		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
    		if(match != null){
    			return match.getSets().size()+1;
    		}
    		else{
    			return 0;
    		}
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	if(position >= scoutingService.findMatchById(viewHelper.getSelectedMatch()).getSets().size()){
        		return "Totaal";
        	}
        	else{
        		return "Set " + (position + 1);
        	}
        }
    }
    
	public void setScoutingService(ScoutingService scoutingService) {
		StatisticsPager.scoutingService = scoutingService;
	}

	public void setPlayer(Integer player) {
		StatisticsPager.player = player;
	}
}
