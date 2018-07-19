package com.puzzleCompletion.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.gameInputProcessors.game.PolygonActor;
import com.gameTools.game.ScalableFontButton;
import com.polyngons.game.GameScreen;
import com.polyngons.game.PolyNGons;
import com.polyNGonsMainMenu.game.MainMenuScreen;
import com.polyNgonConstants.game.PolyNGonsGameSelection;
import com.polyNgonConstants.game.PolygonState;
import com.polygons.game.Polygon;

public class PuzzleCompletionManager {
	private GameScreen gScreen;
	private PolyNGons game;
	private boolean puzzleComplete, continueChecking, forward;
	private boolean puzzleSolved, scanComplete, playUnlockSound;
	private ScalableFontButton nextPuzzle, selectDifficulty, rateGame, quit, playChallenge, moreApps;
	private Stage pCompletionStage;

	private PuzzleCompletionScanner pCScanner;
	private PuzzleCompletionUnlocker pCUnlocker;
	private PuzzleCompletionAnimation pCAnimation;

	private long delayDisplay, delayAlpha;
	private float colorCode;

	private boolean initInterstitial;

	public PuzzleCompletionManager(GameScreen gScreen){
		this.gScreen = gScreen;
		game = gScreen.game;
		puzzleComplete = false;
		initInterstitial = true;
		playUnlockSound = true;
		continueChecking = true;
		forward = false;
		pCScanner = new PuzzleCompletionScanner(gScreen.getPuzzleGen());
		pCUnlocker = new PuzzleCompletionUnlocker(game);
		pCAnimation = new PuzzleCompletionAnimation(gScreen);
		colorCode = 1;
		delayAlpha = 0;
		delayDisplay = -1;

		createImageTextButtons();
	}

	private void createImageTextButtons() {

		moreApps = gScreen.game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")), 
				game.assetM.get(game.assetID.get("bSmall2")), "View Ads", 2.4f);
		moreApps.setBounds(270, 127, 250, 50);
		moreApps.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.requestAd.showOrLoadAdmobInterstital();
				super.clicked(event, x, y);
			}
		});

		rateGame = gScreen.game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")), 
				game.assetM.get(game.assetID.get("bSmall2")), "Rate Game", 2.4f);
		rateGame.setBounds(270, 179, 250, 50);
		rateGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("http://play.google.com/store/apps/details?id=com.polyngons.game.android");
				super.clicked(event, x, y);
			}
		});

		quit = gScreen.game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")), 
				game.assetM.get(game.assetID.get("bSmall2")), "Quit", 2.4f);
		quit.setBounds(270, 75, 250, 50);
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
				game.gSelection = PolyNGonsGameSelection.NONE;
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run() {
						gScreen.dispose();
					}
				});
				super.clicked(event, x, y);
			}
		});

		pCompletionStage = new Stage();
		switch (game.gSelection){
		case NONE:{

		}; break;
		case PUZZLE_CHALLENGE:{
			playChallenge = gScreen.game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")), 
					game.assetM.get(game.assetID.get("bSmall2")), "Play Again", 2.4f);
			playChallenge.setBounds(270, 231, 250, 50);
			playChallenge.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.pAdManager.stopInterstitial();
					if (game.requestAd != null) game.pAdManager.showBannerAds(false);
					game.setScreen(new GameScreen(game));
					updateCam();
					Gdx.app.postRunnable(new Runnable(){
						@Override
						public void run() {
							gScreen.dispose();
						}
					});
					super.clicked(event, x, y);
				}
			});

			pCompletionStage.addActor(playChallenge);
			pCompletionStage.addActor(rateGame);
			pCompletionStage.addActor(moreApps);
			pCompletionStage.addActor(quit);
		}; break;
		case PUZZLE_COLLECTION:{
			nextPuzzle = gScreen.game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")), 
					game.assetM.get(game.assetID.get("bSmall2")), "Next Puzzle", 2.4f);

			//			nextPuzzle = gScreen.game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")), 
			//					game.assetM.get(game.assetID.get("bSmall2")), "Next Puzzle (" 
			//			+ (game.pUpdater.getPValue().get("currentPuzzle").asIntArray()[game.diffSelected - 1] + 1) + ")", 2.4f);

			nextPuzzle.setBounds(270, 231, 250, 50);
			nextPuzzle.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.pNumSelected += 1;
					game.pUpdater.setIntValue(game.diffSelected - 1, game.pNumSelected, true);
					game.pAdManager.stopInterstitial();
					if (game.requestAd != null) game.pAdManager.showBannerAds(false);
					game.setScreen(new GameScreen(game));
					updateCam();
					Gdx.app.postRunnable(new Runnable(){
						@Override
						public void run() {
							gScreen.dispose();
						}
					});
					super.clicked(event, x, y);
				}
			});

			selectDifficulty = gScreen.game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")), 
					game.assetM.get(game.assetID.get("bSmall2")), "Select Difficulty", 2.4f);
			selectDifficulty.setBounds(270, 231, 250, 50);
			selectDifficulty.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.setScreen(new MainMenuScreen(game));
					game.gSelection = PolyNGonsGameSelection.PUZZLE_COLLECTION;
					Gdx.app.postRunnable(new Runnable(){
						@Override
						public void run() {
							gScreen.dispose();
						}
					});
					super.clicked(event, x, y);
				}
			});

			if (game.pNumSelected == game.pUpdater.getByteArray("d" + game.diffSelected + "PUnlocks").length) 
				pCompletionStage.addActor(selectDifficulty);
			else pCompletionStage.addActor(nextPuzzle);
			pCompletionStage.addActor(moreApps);
			pCompletionStage.addActor(rateGame);
			pCompletionStage.addActor(quit);
		}; break;
		case TUTORIAL:{
			pCompletionStage.addActor(quit);
		}; break;
		default:
			break;
		}
	}

	private void updateCam(){
		game.cam.zoom = 1;
		game.cam.position.x = 400;
		game.cam.position.y = 250;
		game.cam.update();
	}

	public float getColorCode(){
		return colorCode;
	}

	public boolean checkDelayComplete(){
		float limitT = 100000 * game.averageDeltaTime;
		if (TimeUtils.millis() - delayDisplay >= limitT){
			return true;
		}
		else return false;
	}

	public void scanPuzzle(){
		if (continueChecking && pCScanner.checkIfPuzzleComplete(gScreen.getPpManager().getPolysOnPuzzle())) {
			continueChecking = false;
			delayDisplay = TimeUtils.millis();
			puzzleComplete = true;
		}
	}

	public void checkDelayCompletion(){
		if (delayDisplay != -1 && !checkDelayComplete()){
			setProperPolyStats();
			if (colorCode >= 1) forward = false;
			if (colorCode <= .7f) forward = true;
			if (TimeUtils.millis() - delayAlpha >= 1000 * game.averageDeltaTime){
				delayAlpha = TimeUtils.millis();
				float val = 1.2f * game.averageDeltaTime;
				colorCode += (forward) ? val : -val;
			}
		}
	}

	public void renderPuzzleCompletion(){
		if (pCAnimation.isNewUnlocks() && playUnlockSound){
			playUnlockSound = false;
			game.gSoundManager.playSound("new");
		}

		gScreen.uiBatch.begin();
		gScreen.uiBatch.draw(game.assetM.get(game.assetID.get("layout2")), 200, 50, 400, 400);
		switch (game.gSelection){
		case TUTORIAL:{
			gScreen.uiBatch.draw(game.assetM.get(game.assetID.get("puzzleSolved")), 225, 380, 350, 50);
			quit.setBounds(270, 205, 250, 50);
			quit.draw(gScreen.uiBatch, 1);
		}; break;
		case PUZZLE_COLLECTION:{
			pCUnlocker.unlockPuzzleCollectionFeatures(game.diffSelected, game.pNumSelected);
			pCAnimation.setTextures(pCUnlocker.getCurrentUnlockState());

			if (pCAnimation.isNewUnlocks()){
				gScreen.game.getlScreen().getBlockAnimation().drawAnimation(gScreen.uiBatch,
						237, 281, .8f, .8f);
				pCAnimation.renderUnlocks(360, 281);
			}
			else {
				gScreen.game.getlScreen().getBlockAnimation().drawAnimation(gScreen.uiBatch,
						350, 281, .8f, .8f);
			}
			if (puzzleSolved){
				gScreen.uiBatch.draw(game.assetM.get(game.assetID.get("puzzleSolved")), 225, 380, 350, 50);
			}
			else gScreen.uiBatch.draw(game.assetM.get(game.assetID.get("puzzleFailed")), 225, 380, 350, 50);
			if (game.pNumSelected == game.pUpdater.getByteArray("d" + game.diffSelected + "PUnlocks").length) 
				selectDifficulty.draw(gScreen.uiBatch, 1);
			else nextPuzzle.draw(gScreen.uiBatch, 1);
			rateGame.draw(gScreen.uiBatch, 1);
			quit.draw(gScreen.uiBatch, 1);
			moreApps.draw(gScreen.uiBatch, 1);
		}; break;
		case PUZZLE_CHALLENGE:{
			pCUnlocker.unlockPuzzleChallengeFeatures(game.pUpdater.getPValue().getInt("rating"));
			pCAnimation.setTextures(pCUnlocker.getCurrentUnlockState());

			if (puzzleSolved) {
				gScreen.uiBatch.draw(game.assetM.get(game.assetID.get("puzzleSolved")), 225, 380, 350, 50);
			}
			else gScreen.uiBatch.draw(game.assetM.get(game.assetID.get("puzzleFailed")), 225, 380, 350, 50);
			if (pCAnimation.isNewUnlocks()){
				gScreen.getpCDisplay().renderRatingResult(195, 342);
				pCAnimation.renderUnlocks(382, 281);
			}
			else {
				gScreen.getpCDisplay().renderRatingResult(300, 342);
			}
			playChallenge.draw(gScreen.uiBatch, 1);
			rateGame.draw(gScreen.uiBatch, 1);
			quit.draw(gScreen.uiBatch, 1);
			moreApps.draw(gScreen.uiBatch, 1);
		}; break;
		default: break;
		}
		gScreen.uiBatch.end();


		if (initInterstitial){
			initInterstitial = false;
			game.pAdManager.randomShowInterstitial();
		}

		game.pAdManager.showDelayedInterstitial(true);
		if (game.requestAd != null) game.pAdManager.showBannerAds(true);
	}

	public void setProperPolyStats(){
		ArrayMap<Polygon, PolygonActor> polyInPuzzle =  gScreen.getPpManager().getPolysOnPuzzle();
		for (PolygonActor actor : polyInPuzzle.values()){
			actor.setState(PolygonState.FIXED);
			actor.updateSpriteTexture();
		}
		gScreen.selectedPolygon = null;
	}

	public void setPCListeners(){
		pCompletionStage.getViewport().setCamera(gScreen.uiCam);
		Gdx.input.setInputProcessor(pCompletionStage);
		pCompletionStage.act();
	}

	public boolean isPuzzleComplete() {
		return puzzleComplete;
	}

	public void setPuzzleComplete(boolean puzzleComplete) {
		this.puzzleComplete = puzzleComplete;
	}

	public void setPuzzleSolved(boolean puzzleSolved) {
		this.puzzleSolved = puzzleSolved;
	}

	public boolean isScanComplete() {
		return scanComplete;
	}

	public void setScanComplete(boolean scanComplete) {
		this.scanComplete = scanComplete;
	}
}
