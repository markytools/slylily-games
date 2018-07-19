package com.connectcoins.connection;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.awards.AchievementManager;
import com.connectcoins.awards.AchievementVerification;
import com.connectcoins.awards.CoinsComboData;
import com.connectcoins.coins.Coin;
import com.connectcoins.coins.CoinManager;
import com.connectcoins.coins.Coin.CoinType;
import com.connectcoins.coins.CoinQueue;
import com.connectcoins.combo.ComboScorer;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen;
import com.connectcoins.gameCompletion.Scorer;
import com.connectcoins.listeners.CoinActor;
import com.connectcoins.slots.Slot;
import com.connectcoins.slots.SlotManager;
import com.connectcoins.slots.SlotPosition.AdjacentSlotPos;
import com.connectcoins.tutorial.TutorialManager;
import com.connectcoins.ui.SpecialUI;

public class ConnectionManager implements Disposable {
	private Array<CoinActor> connectedCoinActors;
	private Array<CoinActor> coinActors;
	private Slot lastConnectedSlot;
	private CoinQueue coinQueue;
	private CoinSubmission coinSubmission;
	private ConnectCoins game;
	private GameScreen gScreen;
	private ComboScorer comboScorer;
	private SlotManager slotManager;
	private TutorialManager tutorialManager;
	private AchievementVerification achVerification;
	private Scorer scorer;
	private CoinManager cManager;

	private boolean ON_FIRE = false;

	public ConnectionManager(GameScreen gScreen, SlotManager slotManager, CoinQueue coinQueue, AchievementManager achievementManager,
			Scorer scorer){
		this.tutorialManager = gScreen.getTutorialManager();
		this.achVerification = achievementManager.getAchVerification();
		this.gScreen = gScreen;
		this.scorer = scorer;
		this.game = gScreen.game;
		this.slotManager = slotManager;
		this.coinQueue = coinQueue;

		initManager();
	}

	private void initManager() {
		connectedCoinActors = new Array<CoinActor>();
		comboScorer = new ComboScorer(game, slotManager.getChallengeCData(), scorer);
		coinSubmission = new CoinSubmission(gScreen, this, comboScorer, slotManager, coinQueue, tutorialManager);
	}

	public void connectSlots(CoinActor cActor, AdjacentSlotPos adjacentSP){
		Slot slot = cActor.getSlot();
		if (connectedCoinActors.size == 0){
			slot.connected = true;
			slot.getCurrentCoin().setConnected(true);
			lastConnectedSlot = slot;
			connectedCoinActors.add(cActor);
			coinSubmission.setQueuedCoins(cActor, connectedCoinActors.indexOf(cActor, true));
		}
		else {
			switch (adjacentSP){
			case BOTTOM: {
				slot.connectedBottom = true;
				lastConnectedSlot.connectedTop = true;
			}; break;
			case BOTTOM_LEFT: {
				slot.connectedBottomRight = true;
				lastConnectedSlot.connectedTopLeft = true;
			}; break;
			case BOTTOM_RIGHT: {
				slot.connectedBottomLeft = true;
				lastConnectedSlot.connectedTopRight = true;
			}; break;
			case LEFT: {
				slot.connectedRight= true;
				lastConnectedSlot.connectedLeft = true;
			}; break;
			case RIGHT: {
				slot.connectedLeft = true;
				lastConnectedSlot.connectedRight = true;
			}; break;
			case TOP: {
				slot.connectedTop = true;
				lastConnectedSlot.connectedBottom = true;
			}; break;
			case TOP_LEFT: {
				slot.connectedTopRight = true;
				lastConnectedSlot.connectedBottomLeft = true;
			}; break;
			case TOP_RIGHT: {
				slot.connectedTopLeft = true;
				lastConnectedSlot.connectedBottomRight = true;
			}; break;
			case NONE: {

			}; break;
			default: break;
			}

			if (adjacentSP != AdjacentSlotPos.NONE){
				addSlotToConnection(slot, cActor, true);
			}
			else {
				if (game.gMConfig.mode == GameMode.CHALLENGE){
					submitConnection();  	
					setActorToNormal(cActor);
				}
				else autoConnectDistantCoins(cActor);
//				submitConnection();  		// RESETTER
//				setActorToNormal(cActor);
			}
		}
	}
	
	private void addSlotToConnection(Slot slot, CoinActor cActor, boolean addToMainConnection){
		slot.connected = true;
		slot.getCurrentCoin().setConnected(true);
		if (addToMainConnection){
			connectedCoinActors.add(cActor);
			lastConnectedSlot = slot;
			coinSubmission.setQueuedCoins(cActor, connectedCoinActors.indexOf(cActor, true));
		}
	}
	
	public void autoConnectDistantCoins(CoinActor targetCActor){
		Array<CoinActor> autoConnectedCActors = new Array<CoinActor>();
		Slot tempLastConnectedSlot = lastConnectedSlot;
		
		while (true){
			Slot nearestSlot = getNearestSlot(tempLastConnectedSlot.getsPos().slotPosID, targetCActor.getSlotID());
			if (nearestSlot != null){
				String slotID = nearestSlot.getsPos().slotPosID;
				CoinActor nearestSlotActor = cManager.getActorByID(slotID);

				autoConnectedCActors.add(nearestSlotActor);
				nearestSlotActor.addActorToConnection();
				addSlotToConnection(nearestSlot, nearestSlotActor, false);
				tempLastConnectedSlot = nearestSlot;
				if (5 - connectedCoinActors.size - autoConnectedCActors.size >= 0){
					if (nearestSlotActor == targetCActor){
						for (CoinActor coinActor : autoConnectedCActors){
							connectedCoinActors.add(coinActor);
							coinSubmission.setQueuedCoins(coinActor, connectedCoinActors.indexOf(coinActor, true));
						}
						lastConnectedSlot = nearestSlot;
						for (CoinActor coinActor : connectedCoinActors) coinActor.setAllowConnection(true);
						submitConnection();
						setActorToNormal(targetCActor);
						break;
					}
				}
				else {
					for (CoinActor cActors : autoConnectedCActors){
						cActors.resetCoinActor();
					}
					for (CoinActor coinActor : connectedCoinActors) coinActor.setAllowConnection(true);
					submitConnection();
					setActorToNormal(targetCActor);
					break;
				}
			}
			else {
				for (CoinActor cActors : autoConnectedCActors){
					cActors.resetCoinActor();
				}
				for (CoinActor coinActor : connectedCoinActors) coinActor.setAllowConnection(true);
				submitConnection();
				setActorToNormal(targetCActor);
				break;
			}
		}
	}
	
	private Slot getNearestSlot(String initialSlotID, String finalSlotID){
		Array<String> filteredNearbySlotIDs = new Array<String>();
		Array<String> adjacentSlots = SlotManager.getAdjacentSlotIDs(initialSlotID);
		for (String id : adjacentSlots){
			Slot slot = slotManager.getSlotByID(id);
			if (!slot.isDisabled() && !slot.connected) filteredNearbySlotIDs.add(id);
		}

		if (filteredNearbySlotIDs.size == 0) return null;

		char targetColID = finalSlotID.substring(0, 1).charAt(0);
		int targetRowID = Integer.parseInt(finalSlotID.substring(1, 2));

		int smallestColDistance = 4;
		for (int i = 0; i < filteredNearbySlotIDs.size; i++){
			String nearbySlot = filteredNearbySlotIDs.get(i);
			char idLetter = nearbySlot.substring(0, 1).charAt(0);
			
			int dist = Math.abs(targetColID - idLetter);
			if (dist < smallestColDistance) smallestColDistance = dist;
		}
		
		for (int i = 0; i < filteredNearbySlotIDs.size; i++){
			String nearbySlotID = filteredNearbySlotIDs.get(i);
			char idLetter = nearbySlotID.substring(0, 1).charAt(0);
			
			if (Math.abs(targetColID - idLetter) != smallestColDistance){
				filteredNearbySlotIDs.removeValue(nearbySlotID, false);
				filteredNearbySlotIDs.shrink();
				i = 0;
			}
		}
		
		int smallestRowDistance = 4;
		for (int i = 0; i < filteredNearbySlotIDs.size; i++){
			String nearbySlot = filteredNearbySlotIDs.get(i);
			int rowID = Integer.parseInt(nearbySlot.substring(1, 2));
			
			int dist = Math.abs(targetRowID - rowID);
			if (dist < smallestRowDistance) smallestRowDistance = dist;
		}

		for (int i = 0; i < filteredNearbySlotIDs.size; i++){
			String nearbySlotID = filteredNearbySlotIDs.get(i);
			int rowID = Integer.parseInt(nearbySlotID.substring(1, 2));
			
			if (Math.abs(targetRowID - rowID) != smallestRowDistance){
				filteredNearbySlotIDs.removeValue(nearbySlotID, false);
				filteredNearbySlotIDs.shrink();
				i = 0;
			}
		}
		
		return slotManager.getSlotByID(filteredNearbySlotIDs.random());
	}
	
	public static Array<String> toArray(Iterable<String> iter) {
		Array<String> list = new Array<String>();
	    for (String item : iter) {
	        list.add(item);
	    }
	    return list;
	}

	public AdjacentSlotPos checkIfAdjacent(Slot slot){
		return slot.getsPos().getAdjacentPos(lastConnectedSlot);
	}

	public void submitConnection(){
		if (connectedCoinActors.size != 0){
			//			Submit Connection

			if (game.gMConfig.mode == GameMode.TUTORIAL){
				updateCoinsForTutorialOnSubmit(connectedCoinActors);
				if (tutorialManager.getCurrentMsgNum() == 4 && connectedCoinActors.size >= 3) tutorialManager.nextMsg(4);
				if (gScreen.getTutorialManager().getCurrentMsgNum() == 11){
					for (CoinActor cActors : connectedCoinActors){
						if (cActors.getSlot().getCurrentCoin().isShining()){
							gScreen.setResetTutorialPane(true);
							gScreen.getTutorialManager().nextMsg(11);
							break;
						}
					}
				}
				if (gScreen.getTutorialManager().getCurrentMsgNum() == 20){
					for (CoinActor cActors : connectedCoinActors){
						if (cActors.getSlot().getCurrentCoin().getCoinType() == CoinType.HALF ||
								cActors.getSlot().getCurrentCoin().getCoinType() == CoinType.THIRD){
							gScreen.setResetTutorialPane(true);
							gScreen.getTutorialManager().nextMsg(20);
							break;
						}
					}
				}
			}

			if (ON_FIRE){
				if (connectedCoinActors.size == 1){
					Array<Slot> autoConnectedSlots = connectedCoinActors.get(0).getSlotManager().getcAutoConnector().autoConnect(
							connectedCoinActors.get(0).getSlot());

					for (Slot adSlot : autoConnectedSlots){
						if (adSlot != connectedCoinActors.get(0).getSlot()){
							for (CoinActor gameCActors : coinActors){
								if (adSlot == gameCActors.getSlot()){
									adSlot.connected = true;
									adSlot.getCurrentCoin().setConnected(true);
									gameCActors.getSlot().getCurrentCoin().setConnected(true);
									lastConnectedSlot = connectedCoinActors.get(0).getSlot();
									connectedCoinActors.add(gameCActors);
									coinSubmission.setQueuedCoins(gameCActors, connectedCoinActors.indexOf(gameCActors, true));
								}
							}
						}
					}

					if (game.gMConfig.mode == GameMode.TUTORIAL) 
						if (tutorialManager.getCurrentMsgNum() == 22 && connectedCoinActors.size >= 3){
							tutorialManager.nextMsg(22);
							gScreen.setResetTutorialPane(true);
						}
				}
			}

			coinSubmission.setAnimPlaying(true);

			Array<Coin> coinsToSubmit = new Array<Coin>();
			for (CoinActor cActor : connectedCoinActors){
				Slot slot = cActor.getSlot();
				setActorToNormal(cActor);
				slot.queueForRecreate = true;

				coinsToSubmit.add(cActor.getSlot().getCurrentCoin());
			}
			CoinsComboData cComboData = comboScorer.addCoinConnection(coinsToSubmit);
			if (game.gMConfig.mode == GameMode.NORMAL) 
				achVerification.verifyAchievements(cComboData);

			coinSubmission.readyCoinAnimation();
			coinSubmission.startInitializers();

		}
		connectedCoinActors.clear();
		lastConnectedSlot = null;
	}
	
	public void initCoinsForTutorial(){
		boolean hasHalfOrThird = false;
		boolean hasShining = false;
		
		outerloop:
			for (int i = 0; i < Slot.slotCols.length; i++){
				for (int i2 = 0; i2 < Slot.slotRows.length; i2++){
					String slotID = String.valueOf(Slot.slotCols[i]) + String.valueOf(Slot.slotRows[i2]);
					Coin coin = slotManager.getSlotByID(slotID).getCoinQueue().first();
					if (coin.getCoinType() == CoinType.HALF || coin.getCoinType() == CoinType.THIRD) hasHalfOrThird = true;
					if (coin.isShining()) hasShining = true;
					if (hasHalfOrThird && hasShining) break outerloop;
				}
			}
		if (!hasHalfOrThird){
			Slot slot = coinActors.random().getSlot();
			Coin randomCoin = CoinQueue.createRandomCoin(0, 50, 50);
			randomCoin.setCoinID(slot.getsPos().slotPosID);
			Array<Coin> coinQ = slot.getCoinQueue();
			coinQ.set(0, randomCoin);
		}
		if (!hasShining){
			Slot slot = coinActors.random().getSlot();
			Array<Coin> coinQ = slot.getCoinQueue();
			coinQ.get(0).setShining(true);
		}
	}

	/**
	 * Call this method before submitting the array coinsToSubmit
	 */
	private void updateCoinsForTutorialOnSubmit(Array<CoinActor> coinsToSubmit){
		boolean hasHalfOrThird = false;
		boolean hasShining = false;

		outerloop:
			for (int i = 0; i < Slot.slotCols.length; i++){
				for (int i2 = 0; i2 < Slot.slotRows.length; i2++){
					String slotID = String.valueOf(Slot.slotCols[i]) + String.valueOf(Slot.slotRows[i2]);
					Coin coin;
					if (hasSlot(coinsToSubmit, slotID)){
						coin = slotManager.getSlotByID(slotID).getCoinQueue().get(1);
					}
					else {
						coin = slotManager.getSlotByID(slotID).getCoinQueue().first();
					}
					if (coin.getCoinType() == CoinType.HALF || coin.getCoinType() == CoinType.THIRD) hasHalfOrThird = true;
					if (coin.isShining()) hasShining = true;
					if (hasHalfOrThird && hasShining) break outerloop;
				}
			}
		if (!hasHalfOrThird){
			Slot slot = coinsToSubmit.random().getSlot();
			Coin randomCoin = CoinQueue.createRandomCoin(0, 50, 50);
			randomCoin.setCoinID(slot.getsPos().slotPosID);
			Array<Coin> coinQ = slot.getCoinQueue();
			coinQ.set(1, randomCoin);
		}
		if (!hasShining){
			CoinActor cActor = coinsToSubmit.random();
			Array<Coin> coinQ = cActor.getSlot().getCoinQueue();
			coinQ.get(1).setShining(true);
		}
	}

	private boolean hasSlot(Array<CoinActor> coinsToSubmit, String slotID){
		for (CoinActor cActor : coinsToSubmit){
			if (cActor.getSlotID().equals(slotID)) return true;
		}
		return false;
	}

	public void setActorToNormal(CoinActor cActor){
		Slot slot = cActor.getSlot();
		Coin coin = slot.getCurrentCoin();
		if (coin != null) coin.setConnected(false);
		cActor.setAllowConnection(true);
		cActor.setCoinConnected(false);
		slot.connected = false;
		slot.connectedBottom = false;
		slot.connectedBottomLeft = false;
		slot.connectedBottomRight = false;
		slot.connectedLeft = false;
		slot.connectedRight = false;
		slot.connectedTop = false;
		slot.connectedTopLeft = false;
		slot.connectedTopRight = false;
	}

	public void resetAll() {
		ON_FIRE = false;
		connectedCoinActors.clear();
		connectedCoinActors.shrink();
		lastConnectedSlot = null;
		coinSubmission.resetAll();
		comboScorer.resetAll();
	}

	public CoinSubmission getCoinSubmission() {
		return coinSubmission;
	}

	public Slot getLastConnectedSlot() {
		return lastConnectedSlot;
	}

	public Array<CoinActor> getConnectedCoinActors() {
		return connectedCoinActors;
	}

	public ComboScorer getComboScorer() {
		return comboScorer;
	}

	public void setCoinActors(Array<CoinActor> coinActors) {
		this.coinActors = coinActors;
	}

	public boolean isON_FIRE() {
		return ON_FIRE;
	}

	public void setON_FIRE(boolean oN_FIRE) {
		this.ON_FIRE = oN_FIRE;
	}

	public void setSpecialUI(SpecialUI specialUI){
		comboScorer.setSpecialUI(specialUI);
	}

	public void setcManager(CoinManager cManager) {
		this.cManager = cManager;
	}

	public void setRecreateCoinState(boolean recreateCoins){
		coinSubmission.setRECREATE_COINS(recreateCoins);
	}

	@Override
	public void dispose() {
		ON_FIRE = false;
		if (connectedCoinActors != null) connectedCoinActors.clear();
		connectedCoinActors = null;
		coinActors = null;
		lastConnectedSlot = null;
		coinQueue = null;
		if (coinSubmission != null) coinSubmission.dispose();
		coinSubmission = null;
		game = null;
		gScreen = null;
		if (comboScorer != null) comboScorer.dispose();
		comboScorer = null;
		slotManager = null;
		tutorialManager = null;
		achVerification = null;
		scorer = null;
		cManager = null;
	}
}