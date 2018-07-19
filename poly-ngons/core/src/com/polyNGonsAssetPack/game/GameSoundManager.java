package com.polyNGonsAssetPack.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polyngons.game.PolyNGons;

public class GameSoundManager {
	private PolyNGons game;
	private SpriteBatch uiBatch;
	
	public GameSoundManager(PolyNGons game){
		this.game = game;
	}
	
	public void playSound(String soundID){
		if (game.getUser().getBoolean("sounds")){
			game.batch.flush();
			uiBatch.flush();
			game.assetM.get(game.soundID.get(soundID)).stop();
			game.assetM.get(game.soundID.get(soundID)).play();
		}
	}
	
	public void stopSound(String soundID){
		game.assetM.get(game.soundID.get(soundID)).stop();
	}

	public SpriteBatch getUiBatch() {
		return uiBatch;
	}

	public void setUiBatch(SpriteBatch uiBatch) {
		this.uiBatch = uiBatch;
	}
}
