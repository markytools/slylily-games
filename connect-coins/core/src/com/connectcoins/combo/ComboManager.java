package com.connectcoins.combo;

import com.badlogic.gdx.utils.Array;
import com.connectcoins.coins.Coin;
import com.connectcoins.coins.Coin.CoinColor;
import com.connectcoins.coins.Coin.CoinValue;

public class ComboManager {
	public static Array<Coin> hasBigFlush(Array<Coin> coinCombo){
		Array<Coin> coins = checkFlush(coinCombo, 5);
		if (coins.size >= 5) return coins; 
		return null;
	}
	
	public static Array<Coin> hasFlush(Array<Coin> coinCombo){
		Array<Coin> coins = checkFlush(coinCombo, 4);
		if (coins.size >= 4) return coins; 
		return null;
	}
	
	public static Array<Coin> hasSmallFlush(Array<Coin> coinCombo){
		Array<Coin> coins = checkFlush(coinCombo, 3);
		if (coins.size >= 3) return coins; 
		return null;
	}
	
	public static Array<Coin> hasFiveOfAKind(Array<Coin> coinCombo){
		Array<Coin> coins = checkAKind(coinCombo, 5);
		if (coins.size >= 5) return coins; 
		return null;
	}
	
	public static Array<Coin> hasFourOfAKind(Array<Coin> coinCombo){
		Array<Coin> coins = checkAKind(coinCombo, 4);
		if (coins.size >= 4) return coins; 
		return null;
	}
	
	public static Array<Coin> hasThreeOfAKind(Array<Coin> coinCombo){
		Array<Coin> coins = checkAKind(coinCombo, 3);
		if (coins.size >= 3) return coins; 
		return null;
	}
	
	
	public static Array<Coin> checkFlush(Array<Coin> coinCombo, int minTotalCoins){
		Array<Coin> flushCoins = new Array<Coin>();
		
		for (int i = 0; i < coinCombo.size; i++){
			Coin currentCoinA = coinCombo.get(i);
			flushCoins.clear();
			flushCoins.add(currentCoinA);
			
			switch (currentCoinA.getCoinType()){
			case WHOLE: {
				CoinValue coinValueA = currentCoinA.getCoinValue();
				CoinColor coinColorA = currentCoinA.getCoinColor();
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinValueA == currentCoinB.getCoinValue() && coinColorA == currentCoinB.getCoinColor()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinValueA == currentCoinB.getCoinValue() && (coinColorA == currentCoinB.getTopCoinColor() ||
									coinColorA == currentCoinB.getBottomCoinColor())){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							if (coinValueA == currentCoinB.getCoinValue()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						default: break;
						}
					}
				}
				if (flushCoins.size >= minTotalCoins) return flushCoins;
			}; break;
			case HALF: {
				CoinValue coinValueA = currentCoinA.getCoinValue();
				CoinColor coinColorA = currentCoinA.getTopCoinColor();
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinValueA == currentCoinB.getCoinValue() && coinColorA == currentCoinB.getCoinColor()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinValueA == currentCoinB.getCoinValue() && (coinColorA == currentCoinB.getBottomCoinColor() ||
									coinColorA == currentCoinB.getTopCoinColor())){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							if (coinValueA == currentCoinB.getCoinValue()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						default: break;
						}
					}
				}
				if (flushCoins.size >= minTotalCoins) return flushCoins;
				
				flushCoins.clear();
				flushCoins.add(currentCoinA);
				

				coinColorA = currentCoinA.getBottomCoinColor();
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinValueA == currentCoinB.getCoinValue() && coinColorA == currentCoinB.getCoinColor()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinValueA == currentCoinB.getCoinValue() && (coinColorA == currentCoinB.getBottomCoinColor() ||
									coinColorA == currentCoinB.getTopCoinColor())){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							if (coinValueA == currentCoinB.getCoinValue()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						default: break;
						}
					}
				}
				if (flushCoins.size >= minTotalCoins) return flushCoins;
			}; break;
			case THIRD: {
				CoinValue coinValueA = currentCoinA.getCoinValue();
				CoinColor coinColorA = CoinColor.GOLD;
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinValueA == currentCoinB.getCoinValue() && coinColorA == currentCoinB.getCoinColor()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinValueA == currentCoinB.getCoinValue() && (coinColorA == currentCoinB.getBottomCoinColor() ||
									coinColorA == currentCoinB.getTopCoinColor())){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							if (coinValueA == currentCoinB.getCoinValue()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						default: break;
						}
					}
				}
				if (flushCoins.size >= minTotalCoins) return flushCoins;
				
				flushCoins.clear();
				flushCoins.add(currentCoinA);
				

				coinColorA = CoinColor.SILVER;
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinValueA == currentCoinB.getCoinValue() && coinColorA == currentCoinB.getCoinColor()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinValueA == currentCoinB.getCoinValue() && (coinColorA == currentCoinB.getBottomCoinColor() ||
									coinColorA == currentCoinB.getTopCoinColor())){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							if (coinValueA == currentCoinB.getCoinValue()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						default: break;
						}
					}
				}
				if (flushCoins.size >= minTotalCoins) return flushCoins;
				
				flushCoins.clear();
				flushCoins.add(currentCoinA);

				
				coinColorA = CoinColor.BRONZE;
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinValueA == currentCoinB.getCoinValue() && coinColorA == currentCoinB.getCoinColor()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinValueA == currentCoinB.getCoinValue() && (coinColorA == currentCoinB.getBottomCoinColor() ||
									coinColorA == currentCoinB.getTopCoinColor())){
								flushCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							if (coinValueA == currentCoinB.getCoinValue()){
								flushCoins.add(currentCoinB);
							}
						}; break;
						default: break;
						}
					}
				}
				if (flushCoins.size >= minTotalCoins) return flushCoins;
			}; break;
			default: break;
			}
			
			flushCoins.clear();
		}
		return flushCoins;
	}

	/**
	 *  Should always be run after checking flushes.
	 */
	public static Array<Coin> checkAKind(Array<Coin> coinCombo, int minTotalCoins){
		Array<Coin> sameKindCoins = new Array<Coin>();
		
		for (int i = 0; i < coinCombo.size; i++){
			Coin currentCoinA = coinCombo.get(i);
			sameKindCoins.clear();
			sameKindCoins.add(currentCoinA);

			switch (currentCoinA.getCoinType()){
			case WHOLE: {
				CoinValue coinValueA = currentCoinA.getCoinValue();
				
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinValueA == currentCoinB.getCoinValue()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinValueA == currentCoinB.getCoinValue()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							if (coinValueA == currentCoinB.getCoinValue()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						default: break;
						}
					}
				}
				if (sameKindCoins.size >= minTotalCoins) return sameKindCoins;

				sameKindCoins.clear();
				sameKindCoins.add(currentCoinA);
				
				
				CoinColor coinColorA = currentCoinA.getCoinColor();
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinColorA == currentCoinB.getCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinColorA == currentCoinB.getTopCoinColor() || coinColorA == currentCoinB.getBottomCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							sameKindCoins.add(currentCoinB);
						}; break;
						default: break;
						}
					}
				}
				if (sameKindCoins.size >= minTotalCoins) return sameKindCoins;
			}; break;
			case HALF: {
				CoinValue coinValueA = currentCoinA.getCoinValue();
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinValueA == currentCoinB.getCoinValue()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinValueA == currentCoinB.getCoinValue()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							if (coinValueA == currentCoinB.getCoinValue()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						default: break;
						}
					}
				}
				if (sameKindCoins.size >= minTotalCoins) return sameKindCoins;

				sameKindCoins.clear();
				sameKindCoins.add(currentCoinA);
				

				CoinColor coinColorA = currentCoinA.getTopCoinColor();
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinColorA == currentCoinB.getCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinColorA == currentCoinB.getTopCoinColor() || coinColorA == currentCoinB.getBottomCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							sameKindCoins.add(currentCoinB);
						}; break;
						default: break;
						}
					}
				}
				if (sameKindCoins.size >= minTotalCoins) return sameKindCoins;
				

				sameKindCoins.clear();
				sameKindCoins.add(currentCoinA);
				
				coinColorA = currentCoinA.getBottomCoinColor();
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinColorA == currentCoinB.getCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinColorA == currentCoinB.getTopCoinColor() || coinColorA == currentCoinB.getBottomCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							sameKindCoins.add(currentCoinB);
						}; break;
						default: break;
						}
					}
				}
				if (sameKindCoins.size >= minTotalCoins) return sameKindCoins;
			}; break;
			case THIRD: {
				CoinValue coinValueA = currentCoinA.getCoinValue();
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinValueA == currentCoinB.getCoinValue()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinValueA == currentCoinB.getCoinValue()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							if (coinValueA == currentCoinB.getCoinValue()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						default: break;
						}
					}
				}
				if (sameKindCoins.size >= minTotalCoins) return sameKindCoins;
				
				sameKindCoins.clear();
				sameKindCoins.add(currentCoinA);
				

				CoinColor coinColorA = CoinColor.GOLD;
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinColorA == currentCoinB.getCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinColorA == currentCoinB.getTopCoinColor() || coinColorA == currentCoinB.getBottomCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							sameKindCoins.add(currentCoinB);
						}; break;
						default: break;
						}
					}
				}
				if (sameKindCoins.size >= minTotalCoins) return sameKindCoins;
				
				sameKindCoins.clear();
				sameKindCoins.add(currentCoinA);
				

				coinColorA = CoinColor.SILVER;
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinColorA == currentCoinB.getCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinColorA == currentCoinB.getTopCoinColor() || coinColorA == currentCoinB.getBottomCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							sameKindCoins.add(currentCoinB);
						}; break;
						default: break;
						}
					}
				}
				if (sameKindCoins.size >= minTotalCoins) return sameKindCoins;
				
				sameKindCoins.clear();
				sameKindCoins.add(currentCoinA);
				

				coinColorA = CoinColor.BRONZE;
				for (int i2 = 0; i2 < coinCombo.size; i2++){
					Coin currentCoinB = coinCombo.get(i2);
					if (currentCoinA != currentCoinB){
						
						switch (currentCoinB.getCoinType()){
						case WHOLE: {
							if (coinColorA == currentCoinB.getCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case HALF: {
							if (coinColorA == currentCoinB.getTopCoinColor() || coinColorA == currentCoinB.getBottomCoinColor()){
								sameKindCoins.add(currentCoinB);
							}
						}; break;
						case THIRD: {
							sameKindCoins.add(currentCoinB);
						}; break;
						default: break;
						}
					}
				}
				if (sameKindCoins.size >= minTotalCoins) return sameKindCoins;
			}; break;
			default: break;
			}
			
			sameKindCoins.clear();
		}
		return sameKindCoins;
	}
}
