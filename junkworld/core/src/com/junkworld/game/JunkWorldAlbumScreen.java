package com.junkworld.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

public class JunkWorldAlbumScreen implements Screen {

	private OrthographicCamera camera;
	private Batch batch;
	private Vector3 touchPos;
	private Stage stage;
	private AssetManager manager;
	private JunkWorldEngines junkWorldEngines;
	private int currentTrash = 0;
	private Rectangle bLayer, rLayer, nrLayer, backLayer, leftArrowLayer, rightArrowLayer;
	private Actor b, r, nr, back, leftArrow, rightArrow;
	private Array<TextureRegion> bRegions, rRegions, nrRegions;
	private CurrentSelectedType currentSelectedType;
	private boolean isLoading = false, playMusic = true;
	private FileHandle profileFile;
	private JsonValue jsonValue;
	private long delayAds = TimeUtils.millis();

	private void disposeAsset(){
		batch.dispose();
		stage.dispose();
		this.dispose();
	}

	private void loadManager(){
		//		TODO
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/acorn.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/apple.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/bananaPeel.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/branch.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/deadMouse.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/eggShell.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/feather.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/fishBone.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/flower.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/grass.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/hair.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/hay.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/leaves.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/manure.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/biodegradableTrash/roots.png", Texture.class);

		//		RECYCLABLE
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/bottle.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/can.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/cardboard.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/cerealBox.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/dirtyShirt.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/envelope.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/funnel.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/hanger.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/newspaper.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/paper.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/pillBottle.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/plasticBag.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/tyre.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/vase.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/recyclableTrash/waffleIron.png", Texture.class);

		//		NONRECYCLABLE
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/aerosolCan.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/brokenBulb.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/brokenGlass.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/chewingGum.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/cigarette.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/deadBattery.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/dirtyDiaper.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/insecticideSpray.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/leftoverCake.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/leftoverChicken.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/nail.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/paintCan.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/syringe.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/tornPaper.png", Texture.class);
		manager.load("gameScreenAssets/trashAssets/nonRecyclableTrash/usedMotorOil.png", Texture.class);
		manager.load("backgrounds/firstBackground.png", Texture.class);
		manager.load("mainMenuAssets/loadingAssets/blackShader2.png", Texture.class);
		manager.load("backgrounds/junkWorldAlbum.png", Texture.class);
		manager.load("backgrounds/bFrame.png", Texture.class);
		manager.load("backgrounds/rFrame.png", Texture.class);
		manager.load("backgrounds/nrFrame.png", Texture.class);
		manager.load("backgrounds/b.png", Texture.class);
		manager.load("backgrounds/r.png", Texture.class);
		manager.load("backgrounds/nr.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradableClicked.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclableClicked.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclableClicked.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png", Texture.class);
		manager.load("profileAssets/back.png", Texture.class);
		manager.load("backgrounds/frame.png", Texture.class);

		manager.finishLoading();
	}

	private void unloadManager(){
		//			TODO
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/acorn.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/apple.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/bananaPeel.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/branch.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/deadMouse.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/eggShell.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/feather.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/fishBone.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/flower.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/grass.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/hair.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/hay.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/leaves.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/manure.png");
		manager.unload("gameScreenAssets/trashAssets/biodegradableTrash/roots.png");

		//		RECYCLABLE
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/bottle.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/can.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/cardboard.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/cerealBox.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/dirtyShirt.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/envelope.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/funnel.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/hanger.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/newspaper.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/paper.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/pillBottle.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/plasticBag.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/tyre.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/vase.png");
		manager.unload("gameScreenAssets/trashAssets/recyclableTrash/waffleIron.png");

		//		NONRECYCLABLE
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/aerosolCan.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/brokenBulb.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/brokenGlass.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/chewingGum.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/cigarette.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/deadBattery.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/dirtyDiaper.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/insecticideSpray.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/leftoverCake.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/leftoverChicken.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/nail.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/paintCan.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/syringe.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/tornPaper.png");
		manager.unload("gameScreenAssets/trashAssets/nonRecyclableTrash/usedMotorOil.png");

		manager.unload("backgrounds/firstBackground.png");
		manager.unload("mainMenuAssets/loadingAssets/blackShader2.png");
		manager.unload("backgrounds/junkWorldAlbum.png");
		manager.unload("backgrounds/bFrame.png");
		manager.unload("backgrounds/rFrame.png");
		manager.unload("backgrounds/nrFrame.png");
		manager.unload("backgrounds/b.png");
		manager.unload("backgrounds/r.png");
		manager.unload("backgrounds/nr.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradableClicked.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclableClicked.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclableClicked.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png");
		manager.unload("profileAssets/back.png");
		manager.unload("backgrounds/frame.png");
	}

	public JunkWorldAlbumScreen(final JunkWorld game, final AssetManager manager,
			final JunkWorldEngines junkWorldEngines){
		this.manager = manager;
		this.junkWorldEngines = junkWorldEngines;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		touchPos = new Vector3();
		camera.setToOrtho(false, 512, 800);
		loadManager();
		profileFile = Gdx.files.local(junkWorldEngines.getProfileName() + ".json");
		jsonValue = new JsonReader().parse(profileFile);

		bLayer = new Rectangle(90, 130 + 10 + 30, 96, 96);
		rLayer = new Rectangle(210, 130 + 10 + 30, 96	, 96);
		nrLayer = new Rectangle(330, 130 + 10 + 30, 96, 96);
		backLayer = new Rectangle(256 - 60, 50 + 30 + 40, 120, 50);
		leftArrowLayer = new Rectangle(20, 230 + 30, 64, 64);
		rightArrowLayer = new Rectangle(512 - 20 - 64, 230 + 30, 64, 64);

		bRegions = new Array<TextureRegion>();
		rRegions = new Array<TextureRegion>();
		nrRegions = new Array<TextureRegion>();

		b = new Actor();
		r = new Actor();
		nr = new Actor();
		back = new Actor();
		leftArrow = new Actor();
		rightArrow = new Actor();

		if (jsonValue.getBoolean("acornAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/acorn.png", Texture.class)));
		}
		if (jsonValue.getBoolean("appleAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/apple.png", Texture.class)));
		}
		if (jsonValue.getBoolean("bananaPeelAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/bananaPeel.png", Texture.class)));
		}
		if (jsonValue.getBoolean("branchAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/branch.png", Texture.class)));
		}
		if (jsonValue.getBoolean("deadMouseAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/deadMouse.png", Texture.class)));
		}
		if (jsonValue.getBoolean("eggShellAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/eggShell.png", Texture.class)));
		}
		if (jsonValue.getBoolean("featherAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/feather.png", Texture.class)));
		}
		if (jsonValue.getBoolean("fishBoneAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/fishBone.png", Texture.class)));
		}
		if (jsonValue.getBoolean("flowerAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/flower.png", Texture.class)));
		}
		if (jsonValue.getBoolean("grassAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/grass.png", Texture.class)));
		}
		if (jsonValue.getBoolean("hairAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/hair.png", Texture.class)));
		}
		if (jsonValue.getBoolean("hayAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/hay.png", Texture.class)));
		}
		if (jsonValue.getBoolean("leavesAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/leaves.png", Texture.class)));
		}
		if (jsonValue.getBoolean("manureAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/manure.png", Texture.class)));
		}
		if (jsonValue.getBoolean("rootsAlbum")){
			bRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/biodegradableTrash/roots.png", Texture.class)));
		}

		
		if (jsonValue.getBoolean("bottleAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/bottle.png", Texture.class)));
		}
		if (jsonValue.getBoolean("canAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/can.png", Texture.class)));
		}
		if (jsonValue.getBoolean("cardboardAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/cardboard.png", Texture.class)));
		}
		if (jsonValue.getBoolean("cerealBoxAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/cerealBox.png", Texture.class)));
		}
		if (jsonValue.getBoolean("dirtyShirtAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/dirtyShirt.png", Texture.class)));
		}
		if (jsonValue.getBoolean("envelopeAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/envelope.png", Texture.class)));
		}
		if (jsonValue.getBoolean("funnelAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/funnel.png", Texture.class)));
		}
		if (jsonValue.getBoolean("hangerAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/hanger.png", Texture.class)));
		}
		if (jsonValue.getBoolean("newspaperAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/newspaper.png", Texture.class)));
		}
		if (jsonValue.getBoolean("paperAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/paper.png", Texture.class)));
		}
		if (jsonValue.getBoolean("pillBottleAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/pillBottle.png", Texture.class)));
		}
		if (jsonValue.getBoolean("plasticBagAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/plasticBag.png", Texture.class)));
		}
		if (jsonValue.getBoolean("tyreAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/tyre.png", Texture.class)));
		}
		if (jsonValue.getBoolean("vaseAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/vase.png", Texture.class)));
		}
		if (jsonValue.getBoolean("waffleIronAlbum")){
			rRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/recyclableTrash/waffleIron.png", Texture.class)));
		}
		
		if (jsonValue.getBoolean("aerosolCanAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/aerosolCan.png", Texture.class)));
		}
		if (jsonValue.getBoolean("brokenBulbAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/brokenBulb.png", Texture.class)));
		}
		if (jsonValue.getBoolean("brokenGlassAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/brokenGlass.png", Texture.class)));
		}
		if (jsonValue.getBoolean("chewingGumAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/chewingGum.png", Texture.class)));
		}
		if (jsonValue.getBoolean("cigaretteAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/cigarette.png", Texture.class)));
		}
		if (jsonValue.getBoolean("deadBatteryAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/deadBattery.png", Texture.class)));
		}
		if (jsonValue.getBoolean("dirtyDiaperAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/dirtyDiaper.png", Texture.class)));
		}
		if (jsonValue.getBoolean("insecticideSprayAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/insecticideSpray.png", Texture.class)));
		}
		if (jsonValue.getBoolean("leftoverCakeAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/leftoverCake.png", Texture.class)));
		}
		if (jsonValue.getBoolean("leftoverChickenAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/leftoverChicken.png", Texture.class)));
		}
		if (jsonValue.getBoolean("nailAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/nail.png", Texture.class)));
		}
		if (jsonValue.getBoolean("paintCanAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/paintCan.png", Texture.class)));
		}
		if (jsonValue.getBoolean("syringeAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/syringe.png", Texture.class)));
		}
		if (jsonValue.getBoolean("tornPaperAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/tornPaper.png", Texture.class)));
		}
		if (jsonValue.getBoolean("usedMotorOilAlbum")){
			nrRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashAssets/nonRecyclableTrash/usedMotorOil.png", Texture.class)));
		}

		b.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				if (bRegions.size > 0){
					if (currentSelectedType == CurrentSelectedType.BIODEGRADABLE){
						currentSelectedType = CurrentSelectedType.NONE;
					} else currentSelectedType = CurrentSelectedType.BIODEGRADABLE;
				}
				return true;
			}
		});

		r.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				if (rRegions.size > 0){
					if (currentSelectedType == CurrentSelectedType.RECYCLABLE){
						currentSelectedType = CurrentSelectedType.NONE;
					} else currentSelectedType = CurrentSelectedType.RECYCLABLE;
				}
				return true;
			}
		});

		nr.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				if (rRegions.size > 0){
					if (currentSelectedType == CurrentSelectedType.NONRECYCLABLE){
						currentSelectedType = CurrentSelectedType.NONE;
					} else currentSelectedType = CurrentSelectedType.NONRECYCLABLE;
				}
				return true;
			}
		});

		leftArrow.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				switch (currentSelectedType){
				case BIODEGRADABLE: 
					if (currentTrash - 1 < 0){
						currentTrash = bRegions.size - 1;
					} else currentTrash -= 1;
					break;
				case NONE:
					break;
				case NONRECYCLABLE:
					if (currentTrash - 1 < 0){
						currentTrash = nrRegions.size - 1;
					} else currentTrash -= 1;
					break;
				case RECYCLABLE:
					if (currentTrash - 1 < 0){
						currentTrash = rRegions.size - 1;
					} else currentTrash -= 1;
					break;
				default:
					break;
				}
				return true;
			}
		});

		rightArrow.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				switch (currentSelectedType){
				case BIODEGRADABLE: 
					if (currentTrash + 1 == bRegions.size){
						currentTrash = 0;
					} else currentTrash += 1;
					break;
				case NONE:
					break;
				case NONRECYCLABLE:
					if (currentTrash + 1 == rRegions.size){
						currentTrash = 0;
					} else currentTrash += 1;
					break;
				case RECYCLABLE:
					if (currentTrash + 1 == nrRegions.size){
						currentTrash = 0;
					} else currentTrash += 1;
					break;
				default:
					break;
				}
				return true;
			}
		});

		back.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
					int pointer, int button) {
				isLoading = true;
				unloadManager();
				disposeAsset();
				game.setScreen(new MainMenuScreen(game, manager, junkWorldEngines));
				return true;
			}
		});

		stage = new Stage();
		stage.addActor(b);
		stage.addActor(r);
		stage.addActor(nr);
		stage.addActor(back);
		stage.addActor(leftArrow);
		stage.addActor(rightArrow);

		currentSelectedType = CurrentSelectedType.NONE;
//		game.adManager.showBannerAds(true);
		loadMusic();
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

			setBounds();
			drawBatches();

			stage.getViewport().setCamera(camera);
			stage.draw();
			stage.act();
			Gdx.input.setInputProcessor(stage);
		}
	}

	private void setBounds() {
		b.setBounds(bLayer.x, bLayer.y, bLayer.width, bLayer.height);
		r.setBounds(rLayer.x, rLayer.y, rLayer.width, rLayer.height);
		nr.setBounds(nrLayer.x, nrLayer.y, nrLayer.width, nrLayer.height);
		back.setBounds(backLayer.x, backLayer.y, backLayer.width, backLayer.height);
		leftArrow.setBounds(leftArrowLayer.x, leftArrowLayer.y, leftArrowLayer.width, leftArrowLayer.height);
		rightArrow.setBounds(rightArrowLayer.x, rightArrowLayer.y, rightArrowLayer.width, rightArrowLayer.height);
	}

	private void drawBatches() {
		batch.begin();
		batch.draw(manager.get("backgrounds/firstBackground.png", Texture.class), 0, 0);
		batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader2.png", Texture.class), 0, 0, 512, 800);
		batch.draw(manager.get("backgrounds/junkWorldAlbum.png", Texture.class), 56, 690 + 30, 400, 64);
		switch (currentSelectedType){
		case BIODEGRADABLE: {
			batch.draw(manager.get("backgrounds/bFrame.png", Texture.class), 16, 300 + 30, 480, 360);
			batch.draw(bRegions.get(currentTrash), 128, 370, 256, 256);
			batch.draw(manager.get("backgrounds/b.png", Texture.class), 256 - 150, 230 + 30, 300, 64);
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradableClicked.png", Texture.class),
					bLayer.x, bLayer.y, bLayer.width, bLayer.height);
		}; break;
		case RECYCLABLE: {
			batch.draw(manager.get("backgrounds/rFrame.png", Texture.class), 16, 300 + 30, 480, 360);
			batch.draw(rRegions.get(currentTrash), 128, 370, 256, 256);
			batch.draw(manager.get("backgrounds/r.png", Texture.class), 256 - 150, 230 + 30, 300, 64);
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclableClicked.png", Texture.class),
					rLayer.x, rLayer.y, rLayer.width, rLayer.height);
		}; break;
		case NONE: {
			batch.draw(manager.get("backgrounds/frame.png", Texture.class), 16, 300 + 30, 480, 360);
		}; break;
		case NONRECYCLABLE: {
			batch.draw(manager.get("backgrounds/nrFrame.png", Texture.class), 16, 300 + 30, 480, 360);
			batch.draw(nrRegions.get(currentTrash), 128, 370, 256, 256);
			batch.draw(manager.get("backgrounds/nr.png", Texture.class), 256 - 150, 230 + 30, 300, 64);
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclableClicked.png", Texture.class),
					nrLayer.x, nrLayer.y, nrLayer.width, nrLayer.height);
		}; break;
		default: break;
		}

		batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class),
				bLayer.x, bLayer.y, bLayer.width, bLayer.height);
		batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class),
				rLayer.x, rLayer.y, rLayer.width, rLayer.height);
		batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class),
				nrLayer.x, nrLayer.y, nrLayer.width, nrLayer.height);
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png", Texture.class), leftArrowLayer.x, leftArrowLayer.y, leftArrowLayer.width,
				leftArrowLayer.height);
		batch.draw(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png", Texture.class), rightArrowLayer.x, rightArrowLayer.y, rightArrowLayer.width,
				rightArrowLayer.height);
		batch.draw(manager.get("profileAssets/back.png", Texture.class), backLayer.x, backLayer.y, backLayer.width, backLayer.height);
		batch.end();
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
		// TODO Auto-generated method stub

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
