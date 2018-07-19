package com.junkworld.game;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;
import com.badlogic.gdx.utils.TimeUtils;

public class JunkWorldMapScreen implements Screen {
	private JunkWorld game;
	private AssetManager manager;
	private OrthographicCamera camera;
	private Batch batch;
	private Vector3 touchPos;
	private Stage stage, stage2, stage3, stage4;
	private Rectangle leftArrowLayer, rightArrowLayer; 
	private CurrentSelectedCommunity currentSelectedCommunity;
	private Actor house, city, forest, ok, left, right, ok2, left2, right2, back, back2, back3, accept, yes, no, cancel;
	private JunkWorldEngines junkWorldEngines;
	private CurrentSinglePlayerState currentSinglePlayerState;
	private int unlockedComm = 0;
	private int currentSelectedComm = 1, currentAreaIndex = 1;
	private Array<Integer> houseAreasNums, cityAreasNums;
	private Array<TextureRegion> houseAreasRegion, cityAreasRegion;
	private CurrentSelectedArea currentSelectedArea;
	private BitmapFont stageDesc, profileData, font1, font2, font3, gameModeTitle;
	private Rectangle stagesLayer, cancelLayer;
	private boolean notify = false;
	private Random generator;
	private int gameMode, garbageLimit, fee;
	private float garbageSpeed;
	private Array<Float> spawnTime;
	private Array<Integer> timerArray, garbageAmountArray, roundsArray, garbageGoalArrayRandom;
	private Array<Boolean> hasJobArray;
	private FileHandle profileFile;
	private Json json;
	private JsonValue jsonValue;
	private float currentDifficulty;
	private Array<Float> ave1;
	private Array<Array<Float>> spawnTimeValue;
	private Array<Sprite> difficultyBar;
	private long delayNotifier = TimeUtils.millis();
	private TextureRegion notifier0, notifier1;
	private int currentStageState = 0;
	private boolean isLoading = false, playMusic = true;
	private int perfectJC;
	private float[] spawnTimeFloat = new float[5];
	private long delayAds = TimeUtils.millis();

	private void disposeAssets(){
		batch.dispose();
		stage.dispose();
		stage2.dispose();
		stage3.dispose();
		stage4.dispose();
		stageDesc.dispose();
		profileData.dispose();
		font1.dispose();
		font2.dispose();
		font3.dispose();
		gameModeTitle.dispose();
		this.dispose();
	}

	private void loadManager(){
		//		TODO
		manager.load("singlePlayerAssets/JunkWorld Areas/House/Kitchen.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Areas/House/Basement.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Areas/House/Garage.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Areas/House/Backyard.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Areas/House/Sidewalk.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Areas/City/Factory.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Areas/City/Highway.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Areas/City/Park.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Areas/City/Sewer.png", Texture.class);
		manager.load("backgrounds/firstBackground.png", Texture.class);
		manager.load("backgrounds/greenBackground.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/house.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/city.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/forest.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/hyphen.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/level.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/jc.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/kitchen.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/basement.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/garage.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/backyard.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/sidewalk.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/factory.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/highway.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/park.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/sewers.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/onCancel.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class);
		manager.load("backgrounds/mainJunkworldFrame.png", Texture.class);
		manager.load("profileAssets/accept.png", Texture.class);
		manager.load("profileAssets/continue.png", Texture.class);
		manager.load("profileAssets/cancel.png", Texture.class);
		manager.load("backgrounds/stageDesc.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/noRequirements.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/selectAStage.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/areas.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/notifier0.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/notifier1.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/yesButton.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/noButton.png", Texture.class);
		manager.load("backgrounds/grassTexture.png", Texture.class);
		manager.load("profileAssets/ok.png", Texture.class);
		manager.load("profileAssets/back.png", Texture.class);
		for (int i = 0; i < 3; i++){
			manager.load("profileAssets/triesLeft/" + i + ".png", Texture.class);
		}
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/levelAssets/grassLand.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/selectACommunity.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/communityAssets/city.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/city.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/communityAssets/cityClicked.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/communityAssets/house.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/house.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/communityAssets/houseClicked.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/communityAssets/forest.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/forest.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/communityAssets/forestClicked.png", Texture.class);
		for (int i = 0; i < 10; i++){
			manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/difficultyBars/" + i + ".png", Texture.class);
		}
		manager.finishLoading();
	}

	private void unloadManager(){
		//		TODO
		manager.unload("singlePlayerAssets/JunkWorld Areas/House/Kitchen.png");
		manager.unload("singlePlayerAssets/JunkWorld Areas/House/Basement.png");
		manager.unload("singlePlayerAssets/JunkWorld Areas/House/Garage.png");
		manager.unload("singlePlayerAssets/JunkWorld Areas/House/Backyard.png");
		manager.unload("singlePlayerAssets/JunkWorld Areas/House/Sidewalk.png");
		manager.unload("singlePlayerAssets/JunkWorld Areas/City/Factory.png");
		manager.unload("singlePlayerAssets/JunkWorld Areas/City/Highway.png");
		manager.unload("singlePlayerAssets/JunkWorld Areas/City/Park.png");
		manager.unload("singlePlayerAssets/JunkWorld Areas/City/Sewer.png");
		manager.unload("backgrounds/firstBackground.png");
		manager.unload("backgrounds/greenBackground.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/house.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/city.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/forest.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/hyphen.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/level.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/jc.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/kitchen.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/basement.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/garage.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/backyard.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/sidewalk.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/factory.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/highway.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/park.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/sewers.png");
		manager.unload("backgrounds/mainJunkworldFrame.png");
		manager.unload("profileAssets/accept.png");
		manager.unload("profileAssets/continue.png");
		manager.unload("profileAssets/cancel.png");
		manager.unload("backgrounds/stageDesc.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/noRequirements.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/selectAStage.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/areas.png");
		manager.unload("backgrounds/grassTexture.png");
		manager.unload("profileAssets/ok.png");
		manager.unload("profileAssets/back.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/levelAssets/grassLand.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/selectACommunity.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/communityAssets/city.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/city.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/communityAssets/cityClicked.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/communityAssets/house.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/house.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/communityAssets/houseClicked.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/communityAssets/forest.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/forest.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/communityAssets/forestClicked.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/notifier0.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/notifier1.png");
		for (int i = 0; i < 10; i++){
			manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/difficultyBars/" + i + ".png");
		}
		for (int i = 0; i < 3; i++){
			manager.unload("profileAssets/triesLeft/" + i + ".png");
		}
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/yesButton.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/noButton.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/onCancel.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png");
	}

	@SuppressWarnings("deprecation")
	public JunkWorldMapScreen(final JunkWorld game, final AssetManager manager,
			final JunkWorldEngines junkWorldEngines){
		this.game = game;
		this.manager = manager;
		this.junkWorldEngines = junkWorldEngines;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		batch = new SpriteBatch();
		touchPos = new Vector3(); 
		stage = new Stage();
		stage2 = new Stage();
		stage3 = new Stage();
		stage4 = new Stage();
		stagesLayer = new Rectangle();
		json = new Json();
		json.setUsePrototypes(false);
		loadManager();

		stagesLayer.setHeight(30);
		generator = new Random();
		spawnTime = new Array<Float>();
		timerArray = new Array<Integer>();
		garbageAmountArray = new Array<Integer>();
		garbageGoalArrayRandom = new Array<Integer>();
		roundsArray = new Array<Integer>();
		spawnTimeValue = new Array<Array<Float>>();
		ave1 = new Array<Float>();
		ave1.add(5f);
		ave1.add(4f);
		ave1.add(3f);
		ave1.add(2f);
		difficultyBar = new Array<Sprite>();
		for (int i = 0; i < 10; i++){
			difficultyBar.add(new Sprite(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/difficultyBars/" + i + ".png", Texture.class)));
		}
		notifier0 = new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/notifier0.png", Texture.class));
		notifier1 = new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/notifier1.png", Texture.class));

		garbageLimit = (junkWorldEngines.getLevel() / 5) + 3;
		garbageSpeed =   (float) (0.05 + ((((float)(junkWorldEngines.getLevel()) + 4) / 5) * 0.05));
		perfectJC = ((junkWorldEngines.getLevel() + 9 / 10)) * 3;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SHOWG.TTF"));
		stageDesc = generator.generateFont(22);
		stageDesc.setColor(0, .4f, .4f, 1);
		profileData = generator.generateFont(26);
		profileData.setColor(1, 1, 0, 1);
		font1 = generator.generateFont(22);
		font1.setColor(1, 1, 0, 1);
		font2 = generator.generateFont(22);
		font2.setColor(0, .2f, 0, 1);
		font3 = generator.generateFont(22);
		font3.setColor(1, 0, 0, 1);
		gameModeTitle = generator.generateFont(45);
		gameModeTitle.setColor(0, 0, 1, 1);

		generator.dispose();

		houseAreasNums = new Array<Integer>();
		cityAreasNums = new Array<Integer>();
		houseAreasRegion = new Array<TextureRegion>();
		cityAreasRegion = new Array<TextureRegion>();
		hasJobArray = new Array<Boolean>();

		houseAreasNums.add(1);
		if (junkWorldEngines.isBasementUnlocked()){
			houseAreasNums.add(2);
		}
		if (junkWorldEngines.isGarageUnlocked()){
			houseAreasNums.add(3);
		}
		if (junkWorldEngines.isBackyardUnlocked()){
			houseAreasNums.add(4);
		}
		if (junkWorldEngines.isSidewalkUnlocked()){
			houseAreasNums.add(5);
		}

		if (junkWorldEngines.isFactoryUnlocked()){
			cityAreasNums.add(1);
		}
		if (junkWorldEngines.isHighwayUnlocked()){
			cityAreasNums.add(2);
		}
		if (junkWorldEngines.isParkUnlocked()){
			cityAreasNums.add(3);
		}
		if (junkWorldEngines.isSewerUnlocked()){
			cityAreasNums.add(4);
		}

		setRegions();

		leftArrowLayer = new Rectangle(256 - 200, 234, 64, 64);
		rightArrowLayer = new Rectangle(256 + 200 - 64, 234, 64, 64);
		cancelLayer = new Rectangle(206, 280, 100, 40);

		if (junkWorldEngines.isHouseUnlocked()){
			unlockedComm += 1;
		}
		if (junkWorldEngines.isCityUnlocked()){
			unlockedComm += 1;
		}
		if (junkWorldEngines.isForestUnlocked()){
			unlockedComm += 1;
		}

		house = new Actor();
		city = new Actor();
		forest = new Actor();
		ok = new Actor();
		ok2 = new Actor();
		right = new Actor();
		left = new Actor();
		right2 = new Actor();
		left2 = new Actor();
		back = new Actor();
		back2 = new Actor();
		back3 = new Actor();
		accept = new Actor();
		yes = new Actor();
		no = new Actor();
		cancel = new Actor();

		house.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (junkWorldEngines.isHouseUnlocked() && houseAreasNums.size != 0){
					currentSelectedComm = 1;
					currentSelectedCommunity = CurrentSelectedCommunity.HOUSE;
					currentSelectedArea = CurrentSelectedArea.KITCHEN;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		city.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (junkWorldEngines.isCityUnlocked() && cityAreasNums.size != 0){
					currentSelectedComm = 2;
					currentSelectedCommunity = CurrentSelectedCommunity.CITY;
					currentSelectedArea = CurrentSelectedArea.FACTORY;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		forest.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (junkWorldEngines.isForestUnlocked()){
					currentSelectedComm = 3;
					currentSelectedCommunity = CurrentSelectedCommunity.FOREST;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		ok.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.log("Debug", "ok clicked");
				switch (currentSelectedCommunity){
				case HOUSE: {
					if (houseAreasNums.size != 0){
						currentSelectedArea = CurrentSelectedArea.KITCHEN;
						currentSinglePlayerState = CurrentSinglePlayerState.AREA_SELECTION;
					}
				}; break;
				case CITY: {
					if (cityAreasNums.size != 0){
						currentSelectedArea = CurrentSelectedArea.FACTORY;
						currentSinglePlayerState = CurrentSinglePlayerState.AREA_SELECTION;
					}
				}; break;
				default: break;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		left.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentSelectedComm - 1 == 0){
					currentSelectedComm = unlockedComm;
				} else currentSelectedComm -= 1;
				currentAreaIndex = 1;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		right.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentSelectedComm + 1 > unlockedComm){
					currentSelectedComm = 1;
				} else currentSelectedComm += 1;
				currentAreaIndex = 1;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		left2.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				switch (currentSelectedCommunity){
				case HOUSE: {
					if (currentAreaIndex - 1 == 0){
						currentAreaIndex = 5;
					} else currentAreaIndex -= 1;
					while (!houseAreasNums.contains(currentAreaIndex, false)){
						if (currentAreaIndex - 1 == 0){
							currentAreaIndex = 5;
						} else currentAreaIndex -= 1;
					}

					switch (currentAreaIndex){
					case 1: currentSelectedArea = CurrentSelectedArea.KITCHEN; break;
					case 2: currentSelectedArea = CurrentSelectedArea.BASEMENT; break;
					case 3: currentSelectedArea = CurrentSelectedArea.GARAGE; break;
					case 4: currentSelectedArea = CurrentSelectedArea.BACKYARD; break;
					case 5: currentSelectedArea = CurrentSelectedArea.SIDEWALK; break;
					default: break;
					}
				}; break;
				case CITY: {
					if (currentAreaIndex - 1 == 0){
						currentAreaIndex = 4;
					} else currentAreaIndex -= 1;
					while (!cityAreasNums.contains(currentAreaIndex, false)){
						if (currentAreaIndex - 1 == 0){
							currentAreaIndex = 4;
						} else currentAreaIndex -= 1;
					}

					switch (currentAreaIndex){
					case 1: currentSelectedArea = CurrentSelectedArea.FACTORY; break;
					case 2: currentSelectedArea = CurrentSelectedArea.HIGHWAY; break;
					case 3: currentSelectedArea = CurrentSelectedArea.PARK; break;
					case 4: currentSelectedArea = CurrentSelectedArea.SEWERS; break;
					default: break;
					}
				}; break;
				case FOREST: {

				}; break;
				default: break;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		right2.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				switch (currentSelectedCommunity){
				case HOUSE: {
					if (currentAreaIndex + 1 > 5){
						currentAreaIndex = 1;
					} else currentAreaIndex += 1;
					while (!houseAreasNums.contains(currentAreaIndex, false)){
						if (currentAreaIndex + 1 > 5){
							currentAreaIndex = 1;
						} else currentAreaIndex += 1;
					}
					switch (currentAreaIndex){
					case 1: currentSelectedArea = CurrentSelectedArea.KITCHEN; break;
					case 2: currentSelectedArea = CurrentSelectedArea.BASEMENT; break;
					case 3: currentSelectedArea = CurrentSelectedArea.GARAGE; break;
					case 4: currentSelectedArea = CurrentSelectedArea.BACKYARD; break;
					case 5: currentSelectedArea = CurrentSelectedArea.SIDEWALK; break;
					default: break;
					}

				}; break;
				case CITY: {
					if (currentAreaIndex + 1 > 4){
						currentAreaIndex = 1;
					} else currentAreaIndex += 1;
					while (!cityAreasNums.contains(currentAreaIndex, false)){
						if (currentAreaIndex + 1 > 4){
							currentAreaIndex = 1;
						} else currentAreaIndex += 1;
					}
					switch (currentAreaIndex){
					case 1: currentSelectedArea = CurrentSelectedArea.FACTORY; break;
					case 2: currentSelectedArea = CurrentSelectedArea.HIGHWAY; break;
					case 3: currentSelectedArea = CurrentSelectedArea.PARK; break;
					case 4: currentSelectedArea = CurrentSelectedArea.SEWERS; break;
					default: break;
					}
				}; break;
				case FOREST: {

				}; break;
				default: break;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		ok2.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
//				game.adManager.showBannerAds(false);
				currentSinglePlayerState = CurrentSinglePlayerState.STAGE_SELECTION;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		back.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenuScreen(game, manager, junkWorldEngines));
				isLoading = true;
				unloadManager();
				disposeAssets();
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		back2.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				currentSinglePlayerState = CurrentSinglePlayerState.COMMUNITY_SELECTION;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		back3.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
//				game.adManager.showBannerAds(true);
				currentSinglePlayerState = CurrentSinglePlayerState.AREA_SELECTION;
				currentStageState = 0;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		no.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				notify = false;
				currentStageState = 0;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		setGameEngines();

		stage.addActor(house);
		stage.addActor(city);
		stage.addActor(forest);
		stage.addActor(ok);
		stage.addActor(left);
		stage.addActor(right);
		stage.addActor(back);

		stage2.addActor(ok2);
		stage2.addActor(left2);
		stage2.addActor(right2);
		stage2.addActor(back2);

		stage3.addActor(back3);
		stage3.addActor(accept);
		stage3.addActor(cancel);

		stage4.addActor(yes);
		stage4.addActor(no);

		currentSelectedCommunity = CurrentSelectedCommunity.HOUSE;
		currentSinglePlayerState = CurrentSinglePlayerState.COMMUNITY_SELECTION;
		currentSelectedArea = CurrentSelectedArea.KITCHEN;

		checkJobs();
		JsonIterator iter = jsonValue.get("spawnTime").iterator();
		for (int i = 0; i < 9; i++){
			spawnTimeValue.add(new Array<Float>());
			JsonIterator iter2 = iter.next().iterator();
			for (int i2 = 0; i2 < 5; i2++){
				spawnTimeValue.get(i).add(iter2.next().asFloat());
			}
		}

		cancel.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				notify = true;
				currentStageState = 2;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		accept.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					switch (currentSelectedArea){
					case KITCHEN: {
						if (jsonValue.get("hasJob").getBoolean(0)){
							calculateJC();
						}
					}; break;
					case BASEMENT: {
						if (jsonValue.get("hasJob").getBoolean(1)){
							calculateJC();
						}
					}; break;
					case GARAGE: {
						if (jsonValue.get("hasJob").getBoolean(2)){
							calculateJC();
						}
					}; break;
					case BACKYARD: {
						if (jsonValue.get("hasJob").getBoolean(3)){
							calculateJC();
						}
					}; break;
					case SIDEWALK: {
						if (jsonValue.get("hasJob").getBoolean(4)){
							calculateJC();
						}
					}; break;
					case FACTORY: {
						if (jsonValue.get("hasJob").getBoolean(5)){
							calculateJC();
						}
					}; break;
					case HIGHWAY: {
						if (jsonValue.get("hasJob").getBoolean(6)){
							calculateJC();
						}
					}; break;
					case PARK: {
						if (jsonValue.get("hasJob").getBoolean(7)){
							calculateJC();
						}
					}; break;
					case SEWERS: {
						if (jsonValue.get("hasJob").getBoolean(8)){
							calculateJC();
						}
					}; break;
					default: break;
					}
				}
				else {
					if (jsonValue.getString("currentJob").toString().equals("kitchen") && currentSelectedArea == CurrentSelectedArea.KITCHEN){
						notify = true;
						currentStageState = 4;
					}
					else if (jsonValue.getString("currentJob").toString().equals("basement") && currentSelectedArea == CurrentSelectedArea.BASEMENT){
						notify = true;
						currentStageState = 4;
					}
					else if (jsonValue.getString("currentJob").toString().equals("garage") && currentSelectedArea == CurrentSelectedArea.GARAGE){
						notify = true;
						currentStageState = 4;
					}
					else if (jsonValue.getString("currentJob").toString().equals("backyard") && currentSelectedArea == CurrentSelectedArea.BACKYARD){
						notify = true;
						currentStageState = 4;
					}
					else if (jsonValue.getString("currentJob").toString().equals("sidewalk") && currentSelectedArea == CurrentSelectedArea.SIDEWALK){
						notify = true;
						currentStageState = 4;
					}
					else if (jsonValue.getString("currentJob").toString().equals("factory") && currentSelectedArea == CurrentSelectedArea.FACTORY){
						notify = true;
						currentStageState = 4;
					}
					else if (jsonValue.getString("currentJob").toString().equals("highway") && currentSelectedArea == CurrentSelectedArea.HIGHWAY){
						notify = true;
						currentStageState = 4;
					}
					else if (jsonValue.getString("currentJob").toString().equals("park") && currentSelectedArea == CurrentSelectedArea.PARK){
						notify = true;
						currentStageState = 4;
					}
					else if (jsonValue.getString("currentJob").toString().equals("sewers") && currentSelectedArea == CurrentSelectedArea.SEWERS){
						notify = true;
						currentStageState = 4;
					}
					else {
						notify = false;
						currentStageState = 0;
					}
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

//		game.adManager.showBannerAds(true);
		loadMusic();
	}

	private void calculateJC(){
		if (jsonValue.getInt("junkCoins") - fee >= 0){
			notify = true;
			currentStageState = 1;
		} else {
			currentStageState = 3;
		}
	}

	private void setGameEngines() {

		yes.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				switch (currentStageState){
				case 1: {
					switch (currentSelectedArea){
					case KITCHEN: {
						jsonValue.get("currentJob").set("kitchen");
						junkWorldEngines.setCurrentJob("kitchen");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") - fee, null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						jsonValue.get("triesLeft").set(3, null);
						jsonValue.get("currentRound").set(1, null);
						setGameEngines(0);
					}; break;
					case BASEMENT: {
						jsonValue.get("currentJob").set("basement");
						junkWorldEngines.setCurrentJob("basement");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") - fee, null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						jsonValue.get("triesLeft").set(3, null);
						jsonValue.get("currentRound").set(1, null);
						setGameEngines(1);
					}; break;
					case GARAGE: {
						jsonValue.get("currentJob").set("garage");
						junkWorldEngines.setCurrentJob("garage");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") - fee, null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						jsonValue.get("triesLeft").set(3, null);
						jsonValue.get("currentRound").set(1, null);
						setGameEngines(2);
					}; break;
					case BACKYARD: {
						jsonValue.get("currentJob").set("backyard");
						junkWorldEngines.setCurrentJob("backyard");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") - fee, null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						jsonValue.get("triesLeft").set(3, null);
						jsonValue.get("currentRound").set(1, null);
						setGameEngines(3);
					}; break;
					case SIDEWALK: {
						jsonValue.get("currentJob").set("sidewalk");
						junkWorldEngines.setCurrentJob("sidewalk");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") - fee, null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						jsonValue.get("triesLeft").set(3, null);
						jsonValue.get("currentRound").set(1, null);
						setGameEngines(4);
					}; break;
					case FACTORY: {
						jsonValue.get("currentJob").set("factory");
						junkWorldEngines.setCurrentJob("factory");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") - fee, null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						jsonValue.get("triesLeft").set(3, null);
						jsonValue.get("currentRound").set(1, null);
						setGameEngines(5);
					}; break;
					case HIGHWAY: {
						jsonValue.get("currentJob").set("highway");
						junkWorldEngines.setCurrentJob("highway");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") - fee, null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						jsonValue.get("triesLeft").set(3, null);
						jsonValue.get("currentRound").set(1, null);
						setGameEngines(6);
					}; break;
					case PARK: {
						jsonValue.get("currentJob").set("park");
						junkWorldEngines.setCurrentJob("park");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") - fee, null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						jsonValue.get("triesLeft").set(3, null);
						jsonValue.get("currentRound").set(1, null);
						setGameEngines(7);
					}; break;
					case SEWERS: {
						jsonValue.get("currentJob").set("sewers");
						junkWorldEngines.setCurrentJob("sewers");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") - fee, null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						jsonValue.get("triesLeft").set(3, null);
						jsonValue.get("currentRound").set(1, null);
						setGameEngines(8);
					}; break;
					default: break;
					}
					jsonValue.get("fee").set(fee, null);
					profileFile.writeString(jsonValue.toString(), false);
				}; break;
				case 2: {
					if (!jsonValue.getString("currentJob").toString().equals("none")){
						jsonValue.get("currentJob").set("none");
						junkWorldEngines.setCurrentJob("none");
						jsonValue.get("junkCoins").set(jsonValue.getInt("junkCoins") + (jsonValue.getInt("fee") / 2), null);
						junkWorldEngines.setJunkCoins(jsonValue.getInt("junkCoins"));
						profileFile.writeString(jsonValue.toString(), false);
						notify = false;
						currentStageState = 0;
					}
				}; break;
				case 3: {

				}; break;
				case 4: {
					switch (currentSelectedArea){
					case KITCHEN: {
						if (jsonValue.getString("currentJob").toString().equals("kitchen")){
							setGameEngines(0);
						}
					}; break;
					case BASEMENT: {
						if (jsonValue.getString("currentJob").toString().equals("basement")){
							setGameEngines(1);
						}
					}; break;
					case GARAGE: {
						if (jsonValue.getString("currentJob").toString().equals("garage")){
							setGameEngines(2);
						}
					}; break;
					case BACKYARD: {
						if (jsonValue.getString("currentJob").toString().equals("backyard")){
							setGameEngines(3);
						}
					}; break;
					case SIDEWALK: {
						if (jsonValue.getString("currentJob").toString().equals("sidewalk")){
							setGameEngines(4);
						}
					}; break;
					case FACTORY: {
						if (jsonValue.getString("currentJob").toString().equals("factory")){
							setGameEngines(5);
						}
					}; break;
					case HIGHWAY: {
						if (jsonValue.getString("currentJob").toString().equals("highway")){
							setGameEngines(6);
						}
					}; break;
					case PARK: {
						if (jsonValue.getString("currentJob").toString().equals("park")){
							setGameEngines(7);
						}
					}; break;
					case SEWERS: {
						if (jsonValue.getString("currentJob").toString().equals("sewers")){
							setGameEngines(8);
						}
					}; break;
					default: {
						notify = false;
						currentStageState = 0;
					}; break;
					}
				}; break;
				default: break;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	protected void setGameEngines(int area) {
		//		TODO
		junkWorldEngines.setSpawnTime(spawnTimeValue.get(area));
		junkWorldEngines.setTrashAccerleration(garbageSpeed);
		junkWorldEngines.setGameTimer(jsonValue.get("timer").getInt(area));
		junkWorldEngines.setMaxTrashNotDumped(garbageLimit);
		junkWorldEngines.setGameSelection(2);
		junkWorldEngines.setGameMode(jsonValue.get("gameMode").getInt(area));
		junkWorldEngines.setGarbageGoal(jsonValue.get("garbageGoal").getInt(area));
		junkWorldEngines.setTheme(area + 1);
		junkWorldEngines.setCurrentDifficulty((jsonValue.getInt("level") + 9)/ 10);
		junkWorldEngines.setFee(jsonValue.getInt("fee"));
		junkWorldEngines.setDumpsterCapacity((((jsonValue.getInt("level") - 1) / 5) * 1) + 10);
		isLoading = false;
		unloadManager();
		disposeAssets();
		game.setScreen(new TrashCanSelectionScreen(game, manager, junkWorldEngines));
	}

	private void setRegions() {
		houseAreasRegion.add(new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Areas/House/Kitchen.png", Texture.class)));
		houseAreasRegion.add(new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Areas/House/Basement.png", Texture.class)));
		houseAreasRegion.add(new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Areas/House/Garage.png", Texture.class)));
		houseAreasRegion.add(new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Areas/House/Backyard.png", Texture.class)));
		houseAreasRegion.add(new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Areas/House/Sidewalk.png", Texture.class)));

		cityAreasRegion.add(new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Areas/City/Factory.png", Texture.class)));
		cityAreasRegion.add(new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Areas/City/Highway.png", Texture.class)));
		cityAreasRegion.add(new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Areas/City/Park.png", Texture.class)));
		cityAreasRegion.add(new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Areas/City/Sewer.png", Texture.class)));
	}

	@Override
	public void render(float delta) {
		if (!isLoading && manager.update()){
			playMusic();
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			if (Gdx.input.justTouched()){
				delayAds = TimeUtils.millis();
			}
			if (TimeUtils.millis() - delayAds >= 5000){
				delayAds = TimeUtils.millis();
//				myRequestHandler.showAds2(true);
			}

			batch.begin();
			batch.draw(manager.get("backgrounds/firstBackground.png", Texture.class), 0, 0);
			batch.draw(manager.get("backgrounds/greenBackground.png", Texture.class), 0, 0, 512, 800);
			batch.end();
			setDifficulty();

			switch (currentSinglePlayerState){
			case COMMUNITY_SELECTION: {
				setActorBounds();
				draw();

				stage.getViewport().setCamera(camera);
				stage.draw();
				stage.act();
				Gdx.input.setInputProcessor(stage);
			}; break;
			case AREA_SELECTION: {
				setActorBounds2();
				draw2();

				stage2.getViewport().setCamera(camera);
				stage2.draw();
				stage2.act();
				Gdx.input.setInputProcessor(stage2);
			}; break;
			case STAGE_SELECTION: {
				setStageMechanics();

				if (!notify){
					stage3.getViewport().setCamera(camera);
					stage3.draw();
					stage3.act();
					Gdx.input.setInputProcessor(stage3);
				} else {
					stage4.getViewport().setCamera(camera);
					stage4.draw();
					stage4.act();
					Gdx.input.setInputProcessor(stage4);
				}
			}; break;
			default: break;
			}
		}
	}

	private void setDifficulty() {
		switch (currentSelectedArea){
		case KITCHEN: {
			currentDifficulty = (((jsonValue.get("garbageAmount").get(0).asFloat() - 2) / 16) * .45f) + ((garbageSpeed / 2) * 0.5f) 
					+ (((getArrayAverage(ave1) - (getArrayAverage(spawnTimeValue.get(0)) - (getArrayAverage(ave1)))) / (getArrayAverage(ave1))) * .05f);
			if (jsonValue.get("gameMode").getInt(0) == 1){
				fee = (int) (((jsonValue.get("timer").getInt(0) / getArrayAverage(spawnTimeValue.get(0))) * jsonValue.get("rounds").getInt(0))) * perfectJC;
			} 
			else {
				fee = (int) ((jsonValue.get("garbageGoal").getInt(0) * jsonValue.get("rounds").getInt(0))) * perfectJC;
			}
		}; break;
		case BASEMENT: {
			currentDifficulty = (((jsonValue.get("garbageAmount").get(1).asFloat() - 2) / 16) * .45f) + ((garbageSpeed / 2) * 0.5f) 
					+ (((getArrayAverage(ave1) - (getArrayAverage(spawnTimeValue.get(1)) - (getArrayAverage(ave1)))) / (getArrayAverage(ave1))) * .05f);
			if (jsonValue.get("gameMode").getInt(1) == 1){
				fee = (int) (((jsonValue.get("timer").getInt(1) / getArrayAverage(spawnTimeValue.get(1))) * jsonValue.get("rounds").getInt(1))) * perfectJC;
			} 
			else {
				fee = (int) ((jsonValue.get("garbageGoal").getInt(1) * jsonValue.get("rounds").getInt(1))) * perfectJC;
			}
		}; break;
		case GARAGE: {
			currentDifficulty = (((jsonValue.get("garbageAmount").get(2).asFloat() - 2) / 16) * .45f) + ((garbageSpeed / 2) * 0.5f) 
					+ (((getArrayAverage(ave1) - (getArrayAverage(spawnTimeValue.get(2)) - (getArrayAverage(ave1)))) / (getArrayAverage(ave1))) * .05f);
			if (jsonValue.get("gameMode").getInt(2) == 1){
				fee = (int) (((jsonValue.get("timer").getInt(2) / getArrayAverage(spawnTimeValue.get(2))) * jsonValue.get("rounds").getInt(2))) * perfectJC;
			} 
			else {
				fee = (int) ((jsonValue.get("garbageGoal").getInt(2) * jsonValue.get("rounds").getInt(2))) * perfectJC;
			}
		}; break;
		case BACKYARD: {
			currentDifficulty = (((jsonValue.get("garbageAmount").get(3).asFloat() - 2) / 16) * .45f) + ((garbageSpeed / 2) * 0.5f) 
					+ (((getArrayAverage(ave1) - (getArrayAverage(spawnTimeValue.get(3)) - (getArrayAverage(ave1)))) / (getArrayAverage(ave1))) * .05f);
			if (jsonValue.get("gameMode").getInt(3) == 1){
				fee = (int) (((jsonValue.get("timer").getInt(3) / getArrayAverage(spawnTimeValue.get(3))) * jsonValue.get("rounds").getInt(3))) * perfectJC;
			} 
			else {
				fee = (int) ((jsonValue.get("garbageGoal").getInt(3) * jsonValue.get("rounds").getInt(3))) * perfectJC;
			}
		}; break;
		case SIDEWALK: {
			currentDifficulty = (((jsonValue.get("garbageAmount").get(4).asFloat() - 2) / 16) * .45f) + ((garbageSpeed / 2) * 0.5f) 
					+ (((getArrayAverage(ave1) - (getArrayAverage(spawnTimeValue.get(4)) - (getArrayAverage(ave1)))) / (getArrayAverage(ave1))) * .05f);
			if (jsonValue.get("gameMode").getInt(4) == 1){
				fee = (int) (((jsonValue.get("timer").getInt(4) / getArrayAverage(spawnTimeValue.get(4))) * jsonValue.get("rounds").getInt(4))) * perfectJC;
			} 
			else {
				fee = (int) ((jsonValue.get("garbageGoal").getInt(4) * jsonValue.get("rounds").getInt(4))) * perfectJC;
			}
		}; break;
		case FACTORY: {
			currentDifficulty = (((jsonValue.get("garbageAmount").get(5).asFloat() - 2) / 16) * .45f) + ((garbageSpeed / 2) * 0.5f) 
					+ (((getArrayAverage(ave1) - (getArrayAverage(spawnTimeValue.get(5)) - (getArrayAverage(ave1)))) / (getArrayAverage(ave1))) * .05f);
			if (jsonValue.get("gameMode").getInt(5) == 1){
				fee = (int) (((jsonValue.get("timer").getInt(5) / getArrayAverage(spawnTimeValue.get(5))) * jsonValue.get("rounds").getInt(5))) * perfectJC;
			} 
			else {
				fee = (int) ((jsonValue.get("garbageGoal").getInt(5) * jsonValue.get("rounds").getInt(5))) * perfectJC;
			}
		}; break;
		case HIGHWAY: {
			currentDifficulty = (((jsonValue.get("garbageAmount").get(6).asFloat() - 2) / 16) * .45f) + ((garbageSpeed / 2) * 0.5f) 
					+ (((getArrayAverage(ave1) - (getArrayAverage(spawnTimeValue.get(6)) - (getArrayAverage(ave1)))) / (getArrayAverage(ave1))) * .05f);
			if (jsonValue.get("gameMode").getInt(6) == 1){
				fee = (int) (((jsonValue.get("timer").getInt(6) / getArrayAverage(spawnTimeValue.get(6))) * jsonValue.get("rounds").getInt(6))) * perfectJC;
			} 
			else {
				fee = (int) ((jsonValue.get("garbageGoal").getInt(6) * jsonValue.get("rounds").getInt(6))) * perfectJC;
			}
		}; break;
		case PARK: {
			currentDifficulty = (((jsonValue.get("garbageAmount").get(7).asFloat() - 2) / 16) * .45f) + ((garbageSpeed / 2) * 0.5f) 
					+ (((getArrayAverage(ave1) - (getArrayAverage(spawnTimeValue.get(7)) - (getArrayAverage(ave1)))) / (getArrayAverage(ave1))) * .05f);
			if (jsonValue.get("gameMode").getInt(7) == 1){
				fee = (int) (((jsonValue.get("timer").getInt(7) / getArrayAverage(spawnTimeValue.get(7))) * jsonValue.get("rounds").getInt(7))) * perfectJC;
			} 
			else {
				fee = (int) ((jsonValue.get("garbageGoal").getInt(7) * jsonValue.get("rounds").getInt(7))) * perfectJC;
			}
		}; break;
		case SEWERS: {
			currentDifficulty = (((jsonValue.get("garbageAmount").get(8).asFloat() - 2) / 16) * .45f) + ((garbageSpeed / 2) * 0.5f) 
					+ (((getArrayAverage(ave1) - (getArrayAverage(spawnTimeValue.get(8)) - (getArrayAverage(ave1)))) / (getArrayAverage(ave1))) * .05f);
			if (jsonValue.get("gameMode").getInt(8) == 1){
				fee = (int) (((jsonValue.get("timer").getInt(8) / getArrayAverage(spawnTimeValue.get(8))) * jsonValue.get("rounds").getInt(8))) * perfectJC;
			} 
			else {
				fee = (int) ((jsonValue.get("garbageGoal").getInt(8) * jsonValue.get("rounds").getInt(8))) * perfectJC;
			}
		}; break;
		default: break;
		}
	}

	private void setStageMechanics() {

		//		TODO
		back3.setBounds(256 - 128 - 32 - 30, 150, 128, 64);
		accept.setBounds(256 + 32, 150, 150, 64);
		cancel.setBounds(cancelLayer.x, cancelLayer.y, cancelLayer.width, cancelLayer.height);
		yes.setBounds(256 + 50, 80, 60, 50);
		no.setBounds(256 - 110, 80, 60, 50);

		batch.begin();
		switch (currentSelectedCommunity){
		case HOUSE: batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/house.png", Texture.class), 100, 680, 117, 50); break;
		case CITY: batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/city.png", Texture.class), 100, 680, 117, 50); break;
		case FOREST: batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/forest.png", Texture.class), 100, 680, 117, 50); break;
		default: break;
		}

		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/hyphen.png", Texture.class), 235, 676, 32, 64);
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/level.png", Texture.class), 55, 624, 120, 50);
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jc.png", Texture.class), 220, 620, 100, 50);
		profileData.draw(batch, String.valueOf(junkWorldEngines.getLevel()), 173, 655);
		profileData.draw(batch, String.valueOf(junkWorldEngines.getJunkCoins()), 320, 655);

		batch.draw(manager.get("backgrounds/mainJunkworldFrame.png", Texture.class), 0, 0);
		batch.draw(manager.get("profileAssets/back.png", Texture.class), 256 - 128 - 32 - 30, 150);
		batch.draw(manager.get("backgrounds/stageDesc.png", Texture.class), 56, 220);

		switch (currentSelectedArea){
		case KITCHEN: {
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/kitchen.png", Texture.class), 250, 680, 200, 50);
			if (!jsonValue.get("hasJob").getBoolean(0)){
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class), 256 - 150, 380, 300, 70);
			}
			else {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					if (jsonValue.get("gameMode").getInt(0) == 1){
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
						font2.draw(batch, "Survive until the timer", 150, 480);
						font2.draw(batch, "runs out!", 150, 450);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
						font2.draw(batch, "Dump all trash!", 150, 480);
					}
					stageDesc.draw(batch, "Goal: ", 80, 480);
					stageDesc.draw(batch, "Rounds: ", 80, 410);
					font2.draw(batch, String.valueOf(jsonValue.get("rounds").getInt(0)), 180, 410);
					stageDesc.draw(batch, "Difficulty: ", 80, 370);
					drawBar();
					stageDesc.draw(batch, "Fee: ", 212, 280);
					if (jsonValue.getInt("junkCoins") - fee >= 0){
						font1.draw(batch, String.valueOf(fee), 262, 280);
					}
					else font3.draw(batch, String.valueOf(fee), 262, 280);
					batch.draw(manager.get("profileAssets/accept.png", Texture.class), 256 + 32, 150, 150, 64);
				}
				else if (jsonValue.getString("currentJob").toString().equals("kitchen")){
					if (jsonValue.get("gameMode").getInt(0) == 1){
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					if (TimeUtils.millis() - delayNotifier < 1000){
						batch.draw(notifier0, 256 - 150, 400, 300, 90);
					}
					else if (TimeUtils.millis() - delayNotifier >= 1000 && TimeUtils.millis() - delayNotifier < 2000){
						batch.draw(notifier1, 256 - 150, 400, 300, 90);
					}
					else {
						delayNotifier = TimeUtils.millis();
					}

					drawTries();
				}
				else {
					if (jsonValue.get("gameMode").getInt(0) == 1){
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class), 256 - 150, 400, 300, 90);
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class), 256 - 150, 330, 300, 64);
					drawCurrentJob();
				}
			}
		}; break;
		case BASEMENT: {
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/basement.png", Texture.class), 250, 680, 200, 50); // Here
			if (!jsonValue.get("hasJob").getBoolean(1)){// Here
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class), 256 - 150, 380, 300, 70);
			}
			else {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					if (jsonValue.get("gameMode").getInt(1) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
						font2.draw(batch, "Survive until the timer", 150, 480);
						font2.draw(batch, "runs out!", 150, 450);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
						font2.draw(batch, "Dump all trash!", 150, 480);
					}
					stageDesc.draw(batch, "Goal: ", 80, 480);
					stageDesc.draw(batch, "Rounds: ", 80, 410);
					font2.draw(batch, String.valueOf(jsonValue.get("rounds").getInt(1)), 180, 410);// Here
					stageDesc.draw(batch, "Difficulty: ", 80, 370);
					drawBar();
					stageDesc.draw(batch, "Fee: ", 212, 280);
					if (jsonValue.getInt("junkCoins") - fee >= 0){
						font1.draw(batch, String.valueOf(fee), 262, 280);
					}
					else font3.draw(batch, String.valueOf(fee), 262, 280);
					batch.draw(manager.get("profileAssets/accept.png", Texture.class), 256 + 32, 150, 150, 64);
				}
				else if (jsonValue.getString("currentJob").toString().equals("basement")){// Here
					if (jsonValue.get("gameMode").getInt(1) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					if (TimeUtils.millis() - delayNotifier < 1000){
						batch.draw(notifier0, 256 - 150, 400, 300, 90);
					}
					else if (TimeUtils.millis() - delayNotifier >= 1000 && TimeUtils.millis() - delayNotifier < 2000){
						batch.draw(notifier1, 256 - 150, 400, 300, 90);
					}
					else {
						delayNotifier = TimeUtils.millis();
					}

					drawTries();
				}
				else {
					if (jsonValue.get("gameMode").getInt(1) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class), 256 - 150, 400, 300, 90);
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class), 256 - 150, 330, 300, 64);
					drawCurrentJob();
				}
			}
		}; break;
		case GARAGE: {
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/garage.png", Texture.class), 250, 680, 200, 50); // Here
			if (!jsonValue.get("hasJob").getBoolean(2)){// Here
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class), 256 - 150, 380, 300, 70);
			}
			else {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					if (jsonValue.get("gameMode").getInt(2) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
						font2.draw(batch, "Survive until the timer", 150, 480);
						font2.draw(batch, "runs out!", 150, 450);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
						font2.draw(batch, "Dump all trash!", 150, 480);
					}
					stageDesc.draw(batch, "Goal: ", 80, 480);
					stageDesc.draw(batch, "Rounds: ", 80, 410);
					font2.draw(batch, String.valueOf(jsonValue.get("rounds").getInt(2)), 180, 410);// Here
					stageDesc.draw(batch, "Difficulty: ", 80, 370);
					drawBar();
					stageDesc.draw(batch, "Fee: ", 212, 280);
					if (jsonValue.getInt("junkCoins") - fee >= 0){
						font1.draw(batch, String.valueOf(fee), 262, 280);
					}
					else font3.draw(batch, String.valueOf(fee), 262, 280);
					batch.draw(manager.get("profileAssets/accept.png", Texture.class), 256 + 32, 150, 150, 64);
				}
				else if (jsonValue.getString("currentJob").toString().equals("garage")){// Here
					if (jsonValue.get("gameMode").getInt(2) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					if (TimeUtils.millis() - delayNotifier < 1000){
						batch.draw(notifier0, 256 - 150, 400, 300, 90);
					}
					else if (TimeUtils.millis() - delayNotifier >= 1000 && TimeUtils.millis() - delayNotifier < 2000){
						batch.draw(notifier1, 256 - 150, 400, 300, 90);
					}
					else {
						delayNotifier = TimeUtils.millis();
					}

					drawTries();
				}
				else {
					if (jsonValue.get("gameMode").getInt(2) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class), 256 - 150, 400, 300, 90);
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class), 256 - 150, 330, 300, 64);
					drawCurrentJob();
				}
			}
		}; break;
		case BACKYARD: {
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/backyard.png", Texture.class), 250, 680, 200, 50); // Here
			if (!jsonValue.get("hasJob").getBoolean(3)){// Here
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class), 256 - 150, 380, 300, 70);
			}
			else {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					if (jsonValue.get("gameMode").getInt(3) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
						font2.draw(batch, "Survive until the timer", 150, 480);
						font2.draw(batch, "runs out!", 150, 450);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
						font2.draw(batch, "Dump all trash!", 150, 480);
					}
					stageDesc.draw(batch, "Goal: ", 80, 480);
					stageDesc.draw(batch, "Rounds: ", 80, 410);
					font2.draw(batch, String.valueOf(jsonValue.get("rounds").getInt(3)), 180, 410);// Here
					stageDesc.draw(batch, "Difficulty: ", 80, 370);
					drawBar();
					stageDesc.draw(batch, "Fee: ", 212, 280);
					if (jsonValue.getInt("junkCoins") - fee >= 0){
						font1.draw(batch, String.valueOf(fee), 262, 280);
					}
					else font3.draw(batch, String.valueOf(fee), 262, 280);
					batch.draw(manager.get("profileAssets/accept.png", Texture.class), 256 + 32, 150, 150, 64);
				}
				else if (jsonValue.getString("currentJob").toString().equals("backyard")){// Here
					if (jsonValue.get("gameMode").getInt(3) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					if (TimeUtils.millis() - delayNotifier < 1000){
						batch.draw(notifier0, 256 - 150, 400, 300, 90);
					}
					else if (TimeUtils.millis() - delayNotifier >= 1000 && TimeUtils.millis() - delayNotifier < 2000){
						batch.draw(notifier1, 256 - 150, 400, 300, 90);
					}
					else {
						delayNotifier = TimeUtils.millis();
					}

					drawTries();
				}
				else {
					if (jsonValue.get("gameMode").getInt(3) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class), 256 - 150, 400, 300, 90);
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class), 256 - 150, 330, 300, 64);
					drawCurrentJob();
				}
			}
		}; break;
		case SIDEWALK: {
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/sidewalk.png", Texture.class), 250, 680, 200, 50); // Here
			if (!jsonValue.get("hasJob").getBoolean(4)){// Here
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class), 256 - 150, 380, 300, 70);
			}
			else {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					if (jsonValue.get("gameMode").getInt(4) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
						font2.draw(batch, "Survive until the timer", 150, 480);
						font2.draw(batch, "runs out!", 150, 450);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
						font2.draw(batch, "Dump all trash!", 150, 480);
					}
					stageDesc.draw(batch, "Goal: ", 80, 480);
					stageDesc.draw(batch, "Rounds: ", 80, 410);
					font2.draw(batch, String.valueOf(jsonValue.get("rounds").getInt(4)), 180, 410);// Here
					stageDesc.draw(batch, "Difficulty: ", 80, 370);
					drawBar();
					stageDesc.draw(batch, "Fee: ", 212, 280);
					if (jsonValue.getInt("junkCoins") - fee >= 0){
						font1.draw(batch, String.valueOf(fee), 262, 280);
					}
					else font3.draw(batch, String.valueOf(fee), 262, 280);
					batch.draw(manager.get("profileAssets/accept.png", Texture.class), 256 + 32, 150, 150, 64);
				}
				else if (jsonValue.getString("currentJob").toString().equals("sidewalk")){// Here
					if (jsonValue.get("gameMode").getInt(4) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					if (TimeUtils.millis() - delayNotifier < 1000){
						batch.draw(notifier0, 256 - 150, 400, 300, 90);
					}
					else if (TimeUtils.millis() - delayNotifier >= 1000 && TimeUtils.millis() - delayNotifier < 2000){
						batch.draw(notifier1, 256 - 150, 400, 300, 90);
					}
					else {
						delayNotifier = TimeUtils.millis();
					}

					drawTries();
				}
				else {
					if (jsonValue.get("gameMode").getInt(4) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class), 256 - 150, 400, 300, 90);
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class), 256 - 150, 330, 300, 64);
					drawCurrentJob();
				}
			}
		}; break;
		case FACTORY: {
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/factory.png", Texture.class), 250, 680, 200, 50); // Here
			if (!jsonValue.get("hasJob").getBoolean(5)){// Here
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class), 256 - 150, 380, 300, 70);
			}
			else {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					if (jsonValue.get("gameMode").getInt(5) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
						font2.draw(batch, "Survive until the timer", 150, 480);
						font2.draw(batch, "runs out!", 150, 450);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
						font2.draw(batch, "Dump all trash!", 150, 480);
					}
					stageDesc.draw(batch, "Goal: ", 80, 480);
					stageDesc.draw(batch, "Rounds: ", 80, 410);
					font2.draw(batch, String.valueOf(jsonValue.get("rounds").getInt(5)), 180, 410);// Here
					stageDesc.draw(batch, "Difficulty: ", 80, 370);
					drawBar();
					stageDesc.draw(batch, "Fee: ", 212, 280);
					if (jsonValue.getInt("junkCoins") - fee >= 0){
						font1.draw(batch, String.valueOf(fee), 262, 280);
					}
					else font3.draw(batch, String.valueOf(fee), 262, 280);
					batch.draw(manager.get("profileAssets/accept.png", Texture.class), 256 + 32, 150, 150, 64);
				}
				else if (jsonValue.getString("currentJob").toString().equals("factory")){// Here
					if (jsonValue.get("gameMode").getInt(5) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					if (TimeUtils.millis() - delayNotifier < 1000){
						batch.draw(notifier0, 256 - 150, 400, 300, 90);
					}
					else if (TimeUtils.millis() - delayNotifier >= 1000 && TimeUtils.millis() - delayNotifier < 2000){
						batch.draw(notifier1, 256 - 150, 400, 300, 90);
					}
					else {
						delayNotifier = TimeUtils.millis();
					}

					drawTries();
				}
				else {
					if (jsonValue.get("gameMode").getInt(5) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class), 256 - 150, 400, 300, 90);
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class), 256 - 150, 330, 300, 64);
					drawCurrentJob();
				}
			}
		}; break;
		case HIGHWAY: {
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/highway.png", Texture.class), 250, 680, 200, 50); // Here
			if (!jsonValue.get("hasJob").getBoolean(6)){// Here
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class), 256 - 150, 380, 300, 70);
			}
			else {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					if (jsonValue.get("gameMode").getInt(6) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
						font2.draw(batch, "Survive until the timer", 150, 480);
						font2.draw(batch, "runs out!", 150, 450);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
						font2.draw(batch, "Dump all trash!", 150, 480);
					}
					stageDesc.draw(batch, "Goal: ", 80, 480);
					stageDesc.draw(batch, "Rounds: ", 80, 410);
					font2.draw(batch, String.valueOf(jsonValue.get("rounds").getInt(6)), 180, 410);// Here
					stageDesc.draw(batch, "Difficulty: ", 80, 370);
					drawBar();
					stageDesc.draw(batch, "Fee: ", 212, 280);
					if (jsonValue.getInt("junkCoins") - fee >= 0){
						font1.draw(batch, String.valueOf(fee), 262, 280);
					}
					else font3.draw(batch, String.valueOf(fee), 262, 280);
					batch.draw(manager.get("profileAssets/accept.png", Texture.class), 256 + 32, 150, 150, 64);
				}
				else if (jsonValue.getString("currentJob").toString().equals("highway")){// Here
					if (jsonValue.get("gameMode").getInt(6) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					if (TimeUtils.millis() - delayNotifier < 1000){
						batch.draw(notifier0, 256 - 150, 400, 300, 90);
					}
					else if (TimeUtils.millis() - delayNotifier >= 1000 && TimeUtils.millis() - delayNotifier < 2000){
						batch.draw(notifier1, 256 - 150, 400, 300, 90);
					}
					else {
						delayNotifier = TimeUtils.millis();
					}

					drawTries();
				}
				else {
					if (jsonValue.get("gameMode").getInt(6) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class), 256 - 150, 400, 300, 90);
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class), 256 - 150, 330, 300, 64);
					drawCurrentJob();
				}
			}
		}; break;
		case PARK: {
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/park.png", Texture.class), 250, 680, 200, 50); // Here
			if (!jsonValue.get("hasJob").getBoolean(7)){// Here
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class), 256 - 150, 380, 300, 70);
			}
			else {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					if (jsonValue.get("gameMode").getInt(7) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
						font2.draw(batch, "Survive until the timer", 150, 480);
						font2.draw(batch, "runs out!", 150, 450);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
						font2.draw(batch, "Dump all trash!", 150, 480);
					}
					stageDesc.draw(batch, "Goal: ", 80, 480);
					stageDesc.draw(batch, "Rounds: ", 80, 410);
					font2.draw(batch, String.valueOf(jsonValue.get("rounds").getInt(7)), 180, 410);// Here
					stageDesc.draw(batch, "Difficulty: ", 80, 370);
					drawBar();
					stageDesc.draw(batch, "Fee: ", 212, 280);
					if (jsonValue.getInt("junkCoins") - fee >= 0){
						font1.draw(batch, String.valueOf(fee), 262, 280);
					}
					else font3.draw(batch, String.valueOf(fee), 262, 280);
					batch.draw(manager.get("profileAssets/accept.png", Texture.class), 256 + 32, 150, 150, 64);
				}
				else if (jsonValue.getString("currentJob").toString().equals("park")){// Here
					if (jsonValue.get("gameMode").getInt(7) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					if (TimeUtils.millis() - delayNotifier < 1000){
						batch.draw(notifier0, 256 - 150, 400, 300, 90);
					}
					else if (TimeUtils.millis() - delayNotifier >= 1000 && TimeUtils.millis() - delayNotifier < 2000){
						batch.draw(notifier1, 256 - 150, 400, 300, 90);
					}
					else {
						delayNotifier = TimeUtils.millis();
					}

					drawTries();
				}
				else {
					if (jsonValue.get("gameMode").getInt(7) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class), 256 - 150, 400, 300, 90);
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class), 256 - 150, 330, 300, 64);
					drawCurrentJob();
				}
			}
		}; break;
		case SEWERS: {
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/sewers.png", Texture.class), 250, 680, 200, 50); // Here
			if (!jsonValue.get("hasJob").getBoolean(8)){// Here
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noJobsNotifier.png", Texture.class), 256 - 150, 380, 300, 70);
			}
			else {
				if (jsonValue.getString("currentJob").toString().equals("none")){
					if (jsonValue.get("gameMode").getInt(8) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
						font2.draw(batch, "Survive until the timer", 150, 480);
						font2.draw(batch, "runs out!", 150, 450);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
						font2.draw(batch, "Dump all trash!", 150, 480);
					}
					stageDesc.draw(batch, "Goal: ", 80, 480);
					stageDesc.draw(batch, "Rounds: ", 80, 410);
					font2.draw(batch, String.valueOf(jsonValue.get("rounds").getInt(8)), 180, 410);// Here
					stageDesc.draw(batch, "Difficulty: ", 80, 370);
					drawBar();
					stageDesc.draw(batch, "Fee: ", 212, 280);
					if (jsonValue.getInt("junkCoins") - fee >= 0){
						font1.draw(batch, String.valueOf(fee), 262, 280);
					}
					else font3.draw(batch, String.valueOf(fee), 262, 280);
					batch.draw(manager.get("profileAssets/accept.png", Texture.class), 256 + 32, 150, 150, 64);
				}
				else if (jsonValue.getString("currentJob").toString().equals("sewers")){// Here
					if (jsonValue.get("gameMode").getInt(8) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					if (TimeUtils.millis() - delayNotifier < 1000){
						batch.draw(notifier0, 256 - 150, 400, 300, 90);
					}
					else if (TimeUtils.millis() - delayNotifier >= 1000 && TimeUtils.millis() - delayNotifier < 2000){
						batch.draw(notifier1, 256 - 150, 400, 300, 90);
					}
					else {
						delayNotifier = TimeUtils.millis();
					}

					drawTries();
				}
				else {
					if (jsonValue.get("gameMode").getInt(8) == 1){// Here
						gameModeTitle.draw(batch, "Time Attack", 110, 540);
					} 
					else {
						gameModeTitle.draw(batch, "Dumping Job", 110, 540);
					}
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onlyOneJob.png", Texture.class), 256 - 150, 400, 300, 90);
					batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/jobArea.png", Texture.class), 256 - 150, 330, 300, 64);
					drawCurrentJob();
				}
			}
		}; break;
		default: break;
		}

		switch (currentStageState){
		case 1: {// Accept
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noButton.png", Texture.class), 256 - 110, 80, 60, 50);
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/yesButton.png", Texture.class), 256 + 50, 80, 60, 50);
		}; break;
		case 2: {//cancel
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/onCancel.png", Texture.class), 106, 220 + (300 - 256));
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noButton.png", Texture.class), 256 - 110, 80, 60, 50);
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/yesButton.png", Texture.class), 256 + 50, 80, 60, 50);
		}; break;
		case 3: {//notifier
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noRequirements.png", Texture.class), 256 - 120, 60, 240, 60);
		}; break;
		case 4: {// Continue
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/noButton.png", Texture.class), 256 - 110, 80, 60, 50);
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/yesButton.png", Texture.class), 256 + 50, 80, 60, 50);
		}; break;
		default: break;
		}

		batch.end();
	}

	private void drawTries() {
		switch (jsonValue.get("triesLeft").asInt()){
		case 1: batch.draw(manager.get("profileAssets/triesLeft/2.png",  Texture.class), 156, 320, 200, 40); break;
		case 2: batch.draw(manager.get("profileAssets/triesLeft/1.png",  Texture.class), 156, 320, 200, 40); break;
		case 3: batch.draw(manager.get("profileAssets/triesLeft/0.png",  Texture.class), 156, 320, 200, 40); break;
		default: break;
		}
		batch.draw(manager.get("profileAssets/cancel.png", Texture.class), cancelLayer.x, cancelLayer.y, cancelLayer.width, cancelLayer.height);
		batch.draw(manager.get("profileAssets/continue.png", Texture.class), 256 + 32 - 50, 150, 200, 64);
	}

	private void drawCurrentJob() {
		if (jsonValue.getString("currentJob").toString().equals("kitchen")){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/kitchen.png", Texture.class), 128, 250, 256, 64);
		}
		else if (jsonValue.getString("currentJob").toString().equals("basement")){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/basement.png", Texture.class), 128, 250, 256, 64);
		}
		else if (jsonValue.getString("currentJob").toString().equals("garage")){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/garage.png", Texture.class), 128, 250, 256, 64);
		}
		else if (jsonValue.getString("currentJob").toString().equals("backyard")){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/backyard.png", Texture.class), 128, 250, 256, 64);
		}
		else if (jsonValue.getString("currentJob").toString().equals("sidewalk")){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/sidewalk.png", Texture.class), 128, 250, 256, 64);
		}
		else if (jsonValue.getString("currentJob").toString().equals("factory")){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/factory.png", Texture.class), 128, 250, 256, 64);
		}
		else if (jsonValue.getString("currentJob").toString().equals("highway")){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/highway.png", Texture.class), 128, 250, 256, 64);
		}
		else if (jsonValue.getString("currentJob").toString().equals("park")){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/park.png", Texture.class), 128, 250, 256, 64);
		}
		else if (jsonValue.getString("currentJob").toString().equals("sewers")){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/sewers.png", Texture.class), 128, 250, 256, 64);
		}
	}

	private void setActorBounds2() {
		left2.setBounds(leftArrowLayer.x, 200, leftArrowLayer.width,
				leftArrowLayer.height);
		right2.setBounds(rightArrowLayer.x, 200, rightArrowLayer.width,
				rightArrowLayer.height);
		ok2.setBounds(256 + 64, 134, 64, 64);
		back2.setBounds(256 - 128 - 30, 134, 128, 64);
	}

	private void draw2() {
		batch.begin();
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/areas.png", Texture.class), 256 - 75, 730, 150, 64);
		batch.draw(manager.get("backgrounds/grassTexture.png", Texture.class), 256 - 150, 300 - 20, 300, 440);
		batch.draw(manager.get("profileAssets/ok.png", Texture.class), 256 + 64, 134, 64, 64);
		batch.draw(manager.get("profileAssets/back.png", Texture.class), 256 - 128 - 30, 134);
		switch (currentSelectedCommunity){
		case HOUSE: {
			switch (currentAreaIndex){
			case 1: {
				batch.draw(houseAreasRegion.get(0), 128, 300, 256, 400);
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/kitchen.png", Texture.class), 128, 200, 256, 64);
			};; break;
			case 2: {
				batch.draw(houseAreasRegion.get(1), 128, 300, 256, 400);
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/basement.png", Texture.class), 128, 200, 256, 64);
			};; break;
			case 3: {
				batch.draw(houseAreasRegion.get(2), 128, 300, 256, 400);
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/garage.png", Texture.class), 128, 200, 256, 64);
			};; break;
			case 4: {
				batch.draw(houseAreasRegion.get(3), 128, 300, 256, 400);
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/backyard.png", Texture.class), 128, 200, 256, 64);
			};; break;
			case 5: {
				batch.draw(houseAreasRegion.get(4), 128, 300, 256, 400);
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/sidewalk.png", Texture.class), 128, 200, 256, 64);
			};; break;
			default: break;
			}
		}; break;
		case CITY: {
			switch (currentAreaIndex){
			case 1: {
				batch.draw(cityAreasRegion.get(0), 128, 300, 256, 400);
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/factory.png", Texture.class), 128, 200, 256, 64);
			};; break;
			case 2: {
				batch.draw(cityAreasRegion.get(1), 128, 300, 256, 400);
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/highway.png", Texture.class), 128, 200, 256, 64);
			};; break;
			case 3: {
				batch.draw(cityAreasRegion.get(2), 128, 300, 256, 400);
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/park.png", Texture.class), 128, 200, 256, 64);
			};; break;
			case 4: {
				batch.draw(cityAreasRegion.get(3), 128, 300, 256, 400);
				batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/sewers.png", Texture.class), 128, 200, 256, 64);
			};; break;
			default: break;
			}
		}; break;
		case FOREST: {

		}; break;
		default: break;
		}
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png", Texture.class), leftArrowLayer.x, 200, leftArrowLayer.width,
				leftArrowLayer.height);
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png", Texture.class), rightArrowLayer.x, 200, rightArrowLayer.width,
				rightArrowLayer.height);
		batch.end();
	}

	private void setActorBounds() {
		house.setBounds(75, 460, 220 - 75, 560 - 460);
		city.setBounds(180, 570, 350 - 180, 670 - 570);
		forest.setBounds(330, 470, 470 - 330, 560 - 470);
		ok.setBounds(256 + 64, 134, 64, 64);
		back.setBounds(256 - 128 - 30, 134, 128, 64);
		left.setBounds(leftArrowLayer.x, leftArrowLayer.y, leftArrowLayer.width,
				leftArrowLayer.height);
		right.setBounds(rightArrowLayer.x, rightArrowLayer.y, rightArrowLayer.width,
				rightArrowLayer.height);

		if (currentSelectedComm == 1){
			currentSelectedCommunity = CurrentSelectedCommunity.HOUSE;
		}
		else if (currentSelectedComm == 2){
			currentSelectedCommunity = CurrentSelectedCommunity.CITY;
		}
		else if (currentSelectedComm == 3){
			currentSelectedCommunity = CurrentSelectedCommunity.FOREST;
		}
	}

	private void draw() {
		batch.begin();
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/levelAssets/grassLand.png", Texture.class), 6, 434, 500, 360);
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/selectACommunity.png", Texture.class), 56, 334, 400, 64);
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png", Texture.class), leftArrowLayer.x, leftArrowLayer.y, leftArrowLayer.width,
				leftArrowLayer.height);
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png", Texture.class), rightArrowLayer.x, rightArrowLayer.y, rightArrowLayer.width,
				rightArrowLayer.height);
		batch.draw(manager.get("profileAssets/ok.png", Texture.class), 256 + 64, 134, 64, 64);
		batch.draw(manager.get("profileAssets/back.png", Texture.class), 256 - 128 - 30, 134);

		if (junkWorldEngines.isCityUnlocked()){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/communityAssets/city.png", Texture.class), 6, 434);
		}
		if (currentSelectedCommunity == CurrentSelectedCommunity.CITY){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/city.png", Texture.class), 256 - 75, 234, 150, 64);
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/communityAssets/cityClicked.png", Texture.class), 6, 434);
		}
		if (junkWorldEngines.isHouseUnlocked()){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/communityAssets/house.png", Texture.class), 6, 434);
		}
		if (currentSelectedCommunity == CurrentSelectedCommunity.HOUSE){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/house.png", Texture.class), 256 - 75, 234, 150, 64);
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/communityAssets/houseClicked.png", Texture.class), 6, 434);
		}
		if (junkWorldEngines.isForestUnlocked()){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/communityAssets/forest.png", Texture.class), 6, 434);
		}
		if (currentSelectedCommunity == CurrentSelectedCommunity.FOREST){
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/forest.png", Texture.class), 256 - 75, 234, 150, 64);
			batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/communityAssets/forestClicked.png", Texture.class), 6, 434);
		}
		batch.end();
	}

	private void checkJobs(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = Calendar.getInstance().getTime();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, 1);

		Date date2 = new Date();
		date2.setTime(cal.getTimeInMillis());

		profileFile = Gdx.files.local(junkWorldEngines.getProfileName() + ".json");
		jsonValue = new JsonReader().parse(profileFile);

		Array<String> jsonDates = new Array<String>();
		jsonDates.addAll(jsonValue.get("jobChangeTimer").asStringArray());

		for (String datesStores : jsonDates){
			try {
				if (date.compareTo(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(datesStores)) > 0){
					switch (jsonDates.indexOf(datesStores, true)){
					case 0: {
						if (!junkWorldEngines.getCurrentJob().equals("kitchen")){
							jsonValue.get("jobChangeTimer").get(0).set(df.format(date2));

							hasJobArray.add(true);
							hasJobArray.add(false);
							jsonValue.get("hasJob").get(0).set(hasJobArray.random());
							hasJobArray.clear();

							gameMode = generator.nextInt(3);
							while (gameMode == 0){
								gameMode = generator.nextInt(3);
							}
							jsonValue.get("gameMode").get(0).set(gameMode, null);

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 20){
								timerArray.add(60);
								timerArray.add(75);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 40){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 60){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 80){
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 100){
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							jsonValue.get("timer").get(0).set(timerArray.random(), null);
							timerArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								garbageAmountArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
								garbageAmountArray.add(3);
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
								garbageAmountArray.add(9);
							}
							else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
								garbageAmountArray.add(9);
								garbageAmountArray.add(10);
								garbageAmountArray.add(11);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
								garbageAmountArray.add(11);
								garbageAmountArray.add(12);
								garbageAmountArray.add(13);
							}
							else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
								garbageAmountArray.add(13);
								garbageAmountArray.add(14);
								garbageAmountArray.add(15);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
								garbageAmountArray.add(15);
								garbageAmountArray.add(16);
								garbageAmountArray.add(17);
							}
							else if (junkWorldEngines.getLevel() >= 91){
								garbageAmountArray.add(18);
							}
							jsonValue.get("garbageAmount").get(0).set(garbageAmountArray.random(), null);
							garbageAmountArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								roundsArray.add(1);
								roundsArray.add(2);
							}
							else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
								roundsArray.add(4);
							}
							else {
								for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
									roundsArray.add(i2);
								}
							}
							jsonValue.get("rounds").get(0).set(roundsArray.random(), null);
							roundsArray.clear();

							spawnTime.clear();
							while (spawnTime.size < 5){
								spawnTime.add(0f);
							}
							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 30){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 4) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 60){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 3) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 100){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 2) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							for (int i4 = 0; i4 < spawnTime.size; i4++){
								spawnTimeFloat[i4] = spawnTime.get(i4);
							}
							for (int i3 = 0; i3 < 5; i3++){
								jsonValue.get("spawnTime").get(0).get(i3).set(spawnTimeFloat[i3], null);
							}
							spawnTime.clear();

							for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
								garbageGoalArrayRandom.add(i2);
							}
							jsonValue.get("garbageGoal").get(0).set(garbageGoalArrayRandom.random(), null);
							garbageGoalArrayRandom.clear();
						}
					}; break;
					case 1: {
						if (!junkWorldEngines.getCurrentJob().equals("basement")){
							jsonValue.get("jobChangeTimer").get(1).set(df.format(date2));

							hasJobArray.add(true);
							hasJobArray.add(false);
							jsonValue.get("hasJob").get(1).set(hasJobArray.random());
							hasJobArray.clear();

							gameMode = generator.nextInt(3);
							while (gameMode == 0){
								gameMode = generator.nextInt(3);
							}
							jsonValue.get("gameMode").get(1).set(gameMode, null);

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 20){
								timerArray.add(60);
								timerArray.add(75);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 40){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 60){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 80){
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 100){
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							jsonValue.get("timer").get(1).set(timerArray.random(), null);
							timerArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								garbageAmountArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
								garbageAmountArray.add(3);
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
								garbageAmountArray.add(9);
							}
							else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
								garbageAmountArray.add(9);
								garbageAmountArray.add(10);
								garbageAmountArray.add(11);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
								garbageAmountArray.add(11);
								garbageAmountArray.add(12);
								garbageAmountArray.add(13);
							}
							else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
								garbageAmountArray.add(13);
								garbageAmountArray.add(14);
								garbageAmountArray.add(15);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
								garbageAmountArray.add(15);
								garbageAmountArray.add(16);
								garbageAmountArray.add(17);
							}
							else if (junkWorldEngines.getLevel() >= 91){
								garbageAmountArray.add(18);
							}
							jsonValue.get("garbageAmount").get(1).set(garbageAmountArray.random(), null);
							garbageAmountArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								roundsArray.add(1);
								roundsArray.add(2);
							}
							else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
								roundsArray.add(4);
							}
							else {
								for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
									roundsArray.add(i2);
								}
							}
							jsonValue.get("rounds").get(1).set(roundsArray.random(), null);
							roundsArray.clear();

							spawnTime.clear();
							while (spawnTime.size < 5){
								spawnTime.add(0f);
							}
							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 30){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 4) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 60){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 3) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 100){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 2) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							for (int i4 = 0; i4 < spawnTime.size; i4++){
								spawnTimeFloat[i4] = spawnTime.get(i4);
							}
							for (int i3 = 0; i3 < 5; i3++){
								jsonValue.get("spawnTime").get(1).get(i3).set(spawnTimeFloat[i3], null);
							}
							spawnTime.clear();

							for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
								garbageGoalArrayRandom.add(i2);
							}
							jsonValue.get("garbageGoal").get(1).set(garbageGoalArrayRandom.random(), null);
							garbageGoalArrayRandom.clear();
						}
					}; break;
					case 2: {
						if (!junkWorldEngines.getCurrentJob().equals("garage")){
							jsonValue.get("jobChangeTimer").get(2).set(df.format(date2));

							hasJobArray.add(true);
							hasJobArray.add(false);
							jsonValue.get("hasJob").get(2).set(hasJobArray.random());
							hasJobArray.clear();

							gameMode = generator.nextInt(3);
							while (gameMode == 0){
								gameMode = generator.nextInt(3);
							}
							jsonValue.get("gameMode").get(2).set(gameMode, null);

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 20){
								timerArray.add(60);
								timerArray.add(75);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 40){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 60){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 80){
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 100){
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							jsonValue.get("timer").get(2).set(timerArray.random(), null);
							timerArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								garbageAmountArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
								garbageAmountArray.add(3);
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
								garbageAmountArray.add(9);
							}
							else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
								garbageAmountArray.add(9);
								garbageAmountArray.add(10);
								garbageAmountArray.add(11);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
								garbageAmountArray.add(11);
								garbageAmountArray.add(12);
								garbageAmountArray.add(13);
							}
							else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
								garbageAmountArray.add(13);
								garbageAmountArray.add(14);
								garbageAmountArray.add(15);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
								garbageAmountArray.add(15);
								garbageAmountArray.add(16);
								garbageAmountArray.add(17);
							}
							else if (junkWorldEngines.getLevel() >= 91){
								garbageAmountArray.add(18);
							}
							jsonValue.get("garbageAmount").get(2).set(garbageAmountArray.random(), null);
							garbageAmountArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								roundsArray.add(1);
								roundsArray.add(2);
							}
							else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
								roundsArray.add(4);
							}
							else {
								for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
									roundsArray.add(i2);
								}
							}
							jsonValue.get("rounds").get(2).set(roundsArray.random(), null);
							roundsArray.clear();

							spawnTime.clear();
							while (spawnTime.size < 5){
								spawnTime.add(0f);
							}
							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 30){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 4) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 60){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 3) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 100){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 2) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							for (int i4 = 0; i4 < spawnTime.size; i4++){
								spawnTimeFloat[i4] = spawnTime.get(i4);
							}
							for (int i3 = 0; i3 < 5; i3++){
								jsonValue.get("spawnTime").get(2).get(i3).set(spawnTimeFloat[i3], null);
							}
							spawnTime.clear();

							for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
								garbageGoalArrayRandom.add(i2);
							}
							jsonValue.get("garbageGoal").get(2).set(garbageGoalArrayRandom.random(), null);
							garbageGoalArrayRandom.clear();
						}
					}; break;
					case 3: {
						if (!junkWorldEngines.getCurrentJob().equals("backyard")){
							jsonValue.get("jobChangeTimer").get(3).set(df.format(date2));

							hasJobArray.add(true);
							hasJobArray.add(false);
							jsonValue.get("hasJob").get(3).set(hasJobArray.random());
							hasJobArray.clear();

							gameMode = generator.nextInt(3);
							while (gameMode == 0){
								gameMode = generator.nextInt(3);
							}
							jsonValue.get("gameMode").get(3).set(gameMode, null);

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 20){
								timerArray.add(60);
								timerArray.add(75);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 40){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 60){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 80){
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 100){
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							jsonValue.get("timer").get(3).set(timerArray.random(), null);
							timerArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								garbageAmountArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
								garbageAmountArray.add(3);
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
								garbageAmountArray.add(9);
							}
							else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
								garbageAmountArray.add(9);
								garbageAmountArray.add(10);
								garbageAmountArray.add(11);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
								garbageAmountArray.add(11);
								garbageAmountArray.add(12);
								garbageAmountArray.add(13);
							}
							else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
								garbageAmountArray.add(13);
								garbageAmountArray.add(14);
								garbageAmountArray.add(15);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
								garbageAmountArray.add(15);
								garbageAmountArray.add(16);
								garbageAmountArray.add(17);
							}
							else if (junkWorldEngines.getLevel() >= 91){
								garbageAmountArray.add(18);
							}
							jsonValue.get("garbageAmount").get(3).set(garbageAmountArray.random(), null);
							garbageAmountArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								roundsArray.add(1);
								roundsArray.add(2);
							}
							else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
								roundsArray.add(4);
							}
							else {
								for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
									roundsArray.add(i2);
								}
							}
							jsonValue.get("rounds").get(3).set(roundsArray.random(), null);
							roundsArray.clear();

							spawnTime.clear();
							while (spawnTime.size < 5){
								spawnTime.add(0f);
							}
							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 30){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 4) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 60){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 3) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 100){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 2) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							for (int i4 = 0; i4 < spawnTime.size; i4++){
								spawnTimeFloat[i4] = spawnTime.get(i4);
							}
							for (int i3 = 0; i3 < 5; i3++){
								jsonValue.get("spawnTime").get(3).get(i3).set(spawnTimeFloat[i3], null);
							}
							spawnTime.clear();

							for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
								garbageGoalArrayRandom.add(i2);
							}
							jsonValue.get("garbageGoal").get(3).set(garbageGoalArrayRandom.random(), null);
							garbageGoalArrayRandom.clear();
						}
					}; break;
					case 4: {
						if (!junkWorldEngines.getCurrentJob().equals("sidewalk")){
							jsonValue.get("jobChangeTimer").get(4).set(df.format(date2));

							hasJobArray.add(true);
							hasJobArray.add(false);
							jsonValue.get("hasJob").get(4).set(hasJobArray.random());
							hasJobArray.clear();

							gameMode = generator.nextInt(3);
							while (gameMode == 0){
								gameMode = generator.nextInt(3);
							}
							jsonValue.get("gameMode").get(4).set(gameMode, null);

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 20){
								timerArray.add(60);
								timerArray.add(75);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 40){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 60){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 80){
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 100){
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							jsonValue.get("timer").get(4).set(timerArray.random(), null);
							timerArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								garbageAmountArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
								garbageAmountArray.add(3);
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
								garbageAmountArray.add(9);
							}
							else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
								garbageAmountArray.add(9);
								garbageAmountArray.add(10);
								garbageAmountArray.add(11);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
								garbageAmountArray.add(11);
								garbageAmountArray.add(12);
								garbageAmountArray.add(13);
							}
							else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
								garbageAmountArray.add(13);
								garbageAmountArray.add(14);
								garbageAmountArray.add(15);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
								garbageAmountArray.add(15);
								garbageAmountArray.add(16);
								garbageAmountArray.add(17);
							}
							else if (junkWorldEngines.getLevel() >= 91){
								garbageAmountArray.add(18);
							}
							jsonValue.get("garbageAmount").get(4).set(garbageAmountArray.random(), null);
							garbageAmountArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								roundsArray.add(1);
								roundsArray.add(2);
							}
							else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
								roundsArray.add(4);
							}
							else {
								for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
									roundsArray.add(i2);
								}
							}
							jsonValue.get("rounds").get(4).set(roundsArray.random(), null);
							roundsArray.clear();

							spawnTime.clear();
							while (spawnTime.size < 5){
								spawnTime.add(0f);
							}
							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 30){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 4) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 60){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 3) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 100){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 2) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							for (int i4 = 0; i4 < spawnTime.size; i4++){
								spawnTimeFloat[i4] = spawnTime.get(i4);
							}
							for (int i3 = 0; i3 < 5; i3++){
								jsonValue.get("spawnTime").get(4).get(i3).set(spawnTimeFloat[i3], null);
							}
							spawnTime.clear();

							for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
								garbageGoalArrayRandom.add(i2);
							}
							jsonValue.get("garbageGoal").get(4).set(garbageGoalArrayRandom.random(), null);
							garbageGoalArrayRandom.clear();
						}
					}; break;
					case 5: {
						if (!junkWorldEngines.getCurrentJob().equals("factory")){
							jsonValue.get("jobChangeTimer").get(5).set(df.format(date2));

							hasJobArray.add(true);
							hasJobArray.add(false);
							jsonValue.get("hasJob").get(5).set(hasJobArray.random());
							hasJobArray.clear();

							gameMode = generator.nextInt(3);
							while (gameMode == 0){
								gameMode = generator.nextInt(3);
							}
							jsonValue.get("gameMode").get(5).set(gameMode, null);

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 20){
								timerArray.add(60);
								timerArray.add(75);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 40){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 60){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 80){
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 100){
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							jsonValue.get("timer").get(5).set(timerArray.random(), null);
							timerArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								garbageAmountArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
								garbageAmountArray.add(3);
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
								garbageAmountArray.add(9);
							}
							else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
								garbageAmountArray.add(9);
								garbageAmountArray.add(10);
								garbageAmountArray.add(11);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
								garbageAmountArray.add(11);
								garbageAmountArray.add(12);
								garbageAmountArray.add(13);
							}
							else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
								garbageAmountArray.add(13);
								garbageAmountArray.add(14);
								garbageAmountArray.add(15);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
								garbageAmountArray.add(15);
								garbageAmountArray.add(16);
								garbageAmountArray.add(17);
							}
							else if (junkWorldEngines.getLevel() >= 91){
								garbageAmountArray.add(18);
							}
							jsonValue.get("garbageAmount").get(5).set(garbageAmountArray.random(), null);
							garbageAmountArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								roundsArray.add(1);
								roundsArray.add(2);
							}
							else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
								roundsArray.add(4);
							}
							else {
								for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
									roundsArray.add(i2);
								}
							}
							jsonValue.get("rounds").get(5).set(roundsArray.random(), null);
							roundsArray.clear();

							spawnTime.clear();
							while (spawnTime.size < 5){
								spawnTime.add(0f);
							}
							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 30){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 4) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 60){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 3) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 100){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 2) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							for (int i4 = 0; i4 < spawnTime.size; i4++){
								spawnTimeFloat[i4] = spawnTime.get(i4);
							}
							for (int i3 = 0; i3 < 5; i3++){
								jsonValue.get("spawnTime").get(5).get(i3).set(spawnTimeFloat[i3], null);
							}
							spawnTime.clear();

							for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
								garbageGoalArrayRandom.add(i2);
							}
							jsonValue.get("garbageGoal").get(5).set(garbageGoalArrayRandom.random(), null);
							garbageGoalArrayRandom.clear();
						}
					}; break;
					case 6: {
						if (!junkWorldEngines.getCurrentJob().equals("highway")){
							jsonValue.get("jobChangeTimer").get(6).set(df.format(date2));

							hasJobArray.add(true);
							hasJobArray.add(false);
							jsonValue.get("hasJob").get(6).set(hasJobArray.random());
							hasJobArray.clear();

							gameMode = generator.nextInt(3);
							while (gameMode == 0){
								gameMode = generator.nextInt(3);
							}
							jsonValue.get("gameMode").get(6).set(gameMode, null);

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 20){
								timerArray.add(60);
								timerArray.add(75);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 40){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 60){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 80){
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 100){
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							jsonValue.get("timer").get(6).set(timerArray.random(), null);
							timerArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								garbageAmountArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
								garbageAmountArray.add(3);
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
								garbageAmountArray.add(9);
							}
							else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
								garbageAmountArray.add(9);
								garbageAmountArray.add(10);
								garbageAmountArray.add(11);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
								garbageAmountArray.add(11);
								garbageAmountArray.add(12);
								garbageAmountArray.add(13);
							}
							else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
								garbageAmountArray.add(13);
								garbageAmountArray.add(14);
								garbageAmountArray.add(15);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
								garbageAmountArray.add(15);
								garbageAmountArray.add(16);
								garbageAmountArray.add(17);
							}
							else if (junkWorldEngines.getLevel() >= 91){
								garbageAmountArray.add(18);
							}
							jsonValue.get("garbageAmount").get(6).set(garbageAmountArray.random(), null);
							garbageAmountArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								roundsArray.add(1);
								roundsArray.add(2);
							}
							else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
								roundsArray.add(4);
							}
							else {
								for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
									roundsArray.add(i2);
								}
							}
							jsonValue.get("rounds").get(6).set(roundsArray.random(), null);
							roundsArray.clear();

							spawnTime.clear();
							while (spawnTime.size < 5){
								spawnTime.add(0f);
							}
							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 30){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 4) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 60){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 3) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 100){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 2) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							for (int i4 = 0; i4 < spawnTime.size; i4++){
								spawnTimeFloat[i4] = spawnTime.get(i4);
							}
							for (int i3 = 0; i3 < 5; i3++){
								jsonValue.get("spawnTime").get(6).get(i3).set(spawnTimeFloat[i3], null);
							}
							spawnTime.clear();

							for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
								garbageGoalArrayRandom.add(i2);
							}
							jsonValue.get("garbageGoal").get(6).set(garbageGoalArrayRandom.random(), null);
							garbageGoalArrayRandom.clear();
						}
					}; break;
					case 7: {
						if (!junkWorldEngines.getCurrentJob().equals("park")){
							jsonValue.get("jobChangeTimer").get(7).set(df.format(date2));

							hasJobArray.add(true);
							hasJobArray.add(false);
							jsonValue.get("hasJob").get(7).set(hasJobArray.random());
							hasJobArray.clear();

							gameMode = generator.nextInt(3);
							while (gameMode == 0){
								gameMode = generator.nextInt(3);
							}
							jsonValue.get("gameMode").get(7).set(gameMode, null);

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 20){
								timerArray.add(60);
								timerArray.add(75);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 40){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 60){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 80){
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 100){
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							jsonValue.get("timer").get(7).set(timerArray.random(), null);
							timerArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								garbageAmountArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
								garbageAmountArray.add(3);
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
								garbageAmountArray.add(9);
							}
							else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
								garbageAmountArray.add(9);
								garbageAmountArray.add(10);
								garbageAmountArray.add(11);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
								garbageAmountArray.add(11);
								garbageAmountArray.add(12);
								garbageAmountArray.add(13);
							}
							else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
								garbageAmountArray.add(13);
								garbageAmountArray.add(14);
								garbageAmountArray.add(15);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
								garbageAmountArray.add(15);
								garbageAmountArray.add(16);
								garbageAmountArray.add(17);
							}
							else if (junkWorldEngines.getLevel() >= 91){
								garbageAmountArray.add(18);
							}
							jsonValue.get("garbageAmount").get(7).set(garbageAmountArray.random(), null);
							garbageAmountArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								roundsArray.add(1);
								roundsArray.add(2);
							}
							else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
								roundsArray.add(4);
							}
							else {
								for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
									roundsArray.add(i2);
								}
							}
							jsonValue.get("rounds").get(7).set(roundsArray.random(), null);
							roundsArray.clear();

							spawnTime.clear();
							while (spawnTime.size < 5){
								spawnTime.add(0f);
							}
							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 30){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 4) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 60){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 3) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 100){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 2) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							for (int i4 = 0; i4 < spawnTime.size; i4++){
								spawnTimeFloat[i4] = spawnTime.get(i4);
							}
							for (int i3 = 0; i3 < 5; i3++){
								jsonValue.get("spawnTime").get(7).get(i3).set(spawnTimeFloat[i3], null);
							}
							spawnTime.clear();

							for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
								garbageGoalArrayRandom.add(i2);
							}
							jsonValue.get("garbageGoal").get(7).set(garbageGoalArrayRandom.random(), null);
							garbageGoalArrayRandom.clear();
						}
					}; break;
					case 8: {
						if (!junkWorldEngines.getCurrentJob().equals("sewer")){
							jsonValue.get("jobChangeTimer").get(8).set(df.format(date2));

							hasJobArray.add(true);
							hasJobArray.add(false);
							jsonValue.get("hasJob").get(8).set(hasJobArray.random());
							hasJobArray.clear();

							gameMode = generator.nextInt(3);
							while (gameMode == 0){
								gameMode = generator.nextInt(3);
							}
							jsonValue.get("gameMode").get(8).set(gameMode, null);

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 20){
								timerArray.add(60);
								timerArray.add(75);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 40){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 60){
								timerArray.add(60);
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 80){
								timerArray.add(75);
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 100){
								timerArray.add(90);
								timerArray.add(105);
								timerArray.add(120);
							}
							jsonValue.get("timer").get(8).set(timerArray.random(), null);
							timerArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								garbageAmountArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
								garbageAmountArray.add(3);
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
								garbageAmountArray.add(4);
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
							}
							else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
								garbageAmountArray.add(5);
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
								garbageAmountArray.add(6);
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
							}
							else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
								garbageAmountArray.add(7);
								garbageAmountArray.add(8);
								garbageAmountArray.add(9);
							}
							else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
								garbageAmountArray.add(9);
								garbageAmountArray.add(10);
								garbageAmountArray.add(11);
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
								garbageAmountArray.add(11);
								garbageAmountArray.add(12);
								garbageAmountArray.add(13);
							}
							else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
								garbageAmountArray.add(13);
								garbageAmountArray.add(14);
								garbageAmountArray.add(15);
							}
							else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
								garbageAmountArray.add(15);
								garbageAmountArray.add(16);
								garbageAmountArray.add(17);
							}
							else if (junkWorldEngines.getLevel() >= 91){
								garbageAmountArray.add(18);
							}
							jsonValue.get("garbageAmount").get(8).set(garbageAmountArray.random(), null);
							garbageAmountArray.clear();

							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
								roundsArray.add(1);
								roundsArray.add(2);
							}
							else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
							}
							else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
								roundsArray.add(1);
								roundsArray.add(2);
								roundsArray.add(3);
								roundsArray.add(4);
							}
							else {
								for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
									roundsArray.add(i2);
								}
							}
							jsonValue.get("rounds").get(8).set(roundsArray.random(), null);
							roundsArray.clear();

							spawnTime.clear();
							while (spawnTime.size < 5){
								spawnTime.add(0f);
							}
							if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 30){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 4) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 60){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 3) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 100){
								for (int i3 = 0; i3 < 5; i3++){
									while (spawnTime.get(i3) < 2) {
										spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
									}
								}
							}
							for (int i4 = 0; i4 < spawnTime.size; i4++){
								spawnTimeFloat[i4] = spawnTime.get(i4);
							}
							for (int i3 = 0; i3 < 5; i3++){
								jsonValue.get("spawnTime").get(8).get(i3).set(spawnTimeFloat[i3], null);
							}
							spawnTime.clear();

							for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
								garbageGoalArrayRandom.add(i2);
							}
							jsonValue.get("garbageGoal").get(8).set(garbageGoalArrayRandom.random(), null);
							garbageGoalArrayRandom.clear();
						}
					}; break;
					default: break;
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		profileFile.writeString(jsonValue.toString(), false);
	}

	private float getArrayAverage(Array<Float> array){
		float sum = 0;
		for (float num : array){
			sum += num;
		}
		return (float) sum / array.size;
	}

	private void drawBar() {

		if (currentDifficulty < .1){
			difficultyBar.get(0).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(0).draw(batch);
		}
		else if (currentDifficulty >= .1 && currentDifficulty < .2){
			difficultyBar.get(1).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(1).draw(batch);
		}
		else if (currentDifficulty >= .2 && currentDifficulty < .3){
			difficultyBar.get(2).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(2).draw(batch);
		}
		else if (currentDifficulty >= .3 && currentDifficulty < .4){
			difficultyBar.get(3).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(3).draw(batch);
		}
		else if (currentDifficulty >= .4 && currentDifficulty < .5){
			difficultyBar.get(4).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(4).draw(batch);
		}
		else if (currentDifficulty >= .5 && currentDifficulty < .6){
			difficultyBar.get(5).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(5).draw(batch);
		}
		else if (currentDifficulty >= .6 && currentDifficulty < .7){
			difficultyBar.get(6).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(6).draw(batch);
		}
		else if (currentDifficulty >= .7 && currentDifficulty < .8){
			difficultyBar.get(7).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(7).draw(batch);
		}
		else if (currentDifficulty >= .8 && currentDifficulty < .9){
			difficultyBar.get(8).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(8).draw(batch);
		}
		else if (currentDifficulty >= .9){
			difficultyBar.get(9).setBounds(220, 338, (currentDifficulty / 1) * 200, 50);
			difficultyBar.get(9).draw(batch);
		}
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
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
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
		// TODO Auto-generated method stub

	}

}
