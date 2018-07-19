package com.connectcoins.store;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.game.ConnectCoins;

public class PurchaseSoundPlayer implements Disposable {
	private Array<Sound> purchaseSounds;

	public PurchaseSoundPlayer(ConnectCoins game){
		purchaseSounds = new Array<Sound>();
		purchaseSounds.add(game.assetLoader.getSound("purchaseSound1"));
		purchaseSounds.add(game.assetLoader.getSound("purchaseSound2"));
		purchaseSounds.add(game.assetLoader.getSound("purchaseSound3"));
		purchaseSounds.add(game.assetLoader.getSound("purchaseSound4"));
	}

	public void playPurchaseSound(){
		purchaseSounds.random().play();
	}

	@Override
	public void dispose() {
		if (purchaseSounds != null){
//			for (Sound sound : purchaseSounds){
//				sound.dispose();
//			}
			purchaseSounds.clear();
			purchaseSounds = null;
		}
	}
}