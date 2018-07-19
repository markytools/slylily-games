package com.connectcoins.gameCompletion;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.combo.ComboScorer;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.listeners.ScoreManager;

public class Scorer implements Disposable {
	/////////////////////// DATA TO FLUSH
	private int bigFlushRealScore; //The total score.
	private int totalBigFlush; //In numbers, not the total score.
	private int bigFlushBonus;
	private int flushRealScore; //The total score.
	private int totalFlush;
	private int flushBonus;
	private int smallFlushRealScore; //The total score.
	private int totalSmallFlush;
	private int smallFlushBonus;
	private int fiveOfAKindRealScore; //The total score.
	private int totalFiveOfAKind;
	private int fiveOfAKindBonus;
	private int fourOfAKindRealScore; //The total score.
	private int totalFourOfAKind;
	private int fourOfAKindBonus;
	private int threeOfAKindRealScore; //The total score.
	private int totalThreeOfAKind;
	private int threeOfAKindBonus;
	private int other;

	private int score;
	private int ccEarned;
	private int bestScore;
	private int totalCC;
	/////////////////////////////////

	///////////////////////TEMPORARY DATA
	//	private int totalOneCoins;
	//	private int totalFiveCoins;
	//	private int totalTenCoins;
	////////////////////////

	private ConnectCoins game;
	private StatisticsComputer statisticsComputer;

	public Scorer(ConnectCoins game){
		this.game = game;
		statisticsComputer = new StatisticsComputer(game);
		resetAll();
	}

	public void addOneCombo(String combo, int realScore, int bonus){
		if (game.gMConfig.mode == GameMode.NORMAL){
			switch (combo){
			case ComboScorer.BIG_FLUSH: {
				bigFlushRealScore += realScore;
				totalBigFlush++;
				bigFlushBonus += bonus;
			}; break;
			case ComboScorer.FLUSH: {
				flushRealScore += realScore;
				totalFlush++;
				flushBonus += bonus;
			}; break;
			case ComboScorer.SMALL_FLUSH: {
				smallFlushRealScore += realScore;
				totalSmallFlush++;
				smallFlushBonus += bonus;
			}; break;
			case ComboScorer.FIVE_OF_A_KIND: {
				fiveOfAKindRealScore += realScore;
				totalFiveOfAKind++;
				fiveOfAKindBonus += bonus;
			}; break;
			case ComboScorer.FOUR_OF_A_KIND: {
				fourOfAKindRealScore += realScore;
				totalFourOfAKind++;
				fourOfAKindBonus += bonus;
			}; break;
			case ComboScorer.THREE_OF_A_KIND: {
				threeOfAKindRealScore += realScore;
				totalThreeOfAKind++;
				threeOfAKindBonus += bonus;
			}; break;
			default: {
				other += realScore;
			}; break;
			}
		}
	}
	
	/**
	 * Use for challenge mode.
	 * @param timedGame
	 */
	public void setGameData(boolean timedGame){
		int totalOneCoins = 0;
		int totalFiveCoins = 0;
		int totalTenCoins = 0;
		int totalShinyCoins = 0;
		
		flushData(timedGame, ccEarned, score, totalBigFlush, totalFlush, totalSmallFlush, totalFiveOfAKind,
				totalFourOfAKind, totalThreeOfAKind, totalOneCoins, totalFiveCoins, totalTenCoins, totalShinyCoins, 0);

//		ProfileState pState = game.pUpdater.getProfileState();
//		this.bestScore = pState.bestScore;
//		this.totalCC = pState.totalCCCollected;
	}

	/**
	 * Do not use for challenge mode.
	 * @param timedGame
	 */
	public void setGameData(boolean timedGame, int ccEarned, int score, float totalHours, Array<ScoreManager> scoreManagers){
		this.score = score;
		this.ccEarned = ccEarned;

		int totalOneCoins = 0;
		int totalFiveCoins = 0;
		int totalTenCoins = 0;
		int totalShinyCoins = 0;
		for (ScoreManager scoreManager : scoreManagers){
			totalOneCoins += scoreManager.totalOneCoins;
			totalFiveCoins += scoreManager.totalFiveCoins;
			totalTenCoins += scoreManager.totalTenCoins;
			totalShinyCoins += scoreManager.totalShinyCoins;
		}
		flushData(timedGame, ccEarned, score, totalBigFlush, totalFlush, totalSmallFlush, totalFiveOfAKind,
				totalFourOfAKind, totalThreeOfAKind, totalOneCoins, totalFiveCoins, totalTenCoins, totalShinyCoins, totalHours);

//		ProfileState pState = game.pUpdater.getProfileState();
//		this.bestScore = pState.bestScore;
//		this.totalCC = pState.totalCCCollected;
	}

	private void flushData(boolean timedGame, long ccEarned, int score, int totalBigFlush, int totalFlush, int totalSmallFlush,
			int totalFiveOfAKind, int totalFourOfAKind, int totalThreeOfAKind, int oneCoins, int fiveCoins, int tenCoins,
			int shinyCoins, float totalHours){
//		ProfileState pState = game.pUpdater.getProfileState();
		statisticsComputer.incrementTotalGames(timedGame);
		statisticsComputer.addTotalCC(ccEarned);
		statisticsComputer.submitBestScore(score, timedGame);
		statisticsComputer.addBigFlushAndCalculate(totalBigFlush);
		statisticsComputer.addFlushAndCalculate(totalFlush);
		statisticsComputer.addSmallFlushAndCalculate(totalSmallFlush);
		statisticsComputer.addFiveOfAKindAndCalculate(totalFiveOfAKind);
		statisticsComputer.addFourOfAKindAndCalculate(totalFourOfAKind);
		statisticsComputer.addThreeOfAKindAndCalculate(totalThreeOfAKind);
		statisticsComputer.addOneCoins(oneCoins);
		statisticsComputer.addFiveCoins(fiveCoins);
		statisticsComputer.addTenCoins(tenCoins);
		statisticsComputer.calculateScorePerGame(score);
		statisticsComputer.addShinyCoins(shinyCoins);
		statisticsComputer.totalHoursPlayed(totalHours);
//		game.pUpdater.updateReferences(pState);
	}

	public void resetAll(){
		totalBigFlush = 0;
		bigFlushBonus = 0;
		totalFlush = 0;
		flushBonus = 0;
		totalSmallFlush = 0;
		smallFlushBonus = 0;
		totalFiveOfAKind = 0;
		fiveOfAKindBonus = 0;
		totalFourOfAKind = 0;
		fourOfAKindBonus = 0;
		totalThreeOfAKind = 0;
		threeOfAKindBonus = 0;
		other = 0;

		score = 0;
		ccEarned = 0;
		bestScore = 0;
		totalCC = 0;
	}

	public long getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getCcEarned() {
		return ccEarned;
	}

	public void setCcEarned(int ccEarned) {
		this.ccEarned = ccEarned;
	}

	public long getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	public long getTotalCC() {
		return totalCC;
	}

	public void setTotalCC(int totalCC) {
		this.totalCC = totalCC;
	}

	public StatisticsComputer getStatisticsComputer() {
		return statisticsComputer;
	}

	public int getBigFlushRealScore() {
		return bigFlushRealScore;
	}

	public int getTotalBigFlush() {
		return totalBigFlush;
	}

	public int getFlushRealScore() {
		return flushRealScore;
	}

	public int getTotalFlush() {
		return totalFlush;
	}

	public int getSmallFlushRealScore() {
		return smallFlushRealScore;
	}

	public int getTotalSmallFlush() {
		return totalSmallFlush;
	}

	public int getFiveOfAKindRealScore() {
		return fiveOfAKindRealScore;
	}

	public int getTotalFiveOfAKind() {
		return totalFiveOfAKind;
	}

	public int getFourOfAKindRealScore() {
		return fourOfAKindRealScore;
	}

	public int getTotalFourOfAKind() {
		return totalFourOfAKind;
	}

	public int getThreeOfAKindRealScore() {
		return threeOfAKindRealScore;
	}

	public int getTotalThreeOfAKind() {
		return totalThreeOfAKind;
	}

	public int getOther() {
		return other;
	}

	public int getBigFlushBonus() {
		return bigFlushBonus;
	}

	public int getFlushBonus() {
		return flushBonus;
	}

	public int getSmallFlushBonus() {
		return smallFlushBonus;
	}

	public int getFiveOfAKindBonus() {
		return fiveOfAKindBonus;
	}

	public int getFourOfAKindBonus() {
		return fourOfAKindBonus;
	}

	public int getThreeOfAKindBonus() {
		return threeOfAKindBonus;
	}

	@Override
	public void dispose() {
		game = null;
		statisticsComputer = null;
		

		bigFlushRealScore= 0; //The total score.
		totalBigFlush= 0; //In numbers, not the total score.
		bigFlushBonus= 0;
		flushRealScore= 0; //The total score.
		totalFlush= 0;
		flushBonus= 0;
		smallFlushRealScore= 0; //The total score.
		totalSmallFlush= 0;
		smallFlushBonus= 0;
		fiveOfAKindRealScore= 0; //The total score.
		totalFiveOfAKind= 0;
		fiveOfAKindBonus= 0;
		fourOfAKindRealScore= 0; //The total score.
		totalFourOfAKind= 0;
		fourOfAKindBonus= 0;
		threeOfAKindRealScore= 0; //The total score.
		totalThreeOfAKind= 0;
		threeOfAKindBonus= 0;
		other= 0;

		score= 0;
		ccEarned= 0;
		bestScore= 0;
		totalCC= 0;
	}
}
