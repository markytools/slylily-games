package com.connectcoins.mainMenu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.game.ConnectCoins;

public class ChallengeAssets implements Disposable {
	public static Array<TextureRegion> solved;
	public static Array<TextureRegion> challenge1;
	public static Array<TextureRegion> challenge2;
	public static Array<TextureRegion> challenge3;
	public static Array<TextureRegion> challenge4;
	public static Array<TextureRegion> challenge5;
	
	public ChallengeAssets(ConnectCoins game){
		TextureAtlas solvedAtlas = game.assetLoader.getTextureAtlas("solved");
		solved = new Array<TextureRegion>();
		for (int i = 0; i < 8; i++){
			solved.add(solvedAtlas.findRegion(String.valueOf(i)));
		}
		solved.add(solvedAtlas.findRegion("0"));

		TextureAtlas challenge1Atlas = game.assetLoader.getTextureAtlas("challenge1");
		challenge1 = new Array<TextureRegion>();
		for (int i = 0; i < 8; i++){
			challenge1.add(challenge1Atlas.findRegion(String.valueOf(i)));
		}
		challenge1.add(solvedAtlas.findRegion("0"));

		TextureAtlas challenge2Atlas = game.assetLoader.getTextureAtlas("challenge2");
		challenge2 = new Array<TextureRegion>();
		for (int i = 0; i < 8; i++){
			challenge2.add(challenge2Atlas.findRegion(String.valueOf(i)));
		}
		challenge2.add(solvedAtlas.findRegion("0"));

		TextureAtlas challenge3Atlas = game.assetLoader.getTextureAtlas("challenge3");
		challenge3 = new Array<TextureRegion>();
		for (int i = 0; i < 8; i++){
			challenge3.add(challenge3Atlas.findRegion(String.valueOf(i)));
		}
		challenge3.add(solvedAtlas.findRegion("0"));

		TextureAtlas challenge4Atlas = game.assetLoader.getTextureAtlas("challenge4");
		challenge4 = new Array<TextureRegion>();
		for (int i = 0; i < 8; i++){
			challenge4.add(challenge4Atlas.findRegion(String.valueOf(i)));
		}
		challenge4.add(solvedAtlas.findRegion("0"));

		TextureAtlas challenge5Atlas = game.assetLoader.getTextureAtlas("challenge5");
		challenge5 = new Array<TextureRegion>();
		for (int i = 0; i < 8; i++){
			challenge5.add(challenge5Atlas.findRegion(String.valueOf(i)));
		}
		challenge5.add(solvedAtlas.findRegion("0"));
	}

	@Override
	public void dispose() {
//		for (TextureRegion tR : solved){
//			tR.getTexture().dispose();
//		}
//		for (TextureRegion tR : challenge1){
//			tR.getTexture().dispose();
//		}
//		for (TextureRegion tR : challenge2){
//			tR.getTexture().dispose();
//		}
//		for (TextureRegion tR : challenge3){
//			tR.getTexture().dispose();
//		}
//		for (TextureRegion tR : challenge4){
//			tR.getTexture().dispose();
//		}
//		for (TextureRegion tR : challenge5){
//			tR.getTexture().dispose();
//		}
		solved.clear();
		challenge1.clear();
		challenge2.clear();
		challenge3.clear();
		challenge4.clear();
		challenge5.clear();

		solved = null;
		challenge1 = null;
		challenge2 = null;
		challenge3 = null;
		challenge4 = null;
		challenge5 = null;
	}
}
