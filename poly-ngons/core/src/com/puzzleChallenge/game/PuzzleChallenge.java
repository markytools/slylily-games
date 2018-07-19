package com.puzzleChallenge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gameTools.game.ScalableFontButton;
import com.gameTools.game.ShadedBitmapFont;
import com.polyngons.game.GameScreen;
import com.polyngons.game.PolyNGons;
import com.polyNGonsMainMenu.game.MainMenuScreen;
import com.polyNgonConstants.game.SelectedChallenge;

public class PuzzleChallenge {
	private PolyNGons game;
	private ScalableFontButton regularP, timedP, start;
	private ShadedBitmapFont sChallengeLabel, details, myRating, ratingNum;
	private String regularD, timedD;
	private Stage pChallengeStage;
	private MainMenuScreen mMScreen;
	
	public PuzzleChallenge(PolyNGons game, MainMenuScreen mMScreen){
		this.game = game;
		this.mMScreen = mMScreen;
		
		game.pCType = SelectedChallenge.NONE;
		
		createButtons();
		createText();
	}
	
	private void createButtons() {
		regularP = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")), 
				game.assetM.get(game.assetID.get("bSmall2")), "Regular Puzzle", 2.4f);
		regularP.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.pCType = SelectedChallenge.REGULAR_PUZZLE;
				super.clicked(event, x, y);
			}
		});
		regularP.setBounds(140, 300, 250, 50);

		timedP = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")), 
				game.assetM.get(game.assetID.get("bSmall2")), "Timed Puzzle", 2.4f);
		timedP.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.pCType = SelectedChallenge.TIMED_PUZZLE;
				super.clicked(event, x, y);
			}
		});
		timedP.setBounds(140, 240, 250, 50);

		start = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bBig1")), 
				game.assetM.get(game.assetID.get("bBig2")), "Start", 2.2f);
		start.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.pAdManager.stopInterstitial();
				if (game.requestAd != null) game.pAdManager.showBannerAds(false);
				game.setScreen(new GameScreen(game));
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run() {
						mMScreen.dispose();
					}
				});
				super.clicked(event, x, y);
			}
		});
		start.setBounds(210, 160, 120, 60);
		
		pChallengeStage = new Stage();
		pChallengeStage.addActor(regularP);
		pChallengeStage.addActor(timedP);
		pChallengeStage.addActor(start);
	}
	
	private void createText() {
		regularD = "A regular puzzle generates for you a puzzle to solve without having to worry about a timer. This challenge will " +
				"slightly increase your rating if it doesn't take you too long to solve it. Puzzle difficulty will depend on your rating.";
		timedD = "A timed puzzle gives more rating than a regular puzzle. However, if the player cannot solve the puzzle within the " +
				"given time, rating will decrease as a penalty. Puzzle difficulty will depend on your rating.";
		
		sChallengeLabel = game.bLayout.getCustomFont();
		details = game.bLayout.getCustomFont();
		myRating = game.bLayout.getCustomFont();
		ratingNum = game.bLayout.getCustomFont();
	}


	public void renderPChallenge(){
		game.batch.draw(game.assetM.get(game.assetID.get("layout2")), 100, 50, 600, 400);
		sChallengeLabel.drawFont(game.batch, "Select Challenge", 220, 430, 3, 3, Color.WHITE);
		myRating.drawFont(game.batch, "My Rating:", 420, 170, 2, 2, Color.valueOf("ff9a00"));
		String dString = String.format("%.02f", PuzzleChallengeDisplayer.getRatingBy10(game.pUpdater.getPValue().getFloat("rating")));
		ratingNum.drawFont(game.batch, dString + " out of 10", 430, 130, 2, 2, Color.valueOf("ff8400"));
		regularP.draw(game.batch, 1);
		timedP.draw(game.batch, 1);
		
		switch (game.pCType){
		case NONE:{
			
		}; break;
		case REGULAR_PUZZLE:{
			details.drawWrappedFont(game.batch, regularD, 410, 350, 260, BitmapFont.HAlignment.CENTER,
					1, 1, Color.WHITE);
			start.draw(game.batch, 1);
		}; break;
		case TIMED_PUZZLE:{
			details.drawWrappedFont(game.batch, timedD, 410, 350, 260, BitmapFont.HAlignment.CENTER,
					1, 1, Color.WHITE);
			start.draw(game.batch, 1);
		}; break;
		default: break;
		}
		
		game.batch.end();
		mMScreen.getBackStage().draw();
		game.batch.begin();
	}
	
	public void setPChallengeListeners(){
		mMScreen.getInputs().getProcessors().set(0, mMScreen.getBackStage());
		mMScreen.getInputs().getProcessors().set(1, pChallengeStage);
		
		Gdx.input.setInputProcessor(mMScreen.getInputs());
		mMScreen.getBackStage().getViewport().setCamera(game.cam);
		pChallengeStage.getViewport().setCamera(game.cam);
		mMScreen.getBackStage().act();
		pChallengeStage.act();
		if (game.pCType == SelectedChallenge.NONE) start.setTouchable(Touchable.disabled);
		else start.setTouchable(Touchable.enabled);
	}
	
	public void dispose(){
		pChallengeStage.dispose();
	}
}
