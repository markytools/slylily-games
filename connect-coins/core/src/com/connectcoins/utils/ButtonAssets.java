package com.connectcoins.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.connectcoins.assets.AssetScreenSpace;
import com.connectcoins.game.ConnectCoins;

public class ButtonAssets {
	public final AssetScreenSpace assetScreenID = AssetScreenSpace.ALL;
	
	public static Array<TextureRegion> averageButton;
	public static TextureRegion averageButtonD, largeButtonD;
	public static Array<TextureRegion> largeButton;
	
	public ButtonAssets(ConnectCoins game){
		TextureAtlas averageBAtlas = game.assetLoader.getTextureAtlas("averageButton");
		averageButton = new Array<TextureRegion>();
		for (int i = 0; i < 13; i++){
			averageButton.add(averageBAtlas.findRegion(String.valueOf(i)));
		}
		averageButtonD = new TextureRegion(game.assetLoader.getTexture("averageButtonDisabled"));

		TextureAtlas largeBAtlas = game.assetLoader.getTextureAtlas("largeButton");
		largeButton = new Array<TextureRegion>();
		for (int i = 0; i < 15; i++){
			largeButton.add(largeBAtlas.findRegion(String.valueOf(i)));
		}
		largeButtonD = new TextureRegion(game.assetLoader.getTexture("largeButtonDisabled"));
	}
}
