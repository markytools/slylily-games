package com.polyNGonsMainMenu.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.JsonValue;
import com.gameTools.game.ScalableFontButton;
import com.gameTools.game.ShadedBitmapFont;
import com.polyngons.game.GameScreen;
import com.polyngons.game.PolyNGons;
import com.polyNGonsFunctions.game.PolyNGonsSettings;
import com.polyNgonConstants.game.PolyNGonsDimConstants;
import com.polyNgonConstants.game.PolyNGonsGameSelection;
import com.polyNgonConstants.game.SelectedChallenge;
import com.puzzleChallenge.game.PuzzleChallenge;

public class MainMenuScreen implements Screen {
	private PolyNGons game;
	private Stage mainMenu, pCollectionStage, backStage, emptyStage;
	private InputMultiplexer inputs;
	private Array<InputProcessor> processors;
	private Button back;
	private Label pNumLayout;
	private ArrayMap<String, Button> pCollectionButtons1, pCollectionButtons2;
	private final int diffNum = 20;
	private int diffSelected, pNumSelected;
	private ShadedBitmapFont currrentPNum, latestPNum, currentPState, currentDiff, selectP, sDifficulty;
	private JsonValue value;
	private byte[] dUnlocks;
	private int[] currentP, highestP;
	private byte currentPuzzleState;
	private PolyNGonsSettings gameSettings;
	private PuzzleChallenge pChallenge;
	private PolyNGonsPolygons pPolyNGons;

	public MainMenuScreen(final PolyNGons game){
		this.game = game;

		game.assetLoader.loadMainMenuAssets();
		game.assetM.finishLoading();
		
		emptyStage = new Stage();
		processors = new Array<InputProcessor>();
		processors.add(null);
		processors.add(null);
		inputs = new InputMultiplexer();
		inputs.setProcessors(processors);
		
		final ScalableFontButton tutorial = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")),
				game.assetM.get(game.assetID.get("bSmall2")), "Tutorial", 2.5f);
		tutorial.setBounds(10, 100, 250, 50);

		tutorial.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game.getUser().contains("save")){
					game.pAdManager.stopInterstitial();
					if (game.requestAd != null) game.pAdManager.showBannerAds(false);
					game.gSelection = PolyNGonsGameSelection.TUTORIAL;
					game.setScreen(new GameScreen(game));
					Gdx.app.postRunnable(new Runnable(){
						@Override
						public void run() {
							dispose();
						}

					});
				}
				super.clicked(event, x, y);
			}
		});
		
		final ScalableFontButton puzzleCollection = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")),
				game.assetM.get(game.assetID.get("bSmall2")), "Puzzle Collection", 2.5f);
		puzzleCollection.setBounds(10, 30, 250, 50);
		puzzleCollection.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game.getUser().contains("save")) game.gSelection = PolyNGonsGameSelection.PUZZLE_COLLECTION;
				super.clicked(event, x, y);
			}
		});

		final ScalableFontButton puzzleChallenge = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")),
				game.assetM.get(game.assetID.get("bSmall2")), "Puzzle Challenge", 2.5f);
		puzzleChallenge.setBounds(273, 100, 250, 50);
		puzzleChallenge.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game.getUser().contains("save")) game.gSelection = PolyNGonsGameSelection.PUZZLE_CHALLENGE;
				super.clicked(event, x, y);
			}
		});

		final ScalableFontButton polygons = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")),
				game.assetM.get(game.assetID.get("bSmall2")), "Poly N-gons", 2.5f);
		polygons.setBounds(273, 30, 250, 50);
		polygons.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game.getUser().contains("save")) game.gSelection = PolyNGonsGameSelection.POLYNGONS;
				super.clicked(event, x, y);
			}
		});

		final ScalableFontButton settings = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")),
				game.assetM.get(game.assetID.get("bSmall2")), "Settings", 2.5f);
		settings.setBounds(536, 100, 250, 50);
		settings.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameSettings.setToggleSettings(true);
				super.clicked(event, x, y);
			}
		});

		final ScalableFontButton quit = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")),
				game.assetM.get(game.assetID.get("bSmall2")), "Quit", 2.5f);
		quit.setBounds(536, 30, 250, 50);
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run() {
						Gdx.app.exit();
					}
				});
				super.clicked(event, x, y);
			}
		});

		
		createTextField();
		mainMenu = new Stage();
		mainMenu.addActor(polygons);
		mainMenu.addActor(puzzleCollection);
		mainMenu.addActor(puzzleChallenge);
		mainMenu.addActor(quit);
		mainMenu.addActor(settings);
		mainMenu.addActor(tutorial);
		mainMenu.addActor(game.bLayout.createMoreAppsButton());
		mainMenu.addActor(game.bLayout.createRestoreButton());

		createPCollectionLayout();
		createLayout();

		sDifficulty = game.bLayout.getCustomFont();
		diffSelected = 0;

		gameSettings = new PolyNGonsSettings(null, game);
		subtractRating();
		updateCam();

		if (game.requestAd != null) game.pAdManager.showBannerAds(true);
		game.pAdManager.initInterstitial();
		
		game.requestAd.showRateDialog();
	}

	private void subtractRating() {
		int ratingToSubtract = game.pUpdater.getPValue().getInt("ratingToSubtract");
		if (ratingToSubtract != 0){
			int newRating = game.pUpdater.getPValue().getInt("rating") + ratingToSubtract;
			if (newRating > PolyNGonsDimConstants.MAX_PCHALLENGE_LIMIT) newRating = PolyNGonsDimConstants.MAX_PCHALLENGE_LIMIT;
			else if (newRating < 5) newRating = 5;
			game.pUpdater.setRatingValue(newRating);
			game.pUpdater.setRatingSubtract(0);
		}
	}

	private void updateCam() {
		game.cam = new OrthographicCamera();
		game.cam.setToOrtho(false, 800, 500);
		game.cam.zoom = 1;
		game.cam.position.x = 400;
		game.cam.position.y = 250;
	}

	private void createTextField() {
		TextFieldStyle style = new TextFieldStyle();
		style.font = game.bLayout.getSelectBoxFont();
		style.fontColor = Color.WHITE;
	}

	private void createLayout() {
		pChallenge = new PuzzleChallenge(game, this);
		pPolyNGons = new PolyNGonsPolygons(game, this);
	}

	private void createPCollectionLayout() {

		//		DIFFICULTY SELECTION
		pCollectionButtons1 = new ArrayMap<String, Button>();
		pCollectionStage = new Stage();
		for (int i = 0; i < diffNum; i++){
			final int diffInd = i;

			ButtonStyle style = new ButtonStyle();
			style.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("dButton1"))));
			style.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("dButton2"))));

			final Button button = new Button(style);

			int x, y, xMod, yMod;

			if (i >= 0 && i < 5) xMod = i;
			else xMod = i % 5;
			x = 190 + (90 * xMod);

			yMod = ((diffNum + 4 - i) / 5) - 1;
			y = 80 + (75 * yMod);

			button.setBounds(x, y, 60, 60);
			pCollectionButtons1.put("d" + (i + 1), button);
			pCollectionStage.addActor(button);

			button.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (game.pUpdater.getPValue().get("dUnlocks").asByteArray()[diffInd] == 1){
						diffSelected = diffInd + 1;
					}
					super.clicked(event, x, y);
				}
			});
		}

		ButtonStyle style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("back1"))));
		style.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("back2"))));
		back = new Button(style);
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				switch (game.gSelection){
				case NONE:{

				}; break;
				case PUZZLE_COLLECTION:{ 
					if (diffSelected == 0) {
						game.gSelection = PolyNGonsGameSelection.NONE;
					}
					else diffSelected = 0;
				}; break;
				case PUZZLE_CHALLENGE:{ 
					game.gSelection = PolyNGonsGameSelection.NONE;
					game.pCType = SelectedChallenge.NONE;
					
				}; break;
				case POLYNGONS:{ 
					pPolyNGons.setCurrentDisplayedPoly(0);
					if (pPolyNGons.isNewPolyTexture()) pPolyNGons.setNewPolyTexture(false);
					if (pPolyNGons.isNewBordTexture()) pPolyNGons.setNewBordTexture(false);
					game.gSelection = PolyNGonsGameSelection.NONE;
				}; break;
				default:break;
				}
				
				super.clicked(event, x, y);
			}
		});
		back.setBounds(20, 80, 60, 60);


		//		PUZZLE SELECTION
		pCollectionButtons2 = new ArrayMap<String, Button>();

		TextureRegion rRight1 = new TextureRegion(game.assetM.get(game.assetID.get("scroll1")));
		TextureRegion rRight2 = new TextureRegion(game.assetM.get(game.assetID.get("scroll2")));

		ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable(rRight1);
		style2.down = new TextureRegionDrawable(rRight2);
		final Button arrowRight = new Button(style2);
		arrowRight.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (value.get("currentPuzzle").asIntArray()[diffSelected - 1] + 1 >
						value.get("d" + diffSelected + "PUnlocks").asByteArray().length){
					game.pUpdater.setIntValue(diffSelected - 1, 1, true);
				} else game.pUpdater.setIntValue(diffSelected - 1,
						value.get("currentPuzzle").asIntArray()[diffSelected - 1] + 1, true);

				super.clicked(event, x, y);
			}
		});
		arrowRight.setBounds(530, 220, 60, 60);
		pCollectionButtons2.put("arrowRight", arrowRight);

		TextureRegion rLeft1 = new TextureRegion(rRight1);
		TextureRegion rLeft2 = new TextureRegion(rRight2);
		rLeft1.flip(true, false);
		rLeft2.flip(true, false);

		ButtonStyle style3 = new ButtonStyle();
		style3.up = new TextureRegionDrawable(rLeft1);
		style3.down = new TextureRegionDrawable(rLeft2);
		final Button arrowLeft = new Button(style3);
		arrowLeft.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (value.get("currentPuzzle").asIntArray()[diffSelected - 1] - 1 <= 0){
					game.pUpdater.setIntValue(diffSelected - 1,
							value.get("d" + diffSelected + "PUnlocks").asByteArray().length, true);
				} else game.pUpdater.setIntValue(diffSelected - 1,
						value.get("currentPuzzle").asIntArray()[diffSelected - 1] - 1, true);
				super.clicked(event, x, y);
			}
		});
		arrowLeft.setBounds(210, 220, 60, 60);
		pCollectionButtons2.put("arrowLeft", arrowLeft);

		ButtonStyle style4 = new ButtonStyle();
		style4.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("selectPuzzle1"))));
		style4.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("selectPuzzle2"))));
		final Button solvePuzzle = new Button(style4);
		solvePuzzle.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.pAdManager.stopInterstitial();
				if (game.requestAd != null) game.pAdManager.showBannerAds(false);
				game.setScreen(new GameScreen(game));
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run() {
						dispose();
					}

				});
				super.clicked(event, x, y);
			}
		});
		solvePuzzle.setBounds(170, 140, 150, 50);
		pCollectionButtons2.put("solvePuzzle", solvePuzzle);

		ButtonStyle style5 = new ButtonStyle();
		style5.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("indicator1"))));
		style5.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("indicator2"))));
		final Button goToLatest = new Button(style5);
		goToLatest.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int highestNum = value.get("highestPuzzle").asIntArray()[diffSelected - 1];
				if (highestNum != 0) game.pUpdater.setIntValue(diffSelected - 1,
						value.get("highestPuzzle").asIntArray()[diffSelected - 1], true);
				super.clicked(event, x, y);
			}
		});
		goToLatest.setBounds(340, 90, 300, 100);
		pCollectionButtons2.put("goToLatest", goToLatest);

		LabelStyle lStyle = new LabelStyle();
		lStyle.background = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("pNumLayout")))); 
		lStyle.font = game.bLayout.getCustomFont();

		pNumLayout = new Label("", lStyle);
		pNumLayout.setBounds(300, 220, 200, 60);

		for (String key : pCollectionButtons2.keys()){
			pCollectionStage.addActor(pCollectionButtons2.get(key));
		}
		
		backStage = new Stage();
		backStage.addActor(back);

		//		fonts    currrentPNum, latestPNum, currentPState, currentDiff, selectP

		currentDiff = game.bLayout.getCustomFont();
		selectP = game.bLayout.getCustomFont();
		currrentPNum = game.bLayout.getCustomFont();
		currentPState = game.bLayout.getCustomFont();
		latestPNum = game.bLayout.getCustomFont();
	}

	@Override
	public void render(float delta) {
		updateReferences();
		shiftActors(diffSelected);
		game.batch.begin();
		game.batch.draw(game.splashImage, 0, 0, 800, 500);
		drawPSelectionsButton();
		drawSettings();
		game.batch.end();

		setInputListeners();
		
		if (game.requestAd != null) game.pAdManager.showBannerAds(true);
		game.pAdManager.showDelayedInterstitial(true);
	}

	private void updateReferences() {
		value = game.pUpdater.getPValue();
		if (diffSelected != 0) {
			game.pNumSelected = value.get("currentPuzzle").asIntArray()[diffSelected - 1];
			game.diffSelected = diffSelected;
		}

		dUnlocks = value.get("dUnlocks").asByteArray();
		currentP = value.get("currentPuzzle").asIntArray();
		highestP = value.get("highestPuzzle").asIntArray();

		pPolyNGons.gettRenderer().setProperTextures();
	}

	private void drawPSelectionsButton() {
		pCollectionTextures();
	}

	private void pCollectionTextures() {
		if (game.gSelection == PolyNGonsGameSelection.PUZZLE_COLLECTION){
			game.batch.draw(game.assetM.get(game.assetID.get("layout2")), 100, 50);
			if (diffSelected == 0){
				sDifficulty.drawFont(game.batch, "Select Puzzle Difficulty", 192, 430, 2.4f, 2.4f,
						Color.WHITE);
				for (int i = 0; i < diffNum; i++){
					pCollectionButtons1.getValueAt(i).draw(game.batch, 1);
					if (dUnlocks[i] == 1){
						pCollectionButtons1.getValueAt(i).setDisabled(false);
						game.batch.draw(game.assetM.get(game.assetID.get("d" + (i + 1) + "Button")),
								pCollectionButtons1.getValueAt(i).getX(), pCollectionButtons1.getValueAt(i).getY(),
								60, 60);
					}
					else {
						pCollectionButtons1.getValueAt(i).setDisabled(true);
						game.batch.draw(game.assetM.get(game.assetID.get("locked")),
								pCollectionButtons1.getValueAt(i).getX(), pCollectionButtons1.getValueAt(i).getY(),
								60, 60);
					}
				}
			}
			else {
				for (String key : pCollectionButtons2.keys()){
					pCollectionButtons2.get(key).draw(game.batch, 1);
				}
				pNumLayout.draw(game.batch, 1);
				currentDiff.drawMultiLineFont(game.batch, "Difficulty " + diffSelected,
						155, 440, 500, BitmapFont.HAlignment.CENTER, 4, 4, Color.WHITE);
				selectP.drawMultiLineFont(game.batch, "Select A Puzzle", 238, 360, 2.8f, 2.8f,
						Color.WHITE);
				currrentPNum.drawMultiLineFont(game.batch, "Puzzle " + currentP[diffSelected - 1],
						274, 282, 250, BitmapFont.HAlignment.CENTER, 2.1f, 2.3f, Color.WHITE);

				currentPuzzleState = value.get("d" + diffSelected + "PUnlocks").
						asByteArray()[currentP[diffSelected - 1] - 1];
				
				switch (currentPuzzleState){
				case 0: {
					game.batch.draw(game.assetM.get(game.assetID.get("locked")), 220, 140, 50, 50);
					latestPNum.drawFont(game.batch, "Solve previous puzzles to unlock.", 355, 181,
							1f, 1.1f, Color.WHITE);
				}; break;
				case 1: {
					currentPState.drawMultiLineFont(game.batch, "Solve", 147, 194, 200,
							BitmapFont.HAlignment.CENTER, 2.2f, 2.2f, Color.WHITE);
					latestPNum.drawMultiLineFont(game.batch, "This puzzle has not been solve.", 355, 181,
							200, BitmapFont.HAlignment.LEFT, 1f, 1.1f, Color.WHITE);
				}; break;
				case 2: {
					currentPState.drawMultiLineFont(game.batch, "Solved!", 147, 194, 200, BitmapFont.HAlignment.CENTER,
							2.2f, 2.2f, Color.WHITE);
					latestPNum.drawMultiLineFont(game.batch, "You had solved this puzzle!", 355, 181,
							200, BitmapFont.HAlignment.LEFT, 1f, 1.1f, Color.WHITE);
				}; break;
				default: break;
				}

				if (currentP[diffSelected - 1] != highestP[diffSelected - 1] &&
						highestP[diffSelected - 1] != 0){
					latestPNum.drawMultiLineFont(game.batch, "Highest Unsolved --- Puzzle " +
							highestP[diffSelected - 1] + ".", 355, 156, 200, BitmapFont.HAlignment.LEFT,
							1f, 1.1f, Color.WHITE);
					latestPNum.drawMultiLineFont(game.batch, "Click here to go there!", 355, 131,
							200, BitmapFont.HAlignment.LEFT, 1f, 1.1f, Color.WHITE);
				}
			}
		}
		else if (game.gSelection == PolyNGonsGameSelection.PUZZLE_CHALLENGE) {
			pChallenge.renderPChallenge();
		}
		else if (game.gSelection == PolyNGonsGameSelection.POLYNGONS) pPolyNGons.render();
		else for (Actor b : mainMenu.getActors()) b.draw(game.batch, 1);
	}

	private void setInputListeners() {
		if (!gameSettings.isToggleSettings()){
			if (game.gSelection == PolyNGonsGameSelection.NONE){
				mainMenu.getViewport().setCamera(game.cam);
				inputs.getProcessors().set(0, mainMenu);
				inputs.getProcessors().set(1, emptyStage);
				Gdx.input.setInputProcessor(inputs);
				mainMenu.act();
			}
			else if (game.gSelection == PolyNGonsGameSelection.PUZZLE_COLLECTION){
				backStage.getViewport().setCamera(game.cam);
				pCollectionStage.getViewport().setCamera(game.cam);
				backStage.draw();
				
				inputs.getProcessors().set(0, backStage);
				inputs.getProcessors().set(1, pCollectionStage);
				Gdx.input.setInputProcessor(inputs);
				backStage.act();
				pCollectionStage.act();
			}
			else if (game.gSelection == PolyNGonsGameSelection.PUZZLE_CHALLENGE){
				pChallenge.setPChallengeListeners();
			}
			else if (game.gSelection == PolyNGonsGameSelection.POLYNGONS){
				pPolyNGons.setListeners();
			}
		} else gameSettings.setSettingsListeners(false);
	}

	private void drawSettings() {
		if (gameSettings.isToggleSettings()) gameSettings.renderSettings();
	}

	private void shiftActors(int first){
		if (first == 0){
			for (String key : pCollectionButtons1.keys()) pCollectionButtons1.get(key).setTouchable(Touchable.enabled);
			for (String key : pCollectionButtons2.keys()) pCollectionButtons2.get(key).setTouchable(Touchable.disabled);
		}
		else {
			for (String key : pCollectionButtons1.keys()) pCollectionButtons1.get(key).setTouchable(Touchable.disabled);
			for (String key : pCollectionButtons2.keys()) {
				if (key != "solvePuzzle") pCollectionButtons2.get(key).setTouchable(Touchable.enabled);
			}

			switch (currentPuzzleState){
			case 0: pCollectionButtons2.get("solvePuzzle").setTouchable(Touchable.disabled); break;
			case 1: pCollectionButtons2.get("solvePuzzle").setTouchable(Touchable.enabled); break;
			case 2: pCollectionButtons2.get("solvePuzzle").setTouchable(Touchable.enabled); break;
			default: break;
			}
		}
	}

	public InputMultiplexer getInputs() {
		return inputs;
	}

	public Stage getBackStage() {
		return backStage;
	}

	public Stage getMainMenu() {
		return mainMenu;
	}

	public int getDiffSelected() {
		return diffSelected;
	}

	public int getpNumSelected() {
		return pNumSelected;
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		mainMenu.dispose();
		pCollectionStage.dispose();
		pChallenge.dispose();
		gameSettings.dispose();
		backStage.dispose();
		emptyStage.dispose();
		pPolyNGons.dispose();
		
		game.assetM.unload(game.assetID.get("dButton1").fileName);
		game.assetM.unload(game.assetID.get("dButton2").fileName);
		game.assetM.unload(game.assetID.get("back1").fileName);
		game.assetM.unload(game.assetID.get("back2").fileName);
		game.assetM.unload(game.assetID.get("locked").fileName);
		for (int i = 0; i < 20; i++){
			game.assetM.unload(game.assetID.get("d" + (i + 1) + "Button").fileName);
		}
		
		game.assetM.unload(game.assetID.get("pNumLayout").fileName);
		game.assetM.unload(game.assetID.get("scroll1").fileName);
		game.assetM.unload(game.assetID.get("scroll2").fileName);
		game.assetM.unload(game.assetID.get("selectPuzzle1").fileName);
		game.assetM.unload(game.assetID.get("selectPuzzle2").fileName);
		game.assetM.unload(game.assetID.get("indicator1").fileName);
		game.assetM.unload(game.assetID.get("indicator2").fileName);

		game.assetM.unload(game.assetID.get("bBig1").fileName);
		game.assetM.unload(game.assetID.get("bBig2").fileName);

		game.assetM.unload(game.assetID.get("lSelectBg").fileName);
		game.assetM.unload(game.assetID.get("scrollBg").fileName);
		game.assetM.unload(game.assetID.get("scrollCorner").fileName);
		game.assetM.unload(game.assetID.get("scrollVScroll").fileName);
		game.assetM.unload(game.assetID.get("scrollVScrollKnob").fileName);
		game.assetM.unload(game.assetID.get("sbBg").fileName);
		
		game.assetM.unload(game.assetID.get("new").fileName);

		gameSettings = null;
		pChallenge = null;
		pPolyNGons = null;
	}
}
