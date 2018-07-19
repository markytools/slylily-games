package com.connectcoins.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.audio.GameScreenAudioAssets;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.ConnectCoins;

public class GameReady implements Disposable {
	private ConnectCoins game;
	private Animation<TextureRegion> readyAnim, readyNumAnim;
	private TextureRegion finalReadyReg;
	private Sprite connectSprite;
	private float readyStateTime = 0, readyNumStatetime = 0, connectAlpha = 1, connectScale = .75f;
	private static int readyState;
	private boolean disable = false;
	private boolean goSoundPlayed = false;

	public GameReady(ConnectCoins game){
		this.game = game;

		Array<TextureRegion> readyNums;
		TextureAtlas readyNumAtlas = game.assetLoader.getTextureAtlas("readyNum");
		readyNums = new Array<TextureRegion>();
		readyNums.add(readyNumAtlas.findRegion("3"));
		readyNums.add(readyNumAtlas.findRegion("2"));
		readyNums.add(readyNumAtlas.findRegion("1"));
		readyNumAnim = new Animation<TextureRegion>(.2f, readyNums);
		
		Array<TextureRegion> readyTextures = new Array<TextureRegion>();
		TextureAtlas readyAtlas = game.assetLoader.getTextureAtlas("readyAnims");
		TextureRegion normalReady = readyAtlas.findRegion("8");
		for (int i = 0; i <= 8; i++){
			readyTextures.add(readyAtlas.findRegion(String.valueOf(i)));
		}
		for (int i = 0; i <= 10; i++){
			readyTextures.add(normalReady);
		}
		
		readyAnim = new Animation<TextureRegion>(0.07f, readyTextures);
		finalReadyReg = readyTextures.peek();
		connectSprite = new Sprite(game.assetLoader.getTexture("connectText"));
		connectSprite.setBounds(0, 850, 1080, 250);
		connectSprite.setOriginCenter();
		
		initReadyState();
	}
	
	public void render(SpriteBatch batch){
		if (!disable){
			switch (readyState){
			case 0: {
				readyStateTime += Gdx.graphics.getDeltaTime();
				TextureRegion currentReady = readyAnim.getKeyFrame(readyStateTime);
				batch.draw(currentReady, 0, 890, 1080, 250);
				
				if (readyAnim.isAnimationFinished(readyStateTime)) readyState++;
			}; break;
			case 1: {
				batch.draw(finalReadyReg, 0, 890, 1080, 250);
				readyNumStatetime += Gdx.graphics.getDeltaTime();
				TextureRegion currentReadyNum = readyNumAnim.getKeyFrame(readyNumStatetime);
				batch.draw(currentReadyNum, -400, 660, 1080, 250);
				if (readyNumAnim.isAnimationFinished(readyNumStatetime)) readyState++;
			}; break;
			case 2: {
				connectAlpha = MathUtils.clamp(connectAlpha - (Gdx.graphics.getDeltaTime() * 2f), 0, 1);
				connectScale = MathUtils.clamp(connectScale + Gdx.graphics.getDeltaTime(), .75f, 2);
				connectSprite.setAlpha(connectAlpha);
				connectSprite.setScale(connectScale);
				connectSprite.draw(batch);
				connectSprite.setBounds(0, 850, 1080, 250);
				connectSprite.setOriginCenter();
				
				if (connectAlpha <= 0){
					connectSprite.setBounds(0, 850, 1080, 250);
					connectAlpha = 0;
					readyState = -1;
				}
				if (!goSoundPlayed){
					goSoundPlayed = true;
					GameScreenAudioAssets.go.play();
				}
			}; break;
			case -2: {
				batch.draw(finalReadyReg, 0, 890, 1080, 250);
			}; break;
			default: break;
			}
		}
	}
	
	private void initReadyState(){
		if (game.gMConfig.mode == GameMode.NORMAL){
			readyState = 0;
		}
		else readyState = -1;
	}
	
	public void reset(){
		goSoundPlayed = false;
		readyStateTime = 0;
		readyNumStatetime = 0;
		initReadyState();
		connectAlpha = 1;
		connectScale = .75f;
		connectSprite.setAlpha(1);
		connectSprite.setScale(1);
	}
	
	public static void startReadyForMultiplayer(){
		if (readyState != -1) readyState = 0;
	}
	
	public boolean finishedReady(){
		return readyState == -1;
	}
	
	public void disableGameReady(boolean disable){
		this.disable = disable;
	}

	public boolean isDisable() {
		return disable;
	}

	@Override
	public void dispose() {
		readyStateTime = 0;
		readyNumStatetime = 0;
		connectAlpha = 0;
		connectScale = 0;
		readyState = 0;
		disable = false;
		goSoundPlayed = false;
		
		game = null;
		readyAnim = null;
		readyNumAnim = null;
		finalReadyReg = null;
		connectSprite = null;
	}
}