package com.connectcoins.connection;

import java.util.HashMap;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.coins.Coin;
import com.connectcoins.coins.Coin.CoinColor;
import com.connectcoins.coins.Coin.CoinType;
import com.connectcoins.coins.Coin.CoinValue;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.slots.Slot;
import com.connectcoins.slots.SlotManager;

public class CoinAutoConnector implements Disposable {
	private SlotManager sManager;
	private HashMap<String, Integer> maxRanges;
	
	enum AutoMatchType {
		FLUSH, SAME_COLOR, SAME_VALUE
	}

	public CoinAutoConnector(ConnectCoins game, SlotManager sManager){
		this.sManager = sManager;
		maxRanges = new HashMap<String, Integer>();
		maxRanges.put("A1", 4);
		maxRanges.put("A2", 4);
		maxRanges.put("A3", 4);
		maxRanges.put("A4", 4);
		maxRanges.put("A5", 4);
		maxRanges.put("B1", 4);
		maxRanges.put("B5", 4);
		maxRanges.put("C1", 4);
		maxRanges.put("C5", 4);
		maxRanges.put("D1", 4);
		maxRanges.put("D5", 4);
		maxRanges.put("E1", 4);
		maxRanges.put("E2", 4);
		maxRanges.put("E3", 4);
		maxRanges.put("E4", 4);
		maxRanges.put("E5", 4);
		maxRanges.put("B2", 3);
		maxRanges.put("B3", 3);
		maxRanges.put("B4", 3);
		maxRanges.put("C2", 3);
		maxRanges.put("C4", 3);
		maxRanges.put("D2", 3);
		maxRanges.put("D3", 3);
		maxRanges.put("D4", 3);
		maxRanges.put("C3", 2);
	}

	public Array<Slot> autoConnect(Slot slot){
		Array<Slot> connectedSlots = null;
		Coin currentCoin = slot.getCurrentCoin();

		switch (currentCoin.getCoinType()){
		case WHOLE: {
			connectedSlots = autoConnectFlushes(AutoMatchType.FLUSH, slot, currentCoin.getCoinValue(), currentCoin.getCoinColor());
		}; break;
		case HALF: {
			Array<Slot> connectedSlots1 = autoConnectFlushes(AutoMatchType.FLUSH, slot, currentCoin.getCoinValue(), currentCoin.getBottomCoinColor());
			Array<Slot> connectedSlots2 = autoConnectFlushes(AutoMatchType.FLUSH, slot, currentCoin.getCoinValue(), currentCoin.getTopCoinColor());
			if (connectedSlots1.size > connectedSlots2.size) connectedSlots = connectedSlots1;
			else if (connectedSlots2.size > connectedSlots1.size) connectedSlots = connectedSlots2;
			else {
				if (MathUtils.randomBoolean()) connectedSlots = connectedSlots1;
				else connectedSlots = connectedSlots2;
			}
		}; break;
		case THIRD: {
			Array<Slot> connectedSlots1 = autoConnectFlushes(AutoMatchType.FLUSH, slot, currentCoin.getCoinValue(), CoinColor.BRONZE);
			Array<Slot> connectedSlots2 = autoConnectFlushes(AutoMatchType.FLUSH, slot, currentCoin.getCoinValue(), CoinColor.SILVER);
			Array<Slot> connectedSlots3 = autoConnectFlushes(AutoMatchType.FLUSH, slot, currentCoin.getCoinValue(), CoinColor.GOLD);
			Array<Array<Slot>> temp = new Array<Array<Slot>>();
			int max = max(connectedSlots1.size, connectedSlots2.size, connectedSlots3.size);
			if (connectedSlots1.size == max) temp.add(connectedSlots1);
			if (connectedSlots2.size == max) temp.add(connectedSlots2);
			if (connectedSlots3.size == max) temp.add(connectedSlots3);
			connectedSlots = temp.random();
		}; break;
		default: break;
		}
		if (connectedSlots.size >= 3) return connectedSlots;
		
		connectedSlots.clear();

		switch (currentCoin.getCoinType()){
		case WHOLE: {
			connectedSlots = autoConnectFlushes(AutoMatchType.SAME_COLOR, slot, currentCoin.getCoinValue(), currentCoin.getCoinColor());
		}; break;
		case HALF: {
			Array<Slot> connectedSlots1 = autoConnectFlushes(AutoMatchType.SAME_COLOR, slot, currentCoin.getCoinValue(), currentCoin.getBottomCoinColor());
			Array<Slot> connectedSlots2 = autoConnectFlushes(AutoMatchType.SAME_COLOR, slot, currentCoin.getCoinValue(), currentCoin.getTopCoinColor());
			if (connectedSlots1.size > connectedSlots2.size) connectedSlots = connectedSlots1;
			else if (connectedSlots2.size > connectedSlots1.size) connectedSlots = connectedSlots2;
			else {
				if (MathUtils.randomBoolean()) connectedSlots = connectedSlots1;
				else connectedSlots = connectedSlots2;
			}
		}; break;
		case THIRD: {
			Array<Slot> connectedSlots1 = autoConnectFlushes(AutoMatchType.SAME_COLOR, slot, currentCoin.getCoinValue(), CoinColor.BRONZE);
			Array<Slot> connectedSlots2 = autoConnectFlushes(AutoMatchType.SAME_COLOR, slot, currentCoin.getCoinValue(), CoinColor.SILVER);
			Array<Slot> connectedSlots3 = autoConnectFlushes(AutoMatchType.SAME_COLOR, slot, currentCoin.getCoinValue(), CoinColor.GOLD);
			Array<Array<Slot>> temp = new Array<Array<Slot>>();
			int max = max(connectedSlots1.size, connectedSlots2.size, connectedSlots3.size);
			if (connectedSlots1.size == max) temp.add(connectedSlots1);
			if (connectedSlots2.size == max) temp.add(connectedSlots2);
			if (connectedSlots3.size == max) temp.add(connectedSlots3);
			connectedSlots = temp.random();
		}; break;
		default: break;
		}
		if (connectedSlots.size >= 3) return connectedSlots;

		connectedSlots.clear();

		switch (currentCoin.getCoinType()){
		case WHOLE: {
			connectedSlots = autoConnectFlushes(AutoMatchType.SAME_VALUE, slot, currentCoin.getCoinValue(), currentCoin.getCoinColor());
		}; break;
		case HALF: {
			Array<Slot> connectedSlots1 = autoConnectFlushes(AutoMatchType.SAME_VALUE, slot, currentCoin.getCoinValue(), currentCoin.getBottomCoinColor());
			Array<Slot> connectedSlots2 = autoConnectFlushes(AutoMatchType.SAME_VALUE, slot, currentCoin.getCoinValue(), currentCoin.getTopCoinColor());
			if (connectedSlots1.size > connectedSlots2.size) connectedSlots = connectedSlots1;
			else if (connectedSlots2.size > connectedSlots1.size) connectedSlots = connectedSlots2;
			else {
				if (MathUtils.randomBoolean()) connectedSlots = connectedSlots1;
				else connectedSlots = connectedSlots2;
			}
		}; break;
		case THIRD: {
			Array<Slot> connectedSlots1 = autoConnectFlushes(AutoMatchType.SAME_VALUE, slot, currentCoin.getCoinValue(), CoinColor.BRONZE);
			Array<Slot> connectedSlots2 = autoConnectFlushes(AutoMatchType.SAME_VALUE, slot, currentCoin.getCoinValue(), CoinColor.SILVER);
			Array<Slot> connectedSlots3 = autoConnectFlushes(AutoMatchType.SAME_VALUE, slot, currentCoin.getCoinValue(), CoinColor.GOLD);
			Array<Array<Slot>> temp = new Array<Array<Slot>>();
			int max = max(connectedSlots1.size, connectedSlots2.size, connectedSlots3.size);
			if (connectedSlots1.size == max) temp.add(connectedSlots1);
			if (connectedSlots2.size == max) temp.add(connectedSlots2);
			if (connectedSlots3.size == max) temp.add(connectedSlots3);
			connectedSlots = temp.random();
		}; break;
		default: break;
		}
		if (connectedSlots.size >= 3) return connectedSlots;

		connectedSlots.clear();
		connectedSlots.add(slot);
		return connectedSlots;
	}

	private Array<Slot> autoConnectFlushes(AutoMatchType matchType, Slot slot, CoinValue value, CoinColor color){
		Array<Slot> connectedSlots = new Array<Slot>();
		Array<Slot> queueForProp = new Array<Slot>();

		Coin coin = new Coin(CoinType.WHOLE, value, color);
		queueForProp.add(slot);
		connectedSlots.add(slot);

		int maxCounts = 5;
//		Array<Integer> indexToRemove = new Array<Integer>();

		outerloopA:
			while (queueForProp.size != 0){
				int maxConnectionRange = maxCounts - connectedSlots.size;
				if (maxConnectionRange <= 0) break outerloopA;
				
				outerloopB:
				for (int i = 1; i <= maxConnectionRange; i++){
//					for (Integer integer : indexToRemove) queueForProp.removeIndex(integer);
					for (int i2 = 0; i2 < queueForProp.size; i2++){
						Slot slotToScan = queueForProp.get(i2);
						String slotID = slotToScan.getsPos().slotPosID;
						int maxRange = maxRanges.get(slotID);
						if (i <= maxRange){

							Array<String> inRangeSlotIDs = SlotManager.getNearbySlotsByRange(slotID, i);
							for (String id : inRangeSlotIDs){
								Slot iRSlot = sManager.getSlotByID(id);
								Coin iRCoin = iRSlot.getCurrentCoin();
								
								switch (matchType){
								case FLUSH: {
									if (isMatchedCoin(coin, iRCoin)){
										if (!connectedSlots.contains(iRSlot, true)){
											maxCounts -= i - 1;
											if (i != 1){
												Array<Slot> connectionOfTwoSlots = getConnectionOfTwoSlots(slotToScan, iRSlot);
												while (connectionOfTwoSlots.size != 0){
													for (Slot midSlot : connectionOfTwoSlots){
														if  (!connectedSlots.contains(midSlot, true)){
															System.out.println("Mid slot added");
															connectedSlots.add(midSlot);
															queueForProp.add(midSlot);
														}
														connectionOfTwoSlots.removeValue(midSlot, true);
													}
												}
											}
											connectedSlots.add(iRSlot);
											queueForProp.add(iRSlot);
											if (connectedSlots.size >= 5) break outerloopA;
											break outerloopB;
										}
									}
								}; break;
								case SAME_COLOR: {
									if (isMatchedColor(coin, iRCoin)){
										if (!connectedSlots.contains(iRSlot, true)){
											maxCounts -= i - 1;
											if (i != 1){
												Array<Slot> connectionOfTwoSlots = getConnectionOfTwoSlots(slotToScan, iRSlot);
												while (connectionOfTwoSlots.size != 0){
													for (Slot midSlot : connectionOfTwoSlots){
														if  (!connectedSlots.contains(midSlot, true)){
															System.out.println("Mid slot added");
															connectedSlots.add(midSlot);
															queueForProp.add(midSlot);
														}
														connectionOfTwoSlots.removeValue(midSlot, true);
													}
												}
											}
											connectedSlots.add(iRSlot);
											queueForProp.add(iRSlot);
											if (connectedSlots.size >= 5) break outerloopA;
											break outerloopB;
										}
									}
								}; break;
								case SAME_VALUE: {
									if (isMatchedValue(coin, iRCoin)){
										if (!connectedSlots.contains(iRSlot, true)){
											maxCounts -= i - 1;
											if (i != 1){
												Array<Slot> connectionOfTwoSlots = getConnectionOfTwoSlots(slotToScan, iRSlot);
												while (connectionOfTwoSlots.size != 0){
													for (Slot midSlot : connectionOfTwoSlots){
														if  (!connectedSlots.contains(midSlot, true)){
															System.out.println("Mid slot added");
															connectedSlots.add(midSlot);
															queueForProp.add(midSlot);
														}
														connectionOfTwoSlots.removeValue(midSlot, true);
													}
												}
											}
											connectedSlots.add(iRSlot);
											queueForProp.add(iRSlot);
											if (connectedSlots.size >= 5) break outerloopA;
											break outerloopB;
										}
									}
								}; break;
								default: break;
								}
							}
							if (i2 == queueForProp.size - 1 && i == maxConnectionRange) queueForProp.pop();
						}
						else {
							queueForProp.removeIndex(i2);
							break outerloopB;
						}
					}
				}
				queueForProp.shrink();
			}

		System.out.println("Connected Slot Size: " + connectedSlots.size);
		for (Slot cSlot : connectedSlots){
			System.out.println("Connected Slot ID: " + cSlot.getsPos().slotPosID);
		}
		return connectedSlots;
	}

	private Array<Slot> getConnectionOfTwoSlots(Slot slotToScan, Slot iRSlot) {
		Slot currentSlot = slotToScan;
		Array<Slot> connectionOfTwoSlots = new Array<Slot>();
		
		while (true){
			char slotToScanCol = currentSlot.getsPos().slotPosID.charAt(0);
			int slotToScanRow = Integer.parseInt("" + currentSlot.getsPos().slotPosID.charAt(1), 36);
			char iRSlotCol = iRSlot.getsPos().slotPosID.charAt(0);
			int iRSlotRow = Integer.parseInt("" + iRSlot.getsPos().slotPosID.charAt(1), 36);
			
			if (slotToScanCol == iRSlotCol){
				
			}
			else if (slotToScanCol < iRSlotCol){
				slotToScanCol++;
			}
			else if (slotToScanCol > iRSlotCol){
				slotToScanCol--;
			}
			
			if (slotToScanRow == iRSlotRow){
				
			}
			else if (slotToScanRow < iRSlotRow){
				slotToScanRow++;
			}
			else if (slotToScanRow > iRSlotRow){
				slotToScanRow--;
			}
			currentSlot = sManager.getSlotByID(String.valueOf(slotToScanCol) + String.valueOf(slotToScanRow));
			if (currentSlot == iRSlot) break;
			connectionOfTwoSlots.add(currentSlot);
		}
		return connectionOfTwoSlots;
	}

	static int max(int... a){
		int max = 0;
		for (int i = 0; i < a.length; i++){
			if(a[i] >= a[max]) max=i;
		}
		return (a[max]);
	}

	public static boolean isMatchedCoin(Coin coin1, Coin coin2){
		return (isMatchedColor(coin1, coin2) && isMatchedValue(coin1, coin2));
	}

	public static boolean isMatchedColor(Coin coin1, Coin coin2){
		switch (coin1.getCoinType()){
		case WHOLE: {
			switch (coin2.getCoinType()){
			case WHOLE: {
				if (coin1.getCoinColor() == coin2.getCoinColor()) return true;
			}; break;
			case HALF: {
				if (coin1.getCoinColor() == coin2.getBottomCoinColor() ||
						coin1.getCoinColor() == coin2.getTopCoinColor()) return true;
			}; break;
			case THIRD: {
				return true;
			} 
			default: break;
			}
		}; break;
		case HALF: {
			switch (coin2.getCoinType()){
			case WHOLE: {
				if (coin2.getCoinColor() == coin1.getBottomCoinColor() ||
						coin2.getCoinColor() == coin1.getTopCoinColor()) return true;
			}; break;
			case HALF: {
				if (coin1.getBottomCoinColor() == coin2.getBottomCoinColor() ||
						coin1.getBottomCoinColor() == coin2.getTopCoinColor() ||
						coin1.getTopCoinColor() == coin2.getBottomCoinColor() ||
						coin1.getTopCoinColor() == coin2.getTopCoinColor()) return true;
			}; break;
			case THIRD: {
				return true;
			} 
			default: break;
			}
		}; break;
		case THIRD: {
			return true;
		} 
		default: break;
		}
		return false;
	}

	public static boolean isMatchedValue(Coin coin1, Coin coin2){
		if (coin1.getCoinValue() == coin2.getCoinValue()) return true;
		else return false;
	}

	@Override
	public void dispose() {
		sManager = null;
		if (maxRanges != null) maxRanges.clear();
		maxRanges = null;
	}
}