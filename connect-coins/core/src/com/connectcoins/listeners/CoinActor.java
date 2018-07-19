package com.connectcoins.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.audio.GameScreenAudioAssets;
import com.connectcoins.coins.Coin;
import com.connectcoins.connection.ConnectionManager;
import com.connectcoins.game.GameScreen;
import com.connectcoins.slots.Slot;
import com.connectcoins.slots.SlotManager;
import com.connectcoins.slots.SlotPosition.AdjacentSlotPos;

public class CoinActor extends Actor implements Disposable {
	private int MAX_NUM_COIN_CONNECTIONS = 5;
	
	private GameScreen gScreen;
	private Circle circle;
	private float x, y, topX, topY;
	private String slotID;
	private SlotManager slotManager;
	private ConnectionManager connectionManager;
	private Slot slot;
	private boolean allowConnection = true;
	private boolean coinConnected = false;

	public CoinActor(GameScreen gScreen, SlotManager slotManager,
			ConnectionManager connectionManager, float circleX, float circleY, float radius, String slotID){
		this.gScreen = gScreen;
		this.slotID = slotID;
		this.connectionManager = connectionManager;
		this.slotManager = slotManager;
		circle = new Circle(circleX, circleY, radius);
		slot = getActorSlot();
	}
	
	@Override
	public void act(float delta) {
		if (getTouchable() == Touchable.enabled){
			if (slot.hasCoins() && !slot.isDisabled()){
				gScreen.game.touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				gScreen.game.cam.unproject(gScreen.game.touchPos);
				gScreen.game.cam.update();
				
				Array<Coin> coinQueue = getSlot().getCoinQueue();
				if (coinQueue.size == 0) allowConnection = false;
				
				checkIfTouched(gScreen.game.touchPos.x, gScreen.game.touchPos.y);
			}
		}
	}

	@Override
	public void setBounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.topX = x + width;
		this.topY = y + height;
		super.setBounds(x, y, width, height);
	}

	public void checkIfTouched(float x, float y){
		if (allowConnection && !connectionManager.getCoinSubmission().isAnimPlaying()){
			if (Gdx.input.isTouched(0)){
				if (x >= this.x && x < topX && y >= this.y && y < topY){
					if (Math.pow(x - circle.x, 2) + Math.pow(y - circle.y, 2) <= Math.pow(circle.radius, 2)){
						Slot slot = getSlot();
						if (connectionManager.getConnectedCoinActors().size == 0){
							if (Gdx.input.justTouched()) connectCoins(slot);
						}
						else connectCoins(slot);
					}
				}
			}
		}
	}
	
	private void connectCoins(Slot slot){
		if (connectionManager.getConnectedCoinActors().size >= MAX_NUM_COIN_CONNECTIONS){
			connectionManager.submitConnection();
		}
		else {
			AdjacentSlotPos adjacentSPos = null;
			if (connectionManager.getConnectedCoinActors().size != 0){
				adjacentSPos = connectionManager.checkIfAdjacent(slot);
			}

			if (!coinConnected){
				allowConnection = false;
				coinConnected = true;
				connectionManager.connectSlots(this, adjacentSPos);
				
				GameScreenAudioAssets.coinTouch.play(.25f);
			}
		}
	}
	
	public void addActorToConnection(){
		allowConnection = false;
		coinConnected = true;
		
		GameScreenAudioAssets.coinTouch.play(.25f);
	}
	
	public void resetCoinActor(){
		allowConnection = true;
		coinConnected = false;
		connectionManager.setActorToNormal(this);
	}

	public SlotManager getSlotManager() {
		return slotManager;
	}

	public Slot getSlot(){
		return slot;
	}

	private Slot getActorSlot(){
		return slotManager.getSlotByID(slotID);
	}

	public void setAllowConnection(boolean allowConnection) {
		this.allowConnection = allowConnection;
	}

	public void setCoinConnected(boolean coinConnected) {
		this.coinConnected = coinConnected;
	}

	public boolean isCoinConnected() {
		return coinConnected;
	}

	public String getSlotID() {
		return slotID;
	}

	@Override
	public void dispose() {
		gScreen = null;
		circle = null;
		slotID = null;
		slotManager = null;
		connectionManager = null;
		slot = null;
	}
}