package com.awouters.scouting.scouting;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.awouters.scouting.R;
import com.awouters.scouting.helpers.ViewHelper;
import com.awouters.scouting.server.ScoutingService;
import com.awouters.scouting.src.Match;

public class ScoutingField extends Fragment {
	private static float startX = 0;
	private static float startY = 0;
	private static float endX = 0;
	private static float endY = 0;
	private static CanvasView canvasView;
	private static ViewHelper viewHelper;
	private static ScoutingService scoutingService;

	public ScoutingField() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View V;
		
		viewHelper = new ViewHelper();
		
		final Match match = scoutingService.findMatchById(viewHelper.getSelectedMatch());
		
    	if(match != null){
			V = inflater.inflate(R.layout.scouting_field, container, false);
			
			final LinearLayout frameScoutingField = (LinearLayout) V.findViewById(R.id.fragment_scouting_field);
	        canvasView = new CanvasView(getActivity());
	        frameScoutingField.addView(canvasView);
	        
			frameScoutingField.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						startX = event.getX();
						startY = event.getY();
						break;
						
					case MotionEvent.ACTION_MOVE:
						endX = event.getX();
						endY = event.getY();
						viewHelper.setDirection(startX/canvasView.getWidth(), startY/canvasView.getHeight(), endX/canvasView.getWidth(), endY/canvasView.getHeight(), Integer.parseInt((String) frameScoutingField.getTag()));
						canvasView.paintLine(startX, startY, endX, endY);
						break;
						
					case MotionEvent.ACTION_UP:
						endX = event.getX();
						endY = event.getY();
						viewHelper.setDirection(startX/canvasView.getWidth(), startY/canvasView.getHeight(), endX/canvasView.getWidth(), endY/canvasView.getHeight(), Integer.parseInt((String) frameScoutingField.getTag()));
						canvasView.paintLine(startX, startY, endX, endY);
						
						hideFragment();
					default:
						break;
					}
					return true;
				}
			});
    	}
    	else{
			V = new View(getActivity());
		}
		
		return V;
	}
	
	protected void hideFragment() {
		FragmentManager fragMan = getFragmentManager();
		FragmentTransaction transaction = fragMan.beginTransaction();
		
		if(getActivity().findViewById(R.id.frameScoutingField) == null){
			Fragment fragment = new ScoutingButtons();
			transaction.replace(R.id.frameScoutingButtons, fragment, "FragmentScoutingButtons");
		}
		
		fragMan.popBackStack();
		
		transaction.commit();		
	}

	private class CanvasView extends View {
		private Paint paint;
		private float startX = 0;
		private float startY = 0;
		private float endX = 0;
		private float endY = 0;

		public CanvasView(Context context) {
			super(context);
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(3);
			if(startX != 0 && startY != 0 && endX != 0 && endY != 0){
				canvas.drawCircle(startX, startY, 5, paint);
				canvas.drawLine(startX, startY, endX, endY, paint);
			}
		}
		
		public void paintLine(float startX, float startY, float endX, float endY){
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
			this.invalidate();
		}
	}
	
	public static void resetDirection(){
		viewHelper.setDirection(0, 0, 0, 0, 0);
		startX = 0;
		startY = 0;
		endX = 0;
		endY = 0;
		canvasView.paintLine(startX, startY, endX, endY);
	}
	
	public void setScoutingService(ScoutingService scoutingService) {
		ScoutingField.scoutingService = scoutingService;
	}
}
