package com.connectcoins.challenge;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.coins.Coin;
import com.connectcoins.coins.CoinManager;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.listeners.CoinActor;
import com.connectcoins.listeners.Scoreboard;
import com.connectcoins.slots.Slot;
import com.connectcoins.slots.SlotManager;
import com.connectcoins.ui.InGameMenu;

public class ChallengeConnectionData implements Disposable {

	public class ConnectionData implements Disposable {
		private Array<Coin> coin;
		private int cc;
		private int score;

		public Array<Coin> getCoin() {
			return coin;
		}

		public ConnectionData(Array<Coin> coin, int score, int cc){
			this.coin = coin;
			this.score = score;
			this.cc = cc;
		}
		
		public void reset(){
			coin.clear();
			coin = null;
			cc = 0;
			score = 0;
		}

		@Override
		public void dispose() {
			if (coin != null){
				coin.clear();
			}
			coin = null;
			cc = 0;
			score = 0;
		}
	}

	private boolean on;
	private Array<ConnectionData> connectionData;
	private SlotManager slotManager;
	private CoinManager coinManager;
	private InGameMenu inGameMenu;
	private Scoreboard sb;

	public ChallengeConnectionData(ConnectCoins game, SlotManager slotManager){
		this.slotManager = slotManager;
		connectionData = new Array<ConnectionData>();

		on = false;
	}

	public void addData(Array<Coin> connectedCoins, int totalScore, int totalCC){
		if (on) connectionData.add(new ConnectionData(connectedCoins, totalScore, totalCC));
	}

	public void activateChallengeCData(boolean activate){
		on = activate;
	}

	public void undo(){
		if (on && canUndoAndRestart()){
			ConnectionData data = connectionData.pop();
			Array<Coin> coins = data.coin;
			for (Coin coin : coins){
				coin.resetCoin();
				String id = coin.getCoinID();
				CoinActor actor = coinManager.getActorByID(id);
				actor.resetCoinActor();
				Slot slot = slotManager.getSlotByID(id);
				slot.setDisabled(false);
				Array<Coin> coinQueue = slot.getCoinQueue();
				coinQueue.insert(0, coin);
			}
			sb.subtractScore(data.score);
			sb.subtractCC(data.cc);
			sb.removeLastComboInstance();
			inGameMenu.backToGame();
		}
	}

	public void restart(){
		while (connectionData.size > 0){
			undo();
		}
		inGameMenu.backToGame();
	}
	
	public void reset(){
		on = false;
		for (ConnectionData cData : connectionData) cData.reset();
		connectionData.clear();
		connectionData.shrink();
	}

	public boolean canUndoAndRestart(){
		return connectionData.size != 0;
	}

	public void setInGameMenu(InGameMenu inGameMenu) {
		this.inGameMenu = inGameMenu;
	}

	public void setBoard(Scoreboard sb) {
		this.sb = sb;
	}
	
	public void setCoinManager(CoinManager coinManager) {
		this.coinManager = coinManager;
	}

	@Override
	public void dispose() {
		for (ConnectionData cd : connectionData){
			if (cd != null) cd.dispose();
			cd = null;
		}
		if (connectionData != null) connectionData.clear();
		connectionData = null;
		slotManager = null;
		coinManager = null;
		inGameMenu = null;
		sb = null;
	}

}
