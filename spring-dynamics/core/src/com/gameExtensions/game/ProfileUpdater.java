package com.gameExtensions.game;

import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.springdynamics.game.SpringDynamics;

public class ProfileUpdater {
	public JsonValue profVal;
	public String userProf;
	
	private Profile prof;
	private SpringDynamics game;
	
	public ProfileUpdater(SpringDynamics game, String jsonString){
		this.game = game;
		
		profVal = new JsonReader().parse(jsonString);
		userProf = game.getLoadedProf();
		prof = game.json.fromJson(Profile.class, jsonString);
	}
	
	public boolean hasNoAds(){
		return prof.noAds;
	}
	
	public void unlockNoAds(){
		prof.noAds = true;
		profVal.get("noAds").set(true);
		game.getPrefs().putString(game.PROF_NAME, Base64Coder.encodeString(game.json.toJson(prof)));
		saveNewData();
	}
	
	public void setLongestDistanceTraveled(int dist){
		prof.longestDistTraveled = dist;
		profVal.get("longestDistTraveled").set(dist, null);
		game.getPrefs().putString(game.PROF_NAME, Base64Coder.encodeString(game.json.toJson(prof)));
		saveNewData();
	}

	public void setQueueHighScore(boolean yes){
		prof.queueHighScore = yes;
		profVal.get("queueHighScore").set(yes);
		game.getPrefs().putString(game.PROF_NAME, Base64Coder.encodeString(game.json.toJson(prof)));
		saveNewData();
	}
	
	private void saveNewData(){
		game.getPrefs().flush();
	}

	/*When changing values in the save profile, call this
	ALWAYS.*/
	public void updateReferences(Profile newValues){
		game.writeString(game.json.toJson(newValues));
		profVal = new JsonReader().parse(game.getLoadedProf());
		userProf = game.getLoadedProf();
	}
}