package com.polyNGonsFunctions.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gameTools.game.MySprite;
import com.gameTools.game.ScalableFontLabel;
import com.gameTools.game.ShadedBitmapFont;
import com.polyngons.game.GameScreen;
import com.polyngons.game.PolyNGons;
import com.polyNgonConstants.game.PolyNGonsGameSelection;
import com.polyNgonConstants.game.PolyNGonsMarker;
import com.polygons.game.Polygon;

public class PolyNGonsSettings {
	private GameScreen gScreen;
	private PolyNGons game;
	private CheckBox cBSound, cBHighlight;
	private Button back, hand, square, circle;
	private ScalableFontLabel sounds, tips;
	private boolean toggleSettings;
	private Stage settingStage;
	private ShadedBitmapFont font;

	private MarkerAssetCreator mAssetCreator;
	private String currentMarker;
	private MySprite currentMarkerSp;
	private float pgClickerModifier;
	private boolean forward;

	private String defaultMarker = PolyNGonsMarker.SQUARE;

	public PolyNGonsSettings(final GameScreen gScreen, PolyNGons game){
		this.gScreen = gScreen;
		this.game = game;

		toggleSettings = false;
		final Preferences save = game.getUser();
		if (!save.contains("sounds")) save.putBoolean("sounds", true);
		if (!save.contains("pHighlight")) save.putBoolean("pHighlight", true);
		if (!save.contains("marker")) save.putString("marker", defaultMarker);
		save.flush();

		currentMarkerSp = new MySprite(game.assetM.get(game.assetID.get("squareW")));
		currentMarkerSp.setScale(.5f);
		mAssetCreator = new MarkerAssetCreator();
		currentMarker = mAssetCreator.getMarkerKey(save.getString("marker"));

		CheckBoxStyle style1 = new CheckBoxStyle();
		style1.checkboxOff = new TextureRegionDrawable(new TextureRegion(
				game.assetM.get(game.assetID.get("checkbox1"))));
		style1.checkboxOn = new TextureRegionDrawable(new TextureRegion(
				game.assetM.get(game.assetID.get("checkbox2"))));
		style1.font = game.bLayout.getCustomFont();
		style1.fontColor = Color.WHITE;
		style1.checkedFontColor = Color.ORANGE;

		cBSound = new CheckBox(null, style1);
		cBSound.setPosition(280, 325);
		cBSound.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				save.putBoolean ("sounds", cBSound.isChecked());
				save.flush();
				super.clicked(event, x, y);
			}
		});

		CheckBoxStyle style2 = new CheckBoxStyle();
		style2.checkboxOff = new TextureRegionDrawable(new TextureRegion(
				game.assetM.get(game.assetID.get("checkbox1"))));
		style2.checkboxOn = new TextureRegionDrawable(new TextureRegion(
				game.assetM.get(game.assetID.get("checkbox2"))));
		style2.font = game.bLayout.getCustomFont();
		style2.fontColor = Color.WHITE;
		style2.checkedFontColor = Color.ORANGE;

		cBHighlight = new CheckBox(null, style2);
		cBHighlight.setPosition(280, 282);
		cBHighlight.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gScreen != null) gScreen.getUiManager().getgTips().setTipDelay(-1);
				save.putBoolean ("pHighlight", cBHighlight.isChecked());
				save.flush();
				super.clicked(event, x, y);
			}
		});

		back = game.bLayout.createImageTextButton(game.assetM.get(game.assetID.get("bSmall1")),
				game.assetM.get(game.assetID.get("bSmall2")), "Back", 2.4f);
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				toggleSettings = false;
				super.clicked(event, x, y);
			}
		});
		back.setBounds(270, 91, 250, 50);

		cBSound.setChecked(save.getBoolean("sounds"));
		cBHighlight.setChecked(save.getBoolean("pHighlight"));

		LabelStyle lStyle1 = new LabelStyle();
		lStyle1.fontColor = Color.WHITE;
		lStyle1.font = game.bLayout.getCustomFont();

		LabelStyle lStyle2 = new LabelStyle(lStyle1);

		sounds = new ScalableFontLabel("Sounds", lStyle1, game.fontShader, null);
		sounds.setBounds(383, 372, 40, 40);
		sounds.setFontScales(2.2f, 2.2f);

		tips = new ScalableFontLabel("Game Tips", lStyle2, game.fontShader, null);
		tips.setBounds(410, 328, 40, 40);
		tips.setFontScales(2.2f, 2.2f);

		ButtonStyle bStyle1 = new ButtonStyle();
		bStyle1.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("hand"))));
		bStyle1.checked = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("handC"))));
		hand = new Button(bStyle1);
		hand.setBounds(440, 158, 60, 60);
		hand.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resetButtons(PolyNGonsMarker.HAND);
				super.clicked(event, x, y);
			}
		});

		ButtonStyle bStyle2 = new ButtonStyle();
		bStyle2.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("square"))));
		bStyle2.checked = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("squareC"))));
		square = new Button(bStyle2);
		square.setBounds(300, 158, 60, 60);
		square.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resetButtons(PolyNGonsMarker.SQUARE);
				super.clicked(event, x, y);
			}
		});

		ButtonStyle bStyle3 = new ButtonStyle();
		bStyle3.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("circle"))));
		bStyle3.checked = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("circleC"))));
		circle = new Button(bStyle3);
		circle.setBounds(370, 158, 60, 60);
		circle.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resetButtons(PolyNGonsMarker.CIRCLE);
				super.clicked(event, x, y);
			}
		});

		resetButtons(game.getUser().getString("marker"));

		mAssetCreator.setMarkerAsset(game.getUser().getString("marker"));
		font = game.bLayout.getCustomFont();

		settingStage = new Stage();
		settingStage.addActor(cBSound);
		settingStage.addActor(cBHighlight);
		settingStage.addActor(hand);
		settingStage.addActor(circle);
		settingStage.addActor(square);
		settingStage.addActor(back);
	}

	private void resetButtons(String value){
		hand.setChecked(false);
		square.setChecked(false);
		circle.setChecked(false);

		switch (value){
		case PolyNGonsMarker.HAND: {
			mAssetCreator.setMarkerAsset(PolyNGonsMarker.HAND);
			hand.setChecked(true);
		}; break;
		case PolyNGonsMarker.SQUARE: {
			mAssetCreator.setMarkerAsset(PolyNGonsMarker.SQUARE);
			square.setChecked(true);
		}; break;
		case PolyNGonsMarker.CIRCLE: {
			mAssetCreator.setMarkerAsset(PolyNGonsMarker.CIRCLE);
			circle.setChecked(true);
		}; break;
		default: break;
		}
	}

	public void renderSettings(){
		if (game.gSelection != PolyNGonsGameSelection.NONE){
			drawGameSettings(gScreen.uiBatch);
		}
		else {
			drawGameSettings(game.batch);
		}
	}

	private void drawGameSettings(SpriteBatch batch){
		batch.draw(game.assetM.get(game.assetID.get("layout2")), 200, 50, 400, 400);
		game.bLayout.getCustomFont().drawFont(batch, "Settings", 310, 442, 3f, 3f, Color.WHITE);
		cBSound.draw(batch, 1);
		cBHighlight.draw(batch, 1);
		hand.draw(batch, 1);
		circle.draw(batch, 1);
		square.draw(batch, 1);
		font.drawFont(batch, "Select Marker:", 280, 278, 2.2f, 2.2f, Color.WHITE);
		back.draw(batch, 1);

		sounds.draw(batch, 1);
		tips.draw(batch, 1);
	}

	public void drawMarker(boolean fixable){
		Polygon selectedPoly = gScreen.selectedPolygon;
		if (fixable) currentMarkerSp.setTexture(game.assetM.get(game.assetID.get(currentMarker + "W")));
		else currentMarkerSp.setTexture(game.assetM.get(game.assetID.get(currentMarker + "R")));
		switch (game.getUser().getString("marker")){
		case PolyNGonsMarker.HAND:{
			currentMarkerSp.setPosition(
					gScreen.getPpManager().getPolysOnPuzzle().get(selectedPoly).getPolySprite().getActualX() - 25 - pgClickerModifier,
					gScreen.getPpManager().getPolysOnPuzzle().get(selectedPoly).getPolySprite().getActualY() - 28 + pgClickerModifier);
			currentMarkerSp.draw(game.batch);

			pgClickerModifier = (forward) ? pgClickerModifier + (10f * game.averageDeltaTime) : 
				pgClickerModifier - (10f * game.averageDeltaTime);
			if (pgClickerModifier >= 5) forward = false;
			if (pgClickerModifier < 0) forward = true;
		}; break;
		case PolyNGonsMarker.SQUARE:{
			currentMarkerSp.setPosition(
					gScreen.getPpManager().getPolysOnPuzzle().get(selectedPoly).getPolySprite().getActualX() - 25,
					gScreen.getPpManager().getPolysOnPuzzle().get(selectedPoly).getPolySprite().getActualY() - 25);
			currentMarkerSp.setScale(pgClickerModifier);
			currentMarkerSp.draw(game.batch);

			pgClickerModifier = (forward) ? pgClickerModifier + (.5f * game.averageDeltaTime) : 
				pgClickerModifier - (.5f * game.averageDeltaTime);
			if (pgClickerModifier >= .5f) forward = false;
			if (pgClickerModifier < .3f) forward = true;
		}; break;
		case PolyNGonsMarker.CIRCLE:{
			currentMarkerSp.setPosition(
					gScreen.getPpManager().getPolysOnPuzzle().get(selectedPoly).getPolySprite().getActualX() - 25,
					gScreen.getPpManager().getPolysOnPuzzle().get(selectedPoly).getPolySprite().getActualY() - 25);
			currentMarkerSp.draw(game.batch);

			currentMarkerSp.setRotation(pgClickerModifier);

			pgClickerModifier = (forward) ? pgClickerModifier + (100f * game.averageDeltaTime) : 
				pgClickerModifier - (100f * game.averageDeltaTime);
			if (pgClickerModifier >= 540) forward = false;
			if (pgClickerModifier < 0) forward = true;
		}; break;
		default: { 
			pgClickerModifier = 0;
			forward = false;
		}; break;
		}

		game.batch.flush();
	}

	private class MarkerAssetCreator {
		private  MarkerAssetCreator(){

		}

		private void setMarkerAsset(String markerID){
			game.getUser().putString("marker", markerID);
			game.getUser().flush();
			currentMarker = getMarkerKey(markerID);;
			switch (markerID){
			case PolyNGonsMarker.HAND:{
				pgClickerModifier = 0;
				currentMarkerSp.setRotation(0);
				currentMarkerSp.setScale(.5f);
				forward = true;
			}; break;
			case PolyNGonsMarker.SQUARE:{
				pgClickerModifier = .5f;
				currentMarkerSp.setRotation(0);
				currentMarkerSp.setScale(.5f);
				forward = false;
			}; break;
			case PolyNGonsMarker.CIRCLE:{
				pgClickerModifier = 0;
				currentMarkerSp.setRotation(0);
				currentMarkerSp.setScale(.5f);
				forward = true;
			}; break;
			default: break;
			}
		}

		public String getMarkerKey(String markerID){
			switch (markerID){
			case PolyNGonsMarker.HAND: {
				return "hand";
			}
			case PolyNGonsMarker.SQUARE: {
				return "square";
			}
			case PolyNGonsMarker.CIRCLE: {
				return "circle";
			}
			default: return null;
			}
		}
	}

	public void setSettingsListeners(boolean inGame){
		Gdx.input.setInputProcessor(settingStage);
		settingStage.act();
		if (inGame) settingStage.getViewport().setCamera(gScreen.uiCam);
		else settingStage.getViewport().setCamera(game.cam);
	}

	public boolean isToggleSettings() {
		return toggleSettings;
	}

	public void setToggleSettings(boolean toggleSettings) {
		this.toggleSettings = toggleSettings;
	}

	public void dispose(){
		settingStage.dispose();
	}

	public String getCurrentMarker() {
		return currentMarker;
	}

	public void setCurrentMarker(String currentMarker) {
		this.currentMarker = currentMarker;
	}
}
