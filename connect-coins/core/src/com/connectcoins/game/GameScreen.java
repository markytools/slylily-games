package com.connectcoins.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.connectcoins.assets.AssetScreenSpace;
import com.connectcoins.audio.GameScreenAudioAssets;
import com.connectcoins.awards.AchievementManager;
import com.connectcoins.coins.CoinManager;
import com.connectcoins.connection.ConnectionManager;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.gameCompletion.Scorer;
import com.connectcoins.listeners.GameReady;
import com.connectcoins.slots.SlotManager;
import com.connectcoins.tutorial.TutorialManager;
import com.connectcoins.ui.UIManager;

public class GameScreen extends ScreenAdapter {

	public enum GameState {
		NORMAL, IN_GAME_MENU, COMBO_LIST, GAME_COMPLETION, ACHIEVEMENT_UNLOCKED, REMATCH, DISCONNECTED
	}

	public ConnectCoins game;
	private GameScreenAudioAssets gScreenAudioAssets;
	private ConnectionManager connectionManager;
	private CoinManager coinManager;
	private SlotManager slotManager;
	private TutorialManager tutorialManager;
	private UIManager uiManager;
	private AchievementManager achievementManager;
	private Scorer scorer;
	private AssetManager manager;

	private boolean resetTutorialPane = false;
	private boolean synchroLoadAssets = true;
	private boolean gameSetupComplete = false;
	private boolean runSetupThread = true;
	private boolean loadPostSetup = true;
	private float startUpAlpha = 0;
	private int delayTutorialPaneReset = 0;

	public static int initialCDataCount;
	public static boolean readyReceived;

	//	Temporary Members
	//	private Texture p;

	public GameScreen(ConnectCoins game){
		this.game = game;
		this.manager = game.assetLoader.manager;
		game.stage.clear();

//		game.adManager.showBannerAds(false);
		initAssets();
		initTutorialLanguage();
		//		Temporary Members
		//		p = game.assetLoader.getTexture("onFireBar1");
	}

	private void initAssets() {
		game.assetLoader.loadScreenAssets(AssetScreenSpace.GAME_SCREEN, null, false, false);
		game.assetLoader.unloadAndLoadCoinAssets(game.pUpdater.getCurrentCoinTexture());

//		if (game.gMConfig.mode == GameMode.MULTIPLAYER){
//			opponentDisconnect = new OpponentDisconnect(this, game);
//		}
	}

	private void initManagers() {
		gScreenAudioAssets = new GameScreenAudioAssets(game);
		scorer = new Scorer(game);
		achievementManager = new AchievementManager(game);
		slotManager = new SlotManager(this);
		tutorialManager = new TutorialManager(this);
		connectionManager = new ConnectionManager(this, slotManager, slotManager.getCoinQueue(), achievementManager, scorer);

		sleep();

		coinManager = new CoinManager(this, slotManager, connectionManager);
		uiManager = new UIManager(this, connectionManager, tutorialManager, achievementManager, scorer);
		connectionManager.setSpecialUI(uiManager.getSpecialUI());
		connectionManager.getComboScorer().setScoreboard(uiManager.getSb());
		connectionManager.setcManager(coinManager);
		slotManager.getChallengeCData().setCoinManager(coinManager);
	}

	private void initTutorialLanguage() {
		game.langManager.unloadAll();
		game.langManager.loadAllPack();
		game.fManager.initTutorialFont(game.fManager.getFilteredStringsFromLanguage());
		game.langManager.unloadAll();
		game.langManager.loadLanguagePack(game.pUpdater.getCurrentLanguage());
	}

	private void initListeners() {
		game.comboList.canOpenComboList(true);
		Gdx.input.setInputProcessor(game.stage);
	}

	private void configGameStates() {
		switch (game.gMConfig.mode){
		case NORMAL: {
			game.gMConfig.updateBoosters();
			uiManager.setOnFireModeEnabled(true);
			slotManager.enableAllSlots(false);
		}; break;
		case CHALLENGE: {
			game.gMConfig.disableAllBoosters();
			game.challManager.setPuzzleData(game.gMConfig.challengeNum, game.gMConfig.levelNum);
			slotManager.setSlotChallenges();
			connectionManager.setRecreateCoinState(false);
			connectionManager.getCoinSubmission().setActivateIlluminate(false);
			uiManager.setOnFireModeEnabled(false);
			slotManager.enableAllSlots(true);
		}; break;
		case TUTORIAL: {
			game.gMConfig.disableAllBoosters();
			game.comboList.setgScreen(this);
			uiManager.setOnFireModeEnabled(false);
			connectionManager.getComboScorer().setEnableIllumination(false);
			connectionManager.initCoinsForTutorial();
			slotManager.enableAllSlots(true);
		}; break;
		default: break;
		}
	}

	@Override
	public void render(float delta) {
		boolean managerUpdated = false;
		
		if (synchroLoadAssets){
			managerUpdated = manager.update();
			if (managerUpdated){
//				if (game.gMConfig.mode == GameMode.MULTIPLAYER){
//					if (initialCDataCount >= 18) synchroLoadAssets = false;
//				}
//				else {
					synchroLoadAssets = false;
//				}
			}
		}
		else setupGameLoadingAssets();

		if (gameSetupComplete){
			if (startUpAlpha < 1){
				startUpAlpha += 2f * Gdx.graphics.getDeltaTime();
				startUpAlpha = MathUtils.clamp(startUpAlpha, 0, 1);
				uiManager.getGameReady().reset();
			}
			else {
				if (loadPostSetup){
					loadPostSetup = false;
					uiManager.getGameReady().reset();
					ConnectCoins.setStageActorsVisible(game.stage, GameState.NORMAL);
					//					game.assetLoader.unloadCoinAssets(game.pUpdater.getCurrentCoinTexture());
					game.musicPlayer.startPlayer();

//					if (game.gMConfig.mode == GameMode.MULTIPLAYER) WarpController.getInstance().sendReadyUpdate();
				}
			}

			if (loadPostSetup){
				Color bColor = game.batch.getColor();
				game.batch.setColor(bColor.r, bColor.g, bColor.b, startUpAlpha);

				game.batch.begin();

				game.stage.act();
				updateGameMechanics();
				renderGame();
				game.batch.end();

				setListeners();

				//////		Temporary Members
				//				game.batch.begin();
				//				game.fManager.drawDisplayFont(game.batch, Color.BLACK, 1, 1, String.valueOf(Gdx.graphics.getFramesPerSecond()),
				//						200, 600, 100, Align.center);
				//				game.batch.end();
				//
				//				game.batch.setColor(bColor.r, bColor.g, bColor.b, prevAlpha);
			}
			else {
//				fireReadyMsg();
				game.batch.begin();
				game.stage.act();
				updateGameMechanics();
				renderGame();
				game.batch.end();

				setListeners();

				//////		Temporary Members
				//				game.batch.begin();
				//				game.fManager.drawDisplayFont(game.batch, Color.BLACK, 1, 1, String.valueOf(Gdx.graphics.getFramesPerSecond()),
				//						200, 600, 100, Align.center);
				//				game.batch.end();
			}
		}

//		if (game.gMConfig.mode == GameMode.MULTIPLAYER){
//			game.batch.begin();
////			opponentDisconnect.render(game.batch);
//			game.batch.end();
//		}
	}

//	private void fireReadyMsg(){
//		if (game.gMConfig.mode == GameMode.MULTIPLAYER && readyReceived){
//			readyReceived = false;
//			GameReady.startReadyForMultiplayer();
//		}
//	}

	private void setupGameLoadingAssets() {
		if (runSetupThread && !synchroLoadAssets){
			runSetupThread = false;

			Timer t = new Timer();
			t.scheduleTask(new Task() {
				@Override
				public void run() {
					sleep();
					initManagers();
					sleep();
					initListeners();
					sleep();
					//					initGenerator();
					sleep();
					configGameStates();

					gameSetupComplete = true;
				}
			}, 0);
			t.start();
			
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					sleep();
//					initManagers();
//					sleep();
//					initListeners();
//					sleep();
//					//					initGenerator();
//					sleep();
//					configGameStates();
//
//					gameSetupComplete = true;
//				}
//
//			}).start();

		}
	}

	private void sleep(){
		Timer t = new Timer();
		t.scheduleTask(new Task() {
			@Override
			public void run() {
				
			}
		}, .2f);
		t.start();
//		try {
//			Thread.sleep(200);
//		} catch (InterruptedException ex){}
	}

	private void restartMultiplayerData(){
		initialCDataCount = 0;
		readyReceived = false;
	}

	public void restartGame(){
		resetTutorialPane = false;
		gameSetupComplete = true;
		loadPostSetup = true;
		startUpAlpha = 0;

		scorer.resetAll();

		achievementManager.resetAll(false);
		slotManager.resetAll();
		tutorialManager.resetAll();
		connectionManager.resetAll();
		coinManager.resetAll();
		uiManager.resetAll();

		initListeners();
		configGameStates();
		restartMultiplayerData();

		uiManager.getGameReady().reset();

		System.gc();
	}

	private void updateGameMechanics() {
		if (uiManager != null) uiManager.updateUI();
	}

	private void renderGame() {

		//		game.stage.draw();
		//		game.gameBG.render(game.batch)
		uiManager.renderUI(game.batch);
		slotManager.render(game.batch);
		coinManager.render(game.batch);
		connectionManager.getCoinSubmission().renderAnimation(game.batch, game.gMConfig);
		uiManager.renderUIFront(game.batch);

		//		game.fManager.drawSmallFont(game.batch, Color.BLACK, 1, 1, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()),
		//				100, 600, 100, Align.left);
		uiManager.renderResults(game.batch, false);

		//		interpolateBodies((float)accumulator / TIME_STEP);
	}

	private void setListeners() {
		if (game.comboList.isShowComboList() || uiManager.isInGameMenuOn()){
			game.stage.draw();
		}
		if (delayTutorialPaneReset != 0) delayTutorialPaneReset--;
		if (resetTutorialPane && delayTutorialPaneReset == 0){
			resetTutorialPane = false;
			uiManager.getSb().resetTutorialPane();
		}
		if (game.gMConfig.mode == GameMode.TUTORIAL && game.comboList.isShowComboList()){
			uiManager.getSb().renderTutorialMsg(game.batch, game.comboList.getComboListPane(), game.comboList.getBackButton());
		}
		//		game.stage.act();
	}

	public SlotManager getSlotManager() {
		return slotManager;
	}

	public TutorialManager getTutorialManager() {
		return tutorialManager;
	}

	@Override
	public void resize(int width, int height) {
		game.resize(width, height);
	}

	public UIManager getUiManager() {
		return uiManager;
	}

//	public OpponentDisconnect getOpponentDisconnect() {
//		return opponentDisconnect;
//	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public AchievementManager getAchievementManager() {
		return achievementManager;
	}

	public void setResetTutorialPane(boolean resetTutorialPane) {
		delayTutorialPaneReset = 5;
		this.resetTutorialPane = resetTutorialPane;
	}

	@Override
	public void pause() {
//		if (game.gMConfig.mode == GameMode.MULTIPLAYER && opponentDisconnect.getOpponentDStates() != OpponentDisconnectStates.SAFE){
//			opponentDisconnect.setMyDisconnection();
//			initialCDataCount = 0;
//			readyReceived = false;
//			ConnectCoins.opponentDisconnected = 0;
//		}
		super.pause();
	}

	@Override
	public void resume() {
		
		super.resume();
	}

	@Override
	public void dispose() {
		//		restartGame();
		//		public ConnectCoins game;
		resetTutorialPane = false;
		synchroLoadAssets = true;
		gameSetupComplete = false;
		runSetupThread = true;
		loadPostSetup = true;
		startUpAlpha = 0;

		initialCDataCount = 0;
		readyReceived = false;

		if (gScreenAudioAssets != null) gScreenAudioAssets.dispose();
		gScreenAudioAssets = null;
		if (connectionManager != null) connectionManager.dispose();
		connectionManager = null;
		if (coinManager != null) coinManager.dispose();
		coinManager = null;
		if (slotManager != null) slotManager.dispose();
		slotManager = null;
		if (tutorialManager != null) tutorialManager.dispose();
		tutorialManager = null;
		if (uiManager != null) uiManager.dispose();
		if (achievementManager != null) achievementManager.dispose();
		achievementManager = null;
		if (scorer != null) scorer.dispose();
//		if (opponentDisconnect != null) opponentDisconnect.dispose();
//		opponentDisconnect = null;
		scorer = null;
		manager = null;
	}
}
