package com.junkworld.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author home
 *
 */
public class TrashCanSelectionScreen implements Screen {
	public JunkWorld game;
	private OrthographicCamera camera;
	private Batch batch;
	private Rectangle backButtonLayer, nextButtonLayer, trashCanSlotLayer1 ,trashCanSlotLayer2,
	trashCanSlotLayer3, trashCanSlotLayer4;
	private JunkWorldEngines junkWorldEngines;
	private Rectangle leftArrowLayer, firstTrashLayer, secondTrashLayer, currentTrashLayer, downArrowLayer;
	private Actor back, next, trashCanSlot1, trashCanSlot2, trashCanSlot3, trashCanSlot4;
	private Stage stageMain, stage;
	private boolean openCanSelectionScreen = false;
	protected boolean dispose = false;
	protected boolean changeBlackCanState = false;
	private AssetManager manager;
	private Vector3 touchPos;
	private Array<Texture> trashCans;
	private boolean addTextures = true;
	private Rectangle biodegradableLayer, recyclableLayer, nonRecyclableLayer, selectLayer;
	private Actor biodegradable, recyclable, nonRecyclable, select;
	private TrashCan1Properties trashCan1Properties;
	private TrashCan2Properties trashCan2Properties;
	private TrashCan3Properties trashCan3Properties;
	private TrashCan4Properties trashCan4Properties;
	private Actor downArrow, leftArrow, zoom, ok;
	private int currentSelectedCan = 0;
	private int currentTrashCanNum = 0;
	private int currentSlotNum = 0;
	private int lastTrashCanNum;
	private int nextTrashCanNum, countCan = 0;
	private Rectangle selectSlot1TypeLayer, selectSlot2TypeLayer, selectSlot3TypeLayer, selectSlot4TypeLayer, gameRequirementsArrowLayer, randomizeButtonLayer, okLayer;
	private Actor selectSlot1Type, selectSlot2Type, selectSlot3Type, selectSlot4Type, gameRequirementsArrow, randomize, slotType1, slotType2, slotType3, slotType4;
	private long selectCanInt = 0, selectTypeInt = 0, delayGameArrow = TimeUtils.millis(), delayGameReqMovement = 0, delayRandom = 0, delayTutoralArrow = TimeUtils.millis();
	private int currentFrame1 = 1, currentFrame2 = 1;
	private boolean clickedB = false, clickedR = false, clickedNR = false, playMusic = true;
	private Animation colorAnima;
	private float stateTime;
	private Array<TextureRegion> colors;
	private TextureRegion currentColorRegion;
	private boolean chooseCan = false, chooseType = false, backClicked = false, nextClicked = false, gameReqOpened = false, setActors = true;
	private boolean propertiesSet = false, removeRegions = false;
	private boolean biodegradablePres = true, recyclablePres = true, nonRecyclablePres = true;
	private TextureRegion backButtonRegion, nextButtonRegion, backButtonClickedRegion, nextButtonClickedRegion, randomizeButtonRegion, currentRequirementsArrayL,
	currentRequirementsArrayR, greenBGRegion;
	private boolean setRegions = true, zoomDesc = false, isLoading = false;
	private Random generator;
	private Array<Integer> randomCans, randomTypes;
	private Sprite arrow;
	private Actor quit, clickContinue;
	private Array<Texture> tutorial;
	private Array<Texture> tutorialArrow;
	private Rectangle tutorialLayer;
	private long delayAds = TimeUtils.millis();
	
	private void disposeAssets(){
		batch.dispose();
		stageMain.dispose();
		stage.dispose();
		this.dispose();
	}

	private void loadManager(){
		manager.load("gameScreenAssets/trashCanSelectionAssets/noTrash.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashCanLocked.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/default.png", Texture.class);
		manager.load("buttons/randomizeButton.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class);
		manager.load("backgrounds/greenBackground.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/leftArrow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/downArrow.png", Texture.class);
		manager.load("backgrounds/trashCanDescription.png", Texture.class);
		manager.load("mainMenuAssets/loadingAssets/blackShader.png", Texture.class);
		manager.load("screenLabels/selectType.png", Texture.class);
		manager.load("screenLabels/biodegradable.png", Texture.class);
		manager.load("screenLabels/recyclable.png", Texture.class);
		manager.load("screenLabels/nonRecyclable.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/gameRequirements.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/types.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class);	
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class);	
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradableClicked.png", Texture.class);	
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclableClicked.png", Texture.class);	
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclableClicked.png", Texture.class);	
		manager.load("gameScreenAssets/comboAssets/colors/color.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/red.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/orange.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/yellow.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/green.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/blue.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/purple.png", Texture.class);
		manager.load("buttons/backButton.png", Texture.class);
		manager.load("buttons/nextButton.png", Texture.class);
		manager.load("buttons/backButtonClicked.png", Texture.class);
		manager.load("buttons/nextButtonClicked.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/description.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/description.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/description.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/description.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/description.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/description.png", Texture.class);
		manager.load("screenLabels/noCanSelected.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/zoom.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/okButton.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL2.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR2.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class);
		manager.load("screenLabels/selectCan.png", Texture.class);
		manager.load("screenLabels/selectCan2.png", Texture.class);
		manager.load("backgrounds/firstBackground.png", Texture.class);
		manager.load("backgrounds/blueFrame.png", Texture.class);
		manager.load("backgrounds/brickFrame.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class);
		manager.load("screenLabels/chooseACan.png", Texture.class);
		manager.load("screenLabels/chooseType.png", Texture.class);
		manager.load("screenLabels/noBiodegradableCan.png", Texture.class);
		manager.load("screenLabels/noRecyclableCan.png", Texture.class);
		manager.load("screenLabels/noNonRecyclableCan.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashCanSlotsClicked.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultB.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultNR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultB.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultNR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultB.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultNR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultB.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultNR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultB.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultNR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultB.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultNR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/default.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect2.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/selectType.png", Texture.class);

		if (junkWorldEngines.getGameMode() == 0){
			for (int i = 0; i < 29; i++){
				manager.load("gameScreenAssets/tutorialAssets/Tutorials/" + i + ".png", Texture.class);
			}
			for (int i = 0; i < 3; i++){
				manager.load("gameScreenAssets/tutorialAssets/arrow" + i + ".png", Texture.class);
			}
		}

		manager.finishLoading();
	}

	private void unloadManager(){
		manager.unload("gameScreenAssets/trashCanSelectionAssets/noTrash.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashCanLocked.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/default.png");
		manager.unload("buttons/randomizeButton.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png");
		manager.unload("backgrounds/greenBackground.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/leftArrow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/downArrow.png");
		manager.unload("backgrounds/trashCanDescription.png");
		manager.unload("screenLabels/selectType.png");
		manager.unload("screenLabels/biodegradable.png");
		manager.unload("screenLabels/recyclable.png");
		manager.unload("screenLabels/nonRecyclable.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/gameRequirements.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/types.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png");	
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png");	
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png");	
		manager.unload("gameScreenAssets/comboAssets/colors/color.png");
		manager.unload("gameScreenAssets/comboAssets/colors/red.png");
		manager.unload("gameScreenAssets/comboAssets/colors/orange.png");
		manager.unload("gameScreenAssets/comboAssets/colors/yellow.png");
		manager.unload("gameScreenAssets/comboAssets/colors/green.png");
		manager.unload("gameScreenAssets/comboAssets/colors/blue.png");
		manager.unload("gameScreenAssets/comboAssets/colors/purple.png");
		manager.unload("buttons/backButton.png");
		manager.unload("buttons/nextButton.png");
		manager.unload("buttons/backButtonClicked.png");
		manager.unload("buttons/nextButtonClicked.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/description.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/description.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/description.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/description.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/description.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/description.png");
		manager.unload("screenLabels/noCanSelected.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/zoom.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL2.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR2.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png");
		manager.unload("screenLabels/selectCan.png");
		manager.unload("screenLabels/selectCan2.png");
		manager.unload("backgrounds/firstBackground.png");
		manager.unload("backgrounds/blueFrame.png");
		manager.unload("backgrounds/brickFrame.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png");
		manager.unload("screenLabels/chooseACan.png");
		manager.unload("screenLabels/chooseType.png");
		manager.unload("screenLabels/noBiodegradableCan.png");
		manager.unload("screenLabels/noRecyclableCan.png");
		manager.unload("screenLabels/noNonRecyclableCan.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashCanSlotsClicked.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultB.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultNR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultB.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultNR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultB.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultNR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultB.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultNR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultB.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultNR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultB.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultNR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/default.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect2.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradableClicked.png");	
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclableClicked.png");	
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclableClicked.png");	
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/selectType.png");

	}

	public TrashCanSelectionScreen (final JunkWorld game,  final AssetManager manager,
			final JunkWorldEngines junkWorldEngines){
		this.game = game;
		this.manager = manager;
		this.junkWorldEngines = junkWorldEngines;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		batch = new SpriteBatch();
		touchPos = new Vector3();
		loadManager();

		if (junkWorldEngines.getGameMode() == 0){
			tutorial = new Array<Texture>();
			tutorialArrow = new Array<Texture>();
			for (int i = 0; i < 29; i++){
				tutorial.add(manager.get("gameScreenAssets/tutorialAssets/Tutorials/" + i + ".png", Texture.class));
			}
			for (int i = 0; i < 3; i++){
				tutorialArrow.add(manager.get("gameScreenAssets/tutorialAssets/arrow" + i + ".png", Texture.class));
			}
			junkWorldEngines.setTutorial(tutorial);
			junkWorldEngines.setTutorialArrow(tutorialArrow);
		}

		generator = new Random();
		trashCans = new Array<Texture>();
		randomCans = new Array<Integer>();
		randomTypes = new Array<Integer>();

		randomCans.add(0);
		if (junkWorldEngines.isAverageCanUnlocked()){
			randomCans.add(1);
		}
		if (junkWorldEngines.isScorchingCanUnlocked()){
			randomCans.add(2);
		}
		if (junkWorldEngines.isVacuumCanUnlocked()){
			randomCans.add(3);
		}
		if (junkWorldEngines.isTrashBlowerUnlocked()){
			randomCans.add(4);
		}
		if (junkWorldEngines.isDullCanUnlocked()){
			randomCans.add(5);
		}
		if (junkWorldEngines.isSwiftCanUnlocked()){
			randomCans.add(6);
		}
		//		TODO

		if (!propertiesSet){
			trashCan1Properties = new TrashCan1Properties();
			trashCan2Properties = new TrashCan2Properties();
			trashCan3Properties = new TrashCan3Properties();
			trashCan4Properties = new TrashCan4Properties();
		}

		backButtonLayer = new Rectangle(64, 64, 100, 50);
		nextButtonLayer = new Rectangle(350, 64, 100, 50);
		trashCanSlotLayer1 = new Rectangle(52 + 32, 250, 64, 64);
		trashCanSlotLayer2 = new Rectangle(52 + 64 + 64, 250, 64, 64);
		trashCanSlotLayer3 = new Rectangle(52 + 128 + 96, 250, 64, 64);
		trashCanSlotLayer4 = new Rectangle(52 + 192 + 128, 250, 64, 64);
		gameRequirementsArrowLayer = new Rectangle(455, 400, 50, 100);

		selectSlot1TypeLayer = new Rectangle(52 + 32, 250 - 100, 64, 64);
		selectSlot2TypeLayer = new Rectangle(52 + 64 + 64, 250 - 100, 64, 64);
		selectSlot3TypeLayer = new Rectangle(52 + 128 + 96, 250 - 100, 64, 64);
		selectSlot4TypeLayer = new Rectangle(52 + 192 + 128, 250 - 100, 64, 64);
		randomizeButtonLayer = new Rectangle(256 - 60, 66, 120, 46);
		okLayer = new Rectangle(192, 130, 128, 64);

		//		leftArrowLayer, firstTrashLayer, secondTrashLayer, currentTrashLayer, fourthTrashLayer,
		//		fifthTrashLayer, downArrowLayer, trashCanTypeDescriptionLayer;

		leftArrowLayer = new Rectangle(124, 595, 60, 60);
		firstTrashLayer = new Rectangle(196, 585, 80, 80);
		currentTrashLayer = new Rectangle(288, 575, 100, 100);
		secondTrashLayer = new Rectangle(298, 478, 80, 80);
		downArrowLayer = new Rectangle(309, 405, 60, 60);

		biodegradableLayer = new Rectangle(64, 300, 128, 128);
		recyclableLayer = new Rectangle(64 + 128, 300, 128, 128);
		nonRecyclableLayer = new Rectangle(64 + 256, 300, 128, 128);
		selectLayer = new Rectangle(192, 200, 128, 30);

		back = new Actor();
		next = new Actor();
		trashCanSlot1 = new Actor();
		trashCanSlot2 = new Actor();
		trashCanSlot3 = new Actor();
		trashCanSlot4 = new Actor();
		gameRequirementsArrow = new Actor();
		randomize = new Actor();
		slotType1 = new Actor();
		slotType2 = new Actor();
		slotType3 = new Actor();
		slotType4 = new Actor();
		
		biodegradable = new Actor();
		recyclable = new Actor();
		nonRecyclable = new Actor();
		select = new Actor();

		leftArrow = new Actor();
		downArrow = new Actor();
		zoom = new Actor();
		ok = new Actor();

		stageMain = new Stage();
		stageMain.addActor(back);
		stageMain.addActor(next);
		stageMain.addActor(trashCanSlot1);
		stageMain.addActor(trashCanSlot2);
		stageMain.addActor(trashCanSlot3);
		stageMain.addActor(trashCanSlot4);
		stageMain.addActor(leftArrow);
		stageMain.addActor(downArrow);
		stageMain.addActor(gameRequirementsArrow);
		stageMain.addActor(randomize);
		stageMain.addActor(slotType1);
		stageMain.addActor(slotType2);
		stageMain.addActor(slotType3);
		stageMain.addActor(slotType4);
		stageMain.addActor(zoom);
		stageMain.addActor(ok);

		setActors();
		setSelectTypeActors();
		setAnimation();

		leftArrow.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentTrashCanNum + 1 == trashCans.size){
					currentTrashCanNum = 0;
				}
				else {
					currentTrashCanNum++;
				}
				while (!randomCans.contains(currentTrashCanNum, false)){
					if (currentTrashCanNum + 1 == trashCans.size){
						currentTrashCanNum = 0;
					}
					else {
						currentTrashCanNum++;
					}
				}
				super.touchDown(event, x, y, pointer, button);
			}
		});

		downArrow.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentTrashCanNum - 1 < 0){
					currentTrashCanNum = trashCans.size - 1;
				}
				else {
					currentTrashCanNum--;
				}
				while (!randomCans.contains(currentTrashCanNum, false)){
					if (currentTrashCanNum - 1 < 0){
						currentTrashCanNum = trashCans.size - 1;
					}
					else {
						currentTrashCanNum--;
					}
				}
				super.touchDown(event, x, y, pointer, button);
			}
		});

		if (junkWorldEngines.getGameMode() == 0){

			tutorialLayer = new Rectangle().setSize(320, 320);
			quit = new Actor();
			quit.addListener(new ActorGestureListener(){
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
			clickContinue = new Actor();
			clickContinue.addListener(new ActorGestureListener(){
				@Override
				public void touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (junkWorldEngines.getCurrentTutorial() + 1 == 29){
					}
					else junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
					super.touchDown(event, x, y, pointer, button);
				}
			});

			arrow = new Sprite(manager.get("gameScreenAssets/tutorialAssets/arrow0.png", Texture.class));
			arrow.setOrigin(arrow.getWidth() / 2, arrow.getHeight() / 2);

			junkWorldEngines.setTutorialLayer(tutorialLayer);
			junkWorldEngines.setQuitTutorial(quit);
			junkWorldEngines.setTutorialStage(new Stage());
			junkWorldEngines.setCurrentArrow(arrow);
			junkWorldEngines.setClickContinueTutorial(clickContinue);
			junkWorldEngines.getTutorialStage().addActor(leftArrow);
			junkWorldEngines.getTutorialStage().addActor(downArrow);
			junkWorldEngines.getTutorialStage().addActor(junkWorldEngines.getQuitTutorial());
			junkWorldEngines.getTutorialStage().addActor(junkWorldEngines.getClickContinueTutorial());
			junkWorldEngines.getTutorialStage().addActor(trashCanSlot4);
			junkWorldEngines.getTutorialStage().addActor(randomize);
			junkWorldEngines.getTutorialStage().addActor(next);
			junkWorldEngines.getTutorialStage().addActor(slotType4);
			junkWorldEngines.getTutorialStage().addActor(biodegradable);
			junkWorldEngines.getTutorialStage().addActor(recyclable);
			junkWorldEngines.getTutorialStage().addActor(nonRecyclable);
			junkWorldEngines.getTutorialStage().addActor(select);
		}
//		game.adManager.showBannerAds(false);
		loadMusic();
	}

	private void setAnimation() {
		colors = new Array<TextureRegion>();
		for (int i = 0; i < 6; i++){
			colors.add(new TextureRegion(new Texture(Gdx.files.internal("gameAnimations/selectedCan/color" + i + ".png"))));
		}
		colorAnima = new Animation(0.2f, colors);
		stateTime = 0f;
	}

	@Override
	public void render(float delta) {
		if (manager.update() && !isLoading){
			playMusic();
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			stateTime += Gdx.graphics.getDeltaTime();
			
			if (Gdx.input.justTouched()){
				delayAds = TimeUtils.millis();
			}
			if (TimeUtils.millis() - delayAds >= 5000 && junkWorldEngines.getGameSelection() == 1 && junkWorldEngines.getGameMode() == 0){
				delayAds = TimeUtils.millis();
//				myRequestHandler.showAds2(true);
			}

			currentColorRegion = colorAnima.getKeyFrame(stateTime, true);
			setActor();

			if (addTextures){
				addTextures = false;

				trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/noTrash.png", Texture.class));

				if (junkWorldEngines.isAverageCanUnlocked()){
					trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/default.png", Texture.class));
				} else trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanLocked.png", Texture.class));

				if (junkWorldEngines.isScorchingCanUnlocked()){
					trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/default.png", Texture.class));
				} else trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanLocked.png", Texture.class));

				if (junkWorldEngines.isVacuumCanUnlocked()){
					trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/default.png", Texture.class));
				} else trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanLocked.png", Texture.class));

				if (junkWorldEngines.isTrashBlowerUnlocked()){
					trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/default.png", Texture.class));
				} else trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanLocked.png", Texture.class));

				if (junkWorldEngines.isDullCanUnlocked()){
					trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/default.png", Texture.class));
				} else trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanLocked.png", Texture.class));

				if (junkWorldEngines.isSwiftCanUnlocked()){
					trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/default.png", Texture.class));
				} else trashCans.add(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanLocked.png", Texture.class));

				lastTrashCanNum = trashCans.size - 1;
				nextTrashCanNum = 1;
				selectCanInt = System.currentTimeMillis();
				selectTypeInt = System.currentTimeMillis();

				randomizeButtonRegion = new TextureRegion(manager.get("buttons/randomizeButton.png", Texture.class));
				currentRequirementsArrayL = new TextureRegion(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class));
				currentRequirementsArrayR = new TextureRegion(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class));
				greenBGRegion = new TextureRegion(manager.get("backgrounds/greenBackground.png", Texture.class));
			}

			setDelay();

			back.setBounds(backButtonLayer.x, backButtonLayer.y, backButtonLayer.width, backButtonLayer.height); 
			next.setBounds(nextButtonLayer.x, nextButtonLayer.y, nextButtonLayer.width, nextButtonLayer.height);
			trashCanSlot1.setBounds(trashCanSlotLayer1.x, trashCanSlotLayer1.y, trashCanSlotLayer1.width, trashCanSlotLayer1.height);
			trashCanSlot2.setBounds(trashCanSlotLayer2.x, trashCanSlotLayer2.y, trashCanSlotLayer2.width, trashCanSlotLayer2.height);
			trashCanSlot3.setBounds(trashCanSlotLayer3.x, trashCanSlotLayer3.y, trashCanSlotLayer3.width, trashCanSlotLayer3.height);
			trashCanSlot4.setBounds(trashCanSlotLayer4.x, trashCanSlotLayer4.y, trashCanSlotLayer4.width, trashCanSlotLayer4.height);
			leftArrow.setBounds(leftArrowLayer.x, leftArrowLayer.y, leftArrowLayer.width, leftArrowLayer.height);
			downArrow.setBounds(downArrowLayer.x, downArrowLayer.y, downArrowLayer.width, downArrowLayer.height);
			biodegradable.setBounds(biodegradableLayer.x, biodegradableLayer.y, biodegradableLayer.width, biodegradableLayer.height);
			recyclable.setBounds(recyclableLayer.x, recyclableLayer.y, recyclableLayer.width, recyclableLayer.height);
			nonRecyclable.setBounds(nonRecyclableLayer.x, nonRecyclableLayer.y, nonRecyclableLayer.width, nonRecyclableLayer.height);
			select.setBounds(selectLayer.x, selectLayer.y, selectLayer.width, selectLayer.height);
			gameRequirementsArrow.setBounds(gameRequirementsArrowLayer.x, gameRequirementsArrowLayer.y, gameRequirementsArrowLayer.width,
					gameRequirementsArrowLayer.height);
			randomize.setBounds(randomizeButtonLayer.x, randomizeButtonLayer.y, randomizeButtonLayer.width, randomizeButtonLayer.height);
			slotType1.setBounds(selectSlot1TypeLayer.x, selectSlot1TypeLayer.y, selectSlot1TypeLayer.width, selectSlot1TypeLayer.height);
			slotType2.setBounds(selectSlot2TypeLayer.x, selectSlot2TypeLayer.y, selectSlot2TypeLayer.width, selectSlot2TypeLayer.height);
			slotType3.setBounds(selectSlot3TypeLayer.x, selectSlot3TypeLayer.y, selectSlot3TypeLayer.width, selectSlot3TypeLayer.height);
			slotType4.setBounds(selectSlot4TypeLayer.x, selectSlot4TypeLayer.y, selectSlot4TypeLayer.width, selectSlot4TypeLayer.height);
			ok.setBounds(okLayer.x, okLayer.y, okLayer.width, okLayer.height);
			leftArrow.setBounds(leftArrowLayer.x, leftArrowLayer.y, leftArrowLayer.width, leftArrowLayer.height);
			downArrow.setBounds(downArrowLayer.x, downArrowLayer.y, downArrowLayer.width, downArrowLayer.height);
			zoom.setBounds(113, 398, 180, 180);

			configureAssets();

			batch.begin();
			drawAssets();
			if (!openCanSelectionScreen){
				displaySelectedTrashCans();
				displayTrashCanTypSelection();
				batch.draw(randomizeButtonRegion, randomizeButtonLayer.x, randomizeButtonLayer.y, randomizeButtonLayer.width, randomizeButtonLayer.height);
				if (!gameReqOpened){
					batch.draw(currentRequirementsArrayL, gameRequirementsArrowLayer.x, gameRequirementsArrowLayer.y, gameRequirementsArrowLayer.width,
							gameRequirementsArrowLayer.height);
				}
				else {
					batch.draw(currentRequirementsArrayR, gameRequirementsArrowLayer.x, gameRequirementsArrowLayer.y, gameRequirementsArrowLayer.width,
							gameRequirementsArrowLayer.height);
				}
				batch.draw(greenBGRegion, gameRequirementsArrowLayer.x + 62, 200, 440,
						600);
				if (currentSelectedCan != 0){
					batch.draw(currentColorRegion, currentTrashLayer.x, currentTrashLayer.y, currentTrashLayer.width, currentTrashLayer.height);
					batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/leftArrow.png", Texture.class),
							leftArrowLayer.x, leftArrowLayer.y, leftArrowLayer.width, leftArrowLayer.height);
					batch.draw(trashCans.get(lastTrashCanNum),firstTrashLayer.x, firstTrashLayer.y, firstTrashLayer.width, firstTrashLayer.height);
					batch.draw(trashCans.get(currentTrashCanNum), currentTrashLayer.x, currentTrashLayer.y, currentTrashLayer.width, currentTrashLayer.height);
					batch.draw(trashCans.get(nextTrashCanNum), secondTrashLayer.x, secondTrashLayer.y, secondTrashLayer.width, secondTrashLayer.height);
					batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/downArrow.png", Texture.class),
							downArrowLayer.x, downArrowLayer.y, downArrowLayer.width, downArrowLayer.height);
					if (!zoomDesc){
						batch.draw(manager.get("backgrounds/trashCanDescription.png", Texture.class),
								113, 398, 180, 180);
					}
					else {
						batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader.png", Texture.class), 0, 0, 512, 800);
						batch.draw(manager.get("backgrounds/trashCanDescription.png", Texture.class),
								256 - (384 / 2), 400 - (384 / 2), 384, 384);
					}
					setCanDescription();
				}
				else {
					drawBlinks();
				}
			}
			if (openCanSelectionScreen){
				batch.draw(greenBGRegion, 0, 0, 512, 800);
				batch.draw(manager.get("screenLabels/selectType.png", Texture.class), 128, 550, 256, 64);
				batch.draw(manager.get("screenLabels/biodegradable.png", Texture.class), 64, 430, 128, 40);
				batch.draw(manager.get("screenLabels/recyclable.png", Texture.class), 256 - 53, 430, 106, 40);
				batch.draw(manager.get("screenLabels/nonRecyclable.png", Texture.class), 64 + 256, 430, 128, 80);
				drawTypes();
			}

			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/gameRequirements.png", Texture.class),
					gameRequirementsArrowLayer.x + 62 + 10, 690, 400, 64);
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/types.png", Texture.class),
					gameRequirementsArrowLayer.x + 62 + 130, 590, 160, 64);
			if (junkWorldEngines.isBiodegradable()){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class),
						gameRequirementsArrowLayer.x + 62 + 50, 510, 64, 64);	
			}
			if (junkWorldEngines.isRecyclable()){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class),
						gameRequirementsArrowLayer.x + 62 + 170, 510, 64, 64);	
			}
			if (junkWorldEngines.isNonRecyclable()){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class),
						gameRequirementsArrowLayer.x + 62 + 290, 510, 64, 64);	
			}
			batch.draw(manager.get("gameScreenAssets/comboAssets/colors/color.png", Texture.class), gameRequirementsArrowLayer.x + 62 + 130 , 410, 160, 64);
			if (junkWorldEngines.isRed()){
				batch.draw(manager.get("gameScreenAssets/comboAssets/colors/red.png", Texture.class), gameRequirementsArrowLayer.x + 62 + 50, 310, 64, 64);
			}
			if (junkWorldEngines.isOrange()){
				batch.draw(manager.get("gameScreenAssets/comboAssets/colors/orange.png", Texture.class), gameRequirementsArrowLayer.x + 62 + 170, 310, 64, 64);
			}
			if (junkWorldEngines.isYellow()){
				batch.draw(manager.get("gameScreenAssets/comboAssets/colors/yellow.png", Texture.class), gameRequirementsArrowLayer.x + 62 + 290, 310, 64, 64);
			}
			if (junkWorldEngines.isGreen()){
				batch.draw(manager.get("gameScreenAssets/comboAssets/colors/green.png", Texture.class), gameRequirementsArrowLayer.x + 62 + 50, 210, 64, 64);
			}
			if (junkWorldEngines.isBlue()){
				batch.draw(manager.get("gameScreenAssets/comboAssets/colors/blue.png", Texture.class), gameRequirementsArrowLayer.x + 62 + 170, 210, 64, 64);
			}
			if (junkWorldEngines.isPurple()){
				batch.draw(manager.get("gameScreenAssets/comboAssets/colors/purple.png", Texture.class), gameRequirementsArrowLayer.x + 62 + 290, 210, 64, 64);
			}
			drawTutorials();
			batch.end();
			drawSelectedTrashCan();
		}

		if (junkWorldEngines.getGameMode() == 0){
			junkWorldEngines.getTutorialStage().getViewport().setCamera(camera);
			junkWorldEngines.getTutorialStage().draw();
			junkWorldEngines.getTutorialStage().act();
			Gdx.input.setInputProcessor(junkWorldEngines.getTutorialStage());
		}
		else {
			if (!openCanSelectionScreen){
				stageMain.act();
				stageMain.draw();
				stageMain.getViewport().setCamera(camera);
				Gdx.input.setInputProcessor(stageMain);
			}
			else {
				stage.act();
				stage.draw();
				stage.getViewport().setCamera(camera);
				Gdx.input.setInputProcessor(stage);
			}
		}

//		game.adManager.showBannerAds(false);
	}

	private void drawTutorials() {
		if (junkWorldEngines.getGameMode() == 0){
			if (TimeUtils.millis() - delayTutoralArrow >= 800){
				delayTutoralArrow = TimeUtils.millis();
				if (junkWorldEngines.getCurrentArrowNum() + 1 == 3){
					junkWorldEngines.setCurrentArrowNum(0);
				} else junkWorldEngines.setCurrentArrowNum(junkWorldEngines.getCurrentArrowNum() + 1);
			}
			junkWorldEngines.getCurrentArrow().setTexture(manager.get("gameScreenAssets/tutorialAssets/arrow" 
					+ junkWorldEngines.getCurrentArrowNum() + ".png", Texture.class));

			switch (junkWorldEngines.getCurrentTutorial()){
			case 0 : {
				junkWorldEngines.getTutorialLayer().setPosition(96, 240);
				junkWorldEngines.getClickContinueTutorial().setBounds(0, 0, 512, 800);
				junkWorldEngines.getClickContinueTutorial().setTouchable(Touchable.enabled);
				trashCanSlot4.setTouchable(Touchable.disabled);
				randomize.setTouchable(Touchable.disabled);
				leftArrow.setTouchable(Touchable.disabled);
				downArrow.setTouchable(Touchable.disabled);
				next.setTouchable(Touchable.disabled);
				biodegradable.setTouchable(Touchable.disabled);
				recyclable.setTouchable(Touchable.disabled);
				nonRecyclable.setTouchable(Touchable.disabled);
				select.setTouchable(Touchable.disabled);
			}; break;
			case 1 : {
				junkWorldEngines.getTutorialLayer().setPosition(50, 400);
				junkWorldEngines.getCurrentArrow().setPosition(52 + 192 + 128, 250 - 64);
				junkWorldEngines.getCurrentArrow().setRotation(0);
				junkWorldEngines.getCurrentArrow().draw(batch);
				junkWorldEngines.getClickContinueTutorial().setTouchable(Touchable.disabled);
				trashCanSlot4.setTouchable(Touchable.enabled);
			}; break;
			case 2 : {
				junkWorldEngines.getTutorialLayer().setPosition(0, 0);
				junkWorldEngines.getCurrentArrow().setPosition(52 + 192 + 128, 250 - 100 - 64);
				junkWorldEngines.getCurrentArrow().setRotation(0);
				junkWorldEngines.getCurrentArrow().draw(batch);
				trashCanSlot4.setTouchable(Touchable.disabled);
				leftArrow.setTouchable(Touchable.enabled);
				downArrow.setTouchable(Touchable.enabled);
				slotType4.setTouchable(Touchable.enabled);
			}; break;
			case 3 : {
				junkWorldEngines.getTutorialLayer().setPosition(30, 800 - 320);
				junkWorldEngines.getCurrentArrow().setPosition(52 + 192 + 128, 250 - 100 - 64);
				leftArrow.setTouchable(Touchable.disabled);
				downArrow.setTouchable(Touchable.disabled);
				slotType4.setTouchable(Touchable.disabled);
				biodegradable.setTouchable(Touchable.enabled);
				recyclable.setTouchable(Touchable.enabled);
				nonRecyclable.setTouchable(Touchable.enabled);
				select.setTouchable(Touchable.enabled);
			}; break;
			case 4: {
				junkWorldEngines.getTutorialLayer().setPosition(90, 800 - 320);
				biodegradable.setTouchable(Touchable.disabled);
				recyclable.setTouchable(Touchable.disabled);
				nonRecyclable.setTouchable(Touchable.disabled);
				select.setTouchable(Touchable.disabled);
				randomize.setTouchable(Touchable.enabled);
				next.setTouchable(Touchable.enabled);
			}; break;
			default: break;
			}
			batch.draw(junkWorldEngines.getTutorial().get(junkWorldEngines.getCurrentTutorial()),
					junkWorldEngines.getTutorialLayer().x, junkWorldEngines.getTutorialLayer().y);
			junkWorldEngines.getQuitTutorial().setBounds(junkWorldEngines.getTutorialLayer().x, junkWorldEngines.getTutorialLayer().y, 120, 50);
			//		TODO
		}
	}

	private void setActor() {
		if (setActors){
			setActors = false;

			back.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					switch (junkWorldEngines.getGameSelection()){
					case 1: {
						backClicked = true;
//						game.adManager.showBannerAds(true);
						isLoading = true;
						unloadManager();
						disposeAssets();
						game.setScreen(new DifficultySelectionScreen(game, manager, junkWorldEngines));
					}; break;
					case 2: {
						backClicked = true;
//						game.adManager.showBannerAds(true);
						isLoading = true;
						unloadManager();
						disposeAssets();
						game.setScreen(new JunkWorldMapScreen(game, manager, junkWorldEngines));
					}; break;
					default: break;
					}
					return true;
				}
			});
			next.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					nextClicked = true;
					if (trashCan1Properties.getTrashCan() != 0 || 
							trashCan2Properties.getTrashCan() != 0 || 
							trashCan3Properties.getTrashCan() != 0 || 
							trashCan4Properties.getTrashCan() != 0){
						chooseCan = false;
						if (trashCan1Properties.getTrashCan() != 0){
							if (trashCan1Properties.getTrashCanType() == 0){
								chooseType = true;
							} else chooseType = false;
						}
						if (trashCan2Properties.getTrashCan() != 0){
							if (trashCan2Properties.getTrashCanType() == 0){
								chooseType = true;
							} else chooseType = false;
						} 
						if (trashCan3Properties.getTrashCan() != 0){
							if (trashCan3Properties.getTrashCanType() == 0){
								chooseType = true;
							} else chooseType = false;
						}
						if (trashCan4Properties.getTrashCan() != 0){
							if (trashCan4Properties.getTrashCanType() == 0){
								chooseType = true;
							} else chooseType = false;
						}
					}
					else if (trashCan1Properties.getTrashCan() == 0 && 
							trashCan2Properties.getTrashCan() == 0 && 
							trashCan3Properties.getTrashCan() == 0 && 
							trashCan4Properties.getTrashCan() == 0) {
						chooseCan = true;
					}
					if ((trashCan1Properties.getTrashCanType() == 1 ||
							trashCan2Properties.getTrashCanType() == 1||
							trashCan3Properties.getTrashCanType() == 1||
							trashCan4Properties.getTrashCanType() == 1) && junkWorldEngines.isBiodegradable()){
						biodegradablePres = true;
					} else biodegradablePres = false;
					if ((trashCan1Properties.getTrashCanType() == 2 ||
							trashCan2Properties.getTrashCanType() == 2||
							trashCan3Properties.getTrashCanType() == 2||
							trashCan4Properties.getTrashCanType() == 2) && junkWorldEngines.isRecyclable()){
						recyclablePres = true;
					} else recyclablePres = false;
					if ((trashCan1Properties.getTrashCanType() == 3 ||
							trashCan2Properties.getTrashCanType() == 3||
							trashCan3Properties.getTrashCanType() == 3||
							trashCan4Properties.getTrashCanType() == 3) && junkWorldEngines.isNonRecyclable()){
						nonRecyclablePres = true;
					} else nonRecyclablePres = false;
					if (!chooseCan &&
							!chooseType &&
							biodegradablePres &&
							recyclablePres &&
							nonRecyclablePres){
						if (junkWorldEngines.getGameMode() == 0){
							junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
						}
						isLoading = true;
						unloadManager();
						disposeAssets();
						game.setScreen(new TrashCanColorScreen(game, trashCan1Properties, trashCan2Properties,
								trashCan3Properties, trashCan4Properties, manager, junkWorldEngines));
					}
					return true;
				}
			});
			trashCanSlot1.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					switch (trashCan1Properties.getTrashCan()) {
					case 1: currentTrashCanNum = 1; break;
					case 2: currentTrashCanNum = 2; break;
					case 3: currentTrashCanNum = 3; break;
					case 4: currentTrashCanNum = 4; break;
					case 5: currentTrashCanNum = 5; break;
					case 6: currentTrashCanNum = 6; break;
					default : currentTrashCanNum = 0; break;
					}
					if (currentSelectedCan == 1){
						currentSelectedCan = 0;
					}
					else {
						currentSelectedCan = 1;
					}
					return true;
				}
			});
			trashCanSlot2.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					switch (trashCan2Properties.getTrashCan()) {
					case 1: currentTrashCanNum = 1; break;
					case 2: currentTrashCanNum = 2; break;
					case 3: currentTrashCanNum = 3; break;
					case 4: currentTrashCanNum = 4; break;
					case 5: currentTrashCanNum = 5; break;
					case 6: currentTrashCanNum = 6; break;
					default : currentTrashCanNum = 0; break;
					}
					if (currentSelectedCan == 2){
						currentSelectedCan = 0;
					}
					else {
						currentSelectedCan = 2;
					}
					return true;
				}
			});
			trashCanSlot3.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					switch (trashCan3Properties.getTrashCan()) {
					case 1: currentTrashCanNum = 1; break;
					case 2: currentTrashCanNum = 2; break;
					case 3: currentTrashCanNum = 3; break;
					case 4: currentTrashCanNum = 4; break;
					case 5: currentTrashCanNum = 5; break;
					case 6: currentTrashCanNum = 6; break;
					default : currentTrashCanNum = 0; break;
					}
					if (currentSelectedCan == 3){
						currentSelectedCan = 0;
					}
					else {
						currentSelectedCan = 3;
					}
					return true;
				}
			});
			trashCanSlot4.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (junkWorldEngines.getGameMode() == 0){
						junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
					}
					switch (trashCan4Properties.getTrashCan()) {
					case 1: currentTrashCanNum = 1; break;
					case 2: currentTrashCanNum = 2; break;
					case 3: currentTrashCanNum = 3; break;
					case 4: currentTrashCanNum = 4; break;
					case 5: currentTrashCanNum = 5; break;
					case 6: currentTrashCanNum = 6; break;
					default : currentTrashCanNum = 0; break;
					}
					if (currentSelectedCan == 4){
						currentSelectedCan = 0;
					}
					else {
						currentSelectedCan = 4;
					}
					return true;
				}
			});

			randomize.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					delayRandom = TimeUtils.nanoTime();
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			//			TODO

			gameRequirementsArrow.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					removeRegions = true;
					delayGameReqMovement = TimeUtils.nanoTime();
					return super.touchDown(event, x, y, pointer, button);
				}

			});

			slotType1.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (!(trashCan1Properties.getTrashCan() == 0)){
						openCanSelectionScreen = true;
						currentSlotNum = 1;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			slotType2.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (!(trashCan2Properties.getTrashCan() == 0)){
						openCanSelectionScreen = true;
						currentSlotNum = 2;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			slotType3.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (!(trashCan3Properties.getTrashCan() == 0)){
						openCanSelectionScreen = true;
						currentSlotNum = 3;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			slotType4.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (!(trashCan4Properties.getTrashCan() == 0)){
						if (junkWorldEngines.getGameMode() == 0){
							junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
						}
						openCanSelectionScreen = true;
						currentSlotNum = 4;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			zoom.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					zoomDesc = true;
					removeRegions = true;
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			ok.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (zoomDesc){
						zoomDesc = false;
						setActors = true;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
		}

		if (removeRegions){
			removeRegions = false;

			back.clearListeners();
			next.clearListeners();
			trashCanSlot1.clearListeners();
			trashCanSlot2.clearListeners();
			trashCanSlot3.clearListeners();
			trashCanSlot4.clearListeners();
			leftArrow.clearListeners();
			downArrow.clearListeners();
			slotType1.clearListeners();
			slotType2.clearListeners();
			slotType3.clearListeners();
			slotType4.clearListeners();
			randomize.clearListeners();
			zoom.clearListeners();
		}
	}

	private void configureAssets() {
		if (setRegions){
			setRegions = false;
			backButtonRegion = new TextureRegion(manager.get("buttons/backButton.png", Texture.class));
			nextButtonRegion = new TextureRegion(manager.get("buttons/nextButton.png", Texture.class));
			backButtonClickedRegion = new TextureRegion(manager.get("buttons/backButtonClicked.png", Texture.class));
			nextButtonClickedRegion = new TextureRegion(manager.get("buttons/nextButtonClicked.png", Texture.class));
		}
	}

	private void setCanDescription() {
		switch (currentTrashCanNum) {
		case 1: {
			if (!zoomDesc){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/description.png", Texture.class),
						113, 398, 180, 180);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/description.png", Texture.class),
						256 - (384 / 2), 400 - (384 / 2), 384, 384);
			}
		}; break;
		case 2: {
			if (!zoomDesc){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/description.png", Texture.class),
						113, 398, 180, 180);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/description.png", Texture.class),
						256 - (384 / 2), 400 - (384 / 2), 384, 384);
			}
		}; break;
		case 3: {
			if (!zoomDesc){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/description.png", Texture.class),
						113, 398, 180, 180);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/description.png", Texture.class),
						256 - (384 / 2), 400 - (384 / 2), 384, 384);
			}
		}; break;
		case 4: {
			if (!zoomDesc){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/description.png", Texture.class),
						113, 398, 180, 180);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/description.png", Texture.class),
						256 - (384 / 2), 400 - (384 / 2), 384, 384);
			}
		}; break;
		case 5: {
			if (!zoomDesc){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/description.png", Texture.class),
						113, 398, 180, 180);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/description.png", Texture.class),
						256 - (384 / 2), 400 - (384 / 2), 384, 384);
			}
		}; break;
		case 6: {
			if (!zoomDesc){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/description.png", Texture.class),
						113, 398, 180, 180);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/description.png", Texture.class),
						256 - (384 / 2), 400 - (384 / 2), 384, 384);
			}
		}; break;
		default : batch.draw(manager.get("screenLabels/noCanSelected.png", Texture.class),
				113, 398, 180, 180); break;
		}
		batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/zoom.png", Texture.class), 113, 398, 180, 180);
		if (zoomDesc){
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/okButton.png", Texture.class), okLayer.x, okLayer.y, okLayer.width,
					okLayer.height);
		}
	}

	private void setDelay() {
		if (System.currentTimeMillis() - selectCanInt >= 500){
			if (currentFrame1 == 1){
				currentFrame1 = 2;
			}
			else {
				currentFrame1 = 1;
			}
			selectCanInt = System.currentTimeMillis();
		}
		if (System.currentTimeMillis() - selectTypeInt >= 300){
			if (currentFrame2 == 1){
				currentFrame2 = 2;
			}
			else {
				currentFrame2 = 1;
			}
			selectTypeInt = System.currentTimeMillis();
		}

		if (System.currentTimeMillis() - delayGameArrow >= 400){
			delayGameArrow = System.currentTimeMillis();
			if (!gameReqOpened){
				if (currentRequirementsArrayL.getTexture() == manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class)){
					currentRequirementsArrayL.setTexture(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL2.png", Texture.class));
				}
				else currentRequirementsArrayL.setTexture(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class));
			}
			else {
				if (currentRequirementsArrayL.getTexture() == manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class)){
					currentRequirementsArrayL.setTexture(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR2.png", Texture.class));
				}
				else currentRequirementsArrayL.setTexture(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class));
			}
		}

		if (delayGameReqMovement != 0){
			if (TimeUtils.nanoTime() - delayGameReqMovement >= 1000){
				if (!gameReqOpened){
					gameRequirementsArrowLayer.x -= 50;
					delayGameReqMovement = TimeUtils.nanoTime();
					if (gameRequirementsArrowLayer.x <= 10){
						delayGameReqMovement = 0;
						gameReqOpened = true;
					}
				}
				else {
					gameRequirementsArrowLayer.x += 50;
					delayGameReqMovement = TimeUtils.nanoTime();
					if (gameRequirementsArrowLayer.x >= 454){
						delayGameReqMovement = 0;
						gameReqOpened = false;
						setActors = true;
					}
				}
			}
		}

		if (delayRandom != 0){
			if (TimeUtils.nanoTime() - delayRandom >= 10000){
				delayRandom = 0;
				currentSelectedCan = 0;
				if (junkWorldEngines.isBiodegradable()){
					countCan += 1;
				}
				if (junkWorldEngines.isRecyclable()){
					countCan += 1;
				}
				if (junkWorldEngines.isNonRecyclable()){
					countCan += 1;
				}
				if (!junkWorldEngines.isPalette3Unlocked()){
					countCan += 1;
				}

				while (countCan != 0){
					trashCan1Properties.setTrashCan(randomCans.get(generator.nextInt(randomCans.size)));
					if (trashCan1Properties.getTrashCan() != 0){
						countCan -= 1;
					}

					trashCan2Properties.setTrashCan(randomCans.get(generator.nextInt(randomCans.size)));
					if (trashCan2Properties.getTrashCan() != 0){
						countCan -= 1;
					}

					trashCan3Properties.setTrashCan(randomCans.get(generator.nextInt(randomCans.size)));
					if (trashCan3Properties.getTrashCan() != 0){
						countCan -= 1;
					}

					trashCan4Properties.setTrashCan(randomCans.get(generator.nextInt(randomCans.size)));
					if (trashCan4Properties.getTrashCan() != 0){
						countCan -= 1;
					}

					if (countCan < 0){
						countCan = 0;
					}
					else if (countCan > 0){
						countCan = 0;
						if (junkWorldEngines.isBiodegradable()){
							countCan += 1;
						}
						if (junkWorldEngines.isRecyclable()){
							countCan += 1;
						}
						if (junkWorldEngines.isNonRecyclable()){
							countCan += 1;
						}
						if (!junkWorldEngines.isPalette3Unlocked()){
							countCan += 1;
						}
					}
				} 

				if (junkWorldEngines.isBiodegradable()){
					randomTypes.add(1);
				}
				if (junkWorldEngines.isRecyclable()){
					randomTypes.add(2);
				}
				if (junkWorldEngines.isNonRecyclable()){
					randomTypes.add(3);
				}

				if (trashCan1Properties.getTrashCan() != 0){
					int random = randomTypes.random();
					junkWorldEngines.setPreviousTrashCan1Type(random);
					trashCan1Properties.setTrashCanType(random);
					randomTypes.removeValue(trashCan1Properties.getTrashCanType(), true);
					if (randomTypes.size == 0){
						if (junkWorldEngines.isBiodegradable()){
							randomTypes.add(1);
						}
						if (junkWorldEngines.isRecyclable()){
							randomTypes.add(2);
						}
						if (junkWorldEngines.isNonRecyclable()){
							randomTypes.add(3);
						}
					}
				}
				if (trashCan2Properties.getTrashCan() != 0){
					int random = randomTypes.random();
					junkWorldEngines.setPreviousTrashCan2Type(random);
					trashCan2Properties.setTrashCanType(random);
					randomTypes.removeValue(trashCan2Properties.getTrashCanType(), true);
					if (randomTypes.size == 0){
						if (junkWorldEngines.isBiodegradable()){
							randomTypes.add(1);
						}
						if (junkWorldEngines.isRecyclable()){
							randomTypes.add(2);
						}
						if (junkWorldEngines.isNonRecyclable()){
							randomTypes.add(3);
						}
					}
				}
				if (trashCan3Properties.getTrashCan() != 0){
					int random = randomTypes.random();
					junkWorldEngines.setPreviousTrashCan3Type(random);
					trashCan3Properties.setTrashCanType(random);
					randomTypes.removeValue(trashCan3Properties.getTrashCanType(), true);
					if (randomTypes.size == 0){
						if (junkWorldEngines.isBiodegradable()){
							randomTypes.add(1);
						}
						if (junkWorldEngines.isRecyclable()){
							randomTypes.add(2);
						}
						if (junkWorldEngines.isNonRecyclable()){
							randomTypes.add(3);
						}
					}
				}
				if (trashCan4Properties.getTrashCan() != 0){
					int random = randomTypes.random();
					junkWorldEngines.setPreviousTrashCan4Type(random);
					trashCan4Properties.setTrashCanType(random);
					randomTypes.removeValue(trashCan4Properties.getTrashCanType(), true);
					if (randomTypes.size == 0){
						if (junkWorldEngines.isBiodegradable()){
							randomTypes.add(1);
						}
						if (junkWorldEngines.isRecyclable()){
							randomTypes.add(2);
						}
						if (junkWorldEngines.isNonRecyclable()){
							randomTypes.add(3);
						}
					}
				}
				randomTypes.clear();
			}
		}
	}

	private void drawSelectedTrashCan() {
		if (currentSelectedCan == 1){
			switch (currentTrashCanNum) {
			case 0 : trashCan1Properties.setTrashCan(0); break;
			case 1 : trashCan1Properties.setTrashCan(1); break;
			case 2 : trashCan1Properties.setTrashCan(2); break;
			case 3 : trashCan1Properties.setTrashCan(3); break;
			case 4 : trashCan1Properties.setTrashCan(4); break;
			case 5 : trashCan1Properties.setTrashCan(5); break;
			case 6 : trashCan1Properties.setTrashCan(6); break;
			}
		}
		else if (currentSelectedCan == 2){
			switch (currentTrashCanNum) {
			case 0 : trashCan2Properties.setTrashCan(0); break;
			case 1 : trashCan2Properties.setTrashCan(1); break;
			case 2 : trashCan2Properties.setTrashCan(2); break;
			case 3 : trashCan2Properties.setTrashCan(3); break;
			case 4 : trashCan2Properties.setTrashCan(4); break;
			case 5 : trashCan2Properties.setTrashCan(5); break;
			case 6 : trashCan2Properties.setTrashCan(6); break;
			}
		}
		else if (currentSelectedCan == 3){
			switch (currentTrashCanNum) {
			case 0 : trashCan3Properties.setTrashCan(0); break;
			case 1 : trashCan3Properties.setTrashCan(1); break;
			case 2 : trashCan3Properties.setTrashCan(2); break;
			case 3 : trashCan3Properties.setTrashCan(3); break;
			case 4 : trashCan3Properties.setTrashCan(4); break;
			case 5 : trashCan3Properties.setTrashCan(5); break;
			case 6 : trashCan3Properties.setTrashCan(6); break;
			}
		}
		else if (currentSelectedCan == 4){
			switch (currentTrashCanNum) {
			case 0 : trashCan4Properties.setTrashCan(0); break;
			case 1 : trashCan4Properties.setTrashCan(1); break;
			case 2 : trashCan4Properties.setTrashCan(2); break;
			case 3 : trashCan4Properties.setTrashCan(3); break;
			case 4 : trashCan4Properties.setTrashCan(4); break;
			case 5 : trashCan4Properties.setTrashCan(5); break;
			case 6 : trashCan4Properties.setTrashCan(6); break;
			}
		}

		if (currentSelectedCan != 0){
			if (currentTrashCanNum - 1 < 0){
				lastTrashCanNum = trashCans.size - 1;
			}
			else {
				lastTrashCanNum = currentTrashCanNum - 1;
			}
			if (currentTrashCanNum + 1 == trashCans.size){
				nextTrashCanNum = 0;
			}
			else {
				nextTrashCanNum = currentTrashCanNum + 1;
			}
		}
	}

	private void drawBlinks() {
		if (currentFrame1 == 1){
			batch.draw(manager.get("screenLabels/selectCan.png", Texture.class), 128, 500, 256, 64);
		}
		else {
			batch.draw(manager.get("screenLabels/selectCan2.png", Texture.class), 128, 500, 256, 64);
		}
	}

	private void drawAssets() {
		batch.draw(manager.get("backgrounds/firstBackground.png", Texture.class), 0, 0);
		batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader.png", Texture.class), 0, 0, 512, 800);
		batch.draw(manager.get("backgrounds/blueFrame.png", Texture.class), 0, 0);
		batch.draw(manager.get("backgrounds/brickFrame.png", Texture.class), 64, 350);
		batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
				trashCanSlotLayer1.x, trashCanSlotLayer1.y, trashCanSlotLayer1.width, trashCanSlotLayer1.height);
		batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
				trashCanSlotLayer2.x, trashCanSlotLayer2.y, trashCanSlotLayer2.width, trashCanSlotLayer2.height);
		batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class), 
				trashCanSlotLayer3.x, trashCanSlotLayer3.y, trashCanSlotLayer3.width, trashCanSlotLayer3.height);
		batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
				trashCanSlotLayer4.x, trashCanSlotLayer4.y, trashCanSlotLayer4.width, trashCanSlotLayer4.height);

		if (!backClicked){
			batch.draw(backButtonRegion, 64, 64, 100, 50);
		}
		else {
			batch.draw(backButtonClickedRegion, 64, 64, 100, 50);
		}
		if (!nextClicked){
			batch.draw(nextButtonRegion, 350, 64, 100, 50);
		}
		else {
			batch.draw(nextButtonClickedRegion, 350, 64, 100, 50);
		}

		if (chooseCan){
			batch.draw(manager.get("screenLabels/chooseACan.png", Texture.class), 128, 0, 256, 50);
		}
		else if (chooseType){
			batch.draw(manager.get("screenLabels/chooseType.png", Texture.class), 128, 0, 256, 50);
		}
		else if (!biodegradablePres){
			batch.draw(manager.get("screenLabels/noBiodegradableCan.png", Texture.class), 128, 0, 256, 50);
		}
		else if (!recyclablePres){
			batch.draw(manager.get("screenLabels/noRecyclableCan.png", Texture.class), 128, 0, 256, 50);
		}
		else if (!nonRecyclablePres){
			batch.draw(manager.get("screenLabels/noNonRecyclableCan.png", Texture.class), 128, 0, 256, 50);
		}
	}

	private void setActors() {

		biodegradable.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentSlotNum == 1){
					junkWorldEngines.setPreviousTrashCan1Type(1);
					trashCan1Properties.setTrashCanType(1);
				}
				else if (currentSlotNum == 2){
					junkWorldEngines.setPreviousTrashCan2Type(1);
					trashCan2Properties.setTrashCanType(1);
				}
				else if (currentSlotNum == 3){
					junkWorldEngines.setPreviousTrashCan3Type(1);
					trashCan3Properties.setTrashCanType(1);
				}
				else if (currentSlotNum == 4){
					junkWorldEngines.setPreviousTrashCan4Type(1);
					trashCan4Properties.setTrashCanType(1);
				}

				if (clickedB){
					clickedB = false;
				}
				else {
					clickedB = true;
					clickedR = false;
					clickedNR = false;
				}
				return true;
			}
		});
		recyclable.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentSlotNum == 1){
					junkWorldEngines.setPreviousTrashCan1Type(2);
					trashCan1Properties.setTrashCanType(2);
				}
				else if (currentSlotNum == 2){
					junkWorldEngines.setPreviousTrashCan2Type(2);
					trashCan2Properties.setTrashCanType(2);
				}
				else if (currentSlotNum == 3){
					junkWorldEngines.setPreviousTrashCan3Type(2);
					trashCan3Properties.setTrashCanType(2);
				}
				else if (currentSlotNum == 4){
					junkWorldEngines.setPreviousTrashCan4Type(2);
					trashCan4Properties.setTrashCanType(2);
				}

				if (clickedR){
					clickedR = false;
				}
				else {
					clickedB = false;
					clickedR = true;
					clickedNR = false;
				}
				return true;
			}
		});
		nonRecyclable.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (currentSlotNum == 1){
					junkWorldEngines.setPreviousTrashCan1Type(3);
					trashCan1Properties.setTrashCanType(3);
				}
				else if (currentSlotNum == 2){
					junkWorldEngines.setPreviousTrashCan2Type(3);
					trashCan2Properties.setTrashCanType(3);
				}
				else if (currentSlotNum == 3){
					junkWorldEngines.setPreviousTrashCan3Type(3);
					trashCan3Properties.setTrashCanType(3);
				}
				else if (currentSlotNum == 4){
					junkWorldEngines.setPreviousTrashCan4Type(3);
					trashCan4Properties.setTrashCanType(3);
				}

				if (clickedNR){
					clickedNR = false;
				}
				else {
					clickedB = false;
					clickedR = false;
					clickedNR = true;
				}
				return true;
			}
		});
		select.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (junkWorldEngines.getGameMode() == 0 && (clickedNR || clickedR || clickedB)){
					junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
				openCanSelectionScreen = false; 
				clickedB = false;
				clickedR = false;
				clickedNR = false;
				currentSlotNum = 0;
				}
				else {
					openCanSelectionScreen = false; 
					clickedB = false;
					clickedR = false;
					clickedNR = false;
					currentSlotNum = 0;
				}
				return true;
			}
		});

		stage = new Stage();
		stage.addActor(biodegradable);
		stage.addActor(recyclable);
		stage.addActor(nonRecyclable);
		stage.addActor(select);
	}

	private void displaySelectedTrashCans() {
		if (currentSelectedCan == 1){
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlotsClicked.png", Texture.class),
					trashCanSlotLayer1.x, trashCanSlotLayer1.y, trashCanSlotLayer1.width, trashCanSlotLayer1.height);
		}
		else if (currentSelectedCan == 2){
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlotsClicked.png", Texture.class),
					trashCanSlotLayer2.x, trashCanSlotLayer2.y, trashCanSlotLayer2.width, trashCanSlotLayer2.height);
		}
		else if (currentSelectedCan == 3){
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlotsClicked.png", Texture.class),
					trashCanSlotLayer3.x, trashCanSlotLayer3.y, trashCanSlotLayer3.width, trashCanSlotLayer3.height);
		}
		else if (currentSelectedCan == 4){
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlotsClicked.png", Texture.class),
					trashCanSlotLayer4.x, trashCanSlotLayer4.y, trashCanSlotLayer4.width, trashCanSlotLayer4.height);
		}

		if ((trashCan1Properties.getTrashCan()) == 1){
			switch (trashCan1Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultB.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultNR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/default.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96);
			}
		}
		else if ((trashCan1Properties.getTrashCan()) == 2){
			switch (trashCan1Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultB.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultNR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/default.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96);
			}
		}
		else if ((trashCan1Properties.getTrashCan()) == 3){
			switch (trashCan1Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultB.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultNR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/default.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96);
			}
		}
		else if ((trashCan1Properties.getTrashCan()) == 4){
			switch (trashCan1Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultB.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultNR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/default.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96);
			}
		}
		else if ((trashCan1Properties.getTrashCan()) == 5){
			switch (trashCan1Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultB.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultNR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/default.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96);
			}
		}
		else if ((trashCan1Properties.getTrashCan()) == 6){
			switch (trashCan1Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultB.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultNR.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/default.png", Texture.class),
					trashCanSlotLayer1.x - 16, trashCanSlotLayer1.y - 16, 96, 96);
			}
		}

		if ((trashCan2Properties.getTrashCan()) == 1){
			switch (trashCan2Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultB.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultNR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/default.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96);
			}
		}
		else if ((trashCan2Properties.getTrashCan()) == 2){
			switch (trashCan2Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultB.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultNR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/default.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96);
			}
		}
		else if ((trashCan2Properties.getTrashCan()) == 3){
			switch (trashCan2Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultB.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultNR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/default.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96);
			}
		}
		else if ((trashCan2Properties.getTrashCan()) == 4){
			switch (trashCan2Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultB.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultNR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/default.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96);
			}
		}
		else if ((trashCan2Properties.getTrashCan()) == 5){
			switch (trashCan2Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultB.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultNR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/default.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96);
			}
		}
		else if ((trashCan2Properties.getTrashCan()) == 6){
			switch (trashCan2Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultB.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultNR.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/default.png", Texture.class),
					trashCanSlotLayer2.x - 16, trashCanSlotLayer2.y - 16, 96, 96);
			}
		}

		if ((trashCan3Properties.getTrashCan()) == 1){
			switch (trashCan3Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultB.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultNR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/default.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96);
			}
		}
		else if ((trashCan3Properties.getTrashCan()) == 2){
			switch (trashCan3Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultB.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultNR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/default.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96);
			}
		}
		else if ((trashCan3Properties.getTrashCan()) == 3){
			switch (trashCan3Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultB.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultNR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/default.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96);
			}
		}
		else if ((trashCan3Properties.getTrashCan()) == 4){
			switch (trashCan3Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultB.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultNR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/default.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96);
			}
		}
		else if ((trashCan3Properties.getTrashCan()) == 5){
			switch (trashCan3Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultB.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultNR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/default.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96);
			}
		}
		else if ((trashCan3Properties.getTrashCan()) == 6){
			switch (trashCan3Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultB.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultNR.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/default.png", Texture.class),
					trashCanSlotLayer3.x - 16, trashCanSlotLayer3.y - 16, 96, 96);
			}
		}

		if ((trashCan4Properties.getTrashCan()) == 1){
			switch (trashCan4Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultB.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/defaultNR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/default.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96);
			}
		}
		else if ((trashCan4Properties.getTrashCan()) == 2){
			switch (trashCan4Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultB.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/defaultNR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/default.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96);
			}
		}
		else if ((trashCan4Properties.getTrashCan()) == 3){
			switch (trashCan4Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultB.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/defaultNR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/default.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96);
			}
		}
		else if ((trashCan4Properties.getTrashCan()) == 4){
			switch (trashCan4Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultB.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/defaultNR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/default.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96);
			}
		}
		else if ((trashCan4Properties.getTrashCan()) == 5){
			switch (trashCan4Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultB.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/defaultNR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/default.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96);
			}
		}
		else if ((trashCan4Properties.getTrashCan()) == 6){
			switch (trashCan4Properties.getTrashCanType()) {
			case 1: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultB.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 2: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			case 3: batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/defaultNR.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96); break;
			default : batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/default.png", Texture.class),
					trashCanSlotLayer4.x - 16, trashCanSlotLayer4.y - 16, 96, 96);
			}
		}
	}

	private void displayTrashCanTypSelection() {
		if (!(trashCan1Properties.getTrashCan() == 0)){
			if (currentFrame2 == 1){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class),
						selectSlot1TypeLayer.x, selectSlot1TypeLayer.y, selectSlot1TypeLayer.width, selectSlot1TypeLayer.height);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect2.png", Texture.class),
						selectSlot1TypeLayer.x, selectSlot1TypeLayer.y, selectSlot1TypeLayer.width, selectSlot1TypeLayer.height);
			}
			if (trashCan1Properties.getTrashCanType() == 1){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class),
						selectSlot1TypeLayer.x, selectSlot1TypeLayer.y, selectSlot1TypeLayer.width, selectSlot1TypeLayer.height);
			}
			else if (trashCan1Properties.getTrashCanType() == 2){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class),
						selectSlot1TypeLayer.x, selectSlot1TypeLayer.y, selectSlot1TypeLayer.width, selectSlot1TypeLayer.height);
			}
			else if (trashCan1Properties.getTrashCanType() == 3){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class),
						selectSlot1TypeLayer.x, selectSlot1TypeLayer.y, selectSlot1TypeLayer.width, selectSlot1TypeLayer.height);
			}
		}
		else {
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class),
					selectSlot1TypeLayer.x, selectSlot1TypeLayer.y, selectSlot1TypeLayer.width, selectSlot1TypeLayer.height);
		}

		if (!(trashCan2Properties.getTrashCan() == 0)){
			if (currentFrame2 == 1){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class),
						selectSlot2TypeLayer.x, selectSlot2TypeLayer.y, selectSlot2TypeLayer.width, selectSlot2TypeLayer.height);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect2.png", Texture.class),
						selectSlot2TypeLayer.x, selectSlot2TypeLayer.y, selectSlot2TypeLayer.width, selectSlot2TypeLayer.height);
			}
			if (trashCan2Properties.getTrashCanType() == 1){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class),
						selectSlot2TypeLayer.x, selectSlot2TypeLayer.y, selectSlot2TypeLayer.width, selectSlot2TypeLayer.height);
			}
			else if (trashCan2Properties.getTrashCanType() == 2){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class),
						selectSlot2TypeLayer.x, selectSlot2TypeLayer.y, selectSlot2TypeLayer.width, selectSlot2TypeLayer.height);
			}
			else if (trashCan2Properties.getTrashCanType() == 3){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class),
						selectSlot2TypeLayer.x, selectSlot2TypeLayer.y, selectSlot2TypeLayer.width, selectSlot2TypeLayer.height);
			}
		}
		else {
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class),
					selectSlot2TypeLayer.x, selectSlot2TypeLayer.y, selectSlot2TypeLayer.width, selectSlot2TypeLayer.height);
		}

		if (!(trashCan3Properties.getTrashCan() == 0)){
			if (currentFrame2 == 1){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class),
						selectSlot3TypeLayer.x, selectSlot3TypeLayer.y, selectSlot3TypeLayer.width, selectSlot3TypeLayer.height);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect2.png", Texture.class),
						selectSlot3TypeLayer.x, selectSlot3TypeLayer.y, selectSlot3TypeLayer.width, selectSlot3TypeLayer.height);
			}
			if (trashCan3Properties.getTrashCanType() == 1){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class),
						selectSlot3TypeLayer.x, selectSlot3TypeLayer.y, selectSlot3TypeLayer.width, selectSlot3TypeLayer.height);
			}
			else if (trashCan3Properties.getTrashCanType() == 2){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class),
						selectSlot3TypeLayer.x, selectSlot3TypeLayer.y, selectSlot3TypeLayer.width, selectSlot3TypeLayer.height);
			}
			else if (trashCan3Properties.getTrashCanType() == 3){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class),
						selectSlot3TypeLayer.x, selectSlot3TypeLayer.y, selectSlot3TypeLayer.width, selectSlot3TypeLayer.height);
			}
		}
		else {
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class),
					selectSlot3TypeLayer.x, selectSlot3TypeLayer.y, selectSlot3TypeLayer.width, selectSlot3TypeLayer.height);
		}

		if (!(trashCan4Properties.getTrashCan() == 0)){
			if (currentFrame2 == 1){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class),
						selectSlot4TypeLayer.x, selectSlot4TypeLayer.y, selectSlot4TypeLayer.width, selectSlot4TypeLayer.height);
			}
			else {
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect2.png", Texture.class),
						selectSlot4TypeLayer.x, selectSlot4TypeLayer.y, selectSlot4TypeLayer.width, selectSlot4TypeLayer.height);
			}
			if (trashCan4Properties.getTrashCanType() == 1){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class),
						selectSlot4TypeLayer.x, selectSlot4TypeLayer.y, selectSlot4TypeLayer.width, selectSlot4TypeLayer.height);
			}
			else if (trashCan4Properties.getTrashCanType() == 2){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class),
						selectSlot4TypeLayer.x, selectSlot4TypeLayer.y, selectSlot4TypeLayer.width, selectSlot4TypeLayer.height);
			}
			else if (trashCan4Properties.getTrashCanType() == 3){
				batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class),
						selectSlot4TypeLayer.x, selectSlot4TypeLayer.y, selectSlot4TypeLayer.width, selectSlot4TypeLayer.height);
			}
		}
		else {
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/openTypeSelect.png", Texture.class),
					selectSlot4TypeLayer.x, selectSlot4TypeLayer.y, selectSlot4TypeLayer.width, selectSlot4TypeLayer.height);
		}
	}

	private void setSelectTypeActors() {
		selectSlot1Type = new Actor();
		selectSlot2Type = new Actor();
		selectSlot3Type = new Actor();
		selectSlot4Type = new Actor();

		selectSlot1Type.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				openCanSelectionScreen = true;
				currentSlotNum = 1;
				return true;
			}
		});
		selectSlot2Type.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				openCanSelectionScreen = true;
				currentSlotNum = 2;
				return true;
			}
		});
		selectSlot3Type.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				openCanSelectionScreen = true;
				currentSlotNum = 3;
				return true;
			}
		});
		selectSlot4Type.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				openCanSelectionScreen = true;
				currentSlotNum = 4;
				return true;
			}
		});
	}

	private void drawTypes() {
		if (!clickedB){
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class),
					biodegradableLayer.x, biodegradableLayer.y, biodegradableLayer.width, biodegradableLayer.height);
		}
		else {
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradableClicked.png", Texture.class),
					biodegradableLayer.x, biodegradableLayer.y, biodegradableLayer.width, biodegradableLayer.height);
		}

		if (!clickedR){
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class),
					recyclableLayer.x, recyclableLayer.y, recyclableLayer.width, recyclableLayer.height);
		}
		else {
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclableClicked.png", Texture.class),
					recyclableLayer.x, recyclableLayer.y, recyclableLayer.width, recyclableLayer.height);
		}

		if (!clickedNR){
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class),
					nonRecyclableLayer.x, nonRecyclableLayer.y, nonRecyclableLayer.width, nonRecyclableLayer.height);
		}
		else {
			batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclableClicked.png", Texture.class),
					nonRecyclableLayer.x, nonRecyclableLayer.y, nonRecyclableLayer.width, nonRecyclableLayer.height);
		}

		batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/selectType.png", Texture.class),
				selectLayer.x, selectLayer.y, selectLayer.width, selectLayer.height);
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
	}
}
