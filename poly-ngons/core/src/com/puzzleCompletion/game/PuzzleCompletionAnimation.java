package com.puzzleCompletion.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.polyngons.game.GameScreen;

public class PuzzleCompletionAnimation {
	private Array<TextureRegion> imagesToDisplay;
	private Array<Byte> currentUnlockState;
	private GameScreen gScreen;
	private float currentU;
	private int currentRegion;
	private boolean finishedTextureCreation, newUnlocks;

	public PuzzleCompletionAnimation(GameScreen gScreen){
		this.gScreen = gScreen;
		imagesToDisplay = new Array<TextureRegion>();

		currentU = 0;
		currentRegion = 0;
		finishedTextureCreation = false;
		newUnlocks = false;
	}

	public void setTextures(Array<Byte> currentUnlockState){
		if (!finishedTextureCreation){
			finishedTextureCreation = true;
			this.currentUnlockState = currentUnlockState;
			if (currentUnlockState.contains(PuzzleCompletionUnlocker.PUZZLE_UNLOCK, true)){
				if (currentUnlockState.size > 1) {
					newUnlocks = true;
					addTextures();
				}
			}
			else {
				if (currentUnlockState.size > 0) {
					newUnlocks = true;
					addTextures();
				}
			}
		}
	}

//	All Images must be of the same size!
	private void addTextures(){
		if (currentUnlockState.contains(PuzzleCompletionUnlocker.DIFFICULTY_UNLOCK, true))
			imagesToDisplay.add(new TextureRegion(gScreen.game.assetM.get(gScreen.game.assetID.get(
					"newDifficulty"))));
		if (currentUnlockState.contains(PuzzleCompletionUnlocker.NEW_TEXTURE_UNLOCK, true))
			imagesToDisplay.add(new TextureRegion(gScreen.game.assetM.get(gScreen.game.assetID.get(
					"newTexture"))));
	}

	public void renderUnlocks(float x, float y){


		if (imagesToDisplay.size != 0){
			if (imagesToDisplay.size > 1){

				currentU += 0.25f * Gdx.graphics.getDeltaTime();

				if (currentU >= 1) {
					currentU = 0;
					for (TextureRegion reg : imagesToDisplay) reg.setRegion(0, 0, 1f, 1f);
					if (currentRegion + 1 >= imagesToDisplay.size) currentRegion = 0;
					else currentRegion++;
				}

				int index1 = currentRegion;
				int index2 = (currentRegion + 1 >= imagesToDisplay.size) ? 0 : currentRegion + 1;	
				imagesToDisplay.get(index1).setU(currentU);
				imagesToDisplay.get(index2).setU2(currentU);

				gScreen.uiBatch.draw(imagesToDisplay.get(index1), x, y,
						imagesToDisplay.get(index1).getRegionWidth(),
						imagesToDisplay.get(index1).getRegionHeight());
				
				float regWidth = imagesToDisplay.get(index1).getRegionWidth();
				gScreen.uiBatch.draw(imagesToDisplay.get(index2),
						x + regWidth, y, imagesToDisplay.get(index2).getRegionWidth(),
						imagesToDisplay.get(index2).getRegionHeight());
			}
			else {
				gScreen.uiBatch.draw(imagesToDisplay.get(0), x, y, imagesToDisplay.get(0).getRegionWidth(),
						imagesToDisplay.get(0).getRegionHeight());
			}
		}
	}

	public boolean isNewUnlocks() {
		return newUnlocks;
	}
}
