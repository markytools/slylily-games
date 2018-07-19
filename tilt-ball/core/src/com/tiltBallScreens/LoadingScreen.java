package com.tiltBallScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;
import com.handlers.MyAdActivity;
import com.tiltball.game.TiltBall;
import com.tiltball.game.TiltBallAssetsManager;
import com.tiltball.game.TiltBallEngines;

public class LoadingScreen extends TiltBallAssetsManager implements Screen {
	private long delayTitleScreen = TimeUtils.millis();
	private boolean isLoading = false;
	private TiltBall game;
	private Texture noSensor, deviceExit, brown;
	private boolean hasMotionSensor;
	private BitmapFont appExit;
	private long delayExitApp = TimeUtils.millis();
	private int countdown = 10;
	@SuppressWarnings("deprecation")
	public LoadingScreen(TiltBall game, MyAdActivity requestAd){
		this.game = game;
		mainCam = new OrthographicCamera();
		mainCam.setToOrtho(false, TiltBallEngines.MAP_WIDTH, TiltBallEngines.MAP_HEIGHT);
		assetManager.load("splashScreen.png", Texture.class);

		noSensor = new Texture(Gdx.files.internal("mainMenuAssets/noSensor.png"));
		deviceExit = new Texture(Gdx.files.internal("mainMenuAssets/deviceExit.png"));
		brown = new Texture(Gdx.files.internal("brownTrans.png"));

//		for Android Test
//		hasMotionSensor = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
//		for Desktop Test
		hasMotionSensor = true;
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SHOWG.TTF"));
		appExit = gen.generateFont(50);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (assetManager.update() && !isLoading){
			mainCam.update();
			batch.setProjectionMatrix(mainCam.combined);

			batch.begin();
			batch.draw((Texture) assetManager.get("splashScreen.png"), 0, 0, 512, 832);
			if (!hasMotionSensor){
				batch.draw(brown, 0, 0, 512, 832);
				batch.draw(noSensor, 64, 400);
				batch.draw(deviceExit, 64, 200);
				appExit.draw(batch, String.valueOf(countdown), 240, 225);
			}
			batch.end();

			if (hasMotionSensor){
				if (TimeUtils.millis() - delayTitleScreen >= 1500){
					delayTitleScreen = 0;
					assetManager.clear();
					isLoading = true;
					dispose();
					game.setScreen(new MainMenuScreen(game));
				}
			}
			else {
				if (TimeUtils.millis() - delayExitApp >= 1000){
					delayExitApp = TimeUtils.millis();
					countdown -= 1;
				}
				if (countdown <= 0){
					assetManager.clear();
					isLoading = true;
					dispose();
					Gdx.app.exit();
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
		appExit.dispose();
		noSensor.dispose();
		deviceExit.dispose();
		brown.dispose();
	}

}
