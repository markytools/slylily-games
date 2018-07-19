package com.polyNGonsUI.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.gameInputProcessors.game.PolygonActor;
import com.gameTools.game.ScalableFontLabel;
import com.polyngons.game.GameScreen;
import com.polyNgonConstants.game.GameState;
import com.polyNgonConstants.game.PolygonState;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;

public class PolyNGonUI {
	private HashMap<String, Button> iuButtonsMap;
	private ArrayMap<String, Button> polyButtonsMap;
	private ArrayMap<String, ScalableFontLabel> polyNumLabelMap;
	private ArrayMap<Float, Button> buttonYPosDiff;
	private HashMap<String, AssetDescriptor<Texture>> assetMap;

	private GameScreen gScreen;
	private Stage uiStage;
	private AssetManager m;

	private Array<String> polyIDs;
	private Button currentSelectedB;

	private ActorGestureListener bPanning;

	private boolean useCutPolyHUD, resetPolyButtons;
	private float minY, pButtonsXPos, pButtonsYPos, pBHeight;

	private float lastY = 0;

	public BitmapFont font;

	public PolyNGonUI(GameScreen gScreen, Stage uiStage, Stage puzzleStage){
		this.gScreen = gScreen;
		this.uiStage = uiStage;
		m = gScreen.game.assetM;
		assetMap = gScreen.game.assetID;

		iuButtonsMap = new HashMap<String, Button>();
		polyButtonsMap = new ArrayMap<String, Button>();
		buttonYPosDiff = new ArrayMap<Float, Button>();
		polyNumLabelMap = new ArrayMap<String, ScalableFontLabel>();

		font = gScreen.game.getFont();

		createControlButtons();
		createFixablePolygonButtons();
		addPanningListener();
		createButtonLabels();

		useCutPolyHUD = gScreen.getPpManager().checkMoreThanFourPoly();
		polyIDs = new Array<String>();
		addStringIDs();

		for (Button button : polyButtonsMap.values()){
			uiStage.addActor(button);
		}

		for (Button button : iuButtonsMap.values()){
			uiStage.addActor(button);
		}

		resetPolyButtons = false;
	}

	private void createButtonLabels() {
		for (String key : polyButtonsMap.keys()){
			LabelStyle style = new LabelStyle();
			style.font = font;

			final ScalableFontLabel numLabel = new ScalableFontLabel("", style, gScreen.game.fontShader, null);

			polyNumLabelMap.put(key, numLabel);
		}
	}

	private void createControlButtons(){
		ButtonStyle style1 = new ButtonStyle();
		style1.up = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("cc1"))));
		style1.down = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("cc2"))));

		final Button counterclockwise = new Button(style1);
		counterclockwise.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gScreen.selectedPolygon != null){
					rotatePolygonCC(gScreen.selectedPolygon);
					gScreen.getPpManager().setSelectedPolyState();
				}
				super.clicked(event, x, y);
			}
		});
		counterclockwise.setBounds(10, 125, 100, 100);
		iuButtonsMap.put("ccButton", counterclockwise);

		ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("c1"))));
		style2.down = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("c2"))));

		final Button clockwise = new Button(style2);
		clockwise.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gScreen.selectedPolygon != null){
					rotatePolygonC(gScreen.selectedPolygon);
					gScreen.getPpManager().setSelectedPolyState();
				}
				super.clicked(event, x, y);
			}
		});
		clockwise.setBounds(10, 25, 100, 100);

		iuButtonsMap.put("cButton", clockwise);

		ButtonStyle style3 = new ButtonStyle();
		style3.up = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("flip1"))));
		style3.down = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("flip2"))));

		final Button flip = new Button(style3);
		flip.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gScreen.selectedPolygon != null){
					flipPolygon(gScreen.selectedPolygon);
					gScreen.getPpManager().setSelectedPolyState();
				}
				super.clicked(event, x, y);
			}
		});
		flip.setBounds(10, 225, 100, 100);

		iuButtonsMap.put("flipButton", flip);

		ButtonStyle style4 = new ButtonStyle();
		style4.up = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("removeLast1"))));
		style4.down = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("removeLast2"))));

		final Button removeLast = new Button(style4);
		removeLast.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				ArrayMap<Polygon, PolygonActor> polyOnPuzzle = gScreen.getPpManager().getPolysOnPuzzle();
				if (polyOnPuzzle.size != 0){
					if (gScreen.selectedPolygon != null) {
						polyOnPuzzle.get(gScreen.selectedPolygon).setState(PolygonState.NULL);
						polyOnPuzzle.removeIndex(polyOnPuzzle.indexOfKey(gScreen.selectedPolygon));
						gScreen.selectedPolygon = null;
					}
					else polyOnPuzzle.removeIndex(polyOnPuzzle.size - 1);
					for (Button b1 : polyButtonsMap.values()) b1.setChecked(false);
				}
				super.clicked(event, x, y);
			}
		});
		removeLast.setBounds(10, 325, 100, 100);

		iuButtonsMap.put("removeLastButton", removeLast);

		ButtonStyle style5 = new ButtonStyle();
		style5.up = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("menu1"))));
		style5.down = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get("menu2"))));

		final Button menu = new Button(style5);
		menu.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gScreen.game.requestAd != null) gScreen.game.pAdManager.showBannerAds(true);
				gScreen.gState = GameState.IN_MENU;
				super.clicked(event, x, y);
			}
		});
		menu.setBounds(0, 440, 120, 50);

		iuButtonsMap.put("menuButton", menu);
	}

	private void addPanningListener() {
		bPanning = new ActorGestureListener();
		uiStage.addListener(bPanning);
	}

	private void checkIfPanning(){
		if (gScreen.uiTouchPos.x >= 650){
			if (bPanning.getGestureDetector().isPanning()){
				resetPolyButtons = true;
				if (lastY == 0) lastY = gScreen.game.touchPos.y;
				pButtonsYPos += (gScreen.game.touchPos.y - lastY) * 5 / Gdx.graphics.getFramesPerSecond();
				for (Button b : polyButtonsMap.values()) b.setChecked(false);
				if (currentSelectedB != null) currentSelectedB = null;
			}
			else {
				lastY = 0;
				if (resetPolyButtons) {
					resetPolyButtons = false;
					resetPolyButtons();
				}
			}
		}
		if (polyButtonsMap.size >= 4) pButtonsYPos = MathUtils.clamp(pButtonsYPos, minY, 10);
		else pButtonsYPos = MathUtils.clamp(pButtonsYPos, minY, minY);


		int count = 0;
		for (int i = polyButtonsMap.size; i > 0; i--){
			Button b = polyButtonsMap.getValueAt(i - 1);
			if (b.isChecked()) count++;
			if (count >= 2){
				for (Button b1 : polyButtonsMap.values()) b1.setChecked(false);
				break;
			}
		}
	}

	private void resetPolyButtons() {
		for (Button b : polyButtonsMap.values()) b.setChecked(false);
		currentSelectedB = null;
	}

	public void renderUI(){
		checkIfPanning();
		reAbleButtons();
		drawPolyButtons();

		if (useCutPolyHUD) {
			drawPolyButtons();
			gScreen.uiBatch.draw(m.get(assetMap.get("cut")), 650, 0, 150, 500);
		}
		else {
			gScreen.uiBatch.draw(m.get(assetMap.get("notCut")), 650, 0, 150, 500);
			drawPolyButtons();
		}

		for (String key : iuButtonsMap.keySet()){
			if (key != "removeLastButton") iuButtonsMap.get(key).draw(gScreen.uiBatch, 1);
		}

		if (gScreen.getPpManager().getPolysOnPuzzle().size != 0) {
			iuButtonsMap.get("removeLastButton").draw(gScreen.uiBatch, 1);
		}
	}

	private void reAbleButtons() {
		for (String key : polyButtonsMap.keys()){
			if (gScreen.getPpManager().getPolyLeft(PolygonName.getPolygonID(key)) > 0) polyButtonsMap.get(key).setDisabled(false);
			else polyButtonsMap.get(key).setDisabled(true);
		}
	}

	private void drawPolyButtons(){

		for (int i = 0; i < buttonYPosDiff.size; i++){
			Button pButton = buttonYPosDiff.getValueAt(i);
			float y = pButtonsYPos + buttonYPosDiff.getKey(pButton, true);
			pButton.setBounds(pButtonsXPos, y, 120, 120);
			pButton.draw(gScreen.uiBatch, 1);
			drawRemainingPoly(pButton, y);
		}
	}

	private void drawRemainingPoly(Button pButton, float y){
		ScalableFontLabel label = polyNumLabelMap.get(polyButtonsMap.getKey(pButton, true));
		int polyLeft = gScreen.getPpManager().getPolyLeft(PolygonName.getPolygonID(
				polyButtonsMap.getKey(pButton, true)));
		label.setPosition(pButtonsXPos + 93, y + 55f);
		label.setFontScales(1.15f, 1.2f);
		label.setText(String.valueOf(polyLeft));

		if (polyLeft != 0) label.setColor(Color.YELLOW);
		else label.setColor(Color.RED);
		label.draw(gScreen.uiBatch, 1);
	}

	public void flipPolygon(Polygon poly){
		if (poly.getName() == PolygonName.rhom || poly.getName() == PolygonName.paraA || poly.getName() == PolygonName.paraB ||
				poly.getName() == PolygonName.rTrapA || poly.getName() == PolygonName.rTrapB){
			poly.setReversed(!poly.isReversed());
		}
		else gScreen.getUiManager().getgTips().setAndRenderTip("unflip");
	}

	private void createFixablePolygonButtons(){
		if (gScreen.getPpManager().triASize != 0){
			createButton("fTriA1", "fTriA2", "triA");
		}
		if (gScreen.getPpManager().triBSize != 0){
			createButton("fTriB1", "fTriB2", "triB");
		}
		if (gScreen.getPpManager().sqSize != 0){
			createButton("fSq1", "fSq2", "sq");
		}
		if (gScreen.getPpManager().recSize != 0){
			createButton("fRec1", "fRec2", "rec");
		}
		if (gScreen.getPpManager().rhomSize != 0){
			createButton("fRhom1", "fRhom2", "rhom");
		}
		if (gScreen.getPpManager().trapASize != 0){
			createButton("fTrapA1", "fTrapA2", "trapA");
		}
		if (gScreen.getPpManager().trapBSize != 0){
			createButton("fTrapB1", "fTrapB2", "trapB");
		}
		if (gScreen.getPpManager().paraASize != 0){
			createButton("fParaA1", "fParaA2", "paraA");
		}
		if (gScreen.getPpManager().paraBSize != 0){
			createButton("fParaB1", "fParaB2", "paraB");
		}
		if (gScreen.getPpManager().rTrapASize != 0){
			createButton("fRTrapA1", "fRTrapA2", "rTrapA");
		}
		if (gScreen.getPpManager().rTrapBSize != 0){
			createButton("fRTrapB1", "fRTrapB2", "rTrapB");
		}

		int size = polyButtonsMap.size;
		pBHeight = 120 * size;
		pButtonsYPos = 490 - pBHeight;
		minY = pButtonsYPos;
		pButtonsXPos = 665;
	} 

	private void addStringIDs(){
		polyIDs.add("triA");
		polyIDs.add("triB");
		polyIDs.add("sq");
		polyIDs.add("rec");
		polyIDs.add("rhom");
		polyIDs.add("trapA");
		polyIDs.add("trapB");
		polyIDs.add("paraA");
		polyIDs.add("paraB");
		polyIDs.add("rTrapA");
		polyIDs.add("rTrapB");

		int polyIDCount = 0;
		int size = polyButtonsMap.size;
		while (polyIDCount < polyIDs.size){
			for (String key : polyButtonsMap.keys()){
				if (!key.equalsIgnoreCase(polyIDs.get(polyIDCount))){
					if (polyButtonsMap.indexOfKey(key) == polyButtonsMap.size - 1) {
						polyIDCount++;
					}
				}
				else {
					buttonYPosDiff.put((float) ((size - 1) * 120), polyButtonsMap.get(key));
					polyIDCount++;
					size--;
					break;
				}
			}
		}
	}

	private void createButton(String upKey, String downKey, String buttonKey){
		ButtonStyle style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get(upKey))));
		style.down = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get(downKey))));
		style.checked = new TextureRegionDrawable(new TextureRegion(m.get(assetMap.get(downKey))));

		final Button polyButton = new Button(style);
		polyButton.addListener(new ClickListener(){


			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!bPanning.getGestureDetector().isPanning()){
					for (Button b : polyButtonsMap.values()) if (b != polyButton) b.setChecked(false);
					if (polyButton.isChecked()) {
						currentSelectedB = polyButton;
					}
					else currentSelectedB = null;
				}
				super.clicked(event, x, y);
			}

		});

		polyButtonsMap.put(buttonKey, polyButton);
	}

	public void rotatePolygonC(Polygon poly){

		switch (poly.getName()){
		case PolygonName.triA: {
			if (poly.getPosition() == 4){
				poly.setPosition(1);
			} else poly.setPosition(poly.getPosition() + 1);
		}; break;
		case PolygonName.triB: {
			if (poly.getPosition() == 4){
				poly.setPosition(1);
			} else poly.setPosition(poly.getPosition() + 1);
		}; break;
		case PolygonName.sq: {

		}; break;
		case PolygonName.rec: {
			if (poly.getPosition() == 2){
				poly.setPosition(1);
			} else poly.setPosition(poly.getPosition() + 1);
		}; break;
		case PolygonName.rhom: {
			if (poly.getPosition() == 2){
				poly.setPosition(1);
			} else poly.setPosition(poly.getPosition() + 1);
		}; break;
		case PolygonName.trapA: {
			if (poly.getPosition() == 4){
				poly.setPosition(1);
			} else poly.setPosition(poly.getPosition() + 1);
		}; break;
		case PolygonName.trapB: {
			if (poly.getPosition() == 4){
				poly.setPosition(1);
			} else poly.setPosition(poly.getPosition() + 1);
		}; break;
		case PolygonName.paraA: {
			if (poly.getPosition() == 2){
				poly.setPosition(1);
			} else poly.setPosition(poly.getPosition() + 1);
		}; break;
		case PolygonName.paraB: {
			if (poly.getPosition() == 2){
				poly.setPosition(1);
			} else poly.setPosition(poly.getPosition() + 1);
		}; break;
		case PolygonName.rTrapA: {
			if (!poly.isReversed()){
				if (poly.getPosition() == 4){
					poly.setPosition(1);
				} else poly.setPosition(poly.getPosition() + 1);
			}
			else {
				if (poly.getPosition() == 1){
					poly.setPosition(4);
				} else poly.setPosition(poly.getPosition() - 1);
			}
		}; break;
		case PolygonName.rTrapB: {
			if (!poly.isReversed()){
				if (poly.getPosition() == 4){
					poly.setPosition(1);
				} else poly.setPosition(poly.getPosition() + 1);
			}
			else {
				if (poly.getPosition() == 1){
					poly.setPosition(4);
				} else poly.setPosition(poly.getPosition() - 1);
			}
		}; break;
		default: break;
		}
	}

	public void rotatePolygonCC(Polygon poly){
		switch (poly.getName()){
		case PolygonName.triA: {
			if (poly.getPosition() == 1){
				poly.setPosition(4);
			} else poly.setPosition(poly.getPosition() - 1);
		}; break;
		case PolygonName.triB: {
			if (poly.getPosition() == 1){
				poly.setPosition(4);
			} else poly.setPosition(poly.getPosition() - 1);
		}; break;
		case PolygonName.sq: {

		}; break;
		case PolygonName.rec: {
			if (poly.getPosition() == 1){
				poly.setPosition(2);
			} else poly.setPosition(poly.getPosition() - 1);
		}; break;
		case PolygonName.rhom: {
			if (poly.getPosition() == 1){
				poly.setPosition(2);
			} else poly.setPosition(poly.getPosition() - 1);
		}; break;
		case PolygonName.trapA: {
			if (poly.getPosition() == 1){
				poly.setPosition(4);
			} else poly.setPosition(poly.getPosition() - 1);
		}; break;
		case PolygonName.trapB: {
			if (poly.getPosition() == 1){
				poly.setPosition(4);
			} else poly.setPosition(poly.getPosition() - 1);
		}; break;
		case PolygonName.paraA: {
			if (poly.getPosition() == 1){
				poly.setPosition(2);
			} else poly.setPosition(poly.getPosition() - 1);
		}; break;
		case PolygonName.paraB: {
			if (poly.getPosition() == 1){
				poly.setPosition(2);
			} else poly.setPosition(poly.getPosition() - 1);
		}; break;
		case PolygonName.rTrapA: {
			if (!poly.isReversed()){
				if (poly.getPosition() == 1){
					poly.setPosition(4);
				} else poly.setPosition(poly.getPosition() - 1);
			}
			else {
				if (poly.getPosition() == 4){
					poly.setPosition(1);
				} else poly.setPosition(poly.getPosition() + 1);
			}
		}; break;
		case PolygonName.rTrapB: {
			if (!poly.isReversed()){
				if (poly.getPosition() == 1){
					poly.setPosition(4);
				} else poly.setPosition(poly.getPosition() - 1);
			}
			else {
				if (poly.getPosition() == 4){
					poly.setPosition(1);
				} else poly.setPosition(poly.getPosition() + 1);
			}
		}; break;
		default: break;
		}
	}

	public ArrayMap<String, Button> getPolyButtonsMap() {
		return polyButtonsMap;
	}

	public float getMinY() {
		return minY;
	}

	public float getpButtonsYPos() {
		return pButtonsYPos;
	}

	public float getTopBound() {
		return pButtonsYPos + pBHeight;
	}

	public String getButtonSelected(){
		for (String key : polyButtonsMap.keys()){
			if (polyButtonsMap.get(key) == currentSelectedB) return key;
		}
		return "";
	}

	public Button getCurrentSelectedB() {
		return currentSelectedB;
	}
}
