package com.springdynamics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Mark Ty on 8/6/2016.
 */
public class LoadingScreen extends ScreenAdapter {
    private SpringDynamics game;
    private Texture splashscreen;
    private long time = -1;
    private boolean setNewScreen = false;

    public LoadingScreen(SpringDynamics game){
        this.game = game;

        splashscreen = new Texture(Gdx.files.internal("splashscreen.png"));
        time = TimeUtils.millis();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void render(float delta) {
        if (time == -1){
            time = TimeUtils.millis();
        }
        else {
            game.uiBatch.begin();
            game.uiBatch.draw(splashscreen, 0, 0, 800, 500);
            if (TimeUtils.millis() - time >= 1000){
                if (!setNewScreen){
                    setNewScreen = true;

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run () {
                            game.loadOtherFunctions();
                            game.setScreen(new MainMenuScreen(game));
                            SpringDynamics.loadingScreenDone = 1;
                            dispose();
                        }
                    });
                }
            }
            game.uiBatch.end();
        }
    }

    @Override
    public void dispose() {
        splashscreen.dispose();
    }
}
