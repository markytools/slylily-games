package com.gameCompletionManager.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.springdynamics.game.GameScreen;
import com.springdynamics.game.MainMenuScreen;
import com.springdynamics.game.SpringDynamics;
import com.springDynamicsConstants.game.Dimensions;
import com.springDynamicsConstants.game.GameState;

public class GameOver {
	private GameScreen gScreen;
	private SpringDynamics game;
	private Button playAgain, rateGame, moreApps, quit;
	private Stage stage;
	private boolean toogleGameOver;
	private boolean newLongestDist;
	
	public GameOver(GameScreen gScreen, SpringDynamics game){
		this.gScreen = gScreen;
		this.game = game;
		
		toogleGameOver = false;
		newLongestDist = false;
		createGameOverAssets();
	}

	private void createGameOverAssets() {

		float y = 255;
		
		ButtonStyle style1 = new ButtonStyle();
		style1.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("playAgain1"))));
		style1.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("playAgain2"))));
		playAgain = new Button(style1);
		playAgain.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
				gScreen.dispose();
				super.clicked(event, x, y);
			}
		});
		playAgain.setBounds(250, y, 300, 50);

		ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("rateGame1"))));
		style2.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("rateGame2"))));
		rateGame = new Button(style2);
		rateGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("http://play.google.com/store/apps/details?id=com.springdynamics.game");
				super.clicked(event, x, y);
			}
		});
		rateGame.setBounds(250, y - (60 * 1), 300, 50);

		ButtonStyle style3 = new ButtonStyle();
		style3.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("moreApps1"))));
		style3.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("moreApps2"))));
		moreApps = new Button(style3);
		moreApps.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
			}
		});
		moreApps.setBounds(250, y - (60 * 2), 300, 50);

		ButtonStyle style4 = new ButtonStyle();
		style4.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("quit1"))));
		style4.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("quit2"))));
		quit = new Button(style4);
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
				game.dispose();
				super.clicked(event, x, y);
			}
		});
		quit.setBounds(250, y - (60 * 3), 300, 50);
		
		stage = new Stage();
		stage.addActor(quit);
		stage.addActor(moreApps);
		stage.addActor(rateGame);
		stage.addActor(playAgain);
	}
	
	public void checkIfGameOver(){
		Body spring = gScreen.getwManager().getSpring();
		
		if (!toogleGameOver && gScreen.getwManager().getSpring().getPosition().y * Dimensions.BOX_TO_WORLD < -50) {
			toogleGameOver = true;
			gScreen.gState = GameState.GAME_OVER;
			applyNewLongestDist();
			gScreen.game.assetM.get(gScreen.game.soundID.get("gOverSound")).play();
		}
		if (gScreen.gState == GameState.GAME_OVER) spring.setLinearVelocity(0, 0);
	}
	
	private void applyNewLongestDist(){
		if (game.profUpdater.profVal.getLong("longestDistTraveled") < gScreen.getCurrentDist()){
			newLongestDist = true;
			game.profUpdater.setLongestDistanceTraveled(gScreen.getCurrentDist());
			game.profUpdater.setQueueHighScore(true);
		}
	}
	
	public void render(){
		game.uiBatch.draw(game.assetM.get(game.assetID.get("InGameMenuBG")), 200, 50);
		if (!newLongestDist) game.uiBatch.draw(game.assetM.get(game.assetID.get("gameOverTitle")), 228, 345);
		else {
			game.uiBatch.draw(game.assetM.get(game.assetID.get("gameOverTitle")), 228, 380);
			gScreen.game.fontManager.getFont().getData().setScale(1f);
			gScreen.game.fontManager.getFont().draw(gScreen.game.uiBatch, "New Longest Distance!", 252, 393);
//			game.fontManager.getFont().draw(game.uiBatch, String.valueOf(gScreen.getCurrentDist()) + " meters",
//					107, 360, 600, Align.center, true);
			game.fontManager.getFont().draw(game.uiBatch, String.valueOf(gScreen.getCurrentDist()) + " meters",
					107, 360, 600, Align.center, true);
//			game.fontManager.getFont().drawMultiLine(game.uiBatch, String.valueOf(gScreen.getCurrentDist()) + " meters", 107, 360,
//					600, Align.center);
		}
	}
	
	public void setListeners(){
		Gdx.input.setInputProcessor(stage);
		stage.act();
		stage.draw();
		stage.getViewport().setCamera(game.uiCam);
	}
	
	public void dispose(){
		game.assetM.unload(game.assetID.get("playAgain1").fileName);
		game.assetM.unload(game.assetID.get("playAgain2").fileName);
		game.assetM.unload(game.assetID.get("rateGame1").fileName);
		game.assetM.unload(game.assetID.get("rateGame2").fileName);
		game.assetM.unload(game.assetID.get("moreApps2").fileName);
		game.assetM.unload(game.assetID.get("moreApps1").fileName);
		game.assetM.unload(game.assetID.get("quit1").fileName);
		game.assetM.unload(game.assetID.get("quit2").fileName);
		game.assetM.unload(game.assetID.get("gameOverTitle").fileName);
		stage.dispose();
	}
}
