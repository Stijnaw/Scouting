package com.example.scouting;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scouting.SettingsFragment.OnSettingsFragmentInteractionListener;
import com.example.scouting.server.ScoutingImpl;
import com.example.scouting.server.ScoutingLocalDB;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Set;
import com.example.scouting.src.Team;
import com.example.settings.SettingsNewMatch.OnSettingsNewMatchFragmentInteractionListener;
import com.example.settings.SettingsNewPlayer.OnSettingsNewPlayerFragmentInteractionListener;
import com.example.settings.SettingsNewTeam.OnSettingsNewTeamFragmentInteractionListener;
import com.example.settings.SettingsOverview.OnSettingsOverviewFragmentInteractionListener;
import com.example.testproj1.R;


public class ScoutingApplication extends Activity implements ScoutingFragment.OnScoutingFragmentInteractionListener, StatisticsFragment.OnStatisticsFragmentInteractionListener, OnSettingsFragmentInteractionListener, OnSettingsOverviewFragmentInteractionListener, OnSettingsNewPlayerFragmentInteractionListener, OnSettingsNewTeamFragmentInteractionListener, OnSettingsNewMatchFragmentInteractionListener {

	private ScoutingService scoutingService;
	private ViewHelper viewHelper;
	
	private ArrayList<String> setList;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        /***********************************
    	 * Scouting Service
    	 **********************************/
        if (savedInstanceState != null) {
            scoutingService = (ScoutingService) savedInstanceState.getSerializable("scoutingService");
            viewHelper = (ViewHelper) savedInstanceState.getSerializable("viewHelper");            
        }
        else{
        	scoutingService = new ScoutingImpl(new ScoutingLocalDB());
        	viewHelper = new ViewHelper();
        	
			Player player1 = scoutingService.createNewPlayer("Player 1", 1);
			Player player2 = scoutingService.createNewPlayer("Player 2", 2);
			Player player3 = scoutingService.createNewPlayer("Player 3", 3);
			Player player4 = scoutingService.createNewPlayer("Player 4", 4);
			Player player5 = scoutingService.createNewPlayer("Player 5", 5);
			Player player6 = scoutingService.createNewPlayer("Player 6", 6);
	
	        Team team1 = scoutingService.createNewTeam("VC Overpelt");
	        team1.addPlayer(player1);
	        team1.addPlayer(player2);
	        team1.addPlayer(player3);
	        team1.addPlayer(player4);
	        team1.addPlayer(player5);
	        team1.addPlayer(player6);
	        
	        // TODO: create match in settings
	        Match testMatch = scoutingService.createNewMatch(team1, "Uikhoven", true);
	        
	        testMatch.setPlayerActive(player1, 1);
	        testMatch.setPlayerActive(player2, 2);
	        testMatch.setPlayerActive(player3, 3);
	        testMatch.setPlayerActive(player4, 4);
	        testMatch.setPlayerActive(player5, 5);
	        testMatch.setPlayerActive(player6, 6);
	        
	        scoutingService.saveMatch(testMatch);
        }
        
    	/***********************************
    	 * Actionbar tabs
    	 **********************************/
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
        ScoutingFragment scoutingFragment = new ScoutingFragment();
        scoutingFragment.setScoutingService(scoutingService);
        scoutingFragment.setViewHelper(viewHelper);
        
        StatisticsFragment statisticsFragment = new StatisticsFragment();
        statisticsFragment.setViewHelper(viewHelper);
        
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setScoutingService(scoutingService);
        settingsFragment.setViewHelper(viewHelper);

        ActionBar.Tab scoutingTab = actionBar.newTab()
                .setText(R.string.TabScouting)
                .setTabListener(new MyTabListener(scoutingFragment, "Scouting"));
        
        ActionBar.Tab statisticsTab = actionBar.newTab()
                .setText(R.string.TabStatistics)
                .setTabListener(new MyTabListener(statisticsFragment, "Statistics"));        
        
        ActionBar.Tab settingsTab = actionBar.newTab()
                .setText(R.string.TabSettings)
                .setTabListener(new MyTabListener(settingsFragment, "Settings"));
        
        actionBar.addTab(scoutingTab);
        actionBar.addTab(statisticsTab);
        actionBar.addTab(settingsTab);
        
        if(viewHelper.getSelectedTab() != null){
        	actionBar.setSelectedNavigationItem(viewHelper.getSelectedTab());
        }
    }
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("scoutingService", scoutingService);
        savedInstanceState.putSerializable("viewHelper", viewHelper);
        
        viewHelper.setSelectedTab(getActionBar().getSelectedNavigationIndex());
        //super.onSaveInstanceState(savedInstanceState);
    }
	
    
	/***********************************
	 * Actionbar menu + text
	 **********************************/
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	getMenuInflater().inflate(R.menu.main, menu);
    	getMenuInflater().inflate(R.menu.menu, menu);
    	
    	MenuItem menuSpinner = (MenuItem) menu.findItem(R.id.set_spinner);
    	Spinner spinner = (Spinner) menuSpinner.getActionView();
    	MenuItem newSet = (MenuItem) menu.findItem(R.id.menu_new_set);
    	TextView text;
    	
    	if(viewHelper.getSelectedMatch() != null){
	        setList = new ArrayList<String>();
	        for(Set set : viewHelper.getSelectedMatch().getSetList()) {
				setList.add("Set " + viewHelper.getSelectedMatch().getSetNumber(set));
			}
	        
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, setList);
	        
	        spinner.setAdapter(adapter);
	        spinner.setSelection(viewHelper.getSelectedMatch().getCurrentSetNumber()-1);
	        
	        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	            	viewHelper.getSelectedMatch().setCurrentSet(viewHelper.getSelectedMatch().getSetByNumber(position));
	            	Toast.makeText(getApplicationContext(), "Huidige set: " + viewHelper.getSelectedMatch().getCurrentSetNumber(), Toast.LENGTH_SHORT).show();
	            }
	
	            @Override
	            public void onNothingSelected(AdapterView<?> parentView) {
	            }
	
	        });
	        
	        menuSpinner.setVisible(true);
	        newSet.setVisible(true);
	        
	        text = (TextView) menu.findItem(R.id.selected_match).getActionView();
	        text.setText(viewHelper.getSelectedMatch().toString());
    	}
    	else{
	        text = (TextView) menu.findItem(R.id.selected_match).getActionView();
	        text.setText("Selecteer eerst een match ");
    	}
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_new_set:
            	setList.add("Set " + viewHelper.getSelectedMatch().getSetNumber(viewHelper.getSelectedMatch().newSet()));
            	Spinner spinner = (Spinner) findViewById(R.id.set_spinner);
            	spinner.setSelection(viewHelper.getSelectedMatch().getCurrentSetNumber()-1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**************************************************************
     * Tab Listener
     **************************************************************/
    public class MyTabListener implements ActionBar.TabListener {
        private Fragment mFragment;
        private final String mTag;
        
        public MyTabListener(Fragment fragment, String tag){
        	this.mFragment = fragment;
        	this.mTag = tag;
        }

        /* The following are each of the ActionBar.TabListener callbacks */

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            ft.replace(android.R.id.content, mFragment, mTag);
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.remove(mFragment);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // User selected the already selected tab. Usually do nothing.
        }
    }


    /*
     * Use to communicate with scouting fragment
     */
	@Override
	public void onScoutingFragmentInteraction(View v) {

	}
	
	/*
     * Use to communicate with statistics fragment
     */
	@Override
	public void onStatisticsFragmentInteraction() {
		// TODO Auto-generated method stub
		
	}
	
	
	/*
     * Use to communicate with settings fragment
     */
	@Override
	public void onSettingsFragmentInteraction() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onSettingsOverviewFragmentInteraction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSettingsNewPlayerFragmentInteraction() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSettingsNewTeamFragmentInteraction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSettingsNewMatchFragmentInteraction() {
		// TODO Auto-generated method stub
		
	}
}
