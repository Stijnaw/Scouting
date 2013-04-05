package com.example.scouting;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scouting.helpers.ViewHelper;
import com.example.scouting.server.ScoutingFileDB;
import com.example.scouting.server.ScoutingImpl;
import com.example.scouting.server.ScoutingLocalDB;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Match;
import com.example.scouting.src.Set;
import com.example.scouting.statistics.StatisticsDetailPager;
import com.example.scouting.statistics.StatisticsPager;
import com.example.testproj1.R;


public class ScoutingApplication extends Activity {

	private ScoutingService scoutingService;
	private ViewHelper viewHelper;
	private ScoutingFileDB scoutingFileDB;

	private static final int SELECT_TEXT_DIALOG = 1;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        scoutingFileDB = new ScoutingFileDB(this);
                
        /***********************************
    	 * Scouting Service
    	 **********************************/
        if (savedInstanceState != null) {
            scoutingService = (ScoutingService) savedInstanceState.getSerializable("scoutingService");
            viewHelper = (ViewHelper) savedInstanceState.getSerializable("viewHelper");            
        }
        else{
        	scoutingService = scoutingFileDB.getScouting();
        	//viewHelper = scoutingFileDB.getView();
        	
        	if(scoutingService == null){
        		scoutingService = new ScoutingImpl(new ScoutingLocalDB());
        	}
        	
        	//if(viewHelper == null){
        		viewHelper = new ViewHelper();
        	//}
        }
        
    	/***********************************
    	 * Actionbar tabs
    	 **********************************/
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        ScoutingFragment scoutingFragment = new ScoutingFragment();
        scoutingFragment.setScoutingService(scoutingService);
        scoutingFragment.setScoutingFileDB(scoutingFileDB);
        
        StatisticsPager statisticsPager = new StatisticsPager();
        statisticsPager.setScoutingService(scoutingService);
        
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setScoutingService(scoutingService);

        ActionBar.Tab scoutingTab = actionBar.newTab()
                .setText(R.string.TabScouting)
                .setTabListener(new MyTabListener(scoutingFragment, "Scouting"));
        
        ActionBar.Tab statisticsTab = actionBar.newTab()
                .setText(R.string.TabStatistics)
                .setTabListener(new MyStatsTabListener(statisticsPager, "Statistics"));        
        
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
	protected void onPause(){
		scoutingFileDB.saveScouting(scoutingService);
		scoutingFileDB.saveView(viewHelper);
		super.onPause();
	}
	
	@Override
	public void onBackPressed() {
		
		if(findViewById(R.id.stats_details_pager) != null && viewHelper.getSelectedFragment() != null){
				viewHelper.setSelectedFragment(null);
				StatisticsDetailPager.setSelectedSet(null);
				getFragmentManager().popBackStackImmediate();
				ActionBar actionBar = getActionBar();
				actionBar.getSelectedTab().select();
		}
		else if(getFragmentManager().getBackStackEntryCount() == 0){
		    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
	
		    alertDialog.setPositiveButton("Ja", new OnClickListener() {
	
				@Override
				public void onClick(DialogInterface dialog, int which) {
		            finish();
				}
			});
	
		    alertDialog.setNegativeButton("Nee", null);
	
		    alertDialog.setMessage("Wil je stoppen met scouten?");
		    alertDialog.setTitle("Afsluiten?");
		    alertDialog.show();
		}
		else{
			StatisticsPager.setSelectedSet(null);
			super.onBackPressed();
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
    	
    	TextView text;
    	
    	final Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
    	if(match != null){
	        text = (TextView) menu.findItem(R.id.selected_match).getActionView();
	        text.setText(match.toString());
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
            case R.id.menu_export_file:
            	scoutingFileDB.saveScoutingExtern(scoutingService);
            	return true;
            case R.id.menu_import_file:
            	Intent intent = new Intent();
            	intent.setType("text/*");
            	intent.setAction(Intent.ACTION_GET_CONTENT);
            	startActivityForResult(Intent.createChooser(intent, "Select Text"), SELECT_TEXT_DIALOG);
            	return true;
            case android.R.id.home:
            	onBackPressed();
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
	    if (resultCode == RESULT_OK) {
	        if (requestCode == SELECT_TEXT_DIALOG) {
	            Uri data = result.getData();

	            if(data.getLastPathSegment().endsWith("txt")){
	            	scoutingService = scoutingFileDB.importScoutingFromExtern(data.getPath());

	            	Toast.makeText(this, "Herstart de applicatie om de wijzigingen toe te passen.", Toast.LENGTH_LONG).show();
	            } 
	            else {
	                Toast.makeText(this, "Invalid file type", Toast.LENGTH_SHORT).show();   
	            }               
	        }
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

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
    }
    
    public class MyStatsTabListener implements ActionBar.TabListener {
        private Fragment mFragment;
        private final String mTag;
        
        public MyStatsTabListener(Fragment fragment, String tag){
        	this.mFragment = fragment;
        	this.mTag = tag;
        }

        /* The following are each of the ActionBar.TabListener callbacks */

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
        	if(viewHelper.getSelectedFragment() != null){
        		mFragment = new StatisticsDetailPager();
        	}
        	else{
        		mFragment = new StatisticsPager();
        	}
        	ft.replace(android.R.id.content, mFragment, mTag);
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.remove(mFragment);
            viewHelper.setSelectedFragment(null);
            StatisticsPager.setSelectedSet(null);
            StatisticsDetailPager.setSelectedSet(null);
        }

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
        	if(viewHelper.getSelectedFragment() != null){
        		mFragment = new StatisticsDetailPager();
        	}
        	else{
        		mFragment = new StatisticsPager();
        	}
        	ft.replace(android.R.id.content, mFragment, mTag);
		}
    }
}