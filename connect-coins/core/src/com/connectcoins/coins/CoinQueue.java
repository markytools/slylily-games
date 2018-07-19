package com.connectcoins.coins;

import java.util.HashMap;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.connectcoins.coins.Coin.CoinColor;
import com.connectcoins.coins.Coin.CoinType;
import com.connectcoins.coins.Coin.CoinValue;
import com.connectcoins.functions.GameModeConfig;
//import com.connectcoins.multiplayerUtils.CoinData;
//import com.connectcoins.multiplayerUtils.MultiplayerCoins;
import com.connectcoins.slots.Slot;

public class CoinQueue {
	private static float shiningCoinChance = 10f;

	public CoinQueue(){
		reset();
	}

	public static CoinType getRandomCoinType(float wholeCoinChance, float halfCoinChance, float thirdCoinChance){
		float randCoinType = MathUtils.random(1, 100);
		float wholeCoinRange = 0;
		float halfCoinRange = wholeCoinChance;
		float thirdCoinRange = wholeCoinChance + halfCoinChance;

		if (randCoinType > wholeCoinRange && randCoinType <= halfCoinRange){
			return CoinType.WHOLE;
		}
		else if  (randCoinType > halfCoinRange && randCoinType <= thirdCoinRange){
			return CoinType.HALF;
		}
		else return CoinType.THIRD;
	}

	public static CoinType getRandomCoinType(){
		return CoinType.values()[(int) (Math.random() * CoinType.values().length)];
	}

	public static CoinValue getRandomCoinValue(){
		return CoinValue.values()[(int) (Math.random() * CoinValue.values().length)];
	}

	public static CoinColor getRandomCoinColor(){
		return CoinColor.values()[(int) (Math.random() * CoinColor.values().length)];
	}

	public void initCoins(Array<Slot> slots){
		for (Slot slot : slots){
			int i = 0;
			Array<Coin> coinQueue = new Array<Coin>();
			while (i < 5){
				Coin coin = createRandomCoin(85, 10, 5);
				coin.setCoinID(slot.getsPos().slotPosID);

				float isShining = MathUtils.random(1, 100);
				if (isShining >= 1 && isShining <= shiningCoinChance) coin.setShining(true);

				coinQueue.add(coin);
				i++;
			}
			slot.setCoinQueue(coinQueue);
		}
	}

	public void initCoinsForMultiplayer(Array<Slot> slots){
		HashMap<String, Array<Coin>> coinQueueMap = new HashMap<String, Array<Coin>>();

		String col = "A";
		int row = 1;

		for (int i = 0; i < 5; i++){
			for (int i2 = 0; i2 < 5; i2++){
				String location = col + row;
				coinQueueMap.put(location, new Array<Coin>());
				row++;
			}
			col = String.valueOf( (char) (col.charAt(0) + 1));
			row = 1;
		}

//		while (MultiplayerCoins.getInstance().hasCoinData()){
//			HashMap<String, CoinData> cData = MultiplayerCoins.getInstance().retrieveFirstCData();
//
//			col = "A";
//			row = 1;
//
//			for (int i = 0; i < 5; i++){
//				for (int i2 = 0; i2 < 5; i2++){
//					String location = col + row;
//					CoinData currentCData = cData.get(location);
//					Coin coin = null;
//
//					switch (currentCData.getCoinType()){
//					case WHOLE: {
//						CoinValue coinValue = currentCData.getCoinValue();
//						CoinColor coinColor = currentCData.getCoinColor();
//
//						coin = new Coin(null);
//						coin.setWholeCoin(coinValue, coinColor);
//					}; break;
//					case THIRD: {
//						CoinValue coinValue = currentCData.getCoinValue();
//
//						coin = new Coin(null);
//						coin.setThirdCoin(coinValue);
//					}; break;
//					case HALF: {
//						CoinValue coinValue = currentCData.getCoinValue();
//						CoinColor coinColor1, coinColor2;
//						coinColor1 = currentCData.getCoinColor();
//						coinColor2 = currentCData.getBottomColor();
//
//						coin = new Coin(null);
//						coin.setHalfCoin(coinValue, coinColor1, coinColor2);
//					}; break;
//					default: break;
//					}
//					if (currentCData.isShining()) coin.setShining(true);
//					coin.setCoinID(location);
//					coinQueueMap.get(location).add(coin);
//					row++;
//				}
//				col = String.valueOf( (char) (col.charAt(0) + 1));
//				row = 1;
//			}
//		}

		for (Slot slot : slots){
			String slotID = slot.getsPos().slotPosID;
			slot.setCoinQueue(coinQueueMap.get(slotID));
		}
	}

	public void recreateCoin(Slot slot, int num){
		Array<Coin> coinQueue = slot.getCoinQueue();
		removeCoins(slot, num);
		while (coinQueue.size < 5){
			Coin coin = createRandomCoin(85, 10, 5);
			coin.setCoinID(slot.getsPos().slotPosID);

			float isShining = MathUtils.random(1f, 100f);
			if (isShining >= 1 && isShining <= shiningCoinChance) coin.setShining(true);

			coinQueue.add(coin);
		}
	}
	
	public void recreateCoinForMultiplayer(Slot slot, int num, boolean isHost){
		Array<Coin> coinQueue = slot.getCoinQueue();
		removeCoins(slot, num);
		if (coinQueue.size < 5){
//			if (!WarpController.getInstance().isNewCoinLayerRequestSent()){
//				WarpController.getInstance().setNewCoinLayerRequestSent(true);
//				WarpController.getInstance().requestNewCoinDataLayer(isHost);
//			}
		}
	}

	public void removeCoins(Slot slot, int num){
		Array<Coin> coinQueue = slot.getCoinQueue();
		for (int i = 0; i < num; i++){
			coinQueue.removeIndex(0);
		}
		coinQueue.shrink();
	}

	private static Coin createRandomCoin(CoinType coinType){
		Coin coin = null;

		switch (coinType){
		case WHOLE: {
			CoinValue coinValue = getRandomCoinValue();
			CoinColor coinColor = getRandomCoinColor();

			coin = new Coin(null);
			coin.setWholeCoin(coinValue, coinColor);
		}; break;
		case THIRD: {
			CoinValue coinValue = getRandomCoinValue();

			coin = new Coin(null);
			coin.setThirdCoin(coinValue);
		}; break;
		case HALF: {
			CoinValue coinValue = getRandomCoinValue();
			CoinColor coinColor1, coinColor2;
			coinColor1 = getRandomCoinColor();
			do {
				coinColor2 = getRandomCoinColor();
			}
			while (coinColor1.equals(coinColor2));

			coin = new Coin(null);
			coin.setHalfCoin(coinValue, coinColor1, coinColor2);
		}; break;
		default: break;
		}

		return coin;
	}

	/**
	 * wholeCoinChance, halfCoinChance, and thirdCoinChance must add up to 100
	 */
	public static Coin createRandomCoin(float wholeCoinChance, float halfCoinChance, float thirdCoinChance){
		reset();
		float finalHalfCoinChance = (GameModeConfig.LUCKY_HALF) ? halfCoinChance * 2 : halfCoinChance;
		float finalThirdCoinChance = (GameModeConfig.LUCKY_THIRD) ? thirdCoinChance * 2 : thirdCoinChance;
		float finalWholeCoinChance = 100 - finalHalfCoinChance - finalThirdCoinChance;

		float randCoinType = MathUtils.random(1, 100);
		float wholeCoinRange = 0;
		float halfCoinRange = finalWholeCoinChance;
		float thirdCoinRange = finalWholeCoinChance + finalHalfCoinChance;

		if (randCoinType > wholeCoinRange && randCoinType <= halfCoinRange){
			return createRandomCoin(CoinType.WHOLE);
		}
		else if  (randCoinType > halfCoinRange && randCoinType <= thirdCoinRange){
			return createRandomCoin(CoinType.HALF);
		}
		else return createRandomCoin(CoinType.THIRD);
	}
	
//	public static CoinData createRandomCoinData(float wholeCoinChance, float halfCoinChance, float thirdCoinChance){
//		reset();
//		float finalHalfCoinChance = (GameModeConfig.LUCKY_HALF) ? halfCoinChance * 2 : halfCoinChance;
//		float finalThirdCoinChance = (GameModeConfig.LUCKY_THIRD) ? thirdCoinChance * 2 : thirdCoinChance;
//		float finalWholeCoinChance = 100 - finalHalfCoinChance - finalThirdCoinChance;
//
//		float randCoinType = MathUtils.random(1, 100);
//		float wholeCoinRange = 0;
//		float halfCoinRange = finalWholeCoinChance;
//		float thirdCoinRange = finalWholeCoinChance + finalHalfCoinChance;
//
//		if (randCoinType > wholeCoinRange && randCoinType <= halfCoinRange){
//			return createRandomCoinData(CoinType.WHOLE);
//		}
//		else if  (randCoinType > halfCoinRange && randCoinType <= thirdCoinRange){
//			return createRandomCoinData(CoinType.HALF);
//		}
//		else return createRandomCoinData(CoinType.THIRD);
//	}
	
//	public static CoinData createRandomCoinData(CoinType coinType){
//		CoinData cData = null;
//
//		switch (coinType){
//		case WHOLE: {
//			CoinValue coinValue = getRandomCoinValue();
//			CoinColor coinColor = getRandomCoinColor();
//
//			cData = new CoinData(coinType, coinValue, coinColor, null, false);
//		}; break;
//		case THIRD: {
//			CoinValue coinValue = getRandomCoinValue();
//
//			cData = new CoinData(coinType, coinValue, null, null, false);
//		}; break;
//		case HALF: {
//			CoinValue coinValue = getRandomCoinValue();
//			CoinColor coinColor1, coinColor2;
//			coinColor1 = getRandomCoinColor();
//			do {
//				coinColor2 = getRandomCoinColor();
//			}
//			while (coinColor1.equals(coinColor2));
//
//			cData = new CoinData(coinType, coinValue, coinColor1, coinColor2, false);
//		}; break;
//		default: break;
//		}

//		float isShining = MathUtils.random(1, 100);
//		if (isShining >= 1 && isShining <= shiningCoinChance) cData.setShining(true);
//		else cData.setShining(false);
//		
//		return cData;
//	}

	public static void reset(){
		if (GameModeConfig.SHINE_ALL_THE_WAY) shiningCoinChance = 20f;
		else shiningCoinChance = 10f;
	}
}