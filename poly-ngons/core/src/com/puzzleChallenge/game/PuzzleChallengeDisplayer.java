package com.puzzleChallenge.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.polyngons.game.GameScreen;
import com.polyNgonConstants.game.PolyNGonsDimConstants;
import com.polyNgonConstants.game.SelectedChallenge;

public class PuzzleChallengeDisplayer {
	private GameScreen gScreen;
	private SelectedChallenge pPType;

	private float oldRating;
	private int starCount;
	private long timeLimit;
	private long startTime, blinkTimerDelay;
	private float bTimerAlpha, playTickingVal;
	private String newString;
	private String valueToAddOrSubtract;
	private String stoppedTime;
	private boolean resultsApplied, displayable;
	private boolean stopTimer, startBlink;

	public PuzzleChallengeDisplayer(GameScreen gScreen, SelectedChallenge pPType){
		this.gScreen = gScreen;
		this.pPType = pPType;
		resultsApplied = false;
		stopTimer = false;
		displayable = false;
		startBlink = false;
		blinkTimerDelay = -1;
		playTickingVal = -1;
		bTimerAlpha = 1;
		starCount = 0;

		initPPChallenge();
	}

	private void initPPChallenge() {
		oldRating = gScreen.game.pUpdater.getPValue().getFloat("rating");
		timeLimit = (long) ((((oldRating - 5) / 5) * 20) + ((oldRating / 15) * 30) + 15);
		startTime = - 1;
	}

	public void renderTimer(){
		startTimer();
		if (!stopTimer){
			int secondsLeft = (int) (timeLimit - (TimeUtils.millis() - startTime) / 1000);

			String seconds = String.valueOf(secondsLeft % 60);
			if (seconds.length() != 2) seconds = "0" + seconds;
			String minutes = String.valueOf(secondsLeft / 60);

			stoppedTime = String.valueOf(minutes + ":" + seconds);
			checkIfPuzzleFailed(secondsLeft);

			if (secondsLeft <= 10) {
				if (blinkTimerDelay == -1) startBlink = true;
				if (playTickingVal != secondsLeft && secondsLeft > 0){
					playTickingVal = secondsLeft;
					gScreen.game.gSoundManager.playSound("ticking");
				}
			}
		}
		else {
			blinkTimerDelay = -1;
		}

		if (startBlink){
			startBlink = false;
			blinkTimerDelay = TimeUtils.millis();
		}

		if (blinkTimerDelay == -1){
			gScreen.game.bLayout.getCustomFont().drawFont(gScreen.uiBatch, stoppedTime,
					580, 490, 1.5f, 1.5f, Color.WHITE);
		}
		else {
			if (bTimerAlpha == 0) gScreen.game.bLayout.getCustomFont().drawFont(gScreen.uiBatch, stoppedTime,
					580, 490, 1.5f, 1.5f, Color.WHITE);
			else gScreen.game.bLayout.getCustomFont().drawFont(gScreen.uiBatch, stoppedTime,
					580, 490, 1.5f, 1.5f, Color.RED);
			startBlinking();
		}
	}

	public void checkIfPuzzleFailed(int secondsLeft){
		if (secondsLeft <= 0) {
			stopTimer();
			if (gScreen.game.pCType == SelectedChallenge.TIMED_PUZZLE) gScreen.getpCManager().setPuzzleSolved(false);
		}
	}

	private void startBlinking(){
		if (TimeUtils.millis() - blinkTimerDelay >= 600){
			blinkTimerDelay = TimeUtils.millis();
			if (bTimerAlpha == 1) bTimerAlpha = 0;
			else bTimerAlpha = 1;
		}
	}

	public void startTimer(){
		if (startTime == - 1) startTime = TimeUtils.millis();
	}

	public void stopTimer(){
		stopTimer = true;
	}

	public void renderRatingResult(float x, float y){
		if (gScreen.game.pCType == SelectedChallenge.REGULAR_PUZZLE){
			gScreen.game.bLayout.getCustomFont().drawWrappedFont(gScreen.uiBatch, "Your rating is:",
					x + 25, y + 24, 150, BitmapFont.HAlignment.CENTER, 1.3f, 1.3f, Color.valueOf("ff8400"));
			if (Float.valueOf(valueToAddOrSubtract) >= 0){
				Color color = (!MathUtils.isZero(Float.valueOf(valueToAddOrSubtract))) ? Color.valueOf("c2770b") : Color.valueOf("ff8400");
				gScreen.game.bLayout.getCustomFont().drawWrappedFont(gScreen.uiBatch, newString + "(+" + valueToAddOrSubtract + ")",
						x, y - 1, 200, BitmapFont.HAlignment.CENTER, 1.5f, 1.5f, color);
			}
			else gScreen.game.bLayout.getCustomFont().drawWrappedFont(gScreen.uiBatch, newString + "(" + valueToAddOrSubtract + ")",
					x, y - 1, 200, BitmapFont.HAlignment.CENTER, 1.5f, 1.5f, Color.RED);
		}
		else {
			if (Float.valueOf(valueToAddOrSubtract) >= 0){
				gScreen.game.bLayout.getCustomFont().drawWrappedFont(gScreen.uiBatch, "Your rating is:",
						x + 25, y + 39, 150, BitmapFont.HAlignment.CENTER, 1.3f, 1.3f, Color.valueOf("ff8400"));
				Color color = (!MathUtils.isZero(Float.valueOf(valueToAddOrSubtract))) ? Color.valueOf("c2770b") : Color.valueOf("ff8400");
				gScreen.game.bLayout.getCustomFont().drawWrappedFont(gScreen.uiBatch, newString + "(+" + valueToAddOrSubtract + ")",
						x, y + 14, 200, BitmapFont.HAlignment.CENTER, 1.5f, 1.5f, color);
				for (int i = 0; i < starCount; i++){
					gScreen.uiBatch.draw(gScreen.game.assetM.get(gScreen.game.assetID.get("star")), x + 25 + (i * 30), y - 51, 30, 30);
				}
			}
			else {
				gScreen.game.bLayout.getCustomFont().drawWrappedFont(gScreen.uiBatch, "Your rating is:",
						x + 25, y + 24, 150, BitmapFont.HAlignment.CENTER, 1.3f, 1.3f, Color.valueOf("ff8400"));
				gScreen.game.bLayout.getCustomFont().drawWrappedFont(gScreen.uiBatch, newString + "(" + valueToAddOrSubtract + ")",
						x, y - 1, 200, BitmapFont.HAlignment.CENTER, 1.5f, 1.5f, Color.RED);
			}
		}
	}

	public void appleFailedResult(boolean quitLastGame){
		displayable =  true;
		switch (pPType){
		case NONE:{

		}; break;
		case REGULAR_PUZZLE:{
			setRatingResults(-1);
		}; break;
		case TIMED_PUZZLE:{
			if (quitLastGame){
				setRatingResults(-3);
			}
			else {
				if (gScreen.getPpManager().getPolysOnPuzzle().size >= 
						gScreen.getPuzzleGen().getPuzzlePolygon().size - (MathUtils.floor((oldRating - 1) / 100))){
					setRatingResults(-1f);
				}
				else if (gScreen.getPpManager().getPolysOnPuzzle().size >= 
						gScreen.getPuzzleGen().getPuzzlePolygon().size - (MathUtils.floor((oldRating - 1) / 50))){
					setRatingResults(-1.5f);
				}
				else if (gScreen.getPpManager().getPolysOnPuzzle().size >= 
						gScreen.getPuzzleGen().getPuzzlePolygon().size - (MathUtils.floor((oldRating - 1) / 35))){
					setRatingResults(-2f);
				}
				else {
					setRatingResults(-2.5f);
				}
			}
		}; break;
		default: break;
		}
		gScreen.game.pUpdater.setRatingSubtract(0);
	}

	public void pChallengeCompleteResult(){
		if (!resultsApplied){
			resultsApplied = true;
			displayable =  true;

			int timeValue = (int) (timeLimit - (TimeUtils.millis() - startTime) / 1000);
			switch (pPType){
			case NONE:{

			}; break;
			case REGULAR_PUZZLE:{
				if (timeValue >= timeLimit * (9f / 25)){
					setRatingResults(1); break;
				}
				else if (timeValue >= timeLimit * (6 / 25)){
					setRatingResults(.5f); break;
				}
				else {
					setRatingResults(0);
				}
			}; break;
			case TIMED_PUZZLE:{
				if (timeValue >= (float)timeLimit * (3f / 5)){
					starCount = 5;
					setRatingResults(3); break;
				}
				else if (timeValue >= (float)timeLimit * (12f / 25)){
					starCount = 5;
					setRatingResults(2.5f); break;
				}
				else if (timeValue >= (float)timeLimit * (9f / 25)){
					starCount = 4;
					setRatingResults(2); break;
				}
				else if (timeValue >= (float)timeLimit * (6f / 25)){
					starCount = 3;
					setRatingResults(1.5f); break;
				}
				else if (timeValue >= (float)timeLimit * (3f / 25)){
					starCount = 2;
					setRatingResults(1f); break;
				}
				else {
					starCount = 1;
					setRatingResults(.5f);
				}
			}; break;
			default: break;
			}
			gScreen.game.pUpdater.setRatingSubtract(0);
		}
	}

	private void setRatingResults(float points){
		String oldString = String.format("%.02f", getRatingBy10(oldRating));
		float newRating = oldRating + points;
		if (newRating > PolyNGonsDimConstants.MAX_PCHALLENGE_LIMIT) newRating = PolyNGonsDimConstants.MAX_PCHALLENGE_LIMIT;
		else if (newRating < 5) newRating = 5;
		gScreen.game.pUpdater.setRatingValue(newRating);
		newString = String.format("%.02f", getRatingBy10(newRating));
		if (newRating >= 5 && newRating <= PolyNGonsDimConstants.MAX_PCHALLENGE_LIMIT) 
			valueToAddOrSubtract = String.format("%.02f", Float.parseFloat(newString) - Float.parseFloat(oldString));
		else valueToAddOrSubtract = "0";
	}

	public static float getRatingBy10(float newRating){
		return (((float)newRating - 5f) * 10f) / (PolyNGonsDimConstants.MAX_PCHALLENGE_LIMIT - 5);
	}

	public String getValueToAddOrSubtract() {
		return valueToAddOrSubtract;
	}

	public void setStopTimer(boolean stopTimer) {
		this.stopTimer = stopTimer;
	}

	public boolean isStopTimer() {
		return stopTimer;
	}

	public boolean isDisplayable() {
		return displayable;
	}
}
