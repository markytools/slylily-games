package com.connectcoins.gameCompletion;

public class ScoreCoinsData {
	public int totalOneCoins;
	public int totalFiveCoins;
	public int totalTenCoins;
	public int totalShinyCoins;
	
	public ScoreCoinsData(){
		
	}

	public ScoreCoinsData(int totalOneCoins, int totalFiveCoins,
			int totalTenCoins, int totalShinyCoins) {
		super();
		this.totalOneCoins = totalOneCoins;
		this.totalFiveCoins = totalFiveCoins;
		this.totalTenCoins = totalTenCoins;
		this.totalShinyCoins = totalShinyCoins;
	}
}
