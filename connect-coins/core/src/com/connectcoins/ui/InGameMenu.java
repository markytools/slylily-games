package com.connectcoins.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.challenge.ChallengeConnectionData;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen;
import com.connectcoins.game.GameScreen.GameState;
import com.connectcoins.languages.LanguageManager;
import com.connectcoins.listeners.GameReady;
import com.connectcoins.mainMenu.MainMenuScreen;
import com.connectcoins.utils.AnimatedButton;
import com.connectcoins.utils.ButtonAssets;
import com.connectcoins.utils.ScalableFontButton.Size;

public class InGameMenu implements Disposable {
	enum InGameMenuState {
		NORMAL, QUIT
	}

	private GameScreen gScreen;
	private ConnectCoins game;
	private InGameMenuState inGameMenuState;
	private Texture panel, blackBG;
	private final float LAYOUT_Y = 400;
	private boolean inGameMenuOn = false;
	private boolean enablePressedGameMenu = true;
	private GameReady gameReady;
	private ChallengeConnectionData challengeCData;
	private Button checkButton, xButton;
	private AnimatedButton back, comboList, quitGame, undo, restart;

	public InGameMenu(GameScreen gScreen, GameReady gameReady, ChallengeConnectionData challengeCData){
		this.gScreen = gScreen;
		this.game = gScreen.game;
		this.gameReady = gameReady;
		this.challengeCData = challengeCData;
		blackBG = game.assetLoader.getTexture("blackBG");
		panel = game.assetLoader.getTexture("inGamePanel");
		inGameMenuState = InGameMenuState.NORMAL;

		initInGameMenuButtons();
	}

	private void initInGameMenuButtons() {
		ButtonStyle style1 = new ButtonStyle();
		style1.up = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));
		style1.down = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));
		style1.disabled = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("largeButtonDisabled")));

		back = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Back", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
				true) {
			@Override
			public void onAnimationEnd() {
				backToGame();
			}
		};
		back.setBounds(190, LAYOUT_Y + 550, 700, 120);
		back.setUserObject(GameState.IN_GAME_MENU);
		back.setButtonIcons(game.buttonIconAssetManager, "back");

		comboList = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Combo List", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
				true) {
			@Override
			public void onAnimationEnd() {
				inGameMenuOn = false;
				game.comboList.showComboList(GameState.NORMAL, GameState.IN_GAME_MENU, true, gScreen);
			}
		};
		comboList.setBounds(190, LAYOUT_Y + 400, 700, 120);
		comboList.setUserObject(GameState.IN_GAME_MENU);
		comboList.setButtonIcons(game.buttonIconAssetManager, "comboList");

		quitGame = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Quit Game", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
				true) {
			@Override
			public void onAnimationEnd() {
				inGameMenuState = InGameMenuState.QUIT;
			}
		};
		quitGame.setBounds(190, LAYOUT_Y + 250, 700, 120);
		quitGame.setUserObject(GameState.IN_GAME_MENU);
		quitGame.setButtonIcons(game.buttonIconAssetManager, "quit");

		ButtonStyle xButtonStyle = new ButtonStyle();
		xButtonStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("xButton1")));
		xButtonStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("xButton2")));
		xButtonStyle.disabled = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("xButton3")));
		xButton = new Button(xButtonStyle);
		xButton.setUserObject(GameState.IN_GAME_MENU);
		xButton.setBounds(240, 620, 150, 150);
		xButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.miscellaneous.playNormalBClick1();
				inGameMenuState = InGameMenuState.NORMAL;
			}
		});

		ButtonStyle checkButtonStyle = new ButtonStyle();
		checkButtonStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("checkButton1")));
		checkButtonStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("checkButton2")));
		checkButtonStyle.disabled = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("checkButton3")));
		checkButton = new Button(checkButtonStyle);
		checkButton.setUserObject(GameState.IN_GAME_MENU);
		checkButton.setBounds(690, 620, 150, 150);
		checkButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.miscellaneous.playNormalBClick1();
//				if (game.gMConfig.mode == GameMode.MULTIPLAYER && WarpController.hasInstance()){
//					WarpController.getInstance().handleLeave();
//					
//				}
				inGameMenuState = InGameMenuState.NORMAL;
				game.setScreen(new MainMenuScreen(game));
				gScreen.dispose();
				System.gc();
			}
		});

		if (game.gMConfig.mode == GameMode.CHALLENGE){
			challengeCData.setInGameMenu(this);

			undo = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
					"Undo", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
					true) {
				@Override
				public void onAnimationEnd() {
					challengeCData.undo();
				}

				@Override
				public void act(float delta) {
					if (challengeCData.canUndoAndRestart()) setDisabled(false);
					else setDisabled(true);
					super.act(delta);
				}
			};
			undo.setUserObject(GameState.IN_GAME_MENU);
			undo.setButtonIcons(game.buttonIconAssetManager, "undo");

			restart = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
					"Restart", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
					true) {
				@Override
				public void onAnimationEnd() {
					challengeCData.restart();
				}

				@Override
				public void act(float delta) {
					if (challengeCData.canUndoAndRestart()) setDisabled(false);
					else setDisabled(true);
					super.act(delta);
				}
			};
			restart.setUserObject(GameState.IN_GAME_MENU);
			restart.setButtonIcons(game.buttonIconAssetManager, "restart");

			back.setBounds(190, LAYOUT_Y + 650, 700, 120);
			undo.setBounds(190, LAYOUT_Y + 510, 700, 120);
			restart.setBounds(190, LAYOUT_Y + 370, 700, 120);
			comboList.setBounds(190, LAYOUT_Y + 230, 700, 120);
			quitGame.setBounds(190, LAYOUT_Y + 90, 700, 120);

			game.stage.addActor(undo);
			game.stage.addActor(restart);
		}

		game.stage.addActor(xButton);
		game.stage.addActor(checkButton);
		game.stage.addActor(back);
		game.stage.addActor(comboList);
		game.stage.addActor(quitGame);
	}

	public void turnInGameMenu(boolean on){
		inGameMenuOn = on;
		if (inGameMenuOn){
			ConnectCoins.setStageActorsVisible(game.stage, GameState.IN_GAME_MENU);
		}
		else {
			ConnectCoins.setStageActorsVisible(game.stage, GameState.NORMAL);
		}
	}

	public void render(SpriteBatch batch){
		if (enablePressedGameMenu && gameReady.finishedReady() &&
				(Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE))) turnInGameMenu(true);
		if (inGameMenuOn){
			batch.draw(blackBG, -180, 0, 1440, 1920);
			batch.draw(panel, 90, LAYOUT_Y, 900, 900);
			switch (inGameMenuState){
			case NORMAL: {
				back.setVisible(true);
				comboList.setVisible(true);
				quitGame.setVisible(true);
				if (undo != null) undo.setVisible(true);
				if (restart != null) restart.setVisible(true);

				checkButton.setVisible(false);
				xButton.setVisible(false);
			}; break;
			case QUIT: {
				game.fManager.drawFont(game.fManager.largeFont2, batch, Color.valueOf("421903"), 1, 1, "Are you sure you\nwant to quit?",
						250, 1050, 600, Align.center);

				back.setVisible(false);
				comboList.setVisible(false);
				quitGame.setVisible(false);
				if (undo != null) undo.setVisible(false);
				if (restart != null) restart.setVisible(false);

				checkButton.setVisible(true);
				xButton.setVisible(true);
			}; break;
			default: break;
			}
		}
		game.comboList.render(batch);
	}

	public void resetAll(){
		inGameMenuOn = false;
		enablePressedGameMenu = true;
	}

	public void backToGame(){
		turnInGameMenu(false);
	}

	public boolean isInGameMenuOn(){
		return inGameMenuOn;
	}

	public void close(){
		turnInGameMenu(false);
	}

	public void setEnablePressedGameMenu(boolean enablePressedGameMenu) {
		this.enablePressedGameMenu = enablePressedGameMenu;
	}

	public boolean isEnablePressedGameMenu() {
		return enablePressedGameMenu;
	}

	public void setInGameMenuOn(boolean inGameMenuOn) {
		this.inGameMenuOn = inGameMenuOn;
	}

	@Override
	public void dispose() {
		inGameMenuState = null;
		inGameMenuOn = false;
		enablePressedGameMenu = true;
		panel = null;
		blackBG = null;
		gameReady = null;
		challengeCData = null;
		checkButton = null;
		xButton = null;

		back = null;
		comboList = null;
		quitGame = null;
		undo = null;
		restart = null;
		gScreen = null;
	}
}
