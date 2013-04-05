package com.example.scouting.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.scouting.helpers.ViewHelper;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Action;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Set;
import com.example.scouting.src.Stats;
import com.example.testproj1.R;

public class StatisticsDetailPager extends Fragment {
	
	private static ScoutingService scoutingService;
	private static ViewHelper viewHelper;
	private static Integer selectedSet = null;
	private static Player selectedPlayer = null;

	public StatisticsDetailPager() {
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
		
		//Toast.makeText(getActivity(), "Oncreate details viewpager", Toast.LENGTH_SHORT).show();
		
		if(match != null){
	    	V = inflater.inflate(R.layout.fragment_statistics_details_pager, container, false);
	    	
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
	        
	        if(selectedSet == null){
	        	selectedSet = StatisticsPager.getSelectedSet();
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
    		StatisticsDetail fragment = new StatisticsDetail();
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
		StatisticsDetailPager.scoutingService = scoutingService;
	}

	public void setPlayer(Player player) {
		StatisticsDetailPager.selectedPlayer = player;
	}

	public static void setSelectedSet(Integer selectedSet) {
		StatisticsDetailPager.selectedSet = selectedSet;
	}
}
