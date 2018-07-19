package com.tiltBallScreens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tiltball.game.MyFileHandle;
import com.tiltball.game.TiltBall;
import com.tiltball.game.TiltBallAssetsManager;
import com.tiltball.game.TiltBallEngines;

public class MainMenuScreen extends TiltBallAssetsManager implements Screen, MyFileHandle {
	private Actor onePlayer, noAds, restoreP;
	private Rectangle onePlayerLayer, noAdsLayer, restorePLayer;
	private OrthogonalTiledMapRenderer tm;
	private Body ballBody;
	private TiledMap tiledMap;
	private double accumulator = 0;
	private double currentTime;
	private boolean isLoading = false;
	private BitmapFont bestRecord;
	private int minutes, seconds, milliseconds;
	public static final float WORLD_WIDTH = 512;
	public static final float WORLD_HEIGHT = 832;
	private ExtendViewport gameVP;
	private Vector3 touchPos;

	private FileHandle timeRecord = Gdx.files.local("bestTime.txt");

	@SuppressWarnings("deprecation")
	public MainMenuScreen(final TiltBall game){
		assetManager.load("LabelAssets/noAds.png", Texture.class);
		assetManager.load("LabelAssets/restorePurchases.png", Texture.class);
		assetManager.load("LabelAssets/1Player.png", Texture.class);
		assetManager.load("mainMenuAssets/rules.png", Texture.class);
		assetManager.load("ball3.png", Texture.class);
		assetManager.finishLoading();
		createFile();
		touchPos = new Vector3();

		tiledMap = new TmxMapLoader().load("mainMenuAssets/titleScreen.tmx");

		tm = new OrthogonalTiledMapRenderer(tiledMap);

		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SHOWG.TTF"));
		bestRecord = gen.generateFont(30);
		bestRecord.setColor(96 / 255f, 51/ 255f, 17 / 255f, 1);

		onePlayerLayer = new Rectangle(37.5f, 430, 200, 75);
		onePlayer = new Actor();
		onePlayer.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y,

					int pointer, int button) {
				isLoading = true;
				dispose();
				game.setScreen(new GameScreen(game));
				super.touchDown(event, x, y, pointer, button);
			}
		});
		
		noAdsLayer = new Rectangle(37.5f + 200 + 25, 430, 200, 75);
		noAds = new Actor();
		noAds.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				FileHandle noAdsRecord = Gdx.files.local("noAds.txt");
				String noAdsFileTxt = noAdsRecord.readString();
				if (noAdsFileTxt.equals("adsdisabled")){
					game.adActivity.showToast("Item already purchased.");
//					game.adTimer.disableAllAds();
				}
				else {
//					game.tBPurchaser.purchaseNoAd();
				}
			}
		});
		
		restorePLayer = new Rectangle(150, 340, 200, 75);
		restoreP = new Actor();
		restoreP.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
//				game.tBPurchaser.restoreAllPurchase();
			}
		});
		
		
		Gdx.input.setCatchBackKey(true);

		mainCam = new OrthographicCamera();
		mainCam.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		gameVP = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, mainCam);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(gameVP, batch){

			@Override
			public void clear() {
				super.clear();
			}
		};
		stage.addActor(onePlayer);
		stage.addActor(noAds);
		stage.addActor(restoreP);
		stage.addListener(new InputListener(){

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.HOME){
					Gdx.app.postRunnable(new Runnable(){
						@Override
						public void run() {
							isLoading = true;
							Gdx.app.exit();
						}
					});
				}
				return super.keyDown(event, keycode);
			}

		});
		if (!timeRecord.readString().equals("")){
			int recordedTime =  Integer.valueOf(timeRecord.readString());
			milliseconds = recordedTime % 100;
			seconds = (recordedTime / 1000) % 60;
			minutes = (recordedTime / 1000) / 60;
		}

		createMovingBall();
		
//		game.adTimer.showBanners(true);
//		game.adTimer.randomShowInterstitial();
		game.adActivity.showRateDialog();
	}

	@Override
	public void render(float delta) {
		if (!isLoading){
			Gdx.gl.glClearColor(255/255f, 222/255f, 173/255f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			double newTime = TimeUtils.millis() / 1000.0;
			double frameTime = Math.min(newTime - currentTime, 0.25);
			float deltaTime = (float)frameTime;

			currentTime = newTime;

			accumulator += deltaTime;

			while (accumulator >= TiltBallEngines.TIME_STEP){
				accumulator -= TiltBallEngines.TIME_STEP;
				world.step(1/60f, 6, 2);
			}

			interpolateBodies((float)accumulator / TiltBallEngines.TIME_STEP);

			mainCam.position.y = 416;
			mainCam.position.x = 256;
			mainCam.update();
			batch.setProjectionMatrix(mainCam.combined);
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			mainCam.unproject(touchPos);

			onePlayer.setBounds(onePlayerLayer.x, onePlayerLayer.y, onePlayerLayer.width, onePlayerLayer.height);
			noAds.setBounds(noAdsLayer.x, noAdsLayer.y, noAdsLayer.width, noAdsLayer.height);
			restoreP.setBounds(restorePLayer.x, restorePLayer.y, restorePLayer.width, restorePLayer.height);

			tm.setView(mainCam);
			tm.render();

			mainCam.combined.cpy();

			batch.begin();
			batch.draw((Texture) assetManager.get("ball3.png"), ballBody.getPosition().x * TiltBallEngines.BOX_TO_WORLD - 20,
					ballBody.getPosition().y * TiltBallEngines.BOX_TO_WORLD - 20, 40, 40);
			batch.draw((Texture) assetManager.get("LabelAssets/1Player.png"), onePlayerLayer.x, onePlayerLayer.y, onePlayerLayer.width, onePlayerLayer.height);
			batch.draw((Texture) assetManager.get("mainMenuAssets/rules.png"), 56, 40);
			batch.draw((Texture) assetManager.get("LabelAssets/noAds.png"), noAdsLayer.x, noAdsLayer.y, noAdsLayer.width, noAdsLayer.height);
			batch.draw((Texture) assetManager.get("LabelAssets/restorePurchases.png"), restorePLayer.x, restorePLayer.y, restorePLayer.width, restorePLayer.height);
			
			if (!timeRecord.readString().equals("")){
				if (seconds - 9 <= 0){
					if (milliseconds - 9 <= 0){
						bestRecord.draw(batch, "Best Record = " + String.valueOf(minutes) + " : 0" +
								String.valueOf(seconds) + " : 0" + String.valueOf(milliseconds), 75, 320);
					}
					else {
						bestRecord.draw(batch, "Best Record = " + String.valueOf(minutes) + " : 0" +
								String.valueOf(seconds) + " : " + String.valueOf(milliseconds), 75, 320);
					}
				} else {
					if (milliseconds - 9 <= 0){
						bestRecord.draw(batch, "Best Record = " + String.valueOf(minutes) + " : " +
								String.valueOf(seconds) + " : 0" + String.valueOf(milliseconds), 75, 320);
					}
					else {
						bestRecord.draw(batch, "Best Record = " + String.valueOf(minutes) + " : " +
								String.valueOf(seconds) + " : " + String.valueOf(milliseconds), 75, 320);
					}
				}
			}
			else {
				bestRecord.draw(batch, "Best Record = 00 : 00 : 00", 75, 320);
			}
			batch.end();

			stage.getViewport().setCamera(mainCam);
			stage.draw();
			stage.act();
			Gdx.input.setInputProcessor(stage);
			
//			game.adTimer.showDelayedInterstitial(true);
		}
	}

	private void createMovingBall() {
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
		PolygonShape shape = new PolygonShape();

		float tileSize = layer.getTileHeight();

		for (int row = 0; row < layer.getHeight(); row++){
			for (int col = 0; col < layer.getWidth(); col++){
				Cell cell = layer.getCell(col, row);

				if (cell == null) continue;
				if (cell.getTile() == null) continue;

				bDef.type = BodyType.StaticBody;
				bDef.position.set((col + 0.5f) * tileSize * TiltBallEngines.WORLD_TO_BOX, (row + 0.5f) * tileSize * TiltBallEngines.WORLD_TO_BOX);

				shape.setAsBox(tileSize / 2 * TiltBallEngines.WORLD_TO_BOX, tileSize / 2 * TiltBallEngines.WORLD_TO_BOX);
				fDef.friction = 0;
				fDef.restitution = 0;
				fDef.shape = shape;
				fDef.isSensor = false;
				fDef.filter.categoryBits = TiltBallEngines.BOXES;
				world.createBody(bDef).createFixture(fDef);

			}
		}

		bDef.type = BodyType.StaticBody;
		bDef.position.set(0 * TiltBallEngines.WORLD_TO_BOX, -1 * TiltBallEngines.WORLD_TO_BOX);
		shape.setAsBox(512 * TiltBallEngines.WORLD_TO_BOX, 1 * TiltBallEngines.WORLD_TO_BOX);
		fDef.shape = shape;
		fDef.friction = 0;
		fDef.filter.categoryBits = TiltBallEngines.BOTTOM_BODY;
		world.createBody(bDef).createFixture(fDef);

		bDef.type = BodyType.StaticBody;
		bDef.position.set(512 * TiltBallEngines.WORLD_TO_BOX, 0 * TiltBallEngines.WORLD_TO_BOX);
		shape.setAsBox(1 * TiltBallEngines.WORLD_TO_BOX, 832 * TiltBallEngines.WORLD_TO_BOX);
		fDef.shape = shape;
		fDef.friction = 0;
		fDef.filter.categoryBits = TiltBallEngines.RIGHT_BODY;
		world.createBody(bDef).createFixture(fDef);

		bDef.type = BodyType.StaticBody;
		bDef.position.set(0 * TiltBallEngines.WORLD_TO_BOX, 832 * TiltBallEngines.WORLD_TO_BOX);
		shape.setAsBox(512 * TiltBallEngines.WORLD_TO_BOX, 1 * TiltBallEngines.WORLD_TO_BOX);
		fDef.shape = shape;
		fDef.friction = 0;
		fDef.filter.categoryBits = TiltBallEngines.TOP_BODY;
		world.createBody(bDef).createFixture(fDef);

		bDef.type = BodyType.StaticBody;
		bDef.position.set(-1 * TiltBallEngines.WORLD_TO_BOX, 0 * TiltBallEngines.WORLD_TO_BOX);
		shape.setAsBox(1 * TiltBallEngines.WORLD_TO_BOX, 832 * TiltBallEngines.WORLD_TO_BOX);
		fDef.shape = shape;
		fDef.friction = 0;
		fDef.filter.categoryBits = TiltBallEngines.LEFT_BODY;
		world.createBody(bDef).createFixture(fDef);

		CircleShape cs = new CircleShape();
		bDef.type = BodyType.DynamicBody;
		bDef.position.set(50 * TiltBallEngines.WORLD_TO_BOX, 150 * TiltBallEngines.WORLD_TO_BOX);

		cs.setRadius(20f * TiltBallEngines.WORLD_TO_BOX);
		fDef.shape = cs;
		fDef.restitution = 1;
		fDef.friction = 0;
		ballBody = world.createBody(bDef);
		ballBody.createFixture(fDef);

		ballBody.setLinearVelocity(100 * TiltBallEngines.WORLD_TO_BOX, 120 * TiltBallEngines.WORLD_TO_BOX);
	}

	private void interpolateBodies(float alpha){
		Transform transform = ballBody.getTransform();
		Vector2 bodyPosition = transform.getPosition();
		Vector2 position = ballBody.getPosition();

		position.x = bodyPosition.x * alpha + position.x * (1.0f - alpha);
		position.y = bodyPosition.y * alpha + position.x * (1.0f - alpha);
	}

	@Override
	public void createFile(){
		if (!timeRecord.file().exists()){
			try {
				timeRecord.file().createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void writeFile() {

	}

	@Override
	public void resize(int width, int height) {
		gameVP.update(width, height);
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
		batch.dispose();
		batch = null;
		mainCam = null;
		assetManager.dispose();
		assetManager = null;
		touchPos = null;
		bDef = null;
		fDef = null;
		onePlayer.remove();
		onePlayer = null;
		noAds.remove();
		noAds = null;
		restoreP.remove();
		restoreP = null;
		onePlayerLayer = null;
		tm.dispose();
		tm = null;
		ballBody = null;
		tiledMap.dispose();
		tiledMap = null;
		bestRecord.dispose();
		timeRecord = null;

		stage.dispose();
		stage = null;
		world.dispose();
		world = null;
		
		System.gc();
	}

}
