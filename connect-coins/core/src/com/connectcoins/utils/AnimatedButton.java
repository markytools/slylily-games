package com.connectcoins.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.audio.MusicPlayer;
import com.connectcoins.languages.LanguageManager;
import com.connectcoins.mainMenu.ButtonIconAssetManager;

public abstract class AnimatedButton extends ScalableFontButton implements Disposable {
	public abstract void onAnimationEnd();

	private Animation<TextureRegion> buttomAnimation;
	private boolean isClicked = false;
	private boolean newScreenToggled = false;
	private float stateTime = 0;
	private Array<Actor> otherActors;
	private TextureRegion buttonIcon, buttonIconBW;
	private String currentLanguage, englishText;
	private String debugText;
	private boolean playSound = false;

	private boolean createNewScreen;

	public AnimatedButton(Array<? extends TextureRegion> buttonRegions, ButtonStyle style, BitmapFont font, float scaleX,
			float scaleY, String englishText, Color fontColor, Size size, ArrayMap<String, String> textPack, String currentLanguage,
			final boolean createNewScreen, final boolean playSound) {
		super(style, font, scaleX, scaleY, ((textPack.get(englishText) != null) ? textPack.get(englishText) : englishText),
				fontColor, size);
		this.createNewScreen = createNewScreen;
		this.playSound = playSound;
		this.currentLanguage = currentLanguage;
		this.englishText = englishText;
		updateTextScale(currentLanguage);

		otherActors = new Array<Actor>();
		buttomAnimation = new Animation<TextureRegion>(0.015f, buttonRegions);
		buttomAnimation.setPlayMode(PlayMode.NORMAL);
		addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (multiplayerMessage != null && multiplayerMessage.getmMScreen() != null){
//					multiplayerMessage.getmMScreen().extendFriendRequestTimer();
//				}
			}
		});
		ClickListener clickListener;
		addListener(clickListener = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isDisabled() && !isClicked && !newScreenToggled){
					Stage stage = getStage();
					for (Actor actor : stage.getActors()){
						if (actor.isTouchable()){
							actor.setTouchable(Touchable.disabled);
							otherActors.add(actor);
						}
					}
					if (playSound) MusicPlayer.glitter.play();
					isClicked = true;
					if (createNewScreen) newScreenToggled = true;
				}
			}
		});
		clickListener.setTapCountInterval(.5f);
	}
	
	public void exempt(){
		
	}

	protected void createClickListener(){
		ClickListener clickListener;
		addListener(clickListener = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				buttonClicked();
			}
		});
		clickListener.setTapCountInterval(.5f);
	}
	
	protected void buttonClicked(){
		if (!isDisabled() && !isClicked && !newScreenToggled){
			Stage stage = getStage();
			if (debugText != null) System.out.println(debugText);
			for (Actor actor : stage.getActors()){
				if (actor.isTouchable()){
					actor.setTouchable(Touchable.disabled);
					otherActors.add(actor);
				}
			}
			if (playSound) MusicPlayer.glitter.play();
			isClicked = true;
			if (createNewScreen) newScreenToggled = true;
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isClicked) stateTime += Gdx.graphics.getDeltaTime();
		boolean drawSuper = (isDisabled()) ? true : false;
		super.setDrawSuper(drawSuper);
		validate();
		if (!drawSuper) batch.draw(buttomAnimation.getKeyFrame(stateTime), getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
		
		TextureRegion animButtonIcon;
		if (!isDisabled()){
			animButtonIcon = buttonIcon;
		}
		else animButtonIcon = buttonIconBW;
		if (buttonIcon != null){
			switch (size){
			case AVERAGE: {
				batch.draw(animButtonIcon, getX() + 10, getY() + 14, 75, 75);
			}; break;
			case LARGE: {
				batch.draw(animButtonIcon, getX() + 10, getY() + 12, 100, 100);
			}; break;
			case VERY_SMALL: {

			}; break;
			default: break;
			}
		}
	}

	@Override
	public void act(float delta) {
//		if (multiplayerMessage != null){
//			if (multiplayerMessage.noPrevUntouchableActors() && multiplayerMessage.hasNoMessageNotifs()) actButton();
//		}
//		else {
			actButton();
//		}
	}
	
	public void actButton(){
		String currentLanguage = LanguageManager.currentLanguage;
		if (this.currentLanguage != null && this.currentLanguage != currentLanguage){
			this.currentLanguage = currentLanguage;
			updateTextScale(currentLanguage);
		}
		if (isClicked && buttomAnimation.isAnimationFinished(stateTime)){
			isClicked = false;
			stateTime = 0;
			onPostAnimationEnd();
			onAnimationEnd();
		}
		else clearActions();
	}

	private void updateTextScale(String language){
		if (language != null){
			text = LanguageManager.button_CURRENT.get(englishText);
			switch (language){
			case LanguageManager.ENGLISH: {
				verySmallFontScale.setData(0, 0, -5, -27);
				averageFontScale.setData(-.1f, 0, 25, -22);
				largeFontScale.setData(-.02f, 0, 50, -15);
				
//				ORIGINAL
//				verySmallFontScale.setData(0, 0, -5, -27);
//				averageFontScale.setData(0, 0, 3, -22);
//				largeFontScale.setData(0, 0, 10, -15);
			}; break;
			default: break;
			}
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && createNewScreen){
			newScreenToggled = false;
		}
	}

	/**
	 * This reset is different!
	 */
	@Override
	public void reset(){
		reset(false);
	}

	/**
	 * 
	 * @param newScreenToggled default is false
	 */
	public void reset(boolean resetNewScreenToggled){
		if (resetNewScreenToggled) this.newScreenToggled = false;
		for (Actor actor : otherActors){
			actor.setTouchable(Touchable.enabled);
		}
		otherActors.clear();
		isClicked = false;
		stateTime = 0;
	}

	private void onPostAnimationEnd() {
		for (Actor actor : otherActors){
			actor.setTouchable(Touchable.enabled);
		}
		otherActors.clear();
		isClicked = false;
		stateTime = 0;
	}

	public void setButtonIcons(ButtonIconAssetManager bIconAssetManager, String region) {
		this.buttonIcon = bIconAssetManager.buttonIconsAtlas.findRegion(region);
		this.buttonIconBW = bIconAssetManager.buttonIconsAtlasBW.findRegion(region);
	}

	public String getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}
	
	public void setDebugText(String debugText) {
		this.debugText = debugText;
	}

	@Override
	public void dispose() {
		buttomAnimation = null;
		isClicked = false;
		newScreenToggled = false;
		stateTime = 0;
		for (Actor actor : otherActors){
			actor.remove();
			actor = null;
		}
		otherActors.clear();
		otherActors = null;
		buttonIcon = null;
		buttonIconBW = null;
		currentLanguage = null;
		englishText = null;
		debugText = null;
		createNewScreen = false;
	}
}
