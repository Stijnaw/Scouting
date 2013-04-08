package com.awouters.scouting.scouting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awouters.scouting.R;
import com.awouters.scouting.helpers.ViewHelper;
import com.awouters.scouting.server.ScoutingFileDB;
import com.awouters.scouting.server.ScoutingService;
import com.awouters.scouting.src.Action;
import com.awouters.scouting.src.ActionScore;
import com.awouters.scouting.src.ActionType;
import com.awouters.scouting.src.Match;
import com.awouters.scouting.src.Player;
import com.awouters.scouting.src.Set;

public class ScoutingButtons extends Fragment {

	private static final int NEW_SET = 0;
	private Vibrator myVib;
	static private ViewHelper viewHelper;
	static private ScoutingFileDB scoutingFileDB;
	static ScoutingService scoutingService;
	private long vibrateTime = 25;
	private ArrayList<String> setList;
	
	public ScoutingButtons() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		setHasOptionsMenu(true);
		
        View V = inflater.inflate(R.layout.scouting_buttons, container, false);
        
        viewHelper = new ViewHelper();
        
        showFieldFragment(false);
        
		myVib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        
        /***********************************
    	 * Get buttons
    	 **********************************/
        Button button;
        TextView text;
        ImageButton imgButton;
        imgButton = (ImageButton) V.findViewById(R.id.btnPlayer1);
        text = (TextView) V.findViewById(R.id.txtPlayer1);
        imgButton.setTag(1);
        text.setTag(10);
        imgButton.setOnClickListener(playerOnClickListener);
        imgButton.setOnLongClickListener(playerOnLongClickListener);
        
        Player player;
        Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
        
        if(match == null){
			imgButton.setEnabled(false);
		}
        else{
        	player = match.getActivePlayers().get(0);
        	if(player != null){
                BitmapDrawable d = new BitmapDrawable(getResources(), player.getPicture());
                imgButton.setImageDrawable(d);
        		text.setText(player.getNumber() + ". " + player.getName());
        	}
        	else{
        		imgButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        imgButton = (ImageButton) V.findViewById(R.id.btnPlayer2);
        text = (TextView) V.findViewById(R.id.txtPlayer2);
        imgButton.setTag(2);
        text.setTag(20);
        imgButton.setOnClickListener(playerOnClickListener);
        imgButton.setOnLongClickListener(playerOnLongClickListener);
        
        if(match == null){
			imgButton.setEnabled(false);
		}
        else{
        	player = match.getActivePlayers().get(1);
        	if(player != null){
                BitmapDrawable d = new BitmapDrawable(getResources(), player.getPicture());
                imgButton.setImageDrawable(d);
        		text.setText(player.getNumber() + ". " + player.getName());
        	}
        	else{
        		imgButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        imgButton = (ImageButton) V.findViewById(R.id.btnPlayer3);
        text = (TextView) V.findViewById(R.id.txtPlayer3);
        imgButton.setTag(3);
        text.setTag(30);
        imgButton.setOnClickListener(playerOnClickListener);
        imgButton.setOnLongClickListener(playerOnLongClickListener);
        
        if(match == null){
			imgButton.setEnabled(false);
		}
        else{
        	player = match.getActivePlayers().get(2);
        	if(player != null){
                BitmapDrawable d = new BitmapDrawable(getResources(), player.getPicture());
                imgButton.setImageDrawable(d);
        		text.setText(player.getNumber() + ". " + player.getName());
        	}
        	else{
        		imgButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        imgButton = (ImageButton) V.findViewById(R.id.btnPlayer4);
        text = (TextView) V.findViewById(R.id.txtPlayer4);
        imgButton.setTag(4);
        text.setTag(40);
        imgButton.setOnClickListener(playerOnClickListener);
        imgButton.setOnLongClickListener(playerOnLongClickListener);
        if(match == null){
			imgButton.setEnabled(false);
		}
        else{
        	player = match.getActivePlayers().get(3);
        	if(player != null){
                BitmapDrawable d = new BitmapDrawable(getResources(), player.getPicture());
                imgButton.setImageDrawable(d);
        		text.setText(player.getNumber() + ". " + player.getName());
        	}
        	else{
        		imgButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        imgButton = (ImageButton) V.findViewById(R.id.btnPlayer5);
        text = (TextView) V.findViewById(R.id.txtPlayer5);
        imgButton.setTag(5);
        text.setTag(50);
        imgButton.setOnClickListener(playerOnClickListener);
        imgButton.setOnLongClickListener(playerOnLongClickListener);
        if(match == null){
			imgButton.setEnabled(false);
		}
        else{
        	player = match.getActivePlayers().get(4);
        	if(player != null){
                BitmapDrawable d = new BitmapDrawable(getResources(), player.getPicture());
                imgButton.setImageDrawable(d);
        		text.setText(player.getNumber() + ". " + player.getName());
        	}
        	else{
        		imgButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        imgButton = (ImageButton) V.findViewById(R.id.btnPlayer6);
        text = (TextView) V.findViewById(R.id.txtPlayer6);
        imgButton.setTag(6);
        text.setTag(60);
        imgButton.setOnClickListener(playerOnClickListener);
        imgButton.setOnLongClickListener(playerOnLongClickListener);
        if(match == null){
			imgButton.setEnabled(false);
		}
        else{
        	player = match.getActivePlayers().get(5);
        	if(player != null){
                BitmapDrawable d = new BitmapDrawable(getResources(), player.getPicture());
                imgButton.setImageDrawable(d);
        		text.setText(player.getNumber() + ". " + player.getName());
        	}
        	else{
        		imgButton.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        
        button = (Button) V.findViewById(R.id.btnActionTypeService);
        button.setTag(ActionType.Service);
        button.setOnClickListener(actionTypeOnClickListener);
        if(match == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionTypeReception);
        button.setTag(ActionType.Reception);
        button.setOnClickListener(actionTypeOnClickListener);
        if(match == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionTypeAttack);
        button.setTag(ActionType.Attack);
        button.setOnClickListener(actionTypeOnClickListener);
        if(match == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionTypeBlock);
        button.setTag(ActionType.Block);
        button.setOnClickListener(actionTypeOnClickListener);
        if(match == null){
			button.setEnabled(false);
		}        
        
        button = (Button) V.findViewById(R.id.btnActionScoreMinusMinus);
        button.setTag(ActionScore.MinusMinus);
        button.setOnClickListener(actionScoreOnClickListener);
        if(match == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionScoreMinus);
        button.setTag(ActionScore.Minus);
        button.setOnClickListener(actionScoreOnClickListener);
        if(match == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionScoreNull);
        button.setTag(ActionScore.Null);
        button.setOnClickListener(actionScoreOnClickListener);
        if(match == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionScorePlus);
        button.setTag(ActionScore.Plus);
        button.setOnClickListener(actionScoreOnClickListener);
        if(match == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionScorePlusPlus);
        button.setTag(ActionScore.PlusPlus);
        button.setOnClickListener(actionScoreOnClickListener);
        if(match == null){
			button.setEnabled(false);
		}
        
        
        button = (Button) V.findViewById(R.id.btnControlsApply);
        button.setOnClickListener(controlOnClickListener); 
        if(match == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnControlsUndo);
        button.setOnClickListener(controlOnClickListener); 
        if(match == null || match.getCurrentSet().getActions().size() == 0){
			button.setEnabled(false);
		}
        else{
        	TextView txt = (TextView) V.findViewById(R.id.txtUndo);
        	txt.setText(match.getCurrentSet().getLastAction().toString());
        }
        
        // Restore previous selections
		if(viewHelper.getSelectedPlayer() != null){
			imgButton = (ImageButton) V.findViewWithTag(viewHelper.getSelectedPlayer());
			imgButton.getBackground().setColorFilter(new LightingColorFilter(0x00000000, getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_dark)));
		}
		
		
		if(viewHelper.getSelectedActionType() != null){
			button = (Button) V.findViewWithTag(viewHelper.getSelectedActionType());
			button.getBackground().setColorFilter(new LightingColorFilter(0x00000000, getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_dark)));
		}
		
		if(viewHelper.getSelectedActionScore() != null){
			button = (Button) V.findViewWithTag(viewHelper.getSelectedActionScore());
			button.getBackground().setColorFilter(new LightingColorFilter(0x00000000, getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_dark)));
		}
		
        return V;
	}
	
	OnClickListener controlOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
						
			switch (v.getId()) {	
			case R.id.btnControlsApply:
				if(viewHelper.getSelectedPlayer() != null && viewHelper.getSelectedActionType() != null && viewHelper.getSelectedActionScore() != null){
					myVib.vibrate(vibrateTime);
					
					Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
					
					if(viewHelper.getSelectedActionType() == ActionType.Attack || viewHelper.getSelectedActionType() == ActionType.Service){
						match.addAction(new Action(match.getActivePlayerByPosition(viewHelper.getSelectedPlayer()), viewHelper.getSelectedActionType(), viewHelper.getSelectedActionScore(), viewHelper.getDirectionStartX(), viewHelper.getDirectionStartY(), viewHelper.getDirectionEndX(), viewHelper.getDirectionEndY(), viewHelper.getDirectionOrientation()));
						ScoutingField.resetDirection();
					}
					else{
						match.addAction(new Action(match.getActivePlayerByPosition(viewHelper.getSelectedPlayer()), viewHelper.getSelectedActionType(), viewHelper.getSelectedActionScore()));
					}
						
			    	ImageButton imgButtonPrev = (ImageButton) getView().findViewWithTag(viewHelper.getSelectedPlayer());
			    	
			    	if(imgButtonPrev != null){
			    		imgButtonPrev.getBackground().clearColorFilter();
			    	}
			    	
			    	Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionType());
			    	
			    	if(buttonPrev != null){
			    		buttonPrev.getBackground().clearColorFilter();
			    	}
			    	
			    	buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionScore());
			    	
			    	if(buttonPrev != null){
			    		buttonPrev.getBackground().clearColorFilter();
			    	}
			    	
					viewHelper.resetSelected();
					
			        Button button = (Button) getView().findViewById(R.id.btnControlsUndo);
			        TextView txt = (TextView) getView().findViewById(R.id.txtUndo);
			        
			        if(match.getCurrentSet().getActions().size() == 0){
						button.setEnabled(false);
						txt.setText("");
					}
			        else{
			        	button.setEnabled(true);
			        	txt.setText(match.getCurrentSet().getLastAction().toString());
			        }
					
					if(scoutingFileDB != null){
						scoutingFileDB.saveScouting(scoutingService);
					}
				}
				else{
					Toast.makeText(getActivity(), "Action not applied", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.btnControlsUndo:
				Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
				Action action = match.undoAction();
				
				if(action != null){
					myVib.vibrate(vibrateTime);
					Toast.makeText(getActivity(), action.toString(), Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getActivity(), "Er zijn geen acties in deze set", Toast.LENGTH_SHORT).show();
				}
				
		        Button button = (Button) getView().findViewById(R.id.btnControlsUndo);
		        TextView txt = (TextView) getView().findViewById(R.id.txtUndo);
		        
		        if(match.getCurrentSet().getActions().size() == 0){
		        	txt.setText("");
					button.setEnabled(false);
				}
		        else{
		        	button.setEnabled(true);
		        	txt.setText(match.getCurrentSet().getLastAction().toString());
		        }
				break;
				
			}
		}
	};
	
	OnClickListener playerOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
	    	ImageButton buttonPrev = (ImageButton) getView().findViewWithTag(viewHelper.getSelectedPlayer());
	    	
	    	if(buttonPrev != null){
	    		buttonPrev.getBackground().clearColorFilter();
	    	}
	    	
	    	ImageButton button = (ImageButton) getActivity().findViewById(v.getId());
	    	
	    	if(button != buttonPrev){

	    		Integer position = (Integer) button.getTag();
	    		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
	    		Player player = (Player) match.getActivePlayerByPosition(position);
	    		
	    		if(player != null){
	    			myVib.vibrate(vibrateTime);
	    			button.getBackground().setColorFilter(new LightingColorFilter(0x00000000, getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_dark)));
	    			viewHelper.setSelectedPlayer(position);
	    		}
			}
	    	else{
	    		myVib.vibrate(vibrateTime);
	    		viewHelper.setSelectedPlayer(null);
	    	}
		}
	};
	
	OnLongClickListener playerOnLongClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			myVib.vibrate(vibrateTime);
			
			final ImageButton button = (ImageButton) getActivity().findViewById(v.getId());
			final TextView text = (TextView) getView().findViewWithTag((Integer) button.getTag()*10);
    		final Integer position = (Integer) button.getTag();

			// Populate playerList
			int i = 0;
			Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
			final List<Player> players = new ArrayList<Player>(match.getTeam().getPlayers());
			List<Player> activePlayers = match.getActivePlayers();
			players.removeAll(activePlayers);
			
			Collections.sort(players, new Comparator<Player>() {

				@Override
				public int compare(Player p1, Player p2) {
					return p1.getNumber().compareTo(p2.getNumber());
				}

			});
			
			final String entries[] = new String[players.size()+1];
			
	        for (Player player: players) {
	            entries[i] = player.getNumber() + ". " + player.getName();
	            i++;
	        }
	        
	        entries[i] = "Verwijder Speler";

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setTitle("Selecteer een speler");
			builder.setItems(entries, new DialogInterface.OnClickListener() {

			   public void onClick(DialogInterface dialog, int item) {
				   Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
				   if(item == (entries.length-1)){
					   match.setPlayerActive(null, position);
					   viewHelper.setSelectedPlayer(null);
					   button.setImageDrawable(null);
					   button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
					   text.setText("");
				   }
				   else{
					   match.setPlayerActive(players.get(item), position);
					   text.setText(entries[item]);
					   button.getBackground().clearColorFilter();
		               BitmapDrawable d = new BitmapDrawable(getResources(), players.get(item).getPicture());
		               button.setImageDrawable(d);
				   }
			   }

			});

			AlertDialog alert = builder.create();

			alert.show();
	    	
	    	return false;
		}
	};
	
	OnClickListener actionTypeOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			myVib.vibrate(vibrateTime);
			
	    	Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionType());
	    	
	    	if(buttonPrev != null){
	    		buttonPrev.getBackground().clearColorFilter();
	    	}
	    	
	    	Button button = (Button) getActivity().findViewById(v.getId());
	    	
	    	if(button != buttonPrev){
	    		button.getBackground().setColorFilter(new LightingColorFilter(0x00000000, getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_dark)));
	    		viewHelper.setSelectedActionType((ActionType) button.getTag());
	    		
		    	if(v.getId() == R.id.btnActionTypeService || v.getId() == R.id.btnActionTypeAttack){
		    		showFieldFragment(true);
		    	}
	    	}
	    	else{
	    		viewHelper.setSelectedActionType(null);
	    	}
		}
	};
	
	OnClickListener actionScoreOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			myVib.vibrate(vibrateTime);
			
	    	Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionScore());
	    	
	    	if(buttonPrev != null){
	    		buttonPrev.getBackground().clearColorFilter();
	    	}
	    	
	    	Button button = (Button) getActivity().findViewById(v.getId());
	    	
	    	if(button != buttonPrev){
	    		button.getBackground().setColorFilter(new LightingColorFilter(0x00000000, getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_dark)));
	    		viewHelper.setSelectedActionScore((ActionScore) button.getTag());
	    	}
	    	else{
	    		viewHelper.setSelectedActionScore(null);
	    	}
		}
	};
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
    	if(getActivity().findViewById(R.id.frameScoutingButtons) != null){
    		inflater.inflate(R.menu.scouting_menu, menu);
			
	    	final Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
	    	if(match != null){	    	
				MenuItem menuSpinner = menu.findItem(R.id.set_spinner);
				menuSpinner.setVisible(true);
		    	Spinner spinner = (Spinner) menuSpinner.getActionView();
		    	
		    	menu.add(0, NEW_SET, 0, "Nieuwe set");
	
		        setList = new ArrayList<String>();
		        
		        for(Set set : match.getSets()) {
					setList.add("Set " + match.getSetNumber(set));
				}
	
		        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, setList);
	
		        spinner.setAdapter(adapter);
		        spinner.setSelection(match.getCurrentSetNumber()-1);
	
		        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		            @Override
		            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		            	Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
	
				        ScoutingButtons scoutingButtons = new ScoutingButtons();
						
				        if(match.getCurrentSetNumber() != position+1){
				        	match.setCurrentSet(match.getSetByNumber(position+1));
				        	showFragment(scoutingButtons);
				        }
		            }
	
		            @Override
		            public void onNothingSelected(AdapterView<?> parentView) {
		            }
	
		        });
		        
		        spinner.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
	
						dialog();
					    
						return false;
					}
				});
	    	}
    	}
    	
    	super.onCreateOptionsMenu(menu, inflater);
	}
	
	private void showFragment(Fragment fragment){
		FragmentManager fragMan = getFragmentManager();
		FragmentTransaction transaction = fragMan.beginTransaction();
		transaction.replace(R.id.frameScoutingButtons, fragment, "FragmentScoutingButtons");
		transaction.commit();
	}
	
	private void showFieldFragment(boolean clicked){
		ScoutingField fragment = new ScoutingField();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		if(clicked == false && getActivity().findViewById(R.id.frameScoutingField) != null){
			transaction.replace(R.id.frameScoutingField, fragment, "FragmentScoutingField");
		}
		else if(clicked == true && getActivity().findViewById(R.id.frameScoutingField) == null){
			transaction.replace(R.id.frameScoutingButtons, fragment, "FragmentScoutingField");
			transaction.addToBackStack(null);
		}
		
		transaction.commit();
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case NEW_SET:
            	Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
            	setList.add("Set " + match.getSetNumber(match.newSet()));
            	Spinner spinner = (Spinner) getActivity().findViewById(R.id.set_spinner);
            	spinner.setSelection(match.getCurrentSetNumber()-1);
            	
	            getActivity().invalidateOptionsMenu();
	            ScoutingButtons scoutingButtons = new ScoutingButtons();
	        	showFragment(scoutingButtons);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
    private void dialog(){
    	final Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
    	
	    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

	    alertDialog.setPositiveButton("Ja", new Dialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
	            match.removeSetByNumber(match.getCurrentSetNumber());
	            getActivity().invalidateOptionsMenu();
	            ScoutingButtons scoutingButtons = new ScoutingButtons();
	        	showFragment(scoutingButtons);
			}
		});
	    
	    alertDialog.setNegativeButton("Nee", null);

	    alertDialog.setMessage("Wil je deze set verwijderen?");
	    alertDialog.setTitle("Verwijderen?");
	    alertDialog.show();
    }

	public void setScoutingService(ScoutingService scoutingService) {
		ScoutingButtons.scoutingService = scoutingService;
	}
	
	public void setScoutingFileDB(ScoutingFileDB scoutingFileDB) {
		ScoutingButtons.scoutingFileDB = scoutingFileDB;
	}
}
