package com.polyngons.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;
import com.polyNGonsMainMenu.game.MainMenuScreen;

public class SplashScreen implements Screen {
	
	private PolyNGons game;
	private long time;
	
	public SplashScreen(PolyNGons game){
		this.game = game;
		time = TimeUtils.millis();
	}

	@Override
	public void render(float delta) {

        game.batch.begin();
		game.batch.draw(game.splashImage, 0, 0, 800, 500);
		game.batch.end();
		
		if (TimeUtils.millis() - time >= 1000){
			game.setScreen(new MainMenuScreen(game));
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
