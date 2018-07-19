package com.polyNGonsUI.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gameTools.game.ShadedBitmapFont;
import com.polyngons.game.GameScreen;
import com.polyNgonConstants.game.PolyNGonsGameSelection;

public class TutorialUIManager {
	private boolean disableOtherInputs, delayListener;
	private int currentTutorialText;
	private GameScreen gScreen;
	private ShadedBitmapFont tipFont;
	private String[] tutorialArgs;

	public TutorialUIManager(GameScreen gScreen, boolean tSelected){
		this.gScreen = gScreen;
		if (tSelected){
			createTutorialText();
			disableOtherInputs = true;
			currentTutorialText = 0;
		} else {
			disableOtherInputs = false;
		}
		delayListener = true;
	}

	private void createTutorialText(){

		//		Set requestOtherInputNum: 3, 6
		tutorialArgs = new String[15];
		tutorialArgs[0] = "Welcome to Poly N-gons. The objective of this game is to use all given polygons by fitting them all into " +
				"empty spaces.";
		tutorialArgs[1] = "There are 11 unique polygons (see \"Poly N-gons\" in menu to check all of the polygons).";
		tutorialArgs[2] = "Depending on the puzzle, all or not all polygons will be given.";
		tutorialArgs[3] = "You can see the puzzle displayed in the center. On the right side is/are the polygon/s you must use" +
				" to fill all spaces in the puzzle.";
		tutorialArgs[4] = "There is a number on the polygon's bottom-right side which indicates the maximum amount of that polygon" +
				" type you can use.";
		tutorialArgs[5] = "The buttons on the left side(flip, counterclockwise, clockwise) can be use to manipulate the polygon's position.";
		tutorialArgs[6] = "Once you'ved clicked your desired polygon, tap on any place of the puzzle to position it there.";
		tutorialArgs[7] = "Use the shifting marker that appears on the BOTTOM-LEFT corner of the polygon" +
				" you placed as a guide for moving your polygon.";
		tutorialArgs[8] = "If there is a red highlight on the shape and marker, that means the spot it is currently on is invalid.";
		tutorialArgs[9] = "Since it is highlighted, you can click anywhere again to move it. If you want to remove it, the remove option can be use.";
		tutorialArgs[10] = "If it is a white highlight, the spot is valid and you can tap on the white shifting marker to fit the shape.";
		tutorialArgs[11] = "Tapping is done by releasing the finger shortly after you've clicked it. Tap the marker to fit the shape.";
		tutorialArgs[12] = "Good job.";
		tutorialArgs[13] = "To move the puzzle's camera, just pan or long press it then drag the puzzle. Also, zoom by pinching the screen " +
				"open or close.";
		tutorialArgs[14] = "Complete the puzzle to continue.";

		tipFont = gScreen.game.bLayout.getCustomFont();
	}

	public void renderTutorialScene(){
		if (gScreen.game.gSelection == PolyNGonsGameSelection.TUTORIAL){
			gScreen.uiBatch.draw(gScreen.game.assetM.get(gScreen.game.assetID.get("tipLayout")), 130, 30);
			tipFont.drawWrappedFont(gScreen.uiBatch, tutorialArgs[currentTutorialText], 150, 81, 465,
					BitmapFont.HAlignment.LEFT, .9f, 1f, Color.WHITE);
		}
	}

	public void updateTutorial(int tNum){
		if (gScreen.game.gSelection == PolyNGonsGameSelection.TUTORIAL){
			if (currentTutorialText == tNum) {
				if (tNum == 5) disableOtherInputs = false;
				if (tNum == 6) disableOtherInputs = true;
				if (tNum == 10) {
					disableOtherInputs = false;
					delayListener = false;
				}
				if (tNum == 11) {
					disableOtherInputs = true;
					gScreen.getpCManager().setProperPolyStats();
				}
				if (tNum == 14) {
					disableOtherInputs = false;
					delayListener = false;
				}
				if (tNum != 15 && tNum != 14) currentTutorialText++;
			}
		}
	}
	
	public int getCurrentTutorialNum(){
		return currentTutorialText;
	}

	public boolean isDisableOtherInputs() {
		return disableOtherInputs;
	}

	public boolean isDelayListener() {
		return delayListener;
	}

	public void setDelayListener(boolean delayListener) {
		this.delayListener = delayListener;
	}
}
