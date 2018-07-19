package com.connectcoins.slots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.challenge.ChallengeConnectionData;
import com.connectcoins.challenge.PuzzleData;
import com.connectcoins.coins.Coin;
import com.connectcoins.coins.CoinAssets;
import com.connectcoins.coins.CoinQueue;
import com.connectcoins.connection.CoinAutoConnector;
import com.connectcoins.game.GameScreen;
//import com.connectcoins.multiplayerUtils.CoinData;
//import com.connectcoins.multiplayerUtils.MultiplayerCoins;
import com.connectcoins.slots.SlotAssets.Theme;

public class SlotManager implements Disposable {
	public static final float[] slotColPos = {SlotPosition.slotPosA, SlotPosition.slotPosB, SlotPosition.slotPosC, SlotPosition.slotPosD,
		SlotPosition.slotPosE};
	public static  final float[] slotRowPos = {SlotPosition.slotPos1, SlotPosition.slotPos2, SlotPosition.slotPos3, SlotPosition.slotPos4,
		SlotPosition.slotPos5};

	private GameScreen gScreen;
	private CoinAssets coinAssets;
	private SlotAssets slotAssets;
	private LightAssets lightAssets;
	private CoinQueue coinQueue;
	private CoinAutoConnector cAutoConnector;
	private ChallengeConnectionData challengeCData;

	private Array<Slot> slots;

	public SlotManager(GameScreen gScreen){
		this.gScreen = gScreen;

		createSlotAssets();
		createSlots();
		initCoinManager();
		initCoins();
		setTestConnected();
	}

	//	Test Only!!!
	private void setTestConnected() {

	}

	private void createSlotAssets() {
		slotAssets = new SlotAssets(gScreen.game);
		slotAssets.setSlotAssets(Theme.NORMAL);

		lightAssets = new LightAssets(gScreen.game);
		lightAssets.setLightsAssets(Theme.NORMAL);
		cAutoConnector = new CoinAutoConnector(gScreen.game, this);
	}

	private void createSlots() {
		slots = new Array<Slot>();

		char col = 'A';
		int row = 1;
		for (int i = 0; i < slotColPos.length; i++){
			for (int i2 = 0; i2 < slotRowPos.length; i2++){
				slots.add(new Slot(new SlotPosition(String.valueOf(col) + String.valueOf(row),
						slotColPos[i], slotRowPos[i2])));
				row++;
			}
			col = (char)((int)(col) + 1);
			row = 1;
		}
	}
	
	public void enableAllSlots(boolean enable){
		if (enable) for (Slot slot : slots) slot.setDisabled(false);
		else for (Slot slot : slots) slot.setDisabled(true);
	}

	public Slot getSlotByID(String slotID){
		for (Slot slot : slots){
			if (slot.getsPos().slotPosID.equals(slotID)) return slot;
		}
		return null;
	}

	private void initCoinManager() {
		coinAssets = new CoinAssets(gScreen.game);
		coinAssets.setCoinAssets(gScreen.game.pUpdater.getCurrentCoinTexture());
		challengeCData = new ChallengeConnectionData(gScreen.game, this);
	}

	private void initCoins() {
		coinQueue = new CoinQueue();
		switch (gScreen.game.gMConfig.mode){
		case NORMAL: {
			coinQueue.initCoins(slots);
		}; break;
		case CHALLENGE: {
			challengeCData.activateChallengeCData(true);
		}; break;
		case TUTORIAL: {
			coinQueue.initCoins(slots);
		}; break;
		default: break;
		}
	}

	public void render(SpriteBatch batch){
		batch.draw(SlotAssets.slotsImg, 0, 162, 1080, 1080);
		for (int i = slots.size - 1; i >= 0; i--){
			Slot slot = slots.get(i);
			slot.isOnFire = true;
			slot.render(batch);
		}
	}

	public CoinQueue getCoinQueue() {
		return coinQueue;
	}

	public static float getSlotX(String slotID){
		if (slotID.contains("A")) return slotColPos[0];
		if (slotID.contains("B")) return slotColPos[1];
		if (slotID.contains("C")) return slotColPos[2];
		if (slotID.contains("D")) return slotColPos[3];
		if (slotID.contains("E")) return slotColPos[4];
		return -1;
	}

	public static float getSlotY(String slotID){
		if (slotID.contains("1")) return slotRowPos[0];
		if (slotID.contains("2")) return slotRowPos[1];
		if (slotID.contains("3")) return slotRowPos[2];
		if (slotID.contains("4")) return slotRowPos[3];
		if (slotID.contains("5")) return slotRowPos[4];
		return -1;
	}

	/**
	 * 
	 * @param slotID the slot to evaluate.
	 * @return Array containing strings of adjacent slots.
	 */
	public static Array<String> getAdjacentSlotIDs(String slotID){
		Array<String> adjacentSlotIDs = new Array<String>();
		String colID = slotID.substring(0, 1);
		int rowID = Integer.parseInt(slotID.substring(1, 2));
		char colChar = colID.charAt(0);
		adjacentSlotIDs.add(String.valueOf((char)(colChar - 1)) + rowID);
		adjacentSlotIDs.add(String.valueOf((char)(colChar - 1)) + String.valueOf(rowID - 1));
		adjacentSlotIDs.add(String.valueOf((char)(colChar - 1)) + String.valueOf(rowID + 1));
		adjacentSlotIDs.add(String.valueOf(colChar) + String.valueOf(rowID - 1));
		adjacentSlotIDs.add(String.valueOf(colChar) + String.valueOf(rowID + 1));
		adjacentSlotIDs.add(String.valueOf((char)(colChar + 1)) + rowID);
		adjacentSlotIDs.add(String.valueOf((char)(colChar + 1)) + String.valueOf(rowID - 1));
		adjacentSlotIDs.add(String.valueOf((char)(colChar + 1)) + String.valueOf(rowID + 1));

		for (int i = 0; i < adjacentSlotIDs.size; i++){
			String adSlotID = adjacentSlotIDs.get(i);
			String adSlotColID = adSlotID.substring(0, 1);
			String adSlotRowID = adSlotID.substring(1, 2);
			if (!((adSlotColID.equals("A") || adSlotColID.equals("B") || adSlotColID.equals("C") || adSlotColID.equals("D") || adSlotColID.equals("E")) &&
					(adSlotRowID.equals("1") || adSlotRowID.equals("2") || adSlotRowID.equals("3") || adSlotRowID.equals("4") || adSlotRowID.equals("5")))){
				adjacentSlotIDs.set(i, null);
			}
		}
		while (adjacentSlotIDs.contains(null, false)) adjacentSlotIDs.removeValue(null, false);
		adjacentSlotIDs.shrink();
		return adjacentSlotIDs;
	}

	/**
	 *  
	 * @param slotID id of the slot
	 * @param range the radius of the square.
	 * @return
	 */
	public static Array<String> getNearbySlotsByRange(String slotID, int range){
		Array<String> adjacentSlotIDs = new Array<String>();

		String colID = slotID.substring(0, 1);
		int rowID = Integer.parseInt(slotID.substring(1, 2));

		String upperLeftCol = String.valueOf((char)(colID.charAt(0) - range));
		String upperLeftRow = String.valueOf(rowID - range);
		String lowerRightCol = String.valueOf((char)(colID.charAt(0) + range));
		String lowerRightRow = String.valueOf(rowID + range);

		for (int i = 0; i < range * 2 + 1; i++){
			adjacentSlotIDs.add(String.valueOf((char)(upperLeftCol.substring(0, 1).charAt(0) + i)) + upperLeftRow);
		}
		for (int i = 0; i < range * 2 + 1; i++){
			adjacentSlotIDs.add(upperLeftCol + String.valueOf(Integer.parseInt(upperLeftRow) + i));
		}
		for (int i = range * 2 + 1 - 1; i >= 0; i--){
			adjacentSlotIDs.add(String.valueOf((char)(lowerRightCol.substring(0, 1).charAt(0) - i)) + lowerRightRow);
		}
		for (int i = range * 2 + 1 - 1; i >= 0; i--){
			adjacentSlotIDs.add(lowerRightCol + String.valueOf(Integer.parseInt(lowerRightRow) - i));
		}

		adjacentSlotIDs = removeStringDuplicates(adjacentSlotIDs);
		for (int i = 0; i < adjacentSlotIDs.size; i++){
			String adSlotID = adjacentSlotIDs.get(i);
			String adSlotColID = adSlotID.substring(0, 1);
			String adSlotRowID = adSlotID.substring(1, 2);
			if (!((adSlotColID.equals("A") || adSlotColID.equals("B") || adSlotColID.equals("C") || adSlotColID.equals("D") || adSlotColID.equals("E")) &&
					(adSlotRowID.equals("1") || adSlotRowID.equals("2") || adSlotRowID.equals("3") || adSlotRowID.equals("4") || adSlotRowID.equals("5")))){
				adjacentSlotIDs.set(i, null);
			}
		}

		while (adjacentSlotIDs.contains(null, false)) adjacentSlotIDs.removeValue(null, false);
		adjacentSlotIDs.sort();
		adjacentSlotIDs.shrink();

		return adjacentSlotIDs;
	}

	public void shineAllTopCoins(){
		for (Slot slot : slots){
			slot.getCoinQueue().first().setShining(true);
		}
	}

	private static Array<String> removeStringDuplicates(Array<String> duplicateStrings){
		Array<String> strings = new Array<String>();

		for (String string : duplicateStrings){
			if (!strings.contains(string, false)) strings.add(string);
		}

		return strings;
	}

	public CoinAutoConnector getcAutoConnector() {
		return cAutoConnector;
	}

	public void setSlotChallenges(){
		PuzzleData pData = gScreen.game.challManager.getpData();
		Array<Coin> coinQueueA1 = new Array<Coin>();
		Array<Coin> coinQueueA2 = new Array<Coin>();
		Array<Coin> coinQueueA3 = new Array<Coin>();
		Array<Coin> coinQueueA4 = new Array<Coin>();
		Array<Coin> coinQueueA5 = new Array<Coin>();
		Array<Coin> coinQueueB1 = new Array<Coin>();
		Array<Coin> coinQueueB2 = new Array<Coin>();
		Array<Coin> coinQueueB3 = new Array<Coin>();
		Array<Coin> coinQueueB4 = new Array<Coin>();
		Array<Coin> coinQueueB5 = new Array<Coin>();
		Array<Coin> coinQueueC1 = new Array<Coin>();
		Array<Coin> coinQueueC2 = new Array<Coin>();
		Array<Coin> coinQueueC3 = new Array<Coin>();
		Array<Coin> coinQueueC4 = new Array<Coin>();
		Array<Coin> coinQueueC5 = new Array<Coin>();
		Array<Coin> coinQueueD1 = new Array<Coin>();
		Array<Coin> coinQueueD2 = new Array<Coin>();
		Array<Coin> coinQueueD3 = new Array<Coin>();
		Array<Coin> coinQueueD4 = new Array<Coin>();
		Array<Coin> coinQueueD5 = new Array<Coin>();
		Array<Coin> coinQueueE1 = new Array<Coin>();
		Array<Coin> coinQueueE2 = new Array<Coin>();
		Array<Coin> coinQueueE3 = new Array<Coin>();
		Array<Coin> coinQueueE4 = new Array<Coin>();
		Array<Coin> coinQueueE5 = new Array<Coin>();
		reinitializeCoinAssets(pData.a1, "A1");
		reinitializeCoinAssets(pData.a2, "A2");
		reinitializeCoinAssets(pData.a3, "A3");
		reinitializeCoinAssets(pData.a4, "A4");
		reinitializeCoinAssets(pData.a5, "A5");
		reinitializeCoinAssets(pData.b1, "B1");
		reinitializeCoinAssets(pData.b2, "B2");
		reinitializeCoinAssets(pData.b3, "B3");
		reinitializeCoinAssets(pData.b4, "B4");
		reinitializeCoinAssets(pData.b5, "B5");
		reinitializeCoinAssets(pData.c1, "C1");
		reinitializeCoinAssets(pData.c2, "C2");
		reinitializeCoinAssets(pData.c3, "C3");
		reinitializeCoinAssets(pData.c4, "C4");
		reinitializeCoinAssets(pData.c5, "C5");
		reinitializeCoinAssets(pData.d1, "D1");
		reinitializeCoinAssets(pData.d2, "D2");
		reinitializeCoinAssets(pData.d3, "D3");
		reinitializeCoinAssets(pData.d4, "D4");
		reinitializeCoinAssets(pData.d5, "D5");
		reinitializeCoinAssets(pData.e1, "E1");
		reinitializeCoinAssets(pData.e2, "E2");
		reinitializeCoinAssets(pData.e3, "E3");
		reinitializeCoinAssets(pData.e4, "E4");
		reinitializeCoinAssets(pData.e5, "E5");
		coinQueueA1.addAll(pData.a1);
		coinQueueA2.addAll(pData.a2);
		coinQueueA3.addAll(pData.a3);
		coinQueueA4.addAll(pData.a4);
		coinQueueA5.addAll(pData.a5);
		coinQueueB1.addAll(pData.b1);
		coinQueueB2.addAll(pData.b2);
		coinQueueB3.addAll(pData.b3);
		coinQueueB4.addAll(pData.b4);
		coinQueueB5.addAll(pData.b5);
		coinQueueC1.addAll(pData.c1);
		coinQueueC2.addAll(pData.c2);
		coinQueueC3.addAll(pData.c3);
		coinQueueC4.addAll(pData.c4);
		coinQueueC5.addAll(pData.c5);
		coinQueueD1.addAll(pData.d1);
		coinQueueD2.addAll(pData.d2);
		coinQueueD3.addAll(pData.d3);
		coinQueueD4.addAll(pData.d4);
		coinQueueD5.addAll(pData.d5);
		coinQueueE1.addAll(pData.e1);
		coinQueueE2.addAll(pData.e2);
		coinQueueE3.addAll(pData.e3);
		coinQueueE4.addAll(pData.e4);
		coinQueueE5.addAll(pData.e5);
		getSlotByID("A1").setCoinQueue(coinQueueA1);
		getSlotByID("A2").setCoinQueue(coinQueueA2);
		getSlotByID("A3").setCoinQueue(coinQueueA3);
		getSlotByID("A4").setCoinQueue(coinQueueA4);
		getSlotByID("A5").setCoinQueue(coinQueueA5);
		getSlotByID("B1").setCoinQueue(coinQueueB1);
		getSlotByID("B2").setCoinQueue(coinQueueB2);
		getSlotByID("B3").setCoinQueue(coinQueueB3);
		getSlotByID("B4").setCoinQueue(coinQueueB4);
		getSlotByID("B5").setCoinQueue(coinQueueB5);
		getSlotByID("C1").setCoinQueue(coinQueueC1);
		getSlotByID("C2").setCoinQueue(coinQueueC2);
		getSlotByID("C3").setCoinQueue(coinQueueC3);
		getSlotByID("C4").setCoinQueue(coinQueueC4);
		getSlotByID("C5").setCoinQueue(coinQueueC5);
		getSlotByID("D1").setCoinQueue(coinQueueD1);
		getSlotByID("D2").setCoinQueue(coinQueueD2);
		getSlotByID("D3").setCoinQueue(coinQueueD3);
		getSlotByID("D4").setCoinQueue(coinQueueD4);
		getSlotByID("D5").setCoinQueue(coinQueueD5);
		getSlotByID("E1").setCoinQueue(coinQueueE1);
		getSlotByID("E2").setCoinQueue(coinQueueE2);
		getSlotByID("E3").setCoinQueue(coinQueueE3);
		getSlotByID("E4").setCoinQueue(coinQueueE4);
		getSlotByID("E5").setCoinQueue(coinQueueE5);
	}

	private void reinitializeCoinAssets(Coin[] coins, String slotID){
		for (int i = 0; i < coins.length; i++){
			Coin coin = coins[i];
			if (coin != null){
				switch (coin.getCoinType()){
				case WHOLE: coin.setWholeCoin(coin.getCoinValue(), coin.getCoinColor()); break;
				case HALF: coin.setHalfCoin(coin.getCoinValue(), coin.getTopCoinColor(), coin.getBottomCoinColor()); break;
				case THIRD: coin.setThirdCoin(coin.getCoinValue()); break;
				default: break;
				}
				coin.setCoinID(slotID);
			}
		}
	}

	public ChallengeConnectionData getChallengeCData() {
		return challengeCData;
	}

	public void resetAll() {
		challengeCData.reset();
		for (Slot slot : slots){
			slot.resetAll();
		}
		initCoins();
	}

	@Override
	public void dispose() {
		gScreen = null;
		if (coinAssets != null) coinAssets.dispose();
		coinAssets = null;
		if (slotAssets != null) slotAssets.dispose();
		slotAssets = null;
		if (lightAssets != null) lightAssets.dispose();
		lightAssets = null;
		coinQueue = null;
		if (cAutoConnector != null) cAutoConnector.dispose();
		cAutoConnector = null;
		if (challengeCData != null) challengeCData.dispose();
		challengeCData = null;
		if (slots != null) {
			for (Slot slot : slots){
				if (slot != null) slot.dispose();
			}
			slots.clear();
		}
		slots = null;
	}
}
