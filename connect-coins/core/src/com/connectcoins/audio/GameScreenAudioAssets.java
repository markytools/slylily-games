package com.connectcoins.audio;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.game.ConnectCoins;

public class GameScreenAudioAssets implements Disposable {
	public static Sound noIlluminate;
	public static Sound coinTouch;
	public static Sound illuminate1;
	public static Sound illuminate2;
	public static Sound illuminate3;
	public static Sound illuminate4;
	public static Sound illuminate5;
	public static Sound illuminate6;
	public static Sound illuminate7;
	public static Sound illuminate8;
	public static Sound illuminate;
	public static Sound onFire;
	public static Sound timesUp;
	public static Sound go;

	public GameScreenAudioAssets(ConnectCoins game){
		noIlluminate = game.assetLoader.getSound("noIlluminate");
		coinTouch = game.assetLoader.getSound("coinTouch");
		illuminate1 = game.assetLoader.getSound("illuminate1");
		illuminate2 = game.assetLoader.getSound("illuminate2");
		illuminate3 = game.assetLoader.getSound("illuminate3");
		illuminate4 = game.assetLoader.getSound("illuminate4");
		illuminate5 = game.assetLoader.getSound("illuminate5");
		illuminate6 = game.assetLoader.getSound("illuminate6");
		illuminate7 = game.assetLoader.getSound("illuminate7");
		illuminate8 = game.assetLoader.getSound("illuminate8");
		onFire = game.assetLoader.getSound("onFireSound");
		illuminate = game.assetLoader.getSound("illuminateSound");
		timesUp = game.assetLoader.getSound("timesUpSound");
		go = game.assetLoader.getSound("goSound");
	}

	@Override
	public void dispose() {
//		noIlluminate.dispose();
//		coinTouch.dispose();
//		illuminate1.dispose();
//		illuminate2.dispose();
//		illuminate3.dispose();
//		illuminate4.dispose();
//		illuminate5.dispose();
//		illuminate6.dispose();
//		illuminate7.dispose();
//		illuminate8.dispose();
//		illuminate.dispose();
//		onFire.dispose();
//		timesUp.dispose();
//		go.dispose();
		noIlluminate = null;
		coinTouch = null;
		illuminate2 = null;
		illuminate2 = null;
		illuminate3 = null;
		illuminate4 = null;
		illuminate5 = null;
		illuminate6 = null;
		illuminate7 = null;
		illuminate8 = null;
		illuminate = null;
		onFire = null;
		timesUp = null;
		go = null;
	}
}
