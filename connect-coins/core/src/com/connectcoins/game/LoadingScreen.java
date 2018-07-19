package com.connectcoins.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.connectcoins.assets.CustomResolutionFileResolver;
import com.connectcoins.mainMenu.MainMenuScreen;

/**
 * Created by Mark Ty on 8/6/2016.
 */
public class LoadingScreen extends ScreenAdapter {
    private ConnectCoins game;
    private Texture splashscreen;
    private long time = -1;
    private boolean setNewScreen = false;
    private boolean filesExtracted = false;
    public LoadingScreen(ConnectCoins game, CustomResolutionFileResolver cRFileResolver){
        this.game = game;
        splashscreen = game.assetLoader.getTexture("splashscreen");
        time = TimeUtils.millis();


        filesExtracted = true;
//        for (int i = 1; i <= 100; i++){
//            if (!Gdx.files.internal("Connect Coins/com.connectcoins.game/puzzleData/challenge5/p" + i + ".ccs").exists()){
//                filesExtracted = false;
//                break;
//            }
//        }
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
        if (filesExtracted){
            if (time == -1){
                time = TimeUtils.millis();
            }
            else {
                game.batch.begin();
                game.batch.draw(splashscreen, -180, 0, 1440, 1920);
                if (TimeUtils.millis() - time >= 100){
                    if (!setNewScreen){
                        setNewScreen = true;

                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run () {
                                game.loadOtherFunctions();
                                game.setScreen(new MainMenuScreen(game));
                                ConnectCoins.loadingScreenDone = 1;
                                dispose();
                            }
                        });
                    }
                }
                game.batch.end();
            }
        }
//        else {
//            if (time == -1){
//                time = TimeUtils.millis();
//            }
//            else {
//                game.batch.begin();
//                game.batch.draw(splashscreen, -180, 0, 1440, 1920);
//                if (TimeUtils.millis() - time >= 1000){
//                    if (!setNewScreen){
//                        setNewScreen = true;
//
//                        Gdx.app.postRunnable(new Runnable() {
//                            @Override
//                            public void run () {
//                                System.out.println("screen set");
//                                game.setScreen(new DownloadScreen(game, cRFileResolver, filesExtracted));
//                                dispose();
//                            }
//                        });
//                    }
//                }
//                game.batch.end();
//            }
//        }
//        if (time == -1){
//            time = TimeUtils.millis();
//        }
//        else {
//            game.batch.begin();
//            game.batch.draw(splashscreen, -180, 0, 1440, 1920);
//            if (TimeUtils.millis() - time >= 100){
//                if (!setNewScreen){
//                    setNewScreen = true;
//
//                    Gdx.app.postRunnable(new Runnable() {
//                        @Override
//                        public void run () {
//                            game.loadOtherFunctions();
//                            game.setScreen(new MainMenuScreen(game));
//                            ConnectCoins.loadingScreenDone = 1;
//                            dispose();
//                        }
//                    });
//                }
//            }
//            game.batch.end();
//        }


    }

    @Override
    public void dispose() {
        splashscreen.dispose();
    }
}
