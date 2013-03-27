package com.example.scouting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scouting.server.ScoutingFileDB;
import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Action;
import com.example.scouting.src.ActionScore;
import com.example.scouting.src.ActionType;
import com.example.scouting.src.Match;
import com.example.scouting.src.Player;
import com.example.testproj1.R;

public class ScoutingFragment extends Fragment {

	@SuppressWarnings("unused")
	private OnScoutingFragmentInteractionListener mListener;
	static private ViewHelper viewHelper;
	static private ScoutingFileDB scoutingFileDB;
	static private ScoutingService scoutingService;
	
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
        
        button = (Button) V.findViewById(R.id.btnActionTypeDig);
        button.setTag(ActionType.Dig);
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
        
        button = (Button) V.findViewById(R.id.btnActionTypePass);
        button.setTag(ActionType.Pass);
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
        
        /*
        button = (Button) V.findViewById(R.id.btnControlsReset);
        button.setOnClickListener(controlOnClickListener); 
        if(match == null){
			button.setEnabled(false);
		}*/
        
        button = (Button) V.findViewById(R.id.btnControlsUndo);
        button.setOnClickListener(controlOnClickListener); 
        if(match == null || match.getCurrentSet().getActions().size() == 0){
			button.setEnabled(false);
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
					
					Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
					
					match.addAction(new Action(match.getActivePlayerByPosition(viewHelper.getSelectedPlayer()), viewHelper.getSelectedActionType(), viewHelper.getSelectedActionScore()));
					
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
			        
			        if(match.getCurrentSet().getActions().size() == 0){
						button.setEnabled(false);
					}
			        else{
			        	button.setEnabled(true);
			        }
					
					if(scoutingFileDB != null){
						scoutingFileDB.saveScouting(scoutingService);
					}
				}
				else{
					Toast.makeText(getActivity(), "Action not applied", Toast.LENGTH_SHORT).show();
				}
				break;
			/*
			case R.id.btnControlsReset:
				
				Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedPlayer());
		    	
		    	if(buttonPrev != null){
		    		buttonPrev.getBackground().clearColorFilter();
		    		//buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
		    	}
		    	
		    	buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionType());
		    	
		    	if(buttonPrev != null){
		    		buttonPrev.getBackground().clearColorFilter();
		    		//buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
		    	}
		    	
		    	buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionScore());
		    	
		    	if(buttonPrev != null){
		    		buttonPrev.getBackground().clearColorFilter();
		    		//buttonPrev.setTextColor(getActivity().getApplication().getResources().getColor(android.R.color.primary_text_dark));
		    	}
		    	
				viewHelper.resetSelected();
				
				break;
				*/
				
			case R.id.btnControlsUndo:
				Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
				Action action = match.undoAction();
				
				if(action != null){
					Toast.makeText(getActivity(), action.toString(), Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(getActivity(), "Er zijn geen acties in deze set", Toast.LENGTH_SHORT).show();
				}
				
		        Button button = (Button) getView().findViewById(R.id.btnControlsUndo);
		        
		        if(match.getCurrentSet().getActions().size() == 0){
					button.setEnabled(false);
				}
		        else{
		        	button.setEnabled(true);
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
	    			button.getBackground().setColorFilter(new LightingColorFilter(0x00000000, getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_dark)));
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
			
			final ImageButton button = (ImageButton) getActivity().findViewById(v.getId());
			final TextView text = (TextView) getView().findViewWithTag((Integer) button.getTag()*10);
    		final Integer position = (Integer) button.getTag();

			// Populate teamList
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
	    	Button buttonPrev = (Button) getView().findViewWithTag(viewHelper.getSelectedActionType());
	    	
	    	if(buttonPrev != null){
	    		buttonPrev.getBackground().clearColorFilter();
	    	}
	    	
	    	Button button = (Button) getActivity().findViewById(v.getId());
	    	
	    	if(button != buttonPrev){
	    		button.getBackground().setColorFilter(new LightingColorFilter(0x00000000, getActivity().getApplication().getResources().getColor(android.R.color.holo_blue_dark)));
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
		public void onScoutingFragmentInteraction(View v);
	}

	public void setViewHelper(ViewHelper viewHelper) {
		ScoutingFragment.viewHelper = viewHelper;
	}

	public void setScoutingService(ScoutingService scoutingService) {
		ScoutingFragment.scoutingService = scoutingService;
	}
	
	public void setScoutingFileDB(ScoutingFileDB scoutingFileDB) {
		ScoutingFragment.scoutingFileDB = scoutingFileDB;
	}
}
