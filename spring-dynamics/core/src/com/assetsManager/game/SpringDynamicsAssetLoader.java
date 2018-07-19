package com.assetsManager.game;

import java.util.HashMap;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.springdynamics.game.SpringDynamics;

public class SpringDynamicsAssetLoader {
	private HashMap<String, AssetDescriptor<Texture>> assetID;
	private HashMap<String, AssetDescriptor<Sound>> soundsID;
	private AssetManager assetM;
	private TextureParameter param;
	
	public SpringDynamicsAssetLoader(SpringDynamics game, AssetManager assetM, HashMap<String, AssetDescriptor<Texture>> assetID,
			HashMap<String, AssetDescriptor<Sound>> soundID){
		this.assetM = assetM;
		this.assetID = assetID;
		this.soundsID = soundID;
	}
	
	public void setGameAssets(){
		param = new TextureParameter();
//		MipMaps do not work on html, only desktop and mobile
		if (Gdx.app.getType() != ApplicationType.WebGL) {
			param.genMipMaps = true;
			param.magFilter = TextureFilter.MipMapLinearLinear;
			param.minFilter = TextureFilter.MipMapLinearLinear;
		}
		
		for (int i = 1; i <= 4; i++){
			assetID.put("springBody" + i, new AssetDescriptor<Texture>
			(Gdx.files.internal("gameSprites/springBody/" + i +".png"), Texture.class, param));
		}
		
		for (int i = 1; i <= 5; i++){
			assetID.put("spring" + i, new AssetDescriptor<Texture>
			(Gdx.files.internal("gameSprites/spring/" + i +".png"), Texture.class, param));
		}
		
		for (int i = 1; i <= 4; i++){
			assetID.put("rightHand" + i, new AssetDescriptor<Texture>
			(Gdx.files.internal("gameSprites/springRightHand/" + i +".png"), Texture.class, param));
		}

		assetID.put("playAgain1", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/playAgain1.png"), Texture.class, param));
		assetID.put("playAgain2", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/playAgain2.png"), Texture.class, param));
		assetID.put("rateGame1", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/rateGame1.png"), Texture.class, param));
		assetID.put("rateGame2", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/rateGame2.png"), Texture.class, param));
		assetID.put("moreApps1", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/moreApps1.png"), Texture.class, param));
		assetID.put("moreApps2", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/moreApps2.png"), Texture.class, param));
		assetID.put("quit1", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/quit1.png"), Texture.class, param));
		assetID.put("quit2", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/quit2.png"), Texture.class, param));
		assetID.put("yes1", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/yes1.png"), Texture.class, param));
		assetID.put("yes2", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/yes2.png"), Texture.class, param));
		assetID.put("no1", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/no1.png"), Texture.class, param));
		assetID.put("no2", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/no2.png"), Texture.class, param));
		assetID.put("no2", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/inGameCompletion/no2.png"), Texture.class, param));

		assetID.put("InGameMenuBG", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/inGameMenu/InGameMenuBG.png"), Texture.class, param));
		assetID.put("gameOverTitle", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/gameOver/gameOverTitle.png"), Texture.class, param));
		assetID.put("menu1", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/inGameMenu/menu1.png"), Texture.class, param));
		assetID.put("menu2", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/inGameMenu/menu2.png"), Texture.class, param));
		assetID.put("back1", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/inGameMenu/back1.png"), Texture.class, param));
		assetID.put("back2", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/inGameMenu/back2.png"), Texture.class, param));
		assetID.put("menuTitle", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/inGameMenu/menuTitle.png"), Texture.class, param));
		
		assetID.put("jPSetter", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/jumpPowerSwitch/jPSetter.png"), Texture.class, param));
		assetID.put("jumpNumDisplay", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/jumpPowerSwitch/jumpNumDisplay.png"), Texture.class, param));
		assetID.put("jPButtons", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/jumpPowerSwitch/jPButtons.png"), Texture.class, param));
		for (int i = 1; i <= 6; i++){
			assetID.put("jpNum" + i, new AssetDescriptor<Texture>
			(Gdx.files.internal("inGameAssets/jumpPowerSwitch/jpNum/" + i + ".png"), Texture.class, param));
		}
		
		assetID.put("mMPlay1", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/play1.png"), Texture.class, param));
		assetID.put("mMPlay2", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/play2.png"), Texture.class, param));
		assetID.put("mMRateGame1", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/rateGame1.png"), Texture.class, param));
		assetID.put("mMRateGame2", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/rateGame2.png"), Texture.class, param));
		assetID.put("mMMoreApps1", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/moreApps1.png"), Texture.class, param));
		assetID.put("mMMoreApps2", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/moreApps2.png"), Texture.class, param));
		assetID.put("mMNoAds1", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/noAds1.png"), Texture.class, param));
		assetID.put("mMNoAds2", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/noAds2.png"), Texture.class, param));
		assetID.put("mMQuit1", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/quit1.png"), Texture.class, param));
		assetID.put("mMQuit2", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/quit2.png"), Texture.class, param));
		assetID.put("mMRestore1", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/restore1.png"), Texture.class, param));
		assetID.put("mMRestore2", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/restore2.png"), Texture.class, param));
		assetID.put("mMBG", new AssetDescriptor<Texture>
		(Gdx.files.internal("inGameAssets/mainMenuScreen/bg.png"), Texture.class, param));
		assetID.put("signIn1", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/googleSignIn/base.png"), Texture.class, param));
		assetID.put("signIn2", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/googleSignIn/press.png"), Texture.class, param));
		assetID.put("signOut1", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/googleSignIn/signOut1.png"), Texture.class, param));
		assetID.put("signOut2", new AssetDescriptor<Texture>
		(Gdx.files.internal("buttons/googleSignIn/signOut2.png"), Texture.class, param));
		
		assetM.setLoader(Sound.class, new SoundLoader(new InternalFileHandleResolver()));
		
		for (int i = 1; i <= 6; i++){
			soundsID.put("bSound" + i, new AssetDescriptor<Sound>
			(Gdx.files.internal("sounds/b" + i + ".ogg"), Sound.class));
		}
		soundsID.put("gOverSound", new AssetDescriptor<Sound>
		(Gdx.files.internal("sounds/gameOver.ogg"), Sound.class));
	}
	
	public void loadGameScreenAssets(){
		for (int i = 1; i <= 4; i++) assetM.load(assetID.get("springBody" + i));
		for (int i = 1; i <= 5; i++) assetM.load(assetID.get("spring" + i));
		for (int i = 1; i <= 4; i++) assetM.load(assetID.get("rightHand" + i));
		assetM.load(assetID.get("playAgain1"));
		assetM.load(assetID.get("playAgain2"));
		assetM.load(assetID.get("rateGame1"));
		assetM.load(assetID.get("rateGame2"));
		assetM.load(assetID.get("moreApps1"));
		assetM.load(assetID.get("moreApps2"));
		assetM.load(assetID.get("quit1"));
		assetM.load(assetID.get("quit2"));
		assetM.load(assetID.get("yes1"));
		assetM.load(assetID.get("yes2"));
		assetM.load(assetID.get("no1"));
		assetM.load(assetID.get("no2"));
		assetM.load(assetID.get("InGameMenuBG"));
		assetM.load(assetID.get("jPButtons"));
		assetM.load(assetID.get("gameOverTitle"));
		assetM.load(assetID.get("jPSetter"));
		assetM.load(assetID.get("jumpNumDisplay"));
		for (int i = 1; i <= 6; i++) assetM.load(assetID.get("jpNum" + i));
		assetM.load(assetID.get("menu1"));
		assetM.load(assetID.get("menu2"));
		assetM.load(assetID.get("back1"));
		assetM.load(assetID.get("back2"));

		for (int i = 1; i <= 6; i++) assetM.load(soundsID.get("bSound" + i));
		assetM.load(soundsID.get("gOverSound"));
	}
	
	public void loadMainMenuScreenAssets(){
		assetM.load(assetID.get("mMPlay1"));
		assetM.load(assetID.get("mMPlay2"));
		assetM.load(assetID.get("mMRateGame1"));
		assetM.load(assetID.get("mMRateGame2"));
		assetM.load(assetID.get("mMMoreApps1"));
		assetM.load(assetID.get("mMMoreApps2"));
		assetM.load(assetID.get("mMNoAds1"));
		assetM.load(assetID.get("mMNoAds2"));
		assetM.load(assetID.get("mMQuit1"));
		assetM.load(assetID.get("mMQuit2"));
		assetM.load(assetID.get("mMBG"));
		assetM.load(assetID.get("menuTitle"));
		assetM.load(assetID.get("signIn1"));
		assetM.load(assetID.get("signIn2"));
		assetM.load(assetID.get("signOut1"));
		assetM.load(assetID.get("signOut2"));
		assetM.load(assetID.get("mMRestore1"));
		assetM.load(assetID.get("mMRestore2"));
	}
}
