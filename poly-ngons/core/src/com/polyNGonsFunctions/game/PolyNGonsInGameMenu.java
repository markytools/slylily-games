package com.polyNGonsFunctions.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gameInputProcessors.game.PolygonActor;
import com.gameTools.game.ScalableFontButton;
import com.gameTools.game.ShadedBitmapFont;
import com.polyngons.game.GameScreen;
import com.polyNGonsMainMenu.game.MainMenuScreen;
import com.polyNgonConstants.game.GameState;
import com.polyNgonConstants.game.PolyNGonsGameSelection;
import com.polyNgonConstants.game.PolygonState;
import com.polyNgonConstants.game.SelectedChallenge;

public class PolyNGonsInGameMenu {
	private GameScreen gScreen;
	private boolean toggleQuitPopUp;
	private Stage inGameMenuStage, toggleQuit;
	private ScalableFontButton yesQuit, noQuit, resumeGame, restartGame, settings, quitGame;
	private ShadedBitmapFont quitFont;

	public PolyNGonsInGameMenu(GameScreen gScreen){
		this.gScreen = gScreen;
		createButtons();
	}

	private void createButtons(){
		resumeGame = gScreen.game.bLayout.createImageTextButton(
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall1")),
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall2")), "Resume Game", 2.4f);
		resumeGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				IActivityRequestHandler requestAd = gScreen.game.requestAd;
				if (requestAd != null) gScreen.game.pAdManager.showBannerAds(false);
				gScreen.gState = GameState.IN_GAME;
				super.clicked(event, x, y);
			}
		});
		resumeGame.setBounds(275, 330, 250, 50);

		restartGame = gScreen.game.bLayout.createImageTextButton(
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall1")),
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall2")), "Restart Game", 2.4f);
		restartGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gScreen.game.pAdManager.showBannerAds(false);
				for (PolygonActor actor : gScreen.getPpManager().getPolysOnPuzzle().values()) actor.setState(PolygonState.NULL);
				gScreen.getPpManager().getPolysOnPuzzle().clear();
				gScreen.selectedPolygon = null;
				gScreen.gState = GameState.IN_GAME;
				super.clicked(event, x, y);
			}
		});
		restartGame.setBounds(275, 270, 250, 50);

		settings = gScreen.game.bLayout.createImageTextButton(
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall1")),
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall2")), "Settings", 2.4f);
		settings.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gScreen.getSettings().setToggleSettings(true);
				super.clicked(event, x, y);
			}
		});
		settings.setBounds(275, 210, 250, 50);

		String quitText = null;
		if (gScreen.game.gSelection == PolyNGonsGameSelection.PUZZLE_CHALLENGE) quitText = "Give Up";
		else quitText = "Quit";
		quitGame = gScreen.game.bLayout.createImageTextButton(
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall1")),
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall2")), quitText, 2.4f);
		quitGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				toggleQuitPopUp = true;
				super.clicked(event, x, y);
			}
		});
		quitGame.setBounds(275, 150, 250, 50);

		yesQuit = gScreen.game.bLayout.createImageTextButton(
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall1")),
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall2")), "Yes", 2.4f);
		yesQuit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gScreen.game.setScreen(new MainMenuScreen(gScreen.game));
				if (gScreen.game.gSelection == PolyNGonsGameSelection.PUZZLE_CHALLENGE){
					gScreen.getpCDisplay().appleFailedResult(true);
					gScreen.game.pCType = SelectedChallenge.NONE;
				}
				gScreen.game.gSelection = PolyNGonsGameSelection.NONE;
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run() {
						gScreen.dispose();
					}

				});
				super.clicked(event, x, y);
			}
		});
		yesQuit.setBounds(275, 245, 250, 50);

		noQuit = gScreen.game.bLayout.createImageTextButton(
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall1")),
				gScreen.game.assetM.get(gScreen.game.assetID.get("bSmall2")), "No", 2.4f);
		noQuit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				toggleQuitPopUp = false;
				super.clicked(event, x, y);
			}
		});
		noQuit.setBounds(275, 175, 250, 50);

		quitFont = gScreen.game.bLayout.getCustomFont();

		inGameMenuStage = new Stage();

		switch (gScreen.game.gSelection){
		case TUTORIAL:{
			quitGame.setBounds(275, 265, 250, 50);
			inGameMenuStage.addActor(resumeGame);
			inGameMenuStage.addActor(quitGame);
		}; break;
		case PUZZLE_COLLECTION:{
			inGameMenuStage.addActor(resumeGame);
			inGameMenuStage.addActor(quitGame);
			inGameMenuStage.addActor(restartGame);
			inGameMenuStage.addActor(settings);
		}; break;
		default: {
			inGameMenuStage.addActor(resumeGame);
			inGameMenuStage.addActor(quitGame);
			inGameMenuStage.addActor(restartGame);
			inGameMenuStage.addActor(settings);
		}; break;
		}

		toggleQuit = new Stage();
		toggleQuit.addActor(yesQuit);
		toggleQuit.addActor(noQuit);

	}

	public void renderInGameMenu(){
		gScreen.uiBatch.draw(gScreen.game.assetM.get(gScreen.game.assetID.get("layout2")), 200, 50, 400, 400);
		switch (gScreen.game.gSelection){
		case TUTORIAL:{
			if (!toggleQuitPopUp) {
				resumeGame.draw(gScreen.uiBatch, 1);
				quitGame.draw(gScreen.uiBatch, 1);
			}
			else {
				quitFont.drawMultiLineFont(gScreen.uiBatch, "Quit Game?", 200, 380, 400,
						BitmapFont.HAlignment.CENTER, 2.6f, 2.6f, Color.RED);
				noQuit.draw(gScreen.uiBatch, 1);
				yesQuit.draw(gScreen.uiBatch, 1);
			}
		}; break;
		case PUZZLE_COLLECTION:{
			if (!toggleQuitPopUp) {
				resumeGame.draw(gScreen.uiBatch, 1);
				restartGame.draw(gScreen.uiBatch, 1);
				settings.draw(gScreen.uiBatch, 1);
				quitGame.draw(gScreen.uiBatch, 1);
			}
			else {
				quitFont.drawMultiLineFont(gScreen.uiBatch, "Quit Game?", 200, 380, 400,
						BitmapFont.HAlignment.CENTER, 2.6f, 2.6f, Color.RED);
				noQuit.draw(gScreen.uiBatch, 1);
				yesQuit.draw(gScreen.uiBatch, 1);
			}
		}; break;
		case PUZZLE_CHALLENGE:{
			if (!toggleQuitPopUp) {
				resumeGame.draw(gScreen.uiBatch, 1);
				restartGame.draw(gScreen.uiBatch, 1);
				settings.draw(gScreen.uiBatch, 1);
				quitGame.draw(gScreen.uiBatch, 1);
			}
			else {
				quitFont.drawWrappedFont(gScreen.uiBatch, "Giving up will incur a loss to your rating?", 200, 400, 400,
						BitmapFont.HAlignment.CENTER, 2.1f, 2.1f, Color.RED);
				quitFont.drawMultiLineFont(gScreen.uiBatch, "Continue?", 200, 290, 400,
						BitmapFont.HAlignment.CENTER, 2f, 2f, Color.RED);
				noQuit.setPosition(275, 115);
				yesQuit.setPosition(275, 175);
				noQuit.draw(gScreen.uiBatch, 1);
				yesQuit.draw(gScreen.uiBatch, 1);
			}
		}; break;
		default: {
			if (!toggleQuitPopUp) {
				resumeGame.draw(gScreen.uiBatch, 1);
				restartGame.draw(gScreen.uiBatch, 1);
				settings.draw(gScreen.uiBatch, 1);
				quitGame.draw(gScreen.uiBatch, 1);
			}
			else {
				quitFont.drawMultiLineFont(gScreen.uiBatch, "Quit Game?", 200, 380, 400,
						BitmapFont.HAlignment.CENTER, 2.6f, 2.6f, Color.RED);
				noQuit.draw(gScreen.uiBatch, 1);
				yesQuit.draw(gScreen.uiBatch, 1);
			}
		}; break;
		}
	}

	public void setInGameMenuListener(){
		if (!gScreen.getSettings().isToggleSettings()){
			if (!toggleQuitPopUp) {
				Gdx.input.setInputProcessor(inGameMenuStage);
				inGameMenuStage.act();
				inGameMenuStage.getViewport().setCamera(gScreen.uiCam);
			}
			else {
				Gdx.input.setInputProcessor(toggleQuit);
				toggleQuit.act();
				toggleQuit.getViewport().setCamera(gScreen.uiCam);
			}
		}
		else gScreen.getSettings().setSettingsListeners(true);
	}
}
