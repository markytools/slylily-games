package com.connectcoins.connection;

public class CoinAnimation {
	public HalfCoinAnimation currentCoin;
	public HalfCoinAnimation nextCoin;
	public boolean renderCurrentCoin = true;
	public float x, y;

	public CoinAnimation(HalfCoinAnimation currentCoin,
			HalfCoinAnimation nextCoin) {
		super();
		this.currentCoin = currentCoin;
		this.nextCoin = nextCoin;
	}
	
	public void reset(){
		currentCoin = null;
		nextCoin = null;
		renderCurrentCoin = true;
		x = 0;
		y = 0;
	}
}
