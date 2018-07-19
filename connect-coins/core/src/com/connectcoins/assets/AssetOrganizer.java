package com.connectcoins.assets;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.connectcoins.game.ConnectCoins;

public class AssetOrganizer {
	private ConnectCoins game;
	public ArrayMap<AssetLabel, AssetDescriptor<TextureAtlas>> textureAtlasAssets;
	public ArrayMap<AssetLabel, AssetDescriptor<Texture>> textureAssets;
	public ArrayMap<AssetLabel, AssetDescriptor<Sound>> soundAssets;
	public ArrayMap<AssetLabel, AssetDescriptor<Music>> musicAssets;
	
	public AssetOrganizer(ConnectCoins game){
		this.game = game;
		textureAtlasAssets = new ArrayMap<AssetLabel, AssetDescriptor<TextureAtlas>>();
		textureAssets = new ArrayMap<AssetLabel, AssetDescriptor<Texture>>();
		soundAssets = new ArrayMap<AssetLabel, AssetDescriptor<Sound>>();
		musicAssets = new ArrayMap<AssetLabel, AssetDescriptor<Music>>();
		initAssetDescriptors();
	}
	
	private void initAssetDescriptors(){
		TextureParameter param = new TextureParameter();
		boolean isWebGL = Gdx.app.getType() == ApplicationType.WebGL;
		param.genMipMaps = !isWebGL;
		
		int graphicsSettings = game.pUpdater.getGraphics();
		if (!isWebGL) {
			switch (graphicsSettings){
			case 1: {
				param.magFilter = TextureFilter.MipMapLinearNearest;
				param.minFilter = TextureFilter.MipMapLinearNearest;
			}; break;
			case 2: {
				param.magFilter = TextureFilter.MipMapNearestLinear;
				param.minFilter = TextureFilter.MipMapNearestLinear;
			}; break;
			case 3: {
				param.magFilter = TextureFilter.MipMapLinearLinear;
				param.minFilter = TextureFilter.MipMapLinearLinear;
			}; break;
			default: break;
			}
		}

//		gameAssets/gameBG/
//		gameAssets/awards/achievements/
//		utils/fonts/backup/
		
//		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "averageB1"),
//				new AssetDescriptor<Texture>(("common/buttons/averageB1.png"), Texture.class, param));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-NORMAL", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-NORMAL/coins-NORMAL.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-AUD", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-AUD/coins-AUD.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-BRL", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-BRL/coins-BRL.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-CAD", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-CAD/coins-CAD.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-CHF", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-CHF/coins-CHF.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-clover", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-clover/coins-clover.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-CNY", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-CNY/coins-CNY.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-diamond", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-diamond/coins-diamond.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-EURO", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-EURO/coins-EURO.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-GBP", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-GBP/coins-GBP.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-heart", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-heart/coins-heart.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-INR", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-INR/coins-INR.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-JPY", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-JPY/coins-JPY.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-KRW", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-KRW/coins-KRW.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-MXN", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-MXN/coins-MXN.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-NZD", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-NZD/coins-NZD.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-PHP", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-PHP/coins-PHP.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-RUB", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-RUB/coins-RUB.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-SEK", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-SEK/coins-SEK.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-SGP", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-SGP/coins-SGP.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-spade", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-spade/coins-spade.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.COINS, "coins-USD", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("coins/coins-USD/coins-USD.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-NORMAL", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-NORMAL.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-AUD", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-AUD.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-BRL", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-BRL.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-CAD", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-CAD.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-CHF", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-CHF.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-clover", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-clover.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-CNY", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-CNY.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-diamond", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-diamond.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-EURO", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-EURO.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-GBP", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-GBP.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-heart", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-heart.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-INR", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-INR.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-JPY", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-JPY.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-KRW", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-KRW.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-MXN", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-MXN.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-NZD", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-NZD.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-PHP", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-PHP.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-RUB", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-RUB.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-SEK", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-SEK.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-SGP", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-SGP.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-spade", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-spade.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.STORE, "coinPic-USD", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/coinPics/coin-USD.png"), Texture.class, param));
		

		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "slots-NORMAL", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("platform/NORMAL/slots/slots.png"), Texture.class, param));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "lights-NORMAL", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("platform/NORMAL/lights/lights.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.SPLASHSCREEN, "splashscreen", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(("gameAssets/layouts/splashscreen.png"), Texture.class, param));

		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "scoreboard", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/scoreboard.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "scoreboard2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/scoreboard2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALWAYS, "gradBG", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(("gameAssets/gameBG/gradBG.png"), Texture.class, param));
		
		Array<String> coinSpriteAtlasesStr = new Array<String>();
		coinSpriteAtlasesStr.add("A");
		coinSpriteAtlasesStr.add("B");
		coinSpriteAtlasesStr.add("C");
		coinSpriteAtlasesStr.add("D");
		int count = 2;
		while (count != 0){
			String randomCSAStr = coinSpriteAtlasesStr.random();
			textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "coinSprite" + randomCSAStr, AssetLabel.FileDest.INTERNAL),
					new AssetDescriptor<TextureAtlas>(("gameAssets/gameBG/coinSprite" + randomCSAStr + ".atlas"), TextureAtlas.class));
			coinSpriteAtlasesStr.removeValue(randomCSAStr, false);
			count--;
		}
//		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "coinSpriteA"), 
//				new AssetDescriptor<TextureAtlas>(("gameAssets/gameBG/coinSpriteA.atlas"), TextureAtlas.class));
//		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "coinSpriteB"), 
//				new AssetDescriptor<TextureAtlas>(("gameAssets/gameBG/coinSpriteB.atlas"), TextureAtlas.class));
//		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "coinSpriteC"), 
//				new AssetDescriptor<TextureAtlas>(("gameAssets/gameBG/coinSpriteC.atlas"), TextureAtlas.class));
//		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "coinSpriteD"), 
//				new AssetDescriptor<TextureAtlas>(("gameAssets/gameBG/coinSpriteD.atlas"), TextureAtlas.class));
		
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "starCoin", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(("gameAssets/gameBG/starCoin.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "checkButton1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/checkButton1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "checkButton2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/checkButton2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "checkButton3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/checkButton3.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "xButton1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/xButton1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "xButton2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/xButton2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "xButton3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/xButton3.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "backButton1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/backButton1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "backButton2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/backButton2.png"), Texture.class, param));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "loadingCircleAtlas", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/displays/loadingCircle/loadingCircle.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "title", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(("gameAssets/gameBG/title.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "challenges", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(("gameAssets/gameBG/challenges.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "multiplayer", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(("gameAssets/gameBG/multiplayer.png"), Texture.class, param));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "challenge1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/levels/challenge1/challenge1.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "challenge2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/levels/challenge2/challenge2.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "challenge3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/levels/challenge3/challenge3.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "challenge4", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/levels/challenge4/challenge4.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "challenge5", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/levels/challenge5/challenge5.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "solved", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/levels/solved/solved.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "swipeScreen", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/levels/swipeScreen.png"), Texture.class, param));
		
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "playerInfoLayout", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/playerInfoLayout.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "playerInfoTitle", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/playerInfo.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "connectCoinSymbol", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/connectCoinSymbol.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "topPlayers", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/topPlayers/topPlayers.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "topPlayersBG", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/topPlayers/topPlayersBG.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "topPlayersCorner", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/topPlayers/topPlayersCorner.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "topPVScrollBar", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/topPlayers/vScrollBar.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "topPVScrollBarKnob", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/topPlayers/vScrollBarKnob.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "topPlayerSelection", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/topPlayers/selection.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "cBBody", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/circularBar/body.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "cBCircle", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/circularBar/circle.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "cBMatchFound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/circularBar/matchFound.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "opponentTextField", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/textField.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "levelBar1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/level/bar1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "levelBar2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/level/bar2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "levelBar3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/level/bar3.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "textCursor", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/textCursor.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "textCursorSmall", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/textCursorSmall.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "textSelectionSmall", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/fSelectionSmall.png"), Texture.class, param));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "matchmakeAtlas", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/multiplayerAssets/matchmake/matchmake.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "playersScreenAtlas", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/multiplayerAssets/players/players.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.MULTIPLAYER, "onlineStoreAtlas", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/multiplayerAssets/onlineStore/onlineStore.atlas"), TextureAtlas.class));

		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "buttonIcons", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/buttonIcons/buttonIcons.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "buttonIconsGreyscale", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/buttonIcons/buttonIconsGreyscale.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "largeButtonDisabled", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/largeButtonDisabled.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "averageButtonDisabled", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/gameButtons/averageButtonDisabled.png"), Texture.class, param));

		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "storeVScrollBar", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/vScrollBar.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "itemButtonLayout1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/itemLayout/item1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "itemButtonLayout2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/itemLayout/item2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "itemButtonLayout3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/itemLayout/item3.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "storeOuterFrameBG", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerFrameBG.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "dollarSymbol", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/dollarSymbol.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "setCurrentItemImg", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/setImage.png"), Texture.class, param));
		
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "amountButton1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/amountButton1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "amountButton2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/amountButton2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "buyButtonPlace", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/buyButtonPlace.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "ccBuy1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/ccBuy1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "ccBuy2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/ccBuy2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "ccBuy3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/ccBuy3.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "iAPBuy1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/iAPBuy1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "iAPBuy2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/iAPBuy2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "iAPBuy3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/iAPBuy3.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "setButton1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/setButton1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "setButton2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/setButton2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "setButton3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/storeAssets/outerButtons/setButton3.png"), Texture.class, param));
		
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.STORE, "storeAssetsAtlas", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/storeAssets/storeAssets.atlas"), TextureAtlas.class));

		textureAssets.put(new AssetLabel(AssetScreenSpace.AWARDS, "achievementText", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/awards/achievements/achievements.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "achievementFrame", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/awards/achievements/frame.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.AWARDS, "achievementImgBG1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/awards/achievements/imgBG1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.AWARDS, "achievementImgBG2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/awards/achievements/imgBG2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.AWARDS, "achievementPane", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/awards/achievements/pane.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "achievementVScrollBar", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/awards/achievements/vScrollBar.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.AWARDS, "achievementBorder", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/awards/achievements/border.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.AWARDS, "statisticsText", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/awards/statistics/statisticsText.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.AWARDS, "statisticsPane", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/awards/statistics/statisticsPane.png"), Texture.class, param));

		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.OPTIONS, "optionsAtlas", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/options/options.atlas"), TextureAtlas.class));

		soundAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "purchaseSound1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/purchase/1.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "purchaseSound2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/purchase/2.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "purchaseSound3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/purchase/3.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "purchaseSound4", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/purchase/4.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "setCoinTextureSound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/setCoinTexture.mp3"), Sound.class));

		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "averageButton", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/gameButtons/averageButton.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "largeButton", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/gameButtons/largeButton.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "blackBG", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/layouts/blackBG.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "comboList", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/comboList.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "cListVScroll", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/comboListAssets/vScroll.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "cListVScrollKnob", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/comboListAssets/vScrollKnob.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "upDownScrollArrow", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/comboListAssets/upDownArrow.png"), Texture.class, param));

		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "achievementIcons", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/layouts/achievementIcons.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "achievementUnlocked", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/layouts/achievementUnlocked/achievementUnlocks.atlas"),
						TextureAtlas.class));

		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "ccSymbol", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/cc.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "lvlSymbol", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/lvl.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "playerNameSymbol", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/playerName.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "combosSymbol", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/combos.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "scoreSymbol", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/score.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "timerSymbol", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/timer.png"), Texture.class, param));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "readyAnims", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/displays/ready/ready.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "readyNum", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/displays/ready/readyNum.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "connectText", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/ready/connect.png"), Texture.class, param));
		
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "timerBG1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/timerBG1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "timerBG2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/timerBG2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "timerPlot", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/timerPlot.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireBar1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/onFireBar1.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireBar2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/onFireBar2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireBar3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/onFireBar3.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireBarLayout", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/onFireBarLayout.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireBarLayout2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/onFireBarLayout2.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireBarLayout3", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/onFireBarLayout3.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireBarLayout4", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/onFireBarLayout4.png"), Texture.class, param));
		for (int i = 1; i <= 5; i++){
			textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireBarGlow" + i, AssetLabel.FileDest.INTERNAL),
					new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/onFireGlow/glow" + i + ".png"), Texture.class, param));
		}
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "inGamePanel", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/inGameMenu/panel.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "scoreboardText", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/scoreboardText.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "tutorialText", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/tutorialText.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "scoreboardMult", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/scoreboardMult.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "tutorialSPBG", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/tutorialSPBG.png"), Texture.class, param));

		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "coinFiguresAtlas", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/multiplayerAssets/coinFigures/coinFigures.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "miniShine", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/miniShine.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "vsText", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/vs.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "rankDown", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/results/rankDown.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "rankUp", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/results/rankUp.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "winnerMult", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/multiplayerAssets/results/winner.png"), Texture.class, param));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "multiplayerPopups", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/multiplayerAssets/popups/multiplayerPopups.atlas"), TextureAtlas.class));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "rankLogo", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/displays/rank.png"), Texture.class, param));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.ALL, "emoticons", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/multiplayerAssets/emoticons/emoticons.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "inGameAtlas", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/multiplayerAssets/inGame/inGame.atlas"), TextureAtlas.class));
		textureAtlasAssets.put(new AssetLabel(AssetScreenSpace.MAIN_MENU_ASSETS, "messagesNotifAtlas", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<TextureAtlas>(Gdx.files.internal("gameAssets/multiplayerAssets/messages/messages.atlas"), TextureAtlas.class));
		
		for (int i = 1; i <= 10; i++){
			textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireImg" + String.valueOf(i), AssetLabel.FileDest.INTERNAL),
					new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/popups/onFire/" + String.valueOf(i) + ".png"), Texture.class, param));
		}
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "multBigFlush", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/popups/bigFlush.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "multFiveOfAKind", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/popups/fiveOfAKind.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "multFlush", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/popups/flush.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "multFourOfAKind", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/popups/fourOfAKind.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "multSmallFlush", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/popups/smallFlush.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "multThreeOfAKind", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("gameAssets/popups/threeOfAKind.png"), Texture.class, param));
		
		textureAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "shine", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("coins/shine.png"), Texture.class, param));
		
		soundAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "winDrumRoll", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/multiplayer/winDrumRoll.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "achieveSound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/achieve.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "goSound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/go.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "onFireSound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/onFire.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "illuminateSound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/illuminate.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.ALL, "noIlluminate", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/noIlluminate.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "coinTouch", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/coinTouch.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "timesUpSound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/timesUp.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "miniCoinOnFire", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/multiplayer/miniCoinOnFire.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.ALL, "matchFoundSound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/friendNotif.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.ALL, "friendNotifSound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/matchFound.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.ALL, "normalBClick1", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/normalBClick.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.ALL, "normalBClick2", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/normalBClick2.mp3"), Sound.class));
		soundAssets.put(new AssetLabel(AssetScreenSpace.ALL, "glitterSound", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Sound>(Gdx.files.internal("audio/glitter.mp3"), Sound.class));
		for (int i = 1; i <= 8; i++){
			soundAssets.put(new AssetLabel(AssetScreenSpace.GAME_SCREEN, "illuminate" + i, AssetLabel.FileDest.INTERNAL),
					new AssetDescriptor<Sound>(Gdx.files.internal("audio/illuminate" + i + ".mp3"), Sound.class));
		}
		
		for (int i = 1; i <= 21; i++){
			musicAssets.put(new AssetLabel(AssetScreenSpace.MUSIC, "music" + i, AssetLabel.FileDest.INTERNAL),
					new AssetDescriptor<Music>(Gdx.files.internal("audio/music/music" + i + ".mp3"), Music.class));
		}
		
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "circularBar", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("debugging/circularBar.png"), Texture.class, param));
		textureAssets.put(new AssetLabel(AssetScreenSpace.ALL, "testFace", AssetLabel.FileDest.INTERNAL),
				new AssetDescriptor<Texture>(Gdx.files.internal("debugging/test.png"), Texture.class, param));
	}
}
