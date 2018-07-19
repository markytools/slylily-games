package com.polyNGonsMainMenu.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.gameTools.game.CustomSelectBox;
import com.gameTools.game.MySprite;
import com.gameTools.game.ShadedBitmapFont;
import com.polyngons.game.PolyNGons;
import com.polyNGonsAssetPack.game.PolyNGonsSpritePool;
import com.polyNgonConstants.game.PolyNGonsTextureName;
import com.polyNgonConstants.game.PolygonState;
import com.polyNgonConstants.game.ProfileState;
import com.polygons.game.PolygonName;
import com.puzzleBorder.game.PuzzleBorderName;

public class PolyNGonsPolygons {
	private PolyNGons game;
	private ShadedBitmapFont polygons;
	private CustomSelectBox selectPolyTextureBox, selectBordTextureBox;
	private Stage stage;
	private TextureRenderer tRenderer;
	private MainMenuScreen mMScreen;
	
	private boolean newPolyTexture, newBordTexture;

	public PolyNGonsPolygons(PolyNGons game, MainMenuScreen mMScreen){
		this.game = game;
		this.mMScreen = mMScreen;
		tRenderer = new TextureRenderer(game);
		setup();
	}

	private void setup() {
		polygons = game.bLayout.getCustomFont();

		ListStyle lStyle = new ListStyle();
		lStyle.font = game.bLayout.getSelectBoxFont();
		lStyle.fontColorSelected = Color.valueOf("ede795");
		lStyle.fontColorUnselected = Color.valueOf("714922");
		lStyle.selection = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("lSelectBg"))));

		ScrollPaneStyle scrollStyle = new ScrollPaneStyle();
		scrollStyle.background = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scrollBg"))));
		scrollStyle.vScroll = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scrollVScroll"))));
		scrollStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scrollVScrollKnob"))));
		scrollStyle.corner = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scrollCorner"))));

		SelectBoxStyle selectBStyle = new SelectBoxStyle();
		selectBStyle.background = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("sbBg"))));
		selectBStyle.font = game.bLayout.getSelectBoxFont();
		selectBStyle.fontColor = Color.valueOf("714922");
		selectBStyle.listStyle = lStyle;
		selectBStyle.scrollStyle = scrollStyle;

		selectPolyTextureBox = new CustomSelectBox(selectBStyle);
		selectPolyTextureBox.getList().setTouchable(Touchable.disabled);
		selectPolyTextureBox.setBounds(150, 300, 220, 40);

		Array<String> polyTextures = new Array<String>();
		polyTextures.add("Simple");
		if (isRevearsed(PolyNGonsTextureName.T_GOLDEN_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_GOLDEN_CODE))) polyTextures.add("Golden");
		if (isRevearsed(PolyNGonsTextureName.T_BRICK_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_BRICK_CODE))) polyTextures.add("Brick");
		if (isRevearsed(PolyNGonsTextureName.T_METALLIC_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_METALLIC_CODE))) polyTextures.add("Metallic");
		if (isRevearsed(PolyNGonsTextureName.T_BLACKANDWHITE_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_BLACKANDWHITE_CODE))) polyTextures.add("Black and White");
		if (isRevearsed(PolyNGonsTextureName.T_SCRAP_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_SCRAP_CODE))) polyTextures.add("Scrap");
		if (isRevearsed(PolyNGonsTextureName.T_FRAME_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_FRAME_CODE))) polyTextures.add("Frame");
		if (isRevearsed(PolyNGonsTextureName.T_BUBBLES_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_BUBBLES_CODE))) polyTextures.add("Bubbles");
		if (isRevearsed(PolyNGonsTextureName.T_MOSAIC_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_MOSAIC_CODE))) polyTextures.add("Mosaic");
		if (isRevearsed(PolyNGonsTextureName.T_GEM_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_GEM_CODE))) polyTextures.add("Gem");
		if (isRevearsed(PolyNGonsTextureName.T_GRASSWEAVED_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_GRASSWEAVED_CODE))) polyTextures.add("Grass Weaved");
		if (isRevearsed(PolyNGonsTextureName.T_CLAY_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_CLAY_CODE))) polyTextures.add("Clay");
		if (isRevearsed(PolyNGonsTextureName.T_BURNT_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_BURNT_CODE))) polyTextures.add("Burnt");
		if (isRevearsed(PolyNGonsTextureName.T_WOOD_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_WOOD_CODE))) polyTextures.add("Wood");
		if (isRevearsed(PolyNGonsTextureName.T_CHOCOLATE_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_CHOCOLATE_CODE))) polyTextures.add("Chocolate");
		if (isRevearsed(PolyNGonsTextureName.T_TRIUMPHANT_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_TRIUMPHANT_CODE))) polyTextures.add("Triumphant");
		if (isRevearsed(PolyNGonsTextureName.T_GENIUS_CODE, 
				game.getUser().getString(PolyNGonsTextureName.T_GENIUS_CODE))) polyTextures.add("Genius");
		selectPolyTextureBox.setItems(polyTextures);
		selectPolyTextureBox.setSelected(PolyNGonsTextureName.byteToStringTexture(
				game.pUpdater.getPValue().getByte("currentPolyTexture")));

		ListStyle lStyle2 = new ListStyle();
		lStyle2.font = game.bLayout.getSelectBoxFont();
		lStyle2.fontColorSelected = Color.valueOf("ede795");
		lStyle2.fontColorUnselected = Color.valueOf("714922");
		lStyle2.selection = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("lSelectBg"))));

		ScrollPaneStyle scrollStyle2 = new ScrollPaneStyle();
		scrollStyle2.background = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scrollBg"))));
		scrollStyle2.vScroll = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scrollVScroll"))));
		scrollStyle2.vScrollKnob = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scrollVScrollKnob"))));
		scrollStyle2.corner = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scrollCorner"))));

		SelectBoxStyle selectBStyle2 = new SelectBoxStyle();
		selectBStyle2.background = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("sbBg"))));
		selectBStyle2.font = game.bLayout.getSelectBoxFont();
		selectBStyle2.fontColor = Color.valueOf("714922");
		selectBStyle2.listStyle = lStyle2;
		selectBStyle2.scrollStyle = scrollStyle2;

		selectBordTextureBox = new CustomSelectBox(selectBStyle);
		selectBordTextureBox.getList().setTouchable(Touchable.disabled);
		selectBordTextureBox.setBounds(150, 200, 220, 40);

		Array<String> bordTextures = new Array<String>();
		bordTextures.add("Simple");
		if (isRevearsed(PolyNGonsTextureName.B_CONCRETE_CODE, 
				game.getUser().getString(PolyNGonsTextureName.B_CONCRETE_CODE))) bordTextures.add("Concrete");
		if (isRevearsed(PolyNGonsTextureName.B_DYED_CODE, 
				game.getUser().getString(PolyNGonsTextureName.B_DYED_CODE))) bordTextures.add("Dyed");
		if (isRevearsed(PolyNGonsTextureName.B_SNOW_CODE, 
				game.getUser().getString(PolyNGonsTextureName.B_SNOW_CODE))) bordTextures.add("Snow");
		if (isRevearsed(PolyNGonsTextureName.B_KINETIC_CODE, 
				game.getUser().getString(PolyNGonsTextureName.B_KINETIC_CODE))) bordTextures.add("Kinetic");
		if (isRevearsed(PolyNGonsTextureName.B_GRASS_CODE, 
				game.getUser().getString(PolyNGonsTextureName.B_GRASS_CODE))) bordTextures.add("Grass");
		if (isRevearsed(PolyNGonsTextureName.B_FIERY_CODE, 
				game.getUser().getString(PolyNGonsTextureName.B_FIERY_CODE))) bordTextures.add("Fiery");
		if (isRevearsed(PolyNGonsTextureName.B_BUTTERFLY_CODE, 
				game.getUser().getString(PolyNGonsTextureName.B_BUTTERFLY_CODE))) bordTextures.add("Butterfly");
		if (isRevearsed(PolyNGonsTextureName.B_TRIUMPHANT_CODE, 
				game.getUser().getString(PolyNGonsTextureName.B_TRIUMPHANT_CODE))) bordTextures.add("Triumphant");
		if (isRevearsed(PolyNGonsTextureName.B_GENIUS_CODE, 
				game.getUser().getString(PolyNGonsTextureName.B_GENIUS_CODE))) bordTextures.add("Genius");
		selectBordTextureBox.setItems(bordTextures);
		selectBordTextureBox.setSelected(PolyNGonsTextureName.byteToStringTexture(
				game.pUpdater.getPValue().getByte("currentBordTexture")));

		ButtonStyle bStyle = new ButtonStyle();
		bStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scroll1"))));
		bStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(
				game.assetID.get("scroll2"))));

		Button nextPolyTexture = new Button(bStyle);
		nextPolyTexture.setBounds(400, 77, 35, 35);
		nextPolyTexture.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tRenderer.updateCurrentDisplayedPoly();
				super.clicked(event, x, y);
			}
		});

		stage = new Stage();
		stage.addActor(selectPolyTextureBox);
		stage.addActor(selectBordTextureBox);
		stage.addActor(nextPolyTexture);
		
		initNewNotify();
	}

	private void initNewNotify() {
		newPolyTexture = false;
		newBordTexture = false;
		
		Preferences user = game.getUser();
		if (user.getBoolean("PolyTextureUnlocked")){
			user.putBoolean("PolyTextureUnlocked", false);
			newPolyTexture = true;
		}
		if (user.getBoolean("BordTextureUnlocked")){
			user.putBoolean("BordTextureUnlocked", false);
			newBordTexture = true;
		}
	}

	public void render(){
		game.batch.draw(game.assetM.get(game.assetID.get("layout2")), 100, 50);
		polygons.drawFont(game.batch, "Poly N-gons", 290, 440, 2.5f, 2.5f, Color.valueOf("693401"));
		if (!(selectPolyTextureBox.getItems().size == 17 && selectBordTextureBox.getItems().size == 10)) 
			polygons.drawWrappedFont(game.batch, "Solve puzzles to unlock more textures!", 150, 166,
					220, BitmapFont.HAlignment.LEFT, 1f, 1f, Color.valueOf("886103"));
		else polygons.drawWrappedFont(game.batch, "Congratulations, you had unlocked all textures!", 150, 166,
				220, BitmapFont.HAlignment.LEFT, 1f, 1f, Color.valueOf("886103"));
		tRenderer.renderTextureSample();
		if (newPolyTexture) game.batch.draw(game.assetM.get(game.assetID.get("new")), 340, 347);
		if (newBordTexture) game.batch.draw(game.assetM.get(game.assetID.get("new")), 327, 245);

		if (!selectPolyTextureBox.getList().isTouchable()) {
			switch (selectPolyTextureBox.getSelected()){
			case "Simple": updateCurrentTexture(PolyNGonsTextureName.T_SIMPLE, 0); break;
			case "Golden": updateCurrentTexture(PolyNGonsTextureName.T_GOLDEN, 0); break;
			case "Brick": updateCurrentTexture(PolyNGonsTextureName.T_BRICK, 0); break;
			case "Metallic": updateCurrentTexture(PolyNGonsTextureName.T_METALLIC, 0); break;
			case "Black and White": updateCurrentTexture(PolyNGonsTextureName.T_BLACKANDWHITE, 0); break;
			case "Scrap": updateCurrentTexture(PolyNGonsTextureName.T_SCRAP, 0); break;
			case "Frame": updateCurrentTexture(PolyNGonsTextureName.T_FRAME, 0); break;
			case "Bubbles": updateCurrentTexture(PolyNGonsTextureName.T_BUBBLES, 0); break;
			case "Mosaic": updateCurrentTexture(PolyNGonsTextureName.T_MOSAIC, 0); break;
			case "Gem": updateCurrentTexture(PolyNGonsTextureName.T_GEM, 0); break;
			case "Grass Weaved": updateCurrentTexture(PolyNGonsTextureName.T_GRASSWEAVED, 0); break;
			case "Clay": updateCurrentTexture(PolyNGonsTextureName.T_CLAY, 0); break;
			case "Burnt": updateCurrentTexture(PolyNGonsTextureName.T_BURNT, 0); break;
			case "Wood": updateCurrentTexture(PolyNGonsTextureName.T_WOOD, 0); break;
			case "Chocolate": updateCurrentTexture(PolyNGonsTextureName.T_CHOCOLATE, 0); break;
			case "Triumphant": updateCurrentTexture(PolyNGonsTextureName.T_TRIUMPHANT, 0); break;
			case "Genius": updateCurrentTexture(PolyNGonsTextureName.T_GENIUS, 0); break;
			default: break;
			}
		}

		if (!selectBordTextureBox.getList().isTouchable()) {
			switch (selectBordTextureBox.getSelected()){
			case "Simple": updateCurrentTexture(PolyNGonsTextureName.B_SIMPLE, 1); break;
			case "Concrete": updateCurrentTexture(PolyNGonsTextureName.B_CONCRETE, 1); break;
			case "Dyed": updateCurrentTexture(PolyNGonsTextureName.B_DYED, 1); break;
			case "Snow": updateCurrentTexture(PolyNGonsTextureName.B_SNOW, 1); break;
			case "Kinetic": updateCurrentTexture(PolyNGonsTextureName.B_KINETIC, 1); break;
			case "Grass": updateCurrentTexture(PolyNGonsTextureName.B_GRASS, 1); break;
			case "Fiery": updateCurrentTexture(PolyNGonsTextureName.B_FIERY, 1); break;
			case "Butterfly": updateCurrentTexture(PolyNGonsTextureName.B_BUTTERFLY, 1); break;
			case "Triumphant": updateCurrentTexture(PolyNGonsTextureName.B_TRIUMPHANT, 1); break;
			case "Genius": updateCurrentTexture(PolyNGonsTextureName.B_GENIUS, 1); break;
			default: break;
			}
		}
	}

	public void setListeners(){
		mMScreen.getBackStage().draw();
		stage.draw();

		mMScreen.getInputs().getProcessors().set(0, mMScreen.getBackStage());
		mMScreen.getInputs().getProcessors().set(1, stage);

		Gdx.input.setInputProcessor(mMScreen.getInputs());
		mMScreen.getBackStage().getViewport().setCamera(game.cam);
		stage.getViewport().setCamera(game.cam);
		mMScreen.getBackStage().act();
		stage.act();
	}

	private boolean isRevearsed(String str1, String str2) {
		for (int i = str1.length() - 1 ; i >= 0; --i) {
			if (str1.charAt(i) != str2.charAt(str1.length() - 1 - i)) return false;
		}

		return true;
	}

	//	Type :    0 - Polygons,   1 = Borders
	private void updateCurrentTexture(byte currentSelected, int type){
		if (type == 0){
			if (game.pUpdater.getPValue().getByte("currentPolyTexture") != currentSelected){
				ProfileState pState = game.json.fromJson(ProfileState.class, game.getLoadedProf());
				pState.currentPolyTexture = currentSelected;
				game.pUpdater.updateReferences(pState);
			}
			game.assetLoader.getpPolyAssets().setPolyTextures(currentSelected);
		}
		else {
			if (game.pUpdater.getPValue().getByte("currentBordTexture") != currentSelected){
				ProfileState pState = game.json.fromJson(ProfileState.class, game.getLoadedProf());
				pState.currentBordTexture = currentSelected;
				game.pUpdater.updateReferences(pState);
			}
			game.assetLoader.getpPolyAssets().setBorderTextures(currentSelected);
		}
	}

	public class TextureRenderer {
		private HashMap<String, Array<PolyNGonTextureDesc>> spDescMap;
		private float bottomLeftBuffX, bottomLeftBuffY;
		private PolyNGonsSpritePool pSpritePool;
		private String[] displayablePoly;
		private int currentDisplayedPoly;

		public TextureRenderer(PolyNGons game){
			spDescMap = new HashMap<String, Array<PolyNGonTextureDesc>>();
			pSpritePool = game.assetLoader.getpPolyAssets().getpSpritePool();
			bottomLeftBuffX = 400;
			bottomLeftBuffY = 120;
			displayablePoly = new String[11];
			displayablePoly[0] = "Triangle A";
			displayablePoly[1] = "Triangle B";
			displayablePoly[2] = "Square";
			displayablePoly[3] = "Rectangle";
			displayablePoly[4] = "Rhombus";
			displayablePoly[5] = "Trapezoid A";
			displayablePoly[6] = "Trapezoid B";
			displayablePoly[7] = "Parallelogram A";
			displayablePoly[8] = "Parallelogram B";
			displayablePoly[9] = "Right Trapezoid A";
			displayablePoly[10] = "Right Trapezoid B";
			currentDisplayedPoly = 0;

			createSprites();
		}

		public void setProperTextures(){
			for (PolyNGonTextureDesc spDesc : spDescMap.get(displayablePoly[currentDisplayedPoly])){
				spDesc.updateSpTexture();
			}
		}

		public void updateCurrentDisplayedPoly(){
			if (currentDisplayedPoly + 1 < displayablePoly.length) currentDisplayedPoly++;
			else currentDisplayedPoly = 0;
		}

		private void createSprites() {
			//			Triangle A
			spDescMap.put(displayablePoly[0], createTriangleA());
			spDescMap.put(displayablePoly[1], createTriangleB());
			spDescMap.put(displayablePoly[2], createSquare());
			spDescMap.put(displayablePoly[3], createRectangle());
			spDescMap.put(displayablePoly[4], createRhombus());
			spDescMap.put(displayablePoly[5], createTrapezoidA());
			spDescMap.put(displayablePoly[6], createTrapezoidB());
			spDescMap.put(displayablePoly[7], createParallelogramA());
			spDescMap.put(displayablePoly[8], createParallelogramB());
			spDescMap.put(displayablePoly[9], createRightTrapezoidA());
			spDescMap.put(displayablePoly[10], createRightTrapezoidB());
		}

		public void renderTextureSample(){
			game.bLayout.getSelectBoxFont().setColor(Color.valueOf("693401"));
			game.bLayout.getSelectBoxFont().draw(game.batch, "Polygon Texture:", 150, 370);
			game.bLayout.getSelectBoxFont().draw(game.batch, "Border Texture:", 150, 268);
			game.bLayout.getSelectBoxFont().drawMultiLine(game.batch,
					displayablePoly[currentDisplayedPoly], 445, 105, 100,
					BitmapFont.HAlignment.LEFT);
			for (PolyNGonTextureDesc spDesc : spDescMap.get(displayablePoly[currentDisplayedPoly])){
				spDesc.drawSprite(game.batch);
			}
		}

		private Array<PolyNGonTextureDesc> createTriangleA(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneSq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("triAN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.triA, 1, 
							bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (2 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createTriangleB(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("triBN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.triB, 1, 
							bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createSquare(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneSq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneSq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("sqN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.sq, 1, 
							bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (2 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createRectangle(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneSq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneSq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("recN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.rec, 1, 
							bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createRhombus(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("rhomN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.rhom, 1, 
							bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createTrapezoidA(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("trapAN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.trapA, 1, 
							bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createTrapezoidB(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneSq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneTria, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneTria, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("trapBN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.trapB, 1, 
							bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createParallelogramA(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("paraAN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.paraA, 1, 
							bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createParallelogramB(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneTria, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneTria, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("paraBN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.paraB, 1, 
							bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createRightTrapezoidA(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneSq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneSq, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("rTrapAN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.rTrapA, 1, 
							bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50), false)));

			return pTDescArray;
		}

		private Array<PolyNGonTextureDesc> createRightTrapezoidB(){
			Array<PolyNGonTextureDesc> pTDescArray = new Array<PolyNGonTextureDesc>();

			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneTria, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.oneTria, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("oneTria", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.oneTria, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("oneSq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.oneSq, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));
			pTDescArray.add(new PolyNGonTextureDesc("emptySq", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (4 * 50),
							PuzzleBorderName.emptySq, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (2 * 50), bottomLeftBuffY + (0 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 3)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (3 * 50), bottomLeftBuffY + (1 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (0 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (2 * 50),
							PuzzleBorderName.turn, false, 2)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 4)));
			pTDescArray.add(new PolyNGonTextureDesc("turn", 
					pSpritePool.getBordSprite(bottomLeftBuffX + (4 * 50), bottomLeftBuffY + (3 * 50),
							PuzzleBorderName.turn, false, 1)));

			pTDescArray.add(new PolyNGonTextureDesc("rTrapBN", 
					pSpritePool.getPolySprite(PolygonState.FIXED, PolygonName.rTrapB, 1, 
							bottomLeftBuffX + (1 * 50), bottomLeftBuffY + (1 * 50), false)));

			return pTDescArray;
		}

		public void setCurrentDisplayedPoly(int currentDisplayedPoly) {
			this.currentDisplayedPoly = currentDisplayedPoly;
		}
	}

	private class PolyNGonTextureDesc {
		private String textID;
		private MySprite sp;

		public PolyNGonTextureDesc(String textID, MySprite sp) {
			super();
			this.textID = textID;
			this.sp = sp;
		}

		public void updateSpTexture(){
			sp.setTexture(game.assetM.get(game.assetID.get(textID)));
		}

		public void drawSprite(SpriteBatch batch){
			sp.draw(batch);
			batch.flush();
		}
	}

	public void setCurrentDisplayedPoly(int currentDisplayedPoly){
		tRenderer.setCurrentDisplayedPoly(currentDisplayedPoly);
	}

	public TextureRenderer gettRenderer() {
		// TODO Auto-generated method stub
		return tRenderer;
	}

	public void setNewPolyTexture(boolean newPolyTexture) {
		this.newPolyTexture = newPolyTexture;
	}

	public void setNewBordTexture(boolean newBordTexture) {
		this.newBordTexture = newBordTexture;
	}

	public boolean isNewPolyTexture() {
		return newPolyTexture;
	}

	public boolean isNewBordTexture() {
		return newBordTexture;
	}
	
	public void dispose(){
		stage.dispose();
	}
}
