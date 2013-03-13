package com.example.scouting;

import java.util.ArrayList;

import com.example.scouting.server.MatchLocalDB;
import com.example.scouting.server.ScoutingImpl;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Action;
import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.scouting.src.Team;
import com.example.testproj1.R;

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
	
	private ArrayList<Button> playerBtns = null;
	private ArrayList<Button> actionTypeBtns = null;
	private ArrayList<Button> actionScoreBtns = null;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
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
    
    private Button findButtonByPlayerTag(Player player){
		for (Button button : playerBtns) {
			if(button.getTag() == player){
				return button;
			}
		}
		return null;
    }
    
    private Button findButtonByActionTypeTag(ActionType actionType){
		for (Button button : actionTypeBtns) {
			if(button.getTag() == actionType){
				return button;
			}
		}
		return null;
    }
    
    private Button findButtonByActionScoreTag(ActionScore actionScore){
		for (Button button : actionScoreBtns) {
			if(button.getTag() == actionScore){
				return button;
			}
		}
		return null;
    }
    
	/***********************************
	 * Scouting control button handler
	 **********************************/
    public void btnScoutingControlOnClick(View view){

		switch (view.getId()) {	
			case R.id.btnControlsApply:
				if(selectedPlayer != null && selectedActionType != null && selectedActionScore != null){
					testMatch.addAction(new Action(selectedPlayer, selectedActionType, selectedActionScore));
					
			    	Button buttonPrev = findButtonByPlayerTag(selectedPlayer);
			    	
			    	if(buttonPrev != null){
			    		buttonPrev.setTextColor(getApplication().getResources().getColor(android.R.color.primary_text_dark));
			    	}
			    	
			    	buttonPrev = findButtonByActionTypeTag(selectedActionType);
			    	
			    	if(buttonPrev != null){
			    		buttonPrev.setTextColor(getApplication().getResources().getColor(android.R.color.primary_text_dark));
			    	}
			    	
			    	buttonPrev = findButtonByActionScoreTag(selectedActionScore);
			    	
			    	if(buttonPrev != null){
			    		buttonPrev.setTextColor(getApplication().getResources().getColor(android.R.color.primary_text_dark));
			    	}
			    	
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
				
		}
    }
    
	/***********************************
	 * Scouting player button handler
	 **********************************/
    public void btnScoutingPlayerOnClick(View view){
    	
    	Button buttonPrev = findButtonByPlayerTag(selectedPlayer);
    	
    	if(buttonPrev != null){
    		buttonPrev.setTextColor(getApplication().getResources().getColor(android.R.color.primary_text_dark));
    	}
    	
    	Button button = (Button) findViewById(view.getId());
    	
    	if(button != buttonPrev){
    		button.setTextColor(getApplication().getResources().getColor(android.R.color.holo_blue_light));
    		selectedPlayer = (Player) button.getTag();
    		Toast.makeText(getApplicationContext(), selectedPlayer.getName(), Toast.LENGTH_SHORT).show();
    	}
    	else{
    		selectedPlayer = null;
    	}
    	
    	/*
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
    		*/
    		
    }
    
	/***********************************
	 * Scouting action type button handler
	 **********************************/
    public void btnScoutingActionTypeOnClick(View view){
    	
    	Button buttonPrev = findButtonByActionTypeTag(selectedActionType);
    	
    	if(buttonPrev != null){
    		buttonPrev.setTextColor(getApplication().getResources().getColor(android.R.color.primary_text_dark));
    	}
    	
    	Button button = (Button) findViewById(view.getId());
    	
    	if(button != buttonPrev){
    		button.setTextColor(getApplication().getResources().getColor(android.R.color.holo_blue_light));
    		selectedActionType = (ActionType) button.getTag();
    		Toast.makeText(getApplicationContext(), selectedActionType.toString(), Toast.LENGTH_SHORT).show();
    	}
    	else{
    		selectedActionType = null;
    	}
    	
    	/*
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
				*/
    }
    
	/***********************************
	 * Scouting action score button handler
	 **********************************/
    public void btnScoutingActionScoreOnClick(View view){
    	
    	Button buttonPrev = findButtonByActionScoreTag(selectedActionScore);
    	
    	if(buttonPrev != null){
    		buttonPrev.setTextColor(getApplication().getResources().getColor(android.R.color.primary_text_dark));
    	}
    	
    	Button button = (Button) findViewById(view.getId());
    	
    	if(button != buttonPrev){
    		button.setTextColor(getApplication().getResources().getColor(android.R.color.holo_blue_light));
    		selectedActionScore = (ActionScore) button.getTag();
    		Toast.makeText(getApplicationContext(), selectedActionScore.toString(), Toast.LENGTH_SHORT).show();
    	}
    	else{
    		selectedActionScore = null;
    	}
    	
    	
    	/*
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
			*/
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
	public void onScoutingFragmentInteraction(ArrayList<Button> playerBtns, ArrayList<Button> actionTypeBtns, ArrayList<Button> actionScoreBtns) {
		this.playerBtns = playerBtns;
		this.actionTypeBtns = actionTypeBtns;
		this.actionScoreBtns = actionScoreBtns;
		
		// TODO: Add players after longClick on buttons
		// add players to button
		playerBtns.get(0).setTag(player1);
		playerBtns.get(1).setTag(player2);
		playerBtns.get(2).setTag(player3);
		playerBtns.get(3).setTag(player4);
		playerBtns.get(4).setTag(player5);
		playerBtns.get(5).setTag(player6);
		
		actionTypeBtns.get(0).setTag(ActionType.Service);
		actionTypeBtns.get(1).setTag(ActionType.Reception);
		actionTypeBtns.get(2).setTag(ActionType.Dig);
		actionTypeBtns.get(3).setTag(ActionType.Attack);
		actionTypeBtns.get(4).setTag(ActionType.Block);
		actionTypeBtns.get(5).setTag(ActionType.Service);
		
		actionScoreBtns.get(0).setTag(ActionScore.MinusMinus);
		actionScoreBtns.get(1).setTag(ActionScore.Minus);
		actionScoreBtns.get(2).setTag(ActionScore.Null);
		actionScoreBtns.get(3).setTag(ActionScore.Plus);
		actionScoreBtns.get(4).setTag(ActionScore.PlusPlus);
		
		Toast.makeText(getApplicationContext(), "Scouting fragment", Toast.LENGTH_SHORT).show();
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
