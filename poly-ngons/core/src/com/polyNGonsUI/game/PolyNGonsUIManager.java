package com.polyNGonsUI.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.polyngons.game.GameScreen;
import com.polyNGonsFunctions.game.PolyNGonsGameTips;
import com.polyNGonsFunctions.game.PolyNGonsInGameMenu;
import com.polyNgonConstants.game.GameState;
import com.polyNgonConstants.game.PolyNGonsGameSelection;
import com.polyNgonConstants.game.SelectedChallenge;

public class PolyNGonsUIManager {
	private GameScreen gScreen;
	private PolyNGonUI ui;
	private GameAnimation gAnimation;
	private PolyNGonsInGameMenu inGameMenu;
	private PolyNGonsGameTips gTips;
	
	public PolyNGonsUIManager(GameScreen gScreen, Stage uiStage, Stage puzzleStage, PolyNGonsInGameMenu inGameMenu){
		this.gScreen = gScreen;
		this.inGameMenu = inGameMenu;
		
		ui = new PolyNGonUI(gScreen, uiStage, puzzleStage);
		gAnimation = new GameAnimation(gScreen);
		inGameMenu = new PolyNGonsInGameMenu(gScreen);
		gTips = new PolyNGonsGameTips(gScreen);
	}
	
	public void renderUI(){
		gScreen.uiBatch.setProjectionMatrix(gScreen.uiCam.combined);
		gScreen.uiBatch.begin();
		ui.renderUI();
		if (ui.getPolyButtonsMap().size > 4){
			if (ui.getTopBound() > 490) gAnimation.renderArrowsUp();
			if (ui.getpButtonsYPos() < 10) gAnimation.renderArrowsDown();
		}
		if (gScreen.game.gSelection == PolyNGonsGameSelection.TUTORIAL) gScreen.gettUIManager().renderTutorialScene();
		else if (gScreen.game.gSelection == PolyNGonsGameSelection.PUZZLE_CHALLENGE) {
			if (gScreen.game.pCType == SelectedChallenge.TIMED_PUZZLE) gScreen.getpCDisplay().renderTimer();
		}
		if (gScreen.game.gSelection != PolyNGonsGameSelection.TUTORIAL) gTips.renderTips();
		if (gScreen.gState == GameState.IN_MENU) inGameMenu.renderInGameMenu();
		if (gScreen.getSettings().isToggleSettings()) gScreen.getSettings().renderSettings();
		gScreen.uiBatch.end();
	}

	public PolyNGonsGameTips getgTips() {
		return gTips;
	}

	public PolyNGonUI getUi() {
		return ui;
	}
}
