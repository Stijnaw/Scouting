package com.example.scouting.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.provider.MediaStore;

import com.example.scouting.server.ScoutingService;
import com.example.scouting.src.Player;
import com.example.testproj1.R;

public class SettingsNewPlayer extends PreferenceFragment {

	private static ScoutingService scoutingService;
	private Player player = null;
	private static final int SELECT_PHOTO = 100;
	private String picture = null;

	public SettingsNewPlayer() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings_player);
		
		Preference image = (Preference) findPreference("Image");
		Preference save = (Preference) findPreference("Save");
		Preference delete = (Preference) findPreference("Delete");
		
		EditTextPreference name = (EditTextPreference) findPreference("Name");
		EditTextPreference number = (EditTextPreference) findPreference("Number");
		
		if(player != null){
			name.setTitle(player.getName());
			name.setText(player.getName());
			number.setTitle("Nummer: " + Integer.toString(player.getNumber()));
			number.setText(Integer.toString(player.getNumber()));
			delete.setEnabled(true);
		}
		else{
			name.setText("");
			number.setText("");
		}

		image.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
				return false;
			}
        });
		
		save.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				EditTextPreference name = (EditTextPreference) findPreference("Name");
				EditTextPreference number = (EditTextPreference) findPreference("Number");
				
				if(player == null){
					scoutingService.createNewPlayer(name.getText(), Integer.parseInt(number.getText()), picture);
				}
				else{
					player.setName(name.getText());
					player.setNumber(Integer.parseInt(number.getText()));
					if(picture != null){
						player.setPicture(picture);
					}
				}
				
				// Refresh the settings overview after edit
				SettingsOverview settingsOverview = new SettingsOverview();
				showFragment(settingsOverview);
				
				return false;
			}
        });
		
		delete.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				
			    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
			    
			    alertDialog.setNegativeButton("Nee", null);

			    alertDialog.setMessage("Wil je deze speler verwijderen?");
			    alertDialog.setTitle("Verwijderen?");

			    alertDialog.setPositiveButton("Ja", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						scoutingService.removePlayer(player);
						
						// Refresh the settings overview after edit
						SettingsOverview settingsOverview = new SettingsOverview();
						showFragment(settingsOverview);
					}
				});

			    alertDialog.show();
				
				return false;
			}
        });
	}
	
	private void showFragment(Fragment fragment){
		FragmentManager fragMan = getFragmentManager();
		FragmentTransaction transaction = fragMan.beginTransaction();
		
		if(getActivity().findViewById(R.id.frameSettingsDetail) != null){
			transaction.remove(this);
		}

		transaction.replace(R.id.frameSettingsOverview, fragment, "SettingsOverview");
		transaction.commit();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == Activity.RESULT_OK){  
	            Uri selectedImage = imageReturnedIntent.getData();
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};

	            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            
	            String[] fullPath = cursor.getString(columnIndex).split("/");
	            
	            picture = fullPath[fullPath.length-1];
	            
	            cursor.close();
	        }
	    }
	}

	public void setScoutingService(ScoutingService scoutingService) {
		SettingsNewPlayer.scoutingService = scoutingService;
	}

	public void setPlayer(Player player) {
		this.player  = player;
	}
}
