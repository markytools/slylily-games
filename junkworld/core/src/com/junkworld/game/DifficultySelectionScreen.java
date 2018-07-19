package com.junkworld.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;

public class DifficultySelectionScreen implements Screen {

	private OrthographicCamera camera;
	private Batch batch;
	private Vector3 touchPos;
	private JsonValue jsonValue;
	private Stage stage;
	private AssetManager manager;
	private JunkWorldEngines junkWorldEngines;
	private boolean isLoading = false, playMusic = true;
	private Actor back, next;
	private FileHandle profileFile;
	private int gameModeInd, themeInd, diffInd;
	private SelectBox<String> gameModeBox, themeBox, difficultyBox;
	private long delayAds = TimeUtils.millis();
//	private IActivityRequestHandler myRequestHandler;
	
	private void disposeAssets(){
		batch.dispose();
		stage.dispose();
		this.dispose();
	}
	
	private void loadManager(){
		manager.load("backgrounds/firstBackground.png", Texture.class);
		manager.load("mainMenuAssets/loadingAssets/blackShader2.png", Texture.class);
		manager.load("backgrounds/blueFrame.png", Texture.class);
		manager.load("buttons/backButton.png", Texture.class);
		manager.load("buttons/nextButton.png", Texture.class);
		
		manager.load("screenLabels/mode.png", Texture.class);
		manager.load("screenLabels/gameSettings.png", Texture.class);
		manager.load("screenLabels/difficulty.png", Texture.class);
		manager.load("screenLabels/theme.png", Texture.class);
		manager.load("screenLabels/bDesc.png", Texture.class);
		manager.load("screenLabels/rDesc.png", Texture.class);
		manager.load("screenLabels/nrDesc.png", Texture.class);
	}

	private void unloadManager(){
		manager.unload("backgrounds/firstBackground.png");
		manager.unload("mainMenuAssets/loadingAssets/blackShader2.png");
		manager.unload("backgrounds/blueFrame.png");
		manager.unload("buttons/backButton.png");
		manager.unload("buttons/nextButton.png");
		manager.unload("screenLabels/mode.png");
		manager.unload("screenLabels/gameSettings.png");
		manager.unload("screenLabels/difficulty.png");
		manager.unload("screenLabels/theme.png");
		manager.unload("screenLabels/bDesc.png");
		manager.unload("screenLabels/rDesc.png");
		manager.unload("screenLabels/nrDesc.png");
	}

	@SuppressWarnings("deprecation")
	public DifficultySelectionScreen(final JunkWorld game,  final  AssetManager manager,
			 final JunkWorldEngines junkWorldEngines){
		this.manager = manager;
//		this.myRequestHandler = myRequestHandler;
		this.junkWorldEngines = junkWorldEngines;
		loadManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		batch = new SpriteBatch();
		touchPos = new Vector3(); 
		stage = new Stage();
		
		profileFile = Gdx.files.local(Gdx.files.local("pLoader.txt").readString() + ".json");
		jsonValue = new JsonReader().parse(profileFile);
		
		Array<String> gameMode = new Array<String>();
		Array<String> theme = new Array<String>();
		Array<String> difficulty = new Array<String>();
		
		gameMode.add("   Tutorial");
		if (jsonValue.getBoolean("timeAttack")) gameMode.add("   Time Attack");
		if (jsonValue.getBoolean("dumpingJob")) gameMode.add("   Dumping Job");
		if (jsonValue.getBoolean("Survival")) gameMode.add("   Survival");

		theme.add("   JunkWorld");
		if (jsonValue.getBoolean("kitchen")) theme.add("   Kitchen");
		if (jsonValue.getBoolean("basement")) theme.add("   Basement");
		if (jsonValue.getBoolean("garage")) theme.add("   Garage");
		if (jsonValue.getBoolean("backyard")) theme.add("   Backyard");
		if (jsonValue.getBoolean("sidewalk")) theme.add("   Sidewalk");
		if (jsonValue.getBoolean("factory")) theme.add("   Factory");
		if (jsonValue.getBoolean("highway")) theme.add("   Highway");
		if (jsonValue.getBoolean("park")) theme.add("   Park");
		if (jsonValue.getBoolean("sewer")) theme.add("   Sewer");
		
		difficulty.add("   1");
		if (jsonValue.getBoolean("diff2")) difficulty.add("   2");
		if (jsonValue.getBoolean("diff3")) difficulty.add("   3");
		if (jsonValue.getBoolean("diff4")) difficulty.add("   4");
		if (jsonValue.getBoolean("diff5")) difficulty.add("   5");
		if (jsonValue.getBoolean("diff6")) difficulty.add("   6");
		if (jsonValue.getBoolean("diff7")) difficulty.add("   7");
		if (jsonValue.getBoolean("diff8")) difficulty.add("   8");
		if (jsonValue.getBoolean("diff9")) difficulty.add("   9");
		if (jsonValue.getBoolean("diff10")) difficulty.add("   10");
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SHOWG.TTF"));
		BitmapFont selectBoxDefault = generator.generateFont(30);
		BitmapFont selectBoxOpen = generator.generateFont(30);
		generator.dispose();
		selectBoxDefault.setColor(.9f, 1, .9f, 1);
		selectBoxOpen.setColor(.8f, 1, .8f, 1);
		
		Skin skin = new Skin();
		skin.add("bg", new Texture(Gdx.files.internal("backgrounds/selectBox.png")));
		skin.add("selection", new Texture(Gdx.files.internal("backgrounds/whiteShader.png")));
		skin.add("scroll", new Texture(Gdx.files.internal("mainMenuAssets/loadingAssets/blackShader.png")));
		
		ListStyle listStyle = new ListStyle();
		listStyle.font = selectBoxOpen;
		listStyle.fontColorSelected = Color.GREEN;
		listStyle.fontColorUnselected = new Color().set(0, .6f, 0, 1);
		listStyle.selection = skin.getDrawable("selection");
		
		ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle();
		scrollPaneStyle.background = skin.getDrawable("scroll");
		
		SelectBoxStyle selectboxstyle = new SelectBoxStyle();
		selectboxstyle.font = selectBoxDefault;
		selectboxstyle.disabledFontColor = Color.GRAY;
		selectboxstyle.background = skin.getDrawable("bg");
		selectboxstyle.fontColor = selectBoxDefault.getColor();
		selectboxstyle.listStyle = listStyle;
		selectboxstyle.scrollStyle = scrollPaneStyle;
		
		gameModeBox = new SelectBox<String>(selectboxstyle);
		themeBox = new SelectBox<String>(selectboxstyle);
		difficultyBox = new SelectBox<String>(selectboxstyle);
		
		gameModeBox.setItems(gameMode);
		gameModeBox.setSelectedIndex(jsonValue.getInt("defaultGameMode"));

		themeBox.setItems(theme);
		themeBox.setSelectedIndex(jsonValue.getInt("defaultTheme"));

		difficultyBox.setItems(difficulty);
		difficultyBox.setSelectedIndex(jsonValue.getInt("defaultDiff"));
		
		back = new Actor();
		back.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				isLoading = true;
				unloadManager();
				disposeAssets();
				game.setScreen(new MainMenuScreen(game, manager, junkWorldEngines));
				super.touchDown(event, x, y, pointer, button);
			}
			
		});
		
		next = new Actor();
		next.addListener(new ActorGestureListener(){

			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Array<Float> spawnTime = new Array<Float>();
				for (int i = 0; i < 5; i++){
					spawnTime.add(new Float(0f));
				}
				Random generator = new Random();
				Array<Integer> garbageAmountArray = new Array<Integer>();
				junkWorldEngines.setGameMode(gameModeInd);
				junkWorldEngines.setCurrentTutorial(0);
				junkWorldEngines.setMaxTrashNotDumped((jsonValue.getInt("level") / 5) + 3);
				
				switch ((jsonValue.getInt("level") + 19) / 20){
				case 1: junkWorldEngines.setDumpingSpeed(0.25f); break;
				case 2: junkWorldEngines.setDumpingSpeed(0.5f); break;
				case 3: junkWorldEngines.setDumpingSpeed(0.75f); break;
				case 4: junkWorldEngines.setDumpingSpeed(1); break;
				default: break;
				}
				
				junkWorldEngines.setDumpsterCapacity((((jsonValue.getInt("level") - 1) / 5) * 1) + 10);
				
				if (themeBox.getSelected().equals("   JunkWorld")){
					junkWorldEngines.setTheme(0);
				}
				if (themeBox.getSelected().equals("   Kitchen")){
					junkWorldEngines.setTheme(1);
				}
				if (themeBox.getSelected().equals("   Basement")){
					junkWorldEngines.setTheme(2);
				}
				if (themeBox.getSelected().equals("   Garage")){
					junkWorldEngines.setTheme(3);
				}
				if (themeBox.getSelected().equals("   Backyard")){
					junkWorldEngines.setTheme(4);
				}
				if (themeBox.getSelected().equals("   Sidewalk")){
					junkWorldEngines.setTheme(5);
				}
				if (themeBox.getSelected().equals("   Factory")){
					junkWorldEngines.setTheme(6);
				}
				if (themeBox.getSelected().equals("   Highway")){
					junkWorldEngines.setTheme(7);
				}
				if (themeBox.getSelected().equals("   Park")){
					junkWorldEngines.setTheme(8);
				}
				if (themeBox.getSelected().equals("   Sewer")){
					junkWorldEngines.setTheme(9);
				}
				
				if (gameModeBox.getSelected().toString().equals("   Time Attack")){
					junkWorldEngines.setCurrentDifficulty(difficultyBox.getSelectedIndex() + 1);
					if (difficultyBox.getSelected().toString().equals("   1")){
						junkWorldEngines.setGameTimer(60);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 0));
						garbageAmountArray.add(5);
						garbageAmountArray.add(6);
						garbageAmountArray.add(7);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < 4f) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   2")){
						junkWorldEngines.setGameTimer(60);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 1));
						garbageAmountArray.add(6);
						garbageAmountArray.add(7);
						garbageAmountArray.add(8);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   3")){
						junkWorldEngines.setGameTimer(65);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 2));
						garbageAmountArray.add(7);
						garbageAmountArray.add(8);
						garbageAmountArray.add(9);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 2))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   4")){
						junkWorldEngines.setGameTimer(65);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 3));
						garbageAmountArray.add(8);
						garbageAmountArray.add(9);
						garbageAmountArray.add(10);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 3))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   5")){
						junkWorldEngines.setGameTimer(70);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 4));
						garbageAmountArray.add(9);
						garbageAmountArray.add(10);
						garbageAmountArray.add(11);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 4))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   6")){
						junkWorldEngines.setGameTimer(70);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 5));
						garbageAmountArray.add(10);
						garbageAmountArray.add(11);
						garbageAmountArray.add(12);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 5))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   7")){
						junkWorldEngines.setGameTimer(75);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 6));
						garbageAmountArray.add(11);
						garbageAmountArray.add(12);
						garbageAmountArray.add(13);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 6))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   8")){
						junkWorldEngines.setGameTimer(80);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 7));
						garbageAmountArray.add(12);
						garbageAmountArray.add(13);
						garbageAmountArray.add(14);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 7))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   9")){
						junkWorldEngines.setGameTimer(85);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 8));
						garbageAmountArray.add(13);
						garbageAmountArray.add(14);
						garbageAmountArray.add(15);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 8))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   10")){
						junkWorldEngines.setGameTimer(90);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 9));
						garbageAmountArray.add(14);
						garbageAmountArray.add(15);
						garbageAmountArray.add(16);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 9))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
				}
				else if (gameModeBox.getSelected().toString().equals("   Dumping Job")){
					junkWorldEngines.setCurrentDifficulty(difficultyBox.getSelectedIndex() + 1);
					if (difficultyBox.getSelected().toString().equals("   1")){
						junkWorldEngines.setGarbageGoal(8);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 0));
						garbageAmountArray.add(5);
						garbageAmountArray.add(6);
						garbageAmountArray.add(7);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < 4f) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   2")){
						junkWorldEngines.setGarbageGoal(10);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 1));
						garbageAmountArray.add(6);
						garbageAmountArray.add(7);
						garbageAmountArray.add(8);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   3")){
						junkWorldEngines.setGarbageGoal(12);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 2));
						garbageAmountArray.add(7);
						garbageAmountArray.add(8);
						garbageAmountArray.add(9);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 2))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   4")){
						junkWorldEngines.setGarbageGoal(14);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 3));
						garbageAmountArray.add(8);
						garbageAmountArray.add(9);
						garbageAmountArray.add(10);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 3))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   5")){
						junkWorldEngines.setGarbageGoal(16);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 4));
						garbageAmountArray.add(9);
						garbageAmountArray.add(10);
						garbageAmountArray.add(11);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 4))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   6")){
						junkWorldEngines.setGarbageGoal(18);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 5));
						garbageAmountArray.add(10);
						garbageAmountArray.add(11);
						garbageAmountArray.add(12);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 5))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   7")){
						junkWorldEngines.setGarbageGoal(20);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 6));
						garbageAmountArray.add(11);
						garbageAmountArray.add(12);
						garbageAmountArray.add(13);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 6))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   8")){
						junkWorldEngines.setGarbageGoal(22);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 7));
						garbageAmountArray.add(12);
						garbageAmountArray.add(13);
						garbageAmountArray.add(14);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 7))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   9")){
						junkWorldEngines.setGarbageGoal(24);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 8));
						garbageAmountArray.add(13);
						garbageAmountArray.add(14);
						garbageAmountArray.add(15);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 8))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					if (difficultyBox.getSelected().toString().equals("   10")){
						junkWorldEngines.setGarbageGoal(26);
						junkWorldEngines.setTrashAccerleration(0.05f + (0.09f * 9));
						garbageAmountArray.add(14);
						garbageAmountArray.add(15);
						garbageAmountArray.add(16);
						junkWorldEngines.setGarbageAmount(garbageAmountArray.random());
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (0.15f * 9))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
				}
				else if (gameModeBox.getSelected().toString().equals("   Survival")){
					junkWorldEngines.setCurrentDifficulty((jsonValue.getInt("level") + 9)/ 10);
					junkWorldEngines.setGameTimer(0);
					junkWorldEngines.setTrashAccerleration(0.05f);
					if (junkWorldEngines.getLevel() >= 1 && junkWorldEngines.getLevel() <= 10){
						junkWorldEngines.setGarbageAmount(3);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 0))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					else if (junkWorldEngines.getLevel() >= 11 && junkWorldEngines.getLevel() <= 20){
						junkWorldEngines.setGarbageAmount(4);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 1))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					else if (junkWorldEngines.getLevel() >= 21 && junkWorldEngines.getLevel() <= 30){
						junkWorldEngines.setGarbageAmount(5);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 2))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					else if (junkWorldEngines.getLevel() >= 31 && junkWorldEngines.getLevel() <= 40){
						junkWorldEngines.setGarbageAmount(6);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 3))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					else if (junkWorldEngines.getLevel() >= 41 && junkWorldEngines.getLevel() <= 50){
						junkWorldEngines.setGarbageAmount(7);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 4))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					else if (junkWorldEngines.getLevel() >= 51 && junkWorldEngines.getLevel() <= 60){
						junkWorldEngines.setGarbageAmount(8);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 5))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					else if (junkWorldEngines.getLevel() >= 61 && junkWorldEngines.getLevel() <= 70){
						junkWorldEngines.setGarbageAmount(9);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 6))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					else if (junkWorldEngines.getLevel() >= 71 && junkWorldEngines.getLevel() <= 80){
						junkWorldEngines.setGarbageAmount(10);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 7))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					else if (junkWorldEngines.getLevel() >= 81 && junkWorldEngines.getLevel() <= 90){
						junkWorldEngines.setGarbageAmount(11);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 8))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
					else if (junkWorldEngines.getLevel() >= 91 && junkWorldEngines.getLevel() <= 100){
						junkWorldEngines.setGarbageAmount(12);
						for (int i3 = 0; i3 < 5; i3++){
							while (spawnTime.get(i3) < (4f - (.1f * 9))) {
								spawnTime.set(i3, generator.nextFloat() + generator.nextInt(5));
							}
						}
						junkWorldEngines.setSpawnTime(spawnTime);
					}
				}
				isLoading = true;
				unloadManager();
				disposeAssets();
				game.setScreen(new TrashCanSelectionScreen(game, manager, junkWorldEngines));
				super.touchDown(event, x, y, pointer, button);
			}
		});
		
		stage.addActor(difficultyBox);
		stage.addActor(themeBox);
		stage.addActor(gameModeBox);
		stage.addActor(back);
		stage.addActor(next);
//		game.adManager.showBannerAds(false);
		loadMusic();
	}

	@Override
	public void render(float delta) {
		if (manager.update() && !isLoading){
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
			
			gameModeInd = gameModeBox.getSelectedIndex();
			themeInd = themeBox.getSelectedIndex();
			diffInd = difficultyBox.getSelectedIndex();

			batch.begin();
			batch.draw(manager.get("backgrounds/firstBackground.png", Texture.class), 0, 0);
			batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader2.png", Texture.class), 0, 0, 512, 800);
			batch.draw(manager.get("backgrounds/blueFrame.png", Texture.class), 0, 0);
			batch.draw(manager.get("buttons/backButton.png", Texture.class), 64, 64, 100, 50);
			batch.draw(manager.get("buttons/nextButton.png", Texture.class), 350, 64, 100, 50);
			batch.draw(manager.get("screenLabels/gameSettings.png", Texture.class), 128, 650);
			batch.draw(manager.get("screenLabels/mode.png", Texture.class), 64, 600);
			batch.draw(manager.get("screenLabels/theme.png", Texture.class), 64, 550);
			batch.draw(manager.get("screenLabels/difficulty.png", Texture.class), 64, 500);
			batch.draw(manager.get("screenLabels/bDesc.png", Texture.class), 64, 360);
			batch.draw(manager.get("screenLabels/rDesc.png", Texture.class), 64, 240);
			batch.draw(manager.get("screenLabels/nrDesc.png", Texture.class), 64, 120);
			batch.end();

			back.setBounds(64, 64, 100, 50);
			next.setBounds(350, 64, 100, 50);
			gameModeBox.setBounds(64 + 130, 600, 256, 50);
			themeBox.setBounds(64 + 130, 550, 256, 50);
			difficultyBox.setBounds(64 + 130, 500, 256, 50);
			
			if (gameModeInd == 0 || gameModeInd == 3){
				themeBox.setDisabled(true);
				difficultyBox.setDisabled(true);
			} else {
				themeBox.setDisabled(false);
				difficultyBox.setDisabled(false);
			}

			jsonValue.get("defaultGameMode").set(gameModeInd, null);
			profileFile.writeString(jsonValue.toString(), false);
			jsonValue.get("defaultTheme").set(themeInd, null);
			profileFile.writeString(jsonValue.toString(), false);
			jsonValue.get("defaultDiff").set(diffInd, null);
			profileFile.writeString(jsonValue.toString(), false);

			stage.getViewport().setCamera(camera);
			stage.draw();
			stage.act();
			Gdx.input.setInputProcessor(stage);
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
