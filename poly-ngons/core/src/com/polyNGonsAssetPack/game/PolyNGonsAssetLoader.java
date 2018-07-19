package com.polyNGonsAssetPack.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.polyngons.game.PolyNGons;
import com.polyNgonConstants.game.SelectedChallenge;

public class PolyNGonsAssetLoader {
	private HashMap<String, AssetDescriptor<Texture>> assetID;
	private HashMap<String, AssetDescriptor<Sound>> soundID;
	private AssetManager assetM;
	private TextureParameter param;
	private PolyNGonsPolyAsset pPolyAssets;

	private PolyNGons game;
	
	public PolyNGonsAssetLoader(PolyNGons game, HashMap<String, AssetDescriptor<Texture>> assetID,
			HashMap<String, AssetDescriptor<Sound>> soundID,
			AssetManager assetM){
		this.game = game;
		this.assetID = assetID;
		this.soundID = soundID;
		this.assetM = assetM;
	}
	
	public void setAssetDesc(){
		param = new TextureParameter();
		param.genMipMaps = true;
		param.magFilter = TextureFilter.MipMapLinearNearest;
		param.minFilter = TextureFilter.MipMapLinearNearest;

		pPolyAssets = new PolyNGonsPolyAsset(game, param);

		//		MAIN MENU LOADERS
		//		mainMenuAssets
		assetID.put("dButton1", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/dButton1.png"), Texture.class, param));
		
		assetID.put("dButton2", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/dButton2.png"), Texture.class, param));
		assetID.put("locked", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/locked.png"), Texture.class, param));
		for (int i = 0; i < 20; i++){
			assetID.put("d" + (i + 1) + "Button", new AssetDescriptor<Texture>
			(Gdx.files.internal("mainMenuAssets/pCollection/" + (i + 1) + ".png"), Texture.class, param));
		}
		assetID.put("back1", new AssetDescriptor<Texture>
		(Gdx.files.internal("bgAssets/back1.png"), Texture.class, param));
		assetID.put("back2", new AssetDescriptor<Texture>
		(Gdx.files.internal("bgAssets/back2.png"), Texture.class, param));
		
		assetID.put("layout1", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/layout1.png"), Texture.class, param));
		assetID.put("layout2", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/layout2.png"), Texture.class, param));
		assetID.put("bSmall1", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/bSmall1.png"), Texture.class, param));
		assetID.put("bSmall2", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/bSmall2.png"), Texture.class, param));
		assetID.put("bBig1", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/bBig1.png"), Texture.class, param));
		assetID.put("bBig2", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/bBig2.png"), Texture.class, param));
		assetID.put("squareR", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/squareR.png"), Texture.class, param));
		assetID.put("squareW", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/squareW.png"), Texture.class, param));
		assetID.put("circleR", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/circleR.png"), Texture.class, param));
		assetID.put("circleW", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/circleW.png"), Texture.class, param));
		assetID.put("handR", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/handR.png"), Texture.class, param));
		assetID.put("handW", new AssetDescriptor<Texture>
		(Gdx.files.internal("layouts/handW.png"), Texture.class, param));

		assetID.put("checkbox1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/settings/checkbox1.png"), Texture.class, param));
		assetID.put("checkbox2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/settings/checkbox2.png"), Texture.class, param));
		assetID.put("circle", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/settings/circle.png"), Texture.class, param));
		assetID.put("circleC", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/settings/circleC.png"), Texture.class, param));
		assetID.put("hand", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/settings/hand.png"), Texture.class, param));
		assetID.put("handC", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/settings/handC.png"), Texture.class, param));
		assetID.put("square", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/settings/square.png"), Texture.class, param));
		assetID.put("squareC", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/settings/squareC.png"), Texture.class, param));

		assetID.put("pNumLayout", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/pSelection/pNumLayout.png"), Texture.class, param));
		assetID.put("scroll1", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/pSelection/scroll1.png"), Texture.class, param));
		assetID.put("scroll2", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/pSelection/scroll2.png"), Texture.class, param));
		assetID.put("selectPuzzle1", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/pSelection/selectPuzzle1.png"), Texture.class, param));
		assetID.put("selectPuzzle2", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/pSelection/selectPuzzle2.png"), Texture.class, param));
		assetID.put("indicator1", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/pSelection/indicator1.png"), Texture.class, param));
		assetID.put("indicator2", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/pCollection/pSelection/indicator2.png"), Texture.class, param));

		assetID.put("scrollBg", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/Poly N-gons/bg.png"), Texture.class, param));
		assetID.put("lSelectBg", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/Poly N-gons/selectionBg.png"), Texture.class, param));
		assetID.put("scrollCorner", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/Poly N-gons/corner.png"), Texture.class, param));
		assetID.put("scrollVScroll", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/Poly N-gons/vScroll.png"), Texture.class, param));
		assetID.put("scrollVScrollKnob", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/Poly N-gons/vScrollKnob.png"), Texture.class, param));
		assetID.put("sbBg", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/Poly N-gons/selectBoxBg.png"), Texture.class, param));

		//		GAME LOADERS
		//		Game UI Buttons
		assetID.put("cc1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/counterclockwise/1.png"), Texture.class, param));
		assetID.put("cc2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/counterclockwise/2.png"), Texture.class, param));
		assetID.put("c1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/clockwise/1.png"), Texture.class, param));
		assetID.put("c2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/clockwise/2.png"), Texture.class, param));
		assetID.put("flip1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/flip/1.png"), Texture.class, param));
		assetID.put("flip2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/flip/2.png"), Texture.class, param));
		assetID.put("removeLast1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/removeLast/1.png"), Texture.class, param));
		assetID.put("removeLast2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/removeLast/2.png"), Texture.class, param));
		assetID.put("menu1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/menu/1.png"), Texture.class, param));
		assetID.put("menu2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/menu/2.png"), Texture.class, param));

		assetID.put("fTriA1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/triA/1.png"), Texture.class, param));
		assetID.put("fTriA2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/triA/2.png"), Texture.class, param));
		assetID.put("fTriB1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/triB/1.png"), Texture.class, param));
		assetID.put("fTriB2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/triB/2.png"), Texture.class, param));
		assetID.put("fSq1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/sq/1.png"), Texture.class, param));
		assetID.put("fSq2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/sq/2.png"), Texture.class, param));
		assetID.put("fRec1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/rec/1.png"), Texture.class, param));
		assetID.put("fRec2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/rec/2.png"), Texture.class, param));
		assetID.put("fRhom1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/rhom/1.png"), Texture.class, param));
		assetID.put("fRhom2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/rhom/2.png"), Texture.class, param));
		assetID.put("fTrapA1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/trapA/1.png"), Texture.class, param));
		assetID.put("fTrapA2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/trapA/2.png"), Texture.class, param));
		assetID.put("fTrapB1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/trapB/1.png"), Texture.class, param));
		assetID.put("fTrapB2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/trapB/2.png"), Texture.class, param));
		assetID.put("fParaA1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/paraA/1.png"), Texture.class, param));
		assetID.put("fParaA2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/paraA/2.png"), Texture.class, param));
		assetID.put("fParaB1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/paraB/1.png"), Texture.class, param));
		assetID.put("fParaB2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/paraB/2.png"), Texture.class, param));
		assetID.put("fRTrapA1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/rTrapA/1.png"), Texture.class, param));
		assetID.put("fRTrapA2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/rTrapA/2.png"), Texture.class, param));
		assetID.put("fRTrapB1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/rTrapB/1.png"), Texture.class, param));
		assetID.put("fRTrapB2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/rTrapB/2.png"), Texture.class, param));

		assetID.put("arrow1", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/arrow/1.png"), Texture.class, param));
		assetID.put("arrow2", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/arrow/2.png"), Texture.class, param));
		assetID.put("arrow3", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/arrow/3.png"), Texture.class, param));

		assetID.put("cut", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/pHUDLayout/cut.png"), Texture.class, param));
		assetID.put("notCut", new AssetDescriptor<Texture>
		(Gdx.files.internal("GameUI/pHUDLayout/notCut.png"), Texture.class, param));

		setPolygonTextures();
//		assetID.put("triAN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/triA/n.png"), Texture.class, param));
//		assetID.put("triAF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/triA/f.png"), Texture.class, param));
//		assetID.put("triAUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/triA/uF.png"), Texture.class, param));
//		assetID.put("triBN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/triB/n.png"), Texture.class, param));
//		assetID.put("triBF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/triB/f.png"), Texture.class, param));
//		assetID.put("triBUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/triB/uF.png"), Texture.class, param));
//		assetID.put("sqN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/sq/n.png"), Texture.class, param));
//		assetID.put("sqF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/sq/f.png"), Texture.class, param));
//		assetID.put("sqUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/sq/uF.png"), Texture.class, param));
//		assetID.put("recN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rec/n.png"), Texture.class, param));
//		assetID.put("recF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rec/f.png"), Texture.class, param));
//		assetID.put("recUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rec/uF.png"), Texture.class, param));
//		assetID.put("rhomN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rhom/n.png"), Texture.class, param));
//		assetID.put("rhomF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rhom/f.png"), Texture.class, param));
//		assetID.put("rhomUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rhom/uF.png"), Texture.class, param));
//		assetID.put("trapAN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/trapA/n.png"), Texture.class, param));
//		assetID.put("trapAF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/trapA/f.png"), Texture.class, param));
//		assetID.put("trapAUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/trapA/uF.png"), Texture.class, param));
//		assetID.put("trapBN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/trapB/n.png"), Texture.class, param));
//		assetID.put("trapBF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/trapB/f.png"), Texture.class, param));
//		assetID.put("trapBUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/trapB/uF.png"), Texture.class, param));
//		assetID.put("paraAN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/paraA/n.png"), Texture.class, param));
//		assetID.put("paraAF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/paraA/f.png"), Texture.class, param));
//		assetID.put("paraAUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/paraA/uF.png"), Texture.class, param));
//		assetID.put("paraBN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/paraB/n.png"), Texture.class, param));
//		assetID.put("paraBF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/paraB/f.png"), Texture.class, param));
//		assetID.put("paraBUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/paraB/uF.png"), Texture.class, param));
//		assetID.put("rTrapAN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rTrapA/n.png"), Texture.class, param));
//		assetID.put("rTrapAF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rTrapA/f.png"), Texture.class, param));
//		assetID.put("rTrapAUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rTrapA/uF.png"), Texture.class, param));
//		assetID.put("rTrapBN", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rTrapB/n.png"), Texture.class, param));
//		assetID.put("rTrapBF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rTrapB/f.png"), Texture.class, param));
//		assetID.put("rTrapBUF", new AssetDescriptor<Texture>
//		(Gdx.files.internal("polygons/rTrapB/uF.png"), Texture.class, param));
//
//		assetID.put("oneTria", new AssetDescriptor<Texture>
//		(Gdx.files.internal("borders/oneTria/1.png"), Texture.class, param));
//		assetID.put("twoTria", new AssetDescriptor<Texture>
//		(Gdx.files.internal("borders/twoTria/1.png"), Texture.class, param));
//		assetID.put("oneSq", new AssetDescriptor<Texture>
//		(Gdx.files.internal("borders/oneSq/1.png"), Texture.class, param));
//		assetID.put("perpendSq", new AssetDescriptor<Texture>
//		(Gdx.files.internal("borders/perpendSq/1.png"), Texture.class, param));
//		assetID.put("threeSq", new AssetDescriptor<Texture>
//		(Gdx.files.internal("borders/threeSq/1.png"), Texture.class, param));
//		assetID.put("turn", new AssetDescriptor<Texture>
//		(Gdx.files.internal("borders/turn/1.png"), Texture.class, param));
//		assetID.put("emptySq", new AssetDescriptor<Texture>
//		(Gdx.files.internal("borders/emptySq.png"), Texture.class, param));
//		assetID.put("polySpace", new AssetDescriptor<Texture>
//		(Gdx.files.internal("borders/polySpace.png"), Texture.class, param));
		
		assetID.put("tipLayout", new AssetDescriptor<Texture>
		(Gdx.files.internal("mainMenuAssets/tutorials/Tips Layout.png"), Texture.class, param));

		assetID.put("newDifficulty", new AssetDescriptor<Texture>
		(Gdx.files.internal("puzzleCompletion/newDifficulty.png"), Texture.class, param));
		assetID.put("newTexture", new AssetDescriptor<Texture>
		(Gdx.files.internal("puzzleCompletion/newTexture.png"), Texture.class, param));
		assetID.put("puzzleFailed", new AssetDescriptor<Texture>
		(Gdx.files.internal("puzzleCompletion/puzzleFailed.png"), Texture.class, param));
		assetID.put("puzzleSolved", new AssetDescriptor<Texture>
		(Gdx.files.internal("puzzleCompletion/puzzleSolved.png"), Texture.class, param));
		assetID.put("new", new AssetDescriptor<Texture>
		(Gdx.files.internal("puzzleCompletion/new.png"), Texture.class, param));
		assetID.put("star", new AssetDescriptor<Texture>
		(Gdx.files.internal("puzzleCompletion/star.png"), Texture.class, param));
		
//		Animations
		for (int i = 0; i < 24; i++) 
			assetID.put("animation" + i, new AssetDescriptor<Texture>
			(Gdx.files.internal("animation/" + i + ".png"), Texture.class, param));
		
		//		Sound and Music load here.
		
		soundID.put("blip", new AssetDescriptor<Sound>
		(Gdx.files.internal("sounds/blipButton.mp3"), Sound.class));
		soundID.put("invalid", new AssetDescriptor<Sound>
		(Gdx.files.internal("sounds/invalidPolyPlaced.ogg"), Sound.class));	
		soundID.put("normal", new AssetDescriptor<Sound>
		(Gdx.files.internal("sounds/normalPolyPlaced.ogg"), Sound.class));	
		soundID.put("win", new AssetDescriptor<Sound>
		(Gdx.files.internal("sounds/winningPolyPlaced.ogg"), Sound.class));
		soundID.put("new", new AssetDescriptor<Sound>
		(Gdx.files.internal("sounds/newUnlocks.ogg"), Sound.class));
		soundID.put("ticking", new AssetDescriptor<Sound>
		(Gdx.files.internal("sounds/ticking.ogg"), Sound.class));

		for (int i = 0; i < 24; i++) assetM.load(assetID.get("animation" + i));

		assetM.load(assetID.get("bBig1"));
		assetM.load(assetID.get("bBig2"));
		assetM.finishLoading();
	}

	public void loadMainMenuAssets(){
		assetM.load(assetID.get("dButton1"));
		assetM.load(assetID.get("dButton2"));
		assetM.load(assetID.get("layout2"));
		assetM.load(assetID.get("back1"));
		assetM.load(assetID.get("back2"));
		assetM.load(assetID.get("locked"));
		for (int i = 0; i < 20; i++){
			assetM.load(assetID.get("d" + (i + 1) + "Button"));
		}
		assetM.load(assetID.get("pNumLayout"));
		assetM.load(assetID.get("scroll1"));
		assetM.load(assetID.get("scroll2"));
		assetM.load(assetID.get("selectPuzzle1"));
		assetM.load(assetID.get("selectPuzzle2"));
		assetM.load(assetID.get("indicator1"));
		assetM.load(assetID.get("indicator2"));
		
		assetM.load(assetID.get("bSmall1"));
		assetM.load(assetID.get("bSmall2"));
		assetM.load(assetID.get("checkbox1"));
		assetM.load(assetID.get("checkbox2"));
		assetM.load(assetID.get("sbBg"));
		assetM.load(assetID.get("circle"));
		assetM.load(assetID.get("circleC"));
		assetM.load(assetID.get("hand"));
		assetM.load(assetID.get("handC"));
		assetM.load(assetID.get("square"));
		assetM.load(assetID.get("squareC"));
		
		assetM.load(assetID.get("squareR"));
		assetM.load(assetID.get("squareW"));
		assetM.load(assetID.get("circleR"));
		assetM.load(assetID.get("circleW"));
		assetM.load(assetID.get("handR"));
		assetM.load(assetID.get("handW"));

		assetM.load(assetID.get("lSelectBg"));
		assetM.load(assetID.get("scrollBg"));
		assetM.load(assetID.get("scrollCorner"));
		assetM.load(assetID.get("scrollVScroll"));
		assetM.load(assetID.get("scrollVScrollKnob"));
		assetM.load(assetID.get("sbBg"));
		
		assetM.load(assetID.get("new"));
		
		game.bLayout.createAdditionalFont();
	}

	public void loadGameScreenAssets(){
		assetM.load(assetID.get("cc1"));
		assetM.load(assetID.get("cc2"));
		assetM.load(assetID.get("c1"));
		assetM.load(assetID.get("c2"));
		assetM.load(assetID.get("flip1"));
		assetM.load(assetID.get("flip2"));
		assetM.load(assetID.get("removeLast1"));
		assetM.load(assetID.get("removeLast2"));
		assetM.load(assetID.get("menu1"));
		assetM.load(assetID.get("menu2"));

		assetM.load(assetID.get("fTriA1"));
		assetM.load(assetID.get("fTriA2"));
		assetM.load(assetID.get("fTriB1"));
		assetM.load(assetID.get("fTriB2"));
		assetM.load(assetID.get("fSq1"));
		assetM.load(assetID.get("fSq2"));
		assetM.load(assetID.get("fRec1"));
		assetM.load(assetID.get("fRec2"));
		assetM.load(assetID.get("fRhom1"));
		assetM.load(assetID.get("fRhom2"));
		assetM.load(assetID.get("fTrapA1"));
		assetM.load(assetID.get("fTrapA2"));
		assetM.load(assetID.get("fTrapB1"));
		assetM.load(assetID.get("fTrapB2"));
		assetM.load(assetID.get("fParaA1"));
		assetM.load(assetID.get("fParaA2"));
		assetM.load(assetID.get("fParaB1"));
		assetM.load(assetID.get("fParaB2"));
		assetM.load(assetID.get("fRTrapA1"));
		assetM.load(assetID.get("fRTrapA2"));
		assetM.load(assetID.get("fRTrapB1"));
		assetM.load(assetID.get("fRTrapB2"));

		assetM.load(assetID.get("arrow1"));
		assetM.load(assetID.get("arrow2"));
		assetM.load(assetID.get("arrow3"));

		assetM.load(assetID.get("cut"));
		assetM.load(assetID.get("notCut"));

		assetM.load(assetID.get("oneTria"));
		assetM.load(assetID.get("twoTria"));
		assetM.load(assetID.get("oneSq"));
		assetM.load(assetID.get("perpendSq"));
		assetM.load(assetID.get("threeSq"));
		assetM.load(assetID.get("turn"));
		assetM.load(assetID.get("emptySq"));
		assetM.load(assetID.get("polySpace"));

		assetM.load(assetID.get("newDifficulty"));
		assetM.load(assetID.get("newTexture"));
		assetM.load(assetID.get("puzzleFailed"));
		assetM.load(assetID.get("puzzleSolved"));
		assetM.load(assetID.get("star"));

//		For TUTORIAL
		assetM.load(assetID.get("tipLayout"));

		assetM.setLoader(Sound.class, new SoundLoader(new InternalFileHandleResolver()));
		assetM.load(soundID.get("invalid"));
		assetM.load(soundID.get("normal"));
		assetM.load(soundID.get("win"));
		assetM.load(soundID.get("blip"));
		assetM.load(soundID.get("new"));
		if (game.pCType == SelectedChallenge.TIMED_PUZZLE) assetM.load(soundID.get("ticking"));
		
		game.bLayout.disposeAdditionalFont();

	}
	
	public void updateSelectedTextures(){
		setPolygonTextures();
	}
	
	private void setPolygonTextures(){
		pPolyAssets.setPolyTextures(game.pUpdater.getPValue().getByte("currentPolyTexture"));
		pPolyAssets.setBorderTextures(game.pUpdater.getPValue().getByte("currentBordTexture"));
	}

	public PolyNGonsPolyAsset getpPolyAssets() {
		return pPolyAssets;
	}
}
