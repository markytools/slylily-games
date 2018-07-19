package com.springdynamics.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class MainMenuScreen implements Screen {
	private SpringDynamics game;

	private Button play, rateGame, leaderboards, quit, signIn, signOut, noAds, restoreB;
	private Stage stage;
	private Texture bg, boxSprite;
	private TextureRegion boxReg;

	private final String SIGN_IN_BUTTON = "afqfeas";
	private final String SIGN_OUT_BUTTON = "afqasdeas";

	public MainMenuScreen(SpringDynamics game){
		this.game = game;

		game.assetLoader.loadMainMenuScreenAssets();
		game.assetM.finishLoading();

		bg = game.assetM.get(game.assetID.get("mMBG"));
		boolean isWebGL = Gdx.app.getType() == ApplicationType.WebGL;
		boxSprite = new Texture(Gdx.files.internal("box2.png"), !isWebGL);
		if (!isWebGL) boxSprite.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.MipMapLinearNearest);
		boxReg = new TextureRegion(boxSprite);
		createMMButtons();
	}

	private void createMMButtons() {
		ButtonStyle style1 = new ButtonStyle();
		style1.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMPlay1"))));
		style1.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMPlay2"))));
		play = new Button(style1);
		play.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
				dispose();
				super.clicked(event, x, y);
			}
		});
		play.setBounds(79, 349, 250, 65);

		ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMRateGame1"))));
		style2.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMRateGame2"))));
		rateGame = new Button(style2);
		rateGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("http://play.google.com/store/apps/details?id=com.springdynamics.game");
				super.clicked(event, x, y);
			}
		});
		rateGame.setBounds(79, 220, 250, 65);

		ButtonStyle style3 = new ButtonStyle();
		style3.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMMoreApps1"))));
		style3.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMMoreApps2"))));
		leaderboards = new Button(style3);
		leaderboards.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
			}
		});
		leaderboards.setBounds(79, 284, 250, 65);

		ButtonStyle style4 = new ButtonStyle();
		style4.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMQuit1"))));
		style4.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMQuit2"))));
		quit = new Button(style4);
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run() {
						Gdx.app.exit();
					}
				});
				super.clicked(event, x, y);
			}
		});
		quit.setBounds(79, 25, 250, 65);

		ButtonStyle style7 = new ButtonStyle();
		style7.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMRestore1"))));
		style7.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMRestore2"))));
		restoreB = new Button(style7);
		restoreB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.sDPurchaser.restoreAllPurchase();
			}
		});
		restoreB.setBounds(79, 90, 250, 65);

		ButtonStyle style6 = new ButtonStyle();
		style6.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMNoAds1"))));
		style6.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("mMNoAds2"))));
		noAds = new Button(style6);
		noAds.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		noAds.setBounds(79, 155, 250, 65);

		ButtonStyle googleBStyle = new ButtonStyle();
		googleBStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("signIn1"))));
		googleBStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("signIn2"))));
		signIn = new Button(googleBStyle);
		signIn.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run() {
						
					}
				});
				super.clicked(event, x, y);
			}
		});
		signIn.setBounds(600, 26, 176, 60);
		signIn.setUserObject(SIGN_IN_BUTTON);

		ButtonStyle googleBSignOutStyle = new ButtonStyle();
		googleBSignOutStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("signOut1"))));
		googleBSignOutStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("signOut2"))));
		signOut = new Button(googleBSignOutStyle);
		signOut.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run() {
						
					}
				});
				super.clicked(event, x, y);
			}
		});
		signOut.setBounds(650, 60, 120, 60);
		signOut.setUserObject(SIGN_OUT_BUTTON);

		stage = new Stage();
		stage.addActor(play);
		stage.addActor(leaderboards);
		stage.addActor(rateGame);
		stage.addActor(quit);
		stage.addActor(signIn);
		stage.addActor(noAds);
		stage.addActor(signOut);
		stage.addActor(restoreB);
	}

	@Override
	public void render(float delta) {

		game.uiBatch.begin();
		game.uiBatch.draw(bg, 0, 0);
		game.uiBatch.draw(boxReg, 690, 165, 50, 50);
		game.uiBatch.draw(boxReg, 85, 35, 50, 50);
		game.uiBatch.draw(boxReg, 400, 302, 50, 50);
		game.fontManager.getFont().getData().setScale(1f);
		//		game.fontManager.getFont().draw(game.uiBatch, "Longest Traveled Distance", 70, 310, 1000, Align.center, true);
		game.fontManager.getFont().draw(game.uiBatch, "Longest Traveled Distance", 70, 310, 1000, Align.center, true);
		game.fontManager.getFont().getData().setScale(1f);
		//		game.fontManager.getFont().draw(game.uiBatch, String.valueOf(game.profUpdater.profVal.getLong("longestDistTraveled")) +
		//				" meters", 470, 275, 200, Align.center, true);
		game.fontManager.getFont().draw(game.uiBatch, String.valueOf(game.profUpdater.profVal.getLong("longestDistTraveled")) +
				" meters", 470, 275, 200, Align.center, true);
		game.uiBatch.end();

		checkIfSignedIn();
		submitScore();
		setListeners();
	}

	private void submitScore() {
		
	}

	private void setListeners(){
		Gdx.input.setInputProcessor(stage);
		stage.act();
		stage.draw();
		stage.getViewport().setCamera(game.uiCam);
	}

	private void checkIfSignedIn(){
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
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
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		game.assetM.unload(game.assetID.get("mMPlay1").fileName);
		game.assetM.unload(game.assetID.get("mMPlay2").fileName);
		game.assetM.unload(game.assetID.get("mMRateGame1").fileName);
		game.assetM.unload(game.assetID.get("mMRateGame2").fileName);
		game.assetM.unload(game.assetID.get("mMMoreApps1").fileName);
		game.assetM.unload(game.assetID.get("mMMoreApps2").fileName);
		game.assetM.unload(game.assetID.get("mMQuit1").fileName);
		game.assetM.unload(game.assetID.get("mMQuit2").fileName);
		game.assetM.unload(game.assetID.get("mMBG").fileName);
		bg.dispose();
		play.remove();
		rateGame.remove();
		leaderboards.remove();
		quit.remove();
		signIn.remove();
		signOut.remove();
		noAds.remove();
		boxSprite.dispose();
	}
}
