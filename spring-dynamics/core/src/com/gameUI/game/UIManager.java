package com.gameUI.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.gameCompletionManager.game.GameOver;
import com.springdynamics.game.GameScreen;
import com.springDynamicsConstants.game.GameState;

public class UIManager {
	private GameScreen gScreen;
	private JumpController jController;
	private InGameMenu igMenu;
	private GameOver gOver;
	
	private Texture bgSky;
	
	private float bgXAdjuster;
	
	public UIManager(GameScreen gScreen){
		this.gScreen = gScreen;

		jController = new JumpController(gScreen, this);
		igMenu = new InGameMenu(gScreen);
		gOver = new GameOver(gScreen, gScreen.game);
		bgSky = new Texture(Gdx.files.internal("bgSky.png"), true);
		bgXAdjuster = 0;
		gScreen.gState = GameState.IN_GAME;
	}
	
	public void renderUI(){
		gScreen.game.uiBatch.begin();
		jController.renderJumpController();
		igMenu.renderInGameMenu(gScreen.gState == GameState.GAME_OVER);
		renderCurrentDist();
		if (gScreen.gState == GameState.GAME_OVER) gOver.render();
		gScreen.game.uiBatch.end();
	}
	
	private void renderCurrentDist() {
		gScreen.game.fontManager.getFont().getData().setScale(.8f);
		gScreen.game.fontManager.getFont().draw(gScreen.game.uiBatch, "Distance: " + String.valueOf(gScreen.getCurrentDist()) + 
				" m", 525, 500);
	}

	public void setUIListeners(){
		switch (gScreen.gState){
		case IN_GAME: {
			jController.setJumpControllerListener();
			igMenu.setListeners();
		}; break;
		case IN_GAME_MENU: {
			jController.setJumpControllerListener();
			igMenu.setListeners();
		}; break;
		case GAME_OVER: {
			jController.setJumpControllerListener();
			gOver.setListeners();
		}; break;
		default: break;
		}
	}
	
	public void renderSky(){
		float bgX = 81.25f + bgXAdjuster - (gScreen.gameCam.position.x / 4f);
		float bgY = 250 - gScreen.gameCam.position.y;
		
		gScreen.game.uiBatch.begin();
		gScreen.game.uiBatch.draw(bgSky, bgX, bgY);
		if (bgX + 2400 <= 900) gScreen.game.uiBatch.draw(bgSky, bgX + 2400, bgY);
		if (bgX + 2400 <= -100) bgXAdjuster += 2400;
		gScreen.game.uiBatch.end();
	}

	public GameOver getgOver() {
		return gOver;
	}

	public InGameMenu getIgMenu() {
		return igMenu;
	}
	
	public void dispose(){
		jController.dispose();
		igMenu.dispose();
		gOver.dispose();
		bgSky.dispose();
	}
}