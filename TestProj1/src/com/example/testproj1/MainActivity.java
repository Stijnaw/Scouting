package com.example.testproj1;

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


public class MainActivity extends Activity implements ScoutingFragment.OnScoutingFragmentInteractionListener, StatisticsFragment.OnStatisticsFragmentInteractionListener, SettingsFragment.OnSettingsFragmentInteractionListener {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar.newTab()
                .setText(R.string.TabScouting)
                .setTabListener(new TabListener<ScoutingFragment>(
                        this, "Scouting", ScoutingFragment.class));
        actionBar.addTab(tab);
        

        tab = actionBar.newTab()
                .setText(R.string.TabStatistics)
                .setTabListener(new TabListener<StatisticsFragment>(
                        this, "Statistics", StatisticsFragment.class));
        actionBar.addTab(tab);
        
        tab = actionBar.newTab()
                .setText(R.string.TabSettings)
                .setTabListener(new TabListener<SettingsFragment>(
                        this, "Settings", SettingsFragment.class));
        actionBar.addTab(tab);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        View view;
        
    	getMenuInflater().inflate(R.menu.main, menu);
    	getMenuInflater().inflate(R.menu.menu, menu);
    	
        MenuItem mSpinnerItem = menu.findItem(R.id.set_spinner);
        view = mSpinnerItem.getActionView();
        Spinner spinner = (Spinner) view;
        spinner.setAdapter(
        	ArrayAdapter.createFromResource(
        		this,
        		R.array.Sets,
        		android.R.layout.simple_spinner_dropdown_item
        	)
        );
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	Toast.makeText(getApplicationContext(), parentView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            	Toast.makeText(getApplicationContext(), "Sets Nothing", Toast.LENGTH_SHORT).show();
            }

        });
            	
    	MenuItem mTextViewItem = menu.findItem(R.id.menu_text);
        view = mTextViewItem.getActionView();
        TextView text = (TextView) view;
        text.setText("VCO - Uikhoven");
        
        return true;
    }
    
	/***********************************
	 * Scouting button handler
	 **********************************/
    public void btnScoutingOnClick(View view){

		switch (view.getId()) {
			/***********************************
			 * Controls
			 **********************************/ 	
			case R.id.btnControlsApply:
				Toast.makeText(getApplicationContext(), "Apply", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnControlsReset:
				Toast.makeText(getApplicationContext(), "Reset", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnControlsUndo:
				Toast.makeText(getApplicationContext(), "Undo", Toast.LENGTH_SHORT).show();
				break;
    	
			/***********************************
			 * Players
			 **********************************/
			case R.id.btnPlayer1:
				Toast.makeText(getApplicationContext(), "Player: 1", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnPlayer2:
				Toast.makeText(getApplicationContext(), "Player: 2", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnPlayer3:
				Toast.makeText(getApplicationContext(), "Player: 3", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnPlayer4:
				Toast.makeText(getApplicationContext(), "Player: 4", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnPlayer5:
				Toast.makeText(getApplicationContext(), "Player: 5", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnPlayer6:
				Toast.makeText(getApplicationContext(), "Player: 6", Toast.LENGTH_SHORT).show();
				break;
    		
    		
    		/***********************************
    		 * Actions
    		 **********************************/
			case R.id.btnActionAttack:
				Toast.makeText(getApplicationContext(), "Action: attack", Toast.LENGTH_SHORT).show();
				break;
			
			case R.id.btnActionBlock:
				Toast.makeText(getApplicationContext(), "Action: block", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnActionDig:
				Toast.makeText(getApplicationContext(), "Action: dig", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnActionPass:
				Toast.makeText(getApplicationContext(), "Action: pass", Toast.LENGTH_SHORT).show();
				break;
			
			case R.id.btnActionReception:
				Toast.makeText(getApplicationContext(), "Action: reception", Toast.LENGTH_SHORT).show();
				break;
			
			case R.id.btnActionService:
				Toast.makeText(getApplicationContext(), "Action: service", Toast.LENGTH_SHORT).show();
				break;
				
    		/***********************************
    		 * Quality
    		 **********************************/
			case R.id.btnQualityMinusMinus:
				Toast.makeText(getApplicationContext(), "Quality: --", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnQualityMinus:
				Toast.makeText(getApplicationContext(), "Quality: -", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnQualityNull:
				Toast.makeText(getApplicationContext(), "Quality: 0", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnQualityPlus:
				Toast.makeText(getApplicationContext(), "Quality: +", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.btnQualityPlusPlus:
				Toast.makeText(getApplicationContext(), "Quality: ++", Toast.LENGTH_SHORT).show();
				break;
				
			default:
			break;
		}
    }
    
	/***********************************
	 * Scouting button handler
	 **********************************/
    public void btnMenuOnClick(View view){

		switch (view.getId()) {
			/***********************************
			 * Sets
			 **********************************/ 	
			case R.id.set_spinner:
				Toast.makeText(getApplicationContext(), "Sets!", Toast.LENGTH_SHORT).show();
				break;
				
			default:
			break;
		}
    }
    
    /**************************************************************
     * Tab Listener
     **************************************************************/
    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        /** Constructor used each time a new tab is created.
          * @param activity  The host Activity, used to instantiate the fragment
          * @param tag  The identifier tag for the fragment
          * @param clz  The fragment's Class, used to instantiate the fragment
          */
        public TabListener(Activity activity, String tag, Class<T> clz) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
        }

        /* The following are each of the ActionBar.TabListener callbacks */

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // Check if the fragment is already initialized
            if (mFragment == null) {
                // If not, instantiate and add it to the activity
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                // If it exists, simply attach it in order to show it
                ft.attach(mFragment);
            }
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                // Detach the fragment, because another one is being attached
                ft.detach(mFragment);
            }
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // User selected the already selected tab. Usually do nothing.
        }
    }


    /*
     * Use to communicate with scouting fragment
     */
	@Override
	public void onScoutingFragmentInteraction() {
		// TODO Auto-generated method stub
		
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
}
