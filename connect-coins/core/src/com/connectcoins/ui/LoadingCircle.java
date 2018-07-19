package com.connectcoins.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.connectcoins.game.ConnectCoins;

public class LoadingCircle {
	private Animation<TextureRegion> loadingCircle;
	private float stateTime;
	private boolean render;
	
	public LoadingCircle(ConnectCoins game){
		stateTime = 0;
		TextureAtlas atlas = game.assetLoader.getTextureAtlas("loadingCircleAtlas");
		Array<TextureRegion> regs = new Array<TextureRegion>();
		for (int i = 1; i <= 12; i++){
			regs.add(atlas.findRegion(String.valueOf(i)));
		}
		loadingCircle = new Animation<TextureRegion>(0.05f, regs, PlayMode.LOOP);
		render = false;
	}
	
	public void start(){
		stateTime = 0;
		render = true;
	}
	
	public void stop(){
		stateTime = 0;
		render = false;
	}
	
	public void render(SpriteBatch batch){
		if (render){
			Color bColor = batch.getColor();
			batch.setColor(bColor.r, bColor.g, bColor.b, 0.75f);
			stateTime += Gdx.graphics.getDeltaTime();
			batch.draw(loadingCircle.getKeyFrame(stateTime), 340, 760, 400, 400);
			batch.setColor(bColor);
		}
	}
}
