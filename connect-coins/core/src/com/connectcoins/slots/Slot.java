package com.connectcoins.slots;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.coins.Coin;

public class Slot implements Disposable {
	public static final char[] slotCols = {'A', 'B', 'C', 'D', 'E'};
	public static final char[] slotRows = {'1', '2', '3', '4', '5'};

	private SlotPosition sPos;
	private Array<Coin> coinQueue;
	private Coin currentCoin;
	private boolean disabled = false;	//Use this if to disable/enable slot usage in game.

	public boolean queueForRecreate = false;
	public boolean renderCoins = true;
	
	public boolean connected = false;

	public boolean connectedTop = false;
	public boolean connectedTopLeft = false;
	public boolean connectedTopRight = false;
	public boolean connectedLeft = false;
	public boolean connectedRight = false;
	public boolean connectedBottomLeft = false;
	public boolean connectedBottomRight = false;
	public boolean connectedBottom = false;
	
	public boolean isOnFire = false;

	private boolean hasTopLights = true;
	private boolean hasTopRightLights = true;
	private boolean hasTopLeftLights = true;
	private boolean hasLeftLights = true;
	private boolean hasRightLights = true;
	private boolean hasBottomLights = true;
	private boolean hasBottomLeftLights = true;
	private boolean hasBottomRightLights = true;

	public Slot(SlotPosition sPos){
		this.sPos = sPos;

		setSlotLights();
	}

	private void setSlotLights() {
		String slotID = sPos.slotPosID;
		if (slotID.equals("A1")){
			hasTopLights = false;
			hasTopRightLights = false;
			hasTopLeftLights = false;
			hasLeftLights = false;
			hasBottomLeftLights = false;
		}
		else if (slotID.equals("B1") || slotID.equals("C1") || slotID.equals("D1")){
			hasTopLights = false;
			hasTopRightLights = false;
			hasTopLeftLights = false;
		}
		else if (slotID.equals("E1")){
			hasTopLights = false;
			hasTopRightLights = false;
			hasTopLeftLights = false;
			hasRightLights = false;
			hasBottomRightLights = false;
		}
		else if (slotID.equals("A2") || slotID.equals("A3") || slotID.equals("A4")){
			hasTopLeftLights = false;
			hasLeftLights = false;
			hasBottomLeftLights = false;
		}
		else if (slotID.equals("E2") || slotID.equals("E3") || slotID.equals("E4")){
			hasTopRightLights = false;
			hasRightLights = false;
			hasBottomRightLights = false;
		}
		else if (slotID.equals("A5")){
			hasTopLeftLights = false;
			hasLeftLights = false;
			hasBottomLights = false;
			hasBottomLeftLights = false;
			hasBottomRightLights = false;
		}
		else if (slotID.equals("E5")){
			hasTopRightLights = false;
			hasRightLights = false;
			hasBottomLights = false;
			hasBottomLeftLights = false;
			hasBottomRightLights = false;
		}
		else if (slotID.equals("B5") || slotID.equals("C5") || slotID.equals("D5")){
			hasBottomLights = false;
			hasBottomLeftLights = false;
			hasBottomRightLights = false;
		}
	}

	public void render(SpriteBatch batch){
//		renderSlot(batch);
		renderLights(batch);
		renderCoin(batch);
	}

//	private void renderSlot(SpriteBatch batch){
//		switch (sPos.slotPosID){
//		case "A1": batch.draw(SlotAssets.topLeft, sPos.x, sPos.y); break;
//		case "B1": batch.draw(SlotAssets.top, sPos.x, sPos.y); break;
//		case "C1": batch.draw(SlotAssets.top, sPos.x, sPos.y); break;
//		case "D1": batch.draw(SlotAssets.top, sPos.x, sPos.y); break;
//		case "E1": batch.draw(SlotAssets.topRight, sPos.x, sPos.y); break;
//		case "A2": batch.draw(SlotAssets.left, sPos.x, sPos.y); break;
//		case "B2": batch.draw(SlotAssets.middle, sPos.x, sPos.y); break;
//		case "C2": batch.draw(SlotAssets.middle, sPos.x, sPos.y); break;
//		case "D2": batch.draw(SlotAssets.middle, sPos.x, sPos.y); break;
//		case "E2": batch.draw(SlotAssets.right, sPos.x, sPos.y); break;
//		case "A3": batch.draw(SlotAssets.left, sPos.x, sPos.y); break;
//		case "B3": batch.draw(SlotAssets.middle, sPos.x, sPos.y); break;
//		case "C3": batch.draw(SlotAssets.middle, sPos.x, sPos.y); break;
//		case "D3": batch.draw(SlotAssets.middle, sPos.x, sPos.y); break;
//		case "E3": batch.draw(SlotAssets.right, sPos.x, sPos.y); break;
//		case "A4": batch.draw(SlotAssets.left, sPos.x, sPos.y); break;
//		case "B4": batch.draw(SlotAssets.middle, sPos.x, sPos.y); break;
//		case "C4": batch.draw(SlotAssets.middle, sPos.x, sPos.y); break;
//		case "D4": batch.draw(SlotAssets.middle, sPos.x, sPos.y); break;
//		case "E4": batch.draw(SlotAssets.right, sPos.x, sPos.y); break;
//		case "A5": batch.draw(SlotAssets.bottomLeft, sPos.x, sPos.y); break;
//		case "B5": batch.draw(SlotAssets.bottom, sPos.x, sPos.y); break;
//		case "C5": batch.draw(SlotAssets.bottom, sPos.x, sPos.y); break;
//		case "D5": batch.draw(SlotAssets.bottom, sPos.x, sPos.y); break;
//		case "E5": batch.draw(SlotAssets.bottomRight, sPos.x, sPos.y); break;
//		default: break;
//		}
//	}

	private void renderLights(SpriteBatch batch){
		boolean slotLightsOn = false;
		if (hasTopLights){
			if (!connectedTop){
//				batch.draw(LightAssets.top1, sPos.x, sPos.y);
			}
			else {
				slotLightsOn = true;
				batch.draw(LightAssets.top2, sPos.x, sPos.y, 216, 216);
			}
		}
		if (hasTopLeftLights){
			if (!connectedTopLeft){
//				batch.draw(LightAssets.topLeft1, sPos.x, sPos.y);
			}
			else {
				slotLightsOn = true;
				batch.draw(LightAssets.topLeft2, sPos.x, sPos.y, 216, 216);
			}
		}
		if (hasTopRightLights){
			if (!connectedTopRight){
//				batch.draw(LightAssets.topRight1, sPos.x, sPos.y);
			}
			else {
				slotLightsOn = true;
				batch.draw(LightAssets.topRight2, sPos.x, sPos.y, 216, 216);
			}
		}
		if (hasLeftLights){
			if (!connectedLeft){
//				batch.draw(LightAssets.left1, sPos.x, sPos.y);
			}
			else {
				slotLightsOn = true;
				batch.draw(LightAssets.left2, sPos.x, sPos.y, 216, 216);
			}
		}
		if (hasRightLights){
			if (!connectedRight){
//				batch.draw(LightAssets.right1, sPos.x, sPos.y);
			}
			else {
				slotLightsOn = true;
				batch.draw(LightAssets.right2, sPos.x, sPos.y, 216, 216);
			}
		}
		if (hasBottomLeftLights){
			if (!connectedBottomLeft){
//				batch.draw(LightAssets.bottomLeft1, sPos.x, sPos.y);
			}
			else {
				slotLightsOn = true;
				batch.draw(LightAssets.bottomLeft2, sPos.x, sPos.y, 216, 216);
			}
		}
		if (hasBottomRightLights){
			if (!connectedBottomRight){
//				batch.draw(LightAssets.bottomRight1, sPos.x, sPos.y);
			}
			else {
				slotLightsOn = true;
				batch.draw(LightAssets.bottomRight2, sPos.x, sPos.y, 216, 216);
			}
		}
		if (hasBottomLights){
			if (!connectedBottom){
//				batch.draw(LightAssets.bottom1, sPos.x, sPos.y);
			}
			else {
				slotLightsOn = true;
				batch.draw(LightAssets.bottom2, sPos.x, sPos.y, 216, 216);
			}
		}

		if (connected || slotLightsOn) batch.draw(LightAssets.slotLight2, sPos.x, sPos.y, 216, 216);
		else batch.draw(LightAssets.slotLight1, sPos.x, sPos.y, 216, 216);
	}

	private void renderCoin(SpriteBatch batch){
		if (coinQueue != null && !coinQueue.contains(null, true) && renderCoins && coinQueue.size != 0){
			currentCoin = coinQueue.first();
			currentCoin.render(batch, sPos.x, sPos.y);
		}
	}
	
	public void resetAll(){
		currentCoin = null;
		coinQueue.clear();
		coinQueue = null;
		
		queueForRecreate = false;
		renderCoins = true;
		connected = false;
		connectedTop = false;
		connectedTopLeft = false;
		connectedTopRight = false;
		connectedLeft = false;
		connectedRight = false;
		connectedBottomLeft = false;
		connectedBottomRight = false;
		connectedBottom = false;
		isOnFire = false;
	}

	public boolean checkIfConnected(){
		if (connectedTop || connectedTopLeft || connectedTopRight || connectedLeft || connectedRight || connectedBottomLeft || 
				connectedBottomRight || connectedBottom) return true;
		return false;
	}
	
	public boolean hasCoins(){
		return coinQueue != null && !coinQueue.contains(null, false) && getCurrentCoin() != null;
	}

	public Coin getCurrentCoin() {
		return currentCoin;
	}

	public Array<Coin> getCoinQueue() {
		return coinQueue;
	}

	public void setCoinQueue(Array<Coin> coinQueue) {
		this.coinQueue = coinQueue;
	}

	public SlotPosition getsPos() {
		return sPos;
	}
	
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public void dispose() {
		if (sPos != null) sPos.dispose();
		sPos = null;
		if (coinQueue != null){
			coinQueue.clear();
		}
		coinQueue = null;
		currentCoin = null;
	}
}
