package com.connectcoins.slots;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.game.ConnectCoins;

public class LightAssets implements Disposable {
	private ConnectCoins game;

	public static TextureRegion bottomLeft1;
	public static TextureRegion bottomLeft2;
	public static TextureRegion bottomRight1;
	public static TextureRegion bottomRight2;
	public static TextureRegion bottom1;
	public static TextureRegion bottom2;
	public static TextureRegion left1;
	public static TextureRegion left2;
	public static TextureRegion right1;
	public static TextureRegion right2;
	public static TextureRegion topLeft1;
	public static TextureRegion topLeft2;
	public static TextureRegion topRight1;
	public static TextureRegion topRight2;
	public static TextureRegion top1;
	public static TextureRegion top2;
	public static TextureRegion slotLight1;
	public static TextureRegion slotLight2;
	
	public LightAssets(ConnectCoins game){
		this.game = game;
	}
	
	public void setLightsAssets(String assetType){
		game.assetLoader.assetType.setCurrentLightAtlas("lights-" + assetType);
		
		bottomLeft1 = game.assetLoader.assetType.getLightRegion("bottomLeft1");
		bottomLeft2 = game.assetLoader.assetType.getLightRegion("bottomLeft2");
		bottomRight1 = game.assetLoader.assetType.getLightRegion("bottomRight1");
		bottomRight2 = game.assetLoader.assetType.getLightRegion("bottomRight2");
		bottom1 = game.assetLoader.assetType.getLightRegion("bottom1");
		bottom2 = game.assetLoader.assetType.getLightRegion("bottom2");
		left1 = game.assetLoader.assetType.getLightRegion("left1");
		left2 = game.assetLoader.assetType.getLightRegion("left2");
		right1 = game.assetLoader.assetType.getLightRegion("right1");
		right2 = game.assetLoader.assetType.getLightRegion("right2");
		topLeft1 = game.assetLoader.assetType.getLightRegion("topLeft1");
		topLeft2 = game.assetLoader.assetType.getLightRegion("topLeft2");
		topRight1 = game.assetLoader.assetType.getLightRegion("topRight1");
		topRight2 = game.assetLoader.assetType.getLightRegion("topRight2");
		top1 = game.assetLoader.assetType.getLightRegion("top1");
		top2 = game.assetLoader.assetType.getLightRegion("top2");
		slotLight1 = game.assetLoader.assetType.getLightRegion("slotLight1");
		slotLight2 = game.assetLoader.assetType.getLightRegion("slotLight2");
	}

	@Override
	public void dispose() {
		game = null;

		bottomLeft1 = null;
		bottomLeft2 = null;
		bottomRight1 = null;
		bottomRight2 = null;
		bottom1 = null;
		bottom2 = null;
		left1 = null;
		left2 = null;
		right1 = null;
		right2 = null;
		topLeft1 = null;
		topLeft2 = null;
		topRight1 = null;
		topRight2 = null;
		top1 = null;
		top2 = null;
		slotLight1 = null;
		slotLight2 = null;
	}
}
