package com.connectcoins.listeners;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.connectcoins.audio.GameScreenAudioAssets;
import com.connectcoins.awards.AchievementManager;
import com.connectcoins.challenge.ChallengeUnlocker;
import com.connectcoins.challenge.ChallengeUnlocker.CHALLENGE_REWARD_TYPE;
import com.connectcoins.challenge.ChallengeUnlocker.ChallengeRewardData;
import com.connectcoins.coins.Coin.CoinColor;
import com.connectcoins.coins.Coin.CoinValue;
import com.connectcoins.combo.ComboScorer;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen;
import com.connectcoins.game.GameScreen.GameState;
import com.connectcoins.gameCompletion.GameCompletionManager;
import com.connectcoins.gameCompletion.Scorer;
import com.connectcoins.tutorial.TutorialManager;
import com.connectcoins.ui.InGameMenu;
import com.connectcoins.utils.AnimatedButton;
import com.connectcoins.utils.StopWatch;

public class Scoreboard implements Disposable {
	public static final String BIG_FLUSH = "Big Flush";
	public static final String FLUSH = "Flush";
	public static final String SMALL_FLUSH = "Small Flush";
	public static final String FIVE_OF_A_KIND = "Five of a Kind";
	public static final String FOUR_OF_A_KIND = "Four of a Kind";
	public static final String THREE_OF_A_KIND = "Three of a Kind";
	public static final String NONE = "--------";

	public enum ScoreboardState {
		NORMAL, CHALLENGE, TUTORIAL, MULTIPLAYER, COMPLETE
	}

	private Scorer scorer;
	private GameScreen gScreen;
	private ConnectCoins game;
	private ComboScorer comboScorer;
	private TutorialManager tutorialManager;
	private TextureRegion sb, scoreSymbol, combosSymbol, ccSymbol, timer, scoreboardText, tutorialText;
	private Texture timerBG1, timerBG2, vsReg, sbMult, blackBG;
	private TextureRegion myFaceReg, opponentFaceReg;
	private Array<ScoreManager> scoreManagers;
	private int opponentTotalScore = 0;
	private int totalScore = 0;
	private int totalCC = 0;
	private int timesUpShowed = 0;
	private int gameStarted = 0;
	private long startTime = -1;
	private long gameCompletionShowDelay = -1;
	private ScoreboardState sBState;
	private final float challScoreOffsetX = 120;
	private boolean endGame;
	private boolean proceedToMultGameCompletion = false;
	private boolean endRound = false;
	private boolean showTutorial = false;
	private boolean startNextRound = true;
	private Button tutorialBackB, tutorialNextB;
//	private CoinFiguresManager cFManager;
	private GameReady gameReady;
	private AchievementManager achievementManager;
	private InGameMenu inGameMenu;
	private ScrollPane tutorialPane;
	private Table tutorialLayoutTable = new Table();
	private Label label;
	private StopWatch stopWatch;
	private long opponentDisconnectTimeLeft;

	private final long totalTimer = 60000;	//In seconds / 1000  / TIME ADJUSTER

	// Test Variables
	private boolean addOpponentConnection = true;
	private boolean updateOpponentScores = false;
	private boolean permanentlyStopTimer = false;
	private int opponentScore = 0;
	private int opponentCC = 0;
	public Scoreboard(GameScreen gScreen, InGameMenu inGameMenu, TutorialManager tutorialManager, GameReady gameReady,
			AchievementManager achievementManager, Scorer scorer){
		this.gScreen = gScreen;
		this.scorer = scorer;
		this.inGameMenu = inGameMenu;
		this.game = gScreen.game;
		this.achievementManager = achievementManager;
		this.tutorialManager = tutorialManager;
		this.gameReady = gameReady;
		this.comboScorer = gScreen.getConnectionManager().getComboScorer();

		blackBG = game.assetLoader.getTexture("blackBG");
		sb = new TextureRegion(game.assetLoader.getTexture("scoreboard"));
		scoreSymbol = new TextureRegion(game.assetLoader.getTexture("scoreSymbol"));
		combosSymbol = new TextureRegion(game.assetLoader.getTexture("combosSymbol"));
		ccSymbol = new TextureRegion(game.assetLoader.getTexture("ccSymbol"));
		timer = new TextureRegion(game.assetLoader.getTexture("timerSymbol"));
		timerBG1 = game.assetLoader.getTexture("timerBG1");
		timerBG2 = game.assetLoader.getTexture("timerBG2");
		new TextureRegion(game.assetLoader.getTexture("timerPlot"));
		scoreboardText = new TextureRegion(game.assetLoader.getTexture("scoreboardText"));
		tutorialText = new TextureRegion(game.assetLoader.getTexture("tutorialText"));
		scoreManagers = new Array<ScoreManager>();
		stopWatch = new StopWatch();

		switch (game.gMConfig.mode){
		case CHALLENGE: {
			sBState = ScoreboardState.CHALLENGE; 
			gameReady.disableGameReady(true);
		}; break;
		case NORMAL: {
			sBState = ScoreboardState.NORMAL;
			gameReady.disableGameReady(false);
		}; break;
		case TUTORIAL: {
			sBState = ScoreboardState.TUTORIAL;
			showTutorial = true;
			setupTutorial();
			updateTutorial(game.batch, false, 160, 820);
			gameReady.disableGameReady(true);
		}; break;
		default: break;
		}

		endGame = false;
	}

	private void setupTutorial() {
		tutorialLayoutTable.setSize(800, 1800);
		tutorialLayoutTable.top().center();

		LabelStyle tutorialLStyle = new LabelStyle();
		tutorialLStyle.fontColor = Color.valueOf("281500");
		tutorialLStyle.font = game.fManager.tutorialFont;
		tutorialLStyle.background = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("tutorialSPBG")));

		String text;
		if (tutorialManager.getCurrentMsgNum() == 14 || tutorialManager.getCurrentMsgNum() == 15 || 
				tutorialManager.getCurrentMsgNum() == 16) text = tutorialManager.getTutorialMsgs().get(13).getMessage();
		else text = tutorialManager.getCurrentMsg().getMessage();

		label = new Label(text, tutorialLStyle);
		label.setAlignment(Align.center);
		label.setWrap(true);
		label.setWidth(340); // or even as low as 10
		tutorialLayoutTable.add(label).width(740);// <--- here you define the width! not ^

		ScrollPaneStyle tutorialPaneStyle = new ScrollPaneStyle();
		tutorialPaneStyle.background = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("tutorialSPBG")));
		tutorialPaneStyle.vScroll = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("topPVScrollBar")));
		tutorialPaneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("topPVScrollBarKnob")));

		tutorialPane = new ScrollPane(tutorialLayoutTable, tutorialPaneStyle);
		tutorialPane.setBounds(150, 1475, 800, 340);
		tutorialPane.setUserObject(GameState.NORMAL);
		tutorialPane.setVariableSizeKnobs(false);
		tutorialPane.setForceScroll(false, false);
		tutorialPane.setFadeScrollBars(false);



		ButtonStyle backBStyle = new ButtonStyle();
		backBStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("backButton1")));
		backBStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("backButton2")));
		tutorialBackB = new Button(backBStyle);
		tutorialBackB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.miscellaneous.playNormalBClick1();
				switch (tutorialManager.getCurrentMsg().gettMsgType()){
				case FIRST: {

				}; break;
				case NORMAL: {
					if (tutorialManager.getCurrentMsgNum() == 17){
						tutorialManager.prevMsg(tutorialManager.getCurrentMsgNum());
						tutorialManager.prevMsg(tutorialManager.getCurrentMsgNum());
						tutorialManager.prevMsg(tutorialManager.getCurrentMsgNum());
						tutorialManager.prevMsg(tutorialManager.getCurrentMsgNum());
					}
					else tutorialManager.prevMsg(tutorialManager.getCurrentMsgNum());
					gScreen.setResetTutorialPane(true);
					tutorialPane.setScrollPercentY(0);
				}; break;
				case ACTION: {
					if (tutorialManager.getCurrentMsgNum() == 22){
						gScreen.getUiManager().getSpecialUI().resetHeatBar();
						gScreen.getUiManager().getSpecialUI().setON_FIRE_MODE_ENABLED(false);
					}
					if (tutorialManager.getCurrentMsgNum() == 28) 
						gScreen.getConnectionManager().getComboScorer().setEnableIllumination(false);
					tutorialManager.prevMsg(tutorialManager.getCurrentMsgNum());
					gScreen.setResetTutorialPane(true);
					tutorialPane.setScrollPercentY(0);
				}; break;
				case OUTER: {

				}; break;
				case OUTER_EXIT: {

				}; break;
				case EXIT: {

				}; break;
				default: break;
				}
			}
		});
		tutorialBackB.setUserObject(GameState.NORMAL);
		tutorialBackB.setBounds(160, 1330, 100, 100);

		ButtonStyle checkBStyle = new ButtonStyle();
		checkBStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("checkButton1")));
		checkBStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("checkButton2")));
		tutorialNextB = new Button(checkBStyle);
		tutorialNextB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.miscellaneous.playNormalBClick1();
				switch (tutorialManager.getCurrentMsg().gettMsgType()){
				case FIRST: {
					tutorialManager.nextMsg(tutorialManager.getCurrentMsgNum());
					gScreen.setResetTutorialPane(true);
					tutorialPane.setScrollPercentY(0);
				}; break;
				case NORMAL: {
					if (tutorialManager.getCurrentMsgNum() == 21) gScreen.getUiManager().getSpecialUI().setON_FIRE_MODE_ENABLED(true);
					if (tutorialManager.getCurrentMsgNum() == 27) gScreen.getConnectionManager().getComboScorer().setEnableIllumination(true);
					tutorialManager.nextMsg(tutorialManager.getCurrentMsgNum());
					gScreen.setResetTutorialPane(true);
					tutorialPane.setScrollPercentY(0);
				}; break;
				case ACTION: {

				}; break;
				case OUTER: {
					tutorialManager.nextMsg(tutorialManager.getCurrentMsgNum());
					gScreen.setResetTutorialPane(true);
					tutorialPane.setScrollPercentY(0);
				}; break;
				case OUTER_EXIT: {
					tutorialManager.nextMsg(tutorialManager.getCurrentMsgNum());
					gScreen.setResetTutorialPane(true);
					tutorialPane.setScrollPercentY(0);
				}; break;
				case EXIT: {

				}; break;
				default: break;
				}
			}
		});
		tutorialNextB.setUserObject(GameState.NORMAL);
		tutorialNextB.setBounds(820, 1330, 100, 100);

		tutorialManager.setPrevButton(tutorialBackB);
		tutorialManager.setNextButton(tutorialNextB);

		game.stage.addActor(tutorialPane);
		game.stage.addActor(tutorialBackB);
		game.stage.addActor(tutorialNextB);
	}

	public void addToScoreBoard(String comboName, int score,
			int cc, int totalOneCoins, int totalFiveCoins, int totalTenCoins, int totalShinyCoins){
		scoreManagers.add(new ScoreManager(sBState, challScoreOffsetX, comboName, String.valueOf(score),
				String.valueOf(cc), game, totalOneCoins, totalFiveCoins, totalTenCoins, totalShinyCoins));
		totalScore += score;
		totalCC += cc;
	}

	public void render(SpriteBatch batch){
		if (startTime == -1){
			if (!gameReady.isDisable() && gameReady.finishedReady()) startTime = TimeUtils.millis();
		}

		int indexOut, startComboIndex;
		if (scoreManagers.size == 0){
			indexOut = 5;
			startComboIndex = -1;
		}
		else if (scoreManagers.size < 5){
			indexOut = 5 - scoreManagers.size;
			startComboIndex = 0;
		}
		else {
			indexOut = 0;
			startComboIndex = scoreManagers.size - 5;
		}

		switch (sBState){
		case TUTORIAL: {
			batch.draw(sb, 0, 873, 1080, 1080);
			updateTutorial(game.batch, true, 160, 820);
		}; break;
		case NORMAL: {
			batch.draw(sb, 0, 873, 1080, 1080);
			batch.draw(timer, 97, 1760, 80, 80);

			long timeLeftInMillis = totalTimer - (TimeUtils.millis() - startTime + 1);
			if (startTime == -1) timeLeftInMillis = totalTimer - 1;
			else {
				timeLeftInMillis = totalTimer - (TimeUtils.millis() - startTime + 1);
			}
			float timeRegionLeft = (float)(timeLeftInMillis) / totalTimer;
			if (timeRegionLeft <= 0){
				timeRegionLeft = 0;
				endGame = true;
			}
			batch.draw(timerBG2, 207, 1770, 700, 60);
			batch.draw(timerBG1, 207, 1830, timeRegionLeft * 700, -60, 0, 0, timeRegionLeft, 1);

			drawTimerText(batch, 156, 1808, timeLeftInMillis);

			if (startComboIndex != -1){
				if (startComboIndex == 0){
					for (int i = startComboIndex; i < scoreManagers.size; i++){
						ScoreManager sManager = scoreManagers.get(i);
						sManager.render(batch, 1674 - i * 50);
					}
				}
				else {
					for (int i = startComboIndex; i < scoreManagers.size; i++){
						ScoreManager sManager = scoreManagers.get(i);
						sManager.render(batch, 1674 - (i - startComboIndex) * 50);
					}
				}
			}

			batch.draw(combosSymbol, 97, 1687, 60, 60);
			batch.draw(scoreSymbol, 445, 1687, 60, 60);
			batch.draw(ccSymbol, 783, 1687, 60, 60);

			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "Combos", 60, 1758, 400, Align.center);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "Scores", 388, 1758, 400, Align.center);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "CC", 665, 1758, 400, Align.center);

			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "_______________________________", 112, 1474, 400,
					Align.left);

			if (indexOut != 0){
				for (int i = indexOut; i > 0; i--){
					float y = 1474 + (i - 1) * 50;
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, NONE, 52, y, 400,
							Align.center);
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, NONE, 375, y, 400,
							Align.center);
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, NONE, 661, y, 400,
							Align.center);
				}
			}

			/////////////////////////

			ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(totalScore));
			float totalScoreX = 515 - (ConnectCoins.glyphLayout.width / 2);
			ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(totalCC));
			float totalCCX = 825 - (ConnectCoins.glyphLayout.width / 2);
			batch.draw(scoreSymbol, totalScoreX, 1334, 60, 60);
			batch.draw(ccSymbol, totalCCX, 1334, 60, 60);

			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "Total:", 72, 1404, 400,
					Align.center);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, String.valueOf(totalScore), totalScoreX + 58, 1404, 400,
					Align.left);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, String.valueOf(totalCC), totalCCX + 46, 1404, 400,
					Align.left);

			if (gameReady.finishedReady() && gameStarted == 0){
				gameStarted = 1;
				gScreen.getSlotManager().enableAllSlots(true);
			}
			computeTotalGameTime();
		}; break;
		case CHALLENGE: {

			batch.draw(sb, 0, 873, 1080, 1080);
			batch.draw(scoreboardText, 0, 1750, 1080, 100);
			batch.draw(combosSymbol, 97, 1687, 60, 60);
			batch.draw(scoreSymbol, 445 + challScoreOffsetX, 1687, 60, 60);

			if (startComboIndex != -1){
				if (startComboIndex == 0){
					for (int i = startComboIndex; i < scoreManagers.size; i++){
						ScoreManager sManager = scoreManagers.get(i);
						sManager.render(batch, 1674 - i * 50);
					}
				}
				else {
					for (int i = startComboIndex; i < scoreManagers.size; i++){
						ScoreManager sManager = scoreManagers.get(i);
						sManager.render(batch, 1674 - (i - startComboIndex) * 50);
					}
				}
			}

			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "Combos", 60, 1758, 400, Align.center);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "Scores", 388 + challScoreOffsetX, 1758, 400,
					Align.center);

			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "_______________________________", 112, 1474, 400,
					Align.left);

			if (indexOut != 0){
				for (int i = indexOut; i > 0; i--){
					float y = 1474 + (i - 1) * 50;
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, NONE, 52, y, 400,
							Align.center);
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, NONE, 375 + challScoreOffsetX, y, 400,
							Align.center);
				}
			}	

			/////////////////////////

			int puzzlePoints = game.challManager.getpData().puzzlePoints;
			String totalScoreDisplay = String.valueOf(totalScore) + "/" + puzzlePoints;
			ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(totalScoreDisplay));
			float totalScoreX = 515 - (ConnectCoins.glyphLayout.width / 2);

			batch.draw(scoreSymbol, totalScoreX + challScoreOffsetX, 1334, 60, 60);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "Total:", 72, 1404, 400,
					Align.center);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f,
					totalScoreDisplay, totalScoreX + 58 + challScoreOffsetX,
					1404, 400, Align.left);

			checkIfChallengeCompleted(totalScore, puzzlePoints);
			computeTotalGameTime();
		}; break;
		case MULTIPLAYER: {
			batch.draw(sbMult, 0, 1120, 1080, 800);
			batch.draw(timer, 97, 1710, 80, 80);
			batch.draw(vsReg, 280, 1470, 140, 60);

			if (opponentDisconnectTimeLeft == -1){
				long timeLeftInMillis = totalTimer - (TimeUtils.millis() - startTime + 1);
				if (startTime == -1) timeLeftInMillis = totalTimer - 1;
				else {
					timeLeftInMillis = totalTimer - (TimeUtils.millis() - startTime + 1);
				}
				float timeRegionLeft = (float)(timeLeftInMillis) / totalTimer;
				if (timeRegionLeft <= 0){
					timeRegionLeft = 0;
					if (!permanentlyStopTimer) endGame = true;
				}

				batch.draw(timerBG2, 207, 1720, 700, 60);
				batch.draw(timerBG1, 207, 1780, timeRegionLeft * 700, -60, 0, 0, timeRegionLeft, 1);
				drawTimerText(batch, 156, 1758, timeLeftInMillis);
			}
			else {

				///Freezes the time!

				long timeLeftInMillis = opponentDisconnectTimeLeft;
				float timeRegionLeft = (float)(timeLeftInMillis) / totalTimer;

				batch.draw(timerBG2, 207, 1720, 700, 60);
				batch.draw(timerBG1, 207, 1780, timeRegionLeft * 700, -60, 0, 0, timeRegionLeft, 1);
				drawTimerText(batch, 156, 1758, timeLeftInMillis);
			}

//			String player1Name = WarpController.getInstance().getMyIGN();
			String player1Score = String.valueOf(totalScore);
//			String player2Name = WarpController.getInstance().getOpponentIGN();
			String player2Score = String.valueOf(opponentTotalScore);

//			ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(player1Name));
			float player1NameX = 285 - (ConnectCoins.glyphLayout.width / 2);
			ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(player1Score));
			float player1ScoreX = 290 - (ConnectCoins.glyphLayout.width / 2);
			batch.draw(myFaceReg, player1NameX, 1622, 70, 70);
			batch.draw(scoreSymbol, player1ScoreX, 1547, 70, 70);
//			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, player1Name, player1NameX + 80, 1700, 400,
//					Align.left);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, player1Score, player1ScoreX + 70, 1620, 400,
					Align.left);

//			ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(player2Name));
			float player2NameX = 285 - (ConnectCoins.glyphLayout.width / 2);
			ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(player2Score));
			float player2ScoreX = 290 - (ConnectCoins.glyphLayout.width / 2);
			batch.draw(opponentFaceReg, player2NameX, 1362, 70, 70);
			batch.draw(scoreSymbol, player2ScoreX, 1287, 70, 70);
//			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, player2Name, player2NameX + 80, 1440, 400,
//					Align.left);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, player2Score, player2ScoreX + 70, 1360, 400,
					Align.left);

//			cFManager.render(batch);

			if (gameReady.finishedReady() && gameStarted == 0){
				gameStarted = 1;
				gScreen.getSlotManager().enableAllSlots(true);

				//Test
				if (addOpponentConnection){
					addOpponentConnection = false;

					//					cFManager.queueMiniCoinsForAnimation(ComboScorer.FLUSH, "B1", "B2", "B3", "B4", "B5");
					//				
					//					cFManager.queueMiniCoinsForAnimation(ComboScorer.ON_FIRE, "C1", "C2");//Neglects the coins inside!!!
					//					
					//					cFManager.queueMiniCoinsForAnimation(ComboScorer.NO_COMBO, "C1", "C2", "C3", "C4", "C5");
					//					cFManager.queueMiniCoinsForAnimation(ComboScorer.NO_COMBO, "D1", "D2", "D3", "D4", "D5");
				}
				//
			}
			computeTotalGameTime();
//			multiplayerGameCompletion();
		}; break;
		default: break;
		}

		updateFinalRoundData();
		onEndTime();
	}

	private void updateFinalRoundData() {
		if (updateOpponentScores && gameCompletionShowDelay == -1){
			updateOpponentScores = false;
			
//			MultiplayerGameData mGData = gScreen.getUiManager().getgCManager().getmGameData();
//			MultiplayerResultScreen mRScreen = gScreen.getUiManager().getgCManager().getmRScreen();
//			if (totalScore > opponentScore){
//				mGData.putRoundWinner(MultiplayerGameData.MY_WIN);
//			}
//			else if (totalScore < opponentScore){
//				mGData.putRoundWinner(MultiplayerGameData.OPPONENT_WIN);
//			}
//			else if (totalScore == opponentScore){
//				mGData.putRoundWinner(MultiplayerGameData.DRAW);
//			}
//			mGData.putMyRoundCCAndScore(totalCC, totalScore);
//			mGData.putOpponentRoundCC(opponentCC);
//
//			mRScreen.placeMyWinsAndDraws(mGData.getTotalRounds(), mGData.getWinStringData());

//			int winResult = mGData.getWholeGameWinner();
//			switch (winResult){
//			case MultiplayerGameData.MY_WIN: {
//				gScreen.getOpponentDisconnect().setOpponentDStates(OpponentDisconnectStates.SAFE);
//				mGData.computeTotalCCAndFees();
////				gScreen.getUiManager().getgCManager().setWinPlayerIGN(WarpController.getInstance().getMyIGN());
//				startNextRound = false;
//
//				mRScreen.flushMultiplayerGameResults();
//				//Result screen will be shown when data has been flushed.
//			}; break;
//			case MultiplayerGameData.OPPONENT_WIN: {
//				gScreen.getOpponentDisconnect().setOpponentDStates(OpponentDisconnectStates.SAFE);
//				mGData.computeTotalCCAndFees();
////				gScreen.getUiManager().getgCManager().setWinPlayerIGN(WarpController.getInstance().getOpponentIGN());
//				startNextRound = false;
//
//				mRScreen.flushMultiplayerGameResults();
//				//Result screen will be shown when data has been flushed.
//			}; break;
//			case MultiplayerGameData.DRAW: {
//				gScreen.getOpponentDisconnect().setOpponentDStates(OpponentDisconnectStates.SAFE);
//				mGData.computeTotalCCAndFees();
//				gScreen.getUiManager().getgCManager().setWinPlayerIGN("--Match Draw--");
//				startNextRound = false;
//
//				mRScreen.flushMultiplayerGameResults();
//				//Result screen will be shown when data has been flushed.
//			}; break;
//			case MultiplayerGameData.NONE: {
//				gScreen.getOpponentDisconnect().setOpponentDStates(OpponentDisconnectStates.NEXT_ROUND);
//				startNextRound = true;
//				mGData.nextRound();
//				endRound = true;
//			}; break;
//			default: {
//				gScreen.getOpponentDisconnect().setOpponentDStates(OpponentDisconnectStates.NEXT_ROUND);
//				startNextRound = true;
//				mGData.nextRound();
//				endRound = true;
//			}; break;
//			}
		}
	}

	public void endRound(){
		endRound = true;
	}

//	private void multiplayerGameCompletion() {
//		if (proceedToMultGameCompletion && endRound){
//			proceedToMultGameCompletion = false;
//
//			gScreen.getUiManager().getgCManager().submitScoreOrDisableSubmitButton(totalScore, true);
//			scorer.setScore(totalScore);
//			scorer.setCcEarned(totalCC);
//			scorer.setGameData(true, totalCC, totalScore, (float) (stopWatch.timeElapsedInSeconds() / 3600f), scoreManagers);
//			//			gScreen.getUiManager().getgCManager().getmRScreen().startWinAnim();	CALL THIS WHEN LAST ROUND AND WINNER
////			gScreen.getUiManager().getgCManager().getmRScreen().setRenderMRScreen(true, startNextRound);
//			proceedToGameCompletionScreen(gScreen.getUiManager().getgCManager(), false);
//		}
//	}

	private void computeTotalGameTime() {
		if (!endGame && gameReady.finishedReady() && !inGameMenu.isInGameMenuOn() && !game.comboList.isShowComboList()){
			if (!stopWatch.isRunning()) stopWatch.start();
			else stopWatch.resume();
		}
		else stopWatch.pause();
	}

	private void checkIfChallengeCompleted(int totalScore, int puzzlePoints) {
		if (totalScore >= puzzlePoints){
			endGame = true;
		}
	}

	private void drawTimerText(SpriteBatch batch, float x, float y, long timeLeftInMillis) {
		long timeLeft = timeLeftInMillis / 1000l;
		int minutes = (int)(((timeLeft) / 60 >= 0) ? timeLeft / 60 : 0);
		int seconds = (int)(timeLeft % 60);
		String realSec = String.valueOf(seconds + 1);
		if (seconds + 1 == 60){
			minutes = 1;
			realSec = "00";
		}
		if (timeLeftInMillis + 1000 < 10000){
			if (timeLeftInMillis <= 0){
				minutes = 0;
				realSec = "00";
			}
			else realSec = "0" + realSec;
		}

		if (minutes < 0) minutes = 0;
		game.fManager.drawFont(game.fManager.largeFont4, batch, Color.valueOf("ffffff"), 1, 1, minutes + ":" + realSec,
				x, y, 800, Align.center);
	}

	private void onEndTime(){
		if (endGame && timesUpShowed == 0){
			endGame = false;
			timesUpShowed = -1;
			gameCompletionShowDelay = TimeUtils.millis();

			gScreen.getUiManager().deactivateGameMenu();
			if (game.comboList.isShowComboList()){
				game.comboList.close();
				game.comboList.canOpenComboList(false);
			}
			gScreen.getSlotManager().enableAllSlots(false);
			gScreen.getUiManager().submitUIConnection();

			GameScreenAudioAssets.timesUp.play(0.7f);
		}
		if (gameCompletionShowDelay != -1 && gameCompletionShowDelay != 0){
			if (TimeUtils.millis() - gameCompletionShowDelay >= 1500){
				gameCompletionShowDelay = 0;
			}
		}
		if (gameCompletionShowDelay == 0 && comboScorer.scoreRenderersIsEmpty()){
			gameCompletionShowDelay = -1;

			GameCompletionManager gCManager = gScreen.getUiManager().getgCManager();
			loadScoreData(gCManager);
		}
	}

	public void setupOpponentDisconnect(){
		gScreen.getUiManager().deactivateGameMenu();
		if (game.comboList.isShowComboList()){
			game.comboList.close();
			game.comboList.canOpenComboList(false);
		}
		gScreen.getSlotManager().enableAllSlots(false);
		gScreen.getUiManager().submitUIConnection();
		timesUpShowed = -1;
		ConnectCoins.setStageActorsVisible(game.stage, GameState.DISCONNECTED);

		/////PLAY DISCONNECT SOUND!!!!
		/////TODO
		/////
	}

	public void setupMyDisconnect(){
		gScreen.getUiManager().deactivateGameMenu();
		if (game.comboList.isShowComboList()){
			game.comboList.close();
			game.comboList.canOpenComboList(false);
		}
		gScreen.getSlotManager().enableAllSlots(false);
		gScreen.getUiManager().submitUIConnection();
		timesUpShowed = -1;
	}
	
	public void permanentlyStopTime(){
		permanentlyStopTimer = true;
	}

	/**
	 * Call when user calls on opponent dc exit button.
	 * The current round that the player has disconnected becomes invalid. No CC and scores are calculated during that round.
	 */
	public void loadGameResultsDuringGameProgress(){
		proceedToMultGameCompletion = false;

		//		scorer.setScore(totalScore);
		//		scorer.setCcEarned(totalCC);
		//		scorer.setGameData(true, totalCC, totalScore, (float) (stopWatch.timeElapsedInSeconds() / 3600f), scoreManagers);
		//		gScreen.getUiManager().getgCManager().getmRScreen().setRenderMRScreen(true, startNextRound);

		sBState = ScoreboardState.COMPLETE;
		gScreen.getUiManager().getgCManager().setShowGameCompletion(true);
		ConnectCoins.setStageActorsVisible(game.stage, GameState.GAME_COMPLETION);
	}

	private void proceedToGameCompletionScreen(GameCompletionManager gCManager, boolean showAchievements){
		sBState = ScoreboardState.COMPLETE;
		gCManager.setShowGameCompletion(true);
		if (showAchievements){
			if (achievementManager.isShowAchievementUnlock()){
				achievementManager.playAchievementSound();
				ConnectCoins.setStageTouchableActors(game.stage, GameState.ACHIEVEMENT_UNLOCKED);
				achievementManager.calculateAnimXs();
			}
			else {
				ConnectCoins.setStageActorsVisible(game.stage, GameState.GAME_COMPLETION);
			}
		}
	}

	private void loadScoreData(final GameCompletionManager gCManager){
		switch (sBState){
		case NORMAL: {
			scorer.setScore(totalScore);
			scorer.setCcEarned(totalCC);
			scorer.setGameData(true, totalCC, totalScore, (float) (stopWatch.timeElapsedInSeconds() / 3600), scoreManagers);
			gCManager.submitScoreOrDisableSubmitButton(totalScore, false);

			proceedToGameCompletionScreen(gCManager, true);
		}; break;
		case MULTIPLAYER: {
//			gScreen.getOpponentDisconnect().setOpponentDStates(OpponentDisconnectStates.NEXT_ROUND);
//			try {
//				JSONObject json = new JSONObject();
//				json.put("tpc", AWDataConstants.T_ROUND_END);
//				json.put("user", WarpController.getInstance().getMyIGN());
//				json.put("roundScore", totalScore);
//				json.put("roundCC", totalCC);
//				WarpController.getInstance().sendGameUpdate(json.toString(), true);
//				proceedToMultGameCompletion = true;
//			} 
//			catch (JSONException e) {
//				e.printStackTrace();
//			}
		}; break;
		case CHALLENGE: {
			int currentChallengeNum = game.gMConfig.challengeNum;
			int currentChallengeLevel = game.gMConfig.levelNum;
			ChallengeUnlocker challengeUnlocker = game.challManager.getChallengeUnlocker();
			ChallengeRewardData rewardData = challengeUnlocker.unlockChallenge(currentChallengeNum, currentChallengeLevel);
			int ccReward = challengeUnlocker.getRecentCCReward();
			game.pUpdater.setLeastNumberOfConnections(currentChallengeNum, currentChallengeLevel, scoreManagers.size);
			scorer.setCcEarned(ccReward);
			scorer.setGameData(false);
			if (rewardData.rewardType == CHALLENGE_REWARD_TYPE.CHALLENGE_UNLOCKED ||
					rewardData.rewardType == CHALLENGE_REWARD_TYPE.CHALLENGE_COMPLETED) achievementManager.playAchievementSound();

			gCManager.setGameCompletionChallengeData(currentChallengeNum, currentChallengeLevel,
					scoreManagers.size, game.pUpdater.getLeastNumberOfConnections(currentChallengeNum, currentChallengeLevel),
					rewardData.rewardType != CHALLENGE_REWARD_TYPE.NONE, rewardData.message);

			proceedToGameCompletionScreen(gCManager, true);
		}; break;
		case TUTORIAL: {
			proceedToGameCompletionScreen(gCManager, true);
		}; break;
		default: break;
		}
	}

	public void renderTutorialButtons(SpriteBatch batch){
		if (tutorialBackB.isVisible()) tutorialBackB.draw(batch, 1);
		if (tutorialNextB.isVisible()) tutorialNextB.draw(batch, 1);
	}

	public void drawTutorialText(SpriteBatch batch, float y){
		batch.draw(tutorialText, 0, y, 1080, 100);
	}

	private void updateTutorial(SpriteBatch batch, boolean drawTutorialPane, float tutorialBackX, float tutorialNextX){
		switch (tutorialManager.getCurrentMsg().gettMsgType()){
		case FIRST: {
			tutorialBackB.setVisible(false);
			tutorialNextB.setVisible(true);
			tutorialBackB.setBounds(tutorialBackX, 1330, 100, 100);
			tutorialNextB.setBounds(tutorialNextX, 1330, 100, 100);
			drawTutorialMessages(batch, 1475, drawTutorialPane);

			if (inGameMenu.isInGameMenuOn() || game.comboList.isShowComboList()){
				tutorialBackB.setVisible(false);
				tutorialNextB.setVisible(false);
				tutorialPane.setVisible(false);
			}
		}; break;
		case NORMAL: {
			if (tutorialManager.getCurrentMsgNum() == 17){
				if (gScreen.getUiManager().isInGameMenuOn() || game.comboList.isShowComboList()){
					tutorialBackB.setVisible(false);
					tutorialNextB.setVisible(false);
					tutorialBackB.setTouchable(Touchable.disabled);
					tutorialNextB.setTouchable(Touchable.disabled);
					tutorialPane.setVisible(false);
				}
				else {
					tutorialBackB.setVisible(true);
					tutorialNextB.setVisible(true);
					tutorialBackB.setTouchable(Touchable.enabled);
					tutorialNextB.setTouchable(Touchable.enabled);
					tutorialBackB.setBounds(tutorialBackX, 1330, 100, 100);
					tutorialNextB.setBounds(tutorialNextX, 1330, 100, 100);
					drawTutorialMessages(batch, 1475, true);
				}
			}
			else {
				tutorialBackB.setVisible(true);
				tutorialNextB.setVisible(true);
				tutorialBackB.setTouchable(Touchable.enabled);
				tutorialNextB.setTouchable(Touchable.enabled);
				tutorialBackB.setBounds(tutorialBackX, 1330, 100, 100);
				tutorialNextB.setBounds(tutorialNextX, 1330, 100, 100);
				drawTutorialMessages(batch, 1475, true);
			}

			if (inGameMenu.isInGameMenuOn() || game.comboList.isShowComboList()){
				tutorialBackB.setVisible(false);
				tutorialNextB.setVisible(false);
				tutorialPane.setVisible(false);
			}
		}; break;
		case ACTION: {
			if (tutorialManager.getCurrentMsgNum() == 13){
				if (!gScreen.getUiManager().isInGameMenuOn()){
					tutorialBackB.setVisible(true);
					tutorialNextB.setVisible(false);
					drawTutorialMessages(batch, 1475, true);
				}
				else {
					tutorialBackB.setVisible(false);
					tutorialNextB.setVisible(false);
					tutorialPane.setVisible(false);
				}
			}
			else {
				tutorialBackB.setVisible(true);
				tutorialNextB.setVisible(false);
				drawTutorialMessages(batch, 1475, true);
			}
			tutorialBackB.setBounds(tutorialBackX, 1330, 100, 100);
			tutorialNextB.setBounds(tutorialNextX, 1330, 100, 100);

			if (inGameMenu.isInGameMenuOn() || game.comboList.isShowComboList()){
				tutorialBackB.setVisible(false);
				tutorialNextB.setVisible(false);
				tutorialPane.setVisible(false);
			}
		}; break;
		case OUTER: {
			if (game.comboList.isShowComboList()){
				tutorialBackB.setVisible(false);
				tutorialNextB.setVisible(true);
				tutorialBackB.setTouchable(Touchable.disabled);
				tutorialNextB.setTouchable(Touchable.enabled);
				tutorialBackB.setBounds(tutorialBackX, 857, 100, 100);
				tutorialNextB.setBounds(tutorialNextX, 857, 100, 100);
			}
			else {
				tutorialBackB.setVisible(false);
				tutorialNextB.setVisible(false);
				tutorialBackB.setTouchable(Touchable.disabled);
				tutorialNextB.setTouchable(Touchable.disabled);
				tutorialPane.setVisible(false);
			}
		}; break;
		case OUTER_EXIT: {
			tutorialBackB.setVisible(false);
			tutorialNextB.setVisible(true);
			tutorialBackB.setTouchable(Touchable.disabled);
			tutorialNextB.setTouchable(Touchable.enabled);
			tutorialBackB.setBounds(tutorialBackX, 857, 100, 100);
			tutorialNextB.setBounds(tutorialNextX, 857, 100, 100);
		}; break;
		case EXIT: {
			tutorialBackB.setVisible(false);
			tutorialNextB.setVisible(false);
			drawTutorialMessages(batch, 1475, !inGameMenu.isInGameMenuOn() && !game.comboList.isShowComboList());
		}; break;
		default: break;
		}

		if (tutorialManager.getCurrentMsgNum() == 4 || tutorialManager.getCurrentMsgNum() == 11 || 
				tutorialManager.getCurrentMsgNum() == 20 || tutorialManager.getCurrentMsgNum() == 22 ||
				tutorialManager.getCurrentMsgNum() == 28 || tutorialManager.getCurrentMsgNum() == 29) 
			gScreen.getSlotManager().enableAllSlots(true);
		else gScreen.getSlotManager().enableAllSlots(false);
	}

	/**
	 * Method automatically sets the scroll pane visible.
	 * @param batch
	 * @param scrollPaneY the y position of the scrollPane
	 * @param drawTutorialPane 
	 */
	public void drawTutorialMessages(SpriteBatch batch, float scrollPaneY, boolean drawTutorialPane){
		if (drawTutorialPane){
			String text;
			if (tutorialManager.getCurrentMsgNum() == 14 || tutorialManager.getCurrentMsgNum() == 15 || 
					tutorialManager.getCurrentMsgNum() == 16) text = tutorialManager.getTutorialMsgs().get(13).getMessage();
			else text = tutorialManager.getCurrentMsg().getMessage();
			label.setText(text);

			tutorialPane.setBounds(150, scrollPaneY, 800, 340);
			tutorialPane.setVisible(true);

			BitmapFont tBFont = game.fManager.largeFont;
			float xScale = tBFont.getScaleX();
			float yScale = tBFont.getScaleY();
			tBFont.getData().setScale(1);
			tutorialPane.draw(batch, 1);
			tBFont.getData().setScale(xScale, yScale);

			drawTutorialText(batch, scrollPaneY - 145);
		}
	}

	public void resetTutorialPane(){
		if (tutorialPane != null) tutorialPane.setScrollPercentY(0);
	}

	public void renderTutorialMsg(SpriteBatch batch, ScrollPane comboListPane, AnimatedButton backButton){
		if (tutorialManager.getCurrentMsgNum() >= 13 && tutorialManager.getCurrentMsgNum() <= 16){
			comboListPane.setTouchable(Touchable.disabled);
			backButton.setTouchable(Touchable.disabled);

			batch.begin();
			batch.draw(blackBG, -180, 0, 1440, 1920);
			batch.draw(sb, 0, 400, 1080, 1080);

			String text;
			if (tutorialManager.getCurrentMsgNum() == 13) tutorialManager.nextMsg(13);
			text = tutorialManager.getCurrentMsg().getMessage();
			if (label.getText().toString() != text){
				label.setText(text);
			}

			BitmapFont tBFont = game.fManager.largeFont;
			float xScale = tBFont.getScaleX();
			float yScale = tBFont.getScaleY();
			tBFont.getData().setScale(1);
			tutorialPane.setBounds(150, 1002, 800, 340);
			tutorialPane.setVisible(true);
			tutorialPane.setTouchable(Touchable.enabled);
			tutorialPane.draw(batch, 1);
			tBFont.getData().setScale(xScale, yScale);

			drawTutorialText(batch, 857);

			if (tutorialBackB.isVisible()) tutorialBackB.draw(batch, 1);
			if (tutorialNextB.isVisible()) tutorialNextB.draw(batch, 1);
			batch.end();
		}
		else {
			comboListPane.setTouchable(Touchable.enabled);
			backButton.setTouchable(Touchable.enabled);
		}
	}

	public void resetAll() {
		endGame = false;
//		if (cFManager != null) cFManager.dispose();

		totalScore = 0;
		totalCC = 0;
		opponentTotalScore = 0;
		timesUpShowed = 0;
		gameStarted = 0;
		startTime = -1;
		gameCompletionShowDelay = -1;
		showTutorial = false;
		proceedToMultGameCompletion = false;
		endRound = false;
		stopWatch.stop();
		for (ScoreManager s : scoreManagers){
			s.dispose();
			s = null;
		}
		scoreManagers.clear();
		scoreManagers.shrink();

		switch (game.gMConfig.mode){
		case CHALLENGE: {
			sBState = ScoreboardState.CHALLENGE; 
			gameReady.disableGameReady(true);
		}; break;
		case NORMAL: {
			sBState = ScoreboardState.NORMAL;
			gameReady.disableGameReady(false);
		}; break;
		case TUTORIAL: {

		}; break;
		default: break;
		}
	}

//	@Override
//	public void addNewMiniCoinLayer(){
//		while (MultiplayerCoins.getInstance().hasOpponentCoinData()){
//			HashMap<String, CoinData> cData = MultiplayerCoins.getInstance().retrieveFirstOpponentCData();
//
//			String col = "A";
//			int row = 1;
//			for (int i = 0; i < 5; i++){
//				for (int i2 = 0; i2 < 5; i2++){
//					String location = col + row;
//					CoinData currentCData = cData.get(location);
//					boolean isShining = (currentCData.isShining()) ? true : false;
//
//					MiniCoin coin = null;
//					switch (currentCData.getCoinType()){
//					case WHOLE: {
//						CoinValue coinValue = currentCData.getCoinValue();
//						CoinColor coinColor = currentCData.getCoinColor();
//
//						coin = new MiniCoin(location, coinValue, coinColor, isShining);
//					}; break;
//					case THIRD: {
//						CoinValue coinValue = currentCData.getCoinValue();
//
//						coin = new MiniCoin(location, coinValue, isShining);
//					}; break;
//					case HALF: {
//						CoinValue coinValue = currentCData.getCoinValue();
//						CoinColor coinColor1, coinColor2;
//						coinColor1 = currentCData.getCoinColor();
//						coinColor2 = currentCData.getBottomColor();
//
//						coin = new MiniCoin(location, coinValue, coinColor1, coinColor2, isShining);
//					}; break;
//					default: break;
//					}
//					cFManager.addCoinToQueue(location, coin);
//					row++;
//				}
//				col = String.valueOf( (char) (col.charAt(0) + 1));
//				row = 1;
//			}
//		}
//	}

	public void setOpponentDTimeLeft(){
		this.opponentDisconnectTimeLeft = totalTimer - (TimeUtils.millis() - startTime + 1);
	}

	public void removeLastComboInstance(){
		scoreManagers.pop();
	}

	public void subtractCC(int value){
		this.totalCC -= value;
	}

	public void subtractScore(int value){
		this.totalScore -= value;
	}

	public boolean isTimesUp() {
		return endGame;
	}

	public boolean isShowTutorial() {
		return showTutorial;
	}

	public void setShowTutorial(boolean showTutorial) {
		this.showTutorial = showTutorial;
	}

	public int getTotalScore() {
		return totalScore;
	}

	@Override
	public void dispose() {
		endGame = false;
		addOpponentConnection = false;
		scorer = null;
		gScreen = null;
		game = null;
		comboScorer = null;
		tutorialManager = null;
		sb = null;
		scoreSymbol = null;
		combosSymbol = null;
		ccSymbol = null;
		timer = null;
		scoreboardText = null;
		tutorialText = null;
		timerBG1 = null;
		timerBG2 = null;
		vsReg = null;
		sbMult = null;
		myFaceReg = null;
		opponentFaceReg = null;
		blackBG = null;
		if (scoreManagers != null){
			for (ScoreManager sM : scoreManagers){
				sM.dispose();
				sM = null;
			}
			scoreManagers.clear();
		}
		scoreManagers = null;
		opponentTotalScore = 0;
		totalScore = 0;
		totalCC = 0;
		timesUpShowed = 0;
		opponentScore = 0;
		opponentCC = 0;
		gameStarted = 0;
		startTime = 0;
		gameCompletionShowDelay = 0;
		proceedToMultGameCompletion = false;
		endRound = false;
		showTutorial = false;
		updateOpponentScores = false;
		permanentlyStopTimer = false;
		startNextRound = true;
		sBState = null;
		if (tutorialBackB != null) tutorialBackB.remove();
		tutorialBackB = null;
		if (tutorialNextB != null) tutorialNextB.remove();
		tutorialNextB = null;
//		if (cFManager != null) cFManager.dispose();
//		cFManager = null;
		if (gameReady != null) gameReady.dispose();
		gameReady = null;
		achievementManager = null;
		if (inGameMenu != null) inGameMenu.dispose();
		inGameMenu = null;
		if (tutorialPane != null) tutorialPane.remove();
		tutorialPane = null;
		tutorialLayoutTable = null;
		if (label != null) label.remove();
		label = null;
		stopWatch = null;
	}
}