package com.awouters.scouting.statistics;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awouters.scouting.R;
import com.awouters.scouting.helpers.FragmentStatePagerAdapter;
import com.awouters.scouting.helpers.ViewHelper;
import com.awouters.scouting.server.ScoutingService;
import com.awouters.scouting.src.Action;
import com.awouters.scouting.src.Match;
import com.awouters.scouting.src.Player;
import com.awouters.scouting.src.Set;
import com.awouters.scouting.src.Stats;

public class StatisticsPlayerDetailPager extends Fragment {
	
	private static ScoutingService scoutingService;
	private static ViewHelper viewHelper;
	private static Integer selectedSet = null;
	private static Player selectedPlayer = null;

	public StatisticsPlayerDetailPager() {
		// Required empty public constructor
	}
	
    /**
     * The {@link android.support.v4.view.ViewPager} that will display the object collection.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        viewHelper = new ViewHelper();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
		View V;
		
		if(match != null){
	    	V = inflater.inflate(R.layout.statistics_details, container, false);
	    	
	        SetCollectionPagerAdapter mSetCollectionPagerAdapter = new SetCollectionPagerAdapter(getFragmentManager());
	        
	        mViewPager = (ViewPager) V.findViewById(R.id.stats_details_pager);
	        mViewPager.setAdapter(mSetCollectionPagerAdapter);
	        mViewPager.setOffscreenPageLimit(3);
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
	        
	        if(selectedSet == null && StatisticsPager.getSelectedSet() != null){
	        	selectedSet = StatisticsPager.getSelectedSet();
	        }
	        else{
	        	selectedSet = 0;
	        }
	        
	        mViewPager.setCurrentItem(selectedSet);
		}
		else{
			V = new View(getActivity());
		}
		
    	return V;
    };
    
    public static class SetCollectionPagerAdapter extends FragmentStatePagerAdapter{

        private ArrayList<Stats> stats;

		public SetCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
            
        	Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
        	
        	stats = new ArrayList<Stats>();
			
			for(int i = 0; i <= match.getSets().size(); i++){
				stats.add(new Stats(selectedPlayer));
			}		
			
			for(Set set: match.getSets()){
				for(Action action: set.getActions()){
					stats.get(match.getSets().indexOf(set)).add(action.getActionType(), action.getActionScore());
					stats.get(match.getSets().size()).add(action.getActionType(), action.getActionScore());
	        	}
			}
        }

        @Override
        public Fragment getItem(int i) {
    		StatisticsPlayerDetail fragment = new StatisticsPlayerDetail();
    		fragment.setStats(stats.get(i));
            return fragment;
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
		StatisticsPlayerDetailPager.scoutingService = scoutingService;
	}

	public void setPlayer(Player player) {
		StatisticsPlayerDetailPager.selectedPlayer = player;
	}

	public static void setSelectedSet(Integer selectedSet) {
		StatisticsPlayerDetailPager.selectedSet = selectedSet;
	}
}
