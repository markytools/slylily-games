package com.polyngons.game;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.gameTools.game.CustomButtonLayout;
import com.gameTools.game.ShadedBitmapFont;
import com.polyNGonsAssetPack.game.GameSoundManager;
import com.polyNGonsAssetPack.game.PolyNGonsAssetLoader;
import com.polyNGonsFunctions.game.IActivityRequestHandler;
import com.polyNGonsFunctions.game.PolyNGonsAdManager;
import com.polyNGonsFunctions.game.PolyNGonsPurchaser;
import com.polyNGonsFunctions.game.ProfileUpdater;
import com.polyNGonsFunctions.game.PuzzleUnlocker;
import com.polyNgonConstants.game.PolyNGonsGameSelection;
import com.polyNgonConstants.game.ProfileState;
import com.polyNgonConstants.game.SelectedChallenge;

public class PolyNGons extends Game {
	public SpriteBatch batch;
	public OrthographicCamera cam;
	public Vector3 touchPos;
	public TextureRegion splashImage;
	public ShaderProgram fontShader, defaultShader;

	public float averageDeltaTime;

	public AssetManager assetM;
	public CustomButtonLayout bLayout;
	public PolyNGonsAssetLoader assetLoader;
	public HashMap<String, AssetDescriptor<Texture>> assetID;
	public HashMap<String, AssetDescriptor<Sound>> soundID;
	public Array<Button> clickableButton;
	public PolyNGonsGameSelection gSelection;
	public PuzzleUnlocker pUnlocker;
	public SelectedChallenge pCType;
	public GameSoundManager gSoundManager;

	public PolyNGonsAdManager pAdManager;
	public PolyNGonsPurchaser pNGonsPurchaser;
	public IActivityRequestHandler requestAd;

	public Json json;
	private ShadedBitmapFont font;

	public ProfileUpdater pUpdater;
	public int diffSelected, pNumSelected;

	private Preferences user;
	private final String userProf = "save";
	private StringBuilder loadedProf;
	private LoadingScreen lScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 800, 500);
		touchPos = new Vector3();

		assetM = new AssetManager();
		assetID = new HashMap<String, AssetDescriptor<Texture>>();
		soundID = new HashMap<String, AssetDescriptor<Sound>>();
		defaultShader = SpriteBatch.createDefaultShader();

		createSplash();
		clickableButton = new Array<Button>();
		gSelection = PolyNGonsGameSelection.NONE;

		getUser();

		createJson();
		temporaryMethod();
		pUnlocker = new PuzzleUnlocker(this);
		pUpdater = new ProfileUpdater(this, json);
		assetLoader = new PolyNGonsAssetLoader(this, assetID, soundID, assetM);
		assetLoader.setAssetDesc();
		bLayout = new CustomButtonLayout(this);
		lScreen = new LoadingScreen(this);
		gSoundManager = new GameSoundManager(this);
		pAdManager = new PolyNGonsAdManager(this);
//		pNGonsPurchaser = new PolyNGonsPurchaser(this);

		this.setScreen(new SplashScreen(this));
	}

	public PolyNGons(IActivityRequestHandler requestAd) {
		this.requestAd = requestAd;
	}

	private void createSplash(){
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true;
		param.magFilter = TextureFilter.MipMapLinearNearest;
		param.minFilter = TextureFilter.MipMapLinearNearest;

		assetID.put("splash", new AssetDescriptor<Texture>
		(Gdx.files.internal("bgAssets/splash.png"), Texture.class, param));

		assetM.load(assetID.get("splash"));
		assetM.finishLoading();
		splashImage = new TextureRegion(assetM.get(assetID.get("splash")));
	}

	private void temporaryMethod() {
		Gdx.input.setCatchBackKey(false);
	}

	private void createJson() {
		json = new Json();
		json.setUsePrototypes(false);
		json.setOutputType(OutputType.minimal);

		if (!user.contains(userProf)) {
			ProfileState newProf = new ProfileState();
			loadedProf = new StringBuilder(json.toJson(newProf));
			user.putString(userProf, Base64Coder.encodeString(loadedProf.toString()));
		}
		else {
			loadedProf = new StringBuilder(Base64Coder.decodeString(user.getString(userProf)));
			filterSaved(loadedProf);
		}
		user.flush();
	}

	public static StringBuilder filterSaved(StringBuilder stringB){
		int i = stringB.indexOf("\"");
		while (i != -1) {
			stringB.delete(i, i + "\"".length());
			i = stringB.indexOf("\"");
		}
		return stringB;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(touchPos);
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		super.render();

	}

	public void renderLoadingScreen(){
		lScreen.render();
	}

	public void stopLoadingScreen(){
		lScreen.reset();
	}

	public void setTextureFilter(){
		for (String key : assetID.keySet()){
			if (assetM.isLoaded(assetID.get(key).fileName)) 
				assetM.get(assetID.get(key)).setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.MipMapLinearNearest);
		}
	}

	public void writeString(final String newWriteProf){
		loadedProf.replace(0, loadedProf.length(), newWriteProf);
		filterSaved(loadedProf);
		user.putString(userProf, Base64Coder.encodeString(json.toJson(newWriteProf)));
		user.flush();
	}

	public Preferences getUser() {
		if (user == null) user = Gdx.app.getPreferences("com.polyNGons.saves");
		return user;
	}

	public String getLoadedProf() {
		return loadedProf.toString();
	}

	public String getUserProf() {
		return userProf;
	}

	public LoadingScreen getlScreen() {
		return lScreen;
	}

	public ShadedBitmapFont getFont() {
		return font;
	}

	public void setFont(ShadedBitmapFont font) {
		this.font = font;
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void dispose() {
//		PurchaseSystem.dispose();
		//		
		//		public ProfileUpdater pUpdater;
		//		public int diffSelected, pNumSelected;
		//
		//		private Preferences user;
		//		private final String userProf = "save";
		//		private String loadedProf;
		//		private LoadingScreen lScreen;
	}
}
