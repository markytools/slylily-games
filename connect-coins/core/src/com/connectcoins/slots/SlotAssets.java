package com.connectcoins.slots;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.game.ConnectCoins;

public class SlotAssets implements Disposable {
	public class Theme {
		public static final String NORMAL = "NORMAL";
	}
	
	private ConnectCoins game;

	public static Texture slotsImg;
	
	public SlotAssets(ConnectCoins game){
		this.game = game;
	}
	
	public void setSlotAssets(String slotID){
		slotsImg = game.assetLoader.getTexture("slots-NORMAL");
	}

	@Override
	public void dispose() {
		game = null;
		slotsImg = null;
	}
	
	
}
