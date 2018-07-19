package com.polyngons.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;
import com.gameInputProcessors.game.PuzzleInputListener;
import com.polyNGonsAssetPack.game.GameSpriteCreator;
import com.polyNGonsFunctions.game.PolyNGonsInGameMenu;
import com.polyNGonsFunctions.game.PolyNGonsSettings;
import com.polyNGonsFunctions.game.PolygonDescriptor;
import com.polyNGonsFunctions.game.PuzzleCollectionDescriptor;
import com.polyNGonsPuzzleSupplier.game.PuzzleDescriptionOutputStreamer;
import com.polyNGonsUI.game.PolyNGonsUIManager;
import com.polyNGonsUI.game.PuzzlePolygonManager;
import com.polyNGonsUI.game.TutorialUIManager;
import com.polyNgonConstants.game.GameState;
import com.polyNgonConstants.game.PolyNGonsDimConstants;
import com.polyNgonConstants.game.PolyNGonsGameSelection;
import com.polyNgonConstants.game.SelectedChallenge;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;
import com.puzzleBorder.game.PuzzleBorderRenderer;
import com.puzzleChallenge.game.PuzzleChallengeDisplayer;
import com.puzzleCompletion.game.PuzzleCompletionManager;
import com.puzzleCreator.game.PolyNGonGenerator;
import com.puzzleCreator.game.PolygonByteCreator;
import com.puzzleCreator.game.PolygonSizeType;

public class GameScreen implements Screen {

	public OrthographicCamera uiCam;
	public SpriteBatch uiBatch;
	public Vector3 uiTouchPos;
	public GameState gState;

	private PolyNGonGenerator puzzleGen;
	private GameSpriteCreator gSpCreator;
	private PuzzlePolygonManager ppManager;
	private PolyNGonsUIManager uiManager;
	private PuzzleDescriptionOutputStreamer pDOS;
	private TutorialUIManager tUIManager;
	private PolyNGonsInGameMenu inGameMenu;
	private PuzzleBorderRenderer pBordManager;
	private PuzzleChallengeDisplayer pCDisplay;

	private Stage puzzleStage, uiStage;

	public Polygon selectedPolygon;
	private boolean puzzleFinish = false;
	//	input manipulators
	public boolean panPuzzle = true;

	private TextureRegion polySpaceReg;
	private PuzzleInputListener pListener;
	private PolyNGonsSettings settings;
	private PuzzleCompletionManager pCManager;

	public PolyNGons game;

	private Array<Integer> arrayBooleans;

	public GameScreen(PolyNGons game){
		this.game = game;
		uiBatch = new SpriteBatch();
		uiCam = new OrthographicCamera();
		uiCam.setToOrtho(false, 800, 500);
		uiTouchPos = new Vector3();
		game.gSoundManager.setUiBatch(uiBatch);
		game.averageDeltaTime = 1/45f;

		game.assetLoader.loadGameScreenAssets();
		game.assetM.finishLoading();

		pDOS = new PuzzleDescriptionOutputStreamer();
		gState = GameState.IN_GAME;

		boolean tSelected = (game.gSelection == PolyNGonsGameSelection.TUTORIAL) ? true : false;
		tUIManager = new TutorialUIManager(this, tSelected);
		prepareListeners();
		preparePuzzleGen();

		//		Temporary Methods
		createArrayBoolean();
	}

	private void createArrayBoolean() {
		arrayBooleans = new Array<Integer>();
	}

	@Override
	public void render(float delta) {
		if (!puzzleFinish){
			game.batch.begin();
			game.renderLoadingScreen();
			game.batch.end();
			generatePuzzle();
		}
		else {
			handleInput();

			drawPuzzle();
			drawUI();
			imployGameProcesses();

			setInputListener();
			setCamBounds();
		}
	}

	private void prepareListeners() {
		puzzleStage = new Stage();
		uiStage = new Stage();
		pListener = new PuzzleInputListener(this, puzzleStage);
	}

	private void preparePuzzleGen() {
		Gdx.input.setInputProcessor(null);
		puzzleGen = new PolyNGonGenerator(this);
	}

	private void generatePuzzle() {
		switch (game.gSelection){
		case PUZZLE_COLLECTION:{
			PuzzleCollectionDescriptor pCDesc = getPuzzleDesc(game.diffSelected, game.pNumSelected);
			Array<Polygon> puzzlePoly = new Array<Polygon>();
			PolygonByteCreator pBCreator = puzzleGen.getPolyCreator();
			for (PolygonDescriptor polyDesc : pCDesc.getPuzzlePolygons()){
				puzzlePoly.add(pBCreator.createPolygon(polyDesc.getPolyName(), polyDesc.getxPos(), polyDesc.getyPos(),
						polyDesc.isRevearsed(), polyDesc.getPosition()));
			}
			puzzleGen.setPuzzlePolygons(puzzlePoly);
			puzzleGen.finalizePuzzle();
			loadOtherPGenVars();

		}; break;
		case PUZZLE_CHALLENGE:{
			if (puzzleGen.isRegeneratePuzzle()){
				arrayBooleans.clear();
				int num = MathUtils.random(8);
				while (arrayBooleans.size < 2){
					num = MathUtils.random(8);
					if (!arrayBooleans.contains(num, true) || num == 0) arrayBooleans.add(num);
				}

				for (int i = 1; i <= 3; i++){ 
					int randomNum = MathUtils.random(1, 100);
					switch (i){
					case 1: {
						if (randomNum >= 1 && randomNum <= 10) puzzleGen.setTriASizeType(PolygonSizeType.UNLIMITED);
						else if (randomNum >= 11 && randomNum <= 40) puzzleGen.setTriASizeType(PolygonSizeType.LIMITED);
						else if (randomNum >= 41 && randomNum <= 100) puzzleGen.setTriASizeType(PolygonSizeType.NONE);
					}; break;
					case 2: {
						if (randomNum >= 1 && randomNum <= 15) puzzleGen.setSqSizeType(PolygonSizeType.UNLIMITED);
						else if (randomNum >= 16 && randomNum <= 50) puzzleGen.setSqSizeType(PolygonSizeType.LIMITED);
						else if (randomNum >= 51 && randomNum <= 100) puzzleGen.setSqSizeType(PolygonSizeType.NONE);
					}; break;
					case 3: {
						if (randomNum >= 1 && randomNum <= 20) puzzleGen.setRecSizeType(PolygonSizeType.UNLIMITED);
						else if (randomNum >= 21 && randomNum <= 55) puzzleGen.setRecSizeType(PolygonSizeType.LIMITED);
						else if (randomNum >= 56 && randomNum <= 100) puzzleGen.setRecSizeType(PolygonSizeType.NONE);
					}; break;
					default: break;
					}
				}

				//		triA, sq, triB, rec, rhom, paraA, trapA, rTrapA, trapB, rTrapB, paraB
				puzzleGen.generatePuzzle(puzzleGen.getTriASizeType() != PolygonSizeType.NONE, 
						puzzleGen.getSqSizeType() != PolygonSizeType.NONE, !arrayBooleans.contains(1, true), 
						puzzleGen.getRecSizeType() != PolygonSizeType.NONE, !arrayBooleans.contains(2, true), 
						!arrayBooleans.contains(3, true), !arrayBooleans.contains(4, true), 
						!arrayBooleans.contains(5, true), !arrayBooleans.contains(6, true), 
						!arrayBooleans.contains(7, true), !arrayBooleans.contains(8, true), 
						game.pUpdater.getPValue().getInt("rating"));
			}
			else {
				if (puzzleGen.isContinuePuzzle()) puzzleGen.continuePuzzle();
				else {
					if (!checkIfDuplicatePuzzle()){
						loadOtherPGenVars();
						pCDisplay = new PuzzleChallengeDisplayer(this, game.pCType);
						pCDisplay.startTimer();

						if (game.pCType == SelectedChallenge.REGULAR_PUZZLE){
							game.pUpdater.setRatingSubtract(-1);
						}
						else game.pUpdater.setRatingSubtract(-3);
					}
				}
			}
		}; break;
		case TUTORIAL:{
			if (puzzleGen.isRegeneratePuzzle()){
				//		triA, sq, triB, rec, rhom, paraA, trapA, rTrapA, trapB, rTrapB, paraB
				puzzleGen.generatePuzzle(true, false, false, false, false, false, false, false, false, false, false, 2);
			}
			else {
				if (puzzleGen.isContinuePuzzle()) puzzleGen.continuePuzzle();
				else {
					loadOtherPGenVars();
				}
			}
		}; break;
		case TESTING_DEBUGING: {
			if (puzzleGen.isRegeneratePuzzle()){
				arrayBooleans.clear();
				int num = MathUtils.random(8);
				while (arrayBooleans.size < 2){
					num = MathUtils.random(8);
					if (!arrayBooleans.contains(num, true) || num == 0) arrayBooleans.add(num);
				}

				for (int i = 1; i <= 3; i++){ 
					int randomNum = MathUtils.random(1, 100);
					switch (i){
					case 1: {
						if (randomNum >= 1 && randomNum <= 10) puzzleGen.setTriASizeType(PolygonSizeType.UNLIMITED);
						else if (randomNum >= 11 && randomNum <= 40) puzzleGen.setTriASizeType(PolygonSizeType.LIMITED);
						else if (randomNum >= 41 && randomNum <= 100) puzzleGen.setTriASizeType(PolygonSizeType.NONE);
					}; break;
					case 2: {
						if (randomNum >= 1 && randomNum <= 15) puzzleGen.setSqSizeType(PolygonSizeType.UNLIMITED);
						else if (randomNum >= 16 && randomNum <= 50) puzzleGen.setSqSizeType(PolygonSizeType.LIMITED);
						else if (randomNum >= 51 && randomNum <= 100) puzzleGen.setSqSizeType(PolygonSizeType.NONE);
					}; break;
					case 3: {
						if (randomNum >= 1 && randomNum <= 20) puzzleGen.setRecSizeType(PolygonSizeType.UNLIMITED);
						else if (randomNum >= 21 && randomNum <= 55) puzzleGen.setRecSizeType(PolygonSizeType.LIMITED);
						else if (randomNum >= 56 && randomNum <= 100) puzzleGen.setRecSizeType(PolygonSizeType.NONE);
					}; break;
					default: break;
					}
				}

				//		triA, sq, triB, rec, rhom, paraA, trapA, rTrapA, trapB, rTrapB, paraB
				puzzleGen.generatePuzzle(puzzleGen.getTriASizeType() != PolygonSizeType.NONE, 
						puzzleGen.getSqSizeType() != PolygonSizeType.NONE, !arrayBooleans.contains(1, true), 
						puzzleGen.getRecSizeType() != PolygonSizeType.NONE, !arrayBooleans.contains(2, true), 
						!arrayBooleans.contains(3, true), !arrayBooleans.contains(4, true), 
						!arrayBooleans.contains(5, true), !arrayBooleans.contains(6, true), 
						!arrayBooleans.contains(7, true), !arrayBooleans.contains(8, true), 15);
			}
			else {
				if (puzzleGen.isContinuePuzzle()) puzzleGen.continuePuzzle();
				else {
					loadOtherPGenVars();
				}
			}
		}; break;
		default: {
			if (puzzleGen.isRegeneratePuzzle()){
				//		triA, sq, triB, rec, rhom, paraA, trapA, rTrapA, trapB, rTrapB, paraB
				puzzleGen.generatePuzzle(true, true, true, true, true, true, true, true, true, true, true, 100);
			}
			else {
				if (puzzleGen.isContinuePuzzle()) puzzleGen.continuePuzzle();
				else {
					loadOtherPGenVars();
				}
			}
		}; break;
		}

		if (puzzleFinish) {
			game.cam.position.x = ((puzzleGen.getPuzzleBorderCreator().getMaxX() + 1) * PolyNGonsDimConstants.PUZZLE_SCALE) / 2;
			game.cam.position.y = ((puzzleGen.getPuzzleBorderCreator().getMaxY() + 1) * PolyNGonsDimConstants.PUZZLE_SCALE) / 2;
		}
	}

	private boolean checkIfDuplicatePuzzle() {
		Preferences userData = game.getUser();
		boolean duplicate = true;

		if (userData.getString("recentP") == "") {
			PuzzleCollectionDescriptor[] pCDescArray = new PuzzleCollectionDescriptor[10];
			for (int i = 10; i > 0; i--){
				PuzzleCollectionDescriptor desc = new PuzzleCollectionDescriptor(0, 0);
				Array<PolygonDescriptor> fPuzzlePoly = new Array<PolygonDescriptor>();
				fPuzzlePoly.add(new PolygonDescriptor(PolygonName.triA, 0, 0,
						false, 1));
				desc.setPuzzlePolygons(fPuzzlePoly);
				pCDescArray[i - 1] = desc;
			}

			userData.putString("recentP", Base64Coder.encodeString(game.json.toJson(pCDescArray)));
			userData.flush();
		}
		String pCDescString = Base64Coder.decodeString(userData.getString("recentP"));
		pCDescString = PolyNGons.filterSaved(new StringBuilder(pCDescString)).toString();

		if (checkNoDuplication(pCDescString)){
			duplicate = false;
			PuzzleCollectionDescriptor[] pCDescArray = game.json.fromJson(PuzzleCollectionDescriptor[].class, pCDescString);
			for (int i =  pCDescArray.length; i > 0; i--){
				if (pCDescArray[i - 1] == null || i == 0) continue;
				else {
					if (i == 0){
						if (pCDescArray[0] == null){
							pCDescArray = createPuzzleDescAtIndex(pCDescArray, 0);
						}
						else {
							pCDescArray = createPuzzleDescAtIndex(pCDescArray, 1);
						}
					}
					else {
						for (int i2 = 0; i2 < i; i2++){
							if (i2 == i - 1){
								pCDescArray = createPuzzleDescAtIndex(pCDescArray, i2);
								break;
							}
							pCDescArray[i2] = pCDescArray[i2 + 1];
						}
					}
					break;
				}
			}
			userData.putString("recentP", Base64Coder.encodeString(game.json.toJson(pCDescArray)));
			userData.flush();
		}
		else {
			puzzleGen.generatorReset();
		}

		return duplicate;
	}

	private boolean checkNoDuplication(String pCDescString){
		JsonValue puzzle = new JsonReader().parse(pCDescString);
		for (int i =  puzzle.size; i > 0; i--){
			if (puzzle.get(i - 1) == null) continue;
			else {
				JsonValue pPoly = puzzle.get(i - 1);

				int samePolyCount = 0;
				for (int polyNum = pPoly.get("polyDescriptor").size; polyNum > 0; polyNum--){
					byte pName = pPoly.get("polyDescriptor").get(polyNum - 1).getByte("polyName");
					float xPos = pPoly.get("polyDescriptor").get(polyNum - 1).getFloat("xPos");
					float yPos = pPoly.get("polyDescriptor").get(polyNum - 1).getFloat("yPos");
					boolean rev = pPoly.get("polyDescriptor").get(polyNum - 1).getBoolean("revearsed");
					int position = pPoly.get("polyDescriptor").get(polyNum - 1).getInt("position");

					for (int i2 = puzzleGen.getPuzzlePolygon().size; i2 > 0; i2--){
						Polygon poly = puzzleGen.getPuzzlePolygon().get(i2 - 1);
						if (pName == poly.getName() &&
								xPos == poly.getPolyXPos() &&
								yPos == poly.getPolyYPos() &&
								rev == poly.isReversed() &&
								position == poly.getPosition()) samePolyCount++;
						if (samePolyCount >= ((pPoly.get("polyDescriptor").size * 1f)/ 2)) return false;
					}
				}
			}
		}

		return true;
	}

	private PuzzleCollectionDescriptor[] createPuzzleDescAtIndex(PuzzleCollectionDescriptor[] pCDescArray, int index){
		PuzzleCollectionDescriptor[] pCArray = pCDescArray;
		PuzzleCollectionDescriptor desc = new PuzzleCollectionDescriptor(0, 0);
		Array<PolygonDescriptor> fPuzzlePoly = new Array<PolygonDescriptor>();
		for (Polygon poly : puzzleGen.getPuzzlePolygon()){
			fPuzzlePoly.add(new PolygonDescriptor(poly.getName(), poly.getPolyXPos(), poly.getPolyYPos(),
					poly.isReversed(), poly.getPosition()));
		}
		desc.setPuzzlePolygons(fPuzzlePoly);
		pCArray[index] = desc;
		return pCArray;
	}

	private void loadOtherPGenVars(){
		ppManager = new PuzzlePolygonManager(this, puzzleGen, puzzleStage);
		gSpCreator = new GameSpriteCreator(this);
		inGameMenu = new PolyNGonsInGameMenu(this);
		settings = new PolyNGonsSettings(this, this.game);
		uiManager = new PolyNGonsUIManager(this, uiStage, puzzleStage, inGameMenu);
		pCManager = new PuzzleCompletionManager(this);

		createImages();
		createFirstTip();
		game.stopLoadingScreen();
		puzzleFinish = true;
		TimeUtils.millis();
	}

	private void createFirstTip(){
		int tFirstTips = 3;
		if (game.gSelection == PolyNGonsGameSelection.PUZZLE_CHALLENGE) tFirstTips += 1;
		int selectedFirstTip = MathUtils.random(0, tFirstTips);

		switch (selectedFirstTip){
		case 2: uiManager.getgTips().setAndRenderOnce("solvingP"); break;
		case 3: uiManager.getgTips().setAndRenderOnce("pan"); break;
		case 4: uiManager.getgTips().setAndRenderOnce("pChallenge"); break;
		default: break;
		}
	}

	private PuzzleCollectionDescriptor getPuzzleDesc(int diffSelected, int pNum){
		String pDos = pDOS.getDifficultyDesc(diffSelected);
		PuzzleCollectionDescriptor[] pDescArray = game.json.fromJson(PuzzleCollectionDescriptor[].class,
				pDos);
		pDos = null;
		for (int i = 0; i < pDescArray.length; i++){
			if (pDescArray[i].getPuzzleNum() == pNum) return pDescArray[i];
		}

		return null;
	}

	private void setInputListener() {
		uiTouchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		uiCam.unproject(uiTouchPos);
		uiCam.update();

		switch (gState){
		case IN_GAME:{
			if (!tUIManager.isDisableOtherInputs()){
				puzzleStage.getViewport().setCamera(game.cam);
				uiStage.getViewport().setCamera(uiCam);
				if (uiTouchPos.x < 120 ||
						uiTouchPos.x >= 650){
					panPuzzle = false;
					Gdx.input.setInputProcessor(uiStage);
					uiStage.act();
					if (Gdx.input.justTouched())
						if (uiManager.getUi().getPolyButtonsMap().size > 4){
							int showTip = MathUtils.random(1, 4);
							if (showTip == 4) uiManager.getgTips().setAndRenderOnce("arrows");
						}
				} 
				else {
					panPuzzle = true;
					Gdx.input.setInputProcessor(puzzleStage);
					puzzleStage.act();
				}
				pListener.checkPuzzleInputs();
			}
			else {
				if (Gdx.input.justTouched()) {
					tUIManager.updateTutorial(14);
					tUIManager.updateTutorial(13);
					tUIManager.updateTutorial(12);
					tUIManager.updateTutorial(10);
					tUIManager.updateTutorial(9);
					tUIManager.updateTutorial(8);
					tUIManager.updateTutorial(7);
					tUIManager.updateTutorial(5);
					tUIManager.updateTutorial(4);
					tUIManager.updateTutorial(3);
					tUIManager.updateTutorial(2);
					tUIManager.updateTutorial(1);
					tUIManager.updateTutorial(0);
				}
			}
		}; break;
		case IN_MENU: {
			inGameMenu.setInGameMenuListener();
		}; break;
		case IN_COMPLETION:{
			if (game.gSelection == PolyNGonsGameSelection.PUZZLE_CHALLENGE) {
				if (game.pCType == SelectedChallenge.REGULAR_PUZZLE){
					pCDisplay.stopTimer();
					pCDisplay.pChallengeCompleteResult();
					pCManager.setPCListeners();
				}
				else if (game.pCType == SelectedChallenge.TIMED_PUZZLE){
					pCDisplay.stopTimer();
					if (pCManager.isPuzzleComplete()) pCDisplay.pChallengeCompleteResult();
					else pCDisplay.appleFailedResult(false);
					pCManager.setPCListeners();
				}
			}
			else {
				if (pCManager.checkDelayComplete()){
					pCManager.setPCListeners();
				} else Gdx.input.setInputProcessor(null);
			}
		}; break;
		default: break;
		}
	}

	private void drawUI(){
		uiManager.renderUI();

		if (game.gSelection == PolyNGonsGameSelection.PUZZLE_CHALLENGE) {
			if (game.pCType == SelectedChallenge.REGULAR_PUZZLE){
				if (pCManager.isPuzzleComplete()){
					gState = GameState.IN_COMPLETION;
					if (pCManager.checkDelayComplete()) {
						pCManager.setPuzzleSolved(true);
						pCManager.renderPuzzleCompletion();
					}
					playWinProper();
				}
				else playPlacementSounds("normal");
			}
			else if (game.pCType == SelectedChallenge.TIMED_PUZZLE){
				if (pCManager.isPuzzleComplete()){
					gState = GameState.IN_COMPLETION;
					if (pCManager.checkDelayComplete()) {
						pCManager.renderPuzzleCompletion();
						if (pCDisplay.isDisplayable()) {
							pCManager.setPuzzleSolved(true);
							pCManager.renderPuzzleCompletion();
						}
					}
					playWinProper();
					game.gSoundManager.stopSound("ticking");
				}
				else playPlacementSounds("normal");

				if (pCDisplay.isStopTimer()) {
					gState = GameState.IN_COMPLETION;
					if (!pCManager.isPuzzleComplete()) {
						pCManager.setPuzzleSolved(false);
						if (pCDisplay.isDisplayable()) {
							pCManager.renderPuzzleCompletion();
						}
					}
				}
			}
		}
		else {
			if (pCManager.isPuzzleComplete()){
				gState = GameState.IN_COMPLETION;
				pCManager.setPuzzleSolved(true);
				if (pCManager.checkDelayComplete()) pCManager.renderPuzzleCompletion();
				playWinProper();
			}
			else playPlacementSounds("normal");
		}
	}

	private void playPlacementSounds(String soundID){
		if (pListener.isPendNormalSound() && pCManager.isScanComplete()){
			if (soundID.equals("normal")) {
				int showTip = MathUtils.random(1, 6);
				if (showTip == 6) uiManager.getgTips().setAndRenderOnce("remove");
			}
			pListener.setPendNormalSound(false);
			game.gSoundManager.playSound(soundID);
		}
	}

	private void playWinProper(){
		if (game.gSelection != PolyNGonsGameSelection.TUTORIAL){
			if (!pListener.isRequestScan()){
				ppManager.setFinishedRegUpdate(false);
				playPlacementSounds("win");
			}
		}
		else {
			if (!pListener.isRequestScan()){
				playPlacementSounds("win");
			}
		}
	}

	private void drawPuzzle() {
		game.batch.begin();
		renderGame();
		game.batch.end();
		if (ppManager.isFinishedRegUpdate() && !pListener.isRequestScan() 
				&& game.gSelection == PolyNGonsGameSelection.TUTORIAL){
			ppManager.setFinishedRegUpdate(false);
			gettUIManager().updateTutorial(11);
		}
	}

	private void imployGameProcesses() {
		if (pListener.isRequestScan()){
			pListener.setRequestScan(false);
			pCManager.scanPuzzle();
			pCManager.setScanComplete(true);
		}
		pCManager.checkDelayCompletion();
	}

	private void renderGame() {
		if (!pCManager.isPuzzleComplete()){
			for (int xPos = 0; xPos <= puzzleGen.getPuzzleBorderCreator().getMaxX(); xPos++){
				for (int yPos = 0; yPos <= puzzleGen.getPuzzleBorderCreator().getMaxY(); yPos++){
					game.batch.draw(polySpaceReg, xPos * PolyNGonsDimConstants.PUZZLE_SCALE, yPos * PolyNGonsDimConstants.PUZZLE_SCALE, PolyNGonsDimConstants.PUZZLE_SCALE, PolyNGonsDimConstants.PUZZLE_SCALE);
				}
			}
		}
		pBordManager.renderBorders();
		ppManager.renderPuzzlePolygons();
	}

	private void createImages() {
		ppManager.createPuzzleAssets();
		pBordManager = new PuzzleBorderRenderer(this);
		polySpaceReg = gSpCreator.getPolySpaceReg();
	}

	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {

			game.cam.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			game.cam.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			game.cam.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			game.cam.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			game.cam.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			game.cam.translate(0, 3, 0);
		}
		//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
		//        	game.cam.rotate(-rotationSpeed, 0, 0, 1);
		//        }
		//        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
		//        	game.cam.rotate(rotationSpeed, 0, 0, 1);
		//        }
	}

	public void setCamBounds(){

		float effectiveViewportWidth = game.cam.viewportWidth * game.cam.zoom;
		float effectiveViewportHeight = game.cam.viewportHeight * game.cam.zoom;

		game.cam.zoom = MathUtils.clamp(game.cam.zoom, 0.75f, 1);
		game.cam.position.x = MathUtils.clamp(game.cam.position.x, effectiveViewportWidth / 2f,
				((puzzleGen.getPuzzleBorderCreator().getMaxX() + 1) * PolyNGonsDimConstants.PUZZLE_SCALE) - effectiveViewportWidth / 2f);
		game.cam.position.y = MathUtils.clamp(game.cam.position.y, effectiveViewportHeight / 2f,
				((puzzleGen.getPuzzleBorderCreator().getMaxY() + 1) * PolyNGonsDimConstants.PUZZLE_SCALE) - effectiveViewportHeight / 2f);
		game.cam.update();
	}

	@Override
	public void resize(int width, int height) {
		game.cam.viewportWidth = 800f;                 // Viewport of 30 units!
		game.cam.viewportHeight = 800f * height/width; // Lets keep things in proportion.
		game.cam.update();

		puzzleStage.getViewport().update(width, height, false);
		uiStage.getViewport().update(width, height, false);
	}

	public PuzzlePolygonManager getPpManager() {
		return ppManager;
	}

	public PolyNGonsUIManager getUiManager() {
		return uiManager;
	}

	public Stage getPuzzleStage() {
		return puzzleStage;
	}

	public PolyNGonGenerator getPuzzleGen() {
		return puzzleGen;
	}

	public GameSpriteCreator getGSpCreator() {
		return gSpCreator;
	}

	public PuzzleInputListener getpListener() {
		return pListener;
	}

	public TutorialUIManager gettUIManager() {
		return tUIManager;
	}

	public PolyNGonsSettings getSettings() {
		return settings;
	}

	public PuzzleCompletionManager getpCManager() {
		return pCManager;
	}

	public PuzzleChallengeDisplayer getpCDisplay() {
		return pCDisplay;
	}

	@Override
	public void show() {
		game.cam.update();
		uiCam.update();
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
		game.assetM.unload(game.assetID.get("cc1").fileName);
		game.assetM.unload(game.assetID.get("cc2").fileName);
		game.assetM.unload(game.assetID.get("c1").fileName);
		game.assetM.unload(game.assetID.get("c2").fileName);
		game.assetM.unload(game.assetID.get("flip1").fileName);
		game.assetM.unload(game.assetID.get("flip2").fileName);
		game.assetM.unload(game.assetID.get("removeLast1").fileName);
		game.assetM.unload(game.assetID.get("removeLast2").fileName);
		game.assetM.unload(game.assetID.get("menu1").fileName);
		game.assetM.unload(game.assetID.get("menu2").fileName);

		game.assetM.unload(game.assetID.get("fTriA1").fileName);
		game.assetM.unload(game.assetID.get("fTriA2").fileName);
		game.assetM.unload(game.assetID.get("fTriB1").fileName);
		game.assetM.unload(game.assetID.get("fTriB2").fileName);
		game.assetM.unload(game.assetID.get("fSq1").fileName);
		game.assetM.unload(game.assetID.get("fSq2").fileName);
		game.assetM.unload(game.assetID.get("fRec1").fileName);
		game.assetM.unload(game.assetID.get("fRec2").fileName);
		game.assetM.unload(game.assetID.get("fRhom1").fileName);
		game.assetM.unload(game.assetID.get("fRhom2").fileName);
		game.assetM.unload(game.assetID.get("fTrapA1").fileName);
		game.assetM.unload(game.assetID.get("fTrapA2").fileName);
		game.assetM.unload(game.assetID.get("fTrapB1").fileName);
		game.assetM.unload(game.assetID.get("fTrapB2").fileName);
		game.assetM.unload(game.assetID.get("fParaA1").fileName);
		game.assetM.unload(game.assetID.get("fParaA2").fileName);
		game.assetM.unload(game.assetID.get("fParaB1").fileName);
		game.assetM.unload(game.assetID.get("fParaB2").fileName);
		game.assetM.unload(game.assetID.get("fRTrapA1").fileName);
		game.assetM.unload(game.assetID.get("fRTrapA2").fileName);
		game.assetM.unload(game.assetID.get("fRTrapB1").fileName);
		game.assetM.unload(game.assetID.get("fRTrapB2").fileName);

		game.assetM.unload(game.assetID.get("arrow1").fileName);
		game.assetM.unload(game.assetID.get("arrow2").fileName);
		game.assetM.unload(game.assetID.get("arrow3").fileName);

		game.assetM.unload(game.assetID.get("cut").fileName);
		game.assetM.unload(game.assetID.get("notCut").fileName);

		game.assetM.unload(game.assetID.get("oneTria").fileName);
		game.assetM.unload(game.assetID.get("twoTria").fileName);
		game.assetM.unload(game.assetID.get("oneSq").fileName);
		game.assetM.unload(game.assetID.get("perpendSq").fileName);
		game.assetM.unload(game.assetID.get("threeSq").fileName);
		game.assetM.unload(game.assetID.get("turn").fileName);
		game.assetM.unload(game.assetID.get("emptySq").fileName);
		game.assetM.unload(game.assetID.get("polySpace").fileName);
		game.assetM.unload(game.assetID.get("star").fileName);

		game.assetM.unload(game.assetID.get("newDifficulty").fileName);
		game.assetM.unload(game.assetID.get("newTexture").fileName);
		game.assetM.unload(game.assetID.get("puzzleFailed").fileName);
		game.assetM.unload(game.assetID.get("puzzleSolved").fileName);

		game.assetM.unload(game.assetID.get("tipLayout").fileName);

		game.assetM.unload(game.soundID.get("invalid").fileName);
		game.assetM.unload(game.soundID.get("normal").fileName);
		game.assetM.unload(game.soundID.get("win").fileName);
		game.assetM.unload(game.soundID.get("blip").fileName);
		game.assetM.unload(game.soundID.get("new").fileName);
		if (game.assetM.containsAsset(game.soundID.get("ticking"))) 
			if (game.assetM.isLoaded(game.soundID.get("ticking").fileName)) game.assetM.unload(game.soundID.get("ticking").fileName);

		uiBatch.dispose();
		puzzleStage.dispose();
		uiStage.dispose();
		ppManager.dispose();
		pBordManager.dispose();

		pListener = null;
		settings = null;
		pCManager = null;
		puzzleGen = null;
		gSpCreator = null;
		ppManager = null;
		uiManager = null;
		pDOS = null;
		tUIManager = null;
		inGameMenu = null;
		pBordManager = null;
		pCDisplay = null;
	}
}
