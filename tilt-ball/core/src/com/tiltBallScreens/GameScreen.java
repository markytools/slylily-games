package com.tiltBallScreens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tiltball.game.GameWorld;
import com.tiltball.game.MyContactListener;
import com.tiltball.game.MyFileHandle;
import com.tiltball.game.MyTiledMap;
import com.tiltball.game.TiltBall;
import com.tiltball.game.TiltBallEngines;
import com.tiltball.game.TiltBallInputListener;
import com.tiltBallRenderUtils.Stopwatch;
import com.tiltBallRenderUtils.TiledMapAssetUtility;
import com.tiltBallRenderUtils.TiledMapRenderer;
import com.tiltBallRenderUtils.TiledMapUtilizer;

public class GameScreen extends GameWorld implements Screen, MyFileHandle {

	private TiltBall game;

	private SpriteBatch batch;
	protected SpriteBatch fontBatch;

	private BitmapFont font, stopwatch;
	private OrthographicCamera movingCamera, hudCam;
	private Vector2 mapMoveSpeed;
	private short gameState;
	private PolygonShape tileShape;
	private TiledMapTileLayer layer;
	private float lastMapPos, destroyMapPos;
	private int currentPlayerNumState;
	private Body rightBody, leftBody, bottomBody, topBody;
	private TextureRegion ball, time, quitRegion, yesRegion, noRegion, quitNotifyRegion;
	private double accumulator = 0;;
	private double currentTime;
	private boolean overwriteRecord = true;
	private Array<Body> boxBodies;

	private Rectangle yesLayer, noLayer, quitLayer, gameOverQuitLayer, gameOverPlayAgainLayer;
	private Actor quit, yes, no, gameOverQuit, gameOverPlayAgain;

	public static final float WORLD_WIDTH = 512;
	public static final float WORLD_HEIGHT = 832;
	private Stage stage, gameOverStage;
	private ExtendViewport gameVP;
	private Vector3 touchPos;

	private boolean toggleQuit = false;
	private boolean isLoading = false;

	private int currentGameTime = 0;
	private int minutes = 0;
	private int seconds = 0;
	private int milliseconds = 0;
	private int prevSecond = -1;

	private Sound gameOverSound;

	private float tiltX, tiltY ;

	private Array<TiledMapRenderer> tiledMapRenderers;

	private TiledMapAssetUtility assetUtils;
	private TiledMapUtilizer mapUtils;
	private TiltBallInputListener inputList;
	private MyContactListener cl;
	private Stopwatch watch;

	private FileHandle timeRecord = Gdx.files.local("bestTime.txt");

	private float maxSpeed = 120;
	private float ballSpeed = 65;	//Min: 65, Max: 120
	private float playerBSpeed = 9;
	private float yPos = 200;
	private boolean decreaseYPos = true;
	private TextureRegion gameOver, brownTrans, playAgainRegion, quitGameRegion, newRecordRegion;

	//	private boolean ballTouched = false;
	//	private boolean soundPlayed = false;

	//	public void setBallTouched(boolean ballTouched) {
	//		this.ballTouched = ballTouched;
	//	}

	@SuppressWarnings("deprecation")
	public GameScreen(TiltBall game){
		this.game = game;

		gameState = TiltBallEngines.SINGLE_PLAYER;
		if (gameState == TiltBallEngines.SINGLE_PLAYER){
			currentPlayerNumState = 1;
		} else currentPlayerNumState = 2;

		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameOver.mp3"));

		assetUtils = new TiledMapAssetUtility(currentPlayerNumState);
		mapUtils = new TiledMapUtilizer();
		inputList = new TiltBallInputListener();
		cl = new MyContactListener(game, gameOverSound);
		watch = new Stopwatch();
		loadTextures();

		batch = new SpriteBatch();
		fontBatch = new SpriteBatch();
		movingCamera = new OrthographicCamera();
		hudCam = new OrthographicCamera();
		movingCamera.setToOrtho(true, 512, 832);
		hudCam.setToOrtho(true, 512, 832);
		touchPos = new Vector3();
		mapMoveSpeed = new Vector2(0, 65);
		yesLayer = new Rectangle(256 - 120, 256, 100, 50);
		noLayer = new Rectangle(256 + 20, 256, 100, 50);
		quitLayer = new Rectangle(50, 10, 100, 50);
		gameOverQuitLayer = new Rectangle(256 + 20, 600 - 200, 200, 100);
		gameOverPlayAgainLayer = new Rectangle(256 - 220, 600 - 200, 200, 100);

		tiledMapRenderers = new Array<TiledMapRenderer>();
		boxBodies = new Array<Body>();
		boxFixtures = new Array<Fixture>();

		destroyMapPos = 0;
		lastMapPos = 832;

		lastMapPos = movingCamera.viewportHeight;
		ball = new TextureRegion(new Texture(Gdx.files.internal("ball1.png")));
		time = new TextureRegion(new Texture(Gdx.files.internal("LabelAssets/time.png")));
		time.flip(false, true);
		quitRegion = new TextureRegion(new Texture(Gdx.files.internal("LabelAssets/quitButton.png")));
		yesRegion = new TextureRegion(new Texture(Gdx.files.internal("LabelAssets/yes.png")));
		noRegion = new TextureRegion(new Texture(Gdx.files.internal("LabelAssets/no.png")));
		quitNotifyRegion = new TextureRegion(new Texture(Gdx.files.internal("LabelAssets/quitNotify.png")));

		quitRegion.flip(false, true);
		yesRegion.flip(false, true);
		noRegion.flip(false, true);
		quitNotifyRegion.flip(false, true);

		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SHOWG.TTF"));
		font = gen.generateFont(22);
		font.setColor(1, 1, 1, 1);
		font.setScale(1, -1);
		stopwatch = gen.generateFont(30);
		stopwatch.setColor(1, 1, 1, 1);
		stopwatch.setScale(1, -1);
		gen.dispose();

		inputListeners();

		createWorldObject();
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(inputList);
		watch.start();

//		game.adTimer.showBanners(false);
//		game.adTimer.stopInterstitial();
	}

	private void inputListeners() {
		quit = new Actor();
		yes = new Actor();
		no = new Actor();
		quit.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				toggleQuit = true;
				super.touchDown(event, x, y, pointer, button);
			}
		});
		yes.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				isLoading = true;
				game.setScreen(new MainMenuScreen(game));
				dispose();
				super.touchDown(event, x, y, pointer, button);
			}
		});
		no.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				toggleQuit = false;
				super.touchDown(event, x, y, pointer, button);
			}
		});


		gameVP = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, hudCam);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(gameVP, batch){

			@Override
			public void clear() {
				super.clear();
			}
		};
		stage.addActor(no);
		stage.addActor(yes);
		stage.addActor(quit);

		gameOverQuit = new Actor();
		gameOverPlayAgain = new Actor();


		gameOverPlayAgain.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(final InputEvent event, final float x, final float y,
					final int pointer, final int button) {
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						isLoading = true;
						restartGame();
						//						dispose();
					}
				});
			}
		});
		gameOverQuit.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				isLoading = true;
				game.setScreen(new MainMenuScreen(game));
				dispose();
				super.touchDown(event, x, y, pointer, button);
			}
		});

		gameOverStage = new Stage();
		gameOverStage.addActor(gameOverQuit);
		gameOverStage.addActor(gameOverPlayAgain);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor((2.45f / 2.55f), (2.22f / 2.55f), (1.79f / 2.55f), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle("Tilt Ball - FPS: " + Gdx.graphics.getFramesPerSecond());

		if(!isLoading){
			if (!cl.isBallTouched()){
				renderEngines();
				stepWorld();
				addTileMap();
				destroyTileMap();
				setCameras();
			}
			drawBatches();
			drawStage();
//			game.adTimer.showDelayedInterstitial(true);
		}
	}

	private void renderEngines() {
		//		writeAllCalculationsHere
		overwriteFile();
	}

	private void overwriteFile() {
		if (overwriteRecord && cl.isBallTouched()){
			overwriteRecord = false;
			writeFile();
		}
	}

	private void setGameTimer() {
		currentGameTime = (int) (watch.getCurrentTime());
		milliseconds = (currentGameTime % 100);
		seconds = (currentGameTime / 1000) % 60;
		minutes = (currentGameTime / 1000) / 60;

		if (prevSecond != seconds && ballSpeed <= maxSpeed){
			ballSpeed += 0.1f;
		}
	}

	private void stepWorld() {
		double newTime = TimeUtils.millis() / 1000.0;
		double frameTime = Math.min(newTime - currentTime, 0.25);
		float deltaTime = (float)frameTime;

		currentTime = newTime;

		accumulator += deltaTime;

		while (accumulator >= TIME_STEP){
			accumulator -= TIME_STEP;
			world.step(TIME_STEP, 6, 2);
			setPositions();
			moveBall();
			setGameTimer();
		}

		interpolateBodies((float)accumulator / TIME_STEP);
	}

	private void moveBall() {
		float speed = playerBSpeed;

		if (Gdx.app.getType() == ApplicationType.Desktop){
			if (Gdx.input.isKeyPressed(Keys.LEFT)) tiltX = speed;
			else if (Gdx.input.isKeyPressed(Keys.RIGHT)) tiltX = -speed;
			else {
				tiltX = 0;
			}

			if (Gdx.input.isKeyPressed(Keys.UP)) tiltY = speed;
			else if (Gdx.input.isKeyPressed(Keys.DOWN)) tiltY = -speed;
			else {
				tiltY = 0;
			}
		}
		else {
			if (Gdx.input.getAccelerometerX() > 0){
				if (Gdx.input.getAccelerometerX() <= speed){
					tiltX = Gdx.input.getAccelerometerX();
				}
				else tiltX = speed;
			}
			else if (Gdx.input.getAccelerometerX() < 0){
				if (Gdx.input.getAccelerometerX() >= -speed){
					tiltX = Gdx.input.getAccelerometerX();
				}
				else tiltX = -speed;
			}

			if (Gdx.input.getAccelerometerY() > 0){
				if (Gdx.input.getAccelerometerY() <= speed){
					tiltY = -Gdx.input.getAccelerometerY();
				}
				else tiltY = -speed;
			}
			else if (Gdx.input.getAccelerometerY() < 0){
				if (Gdx.input.getAccelerometerY() >= -speed){
					tiltY = -Gdx.input.getAccelerometerY();
				}
				else tiltY = speed;
			}
		}

		player1Body.setLinearVelocity(-(tiltX) * (1/5f), -(tiltY) * (1/5f));
	}

	private void destroyTileMap() {
		mapUtils.destroyTileMap(tiledMapRenderers, topBody.getPosition().y * BOX_TO_WORLD + 27, lastMapPos, destroyMapPos, world,
				boxBodies, boxFixtures);
	}

	private void createWorldObject(){

		if (gameState == TiltBallEngines.SINGLE_PLAYER){
			//			Single Player Objects
			bDef.type = BodyType.DynamicBody;
			bDef.position.set(64 * 4 * WORLD_TO_BOX, 64 * 5 * WORLD_TO_BOX);
			player1Body = world.createBody(bDef);
			boxBodies.add(player1Body);

			CircleShape cs = new CircleShape();
			cs.setRadius(26 * WORLD_TO_BOX);
			fDef.shape = cs;
			fDef.friction = 0;
			fDef.restitution = 0;
			fDef.filter.categoryBits = TiltBallEngines.PLAYER1_BALL;
			player1Body.createFixture(fDef).setUserData("player1");

			TiledMap tiledMap = new TmxMapLoader().load("1player - 3,4.tmx");

			MyTiledMap myTiledMap = new MyTiledMap();
			myTiledMap.getLayers().add(tiledMap.getLayers().get("Tile Layer 1"));
			myTiledMap.getTileSets().addTileSet(tiledMap.getTileSets().getTileSet(0));

			tiledMapRenderers.add(new TiledMapRenderer(myTiledMap));
			tileShape = new PolygonShape();
			layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");

			float tileSize = layer.getTileHeight();

			for (int row = 0; row < layer.getHeight(); row++){
				for (int col = 0; col < layer.getWidth(); col++){

					Cell cell = layer.getCell(col, row);

					if (cell == null) continue;
					if (cell.getTile() == null) continue;

					bDef.type = BodyType.StaticBody;
					bDef.position.set((col + 0.5f) * tileSize * WORLD_TO_BOX, (row + 0.5f) * tileSize * WORLD_TO_BOX);

					tileShape.setAsBox(tileSize / 2 * WORLD_TO_BOX, tileSize / 2 * WORLD_TO_BOX);
					fDef.friction = 0;
					fDef.restitution = 0;
					fDef.shape = tileShape;
					fDef.isSensor = false;
					fDef.filter.categoryBits = TiltBallEngines.BOXES;
					Body tile = world.createBody(bDef);
					tile.createFixture(fDef);
					boxBodies.add(tile);
				}
			}
		}
		else if (gameState == TiltBallEngines.MULTIPLAYER){
			//			Multiplayer Objects
		}

		createSideBodies();
	}

	private void addTileMap() {

		mapUtils.createNewTileMap(tiledMapRenderers, assetUtils, topBody.getPosition().y * BOX_TO_WORLD + 27, lastMapPos, currentPlayerNumState,
				mapMoveSpeed, world, bDef, fDef, boxBodies, boxFixtures);

		if (mapUtils.isNewLastPos()){
			mapUtils.setNewLastPos(false);
			lastMapPos += 832;
		}
		if (mapUtils.isDestroyLastPos()){
			mapUtils.setDestroyLastPos(false);
			destroyMapPos += 832;
		}
	}

	private void drawBatches() {
		for (TiledMapRenderer renderers : tiledMapRenderers){
			renderers.setView(movingCamera);
			renderers.render();
		}

		batch.begin();
		batch.draw(ball, player1Body.getPosition().x * BOX_TO_WORLD - 24.5f, player1Body.getPosition().y * BOX_TO_WORLD - 27, 50, 50);
		batch.end();

		fontBatch.setProjectionMatrix(hudCam.combined);
		fontBatch.begin();
		fontBatch.draw(time, 210, 0 + 10);
		if (seconds - 9 <= 0){
			if (milliseconds - 9 <= 0){
				stopwatch.draw(fontBatch, String.valueOf(minutes) + " : 0" +
						String.valueOf(seconds) + " : 0" + String.valueOf(milliseconds), 360, 17 + 10);
			}
			else {
				stopwatch.draw(fontBatch, String.valueOf(minutes) + " : 0" +
						String.valueOf(seconds) + " : " + String.valueOf(milliseconds), 360, 17 + 10);
			}
		} else {
			if (milliseconds - 9 <= 0){
				stopwatch.draw(fontBatch, String.valueOf(minutes) + " : " +
						String.valueOf(seconds) + " : 0" + String.valueOf(milliseconds), 360, 17 + 10);
			}
			else {
				stopwatch.draw(fontBatch, String.valueOf(minutes) + " : " +
						String.valueOf(seconds) + " : " + String.valueOf(milliseconds), 360, 17 + 10);
			}
		}

		fontBatch.draw(quitRegion, quitLayer.x, quitLayer.y + 2);
		if (toggleQuit){
			fontBatch.draw(noRegion, noLayer.x, noLayer.y);
			fontBatch.draw(yesRegion, yesLayer.x, yesLayer.y);
			fontBatch.draw(quitNotifyRegion, 256 - 150, 200);
		}
		if (cl.isBallTouched()){
			renderGameOver();
		}
		fontBatch.end();
	}

	private void setCameras() {
		movingCamera.update();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		hudCam.position.y = 416;
		hudCam.position.x = 256;
		hudCam.unproject(touchPos);
		hudCam.update();
		batch.setProjectionMatrix(movingCamera.combined);
	}

	private void createSideBodies(){
		bDef.position.set(-1 * WORLD_TO_BOX, 0 * WORLD_TO_BOX);
		bDef.type = BodyType.KinematicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1 * WORLD_TO_BOX, 832 * WORLD_TO_BOX);
		fDef.shape = shape;
		fDef.restitution = 0;
		fDef.friction = 0;
		fDef.filter.categoryBits = TiltBallEngines.LEFT_BODY;
		leftBody = world.createBody(bDef);
		leftBody.createFixture(fDef);
		boxBodies.add(leftBody);

		bDef.position.set(512 * WORLD_TO_BOX, 0 * WORLD_TO_BOX);
		bDef.type = BodyType.KinematicBody;

		shape.setAsBox(1 * WORLD_TO_BOX, 832 * WORLD_TO_BOX);
		fDef.shape = shape;
		fDef.restitution = 0;
		fDef.friction = 0;
		fDef.filter.categoryBits = TiltBallEngines.RIGHT_BODY;
		rightBody = world.createBody(bDef);
		rightBody.createFixture(fDef);
		boxBodies.add(rightBody);

		bDef.position.set(0 * WORLD_TO_BOX, 832 * WORLD_TO_BOX);
		bDef.type = BodyType.KinematicBody;

		shape.setAsBox(832 * WORLD_TO_BOX, 1 * WORLD_TO_BOX);
		fDef.shape = shape;
		fDef.restitution = 0;
		fDef.friction = 0;
		fDef.filter.categoryBits = TiltBallEngines.BOTTOM_BODY;
		bottomBody = world.createBody(bDef);
		bottomBody.createFixture(fDef);
		boxBodies.add(bottomBody);

		bDef.position.set(0 * WORLD_TO_BOX, -54 * WORLD_TO_BOX);
		bDef.type = BodyType.KinematicBody;

		shape.setAsBox(832 * WORLD_TO_BOX, 1 * WORLD_TO_BOX);
		fDef.shape = shape;
		fDef.restitution = 0;
		fDef.friction = 0;
		fDef.filter.categoryBits = TiltBallEngines.TOP_BODY;
		topBody = world.createBody(bDef);
		topBody.createFixture(fDef).setUserData("topBorder");
		boxBodies.add(topBody);
	}

	private void setPositions(){
		//		if (cl.isBallTouched()) Gdx.app.exit();

		mapMoveSpeed.y = ballSpeed;
		world.setContactListener(cl);
		topBody.setLinearVelocity(0, mapMoveSpeed.y * WORLD_TO_BOX);
		leftBody.setLinearVelocity(0, mapMoveSpeed.y * WORLD_TO_BOX);
		rightBody.setLinearVelocity(0, mapMoveSpeed.y * WORLD_TO_BOX);
		bottomBody.setLinearVelocity(0, mapMoveSpeed.y * WORLD_TO_BOX);
		movingCamera.position.y += mapMoveSpeed.y * TIME_STEP;
	}

	private void interpolateBodies(float alpha){
		for (Body body : boxBodies){
			Transform transform = body.getTransform();
			Vector2 bodyPosition = transform.getPosition();
			Vector2 position = body.getPosition();

			position.x = bodyPosition.x * alpha + position.x * (1.0f - alpha);
			position.y = bodyPosition.y * alpha + position.x * (1.0f - alpha);
		}
	}

	private void drawStage() {
		yes.setBounds(yesLayer.x, yesLayer.y, yesLayer.width, yesLayer.height);
		no.setBounds(noLayer.x, noLayer.y, noLayer.width, noLayer.height);
		quit.setBounds(quitLayer.x, quitLayer.y, quitLayer.width, quitLayer.height);
		gameOverPlayAgain.setBounds(gameOverPlayAgainLayer.x, gameOverPlayAgainLayer.y, 
				gameOverPlayAgainLayer.width, gameOverPlayAgainLayer.height);
		gameOverQuit.setBounds(gameOverQuitLayer.x, gameOverQuitLayer.y, gameOverQuitLayer.width,
				gameOverQuitLayer.height);

		if (!toggleQuit){
			yes.setTouchable(Touchable.disabled);
			no.setTouchable(Touchable.disabled);
		}
		else {
			yes.setTouchable(Touchable.enabled);
			no.setTouchable(Touchable.enabled);
		}

		if (!cl.isBallTouched()){
			Gdx.input.setInputProcessor(stage);
			stage.getViewport().setCamera(hudCam);
			stage.draw();
			stage.act();
		}
		else {
			overwriteFile();
			Gdx.input.setInputProcessor(gameOverStage);
			gameOverStage.getViewport().setCamera(hudCam);
			gameOverStage.draw();
			gameOverStage.act();
		}
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

	public void restartGame(){
		accumulator = 0;
		overwriteRecord = true;
		toggleQuit = false;
		isLoading = false;
		currentGameTime = 0;
		minutes = 0;
		seconds = 0;
		milliseconds = 0;
		yPos = 200;
		decreaseYPos = true;


		maxSpeed = 120;
		ballSpeed = 65;	//Min: 5000, Max: 7000
		playerBSpeed = 9;
		yPos = 200;
		decreaseYPos = true;

		//		for (TiledMapRenderer ren : tiledMapRenderers){
		//			ren.getMap().dispose();
		//			ren.dispose();
		//		}
		tiledMapRenderers.clear();

		gameState = TiltBallEngines.SINGLE_PLAYER;
		if (gameState == TiltBallEngines.SINGLE_PLAYER){
			currentPlayerNumState = 1;
		} else currentPlayerNumState = 2;

		prevSecond = -1;
		destroyMapPos = 0;
		lastMapPos = 832;

		lastMapPos = movingCamera.viewportHeight;

		watch.start();
//		game.adTimer.showBanners(false);
//		game.adTimer.stopInterstitial();

		world.step(0,0,0);
		player1Body.setActive(false);
		leftBody.setActive(false);
		rightBody.setActive(false);
		bottomBody.setActive(false);
		topBody.setActive(false);
		for (Body body : boxBodies){
			body.setActive(false);
		}

		world.clearForces();
		//		world.destroyBody(player1Body);
		//		world.destroyBody(leftBody);
		//		world.destroyBody(rightBody);
		//		world.destroyBody(bottomBody);
		//		world.destroyBody(topBody);
		//		
		//		for (Body body : boxBodies){
		//			world.destroyBody(body);
		//		}

		if (gameState == TiltBallEngines.SINGLE_PLAYER){
			//			Single Player Objects
			bDef.type = BodyType.DynamicBody;
			bDef.position.set(64 * 4 * WORLD_TO_BOX, 64 * 5 * WORLD_TO_BOX);
			player1Body = world.createBody(bDef);
			boxBodies.add(player1Body);

			CircleShape cs = new CircleShape();
			cs.setRadius(26 * WORLD_TO_BOX);
			fDef.shape = cs;
			fDef.friction = 0;
			fDef.restitution = 0;
			fDef.filter.categoryBits = TiltBallEngines.PLAYER1_BALL;
			player1Body.createFixture(fDef).setUserData("player1");

			TiledMap tiledMap = new TmxMapLoader().load("1player - 3,4.tmx");

			MyTiledMap myTiledMap = new MyTiledMap();
			myTiledMap.getLayers().add(tiledMap.getLayers().get("Tile Layer 1"));
			myTiledMap.getTileSets().addTileSet(tiledMap.getTileSets().getTileSet(0));

			tiledMapRenderers.add(new TiledMapRenderer(myTiledMap));
			tileShape = new PolygonShape();
			layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");

			float tileSize = layer.getTileHeight();

			for (int row = 0; row < layer.getHeight(); row++){
				for (int col = 0; col < layer.getWidth(); col++){

					Cell cell = layer.getCell(col, row);

					if (cell == null) continue;
					if (cell.getTile() == null) continue;

					bDef.type = BodyType.StaticBody;
					bDef.position.set((col + 0.5f) * tileSize * WORLD_TO_BOX, (row + 0.5f) * tileSize * WORLD_TO_BOX);

					tileShape.setAsBox(tileSize / 2 * WORLD_TO_BOX, tileSize / 2 * WORLD_TO_BOX);
					fDef.friction = 0;
					fDef.restitution = 0;
					fDef.shape = tileShape;
					fDef.isSensor = false;
					fDef.filter.categoryBits = TiltBallEngines.BOXES;
					Body tile = world.createBody(bDef);
					boxBodies.add(tile);
					tile.createFixture(fDef);
				}
			}
		}
		else if (gameState == TiltBallEngines.MULTIPLAYER){
			//			Multiplayer Objects
		}

		createSideBodies();

		movingCamera.position.y = 416;
		mapUtils.restart();
		cl.restart();
	}

	@Override
	public void dispose() {
		batch = null;
		fontBatch = null;
		font = null;
		stopwatch = null;
		movingCamera = null;
		hudCam = null;
		mapMoveSpeed = null;
		if (tileShape != null) tileShape.dispose();
		tileShape = null;
		layer = null;
		rightBody = null;
		leftBody = null;
		bottomBody = null;
		topBody = null;
		if (ball != null) ball.getTexture().dispose();
		if (time != null) time.getTexture().dispose();
		if (quitRegion != null) quitRegion.getTexture().dispose();
		if (yesRegion != null) yesRegion.getTexture().dispose();
		if (noRegion != null) noRegion.getTexture().dispose();
		if (quitNotifyRegion != null) quitNotifyRegion.getTexture().dispose();

		ball = null;
		time = null;
		quitRegion = null;
		yesRegion = null;
		noRegion = null;
		quitNotifyRegion = null;

		yesLayer = null;
		noLayer = null;
		quitLayer = null;
		gameOverQuitLayer = null;
		gameOverPlayAgainLayer = null;
		if (quit != null) quit.remove();
		quit = null;
		if (yes != null) yes.remove();
		yes = null;
		if (no != null) no.remove();
		no = null;
		if (gameOverQuit != null) gameOverQuit.remove();
		gameOverQuit = null;
		if (gameOverPlayAgain != null) gameOverPlayAgain.remove();
		gameOverPlayAgain = null;
		if (stage != null) stage.dispose();
		stage = null;
		if (gameOverStage != null) gameOverStage.dispose();
		gameOverStage = null;
		gameVP = null;
		touchPos = null;
		if (gameOverSound != null) gameOverSound.dispose();
		gameOverSound = null;

		//		for (TiledMapRenderer ren : tiledMapRenderers){
		//			ren.getMap().dispose();
		//			ren.dispose();
		//		}
		if (tiledMapRenderers != null) tiledMapRenderers.clear();
		tiledMapRenderers = null;
		if (assetUtils != null) assetUtils.dispose();
		assetUtils = null;
		if (mapUtils != null) mapUtils.dispose();
		mapUtils = null;
		inputList = null;
		if (cl != null) cl.dispose();
		watch = null;
		timeRecord = null;

		if (gameOver != null) gameOver.getTexture().dispose();
		if (brownTrans != null) brownTrans.getTexture().dispose();
		if (playAgainRegion != null) playAgainRegion.getTexture().dispose();
		if (quitRegion != null) quitRegion.getTexture().dispose();
		if (newRecordRegion != null) newRecordRegion.getTexture().dispose();

		if (world != null) world.dispose();
		world = null;
		bDef = null;
		player1Body = null;
		player1Fixture = null;
		fDef = null;
		if (boxBodies != null) boxBodies.clear();
		boxBodies = null;
		if (boxFixtures != null) boxFixtures.clear();
		boxFixtures = null;
	}

	@Override
	public void createFile() {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeFile() {
		if (!timeRecord.readString().equals("")){
			if (milliseconds > Long.parseLong(timeRecord.readString()) % 100){
				if (seconds >= (Long.parseLong(timeRecord.readString()) / 1000) % 60){
					if (minutes >= (Long.parseLong(timeRecord.readString()) / 1000) / 60){
						timeRecord.writeString(String.valueOf(watch.getCurrentTime()), false);
					}
				}
			}
			else {
				if (seconds >= (Long.parseLong(timeRecord.readString()) / 1000) % 60){
					if (minutes >= (Long.parseLong(timeRecord.readString()) / 1000) / 60){
						timeRecord.writeString(String.valueOf(watch.getCurrentTime()), false);
					}
				}
				else {
					if (minutes > (Long.parseLong(timeRecord.readString()) / 1000) / 60){
						timeRecord.writeString(String.valueOf(watch.getCurrentTime()), false);
					}
				}
			}
		}
		else timeRecord.writeString(String.valueOf(watch.getCurrentTime()), false);
	}

	private void renderGameOver(){
		moveYPos();
		fontBatch.draw(brownTrans, -500, -500, 1500, 2000);
		fontBatch.draw(gameOver, 256 - 120, yPos);
		fontBatch.draw(newRecordRegion, 256 - 120 , 460 - 150);
		fontBatch.draw(playAgainRegion, 256 - 220, 600 - 200);
		fontBatch.draw(quitGameRegion, 256 + 20, 600 - 200);
	}

	private void moveYPos(){
		if (decreaseYPos){
			yPos -= 1;
		}
		else {
			yPos += 1;
		}

		if (yPos < 200){
			decreaseYPos = false;
		}
		if (yPos >= 210){
			decreaseYPos = true;
		}
	}

	public void loadTextures(){
		(gameOver = new TextureRegion(new Texture(Gdx.files.internal("LabelAssets/gameOver.png")))).flip(false, true);
		(brownTrans = new TextureRegion(new Texture(Gdx.files.internal("brownTrans.png")))).flip(false, true);
		(playAgainRegion = new TextureRegion(new Texture(Gdx.files.internal("LabelAssets/playAgain.png")))).flip(false, true);
		(quitGameRegion = new TextureRegion(new Texture(Gdx.files.internal("LabelAssets/quit.png")))).flip(false, true);
		(newRecordRegion = new TextureRegion(new Texture(Gdx.files.internal("LabelAssets/newRecord.png")))).flip(false, true);
	}
}
