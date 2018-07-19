package com.springdynamics.game;

import java.util.HashMap;

import com.assetsManager.game.SpringDynamicsAssetLoader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gameExtensions.game.FontManager;
import com.gameExtensions.game.Profile;
import com.gameExtensions.game.ProfileUpdater;

public class SpringDynamics extends Game {
	public OrthographicCamera uiCam;
	public Vector3 uiTouchPos;
	public SpriteBatch uiBatch;
	
	public final String PROF_NAME = "myProf";

	public AssetManager assetM;
	public HashMap<String, AssetDescriptor<Texture>> assetID;
	public HashMap<String, AssetDescriptor<Sound>> soundID;
	public SpringDynamicsAssetLoader assetLoader;
	public FontManager fontManager;
	public Preferences preferences;
	public Json json;
	public ProfileUpdater profUpdater;
	public StringBuilder stringVal;
//	public AdTimer adTimer;
	private StretchViewport vPort;
	
	public static int loadingScreenDone = 0;

	@Override
	public void create () {
		uiCam = new OrthographicCamera();
		uiCam.setToOrtho(false, 800, 500);
		vPort = new StretchViewport(800, 500, uiCam);
		resize(800, 500);
		uiTouchPos = new Vector3();
		uiBatch = new SpriteBatch(); 

		setScreen(new LoadingScreen(this));
	}

	private void manageGameAssets() {
		assetM = new AssetManager();
		assetID = new HashMap<String, AssetDescriptor<Texture>>();
		soundID = new HashMap<String, AssetDescriptor<Sound>>();

		assetLoader = new SpringDynamicsAssetLoader(this, assetM, assetID, soundID);
		assetLoader.setGameAssets();

		fontManager = new FontManager(this);
	}

	private void createProf() {
		json = new Json();
		json.setUsePrototypes(false);
		json.setOutputType(OutputType.minimal);
		preferences = getPrefs();
		
		if (!preferences.contains(PROF_NAME)) {
			Profile prof = new Profile();
			stringVal = new StringBuilder(json.toJson(prof));
			preferences.putString(PROF_NAME, Base64Coder.encodeString(stringVal.toString()));
		} else {
			stringVal = new StringBuilder(Base64Coder.decodeString(preferences.getString(PROF_NAME)));
			filterSaved(stringVal);
		}
		preferences.flush();

		profUpdater = new ProfileUpdater(this, stringVal.toString());
//		adTimer = new AdTimer(this);
	}
	
	public static StringBuilder filterSaved(StringBuilder stringB){
		int i = stringB.indexOf("\"");
		while (i != -1) {
			stringB.delete(i, i + "\"".length());
			i = stringB.indexOf("\"");
		}
		return stringB;
	}

	public void loadOtherFunctions() {
		manageGameAssets();
		createProf();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		uiTouchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		uiCam.unproject(uiTouchPos);
		uiCam.update();
		uiBatch.setProjectionMatrix(uiCam.combined);

		super.render();
	}

	public Preferences getPrefs() {
		if (preferences == null){
			preferences = Gdx.app.getPreferences(PROF_NAME);
		}
		return preferences;
	}

	public void writeString(final String newWriteProf){
		stringVal.replace(0, stringVal.length(), newWriteProf);
		filterSaved(stringVal);
		preferences.putString(PROF_NAME, Base64Coder.encodeString(json.toJson(newWriteProf)));
		preferences.flush();
	}

	public String getLoadedProf() {
		return stringVal.toString();
	}

	@Override
	public void resize(int width, int height) {
		vPort.update(width, height);
	}

	@Override
	public void dispose() {
		loadingScreenDone = 0;
		super.dispose();
	}
}
