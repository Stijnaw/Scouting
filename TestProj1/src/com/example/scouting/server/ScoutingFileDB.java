package com.example.scouting.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.testproj1.R;

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
		/*try {
			File root = Environment.getExternalStorageDirectory(); 

			File dir = new File (root.getAbsolutePath() + "/scouting");
			dir.mkdirs();
			File file = new File(dir, "scouting.txt");
            
            FileOutputStream fOut = new FileOutputStream(file);
            
	        ObjectOutputStream oos = new ObjectOutputStream(fOut);
	        oos.writeObject(scoutingService);
	        oos.close();
            fOut.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }*/
		
		return scoutingService;
	}
}
