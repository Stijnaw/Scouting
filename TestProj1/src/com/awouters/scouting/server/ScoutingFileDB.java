package com.awouters.scouting.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.awouters.scouting.helpers.ViewHelper;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class ScoutingFileDB{
	
	private Context ctx;

	public ScoutingFileDB(Context ctx){
		this.ctx = ctx;
	}

	public ScoutingService saveScouting(ScoutingService scoutingService){
		try {
	        FileOutputStream fos = ctx.openFileOutput("scoutingService.txt", Context.MODE_PRIVATE);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(scoutingService);
	        oos.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
		
		return scoutingService;
	}
	
	public ScoutingService getScouting(){
	    try {
	        FileInputStream fis = ctx.openFileInput("scoutingService.txt");
	        ObjectInputStream is = new ObjectInputStream(fis);
	        Object readObject = is.readObject();
	        is.close();

	        if(readObject != null && readObject instanceof ScoutingService) {
	            return (ScoutingService) readObject;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		
		return null;
	}

	public ScoutingService saveScoutingExtern(ScoutingService scoutingService) {
		
		String dateTime = new SimpleDateFormat("ddMMyy-HHmmss", Locale.getDefault()).format(new Date());		
		
		String filename = "Scouting" + dateTime + ".txt";
		File file = new File(Environment.getExternalStorageDirectory() + "/scouting/", filename);
		FileOutputStream fos;
		try {
		    fos = new FileOutputStream(file);
            
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(scoutingService);
	        oos.close();
		    fos.close();
		} catch (FileNotFoundException e) {
		    Toast.makeText(ctx, "Filenotfound", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(ctx, "IOException", Toast.LENGTH_SHORT).show();
		}
		
		return scoutingService;
	}

	public ScoutingService importScoutingFromExtern(String fileName) {
	    try {
	    	File file = new File(fileName);
	    	FileInputStream fis = new FileInputStream(file);
	        ObjectInputStream is = new ObjectInputStream(fis);
	        Object readObject = is.readObject();
	        is.close();

	        if(readObject != null && readObject instanceof ScoutingService) {
	            return (ScoutingService) readObject;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		
		return null;
	}

	public ViewHelper saveView(ViewHelper viewHelper) {
		try {
	        FileOutputStream fos = ctx.openFileOutput("viewHelper.txt", Context.MODE_PRIVATE);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(viewHelper);
	        oos.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
		
		return viewHelper;
	}
	
	public ViewHelper getView(){
	    try {
	        FileInputStream fis = ctx.openFileInput("viewHelper.txt");
	        ObjectInputStream is = new ObjectInputStream(fis);
	        Object readObject = is.readObject();
	        is.close();

	        if(readObject != null && readObject instanceof ViewHelper) {
	            return (ViewHelper) readObject;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		
		return null;
	}
}
