package com.puzzleCompletion.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.gameInputProcessors.game.PolygonActor;
import com.polyNgonConstants.game.PolygonState;
import com.polygons.game.PolySquare;
import com.polygons.game.PolyTriangle;
import com.polygons.game.Polygon;
import com.puzzleCreator.game.PolyNGonGenerator;

public class PuzzleCompletionScanner {
	public Array<PolySquare> polySquares;
	public Array<PolyTriangle> polyTriangles;

	public PuzzleCompletionScanner(PolyNGonGenerator pGen){
		polySquares = new Array<PolySquare>();
		polyTriangles = new Array<PolyTriangle>();
		addPolyParts(pGen.getPuzzlePolygon());
	}

	private void addPolyParts(Array<Polygon> puzzlePoly){
		for (Polygon poly : puzzlePoly){
			for (PolySquare sq : poly.squares()){
				polySquares.add(sq);
			}
			for (PolyTriangle tri : poly.triangles()){
				polyTriangles.add(tri);
			}
		}
	}

	public boolean checkIfPuzzleComplete(ArrayMap<Polygon, PolygonActor> polysOnPuzzle){
		for (PolySquare fSq : polySquares){
			if (checkSqBySq(fSq, polysOnPuzzle) || chechSqByTri(fSq, polysOnPuzzle)) continue;
			else return false;
		}

		for (PolyTriangle fTri : polyTriangles){
			if (checkTriBySq(fTri, polysOnPuzzle) || checkTriByTri(fTri, polysOnPuzzle)) continue;
			else return false;
		}
		return true;
	}

	private boolean checkSqBySq(PolySquare fSq , ArrayMap<Polygon, PolygonActor> polysOnPuzzle){
		for (Polygon poly : polysOnPuzzle.keys()){
			for (PolySquare sq : poly.squares()){
				if (fSq.xPos == sq.xPos && fSq.yPos == sq.yPos &&
						polysOnPuzzle.get(poly).getState() == PolygonState.FIXED){
					return true;
				}
			}
		}
		return false;
	}

	private boolean chechSqByTri(PolySquare fSq , ArrayMap<Polygon, PolygonActor> polysOnPuzzle){
		for (int i = polysOnPuzzle.size; i > 0; i--){
			for (int i2 = polysOnPuzzle.size; i2 > 0; i2--){
				Polygon poly1 = polysOnPuzzle.getKeyAt(i - 1);
				Polygon poly2 = polysOnPuzzle.getKeyAt(i2 - 1);

				boolean sqOccupied = checkPolygons(poly1, poly2, fSq, polysOnPuzzle);
				if (sqOccupied) return true;
			}
		}
		return false;
	}

	private boolean checkTriBySq(PolyTriangle fTri, ArrayMap<Polygon, PolygonActor> polysOnPuzzle){
		for (Polygon poly : polysOnPuzzle.keys()){
			for (PolySquare sq : poly.squares()){
				if (fTri.xPos == sq.xPos && fTri.yPos == sq.yPos &&
						polysOnPuzzle.get(poly).getState() == PolygonState.FIXED){
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkTriByTri(PolyTriangle fTri, ArrayMap<Polygon, PolygonActor> polysOnPuzzle){

		for (int p1 = polysOnPuzzle.size - 1; p1 >= 0; p1--){
			Polygon poly = polysOnPuzzle.getKeyAt(p1);
			for (int i = poly.triangles().size - 1; i >= 0; i--){
				PolyTriangle tri1 = poly.triangles().get(i);
				if (fTri.xPos == tri1.xPos && fTri.yPos == tri1.yPos &&
						polysOnPuzzle.get(poly).getState() == PolygonState.FIXED){
					if (fTri.position != tri1.position){
						int otherHalfPos = otherHalfTriPos(fTri, tri1);
						if (otherHalfPos != 0){
							for (int p2 = polysOnPuzzle.size - 1; p2 >= 0; p2--){
								Polygon poly2 = polysOnPuzzle.getKeyAt(p2);
								for (int i2 = poly2.triangles().size - 1; i2 >= 0; i2--){
									PolyTriangle tri2 = poly2.triangles().get(i2);
									if (fTri.xPos == tri2.xPos && fTri.yPos == tri2.yPos &&
											polysOnPuzzle.get(poly2).getState() == PolygonState.FIXED){
										if (tri1 != tri2 && tri2.position == otherHalfPos){
											return true;
										}
									}
								}
							}
						}
					}
					else return true;
				}
			}
		}
		return false;
	}

	private int otherHalfTriPos(PolyTriangle pPolyTri, PolyTriangle onPuzzleTri){
		switch (pPolyTri.position){
		case 1: {
			switch (onPuzzleTri.position){
			case 1: return 0;
			case 2: return 4;
			case 3: return 0;
			case 4: return 2;
			default: return 0;
			}
		}
		case 2: {
			switch (onPuzzleTri.position){
			case 1: return 3;
			case 2: return 0;
			case 3: return 1;
			case 4: return 0;
			default: return 0;
			}
		}
		case 3: {
			switch (onPuzzleTri.position){
			case 1: return 0;
			case 2: return 4;
			case 3: return 0;
			case 4: return 2;
			default: return 0;
			}
		}
		case 4: {
			switch (onPuzzleTri.position){
			case 1: return 3;
			case 2: return 0;
			case 3: return 1;
			case 4: return 0;
			default: return 0;
			}
		}
		default: return 0;
		}
	}

	private boolean checkPolygons(Polygon poly1, Polygon poly2, PolySquare fSq,
			ArrayMap<Polygon, PolygonActor> polysOnPuzzle){
		for (int i = poly1.triangles().size; i > 0; i--){
			for (int i2 = poly2.triangles().size; i2 > 0; i2--){
				PolyTriangle tri1 = poly1.triangles().get(i - 1);
				PolyTriangle tri2 = poly2.triangles().get(i2 - 1);
				if (tri1 != tri2 && !tri1.equals(tri2)){
					if (tri1.xPos == fSq.xPos &&
							tri1.yPos == fSq.yPos &&
							tri2.xPos == fSq.xPos &&
							tri2.yPos == fSq.yPos) {
						if (tri1.xPos == tri2.xPos && tri1.yPos == tri2.yPos &&
								polysOnPuzzle.get(tri1.parentPoly).getState() == PolygonState.FIXED &&
								polysOnPuzzle.get(tri2.parentPoly).getState() == PolygonState.FIXED){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
