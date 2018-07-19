package com.connectcoins.gameCompletion;

import com.connectcoins.game.ConnectCoins;

public class StatisticsComputer {
	public StatisticsComputer(ConnectCoins game){
	}
	
	public void incrementTotalGames(boolean timed){
//		data.totalGames++;
//		if (timed) data.totalTimedGames++;
	}
	
	public void addTotalCC(long cc){
//		data.totalCCCollected += cc;
	}
	
	public void subtractTotalCC(long cc){
//		data.totalCCCollected -= cc;
	}
	
	public void submitBestScore(int score, boolean timedGame){
//		if (timedGame) if (data.bestScore < score) data.bestScore = score;
	}
	
	public void addBigFlushAndCalculate(int total){
//		data.totalBigFlush += total;
//		data.bigFlushPerGame = (int) (data.bigFlushPerGame + ((total - data.bigFlushPerGame) / (float)data.totalTimedGames));
	}
	
	public void addFlushAndCalculate(int total){
//		data.totalFlush += total;
//		data.flushPerGame = (int) (data.flushPerGame + ((total - data.flushPerGame) / (float)data.totalTimedGames));
	}
	
	public void addSmallFlushAndCalculate(int total){
//		data.totalSmallFlush += total;
//		data.smallFlushPerGame = (int) (data.smallFlushPerGame + ((total - data.smallFlushPerGame) / (float)data.totalTimedGames));
	}
	
	public void addFiveOfAKindAndCalculate(int total){
//		data.totalFiveOfAKind += total;
//		data.fiveOfAKindPerGame = (int) (data.fiveOfAKindPerGame + ((total - data.fiveOfAKindPerGame) / (float)data.totalTimedGames));
	}
	
	public void addFourOfAKindAndCalculate(int total){
//		data.totalFourOfAKind += total;
//		data.fourOfAKindPerGame = (int) (data.fourOfAKindPerGame + ((total - data.fourOfAKindPerGame) / (float)data.totalTimedGames));
	}
	
	public void addThreeOfAKindAndCalculate(int total){
//		data.totalThreeOfAKind += total;
//		data.threeOfAKindPerGame = (int) (data.threeOfAKindPerGame + ((total - data.threeOfAKindPerGame) / (float)data.totalTimedGames));
	}
	
	public void addOneCoins(int total){
//		data.oneCoin += total;
	}
	
	public void addFiveCoins(int total){
//		data.fiveCoin += total;
	}
	
	public void addTenCoins(int total){
//		data.tenCoin += total;
	}
	
	public void calculateScorePerGame(int score){
//		data.totaScoreRecords++;
//		data.scorePerGame = (int) (data.scorePerGame + ((score - data.scorePerGame) / (float)data.totaScoreRecords));
	}
	
	public void addShinyCoins(int total){
//		data.totalShinyCoins += total;
	}
	
	public void totalHoursPlayed(float totalHours){
//		data.totalHoursPlayed += totalHours;
	}
}
