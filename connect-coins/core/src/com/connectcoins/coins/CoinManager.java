package com.connectcoins.coins;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.connection.ConnectionManager;
import com.connectcoins.game.GameScreen;
import com.connectcoins.game.GameScreen.GameState;
import com.connectcoins.listeners.CoinActor;
import com.connectcoins.slots.Slot;
import com.connectcoins.slots.SlotManager;

public class CoinManager implements Disposable {
	private GameScreen gScreen;
	private Array<CoinActor> coinActors;
	private SlotManager slotManager;
	private ConnectionManager connectionManager;
	
	public CoinManager(GameScreen gScreen, SlotManager slotManager, ConnectionManager connectionManager){
		this.gScreen = gScreen;
		this.slotManager = slotManager;
		this.connectionManager = connectionManager;
		initCoinActor();
	}

	private void initCoinActor() {
		coinActors = new Array<CoinActor>();
		for (int i = 0; i < SlotManager.slotColPos.length; i++){
			for (int i2 = 0; i2 < SlotManager.slotRowPos.length; i2++){
				float xPos = SlotManager.slotColPos[i];
				float yPos = SlotManager.slotRowPos[i2];
				final CoinActor coinActor = new CoinActor(gScreen, slotManager, connectionManager, xPos + 107.5f, yPos + 109.5f, 145f / 2,
						String.valueOf(Slot.slotCols[i]) + String.valueOf(Slot.slotRows[i2]));
				coinActor.setUserObject(GameState.NORMAL);
				coinActor.setBounds(xPos, yPos, 216, 216);
				coinActors.add(coinActor);
				gScreen.game.stage.addActor(coinActor);
			}
		}
		connectionManager.setCoinActors(coinActors);
	}

	public void resetAll() {
		for (CoinActor cActor : coinActors) cActor.resetCoinActor();
	}
	
	public void render(SpriteBatch batch){
		
	}
	
	public void actListeners(){
		
	}
	
	public CoinActor getActorByID(String id){
		for (CoinActor actor : coinActors){
			if (actor.getSlotID().equals(id)) return actor;
		}
		return null;
	}

	public Array<CoinActor> getCoinActors() {
		return coinActors;
	}

	@Override
	public void dispose() {
		gScreen = null;
		if (coinActors != null){
			for (CoinActor cActor : coinActors){
				if (cActor != null) cActor.dispose();
				cActor = null;
			}
			coinActors.clear();
		}
		coinActors = null;
		slotManager = null;
		connectionManager = null;
	}
}