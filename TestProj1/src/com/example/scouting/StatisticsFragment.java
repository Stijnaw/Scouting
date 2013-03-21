package com.example.scouting;

import com.example.scouting.src.Match;
import com.example.testproj1.R;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticsFragment extends Fragment {

	private OnStatisticsFragmentInteractionListener mListener;
	private Match match;
	private ViewHelper vievHelper;

	public StatisticsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View V = inflater.inflate(R.layout.fragment_statistics, container, false);
		return V;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnStatisticsFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnStatisticsFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface OnStatisticsFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onStatisticsFragmentInteraction();
	}

	public void setViewHelper(ViewHelper viewHelper) {
		this.vievHelper = viewHelper;
	}

}
