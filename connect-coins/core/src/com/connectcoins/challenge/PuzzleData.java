package com.connectcoins.challenge;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.connectcoins.coins.Coin;
import com.connectcoins.coins.CoinQueue;

public class PuzzleData {
	public Coin[] a1, a2, a3, a4, a5;
	public Coin[] b1, b2, b3, b4, b5;
	public Coin[] c1, c2, c3, c4, c5;
	public Coin[] d1, d2, d3, d4, d5;
	public Coin[] e1, e2, e3, e4, e5;
	public int puzzlePoints;
	
	public PuzzleData(){
		
	}
	
	public PuzzleData(int totalCoins){
		a1 = new Coin[totalCoins / 25];
		a2 = new Coin[totalCoins / 25];
		a3 = new Coin[totalCoins / 25];
		a4 = new Coin[totalCoins / 25];
		a5 = new Coin[totalCoins / 25];
		b1 = new Coin[totalCoins / 25];
		b2 = new Coin[totalCoins / 25];
		b3 = new Coin[totalCoins / 25];
		b4 = new Coin[totalCoins / 25];
		b5 = new Coin[totalCoins / 25];
		c1 = new Coin[totalCoins / 25];
		c2 = new Coin[totalCoins / 25];
		c3 = new Coin[totalCoins / 25];
		c4 = new Coin[totalCoins / 25];
		c5 = new Coin[totalCoins / 25];
		d1 = new Coin[totalCoins / 25];
		d2 = new Coin[totalCoins / 25];
		d3 = new Coin[totalCoins / 25];
		d4 = new Coin[totalCoins / 25];
		d5 = new Coin[totalCoins / 25];
		e1 = new Coin[totalCoins / 25];
		e2 = new Coin[totalCoins / 25];
		e3 = new Coin[totalCoins / 25];
		e4 = new Coin[totalCoins / 25];
		e5 = new Coin[totalCoins / 25];
		
		for (int i = 0; i < (totalCoins / 25); i++){
			a1[i] = null;
			a2[i] = null;
			a3[i] = null;
			a4[i] = null;
			a5[i] = null;
			b1[i] = null;
			b2[i] = null;
			b3[i] = null;
			b4[i] = null;
			b5[i] = null;
			c1[i] = null;
			c2[i] = null;
			c3[i] = null;
			c4[i] = null;
			c5[i] = null;
			d1[i] = null;
			d2[i] = null;
			d3[i] = null;
			d4[i] = null;
			d5[i] = null;
			e1[i] = null;
			e2[i] = null;
			e3[i] = null;
			e4[i] = null;
			e5[i] = null;
		}
	}
	
	/**
	 * Disable shiny coins if necessary.
	 * @param coins the complete combination, without the extras.
	 * @return extra coins that are not part of the combo.
	 */
	public int addCoinsRandomly(Array<Coin> coins, Array<String> slotIDs){
		int excessCoins = 0;
		while (slotIDs.size != 0){
			String slotID = slotIDs.random();
			if (coins.size == 0){
				Coin coin = CoinQueue.createRandomCoin(85, 10, 5);
				addCoinToSlot(coin, slotID);
				excessCoins++;
			}
			else {
				Coin selectedCoin = coins.pop();
				addCoinToSlot(selectedCoin, slotID);
			}
			slotIDs.removeValue(slotID, false);
		}
		return excessCoins;
	}
	
	private void addCoinToSlot(Coin coin, String slotID){
		Coin[] coins = null;
		switch (slotID){
		case "A1": coins = a1; break;
		case "A2": coins = a2; break;
		case "A3": coins = a3; break;
		case "A4": coins = a4; break;
		case "A5": coins = a5; break;
		case "B1": coins = b1; break;
		case "B2": coins = b2; break;
		case "B3": coins = b3; break;
		case "B4": coins = b4; break;
		case "B5": coins = b5; break;
		case "C1": coins = c1; break;
		case "C2": coins = c2; break;
		case "C3": coins = c3; break;
		case "C4": coins = c4; break;
		case "C5": coins = c5; break;
		case "D1": coins = d1; break;
		case "D2": coins = d2; break;
		case "D3": coins = d3; break;
		case "D4": coins = d4; break;
		case "D5": coins = d5; break;
		case "E1": coins = e1; break;
		case "E2": coins = e2; break;
		case "E3": coins = e3; break;
		case "E4": coins = e4; break;
		case "E5": coins = e5; break;
		default: break;
		}
		
		for (int i = 0; i < coins.length; i++){
			if (coins[i] == null){
				coins[i] = coin;
				
				switch (slotID){
				case "A1": a1 = coins; break;
				case "A2": a2 = coins; break;
				case "A3": a3 = coins; break;
				case "A4": a4 = coins; break;
				case "A5": a5 = coins; break;
				case "B1": b1 = coins; break;
				case "B2": b2 = coins; break;
				case "B3": b3 = coins; break;
				case "B4": b4 = coins; break;
				case "B5": b5 = coins; break;
				case "C1": c1 = coins; break;
				case "C2": c2 = coins; break;
				case "C3": c3 = coins; break;
				case "C4": c4 = coins; break;
				case "C5": c5 = coins; break;
				case "D1": d1 = coins; break;
				case "D2": d2 = coins; break;
				case "D3": d3 = coins; break;
				case "D4": d4 = coins; break;
				case "D5": d5 = coins; break;
				case "E1": e1 = coins; break;
				case "E2": e2 = coins; break;
				case "E3": e3 = coins; break;
				case "E4": e4 = coins; break;
				case "E5": e5 = coins; break;
				default: break;
				}
				
				return;
			}
		}
		
//		try {
//			throw new Exception();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.exit(0);
	}
	
	public int getRemainingSlots(){
		int remainingSlots = 0;
		
		for (int i = 0; i < a1.length; i++){
			if (a1[i] == null) remainingSlots++;
			if (a2[i] == null) remainingSlots++;
			if (a3[i] == null) remainingSlots++;
			if (a4[i] == null) remainingSlots++;
			if (a5[i] == null) remainingSlots++;
			if (b1[i] == null) remainingSlots++;
			if (b2[i] == null) remainingSlots++;
			if (b3[i] == null) remainingSlots++;
			if (b4[i] == null) remainingSlots++;
			if (b5[i] == null) remainingSlots++;
			if (c1[i] == null) remainingSlots++;
			if (c2[i] == null) remainingSlots++;
			if (c3[i] == null) remainingSlots++;
			if (c4[i] == null) remainingSlots++;
			if (c5[i] == null) remainingSlots++;
			if (d1[i] == null) remainingSlots++;
			if (d2[i] == null) remainingSlots++;
			if (d3[i] == null) remainingSlots++;
			if (d4[i] == null) remainingSlots++;
			if (d5[i] == null) remainingSlots++;
			if (e1[i] == null) remainingSlots++;
			if (e2[i] == null) remainingSlots++;
			if (e3[i] == null) remainingSlots++;
			if (e4[i] == null) remainingSlots++;
			if (e5[i] == null) remainingSlots++;
		}
		return remainingSlots;
	}
	
	public String selectRandomStartingPoint(){
		Array<String> startingPoints = new Array<String>();
		if (checkIfSlotNotFull(a1)) startingPoints.add("A1");
		if (checkIfSlotNotFull(a2)) startingPoints.add("A2");
		if (checkIfSlotNotFull(a3)) startingPoints.add("A3");
		if (checkIfSlotNotFull(a4)) startingPoints.add("A4");
		if (checkIfSlotNotFull(a5)) startingPoints.add("A5");
		if (checkIfSlotNotFull(b1)) startingPoints.add("B1");
		if (checkIfSlotNotFull(b2)) startingPoints.add("B2");
		if (checkIfSlotNotFull(b3)) startingPoints.add("B3");
		if (checkIfSlotNotFull(b4)) startingPoints.add("B4");
		if (checkIfSlotNotFull(b5)) startingPoints.add("B5");
		if (checkIfSlotNotFull(c1)) startingPoints.add("C1");
		if (checkIfSlotNotFull(c2)) startingPoints.add("C2");
		if (checkIfSlotNotFull(c3)) startingPoints.add("C3");
		if (checkIfSlotNotFull(c4)) startingPoints.add("C4");
		if (checkIfSlotNotFull(c5)) startingPoints.add("C5");
		if (checkIfSlotNotFull(d1)) startingPoints.add("D1");
		if (checkIfSlotNotFull(d2)) startingPoints.add("D2");
		if (checkIfSlotNotFull(d3)) startingPoints.add("D3");
		if (checkIfSlotNotFull(d4)) startingPoints.add("D4");
		if (checkIfSlotNotFull(d5)) startingPoints.add("D5");
		if (checkIfSlotNotFull(e1)) startingPoints.add("E1");
		if (checkIfSlotNotFull(e2)) startingPoints.add("E2");
		if (checkIfSlotNotFull(e3)) startingPoints.add("E3");
		if (checkIfSlotNotFull(e4)) startingPoints.add("E4");
		if (checkIfSlotNotFull(e5)) startingPoints.add("E5");
		return startingPoints.random();
	}

	public boolean checkIfSlotNotFull(String slotID){
		Coin[] coinSlot = null;
		switch (slotID){
		case "A1": coinSlot = a1; break;
		case "A2": coinSlot = a2; break;
		case "A3": coinSlot = a3; break;
		case "A4": coinSlot = a4; break;
		case "A5": coinSlot = a5; break;
		case "B1": coinSlot = b1; break;
		case "B2": coinSlot = b2; break;
		case "B3": coinSlot = b3; break;
		case "B4": coinSlot = b4; break;
		case "B5": coinSlot = b5; break;
		case "C1": coinSlot = c1; break;
		case "C2": coinSlot = c2; break;
		case "C3": coinSlot = c3; break;
		case "C4": coinSlot = c4; break;
		case "C5": coinSlot = c5; break;
		case "D1": coinSlot = d1; break;
		case "D2": coinSlot = d2; break;
		case "D3": coinSlot = d3; break;
		case "D4": coinSlot = d4; break;
		case "D5": coinSlot = d5; break;
		case "E1": coinSlot = e1; break;
		case "E2": coinSlot = e2; break;
		case "E3": coinSlot = e3; break;
		case "E4": coinSlot = e4; break;
		case "E5": coinSlot = e5; break;
		default: break;
		}
		for (int i = 0; i < coinSlot.length; i++){
			if (coinSlot[i] == null) return true;
		}
		return false;
	}
	
	private boolean checkIfSlotNotFull(Coin[] coinSlot){
		for (int i = 0; i < coinSlot.length; i++){
			if (coinSlot[i] == null) return true;
		}
		return false;
	}
	
	/**
	 * @param fillAllSlots if false, null slots are randomly filled.
	 */
	public void fillEmptySlots(boolean fillAllSlots){
		for (int i = 0; i < a1.length; i++){
			if (a1[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) a1[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (a2[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) a2[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (a3[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) a3[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (a4[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) a4[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (a5[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) a5[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (b1[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) b1[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (b2[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) b2[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (b3[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) b3[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (b4[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) b4[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (b5[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) b5[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (c1[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) c1[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (c2[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) c2[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (c3[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) c3[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (c4[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) c4[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (c5[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) c5[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (d1[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) d1[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (d2[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) d2[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (d3[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) d3[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (d4[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) d4[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (d5[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) d5[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (e1[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) e1[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (e2[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) e2[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (e3[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) e3[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (e4[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) e4[i] = CoinQueue.createRandomCoin(85, 10, 5);
			if (e5[i] == null && (MathUtils.randomBoolean() || fillAllSlots)) e5[i] = CoinQueue.createRandomCoin(85, 10, 5);
		}
	}
}
