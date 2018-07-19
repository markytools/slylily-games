package com.connectcoins.gameCompletion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.awards.AchievementManager;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen;
import com.connectcoins.game.GameScreen.GameState;
import com.connectcoins.languages.LanguageManager;
import com.connectcoins.mainMenu.MainMenuScreen;
//import com.connectcoins.multiplayerUtils.MultiplayerGameData;
import com.connectcoins.utils.AnimatedButton;
import com.connectcoins.utils.ButtonAssets;
import com.connectcoins.utils.ScalableFontButton.Size;

public class GameCompletionManager implements Disposable {
	class ChallengeRewardData {
		private int challengeNum;
		private int challengeLevelNum;
		private boolean rewardUnlocked;
		private int currenctNumOfConnections;
		private int leastNumOfConnections;
		private String challengeRewardMessage;

		public ChallengeRewardData() {
			reset();
		}

		public void reset(){
			challengeNum = 0;
			challengeLevelNum = 0;
			rewardUnlocked = false;
			currenctNumOfConnections = 0;
			leastNumOfConnections = 0;
			challengeRewardMessage = "";
		}
	}

	public enum RematchState {
		NONE, WAITING, READY
	}

//	private OpponentDisconnectListener opponentDListener;
	private GameScreen gScreen;
	private ConnectCoins game;
	private Scorer scorer;
	private TextureRegion titleLayout, dataLayout, bar1, bar2, connectCoinStats;
	private Texture scoreSymbol, ccSymbol, blackBG;
	private Texture winner;
	private Button cancelRButton;
	private String winPlayerIGN;
	private AchievementManager achievementManager;
	private AnimatedButton playAgainButton, submitScoreButton, backButton, rematchButton, befriendButton;
	private AnimatedButton backToChallenge, nextChallenge;
//	private MultiplayerResultScreen mRScreen;
//	private MultiplayerGameData mGameData;
//	private MultiplayerRematchView mRView;
	private boolean showGameCompletion = false;
	private ChallengeRewardData challengeRData;

	public GameCompletionManager(GameScreen gScreen, AchievementManager achievementManager, Scorer scorer){
		this.gScreen = gScreen;
		this.game = gScreen.game;
		this.scorer = scorer;
		this.achievementManager = achievementManager;

		TextureAtlas layoutsAtlas = game.assetLoader.getTextureAtlas("achievementIcons");
		challengeRData = new ChallengeRewardData();
		titleLayout = layoutsAtlas.findRegion("title");
		dataLayout = layoutsAtlas.findRegion("dataLayout");
		bar1 = layoutsAtlas.findRegion("bar1");
		bar2 = layoutsAtlas.findRegion("bar2");
		scoreSymbol = game.assetLoader.getTexture("scoreSymbol");
		ccSymbol = game.assetLoader.getTexture("ccSymbol");
		blackBG = game.assetLoader.getTexture("blackBG");
		connectCoinStats = layoutsAtlas.findRegion("connectCoinStats");
		game.assetLoader.getTexture("scoreboard");
		winPlayerIGN = "";

		createGameButtons();
	}

	private void createGameButtons() {
		ButtonStyle style1 = new ButtonStyle();
		style1.up = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));
		style1.down = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));
		style1.disabled = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButtonD));

		playAgainButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Play Again", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
				true) {
			@Override
			public void onAnimationEnd() {
				gScreen.restartGame();
			}
		};
		playAgainButton.setBounds(190, 130, 700, 120);
		playAgainButton.setUserObject(GameState.GAME_COMPLETION);
		playAgainButton.setButtonIcons(game.buttonIconAssetManager, "playAgain");

		ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable(new TextureRegion(ButtonAssets.averageButton.get(0)));
		style2.down = new TextureRegionDrawable(new TextureRegion(ButtonAssets.averageButton.get(0)));
		style2.disabled = new TextureRegionDrawable(new TextureRegion(ButtonAssets.averageButtonD));

		switch (game.gMConfig.mode){
		case NORMAL: {
			playAgainButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
					"Play Again", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
					true) {
				@Override
				public void onAnimationEnd() {
//					game.appWarpManager.stopAllTask();
					gScreen.restartGame();
				}
			};
			playAgainButton.setBounds(190, 130, 700, 120);
			playAgainButton.setUserObject(GameState.GAME_COMPLETION);
			playAgainButton.setButtonIcons(game.buttonIconAssetManager, "playAgain");

			submitScoreButton = new AnimatedButton(ButtonAssets.averageButton, style2, game.fManager.largeFont, 1.1f, 1.1f,
					"Post Score", Color.valueOf("281500"), Size.AVERAGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
					true) {
				@Override
				public void onAnimationEnd() {
//					if (game.appWarpManager.hasInternetConnection()){
//						ProceedingTask pTask = new ProceedingTask() {
//							@Override
//							public boolean proceedTrigger() {
//								return game.appWarpManager.isSignedIn();
//							}
//
//							@Override
//							public void proceed() {
//								Gdx.app.postRunnable(new Runnable() {
//									@Override
//									public void run() {
//										game.loadingCircle.stop();
//										game.appWarpManager.submitScore(newScore, true);
//										submitScoreButton.setTouchable(Touchable.disabled);
//										submitScoreButton.setDisabled(true);
//									}
//								});
//							}
//
//							@Override
//							public boolean exitTrigger() {
//								return game.appWarpManager.isConnectionFailed();
//							}
//
//							@Override
//							public void reset() {
//								game.loadingCircle.stop();
//								submitScoreButton.reset(true);
//								//							game.appWarpManager.setDebugMsg();
//							}
//						};
//						game.loadingCircle.start();
//						game.appWarpManager.addTasks(pTask);
//						game.appWarpManager.signIn(false);
//					}
//					else {
//						game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
//						submitScoreButton.reset(true);
//					}
				}
			};
			submitScoreButton.setBounds(10, 10, 500, 100);
			submitScoreButton.setUserObject(GameState.GAME_COMPLETION);
			submitScoreButton.setButtonIcons(game.buttonIconAssetManager, "submit");

			backButton = new AnimatedButton(ButtonAssets.averageButton, style2, game.fManager.largeFont, 1.1f, 1.1f,
					"Back", Color.valueOf("281500"), Size.AVERAGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
					true) {
				@Override
				public void onAnimationEnd() {
					Gdx.app.postRunnable(new Runnable() {  
						@Override  
						public void run () {  
//							game.appWarpManager.stopAllTask();
							game.setScreen(new MainMenuScreen(game));
							gScreen.dispose();
							System.gc();
						}  
					});
				}
			};
			backButton.setBounds(560, 10, 500, 100);
			backButton.setUserObject(GameState.GAME_COMPLETION);
			backButton.setButtonIcons(game.buttonIconAssetManager, "back");

			game.stage.addActor(playAgainButton);
			game.stage.addActor(submitScoreButton);
			game.stage.addActor(backButton);
		}; break;
		case CHALLENGE: {
			nextChallenge = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
					"Next Puzzle", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
					true) {
				@Override
				public void onAnimationEnd() {
					game.gMConfig.levelNum++;
					gScreen.restartGame();
				}

				@Override
				public void act(float delta) {
					if (game.gMConfig.levelNum == 100) setDisabled(true);
					super.act(delta);
				}
			};
			nextChallenge.setBounds(190, 130, 700, 120);
			nextChallenge.setUserObject(GameState.GAME_COMPLETION);
			nextChallenge.setButtonIcons(game.buttonIconAssetManager, "nextPuzzle");

			backToChallenge = new AnimatedButton(ButtonAssets.averageButton, style2, game.fManager.largeFont, 1.1f, 1.1f,
					"Back", Color.valueOf("281500"), Size.AVERAGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
					true) {
				@Override
				public void onAnimationEnd() {
					Gdx.app.postRunnable(new Runnable() {  
						@Override  
						public void run () {  
//							game.appWarpManager.stopAllTask();
							game.setScreen(new MainMenuScreen(game));
							gScreen.dispose();
							System.gc();
						}  
					});
				}
			};
			backToChallenge.setBounds(290, 10, 500, 100);
			backToChallenge.setUserObject(GameState.GAME_COMPLETION);
			backToChallenge.setButtonIcons(game.buttonIconAssetManager, "back");

			game.stage.addActor(nextChallenge);
			game.stage.addActor(backToChallenge);
		}; break;
		case TUTORIAL: {

		}; break;
		default: break;
		}
	}

	public void render(SpriteBatch batch, boolean debug){
		if (showGameCompletion || debug){
			switch (game.gMConfig.mode){
			case NORMAL: {
				float resultsY = 80;

				batch.draw(blackBG, -180, 0, 1440, 1920);
				batch.draw(titleLayout, 180, resultsY + 1300, 730, 165);
				batch.draw(dataLayout, 180, resultsY + 200, 730, 1117);
				batch.draw(connectCoinStats, 0, 1460, 1080, 500);

				game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1.5f, 1.5f, "Results", 450, resultsY + 1452, 200,
						Align.center);

				game.fManager.drawSmallFont(batch, Color.valueOf("1f154d"), .8f, .8f, "Big Flush X " + scorer.getTotalBigFlush() + " : " + scorer.getBigFlushRealScore(), 440, resultsY + 1265,
						200, Align.center);
				game.fManager.drawSmallFont(batch, Color.valueOf("1f154d"), .8f, .8f, "+ Bonus : " + scorer.getBigFlushBonus(), 440, resultsY + 1225,
						200, Align.center);

				game.fManager.drawSmallFont(batch, Color.valueOf("1f154d"), .8f, .8f, "Flush X " + scorer.getTotalFlush() + " : " + scorer.getFlushRealScore(), 440, resultsY + 1165,
						200, Align.center);
				game.fManager.drawSmallFont(batch, Color.valueOf("1f154d"), .8f, .8f, "+ Bonus : " + scorer.getFlushBonus(), 440, resultsY + 1125,
						200, Align.center);

				game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .8f, .8f, "Small Flush X " + scorer.getTotalSmallFlush() + " : " + scorer.getSmallFlushRealScore(), 440, resultsY + 1065,
						200, Align.center);
				game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .8f, .8f, "+ Bonus : " + scorer.getSmallFlushBonus(), 440, resultsY + 1025,
						200, Align.center);

				game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .8f, .8f, "5 of a Kind X " + scorer.getTotalFiveOfAKind() + " : " + scorer.getFiveOfAKindRealScore(), 440, resultsY + 965,
						200, Align.center);
				game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .8f, .8f, "+ Bonus : " + scorer.getFiveOfAKindBonus(), 440, resultsY + 925,
						200, Align.center);

				game.fManager.drawSmallFont(batch, Color.valueOf("661212"), .8f, .8f, "4 of a Kind X " + scorer.getTotalFourOfAKind() + " : " + scorer.getFourOfAKindRealScore(), 440, resultsY + 865,
						200, Align.center);
				game.fManager.drawSmallFont(batch, Color.valueOf("661212"), .8f, .8f, "+ Bonus : " + scorer.getFourOfAKindBonus(), 440, resultsY + 825,
						200, Align.center);

				game.fManager.drawSmallFont(batch, Color.valueOf("661212"), .8f, .8f, "3 of a Kind X " + scorer.getTotalThreeOfAKind() + " : " + scorer.getThreeOfAKindRealScore(), 440, resultsY + 765,
						200, Align.center);
				game.fManager.drawSmallFont(batch, Color.valueOf("661212"), .8f, .8f, "+ Bonus : " + scorer.getThreeOfAKindBonus(), 440, resultsY + 725,
						200, Align.center);

				game.fManager.drawSmallFont(batch, Color.valueOf("401f09"), .8f, .8f, "Other : " + scorer.getOther(), 440, resultsY + 655,
						200, Align.center);

				batch.draw(bar1, 255, resultsY + 535, 576, 22);
				batch.draw(bar2, 238, resultsY + 380, 606, 22);

				float scaleX = 1f;
				float scaleY = 1f;

				String score = String.valueOf(scorer.getScore());
				String ccEarned = String.valueOf(scorer.getCcEarned());
				String bestScore = String.valueOf(scorer.getBestScore());
				String totalCC = String.valueOf(scorer.getTotalCC());

				String scoreString = "Score :      " + score;
				String ccEarnedString = "CC Earned :    " + ccEarned;
				String bestScoreString = "Best Score :      " + bestScore;
				String totalCCString = "Total CC :    " + totalCC;

				ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(scoreString));
				float scoreX = 540 - (ConnectCoins.glyphLayout.width / 2);
				ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(ccEarnedString));
				float ccEarnedX = 540 - (ConnectCoins.glyphLayout.width / 2);
				ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(bestScoreString));
				float bestScoreX = 540 - (ConnectCoins.glyphLayout.width / 2);
				ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(totalCCString));
				float totalCCX = 540 - (ConnectCoins.glyphLayout.width / 2);

				float prevScaleX = game.fManager.smallFont.getScaleX();
				float prevScaleY = game.fManager.smallFont.getScaleY();
				Color prevColor = game.fManager.smallFont.getColor();
				game.fManager.smallFont.getData().setScale(scaleX, scaleY);
				game.fManager.smallFont.setColor(Color.valueOf("000000"));


				game.fManager.smallFont.draw(batch, scoreString, scoreX, resultsY + 540, 220, Align.left, false);
				game.fManager.smallFont.draw(batch, ccEarnedString, ccEarnedX, resultsY + 480, 200, Align.left, false);
				game.fManager.smallFont.draw(batch, bestScoreString, bestScoreX, resultsY + 380, 200, Align.left, false);
				game.fManager.smallFont.draw(batch, totalCCString, totalCCX, resultsY + 320, 200, Align.left, false);

				batch.draw(scoreSymbol, scoreX + 185, resultsY + 468, 60, 60);
				batch.draw(ccSymbol, ccEarnedX + 290, resultsY + 406, 60, 60);
				batch.draw(scoreSymbol, bestScoreX + 310, resultsY + 310, 60, 60);
				batch.draw(ccSymbol, totalCCX + 245, resultsY + 246, 60, 60);

				game.fManager.smallFont.getData().setScale(prevScaleX, prevScaleY);
				game.fManager.smallFont.setColor(prevColor);

				playAgainButton.draw(batch, 1);
				submitScoreButton.draw(batch, 1);
				backButton.draw(batch, 1);
				
				if (achievementManager.isShowAchievementUnlock()) displayAchievementManager(batch);
			}; break;
			case CHALLENGE: {
				float resultsY = 80;


				batch.draw(blackBG, -180, 0, 1440, 1920);
				batch.draw(titleLayout, 180, resultsY + 1300, 730, 165);
				batch.draw(dataLayout, 180, resultsY + 200, 730, 1117);
				batch.draw(connectCoinStats, 0, 1460, 1080, 500);

				game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1.5f, 1.5f, "Results", 450, resultsY + 1452, 200,
						Align.center);

				game.fManager.drawSmallFont(batch, Color.valueOf("1f154d"), 1.8f, 1.8f,
						"Challenge " + String.valueOf(challengeRData.challengeNum), 440, resultsY + 1305,
						200, Align.center);
				game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), 1.8f, 1.8f,
						"Puzzle " + String.valueOf(challengeRData.challengeLevelNum), 440, resultsY + 1205,
						200, Align.center);

				if (challengeRData.rewardUnlocked){
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "---Total Connections---", 438, resultsY + 1015,
							200, Align.center);
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f,
							String.valueOf(challengeRData.currenctNumOfConnections), 438, resultsY + 955,
							200, Align.center);

					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "---Least Connections---", 438, resultsY + 865,
							200, Align.center);
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f,
							String.valueOf(challengeRData.leastNumOfConnections), 438, resultsY + 805,
							200, Align.center);
				}
				else {
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "---Total Connections---", 438, resultsY + 935,
							200, Align.center);
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f,
							String.valueOf(challengeRData.currenctNumOfConnections), 438, resultsY + 875,
							200, Align.center);

					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, "---Least Connections---", 438, resultsY + 745,
							200, Align.center);
					game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1f, 1f, 
							String.valueOf(challengeRData.leastNumOfConnections), 438, resultsY + 685,
							200, Align.center);
				}

				batch.draw(bar1, 255, resultsY + 445, 576, 22);
				batch.draw(bar2, 238, resultsY + 330, 606, 22);

				float scaleX = 1f;
				float scaleY = 1f;

				String rewardMsg = challengeRData.challengeRewardMessage;
				String ccEarned = String.valueOf(scorer.getCcEarned());
				String totalCC = String.valueOf(scorer.getTotalCC());

				String ccEarnedString = "CC Earned :    " + ccEarned;
				String totalCCString = "Total CC :    " + totalCC;

				ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(ccEarnedString));
				float ccEarnedX = 540 - (ConnectCoins.glyphLayout.width / 2);
				ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(totalCCString));
				float totalCCX = 540 - (ConnectCoins.glyphLayout.width / 2);

				if (challengeRData.rewardUnlocked){
					game.fManager.drawWrappedFont(game.fManager.smallFont, batch, Color.valueOf("000000"), 1, 1,
							rewardMsg, 238, resultsY + 645, 600, Align.center);
				}

				float prevScaleX = game.fManager.smallFont.getScaleX();
				float prevScaleY = game.fManager.smallFont.getScaleY();
				Color prevColor = game.fManager.smallFont.getColor();
				game.fManager.smallFont.getData().setScale(scaleX, scaleY);
				game.fManager.smallFont.setColor(Color.valueOf("000000"));

				game.fManager.smallFont.draw(batch, ccEarnedString, ccEarnedX, resultsY + 440, 200, Align.left, false);
				game.fManager.smallFont.draw(batch, totalCCString, totalCCX, resultsY + 320, 200, Align.left, false);

				batch.draw(ccSymbol, ccEarnedX + 290, resultsY + 366, 60, 60);
				batch.draw(ccSymbol, totalCCX + 245, resultsY + 246, 60, 60);

				game.fManager.smallFont.getData().setScale(prevScaleX, prevScaleY);
				game.fManager.smallFont.setColor(prevColor);

				nextChallenge.draw(batch, 1);
				backToChallenge.draw(batch, 1);
			}; break;
			case TUTORIAL: {

			}; break;
			default: break;
			}
		}
//		game.appWarpManager.activateTasks();
	}


//	if (mRScreen.isRenderMRScreen()){
//		backButton.setTouchable(Touchable.disabled);
//		rematchButton.setTouchable(Touchable.disabled);
//
//		mRScreen.render(batch);
//	}
//	else {
//		float resultsY = 80;
//
//		batch.draw(blackBG, -180, 0, 1440, 1920);
//		batch.draw(titleLayout, 180, resultsY + 1300, 730, 165);
//		batch.draw(dataLayout, 180, resultsY + 200, 730, 1117);
//		batch.draw(connectCoinStats, 0, 1460, 1080, 500);
//		batch.draw(winner, 341, 1225, 398, 92);
//
//		batch.draw(bar1, 255, resultsY + 535, 576, 22);
//		batch.draw(bar2, 238, resultsY + 380, 606, 22);
//
//
//		float statsY = resultsY - 120;
//
//		game.fManager.drawSmallFont(batch, Color.valueOf("000000"), 1.5f, 1.5f, "Results", 450, resultsY + 1452, 200,
//				Align.center);
//
//		game.fManager.drawSmallFont(batch, Color.valueOf("1f154d"), .8f, .8f,
//				"Round Win/Lose/Draw : " + mGameData.getRoundWins() + "/" + mGameData.getRoundLoses() + "/" + mGameData.getRoundDraws(),
//				440, statsY + 1120, 200, Align.center);
//
//		game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .7f, .7f, "-----------------------------------------------", 440, statsY + 1078,
//				200, Align.center);
//		game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .8f, .8f, mGameData.getRoundCCDetails(1), 440, statsY + 1033,
//				200, Align.center);
//		game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .8f, .8f, mGameData.getRoundCCDetails(2), 440, statsY + 993,
//				200, Align.center);
//		game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .8f, .8f, mGameData.getRoundCCDetails(3), 440, statsY + 953,
//				200, Align.center);
//		game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .8f, .8f, mGameData.getRoundCCDetails(4), 440, statsY + 913,
//				200, Align.center);
//		game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .8f, .8f, mGameData.getRoundCCDetails(5), 440, statsY + 873,
//				200, Align.center);
//		game.fManager.drawSmallFont(batch, Color.valueOf("570f4f"), .7f, .7f, "-----------------------------------------------", 440, statsY + 827,
//				200, Align.center);
//
//		game.fManager.drawSmallFont(batch, Color.valueOf("661212"), .8f, .8f, "Total : " + mGameData.getTotalCC(), 440, statsY + 790,
//				200, Align.center);
//		game.fManager.drawSmallFont(batch, Color.valueOf("6a0f00"), .8f, .8f, "Fees : -" + mGameData.getTotalFees(), 440, statsY + 750,
//				200, Align.center);
//
//
//		float scaleX = 1f;
//		float scaleY = 1f;
//
//		String score = "9999999";
//		String ccEarned = "9999999";
//		String bestScore = "9999999";
//		String totalCC = "999999999";
//
//		String scoreString = "Set Score :      " + score;	//Average score of all rounds
//		String ccEarnedString = "CC Earned :    " + ccEarned;
//		String bestScoreString = "Best Score :      " + bestScore;
//		String totalCCString = "Total CC :    " + totalCC;
//		String winnerText = "WWWWWWWWWW";
//		String rankMsg = "RANK: 999999999 (+10)";
//		String levelMsg = "Lvl. 200 (21990/21990)";
//		boolean wonGame = true;
//
//		if (wonGame) game.fManager.drawDisplayFont(batch, Color.valueOf("1f154d"), .95f, .95f, winnerText,
//				345, 1360, 400, Align.center);
//		else game.fManager.drawDisplayFont(batch, Color.valueOf("1f154d"), .95f, .95f, winnerText,
//				345, 1360, 400, Align.center);
//
//		float scoreX = 540 - (game.fManager.smallFont.getBounds(String.valueOf(scoreString)).width / 2);
//		float ccEarnedX = 540 - (game.fManager.smallFont.getBounds(String.valueOf(ccEarnedString)).width / 2);
//		float bestScoreX = 540 - (game.fManager.smallFont.getBounds(String.valueOf(bestScoreString)).width / 2);
//		float totalCCX = 540 - (game.fManager.smallFont.getBounds(String.valueOf(totalCCString)).width / 2);
//
//		float prevScaleX = game.fManager.smallFont.getScaleX();
//		float prevScaleY = game.fManager.smallFont.getScaleY();
//		Color prevColor = game.fManager.smallFont.getColor();
//		game.fManager.smallFont.setScale(scaleX, scaleY);
//		game.fManager.smallFont.setColor(Color.valueOf("000000"));
//
//		game.fManager.smallFont.drawMultiLine(batch, scoreString, scoreX, resultsY + 540, 220, Align.left);
//		game.fManager.smallFont.drawMultiLine(batch, ccEarnedString, ccEarnedX, resultsY + 480, 200, Align.left);
//		game.fManager.smallFont.drawMultiLine(batch, bestScoreString, bestScoreX, resultsY + 380, 200, Align.left);
//		game.fManager.smallFont.drawMultiLine(batch, totalCCString, totalCCX, resultsY + 320, 200, Align.left);
//
//		batch.draw(scoreSymbol, scoreX + 280, resultsY + 468, 60, 60);
//		batch.draw(ccSymbol, ccEarnedX + 290, resultsY + 406, 60, 60);
//		batch.draw(scoreSymbol, bestScoreX + 310, resultsY + 310, 60, 60);
//		batch.draw(ccSymbol, totalCCX + 245, resultsY + 246, 60, 60);
//
//		game.fManager.smallFont.setScale(prevScaleX, prevScaleY);
//		game.fManager.smallFont.setColor(prevColor);
//
//		///////////		UNRANKED GAMEPLAY
//		///////////
//		float rankStatY = statsY + 250;
//		//						9d1600 - DECREASE, 005e02 - INCREASE OR NONE
//		game.fManager.drawFont(game.fManager.largeFont3, batch, Color.valueOf("42003b"), .8f, .8f,
//				levelMsg, 290, rankStatY + 950, 500, Align.center);
//		///////////
//		///////////		UNRANKED GAMEPLAY
//
//		backButton.setTouchable(Touchable.enabled);
//		rematchButton.setTouchable(Touchable.enabled);
//		befriendButton.setTouchable(Touchable.enabled);
//		backButton.draw(batch, 1);
//		rematchButton.draw(batch, 1);
//		befriendButton.draw(batch, 1);
//
//		mRView.render(batch);
//		if (achievementManager.isShowAchievementUnlock()) displayAchievementManager(batch);




	public void submitScoreOrDisableSubmitButton(long newScore, boolean directSubmit){
	}

	public void setGameCompletionChallengeData(int challengeNum, int challengeLevelNum, int currenctNumOfConnections,
			int leastNumOfConnections, boolean rewardUnlocked, String challengeRewardMessage){
		challengeRData.challengeNum = challengeNum;
		challengeRData.challengeLevelNum = challengeLevelNum;
		challengeRData.currenctNumOfConnections = currenctNumOfConnections;
		challengeRData.leastNumOfConnections = leastNumOfConnections;
		challengeRData.rewardUnlocked = rewardUnlocked;
		challengeRData.challengeRewardMessage = challengeRewardMessage;
	}
	
	public void disableRematchB(){
		if (rematchButton != null) rematchButton.setDisabled(true);
	}

	public void resetAll(){
		challengeRData.reset();
		showGameCompletion = false;
//		if (game.gMConfig.mode == GameMode.MULTIPLAYER) mRScreen.resetAll();
	}

	public void setWinPlayerIGN(String winPlayerIGN) {
		this.winPlayerIGN = winPlayerIGN;
	}

	private void displayAchievementManager(SpriteBatch batch){
		achievementManager.render(batch);
	}
	
//	public MultiplayerRematchView getmRView() {
//		return mRView;
//	}

	public void setShowGameCompletion(boolean showGameCompletion) {
		this.showGameCompletion = showGameCompletion;
	}

//	public MultiplayerResultScreen getmRScreen() {
//		return mRScreen;
//	}

//	public MultiplayerGameData getmGameData() {
//		return mGameData;
//	}

	@Override
	public void dispose() {
		showGameCompletion = false;
		gScreen = null;
		scorer = null;
		titleLayout = null;
		dataLayout = null;
		bar1 = null;
		bar2 = null;
		scoreSymbol = null;
		ccSymbol = null;
		blackBG = null;
		winner = null;
		connectCoinStats = null;
		if (cancelRButton != null) cancelRButton.remove();
		cancelRButton = null;
		achievementManager = null;
		if (playAgainButton != null) playAgainButton.remove();
		if (submitScoreButton != null) submitScoreButton.remove();
		if (backButton != null) backButton.remove();
		if (rematchButton != null) rematchButton.remove();
		playAgainButton = null;
		submitScoreButton = null;
		backButton = null;
		rematchButton = null;
		if (backToChallenge != null){
			backToChallenge.remove();
			backToChallenge.dispose();
		}
		backToChallenge = null;
		if (nextChallenge != null){
			nextChallenge.remove();
			nextChallenge.dispose();
		}
		nextChallenge = null;
//		if (mRScreen != null) mRScreen.dispose();
//		mRScreen = null;
//		mGameData = null;
		challengeRData = null;
	}
}