package com.polyNGonsFunctions.game;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.polyngons.game.PolyNGons;
import com.polyNgonConstants.game.ProfileState;

public class ProfileUpdater {
	private JsonValue pValue;
	private String userProf;
	private PolyNGons game;

	public ProfileUpdater(PolyNGons game, Json json){
		this.game = game;
		userProf = game.getLoadedProf();
		pValue = new JsonReader().parse(userProf);
	}

	public void setRatingValue(float newValue){
		ProfileState pState = game.json.fromJson(ProfileState.class, userProf);
		pState.rating = newValue;
		updateReferences(pState);
	}
	
	public void setRatingSubtract(float value){
		ProfileState pState = game.json.fromJson(ProfileState.class, userProf);
		pState.ratingToSubtract = value;
		updateReferences(pState);
	}

	public void setIntValue(int index, int newValue, boolean currentOrHighest){
		ProfileState pState = game.json.fromJson(ProfileState.class, userProf);
		getIntArrayName(pState, currentOrHighest)[index] = newValue;
		updateReferences(pState);
	}
	
	public boolean noAds(){
		ProfileState pState = game.json.fromJson(ProfileState.class, userProf);
		return pState.noAds;
	}
	
	public void unlockNoAds(){
		ProfileState pState = game.json.fromJson(ProfileState.class, userProf);
		pState.noAds = true;
		updateReferences(pState);
	}

	/*nameID (0 - 20)*/
	//	0 for just the diffUnlockArray
	public void setDiffPuzzleByteValue(int nameID, byte value, int index){
		ProfileState pState = game.json.fromJson(ProfileState.class, userProf);
		switch (nameID){
		case 0: pState.dUnlocks[index] = value; break;
		case 1: pState.d1PUnlocks[index] = value; break;
		case 2: pState.d2PUnlocks[index] = value; break;
		case 3: pState.d3PUnlocks[index] = value; break;
		case 4: pState.d4PUnlocks[index] = value; break;
		case 5: pState.d5PUnlocks[index] = value; break;
		case 6: pState.d6PUnlocks[index] = value; break;
		case 7: pState.d7PUnlocks[index] = value; break;
		case 8: pState.d8PUnlocks[index] = value; break;
		case 9: pState.d9PUnlocks[index] = value; break;
		case 10: pState.d10PUnlocks[index] = value; break;
		case 11: pState.d11PUnlocks[index] = value; break;
		case 12: pState.d12PUnlocks[index] = value; break;
		case 13: pState.d13PUnlocks[index] = value; break;
		case 14: pState.d14PUnlocks[index] = value; break;
		case 15: pState.d15PUnlocks[index] = value; break;
		case 16: pState.d16PUnlocks[index] = value; break;
		case 17: pState.d17PUnlocks[index] = value; break;
		case 18: pState.d18PUnlocks[index] = value; break;
		case 19: pState.d19PUnlocks[index] = value; break;
		case 20: pState.d20PUnlocks[index] = value; break;
		default: break;
		}
		updateReferences(pState);
	}

	public float getFloatValue(String name){
		return pValue.getFloat(name);
	}

	public int getIntValue(String name, int index){
		return pValue.get(name).asIntArray()[index];
	}

	public int[] getIntArray(String name){
		return pValue.get(name).asIntArray();
	}

	public byte getByteValue(String name, int index){
		return pValue.get(name).asByteArray()[index];
	}

	public byte[] getByteArray(String name){
		return pValue.get(name).asByteArray();
	}

	/*When changing values in the save profile, call this
	ALWAYS.*/
	public void updateReferences(ProfileState newValues){
		game.writeString(game.json.toJson(newValues));
		pValue = new JsonReader().parse(game.getLoadedProf());
		userProf = game.getLoadedProf();
	}

	private int[] getIntArrayName(ProfileState state, boolean currentOrHighest){
		if (currentOrHighest) return state.currentPuzzle;
		else return state.highestPuzzle;
	}

	public JsonValue getPValue(){ return pValue; }
}
