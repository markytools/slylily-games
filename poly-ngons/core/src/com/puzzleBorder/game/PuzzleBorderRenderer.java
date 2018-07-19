package com.puzzleBorder.game;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.polyngons.game.GameScreen;
import com.puzzleCreator.game.PolyNGonGenerator;

public class PuzzleBorderRenderer {
	private GameScreen gScreen;
	private PolyNGonGenerator puzzleGen;

	private HashMap<PuzzleBorderPoly, Sprite> turnBorderRegs;
	private HashMap<PuzzleBorderPoly, Sprite> innerBorderRegs;
	private HashMap<PuzzleBorderPoly, Sprite> emptyBorderRegs;
	private HashMap<PuzzleBorderPoly, Sprite> normalBorderRegs;

//	Uncomment to test
//	private HashMap<Polygon, Sprite> pPoly;
	
	public PuzzleBorderRenderer(GameScreen gScreen){
		this.gScreen = gScreen;
		puzzleGen = gScreen.getPuzzleGen();

		innerBorderRegs = new HashMap<PuzzleBorderPoly, Sprite>();
		emptyBorderRegs = new HashMap<PuzzleBorderPoly, Sprite>();
		turnBorderRegs = new HashMap<PuzzleBorderPoly, Sprite>();
		normalBorderRegs = new HashMap<PuzzleBorderPoly, Sprite>();

//		Uncomment to test
//		pPoly = new HashMap<Polygon, Sprite>();
		
		createBorderSprites();
	}
	
	private void createBorderSprites() {
		for (PuzzleBorderPoly bPoly : puzzleGen.getPuzzleBorderCreator().getBorders()){
			normalBorderRegs.put(bPoly, gScreen.getGSpCreator().getBorderRegion(bPoly));
		}

		for (PuzzleBorderPoly bPoly : puzzleGen.getPuzzleBorderCreator().getInnerBorders()){
			innerBorderRegs.put(bPoly, gScreen.getGSpCreator().getBorderRegion(bPoly));
		}

		for (PuzzleBorderPoly bPoly : puzzleGen.getPuzzleBorderCreator().getTurnBorders()){
			turnBorderRegs.put(bPoly, gScreen.getGSpCreator().getBorderRegion(bPoly));
		}

		for (PuzzleBorderPoly bPoly : puzzleGen.getPuzzleBorderCreator().getEmptyBorders()){
			emptyBorderRegs.put(bPoly, gScreen.getGSpCreator().getBorderRegion(bPoly));
		}

//		Uncomment to test
//		for (Polygon poly : gScreen.getPuzzleGen().getPuzzlePolygon()){
//			pPoly.put(poly, gScreen.game.assetLoader.getpPolyAssets().getpSpritePool().getPolySprite(
//					PolygonState.FIXED, poly.getName(), poly.getPosition(),
//					poly.getPolyXPos() * PolyNGonsDimConstants.PUZZLE_SCALE, 
//					poly.getPolyYPos() * PolyNGonsDimConstants.PUZZLE_SCALE, poly.isReversed()));
//		}
	}
	
	

	public void renderBorders(){
		for (Sprite sp : innerBorderRegs.values()) sp.draw(gScreen.game.batch);
		for (Sprite sp : emptyBorderRegs.values()) sp.draw(gScreen.game.batch);
		for (Sprite sp : normalBorderRegs.values()) sp.draw(gScreen.game.batch);
		for (Sprite sp : turnBorderRegs.values()) sp.draw(gScreen.game.batch);
		
//		Uncomment to test
//		for (Sprite sp : pPoly.values()) sp.draw(gScreen.game.batch);
	}
	
	public void dispose(){
		turnBorderRegs.clear();
		innerBorderRegs.clear();
		emptyBorderRegs.clear();
		normalBorderRegs.clear();
	}
}
