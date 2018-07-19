package com.connectcoins.awards;
import com.badlogic.gdx.utils.Array;
import com.connectcoins.coins.Coin;

public class CoinsComboData {
	private Array<Coin> coins;
	private String combo;
	
	public CoinsComboData(Array<Coin> coins, String combo) {
		super();
		this.coins = coins;
		this.combo = combo;
	}

	public Array<Coin> getCoins() {
		return coins;
	}

	public String getCombo() {
		return combo;
	}
}