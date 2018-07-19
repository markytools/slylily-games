package com.junkworld.game;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class LoadingScreen implements Screen {
	protected JunkWorld game;
	private OrthographicCamera camera;
	private Batch batch;
	private Texture splashScreen, text;
	protected boolean loadMainMenu = false;
	private AssetManager manager;
//	private IActivityRequestHandler myRequestHandler;
	private boolean isLoading = false;

	public LoadingScreen(final JunkWorld game) {
		this.game = game;
		manager = new AssetManager();

		manager.load("mainMenuAssets/loadingAssets/blackShader.png", Texture.class);
		manager.load("mainMenuAssets/loadingAssets/loadingCircularSigns.png", Texture.class);

		manager.load("splashScreenAssets/splashscreen.png", Texture.class);

		//		manager.load("shopScreenAssets/jcBoost1.png", Texture.class);
		//		manager.load("shopScreenAssets/jcBoost2.png", Texture.class);
		//		manager.load("shopScreenAssets/jcBoost3.png", Texture.class);
		//		manager.load("shopScreenAssets/xpBoost1.png", Texture.class);
		//		manager.load("shopScreenAssets/xpBoost2.png", Texture.class);
		//		manager.load("shopScreenAssets/xpBoost3.png", Texture.class);
		//		manager.load("shopScreenAssets/gameBoosters.png", Texture.class);
		//		manager.load("shopScreenAssets/accessories.png", Texture.class);
		//		manager.load("shopScreenAssets/scoreBoost.png", Texture.class);
		//		manager.load("shopScreenAssets/trashCans.png", Texture.class);
		//		manager.load("shopScreenAssets/buy.png", Texture.class);

		//		load managers here!!! TODO
		splashScreen = new Texture(Gdx.files.internal("splashScreenAssets/splashscreen.png"));
		text = new Texture(Gdx.files.internal("backgrounds/text.png"));

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		Timer endSplash = new Timer();
		endSplash.schedule(new TimerTask(){
			@Override
			public void run() {
				loadMainMenu = true;
			}
		}, 1000);
		TimeUtils.millis();
	}

	@Override
	public void render(float delta) {
		if (!isLoading){
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			camera.update();

			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(splashScreen, 0, 0);
			batch.draw(text, 0, 0);
			batch.end();

			if (loadMainMenu) {
				if (manager.update()){
					batch.dispose();
					splashScreen.dispose();
					text.dispose();
					game.setScreen(new ProfileSelectionScreen(game, manager));
				}
			}
		}
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
	}

}
