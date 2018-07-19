package com.connectcoins.combo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.game.ConnectCoins;

public class ComboAssets implements Disposable {
	public static TextureRegion bigFlushPopup;
	public static TextureRegion flushPopup;
	public static TextureRegion smallFlushPopup;
	public static TextureRegion fiveOfAKindPopup;
	public static TextureRegion fourOfAKindPopup;
	public static TextureRegion threeOfAKindPopup;
	
	public static TextureRegion mPBigFlushPopup;
	public static TextureRegion mPFlushPopup;
	public static TextureRegion mPSmallFlushPopup;
	public static TextureRegion mPFiveOfAKindPopup;
	public static TextureRegion mPFourOfAKindPopup;
	public static TextureRegion mPThreeOfAKindPopup;
	
	public ComboAssets(ConnectCoins game){
		bigFlushPopup = new TextureRegion(game.assetLoader.getTexture("multBigFlush"));
		flushPopup = new TextureRegion(game.assetLoader.getTexture("multFlush"));
		smallFlushPopup = new TextureRegion(game.assetLoader.getTexture("multSmallFlush"));
		fiveOfAKindPopup = new TextureRegion(game.assetLoader.getTexture("multFiveOfAKind"));
		fourOfAKindPopup = new TextureRegion(game.assetLoader.getTexture("multFourOfAKind"));
		threeOfAKindPopup = new TextureRegion(game.assetLoader.getTexture("multThreeOfAKind"));
		
//		if (game.gMConfig.mode == GameMode.MULTIPLAYER){
//			TextureAtlas mPPopupsAtlas = game.assetLoader.getTextureAtlas("multiplayerPopups");
//			mPBigFlushPopup = mPPopupsAtlas.findRegion("bigFlush");
//			mPFlushPopup = mPPopupsAtlas.findRegion("flush");
//			mPSmallFlushPopup = mPPopupsAtlas.findRegion("smallFlush");
//			mPFiveOfAKindPopup = mPPopupsAtlas.findRegion("fiveOfAKind");
//			mPFourOfAKindPopup = mPPopupsAtlas.findRegion("fourOfAKind");
//			mPThreeOfAKindPopup = mPPopupsAtlas.findRegion("threeOfAKind");
//		}
	}

	@Override
	public void dispose() {
		bigFlushPopup = null;
		flushPopup = null;
		smallFlushPopup = null;
		fiveOfAKindPopup = null;
		fourOfAKindPopup = null;
		threeOfAKindPopup = null;
		
		mPBigFlushPopup = null;
		mPFlushPopup = null;
		mPSmallFlushPopup = null;
		mPFiveOfAKindPopup = null;
		mPFourOfAKindPopup = null;
		mPThreeOfAKindPopup = null;
	}
}
