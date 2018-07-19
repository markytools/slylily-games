package com.connectcoins.mainMenu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.connectcoins.game.ConnectCoins;

public class ButtonIconAssetManager {
	public TextureAtlas buttonIconsAtlas;
	public TextureAtlas buttonIconsAtlasBW;
	
	public ButtonIconAssetManager(ConnectCoins game){
		buttonIconsAtlas = game.assetLoader.getTextureAtlas("buttonIcons");
		buttonIconsAtlasBW = game.assetLoader.getTextureAtlas("buttonIconsGreyscale");
	}
}
