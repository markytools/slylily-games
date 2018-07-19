package com.junkworld.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ShopScreen implements Screen {
	private OrthographicCamera camera;
	private Batch batch;
	private Vector3 touchPos;
	private Stage stage;
	private AssetManager manager;
	public ShopScreen(JunkWorld game, AssetManager manager, JunkWorldEngines junkWorldEngines){
		this.manager = manager;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		batch = new SpriteBatch();
		touchPos = new Vector3(); 
		stage = new Stage();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		
		setBounds();
		drawBatches();
		
		stage.getViewport().setCamera(camera);
		stage.draw();
		stage.act();
		Gdx.input.setInputProcessor(stage);
	}

	private void setBounds() {
		// TODO Auto-generated method stub
		
	}

	private void drawBatches() {
		batch.begin();
		batch.draw(manager.get("backgrounds/firstBackground.png", Texture.class), 0, 0);
		batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader2.png", Texture.class), 0, 0, 512, 800);	
		batch.draw(manager.get("backgrounds/mainJunkworldFrame.png", Texture.class), 0, 0);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
