package com.connectcoins.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen;
import com.connectcoins.languages.LanguageManager;
import com.connectcoins.mainMenu.MainMenuScreen.MainMenuScreenState;
import com.connectcoins.utils.AnimatedButton;
import com.connectcoins.utils.ButtonAssets;
import com.connectcoins.utils.ScalableFontButton.Size;

public class Challenges implements Disposable {

	public enum ChallengesState {
		NORMAL, PUZZLE_SELECTION, CHALLENGE_1, CHALLENGE_2, CHALLENGE_3, CHALLENGE_4, CHALLENGE_5
	}

	public ChallengesState cState;

	private ConnectCoins game;
	private MainMenuScreen mMScreen;

	private ChallengeAssets cAssets;
	private TextureRegion challengesReg;
	private AnimatedButton backButton, backButton2;

	private Rectangle levelLayout;
	private Array<AnimatedButton> challenge1Levels, challenge2Levels, challenge3Levels, challenge4Levels, challenge5Levels;
	private AnimatedButton challenge1, challenge2, challenge3, challenge4, challenge5;
	private Array<ChallengeAnimatedButton> challengeAButtons;

	private boolean enablePan = true;
	private boolean notPanned = true;
	private boolean challengeClicked = false;
	private float swipeAlpha = 1;

	private ActorGestureListener stageListener;
	private TextureRegion swipeScreenReg;
	
	private float currentTargetX;
	private float lastCurrentTargetXSet;

	public Challenges(ConnectCoins game, MainMenuScreen mMScreen){
		this.game = game;
		this.mMScreen = mMScreen;
		cState = ChallengesState.NORMAL;
		swipeScreenReg = new TextureRegion(game.assetLoader.getTexture("swipeScreen"));
		challengeAButtons = new Array<ChallengeAnimatedButton>();

		challengesReg = new TextureRegion(game.assetLoader.getTexture("challenges"));
		challenge1Levels = new Array<AnimatedButton>();
		challenge2Levels = new Array<AnimatedButton>();
		challenge3Levels = new Array<AnimatedButton>();
		challenge4Levels = new Array<AnimatedButton>();
		challenge5Levels = new Array<AnimatedButton>();

		cAssets = new ChallengeAssets(game);
	}

	public void initAssets() {

		enablePan = true;
		notPanned = true;
		challengeClicked = false;
		swipeAlpha = 1;
		challengeAButtons.clear();
		challenge1Levels.clear();
		challenge2Levels.clear();
		challenge3Levels.clear();
		challenge4Levels.clear();
		challenge5Levels.clear();
		
		initButtons();
		initLevelButtons();
	}

	private void initButtons() {
		ButtonStyle style = new ButtonStyle();
		style.disabled = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("largeButtonDisabled")));
		style.up = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));
		style.down = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));

		challenge1 = new AnimatedButton(ButtonAssets.largeButton, style, game.fManager.largeFont, 1.3f, 1.3f,
				"Challenge 1", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				backButton2.setUserObject(ChallengesState.CHALLENGE_1);
				mMScreen.setStageDrawings(ChallengesState.CHALLENGE_1);
				cState = ChallengesState.CHALLENGE_1;
				levelLayout.x = game.pUpdater.getPValue().getFloat("challenge1X");
				swipeAlpha = 1;
				notPanned = true;
				lastCurrentTargetXSet = game.pUpdater.getChallengeX(1);
				currentTargetX = lastCurrentTargetXSet;
			}
		};
		challenge1.setBounds(190, 1100, 700, 120);
		challenge1.setUserObject(ChallengesState.NORMAL);
		challenge1.setButtonIcons(game.buttonIconAssetManager, "challenge");

		challenge2 = new AnimatedButton(ButtonAssets.largeButton, style, game.fManager.largeFont, 1.3f, 1.3f,
				"Challenge 2", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				backButton2.setUserObject(ChallengesState.CHALLENGE_2);
				mMScreen.setStageDrawings(ChallengesState.CHALLENGE_2);
				cState = ChallengesState.CHALLENGE_2;
				levelLayout.x = game.pUpdater.getPValue().getFloat("challenge2X");
				swipeAlpha = 1;
				notPanned = true;
				lastCurrentTargetXSet = game.pUpdater.getChallengeX(2);
				currentTargetX = lastCurrentTargetXSet;
			}
		};
		challenge2.setBounds(190, 960, 700, 120);
		challenge2.setUserObject(ChallengesState.NORMAL);
		challenge2.setButtonIcons(game.buttonIconAssetManager, "challenge");

		challenge3 = new AnimatedButton(ButtonAssets.largeButton, style, game.fManager.largeFont, 1.3f, 1.3f,
				"Challenge 3", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				backButton2.setUserObject(ChallengesState.CHALLENGE_3);
				mMScreen.setStageDrawings(ChallengesState.CHALLENGE_3);
				cState = ChallengesState.CHALLENGE_3;
				levelLayout.x = game.pUpdater.getPValue().getFloat("challenge3X");
				swipeAlpha = 1;
				notPanned = true;
				lastCurrentTargetXSet = game.pUpdater.getChallengeX(3);
				currentTargetX = lastCurrentTargetXSet;
			}
		};
		challenge3.setBounds(190, 820, 700, 120);
		challenge3.setUserObject(ChallengesState.NORMAL);
		challenge3.setButtonIcons(game.buttonIconAssetManager, "challenge");

		challenge4 = new AnimatedButton(ButtonAssets.largeButton, style, game.fManager.largeFont, 1.3f, 1.3f,
				"Challenge 4", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				backButton2.setUserObject(ChallengesState.CHALLENGE_4);
				mMScreen.setStageDrawings(ChallengesState.CHALLENGE_4);
				cState = ChallengesState.CHALLENGE_4;
				levelLayout.x = game.pUpdater.getPValue().getFloat("challenge4X");
				swipeAlpha = 1;
				notPanned = true;
				lastCurrentTargetXSet = game.pUpdater.getChallengeX(4);
				currentTargetX = lastCurrentTargetXSet;
			}
		};
		challenge4.setBounds(190, 680, 700, 120);
		challenge4.setUserObject(ChallengesState.NORMAL);
		challenge4.setButtonIcons(game.buttonIconAssetManager, "challenge");

		challenge5 = new AnimatedButton(ButtonAssets.largeButton, style, game.fManager.largeFont, 1.3f, 1.3f,
				"Challenge 5", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				backButton2.setUserObject(ChallengesState.CHALLENGE_5);
				mMScreen.setStageDrawings(ChallengesState.CHALLENGE_5);
				cState = ChallengesState.CHALLENGE_5;
				levelLayout.x = game.pUpdater.getPValue().getFloat("challenge5X");
				swipeAlpha = 1;
				notPanned = true;
				lastCurrentTargetXSet = game.pUpdater.getChallengeX(5);
				currentTargetX = lastCurrentTargetXSet;
			}
		};
		challenge5.setBounds(190, 540, 700, 120);
		challenge5.setUserObject(ChallengesState.NORMAL);
		challenge5.setButtonIcons(game.buttonIconAssetManager, "challenge");

		if (!game.pUpdater.challengeUnlocked(1)) challenge1.setDisabled(true);
		if (!game.pUpdater.challengeUnlocked(2)) challenge2.setDisabled(true);
		if (!game.pUpdater.challengeUnlocked(3)) challenge3.setDisabled(true);
		if (!game.pUpdater.challengeUnlocked(4)) challenge4.setDisabled(true);
		if (!game.pUpdater.challengeUnlocked(5)) challenge5.setDisabled(true);

		ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable(new TextureRegion(ButtonAssets.averageButton.get(0)));
		style2.down = new TextureRegionDrawable(new TextureRegion(ButtonAssets.averageButton.get(0)));

		backButton = new AnimatedButton(ButtonAssets.averageButton, style2, game.fManager.largeFont, 1.1f, 1.1f,
				"Back", Color.valueOf("281500"), Size.AVERAGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				unload();
				mMScreen.setStageDrawings(MainMenuScreenState.NORMAL);
				mMScreen.mMSState = MainMenuScreenState.NORMAL;
			}
		};
		backButton.setBounds(20, 20, 500, 100);
		backButton.setUserObject(ChallengesState.NORMAL);
		backButton.setButtonIcons(game.buttonIconAssetManager, "back");

		backButton2 = new AnimatedButton(ButtonAssets.averageButton, style2, game.fManager.largeFont, 1.1f, 1.1f,
				"Back", Color.valueOf("281500"), Size.AVERAGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				mMScreen.setStageDrawings(ChallengesState.NORMAL);
				cState = ChallengesState.NORMAL;
			}
		};
		backButton2.setBounds(20, 20, 500, 100);
		backButton2.setButtonIcons(game.buttonIconAssetManager, "back");

		game.stage.addActor(challenge1);
		game.stage.addActor(challenge2);
		game.stage.addActor(challenge3);
		game.stage.addActor(challenge4);
		game.stage.addActor(challenge5);
		game.stage.addActor(backButton);
		game.stage.addActor(backButton2);
	}

	private void initLevelButtons() {
		levelLayout = new Rectangle(0, 0, 5400, 5400);
		currentTargetX = levelLayout.x;

		ButtonStyle solveStyle = new ButtonStyle();
		solveStyle.up = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.solved.get(0)));
		solveStyle.down = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.solved.get(0)));

		ButtonStyle challenge1Style = new ButtonStyle();
		challenge1Style.up = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge1.get(0)));
		challenge1Style.down = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge1.get(0)));

		ButtonStyle challenge2Style = new ButtonStyle();
		challenge2Style.up = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge2.get(0)));
		challenge2Style.down = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge2.get(0)));

		ButtonStyle challenge3Style = new ButtonStyle();
		challenge3Style.up = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge3.get(0)));
		challenge3Style.down = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge3.get(0)));

		ButtonStyle challenge4Style = new ButtonStyle();
		challenge4Style.up = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge4.get(0)));
		challenge4Style.down = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge4.get(0)));

		ButtonStyle challenge5Style = new ButtonStyle();
		challenge5Style.up = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge5.get(0)));
		challenge5Style.down = new TextureRegionDrawable(new TextureRegion(ChallengeAssets.challenge5.get(0)));

		for (int i = 0; i < 5; i++){
			final int challengeNum = i + 1;
			ChallengesState currentCState = null;
			Array<AnimatedButton> animButton = null;
			Array<TextureRegion> levelRegs = null;
			ButtonStyle bStyle = null;
			switch (i){
			case 0: {
				currentCState = ChallengesState.CHALLENGE_1;
				animButton = challenge1Levels;
				levelRegs = ChallengeAssets.challenge1;
				bStyle = challenge1Style;
			}; break;
			case 1: {
				currentCState = ChallengesState.CHALLENGE_2;
				animButton = challenge2Levels;
				levelRegs = ChallengeAssets.challenge2;
				bStyle = challenge2Style;
			}; break;
			case 2: {
				currentCState = ChallengesState.CHALLENGE_3;
				animButton = challenge3Levels;
				levelRegs = ChallengeAssets.challenge3;
				bStyle = challenge3Style;
			}; break;
			case 3: {
				currentCState = ChallengesState.CHALLENGE_4;
				animButton = challenge4Levels;
				levelRegs = ChallengeAssets.challenge4;
				bStyle = challenge4Style;
			}; break;
			case 4: {
				currentCState = ChallengesState.CHALLENGE_5;
				animButton = challenge5Levels;
				levelRegs = ChallengeAssets.challenge5;
				bStyle = challenge5Style;
			}; break;
			default: break;
			}

			for (int i2 = 0; i2 < 100; i2++){
				final int levelNum = i2 + 1;
				int isSolved = game.pUpdater.getPValue().get("challenge" + String.valueOf(i + 1)).asByteArray()[i2];
				Array<TextureRegion> currentLevelRegs = (isSolved == 1) ? ChallengeAssets.solved : levelRegs;
				ButtonStyle currentBStyle = (isSolved == 1) ? solveStyle : bStyle;
				ChallengeAnimatedButton levelButton = new ChallengeAnimatedButton(game, currentLevelRegs, currentBStyle,
						game.fManager.largeFont, 1.7f, 1.7f, String.valueOf(i2 + 1), Color.valueOf("362513"), Size.VERY_SMALL,
						LanguageManager.button_CURRENT, null, i2) {
					@Override
					public void onAnimationEnd() {
						Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {
								if (notPanned){
									enablePan = false;
									challengeClicked = true;
									game.gMConfig.mode = GameMode.CHALLENGE;
									game.gMConfig.challengeNum = challengeNum;
									game.gMConfig.levelNum = levelNum;
									
									Gdx.app.postRunnable(new Runnable() {  
										@Override  
										public void run () {  
											game.setScreen(new GameScreen(game));
										}  
									});
								}
								else {
									notPanned = true;
									reset();
								}
							}
						});
						
					}

					@Override
					public void act(float delta) {
						if (!notPanned){
							reset();
						}
						super.act(delta);
						setTargetX(currentTargetX);
					}

					@Override
					public void draw(Batch batch, float parentAlpha) {
						float x = getX();
						if (x >= -180 && x < 1044) super.draw(batch, parentAlpha);
					}
				};

				challengeAButtons.add(levelButton);
				animButton.add(levelButton);
				levelButton.setUserObject(currentCState);

				game.stage.addActor(levelButton);
			}
		}

		game.stage.addListener(stageListener = new ActorGestureListener(){
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (game.touchPos.y >= 432 && game.touchPos.y < 1296){
					if (enablePan){
						notPanned = false;
						if (deltaX < 0){
							if (levelLayout.x + deltaX < -4320) levelLayout.x = -4320;
							else levelLayout.x += deltaX;
						}
						else if (deltaX > 0){
							if (levelLayout.x + deltaX > 0) levelLayout.x = 0;
							else levelLayout.x += deltaX;
						}
					}
					game.cam.update();
				}
			}
		});
	}

	public void render(SpriteBatch batch){
		batch.draw(challengesReg, 0, 1300, 1080, 500);
		drawSwipeScreenAnim(batch);
	}

	private void drawSwipeScreenAnim(SpriteBatch batch) {
		if (cState == ChallengesState.CHALLENGE_1 || cState == ChallengesState.CHALLENGE_2 ||
				cState == ChallengesState.CHALLENGE_3 || cState == ChallengesState.CHALLENGE_4 ||
				cState == ChallengesState.CHALLENGE_5){

			Color color = batch.getColor();

			float alpha2, alpha3;
			if (swipeAlpha - .3f < 0) alpha2 = swipeAlpha + 1 - .3f;
			else alpha2 = swipeAlpha - .3f;
			if (swipeAlpha - .6f < 0) alpha3 = swipeAlpha + 1 - .6f;
			else alpha3 = swipeAlpha - .6f;

			float alpha = Gdx.graphics.getDeltaTime();
			if (swipeAlpha - alpha < 0) swipeAlpha = 1;
			else swipeAlpha -= alpha;

			//			Color alphaColor = Color.valueOf("362513");
			//			alphaColor.a = alpha3;
			game.fManager.drawFont(game.fManager.largeFont2, batch, Color.valueOf("362513"), 1.6f, 1.6f, "Swipe", 445, 427,
					200, Align.center);

			batch.setColor(color.r, color.g, color.b, swipeAlpha);
			batch.draw(swipeScreenReg, 146, 300, -100, 100);
			batch.setColor(color.r, color.g, color.b, alpha2);
			batch.draw(swipeScreenReg, 246, 300, -100, 100);
			batch.setColor(color.r, color.g, color.b, alpha3);
			batch.draw(swipeScreenReg, 346, 300, -100, 100);
			batch.setColor(color.r, color.g, color.b, alpha3);
			batch.draw(swipeScreenReg, 746, 300, 100, 100);
			batch.setColor(color.r, color.g, color.b, alpha2);
			batch.draw(swipeScreenReg, 846, 300, 100, 100);
			batch.setColor(color.r, color.g, color.b, swipeAlpha);
			batch.draw(swipeScreenReg, 946, 300, 100, 100);
			batch.setColor(color.r, color.g, color.b, 1);
		}
//		else {
//			mMScreen.setStageDrawings(ChallengesState.NORMAL);
//		}
	}

	public void updateScene(){
		switch (cState){
		case CHALLENGE_1: {
			for (int i = 0; i < 100; i++){
				float recAlignX = 1080 * (i / 20);
				float recPosX = ((i % 20) % 5) * 216;
				float recAlignY = ((i % 20) / 5) * 216;
				challenge1Levels.get(i).setBounds(levelLayout.x + recAlignX + recPosX, levelLayout.y + (1080 - (recAlignY)),
						216, 216);
			}
		}; break;
		case CHALLENGE_2: {
			for (int i = 0; i < 100; i++){
				float recAlignX = 1080 * (i / 20);
				float recPosX = ((i % 20) % 5) * 216;
				float recAlignY = ((i % 20) / 5) * 216;
				challenge2Levels.get(i).setBounds(levelLayout.x + recAlignX + recPosX, levelLayout.y + (1080 - (recAlignY)),
						216, 216);
			}
		}; break;
		case CHALLENGE_3: {
			for (int i = 0; i < 100; i++){
				float recAlignX = 1080 * (i / 20);
				float recPosX = ((i % 20) % 5) * 216;
				float recAlignY = ((i % 20) / 5) * 216;
				challenge3Levels.get(i).setBounds(levelLayout.x + recAlignX + recPosX, levelLayout.y + (1080 - (recAlignY)),
						216, 216);
			}
		}; break;
		case CHALLENGE_4: {
			for (int i = 0; i < 100; i++){
				float recAlignX = 1080 * (i / 20);
				float recPosX = ((i % 20) % 5) * 216;
				float recAlignY = ((i % 20) / 5) * 216;
				challenge4Levels.get(i).setBounds(levelLayout.x + recAlignX + recPosX, levelLayout.y + (1080 - (recAlignY)),
						216, 216);
			}
		}; break;
		case CHALLENGE_5: {
			for (int i = 0; i < 100; i++){
				float recAlignX = 1080 * (i / 20);
				float recPosX = ((i % 20) % 5) * 216;
				float recAlignY = ((i % 20) / 5) * 216;
				//				float recPosY = ((i % 20) % 5) * 40;
				challenge5Levels.get(i).setBounds(levelLayout.x + recAlignX + recPosX, levelLayout.y + (1080 - (recAlignY)),
						216, 216);
			}
		}; break;
		default: break;
		}

		panLevelDisplay();
	}

	private void panLevelDisplay() {
		if (cState == ChallengesState.CHALLENGE_1 || cState == ChallengesState.CHALLENGE_2 ||
				cState == ChallengesState.CHALLENGE_3 || cState == ChallengesState.CHALLENGE_4 ||
				cState == ChallengesState.CHALLENGE_5){
			if (!stageListener.getGestureDetector().isPanning() && !challengeClicked){
				notPanned = true;
				stageListener.getGestureDetector().reset();

				int challengeNum = 0;
				switch (cState){
				case CHALLENGE_1: challengeNum = 1; break;
				case CHALLENGE_2: challengeNum = 2; break;
				case CHALLENGE_3: challengeNum = 3; break;
				case CHALLENGE_4: challengeNum = 4; break;
				case CHALLENGE_5: challengeNum = 5; break;
				default: break;
				}
				alignRecX(challengeNum);
			}
		}

		levelLayout.x = MathUtils.clamp(levelLayout.x, -4320, 0);
	}

	private void alignRecX(int challengeNum){
		float moveX = 3000 * Gdx.graphics.getDeltaTime();
		float challengeX = lastCurrentTargetXSet;

		if (challengeX == -4320){
			if (levelLayout.x >= -4320 && levelLayout.x < -3960){
				if (levelLayout.x >= -4320) moveLayout(challengeNum, -moveX, -4320);
				else moveLayout(challengeNum, moveX, -4320);
			}
			else if (levelLayout.x >= -3960){
				moveLayout(challengeNum, moveX, -3240);
			}
		}
		else if (challengeX == -3240){
			if (levelLayout.x >= -3600 && levelLayout.x < -2880){
				if (levelLayout.x >= -3240) moveLayout(challengeNum, -moveX, -3240);
				else moveLayout(challengeNum, moveX, -3240);
			}
			else if (levelLayout.x < -3600){
				moveLayout(challengeNum, -moveX, -4320);
			}
			else if (levelLayout.x >= -2880){
				moveLayout(challengeNum, moveX, -2160);
			}
		}
		else if (challengeX == -2160){
			if (levelLayout.x >= -2520 && levelLayout.x < -1800){
				if (levelLayout.x >= -2160) moveLayout(challengeNum, -moveX, -2160);
				else moveLayout(challengeNum, moveX, -2160);
			}
			else if (levelLayout.x < -2520){
				moveLayout(challengeNum, -moveX, -3240);
			}
			else if (levelLayout.x >= -1800){
				moveLayout(challengeNum, moveX, -1080);
			}
		}
		else if (challengeX == -1080){
			if (levelLayout.x >= -1440 && levelLayout.x < -720){
				if (levelLayout.x >= -1080) moveLayout(challengeNum, -moveX, -1080);
				else moveLayout(challengeNum, moveX, -1080);
			}
			else if (levelLayout.x < -1440){
				moveLayout(challengeNum, -moveX, -2160);
			}
			else if (levelLayout.x >= -720){
				moveLayout(challengeNum, moveX, -0);
			}
		}
		else if (challengeX == -0){
			if (levelLayout.x >= -360 && levelLayout.x < -0){
				if (levelLayout.x >= 0) moveLayout(challengeNum, -moveX, -0);
				else moveLayout(challengeNum, moveX, -0);
			}
			else if (levelLayout.x < -360){
				moveLayout(challengeNum, -moveX, -1080);
			}
		}
	}

	private void moveLayout(int challengeNum, float moveX, int challengeX){
		currentTargetX = challengeX;
		if (moveX < 0){
			if (levelLayout.x + moveX < currentTargetX) levelLayout.x = currentTargetX;
			else levelLayout.x += moveX;
		}
		else {
			if (levelLayout.x + moveX > currentTargetX) levelLayout.x = currentTargetX;
			else levelLayout.x += moveX;
		}
		if (levelLayout.x == currentTargetX){
			if (lastCurrentTargetXSet != currentTargetX){
				lastCurrentTargetXSet = currentTargetX;
				game.pUpdater.setChallengeX(challengeNum, lastCurrentTargetXSet);
			}
		}
	}
	
	private void unload(){
		challenge1.remove();
		challenge2.remove();
		challenge3.remove();
		challenge4.remove();
		challenge5.remove();
		backButton.remove();
		backButton2.remove();
		
		for (ChallengeAnimatedButton cAB : challengeAButtons){
			cAB.remove();
		}
		challengeAButtons.clear();
	}

	@Override
	public void dispose(){
		cState = null;
		game = null;
		mMScreen = null;
		cAssets.dispose();
		cAssets = null;
		challengesReg = null;
		backButton = null;
		backButton2 = null;
		levelLayout = null;
		for (AnimatedButton aB : challenge1Levels){
			aB.dispose();
			aB = null;
		}
		for (AnimatedButton aB : challenge2Levels){
			aB.dispose();
			aB = null;
		}
		for (AnimatedButton aB : challenge3Levels){
			aB.dispose();
			aB = null;
		}
		for (AnimatedButton aB : challenge4Levels){
			aB.dispose();
			aB = null;
		}
		for (AnimatedButton aB : challenge5Levels){
			aB.dispose();
			aB = null;
		}
		challenge1Levels.clear();
		challenge2Levels.clear();
		challenge3Levels.clear();
		challenge4Levels.clear();
		challenge5Levels.clear();
		challenge1Levels = null;
		challenge2Levels = null;
		challenge3Levels = null;
		challenge4Levels = null;
		challenge5Levels = null;
		enablePan = false;
		notPanned = false;
		challengeClicked = false;
		swipeAlpha = 0;
		stageListener = null;
		currentTargetX = 0;
		lastCurrentTargetXSet = 0;
	}
}