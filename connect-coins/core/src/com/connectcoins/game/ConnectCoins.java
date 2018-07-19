package com.connectcoins.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.connectcoins.assets.AssetScreenLoader;
import com.connectcoins.assets.AssetScreenSpace;
import com.connectcoins.audio.MusicPlayer;
import com.connectcoins.awards.RewardsData;
import com.connectcoins.challenge.ChallengeManager;
import com.connectcoins.functions.GameModeConfig;
import com.connectcoins.functions.ProfileUpdater;
import com.connectcoins.languages.LanguageManager;
import com.connectcoins.mainMenu.ButtonIconAssetManager;
//import com.connectcoins.multiplayerUtils.LeavePenalty;
import com.connectcoins.ui.ComboList;
import com.connectcoins.ui.GameBackground;
import com.connectcoins.ui.LoadingCircle;
import com.connectcoins.utils.ButtonAssets;
import com.connectcoins.utils.FontManager;

public class ConnectCoins extends Game {
	public static final float WORLD_WIDTH = 1080;
	public static final float WORLD_HEIGHT = 1920;

	public SpriteBatch batch;
	public OrthographicCamera cam;
	public Stage stage;
	public Vector3 touchPos;
	public ExtendViewport gameVP;

	public ComboList comboList;
	public AssetScreenLoader assetLoader;
	public FontManager fManager;
	public ButtonAssets buttonAssets;
	public GameBackground gameBG;
	public LoadingCircle loadingCircle;
	public Json json;
	public Json getJson() {
		return json;
	}

	public ProfileUpdater pUpdater;
	public ChallengeManager challManager;
	public LanguageManager langManager;
	public ButtonIconAssetManager buttonIconAssetManager;
//	public FaceAssets faceAssets;
	public MusicPlayer musicPlayer;
//	public Miscellaneous miscellaneous;
	public RewardsData rewardsData;
//	public AppWarpManager appWarpManager;
//	public AdManager adManager;
//	public LeavePenalty leavePenalty;

	public GameModeConfig gMConfig;

	private ConnectCoins game = this;
	private String userProf = "data";
	private StringBuilder loadedProf;
//	private AdInterface adInterface;
	private static Preferences user, playerMessages, miscellaneousPref;

	//	Temporary Members
	public String fpsMsg;
	public static int opponentDisconnected = 0;
	public static int loadingScreenDone = 0;
	public static GlyphLayout glyphLayout = new GlyphLayout();

	public ConnectCoins() {
//		appWarpManager = new AppWarpManager(this, serviceActivity, appwarpActivity);
//		this.adInterface = adInterface;
	}

	@Override
	public void create (){
		//		testMethods();

//				gMConfig.mode = GameMode.NORMAL;
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1080, 1920);
		touchPos = new Vector3();

		gameVP = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, cam);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		user = getUser();
		json = new Json();
		json.setUsePrototypes(false);
		json.setOutputType(OutputType.minimal);

		if (!user.contains(userProf)) {
//			ProfileState newProf = new ProfileState();
//			loadedProf = new StringBuilder(json.toJson(newProf));
			user.putString(userProf, Base64Coder.encodeString(loadedProf.toString()));
		}
		else {
			loadedProf = new StringBuilder(Base64Coder.decodeString(user.getString(userProf)));
			filterSaved(loadedProf);
		}
		user.flush();

		pUpdater = new ProfileUpdater(this);

		assetLoader = new AssetScreenLoader(this);
		assetLoader.loadScreenAssets(AssetScreenSpace.SPLASHSCREEN, null, true, false);

		langManager = new LanguageManager(this);
		fManager = new FontManager(langManager);
//		adManager = new AdManager(this, pUpdater.noAds());
//		adManager.setAdInterface(adInterface);


//		FileHandle imageFile = Gdx.files.local("6gsdg73jsg.png");
//		if (!imageFile.file().exists()){
//			FileHandle from = Gdx.files.internal("utils/shareImg.png");
//			from.copyTo(imageFile);
//		}

		Gdx.app.postRunnable(new Runnable() {  
			@Override  
			public void run () {  
				setScreen(new LoadingScreen(game, assetLoader.cRFileResolver));
			}  
		});
	}

	private void initGameSetups() {
		stage = new Stage(gameVP, batch){

			@Override
			public void clear() {
				super.clear();
				comboList.initComboList();
			}
		};
	}

	public void loadOtherFunctions(){
		initGameSetups();
		initAssets();
		initFunctions();
	}

	private void initAssets() {
		assetLoader.loadScreenAssets(AssetScreenSpace.ALL, null, true, true);

		gameBG = new GameBackground(this);
		musicPlayer = new MusicPlayer(this);
		buttonAssets = new ButtonAssets(this);
//		faceAssets = new FaceAssets(game);
		loadingCircle = new LoadingCircle(this);
	}
	
	private void initFunctions() {
		fManager.setup();
		gMConfig = new GameModeConfig(this);
		challManager = new ChallengeManager(this);
		langManager.loadLanguagePack(pUpdater.getCurrentLanguage());
		buttonIconAssetManager = new ButtonIconAssetManager(this);
		comboList = new ComboList(this);
		rewardsData = new RewardsData();
//		leavePenalty = new LeavePenalty(this);
//		miscellaneous = new Miscellaneous(this, pUpdater.vibrationOn());
		if (pUpdater.isBGAnimationOn()) gameBG.turnOnFallingCoinsBGAnim(true);
		else gameBG.turnOnFallingCoinsBGAnim(false);
		Gdx.input.setCatchBackKey(true);
		GameScreen.initialCDataCount = 0;
		GameScreen.readyReceived = false;
		setApplicationType();
	}

	private void setApplicationType() {
//		switch (Gdx.app.getType()) {
//		case Android: appWarpManager.setDEVICE_ID("ANDRD_"); break;
//		case Desktop: appWarpManager.setDEVICE_ID("DSKTP_"); break;
//		case iOS: appWarpManager.setDEVICE_ID("IOS_"); break;
//		default: break;
//		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(204 / 255f, 161 / 255f, 110 / 255f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(touchPos);
		cam.update();
		cam.position.x = 540;
		batch.setProjectionMatrix(cam.combined);

		if (loadingScreenDone == 0){
			super.render();
		}
		else if (loadingScreenDone == 1){
			gameBG.resetOnceGameBG();
			gameBG.drawScreenBackground(batch);
			musicPlayer.playMusic();

			super.render();

			batch.begin();
			loadingCircle.render(batch);
			renderDebugFPS(false);
			batch.end();
		}
	}

	public void renderDebugFPS(boolean render){
		if (render){
			if (fpsMsg == null) fManager.drawDisplayFont(batch, Color.BLACK, 1, 1, String.valueOf(Gdx.graphics.getFramesPerSecond()),
					200, 600, 100, Align.center);
			else fManager.drawDisplayFont(batch, Color.WHITE, 1, 1, fpsMsg,
					200, 600, 100, Align.center);
		}
	}

	@Override
	public void resize(int width, int height) {
		gameVP.update(width, height);
	}

	public static StringBuilder filterSaved(StringBuilder stringB){
		int i = stringB.indexOf("\"");
		while (i != -1) {
			stringB.delete(i, i + "\"".length());
			i = stringB.indexOf("\"");
		}
		return stringB;
	}

	public String getLoadedProf() {
		return loadedProf.toString();
	}

	public void writeString(final String newWriteProf){
		loadedProf.replace(0, loadedProf.length(), newWriteProf);
		filterSaved(loadedProf);
		user.putString(userProf, Base64Coder.encodeString(json.toJson(newWriteProf)));
		user.flush();
	}

	public Preferences getUser() {
		if (user == null) user = Gdx.app.getPreferences("com.connectcoins.data");
		return user;
	}

	public Preferences getMiscellaneousPref(){
		if (miscellaneousPref == null) miscellaneousPref = Gdx.app.getPreferences("com.connectcoins.miscellaneous");
		return miscellaneousPref;
	}
	
	public static Preferences getPlayerMessages(){
		if (playerMessages == null) playerMessages = Gdx.app.getPreferences("com.connectcoins.playerMessages");
		return playerMessages;
	}

	public static void setStageActorsVisible(Stage stage, Object object){
		for (Actor actor : stage.getActors()){
			if (actor.getUserObject() != null){
				if (actor.getUserObject().equals(object)){
					actor.setVisible(true);
					actor.setTouchable(Touchable.enabled);
				}
				else {
					actor.setVisible(false);
					actor.setTouchable(Touchable.disabled);
				}
			}
		}
	}

	public static void setStageTouchableActors(Stage stage, Object object){
		for (Actor actor : stage.getActors()){
			if (actor.getUserObject().equals(object)){
				actor.setVisible(true);
				actor.setTouchable(Touchable.enabled);
			}
			else actor.setTouchable(Touchable.disabled);
		}
	}

	public static void setStageTouchableActors(Stage stage, Enum<?> object){
		for (Actor actor : stage.getActors()){
			if (actor.getUserObject() == object){
				actor.setVisible(true);
				actor.setTouchable(Touchable.enabled);
			}
			else actor.setTouchable(Touchable.disabled);
		}
	}

	public static void setStageActorsVisible(Stage stage, Object objectToMatch, Object objectToHide){
		for (Actor actor : stage.getActors()){
			if (actor.getUserObject() != null){
				if (actor.getUserObject().equals(objectToMatch)){
					actor.setVisible(true);
					actor.setTouchable(Touchable.enabled);
				}
				else {
					if (actor.getUserObject().equals(objectToHide)) actor.setVisible(false);
					actor.setTouchable(Touchable.disabled);
				}
			}
		}
	}

	public String getUserProf() {
		return userProf;
	}

	public GameBackground getGameBG() {
		return gameBG;
	}

	@Override
	public void dispose() {
//		if (appWarpManager.hasInternetConnection() && WarpController.hasInstance() && WarpController.getInstance().isConnected()){
//			appWarpManager.signOut(false);
//			WarpController.getInstance().handleLeave();
//			WarpController.getInstance().disconnect();
//		}
		loadingScreenDone = 0;
		opponentDisconnected = 0;
	}
}
