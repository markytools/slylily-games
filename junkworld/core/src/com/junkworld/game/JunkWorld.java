package com.junkworld.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class JunkWorld extends Game {
	
//	public IActivityRequestHandler myRequestHandler;
	public static Sprite loading;
//	public AdManager adManager;
	public JunkWorldPurchaser jkPurchaser;
	
	public JunkWorld(){
	}
	
	@Override
	public void create() {		
		loading = new Sprite(new Texture(Gdx.files.internal("mainMenuAssets/loadingAssets/loadingCircleArrows0.png")));
		loading.setOrigin(loading.getWidth() / 2, loading.getHeight() / 2);
		loading.setPosition(64, 208);
//		adManager = new AdManager(this);
//		jkPurchaser = new JunkWorldPurchaser(this);
		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void dispose() {
//		PurchaseSystem.dispose();
	}

	@Override
	public void render() {	
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
