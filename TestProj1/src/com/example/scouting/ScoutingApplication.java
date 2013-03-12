package com.example.scouting;

import java.util.ArrayList;

import com.example.scouting.R;
import com.example.scouting.server.MatchLocalDB;
import com.example.scouting.server.ScoutingImpl;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Action;
import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Team;

import android.R.color;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ScoutingApplication extends Activity implements ScoutingFragment.OnScoutingFragmentInteractionListener, StatisticsFragment.OnStatisticsFragmentInteractionListener, SettingsFragment.OnSettingsFragmentInteractionListener {

	private ScoutingService scoutingService;
	private Player selectedPlayer = null;
	private ActionType selectedActionType = null;
	private ActionScore selectedActionScore = null;
	private Match testMatch;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private Player player5;
	private Player player6;
	
	private ArrayList<Button> playerBtns;
	private ArrayList<Button> actionTypeBtns;
	private ArrayList<Button> actionScoreBtns;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /***********************************
    	 * Scouting Service
    	 **********************************/
		scoutingService = new ScoutingImpl(new MatchLocalDB());
		
		player1 = new Player("Player 1", 1);
		player2 = new Player("Player 2", 2);
		player3 = new Player("Player 3", 3);
		player4 = new Player("Player 4", 4);
		player5 = new Player("Player 5", 5);
		player6 = new Player("Player 6", 6);

        Team team1 = new Team("VC Overpelt");
        team1.addPlayer(player1);
        team1.addPlayer(player2);
        team1.addPlayer(player3);
        team1.addPlayer(player4);
        team1.addPlayer(player5);
        team1.addPlayer(player6);
        
        // TODO: create match in settings
        testMatch = scoutingService.createNewMatch(team1, "Uikhoven");
        
        scoutingService.saveMatch(testMatch);
        
        /***********************************
    	 * Get buttons
    	 **********************************/
        Button button;
        
        // TODO: Add players after longClick on buttons
        playerBtns = new ArrayList<Button>();
        button = (Button) findViewById(R.id.btnPlayer1);
        button.setTag(player1);
        playerBtns.add(button);
        
        button = (Button) findViewById(R.id.btnPlayer2);
        button.setTag(player2);
        playerBtns.add(button);
        
        button = (Button) findViewById(R.id.btnPlayer3);
        button.setTag(player3);
        playerBtns.add(button);
        
        button = (Button) findViewById(R.id.btnPlayer4);
        button.setTag(player4);
        playerBtns.add(button);
        
        button = (Button) findViewById(R.id.btnPlayer5);
        button.setTag(player5);
        playerBtns.add(button);
        
        button = (Button) findViewById(R.id.btnPlayer6);
        button.setTag(player6);
        playerBtns.add(button);
        
        actionTypeBtns = new ArrayList<Button>();
        button = (Button) findViewById(R.id.btnActionTypeService);
        button.setTag(ActionType.Service);
        actionTypeBtns.add(button);
        
        button = (Button) findViewById(R.id.btnActionTypeReception);
        button.setTag(ActionType.Reception);
        actionTypeBtns.add(button);
        
        button = (Button) findViewById(R.id.btnActionTypeDig);
        button.setTag(ActionType.Dig);
        actionTypeBtns.add(button);
        
        button = (Button) findViewById(R.id.btnActionTypeAttack);
        button.setTag(ActionType.Attack);
        actionTypeBtns.add(button);
        
        button = (Button) findViewById(R.id.btnActionTypeBlock);
        button.setTag(ActionType.Block);
        actionTypeBtns.add(button);
        
        button = (Button) findViewById(R.id.btnActionTypePass);
        button.setTag(ActionType.Pass);
        actionTypeBtns.add(button);        
        
        actionScoreBtns = new ArrayList<Button>();
        button = (Button) findViewById(R.id.btnActionScoreMinusMinus);
        button.setTag(ActionScore.MinusMinus);
        actionScoreBtns.add(button);
        
        button = (Button) findViewById(R.id.btnActionScoreMinus);
        button.setTag(ActionScore.Minus);
        actionScoreBtns.add(button);
        
        button = (Button) findViewById(R.id.btnActionScoreNull);
        button.setTag(ActionScore.Null);
        actionScoreBtns.add(button);
        
        button = (Button) findViewById(R.id.btnActionScorePlus);
        button.setTag(ActionScore.Plus);
        actionScoreBtns.add(button);
        
        button = (Button) findViewById(R.id.btnActionScorePlusPlus);
        button.setTag(ActionScore.PlusPlus);
        actionScoreBtns.add(button);
    	
    	/***********************************
    	 * Actionbar tabs
    	 **********************************/
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
    
	/***********************************
	 * Actionbar menu + text
	 **********************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        View view;
        
    	getMenuInflater().inflate(R.menu.main, menu);
    	getMenuInflater().inflate(R.menu.menu, menu);
    	
        MenuItem mSpinnerItem = menu.findItem(R.id.set_spinner);
        view = mSpinnerItem.getActionView();
        Spinner spinner = (Spinner) view;
        // TODO: populate sets with real sets + add set option
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
        text.setText(testMatch.getTeam().getName() + " - " + testMatch.getOpponent());
        
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
				if(selectedPlayer != null && selectedActionType != null && selectedActionScore != null){
					testMatch.addAction(new Action(selectedPlayer, selectedActionType, selectedActionScore));
					selectedPlayer = null;
					selectedActionType = null;
					selectedActionScore = null;
					
					Toast.makeText(getApplicationContext(), "Action applied", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getApplicationContext(), "Action not applied", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.btnControlsReset:
				Toast.makeText(getApplicationContext(), "Reset", Toast.LENGTH_SHORT).show();
				
				selectedPlayer = null;
				selectedActionType = null;
				selectedActionScore = null;
				break;
				
			case R.id.btnControlsUndo:
				// TODO: make undo function
				Toast.makeText(getApplicationContext(), "Undo", Toast.LENGTH_SHORT).show();
				break;
    	
			/***********************************
			 * Players
			 **********************************/
			case R.id.btnPlayer1:
				Toast.makeText(getApplicationContext(), "Player: 1", Toast.LENGTH_SHORT).show();
				selectedPlayer = player1;
				break;
				
			case R.id.btnPlayer2:
				Toast.makeText(getApplicationContext(), "Player: 2", Toast.LENGTH_SHORT).show();
				selectedPlayer = player2;
				break;
				
			case R.id.btnPlayer3:
				Toast.makeText(getApplicationContext(), "Player: 3", Toast.LENGTH_SHORT).show();
				selectedPlayer = player3;
				break;
				
			case R.id.btnPlayer4:
				Toast.makeText(getApplicationContext(), "Player: 4", Toast.LENGTH_SHORT).show();
				selectedPlayer = player4;
				break;
				
			case R.id.btnPlayer5:
				Toast.makeText(getApplicationContext(), "Player: 5", Toast.LENGTH_SHORT).show();
				selectedPlayer = player5;
				break;
				
			case R.id.btnPlayer6:
				Toast.makeText(getApplicationContext(), "Player: 6", Toast.LENGTH_SHORT).show();
				selectedPlayer = player6;
				break;
    		
    		
    		/***********************************
    		 * ActionType
    		 **********************************/
			case R.id.btnActionTypeAttack:
				Toast.makeText(getApplicationContext(), "Action: attack", Toast.LENGTH_SHORT).show();
				selectedActionType = ActionType.Attack;
				break;
			
			case R.id.btnActionTypeBlock:
				Toast.makeText(getApplicationContext(), "Action: block", Toast.LENGTH_SHORT).show();
				selectedActionType = ActionType.Block;
				break;
				
			case R.id.btnActionTypeDig:
				Toast.makeText(getApplicationContext(), "Action: dig", Toast.LENGTH_SHORT).show();
				selectedActionType = ActionType.Dig;
				break;
				
			case R.id.btnActionTypePass:
				Toast.makeText(getApplicationContext(), "Action: pass", Toast.LENGTH_SHORT).show();
				selectedActionType = ActionType.Pass;
				break;
			
			case R.id.btnActionTypeReception:
				Toast.makeText(getApplicationContext(), "Action: reception", Toast.LENGTH_SHORT).show();
				selectedActionType = ActionType.Reception;
				break;
			
			case R.id.btnActionTypeService:
				Toast.makeText(getApplicationContext(), "Action: service", Toast.LENGTH_SHORT).show();
				selectedActionType = ActionType.Service;
				break;
				
    		/***********************************
    		 * ActionScore
    		 **********************************/
			case R.id.btnActionScoreMinusMinus:
				Toast.makeText(getApplicationContext(), "ActionScore: --", Toast.LENGTH_SHORT).show();
				selectedActionScore = ActionScore.MinusMinus;
				Button button = (Button) findViewById(R.id.btnActionScoreMinusMinus);
				button.setTextColor(getApplication().getResources().getColor(android.R.color.holo_blue_light));
				break;
				
			case R.id.btnActionScoreMinus:
				Toast.makeText(getApplicationContext(), "ActionScore: -", Toast.LENGTH_SHORT).show();
				selectedActionScore = ActionScore.Minus;
				break;
				
			case R.id.btnActionScoreNull:
				Toast.makeText(getApplicationContext(), "ActionScore: 0", Toast.LENGTH_SHORT).show();
				selectedActionScore = ActionScore.Null;
				break;
			case R.id.btnActionScorePlus:
				Toast.makeText(getApplicationContext(), "ActionScore: +", Toast.LENGTH_SHORT).show();
				selectedActionScore = ActionScore.Plus;
				break;
				
			case R.id.btnActionScorePlusPlus:
				Toast.makeText(getApplicationContext(), "ActionScore: ++", Toast.LENGTH_SHORT).show();
				selectedActionScore = ActionScore.PlusPlus;
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
