package com.connectcoins.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.connectcoins.game.ConnectCoins;

public class GameBackground {
	private class CoinBGAnimation {
		public Animation<TextureRegion> anim;
		public float stateTime = 0;
		public float dimensions;
		public float x = 0, y = 0;
		public boolean isVisible = false;
		public boolean dimSet = false;

		public CoinBGAnimation(Array<TextureRegion> coinBGRegions){
			int playMode = MathUtils.random(1, 2);
			PlayMode mode = (playMode == 1) ? Animation.PlayMode.LOOP : Animation.PlayMode.LOOP_REVERSED;
			anim = new Animation<TextureRegion>(0.05f, coinBGRegions, mode);
		}
	}

	private boolean resetOnce = false;
	private float fallSpeed = 1;
	private float fallingCoinAnimDur;
	private ConnectCoins game;
	private TextureRegion gradBG;
	private Array<TextureRegion> coinBGRegionsA, coinBGRegionsB, coinBGRegionsC, coinBGRegionsD;
	private Array<CoinBGAnimation> coinBGAnims;

	private boolean spawnCoin = true;
	private boolean bgOn = true;

	public GameBackground(ConnectCoins game){
		this.game = game;
	}

	private void initBG() {
		gradBG = new TextureRegion(game.assetLoader.getTexture("gradBG"));
		coinBGAnims = new Array<CoinBGAnimation>();
		coinBGRegionsA = new Array<TextureRegion>();
		coinBGRegionsB = new Array<TextureRegion>();
		coinBGRegionsC = new Array<TextureRegion>();
		coinBGRegionsD = new Array<TextureRegion>();


		//		for (int i = 0; i <= 60; i++){
		//			coinBGRegionsA.add(gradBAtlas.findRegion("fallingCoinA/" + String.valueOf(i)));
		//		}
		//		for (int i = 0; i <= 60; i++){
		//			coinBGRegionsB.add(gradBAtlas.findRegion("fallingCoinB/" + String.valueOf(i)));
		//		}
		//		for (int i = 0; i <= 60; i++){
		//			coinBGRegionsC.add(gradBAtlas.findRegion("fallingCoinC/" + String.valueOf(i)));
		//		}
		//		for (int i = 0; i <= 60; i++){
		//			coinBGRegionsD.add(gradBAtlas.findRegion("fallingCoinD/" + String.valueOf(i)));
		//		}

		TextureAtlas coinSpriteRegA = game.assetLoader.getTextureAtlas("coinSpriteA");
		TextureAtlas coinSpriteRegB = game.assetLoader.getTextureAtlas("coinSpriteB");
		TextureAtlas coinSpriteRegC = game.assetLoader.getTextureAtlas("coinSpriteC");
		TextureAtlas coinSpriteRegD = game.assetLoader.getTextureAtlas("coinSpriteD");
		for (int i = 0; i <= 60; i++){
			if (coinSpriteRegA != null) coinBGRegionsA.add(coinSpriteRegA.findRegion(String.valueOf(i)));
			else coinBGRegionsA = null;
			if (coinSpriteRegB != null) coinBGRegionsB.add(coinSpriteRegB.findRegion(String.valueOf(i)));
			else coinBGRegionsB = null;
			if (coinSpriteRegC != null) coinBGRegionsC.add(coinSpriteRegC.findRegion(String.valueOf(i)));
			else coinBGRegionsC = null;
			if (coinSpriteRegD != null) coinBGRegionsD.add(coinSpriteRegD.findRegion(String.valueOf(i)));
			else coinBGRegionsD = null;
		}
		
		coinBGAnims.add(new CoinBGAnimation(getRandomFallingCoinRegion()));
		coinBGAnims.get(0).isVisible = true;
	}

	private Array<TextureRegion> getRandomFallingCoinRegion(){
		Array<Array<TextureRegion>> coinSprites = new Array<Array<TextureRegion>>();
		if (coinBGRegionsA != null) coinSprites.add(coinBGRegionsA);
		if (coinBGRegionsB != null) coinSprites.add(coinBGRegionsB);
		if (coinBGRegionsC != null) coinSprites.add(coinBGRegionsC);
		if (coinBGRegionsD != null) coinSprites.add(coinBGRegionsD);
		return coinSprites.random();
	}

	private void startCoinAnim(){
		while (coinBGAnims.size < 30){
			coinBGAnims.add(new CoinBGAnimation(getRandomFallingCoinRegion()));
		}

		for (CoinBGAnimation cBGAnim : coinBGAnims){
			if (cBGAnim.isVisible && !cBGAnim.dimSet){
				cBGAnim.dimSet = true;
				cBGAnim.stateTime = MathUtils.random(0, 2f);
				cBGAnim.x = MathUtils.random(-192, 1016);
				cBGAnim.y = 1920;
				cBGAnim.dimensions = MathUtils.random(150, 256);
			}
		}

		for (CoinBGAnimation cBGAnim : coinBGAnims){
			if (cBGAnim.isVisible && cBGAnim.dimSet){
				cBGAnim.stateTime += Gdx.graphics.getDeltaTime();
				cBGAnim.y -= 100 * Gdx.graphics.getDeltaTime() * Math.pow(fallSpeed, 3);
				if (cBGAnim.y < MathUtils.random(1350, 1650)){
					int nextIndex = coinBGAnims.indexOf(cBGAnim, true) + 1;
					if (nextIndex < coinBGAnims.size){
						coinBGAnims.get(nextIndex).isVisible = true;
					}
				}

				if (cBGAnim.y < -256){
					coinBGAnims.removeValue(cBGAnim, true);
				}
			}
		}
		coinBGAnims.shrink();
	}

	private void moveCoins(SpriteBatch batch){
		if (spawnCoin) startCoinAnim();

		for (CoinBGAnimation cBGAnim : coinBGAnims){
			if (cBGAnim.isVisible){
				cBGAnim.anim.setFrameDuration(fallingCoinAnimDur);
				TextureRegion reg = cBGAnim.anim.getKeyFrame(cBGAnim.stateTime);
				batch.draw(reg, cBGAnim.x, cBGAnim.y, cBGAnim.dimensions, cBGAnim.dimensions);
			}
		}
	}

	private void render(SpriteBatch batch){
		Color color = batch.getColor();
		batch.setColor(Color.valueOf("f3d4a2"));
		batch.draw(gradBG, -180, 0, 1440, 1920);
		if (bgOn){
			fallingCoinAnimDur = 0.05f / (float) Math.pow(fallSpeed, 2);
			moveCoins(batch);
		}
		batch.setColor(color);
	}

	public void turnOnFallingCoinsBGAnim(boolean turnOn){
		this.bgOn = turnOn;
	}

	public void drawScreenBackground(SpriteBatch batch){
		batch.begin();
		render(batch);
		batch.end();
	}

	public void resetOnceGameBG(){
		if (!resetOnce){
			resetOnce = true;
			initBG();
		}
	}

	public boolean isSpawnCoin() {
		return spawnCoin;
	}

	public void setSpawnCoin(boolean spawnCoin) {
		this.spawnCoin = spawnCoin;
	}

	public void setFallSpeed(float fallSpeed) {
		this.fallSpeed = fallSpeed;
	}
}
