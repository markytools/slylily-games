package com.connectcoins.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.connectcoins.audio.GameScreenAudioAssets;
import com.connectcoins.connection.ConnectionManager;
import com.connectcoins.functions.GameModeConfig;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen;
import com.connectcoins.game.GameScreen.GameState;
import com.connectcoins.utils.CustomActorGestureListener;

public class SpecialUI implements Disposable {
	private UIManager uiManager;
	private InGameMenu inGameMenu;
	private ConnectionManager cManager;
	private int playOnFireAnim = 0;
	private Animation<TextureRegion> onFireAnim;
	private ConnectCoins game;
	private GameScreen gScreen;
	private Button inGameButton;
	private CustomActorGestureListener listener;
	
	private boolean turnOnFireAnim = false;
	private float onFireStateTime = 0;
	private float onFireAlpha = 1;
	private float u2 = 0;
	
	private float heat = 0;
	
	private TextureRegion onFireBar1, onFireBar2, onFireBar3;
	private Animation<TextureRegion> onFireBarGlow;
	private float glowStateTime = 0;
	private long isOnFireTimer = -1;
	private long TOTAL_ON_FIRE_TIME = 10000;
	private TextureRegionDrawable onFireBarLayoutDraw, onFireBarLayout2Draw, onFireBarLayout3Draw, onFireBarLayout4Draw;
	private boolean ON_FIRE_MODE_ENABLED = true;
	
	public SpecialUI(GameScreen gScreen, UIManager uiManager, ConnectionManager cManager, InGameMenu inGameMenu){
		this.gScreen = gScreen;
		this.uiManager = uiManager;
		this.inGameMenu = inGameMenu;
		this.game = gScreen.game;
		this.cManager = cManager;

		Array<TextureRegion> onFireRegs = new Array<TextureRegion>();
		for (int i = 1; i <= 10; i++){
			onFireRegs.add(new TextureRegion(game.assetLoader.getTexture("onFireImg" + String.valueOf(i))));
		}
		
		onFireAnim = new Animation<TextureRegion>(.09f, onFireRegs);
		onFireAnim.setPlayMode(PlayMode.LOOP);
		
		initOnFireBar();
		resetAll();
	}
	
	private void initOnFireBar() {
		onFireBar1 = new TextureRegion(game.assetLoader.getTexture("onFireBar1"));
		onFireBar2 = new TextureRegion(game.assetLoader.getTexture("onFireBar2"));
		onFireBar3 = new TextureRegion(game.assetLoader.getTexture("onFireBar3"));

		Array<TextureRegion> onFireGlow = new Array<TextureRegion>();
		for (int i = 5; i >= 1; i--){
			onFireGlow.add(new TextureRegion(game.assetLoader.getTexture("onFireBarGlow" + i)));
		}
		onFireBarGlow = new Animation<TextureRegion>(.15f, onFireGlow);
		onFireBarGlow.setPlayMode(PlayMode.LOOP_PINGPONG);

		ButtonStyle iGBStyle = new ButtonStyle();
		onFireBarLayoutDraw = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("onFireBarLayout")));
		onFireBarLayout2Draw = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("onFireBarLayout2")));
		onFireBarLayout3Draw = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("onFireBarLayout3")));
		onFireBarLayout4Draw = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("onFireBarLayout4")));
		if (ON_FIRE_MODE_ENABLED){
			iGBStyle.up = onFireBarLayoutDraw;
			iGBStyle.down = onFireBarLayout2Draw;
		}
		else {
			iGBStyle.up = onFireBarLayout3Draw;
			iGBStyle.down = onFireBarLayout4Draw;
		}
		
		inGameButton = new Button(iGBStyle);
		listener = new CustomActorGestureListener(){
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (enableTouchUp && inGameMenu.isEnablePressedGameMenu()){
//					game.miscellaneous.playNormalBClick2();
					inGameMenu.turnInGameMenu(true);
				}
			}

			@Override
			public boolean longPress(Actor actor, float x, float y) {
				if (game.comboList.isCanOpen()){
//					game.miscellaneous.playNormalBClick2();
					inGameMenu.turnInGameMenu(true);
					inGameMenu.setInGameMenuOn(false);
					game.comboList.showComboList(GameState.NORMAL, GameState.IN_GAME_MENU, true, gScreen);
				}
				enableTouchUp = false;
				return super.longPress(actor, x, y);
			}
		};
		game.comboList.setListenerToReset(listener);
		inGameButton.addListener(listener);
		inGameButton.setUserObject(GameState.NORMAL);
		inGameButton.setBounds(0, 0, 1080, 160);
		inGameButton.setTouchable(Touchable.disabled);
		
		game.stage.addActor(inGameButton);
	}
	
	/**
	 * 
	 * @param points
	 * @return true if the point added results to ON FIRE, false otherwise.
	 */
	public boolean addHeatPoints(float points){
		if (ON_FIRE_MODE_ENABLED){
			if (isOnFireTimer == -1) heat += points;
			heat = MathUtils.clamp(heat, 0, 1);
			if (heat >= 1){
//				game.miscellaneous.vibrate(500);
				GameScreenAudioAssets.onFire.play();
				turnOnFireAnim = true;
				isOnFireTimer = TimeUtils.millis();

				return true;
			}
		}
		
		return false;
	}
	
	public void render(SpriteBatch batch){
		updateHeatBarImage();
		updateOnFireState();
		
		drawOnFireAnimation(batch);
	}

	private void updateHeatBarImage() {
		if (ON_FIRE_MODE_ENABLED){
			inGameButton.getStyle().up = onFireBarLayoutDraw;
			inGameButton.getStyle().down = onFireBarLayout2Draw;
		}
		else {
			inGameButton.getStyle().up = onFireBarLayout3Draw;
			inGameButton.getStyle().down = onFireBarLayout4Draw;
		}
	}

	private void updateOnFireState() {
		if (isOnFireTimer != -1){
			heat = 1 - (TimeUtils.millis() - isOnFireTimer) / (float)TOTAL_ON_FIRE_TIME;
			if (heat <= 0){
				cManager.setON_FIRE(false);
				isOnFireTimer = -1;
				heat = 0;
			}
		}
	}

	private void drawOnFireAnimation(SpriteBatch batch) {
		if (turnOnFireAnim){
			if (playOnFireAnim == 0){
				playOnFireAnim = 1;
				cManager.setON_FIRE(true);
			}
		}
		else playOnFireAnim = 0;
		onFireStateTime += Gdx.graphics.getDeltaTime();
		if (onFireStateTime > 10000000) onFireStateTime = 0;
		
		if (playOnFireAnim == 1){
			Color bColor = batch.getColor();
			batch.setColor(bColor.r, bColor.g, bColor.b, onFireAlpha);
			TextureRegion currentFrame = onFireAnim.getKeyFrame(onFireStateTime);
			currentFrame.setRegion(0, 0, 1f, 1f);
			u2 += 2f * Gdx.graphics.getDeltaTime(); 
			u2 = MathUtils.clamp(u2, 0, 1);
			currentFrame.setU2(u2);
			if (u2 == 1) onFireAlpha -= 2f * Gdx.graphics.getDeltaTime();
			batch.draw(currentFrame, 240, 650, u2 * 600, 300);
			if (onFireAlpha <= 0){
				u2 = 0;
				onFireAlpha = 1;
				playOnFireAnim = 0;
				turnOnFireAnim = false;
			}
			batch.setColor(bColor.r, bColor.g, bColor.b, 1);
		}
	}
	
	public void drawOnFireBarLayout(SpriteBatch batch){
		if (!uiManager.getGameReady().finishedReady()) inGameButton.setTouchable(Touchable.disabled);
		else inGameButton.setTouchable(Touchable.enabled);
		inGameButton.draw(batch, 1);
	}

	public void drawOnFireBar(SpriteBatch batch) {
		if (ON_FIRE_MODE_ENABLED){
			batch.draw(onFireBar1, 168, 30, 750, 80);
			
			if (isOnFireTimer == -1){
				game.gameBG.setFallSpeed(1);
				onFireBar2.setU2(heat);
				batch.draw(onFireBar2, 168, 30, 750 * heat, 80);
			}
			else {
				game.gameBG.setFallSpeed(2);
				glowStateTime += Gdx.graphics.getDeltaTime();
				onFireBar3.setU2(heat);
				batch.draw(onFireBar3, 168, 30, 750 * heat, 80);
				batch.draw(onFireBarGlow.getKeyFrame(glowStateTime), 168 + 720 * heat, 30, 34, 80);
			}
		}
	}

	public void setON_FIRE_MODE_ENABLED(boolean oN_FIRE_MODE_ENABLED) {
		this.ON_FIRE_MODE_ENABLED = oN_FIRE_MODE_ENABLED;
	}
	
	public void disableGameMenuButton(){
		inGameButton.setDisabled(true);
	}
	
	public void resetHeatBar(){
		heat = 0;
	}
	
	public void resetAll(){
		playOnFireAnim = 0;
		onFireStateTime = 0;
		onFireAlpha = 1;
		u2 = 0;
		heat = 0;
		glowStateTime = 0;
		isOnFireTimer = -1;
		
		listener.reset();
		inGameButton.setTouchable(Touchable.disabled);
		
		if (GameModeConfig.FIRED_UP) TOTAL_ON_FIRE_TIME = 12000;
		else TOTAL_ON_FIRE_TIME = 10000;
	}

	@Override
	public void dispose() {
		turnOnFireAnim = false;
		onFireStateTime = 0;
		onFireAlpha = 0;
		u2 = 0;
		heat = 0;
		playOnFireAnim = 0;
		uiManager = null;
		inGameMenu = null;
		cManager = null;
		onFireAnim = null;
		game = null;
		gScreen = null;
		inGameButton.remove();
		inGameButton = null;
		listener = null;
		onFireBar1 = null;
		onFireBar2 = null;
		onFireBar3 = null;
		onFireBarGlow = null;
		onFireBarLayoutDraw = null;
		onFireBarLayout2Draw = null;
		onFireBarLayout3Draw = null;
		onFireBarLayout4Draw = null;
	}
}
