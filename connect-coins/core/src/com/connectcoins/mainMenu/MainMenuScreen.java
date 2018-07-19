package com.connectcoins.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.connectcoins.assets.AssetScreenSpace;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen;
import com.connectcoins.languages.LanguageManager;
import com.connectcoins.mainMenu.Challenges.ChallengesState;
import com.connectcoins.store.PurchaseSoundPlayer;
import com.connectcoins.store.StoreScreen;
import com.connectcoins.store.StoreScreen.StoreScreenState;
import com.connectcoins.ui.FrontUI;
import com.connectcoins.utils.AnimatedButton;
import com.connectcoins.utils.ButtonAssets;
import com.connectcoins.utils.ButtonUtils;
import com.connectcoins.utils.ScalableFontButton.Size;
import com.connectcoins.utils.StopWatch;

public class MainMenuScreen extends ScreenAdapter implements MainMenuScreenListener {
	public enum MainMenuScreenState {
		NORMAL, CHALLENGES, MULTIPLAYER, STORE, AWARDS, OPTIONS
	}

	private boolean test = true;

	public MainMenuScreenState mMSState;
	private Stage stage;
	private ConnectCoins game;
	private Challenges challenges;
//	private MultiplayerScreen multiPScreen;
	private Array<FrontUI> frontUIs;
	private TextureRegion title;
//	private MatchMaker mMaker;
	private StoreScreen storeScreen;
//	private AwardsScreen awardsScreen;
//	private OptionsScreen optionsScreen;
	private Object currentObject;
	private AnimatedButton multiplayerButton;
	private PurchaseSoundPlayer purchaseSoundPlayer;
	private MainMenuScreen mMScreen = this;
//	private MultiplayerMessage multiplayerMessage;
	private boolean synchroLoadAssets, runSetupThread, mainMenuSetupComplete, loadPostSetup;
	private int autoLoadMultiplayerScreen;
	private float startUpAlpha = 0;
	private StopWatch friendRequestTimeout;
	private AssetManager manager;
	private AnimatedButton quitButton, awardsButton;
	public MainMenuScreen(ConnectCoins game){
		this.game = game;
		this.stage = game.stage;
		stage.clear();
		if (game != null){
			if (game.musicPlayer != null) game.musicPlayer.stopPlayer();
			if (game.comboList != null) game.comboList.canOpenComboList(true);
			if (game.gameBG != null) game.gameBG.setFallSpeed(1);
		}
		mMSState = MainMenuScreenState.NORMAL;

		manager = game.assetLoader.manager;
		synchroLoadAssets = true;
		runSetupThread = true;
		mainMenuSetupComplete = false;
		autoLoadMultiplayerScreen = 0;
		loadPostSetup = true;
		startUpAlpha = 0;
		game.getMiscellaneousPref().getBoolean("gameRated");

		game.assetLoader.loadScreenAssets(AssetScreenSpace.MAIN_MENU_ASSETS, null, false, false);
		game.comboList.setGameScreenForTutorial(null);



		
		
		///////////////////GENERATE. MUST DELETE
		///////////////////
		///////////////////
		///////////////////
		///////////////////
		///////////////////
		///////////////////
		///////////////////
		
		
//		PuzzleGenerator pGen = new PuzzleGenerator(game);
//		pGen.startPuzzleGenerator();


//		Calendar cal = Calendar.getInstance();
//		Date date = cal.getTime();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
//		String dueDateStr = dateFormat.format(date); // renders as 11/29/2009
//		Gdx.app.log("Debug", dueDateStr);

	}

	private void loadAssets() {
		game.getGameBG().setFallSpeed(1);
		title = new TextureRegion(game.assetLoader.getTexture("title"));
		frontUIs = new Array<FrontUI>();
//		mMaker = new MatchMaker(game);
//		optionsScreen = new OptionsScreen(game, this);
		purchaseSoundPlayer = new PurchaseSoundPlayer(game);
//		awardsScreen = new AwardsScreen(game, this, purchaseSoundPlayer);
//		multiplayerMessage = new MultiplayerMessage(game, this);
		friendRequestTimeout = new StopWatch();
		friendRequestTimeout.start();
	}

	private void createButtons() {
		ButtonStyle style1 = new ButtonStyle();
		style1.up = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));
		style1.down = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));

		AnimatedButton playButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Play", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
				true) {
			@Override
			public void onAnimationEnd() {
//				game.appWarpManager.stopAllTask();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						game.gMConfig.mode = GameMode.NORMAL;
						game.setScreen(new GameScreen(game));
						mMScreen.dispose();
						System.gc();

						/**
						 * ALWAYS PUT THE DISPOSING OF OBJECTS AFTER THE DISPOSING OF NATIVES
						 */
					}
				});
			}
		};
		playButton.setBounds(190, 1040, 700, 120);
		playButton.setUserObject(MainMenuScreenState.NORMAL);
		playButton.setButtonIcons(game.buttonIconAssetManager, "play");

		AnimatedButton challengesButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Challenges", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
//				game.appWarpManager.stopAllTask();
				challenges.initAssets();
				setStageDrawings(ChallengesState.NORMAL);
				mMSState = MainMenuScreenState.CHALLENGES;
				challenges.cState = ChallengesState.NORMAL;
			}
		};
		challengesButton.setBounds(190, 910, 700, 120);
		challengesButton.setUserObject(MainMenuScreenState.NORMAL);
		challengesButton.setButtonIcons(game.buttonIconAssetManager, "challenges");

		multiplayerButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Multiplayer", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
//				game.appWarpManager.stopAllTask();
//				Gdx.app.log("Multiplayer State", "yes5");
//				Gdx.app.log("Multiplayer State", "connected: " + WarpController.getInstance().isConnected());
//				switch(Gdx.app.getType()) {
//				case Android: {
//					if (game.appWarpManager.hasInternetConnection()){
//						if (!game.appWarpManager.isSignedIn()){
//
//							Gdx.app.log("Multiplayer State", "yes1");
//							Gdx.app.log("Multiplayer State", "connected: " + WarpController.getInstance().isConnected());
//							ProceedingTask pTask = new ProceedingTask() {
//								@Override
//								public boolean proceedTrigger() {
//									return game.appWarpManager.isSignedIn();
//								}
//
//								@Override
//								public void proceed() {
//									Gdx.app.postRunnable(new Runnable() {
//										@Override
//										public void run() {
//											Gdx.app.log("Multiplayer State", "yes3");
//											extendFriendRequestTimer();
//											game.assetLoader.loadScreenAssets(AssetScreenSpace.MULTIPLAYER, AssetScreenSpace.MAIN_MENU_ASSETS, true, false);
//											multiPScreen.initMultiplayerAssets();
//											multiPScreen.getNotificationsManager().activateNewPlayerNotifier(game.appWarpManager.getDEVICE_ID(),
//													game.appWarpManager.getPlayerID(), MainMenuScreenState.NORMAL);
//										}
//									});
//
//									//									game.fpsMsg = game.appWarpManager.getPlayerID();
//								}
//
//								@Override
//								public boolean exitTrigger() {
//									return !game.appWarpManager.hasInternetConnection() ||
//											game.appWarpManager.isConnectionFailed();
//								}
//
//								@Override
//								public void reset() {
//									game.loadingCircle.stop();
//									game.appWarpManager.setDebugMsg();
//									multiplayerButton.reset(true);
//								}
//							};
//							game.loadingCircle.start();
//							game.appWarpManager.addTasks(pTask);
//							game.appWarpManager.signIn(true);
//						}
//						else {
//							Gdx.app.log("Multiplayer State", "yes2");
//							Gdx.app.log("Multiplayer State", "connected: " + WarpController.getInstance().isConnected());
//							game.loadingCircle.start();
//							Gdx.app.postRunnable(new Runnable() {
//								@Override
//								public void run() {
//									extendFriendRequestTimer();
//									game.assetLoader.loadScreenAssets(AssetScreenSpace.MULTIPLAYER, AssetScreenSpace.MAIN_MENU_ASSETS, true, false);
//									multiPScreen.initMultiplayerAssets();
//									multiPScreen.getNotificationsManager().activateNewPlayerNotifier(game.appWarpManager.getDEVICE_ID(),
//											game.appWarpManager.getPlayerID(), MainMenuScreenState.NORMAL);
//								}
//							});
//						}
//					}
//					else {
//						Gdx.app.log("Multiplayer State", "yes4");
//						Gdx.app.log("Multiplayer State", "connected: " + WarpController.getInstance().isConnected());
//						game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
//						multiplayerButton.reset(true);
//					}
//
//					//					setStageDrawings(MultiplayerState.NORMAL);
//					//					mMSState = MainMenuScreenState.MULTIPLAYER;
//				}; break;	
//				case iOS: {
//					game.loadingCircle.stop();
//					game.assetLoader.loadScreenAssets(AssetScreenSpace.MULTIPLAYER, AssetScreenSpace.MAIN_MENU_ASSETS, true, false);
//					multiPScreen.initMultiplayerAssets();
//					setStageDrawings(MultiplayerState.NORMAL);
//					mMSState = MainMenuScreenState.MULTIPLAYER;
//				}; break;	
//				case Desktop: {
//					game.loadingCircle.stop();
//					game.assetLoader.loadScreenAssets(AssetScreenSpace.MULTIPLAYER, AssetScreenSpace.MAIN_MENU_ASSETS, true, false);
//					multiPScreen.initMultiplayerAssets();
//					setStageDrawings(MultiplayerState.NORMAL);
//					mMSState = MainMenuScreenState.MULTIPLAYER;
//				}; break;	
//				default: break;	
//				}
//
//				//								setStageDrawings(MultiplayerState.NORMAL);
//				//								mMSState = MainMenuScreenState.MULTIPLAYER;
//
//				//								game.gMConfig.mode = GameMode.MULTIPLAYER;
//				//								game.setScreen(new GameScreen(game));
//				//				dispose();
			}
		};
		multiplayerButton.setBounds(190, 780, 700, 120);
		multiplayerButton.setUserObject(MainMenuScreenState.NORMAL);
		multiplayerButton.setButtonIcons(game.buttonIconAssetManager, "multiplayer");

		AnimatedButton storeButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Store", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
//				game.appWarpManager.stopAllTask();
				game.assetLoader.loadScreenAssets(AssetScreenSpace.STORE, AssetScreenSpace.MAIN_MENU_ASSETS, true, false);
				storeScreen.initStoreAssets();
				storeScreen.initCategoryButtons();
				storeScreen.updateTotalCollectedCC();
				storeScreen.setRenderOuterLayout(true);
				setStageDrawings(StoreScreenState.NORMAL);
				mMSState = MainMenuScreenState.STORE;
			}
		};
		storeButton.setBounds(190, 650, 700, 120);
		storeButton.setUserObject(MainMenuScreenState.NORMAL);
		storeButton.setButtonIcons(game.buttonIconAssetManager, "store");

		awardsButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Awards", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
//				game.miscellaneous.awardScreenOpened();
//				game.appWarpManager.stopAllTask();
//				game.assetLoader.loadScreenAssets(AssetScreenSpace.AWARDS, AssetScreenSpace.MAIN_MENU_ASSETS, true, false);
//				awardsScreen.initAssets();
//				awardsScreen.updateNextCCTimer();
//				setStageDrawings(AwardScreenState.NORMAL);
//				mMSState = MainMenuScreenState.AWARDS;
			}
		};
		awardsButton.setBounds(190, 520, 700, 120);
		awardsButton.setUserObject(MainMenuScreenState.NORMAL);
		awardsButton.setButtonIcons(game.buttonIconAssetManager, "awards");

		AnimatedButton comboListButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Combo List", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
				true) {
			@Override
			public void onAnimationEnd() {
//				game.appWarpManager.stopAllTask();
				game.comboList.showComboList(MainMenuScreenState.NORMAL, MainMenuScreenState.NORMAL, true);
			}
		};
		comboListButton.setBounds(190, 390, 700, 120);
		comboListButton.setUserObject(MainMenuScreenState.NORMAL);
		comboListButton.setButtonIcons(game.buttonIconAssetManager, "comboList");

		AnimatedButton optionsButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Options", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				//				optionsScreen.updateFaceGroup();
//				game.appWarpManager.stopAllTask();
//				game.assetLoader.loadScreenAssets(AssetScreenSpace.OPTIONS, AssetScreenSpace.MAIN_MENU_ASSETS, true, false);
//				optionsScreen.initOptions();
//				optionsScreen.updateOptionsCheckButtons();
//				setStageDrawings(OptionsScreenState.NORMAL);
//				mMSState = MainMenuScreenState.OPTIONS;
			}
		};
		optionsButton.setBounds(190, 260, 700, 120);
		optionsButton.setUserObject(MainMenuScreenState.NORMAL);
		optionsButton.setButtonIcons(game.buttonIconAssetManager, "options");

		ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable(new TextureRegion(ButtonAssets.averageButton.get(0)));
		style2.down = new TextureRegionDrawable(new TextureRegion(ButtonAssets.averageButton.get(0)));

		AnimatedButton tutorialButton = new AnimatedButton(ButtonAssets.averageButton, style2, game.fManager.largeFont, 1.1f, 1.1f,
				"Tutorial", Color.valueOf("281500"), Size.AVERAGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
				true) {
			@Override
			public void onAnimationEnd() {
//				game.appWarpManager.stopAllTask();
				Gdx.app.postRunnable(new Runnable() {  
					@Override  
					public void run () {
						GameScreen gScreen;
						game.gMConfig.mode = GameMode.TUTORIAL;
						game.setScreen(gScreen = new GameScreen(game));
						game.comboList.setGameScreenForTutorial(gScreen);
						mMScreen.dispose();
						System.gc();
					}  
				});
			}
		};
		tutorialButton.setBounds(20, 20, 500, 100);
		tutorialButton.setUserObject(MainMenuScreenState.NORMAL);
		tutorialButton.setButtonIcons(game.buttonIconAssetManager, "howToPlay");

		quitButton = new AnimatedButton(ButtonAssets.averageButton, style2, game.fManager.largeFont, 1.1f, 1.1f,
				"Quit", Color.valueOf("281500"), Size.AVERAGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, false,
				true) {
			@Override
			public void onAnimationEnd() {
//				game.appWarpManager.stopAllTask();
				Gdx.app.exit();
			}
		};
		quitButton.setBounds(560, 20, 500, 100);
		quitButton.setUserObject(MainMenuScreenState.NORMAL);
		quitButton.setButtonIcons(game.buttonIconAssetManager, "quit");


		stage.addActor(playButton);
		stage.addActor(challengesButton);
		stage.addActor(multiplayerButton);
		stage.addActor(storeButton);
		stage.addActor(awardsButton);
		stage.addActor(optionsButton);
		stage.addActor(comboListButton);
		stage.addActor(tutorialButton);
		stage.addActor(quitButton);
	}

	@Override
	public void goToAwardsScreen() {
		ButtonUtils.performClick(awardsButton);
	}

	@Override
	public void onFreeCCDeclined() {
//		game.miscellaneous.freeCCShowed();
	}

	public void resetBackToMainMenu(){
		multiplayerButton.reset(true);
	}

	public void proceedToMultiPlayerScreen(){
//		setStageDrawings(MultiplayerState.NORMAL);
		mMSState = MainMenuScreenState.MULTIPLAYER;
		//		game.fpsMsg = multiPScreen.getCurrentLoginPlayer();
	}

	private void createScreen() {
		challenges = new Challenges(game, this);
//		multiPScreen = new MultiplayerScreen(game, this, purchaseSoundPlayer);
		storeScreen = new StoreScreen(game, this, purchaseSoundPlayer);
	}

	public void setStageDrawings(Object object){
		this.currentObject = object;
		if (stage == null) stage = game.stage;
		for (int i = 0; i < stage.getActors().size; i++){
			Actor actor = stage.getActors().get(i);
			if (actor.getUserObject() == null || actor.getUserObject() != object){
				try {
					AnimatedButton animButton = (AnimatedButton)actor;
					animButton.reset();
					actor.setVisible(false);
					actor.setTouchable(Touchable.disabled);
				}
				catch (ClassCastException e){
					actor.setVisible(false);
					actor.setTouchable(Touchable.disabled);
				}

			}
		
		}
		for (int i = 0; i < stage.getActors().size; i++){
			Actor actor = stage.getActors().get(i);
			if (actor.getUserObject() != null && actor.getUserObject() == object){
				actor.setVisible(true);
				actor.setTouchable(Touchable.enabled);
			}
		}
	}

	@Override
	public void render(float delta) {
		if (synchroLoadAssets){
			if (manager.update()){
				synchroLoadAssets = false;
			}
		}
		else setupGameLoadingAssets();

		if (mainMenuSetupComplete){
			if (startUpAlpha < 1){
				startUpAlpha += 2f * Gdx.graphics.getDeltaTime();
				startUpAlpha = MathUtils.clamp(startUpAlpha, 0, 1);


				//Try to put here something like going directly to multiplayer screen.
			}
			else {
				if (loadPostSetup){
					loadPostSetup = false;

				}
			}

			if (loadPostSetup){
				Color bColor = game.batch.getColor();
				game.batch.setColor(bColor.r, bColor.g, bColor.b, startUpAlpha);

				//Temporary
				//

				updateScene();
				drawBatches();
				setListeners();
				drawFrontRenders();
				stage.act();

//				multiplayerMessage.render(game.batch);
				test();
				
				if (autoLoadMultiplayerScreen == 1){
					autoLoadMultiplayerScreen = 0;

					extendFriendRequestTimer();
					game.assetLoader.loadScreenAssets(AssetScreenSpace.MULTIPLAYER, AssetScreenSpace.MAIN_MENU_ASSETS, true, false);
//					multiPScreen.initMultiplayerAssets();
					extendFriendRequestTimer();
//					multiPScreen.setCurrentUserRank(false);
					mMScreen.proceedToMultiPlayerScreen();
					mMScreen.checkForFriendRequests();
					game.loadingCircle.stop();
				}

				if (autoLoadMultiplayerScreen == 2){
					autoLoadMultiplayerScreen = 0;
					
//					game.gMConfig.currentPlayer = null;
//					game.appWarpManager.signOut(false);
//					WarpController.getInstance().handleLeave();
//					WarpController.getInstance().disconnect();
					mMScreen.setStageDrawings(MainMenuScreenState.NORMAL);
					mMScreen.mMSState = MainMenuScreenState.NORMAL;
				}
			}
			else {
				//Temporary
				//

				updateScene();
				drawBatches();
				setListeners();
				drawFrontRenders();
				stage.act();

//				multiplayerMessage.render(game.batch);
				test();
			}
		}
	}

	private void setupGameLoadingAssets() {
		if (runSetupThread && !synchroLoadAssets){
			runSetupThread = false;
			
			Timer t = new Timer();
			t.scheduleTask(new Task() {
				@Override
				public void run() {
					loadAssets();
					sleep();
					createButtons();
					sleep();
					//					initGenerator();
					sleep();
					createScreen();
					sleep();
					game.musicPlayer.stopPlayer();
					Gdx.input.setInputProcessor(stage);
					setStageDrawings(MainMenuScreenState.NORMAL);

					mainMenuSetupComplete = true;
				}
			}, 0);
			t.start();
			
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					loadAssets();
//					sleep();
//					createButtons();
//					sleep();
//					//					initGenerator();
//					sleep();
//					createScreen();
//					sleep();
//					game.musicPlayer.stopPlayer();
//					Gdx.input.setInputProcessor(stage);
//					setStageDrawings(MainMenuScreenState.NORMAL);
//
//					mainMenuSetupComplete = true;
//				}
//			}).start();

		}
	}

	private void sleep() {
		Timer t = new Timer();
		t.scheduleTask(new Task() {
			@Override
			public void run() {
				
			}
		}, .2f);
		t.start();
//		try {
//			Thread.sleep(200);
//		} catch (InterruptedException ex){}
	}

	private void test() {
		if (test){
			test = false;

//			game.batch.begin();
//			notEnoughCC.render(game.batch);
//			game.batch.end();
			
//			game.batch.begin();
//			if (pMessageChat != null) pMessageChat.render(game.batch);
//			game.batch.end();
			//			game.multiplayerMessage.addMessageNotif(MessageNotifType.SENT_MSG, "Hello", "asda");
			//			game.multiplayerMessage.addMessageNotif(MessageNotifType.SENT_MSG, "haahaha", "afawgagaag");
			//			game.multiplayerMessage.addMessageNotif(MessageNotifType.SENT_MSG, "whrgs", "rddsdg");
			//			game.multiplayerMessage.addMessageNotif(MessageNotifType.SENT_MSG, "sgsge", "dsgse");
			//			multiPScreen.getNotificationsManager().activateNotifications(true, 99, true, "FACSCAF", getCurrentObject());
		}
	}

	private void updateScene() {
//		game.appWarpManager.activateTasks();
	}

	private void drawBatches() {
		game.batch.begin();
		//		game.gameBG.render(game.batch);
		switch (mMSState){
		case NORMAL: {
			game.batch.draw(title, 0, 1250, 1080, 500);
			deployFriendRequestTask();

//			if (!game.comboList.isShowComboList()) game.adManager.showBannerAds(true);
//			else game.adManager.showBannerAds(false);

//			if (game.miscellaneous.showFreeCCDialog(this)){
//
//			}
//			else {
////				if (!isGameRated) game.appWarpManager.showRateDialog();
//			}

//			if (mMScreen.getMultiplayerMessage().hasNoMessageNotifs()){
//				if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
//					ButtonUtils.performClick(quitButton);
//				}
//			}
		}; break;
		case CHALLENGES: {
			challenges.updateScene();
			challenges.render(game.batch);

//			if (!game.comboList.isShowComboList()) game.adManager.showBannerAds(true);
//			else game.adManager.showBannerAds(false);
		}; break;
		case MULTIPLAYER: {
//			multiPScreen.render(game.batch);

//			game.adManager.showBannerAds(false);
		}; break;
		case STORE: {
			storeScreen.render(game.batch);

//			game.adManager.showBannerAds(false);
		}; break;
		case AWARDS: {
//			awardsScreen.render(game.batch);
		}; break;
		case OPTIONS: {
			Color bColor = game.batch.getColor();
			Color color = bColor.set(bColor.r, bColor.g, bColor.b, 1);
			game.batch.setColor(color);
//			optionsScreen.render(game.batch);
			game.batch.setColor(bColor);

//			game.adManager.showBannerAds(false);
		}; break;
		default: break;
		}
		if (game.comboList.isShowComboList()) game.comboList.render(game.batch);
		game.batch.end();
	}

	public void extendFriendRequestTimer(){
		friendRequestTimeout.stop();
		friendRequestTimeout.start();
	}

	public void deployFriendRequestTask(){
//		if (Gdx.app.getType() != ApplicationType.Desktop && game.appWarpManager.hasInternetConnection() 
//				&& game.appWarpManager.isSignedIn() && game.gMConfig.currentPlayer != null){
//			if (!checkIfAfk()){
//				if (friendRequestTimeout.timeElapsedInSeconds() >= 5){
//					checkForFriendRequests();
//					
////					checkForBuddyMessages();
////					TODO
////					TEMPORARILY DISABLED
//				}
//			}
//			else {
//				extendFriendRequestTimer();
//			}
//		}
	}
	
	public void checkForFriendRequests(){
		friendRequestTimeout.stop();
		friendRequestTimeout.start();
//		game.appWarpManager.getAppwarpActivity().getFriendRequests(this, game.gMConfig.currentPlayer.getPlayerD1().getUsername());
	}

//	public void setFriendRequests(Array<FriendRequest> friendRequests){
//		for (int i = 0; i < friendRequests.size; i++){
////			FriendRequest friendRequest = friendRequests.get(i);
////			if (!multiplayerMessage.hasFriendInvite(friendRequest)){
////				multiplayerMessage.addMessageNotif(MessageNotifType.FRIEND_REQ, "You have a friend request from " + friendRequest.ign, 
////						"Click ok to accept", friendRequest);
////			}
//		}
//	}

	public void manageAllMessages(String owner, String realmessage){
//		try {
//			System.out.println("Received message: " + realmessage);
//			String message;
//			try {
//				message = Base64Coder.decodeString(realmessage);
//			}
//			catch (IllegalArgumentException e){
//				e.printStackTrace();
//				message = realmessage;
//			}
//			JSONObject json = new JSONObject(message);
//			switch (json.getInt("tpc")){
//				case AppCloudConstants.T_GAME_INVITE: {
//					long totalCC = game.pUpdater.getTotalCC();
//					//TODO CC
//					if (totalCC >= 0 && !multiplayerMessage.hasGameInviteMsg(message)){
//						String opponentName = json.getString("ign");
//						String gameTypeStr = null;
//						int gameType = json.getInt("type");
//						if (gameType == AppCloudConstants.NORMAL) gameTypeStr = "N";
//						else if (gameType == AppCloudConstants.BOOST) gameTypeStr = "B";
//						multiplayerMessage.addMessageNotif(MessageNotifType.GAME_INV, opponentName + " has challenged you!\nGame: " +
//								gameTypeStr + ", Fee: CC3000", "My CC: " + totalCC, message);
//					}
//				}; break;
//				case AppCloudConstants.T_NORMAL_MSG: {
//					multiPScreen.getPlayersScreen().flushMessage(json.getString("username"), json.getString("ign"), json.getString("msg"));
//				}; break;
//				default: break;
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	}


	private void setListeners() {
		stage.draw();
	}

	private void drawFrontRenders(){
		game.batch.begin();
		for (FrontUI frontUI : frontUIs) frontUI.renderFrontUI(game.batch);
		//		if (mMaker.isRenderMatchMaker()) mMaker.render(game.batch);
		if (storeScreen.isRenderOuterLayout()) storeScreen.renderOuterLayout(game.batch);
//		if (mMSState == MainMenuScreenState.AWARDS) awardsScreen.renderOuter(game.batch);
		//		mMaker.displayMatchInvitation(game.batch);
//		multiPScreen.getNotificationsManager().renderNotifications(game.batch);
		game.batch.end();
	}

	@Override
	public void dispose() {
		test = false;
		mMSState = null;
		stage.clear();
		challenges.dispose();
		challenges = null;
//		multiPScreen.dispose();
//		multiPScreen = null;
		frontUIs.clear();
		frontUIs = null;
		quitButton = null;
		awardsButton.dispose();
		awardsButton = null;
		//		title.getTexture().dispose();
		title = null;
//		mMaker.dispose();
//		mMaker = null;
		storeScreen.dispose();
		storeScreen = null;
//		awardsScreen.dispose();
//		awardsScreen = null;
//		optionsScreen.dispose();
//		optionsScreen = null;
		currentObject = null;
		multiplayerButton.dispose();
		multiplayerButton = null;
		purchaseSoundPlayer.dispose();
		purchaseSoundPlayer = null;
//		multiplayerMessage.dispose();
//		multiplayerMessage = null;
		autoLoadMultiplayerScreen = 0;
	}

	public void addFrontUI(FrontUI frontUI){
		frontUIs.add(frontUI);
	}

//	public MultiplayerMessage getMultiplayerMessage() {
//		return multiplayerMessage;
//	}

	public void deleteFrontUI(String id){
		for (FrontUI frontUI : frontUIs){
			if (frontUI.uiID.equals(id)) frontUIs.removeValue(frontUI, true);
		}
	}

//	public MultiplayerScreen getMultiPScreen() {
//		return multiPScreen;
//	}
	
	public void autoLoadMultiplayerScreen(){
		autoLoadMultiplayerScreen = 1;
	}
	
	public void autoSignOutMainMenuScreen(){
		autoLoadMultiplayerScreen = 2;
	}

//	public MatchMaker getmMaker() {
//		return mMaker;
//	}

	public Object getCurrentObject() {
		return currentObject;
	}

	public PurchaseSoundPlayer getPurchaseSoundPlayer() {
		return purchaseSoundPlayer;
	}
}
