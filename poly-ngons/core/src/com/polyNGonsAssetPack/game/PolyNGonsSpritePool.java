package com.polyNGonsAssetPack.game;

import com.gameTools.game.MySprite;
import com.polyNgonConstants.game.PolyNGonsDimConstants;
import com.polyNgonConstants.game.PolygonState;
import com.polygons.game.PolygonName;
import com.puzzleBorder.game.PuzzleBorderName;

public class PolyNGonsSpritePool {
	private PolyNGonsPolyAsset pPAsset;
	
	public PolyNGonsSpritePool(PolyNGonsPolyAsset pPAsset){
		this.pPAsset = pPAsset;
	}
	
	public MySprite getPolySprite(PolygonState state, byte polyName, int position, float newXPos,
			float newYPos, boolean revearsed){
		MySprite poly = null;
		switch (state){
		case FIXED: {
			switch (polyName){
			case PolygonName.triA: {
				poly = new MySprite(pPAsset.triAN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.triB: {
				poly = new MySprite(pPAsset.triBN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.sq: {
				poly = new MySprite(pPAsset.sqN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rec: {
				poly = new MySprite(pPAsset.recN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rhom: {
				poly = new MySprite(pPAsset.rhomN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.trapA: {
				poly = new MySprite(pPAsset.trapAN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.trapB: {
				poly = new MySprite(pPAsset.trapBN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.paraA: {
				poly = new MySprite(pPAsset.paraAN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.paraB: {
				poly = new MySprite(pPAsset.paraBN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rTrapA: {
				poly = new MySprite(pPAsset.rTrapAN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rTrapB: {
				poly = new MySprite(pPAsset.rTrapBN);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			default: break;
			}
		}; break;
		case FIXABLE: {
			switch (polyName){
			case PolygonName.triA: {
				poly = new MySprite(pPAsset.triAF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.triB: {
				poly = new MySprite(pPAsset.triBF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.sq: {
				poly = new MySprite(pPAsset.sqF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rec: {
				poly = new MySprite(pPAsset.recF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rhom: {
				poly = new MySprite(pPAsset.rhomF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.trapA: {
				poly = new MySprite(pPAsset.trapAF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.trapB: {
				poly = new MySprite(pPAsset.trapBF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.paraA: {
				poly = new MySprite(pPAsset.paraAF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.paraB: {
				poly = new MySprite(pPAsset.paraBF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rTrapA: {
				poly = new MySprite(pPAsset.rTrapAF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rTrapB: {
				poly = new MySprite(pPAsset.rTrapBF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			default: break;
			}
		}; break;
		case UNFIXABLE: {
			switch (polyName){
			case PolygonName.triA: {
				poly = new MySprite(pPAsset.triAUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.triB: {
				poly = new MySprite(pPAsset.triBUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.sq: {
				poly = new MySprite(pPAsset.sqUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rec: {
				poly = new MySprite(pPAsset.recUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rhom: {
				poly = new MySprite(pPAsset.rhomUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.trapA: {
				poly = new MySprite(pPAsset.trapAUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.trapB: {
				poly = new MySprite(pPAsset.trapBUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.paraA: {
				poly = new MySprite(pPAsset.paraAUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.paraB: {
				poly = new MySprite(pPAsset.paraBUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rTrapA: {
				poly = new MySprite(pPAsset.rTrapAUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			case PolygonName.rTrapB: {
				poly = new MySprite(pPAsset.rTrapBUF);
				poly.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
				switch (position){
				case 1: poly.setRotation(0); break;
				case 2: poly.setRotation(-90); break;
				case 3: poly.setRotation(-180); break;
				case 4: poly.setRotation(-270); break;
				default: break;
				}
				if (!revearsed) poly.setFlip(false, false);
				else {
					if (position % 2 != 0) poly.setFlip(true, false);
					else poly.setFlip(false, true);
				}
				poly.setProperPosition(newXPos, newYPos);
			}; break;
			default: break;
			}
		}; break;
		default: break;
		}

		return poly;
	}
	
	public MySprite getBordSprite(float newXPos, float newYPos, byte borderID, boolean rev, int pos){
		MySprite bord = null;
		switch (borderID){
		case PuzzleBorderName.oneTria: {
			bord = new MySprite(pPAsset.oneTria);
			bord.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
			switch (pos){
			case 1: bord.setRotation(0); break;
			case 2: bord.setRotation(-90); break;
			case 3: bord.setRotation(-180); break;
			case 4: bord.setRotation(-270); break;
			default: break;
			}
			bord.setProperPosition(newXPos, newYPos);
		}; break;
		case PuzzleBorderName.twoTria: {
			bord = new MySprite(pPAsset.twoTria);
			bord.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
			switch (pos){
			case 1: bord.setRotation(0); break;
			case 2: bord.setRotation(-90); break;
			case 3: bord.setRotation(-180); break;
			case 4: bord.setRotation(-270); break;
			default: break;
			}
			if (!rev) bord.setFlip(false, false);
			else {
				if (pos % 2 != 0) bord.setFlip(true, false);
				else bord.setFlip(false, true);
			}
			bord.setProperPosition(newXPos, newYPos);
		}; break;
		case PuzzleBorderName.oneSq: {
			bord = new MySprite(pPAsset.oneSq);
			bord.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
			switch (pos){
			case 1: bord.setRotation(0); break;
			case 2: bord.setRotation(-90); break;
			case 3: bord.setRotation(-180); break;
			case 4: bord.setRotation(-270); break;
			default: break;
			}
			bord.setProperPosition(newXPos, newYPos);
		}; break;
		case PuzzleBorderName.perpendSq: {
			bord = new MySprite(pPAsset.perpendSq);
			bord.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
			switch (pos){
			case 1: bord.setRotation(0); break;
			case 2: bord.setRotation(-90); break;
			case 3: bord.setRotation(-180); break;
			case 4: bord.setRotation(-270); break;
			default: break;
			}
			bord.setProperPosition(newXPos, newYPos);
		}; break;
		case PuzzleBorderName.threeSq: {
			bord = new MySprite(pPAsset.threeSq);
			bord.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
			switch (pos){
			case 1: bord.setRotation(0); break;
			case 2: bord.setRotation(-90); break;
			case 3: bord.setRotation(-180); break;
			case 4: bord.setRotation(-270); break;
			default: break;
			}
			bord.setProperPosition(newXPos, newYPos);
		}; break;
		case PuzzleBorderName.turn: {
			bord = new MySprite(pPAsset.turn);
			bord.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
			switch (pos){
			case 1: bord.setRotation(0); break;
			case 2: bord.setRotation(-90); break;
			case 3: bord.setRotation(-180); break;
			case 4: bord.setRotation(-270); break;
			default: break;
			}
			bord.setProperPosition(newXPos, newYPos);
		}; break;
		case PuzzleBorderName.emptySq: {
			bord = new MySprite(pPAsset.emptySq);
			bord.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
			bord.setProperPosition(newXPos, newYPos);
		}; break;
		case PuzzleBorderName.polySpace: {
			bord = new MySprite(pPAsset.polySpace);
			bord.setScale(PolyNGonsDimConstants.PUZZLE_SCALE / 100);
			bord.setProperPosition(newXPos, newYPos);
		}; break;
		default: break;
		}

		return bord;
	}
	
	
}
