package com.example.scouting;

import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Match;
import com.example.settings.SettingsOverview;
import com.example.testproj1.R;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	private OnSettingsFragmentInteractionListener mListener;
	private ScoutingService scoutingService;
	private ViewHelper viewHelper;

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
		this.scoutingService = scoutingService;
	}
	
	public interface OnSettingsFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onSettingsFragmentInteraction();
	}

	public void setViewHelper(ViewHelper viewHelper) {
		this.viewHelper = viewHelper;
	}
}
