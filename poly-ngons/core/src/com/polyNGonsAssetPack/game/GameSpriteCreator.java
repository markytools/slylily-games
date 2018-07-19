package com.polyNGonsAssetPack.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;
import com.gameTools.game.MySprite;
import com.polyngons.game.GameScreen;
import com.polyNgonConstants.game.PolyNGonsDimConstants;
import com.polyNgonConstants.game.PolygonState;
import com.polygons.game.Polygon;
import com.puzzleBorder.game.PuzzleBorderPoly;

public class GameSpriteCreator {
	private Texture triAN, triAF,triAUF, triBN, triBF,triBUF, sqN, sqF, sqUF, recN, recF, recUF, rhomN, rhomF, rhomUF, trapAN, trapAF,
	trapAUF, trapBN, trapBF, trapBUF, paraAN, paraAF, paraAUF, paraBN, paraBF, paraBUF, rTrapAN, rTrapAF, rTrapAUF, rTrapBN, rTrapBF,
	rTrapBUF;
	private Texture oneTria, twoTria, oneSq, perpendSq, threeSq, turn, emptySq;
	private Texture polySpace;
	private ArrayMap<String, Texture> textStringMap;
	private GameScreen gScreen;

	public GameSpriteCreator(GameScreen gScreen){
		this.gScreen = gScreen;

		triAN = gScreen.game.assetLoader.getpPolyAssets().triAN;
		triBN = gScreen.game.assetLoader.getpPolyAssets().triBN;
		recN = gScreen.game.assetLoader.getpPolyAssets().recN;
		sqN = gScreen.game.assetLoader.getpPolyAssets().sqN;
		rhomN = gScreen.game.assetLoader.getpPolyAssets().rhomN;
		trapAN = gScreen.game.assetLoader.getpPolyAssets().trapAN;
		trapBN = gScreen.game.assetLoader.getpPolyAssets().trapBN;
		paraAN = gScreen.game.assetLoader.getpPolyAssets().paraAN;
		paraBN = gScreen.game.assetLoader.getpPolyAssets().paraBN;
		rTrapAN = gScreen.game.assetLoader.getpPolyAssets().rTrapAN;
		rTrapBN = gScreen.game.assetLoader.getpPolyAssets().rTrapBN;

		triAF = gScreen.game.assetLoader.getpPolyAssets().triAF;
		triBF = gScreen.game.assetLoader.getpPolyAssets().triBF;
		recF = gScreen.game.assetLoader.getpPolyAssets().recF;
		sqF = gScreen.game.assetLoader.getpPolyAssets().sqF;
		rhomF = gScreen.game.assetLoader.getpPolyAssets().rhomF;
		trapAF = gScreen.game.assetLoader.getpPolyAssets().trapAF;
		trapBF = gScreen.game.assetLoader.getpPolyAssets().trapBF;
		paraAF = gScreen.game.assetLoader.getpPolyAssets().paraAF;
		paraBF = gScreen.game.assetLoader.getpPolyAssets().paraBF;
		rTrapAF = gScreen.game.assetLoader.getpPolyAssets().rTrapAF;
		rTrapBF = gScreen.game.assetLoader.getpPolyAssets().rTrapBF;

		triAUF = gScreen.game.assetLoader.getpPolyAssets().triAUF;
		triBUF = gScreen.game.assetLoader.getpPolyAssets().triBUF;
		recUF = gScreen.game.assetLoader.getpPolyAssets().recUF;
		sqUF = gScreen.game.assetLoader.getpPolyAssets().sqUF;
		rhomUF = gScreen.game.assetLoader.getpPolyAssets().rhomUF;
		trapAUF = gScreen.game.assetLoader.getpPolyAssets().trapAUF;
		trapBUF = gScreen.game.assetLoader.getpPolyAssets().trapBUF;
		paraAUF = gScreen.game.assetLoader.getpPolyAssets().paraAUF;
		paraBUF = gScreen.game.assetLoader.getpPolyAssets().paraBUF;
		rTrapAUF = gScreen.game.assetLoader.getpPolyAssets().rTrapAUF;
		rTrapBUF = gScreen.game.assetLoader.getpPolyAssets().rTrapBUF;

		oneTria = gScreen.game.assetLoader.getpPolyAssets().oneTria;
		twoTria = gScreen.game.assetLoader.getpPolyAssets().twoTria;
		oneSq = gScreen.game.assetLoader.getpPolyAssets().oneSq;
		perpendSq = gScreen.game.assetLoader.getpPolyAssets().perpendSq;
		threeSq = gScreen.game.assetLoader.getpPolyAssets().threeSq;
		turn = gScreen.game.assetLoader.getpPolyAssets().turn;
		emptySq = gScreen.game.assetLoader.getpPolyAssets().emptySq;
		polySpace = gScreen.game.assetLoader.getpPolyAssets().polySpace;
		
		textStringMap = new ArrayMap<String, Texture>();
		textStringMap.put("triAN", triAN);
		textStringMap.put("triAF", triAF);
		textStringMap.put("triAUF", triAUF);
		textStringMap.put("triBN", triBN);
		textStringMap.put("triBF", triBF);
		textStringMap.put("triBUF", triBUF);
		textStringMap.put("sqN", sqN);
		textStringMap.put("sqF", sqF);
		textStringMap.put("sqUF", sqUF);
		textStringMap.put("recN", recN);
		textStringMap.put("recF", recF);
		textStringMap.put("recUF", recUF);
		textStringMap.put("rhomN", rhomN);
		textStringMap.put("rhomF", rhomF);
		textStringMap.put("rhomUF", rhomUF);
		textStringMap.put("trapAN", trapAN);
		textStringMap.put("trapAF", trapAF);
		textStringMap.put("trapAUF", trapAUF);
		textStringMap.put("trapBN", trapBN);
		textStringMap.put("trapBF", trapBF);
		textStringMap.put("trapBUF", trapBUF);
		textStringMap.put("paraAN", paraAN);
		textStringMap.put("paraAF", paraAF);
		textStringMap.put("paraAUF", paraAUF);
		textStringMap.put("paraBN", paraBN);
		textStringMap.put("paraBF", paraBF);
		textStringMap.put("paraBUF", paraBUF);
		textStringMap.put("rTrapAN", rTrapAN);
		textStringMap.put("rTrapAF", rTrapAF);
		textStringMap.put("rTrapAUF", rTrapAUF);
		textStringMap.put("rTrapBN", rTrapBN);
		textStringMap.put("rTrapBF", rTrapBF);
		textStringMap.put("rTrapBUF", rTrapBUF);
		
		textStringMap.put("oneTria", oneTria);
		textStringMap.put("twoTria", twoTria);
		textStringMap.put("oneSq", oneSq);
		textStringMap.put("perpendSq", perpendSq);
		textStringMap.put("threeSq", threeSq);
		textStringMap.put("turn", turn);
		textStringMap.put("polySpace", polySpace);
		textStringMap.put("emptySq", emptySq);
	}

	public MySprite getPolySprite(PolygonState state, Polygon polygon){
		final byte polyName = polygon.getName();
		final int position = polygon.getPosition();
		final boolean revearsed = polygon.isReversed();

		float xPos = polygon.getPolyXPos();
		float yPos = polygon.getPolyYPos();
		float polyWidth = gScreen.getPpManager().getPolyWidth(polygon);
		float polyHeight = gScreen.getPpManager().getPolyHeight(polygon);
		if (xPos + polyWidth > gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxX() - 2){
			xPos -= xPos + polyWidth - (gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxX() - 2);
		}
		if (yPos + polyHeight > gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxY() + 1){
			yPos -= yPos + polyHeight - (gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxY() + 1);
		}

		polygon.setPolyXPos(xPos);
		polygon.setPolyYPos(yPos);

		float newXPos = xPos * PolyNGonsDimConstants.PUZZLE_SCALE;
		float newYPos = yPos * PolyNGonsDimConstants.PUZZLE_SCALE;


		return gScreen.game.assetLoader.getpPolyAssets().getpSpritePool().getPolySprite(
				state, polyName, position, newXPos, newYPos, revearsed);
	}

	public MySprite getBorderRegion(PuzzleBorderPoly bPoly){
		float xPos = bPoly.xPos();
		float yPos = bPoly.yPos();
		byte borderID = bPoly.bName();
		int pos = bPoly.getPos();
		boolean rev = bPoly.isRevearsed();

		float newXPos = xPos * PolyNGonsDimConstants.PUZZLE_SCALE;
		float newYPos = yPos * PolyNGonsDimConstants.PUZZLE_SCALE;

		return gScreen.game.assetLoader.getpPolyAssets().getpSpritePool().getBordSprite(
				newXPos, newYPos, borderID, rev, pos);
	}

	public TextureRegion getPolySpaceReg() { return new TextureRegion(polySpace); }

	public ArrayMap<String, Texture> getTextStringMap() {
		return textStringMap;
	}
}
