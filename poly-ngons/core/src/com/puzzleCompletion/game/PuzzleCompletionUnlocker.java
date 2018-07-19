package com.puzzleCompletion.game;

import com.badlogic.gdx.utils.Array;
import com.polyngons.game.PolyNGons;
import com.polyNgonConstants.game.PolyNGonsTextureName;

public class PuzzleCompletionUnlocker {
	private PolyNGons game;

	public static final byte PUZZLE_UNLOCK = 30;
	public static final byte DIFFICULTY_UNLOCK = 31;
	public static final byte NEW_TEXTURE_UNLOCK = 32;

	public static final byte LOCKED_PUZZLE = 0;
	public static final byte UNLOCKED_PUZZLE = 1;
	public static final byte SOLVED_PUZZLE = 2;

	public boolean unlockingComplete;

	private Array<Byte> currentUnlockState;

	public PuzzleCompletionUnlocker(PolyNGons game){
		this.game = game;

		currentUnlockState = new Array<Byte>();
		unlockingComplete = false;
	}

	public void unlockPuzzleChallengeFeatures(int rating){
		if (!unlockingComplete){
			unlockingComplete = true;
			if (rating >= 60) unlockTexture(PolyNGonsTextureName.B_GRASS_CODE,
					PolyNGonsTextureName.BORDER_TEXTURE);
			if (rating >= 90) unlockTexture(PolyNGonsTextureName.T_GRASSWEAVED_CODE,
					PolyNGonsTextureName.POLYGON_TEXTURE);
			if (rating >= 120) {
				unlockTexture(PolyNGonsTextureName.T_CLAY_CODE, PolyNGonsTextureName.POLYGON_TEXTURE);
				unlockTexture(PolyNGonsTextureName.B_FIERY_CODE, PolyNGonsTextureName.BORDER_TEXTURE);
			}
			if (rating >= 150) unlockTexture(PolyNGonsTextureName.T_BURNT_CODE,
					PolyNGonsTextureName.POLYGON_TEXTURE);
			if (rating >= 180) unlockTexture(PolyNGonsTextureName.B_BUTTERFLY_CODE,
					PolyNGonsTextureName.BORDER_TEXTURE);
			if (rating >= 210) unlockTexture(PolyNGonsTextureName.T_WOOD_CODE,
					PolyNGonsTextureName.POLYGON_TEXTURE);
			if (rating >= 240) unlockTexture(PolyNGonsTextureName.T_CHOCOLATE_CODE,
					PolyNGonsTextureName.POLYGON_TEXTURE);
			if (rating >= 250) {
				unlockTexture(PolyNGonsTextureName.T_GENIUS_CODE, PolyNGonsTextureName.POLYGON_TEXTURE);
				unlockTexture(PolyNGonsTextureName.B_GENIUS_CODE, PolyNGonsTextureName.BORDER_TEXTURE);
			}
		}
	}

	public void unlockPuzzleCollectionFeatures(int difficultyNum, int puzzleNum){
		if (!unlockingComplete){
			unlockingComplete = true;
			byte[] bArray = game.pUpdater.getPValue().get("d" + difficultyNum + "PUnlocks").asByteArray();

			currentUnlockState.add(PUZZLE_UNLOCK);
			game.pUpdater.setDiffPuzzleByteValue(difficultyNum, SOLVED_PUZZLE, puzzleNum - 1);
			if (bArray.length != puzzleNum){
				if (bArray[puzzleNum] == 0){
					game.pUpdater.setDiffPuzzleByteValue(difficultyNum, UNLOCKED_PUZZLE, puzzleNum);

					byte[] bArray2 = game.pUpdater.getPValue().get("d" + difficultyNum + "PUnlocks").asByteArray();
					int highestPNum = game.pUpdater.getPValue().get("highestPuzzle").asIntArray()[difficultyNum - 1];
					for (int i = bArray2.length; i > 0; i--) highestPNum = (bArray2[i - 1] == 1) ? i : highestPNum;
					game.pUpdater.setIntValue(difficultyNum - 1, puzzleNum + 1, true);
					game.pUpdater.setIntValue(difficultyNum - 1, highestPNum, false);
				}
			}
			else {
				int highestPNum = 0;
				game.pUpdater.setIntValue(difficultyNum - 1, highestPNum, false);
			}
			unlockPuzzleCollection();
		}
	}

	private float puzzlesToSolveAchieve(int difficulty){
		byte[] bArray = game.pUpdater.getPValue().get("d" + difficulty + "PUnlocks").asByteArray();
		float percentPuzzle = bArray.length;
		switch (difficulty){
		case 1: percentPuzzle *= 1; break;
		case 2: percentPuzzle *= 1; break;
		case 3: percentPuzzle *= 1; break;
		case 4: percentPuzzle *= 1; break;
		case 5: percentPuzzle *= .9f; break;
		case 6: percentPuzzle *= .9f; break;
		case 7: percentPuzzle *= .8f; break;
		case 8: percentPuzzle *= .8f; break;
		case 9: percentPuzzle *= .7f; break;
		case 10: percentPuzzle *= .7f; break;
		case 11: percentPuzzle *= .6f; break;
		case 12: percentPuzzle *= .6f; break;
		case 13: percentPuzzle *= .6f; break;
		case 14: percentPuzzle *= .5f; break;
		case 15: percentPuzzle *= .5f; break;
		case 16: percentPuzzle *= .5f; break;
		case 17: percentPuzzle *= .4f; break;
		case 18: percentPuzzle *= .4f; break;
		case 19: percentPuzzle *= .4f; break;
		case 20: percentPuzzle *= .4f; break;
		default: break;
		}

		return percentPuzzle;
	}

	private void unlockPuzzleCollection(){
		int maxDiffUnlocked = -1;
		int totalPuzzles1To20 = 0;
		int totalPuzzles1To18 = 0;
		int totalPuzzles1To14 = 0;
		int totalPuzzles1To10 = 0;

		byte[] diffNum = game.pUpdater.getPValue().get("dUnlocks").asByteArray();
		for (int i = diffNum.length - 1; i >= 0; i--){
			if (maxDiffUnlocked == -1){
				if (diffNum[i] == 1){
					maxDiffUnlocked = 0;
					maxDiffUnlocked = i + 1;
				}
			}

			if (i + 1 <= 20) totalPuzzles1To20 += game.pUpdater.getPValue().get(
					"d" + (i + 1) + "PUnlocks").asByteArray().length;
			if (i + 1 <= 18) totalPuzzles1To18 += game.pUpdater.getPValue().get(
					"d" + (i + 1) + "PUnlocks").asByteArray().length;
			if (i + 1 <= 14) totalPuzzles1To14 += game.pUpdater.getPValue().get(
					"d" + (i + 1) + "PUnlocks").asByteArray().length;
			if (i + 1 <= 10) totalPuzzles1To10 += game.pUpdater.getPValue().get(
					"d" + (i + 1) + "PUnlocks").asByteArray().length;
		}

		int totalPuzzleSolved = 0;
		for (int i = maxDiffUnlocked - 1; i >= 0; i--){
			int totalPuzzleInDiffSolved = 0;
			float puzzleMaxSolve = puzzlesToSolveAchieve(i + 1);
			byte[] diffPuzzles = game.pUpdater.getPValue().get("d" + (i + 1) + "PUnlocks").asByteArray();
			for (int i2 = diffPuzzles.length - 1; i2 >= 0; i2--){
				if (diffPuzzles[i2] == 2) {
					totalPuzzleSolved++;
					totalPuzzleInDiffSolved++;
				}
			}

			if (totalPuzzleInDiffSolved >= puzzleMaxSolve) {
				if (i + 1 != 20){
					if (diffNum[i + 1] == 0){
						if (!currentUnlockState.contains(DIFFICULTY_UNLOCK, true)) 
							currentUnlockState.add(DIFFICULTY_UNLOCK);
						game.pUpdater.setDiffPuzzleByteValue(0, UNLOCKED_PUZZLE, i + 1);
					}
				}

				if (i + 1 == 2) unlockTexture(PolyNGonsTextureName.T_GOLDEN_CODE,
						PolyNGonsTextureName.POLYGON_TEXTURE);
				if (i + 1 == 4) unlockTexture(PolyNGonsTextureName.T_BRICK_CODE,
						PolyNGonsTextureName.POLYGON_TEXTURE);
				if (i + 1 == 6) unlockTexture(PolyNGonsTextureName.B_CONCRETE_CODE,
						PolyNGonsTextureName.BORDER_TEXTURE);
				if (i + 1 == 8) unlockTexture(PolyNGonsTextureName.T_METALLIC_CODE,
						PolyNGonsTextureName.POLYGON_TEXTURE);
				if (i + 1 == 11) unlockTexture(PolyNGonsTextureName.B_DYED_CODE,
						PolyNGonsTextureName.BORDER_TEXTURE);
				if (i + 1 == 13) unlockTexture(PolyNGonsTextureName.T_SCRAP_CODE,
						PolyNGonsTextureName.POLYGON_TEXTURE);
				if (i + 1 == 15) unlockTexture(PolyNGonsTextureName.B_SNOW_CODE,
						PolyNGonsTextureName.BORDER_TEXTURE);
				if (i + 1 == 17) unlockTexture(PolyNGonsTextureName.T_BUBBLES_CODE,
						PolyNGonsTextureName.POLYGON_TEXTURE);
				if (i + 1 == 19) unlockTexture(PolyNGonsTextureName.B_KINETIC_CODE,
						PolyNGonsTextureName.BORDER_TEXTURE);
				if (i + 1 == 20) unlockTexture(PolyNGonsTextureName.T_GEM_CODE,
						PolyNGonsTextureName.POLYGON_TEXTURE);
			}
		}

		if (totalPuzzleSolved >= totalPuzzles1To10) unlockTexture(PolyNGonsTextureName.T_BLACKANDWHITE_CODE,
				PolyNGonsTextureName.POLYGON_TEXTURE);
		if (totalPuzzleSolved >= totalPuzzles1To14) unlockTexture(PolyNGonsTextureName.T_FRAME_CODE,
				PolyNGonsTextureName.POLYGON_TEXTURE);
		if (totalPuzzleSolved >= totalPuzzles1To18) unlockTexture(PolyNGonsTextureName.T_MOSAIC_CODE,
				PolyNGonsTextureName.POLYGON_TEXTURE);
		if (totalPuzzleSolved >= totalPuzzles1To20) {
			unlockTexture(PolyNGonsTextureName.T_TRIUMPHANT_CODE, PolyNGonsTextureName.POLYGON_TEXTURE);
			unlockTexture(PolyNGonsTextureName.B_TRIUMPHANT_CODE, PolyNGonsTextureName.BORDER_TEXTURE);
		}
	}

	private void unlockTexture(String textureID, byte textureType){
		if (!game.pUnlocker.isUnlocked(textureID)){
			if (!currentUnlockState.contains(NEW_TEXTURE_UNLOCK, true)) 
				currentUnlockState.add(NEW_TEXTURE_UNLOCK);
			game.pUnlocker.unlockTexture(textureID, textureType);
		}
	}

	public Array<Byte> getCurrentUnlockState() {
		return currentUnlockState;
	}
}
