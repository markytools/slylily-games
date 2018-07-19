package com.junkworld.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;

public class MainMenuScreen implements Screen {
	private JunkWorld game;
	private OrthographicCamera camera;
	private Batch batch;
	private Actor playButtonPressed, singlePlayerButtonPressed, multiplayerButtonPressed, quitButtonPressed, shop;
	private boolean playClicked = false, singlePlayerClicked = false, multiplayerClicked = false, quitClicked = false, displayLoading  = false, albumClicked = false,
			comingSoon = false;
	private Stage mainMenuStage;
	private Rectangle playLayer, singlePlayerLayer, multiplayerLayer, quitLayer, albumLayer, shopLayer;
	private Vector3 touchPos = new Vector3();
	private AssetManager manager;
	private boolean buttonClicked = false;
	private long delay = 0, delay2 = 0;
	private TextureRegion albumLabelRegion, albumLabelClickedRegion;
	private Array<TextureRegion> buttonShine;
	private long delayCurrentButtonShine = TimeUtils.millis(), delayCurrentLabelPos = TimeUtils.millis();
	private int currentButtonShine = 0, currentLabelPos = 0;
	private boolean createTextures = true, upwardDirect = true;
	private Random generator;
	private int oldRandom;
	private TextureRegion currentAlbumRegion;
	private Sprite image1, image2, image3;
	private int currentFallingTrash = 800, xpUp = 100, previousXpUp = 0;
//	private IActivityRequestHandler myRequestHandler;
	private JunkWorldEngines junkWorldEngines;
	private BitmapFont prof, level, jc;
	private TextureRegion levelBar;
	private Actor junkWorldAlbum;
	private boolean isLoading = false, playMusic = true;;
	private TextureRegion highestScoreRegion;
	private BitmapFont highestScoreDay, highestScoreEver;
	private FileHandle profileFile;
	private JsonValue jsonValue;
	private long delayToggle = 0;
	private Rectangle soundLayer, restorePurchasesLayer;
	private Actor sounds, restorePurchases;
	private long delayAds = TimeUtils.millis();

	private void disposeAssets(){
		if (batch == null) batch.dispose();
		if (mainMenuStage == null) mainMenuStage.dispose();
		if (prof == null) prof.dispose();
		if (level == null) level.dispose();
		if (jc == null) jc.dispose();
		if (highestScoreDay == null) highestScoreDay.dispose();
		if (highestScoreEver == null) highestScoreEver.dispose();
		this.dispose();
	}

	private void loadManager(){
		//		TODO
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar1.png", Texture.class);
		manager.load("backgrounds/firstBackground.png", Texture.class);
		manager.load("backgrounds/comingSoon.png", Texture.class);
		manager.load("mainMenuAssets/labels/titleLabel.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar2.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar0.png", Texture.class);
		manager.load("mainMenuAssets/junkWorldCoins.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/noJob.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/goToJob.png", Texture.class);
		manager.load("mainMenuAssets/buttonColor/yellowButton.png", Texture.class);
		manager.load("mainMenuAssets/buttonColor/blueButton.png", Texture.class);
		manager.load("mainMenuAssets/buttonColor/redButton.png", Texture.class);
		manager.load("mainMenuAssets/buttonColor/blackButton.png", Texture.class);
		manager.load("mainMenuAssets/buttonColor/yellow2Button.png", Texture.class);
		manager.load("mainMenuAssets/buttonColor/blue2Button.png", Texture.class);
		manager.load("mainMenuAssets/buttonColor/red2Button.png", Texture.class);
		manager.load("mainMenuAssets/buttonColor/black2Button.png", Texture.class);
		manager.load("mainMenuAssets/labels/playLabel.png", Texture.class);
		manager.load("mainMenuAssets/labels/singlePlayerLabel.png", Texture.class);
		manager.load("mainMenuAssets/labels/multiplayerLabel.png", Texture.class);
		manager.load("mainMenuAssets/labels/noAdsLabel.png", Texture.class);
		manager.load("mainMenuAssets/labels/quitLabel.png", Texture.class);
		manager.load("mainMenuAssets/labels/playLabelClicked.png", Texture.class);
		manager.load("mainMenuAssets/labels/singlePlayerLabelClicked.png", Texture.class);
		manager.load("mainMenuAssets/labels/multiplayerLabelClicked.png", Texture.class);
		manager.load("mainMenuAssets/labels/noAdsLabelClicked.png", Texture.class);
		manager.load("mainMenuAssets/labels/quitLabelClicked.png", Texture.class);
		manager.load("mainMenuAssets/buttonColor/trashAlbum.png", Texture.class);
		manager.load("mainMenuAssets/labels/junkworldAlbumLabel.png", Texture.class);
		manager.load("mainMenuAssets/labels/junkworldAlbumLabelClicked.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/bananaPeel.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/bottle.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/apple.png", Texture.class);
		manager.load("profileAssets/highestScore.png", Texture.class);
		manager.load("profileAssets/bestSurvivalRecord.png", Texture.class);
		manager.load("buttons/soundsOn.png", Texture.class);
		manager.load("buttons/soundsOff.png", Texture.class);
		manager.load("buttons/restorePurchases.png", Texture.class);

		if (junkWorldEngines.getGameMode() == 0){
			for (int i = 0; i < 29; i++){
				manager.unload("gameScreenAssets/tutorialAssets/Tutorials/" + i + ".png");
			}
			for (int i = 0; i < 3; i++){
				manager.unload("gameScreenAssets/tutorialAssets/arrow" + i + ".png");
			}
			for (Texture tutor : junkWorldEngines.getTutorial()){
				tutor.dispose();
			}
			for (Texture arrow : junkWorldEngines.getTutorialArrow()){
				arrow.dispose();
			}
			junkWorldEngines.setGameMode(1);
		}

		manager.finishLoading();
	}

	private void unloadManager(){
		//			TODO
		if (manager.isLoaded("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar1.png")) manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar1.png");
		if (manager.isLoaded("backgrounds/firstBackground.png")) manager.unload("backgrounds/firstBackground.png");
		if (manager.isLoaded("backgrounds/comingSoon.png")) manager.unload("backgrounds/comingSoon.png");
		if (manager.isLoaded("mainMenuAssets/labels/titleLabel.png")) manager.unload("mainMenuAssets/labels/titleLabel.png");
		if (manager.isLoaded("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar2.png")) manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar2.png");
		if (manager.isLoaded("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar0.png")) manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar0.png");
		if (manager.isLoaded("mainMenuAssets/junkWorldCoins.png")) manager.unload("mainMenuAssets/junkWorldCoins.png");
		if (manager.isLoaded("singlePlayerAssets/JunkWorld Map/miscellaneous/noJob.png")) manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/noJob.png");
		if (manager.isLoaded("singlePlayerAssets/JunkWorld Map/miscellaneous/goToJob.png")) manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/goToJob.png");
		if (manager.isLoaded("mainMenuAssets/buttonColor/yellowButton.png")) manager.unload("mainMenuAssets/buttonColor/yellowButton.png");
		if (manager.isLoaded("mainMenuAssets/buttonColor/blueButton.png")) manager.unload("mainMenuAssets/buttonColor/blueButton.png");
		if (manager.isLoaded("mainMenuAssets/buttonColor/redButton.png")) manager.unload("mainMenuAssets/buttonColor/redButton.png");
		if (manager.isLoaded("mainMenuAssets/buttonColor/blackButton.png")) manager.unload("mainMenuAssets/buttonColor/blackButton.png");
		if (manager.isLoaded("mainMenuAssets/buttonColor/yellow2Button.png")) manager.unload("mainMenuAssets/buttonColor/yellow2Button.png");
		if (manager.isLoaded("mainMenuAssets/buttonColor/blue2Button.png")) manager.unload("mainMenuAssets/buttonColor/blue2Button.png");
		if (manager.isLoaded("mainMenuAssets/buttonColor/red2Button.png")) manager.unload("mainMenuAssets/buttonColor/red2Button.png");
		if (manager.isLoaded("mainMenuAssets/buttonColor/black2Button.png")) manager.unload("mainMenuAssets/buttonColor/black2Button.png");
		if (manager.isLoaded("mainMenuAssets/labels/playLabel.png")) manager.unload("mainMenuAssets/labels/playLabel.png");
		if (manager.isLoaded("mainMenuAssets/labels/singlePlayerLabel.png")) manager.unload("mainMenuAssets/labels/singlePlayerLabel.png");
		if (manager.isLoaded("mainMenuAssets/labels/multiplayerLabel.png")) manager.unload("mainMenuAssets/labels/multiplayerLabel.png");
		if (manager.isLoaded("mainMenuAssets/labels/noAdsLabel.png")) manager.unload("mainMenuAssets/labels/noAdsLabel.png");
		if (manager.isLoaded("mainMenuAssets/labels/quitLabel.png")) manager.unload("mainMenuAssets/labels/quitLabel.png");
		if (manager.isLoaded("mainMenuAssets/labels/playLabelClicked.png")) manager.unload("mainMenuAssets/labels/playLabelClicked.png");
		if (manager.isLoaded("mainMenuAssets/labels/singlePlayerLabelClicked.png")) manager.unload("mainMenuAssets/labels/singlePlayerLabelClicked.png");
		if (manager.isLoaded("mainMenuAssets/labels/multiplayerLabelClicked.png")) manager.unload("mainMenuAssets/labels/multiplayerLabelClicked.png");
		if (manager.isLoaded("mainMenuAssets/labels/noAdsLabelClicked.png")) manager.unload("mainMenuAssets/labels/noAdsLabelClicked.png");
		if (manager.isLoaded("mainMenuAssets/labels/quitLabelClicked.png")) manager.unload("mainMenuAssets/labels/quitLabelClicked.png");
		if (manager.isLoaded("mainMenuAssets/buttonColor/trashAlbum.png")) manager.unload("mainMenuAssets/buttonColor/trashAlbum.png");
		if (manager.isLoaded("mainMenuAssets/labels/junkworldAlbumLabel.png")) manager.unload("mainMenuAssets/labels/junkworldAlbumLabel.png");
		if (manager.isLoaded("mainMenuAssets/labels/junkworldAlbumLabelClicked.png")) manager.unload("mainMenuAssets/labels/junkworldAlbumLabelClicked.png");
		if (manager.isLoaded("gameScreenAssets/trashAssets/biodegradableTrash/bananaPeel.png")) manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/bananaPeel.png");
		if (manager.isLoaded("gameScreenAssets/trashAssets/recyclableTrash/bottle.png")) manager.unload("gameScreenAssets/trashAssets/recyclableTrash/bottle.png");
		if (manager.isLoaded("gameScreenAssets/trashAssets/biodegradableTrash/apple.png")) manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/apple.png");
		if (manager.isLoaded("profileAssets/highestScore.png")) manager.unload("profileAssets/highestScore.png");
		if (manager.isLoaded("profileAssets/bestSurvivalRecord.png")) manager.unload("profileAssets/bestSurvivalRecord.png");
		if (manager.isLoaded("buttons/soundsOn.png")) manager.unload("buttons/soundsOn.png");
		if (manager.isLoaded("buttons/soundsOff.png")) manager.unload("buttons/soundsOff.png");
		if (manager.isLoaded("buttons/restorePurchases.png")) manager.unload("buttons/restorePurchases.png");
	}

	@SuppressWarnings("deprecation")
	public MainMenuScreen(final JunkWorld game, final AssetManager manager, final JunkWorldEngines junkWorldEngines) {
		this.manager = manager;
		this.game = game;
		this.junkWorldEngines = junkWorldEngines;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		touchPos = new Vector3();
		loadManager();

		//		LOAD TRASH ASSETS HERE!!!

		playLayer = new Rectangle(32, 525 + 20, 96, 96);
		singlePlayerLayer = new Rectangle(32, 425 + 20, 96, 96);
		multiplayerLayer = new Rectangle(32, 325 + 20, 96, 96);
		quitLayer = new Rectangle(32, 225 + 20, 96, 96);
		albumLayer = new Rectangle(395, 200 + 20, 81, 100);
		shopLayer = new Rectangle(0, 0, 0, 0);
		soundLayer = new Rectangle(280, 225 + 20, 60, 50);
		restorePurchasesLayer = new Rectangle(370, 364, 100, 50);

		levelBar = new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar1.png", Texture.class));

		highestScoreRegion = new TextureRegion(manager.get("profileAssets/highestScore.png", Texture.class));
		buttonShine = new Array<TextureRegion>();
		generator = new Random();
		profileFile = Gdx.files.local(junkWorldEngines.getProfileName() + ".json");
		jsonValue = new JsonReader().parse(profileFile);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SHOWG.TTF"));
		prof = generator.generateFont(29);
		level = generator.generateFont(22);
		jc = generator.generateFont(22);
		highestScoreDay = generator.generateFont(20);
		highestScoreEver = generator.generateFont(20);

		highestScoreDay.setColor(0, 1, 1, 1);
		highestScoreEver.setColor(0, 1, 1, 1);
		level.setColor(0, .2f, 0, 1);
		prof.setColor(0, .25f, 0, 1);
		jc.setColor(1, 1, 0, 1);
		generator.dispose();

		playButtonPressed = new Actor();
		singlePlayerButtonPressed = new Actor();
		multiplayerButtonPressed = new Actor();
		quitButtonPressed = new Actor();
		junkWorldAlbum = new Actor();
		sounds = new Actor();
		shop = new Actor();
		restorePurchases = new Actor();

		if (jsonValue.getInt("level") != 100){
			for (int i = 1; i < jsonValue.getInt("level"); i++){
				float lastXPUp;
				lastXPUp = previousXpUp;
				previousXpUp = xpUp;
				xpUp += (xpUp -lastXPUp) + ((xpUp - lastXPUp) * 0.09f);
			}
		}
		else {
			for (int i = 1; i < (jsonValue.getInt("level") - 1); i++){
				float lastXPUp;
				lastXPUp = previousXpUp;
				previousXpUp = xpUp;
				xpUp += (xpUp -lastXPUp) + ((xpUp - lastXPUp) * 0.09f);
			}
		}

		playButtonPressed.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				if (!playClicked &&
						!buttonClicked){
					playClicked  = true;
					buttonClicked = true;
				}
				return true;
			}
		});
		singlePlayerButtonPressed.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				junkWorldEngines.setGameSelection(1);
				singlePlayerClicked  = true;
				buttonClicked = true;
				return true;
			}
		});
		multiplayerButtonPressed.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				multiplayerClicked = true;
				delayToggle = TimeUtils.millis();
				return true;
			}
		});
		quitButtonPressed.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				quitClicked  = true;
				buttonClicked = true;
				return true;
			}
		});

		junkWorldAlbum.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				albumClicked = true;
				return true;
			}
		});

		shop.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				isLoading = true;
				unloadManager();
				disposeAssets();
				game.setScreen(new ShopScreen(game, manager, junkWorldEngines));
				return true;
			}
		});

		sounds.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				playMusic = true;
				if (junkWorldEngines.getCurrentBackgroundMusic() != 0){
					junkWorldEngines.setCurrentBackgroundMusic(0);
				} else junkWorldEngines.setCurrentBackgroundMusic(1);
				playMusic();
				return true;
			}
		});
		restorePurchases.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
//				game.jkPurchaser.restoreAllPurchase();
				return true;
			}
		});

		mainMenuStage = new Stage();
		mainMenuStage.addActor(playButtonPressed);
		mainMenuStage.addActor(singlePlayerButtonPressed);
		mainMenuStage.addActor(multiplayerButtonPressed);
		mainMenuStage.addActor(quitButtonPressed);
		mainMenuStage.addActor(junkWorldAlbum);
		mainMenuStage.addActor(sounds);
		mainMenuStage.addActor(restorePurchases);
		
		if (jsonValue.getString("currentJob").equals("none")){
			jsonValue.get("jobHighestPerfectCombo").set(0, null);
			jsonValue.get("jobTotalPerfectDump").set(0, null);
			jsonValue.get("jobTotalDump").set(0, null);
			jsonValue.get("jobTotalUnofficialDump").set(0, null);
			jsonValue.get("jobTotalNotDumped").set(0, null);
			profileFile.writeString(jsonValue.toString(), false);
		}

//		game.adManager.showBannerAds(true);
//		myRequestHandler.showAds2(true);
		loadMusic();
		
//		game.myRequestHandler.rateApp();
	}

	@Override
	public void render(float delta) {
		if (manager.update() && !isLoading){
			playMusic();
			createTextures();
			Gdx.gl.glClearColor(1, 1, 1, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			
			if (Gdx.input.justTouched()){
				delayAds = TimeUtils.millis();
			}
			if (TimeUtils.millis() - delayAds >= 5000){
				delayAds = TimeUtils.millis();
//				myRequestHandler.showAds2(true);
			}

			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			playButtonPressed.setBounds(playLayer.x, playLayer.y, playLayer.width, playLayer.height);
			singlePlayerButtonPressed.setBounds(singlePlayerLayer.x, singlePlayerLayer.y, singlePlayerLayer.width, singlePlayerLayer.height);
			multiplayerButtonPressed.setBounds(multiplayerLayer.x, multiplayerLayer.y, multiplayerLayer.width, multiplayerLayer.height);
			quitButtonPressed.setBounds(quitLayer.x, quitLayer.y, quitLayer.width, quitLayer.height);
			junkWorldAlbum.setBounds(albumLayer.x, albumLayer.y, albumLayer.width, albumLayer.height);
			shop.setBounds(shopLayer.x, shopLayer.y, shopLayer.width, shopLayer.height);
			sounds.setBounds(soundLayer.x, soundLayer.y, soundLayer.width, soundLayer.height);
			restorePurchases.setBounds(restorePurchasesLayer.x, restorePurchasesLayer.y,
					restorePurchasesLayer.width, restorePurchasesLayer.height);

			image1.setBounds(100, currentFallingTrash, 80, 80);
			image1.setOrigin(image1.getScaleX() + (image1.getWidth() / 2), image1.getScaleY() + (image1.getHeight() / 2));
			image1.rotate(.1f);

			image2.setBounds(250, currentFallingTrash + 600, 96, 96);
			image2.setOrigin(image2.getScaleX() + (image2.getWidth() / 2), image2.getScaleY() + (image2.getHeight() / 2));
			image2.rotate(.1f);

			image3.setBounds(400, currentFallingTrash + 1200, 50, 50);
			image3.setOrigin(image2.getScaleX() + (image2.getWidth() / 2), image2.getScaleY() + (image2.getHeight() / 2));
			image3.rotate(.1f);

			//			if (playClicked){
			//				if (Gdx.input.isTouched()){
			//					game.setScreen(new TrashCanSelectionScreen(game));
			//					this.dispose();
			//				}
			//			}

			batch.begin();
			batch.draw(manager.get("backgrounds/firstBackground.png", Texture.class), 0, 0);
			image1.draw(batch);
			image2.draw(batch);
			image3.draw(batch);
			batch.draw(manager.get("mainMenuAssets/labels/titleLabel.png", Texture.class), 5, 650 + 20, 369, 110);
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar2.png", Texture.class), 300 - 248, 525 + 20 + 50 - 420, 128, 32);
			batch.draw(levelBar, 300 - 248, playLayer.y + 50 - 420, ((jsonValue.getInt("currentXP") - previousXpUp) * 128) / (xpUp - previousXpUp), 32);
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/levelBar0.png", Texture.class), 300 - 248, playLayer.y + 50 - 420, 128, 32);
			batch.draw(manager.get("mainMenuAssets/junkWorldCoins.png", Texture.class), 300 - 248, playLayer.y + 18 - 420, 32, 32);
			prof.draw(batch, junkWorldEngines.getProfileName(), 300 - 248, playLayer.y + 111 - 420);
			level.draw(batch, String.valueOf(jsonValue.getInt("level")), 447 - 60 - 248, 525 + 20 + 74 - 420);
			jc.draw(batch, String.valueOf(jsonValue.getInt("junkCoins")), 330 - 248, playLayer.y + 42 - 420);

			if (junkWorldEngines.getCurrentJob().toString().equals("none")){
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJob.png", Texture.class), 370, 670, 128, 128);
			}
			else {
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/goToJob.png", Texture.class), 380, 670, 128, 128);
			}
			batch.draw(manager.get("mainMenuAssets/buttonColor/yellowButton.png", Texture.class), playLayer.x, playLayer.y, playLayer.width, playLayer.height);
			batch.draw(manager.get("mainMenuAssets/buttonColor/blueButton.png", Texture.class), singlePlayerLayer.x, singlePlayerLayer.y, singlePlayerLayer.width, singlePlayerLayer.height);
			batch.draw(manager.get("mainMenuAssets/buttonColor/redButton.png", Texture.class), multiplayerLayer.x, multiplayerLayer.y, multiplayerLayer.width, multiplayerLayer.height);
			batch.draw(manager.get("mainMenuAssets/buttonColor/blackButton.png", Texture.class), quitLayer.x, quitLayer.y, quitLayer.width, quitLayer.height);
			switch (currentButtonShine){
			case 0: batch.draw(manager.get("mainMenuAssets/buttonColor/yellow2Button.png", Texture.class), playLayer.x, playLayer.y, playLayer.width, playLayer.height); break;
			case 1: batch.draw(manager.get("mainMenuAssets/buttonColor/blue2Button.png", Texture.class), singlePlayerLayer.x, singlePlayerLayer.y, singlePlayerLayer.width, singlePlayerLayer.height); break;
			case 2: batch.draw(manager.get("mainMenuAssets/buttonColor/red2Button.png", Texture.class), multiplayerLayer.x, multiplayerLayer.y, multiplayerLayer.width, multiplayerLayer.height); break;
			case 3: batch.draw(manager.get("mainMenuAssets/buttonColor/black2Button.png", Texture.class), quitLayer.x, quitLayer.y, quitLayer.width, quitLayer.height); break;
			default: break;
			}
			switch (currentLabelPos){
			case 0: {
				batch.draw(currentAlbumRegion, albumLayer.x, albumLayer.y, albumLayer.width, albumLayer.height);
				batch.draw(albumLabelRegion, albumLayer.x - 10, albumLayer.y - 50, albumLayer.width + 20, 64);
				if (albumClicked){
					batch.draw(albumLabelClickedRegion, albumLayer.x - 10, albumLayer.y - 50, albumLayer.width + 20, 64);
				}
			}; break;
			case 1: {
				batch.draw(currentAlbumRegion, albumLayer.x, albumLayer.y - 2, albumLayer.width, albumLayer.height);
				batch.draw(albumLabelRegion, albumLayer.x - 10, albumLayer.y - 50 - 2, albumLayer.width + 20, 64);
				if (albumClicked){
					batch.draw(albumLabelClickedRegion, albumLayer.x - 10, albumLayer.y - 50 - 2, albumLayer.width + 20, 64);
				}
			}; break;
			case 2: {
				batch.draw(currentAlbumRegion, albumLayer.x, albumLayer.y - 4, albumLayer.width, albumLayer.height);
				batch.draw(albumLabelRegion, albumLayer.x - 10, albumLayer.y - 50 - 4, albumLayer.width + 20, 64);
				if (albumClicked){
					batch.draw(albumLabelClickedRegion, albumLayer.x - 10, albumLayer.y - 50 - 4, albumLayer.width + 20, 64);
				}
			}; break;
			case 3: {
				batch.draw(currentAlbumRegion, albumLayer.x, albumLayer.y - 6, albumLayer.width, albumLayer.height);
				batch.draw(albumLabelRegion, albumLayer.x - 10, albumLayer.y - 50 - 6, albumLayer.width + 20, 64);
				if (albumClicked){
					batch.draw(albumLabelClickedRegion, albumLayer.x - 10, albumLayer.y - 50 - 6, albumLayer.width + 20, 64);
				}
			}; break;
			case 4: {
				batch.draw(currentAlbumRegion, albumLayer.x, albumLayer.y - 8, albumLayer.width, albumLayer.height);
				batch.draw(albumLabelRegion, albumLayer.x - 10, albumLayer.y - 50 - 8, albumLayer.width + 20, 64);
				if (albumClicked){
					batch.draw(albumLabelClickedRegion, albumLayer.x - 10, albumLayer.y - 50 - 8, albumLayer.width + 20, 64);
				}
			}; break;
			case 5: {
				batch.draw(currentAlbumRegion, albumLayer.x, albumLayer.y - 10, albumLayer.width, albumLayer.height);
				batch.draw(albumLabelRegion, albumLayer.x - 10, albumLayer.y - 50 - 10, albumLayer.width + 20, 64);
				if (albumClicked){
					batch.draw(albumLabelClickedRegion, albumLayer.x - 10, albumLayer.y - 50 - 10, albumLayer.width + 20, 64);
				}
			}; break;
			case 6: {
				batch.draw(currentAlbumRegion, albumLayer.x, albumLayer.y - 12, albumLayer.width, albumLayer.height);
				batch.draw(albumLabelRegion, albumLayer.x - 10, albumLayer.y - 50 - 12, albumLayer.width + 20, 64);
				if (albumClicked){
					batch.draw(albumLabelClickedRegion, albumLayer.x - 10, albumLayer.y - 50 - 12, albumLayer.width + 20, 64);
				}
			}; break;
			case 7: {
				batch.draw(currentAlbumRegion, albumLayer.x, albumLayer.y - 14, albumLayer.width, albumLayer.height);
				batch.draw(albumLabelRegion, albumLayer.x - 10, albumLayer.y - 50 - 14, albumLayer.width + 20, 64);
				if (albumClicked){
					batch.draw(albumLabelClickedRegion, albumLayer.x - 10, albumLayer.y - 50 - 14, albumLayer.width + 20, 64);
				}
			}; break;
			default: break;
			}
			if (!playClicked){
				batch.draw(manager.get("mainMenuAssets/labels/playLabel.png", Texture.class), 133, playLayer.y, 137, playLayer.height);
			}
			if (!singlePlayerClicked){
				batch.draw(manager.get("mainMenuAssets/labels/singlePlayerLabel.png", Texture.class), 133, singlePlayerLayer.y, 351, singlePlayerLayer.height);
			}
			if (!multiplayerClicked){
				batch.draw(manager.get("mainMenuAssets/labels/noAdsLabel.png", Texture.class), 133, multiplayerLayer.y, 221, multiplayerLayer.height);
			}
			if (!quitClicked){
				batch.draw(manager.get("mainMenuAssets/labels/quitLabel.png", Texture.class), 133, quitLayer.y, 151, quitLayer.height);
			}
			if (playClicked){
				batch.draw(manager.get("mainMenuAssets/labels/playLabelClicked.png", Texture.class), 133, playLayer.y, 137, playLayer.height);
			}
			if (singlePlayerClicked){
				batch.draw(manager.get("mainMenuAssets/labels/singlePlayerLabelClicked.png", Texture.class), 133, singlePlayerLayer.y, 351, singlePlayerLayer.height);
			}
			if (multiplayerClicked){
				batch.draw(manager.get("mainMenuAssets/labels/noAdsLabelClicked.png", Texture.class), 133, multiplayerLayer.y, 221, multiplayerLayer.height);
			}
			if (quitClicked){
				batch.draw(manager.get("mainMenuAssets/labels/quitLabelClicked.png", Texture.class), 133, quitLayer.y, 151, quitLayer.height);
			}

			if (displayLoading){
				batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader.png", Texture.class), 0, 0, 512, 800);
				batch.draw(manager.get("mainMenuAssets/loadingAssets/loadingCircularSigns.png", Texture.class), 64, 208);
			}

			batch.draw(highestScoreRegion, 272, 645, 190, 40);
			batch.draw(manager.get("profileAssets/bestSurvivalRecord.png", Texture.class), 270, 560);
			highestScoreDay.draw(batch, String.valueOf(jsonValue.getInt("highestScoreEver")), 275, 645);
			highestScoreEver.draw(batch, String.valueOf(jsonValue.getInt("survivalMinute")) + ":" + String.valueOf(jsonValue.getInt("survivalSeconds")), 450, 595);
			if (junkWorldEngines.getCurrentBackgroundMusic() != 0){
				batch.draw(manager.get("buttons/soundsOn.png", Texture.class), soundLayer.x, soundLayer.y, soundLayer.width, soundLayer.height);
			}
			else {
				batch.draw(manager.get("buttons/soundsOff.png", Texture.class), soundLayer.x, soundLayer.y, soundLayer.width, soundLayer.height);
			}
			batch.draw(manager.get("buttons/restorePurchases.png", Texture.class), restorePurchasesLayer.x, restorePurchasesLayer.y, restorePurchasesLayer.width, restorePurchasesLayer.height);
			if (comingSoon){
				batch.draw(manager.get("backgrounds/comingSoon.png", Texture.class), 256 - 150, 400 - 32);
			}
			batch.end();

			currentFallingTrash -= 1;
			if (currentFallingTrash <= -1264){
				currentFallingTrash = 800;
			}
		}
		threadDelays();
		buttonDelay();

		if (comingSoon){
			if (Gdx.input.justTouched()){
				comingSoon = false;
			}
		}
		else {
			mainMenuStage.getViewport().setCamera(camera);
			mainMenuStage.act();
			mainMenuStage.draw();
			if (!buttonClicked){
				Gdx.input.setInputProcessor(mainMenuStage);
			}
		}
	}

	private void threadDelays() {
		if (delayCurrentButtonShine != 0){
			if (TimeUtils.millis() - delayCurrentButtonShine >= 1100){
				oldRandom = currentButtonShine;
				generator = new Random();
				if (oldRandom == generator.nextInt(4)){
					if (generator.nextInt(4) - 1 == -1){
						currentButtonShine = 3;
					}
					else if (generator.nextInt(4) + 1 == 4){
						currentButtonShine = 0;
					}
					else {
						currentButtonShine = generator.nextInt(4) + 1;
					}
				}
				else {
					currentButtonShine = generator.nextInt(4);
				}
				delayCurrentButtonShine = TimeUtils.millis();
			}
		}

		if (delayCurrentLabelPos != 0){
			if (TimeUtils.millis() - delayCurrentLabelPos >= 100){
				if (upwardDirect){
					currentLabelPos = currentLabelPos + 1;
					if (currentLabelPos == 7){
						upwardDirect = false;
					}
				}
				else {
					currentLabelPos = currentLabelPos - 1;
					if (currentLabelPos == 0){
						upwardDirect = true;
					}
				}
				delayCurrentLabelPos = TimeUtils.millis();
			}
		}

		if (albumClicked){
			isLoading = true;
			unloadManager();
			disposeAssets();
			game.setScreen(new JunkWorldAlbumScreen(game, manager, junkWorldEngines));
		}
	}

	private void createTextures() {
		if (createTextures){
			createTextures = false;

			buttonShine.add(new TextureRegion(manager.get("mainMenuAssets/buttonColor/yellow2Button.png", Texture.class)));
			buttonShine.add(new TextureRegion(manager.get("mainMenuAssets/buttonColor/blue2Button.png", Texture.class)));
			buttonShine.add(new TextureRegion(manager.get("mainMenuAssets/buttonColor/red2Button.png", Texture.class)));
			buttonShine.add(new TextureRegion(manager.get("mainMenuAssets/buttonColor/black2Button.png", Texture.class)));
			currentAlbumRegion = new TextureRegion(manager.get("mainMenuAssets/buttonColor/trashAlbum.png", Texture.class));
			albumLabelRegion = new TextureRegion(manager.get("mainMenuAssets/labels/junkworldAlbumLabel.png", Texture.class));
			albumLabelClickedRegion = new TextureRegion(manager.get("mainMenuAssets/labels/junkworldAlbumLabelClicked.png", Texture.class));

			image1 = new Sprite(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/bananaPeel.png", Texture.class));
			image2 = new Sprite(manager.get("gameScreenAssets/trashAssets/recyclableTrash/bottle.png", Texture.class));
			image3 = new Sprite(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/apple.png", Texture.class));
		}
	}

	private void buttonDelay() {
		if (delayToggle != 0){
			if (TimeUtils.millis() - delayToggle >= 50){
				delayToggle = 0;
				multiplayerClicked = false;

//				if (game.adManager.noAds()){
//					game.myRequestHandler.showToast("Item already purchased.");
//					game.adManager.disableAllAds();
//				}
//				else {
//					game.jkPurchaser.purchaseNoAd();
//				}
			}
		}

		if (buttonClicked){
			buttonClicked = false;
			playButtonPressed.clearListeners();
			singlePlayerButtonPressed.clearListeners();
			multiplayerButtonPressed.clearListeners();;
			quitButtonPressed.clearListeners();
			delay = TimeUtils.nanoTime();
			delay2 = System.currentTimeMillis();
		}
		if (delay != 0){
			if (TimeUtils.nanoTime() - delay > 1000000000){
				delay = 0;
				displayLoading = true;
			}
		}

		if (displayLoading){
			if (playClicked){
				if (System.currentTimeMillis() - delay2 > 1000){
					junkWorldEngines.setGameSelection(1);
					isLoading = true;
					unloadManager();
					disposeAssets();
					game.setScreen(new DifficultySelectionScreen(game, manager, junkWorldEngines));
				}
			}
			if (singlePlayerClicked){
				if (System.currentTimeMillis() - delay2 > 0){
					junkWorldEngines.setGameSelection(2);
//					game.adManager.showBannerAds(false);
					isLoading = true;
					unloadManager();
					disposeAssets();
					game.setScreen(new JunkWorldMapScreen(game, manager, junkWorldEngines));
				}
			}
		}

		if (quitClicked){
			isLoading = true;
			unloadManager();
			disposeAssets();
			Gdx.app.exit();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	private void loadMusic(){
		switch (junkWorldEngines.getCurrentBackgroundMusic()){
		case 0: {
			if (manager.isLoaded("audioAssets/music/junkWorld.ogg", Music.class)){
				manager.unload("audioAssets/music/junkWorld.ogg");
			}
			if (manager.isLoaded("audioAssets/music/house.ogg", Music.class)){
				manager.unload("audioAssets/music/house.ogg");
			}
			if (manager.isLoaded("audioAssets/music/city.ogg", Music.class)){
				manager.unload("audioAssets/music/city.ogg");
			}
		}; break;
		case 1: {
			if (!manager.isLoaded("audioAssets/music/junkWorld.ogg", Music.class)){
				manager.load("audioAssets/music/junkWorld.ogg", Music.class);
			}
			if (manager.isLoaded("audioAssets/music/house.ogg", Music.class)){
				manager.unload("audioAssets/music/house.ogg");
			}
			if (manager.isLoaded("audioAssets/music/city.ogg", Music.class)){
				manager.unload("audioAssets/music/city.ogg");
			}
		}; break;
		case 2: {
			if (!manager.isLoaded("audioAssets/music/house.ogg", Music.class)){
				manager.load("audioAssets/music/house.ogg", Music.class);
			}
			if (manager.isLoaded("audioAssets/music/junkWorld.ogg", Music.class)){
				manager.unload("audioAssets/music/junkWorld.ogg");
			}
			if (manager.isLoaded("audioAssets/music/city.ogg", Music.class)){
				manager.unload("audioAssets/music/city.ogg");
			}
		}; break;
		case 3: {
			if (!manager.isLoaded("audioAssets/music/city.ogg", Music.class)){
				manager.load("audioAssets/music/city.ogg", Music.class);
			}
			if (manager.isLoaded("audioAssets/music/junkWorld.ogg", Music.class)){
				manager.unload("audioAssets/music/junkWorld.ogg");
			}
			if (manager.isLoaded("audioAssets/music/house.ogg", Music.class)){
				manager.unload("audioAssets/music/house.ogg");
			}
		}; break;
		default: break;
		}
	}

	private void playMusic(){
		if (playMusic){
			playMusic = false;
			switch (junkWorldEngines.getCurrentBackgroundMusic()){
			case 0: {
				if (manager.isLoaded("audioAssets/music/city.ogg", Music.class)){
					manager.get("audioAssets/music/city.ogg", Music.class).stop();
				}
				if (manager.isLoaded("audioAssets/music/junkWorld.ogg", Music.class)){
					manager.get("audioAssets/music/junkWorld.ogg", Music.class).stop();
				}
				if (manager.isLoaded("audioAssets/music/house.ogg", Music.class)){
					manager.get("audioAssets/music/house.ogg", Music.class).stop();
				}
			}; break;
			case 1: {
				if (!manager.get("audioAssets/music/junkWorld.ogg", Music.class).isPlaying()){
					manager.get("audioAssets/music/junkWorld.ogg", Music.class).setLooping(true);
					manager.get("audioAssets/music/junkWorld.ogg", Music.class).play();
				}
			}; break;
			case 2: {
				if (!manager.get("audioAssets/music/house.ogg", Music.class).isPlaying()){
					manager.get("audioAssets/music/house.ogg", Music.class).setLooping(true);
					manager.get("audioAssets/music/house.ogg", Music.class).play();
				}
			}; break;
			case 3: {
				if (!manager.get("audioAssets/music/city.ogg", Music.class).isPlaying()){
					manager.get("audioAssets/music/city.ogg", Music.class).setLooping(true);
					manager.get("audioAssets/music/city.ogg", Music.class).play();
				}
			}; break;
			default: break;
			}
		}
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
//		game.adManager.showBannerAds(false);
	}

	@Override
	public void resume() {
//		game.adManager.showBannerAds(true);
	}

	@Override
	public void dispose(){ 
	}
}
