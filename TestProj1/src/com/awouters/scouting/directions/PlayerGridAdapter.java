package com.awouters.scouting.directions;

import java.util.ArrayList;

import com.awouters.scouting.R;
import com.awouters.scouting.src.Player;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayerGridAdapter extends BaseAdapter {
    private Context mContext;
	private ArrayList<Player> mPlayers;
	private int width;

    public PlayerGridAdapter(Context context, ArrayList<Player> players, int width) {
        this.mContext = context;
        this.mPlayers = players;
    }

	@Override
	public int getCount() {
		return mPlayers.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if(convertView == null){
			
			Player player = mPlayers.get(position);
 
			gridView = inflater.inflate(R.layout.directions_grid_item, null);
			
			RelativeLayout rel = (RelativeLayout) gridView.findViewById(R.id.directions_rel_layout);
			rel.setMinimumHeight(width/3);
 
			TextView textView = (TextView) gridView.findViewById(R.id.imageTextPlayer);
			textView.setText(player.getNumber() + ". " + player.getName());
 
			BitmapDrawable d = new BitmapDrawable(mContext.getResources(), player.getPicture());
			ImageView imageView = (ImageView) gridView.findViewById(R.id.imageViewPlayer);
			imageView.setImageDrawable(d);
		}
		else{
			gridView = (View) convertView;
		}
		
		return gridView;
	}
}
