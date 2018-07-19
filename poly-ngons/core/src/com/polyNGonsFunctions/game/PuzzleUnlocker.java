package com.polyNGonsFunctions.game;

import com.gameTools.game.MyExceptionThrower;
import com.polyngons.game.PolyNGons;
import com.polyNgonConstants.game.PolyNGonsTextureName;

public class PuzzleUnlocker {
	private PolyNGons game;

	public PuzzleUnlocker(PolyNGons game){
		this.game = game;
		createTextureCodeUnlocker();
		checkIfModified();
	}

	public void unlockTexture(String textureID, byte textureType){
		if (!textureID.equals(PolyNGonsTextureName.T_SIMPLE_CODE) && 
				!textureID.equals(PolyNGonsTextureName.B_SIMPLE_CODE) &&
				game.getUser().getBoolean("unlockerExists")){
			StringBuilder t = new StringBuilder(textureID);
			String t1 = t.toString();
			String t2 = t.reverse().toString();

			if (game.getUser().getString(textureID).equals(t1) ||
					game.getUser().getString(textureID).equals(t2)){
				if (textureType == PolyNGonsTextureName.POLYGON_TEXTURE) 
					game.getUser().putBoolean("PolyTextureUnlocked", true);
				else if (textureType == PolyNGonsTextureName.BORDER_TEXTURE) 
					game.getUser().putBoolean("BordTextureUnlocked", true);
				game.getUser().putString(t1, t2);
				game.getUser().flush();
			}
		}
		else {
			try {
				throw (new MyExceptionThrower("Invalid App Signature"));
			} catch (MyExceptionThrower e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean isUnlocked(String textureID){
		if (game.getUser().getString(textureID).equals(getRevearsed(textureID))) return true;
		else return false;
	}

	public void checkIfModified(){
		for (int i = 0; i < 23; i++){
			if (i != 0){
				if (!game.getUser().getString("unlocked" + (i + 1)).equals("Locked")){
					try {
						throw (new MyExceptionThrower("Invalid App Signature"));
					} catch (MyExceptionThrower e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void createTextureCodeUnlocker(){
		if (game.getUser().getBoolean("unlockerExists") != true) putTextureCodes();
	}

	private void putTextureCodes(){
		game.getUser().putString(PolyNGonsTextureName.T_SIMPLE_CODE, "Unlocked");
		game.getUser().putString(PolyNGonsTextureName.T_GOLDEN_CODE,
				PolyNGonsTextureName.T_GOLDEN_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_BRICK_CODE,
				PolyNGonsTextureName.T_BRICK_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_METALLIC_CODE,
				PolyNGonsTextureName.T_METALLIC_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_BLACKANDWHITE_CODE,
				PolyNGonsTextureName.T_BLACKANDWHITE_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_SCRAP_CODE,
				PolyNGonsTextureName.T_SCRAP_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_FRAME_CODE,
				PolyNGonsTextureName.T_FRAME_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_BUBBLES_CODE,
				PolyNGonsTextureName.T_BUBBLES_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_MOSAIC_CODE,
				PolyNGonsTextureName.T_MOSAIC_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_GEM_CODE,
				PolyNGonsTextureName.T_GEM_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_GRASSWEAVED_CODE,
				PolyNGonsTextureName.T_GRASSWEAVED_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_CLAY_CODE,
				PolyNGonsTextureName.T_CLAY_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_BURNT_CODE,
				PolyNGonsTextureName.T_BURNT_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_WOOD_CODE,
				PolyNGonsTextureName.T_WOOD_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_CHOCOLATE_CODE,
				PolyNGonsTextureName.T_CHOCOLATE_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_TRIUMPHANT_CODE,
				PolyNGonsTextureName.T_TRIUMPHANT_CODE);
		game.getUser().putString(PolyNGonsTextureName.T_GENIUS_CODE,
				PolyNGonsTextureName.T_GENIUS_CODE);

		game.getUser().putString(PolyNGonsTextureName.B_SIMPLE_CODE, "Unlocked");
		game.getUser().putString(PolyNGonsTextureName.B_CONCRETE_CODE,
				PolyNGonsTextureName.B_CONCRETE_CODE);
		game.getUser().putString(PolyNGonsTextureName.B_DYED_CODE,
				PolyNGonsTextureName.B_DYED_CODE);
		game.getUser().putString(PolyNGonsTextureName.B_SNOW_CODE,
				PolyNGonsTextureName.B_SNOW_CODE);
		game.getUser().putString(PolyNGonsTextureName.B_KINETIC_CODE,
				PolyNGonsTextureName.B_KINETIC_CODE);
		game.getUser().putString(PolyNGonsTextureName.B_GRASS_CODE,
				PolyNGonsTextureName.B_GRASS_CODE);
		game.getUser().putString(PolyNGonsTextureName.B_FIERY_CODE,
				PolyNGonsTextureName.B_FIERY_CODE);
		game.getUser().putString(PolyNGonsTextureName.B_BUTTERFLY_CODE,
				PolyNGonsTextureName.B_BUTTERFLY_CODE);
		game.getUser().putString(PolyNGonsTextureName.B_TRIUMPHANT_CODE,
				PolyNGonsTextureName.B_TRIUMPHANT_CODE);
		game.getUser().putString(PolyNGonsTextureName.B_GENIUS_CODE,
				PolyNGonsTextureName.B_GENIUS_CODE);

		for (int i = 0; i < 23; i++){
			if (i == 0)game.getUser().putString("unlocked" + (i + 1), "Unlocked");
			else game.getUser().putString("unlocked" + (i + 1), "Locked");
		}

		game.getUser().putBoolean("unlockerExists", true);
		game.getUser().flush();
	}
	
	public static final String getRevearsed(String str){
		StringBuilder sb = new StringBuilder("");
		for (int i = str.length() - 1; i >= 0; --i) sb.append(str.charAt(i));
		return sb.toString();
	}
}
