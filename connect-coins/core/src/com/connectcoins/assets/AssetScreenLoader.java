package com.connectcoins.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.connectcoins.coins.CoinAssets.CoinCurrency;
import com.connectcoins.game.ConnectCoins;

public class AssetScreenLoader {
	public AssetManager manager;
	public AssetOrganizer assetOrg;
	public AssetTypeManager assetType;

	public Resolution[] resolutions;
	public static TextureAtlas currentCoinAtlas;
	public CustomResolutionFileResolver cRFileResolver;

	public AssetScreenLoader(ConnectCoins game){
		manager = new AssetManager();

		resolutions = new Resolution[10];
		resolutions[0] = new Resolution(240, 320, "SD");
		resolutions[1] = new Resolution(240, 400, "SD");
		resolutions[2] = new Resolution(240, 432, "SD");

		resolutions[3] = new Resolution(320, 480, "XSD");
		resolutions[4] = new Resolution(480, 800, "XSD");
		resolutions[5] = new Resolution(480, 854, "XSD");

		resolutions[6] = new Resolution(640, 960, "MD");
		resolutions[6] = new Resolution(640, 1136, "MD");
		resolutions[6] = new Resolution(720, 1280, "MD");
		resolutions[7] = new Resolution(768, 1280, "MD");
		resolutions[8] = new Resolution(1200, 1290, "MD");
		resolutions[8] = new Resolution(750, 1334, "MD");

		resolutions[9] = new Resolution(1080, 1920, "HD");

		boolean lowMemory = false;
//		if (game.appWarpManager.getTotalRam() < 600000000) lowMemory = true;
		cRFileResolver = new CustomResolutionFileResolver(new AutoFileResolver(), lowMemory, resolutions);

//		resolver.addExceptions("coins/coins-NORMAL/coins-NORMAL.atlas");
//		resolver.addExceptions("coins/coins-AUD/coins-AUD.atlas");
//		resolver.addExceptions("coins/coins-BRL/coins-BRL.atlas");
//		resolver.addExceptions("coins/coins-CAD/coins-CAD.atlas");
//		resolver.addExceptions("coins/coins-CHF/coins-CHF.atlas");
//		resolver.addExceptions("coins/coins-clover/coins-clover.atlas");
//		resolver.addExceptions("coins/coins-aCNY/coins-CNY.atlas");
//		resolver.addExceptions("coins/coins-diamond/coins-diamond.atlas");
//		resolver.addExceptions("coins/coins-EURO/coins-EURO.atlas");
//		resolver.addExceptions("coins/coins-GBP/coins-GBP.atlas");
//		resolver.addExceptions("coins/coins-heart/coins-heart.atlas");
//		resolver.addExceptions("coins/coins-INR/coins-INR.atlas");
//		resolver.addExceptions("coins/coins-JPY/coins-JPY.atlas");
//		resolver.addExceptions("coins/coins-KRW/coins-KRW.atlas");
//		resolver.addExceptions("coins/coins-MXN/coins-MXN.atlas");
//		resolver.addExceptions("coins/coins-NZD/coins-NZD.atlas");
//		resolver.addExceptions("coins/coins-PHP/coins-PHP.atlas");
//		resolver.addExceptions("coins/coins-RUB/coins-RUB.atlas");
//		resolver.addExceptions("coins/coins-SEK/coins-SEK.atlas");
//		resolver.addExceptions("coins/coins-SGP/coins-SGP.atlas");
//		resolver.addExceptions("coins/coins-spade/coins-spade.atlas");
//		resolver.addExceptions("coins/coins-USD/coins-USD.atlas");
//
//		resolver.addExceptions("coins/coinPics/coin-NORMAL.png");
//		resolver.addExceptions("coins/coinPics/coin-AUD.png");
//		resolver.addExceptions("coins/coinPics/coin-BRL.png");
//		resolver.addExceptions("coins/coinPics/coin-CAD.png");
//		resolver.addExceptions("coins/coinPics/coin-CHF.png");
//		resolver.addExceptions("coins/coinPics/coin-clover.png");
//		resolver.addExceptions("coins/coinPics/coin-CNY.png");
//		resolver.addExceptions("coins/coinPics/coin-diamond.png");
//		resolver.addExceptions("coins/coinPics/coin-EURO.png");
//		resolver.addExceptions("coins/coinPics/coin-GBP.png");
//		resolver.addExceptions("coins/coinPics/coin-heart.png");
//		resolver.addExceptions("coins/coinPics/coin-INR.png");
//		resolver.addExceptions("coins/coinPics/coin-JPY.png");
//		resolver.addExceptions("coins/coinPics/coin-KRW.png");
//		resolver.addExceptions("coins/coinPics/coin-MXN.png");
//		resolver.addExceptions("coins/coinPics/coin-NZD.png");
//		resolver.addExceptions("coins/coinPics/coin-PHP.png");
//		resolver.addExceptions("coins/coinPics/coin-RUB.png");
//		resolver.addExceptions("coins/coinPics/coin-SEK.png");
//		resolver.addExceptions("coins/coinPics/coin-SGP.png");
//		resolver.addExceptions("coins/coinPics/coin-spade.png");
//		resolver.addExceptions("coins/coinPics/coin-USD.png");
//
//		resolver.addExceptions("gameAssets/displays/comboList.png");
//		resolver.addExceptions("gameAssets/multiplayerAssets/coinFigures/coinFigures.atlas");
//		resolver.addExceptions("platform/NORMAL/slots/slots.png");

		manager.setLoader(Texture.class, new TextureLoader(cRFileResolver));
		manager.setLoader(TextureAtlas.class, new TextureAtlasLoader(cRFileResolver));
		manager.setLoader(Music.class, new MusicLoader(cRFileResolver));
		manager.setLoader(Sound.class, new SoundLoader(cRFileResolver));

		assetOrg = new AssetOrganizer(game);
		assetType = new AssetTypeManager(manager, assetOrg);
	}

	/**
	 * 
	 * @param assetScreen
	 * @param exemptedScreen assets not to unload
	 * @param finishLoading
	 * @param autoLoadALL
	 */
	public void loadScreenAssets(AssetScreenSpace assetScreen, AssetScreenSpace exemptedScreen, boolean finishLoading, boolean autoLoadALL){
		for (AssetScreenSpace space : AssetScreenSpace.values()) {
			if (space != AssetScreenSpace.ALL && space != AssetScreenSpace.ALWAYS && space != assetScreen && space != exemptedScreen) {
				unloadAsset(space);
			}
		}

		if (autoLoadALL){
			for (AssetLabel assetLabel : assetOrg.textureAtlasAssets.keys()){
				if (assetLabel.getAssetScreen() == assetScreen || assetLabel.getAssetScreen() == AssetScreenSpace.ALL
						|| assetLabel.getAssetScreen() == AssetScreenSpace.ALWAYS) {
					if (!manager.isLoaded(assetOrg.textureAtlasAssets.get(assetLabel).fileName)){
						autoSetAssetManagerResolver(assetLabel.getFileDest());
						manager.load(assetOrg.textureAtlasAssets.get(assetLabel));
					}
				}
			}
			for (AssetLabel assetLabel : assetOrg.textureAssets.keys()){
				if (assetLabel.getAssetScreen() == assetScreen || assetLabel.getAssetScreen() == AssetScreenSpace.ALL
						|| assetLabel.getAssetScreen() == AssetScreenSpace.ALWAYS) {
					if (!manager.isLoaded(assetOrg.textureAssets.get(assetLabel).fileName)){
						autoSetAssetManagerResolver(assetLabel.getFileDest());
						manager.load(assetOrg.textureAssets.get(assetLabel));
					}
				}
			}
			for (AssetLabel assetLabel : assetOrg.soundAssets.keys()){
				if (assetLabel.getAssetScreen() == assetScreen || assetLabel.getAssetScreen() == AssetScreenSpace.ALL
						|| assetLabel.getAssetScreen() == AssetScreenSpace.ALWAYS) {
					if (!manager.isLoaded(assetOrg.soundAssets.get(assetLabel).fileName)){
						autoSetAssetManagerResolver(assetLabel.getFileDest());
						manager.load(assetOrg.soundAssets.get(assetLabel));
					}
				}
			}
			for (AssetLabel assetLabel : assetOrg.musicAssets.keys()){
				if (assetLabel.getAssetScreen() == assetScreen || assetLabel.getAssetScreen() == AssetScreenSpace.ALL
						|| assetLabel.getAssetScreen() == AssetScreenSpace.ALWAYS) {
					if (!manager.isLoaded(assetOrg.musicAssets.get(assetLabel).fileName)){
						autoSetAssetManagerResolver(assetLabel.getFileDest());
						manager.load(assetOrg.musicAssets.get(assetLabel));
					}
				}
			}
		}
		else {
			for (AssetLabel assetLabel : assetOrg.textureAtlasAssets.keys()){
				if (assetLabel.getAssetScreen() == assetScreen || assetLabel.getAssetScreen() == AssetScreenSpace.ALWAYS) {
					if (!manager.isLoaded(assetOrg.textureAtlasAssets.get(assetLabel).fileName)){
						autoSetAssetManagerResolver(assetLabel.getFileDest());
						manager.load(assetOrg.textureAtlasAssets.get(assetLabel));
					}
				}
			}
			for (AssetLabel assetLabel : assetOrg.textureAssets.keys()){
				if (assetLabel.getAssetScreen() == assetScreen || assetLabel.getAssetScreen() == AssetScreenSpace.ALWAYS) {
					if (!manager.isLoaded(assetOrg.textureAssets.get(assetLabel).fileName)){
						autoSetAssetManagerResolver(assetLabel.getFileDest());
						manager.load(assetOrg.textureAssets.get(assetLabel));
					}
				}
			}
			for (AssetLabel assetLabel : assetOrg.soundAssets.keys()){
				if (assetLabel.getAssetScreen() == assetScreen || assetLabel.getAssetScreen() == AssetScreenSpace.ALWAYS) {
					if (!manager.isLoaded(assetOrg.soundAssets.get(assetLabel).fileName)){
						autoSetAssetManagerResolver(assetLabel.getFileDest());
						manager.load(assetOrg.soundAssets.get(assetLabel));
					}
				}
			}
			for (AssetLabel assetLabel : assetOrg.musicAssets.keys()){
				if (assetLabel.getAssetScreen() == assetScreen || assetLabel.getAssetScreen() == AssetScreenSpace.ALWAYS) {
					if (!manager.isLoaded(assetOrg.musicAssets.get(assetLabel).fileName)){
						autoSetAssetManagerResolver(assetLabel.getFileDest());
						manager.load(assetOrg.musicAssets.get(assetLabel));
					}
				}
			}
		}
		if (finishLoading) manager.finishLoading();
	}

	private void autoSetAssetManagerResolver(AssetLabel.FileDest fileDest){
		cRFileResolver.getBaseResolver().setFileDest(fileDest);
	}

	public TextureAtlas getTextureAtlas(String atlasID){
		for (AssetLabel assetLabel : assetOrg.textureAtlasAssets.keys()){
			if (assetLabel.getAssetID().equals(atlasID)){
				TextureAtlas atlas = manager.get(assetOrg.textureAtlasAssets.get(assetLabel));
				return atlas;
			}
		}
		return null;
	}

	public Texture getTexture(String assetID){
		for (AssetLabel assetLabel : assetOrg.textureAssets.keys()){
			if (assetLabel.getAssetID().equals(assetID)) {
				return manager.get(assetOrg.textureAssets.get(assetLabel));
			}
		}
		return null;
	}

	public Sound getSound(String assetID){
		for (AssetLabel assetLabel : assetOrg.soundAssets.keys()){
			if (assetLabel.getAssetID().equals(assetID)) {
				return manager.get(assetOrg.soundAssets.get(assetLabel));
			}
		}
		return null;
	}

	public Music getMusic(String assetID){
		for (AssetLabel assetLabel : assetOrg.musicAssets.keys()){
			if (assetLabel.getAssetID().equals(assetID)) {
				return manager.get(assetOrg.musicAssets.get(assetLabel));
			}
		}
		return null;
	}
	
	public void loadAndUnloadMusic(String musicToPlay){
		unloadAsset(AssetScreenSpace.MUSIC);
		for (AssetLabel assetLabel : assetOrg.musicAssets.keys()){
			if (assetLabel.getAssetID().equals(musicToPlay)) {
				manager.load(assetOrg.musicAssets.get(assetLabel)); 
			}
		}
	}

	/**
	 * You cannot unload an ALL ASSET!
	 * @param assetScreen
	 */
	public void unloadAsset(AssetScreenSpace assetScreen){
		for (AssetLabel assetLabel : assetOrg.textureAtlasAssets.keys()){
			if (assetLabel.getAssetScreen() == assetScreen) {
				String fileName = assetOrg.textureAtlasAssets.get(assetLabel).fileName;
				if (manager.isLoaded(fileName)) manager.unload(fileName);
			}
		}
		for (AssetLabel assetLabel : assetOrg.soundAssets.keys()){
			if (assetLabel.getAssetScreen() == assetScreen) {
				String fileName = assetOrg.soundAssets.get(assetLabel).fileName;
				if (manager.isLoaded(fileName)) manager.unload(fileName);
			}
		}
		for (AssetLabel assetLabel : assetOrg.musicAssets.keys()){
			if (assetLabel.getAssetScreen() == assetScreen) {
				String fileName = assetOrg.musicAssets.get(assetLabel).fileName;
				if (manager.isLoaded(fileName)) manager.unload(fileName);
			}
		}
		for (AssetLabel assetLabel : assetOrg.textureAssets.keys()){
			if (assetLabel.getAssetScreen() == assetScreen) {
				String fileName = assetOrg.textureAssets.get(assetLabel).fileName;
				if (manager.isLoaded(fileName)) manager.unload(fileName);
			}
		}
	}

	public void unloadTextureAtlas(String assetID){
		for (AssetLabel assetLabel : assetOrg.textureAtlasAssets.keys()){
			if (assetID.equals(assetLabel.getAssetID())) {
				String fileName = assetOrg.textureAtlasAssets.get(assetLabel).fileName;
				if (manager.isLoaded(fileName)) manager.unload(fileName);
				break;
			}
		}
	}

	/**
	 * Does not finish load assets.
	 * @param exceptCurrencyID
	 */
	public void unloadAndLoadCoinAssets(String exceptCurrencyID){
		Array<String> currencies = new Array<String>();
		currencies.add(CoinCurrency.NORMAL);
		currencies.add(CoinCurrency.AUD);
		currencies.add(CoinCurrency.BRL);
		currencies.add(CoinCurrency.CAD);
		currencies.add(CoinCurrency.CHF);
		currencies.add(CoinCurrency.CNY);
		currencies.add(CoinCurrency.EURO);
		currencies.add(CoinCurrency.GBP);
		currencies.add(CoinCurrency.INR);
		currencies.add(CoinCurrency.JPY);
		currencies.add(CoinCurrency.KRW);
		currencies.add(CoinCurrency.MXN);
		currencies.add(CoinCurrency.NZD);
		currencies.add(CoinCurrency.PHP);
		currencies.add(CoinCurrency.RUB);
		currencies.add(CoinCurrency.SEK);
		currencies.add(CoinCurrency.SGP);
		currencies.add(CoinCurrency.USD);
		currencies.add(CoinCurrency.CLOVER);
		currencies.add(CoinCurrency.DIAMOND);
		currencies.add(CoinCurrency.HEART);
		currencies.add(CoinCurrency.SPADE);

		for (String currencyID : currencies){
			String fileName = "coins/coins-" + currencyID + "/coins-" + currencyID + ".atlas";
			if (currencyID.equals(exceptCurrencyID)){
				if (!manager.isLoaded(fileName)) manager.load(fileName, TextureAtlas.class);
			}
			else {
				if (manager.isLoaded(fileName)) manager.unload(fileName);
			}
		}
	}

	public boolean checkIfTextureAtlasLoaded(String atlasID){
		for (AssetLabel assetLabel : assetOrg.textureAtlasAssets.keys()){
			if (assetLabel.getAssetID().equals(atlasID)){
				String fileName = assetOrg.textureAtlasAssets.get(assetLabel).fileName;
				if (manager.isLoaded(fileName)){
					return true;
				}
				else return false;
			}
		}
		return false;
	}

	public Resolution[] getResolutions() {
		return resolutions;
	}

	public AssetTypeManager getAssetType() {
		return assetType;
	}
}
