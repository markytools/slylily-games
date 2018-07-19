package com.junkworld.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class TrashCanSkillUpgradeScreen implements Screen {
	private JunkWorld game;
	private OrthographicCamera camera;
	private Batch batch;
	private Vector3 touchPos;
	private Vector2 touchPos2d;
	private TrashCan1Properties trashCan1Properties;
	private TrashCan2Properties trashCan2Properties;
	private TrashCan3Properties trashCan3Properties;
	private TrashCan4Properties trashCan4Properties;
	private CurrentItemOfSlot1 currentItemOfSlot1;
	private CurrentItemOfSlot2 currentItemOfSlot2;
	private CurrentItemOfSlot3 currentItemOfSlot3;
	private CurrentItemOfSlot4 currentItemOfSlot4;
	private CurrentItemOfSlot5 currentItemOfSlot5;
	private CurrentItemOfSlot6 currentItemOfSlot6;
	private AssetManager manager;
	private Texture trashCan1Pic, trashCan2Pic, trashCan3Pic, trashCan4Pic ;
	private Rectangle trashCan1Layer, trashCan2Layer, trashCan3Layer, trashCan4Layer, 
	backLayer, nextLayer;
	private Array<TextureRegion> leftArrowPics, rightArrowPics, selectPics, colors, selectAllRegions, unselectAllRegions, itemRegions;
	private Actor trashCan1, trashCan2, trashCan3, trashCan4, leftArrow, rightArrow, checkbox, back, next, gameRequirementsArrow, selectAll, selectItem,
	item1, item2, item3, leftItemArrow, rightItemArrow;
	private boolean loadTextures = true;
	private boolean isTrashCan1, isTrashCan2, isTrashCan3, isTrashCan4, selectedCanChanged = true;
	private int currentSelectedCan = 0;;
	private Stage stage, stage2;
	private Animation colorAnima;
	private TextureRegion itemFrameRegion, currentRequirementsArrayL,
	currentRequirementsArrayR, greenBGRegion, selectItemRegion, itemDescriptionRegion, clickHereRegion, leftItemArrowRegion, rightItemArrowRegion;
	private float stateTime;
	private GamePalette gamePalette;
	private Rectangle itemFrameLayer, gameRequirementsArrowLayer, selectItemLayer, itemDescriptionLayer, itemLayer1,
	itemLayer2, itemLayer3, leftItemArrowLayer, rightItemArrowLayer;
	private int unlockedItemSlots, currentSelectAllRegion;
	private boolean setGameAssets = true;
	private JunkWorldEngines junkWorldEngines;
	private long delayGameArrow = TimeUtils.millis(), delayGameReqMovement = 0, delaySelectAllFrame = TimeUtils.millis(), delaySelectAll = 0;
	private boolean removeListeners = false, gameReqOpened = false;
	private boolean isSelectAll = false, toggleItemSelection = false;
	private int currentSelectedItemSlot = 0, currentItemOfSelection1, currentItemOfSelection2, currentItemOfSelection3, totalUnlockedItems = 0;
	private CurrentSelectedItem currentSelectedItem;
	private Array<Actor> items, itemSlot;
	private long delaySetScreen = 0, delayTutoralArrow = TimeUtils.millis();
	private Array<TextureRegion> itemRegionsClicked, itemRegionsUnclickable;
	private int bombPos = 0, gluePos = 0, iceflakePos = 0, switchMachinePos = 0, augmentedBurstPos = 0, stickerPos = 0;
	private boolean isLoading = false, playMusic = true;
	private long delayAds = TimeUtils.millis();
	
	private void disposeAssets(){
		batch.dispose();
		stage.dispose();
		stage2.dispose();
		this.dispose();
	}

	private void loadManager(){
		//		TODO
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSkillUpgrades/itemFrame.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png", Texture.class);
		manager.load("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSkillUpgrades/miscellaneous/checkbox.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSkillUpgrades/miscellaneous/checkboxClicked.png", Texture.class);
		manager.load("backgrounds/greenBackground2.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/selectType.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/0.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/1.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/2.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/3.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/4.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/5.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/clickHere.png", Texture.class);
		manager.load("buttons/paletteLocked.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/tntBomb.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/glue.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/iceflake.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/switchMachine.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/augmentedBurst.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/sticker.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/tntBombClicked.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/glueClicked.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/iceflakeClicked.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/switchMachineClicked.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/augmentedBurstClicked.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/stickerClicked.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/tntBombUnclickable.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/glueUnclickable.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/iceflakeUnclickable.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/switchMachineUnclickable.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/augmentedBurstUnclickable.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemTextures/stickerUnclickable.png", Texture.class);

		for (int i = 0; i < 7; i++){
			manager.load("gameScreenAssets/trashCanSkillUpgrades/arrowAnimation/leftArrow" + i + ".png", Texture.class);
		}
		for (int i = 0; i < 7; i++){
			manager.load("gameScreenAssets/trashCanSkillUpgrades/arrowAnimation/rightArrow" + i + ".png", Texture.class);
		}
		for (int i = 0; i < 3; i++){
			manager.load("gameScreenAssets/trashCanSkillUpgrades/selectUpgradeAnimation/" + i + ".png", Texture.class);
		}
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class);
		manager.load("screenLabels/noCanSelected.png", Texture.class);

		manager.load("backgrounds/firstBackground.png", Texture.class);
		manager.load("mainMenuAssets/loadingAssets/blackShader.png", Texture.class);
		manager.load("backgrounds/blueFrame.png", Texture.class);
		manager.load("buttons/backButton.png", Texture.class);
		manager.load("buttons/nextButton.png", Texture.class);
		manager.load("screenLabels/tuning.png", Texture.class);
		manager.load("screenLabels/selectItems.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/gameRequirements.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/types.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/biodegradable.png", Texture.class);	
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/recyclable.png", Texture.class);	
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/nonRecyclable.png", Texture.class);	
		manager.load("gameScreenAssets/comboAssets/colors/color.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/red.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/orange.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/yellow.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/green.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/blue.png", Texture.class);
		manager.load("gameScreenAssets/comboAssets/colors/purple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL2.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR2.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class);

		manager.load("gameScreenAssets/itemAssets/itemDescriptions/description1.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemDescriptions/description2.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemDescriptions/description3.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemDescriptions/description0.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemDescriptions/description4.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemDescriptions/description5.png", Texture.class);
		manager.load("gameScreenAssets/itemAssets/itemDescriptions/description6.png", Texture.class);
		manager.load("screenLabels/selectItems.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSkillUpgrades/upgradeFrame.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/gameRequirements.png", Texture.class);

		manager.finishLoading();
	}

	private void unloadManager(){
		//		TODO
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png");

		manager.unload("gameScreenAssets/trashCanSkillUpgrades/itemFrame.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png");
		manager.unload("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png");
		manager.unload("gameScreenAssets/trashCanSkillUpgrades/miscellaneous/checkbox.png");
		manager.unload("gameScreenAssets/trashCanSkillUpgrades/miscellaneous/checkboxClicked.png");
		manager.unload("backgrounds/greenBackground2.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/selectType.png");
		manager.unload("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/0.png");
		manager.unload("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/1.png");
		manager.unload("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/2.png");
		manager.unload("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/3.png");
		manager.unload("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/4.png");
		manager.unload("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/5.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/clickHere.png");
		manager.unload("buttons/paletteLocked.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/tntBomb.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/glue.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/iceflake.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/switchMachine.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/augmentedBurst.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/sticker.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/tntBombClicked.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/glueClicked.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/iceflakeClicked.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/switchMachineClicked.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/augmentedBurstClicked.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/stickerClicked.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/tntBombUnclickable.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/glueUnclickable.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/iceflakeUnclickable.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/switchMachineUnclickable.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/augmentedBurstUnclickable.png");
		manager.unload("gameScreenAssets/itemAssets/itemTextures/stickerUnclickable.png");

		for (int i = 0; i < 7; i++){
			manager.unload("gameScreenAssets/trashCanSkillUpgrades/arrowAnimation/leftArrow" + i + ".png");
		}
		for (int i = 0; i < 7; i++){
			manager.unload("gameScreenAssets/trashCanSkillUpgrades/arrowAnimation/rightArrow" + i + ".png");
		}
		for (int i = 0; i < 3; i++){
			manager.unload("gameScreenAssets/trashCanSkillUpgrades/selectUpgradeAnimation/" + i + ".png");
		}
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png");
		manager.unload("screenLabels/noCanSelected.png");

		manager.unload("backgrounds/firstBackground.png");
		manager.unload("backgrounds/blueFrame.png");
		manager.unload("buttons/backButton.png");
		manager.unload("buttons/nextButton.png");
		manager.unload("screenLabels/tuning.png");
		manager.unload("screenLabels/selectItems.png");

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

		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL2.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR2.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png");

		manager.unload("gameScreenAssets/itemAssets/itemDescriptions/description1.png");
		manager.unload("gameScreenAssets/itemAssets/itemDescriptions/description2.png");
		manager.unload("gameScreenAssets/itemAssets/itemDescriptions/description3.png");
		manager.unload("gameScreenAssets/itemAssets/itemDescriptions/description0.png");
		manager.unload("gameScreenAssets/itemAssets/itemDescriptions/description4.png");
		manager.unload("gameScreenAssets/itemAssets/itemDescriptions/description5.png");
		manager.unload("gameScreenAssets/itemAssets/itemDescriptions/description6.png");
		manager.unload("screenLabels/selectItems.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/gameRequirements.png");

		manager.unload("gameScreenAssets/trashCanSkillUpgrades/upgradeFrame.png");
	}

	public TrashCanSkillUpgradeScreen( final JunkWorld game, TrashCan1Properties trashCan1Properties,
			final TrashCan2Properties trashCan2Properties, final TrashCan3Properties trashCan3Properties,
			final TrashCan4Properties trashCan4Properties, final AssetManager manager, final JunkWorldEngines junkWorldEngines,
			final GamePalette gamePalette){
		this.game = game;
		this.trashCan1Properties = trashCan1Properties;
		this.trashCan2Properties = trashCan2Properties;
		this.trashCan3Properties = trashCan3Properties;
		this.trashCan4Properties = trashCan4Properties;
		this.manager = manager;
		this.junkWorldEngines = junkWorldEngines;
		this.gamePalette = gamePalette;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		batch = new SpriteBatch();
		touchPos = new Vector3(); 	
		touchPos2d = new Vector2();
		unlockedItemSlots = junkWorldEngines.getTotalUnlockedItemsSlots();
		loadManager();

		loadAssets();
		if (trashCan1Properties.getTrashCan() != 0){
			isTrashCan1 = true;
		} else isTrashCan1 = false;
		if (trashCan2Properties.getTrashCan() != 0){
			isTrashCan2 = true;
		} else isTrashCan2 = false;
		if (trashCan3Properties.getTrashCan() != 0){
			isTrashCan3 = true;
		} else isTrashCan3 = false;
		if (trashCan4Properties.getTrashCan() != 0){
			isTrashCan4 = true;
		} else isTrashCan4 = false;

		currentItemOfSelection2 = 2;
		currentItemOfSelection1 = currentItemOfSelection2 - 1;
		currentItemOfSelection3 = currentItemOfSelection2 + 1;

		if (junkWorldEngines.isBomb()){
			totalUnlockedItems += 1;
		}
		if (junkWorldEngines.isGlue()){
			totalUnlockedItems += 1;
		}
		if (junkWorldEngines.isIceflake()){
			totalUnlockedItems += 1;
		}
		if (junkWorldEngines.isSwitchMachine()){
			totalUnlockedItems += 1;
		}
		if (junkWorldEngines.isAugmentedBurst()){
			totalUnlockedItems += 1;
		}
		if (junkWorldEngines.isSticker()){
			totalUnlockedItems += 1;
		}

		stage = new Stage();
		stage2 = new Stage();
		items = new Array<Actor>();
		for (int i = 1; i < 12; i++){
			items.add(new Actor());
		}
		itemSlot = new Array<Actor>();
		for (int i = 1; i < unlockedItemSlots + 1; i++){
			itemSlot.add(new Actor());
		}
		itemRegions = new Array<TextureRegion>();
		itemRegionsUnclickable = new Array<TextureRegion>();

		backLayer = new Rectangle(64, 64, 100, 50);
		nextLayer = new Rectangle(350, 64, 100, 50);
		gameRequirementsArrowLayer = new Rectangle(455, 400, 50, 100);
		selectItemLayer = new Rectangle(256 - 65, 50, 130, 40);
		itemDescriptionLayer = new Rectangle(64, 316, 384, 384);

		itemFrameLayer = new Rectangle();
		itemFrameLayer.setY(400);
		itemFrameLayer.setHeight(64);
		itemLayer1 = new Rectangle(112, 150, 96, 96);
		itemLayer2 = new Rectangle(112 + (96 * 1), 150, 96, 96);
		itemLayer3 = new Rectangle(112 + (96 * 2), 150, 96, 96);
		leftItemArrowLayer = new Rectangle(48, 150 + 16, 64, 64);
		rightItemArrowLayer = new Rectangle(48 + 288 + 64, 150 + 16, 64, 64);

		next = new Actor();
		back = new Actor();
		gameRequirementsArrow = new Actor();
		trashCan1 = new Actor();
		trashCan2 = new Actor();
		trashCan3 = new Actor();
		trashCan4 = new Actor();
		selectItem = new Actor();
		item1 = new Actor();
		item2 = new Actor();
		item3 = new Actor();
		leftItemArrow = new Actor();
		rightItemArrow = new Actor();

		stage.addActor(next);
		stage.addActor(back);
		stage.addActor(trashCan1);
		stage.addActor(trashCan2);
		stage.addActor(trashCan3);
		stage.addActor(trashCan4);
		stage.addActor(gameRequirementsArrow);
		stage2.addActor(selectItem);
		stage2.addActor(item1);
		stage2.addActor(item2);
		stage2.addActor(item3);
		stage2.addActor(leftItemArrow);
		stage2.addActor(rightItemArrow);
		for (int i = 0; i < items.size; i++){
			stage.addActor(items.get(i));
		}
		for (int i = 0; i < itemSlot.size; i++){
			stage.addActor(itemSlot.get(i));
		}

		currentSelectedItem = CurrentSelectedItem.NONE;
		currentItemOfSlot1 = CurrentItemOfSlot1.NONE;
		currentItemOfSlot2 = CurrentItemOfSlot2.NONE;
		currentItemOfSlot3 = CurrentItemOfSlot3.NONE;
		currentItemOfSlot4 = CurrentItemOfSlot4.NONE;
		currentItemOfSlot5 = CurrentItemOfSlot5.NONE;
		currentItemOfSlot6 = CurrentItemOfSlot6.NONE;

		if (junkWorldEngines.getGameMode() == 0){
			junkWorldEngines.setQuitTutorial(new Actor());
			junkWorldEngines.getQuitTutorial().addListener(new ActorGestureListener(){
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

			junkWorldEngines.setTutorialStage(new Stage());
			junkWorldEngines.getTutorialStage().addActor(junkWorldEngines.getQuitTutorial());
			for (Actor slot : itemSlot){
				junkWorldEngines.getTutorialStage().addActor(slot);
			}
			junkWorldEngines.getTutorialStage().addActor(item1);
			junkWorldEngines.getTutorialStage().addActor(item2);
			junkWorldEngines.getTutorialStage().addActor(item3);
			junkWorldEngines.getTutorialStage().addActor(selectItem);
			junkWorldEngines.getTutorialStage().addActor(next);
		}
		loadMusic();
	}

	private void loadAssets() {
		//		TODO
	}

	private void setTexture() {
		if (loadTextures){
			loadTextures = false;
			loadOtherAssets();
			setAnimation();

			if (isTrashCan1){
				trashCan1Layer = new Rectangle(68, 580 - 5, 96, 96);

				switch (trashCan1Properties.getTrashCan()){
				case 1: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 2: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 3: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 4: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;	
				case 5: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 6: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan1Properties.getTrashCanColor()){
						case 1: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan1Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				default: break;
				}
			}

			if (isTrashCan2){
				trashCan2Layer = new Rectangle(164, 580 - 5, 96, 96);

				switch (trashCan2Properties.getTrashCan()){
				case 1: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 2: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 3: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 4: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;	
				case 5: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 6: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan2Properties.getTrashCanColor()){
						case 1: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan2Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				}
			}

			if (isTrashCan3){
				trashCan3Layer = new Rectangle(260, 580 - 5, 96, 96);

				switch (trashCan3Properties.getTrashCan()){
				case 1: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 2: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 3: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 4: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;	
				case 5: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 6: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan3Properties.getTrashCanColor()){
						case 1: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan3Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				default: break;
				}
			}

			if (isTrashCan4){
				trashCan4Layer = new Rectangle(356, 580 - 5, 96, 96);

				switch (trashCan4Properties.getTrashCan()){
				case 1: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 2: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 3: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 4: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;	
				case 5: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				case 6: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 2: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					case 3: {
						switch (trashCan4Properties.getTrashCanColor()){
						case 1: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png", Texture.class);
						break;
						case 2: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png", Texture.class);
						break;
						case 3: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png", Texture.class);
						break;
						case 4: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png", Texture.class);
						break;
						case 5: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png", Texture.class);
						break;
						case 6: trashCan4Pic = manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png", Texture.class);
						break;
						}
					}; break;
					}
				}; break;
				default: break;
				}
			}

			itemFrameRegion = new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/itemFrame.png", Texture.class));
			currentRequirementsArrayL = new TextureRegion(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class));
			currentRequirementsArrayR = new TextureRegion(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class));

			leftItemArrowRegion = new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/leftArrow.png", Texture.class));
			rightItemArrowRegion = new TextureRegion(manager.get("singlePlayerAssets/JunkWorld Map/miscellaneous/rightArrow.png", Texture.class));

			new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/miscellaneous/checkbox.png", Texture.class));
			new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/miscellaneous/checkboxClicked.png", Texture.class));
			itemDescriptionRegion = new TextureRegion();

			greenBGRegion = new TextureRegion(manager.get("backgrounds/greenBackground2.png", Texture.class));
			selectAllRegions = new Array<TextureRegion>();
			selectItemRegion = new TextureRegion(manager.get("gameScreenAssets/trashCanSelectionAssets/trashTypeIcons/selectType.png", Texture.class));
			selectAllRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/0.png", Texture.class)));
			selectAllRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/1.png", Texture.class)));
			selectAllRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/2.png", Texture.class)));

			unselectAllRegions = new Array<TextureRegion>();
			unselectAllRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/3.png", Texture.class)));
			unselectAllRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/4.png", Texture.class)));
			unselectAllRegions.add(new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/selectAllUpgradesAnimation/5.png", Texture.class)));
			clickHereRegion = new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/clickHere.png", Texture.class));
			new TextureRegion(manager.get("buttons/paletteLocked.png", Texture.class));
			itemRegionsClicked = new Array<TextureRegion>();

			itemRegions.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/tntBomb.png", Texture.class)));
			itemRegions.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/glue.png", Texture.class)));
			itemRegions.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/iceflake.png", Texture.class)));
			itemRegions.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/switchMachine.png", Texture.class)));
			itemRegions.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/augmentedBurst.png", Texture.class)));
			itemRegions.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/sticker.png", Texture.class)));

			itemRegionsClicked.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/tntBombClicked.png", Texture.class)));
			itemRegionsClicked.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/glueClicked.png", Texture.class)));
			itemRegionsClicked.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/iceflakeClicked.png", Texture.class)));
			itemRegionsClicked.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/switchMachineClicked.png", Texture.class)));
			itemRegionsClicked.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/augmentedBurstClicked.png", Texture.class)));
			itemRegionsClicked.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/stickerClicked.png", Texture.class)));

			itemRegionsUnclickable.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/tntBombUnclickable.png", Texture.class)));
			itemRegionsUnclickable.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/glueUnclickable.png", Texture.class)));
			itemRegionsUnclickable.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/iceflakeUnclickable.png", Texture.class)));
			itemRegionsUnclickable.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/switchMachineUnclickable.png", Texture.class)));
			itemRegionsUnclickable.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/augmentedBurstUnclickable.png", Texture.class)));
			itemRegionsUnclickable.add(new TextureRegion(manager.get("gameScreenAssets/itemAssets/itemTextures/stickerUnclickable.png", Texture.class)));
		}
	}

	private void loadOtherAssets() {

		leftArrowPics = new Array<TextureRegion>();
		for (int i = 0; i < 7; i++){
			leftArrowPics.add(new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/arrowAnimation/leftArrow" + i + ".png", Texture.class)));
		}
		rightArrowPics = new Array<TextureRegion>();
		for (int i = 0; i < 7; i++){
			rightArrowPics.add(new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/arrowAnimation/rightArrow" + i + ".png", Texture.class)));
		}

		selectPics = new Array<TextureRegion>();
		for (int i = 0; i < 3; i++){
			selectPics.add(new TextureRegion(manager.get("gameScreenAssets/trashCanSkillUpgrades/selectUpgradeAnimation/" + i + ".png", Texture.class)));
		}

		new Animation(0.25f, leftArrowPics);
		new Animation(0.25f, rightArrowPics);
		new Animation(0.5f, selectPics);

		stateTime = 0f;
		new Rectangle(64, 242 + 15, 25, 25);
		new Rectangle(128 - 29, 460 + 13 + 3, 30, 30);
		new Rectangle(384 - 30 + 13 + 16, 460 + 13 + 3, 30, 30);
		new Rectangle(290, 237 + 17, 150, 30);
	}

	private void setAnimation() {
		colors = new Array<TextureRegion>();
		for (int i = 0; i < 6; i++){
			colors.add(new TextureRegion(new Texture(Gdx.files.internal("gameAnimations/selectedCan/color" + i + ".png"))));
		}
		colorAnima = new Animation(0.2f, colors);
		stateTime = 0f;
	}

	private void setActors() {
		if (selectedCanChanged){
			selectedCanChanged = false;

			back.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					isLoading = true;
					unloadManager();
					disposeAssets();
					game.setScreen(new TrashCanColorScreen(game, trashCan1Properties, trashCan2Properties, trashCan3Properties,
							trashCan4Properties, manager, junkWorldEngines));
					return true;
				}
			});
			next.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (junkWorldEngines.getTotalUnlockedItemsSlots() <= 2){
						currentItemOfSlot3 = CurrentItemOfSlot3.NONE;
					}
					if (junkWorldEngines.getTotalUnlockedItemsSlots() <= 3){
						currentItemOfSlot4 = CurrentItemOfSlot4.NONE;
					}
					if (junkWorldEngines.getTotalUnlockedItemsSlots() <= 4){
						currentItemOfSlot5 = CurrentItemOfSlot5.NONE;
					}
					if (junkWorldEngines.getTotalUnlockedItemsSlots() <= 5){
						currentItemOfSlot6 = CurrentItemOfSlot6.NONE;
					}
					delaySetScreen = TimeUtils.millis();
					return true;
				}
			});

			if (isTrashCan1){
				trashCan1.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						if (currentSelectedCan == 1){
							currentSelectedCan = 0;
						} else currentSelectedCan = 1;
						return true;
					}
				});
			}
			if (isTrashCan2){
				trashCan2.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						if (currentSelectedCan == 2){
							currentSelectedCan = 0;
						} else currentSelectedCan = 2;
						return true;
					}
				});
			}
			if (isTrashCan3){
				trashCan3.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						if (currentSelectedCan == 3){
							currentSelectedCan = 0;
						} else currentSelectedCan = 3;
						return true;
					}
				});
			}
			if (isTrashCan4){
				trashCan4.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						if (currentSelectedCan == 4){
							currentSelectedCan = 0;
						} else currentSelectedCan = 4;
						return true;
					}
				});
			}

			item1.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					switch (currentItemOfSelection1){
					case 1: {
						if (currentSelectedItem == CurrentSelectedItem.BOMB){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (bombPos == 1 || bombPos == 0)) ||
									(currentSelectedItemSlot == 2 && (bombPos == 2 || bombPos == 0)) ||
									(currentSelectedItemSlot == 3 && (bombPos == 3 || bombPos == 0)) ||
									(currentSelectedItemSlot == 4 && (bombPos == 4 || bombPos == 0)) ||
									(currentSelectedItemSlot == 5 && (bombPos == 5 || bombPos == 0)) ||
									(currentSelectedItemSlot == 6 && (bombPos == 6 || bombPos == 0))){
								currentSelectedItem = CurrentSelectedItem.BOMB;
							}
						}
					}; break;
					case 2: {
						if (currentSelectedItem == CurrentSelectedItem.GLUE){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (gluePos == 1 || gluePos == 0)) ||
									(currentSelectedItemSlot == 2 && (gluePos == 2 || gluePos == 0)) ||
									(currentSelectedItemSlot == 3 && (gluePos == 3 || gluePos == 0)) ||
									(currentSelectedItemSlot == 4 && (gluePos == 4 || gluePos == 0)) ||
									(currentSelectedItemSlot == 5 && (gluePos == 5 || gluePos == 0)) ||
									(currentSelectedItemSlot == 6 && (gluePos == 6 || gluePos == 0))){
								currentSelectedItem = CurrentSelectedItem.GLUE;
							}
						}
					}; break;
					case 3: {
						if (currentSelectedItem == CurrentSelectedItem.ICEFLAKE){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (iceflakePos == 1 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 2 && (iceflakePos == 2 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 3 && (iceflakePos == 3 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 4 && (iceflakePos == 4 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 5 && (iceflakePos == 5 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 6 && (iceflakePos == 6 || iceflakePos == 0))){
								currentSelectedItem = CurrentSelectedItem.ICEFLAKE;
							}
						}
					}; break;
					case 4: {
						if (currentSelectedItem == CurrentSelectedItem.SWITCH_MACHINE){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (switchMachinePos == 1 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 2 && (switchMachinePos == 2 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 3 && (switchMachinePos == 3 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 4 && (switchMachinePos == 4 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 5 && (switchMachinePos == 5 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 6 && (switchMachinePos == 6 || switchMachinePos == 0))){
								currentSelectedItem = CurrentSelectedItem.SWITCH_MACHINE;
							}
						}
					}; break;
					case 5: {
						if (currentSelectedItem == CurrentSelectedItem.AUGMENTED_BURST){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (augmentedBurstPos == 1 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 2 && (augmentedBurstPos == 2 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 3 && (augmentedBurstPos == 3 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 4 && (augmentedBurstPos == 4 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 5 && (augmentedBurstPos == 5 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 6 && (augmentedBurstPos == 6 || augmentedBurstPos == 0))){
								currentSelectedItem = CurrentSelectedItem.AUGMENTED_BURST;
							}
						}
					}; break;
					case 6: {
						if (currentSelectedItem == CurrentSelectedItem.STICKER){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (stickerPos == 1 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 2 && (stickerPos == 2 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 3 && (stickerPos == 3 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 4 && (stickerPos == 4 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 5 && (stickerPos == 5 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 6 && (stickerPos == 6 || stickerPos == 0))){
								currentSelectedItem = CurrentSelectedItem.STICKER;
							}
						}
					}; break;
					default: break;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			item2.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					switch (currentItemOfSelection2){
					case 1: {
						if (currentSelectedItem == CurrentSelectedItem.BOMB){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (bombPos == 1 || bombPos == 0)) ||
									(currentSelectedItemSlot == 2 && (bombPos == 2 || bombPos == 0)) ||
									(currentSelectedItemSlot == 3 && (bombPos == 3 || bombPos == 0)) ||
									(currentSelectedItemSlot == 4 && (bombPos == 4 || bombPos == 0)) ||
									(currentSelectedItemSlot == 5 && (bombPos == 5 || bombPos == 0)) ||
									(currentSelectedItemSlot == 6 && (bombPos == 6 || bombPos == 0))){
								currentSelectedItem = CurrentSelectedItem.BOMB;
							}
						}
					}; break;
					case 2: {
						if (currentSelectedItem == CurrentSelectedItem.GLUE){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (gluePos == 1 || gluePos == 0)) ||
									(currentSelectedItemSlot == 2 && (gluePos == 2 || gluePos == 0)) ||
									(currentSelectedItemSlot == 3 && (gluePos == 3 || gluePos == 0)) ||
									(currentSelectedItemSlot == 4 && (gluePos == 4 || gluePos == 0)) ||
									(currentSelectedItemSlot == 5 && (gluePos == 5 || gluePos == 0)) ||
									(currentSelectedItemSlot == 6 && (gluePos == 6 || gluePos == 0))){
								currentSelectedItem = CurrentSelectedItem.GLUE;
							}
						}
					}; break;
					case 3: {
						if (currentSelectedItem == CurrentSelectedItem.ICEFLAKE){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (iceflakePos == 1 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 2 && (iceflakePos == 2 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 3 && (iceflakePos == 3 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 4 && (iceflakePos == 4 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 5 && (iceflakePos == 5 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 6 && (iceflakePos == 6 || iceflakePos == 0))){
								currentSelectedItem = CurrentSelectedItem.ICEFLAKE;
							}
						}
					}; break;
					case 4: {
						if (currentSelectedItem == CurrentSelectedItem.SWITCH_MACHINE){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (switchMachinePos == 1 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 2 && (switchMachinePos == 2 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 3 && (switchMachinePos == 3 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 4 && (switchMachinePos == 4 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 5 && (switchMachinePos == 5 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 6 && (switchMachinePos == 6 || switchMachinePos == 0))){
								currentSelectedItem = CurrentSelectedItem.SWITCH_MACHINE;
							}
						}
					}; break;
					case 5: {
						if (currentSelectedItem == CurrentSelectedItem.AUGMENTED_BURST){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (augmentedBurstPos == 1 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 2 && (augmentedBurstPos == 2 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 3 && (augmentedBurstPos == 3 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 4 && (augmentedBurstPos == 4 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 5 && (augmentedBurstPos == 5 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 6 && (augmentedBurstPos == 6 || augmentedBurstPos == 0))){
								currentSelectedItem = CurrentSelectedItem.AUGMENTED_BURST;
							}
						}
					}; break;
					case 6: {
						if (currentSelectedItem == CurrentSelectedItem.STICKER){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (stickerPos == 1 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 2 && (stickerPos == 2 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 3 && (stickerPos == 3 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 4 && (stickerPos == 4 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 5 && (stickerPos == 5 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 6 && (stickerPos == 6 || stickerPos == 0))){
								currentSelectedItem = CurrentSelectedItem.STICKER;
							}
						}
					}; break;
					default: break;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});
			item3.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					switch (currentItemOfSelection3){
					case 1: {
						if (currentSelectedItem == CurrentSelectedItem.BOMB){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (bombPos == 1 || bombPos == 0)) ||
									(currentSelectedItemSlot == 2 && (bombPos == 2 || bombPos == 0)) ||
									(currentSelectedItemSlot == 3 && (bombPos == 3 || bombPos == 0)) ||
									(currentSelectedItemSlot == 4 && (bombPos == 4 || bombPos == 0)) ||
									(currentSelectedItemSlot == 5 && (bombPos == 5 || bombPos == 0)) ||
									(currentSelectedItemSlot == 6 && (bombPos == 6 || bombPos == 0))){
								currentSelectedItem = CurrentSelectedItem.BOMB;
							}
						}
					}; break;
					case 2: {
						if (currentSelectedItem == CurrentSelectedItem.GLUE){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (gluePos == 1 || gluePos == 0)) ||
									(currentSelectedItemSlot == 2 && (gluePos == 2 || gluePos == 0)) ||
									(currentSelectedItemSlot == 3 && (gluePos == 3 || gluePos == 0)) ||
									(currentSelectedItemSlot == 4 && (gluePos == 4 || gluePos == 0)) ||
									(currentSelectedItemSlot == 5 && (gluePos == 5 || gluePos == 0)) ||
									(currentSelectedItemSlot == 6 && (gluePos == 6 || gluePos == 0))){
								currentSelectedItem = CurrentSelectedItem.GLUE;
							}
						}
					}; break;
					case 3: {
						if (currentSelectedItem == CurrentSelectedItem.ICEFLAKE){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (iceflakePos == 1 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 2 && (iceflakePos == 2 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 3 && (iceflakePos == 3 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 4 && (iceflakePos == 4 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 5 && (iceflakePos == 5 || iceflakePos == 0)) ||
									(currentSelectedItemSlot == 6 && (iceflakePos == 6 || iceflakePos == 0))){
								currentSelectedItem = CurrentSelectedItem.ICEFLAKE;
							}
						}
					}; break;
					case 4: {
						if (currentSelectedItem == CurrentSelectedItem.SWITCH_MACHINE){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (switchMachinePos == 1 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 2 && (switchMachinePos == 2 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 3 && (switchMachinePos == 3 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 4 && (switchMachinePos == 4 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 5 && (switchMachinePos == 5 || switchMachinePos == 0)) ||
									(currentSelectedItemSlot == 6 && (switchMachinePos == 6 || switchMachinePos == 0))){
								currentSelectedItem = CurrentSelectedItem.SWITCH_MACHINE;
							}
						}
					}; break;
					case 5: {
						if (currentSelectedItem == CurrentSelectedItem.AUGMENTED_BURST){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (augmentedBurstPos == 1 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 2 && (augmentedBurstPos == 2 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 3 && (augmentedBurstPos == 3 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 4 && (augmentedBurstPos == 4 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 5 && (augmentedBurstPos == 5 || augmentedBurstPos == 0)) ||
									(currentSelectedItemSlot == 6 && (augmentedBurstPos == 6 || augmentedBurstPos == 0))){
								currentSelectedItem = CurrentSelectedItem.AUGMENTED_BURST;
							}
						}
					}; break;
					case 6: {
						if (currentSelectedItem == CurrentSelectedItem.STICKER){
							currentSelectedItem = CurrentSelectedItem.NONE;
						}
						else {
							if ((currentSelectedItemSlot == 1 && (stickerPos == 1 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 2 && (stickerPos == 2 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 3 && (stickerPos == 3 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 4 && (stickerPos == 4 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 5 && (stickerPos == 5 || stickerPos == 0)) ||
									(currentSelectedItemSlot == 6 && (stickerPos == 6 || stickerPos == 0))){
								currentSelectedItem = CurrentSelectedItem.STICKER;
							}
						}
					}; break;
					default: break;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});

			leftItemArrow.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (toggleItemSelection){
						if (currentItemOfSelection2 - 2 >= 1){
							currentItemOfSelection2 -= 1;
						}
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});

			rightItemArrow.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (toggleItemSelection){
						if (currentItemOfSelection2 + 2 <= totalUnlockedItems){
							currentItemOfSelection2 += 1;
						}
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});

			for (int i = 0; i < itemSlot.size; i++){
				switch (i){
				case 0: {
					itemSlot.get(i).addListener(new InputListener(){

						@Override
						public boolean touchDown(InputEvent event, float x, float y,
								int pointer, int button) {
							if (junkWorldEngines.getGameMode() == 0){
								junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
							}
							if (!toggleItemSelection){
								currentSelectedItemSlot = 1;
								toggleItemSelection = true;
							}
							return super.touchDown(event, x, y, pointer, button);
						}

					});
				}; break;
				case 1: {
					itemSlot.get(i).addListener(new InputListener(){

						@Override
						public boolean touchDown(InputEvent event, float x, float y,
								int pointer, int button) {
							if (junkWorldEngines.getGameMode() == 0){
								junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
							}
							if (!toggleItemSelection){
								currentSelectedItemSlot = 2;
								toggleItemSelection = true;
							}
							return super.touchDown(event, x, y, pointer, button);
						}

					});
				}; break;
				case 2: {
					itemSlot.get(i).addListener(new InputListener(){

						@Override
						public boolean touchDown(InputEvent event, float x, float y,
								int pointer, int button) {
							if (junkWorldEngines.getGameMode() == 0){
								junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
							}
							if (!toggleItemSelection){
								currentSelectedItemSlot = 3;
								toggleItemSelection = true;
							}
							return super.touchDown(event, x, y, pointer, button);
						}

					});
				}; break;
				case 3: {
					itemSlot.get(i).addListener(new InputListener(){

						@Override
						public boolean touchDown(InputEvent event, float x, float y,
								int pointer, int button) {
							if (junkWorldEngines.getGameMode() == 0){
								junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
							}
							if (!toggleItemSelection){
								currentSelectedItemSlot = 4;
								toggleItemSelection = true;
							}
							return super.touchDown(event, x, y, pointer, button);
						}

					});
				}; break;
				case 4: {
					itemSlot.get(i).addListener(new InputListener(){

						@Override
						public boolean touchDown(InputEvent event, float x, float y,
								int pointer, int button) {
							if (junkWorldEngines.getGameMode() == 0){
								junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
							}
							if (!toggleItemSelection){
								currentSelectedItemSlot = 5;
								toggleItemSelection = true;
							}
							return super.touchDown(event, x, y, pointer, button);
						}

					});
				}; break;
				case 5: {
					itemSlot.get(i).addListener(new InputListener(){

						@Override
						public boolean touchDown(InputEvent event, float x, float y,
								int pointer, int button) {
							if (junkWorldEngines.getGameMode() == 0){
								junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
							}
							if (!toggleItemSelection){
								currentSelectedItemSlot = 6;
								toggleItemSelection = true;
							}
							return super.touchDown(event, x, y, pointer, button);
						}

					});
				}; break;
				default: break;
				}
			}

			selectItem.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (toggleItemSelection){
						switch (currentSelectedItemSlot){
						case 1: {
							switch (currentSelectedItem){
							case ACCELERATOR: currentItemOfSlot1 = CurrentItemOfSlot1.ACCELERATOR;
							break;
							case ACCELERATOR_X: currentItemOfSlot1 = CurrentItemOfSlot1.ACCELERATOR_X;
							break;
							case BOMB: currentItemOfSlot1 = CurrentItemOfSlot1.BOMB;
							break;
							case ELECTRIC_ZAPPER: currentItemOfSlot1 = CurrentItemOfSlot1.ELECTRIC_ZAPPER;
							break;
							case FLYSWATTER: currentItemOfSlot1 = CurrentItemOfSlot1.FLYSWATTER;
							break;
							case GLUE: currentItemOfSlot1 = CurrentItemOfSlot1.GLUE;
							break;
							case ICEFLAKE: currentItemOfSlot1 = CurrentItemOfSlot1.ICEFLAKE;
							break;
							case ITEM_RESTORE: currentItemOfSlot1 = CurrentItemOfSlot1.ITEM_RESTORE;
							break;
							case NONE: currentItemOfSlot1 = CurrentItemOfSlot1.NONE;
							break;
							case STEEL_FLYSWATTER: currentItemOfSlot1 = CurrentItemOfSlot1.STEEL_FLYSWATTER;
							break;
							case SUPER_GLUE: currentItemOfSlot1 = CurrentItemOfSlot1.SUPER_GLUE;
							break;
							case WIND_VANE: currentItemOfSlot1 = CurrentItemOfSlot1.WIND_VANE;
							break;
							case SWITCH_MACHINE: currentItemOfSlot1 = CurrentItemOfSlot1.SWITCH_MACHINE;
							break;
							case AUGMENTED_BURST: currentItemOfSlot1 = CurrentItemOfSlot1.AUGMENTED_BURST;
							break;
							case STICKER: currentItemOfSlot1 = CurrentItemOfSlot1.STICKER;
							break;
							default:
								break;
							}
						}; break;
						case 2: {
							switch (currentSelectedItem){
							case ACCELERATOR: currentItemOfSlot2 = CurrentItemOfSlot2.ACCELERATOR;
							break;
							case ACCELERATOR_X: currentItemOfSlot2 = CurrentItemOfSlot2.ACCELERATOR_X;
							break;
							case BOMB: currentItemOfSlot2 = CurrentItemOfSlot2.BOMB;
							break;
							case ELECTRIC_ZAPPER: currentItemOfSlot2 = CurrentItemOfSlot2.ELECTRIC_ZAPPER;
							break;
							case FLYSWATTER: currentItemOfSlot2 = CurrentItemOfSlot2.FLYSWATTER;
							break;
							case GLUE: currentItemOfSlot2 = CurrentItemOfSlot2.GLUE;
							break;
							case ICEFLAKE: currentItemOfSlot2 = CurrentItemOfSlot2.ICEFLAKE;
							break;
							case ITEM_RESTORE: currentItemOfSlot2 = CurrentItemOfSlot2.ITEM_RESTORE;
							break;
							case NONE: currentItemOfSlot2 = CurrentItemOfSlot2.NONE;
							break;
							case STEEL_FLYSWATTER: currentItemOfSlot2 = CurrentItemOfSlot2.STEEL_FLYSWATTER;
							break;
							case SUPER_GLUE: currentItemOfSlot2 = CurrentItemOfSlot2.SUPER_GLUE;
							break;
							case WIND_VANE: currentItemOfSlot2 = CurrentItemOfSlot2.WIND_VANE;
							break;
							case SWITCH_MACHINE: currentItemOfSlot2 = CurrentItemOfSlot2.SWITCH_MACHINE;
							break;
							case AUGMENTED_BURST: currentItemOfSlot2 = CurrentItemOfSlot2.AUGMENTED_BURST;
							break;
							case STICKER: currentItemOfSlot2 = CurrentItemOfSlot2.STICKER;
							break;
							default:
								break;
							}
						}; break;
						case 3: {
							switch (currentSelectedItem){
							case ACCELERATOR: currentItemOfSlot3 = CurrentItemOfSlot3.ACCELERATOR;
							break;
							case ACCELERATOR_X: currentItemOfSlot3 = CurrentItemOfSlot3.ACCELERATOR_X;
							break;
							case BOMB: currentItemOfSlot3 = CurrentItemOfSlot3.BOMB;
							break;
							case ELECTRIC_ZAPPER: currentItemOfSlot3 = CurrentItemOfSlot3.ELECTRIC_ZAPPER;
							break;
							case FLYSWATTER: currentItemOfSlot3 = CurrentItemOfSlot3.FLYSWATTER;
							break;
							case GLUE: currentItemOfSlot3 = CurrentItemOfSlot3.GLUE;
							break;
							case ICEFLAKE: currentItemOfSlot3 = CurrentItemOfSlot3.ICEFLAKE;
							break;
							case ITEM_RESTORE: currentItemOfSlot3 = CurrentItemOfSlot3.ITEM_RESTORE;
							break;
							case NONE: currentItemOfSlot3 = CurrentItemOfSlot3.NONE;
							break;
							case STEEL_FLYSWATTER: currentItemOfSlot3 = CurrentItemOfSlot3.STEEL_FLYSWATTER;
							break;
							case SUPER_GLUE: currentItemOfSlot3 = CurrentItemOfSlot3.SUPER_GLUE;
							break;
							case WIND_VANE: currentItemOfSlot3 = CurrentItemOfSlot3.WIND_VANE;
							break;
							case SWITCH_MACHINE: currentItemOfSlot3 = CurrentItemOfSlot3.SWITCH_MACHINE;
							break;
							case AUGMENTED_BURST: currentItemOfSlot3 = CurrentItemOfSlot3.AUGMENTED_BURST;
							break;
							case STICKER: currentItemOfSlot3 = CurrentItemOfSlot3.STICKER;
							break;
							default:
								break;
							}
						}; break;
						case 4: {
							switch (currentSelectedItem){
							case ACCELERATOR: currentItemOfSlot4 = CurrentItemOfSlot4.ACCELERATOR;
							break;
							case ACCELERATOR_X: currentItemOfSlot4 = CurrentItemOfSlot4.ACCELERATOR_X;
							break;
							case BOMB: currentItemOfSlot4 = CurrentItemOfSlot4.BOMB;
							break;
							case ELECTRIC_ZAPPER: currentItemOfSlot4 = CurrentItemOfSlot4.ELECTRIC_ZAPPER;
							break;
							case FLYSWATTER: currentItemOfSlot4 = CurrentItemOfSlot4.FLYSWATTER;
							break;
							case GLUE: currentItemOfSlot4 = CurrentItemOfSlot4.GLUE;
							break;
							case ICEFLAKE: currentItemOfSlot4 = CurrentItemOfSlot4.ICEFLAKE;
							break;
							case ITEM_RESTORE: currentItemOfSlot4 = CurrentItemOfSlot4.ITEM_RESTORE;
							break;
							case NONE: currentItemOfSlot4 = CurrentItemOfSlot4.NONE;
							break;
							case STEEL_FLYSWATTER: currentItemOfSlot4 = CurrentItemOfSlot4.STEEL_FLYSWATTER;
							break;
							case SUPER_GLUE: currentItemOfSlot4 = CurrentItemOfSlot4.SUPER_GLUE;
							break;
							case WIND_VANE: currentItemOfSlot4 = CurrentItemOfSlot4.WIND_VANE;
							break;
							case SWITCH_MACHINE: currentItemOfSlot4 = CurrentItemOfSlot4.SWITCH_MACHINE;
							break;
							case AUGMENTED_BURST: currentItemOfSlot4 = CurrentItemOfSlot4.AUGMENTED_BURST;
							break;
							case STICKER: currentItemOfSlot4 = CurrentItemOfSlot4.STICKER;
							break;
							default:
								break;
							}
						}; break;
						case 5: {
							switch (currentSelectedItem){
							case ACCELERATOR: currentItemOfSlot5 = CurrentItemOfSlot5.ACCELERATOR;
							break;
							case ACCELERATOR_X: currentItemOfSlot5 = CurrentItemOfSlot5.ACCELERATOR_X;
							break;
							case BOMB: currentItemOfSlot5 = CurrentItemOfSlot5.BOMB;
							break;
							case ELECTRIC_ZAPPER: currentItemOfSlot5 = CurrentItemOfSlot5.ELECTRIC_ZAPPER;
							break;
							case FLYSWATTER: currentItemOfSlot5 = CurrentItemOfSlot5.FLYSWATTER;
							break;
							case GLUE: currentItemOfSlot5 = CurrentItemOfSlot5.GLUE;
							break;
							case ICEFLAKE: currentItemOfSlot5 = CurrentItemOfSlot5.ICEFLAKE;
							break;
							case ITEM_RESTORE: currentItemOfSlot5 = CurrentItemOfSlot5.ITEM_RESTORE;
							break;
							case NONE: currentItemOfSlot5 = CurrentItemOfSlot5.NONE;
							break;
							case STEEL_FLYSWATTER: currentItemOfSlot5 = CurrentItemOfSlot5.STEEL_FLYSWATTER;
							break;
							case SUPER_GLUE: currentItemOfSlot5 = CurrentItemOfSlot5.SUPER_GLUE;
							break;
							case WIND_VANE: currentItemOfSlot5 = CurrentItemOfSlot5.WIND_VANE;
							break;
							case SWITCH_MACHINE: currentItemOfSlot5 = CurrentItemOfSlot5.SWITCH_MACHINE;
							break;
							case AUGMENTED_BURST: currentItemOfSlot5 = CurrentItemOfSlot5.AUGMENTED_BURST;
							break;
							case STICKER: currentItemOfSlot5 = CurrentItemOfSlot5.STICKER;
							break;
							default:
								break;
							}
						}; break;
						case 6: {
							switch (currentSelectedItem){
							case ACCELERATOR: currentItemOfSlot6 = CurrentItemOfSlot6.ACCELERATOR;
							break;
							case ACCELERATOR_X: currentItemOfSlot6 = CurrentItemOfSlot6.ACCELERATOR_X;
							break;
							case BOMB: currentItemOfSlot6 = CurrentItemOfSlot6.BOMB;
							break;
							case ELECTRIC_ZAPPER: currentItemOfSlot6 = CurrentItemOfSlot6.ELECTRIC_ZAPPER;
							break;
							case FLYSWATTER: currentItemOfSlot6 = CurrentItemOfSlot6.FLYSWATTER;
							break;
							case GLUE: currentItemOfSlot6 = CurrentItemOfSlot6.GLUE;
							break;
							case ICEFLAKE: currentItemOfSlot6 = CurrentItemOfSlot6.ICEFLAKE;
							break;
							case ITEM_RESTORE: currentItemOfSlot6 = CurrentItemOfSlot6.ITEM_RESTORE;
							break;
							case NONE: currentItemOfSlot6 = CurrentItemOfSlot6.NONE;
							break;
							case STEEL_FLYSWATTER: currentItemOfSlot6 = CurrentItemOfSlot6.STEEL_FLYSWATTER;
							break;
							case SUPER_GLUE: currentItemOfSlot6 = CurrentItemOfSlot6.SUPER_GLUE;
							break;
							case WIND_VANE: currentItemOfSlot6 = CurrentItemOfSlot6.WIND_VANE;
							break;
							case SWITCH_MACHINE: currentItemOfSlot6 = CurrentItemOfSlot6.SWITCH_MACHINE;
							break;
							case AUGMENTED_BURST: currentItemOfSlot6 = CurrentItemOfSlot6.AUGMENTED_BURST;
							break;
							case STICKER: currentItemOfSlot6 = CurrentItemOfSlot6.STICKER;
							break;
							default:
								break;
							}
						}; break;
						}
						currentSelectedItem = CurrentSelectedItem.NONE;
						toggleItemSelection = false;
					}
					return super.touchDown(event, x, y, pointer, button);
				}

			});

			gameRequirementsArrow.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					removeListeners = true;
					delayGameReqMovement = TimeUtils.nanoTime();
					return super.touchDown(event, x, y, pointer, button);
				}

			});
		}

		if (removeListeners){
			removeListeners = false;

			next.clearListeners();
			back.clearListeners();
			leftArrow.clearListeners();
			rightArrow.clearListeners();
			checkbox.clearListeners();
			trashCan1.clearListeners();
			trashCan2.clearListeners();
			trashCan3.clearListeners();
			trashCan4.clearListeners();
			selectAll.clearListeners();
			selectItem.clearListeners();
			item1.clearListeners();
			item2.clearListeners();
			item3.clearListeners();
			leftItemArrow.clearListeners();
			rightItemArrow.clearListeners();
			for (int i = 0; i < itemSlot.size; i++){
				itemSlot.get(i).clearListeners();
			}
		}
	}

	//	TODO
	private void setBounds() {
		if (!toggleItemSelection){
			if (isTrashCan1){
				trashCan1.setBounds(trashCan1Layer.x, trashCan1Layer.y, trashCan1Layer.width, trashCan1Layer.height);
			}
			if (isTrashCan2){
				trashCan2.setBounds(trashCan2Layer.x, trashCan2Layer.y, trashCan2Layer.width, trashCan2Layer.height);
			}
			if (isTrashCan3){
				trashCan3.setBounds(trashCan3Layer.x, trashCan3Layer.y, trashCan3Layer.width, trashCan3Layer.height);
			}
			if (isTrashCan4){
				trashCan4.setBounds(trashCan4Layer.x, trashCan4Layer.y, trashCan4Layer.width, trashCan4Layer.height);
			}
			if (currentSelectedCan != 0){
			}
			back.setBounds(backLayer.x, backLayer.y, backLayer.width, backLayer.height);
			next.setBounds(nextLayer.x, nextLayer.y, nextLayer.width, nextLayer.height);
			for (int i = 0; i < unlockedItemSlots; i++){
				itemSlot.get(i).setBounds(itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
			}
		}
		else {
			item1.setBounds(itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
			item2.setBounds(itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
			item3.setBounds(itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
			leftItemArrow.setBounds(leftItemArrowLayer.x, leftItemArrowLayer.y, leftItemArrowLayer.width, leftItemArrowLayer.height);
			rightItemArrow.setBounds(rightItemArrowLayer.x, rightItemArrowLayer.y, rightItemArrowLayer.width, rightItemArrowLayer.height);
			selectItem.setBounds(selectItemLayer.x, selectItemLayer.y, selectItemLayer.width, selectItemLayer.height);
		}
		gameRequirementsArrow.setBounds(gameRequirementsArrowLayer.x, gameRequirementsArrowLayer.y, gameRequirementsArrowLayer.width,
				gameRequirementsArrowLayer.height);
		item1.hit(touchPos2d.x, touchPos2d.x, true);
		item2.hit(touchPos2d.x, touchPos2d.x, true);
		item3.hit(touchPos2d.x, touchPos2d.x, true);
	}

	//	TODO

	private void drawTrashCans() {
		if (isTrashCan1){
			batch.draw(trashCan1Pic, trashCan1Layer.x, trashCan1Layer.y, trashCan1Layer.width, trashCan1Layer.height);
		} else batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
				68 + 16, 580, 64, 64);
		if (isTrashCan2){
			batch.draw(trashCan2Pic, trashCan2Layer.x, trashCan2Layer.y, trashCan2Layer.width, trashCan2Layer.height);
		} else batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
				164 + 16, 580, 64, 64);
		if (isTrashCan3){
			batch.draw(trashCan3Pic, trashCan3Layer.x, trashCan3Layer.y, trashCan3Layer.width, trashCan3Layer.height);
		} else batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
				260 + 16, 580, 64, 64);
		if (isTrashCan4){
			batch.draw(trashCan4Pic, trashCan4Layer.x, trashCan4Layer.y, trashCan4Layer.width, trashCan4Layer.height);
		} else batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
				356 + 16, 580, 64, 64);
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
			touchPos2d.set(Gdx.input.getX(), Gdx.input.getY());
			camera.unproject(touchPos);
			
			if (Gdx.input.justTouched()){
				delayAds = TimeUtils.millis();
			}
			if (TimeUtils.millis() - delayAds >= 5000 && junkWorldEngines.getGameSelection() == 1 && junkWorldEngines.getGameMode() == 0){
				delayAds = TimeUtils.millis();
//				myRequestHandler.showAds2(true);
			}

			setTexture();
			setActors();
			setGameAssets();
			setBounds();
			checkCurrrentItemOfSlots();

			colorAnima.getKeyFrame(stateTime, true);
			currentItemOfSelection1 = currentItemOfSelection2 - 1;
			currentItemOfSelection3 = currentItemOfSelection2 + 1;

			batch.begin();
			batch.draw(manager.get("backgrounds/firstBackground.png", Texture.class), 0, 0);
			batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader.png", Texture.class), 0, 0, 512, 800);
			batch.draw(manager.get("backgrounds/blueFrame.png", Texture.class), 0, 0);
			batch.draw(manager.get("buttons/backButton.png", Texture.class), 64, 64, 100, 50);
			batch.draw(manager.get("buttons/nextButton.png", Texture.class), 350, 64, 100, 50);
			batch.draw(manager.get("screenLabels/tuning.png", Texture.class), 256 - 110, 670, 220, 64);
			drawTrashCans();
			drawItemSelectionAssets();
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

			if (delaySetScreen != 0){
				if (TimeUtils.millis() - delaySetScreen >= 10){
					if (junkWorldEngines.getGameMode() == 0){
						junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
					}
					isLoading = true;
					disposeAssets();
					unloadManager();
					junkWorldEngines.setTrashCan1Properties(trashCan1Properties);
					junkWorldEngines.setTrashCan2Properties(trashCan2Properties);
					junkWorldEngines.setTrashCan3Properties(trashCan3Properties);
					junkWorldEngines.setTrashCan4Properties(trashCan4Properties);
					junkWorldEngines.setGamePalette(gamePalette);
					game.setScreen(new GameScreen(game, trashCan1Properties, trashCan2Properties,
							trashCan3Properties,trashCan4Properties, manager, junkWorldEngines, gamePalette, 
							currentItemOfSlot1, currentItemOfSlot2, currentItemOfSlot3, currentItemOfSlot4,
							currentItemOfSlot5, currentItemOfSlot6));
				}
			}


			if (junkWorldEngines.getGameMode() == 0){
				junkWorldEngines.getTutorialStage().getViewport().setCamera(camera);
				junkWorldEngines.getTutorialStage().draw();
				junkWorldEngines.getTutorialStage().act();
				Gdx.input.setInputProcessor(junkWorldEngines.getTutorialStage());
			}
			else {
				if (!toggleItemSelection){
					stage.getViewport().setCamera(camera);
					Gdx.input.setInputProcessor(stage);
					stage.act();
					stage.draw();
				}
				else {
					stage2.getViewport().setCamera(camera);
					Gdx.input.setInputProcessor(stage2);
					stage2.act();
					stage2.draw();
				}
			}
		}
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
			case 8: {
				item1.setTouchable(Touchable.disabled);
				item2.setTouchable(Touchable.disabled);
				item3.setTouchable(Touchable.disabled);
				selectItem.setTouchable(Touchable.disabled);
				next.setTouchable(Touchable.disabled);
				junkWorldEngines.getTutorialLayer().setPosition(200, 20);
			}; break;
			case 9: {
				item1.setTouchable(Touchable.enabled);
				item2.setTouchable(Touchable.enabled);
				item3.setTouchable(Touchable.enabled);
				selectItem.setTouchable(Touchable.enabled);
				for (Actor slot : itemSlot){
					slot.setTouchable(Touchable.disabled);
				}
				junkWorldEngines.getTutorialLayer().setPosition(0, 400);
				next.setTouchable(Touchable.enabled);
			}; break;
			default: break;
			}
			batch.draw(junkWorldEngines.getTutorial().get(junkWorldEngines.getCurrentTutorial()),
					junkWorldEngines.getTutorialLayer().x, junkWorldEngines.getTutorialLayer().y);
			junkWorldEngines.getQuitTutorial().setBounds(junkWorldEngines.getTutorialLayer().x, junkWorldEngines.getTutorialLayer().y, 120, 50);
		}
	}

	//	TODO render

	private void setGameAssets() {
		if (setGameAssets){
			setGameAssets = false;
			for (int i = 1; i < unlockedItemSlots + 1; i++){
				itemFrameLayer.width += 64;
				itemFrameLayer.x = 256 - (itemFrameLayer.width / 2);
			}
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
						selectedCanChanged = true;
					}
				}
			}
		}

		if (TimeUtils.millis() - delaySelectAllFrame >= 500){
			delaySelectAllFrame = TimeUtils.millis();
			if (currentSelectAllRegion + 1 == 3){
				currentSelectAllRegion = 0;
			}
			else currentSelectAllRegion += 1;
		}

		if (delaySelectAll != 0){
			if (TimeUtils.nanoTime() - delaySelectAll >= 10000){
				delaySelectAll = 0;

				if (!isSelectAll){
					isSelectAll = true;
					if (trashCan1Properties.getTrashCan() != 0){
						switch (trashCan1Properties.getTrashCan()){
						case 1: {
							if (junkWorldEngines.isAverageSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isAverageSkill2Unlocked()){
								trashCan1Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isAverageSkill3Unlocked()){
								trashCan1Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isAverageSkill4Unlocked()){
								trashCan1Properties.setUpgrade4(true);
							}
						}; break;
						case 2: {
							if (junkWorldEngines.isScorchingSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isScorchingSkill2Unlocked()){
								trashCan1Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isScorchingSkill3Unlocked()){
								trashCan1Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isScorchingSkill4Unlocked()){
								trashCan1Properties.setUpgrade4(true);
							}
						}; break;
						case 3: {
							if (junkWorldEngines.isVacuumSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isVacuumSkill2Unlocked()){
								trashCan1Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isVacuumSkill3Unlocked()){
								trashCan1Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isVacuumSkill4Unlocked()){
								trashCan1Properties.setUpgrade4(true);
							}
						}; break;
						case 4: {
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan1Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan1Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan1Properties.setUpgrade4(true);
							}
						}; break;
						case 5: {
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan1Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan1Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan1Properties.setUpgrade4(true);
							}
						}; break;
						case 6: {
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan1Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan1Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan1Properties.setUpgrade4(true);
							}
						}; break;
						default: break;
						}
					}
					if (trashCan2Properties.getTrashCan() != 0){
						switch (trashCan2Properties.getTrashCan()){
						case 1: {
							if (junkWorldEngines.isAverageSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isAverageSkill2Unlocked()){
								trashCan2Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isAverageSkill3Unlocked()){
								trashCan2Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isAverageSkill4Unlocked()){
								trashCan2Properties.setUpgrade4(true);
							}
						}; break;
						case 2: {
							if (junkWorldEngines.isScorchingSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isScorchingSkill2Unlocked()){
								trashCan2Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isScorchingSkill3Unlocked()){
								trashCan2Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isScorchingSkill4Unlocked()){
								trashCan2Properties.setUpgrade4(true);
							}
						}; break;
						case 3: {
							if (junkWorldEngines.isVacuumSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isVacuumSkill2Unlocked()){
								trashCan2Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isVacuumSkill3Unlocked()){
								trashCan2Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isVacuumSkill4Unlocked()){
								trashCan2Properties.setUpgrade4(true);
							}
						}; break;
						case 4: {
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan2Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan2Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan2Properties.setUpgrade4(true);
							}
						}; break;
						case 5: {
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan2Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan2Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan2Properties.setUpgrade4(true);
							}
						}; break;
						case 6: {
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan2Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan2Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan2Properties.setUpgrade4(true);
							}
						}; break;
						default: break;
						}
					}
					if (trashCan3Properties.getTrashCan() != 0){
						switch (trashCan3Properties.getTrashCan()){
						case 1: {
							if (junkWorldEngines.isAverageSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isAverageSkill2Unlocked()){
								trashCan3Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isAverageSkill3Unlocked()){
								trashCan3Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isAverageSkill4Unlocked()){
								trashCan3Properties.setUpgrade4(true);
							}
						}; break;
						case 2: {
							if (junkWorldEngines.isScorchingSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isScorchingSkill2Unlocked()){
								trashCan3Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isScorchingSkill3Unlocked()){
								trashCan3Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isScorchingSkill4Unlocked()){
								trashCan3Properties.setUpgrade4(true);
							}
						}; break;
						case 3: {
							if (junkWorldEngines.isVacuumSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isVacuumSkill2Unlocked()){
								trashCan3Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isVacuumSkill3Unlocked()){
								trashCan3Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isVacuumSkill4Unlocked()){
								trashCan3Properties.setUpgrade4(true);
							}
						}; break;
						case 4: {
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan3Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan3Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan3Properties.setUpgrade4(true);
							}
						}; break;
						case 5: {
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan3Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan3Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan3Properties.setUpgrade4(true);
							}
						}; break;
						case 6: {
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan3Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan3Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan3Properties.setUpgrade4(true);
							}
						}; break;
						default: break;
						}
					}
					if (trashCan4Properties.getTrashCan() != 0){
						switch (trashCan4Properties.getTrashCan()){
						case 1: {
							if (junkWorldEngines.isAverageSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isAverageSkill2Unlocked()){
								trashCan4Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isAverageSkill3Unlocked()){
								trashCan4Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isAverageSkill4Unlocked()){
								trashCan4Properties.setUpgrade4(true);
							}
						}; break;
						case 2: {
							if (junkWorldEngines.isScorchingSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isScorchingSkill2Unlocked()){
								trashCan4Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isScorchingSkill3Unlocked()){
								trashCan4Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isScorchingSkill4Unlocked()){
								trashCan4Properties.setUpgrade4(true);
							}
						}; break;
						case 3: {
							if (junkWorldEngines.isVacuumSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isVacuumSkill2Unlocked()){
								trashCan4Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isVacuumSkill3Unlocked()){
								trashCan4Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isVacuumSkill4Unlocked()){
								trashCan4Properties.setUpgrade4(true);
							}
						}; break;
						case 4: {
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan4Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan4Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan4Properties.setUpgrade4(true);
							}
						}; break;
						case 5: {
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan4Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan4Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan4Properties.setUpgrade4(true);
							}
						}; break;
						case 6: {
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan4Properties.setUpgrade2(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan4Properties.setUpgrade3(true);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan4Properties.setUpgrade4(true);
							}
						}; break;
						default: break;
						}
					}
				}
				else {
					isSelectAll = false;
					if (trashCan1Properties.getTrashCan() != 0){
						switch (trashCan1Properties.getTrashCan()){
						case 1: {
							if (junkWorldEngines.isAverageSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isAverageSkill2Unlocked()){
								trashCan1Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isAverageSkill3Unlocked()){
								trashCan1Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isAverageSkill4Unlocked()){
								trashCan1Properties.setUpgrade4(false);
							}
						}; break;
						case 2: {
							if (junkWorldEngines.isScorchingSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isScorchingSkill2Unlocked()){
								trashCan1Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isScorchingSkill3Unlocked()){
								trashCan1Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isScorchingSkill4Unlocked()){
								trashCan1Properties.setUpgrade4(false);
							}
						}; break;
						case 3: {
							if (junkWorldEngines.isVacuumSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isVacuumSkill2Unlocked()){
								trashCan1Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isVacuumSkill3Unlocked()){
								trashCan1Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isVacuumSkill4Unlocked()){
								trashCan1Properties.setUpgrade4(false);
							}
						}; break;
						case 4: {
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan1Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan1Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan1Properties.setUpgrade4(false);
							}
						}; break;
						case 5: {
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan1Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan1Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan1Properties.setUpgrade4(false);
							}
						}; break;
						case 6: {
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan1Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan1Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan1Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan1Properties.setUpgrade4(false);
							}
						}; break;
						default: break;
						}
					}
					if (trashCan2Properties.getTrashCan() != 0){
						switch (trashCan2Properties.getTrashCan()){
						case 1: {
							if (junkWorldEngines.isAverageSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isAverageSkill2Unlocked()){
								trashCan2Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isAverageSkill3Unlocked()){
								trashCan2Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isAverageSkill4Unlocked()){
								trashCan2Properties.setUpgrade4(false);
							}
						}; break;
						case 2: {
							if (junkWorldEngines.isScorchingSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isScorchingSkill2Unlocked()){
								trashCan2Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isScorchingSkill3Unlocked()){
								trashCan2Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isScorchingSkill4Unlocked()){
								trashCan2Properties.setUpgrade4(false);
							}
						}; break;
						case 3: {
							if (junkWorldEngines.isVacuumSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isVacuumSkill2Unlocked()){
								trashCan2Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isVacuumSkill3Unlocked()){
								trashCan2Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isVacuumSkill4Unlocked()){
								trashCan2Properties.setUpgrade4(false);
							}
						}; break;
						case 4: {
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan2Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan2Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan2Properties.setUpgrade4(false);
							}
						}; break;
						case 5: {
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan2Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan2Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan2Properties.setUpgrade4(false);
							}
						}; break;
						case 6: {
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan2Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan2Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan2Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan2Properties.setUpgrade4(false);
							}
						}; break;
						default: break;
						}
					}
					if (trashCan3Properties.getTrashCan() != 0){
						switch (trashCan3Properties.getTrashCan()){
						case 1: {
							if (junkWorldEngines.isAverageSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isAverageSkill2Unlocked()){
								trashCan3Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isAverageSkill3Unlocked()){
								trashCan3Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isAverageSkill4Unlocked()){
								trashCan3Properties.setUpgrade4(false);
							}
						}; break;
						case 2: {
							if (junkWorldEngines.isScorchingSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isScorchingSkill2Unlocked()){
								trashCan3Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isScorchingSkill3Unlocked()){
								trashCan3Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isScorchingSkill4Unlocked()){
								trashCan3Properties.setUpgrade4(false);
							}
						}; break;
						case 3: {
							if (junkWorldEngines.isVacuumSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isVacuumSkill2Unlocked()){
								trashCan3Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isVacuumSkill3Unlocked()){
								trashCan3Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isVacuumSkill4Unlocked()){
								trashCan3Properties.setUpgrade4(false);
							}
						}; break;
						case 4: {
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan3Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan3Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan3Properties.setUpgrade4(false);
							}
						}; break;
						case 5: {
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan3Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan3Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan3Properties.setUpgrade4(false);
							}
						}; break;
						case 6: {
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan3Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan3Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan3Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan3Properties.setUpgrade4(false);
							}
						}; break;
						default: break;
						}
					}
					if (trashCan4Properties.getTrashCan() != 0){
						switch (trashCan4Properties.getTrashCan()){
						case 1: {
							if (junkWorldEngines.isAverageSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isAverageSkill2Unlocked()){
								trashCan4Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isAverageSkill3Unlocked()){
								trashCan4Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isAverageSkill4Unlocked()){
								trashCan4Properties.setUpgrade4(false);
							}
						}; break;
						case 2: {
							if (junkWorldEngines.isScorchingSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isScorchingSkill2Unlocked()){
								trashCan4Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isScorchingSkill3Unlocked()){
								trashCan4Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isScorchingSkill4Unlocked()){
								trashCan4Properties.setUpgrade4(false);
							}
						}; break;
						case 3: {
							if (junkWorldEngines.isVacuumSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isVacuumSkill2Unlocked()){
								trashCan4Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isVacuumSkill3Unlocked()){
								trashCan4Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isVacuumSkill4Unlocked()){
								trashCan4Properties.setUpgrade4(false);
							}
						}; break;
						case 4: {
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan4Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan4Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isBlowerSkill1Unlocked()){
								trashCan4Properties.setUpgrade4(false);
							}
						}; break;
						case 5: {
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan4Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan4Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isDullSkill1Unlocked()){
								trashCan4Properties.setUpgrade4(false);
							}
						}; break;
						case 6: {
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan4Properties.setUpgrade1(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan4Properties.setUpgrade2(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan4Properties.setUpgrade3(false);
							}
							if (junkWorldEngines.isSwiftSkill1Unlocked()){
								trashCan4Properties.setUpgrade4(false);
							}
						}; break;
						default: break;
						}
					}
				}
			}
		}

		switch (currentSelectedItem){
		case ACCELERATOR:
			break;
		case ACCELERATOR_X:
			break;
		case BOMB: itemDescriptionRegion.setRegion(manager.get("gameScreenAssets/itemAssets/itemDescriptions/description1.png", Texture.class));
		break;
		case ELECTRIC_ZAPPER:
			break;
		case FLYSWATTER: 
			break;
		case GLUE: itemDescriptionRegion.setRegion(manager.get("gameScreenAssets/itemAssets/itemDescriptions/description2.png", Texture.class));
		break;
		case ICEFLAKE: itemDescriptionRegion.setRegion(manager.get("gameScreenAssets/itemAssets/itemDescriptions/description3.png", Texture.class));
		break;
		case ITEM_RESTORE:
			break;
		case NONE: itemDescriptionRegion.setRegion(manager.get("gameScreenAssets/itemAssets/itemDescriptions/description0.png", Texture.class));
		break;
		case STEEL_FLYSWATTER:
			break;
		case SUPER_GLUE:
			break;
		case WIND_VANE:
			break;
		case SWITCH_MACHINE: itemDescriptionRegion.setRegion(manager.get("gameScreenAssets/itemAssets/itemDescriptions/description4.png", Texture.class));
		break;
		case AUGMENTED_BURST: itemDescriptionRegion.setRegion(manager.get("gameScreenAssets/itemAssets/itemDescriptions/description5.png", Texture.class));
		break;
		case STICKER: itemDescriptionRegion.setRegion(manager.get("gameScreenAssets/itemAssets/itemDescriptions/description6.png", Texture.class));
		break;
		default:
			break;
		}
	}

	private void drawItemSelectionAssets() {
		for (int i = 0; i < unlockedItemSlots; i++){
			switch (i){
			case 0: {
				switch (currentItemOfSlot1){
				case ACCELERATOR:
					break;
				case ACCELERATOR_X:
					break;
				case BOMB: batch.draw(itemRegions.get(0), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ELECTRIC_ZAPPER:
					break;
				case FLYSWATTER:
					break;
				case GLUE: batch.draw(itemRegions.get(1), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ICEFLAKE: batch.draw(itemRegions.get(2), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ITEM_RESTORE:
					break;
				case NONE: batch.draw(clickHereRegion, itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STEEL_FLYSWATTER:
					break;
				case SUPER_GLUE:
					break;
				case WIND_VANE:
					break;
				case SWITCH_MACHINE: batch.draw(itemRegions.get(3), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case AUGMENTED_BURST: batch.draw(itemRegions.get(4), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STICKER: batch.draw(itemRegions.get(5), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				default:
					break;
				}
			}; break;
			case 1: {
				switch (currentItemOfSlot2){
				case ACCELERATOR:
					break;
				case ACCELERATOR_X:
					break;
				case BOMB: batch.draw(itemRegions.get(0), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ELECTRIC_ZAPPER:
					break;
				case FLYSWATTER:
					break;
				case GLUE: batch.draw(itemRegions.get(1), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ICEFLAKE: batch.draw(itemRegions.get(2), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ITEM_RESTORE:
					break;
				case NONE: batch.draw(clickHereRegion, itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STEEL_FLYSWATTER:
					break;
				case SUPER_GLUE:
					break;
				case WIND_VANE:
					break;
				case SWITCH_MACHINE: batch.draw(itemRegions.get(3), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case AUGMENTED_BURST: batch.draw(itemRegions.get(4), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STICKER: batch.draw(itemRegions.get(5), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				default:
					break;
				}
			}; break;
			case 2: {
				switch (currentItemOfSlot3){
				case ACCELERATOR:
					break;
				case ACCELERATOR_X:
					break;
				case BOMB: batch.draw(itemRegions.get(0), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ELECTRIC_ZAPPER:
					break;
				case FLYSWATTER:
					break;
				case GLUE: batch.draw(itemRegions.get(1), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ICEFLAKE: batch.draw(itemRegions.get(2), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ITEM_RESTORE:
					break;
				case NONE: batch.draw(clickHereRegion, itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STEEL_FLYSWATTER:
					break;
				case SUPER_GLUE:
					break;
				case WIND_VANE:
					break;
				case SWITCH_MACHINE: batch.draw(itemRegions.get(3), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case AUGMENTED_BURST: batch.draw(itemRegions.get(4), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STICKER: batch.draw(itemRegions.get(5), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				default:
					break;
				}
			}; break;
			case 3: {
				switch (currentItemOfSlot4){
				case ACCELERATOR:
					break;
				case ACCELERATOR_X:
					break;
				case BOMB: batch.draw(itemRegions.get(0), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ELECTRIC_ZAPPER:
					break;
				case FLYSWATTER:
					break;
				case GLUE: batch.draw(itemRegions.get(1), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ICEFLAKE: batch.draw(itemRegions.get(2), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ITEM_RESTORE:
					break;
				case NONE: batch.draw(clickHereRegion, itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STEEL_FLYSWATTER:
					break;
				case SUPER_GLUE:
					break;
				case WIND_VANE:
					break;
				case SWITCH_MACHINE: batch.draw(itemRegions.get(3), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case AUGMENTED_BURST: batch.draw(itemRegions.get(4), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STICKER: batch.draw(itemRegions.get(5), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				default:
					break;
				}
			}; break;
			case 4: {
				switch (currentItemOfSlot5){
				case ACCELERATOR:
					break;
				case ACCELERATOR_X:
					break;
				case BOMB: batch.draw(itemRegions.get(0), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ELECTRIC_ZAPPER:
					break;
				case FLYSWATTER:
					break;
				case GLUE: batch.draw(itemRegions.get(1), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ICEFLAKE: batch.draw(itemRegions.get(2), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ITEM_RESTORE:
					break;
				case NONE: batch.draw(clickHereRegion, itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STEEL_FLYSWATTER:
					break;
				case SUPER_GLUE:
					break;
				case WIND_VANE:
					break;
				case SWITCH_MACHINE: batch.draw(itemRegions.get(3), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case AUGMENTED_BURST: batch.draw(itemRegions.get(4), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STICKER: batch.draw(itemRegions.get(5), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				default:
					break;
				}
			}; break;
			case 5: {
				switch (currentItemOfSlot6){
				case ACCELERATOR:
					break;
				case ACCELERATOR_X:
					break;
				case BOMB: batch.draw(itemRegions.get(0), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ELECTRIC_ZAPPER:
					break;
				case FLYSWATTER:
					break;
				case GLUE: batch.draw(itemRegions.get(1), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ICEFLAKE: batch.draw(itemRegions.get(2), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case ITEM_RESTORE:
					break;
				case NONE: batch.draw(clickHereRegion, itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STEEL_FLYSWATTER:
					break;
				case SUPER_GLUE:
					break;
				case WIND_VANE:
					break;
				case SWITCH_MACHINE: batch.draw(itemRegions.get(3), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case AUGMENTED_BURST: batch.draw(itemRegions.get(4), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				case STICKER: batch.draw(itemRegions.get(5), itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
				break;
				default:
					break;
				}
			}; break;
			}
		}

		for (int i = 0; i < unlockedItemSlots; i++){
			batch.draw(itemFrameRegion, itemFrameLayer.x + (64 * i) , itemFrameLayer.y, 64, itemFrameLayer.height);
		}

		if (toggleItemSelection){
			batch.draw(greenBGRegion, 0, 0, 512, 800);
			batch.draw(manager.get("screenLabels/selectItems.png", Texture.class), 256 - 125, 700, 250, 80);
			batch.draw(itemDescriptionRegion, itemDescriptionLayer.x, itemDescriptionLayer.y, itemDescriptionLayer.width, itemDescriptionLayer.height);
			batch.draw(selectItemRegion, selectItemLayer.x, selectItemLayer.y, selectItemLayer.width, selectItemLayer.height);

			switch (currentItemOfSelection1){
			case 1: {
				if (currentSelectedItem == CurrentSelectedItem.BOMB){
					batch.draw(itemRegionsClicked.get(0), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (bombPos == 1 || bombPos == 0)) ||
							(currentSelectedItemSlot == 2 && (bombPos == 2 || bombPos == 0)) ||
							(currentSelectedItemSlot == 3 && (bombPos == 3 || bombPos == 0)) ||
							(currentSelectedItemSlot == 4 && (bombPos == 4 || bombPos == 0)) ||
							(currentSelectedItemSlot == 5 && (bombPos == 5 || bombPos == 0)) ||
							(currentSelectedItemSlot == 6 && (bombPos == 6 || bombPos == 0))){
						batch.draw(itemRegions.get(0), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(0), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
				}
			}; break;
			case 2: {
				if (currentSelectedItem == CurrentSelectedItem.GLUE){
					batch.draw(itemRegionsClicked.get(1), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (gluePos == 1 || gluePos == 0)) ||
							(currentSelectedItemSlot == 2 && (gluePos == 2 || gluePos == 0)) ||
							(currentSelectedItemSlot == 3 && (gluePos == 3 || gluePos == 0)) ||
							(currentSelectedItemSlot == 4 && (gluePos == 4 || gluePos == 0)) ||
							(currentSelectedItemSlot == 5 && (gluePos == 5 || gluePos == 0)) ||
							(currentSelectedItemSlot == 6 && (gluePos == 6 || gluePos == 0))){
						batch.draw(itemRegions.get(1), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(1), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
				}
			}; break;
			case 3: {
				if (currentSelectedItem == CurrentSelectedItem.ICEFLAKE){
					batch.draw(itemRegionsClicked.get(2), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (iceflakePos == 1 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 2 && (iceflakePos == 2 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 3 && (iceflakePos == 3 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 4 && (iceflakePos == 4 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 5 && (iceflakePos == 5 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 6 && (iceflakePos == 6 || iceflakePos == 0))){
						batch.draw(itemRegions.get(2), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(2), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
				}
			}; break;
			case 4: {
				if (currentSelectedItem == CurrentSelectedItem.SWITCH_MACHINE){
					batch.draw(itemRegionsClicked.get(3), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (switchMachinePos == 1 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 2 && (switchMachinePos == 2 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 3 && (switchMachinePos == 3 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 4 && (switchMachinePos == 4 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 5 && (switchMachinePos == 5 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 6 && (switchMachinePos == 6 || switchMachinePos == 0))){
						batch.draw(itemRegions.get(3), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(3), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
				}
			}; break;
			case 5: {
				if (currentSelectedItem == CurrentSelectedItem.AUGMENTED_BURST){
					batch.draw(itemRegionsClicked.get(4), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (augmentedBurstPos == 1 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 2 && (augmentedBurstPos == 2 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 3 && (augmentedBurstPos == 3 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 4 && (augmentedBurstPos == 4 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 5 && (augmentedBurstPos == 5 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 6 && (augmentedBurstPos == 6 || augmentedBurstPos == 0))){
						batch.draw(itemRegions.get(4), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(4), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
				}
			}; break;
			case 6: {
				if (currentSelectedItem == CurrentSelectedItem.STICKER){
					batch.draw(itemRegionsClicked.get(5), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (stickerPos == 1 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 2 && (stickerPos == 2 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 3 && (stickerPos == 3 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 4 && (stickerPos == 4 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 5 && (stickerPos == 5 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 6 && (stickerPos == 6 || stickerPos == 0))){
						batch.draw(itemRegions.get(5), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(5), itemLayer1.x, itemLayer1.y, itemLayer1.width, itemLayer1.height);
					}
				}
			}; break;
			default: break;
			}

			switch (currentItemOfSelection2){
			case 1: {
				if (currentSelectedItem == CurrentSelectedItem.BOMB){
					batch.draw(itemRegionsClicked.get(0), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (bombPos == 1 || bombPos == 0)) ||
							(currentSelectedItemSlot == 2 && (bombPos == 2 || bombPos == 0)) ||
							(currentSelectedItemSlot == 3 && (bombPos == 3 || bombPos == 0)) ||
							(currentSelectedItemSlot == 4 && (bombPos == 4 || bombPos == 0)) ||
							(currentSelectedItemSlot == 5 && (bombPos == 5 || bombPos == 0)) ||
							(currentSelectedItemSlot == 6 && (bombPos == 6 || bombPos == 0))){
						batch.draw(itemRegions.get(0), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(0), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
				}
			}; break;
			case 2: {
				if (currentSelectedItem == CurrentSelectedItem.GLUE){
					batch.draw(itemRegionsClicked.get(1), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (gluePos == 1 || gluePos == 0)) ||
							(currentSelectedItemSlot == 2 && (gluePos == 2 || gluePos == 0)) ||
							(currentSelectedItemSlot == 3 && (gluePos == 3 || gluePos == 0)) ||
							(currentSelectedItemSlot == 4 && (gluePos == 4 || gluePos == 0)) ||
							(currentSelectedItemSlot == 5 && (gluePos == 5 || gluePos == 0)) ||
							(currentSelectedItemSlot == 6 && (gluePos == 6 || gluePos == 0))){
						batch.draw(itemRegions.get(1), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(1), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
				}
			}; break;
			case 3: {
				if (currentSelectedItem == CurrentSelectedItem.ICEFLAKE){
					batch.draw(itemRegionsClicked.get(2), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (iceflakePos == 1 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 2 && (iceflakePos == 2 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 3 && (iceflakePos == 3 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 4 && (iceflakePos == 4 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 5 && (iceflakePos == 5 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 6 && (iceflakePos == 6 || iceflakePos == 0))){
						batch.draw(itemRegions.get(2), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(2), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
				}
			}; break;
			case 4: {
				if (currentSelectedItem == CurrentSelectedItem.SWITCH_MACHINE){
					batch.draw(itemRegionsClicked.get(3), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (switchMachinePos == 1 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 2 && (switchMachinePos == 2 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 3 && (switchMachinePos == 3 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 4 && (switchMachinePos == 4 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 5 && (switchMachinePos == 5 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 6 && (switchMachinePos == 6 || switchMachinePos == 0))){
						batch.draw(itemRegions.get(3), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(3), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
				}
			}; break;
			case 5: {
				if (currentSelectedItem == CurrentSelectedItem.AUGMENTED_BURST){
					batch.draw(itemRegionsClicked.get(4), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (augmentedBurstPos == 1 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 2 && (augmentedBurstPos == 2 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 3 && (augmentedBurstPos == 3 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 4 && (augmentedBurstPos == 4 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 5 && (augmentedBurstPos == 5 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 6 && (augmentedBurstPos == 6 || augmentedBurstPos == 0))){
						batch.draw(itemRegions.get(4), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(4), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
				}
			}; break;
			case 6: {
				if (currentSelectedItem == CurrentSelectedItem.STICKER){
					batch.draw(itemRegionsClicked.get(5), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (stickerPos == 1 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 2 && (stickerPos == 2 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 3 && (stickerPos == 3 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 4 && (stickerPos == 4 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 5 && (stickerPos == 5 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 6 && (stickerPos == 6 || stickerPos == 0))){
						batch.draw(itemRegions.get(5), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(5), itemLayer2.x, itemLayer2.y, itemLayer2.width, itemLayer2.height);
					}
				}
			}; break;
			default: break;
			}

			switch (currentItemOfSelection3){
			case 1: {
				if (currentSelectedItem == CurrentSelectedItem.BOMB){
					batch.draw(itemRegionsClicked.get(0), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (bombPos == 1 || bombPos == 0)) ||
							(currentSelectedItemSlot == 2 && (bombPos == 2 || bombPos == 0)) ||
							(currentSelectedItemSlot == 3 && (bombPos == 3 || bombPos == 0)) ||
							(currentSelectedItemSlot == 4 && (bombPos == 4 || bombPos == 0)) ||
							(currentSelectedItemSlot == 5 && (bombPos == 5 || bombPos == 0)) ||
							(currentSelectedItemSlot == 6 && (bombPos == 6 || bombPos == 0))){
						batch.draw(itemRegions.get(0), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(0), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
				}
			}; break;
			case 2: {
				if (currentSelectedItem == CurrentSelectedItem.GLUE){
					batch.draw(itemRegionsClicked.get(1), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (gluePos == 1 || gluePos == 0)) ||
							(currentSelectedItemSlot == 2 && (gluePos == 2 || gluePos == 0)) ||
							(currentSelectedItemSlot == 3 && (gluePos == 3 || gluePos == 0)) ||
							(currentSelectedItemSlot == 4 && (gluePos == 4 || gluePos == 0)) ||
							(currentSelectedItemSlot == 5 && (gluePos == 5 || gluePos == 0)) ||
							(currentSelectedItemSlot == 6 && (gluePos == 6 || gluePos == 0))){
						batch.draw(itemRegions.get(1), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(1), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
				}
			}; break;
			case 3: {
				if (currentSelectedItem == CurrentSelectedItem.ICEFLAKE){
					batch.draw(itemRegionsClicked.get(2), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (iceflakePos == 1 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 2 && (iceflakePos == 2 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 3 && (iceflakePos == 3 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 4 && (iceflakePos == 4 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 5 && (iceflakePos == 5 || iceflakePos == 0)) ||
							(currentSelectedItemSlot == 6 && (iceflakePos == 6 || iceflakePos == 0))){
						batch.draw(itemRegions.get(2), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(2), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
				}
			}; break;
			case 4: {
				if (currentSelectedItem == CurrentSelectedItem.SWITCH_MACHINE){
					batch.draw(itemRegionsClicked.get(3), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (switchMachinePos == 1 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 2 && (switchMachinePos == 2 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 3 && (switchMachinePos == 3 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 4 && (switchMachinePos == 4 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 5 && (switchMachinePos == 5 || switchMachinePos == 0)) ||
							(currentSelectedItemSlot == 6 && (switchMachinePos == 6 || switchMachinePos == 0))){
						batch.draw(itemRegions.get(3), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(3), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
				}
			}; break;
			case 5: {
				if (currentSelectedItem == CurrentSelectedItem.AUGMENTED_BURST){
					batch.draw(itemRegionsClicked.get(4), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (augmentedBurstPos == 1 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 2 && (augmentedBurstPos == 2 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 3 && (augmentedBurstPos == 3 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 4 && (augmentedBurstPos == 4 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 5 && (augmentedBurstPos == 5 || augmentedBurstPos == 0)) ||
							(currentSelectedItemSlot == 6 && (augmentedBurstPos == 6 || augmentedBurstPos == 0))){
						batch.draw(itemRegions.get(4), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(4), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
				}
			}; break;
			case 6: {
				if (currentSelectedItem == CurrentSelectedItem.STICKER){
					batch.draw(itemRegionsClicked.get(5), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
				}
				else {
					if ((currentSelectedItemSlot == 1 && (stickerPos == 1 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 2 && (stickerPos == 2 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 3 && (stickerPos == 3 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 4 && (stickerPos == 4 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 5 && (stickerPos == 5 || stickerPos == 0)) ||
							(currentSelectedItemSlot == 6 && (stickerPos == 6 || stickerPos == 0))){
						batch.draw(itemRegions.get(5), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
					else {
						batch.draw(itemRegionsUnclickable.get(5), itemLayer3.x, itemLayer3.y, itemLayer3.width, itemLayer3.height);
					}
				}
			}; break;
			default: break;
			}

			batch.draw(leftItemArrowRegion, leftItemArrowLayer.x, leftItemArrowLayer.y, leftItemArrowLayer.width, leftItemArrowLayer.height);
			batch.draw(rightItemArrowRegion, rightItemArrowLayer.x, rightItemArrowLayer.y, rightItemArrowLayer.width, rightItemArrowLayer.height);
		}
	}

	private void checkCurrrentItemOfSlots() {
		if (currentItemOfSlot1 == CurrentItemOfSlot1.BOMB){
			bombPos = 1;
		}
		else if (currentItemOfSlot2 == CurrentItemOfSlot2.BOMB){
			bombPos = 2;
		}
		else if (currentItemOfSlot3 == CurrentItemOfSlot3.BOMB){
			bombPos = 3;
		}
		else if (currentItemOfSlot4 == CurrentItemOfSlot4.BOMB){
			bombPos = 4;
		}
		else if (currentItemOfSlot5 == CurrentItemOfSlot5.BOMB){
			bombPos = 5;
		}
		else if (currentItemOfSlot6 == CurrentItemOfSlot6.BOMB){
			bombPos = 6;
		}
		else bombPos = 0;

		if (currentItemOfSlot1 == CurrentItemOfSlot1.GLUE){
			gluePos = 1;
		}
		else if (currentItemOfSlot2 == CurrentItemOfSlot2.GLUE){
			gluePos = 2;
		}
		else if (currentItemOfSlot3 == CurrentItemOfSlot3.GLUE){
			gluePos = 3;
		}
		else if (currentItemOfSlot4 == CurrentItemOfSlot4.GLUE){
			gluePos = 4;
		}
		else if (currentItemOfSlot5 == CurrentItemOfSlot5.GLUE){
			gluePos = 5;
		}
		else if (currentItemOfSlot6 == CurrentItemOfSlot6.GLUE){
			gluePos = 6;
		}
		else gluePos = 0;

		if (currentItemOfSlot1 == CurrentItemOfSlot1.ICEFLAKE){
			iceflakePos = 1;
		}
		else if (currentItemOfSlot2 == CurrentItemOfSlot2.ICEFLAKE){
			iceflakePos = 2;
		}
		else if (currentItemOfSlot3 == CurrentItemOfSlot3.ICEFLAKE){
			iceflakePos = 3;
		}
		else if (currentItemOfSlot4 == CurrentItemOfSlot4.ICEFLAKE){
			iceflakePos = 4;
		}
		else if (currentItemOfSlot5 == CurrentItemOfSlot5.ICEFLAKE){
			iceflakePos = 5;
		}
		else if (currentItemOfSlot6 == CurrentItemOfSlot6.ICEFLAKE){
			iceflakePos = 6;
		}
		else iceflakePos = 0;

		if (currentItemOfSlot1 == CurrentItemOfSlot1.SWITCH_MACHINE){
			switchMachinePos = 1;
		}
		else if (currentItemOfSlot2 == CurrentItemOfSlot2.SWITCH_MACHINE){
			switchMachinePos = 2;
		}
		else if (currentItemOfSlot3 == CurrentItemOfSlot3.SWITCH_MACHINE){
			switchMachinePos = 3;
		}
		else if (currentItemOfSlot4 == CurrentItemOfSlot4.SWITCH_MACHINE){
			switchMachinePos = 4;
		}
		else if (currentItemOfSlot5 == CurrentItemOfSlot5.SWITCH_MACHINE){
			switchMachinePos = 5;
		}
		else if (currentItemOfSlot6 == CurrentItemOfSlot6.SWITCH_MACHINE){
			switchMachinePos = 6;
		}
		else switchMachinePos = 0;

		if (currentItemOfSlot1 == CurrentItemOfSlot1.AUGMENTED_BURST){
			augmentedBurstPos = 1;
		}
		else if (currentItemOfSlot2 == CurrentItemOfSlot2.AUGMENTED_BURST){
			augmentedBurstPos = 2;
		}
		else if (currentItemOfSlot3 == CurrentItemOfSlot3.AUGMENTED_BURST){
			augmentedBurstPos = 3;
		}
		else if (currentItemOfSlot4 == CurrentItemOfSlot4.AUGMENTED_BURST){
			augmentedBurstPos = 4;
		}
		else if (currentItemOfSlot5 == CurrentItemOfSlot5.AUGMENTED_BURST){
			augmentedBurstPos = 5;
		}
		else if (currentItemOfSlot6 == CurrentItemOfSlot6.AUGMENTED_BURST){
			augmentedBurstPos = 6;
		}
		else augmentedBurstPos = 0;

		if (currentItemOfSlot1 == CurrentItemOfSlot1.STICKER){
			stickerPos = 1;
		}
		else if (currentItemOfSlot2 == CurrentItemOfSlot2.STICKER){
			stickerPos = 2;
		}
		else if (currentItemOfSlot3 == CurrentItemOfSlot3.STICKER){
			stickerPos = 3;
		}
		else if (currentItemOfSlot4 == CurrentItemOfSlot4.STICKER){
			stickerPos = 4;
		}
		else if (currentItemOfSlot5 == CurrentItemOfSlot5.STICKER){
			stickerPos = 5;
		}
		else if (currentItemOfSlot6 == CurrentItemOfSlot6.STICKER){
			stickerPos = 6;
		}
		else stickerPos = 0;
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
