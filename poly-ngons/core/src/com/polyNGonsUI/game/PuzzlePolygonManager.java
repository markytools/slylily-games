package com.polyNGonsUI.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.gameInputProcessors.game.PolygonActor;
import com.gameTools.game.MySprite;
import com.polyngons.game.GameScreen;
import com.polyNgonConstants.game.PolyNGonsDimConstants;
import com.polyNgonConstants.game.PolyNGonsGameSelection;
import com.polyNgonConstants.game.PolygonState;
import com.polygons.game.PolySquare;
import com.polygons.game.PolyTriangle;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;
import com.puzzleBorder.game.PuzzleBorderName;
import com.puzzleBorder.game.PuzzleBorderPoly;
import com.puzzleCreator.game.PolyNGonGenerator;

public class PuzzlePolygonManager {
	public int triASize = 0;
	public int triBSize = 0;
	public int sqSize = 0;
	public int recSize = 0;
	public int rhomSize = 0;
	public int trapASize = 0;
	public int trapBSize = 0;
	public int paraASize = 0;
	public int paraBSize = 0;
	public int rTrapASize = 0;
	public int rTrapBSize = 0;

	private PolyNGonGenerator puzzleGen;
	private GameScreen gScreen;
	private Array<PuzzleBorderPoly> puzzleBorders;

	private Array<PTextureReference> polyRegs;
	private ArrayMap<Polygon, PolygonActor> polysOnPuzzle;
	
	private boolean finishedRegUpdate;

	public PuzzlePolygonManager(){
		
	}

	public PuzzlePolygonManager(GameScreen gScreen, PolyNGonGenerator puzzleGen, Stage puzzleStage) {
		this.gScreen = gScreen;
		this.puzzleGen = puzzleGen;
		setPuzzleNums();
		addPuzzleBorders();

		polysOnPuzzle = new ArrayMap<Polygon, PolygonActor>();
		finishedRegUpdate = false;
	}

	public void renderPuzzlePolygons(){
		for (Polygon p : polysOnPuzzle.keys()) updatePolygons(p);
		if (gScreen.selectedPolygon != null){
			for (Polygon poly : polysOnPuzzle.keys()) if (poly != gScreen.selectedPolygon) drawAddedPoly(poly);
			drawAddedPoly(gScreen.selectedPolygon);
		} else for (Polygon poly : polysOnPuzzle.keys()) drawAddedPoly(poly);
		renderMarker();
	}

	private void drawAddedPoly(Polygon poly){
		polysOnPuzzle.get(poly).getPolySprite().draw(gScreen.game.batch, gScreen.getpCManager().getColorCode());
	}

	private void renderMarker() {
		Polygon selectedPoly = gScreen.selectedPolygon;
		if (selectedPoly != null){
			gScreen.getSettings().drawMarker(polysOnPuzzle.get(selectedPoly).getState() != PolygonState.FIXED 
					&& polysOnPuzzle.get(selectedPoly).getState() == PolygonState.FIXABLE);
		}
	}

	private void updatePolygons(Polygon poly) {
		PolygonActor polyActor = polysOnPuzzle.get(poly);
		polyActor.setActorImage(checkAssetReference(poly).getPixmap());
	}

	public float getPolyWidth(Polygon poly){
		return checkAssetReference(poly).getPolyW();
	}

	public float getPolyHeight(Polygon poly){
		return checkAssetReference(poly).getPolyH();
	}

	private PTextureReference checkAssetReference(Polygon poly){
		for (PTextureReference pTRef : polyRegs)
			if (poly.getName() == pTRef.getpName() &&
			poly.getPosition() == pTRef.getPosition() &&
			poly.isReversed() == pTRef.isRevearsed()) return pTRef;
		return null;
	}

	public void createPuzzleAssets(){
		polyRegs = new Array<PTextureReference>();

		if (triASize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.triA, 1, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.triA, 1, 1));
			polyRegs.add(new PTextureReference(gScreen, 3, false, PolygonName.triA, 1, 1));
			polyRegs.add(new PTextureReference(gScreen, 4, false, PolygonName.triA, 1, 1));
		}
		if (triBSize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.triB, 2, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.triB, 1, 2));
			polyRegs.add(new PTextureReference(gScreen, 3, false, PolygonName.triB, 2, 1));
			polyRegs.add(new PTextureReference(gScreen, 4, false, PolygonName.triB, 1, 2));
		}
		if (sqSize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.sq, 1, 1));
		}
		if (recSize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.rec, 2, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.rec, 1, 2));
		}
		if (rhomSize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.rhom, 2, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.rhom, 1, 2));
			polyRegs.add(new PTextureReference(gScreen, 1, true, PolygonName.rhom, 2, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, true, PolygonName.rhom, 1, 2));
		}
		if (trapASize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.trapA, 3, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.trapA, 1, 3));
			polyRegs.add(new PTextureReference(gScreen, 3, false, PolygonName.trapA, 3, 1));
			polyRegs.add(new PTextureReference(gScreen, 4, false, PolygonName.trapA, 1, 3));
		}
		if (trapBSize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.trapB, 2, 2));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.trapB, 2, 2));
			polyRegs.add(new PTextureReference(gScreen, 3, false, PolygonName.trapB, 2, 2));
			polyRegs.add(new PTextureReference(gScreen, 4, false, PolygonName.trapB, 2, 2));
		}
		if (paraASize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.paraA, 3, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.paraA, 1, 3));
			polyRegs.add(new PTextureReference(gScreen, 1, true, PolygonName.paraA, 3, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, true, PolygonName.paraA, 1, 3));
		}
		if (paraBSize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.paraB, 2, 3));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.paraB, 3, 2));
			polyRegs.add(new PTextureReference(gScreen, 1, true, PolygonName.paraB, 2, 3));
			polyRegs.add(new PTextureReference(gScreen, 2, true, PolygonName.paraB, 3, 2));
		}
		if (rTrapASize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.rTrapA, 2, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.rTrapA, 1, 2));
			polyRegs.add(new PTextureReference(gScreen, 3, false, PolygonName.rTrapA, 2, 1));
			polyRegs.add(new PTextureReference(gScreen, 4, false, PolygonName.rTrapA, 1, 2));
			polyRegs.add(new PTextureReference(gScreen, 1, true, PolygonName.rTrapA, 2, 1));
			polyRegs.add(new PTextureReference(gScreen, 2, true, PolygonName.rTrapA, 1, 2));
			polyRegs.add(new PTextureReference(gScreen, 3, true, PolygonName.rTrapA, 2, 1));
			polyRegs.add(new PTextureReference(gScreen, 4, true, PolygonName.rTrapA, 1, 2));
		}
		if (rTrapBSize != 0){
			polyRegs.add(new PTextureReference(gScreen, 1, false, PolygonName.rTrapB, 3, 2));
			polyRegs.add(new PTextureReference(gScreen, 2, false, PolygonName.rTrapB, 2, 3));
			polyRegs.add(new PTextureReference(gScreen, 3, false, PolygonName.rTrapB, 3, 2));
			polyRegs.add(new PTextureReference(gScreen, 4, false, PolygonName.rTrapB, 2, 3));
			polyRegs.add(new PTextureReference(gScreen, 1, true, PolygonName.rTrapB, 3, 2));
			polyRegs.add(new PTextureReference(gScreen, 2, true, PolygonName.rTrapB, 2, 3));
			polyRegs.add(new PTextureReference(gScreen, 3, true, PolygonName.rTrapB, 3, 2));
			polyRegs.add(new PTextureReference(gScreen, 4, true, PolygonName.rTrapB, 2, 3));
		}

		for (PTextureReference pTRef : polyRegs){
			pTRef.setpRegFixed(gScreen.getGSpCreator().getTextStringMap().get(PolygonName.getPolygonName(pTRef.getpName()) + "N"));
			pTRef.setpRegFixable(gScreen.getGSpCreator().getTextStringMap().get(PolygonName.getPolygonName(pTRef.getpName()) + "F"));
			pTRef.setpRegUnfixable(gScreen.getGSpCreator().getTextStringMap().get(PolygonName.getPolygonName(pTRef.getpName()) + "UF"));
		}
	}

	private void addPuzzleBorders() {
		puzzleBorders = new Array<PuzzleBorderPoly>();
		for (PuzzleBorderPoly border : puzzleGen.getPuzzleBorderCreator().getBorders()) puzzleBorders.add(border);
		for (PuzzleBorderPoly border : puzzleGen.getPuzzleBorderCreator().getEmptyBorders()) puzzleBorders.add(border);
	}

	public void addPolyToPuzzle(float x, float y){
		Button currentSelectedB = gScreen.getUiManager().getUi().getCurrentSelectedB();
		ArrayMap<String, Button> polyBMap = gScreen.getUiManager().getUi().getPolyButtonsMap();

		if (currentSelectedB != null){
			if (currentSelectedB.isChecked()){
				currentSelectedB.setChecked(false);

				if (gScreen.selectedPolygon != null) {
					gScreen.getpListener().setSendMessage(false);
					polysOnPuzzle.removeIndex(polysOnPuzzle.indexOfKey(gScreen.selectedPolygon));
				}

				Polygon poly;
				switch (polyBMap.getKey(currentSelectedB, true)){
				case "triA": {
					poly = createPolygon(PolygonName.triA, x, y);
				}; break;
				case "triB": {
					poly = createPolygon(PolygonName.triB, x, y);
				}; break;
				case "sq": {
					poly = createPolygon(PolygonName.sq, x, y);
				}; break;
				case "rec": {
					poly = createPolygon(PolygonName.rec, x, y);
				}; break;
				case "rhom": {
					poly = createPolygon(PolygonName.rhom, x, y);
				}; break;
				case "trapA": {
					poly = createPolygon(PolygonName.trapA, x, y);
				}; break;
				case "trapB": {
					poly = createPolygon(PolygonName.trapB, x, y);
				}; break;
				case "paraA": {
					poly = createPolygon(PolygonName.paraA, x, y);
				}; break;
				case "paraB": {
					poly = createPolygon(PolygonName.paraB, x, y);
				}; break;
				case "rTrapA": {
					poly = createPolygon(PolygonName.rTrapA, x, y);
				}; break;
				case "rTrapB": {
					poly = createPolygon(PolygonName.rTrapB, x, y);
				}; break;
				default: poly = null; break;
				}

				if (poly != null && gScreen.game.gSelection == PolyNGonsGameSelection.TUTORIAL) 
					gScreen.gettUIManager().updateTutorial(6);
				gScreen.selectedPolygon = poly;
				polysOnPuzzle.put(poly, null);
				PolygonActor polyActor = createPolyActor(poly);
				setSelectedPolyState();
				addSprites(polyActor);
			}
		}
	}

	private void addSprites(PolygonActor polyActor) {
		if (polyActor != null){
			Polygon poly = polyActor.getActorPoly();
			MySprite sp = gScreen.getGSpCreator().getPolySprite(polyActor.getState(), poly);
			polyActor.setPolySprite(sp);
		}
	}

	private PolygonActor createPolyActor(Polygon poly){
		if (poly != null){
			PolygonActor polyActor = new PolygonActor(gScreen, checkAssetReference(poly).getPixmap(), poly);
			polysOnPuzzle.put(poly, polyActor);
			return polyActor;
		}
		return null;
	}

	private Polygon createPolygon(byte polyID, float x, float y){
		float xPos;
		float yPos;

		if (x / PolyNGonsDimConstants.PUZZLE_SCALE > gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxX()){
			xPos = gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxX();
		} else xPos = (float) Math.floor(x / PolyNGonsDimConstants.PUZZLE_SCALE);
		if (y / PolyNGonsDimConstants.PUZZLE_SCALE > gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxY()){
			yPos = gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxY();
		} else yPos = (float) Math.floor(y / PolyNGonsDimConstants.PUZZLE_SCALE);

		return gScreen.getPuzzleGen().getPolyCreator().createPolygon(polyID, (int)xPos, (int)yPos, false, 1);
	}

	public void setSelectedPolyState(){
		if (gScreen.selectedPolygon != null){
			PolygonActor pActor = polysOnPuzzle.get(gScreen.selectedPolygon);
			if (checkIfFixablePolygon(gScreen.selectedPolygon, polysOnPuzzle)){
				pActor.setState(PolygonState.FIXABLE);
			}
			else {
				pActor.setState(PolygonState.UNFIXABLE);
			}
		}
	}

	private boolean checkIfFixablePolygon(Polygon selectedPoly, ArrayMap<Polygon, PolygonActor> fixedPoly){

		for (Polygon poly : fixedPoly.keys()){
			if (selectedPoly != poly){
				for (PolyTriangle tria1 : poly.triangles()){
					for (PolyTriangle tria2 : selectedPoly.triangles()){
						if (!puzzleGen.checkIfNoOtherTriangles(tria1, tria2)) return false;
					}

					for (PolySquare sq1 : selectedPoly.squares()){
						if (tria1.xPos == sq1.xPos &&
								tria1.yPos == sq1.yPos) return false;
					}
				}

				for (PolySquare sq1 : poly.squares()){
					for (PolySquare sq2 : selectedPoly.squares()){
						if (sq1.xPos == sq2.xPos &&
								sq1.yPos == sq2.yPos) return false;
					}
					for (PolyTriangle tri : selectedPoly.triangles()){
						if (sq1.xPos == tri.xPos &&
								sq1.yPos == tri.yPos) return false;
					}
				}
			}
		}

		boolean blockBorder =  checkForBorders(selectedPoly);
		if (!blockBorder) return true;
		else return false;
	}

	private boolean checkForBorders(Polygon selectedPoly){
		for (PuzzleBorderPoly border : puzzleBorders){
			for (PolySquare sq : selectedPoly.squares()){
				if (sq.xPos == border.xPos() &&
						sq.yPos == border.yPos()) return true;
			}

			for (PolyTriangle tri : selectedPoly.triangles()){
				if (tri.xPos == border.xPos() &&
						tri.yPos == border.yPos()) if (!checkTriangleFitsBorder(tri, border)) return true;
			}
		}

		return false;
	}

	private boolean checkTriangleFitsBorder(PolyTriangle tri, PuzzleBorderPoly border){
		switch (tri.position){
		case 1: if ((border.bName() == PuzzleBorderName.oneTria && border.getPos() == 3) ||
				(border.bName() == PuzzleBorderName.twoTria && border.getPos() == 3 && !border.isRevearsed()) ||
				(border.bName() == PuzzleBorderName.twoTria && border.getPos() == 2 && border.isRevearsed()) ||
				(border.bName() == PuzzleBorderName.threeTria && border.getPos() == 3)) return true; 
		else return false;
		case 2: if ((border.bName() == PuzzleBorderName.oneTria && border.getPos() == 4) ||
				(border.bName() == PuzzleBorderName.twoTria && border.getPos() == 4 && !border.isRevearsed()) ||
				(border.bName() == PuzzleBorderName.twoTria && border.getPos() == 1 && border.isRevearsed()) ||
				(border.bName() == PuzzleBorderName.threeTria && border.getPos() == 4)) return true;
		else return false;
		case 3: if ((border.bName() == PuzzleBorderName.oneTria && border.getPos() == 1) ||
				(border.bName() == PuzzleBorderName.twoTria && border.getPos() == 1 && !border.isRevearsed()) ||
				(border.bName() == PuzzleBorderName.twoTria && border.getPos() == 4 && border.isRevearsed()) ||
				(border.bName() == PuzzleBorderName.threeTria && border.getPos() == 1)) return true;
		else return false;
		case 4: if ((border.bName() == PuzzleBorderName.oneTria && border.getPos() == 2) ||
				(border.bName() == PuzzleBorderName.twoTria && border.getPos() == 2 && !border.isRevearsed()) ||
				(border.bName() == PuzzleBorderName.twoTria && border.getPos() == 3 && border.isRevearsed()) ||
				(border.bName() == PuzzleBorderName.threeTria && border.getPos() == 2)) return true;
		else return false;
		default: return true;
		}

	}

	private void setPuzzleNums(){
		for (int i = puzzleGen.getPuzzlePolygon().size; i > 0; i--){
			switch (puzzleGen.getPuzzlePolygon().get(i - 1).getName()){
			case PolygonName.triA: triASize += 1; break;
			case PolygonName.triB: triBSize += 1; break;
			case PolygonName.sq: sqSize += 1; break;
			case PolygonName.rec: recSize += 1; break;
			case PolygonName.rhom: rhomSize += 1; break;
			case PolygonName.trapA: trapASize += 1; break;
			case PolygonName.trapB: trapBSize += 1; break;
			case PolygonName.paraA: paraASize += 1; break;
			case PolygonName.paraB: paraBSize += 1; break;
			case PolygonName.rTrapA: rTrapASize += 1; break;
			case PolygonName.rTrapB: rTrapBSize += 1; break;
			default: break;
			}
		}
	}

	public int getPolyLeft(byte polyID){
		int polyLeft = 0;
		for (Polygon poly : polysOnPuzzle.keys()){
			if (polyID == poly.getName()){
				polyLeft += 1;
			}
		}
		switch (polyID){
		case PolygonName.triA: return triASize - polyLeft;
		case PolygonName.triB: return triBSize - polyLeft;
		case PolygonName.sq: return sqSize - polyLeft;
		case PolygonName.rec: return recSize - polyLeft;
		case PolygonName.rhom: return rhomSize - polyLeft;
		case PolygonName.trapA: return trapASize - polyLeft;
		case PolygonName.trapB: return trapBSize - polyLeft;
		case PolygonName.paraA: return paraASize - polyLeft;
		case PolygonName.paraB: return paraBSize - polyLeft;
		case PolygonName.rTrapA: return rTrapASize - polyLeft;
		case PolygonName.rTrapB: return rTrapBSize - polyLeft;
		default: return 0;
		}
	}

	public boolean checkMoreThanFourPoly(){
		int count = 0;
		if (triASize != 0) count++;
		if (triBSize != 0) count++;
		if (sqSize != 0) count++;
		if (recSize != 0) count++;
		if (rhomSize != 0) count++;
		if (trapASize != 0) count++;
		if (trapBSize != 0) count++;
		if (paraASize != 0) count++;
		if (paraBSize != 0) count++;
		if (rTrapASize != 0) count++;
		if (rTrapBSize != 0) count++;
		if (count > 4) return true;
		else return false;
	}

	public void clearPolysOnPuzzle() {
		polysOnPuzzle.clear();
	}

	public ArrayMap<Polygon, PolygonActor> getPolysOnPuzzle() {
		return polysOnPuzzle;
	}
	
	public void dispose(){
		for (PTextureReference pRef : polyRegs){
			pRef.dispose();
		}
	}

	public boolean isFinishedRegUpdate() {
		return finishedRegUpdate;
	}

	public void setFinishedRegUpdate(boolean finishedRegUpdate) {
		this.finishedRegUpdate = finishedRegUpdate;
		
	}
}
