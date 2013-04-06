package com.awouters.scouting.server;


public class ScoutingDB4O{
	/*private EmbeddedObjectContainer db;
	
	public ScoutingDB4O(Context ctx){
		
		if (db == null || db.ext().isClosed()) {  
			EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
			configuration.common().updateDepth(50);
			
			String dbPath = ctx.getDir("data", 0) + "/" + "scouting.db4o";
			db = Db4oEmbedded.openFile(configuration, dbPath);
		}
	}

	public ScoutingService saveScouting(ScoutingService scoutingService){
		db.store(scoutingService);
		
		return scoutingService;
	}
	
	public ScoutingService getScouting(){
		return (ScoutingService) db.query(ScoutingService.class).get(0);
	}

	public void closeScouting() {
		if(db != null){
			db.close();
		}
	}*/
}
