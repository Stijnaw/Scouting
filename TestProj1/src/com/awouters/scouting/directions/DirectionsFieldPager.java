package com.awouters.scouting.directions;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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

public class DirectionsFieldPager extends Fragment {

	private static ViewHelper viewHelper;
	private static ScoutingService scoutingService;
	private static Integer player = null;

	public DirectionsFieldPager() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		viewHelper = new ViewHelper();
		
		DirectionsField directionsField = new DirectionsField();
		directionsField.setScoutingService(scoutingService);
		
		View V;
		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
		
		if(match != null){
	    	V = inflater.inflate(R.layout.directions_pager, null);
	    	
	        DirectionCollectionPagerAdapter mDirectionCollectionPagerAdapter = new DirectionCollectionPagerAdapter(getFragmentManager());
	        ViewPager mViewPager = (ViewPager) V.findViewById(R.id.directions_pager);

	        mViewPager.setAdapter(mDirectionCollectionPagerAdapter);
	        mViewPager.setOffscreenPageLimit(3);
	        /*mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				
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
	        
	        mViewPager.setCurrentItem(selectedSet);*/
		}
		else{
			V = new View(getActivity());
		}
		
    	return V;
	}

    public static class DirectionCollectionPagerAdapter extends FragmentStatePagerAdapter{

		private ArrayList<ArrayList<Action>> actions;
		private ArrayList<ArrayList<Action>> total;

		public DirectionCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
            
        	Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
        	
        	ArrayList<Player> players = match.getTeam().getPlayers();
        	
        	actions = new ArrayList<ArrayList<Action>>();
			total = new ArrayList<ArrayList<Action>>();
			
			for(int i = 0; i <= match.getSets().size(); i++){
				actions.add(new ArrayList<Action>());
				total.add(new ArrayList<Action>());
			}		
			
			for(Set set: match.getSets()){
				for(Action action: set.getActions()){
					if(players.indexOf(action.getPlayer()) == player){
						actions.get(match.getSets().indexOf(set)).add(action);
						actions.get(match.getSets().size()).add(action);
					}
					
					total.get(match.getSets().indexOf(set)).add(action);
					total.get(match.getSets().size()).add(action);
	        	}
			}
        }

        @Override
        public Fragment getItem(int i) {
            DirectionsField fragment = new DirectionsField();
    		if(i%2 == 0){
    			fragment.setStats(player, actions.get((int) i/2), false);
    		}
    		else{
    			fragment.setStats(player, actions.get((int) i/2), true);
    		}
    		
            return fragment;
        }

        @Override
        public int getCount() {
    		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
    		if(match != null){
    			return (match.getSets().size()*2)+2;
    		}
    		else{
    			return 0;
    		}
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	if(position == scoutingService.findMatchById(viewHelper.getSelectedMatch()).getSets().size()*2){
        		return "Opslag: totaal";
        	}
        	else if(position >= (scoutingService.findMatchById(viewHelper.getSelectedMatch()).getSets().size()*2)+1){
        		return "Aanval: totaal";
        	}
        	else{
        		if(position%2 == 0){
        			return "Opslag: set " + (((int) position/2) + 1);
        		}
        		else{
        			return "Aanval: set " + (((int) position/2) + 1);
        		}
        	}
        }
    }
    
	public void setScoutingService(ScoutingService scoutingService) {
		DirectionsFieldPager.scoutingService = scoutingService;
	}

	public void setPlayer(int player) {
		this.player  = player;
	}
}
