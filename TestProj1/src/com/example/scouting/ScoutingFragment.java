package com.example.scouting;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Action;
import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Player;
import com.example.testproj1.R;

public class ScoutingFragment extends Fragment {

	private OnScoutingFragmentInteractionListener mListener;
	private ViewHelper viewHelper;
	
	public ScoutingFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
        View V = inflater.inflate(R.layout.fragment_scouting, container, false);
        
        /***********************************
    	 * Get buttons
    	 **********************************/
        Button button;
        button = (Button) V.findViewById(R.id.btnPlayer1);
        button.setTag(1);
        button.setOnClickListener(playerOnClickListener);
        button.setOnLongClickListener(playerOnLongClickListener);
        Player player;
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        else{
        	player = viewHelper.getSelectedMatch().getActivePlayers().get(0);
        	if(player != null){
        		button.setText(player.getName());
        	}
        	else{
        		button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        button = (Button) V.findViewById(R.id.btnPlayer2);
        button.setTag(2);
        button.setOnClickListener(playerOnClickListener);
        button.setOnLongClickListener(playerOnLongClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        else{
        	player = viewHelper.getSelectedMatch().getActivePlayers().get(1);
        	if(player != null){
        		button.setText(player.getName());
        	}
        	else{
        		button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        button = (Button) V.findViewById(R.id.btnPlayer3);
        button.setTag(3);
        button.setOnClickListener(playerOnClickListener);
        button.setOnLongClickListener(playerOnLongClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        else{
        	player = viewHelper.getSelectedMatch().getActivePlayers().get(2);
        	if(player != null){
        		button.setText(player.getName());
        	}
        	else{
        		button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        button = (Button) V.findViewById(R.id.btnPlayer4);
        button.setTag(4);
        button.setOnClickListener(playerOnClickListener);
        button.setOnLongClickListener(playerOnLongClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        else{
        	player = viewHelper.getSelectedMatch().getActivePlayers().get(3);
        	if(player != null){
        		button.setText(player.getName());
        	}
        	else{
        		button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        button = (Button) V.findViewById(R.id.btnPlayer5);
        button.setTag(5);
        button.setOnLongClickListener(playerOnLongClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        else{
        	player = viewHelper.getSelectedMatch().getActivePlayers().get(4);
        	if(player != null){
        		button.setText(player.getName());
        	}
        	else{
        		button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        button = (Button) V.findViewById(R.id.btnPlayer6);
        button.setTag(6);
        button.setOnLongClickListener(playerOnLongClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        else{
        	player = viewHelper.getSelectedMatch().getActivePlayers().get(5);
        	if(player != null){
        		button.setText(player.getName());
        	}
        	else{
        		button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
        	}
        }
        
        
        button = (Button) V.findViewById(R.id.btnActionTypeService);
        button.setTag(ActionType.Service);
        button.setOnClickListener(actionTypeOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionTypeReception);
        button.setTag(ActionType.Reception);
        button.setOnClickListener(actionTypeOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionTypeDig);
        button.setTag(ActionType.Dig);
        button.setOnClickListener(actionTypeOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionTypeAttack);
        button.setTag(ActionType.Attack);
        button.setOnClickListener(actionTypeOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionTypeBlock);
        button.setTag(ActionType.Block);
        button.setOnClickListener(actionTypeOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionTypePass);
        button.setTag(ActionType.Pass);
        button.setOnClickListener(actionTypeOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        
        button = (Button) V.findViewById(R.id.btnActionScoreMinusMinus);
        button.setTag(ActionScore.MinusMinus);
        button.setOnClickListener(actionScoreOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionScoreMinus);
        button.setTag(ActionScore.Minus);
        button.setOnClickListener(actionScoreOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionScoreNull);
        button.setTag(ActionScore.Null);
        button.setOnClickListener(actionScoreOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionScorePlus);
        button.setTag(ActionScore.Plus);
        button.setOnClickListener(actionScoreOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnActionScorePlusPlus);
        button.setTag(ActionScore.PlusPlus);
        button.setOnClickListener(actionScoreOnClickListener);
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        
        button = (Button) V.findViewById(R.id.btnControlsApply);
        button.setOnClickListener(controlOnClickListener); 
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnControlsReset);
        button.setOnClickListener(controlOnClickListener); 
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        button = (Button) V.findViewById(R.id.btnControlsUndo);
        button.setOnClickListener(controlOnClickListener); 
        if(viewHelper.getSelectedMatch() == null){
			button.setEnabled(false);
		}
        
        // Restore previous selections
		if(viewHelper.getSelectedPlayer() != null){
			button = (Button) V.findViewWithTag(viewHelper.getSelectedPlayer());
			button.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_light));
		}
		
		
		if(viewHelper.getSelectedActionType() != null){
			button = (Button) V.findViewWithTag(viewHelper.getSelectedActionType());
			button.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_light));
		}
		
		if(viewHelper.getSelectedActionScore() != null){
			button = (Button) V.findViewWithTag(viewHelper.getSelectedActionScore());
			button.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_light));
		}
		
        return V;
	}
	
	OnClickListener controlOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {	
			case R.id.btnControlsApply:
				if(viewHelper.getSelectedPlayer() != null && viewHelper.getSelectedActionType() != null && viewHelper.getSelectedActionScore() != null){
					viewHelper.getSelectedMatch().addAction(new Action(viewHelper.getSelectedMatch().getActivePlayerByPosition(viewHelper.getSelectedPlayer()), viewHelper.getSelectedActionType(), viewHelper.getSelectedActionScore()));
					
			    	Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedPlayer());
			    	
			    	if(buttonPrev != null){
			    		buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
			    	}
			    	
			    	buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionType());
			    	
			    	if(buttonPrev != null){
			    		buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
			    	}
			    	
			    	buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionScore());
			    	
			    	if(buttonPrev != null){
			    		buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
			    	}
			    	
					viewHelper.resetSelected();
					
					Toast.makeText(getActivity(), "Action applied", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getActivity(), "Action not applied", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.btnControlsReset:
				Toast.makeText(getActivity(), "Reset", Toast.LENGTH_SHORT).show();
				
				Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedPlayer());
		    	
		    	if(buttonPrev != null){
		    		buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
		    	}
		    	
		    	buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionType());
		    	
		    	if(buttonPrev != null){
		    		buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
		    	}
		    	
		    	buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionScore());
		    	
		    	if(buttonPrev != null){
		    		buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
		    	}
		    	
				viewHelper.resetSelected();
				
				break;
				
			case R.id.btnControlsUndo:
				// TODO: make undo function
				Toast.makeText(getActivity(), "Todo: undo", Toast.LENGTH_SHORT).show();
				break;
				
			}
		}
	};
	
	OnClickListener playerOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
	    	Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedPlayer());
	    	
	    	if(buttonPrev != null){
	    		buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
	    	}
	    	
	    	Button button = (Button) getActivity().findViewById(v.getId());
	    	
	    	if(button != buttonPrev){

	    		Integer position = (Integer) button.getTag();
	    		Player player = (Player) viewHelper.getSelectedMatch().getActivePlayerByPosition(position);
	    		
	    		if(player != null){
	    			button.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_light));
	    			viewHelper.setSelectedPlayer(position);
	    		}
	    		else{
	    			viewHelper.setSelectedPlayer(null);
	    		}
			}
	    	else{
	    		viewHelper.setSelectedPlayer(null);
	    	}
		}
	};
	
	OnLongClickListener playerOnLongClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			
			final Button button = (Button) getActivity().findViewById(v.getId());
    		final Integer position = (Integer) button.getTag();

			// Populate teamList
			int i = 0;
			final List<Player> players = new ArrayList<Player>(viewHelper.getSelectedMatch().getTeam().getPlayers());
			List<Player> activePlayers = viewHelper.getSelectedMatch().getActivePlayers();
			players.removeAll(activePlayers);
			final String entries[] = new String[players.size()+1];
			
	        for (Player player: players) {
	            entries[i] = player.getName();
	            i++;
	        }
	        
	        entries[i] = "Verwijder Speler";

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setTitle("Selecteer een speler");
			builder.setItems(entries, new DialogInterface.OnClickListener() {

			   public void onClick(DialogInterface dialog, int item) {
				   if(item == (entries.length-1)){
					   viewHelper.getSelectedMatch().setPlayerActive(null, position);
					   button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xF0F0F0F0));
					   button.setText("");
				   }
				   else{
					   viewHelper.getSelectedMatch().setPlayerActive(players.get(item), position);
					   button.setText(entries[item]);
					   button.getBackground().clearColorFilter();
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
	    	Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionType());
	    	
	    	if(buttonPrev != null){
	    		buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
	    	}
	    	
	    	Button button = (Button) getActivity().findViewById(v.getId());
	    	
	    	if(button != buttonPrev){
	    		button.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_light));
	    		viewHelper.setSelectedActionType((ActionType) button.getTag());
	    	}
	    	else{
	    		viewHelper.setSelectedActionType(null);
	    	}
		}
	};
	
	OnClickListener actionScoreOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
	    	Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionScore());
	    	
	    	if(buttonPrev != null){
	    		buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
	    	}
	    	
	    	Button button = (Button) getActivity().findViewById(v.getId());
	    	
	    	if(button != buttonPrev){
	    		button.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_light));
	    		viewHelper.setSelectedActionScore((ActionScore) button.getTag());
	    	}
	    	else{
	    		viewHelper.setSelectedActionScore(null);
	    	}
		}
	};
	private ScoutingService scoutingService;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnScoutingFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnScoutingFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onScoutingFragmentInteraction(View v);
	}

	public void setViewHelper(ViewHelper viewHelper) {
		this.viewHelper = viewHelper;
	}

	public void setScoutingService(ScoutingService scoutingService) {
		this.scoutingService = scoutingService;
	}
}
