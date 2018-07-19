package com.junkworld.game;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;

public class ProfileSelectionScreen implements Screen{
	private OrthographicCamera camera;
	private Batch batch;
	private Vector3 touchPos;
	private Stage stage, stage2, stage3;
	private AssetManager manager;
	private FileHandle defaultProfile, profileFileLoad;
	private Actor create, load, selectProfile, ok, back, erase, quit, scrollProfiles, delete, select, yes, no;
	private Array<Actor> characters;
	private CurrentProfileScreenState currentProfileScreenState;
	private StringBuilder defaultString, createProfString;
	private Array<Rectangle> characterLayers;
	private Rectangle defaultStringLength, createProStringLength, profNamesLayer, deleteLayer, selectLayer, yesLayer, noLayer;
	private Array<TextureRegion> charRegions;
	private Array<TextureRegion> charactersRegions;
	private JunkWorldEngines junkWorldEngines;
	private boolean isLoading = false;
	private TextureRegion notifier, notifier2;
	private int currentSelectProfState;
	private BitmapFont profNames;
	private FileHandle profileNamesFile, profileFile;
	private ProfileNames profileNamesClass;
	private ProfileLoader profileLoader;
	private Array<String> profileNames;
	private boolean noName = false, profExists = false;
	private Json json;
	private JsonValue jsonValue, profileValue;
	private float yPos;
	private TextureRegion whiteShader;
	private float whitePosY;
	private SelectProfileState selectProfileState;
	private DateFormat dateFormat;
	private int gameMode, timer, garbageAmount, rounds, garbageGoal;
	private Array<Float> spawnTime;
	private float[] spawnTimeFloat;
	private Array<Integer> timerArray, garbageAmountArray, roundsArray, garbageGoalArrayRandom, garbageGoalArray;
	private Array<Boolean> hasJobArray;
	private boolean hasAnyJob;
	private Random generator;
	private Array<String> jobChangeTimerProf;
	private Array<Boolean> hasJobProf;
	private Array<Integer> gameModeProf;
	private Array<Integer> timerProf;
	private Array<Integer> garbageAmountProf;
	private Array<Integer> roundsProf;
	private Array<float[]> spawnTimeProf;
	private float[] ratings;
	private long delayAds = TimeUtils.millis();

	private void disposeAssets(){
		batch.dispose();
		stage.dispose();
		stage2.dispose();
		stage3.dispose();
		this.dispose();
	}

	private void loadManager(){
		//			TODO
		for (int i = 0; i < 36; i++){
			manager.load("profileAssets/character/" + i + ".png", Texture.class);
			manager.load("profileAssets/characterButtons/" + i + ".png", Texture.class);
		}
		manager.load("profileAssets/selectProfile.png", Texture.class);
		manager.load("profileAssets/profileName.png", Texture.class);
		manager.load("profileAssets/create.png", Texture.class);
		manager.load("profileAssets/load.png", Texture.class);
		manager.load("profileAssets/quit.png", Texture.class);
		manager.load("mainMenuAssets/loadingAssets/blackShader2.png", Texture.class);
		manager.load("profileAssets/characterButtons/back.png", Texture.class);
		manager.load("profileAssets/back.png", Texture.class);
		manager.load("profileAssets/ok.png", Texture.class);
		manager.load("profileAssets/notifier.png", Texture.class);
		manager.load("profileAssets/notifier2.png", Texture.class);
		manager.load("profileAssets/splashPart.png", Texture.class);
		manager.load("profileAssets/splashPart2.png", Texture.class);
		manager.load("profileAssets/select.png", Texture.class);
		manager.load("profileAssets/delete.png", Texture.class);
		manager.load("profileAssets/selectClicked.png", Texture.class);
		manager.load("profileAssets/deleteClicked.png", Texture.class);
		manager.load("backgrounds/greenBackground.png", Texture.class);
		manager.load("backgrounds/whiteShader.png", Texture.class);
		manager.load("profileAssets/yes.png", Texture.class);
		manager.load("profileAssets/no.png", Texture.class);
		manager.load("profileAssets/tap.png", Texture.class);
		manager.finishLoading();
	}

	private void unloadManager(){
		//				TODO
		for (int i = 0; i < 36; i++){
			manager.unload("profileAssets/character/" + i + ".png");
			manager.unload("profileAssets/characterButtons/" + i + ".png");
		}
		manager.unload("profileAssets/selectProfile.png");
		manager.unload("profileAssets/profileName.png");
		manager.unload("profileAssets/create.png");
		manager.unload("profileAssets/load.png");
		manager.unload("profileAssets/quit.png");
		manager.unload("mainMenuAssets/loadingAssets/blackShader2.png");
		manager.unload("profileAssets/characterButtons/back.png");
		manager.unload("profileAssets/back.png");
		manager.unload("profileAssets/ok.png");
		manager.unload("profileAssets/notifier.png");
		manager.unload("profileAssets/notifier2.png");
		manager.unload("profileAssets/splashPart.png");
		manager.unload("profileAssets/splashPart2.png");
		manager.unload("profileAssets/select.png");
		manager.unload("profileAssets/delete.png");
		manager.unload("profileAssets/selectClicked.png");
		manager.unload("profileAssets/deleteClicked.png");
		manager.unload("backgrounds/greenBackground.png");
		manager.unload("backgrounds/whiteShader.png");
		manager.unload("profileAssets/yes.png");
		manager.unload("profileAssets/no.png");
		manager.unload("profileAssets/tap.png");
	}

	@SuppressWarnings("deprecation")
	public ProfileSelectionScreen(final JunkWorld game, final AssetManager manager){
		this.manager = manager;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		batch = new SpriteBatch();	
		touchPos = new Vector3(); 
		stage = new Stage();
		stage2 = new Stage();
		stage3 = new Stage();
		junkWorldEngines = new JunkWorldEngines();
		profileNamesClass = new ProfileNames();
		profileLoader = new ProfileLoader();
		profileNames = new Array<String>();
		generator = new Random();
		spawnTime = new Array<Float>();
		timerArray = new Array<Integer>();
		garbageAmountArray = new Array<Integer>();
		roundsArray = new Array<Integer>();
		jobChangeTimerProf = new Array<String>();
		hasJobProf = new Array<Boolean>();
		hasJobArray = new Array<Boolean>();
		gameModeProf = new Array<Integer>();
		timerProf = new Array<Integer>();
		garbageAmountProf = new Array<Integer>();
		roundsProf = new Array<Integer>();
		spawnTimeProf = new Array<float[]>();
		garbageGoalArrayRandom = new Array<Integer>();
		garbageGoalArray = new Array<Integer>();
		spawnTimeFloat = new float[5];
		ratings = new float[22];
		json = new Json();
		json.setUsePrototypes(false);
		loadManager();

		defaultProfile = Gdx.files.local("pLoader.txt");
		if (!defaultProfile.exists()){
			try {
				defaultProfile.file().createNewFile();
			} catch (IOException e){
				e.printStackTrace();
			}
		}

		profileNamesFile = Gdx.files.local("pNames.json");
		if (!profileNamesFile.exists()){
			try {
				profileNamesFile.file().createNewFile();
			} catch (IOException e){
				e.printStackTrace();
			}
		}

		if (isNotNullNotEmptyNotWhiteSpaceOnlyByJava(profileNamesFile.readString())){
			jsonValue = new JsonReader().parse(profileNamesFile);
			JsonValue jsonArray = jsonValue.get("profiles");
			for (String value : jsonArray.asStringArray()){
				profileNames.add(value);
			}
		}

		profileNames.sort();
		profileNamesClass.setProfiles(profileNames);
		profileNamesFile.writeString(json.prettyPrint(profileNamesClass), false);

		defaultString = new StringBuilder(defaultProfile.readString());
		createProfString = new StringBuilder("");
		currentSelectProfState = 0;

		profNamesLayer = new Rectangle(64, 350, 384, 400);
		defaultStringLength = new Rectangle();
		defaultStringLength.setY(632);
		defaultStringLength.setHeight(64);

		createProStringLength = new Rectangle();
		createProStringLength.setY(632);
		createProStringLength.setHeight(64);

		notifier = new TextureRegion(manager.get("profileAssets/notifier.png", Texture.class));
		notifier2 = new TextureRegion(manager.get("profileAssets/notifier2.png", Texture.class));
		whiteShader = new TextureRegion(manager.get("backgrounds/whiteShader.png", Texture.class));

		deleteLayer = new Rectangle(256 - 110 - 40, 250, 110, 50);
		selectLayer = new Rectangle(256 + 40, 250, 110, 50);
		yesLayer = new Rectangle(256 + 10, 200, 70, 50);
		noLayer = new Rectangle(256 - 70, 200, 60, 50);
		
		whitePosY = profNamesLayer.height - 20;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SHOWG.TTF"));
		profNames = generator.generateFont(35);
		generator.dispose();

		new Array<Integer>();
		characterLayers = new Array<Rectangle>();
		characters = new Array<Actor>();
		charRegions = new Array<TextureRegion>();
		create = new Actor();
		load = new Actor();
		selectProfile = new Actor();
		erase = new Actor();
		ok = new Actor();
		back = new Actor();
		quit = new Actor();
		scrollProfiles = new Actor();
		delete = new Actor();
		select = new Actor();
		yes = new Actor();
		no = new Actor();

		setActorChars();
		setRegions();

		stage.addActor(create);
		stage.addActor(load);
		stage.addActor(selectProfile);
		stage.addActor(quit);

		stage2.addActor(ok);
		stage2.addActor(back);
		stage2.addActor(erase);

		create.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentProfileScreenState == CurrentProfileScreenState.DEFAULT){
					currentProfileScreenState = CurrentProfileScreenState.CREATE_PROF;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		load.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentProfileScreenState == CurrentProfileScreenState.DEFAULT && isNotNullNotEmptyNotWhiteSpaceOnlyByJava(defaultString.toString())){
					profileFileLoad = Gdx.files.local(defaultString.toString() + ".json");
					profileValue = new JsonReader().parse(profileFileLoad);
					junkWorldEngines.setProfileName(defaultString.toString());
					junkWorldEngines.setLevel(profileValue.getInt("level"));
					junkWorldEngines.setJunkCoins(profileValue.getInt("junkCoins"));
					junkWorldEngines.setCurrentJob(profileValue.getString("currentJob"));
					unlockables();
					isLoading = true;
					unloadManager();
					disposeAssets();
					game.setScreen(new MainMenuScreen(game, manager, junkWorldEngines));
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		selectProfile.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentProfileScreenState == CurrentProfileScreenState.DEFAULT && profileNames.size != 0){
					currentProfileScreenState = CurrentProfileScreenState.SELECT_PROF;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		back.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					currentProfileScreenState = CurrentProfileScreenState.DEFAULT;
					createProfString.replace(0, createProfString.length() + 1, "");
					noName = false;	
					profExists = false;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		ok.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					if (isNotNullNotEmptyNotWhiteSpaceOnlyByJava(createProfString.toString())){
						profileFile = Gdx.files.local(createProfString.toString() + ".json");
						if (!profileFile.exists()){
							currentProfileScreenState = CurrentProfileScreenState.DEFAULT;
							defaultProfile.writeString(createProfString.toString(), false);
							defaultString.replace(0, defaultString.length(), defaultProfile.readString());
							profileNames.add(defaultString.toString());
							createProfString.replace(0, createProfString.length(), "");
							noName = false;	
							profExists = false;
							try {
								profileFile.file().createNewFile();
								setExpirationTimer();
								profileFile.writeString(json.prettyPrint(profileLoader), false);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							noName = false;
							profExists = true;
						}
					}
					else {
						noName = true;
						profExists = false;
					}
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		erase.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					if (createProfString.length() - 1 >= 0){
						createProfString.delete(createProfString.length() - 1, createProfString.length());
					}
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		quit.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentProfileScreenState == CurrentProfileScreenState.DEFAULT){
					Gdx.app.exit();
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		scrollProfiles.addListener(new ActorGestureListener(){

			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				yPos = touchPos.y - profNamesLayer.y;
				super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (touchPos.y >= 350 && touchPos.y < 750){
					if (touchPos.y - profNamesLayer.y != yPos){
						profNamesLayer.y = touchPos.y - yPos;
					}
				}
				super.pan(event, x, y, deltaX, deltaY);
			}

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				if (touchPos.y >= 350 && touchPos.y < 750 && profileNames.size > Math.round((profNamesLayer.y + profNamesLayer.height - touchPos.y) / 50)){
					whitePosY = touchPos.y - profNamesLayer.y;
					super.tap(event, x, y, count, button);
				}
			}
		});
		delete.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (selectProfileState == SelectProfileState.DELETE){
					selectProfileState = SelectProfileState.NONE;
				} else selectProfileState = SelectProfileState.DELETE;
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		select.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (selectProfileState == SelectProfileState.SELECT){
					selectProfileState = SelectProfileState.NONE;
				} else selectProfileState = SelectProfileState.SELECT;
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		yes.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (selectProfileState == SelectProfileState.SELECT){
					currentProfileScreenState = CurrentProfileScreenState.DEFAULT;
					defaultProfile.writeString(profileNames.get(Math.round((profNamesLayer.height - whitePosY) / 50)), false);
					defaultString.replace(0, defaultString.length(), defaultProfile.readString());
					selectProfileState = SelectProfileState.NONE;
				} 
				else if (selectProfileState == SelectProfileState.DELETE){
					profileFile = Gdx.files.local(profileNames.get(Math.round((profNamesLayer.height - whitePosY) / 50)));
					profileNames.removeValue(profileFile.name(), true);
					profileFile.delete();
					if (profileNames.size == 0){
						currentProfileScreenState = CurrentProfileScreenState.DEFAULT;
						defaultProfile.writeString("", false);
						defaultString.replace(0, defaultString.length(), defaultProfile.readString());
					}
					selectProfileState = SelectProfileState.NONE;
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		no.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				selectProfileState = SelectProfileState.NONE;
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		stage3.addActor(scrollProfiles);
		stage3.addActor(delete);
		stage3.addActor(select);
		stage3.addActor(yes);
		stage3.addActor(no);
		selectProfileState = SelectProfileState.NONE;
		currentProfileScreenState = CurrentProfileScreenState.DEFAULT;
//		game.adManager.showBannerAds(true);
//		myRequestHandler.showAds2(true);
		
	}

	protected void unlockables() {
		if (profileValue.getInt("level") >= 6) profileValue.get("diff2").set(true);
		if (profileValue.getInt("level") >= 16) profileValue.get("diff3").set(true);
		if (profileValue.getInt("level") >= 26) profileValue.get("diff4").set(true);
		if (profileValue.getInt("level") >= 36) profileValue.get("diff5").set(true);
		if (profileValue.getInt("level") >= 46) profileValue.get("diff6").set(true);
		if (profileValue.getInt("level") >= 56) profileValue.get("diff7").set(true);
		if (profileValue.getInt("level") >= 66) profileValue.get("diff8").set(true);
		if (profileValue.getInt("level") >= 76) profileValue.get("diff9").set(true);
		if (profileValue.getInt("level") >= 86) profileValue.get("diff10").set(true);
		
		if (profileValue.getInt("level") >= 20) junkWorldEngines.setPalette3Unlocked(true);
		if (profileValue.getInt("level") >= 40) junkWorldEngines.setPalette4Unlocked(true);
		
		if (profileValue.getInt("level") >= 2) junkWorldEngines.setKitchenUnlocked(true);
		if (profileValue.getInt("level") >= 4) junkWorldEngines.setBasementUnlocked(true);
		if (profileValue.getInt("level") >= 6) junkWorldEngines.setGarageUnlocked(true);
		if (profileValue.getInt("level") >= 8) junkWorldEngines.setBackyardUnlocked(true);
		if (profileValue.getInt("level") >= 10) junkWorldEngines.setSidewalkUnlocked(true);
		if (profileValue.getInt("level") >= 12) junkWorldEngines.setFactoryUnlocked(true);
		if (profileValue.getInt("level") >= 14) junkWorldEngines.setHighwayUnlocked(true);
		if (profileValue.getInt("level") >= 16) junkWorldEngines.setParkUnlocked(true);
		if (profileValue.getInt("level") >= 18) junkWorldEngines.setSewerUnlocked(true);
		
		if (profileValue.getInt("level") >= 6) junkWorldEngines.setBurstModeCooldown(15000);
		if (profileValue.getInt("level") >= 6) junkWorldEngines.setBurstModeDur(3000);
		if (profileValue.getInt("level") >= 16) junkWorldEngines.setBurstModeCooldown(14000);
		if (profileValue.getInt("level") >= 16) junkWorldEngines.setBurstModeDur(3500);
		if (profileValue.getInt("level") >= 26) junkWorldEngines.setBurstModeCooldown(13000);
		if (profileValue.getInt("level") >= 26) junkWorldEngines.setBurstModeDur(4000);
		if (profileValue.getInt("level") >= 36) junkWorldEngines.setBurstModeCooldown(12000);
		if (profileValue.getInt("level") >= 36) junkWorldEngines.setBurstModeDur(4500);
		if (profileValue.getInt("level") >= 46) junkWorldEngines.setBurstModeCooldown(11000);
		if (profileValue.getInt("level") >= 46) junkWorldEngines.setBurstModeDur(5000);
		if (profileValue.getInt("level") >= 56) junkWorldEngines.setBurstModeCooldown(10000);
		if (profileValue.getInt("level") >= 56) junkWorldEngines.setBurstModeDur(5500);
		if (profileValue.getInt("level") >= 66) junkWorldEngines.setBurstModeCooldown(9000);
		if (profileValue.getInt("level") >= 66) junkWorldEngines.setBurstModeDur(6500);
		if (profileValue.getInt("level") >= 76) junkWorldEngines.setBurstModeCooldown(8000);
		if (profileValue.getInt("level") >= 76) junkWorldEngines.setBurstModeDur(7000);
		if (profileValue.getInt("level") >= 86) junkWorldEngines.setBurstModeCooldown(7000);
		if (profileValue.getInt("level") >= 86) junkWorldEngines.setBurstModeDur(7500);
		if (profileValue.getInt("level") >= 96) junkWorldEngines.setBurstModeCooldown(6000);
		if (profileValue.getInt("level") > 96) junkWorldEngines.setBurstModeDur(8000);
		
		if (profileValue.getInt("level") >= 4) junkWorldEngines.setIceflake(true);
		if (profileValue.getInt("level") >= 8) junkWorldEngines.setSwitchMachine(true);
		if (profileValue.getInt("level") >= 16) junkWorldEngines.setAugmentedBurst(true);
		if (profileValue.getInt("level") >= 24) junkWorldEngines.setSticker(true);
		
		if (profileValue.getInt("level") >= 20) junkWorldEngines.setTotalUnlockedItemsSlots(3);
		if (profileValue.getInt("level") >= 40) junkWorldEngines.setTotalUnlockedItemsSlots(4);
		if (profileValue.getInt("level") >= 60) junkWorldEngines.setTotalUnlockedItemsSlots(5);
		if (profileValue.getInt("level") >= 80) junkWorldEngines.setTotalUnlockedItemsSlots(6);

		if (profileValue.getInt("level") >= 5) junkWorldEngines.setScorchingCanUnlocked(true);
		if (profileValue.getInt("level") >= 10) junkWorldEngines.setVacuumCanUnlocked(true);
		if (profileValue.getInt("level") >= 15) junkWorldEngines.setTrashBlowerUnlocked(true);
		if (profileValue.getInt("level") >= 20) junkWorldEngines.setDullCanUnlocked(true);
		if (profileValue.getInt("level") >= 25) junkWorldEngines.setSwiftCanUnlocked(true);
		
		if (profileValue.getInt("kitchenCount") >= 30) profileValue.get("kitchen").set(true);
		if (profileValue.getInt("basementCount") >= 32) profileValue.get("basement").set(true);
		if (profileValue.getInt("garageCount") >= 34) profileValue.get("garage").set(true);
		if (profileValue.getInt("backyardCount") >= 36) profileValue.get("backyard").set(true);
		if (profileValue.getInt("sidewalkCount") >= 38) profileValue.get("sidewalk").set(true);
		if (profileValue.getInt("factoryCount") >= 40) profileValue.get("factory").set(true);
		if (profileValue.getInt("highwayCount") >= 42) profileValue.get("highway").set(true);
		if (profileValue.getInt("parkCount") >= 44) profileValue.get("park").set(true);
		if (profileValue.getInt("sewerCount") >= 46) profileValue.get("sewer").set(true);

		if (profileValue.getInt("level") >= 5) profileValue.get("dumpingJob").set(true);
		if (profileValue.getInt("level") >= 10) profileValue.get("survival").set(true);
		
		profileFileLoad.writeString(profileValue.toString(), false);
	}

	protected void setExpirationTimer() {
//		TODO
		profileLoader.setProfile(profileFile.name());
		profileLoader.setLevel(1);
		profileLoader.setJunkCoins(0);
		profileLoader.setHighestScoreEver(0);
		profileLoader.setCurrentXP(0);
		profileLoader.setCurrentJob("none");
		profileLoader.setTriesLeft(3);
		profileLoader.setFee(0);
		profileLoader.setCurrentRound(0);
		profileLoader.setDefaultDiff(0);
		profileLoader.setDefaultGameMode(0);
		profileLoader.setDefaultTheme(0);
		profileLoader.setJobHighestPerfectCombo(0);
		profileLoader.setJobTotalPerfectDump(0);
		profileLoader.setJobTotalDump(0);
		profileLoader.setJobTotalUnofficialDump(0);
		profileLoader.setJobTotalNotDumped(0);
		profileLoader.setJobRoundRatings(ratings);
		profileLoader.setSurvivalMinute(0);
		profileLoader.setSurvivalSeconds(0);
		
		profileLoader.setTimeAttack(true);
		profileLoader.setDumpingJob(false);
		profileLoader.setSurvival(false);
		profileLoader.setKitchen(false);
		profileLoader.setBasement(false);
		profileLoader.setGarage(false);
		profileLoader.setBackyard(false);
		profileLoader.setSidewalk(false);
		profileLoader.setFactory(false);
		profileLoader.setHighway(false);
		profileLoader.setPark(false);
		profileLoader.setSewer(false);
		profileLoader.setDiff2(false);
		profileLoader.setDiff3(false);
		profileLoader.setDiff4(false);
		profileLoader.setDiff5(false);
		profileLoader.setDiff6(false);
		profileLoader.setDiff7(false);
		profileLoader.setDiff8(false);
		profileLoader.setDiff9(false);
		profileLoader.setDiff10(false);
		
		profileLoader.setKitchenCount(0);
		profileLoader.setBackyardCount(0);
		profileLoader.setGarageCount(0);
		profileLoader.setBackyardCount(0);
		profileLoader.setSidewalkCount(0);
		profileLoader.setFactoryCount(0);
		profileLoader.setHighwayCount(0);
		profileLoader.setParkCount(0);
		profileLoader.setSewerCount(0);
		
		profileLoader.setAcornAlbum(false);
		profileLoader.setAppleAlbum(false);
		profileLoader.setBananaPeelAlbum(false);
		profileLoader.setBranchAlbum(false);
		profileLoader.setDeadMouseAlbum(false);
		profileLoader.setDeadMouseAlbum(false);
		profileLoader.setEggShellAlbum(false);
		profileLoader.setFeatherAlbum(false);
		profileLoader.setFishBoneAlbum(false);
		profileLoader.setFlowerAlbum(false);
		profileLoader.setGrassAlbum(false);
		profileLoader.setHairAlbum(false);
		profileLoader.setHayAlbum(false);
		profileLoader.setLeavesAlbum(false);
		profileLoader.setManureAlbum(false);
		profileLoader.setRootsAlbum(false);
		
		profileLoader.setBottleAlbum(false);
		profileLoader.setCanAlbum(false);
		profileLoader.setCardboardAlbum(false);
		profileLoader.setCerealBoxAlbum(false);
		profileLoader.setDirtyShirtAlbum(false);
		profileLoader.setEnvelopeAlbum(false);
		profileLoader.setFunnelAlbum(false);
		profileLoader.setHangerAlbum(false);
		profileLoader.setNewspaperAlbum(false);
		profileLoader.setPaperAlbum(false);
		profileLoader.setPillBottleAlbum(false);
		profileLoader.setPlasticBagAlbum(false);
		profileLoader.setTyreAlbum(false);
		profileLoader.setVaseAlbum(false);
		profileLoader.setWaffleIronAlbum(false);
		
		profileLoader.setAerosolCanAlbum(false);
		profileLoader.setBrokenBulbAlbum(false);
		profileLoader.setBrokenGlassAlbum(false);
		profileLoader.setChewingGumAlbum(false);
		profileLoader.setCigaretteAlbum(false);
		profileLoader.setDeadBatteryAlbum(false);
		profileLoader.setDirtyDiaperAlbum(false);
		profileLoader.setInsecticideSprayAlbum(false);
		profileLoader.setLeftoverCakeAlbum(false);
		profileLoader.setLeftoverChickenAlbum(false);
		profileLoader.setNailAlbum(false);
		profileLoader.setPaintCanAlbum(false);
		profileLoader.setSyringeAlbum(false);
		profileLoader.setTornPaperAlbum(false);
		profileLoader.setUsedMotorOilAlbum(false);
		
		junkWorldEngines.setProfileName(profileFile.name());
		junkWorldEngines.setLevel(profileLoader.getLevel());
		junkWorldEngines.setJunkCoins(profileLoader.getJunkCoins());
		generateRandomStages();
	}

	private void setRegions() {
		charactersRegions = new Array<TextureRegion>();
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/0.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/1.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/2.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/3.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/4.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/5.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/6.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/7.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/8.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/9.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/10.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/11.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/12.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/13.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/14.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/15.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/16.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/17.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/18.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/19.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/20.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/21.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/22.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/23.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/24.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/25.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/26.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/27.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/28.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/29.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/30.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/31.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/32.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/33.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/34.png", Texture.class)));
		charactersRegions.add(new TextureRegion(manager.get("profileAssets/character/35.png", Texture.class)));
	}

	@Override
	public void render(float delta) {
		if (!isLoading){
			Gdx.gl.glClearColor(0.5f, 1, 0.3f, 0.9f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			setRectangles();
			drawBatches();

			setActorBounds();
			
			if (Gdx.input.justTouched()){
				delayAds = TimeUtils.millis();
			}
			if (TimeUtils.millis() - delayAds >= 5000){
				delayAds = TimeUtils.millis();
//				myRequestHandler.showAds2(true);
			}

			if (currentProfileScreenState == CurrentProfileScreenState.DEFAULT){
				stage.getViewport().setCamera(camera);
				stage.draw();
				stage.act();
				Gdx.input.setInputProcessor(stage);
			}
			else if (currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
				stage2.getViewport().setCamera(camera);
				stage2.draw();
				stage2.act();
				Gdx.input.setInputProcessor(stage2);
			}
			else if (currentProfileScreenState == CurrentProfileScreenState.SELECT_PROF){
				stage3.getViewport().setCamera(camera);
				stage3.draw();
				stage3.act();
				Gdx.input.setInputProcessor(stage3);
			}
		}
	}

	private void drawBatches() {
		batch.begin();
		if (currentProfileScreenState != CurrentProfileScreenState.SELECT_PROF){
			batch.draw(manager.get("splashScreenAssets/splashscreen.png", Texture.class), 0, 0);
			batch.draw(manager.get("profileAssets/selectProfile.png", Texture.class), 256 - (330 / 2), 450 + 55, 330, 64);
			if (currentProfileScreenState == CurrentProfileScreenState.DEFAULT){
				batch.draw(manager.get("profileAssets/profileName.png", Texture.class), 256 - (manager.get("profileAssets/profileName.png", Texture.class).getWidth() / 2),
						250 + 100 + 50, manager.get("profileAssets/profileName.png", Texture.class).getWidth(),
						manager.get("profileAssets/profileName.png", Texture.class).getHeight());

				for (int i = 0; i < defaultString.length(); i++){
					if (defaultString.substring(i, i + 1).equals("a")){
						batch.draw(charactersRegions.get(0), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("b")){
						batch.draw(charactersRegions.get(1), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("c")){
						batch.draw(charactersRegions.get(2), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("d")){
						batch.draw(charactersRegions.get(3), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("e")){
						batch.draw(charactersRegions.get(4), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("f")){
						batch.draw(charactersRegions.get(5), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("g")){
						batch.draw(charactersRegions.get(6), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("h")){
						batch.draw(charactersRegions.get(7), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("i")){
						batch.draw(charactersRegions.get(8), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("j")){
						batch.draw(charactersRegions.get(9), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("k")){
						batch.draw(charactersRegions.get(10), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("l")){
						batch.draw(charactersRegions.get(11), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("m")){
						batch.draw(charactersRegions.get(12), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("n")){
						batch.draw(charactersRegions.get(13), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("o")){
						batch.draw(charactersRegions.get(14), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("p")){
						batch.draw(charactersRegions.get(15), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("q")){
						batch.draw(charactersRegions.get(16), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("r")){
						batch.draw(charactersRegions.get(17), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("s")){
						batch.draw(charactersRegions.get(18), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("t")){
						batch.draw(charactersRegions.get(19), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("u")){
						batch.draw(charactersRegions.get(20), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("v")){
						batch.draw(charactersRegions.get(21), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("w")){
						batch.draw(charactersRegions.get(22), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("x")){
						batch.draw(charactersRegions.get(23), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("y")){
						batch.draw(charactersRegions.get(24), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("z")){
						batch.draw(charactersRegions.get(25), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("0")){
						batch.draw(charactersRegions.get(26), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("1")){
						batch.draw(charactersRegions.get(27), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("2")){
						batch.draw(charactersRegions.get(28), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("3")){
						batch.draw(charactersRegions.get(29), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("4")){
						batch.draw(charactersRegions.get(30), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("5")){
						batch.draw(charactersRegions.get(31), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("6")){
						batch.draw(charactersRegions.get(32), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("7")){
						batch.draw(charactersRegions.get(33), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("8")){
						batch.draw(charactersRegions.get(34), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
					if (defaultString.substring(i, i + 1).equals("9")){
						batch.draw(charactersRegions.get(35), defaultStringLength.x + (32 * i), 250 + 100 + 16 + 50, 32, 64);
					}
				}
			}

			batch.draw(manager.get("profileAssets/create.png", Texture.class), 256 - 100 - (170 / 2), 150 + 100 + 50, 170, 64);
			batch.draw(manager.get("profileAssets/load.png", Texture.class), 256 + 100 - (170 / 2), 150 + 100 + 50, 170, 64);
			batch.draw(manager.get("profileAssets/quit.png", Texture.class), 256 - 50, 60 + 100 + 20 + 50, 100, 64);

			if (currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
				batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader2.png", Texture.class), 0, 0, 512, 800);
				batch.draw(manager.get("profileAssets/profileName.png", Texture.class), 256 - (manager.get("profileAssets/profileName.png", Texture.class).getWidth() / 2),
						600 + 70, manager.get("profileAssets/profileName.png", Texture.class).getWidth(),
						manager.get("profileAssets/profileName.png", Texture.class).getHeight());
				for (Rectangle chars : characterLayers){
					batch.draw(charRegions.get(characterLayers.indexOf(chars, true)), chars.x, chars.y + 70, chars.width, chars.height);
				}
				batch.draw(manager.get("profileAssets/characterButtons/back.png", Texture.class), 350, 140 + 70, 60, 32);
				batch.draw(manager.get("profileAssets/back.png", Texture.class), 256 - 5 - 150 - 20, 140 + 50, 128, 64);
				batch.draw(manager.get("profileAssets/ok.png", Texture.class), 250, 140 + 50, 64, 64);

				for (int i = 0; i < createProfString.length(); i++){
					if (createProfString.substring(i, i + 1).equals("a")){
						batch.draw(charactersRegions.get(0), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("b")){
						batch.draw(charactersRegions.get(1), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("c")){
						batch.draw(charactersRegions.get(2), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("d")){
						batch.draw(charactersRegions.get(3), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("e")){
						batch.draw(charactersRegions.get(4), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("f")){
						batch.draw(charactersRegions.get(5), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("g")){
						batch.draw(charactersRegions.get(6), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("h")){
						batch.draw(charactersRegions.get(7), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("i")){
						batch.draw(charactersRegions.get(8), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("j")){
						batch.draw(charactersRegions.get(9), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("k")){
						batch.draw(charactersRegions.get(10), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("l")){
						batch.draw(charactersRegions.get(11), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("m")){
						batch.draw(charactersRegions.get(12), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("n")){
						batch.draw(charactersRegions.get(13), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("o")){
						batch.draw(charactersRegions.get(14), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("p")){
						batch.draw(charactersRegions.get(15), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("q")){
						batch.draw(charactersRegions.get(16), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("r")){
						batch.draw(charactersRegions.get(17), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("s")){
						batch.draw(charactersRegions.get(18), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("t")){
						batch.draw(charactersRegions.get(19), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("u")){
						batch.draw(charactersRegions.get(20), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("v")){
						batch.draw(charactersRegions.get(21), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("w")){
						batch.draw(charactersRegions.get(22), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("x")){
						batch.draw(charactersRegions.get(23), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("y")){
						batch.draw(charactersRegions.get(24), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("z")){
						batch.draw(charactersRegions.get(25), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("0")){
						batch.draw(charactersRegions.get(26), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("1")){
						batch.draw(charactersRegions.get(27), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("2")){
						batch.draw(charactersRegions.get(28), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("3")){
						batch.draw(charactersRegions.get(29), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("4")){
						batch.draw(charactersRegions.get(30), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("5")){
						batch.draw(charactersRegions.get(31), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("6")){
						batch.draw(charactersRegions.get(32), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("7")){
						batch.draw(charactersRegions.get(33), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("8")){
						batch.draw(charactersRegions.get(34), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
					if (createProfString.substring(i, i + 1).equals("9")){
						batch.draw(charactersRegions.get(35), createProStringLength.x + (32 * i), 616 + 70, 32, 64);
					}
				}
				if (noName){
					batch.draw(notifier, 256 - (312 / 2), 150, 312, 40);
				}
				if (profExists){
					batch.draw(notifier2, 256 - (200 / 2), 150, 200, 40);
				}
			}

			if (isLoading){
				batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader.png", Texture.class), 0, 0, 512, 800);
				batch.draw(manager.get("mainMenuAssets/loadingAssets/loadingCircularSigns.png", Texture.class), 64, 208);
				JunkWorld.loading.draw(batch);
				JunkWorld.loading.rotate(20);
			}
		}
		else {
			batch.draw(manager.get("profileAssets/splashPart.png", Texture.class), 0, 0);
			batch.draw(manager.get("backgrounds/greenBackground.png", Texture.class), profNamesLayer.x, profNamesLayer.y, profNamesLayer.width, profNamesLayer.height);

			for (String names : profileNames){
				profNames.draw(batch, names, 70, (profNamesLayer.y + profNamesLayer.height - 10) - (profileNames.indexOf(names, true) * 50));
			}

			batch.draw(whiteShader, profNamesLayer.x, profNamesLayer.y + ((Math.round(whitePosY / 50) - 1) * 50),
					384, 50);

			if (currentSelectProfState != 2){
				batch.draw(manager.get("profileAssets/delete.png", Texture.class), 256 - 110 - 20, 280, 110, 50);
			} else batch.draw(manager.get("profileAssets/deleteClicked.png", Texture.class), 256 - 110 - 20, 280, 110, 50);

			if (currentSelectProfState != 1){
				batch.draw(manager.get("profileAssets/select.png", Texture.class), 256 + 20, 280, 110, 50);
			} else batch.draw(manager.get("profileAssets/selectClicked.png", Texture.class), 256 + 20, 280, 110, 50);

			//			manager.load("backgrounds/whiteShader.png", Texture.class);
			batch.draw(manager.get("profileAssets/splashPart2.png", Texture.class), 0, 0);
			batch.draw(manager.get("profileAssets/tap.png", Texture.class), 256 - 175, 300, 350, 40);
			if (selectProfileState == SelectProfileState.DELETE){
				batch.draw(manager.get("profileAssets/deleteClicked.png", Texture.class), deleteLayer.x, deleteLayer.y, deleteLayer.width, deleteLayer.height);
			} else batch.draw(manager.get("profileAssets/delete.png", Texture.class), deleteLayer.x, deleteLayer.y, deleteLayer.width, deleteLayer.height);
			if (selectProfileState == SelectProfileState.SELECT){
				batch.draw(manager.get("profileAssets/selectClicked.png", Texture.class), selectLayer.x, selectLayer.y, selectLayer.width, selectLayer.height);
			} else batch.draw(manager.get("profileAssets/select.png", Texture.class), selectLayer.x, selectLayer.y, selectLayer.width, selectLayer.height);
			if (selectProfileState != SelectProfileState.NONE){
				batch.draw(manager.get("profileAssets/yes.png", Texture.class), yesLayer.x, yesLayer.y, yesLayer.width, yesLayer.height);
				batch.draw(manager.get("profileAssets/no.png", Texture.class), noLayer.x, noLayer.y, noLayer.width, noLayer.height);
			}
		}
		batch.end();
	}

	private void setActorBounds() {
		create.setBounds(256 - 100 - (170 / 2), 200 + 100, 170, 64);
		load.setBounds(256 + 100 - (170 / 2), 200 + 100, 170, 64);
		selectProfile.setBounds(256 - (manager.get("profileAssets/profileName.png", Texture.class).getWidth() / 2),
				250 + 150, manager.get("profileAssets/profileName.png", Texture.class).getWidth(),
				manager.get("profileAssets/profileName.png", Texture.class).getHeight());
		for (Rectangle chars : characterLayers){
			characters.get(characterLayers.indexOf(chars, true)).setBounds(chars.x, chars.y + 70, chars.width, chars.height);
		}
		back.setBounds(256 - 5 - 150 - 20, 140 + 50, 128, 64);
		ok.setBounds(250, 140 + 50, 64, 64);
		erase.setBounds(350, 140 + 70, 60, 32);
		quit.setBounds(256 - 50, 60 + 100 + 20 + 50, 100, 64);
		scrollProfiles.setBounds(profNamesLayer.x, profNamesLayer.y, profNamesLayer.width, profNamesLayer.height);
		delete.setBounds(deleteLayer.x, deleteLayer.y, deleteLayer.width, deleteLayer.height);
		select.setBounds(selectLayer.x, selectLayer.y, selectLayer.width, selectLayer.height);
		yes.setBounds(yesLayer.x, yesLayer.y, yesLayer.width, yesLayer.height);
		no.setBounds(noLayer.x, noLayer.y, noLayer.width, noLayer.height);
	}

	private void setRectangles() {
		profileNames.sort();
		profileNamesClass.setProfiles(profileNames);
		profileNamesFile.writeString(json.prettyPrint(profileNamesClass), false);

		defaultStringLength.set(256 - (defaultStringLength.width / 2), 632, defaultString.toString().length() * 32, 64);
		createProStringLength.set(256 - (createProStringLength.width / 2), 632, createProfString.toString().length() * 32, 64);

		if ((profNamesLayer.y + profNamesLayer.height) <= 750){
			profNamesLayer.y = 750 - profNamesLayer.height;
		}

		if (profNamesLayer.y > 350){
			profNamesLayer.y = 350;
		}

		for (String names : profileNames){
			if (profileNames.indexOf(names, true) > 7){
				profNamesLayer.height = 400 + ((profileNames.indexOf(names, true) - 7) * 50);
			}
		}
	}

	private void setActorChars() {
		for (int i = 0; i < 36; i++){
			charRegions.add(new TextureRegion(manager.get("profileAssets/characterButtons/" + i + ".png", Texture.class)));
			characters.add(new Actor());
			stage2.addActor(characters.get(i));

			characterLayers.add(new Rectangle());

			characterLayers.get(i).setWidth(50);
			characterLayers.get(i).setHeight(50);

			if (i % 6 == 0){
				characterLayers.get(i).setX(256 - 5 - 150 - 20);
			}
			else if (i % 6 == 1){
				characterLayers.get(i).setX(256 - 5 - 100 - 10);
			}
			else if (i % 6 == 2){
				characterLayers.get(i).setX(256 - 5 - 50);
			}
			else if (i % 6 == 3){
				characterLayers.get(i).setX(256 + 5);
			}
			else if (i % 6 == 4){
				characterLayers.get(i).setX(256 + 5 + 50 + 10);
			}
			else if (i % 6 == 5){
				characterLayers.get(i).setX(256 + 5 + 100 + 20);
			}

			if (i / 6 == 0){
				characterLayers.get(i).setY(500);
			}
			else if (i / 6 == 1){
				characterLayers.get(i).setY(440);
			}
			else if (i / 6 == 2){
				characterLayers.get(i).setY(380); 	
			}
			else if (i / 6 == 3){
				characterLayers.get(i).setY(320);
			}
			else if (i / 6 == 4){
				characterLayers.get(i).setY(260);
			}
			else if (i / 6 == 5){
				characterLayers.get(i).setY(200);
			}
		}

		characters.get(0).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "a");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(1).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "b");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(2).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "c");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(3).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "d");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(4).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "e");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(5).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "f");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(6).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "g");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(7).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "h");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(8).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "i");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(9).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "j");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(10).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "k");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(11).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "l");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(12).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "m");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(13).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "n");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(14).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "o");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(15).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "p");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(16).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "q");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(17).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "r");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(18).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "s");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(19).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "t");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(20).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "u");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(21).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "v");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(22).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "w");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(23).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "x");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(24).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "y");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(25).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "z");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(26).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "0");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(27).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "1");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(28).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "2");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(29).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "3");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(30).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "4");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(31).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "5");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(32).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "6");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(33).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "7");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(34).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "8");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		characters.get(35).addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (createProfString.length() != 10 && currentProfileScreenState == CurrentProfileScreenState.CREATE_PROF){
					createProfString.replace(createProfString.length(), createProfString.length() + 1, "9");
				} else {
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	public static boolean isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String string){  
		return string != null && !string.isEmpty() && !string.trim().isEmpty();  
	}  	
	
	private void generateRandomStages() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		for (int i = 0; i < 9; i++){
			Calendar cal = Calendar.getInstance(); // creates calendar
		    cal.setTime(new Date()); // sets calendar time/date
		    cal.add(Calendar.MINUTE, 5);
		    cal.getTime(); // returns new date object, one hour in the future
			
		    jobChangeTimerProf.add(dateFormat.format(cal.getTime()));
		    profileLoader.setJobChangeTimer(jobChangeTimerProf);
			
			hasJobArray.add(true);
			hasJobArray.add(false);
			
			hasAnyJob = hasJobArray.random();
			hasJobArray.clear();
			hasJobProf.add(hasAnyJob);
			profileLoader.setHasJob(hasJobProf);

			gameMode = generator.nextInt(3);
			while (gameMode == 0){
				gameMode = generator.nextInt(3);
			}
			gameModeProf.add(gameMode);
			profileLoader.setGameMode(gameModeProf);

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
			timer = timerArray.random();
			timerArray.clear();
			timerProf.add(timer);
			profileLoader.setTimer(timerProf);

			if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
				garbageAmount = 3;
			}
			else if (junkWorldEngines.getLevel() >= 6 && junkWorldEngines.getLevel() <= 10){
				garbageAmountArray.add(3);
				garbageAmountArray.add(4);
				garbageAmountArray.add(5);
				garbageAmount = garbageAmountArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
				garbageAmountArray.add(4);
				garbageAmountArray.add(5);
				garbageAmountArray.add(6);
				garbageAmount = garbageAmountArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
				garbageAmountArray.add(5);
				garbageAmountArray.add(6);
				garbageAmountArray.add(7);
				garbageAmount = garbageAmountArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
				garbageAmountArray.add(6);
				garbageAmountArray.add(7);
				garbageAmountArray.add(8);
				garbageAmount = garbageAmountArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
				garbageAmountArray.add(7);
				garbageAmountArray.add(8);
				garbageAmountArray.add(9);
				garbageAmount = garbageAmountArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
				garbageAmountArray.add(9);
				garbageAmountArray.add(10);
				garbageAmountArray.add(11);
				garbageAmount = garbageAmountArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
				garbageAmountArray.add(11);
				garbageAmountArray.add(12);
				garbageAmountArray.add(13);
				garbageAmount = garbageAmountArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
				garbageAmountArray.add(13);
				garbageAmountArray.add(14);
				garbageAmountArray.add(15);
				garbageAmount = garbageAmountArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
				garbageAmountArray.add(15);
				garbageAmountArray.add(16);
				garbageAmountArray.add(17);
				garbageAmount = garbageAmountArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 91){
				garbageAmountArray.add(18);
				garbageAmount = garbageAmountArray.random();
			}
			garbageAmountArray.clear();
			garbageAmountProf.add(garbageAmount);
			profileLoader.setGarbageAmount(garbageAmountProf);

			if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 5){
				roundsArray.add(1);
				roundsArray.add(2);
				rounds = roundsArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 5 && junkWorldEngines.getLevel() <= 10){
				roundsArray.add(1);
				roundsArray.add(2);
				roundsArray.add(3);
				rounds = roundsArray.random();
			}
			else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 15){
				roundsArray.add(1);
				roundsArray.add(2);
				roundsArray.add(3);
				roundsArray.add(4);
				rounds = roundsArray.random();
			}
			else {
				for (int i2 = ((junkWorldEngines.getLevel() - 1) / 5) - 1; i2 < ((junkWorldEngines.getLevel() - 1) / 5) + 3; i2++){
					roundsArray.add(i2);
				}
				rounds = roundsArray.random();
			}
			roundsArray.clear();
			roundsProf.add(rounds);
			profileLoader.setRounds(roundsProf);

			for (int i2 = 0; i2 < 5; i2++){
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
			spawnTime.sort();
			for (int i4 = 0; i4 < spawnTime.size; i4++){
				spawnTimeFloat[i4] = spawnTime.get(i4);
			}
			spawnTimeProf.add(spawnTimeFloat);
			profileLoader.setSpawnTime(spawnTimeProf);
			spawnTime.clear();
			
			for (int i2 = (junkWorldEngines.getLevel() / 4) + 5; i2 < (junkWorldEngines.getLevel() / 4) + 5 + 3; i2++){
				garbageGoalArrayRandom.add(i2);
			}
			garbageGoal = garbageGoalArrayRandom.random();
			garbageGoalArrayRandom.clear();
			garbageGoalArray.add(garbageGoal);
			profileLoader.setGarbageGoal(garbageGoalArray);
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
		// TODO Auto-generated method stub

	}
}
