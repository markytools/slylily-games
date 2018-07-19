package com.connectcoins.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetTypeManager {
	private AssetManager manager;
	private AssetOrganizer assetOrg;
	
	private TextureAtlas currentCoinAtlas, currentSlotAtlas, currentLightAtlas;
	
	public AssetTypeManager(AssetManager manager, AssetOrganizer assetOrg){
		this.manager = manager;
		this.assetOrg = assetOrg;
	}
	
	public void setCurrentCoinAtlas(String coinID, boolean dispose){
//		if (currentCoinAtlas != null && dispose) currentCoinAtlas.dispose();
		for (AssetLabel assetLabel : assetOrg.textureAtlasAssets.keys()){
			if (assetLabel.getAssetID().contains("coins") && assetLabel.getAssetID().contains(coinID)){
//				manager.load(assetOrg.textureAtlasAssets.get(assetLabel));
//				manager.finishLoading();
				currentCoinAtlas = manager.get(assetOrg.textureAtlasAssets.get(assetLabel));
				break;
			}
		}
	}

	public TextureRegion getCoinRegion(String imageName){
		return currentCoinAtlas.findRegion(imageName);
	}
	
	

	public void setCurrentSlotAtlas(String slotID){
//		if (currentSlotAtlas != null) currentSlotAtlas.dispose();
		for (AssetLabel assetLabel : assetOrg.textureAtlasAssets.keys()){
			if (assetLabel.getAssetID().contains("slots") && assetLabel.getAssetID().contains(slotID)){
				manager.load(assetOrg.textureAtlasAssets.get(assetLabel));
				manager.finishLoading();
				currentSlotAtlas = manager.get(assetOrg.textureAtlasAssets.get(assetLabel));
				break;
			}
		}
	}

	public TextureRegion getSlotRegion(String imageName){
 		return currentSlotAtlas.findRegion(imageName);
	}
	
	

	public void setCurrentLightAtlas(String lightID){
//		if (currentLightAtlas != null) currentLightAtlas.dispose();
		for (AssetLabel assetLabel : assetOrg.textureAtlasAssets.keys()){
			if (assetLabel.getAssetID().contains("lights") && assetLabel.getAssetID().contains(lightID)){
				manager.load(assetOrg.textureAtlasAssets.get(assetLabel));
				manager.finishLoading();
				currentLightAtlas = manager.get(assetOrg.textureAtlasAssets.get(assetLabel));
				break;
			}
		}
	}

	public TextureRegion getLightRegion(String imageName){
 		return currentLightAtlas.findRegion(imageName);
	}
}
