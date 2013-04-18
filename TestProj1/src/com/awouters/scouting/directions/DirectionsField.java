package com.awouters.scouting.directions;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awouters.scouting.R;
import com.awouters.scouting.helpers.ViewHelper;
import com.awouters.scouting.server.ScoutingService;
import com.awouters.scouting.src.Action;
import com.awouters.scouting.src.ActionType;
import com.awouters.scouting.src.Match;
import com.awouters.scouting.src.Player;

public class DirectionsField extends Fragment {

	private static ScoutingService scoutingService;
	private CanvasView canvasView;
	private ArrayList<Action> actions;
	private boolean attack;
	private Integer player;
	private ViewHelper viewHelper;

	public DirectionsField() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View V = inflater.inflate(R.layout.directions_field, container, false);
		
		viewHelper = new ViewHelper();
		
		Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
		Player myPlayer = match.getTeam().getPlayers().get(player);
		
		
		TextView playerName = (TextView) V.findViewById(R.id.directions_field_player);
		playerName.setText(myPlayer.getNumber() + ". " + myPlayer.getName());
		
		LinearLayout frameDirectionsField = (LinearLayout) V.findViewById(R.id.fragment_directions_field);
        canvasView = new CanvasView(getActivity(), Integer.parseInt((String) frameDirectionsField.getTag()));
        
        frameDirectionsField.addView(canvasView);
		
		return V;
	}
	
	private class CanvasView extends View {
		private Paint paint;
		private Integer direction;

		public CanvasView(Context context, Integer direction) {
			super(context);
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			this.direction = direction;
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			paint.setStrokeWidth(3);
			
			float startX = 0, startY = 0, endX = 0, endY = 0;
			
			for(Action action: actions){
				
				if((action.getActionType() == ActionType.Attack && attack == true) || (action.getActionType() == ActionType.Service && attack == false)){

					if(action.getOrientation() == direction){
						startX = action.getStartX()*getWidth();
						startY = action.getStartY()*getHeight();
						endX = action.getEndX()*getWidth();
						endY = action.getEndY()*getHeight();
					}
					else{
						startX = (1-action.getStartY())*getWidth();
						startY = action.getStartX()*getHeight();
						endX = (1-action.getEndY())*getWidth();
						endY = action.getEndX()*getHeight();
					}					
					
					if(startX != 0 && startY != 0 && endX != 0 && endY != 0){
						
						switch (action.getActionScore()) {
						case PlusPlus:
							paint.setColor(Color.GREEN);
							break;
						case Plus:
							paint.setColor(Color.rgb(200, 255, 0));
							break;
						case Null:
							paint.setColor(Color.GRAY);
							break;
						case Minus:
							paint.setColor(Color.rgb(255, 167, 25));
							break;
						case MinusMinus:
							paint.setColor(Color.RED);
							break;
	
						default:
							paint.setColor(Color.GRAY);
							break;
						}
						
						canvas.drawCircle(startX, startY, 5, paint);
						canvas.drawLine(startX, startY, endX, endY, paint);
					}
				}
			}
		}	
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		DirectionsField.scoutingService = scoutingService;
	}

	public void setStats(Integer player, ArrayList<Action> actions, boolean attack) {
		this.player = player;
		this.actions = actions;
		this.attack = attack;
	}
}
