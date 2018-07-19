package com.connectcoins.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen;
import com.connectcoins.game.GameScreen.GameState;
import com.connectcoins.languages.LanguageManager;
import com.connectcoins.tutorial.TutorialManager;
import com.connectcoins.utils.AnimatedButton;
import com.connectcoins.utils.ButtonAssets;
import com.connectcoins.utils.CustomActorGestureListener;
import com.connectcoins.utils.ScalableFontButton.Size;

public class ComboList {
	private ConnectCoins game;
	private boolean showComboList = false;
	private Object previousStageData;
	private ScrollPane comboListPane;
	private AnimatedButton backButton;
	private Texture upDownArrow, blackBG, scoreboard, tutorialText;
	private TutorialManager tutorialManager;
	private CustomActorGestureListener listenerToReset;
	private boolean canOpen;
	private GameScreen gScreen;

	public ComboList(ConnectCoins game){
		this.game = game;
		
		canOpen = true;
		upDownArrow = game.assetLoader.getTexture("upDownScrollArrow");
		blackBG = game.assetLoader.getTexture("blackBG");
		scoreboard = game.assetLoader.getTexture("scoreboard");
		initComboList();
	}
	
	public void initComboList(){
		
		if (comboListPane != null) comboListPane.remove();
		if (backButton != null) backButton.remove();
		comboListPane = null;
		backButton = null;
		
		Image comboListImg = new Image(new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("comboList"))));
		comboListImg.setSize(1080, 2599);
		Container<Image> imgContainer = new Container<Image>(comboListImg);
		imgContainer.size(1080, 2599);
		ScrollPaneStyle sStyle = new ScrollPaneStyle();
		sStyle.vScroll = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("cListVScroll")));
		sStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("cListVScrollKnob")));
		comboListPane = new ScrollPane(imgContainer, sStyle);
		comboListPane.setUserObject(GameState.COMBO_LIST);
		comboListPane.setBounds(0, 150, 1080, 1770);
		comboListPane.setOverscroll(false, false);
		
		ButtonStyle style1 = new ButtonStyle();
		style1.up = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));
		style1.down = new TextureRegionDrawable(new TextureRegion(ButtonAssets.largeButton.get(0)));

		backButton = new AnimatedButton(ButtonAssets.largeButton, style1, game.fManager.largeFont, 1.3f, 1.3f,
				"Back", Color.valueOf("281500"), Size.LARGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				close();
			}
			
			@Override
			public void draw(Batch batch, float parentAlpha) {
				batch.setColor(1, 1, 1, 1);
				super.draw(batch, parentAlpha);
			}
		};
		backButton.setBounds(190, 15, 700, 120);
		backButton.setUserObject(GameState.COMBO_LIST);
		backButton.setButtonIcons(game.buttonIconAssetManager, "back");
		
		game.stage.addActor(comboListPane);
		game.stage.addActor(backButton);
	}
	
	public void showComboList(Object previousStageData, Object actorsToHide, boolean enable){
		if (enable){
			if (canOpen){
				this.previousStageData = previousStageData;
				showComboList = enable;
				if (showComboList) comboListPane.setScrollPercentY(0);
				if (enable){
					if (gScreen != null) gScreen.setResetTutorialPane(true);
					ConnectCoins.setStageActorsVisible(game.stage, GameState.COMBO_LIST, actorsToHide);
				}
				else ConnectCoins.setStageActorsVisible(game.stage, this.previousStageData);
			}
		}
		else {
			this.previousStageData = previousStageData;
			showComboList = enable;
			if (showComboList) comboListPane.setScrollPercentY(0);
			if (enable){
				if (gScreen != null) gScreen.setResetTutorialPane(true);
				ConnectCoins.setStageActorsVisible(game.stage, GameState.COMBO_LIST, actorsToHide);
			}
			else ConnectCoins.setStageActorsVisible(game.stage, this.previousStageData);	
		}
	}

	public void showComboList(Object previousStageData, Object actorsToHide, boolean enable, GameScreen gScreen){
		if (enable){
			if (canOpen){
				this.previousStageData = previousStageData;
				showComboList = enable;
				if (showComboList) comboListPane.setScrollPercentY(0);
				if (enable){
					ConnectCoins.setStageActorsVisible(game.stage, GameState.COMBO_LIST, actorsToHide);
					if (gScreen != null) gScreen.setResetTutorialPane(true);
				}
				else ConnectCoins.setStageActorsVisible(game.stage, this.previousStageData);
			}
		}
		else {
			this.previousStageData = previousStageData;
			showComboList = enable;
			if (showComboList) comboListPane.setScrollPercentY(0);
			if (enable){
				ConnectCoins.setStageActorsVisible(game.stage, GameState.COMBO_LIST, actorsToHide);
				if (gScreen != null) gScreen.setResetTutorialPane(true);
			}
			else ConnectCoins.setStageActorsVisible(game.stage, this.previousStageData);
		}
	}
	
	public void render(SpriteBatch batch){
		if (showComboList){
			batch.draw(blackBG, -180, 0, 1440, 1920);
			batch.draw(upDownArrow, 10, 30, 100, 100);
		}
	}
	
	public void renderTutorialMsg(SpriteBatch batch){
		if (tutorialManager.getCurrentMsgNum() >= 13 && tutorialManager.getCurrentMsgNum() <= 16){
			comboListPane.setTouchable(Touchable.disabled);
			backButton.setTouchable(Touchable.disabled);
			
			batch.begin();
			batch.draw(blackBG, -180, 0, 1440, 1920);
			batch.draw(scoreboard, 0, 400, 1080, 1080);
			batch.draw(tutorialText, 0, 1277, 1080, 100);

			float y;
			String text;
			if (tutorialManager.getCurrentMsgNum() == 13) tutorialManager.nextMsg(13);
			text = tutorialManager.getCurrentMsg().getMessage();
			Color prevColor = game.fManager.largeFont4.getColor();
			BitmapFontCache cache = game.fManager.largeFont4.getCache();
			game.fManager.largeFont4.setColor(Color.valueOf("000000"));
//			cache.setWrappedText(text, 0, 0, 900);
			cache.setText(text, 0, 0, 900, Align.center, true);
			ConnectCoins.glyphLayout.setText(cache.getFont(), text, cache.getColor(), 900, Align.center, true);
			y = 1137 + ConnectCoins.glyphLayout.height / 2;
//			cache.setWrappedText(text, 92, y + 4, 900, Align.center);
			cache.setText(text, 92, y + 4, 900, Align.center, true);
			cache.draw(batch);
			game.fManager.largeFont4.setColor(prevColor);
			
			if (tutorialManager.getPrevButton().isVisible()) tutorialManager.getPrevButton().draw(batch, 1);
			if (tutorialManager.getNextButton().isVisible()) tutorialManager.getNextButton().draw(batch, 1);
			batch.end();
		}
		else {
			comboListPane.setTouchable(Touchable.enabled);
			backButton.setTouchable(Touchable.enabled);
		}
	}

	public AnimatedButton getBackButton() {
		return backButton;
	}

	public ScrollPane getComboListPane() {
		return comboListPane;
	}

	public void setgScreen(GameScreen gScreen) {
		this.tutorialManager = gScreen.getTutorialManager();
		tutorialText = game.assetLoader.getTexture("tutorialText");
	}
	
	public void canOpenComboList(boolean canOpen){
		this.canOpen = canOpen;
	}
	
	public void close(){
		if (listenerToReset != null) listenerToReset.setEnableTouchUp(true);
		showComboList(previousStageData, null, false);
	}

	public void setGameScreenForTutorial(GameScreen gScreen){
		this.gScreen = gScreen;
	}

	public boolean isShowComboList() {
		return showComboList;
	}
	
	public boolean isCanOpen() {
		return canOpen;
	}
	
	public void deleteListenerForReset(){
		listenerToReset = null;
	}

	public void setListenerToReset(CustomActorGestureListener listenerToReset) {
		this.listenerToReset = listenerToReset;
	}
}
