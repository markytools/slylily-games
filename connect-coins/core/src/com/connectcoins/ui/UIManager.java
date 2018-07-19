package com.connectcoins.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.awards.AchievementManager;
import com.connectcoins.challenge.ChallengeConnectionData;
import com.connectcoins.connection.ConnectionManager;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.GameScreen;
import com.connectcoins.gameCompletion.GameCompletionManager;
import com.connectcoins.gameCompletion.Scorer;
import com.connectcoins.listeners.GameReady;
import com.connectcoins.listeners.Scoreboard;
import com.connectcoins.tutorial.TutorialManager;

public class UIManager implements Disposable {
	private GameScreen gScreen;
	private ConnectionManager connectionManager;
	private Scoreboard sb;
	private GameCompletionManager gCManager;
	private SpecialUI specialUI;
	private InGameMenu inGameMenu;
	private GameReady gameReady;
	public UIManager(GameScreen gScreen, ConnectionManager connectionManager, TutorialManager tutorialManager,
			AchievementManager achievementManager, Scorer scorer){
		this.gScreen = gScreen;
		this.connectionManager = connectionManager;
		gameReady = new GameReady(gScreen.game);
		ChallengeConnectionData challengeCData = gScreen.getSlotManager().getChallengeCData();
		inGameMenu = new InGameMenu(gScreen, gameReady, challengeCData);
		sb = new Scoreboard(gScreen, inGameMenu, tutorialManager, gameReady, achievementManager, scorer);
		gCManager = new GameCompletionManager(gScreen, achievementManager, scorer);
		specialUI = new SpecialUI(gScreen, this, connectionManager, inGameMenu);
		challengeCData.setBoard(sb);
	}

	public void updateUI(){
		if (!Gdx.input.isTouched()) submitUIConnection();
	}
	
	public void submitUIConnection(){
//		for (CoinActor coinActor : connectionManager.getConnectedCoinActors()) coinActor.setAllowConnection(true);
		connectionManager.submitConnection();
	}
	
	public void renderUI(SpriteBatch batch){
		sb.render(batch);
	}
	
	public void renderUIFront(SpriteBatch batch){
		specialUI.render(batch);
		specialUI.drawOnFireBarLayout(batch);
		specialUI.drawOnFireBar(batch);
		inGameMenu.render(batch);
		if (gScreen.game.gMConfig.mode == GameMode.TUTORIAL) sb.renderTutorialButtons(batch);
		gameReady.render(batch);
	}
	
	public void addFrontUI(FrontUI frontUI){
		
	}
	
	public void renderResults(SpriteBatch batch, boolean debug){
		gCManager.render(batch, debug);
	}

	public Scoreboard getSb() {
		return sb;
	}
	
	public SpecialUI getSpecialUI() {
		return specialUI;
	}
	
	public boolean isInGameMenuOn(){
		return inGameMenu.isInGameMenuOn();
	}
	
	public void setOnFireModeEnabled(boolean enable){
		specialUI.setON_FIRE_MODE_ENABLED(enable);
	}

	public boolean isShowTutorial() {
		return sb.isShowTutorial();
	}

	public void setShowTutorial(boolean showTutorial) {
		sb.setShowTutorial(showTutorial);
	}

	public GameReady getGameReady() {
		return gameReady;
	}
	
	public GameCompletionManager getgCManager() {
		return gCManager;
	}

//	public MultiplayerGameData getMultiplayerGameData(){
//		return gCManager.getmGameData();
//	}

//	public MultiplayerResultScreen getMultiplayerResultScreen(){
//		return gCManager.getmRScreen();
//	}
	
	public void deactivateGameMenu(){
		specialUI.disableGameMenuButton();
		inGameMenu.setEnablePressedGameMenu(false);
		inGameMenu.close();
	}

	public void resetAll() {
		inGameMenu.resetAll();
		sb.resetAll();
		gCManager.resetAll();
		specialUI.resetAll();
	}

	@Override
	public void dispose() {
		gScreen = null;
		connectionManager = null;
		if (sb != null) sb.dispose();
		sb = null;
		if (gCManager != null) gCManager.dispose();
		gCManager = null;
		if (specialUI != null) specialUI.dispose();
		specialUI = null;
		if (inGameMenu != null) inGameMenu.dispose();
		inGameMenu = null;
		if (gameReady != null) gameReady.dispose();
		gameReady = null;
	}
}
