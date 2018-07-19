package com.junkworld.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class TrashCanColorScreen implements Screen {
	private JunkWorld game;
	private OrthographicCamera camera;
	private Batch batch;
	private Vector3 touchPos;
	private TrashCan1Properties trashCan1Properties;
	private TrashCan2Properties trashCan2Properties;
	private TrashCan3Properties trashCan3Properties;
	private TrashCan4Properties trashCan4Properties;
	private AssetManager manager;
	private Actor trashCan1Up, trashCan1Down, trashCan2Up, trashCan2Down, trashCan3Up, trashCan3Down,
	trashCan4Up, trashCan4Down;
	private Rectangle trashCan1UpLayer, trashCan1DownLayer, trashCan2UpLayer, trashCan2DownLayer, trashCan3UpLayer,
	trashCan3DownLayer, trashCan4UpLayer, trashCan4DownLayer, trashCan1Layer, trashCan2Layer, trashCan3Layer,
	trashCan4Layer;
	private boolean isTrashCan1, isTrashCan2, isTrashCan3, isTrashCan4;
	private Array<Texture> trashCan1Color, trashCan2Color, trashCan3Color, trashCan4Color, arrayOfUpColors,
	arrayOfDownColors;
	private Array<Texture> trashCan1ColorUp, trashCan1ColorDown, trashCan2ColorUp, trashCan2ColorDown, 
	trashCan3ColorUp, trashCan3ColorDown, trashCan4ColorUp, trashCan4ColorDown;
	private int trashCan1CurrentColor, trashCan2CurrentColor, trashCan3CurrentColor,trashCan4CurrentColor;
	private int lastTrashCan1Color, lastTrashCan2Color, lastTrashCan3Color, lastTrashCan4Color;
	private int nextTrashCan1Color, nextTrashCan2Color, nextTrashCan3Color, nextTrashCan4Color;
	private boolean addColors = true, playMusic = true;
	private Stage stage;
	private Rectangle nextLayer, backLayer;
	private Actor next, back, randomize;
	private GamePalette gamePalette;
	private Array<Texture> firstPalette, secondPalette, thirdPalette, fourthPalette;
	private Array<Texture> firstPaletteArrow, secondPaletteArrow, thirdPaletteArrow, fourthPaletteArrow;
	private Actor firstArrow, secondArrow, thirdArrow, fourthArrow, gameRequirementsArrow;
	private TextureRegion lockedPalette;
	private Rectangle firstArrowLayer, secondArrowLayer, thirdArrowLayer, fourthArrowLayer, firstPaletteLayer,
	secondPaletteLayer, thirdPaletteLayer, fourthPaletteLayer, randomizeLayer, gameRequirementsArrowLayer;
	private int firstCurrentPalArrow = 0, secondCurrentPalArrow = 0, thirdCurrentPalArrow = 0, fourthCurrentPalArrow = 0,
			nextColorPal1, nextColorPal2, nextColorPal3, nextColorPal4;
	private boolean redPres = true, orangePres = true, yellowPres = true, greenPres = true, bluePres = true,
			purplePres = true;
	private JunkWorldEngines junkWorldEngines;
	private Array<Integer> colors;
	private TextureRegion randomizeRegion;
	private boolean setActors = true, gameReqOpened = false;
	private boolean removeListeners = false;
	private long delayRandomize = 0, delayGameReqMovement = 0, delayGameArrow = TimeUtils.millis(), delayTutoralArrow = TimeUtils.millis();
	private TextureRegion currentRequirementsArrayL, currentRequirementsArrayR, greenBGRegion;
	private boolean isLoading = false;
	private long delayAds = TimeUtils.millis();
	
	private void disposeAssets(){
		batch.dispose();
		stage.dispose();
		this.dispose();
	}

	private void loadManager(){
		//		TODO
		manager.load("gameScreenAssets/trashCanColorAssets/upArrowRed.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/upArrowOrange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/upArrowYellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/upArrowGreen.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/upArrowBlue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/upArrowPurple.png", Texture.class);

		manager.load("gameScreenAssets/trashCanColorAssets/downArrowRed.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/downArrowOrange.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/downArrowYellow.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/downArrowGreen.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/downArrowBlue.png", Texture.class);
		manager.load("gameScreenAssets/trashCanColorAssets/downArrowPurple.png", Texture.class);

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

		manager.load("buttons/randomizeButton.png", Texture.class);
		manager.load("buttons/paletteLocked.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL2.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR2.png", Texture.class);
		manager.load("backgrounds/greenBackground.png", Texture.class);

		manager.load("buttons/redColor.png", Texture.class);
		manager.load("buttons/orangeColor.png", Texture.class);
		manager.load("buttons/yellowColor.png", Texture.class);
		manager.load("buttons/greenColor.png", Texture.class);
		manager.load("buttons/blueColor.png", Texture.class);
		manager.load("buttons/purpleColor.png", Texture.class);

		manager.load("backgrounds/firstBackground.png", Texture.class);
		manager.load("mainMenuAssets/loadingAssets/blackShader.png", Texture.class);
		manager.load("backgrounds/blueFrame.png", Texture.class);
		manager.load("screenLabels/selectColor.png", Texture.class);
		manager.load("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class);
		manager.load("buttons/backButton.png", Texture.class);
		manager.load("buttons/nextButton.png", Texture.class);
		manager.load("screenLabels/gamePalette.png", Texture.class);	

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

		manager.load("screenLabels/noColorRed.png", Texture.class);
		manager.load("screenLabels/noColorOrange.png", Texture.class);
		manager.load("screenLabels/noColorYellow.png", Texture.class);
		manager.load("screenLabels/noColorGreen.png", Texture.class);
		manager.load("screenLabels/noColorBlue.png", Texture.class);
		manager.load("screenLabels/noColorPurple.png", Texture.class);

		manager.finishLoading();
	}

	private void unloadManager(){
		//			TODO
		manager.unload("gameScreenAssets/trashCanColorAssets/upArrowRed.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/upArrowOrange.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/upArrowYellow.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/upArrowGreen.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/upArrowBlue.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/upArrowPurple.png");

		manager.unload("gameScreenAssets/trashCanColorAssets/downArrowRed.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/downArrowOrange.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/downArrowYellow.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/downArrowGreen.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/downArrowBlue.png");
		manager.unload("gameScreenAssets/trashCanColorAssets/downArrowPurple.png");

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

		manager.unload("buttons/randomizeButton.png");
		manager.unload("buttons/paletteLocked.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL2.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR2.png");
		manager.unload("backgrounds/greenBackground.png");

		manager.unload("buttons/redColor.png");
		manager.unload("buttons/orangeColor.png");
		manager.unload("buttons/yellowColor.png");
		manager.unload("buttons/greenColor.png");
		manager.unload("buttons/blueColor.png");
		manager.unload("buttons/purpleColor.png");

		manager.unload("backgrounds/firstBackground.png");
		manager.unload("backgrounds/blueFrame.png");
		manager.unload("screenLabels/selectColor.png");
		manager.unload("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png");
		manager.unload("buttons/backButton.png");
		manager.unload("buttons/nextButton.png");
		manager.unload("screenLabels/gamePalette.png");			

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

		manager.unload("screenLabels/noColorRed.png");
		manager.unload("screenLabels/noColorOrange.png");
		manager.unload("screenLabels/noColorYellow.png");
		manager.unload("screenLabels/noColorGreen.png");
		manager.unload("screenLabels/noColorBlue.png");
		manager.unload("screenLabels/noColorPurple.png");
	}

	public TrashCanColorScreen ( final JunkWorld game,  TrashCan1Properties trashCan1Properties,
			TrashCan2Properties trashCan2Properties,  TrashCan3Properties trashCan3Properties, 
			TrashCan4Properties trashCan4Properties,  final AssetManager manager,  final JunkWorldEngines junkWorldEngines){
		this.game = game;
		this.trashCan1Properties = trashCan1Properties;
		this.trashCan2Properties = trashCan2Properties;
		this.trashCan3Properties = trashCan3Properties;
		this.trashCan4Properties = trashCan4Properties;
		this.manager = manager;
		this.junkWorldEngines = junkWorldEngines;
		loadManager();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		batch = new SpriteBatch();
		touchPos = new Vector3();
		colors = new Array<Integer>();

		gamePalette = new GamePalette();
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

		stage = new Stage();

		backLayer = new Rectangle(64, 64, 100, 50);
		nextLayer = new Rectangle(350, 64, 100, 50);
		randomizeLayer = new Rectangle(256 - 60, 66, 120, 46);
		gameRequirementsArrowLayer = new Rectangle(455, 400, 50, 100);

		next = new Actor();
		back = new Actor();
		trashCan1Up = new Actor();
		trashCan1Down = new Actor();
		trashCan2Up = new Actor();
		trashCan2Down = new Actor();
		trashCan3Up = new Actor();
		trashCan3Down = new Actor();
		trashCan4Up = new Actor();
		trashCan4Down = new Actor();
		randomize = new Actor();
		firstArrow = new Actor();
		secondArrow = new Actor();
		thirdArrow = new Actor();
		fourthArrow = new Actor();
		gameRequirementsArrow = new Actor();

		stage.addActor(next);
		stage.addActor(back);
		stage.addActor(trashCan1Up);
		stage.addActor(trashCan1Down);
		stage.addActor(trashCan2Up);
		stage.addActor(trashCan2Down);
		stage.addActor(trashCan3Up);
		stage.addActor(trashCan3Down);
		stage.addActor(trashCan4Up);
		stage.addActor(trashCan4Down);
		stage.addActor(randomize);
		stage.addActor(firstArrow);
		stage.addActor(secondArrow);
		stage.addActor(thirdArrow);
		stage.addActor(fourthArrow);
		stage.addActor(gameRequirementsArrow);

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
			junkWorldEngines.getTutorialStage().addActor(trashCan1Up);
			junkWorldEngines.getTutorialStage().addActor(trashCan2Up);
			junkWorldEngines.getTutorialStage().addActor(trashCan3Up);
			junkWorldEngines.getTutorialStage().addActor(trashCan4Up);
			junkWorldEngines.getTutorialStage().addActor(firstArrow);
			junkWorldEngines.getTutorialStage().addActor(secondArrow);
			junkWorldEngines.getTutorialStage().addActor(thirdArrow);
			junkWorldEngines.getTutorialStage().addActor(fourthArrow);
			junkWorldEngines.getTutorialStage().addActor(randomize);
			junkWorldEngines.getTutorialStage().addActor(next);
		}
		loadMusic();
	}

	private void loadAssets() {

	}

	private void createTrashCanLayers() {

		if (addColors){
			addColors = false;

			arrayOfUpColors = new Array<Texture>();
			arrayOfUpColors.add(manager.get("gameScreenAssets/trashCanColorAssets/upArrowRed.png", Texture.class));
			arrayOfUpColors.add(manager.get("gameScreenAssets/trashCanColorAssets/upArrowOrange.png", Texture.class));
			arrayOfUpColors.add(manager.get("gameScreenAssets/trashCanColorAssets/upArrowYellow.png", Texture.class));
			arrayOfUpColors.add(manager.get("gameScreenAssets/trashCanColorAssets/upArrowGreen.png", Texture.class));
			arrayOfUpColors.add(manager.get("gameScreenAssets/trashCanColorAssets/upArrowBlue.png", Texture.class));
			arrayOfUpColors.add(manager.get("gameScreenAssets/trashCanColorAssets/upArrowPurple.png", Texture.class));

			arrayOfDownColors = new Array<Texture>();
			arrayOfDownColors.add(manager.get("gameScreenAssets/trashCanColorAssets/downArrowRed.png", Texture.class));
			arrayOfDownColors.add(manager.get("gameScreenAssets/trashCanColorAssets/downArrowOrange.png", Texture.class));
			arrayOfDownColors.add(manager.get("gameScreenAssets/trashCanColorAssets/downArrowYellow.png", Texture.class));
			arrayOfDownColors.add(manager.get("gameScreenAssets/trashCanColorAssets/downArrowGreen.png", Texture.class));
			arrayOfDownColors.add(manager.get("gameScreenAssets/trashCanColorAssets/downArrowBlue.png", Texture.class));
			arrayOfDownColors.add(manager.get("gameScreenAssets/trashCanColorAssets/downArrowPurple.png", Texture.class));

			if (isTrashCan1){
				trashCan1Color = new Array<Texture>();
				trashCan1ColorUp = new Array<Texture>();
				trashCan1ColorDown = new Array<Texture>();
				trashCan1Layer = new Rectangle(68, 450 - 16, 96, 96);
				trashCan1UpLayer = new Rectangle(52 + 32, 550, 64, 64);
				trashCan1DownLayer = new Rectangle(52 + 32, 350, 64, 64);

				switch (trashCan1Properties.getTrashCan()) {
				case 1: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 2: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 3: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 4: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 5: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 6: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png", Texture.class));
						trashCan1Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				default: break;
				}
				trashCan1ColorUp.addAll(arrayOfUpColors);
				trashCan1ColorDown.addAll(arrayOfDownColors);
			}

			if (isTrashCan2){
				trashCan2Color = new Array<Texture>();
				trashCan2ColorUp = new Array<Texture>();
				trashCan2ColorDown = new Array<Texture>();
				trashCan2Layer = new Rectangle(164, 450 - 16, 96, 96);
				trashCan2UpLayer = new Rectangle(52 + 128, 550, 64, 64);
				trashCan2DownLayer = new Rectangle(52 + 128, 350, 64, 64);

				switch (trashCan2Properties.getTrashCan()) {
				case 1: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 2: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 3: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 4: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 5: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 6: {
					switch (trashCan2Properties.getTrashCanType()) {
					case 1: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png", Texture.class));
						trashCan2Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				default: break;
				}
				trashCan2ColorUp.addAll(arrayOfUpColors);
				trashCan2ColorDown.addAll(arrayOfDownColors);
			}

			if (isTrashCan3){
				trashCan3Color = new Array<Texture>();
				trashCan3ColorUp = new Array<Texture>();
				trashCan3ColorDown = new Array<Texture>();
				trashCan3Layer = new Rectangle(260, 450 - 16, 96, 96);
				trashCan3UpLayer = new Rectangle(276, 550, 64, 64);
				trashCan3DownLayer = new Rectangle(276, 350, 64, 64);

				switch (trashCan3Properties.getTrashCan()) {
				case 1: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 2: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 3: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 4: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 5: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 6: {
					switch (trashCan3Properties.getTrashCanType()) {
					case 1: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png", Texture.class));
						trashCan3Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				default: break;
				}
				trashCan3ColorUp.addAll(arrayOfUpColors);
				trashCan3ColorDown.addAll(arrayOfDownColors);
			}

			if (isTrashCan4){
				trashCan4Color = new Array<Texture>();
				trashCan4ColorUp = new Array<Texture>();
				trashCan4ColorDown = new Array<Texture>();
				trashCan4Layer = new Rectangle(356, 450 - 16, 96, 96);
				trashCan4UpLayer = new Rectangle(372, 550, 64, 64);
				trashCan4DownLayer = new Rectangle(372, 350, 64, 64);

				switch (trashCan4Properties.getTrashCan()) {
				case 1: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Average Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 2: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Scorching Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 3: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Vacuum Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 4: {
					switch (trashCan4Properties.getTrashCanType()) {
					case 1: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Trash Blower/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 5: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Dull Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				case 6: {
					switch (trashCan1Properties.getTrashCanType()) {
					case 1: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Biodegradable/purple.png", Texture.class));
					}; break;
					case 2: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Recyclable/purple.png", Texture.class));
					}; break;
					case 3: {
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/red.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/orange.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/yellow.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/green.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/blue.png", Texture.class));
						trashCan4Color.add(manager.get("gameScreenAssets/trashCanSelectionAssets/The Swift Can/Non-Recyclable/purple.png", Texture.class));
					}; break;
					default: break;
					}
				}; break;
				default: break;
				}
				trashCan4ColorUp.addAll(arrayOfUpColors);
				trashCan4ColorDown.addAll(arrayOfDownColors);
			}
			randomizeRegion = new TextureRegion(manager.get("buttons/randomizeButton.png", Texture.class));
			lockedPalette = new TextureRegion(manager.get("buttons/paletteLocked.png", Texture.class));
			currentRequirementsArrayL = new TextureRegion(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsL.png", Texture.class));
			currentRequirementsArrayR = new TextureRegion(manager.get("gameScreenAssets/trashCanSelectionAssets/gameRequirementsR.png", Texture.class));
			greenBGRegion = new TextureRegion(manager.get("backgrounds/greenBackground.png", Texture.class));
			createGamePalettes();
		}
	}

	private void createGamePalettes() {
		firstPalette = new Array<Texture>();
		secondPalette = new Array<Texture>();
		thirdPalette = new Array<Texture>();
		fourthPalette = new Array<Texture>();
		firstPaletteArrow = new Array<Texture>();
		secondPaletteArrow = new Array<Texture>();
		thirdPaletteArrow = new Array<Texture>();
		fourthPaletteArrow = new Array<Texture>();

		firstPalette.add(manager.get("buttons/redColor.png", Texture.class));
		firstPalette.add(manager.get("buttons/orangeColor.png", Texture.class));
		firstPalette.add(manager.get("buttons/yellowColor.png", Texture.class));
		firstPalette.add(manager.get("buttons/greenColor.png", Texture.class));
		firstPalette.add(manager.get("buttons/blueColor.png", Texture.class));
		firstPalette.add(manager.get("buttons/purpleColor.png", Texture.class));

		secondPalette.addAll(firstPalette);
		thirdPalette.addAll(firstPalette);
		fourthPalette.addAll(firstPalette);

		firstPaletteArrow.addAll(arrayOfDownColors);
		secondPaletteArrow.addAll(arrayOfDownColors);
		thirdPaletteArrow.addAll(arrayOfDownColors);
		fourthPaletteArrow.addAll(arrayOfDownColors);

		firstPaletteLayer = new Rectangle(64, 200, 96, 96);
		secondPaletteLayer = new Rectangle(64 + (96), 200, 96, 96);
		thirdPaletteLayer = new Rectangle(64 + (96 * 2), 200, 96, 96);
		fourthPaletteLayer = new Rectangle(64 + (96 * 3), 200, 96, 96);

		firstArrowLayer = new Rectangle(64 + 16, 136, 64, 64);
		secondArrowLayer = new Rectangle(64 + (96) + 16, 136, 64, 64);
		thirdArrowLayer = new Rectangle(64 + (96 * 2) + 16, 136, 64, 64);
		fourthArrowLayer = new Rectangle(64 + (96 * 3) + 16, 136, 64, 64);

		trashCan1CurrentColor = 0;
		trashCan2CurrentColor = 0;
		trashCan3CurrentColor = 0;
		trashCan4CurrentColor = 0;
	}

	private void setIndexes() {
		if (isTrashCan1){
			if (trashCan1CurrentColor - 1 < 0){
				lastTrashCan1Color = trashCan1ColorUp.size - 1;
			} else lastTrashCan1Color = trashCan1CurrentColor - 1;
			if (trashCan1CurrentColor + 1 == trashCan1ColorDown.size){
				nextTrashCan1Color = 0;
			} else nextTrashCan1Color = trashCan1CurrentColor + 1;
		}
		if (isTrashCan2){
			if (trashCan2CurrentColor - 1 < 0){
				lastTrashCan2Color = trashCan2ColorUp.size - 1;
			} else lastTrashCan2Color = trashCan2CurrentColor - 1;
			if (trashCan2CurrentColor + 1 == trashCan2ColorDown.size){
				nextTrashCan2Color = 0;
			} else nextTrashCan2Color = trashCan2CurrentColor + 1;
		}
		if (isTrashCan3){
			if (trashCan3CurrentColor - 1 < 0){
				lastTrashCan3Color = trashCan3ColorUp.size - 1;
			} else lastTrashCan3Color = trashCan3CurrentColor - 1;
			if (trashCan3CurrentColor + 1 == trashCan3ColorDown.size){
				nextTrashCan3Color = 0;
			} else nextTrashCan3Color = trashCan3CurrentColor + 1;
		}
		if (isTrashCan4){
			if (trashCan4CurrentColor - 1 < 0){
				lastTrashCan4Color = trashCan4ColorUp.size - 1;
			} else lastTrashCan4Color = trashCan4CurrentColor - 1;
			if (trashCan4CurrentColor + 1 == trashCan4ColorDown.size){
				nextTrashCan4Color = 0;
			} else nextTrashCan4Color = trashCan4CurrentColor + 1;
		}

		if (firstCurrentPalArrow + 1 == firstPaletteArrow.size){
			nextColorPal1 = 0;
		} else nextColorPal1 = firstCurrentPalArrow + 1;
		if (secondCurrentPalArrow + 1 == secondPaletteArrow.size){
			nextColorPal2 = 0;
		} else nextColorPal2 = secondCurrentPalArrow + 1;
		if (thirdCurrentPalArrow + 1 == thirdPaletteArrow.size){
			nextColorPal3 = 0;
		} else nextColorPal3 = thirdCurrentPalArrow + 1;
		if (fourthCurrentPalArrow + 1 == fourthPaletteArrow.size){
			nextColorPal4 = 0;
		} else nextColorPal4 = fourthCurrentPalArrow + 1;

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
	}
	//	TODO
	private void setActors() {
		if (setActors){
			setActors = false;

			back.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					isLoading = true;
					unloadManager();
					disposeAssets();
					game.setScreen(new TrashCanSelectionScreen(game, manager, junkWorldEngines));
					return true;
				}
			});
			next.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (junkWorldEngines.isRed()){
						if ((trashCan1Properties.getTrashCanColor() == 1 ||
								trashCan2Properties.getTrashCanColor() == 1 ||
								trashCan3Properties.getTrashCanColor() == 1 ||
								trashCan4Properties.getTrashCanColor() == 1 ||
								gamePalette.getFirstPalette() == 1 ||
								gamePalette.getSecondPalette() == 1 ||
								gamePalette.getThirdPalette() == 1) && junkWorldEngines.isRed()){
							redPres = true;
						} else redPres = false;
					}
					else redPres = true;
					if (junkWorldEngines.isOrange()){
						if ((trashCan1Properties.getTrashCanColor() == 2 ||
								trashCan2Properties.getTrashCanColor() == 2 ||
								trashCan3Properties.getTrashCanColor() == 2 ||
								trashCan4Properties.getTrashCanColor() == 2 ||
								gamePalette.getFirstPalette() == 2 ||
								gamePalette.getSecondPalette() == 2 ||
								gamePalette.getThirdPalette() == 2) && junkWorldEngines.isOrange()){
							orangePres = true;
						} else orangePres = false;
					}
					else orangePres = true;
					if (junkWorldEngines.isYellow()){
						if ((trashCan1Properties.getTrashCanColor() == 3 ||
								trashCan2Properties.getTrashCanColor() == 3 ||
								trashCan3Properties.getTrashCanColor() == 3 ||
								trashCan4Properties.getTrashCanColor() == 3 ||
								gamePalette.getFirstPalette() == 3 ||
								gamePalette.getSecondPalette() == 3 ||
								gamePalette.getThirdPalette() == 3) && junkWorldEngines.isYellow()){
							yellowPres = true;
						} else yellowPres = false;
					}
					else yellowPres = true;
					if (junkWorldEngines.isGreen()){
						if ((trashCan1Properties.getTrashCanColor() == 4 ||
								trashCan2Properties.getTrashCanColor() == 4 ||
								trashCan3Properties.getTrashCanColor() == 4 ||
								trashCan4Properties.getTrashCanColor() == 4 ||
								gamePalette.getFirstPalette() == 4 ||
								gamePalette.getSecondPalette() == 4 ||
								gamePalette.getThirdPalette() == 4) && junkWorldEngines.isGreen()){
							greenPres = true;
						} else greenPres = false;
					}
					else greenPres = true;
					if (junkWorldEngines.isBlue()){
						if ((trashCan1Properties.getTrashCanColor() == 5 ||
								trashCan2Properties.getTrashCanColor() == 5 ||
								trashCan3Properties.getTrashCanColor() == 5 ||
								trashCan4Properties.getTrashCanColor() == 5 ||
								gamePalette.getFirstPalette() == 5 ||
								gamePalette.getSecondPalette() == 5 ||
								gamePalette.getThirdPalette() == 5) && junkWorldEngines.isBlue()){
							bluePres = true;
						} else bluePres = false;
					}
					else bluePres = true;
					if (junkWorldEngines.isPurple()){
						if ((trashCan1Properties.getTrashCanColor() == 6 ||
								trashCan2Properties.getTrashCanColor() == 6 ||
								trashCan3Properties.getTrashCanColor() == 6 ||
								trashCan4Properties.getTrashCanColor() == 6 ||
								gamePalette.getFirstPalette() == 6 ||
								gamePalette.getSecondPalette() == 6 ||
								gamePalette.getThirdPalette() == 6) && junkWorldEngines.isPurple()){
							purplePres = true;
						} else purplePres = false;
					}
					else purplePres = true;

					if (redPres &&
							orangePres &&
							yellowPres &&
							greenPres &&
							bluePres &&
							purplePres){
						if (junkWorldEngines.getGameMode() == 0){
							junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
						}
						isLoading = true;
						unloadManager();
						disposeAssets();
						game.setScreen(new TrashCanSkillUpgradeScreen(game, trashCan1Properties, trashCan2Properties,
								trashCan3Properties,trashCan4Properties, manager, junkWorldEngines, gamePalette));
					}
					return true;
				}
			});

			trashCan1Up.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (junkWorldEngines.getGameMode() == 0){
						junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
					}
					if (trashCan1CurrentColor - 1 < 0){
						trashCan1CurrentColor = trashCan1Color.size - 1;
					} else trashCan1CurrentColor -= 1;
					return true;
				}
			});
			trashCan1Down.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (trashCan1CurrentColor + 1 == trashCan1Color.size){
						trashCan1CurrentColor = 0;
					} else trashCan1CurrentColor += 1;
					return true;
				}
			});

			trashCan2Up.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (junkWorldEngines.getGameMode() == 0){
						junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
					}
					if (trashCan2CurrentColor - 1 < 0){
						trashCan2CurrentColor = trashCan2Color.size - 1;
					} else trashCan2CurrentColor -= 1;
					return true;
				}
			});
			trashCan2Down.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (trashCan2CurrentColor + 1 == trashCan2Color.size){
						trashCan2CurrentColor = 0;
					} else trashCan2CurrentColor += 1;
					return true;
				}
			});

			trashCan3Up.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (junkWorldEngines.getGameMode() == 0){
						junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
					}
					if (trashCan3CurrentColor - 1 < 0){
						trashCan3CurrentColor = trashCan3Color.size - 1;
					} else trashCan3CurrentColor -= 1;
					return true;
				}
			});
			trashCan3Down.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (trashCan3CurrentColor + 1 == trashCan3Color.size){
						trashCan3CurrentColor = 0;
					} else trashCan3CurrentColor += 1;
					return true;
				}
			});

			trashCan4Up.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (junkWorldEngines.getGameMode() == 0){
						junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
					}
					if (trashCan4CurrentColor - 1 < 0){
						trashCan4CurrentColor = trashCan4Color.size - 1;
					} else trashCan4CurrentColor -= 1;
					return true;
				}
			});
			trashCan4Down.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (trashCan4CurrentColor + 1 == trashCan4Color.size){
						trashCan4CurrentColor = 0;
					} else trashCan4CurrentColor += 1;
					return true;
				}
			});

			if (junkWorldEngines.isPalette1Unlocked()){
				firstArrow.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						if (junkWorldEngines.getGameMode() == 0){
							junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
						}
						if (firstCurrentPalArrow + 1 == firstPaletteArrow.size){
							firstCurrentPalArrow = 0;
						} else firstCurrentPalArrow += 1;
						return true;
					}
				});
			}
			if (junkWorldEngines.isPalette2Unlocked()){
				secondArrow.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						if (junkWorldEngines.getGameMode() == 0){
							junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
						}
						if (secondCurrentPalArrow + 1 == secondPaletteArrow.size){
							secondCurrentPalArrow = 0;
						} else secondCurrentPalArrow += 1;
						return true;
					}
				});
			}
			if (junkWorldEngines.isPalette3Unlocked()){
				thirdArrow.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						if (junkWorldEngines.getGameMode() == 0){
							junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
						}
						if (thirdCurrentPalArrow + 1 == thirdPaletteArrow.size){
							thirdCurrentPalArrow = 0;
						} else thirdCurrentPalArrow += 1;
						return true;
					}
				});
			}
			if (junkWorldEngines.isPalette4Unlocked()){
				fourthArrow.addListener(new InputListener(){
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						if (junkWorldEngines.getGameMode() == 0){
							junkWorldEngines.setCurrentTutorial(junkWorldEngines.getCurrentTutorial() + 1);
						}
						if (fourthCurrentPalArrow + 1 == fourthPaletteArrow.size){
							fourthCurrentPalArrow = 0;
						} else fourthCurrentPalArrow += 1;
						return true;
					}
				});
			}

			randomize.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					delayRandomize = TimeUtils.nanoTime();
					return true;
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

		//		TODO

		if (removeListeners){
			removeListeners = false;
			next.clearListeners();
			back.clearListeners();
			trashCan1Up.clearListeners();
			trashCan1Down.clearListeners();
			trashCan2Up.clearListeners();
			trashCan2Down.clearListeners();
			trashCan3Up.clearListeners();
			trashCan3Down.clearListeners();
			trashCan4Up.clearListeners();
			trashCan4Down.clearListeners();
			randomize.clearListeners();
			if (junkWorldEngines.isPalette1Unlocked()){
				firstArrow.clearListeners();
			}
			if (junkWorldEngines.isPalette2Unlocked()){
				secondArrow.clearListeners();
			}
			if (junkWorldEngines.isPalette3Unlocked()){
				thirdArrow.clearListeners();
			}
			if (junkWorldEngines.isPalette4Unlocked()){
				fourthArrow.clearListeners();
			}
		}
	}

	private void setActorBounds() {
		if (isTrashCan1){
			trashCan1Up.setBounds(trashCan1UpLayer.x, trashCan1UpLayer.y, trashCan1UpLayer.width, trashCan1UpLayer.height);
			trashCan1Down.setBounds(trashCan1DownLayer.x, trashCan1DownLayer.y, trashCan1DownLayer.width, trashCan1DownLayer.height);
		}
		if (isTrashCan2){
			trashCan2Up.setBounds(trashCan2UpLayer.x, trashCan2UpLayer.y, trashCan2UpLayer.width, trashCan2UpLayer.height);
			trashCan2Down.setBounds(trashCan2DownLayer.x, trashCan2DownLayer.y, trashCan2DownLayer.width, trashCan2DownLayer.height);
		}
		if (isTrashCan3){
			trashCan3Up.setBounds(trashCan3UpLayer.x, trashCan3UpLayer.y, trashCan3UpLayer.width, trashCan3UpLayer.height);
			trashCan3Down.setBounds(trashCan3DownLayer.x, trashCan3DownLayer.y, trashCan3DownLayer.width, trashCan3DownLayer.height);
		}
		if (isTrashCan4){
			trashCan4Up.setBounds(trashCan4UpLayer.x, trashCan4UpLayer.y, trashCan4UpLayer.width, trashCan4UpLayer.height);
			trashCan4Down.setBounds(trashCan4DownLayer.x, trashCan4DownLayer.y, trashCan4DownLayer.width, trashCan4DownLayer.height);
		}
		back.setBounds(backLayer.x, backLayer.y, backLayer.width, backLayer.height);
		next.setBounds(nextLayer.x, nextLayer.y, nextLayer.width, nextLayer.height);
		firstArrow.setBounds(firstArrowLayer.x, firstArrowLayer.y, firstArrowLayer.width, firstArrowLayer.height);
		secondArrow.setBounds(secondArrowLayer.x, secondArrowLayer.y, secondArrowLayer.width, secondArrowLayer.height);
		thirdArrow.setBounds(thirdArrowLayer.x, thirdArrowLayer.y, thirdArrowLayer.width, thirdArrowLayer.height);
		fourthArrow.setBounds(fourthArrowLayer.x, fourthArrowLayer.y, fourthArrowLayer.width, fourthArrowLayer.height);
		randomize.setBounds(randomizeLayer.x, randomizeLayer.y, randomizeLayer.width, randomizeLayer.height);
		gameRequirementsArrow.setBounds(gameRequirementsArrowLayer.x, gameRequirementsArrowLayer.y, gameRequirementsArrowLayer.width,
				gameRequirementsArrowLayer.height);
	}

	private void setTrashCanColors() {
		if (isTrashCan1){
			switch (trashCan1CurrentColor) {
			case 0 : {
				junkWorldEngines.setPreviousTrashCan1Color(1);
				trashCan1Properties.setTrashCanColor(1);
			}; break;
			case 1 : {
				junkWorldEngines.setPreviousTrashCan1Color(2);
				trashCan1Properties.setTrashCanColor(2);
			}; break;
			case 2 : {
				junkWorldEngines.setPreviousTrashCan1Color(3);
				trashCan1Properties.setTrashCanColor(3);
			}; break;
			case 3 : {
				junkWorldEngines.setPreviousTrashCan1Color(4);
				trashCan1Properties.setTrashCanColor(4);
			}; break;
			case 4 : {
				junkWorldEngines.setPreviousTrashCan1Color(5);
				trashCan1Properties.setTrashCanColor(5);
			}; break;
			case 5 : {
				junkWorldEngines.setPreviousTrashCan1Color(6);
				trashCan1Properties.setTrashCanColor(6);
			}; break;
			default: break;
			}
		}
		if (isTrashCan2){
			switch (trashCan2CurrentColor) {
			case 0 : {
				junkWorldEngines.setPreviousTrashCan2Color(1);
				trashCan2Properties.setTrashCanColor(1);
			}; break;
			case 1 : {
				junkWorldEngines.setPreviousTrashCan2Color(2);
				trashCan2Properties.setTrashCanColor(2);
			}; break;
			case 2 : {
				junkWorldEngines.setPreviousTrashCan2Color(3);
				trashCan2Properties.setTrashCanColor(3);
			}; break;
			case 3 : {
				junkWorldEngines.setPreviousTrashCan2Color(4);
				trashCan2Properties.setTrashCanColor(4);
			}; break;
			case 4 : {
				junkWorldEngines.setPreviousTrashCan2Color(5);
				trashCan2Properties.setTrashCanColor(5);
			}; break;
			case 5 : {
				junkWorldEngines.setPreviousTrashCan2Color(6);
				trashCan2Properties.setTrashCanColor(6);
			}; break;
			default: break;
			}
		}
		if (isTrashCan3){
			switch (trashCan3CurrentColor) {
			case 0 : {
				junkWorldEngines.setPreviousTrashCan3Color(1);
				trashCan3Properties.setTrashCanColor(1);
			}; break;
			case 1 : {
				junkWorldEngines.setPreviousTrashCan3Color(2);
				trashCan3Properties.setTrashCanColor(2);
			}; break;
			case 2 : {
				junkWorldEngines.setPreviousTrashCan3Color(3);
				trashCan3Properties.setTrashCanColor(3);
			}; break;
			case 3 : {
				junkWorldEngines.setPreviousTrashCan3Color(4);
				trashCan3Properties.setTrashCanColor(4);
			}; break;
			case 4 : {
				junkWorldEngines.setPreviousTrashCan3Color(5);
				trashCan3Properties.setTrashCanColor(5);
			}; break;
			case 5 : {
				junkWorldEngines.setPreviousTrashCan3Color(6);
				trashCan3Properties.setTrashCanColor(6);
			}; break;
			default: break;
			}
		}
		if (isTrashCan4){
			switch (trashCan4CurrentColor) {
			case 0 : {
				junkWorldEngines.setPreviousTrashCan4Color(1);
				trashCan4Properties.setTrashCanColor(1);
			}; break;
			case 1 : {
				junkWorldEngines.setPreviousTrashCan4Color(2);
				trashCan4Properties.setTrashCanColor(2);
			}; break;
			case 2 : {
				junkWorldEngines.setPreviousTrashCan4Color(3);
				trashCan4Properties.setTrashCanColor(3);
			}; break;
			case 3 : {
				junkWorldEngines.setPreviousTrashCan4Color(4);
				trashCan4Properties.setTrashCanColor(4);
			}; break;
			case 4 : {
				junkWorldEngines.setPreviousTrashCan4Color(5);
				trashCan4Properties.setTrashCanColor(5);
			}; break;
			case 5 : {
				junkWorldEngines.setPreviousTrashCan4Color(6);
				trashCan4Properties.setTrashCanColor(6);
			}; break;
			default: break;
			}
		}

		if (delayRandomize != 0){
			if (TimeUtils.nanoTime() - delayRandomize >= 10000){
				delayRandomize = 0;
				if (junkWorldEngines.isRed()){
					colors.add(0);
				}
				if (junkWorldEngines.isOrange()){
					colors.add(1);
				}
				if (junkWorldEngines.isYellow()){
					colors.add(2);
				}
				if (junkWorldEngines.isGreen()){
					colors.add(3);
				}
				if (junkWorldEngines.isBlue()){
					colors.add(4);
				}
				if (junkWorldEngines.isPurple()){
					colors.add(5);
				}

				if (isTrashCan1){
					trashCan1CurrentColor = colors.random();
					colors.removeValue(trashCan1CurrentColor, true);
					if (colors.size == 0){
						if (junkWorldEngines.isRed()){
							colors.add(0);
						}
						if (junkWorldEngines.isOrange()){
							colors.add(1);
						}
						if (junkWorldEngines.isYellow()){
							colors.add(2);
						}
						if (junkWorldEngines.isGreen()){
							colors.add(3);
						}
						if (junkWorldEngines.isBlue()){
							colors.add(4);
						}
						if (junkWorldEngines.isPurple()){
							colors.add(5);
						}
					}
				}
				if (isTrashCan2){
					trashCan2CurrentColor = colors.random();
					colors.removeValue(trashCan2CurrentColor, true);
					if (colors.size == 0){
						if (junkWorldEngines.isRed()){
							colors.add(0);
						}
						if (junkWorldEngines.isOrange()){
							colors.add(1);
						}
						if (junkWorldEngines.isYellow()){
							colors.add(2);
						}
						if (junkWorldEngines.isGreen()){
							colors.add(3);
						}
						if (junkWorldEngines.isBlue()){
							colors.add(4);
						}
						if (junkWorldEngines.isPurple()){
							colors.add(5);
						}
					}
				}
				if (isTrashCan3){
					trashCan3CurrentColor = colors.random();
					colors.removeValue(trashCan3CurrentColor, true);
					if (colors.size == 0){
						if (junkWorldEngines.isRed()){
							colors.add(0);
						}
						if (junkWorldEngines.isOrange()){
							colors.add(1);
						}
						if (junkWorldEngines.isYellow()){
							colors.add(2);
						}
						if (junkWorldEngines.isGreen()){
							colors.add(3);
						}
						if (junkWorldEngines.isBlue()){
							colors.add(4);
						}
						if (junkWorldEngines.isPurple()){
							colors.add(5);
						}
					}
				}
				if (isTrashCan4){
					trashCan4CurrentColor = colors.random();
					colors.removeValue(trashCan4CurrentColor, true);
					if (colors.size == 0){
						if (junkWorldEngines.isRed()){
							colors.add(0);
						}
						if (junkWorldEngines.isOrange()){
							colors.add(1);
						}
						if (junkWorldEngines.isYellow()){
							colors.add(2);
						}
						if (junkWorldEngines.isGreen()){
							colors.add(3);
						}
						if (junkWorldEngines.isBlue()){
							colors.add(4);
						}
						if (junkWorldEngines.isPurple()){
							colors.add(5);
						}
					}
				}
				if (junkWorldEngines.isPalette1Unlocked()){
					firstCurrentPalArrow = colors.random();
					colors.removeValue(firstCurrentPalArrow, true);
					if (colors.size == 0){
						if (junkWorldEngines.isRed()){
							colors.add(0);
						}
						if (junkWorldEngines.isOrange()){
							colors.add(1);
						}
						if (junkWorldEngines.isYellow()){
							colors.add(2);
						}
						if (junkWorldEngines.isGreen()){
							colors.add(3);
						}
						if (junkWorldEngines.isBlue()){
							colors.add(4);
						}
						if (junkWorldEngines.isPurple()){
							colors.add(5);
						}
					}
				}
				if (junkWorldEngines.isPalette2Unlocked()){
					secondCurrentPalArrow = colors.random();
					colors.removeValue(secondCurrentPalArrow, true);
					if (colors.size == 0){
						if (junkWorldEngines.isRed()){
							colors.add(0);
						}
						if (junkWorldEngines.isOrange()){
							colors.add(1);
						}
						if (junkWorldEngines.isYellow()){
							colors.add(2);
						}
						if (junkWorldEngines.isGreen()){
							colors.add(3);
						}
						if (junkWorldEngines.isBlue()){
							colors.add(4);
						}
						if (junkWorldEngines.isPurple()){
							colors.add(5);
						}
					}
				}
				if (junkWorldEngines.isPalette3Unlocked()){
					thirdCurrentPalArrow = colors.random();
					colors.removeValue(thirdCurrentPalArrow, true);
					if (colors.size == 0){
						if (junkWorldEngines.isRed()){
							colors.add(0);
						}
						if (junkWorldEngines.isOrange()){
							colors.add(1);
						}
						if (junkWorldEngines.isYellow()){
							colors.add(2);
						}
						if (junkWorldEngines.isGreen()){
							colors.add(3);
						}
						if (junkWorldEngines.isBlue()){
							colors.add(4);
						}
						if (junkWorldEngines.isPurple()){
							colors.add(5);
						}
					}
				}
				if (junkWorldEngines.isPalette4Unlocked()){
					fourthCurrentPalArrow = colors.random();
					colors.removeValue(fourthCurrentPalArrow, true);
					if (colors.size == 0){
						if (junkWorldEngines.isRed()){
							colors.add(0);
						}
						if (junkWorldEngines.isOrange()){
							colors.add(1);
						}
						if (junkWorldEngines.isYellow()){
							colors.add(2);
						}
						if (junkWorldEngines.isGreen()){
							colors.add(3);
						}
						if (junkWorldEngines.isBlue()){
							colors.add(4);
						}
						if (junkWorldEngines.isPurple()){
							colors.add(5);
						}
					}
				}
				colors.clear();
			}
		}
	}

	private void setGamePalettes() {
		if (junkWorldEngines.isPalette1Unlocked()){
			switch (firstCurrentPalArrow) {
			case 0: {
				junkWorldEngines.setPreviousPalette1(1);
				gamePalette.setFirstPalette(1); 
			}; break;
			case 1: {
				junkWorldEngines.setPreviousPalette1(2);
				gamePalette.setFirstPalette(2); 
			}; break;
			case 2: {
				junkWorldEngines.setPreviousPalette1(3);
				gamePalette.setFirstPalette(3); 
			}; break;
			case 3: {
				junkWorldEngines.setPreviousPalette1(4);
				gamePalette.setFirstPalette(4); 
			}; break;
			case 4: {
				junkWorldEngines.setPreviousPalette1(5);
				gamePalette.setFirstPalette(5); 
			}; break;
			case 5: {
				junkWorldEngines.setPreviousPalette1(6);
				gamePalette.setFirstPalette(6); 
			}; break;
			default: break;
			}
		}
		if (junkWorldEngines.isPalette2Unlocked()){
			switch (secondCurrentPalArrow) {
			case 0: {
				junkWorldEngines.setPreviousPalette2(1);
				gamePalette.setSecondPalette(1); 
			}; break;
			case 1: {
				junkWorldEngines.setPreviousPalette2(2);
				gamePalette.setSecondPalette(2); 
			}; break;
			case 2: {
				junkWorldEngines.setPreviousPalette2(3);
				gamePalette.setSecondPalette(3); 
			}; break;
			case 3: {
				junkWorldEngines.setPreviousPalette2(4);
				gamePalette.setSecondPalette(4); 
			}; break;
			case 4: {
				junkWorldEngines.setPreviousPalette2(5);
				gamePalette.setSecondPalette(5); 
			}; break;
			case 5: {
				junkWorldEngines.setPreviousPalette2(6);
				gamePalette.setSecondPalette(6); 
			}; break;
			default: break;
			}
		}

		if (junkWorldEngines.isPalette3Unlocked()){
			switch (thirdCurrentPalArrow) {
			case 0: {
				junkWorldEngines.setPreviousPalette3(1);
				gamePalette.setThirdPalette(1); 
			}; break;
			case 1: {
				junkWorldEngines.setPreviousPalette3(2);
				gamePalette.setThirdPalette(2); 
			}; break;
			case 2: {
				junkWorldEngines.setPreviousPalette3(3);
				gamePalette.setThirdPalette(3); 
			}; break;
			case 3: {
				junkWorldEngines.setPreviousPalette3(4);
				gamePalette.setThirdPalette(4); 
			}; break;
			case 4: {
				junkWorldEngines.setPreviousPalette3(5);
				gamePalette.setThirdPalette(5); 
			}; break;
			case 5: {
				junkWorldEngines.setPreviousPalette3(6);
				gamePalette.setThirdPalette(6); 
			}; break;
			default: break;
			}
		}

		if (junkWorldEngines.isPalette4Unlocked()){
			switch (fourthCurrentPalArrow) {
			case 0: {
				junkWorldEngines.setPreviousPalette4(1);
				gamePalette.setFourthPalette(1); 
			}; break;
			case 1: {
				junkWorldEngines.setPreviousPalette4(2);
				gamePalette.setFourthPalette(2); 
			}; break;
			case 2: {
				junkWorldEngines.setPreviousPalette4(3);
				gamePalette.setFourthPalette(3); 
			}; break;
			case 3: {
				junkWorldEngines.setPreviousPalette4(4);
				gamePalette.setFourthPalette(4); 
			}; break;
			case 4: {
				junkWorldEngines.setPreviousPalette4(5);
				gamePalette.setFourthPalette(5); 
			}; break;
			case 5: {
				junkWorldEngines.setPreviousPalette4(6);
				gamePalette.setFourthPalette(6); 
			}; break;
			default: break;
			}
		}
	}

	private void drawPalettes() {
		if (junkWorldEngines.isPalette1Unlocked()){
			batch.draw(firstPalette.get(firstCurrentPalArrow), firstPaletteLayer.x, firstPaletteLayer.y, firstPaletteLayer.width, firstPaletteLayer.height);
			batch.draw(firstPaletteArrow.get(nextColorPal1), firstArrowLayer.x, firstArrowLayer.y,
					firstArrowLayer.width, firstArrowLayer.height);
		}
		else {
			batch.draw(lockedPalette, firstPaletteLayer.x, firstPaletteLayer.y, firstPaletteLayer.width, firstPaletteLayer.height);
		}
		if (junkWorldEngines.isPalette2Unlocked()){
			batch.draw(secondPalette.get(secondCurrentPalArrow), secondPaletteLayer.x, secondPaletteLayer.y, secondPaletteLayer.width, secondPaletteLayer.height);
			batch.draw(secondPaletteArrow.get(nextColorPal2), secondArrowLayer.x, secondArrowLayer.y,
					secondArrowLayer.width, secondArrowLayer.height);
		}
		else {
			batch.draw(lockedPalette, secondPaletteLayer.x, secondPaletteLayer.y, secondPaletteLayer.width, secondPaletteLayer.height);
		}
		if (junkWorldEngines.isPalette3Unlocked()){
			batch.draw(thirdPalette.get(thirdCurrentPalArrow), thirdPaletteLayer.x, thirdPaletteLayer.y, thirdPaletteLayer.width, thirdPaletteLayer.height);
			batch.draw(thirdPaletteArrow.get(nextColorPal3), thirdArrowLayer.x, thirdArrowLayer.y,
					thirdArrowLayer.width, thirdArrowLayer.height);
		}
		else {
			batch.draw(lockedPalette, thirdPaletteLayer.x, thirdPaletteLayer.y, thirdPaletteLayer.width, thirdPaletteLayer.height);
		}
		if (junkWorldEngines.isPalette4Unlocked()){
			batch.draw(fourthPalette.get(fourthCurrentPalArrow), fourthPaletteLayer.x, fourthPaletteLayer.y, fourthPaletteLayer.width, fourthPaletteLayer.height);
			batch.draw(fourthPaletteArrow.get(nextColorPal4), fourthArrowLayer.x, fourthArrowLayer.y,
					fourthArrowLayer.width, fourthArrowLayer.height);
		}
		else {
			batch.draw(lockedPalette, fourthPaletteLayer.x, fourthPaletteLayer.y, fourthPaletteLayer.width, fourthPaletteLayer.height);
		}
	}

	private void drawColorAbsences() {
		if (!redPres){
			batch.draw(manager.get("screenLabels/noColorRed.png", Texture.class), 128, 0, 256, 50);
		}
		else if (!orangePres){
			batch.draw(manager.get("screenLabels/noColorOrange.png", Texture.class), 128, 0, 256, 50);
		}
		else if (!yellowPres){
			batch.draw(manager.get("screenLabels/noColorYellow.png", Texture.class), 128, 0, 256, 50);
		}
		else if (!greenPres){
			batch.draw(manager.get("screenLabels/noColorGreen.png", Texture.class), 128, 0, 256, 50);
		}
		else if (!bluePres){
			batch.draw(manager.get("screenLabels/noColorBlue.png", Texture.class), 128, 0, 256, 50);
		}
		else if (!purplePres){
			batch.draw(manager.get("screenLabels/noColorPurple.png", Texture.class), 128, 0, 256, 50);
		}
	}


	@Override
	public void render(float delta) {	
		if (manager.update() && !isLoading){
			playMusic();
			createTrashCanLayers();
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			if (Gdx.input.justTouched()){
				delayAds = TimeUtils.millis();
			}
			if (TimeUtils.millis() - delayAds >= 5000 && junkWorldEngines.getGameSelection() == 1 && junkWorldEngines.getGameMode() == 0){
				delayAds = TimeUtils.millis();
//				myRequestHandler.showAds2(true);
			}

			//TODO
			setActors();
			setActorBounds();
			setTrashCanColors();
			setGamePalettes();

			batch.begin();
			batch.draw(manager.get("backgrounds/firstBackground.png", Texture.class), 0, 0);
			batch.draw(manager.get("mainMenuAssets/loadingAssets/blackShader.png", Texture.class), 0, 0, 512, 800);
			batch.draw(manager.get("backgrounds/blueFrame.png", Texture.class), 0, 0);
			batch.draw(manager.get("screenLabels/selectColor.png", Texture.class), 128, 650, 256, 64);
			if (isTrashCan1){
				batch.draw(trashCan1ColorUp.get(lastTrashCan1Color), trashCan1UpLayer.x, trashCan1UpLayer.y, trashCan1UpLayer.width,
						trashCan1UpLayer.height);
				batch.draw(trashCan1Color.get(trashCan1CurrentColor), trashCan1Layer.x, trashCan1Layer.y, trashCan1Layer.width,
						trashCan1Layer.height);
				batch.draw(trashCan1ColorDown.get(nextTrashCan1Color), trashCan1DownLayer.x, trashCan1DownLayer.y,
						trashCan1DownLayer.width, trashCan1DownLayer.height);
			} else batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
					52 + 32, 450, 64, 64);
			if (isTrashCan2){
				batch.draw(trashCan2ColorUp.get(lastTrashCan2Color), trashCan2UpLayer.x, trashCan2UpLayer.y, trashCan2UpLayer.width,
						trashCan2UpLayer.height);
				batch.draw(trashCan2Color.get(trashCan2CurrentColor), trashCan2Layer.x, trashCan2Layer.y, trashCan2Layer.width,
						trashCan2Layer.height);
				batch.draw(trashCan2ColorDown.get(nextTrashCan2Color), trashCan2DownLayer.x, trashCan2DownLayer.y,
						trashCan2DownLayer.width, trashCan2DownLayer.height);
			} else batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
					52 + 128, 450, 64, 64);
			if (isTrashCan3){
				batch.draw(trashCan3ColorUp.get(lastTrashCan3Color), trashCan3UpLayer.x, trashCan3UpLayer.y, trashCan3UpLayer.width,
						trashCan3UpLayer.height);
				batch.draw(trashCan3Color.get(trashCan3CurrentColor), trashCan3Layer.x, trashCan3Layer.y, trashCan3Layer.width,
						trashCan3Layer.height);
				batch.draw(trashCan3ColorDown.get(nextTrashCan3Color), trashCan3DownLayer.x, trashCan3DownLayer.y,
						trashCan3DownLayer.width, trashCan3DownLayer.height);
			} else batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
					276, 450, 64, 64);
			if (isTrashCan4){
				batch.draw(trashCan4ColorUp.get(lastTrashCan4Color), trashCan4UpLayer.x, trashCan4UpLayer.y, trashCan4UpLayer.width,
						trashCan4UpLayer.height);
				batch.draw(trashCan4Color.get(trashCan4CurrentColor), trashCan4Layer.x, trashCan4Layer.y, trashCan4Layer.width,
						trashCan4Layer.height);
				batch.draw(trashCan4ColorDown.get(nextTrashCan4Color), trashCan4DownLayer.x, trashCan4DownLayer.y,
						trashCan4DownLayer.width, trashCan4DownLayer.height);
			} else batch.draw(manager.get("gameScreenAssets/trashCanSelectionAssets/trashCanSlots.png", Texture.class),
					372, 450, 64, 64);
			batch.draw(randomizeRegion, randomizeLayer.x, randomizeLayer.y, randomizeLayer.width, randomizeLayer.height);
			batch.draw(manager.get("buttons/backButton.png", Texture.class), 64, 64, 100, 50);
			batch.draw(manager.get("buttons/nextButton.png", Texture.class), 350, 64, 100, 50);
			batch.draw(manager.get("screenLabels/gamePalette.png", Texture.class), 256 - (200 / 2), 300, 200, 40);
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
			drawPalettes();
			drawColorAbsences();
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
			setIndexes();
			batch.end();

			if (junkWorldEngines.getGameMode() == 0){
				junkWorldEngines.getTutorialStage().getViewport().setCamera(camera);
				junkWorldEngines.getTutorialStage().draw();
				junkWorldEngines.getTutorialStage().act();
				Gdx.input.setInputProcessor(junkWorldEngines.getTutorialStage());
			}
			else {
				stage.act();
				stage.draw();
				stage.getViewport().setCamera(camera);
				Gdx.input.setInputProcessor(stage);
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
			case 5: {
				junkWorldEngines.getTutorialLayer().setPosition(80, 0);
				junkWorldEngines.getCurrentArrow().setPosition(52 + 32 - 64, 550);
				junkWorldEngines.getCurrentArrow().setRotation(-90);
				junkWorldEngines.getCurrentArrow().draw(batch);
				trashCan1Up.setTouchable(Touchable.enabled);
				trashCan2Up.setTouchable(Touchable.enabled);
				trashCan3Up.setTouchable(Touchable.enabled);
				trashCan4Up.setTouchable(Touchable.enabled);
				firstArrow.setTouchable(Touchable.disabled);
				randomize.setTouchable(Touchable.disabled);
				next.setTouchable(Touchable.disabled);
			}; break;
			case 6: {
				junkWorldEngines.getTutorialLayer().setPosition(150, 800 - 330);
				junkWorldEngines.getCurrentArrow().setPosition(16, 136);
				junkWorldEngines.getCurrentArrow().draw(batch);
				trashCan1Up.setTouchable(Touchable.disabled);
				firstArrow.setTouchable(Touchable.enabled);
			}; break;
			case 7: {
				junkWorldEngines.getTutorialLayer().setPosition(100, 500);
				firstArrow.setTouchable(Touchable.disabled);
				randomize.setTouchable(Touchable.enabled);
				next.setTouchable(Touchable.enabled);
			}; break;
			default: break;
			}
			batch.draw(junkWorldEngines.getTutorial().get(junkWorldEngines.getCurrentTutorial()),
					junkWorldEngines.getTutorialLayer().x, junkWorldEngines.getTutorialLayer().y);
			junkWorldEngines.getQuitTutorial().setBounds(junkWorldEngines.getTutorialLayer().x, junkWorldEngines.getTutorialLayer().y, 120, 50);
		}
		//		TODO
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

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
			else {
				manager.get("audioAssets/music/junkWorld.ogg", Music.class).setLooping(true);
				manager.get("audioAssets/music/junkWorld.ogg", Music.class).play();
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
			else {
				manager.get("audioAssets/music/house.ogg", Music.class).setLooping(true);
				manager.get("audioAssets/music/house.ogg", Music.class).play();
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
			else {
				manager.get("audioAssets/music/city.ogg", Music.class).setLooping(true);
				manager.get("audioAssets/music/city.ogg", Music.class).play();
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
