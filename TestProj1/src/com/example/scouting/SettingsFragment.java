package com.example.scouting;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scouting.server.ScoutingService;
import com.example.settings.SettingsOverview;
import com.example.testproj1.R;

public class SettingsFragment extends Fragment {

	@SuppressWarnings("unused")
	private OnSettingsFragmentInteractionListener mListener;
	private static ScoutingService scoutingService;
	private static ViewHelper viewHelper;

	public SettingsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View V = inflater.inflate(R.layout.fragment_settings, container, false);
		
		SettingsOverview settingsOverview = new SettingsOverview();
		settingsOverview.setScoutingService(scoutingService);
		settingsOverview.setViewHelper(viewHelper);

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.frameSettingsOverview, settingsOverview, "SettingsOverview");
		transaction.commit();
		
		return V;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnSettingsFragmentInteractionListener) activity;
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
	
	public void setScoutingService(ScoutingService scoutingService) {
		SettingsFragment.scoutingService = scoutingService;
	}
	
	public interface OnSettingsFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onSettingsFragmentInteraction();
	}

	public void setViewHelper(ViewHelper viewHelper) {
		SettingsFragment.viewHelper = viewHelper;
	}
}
