package com.puzzleCreator.game;

import com.polygons.game.PolygonName;

public class PolyPoints {
	public static int pTriA = 1;
	public static int pTriB = 2;
	public static int pSq = 1;
	public static int pRec = 2;
	public static int pTrapA = 3;
	public static int pTrapB = 3;
	public static int pParaA = 4;
	public static int pParaB = 4;
	public static int pRhom = 3;
	public static int pRTrapA = 3;
	public static int pRTrapB = 6;
	
	public static int getPolyPoints(byte poly){
		switch (poly){
		case PolygonName.triA:{
			return pTriA;
		}
		case PolygonName.triB:{
			return pTriB;
		}
		case PolygonName.sq:{
			return pSq;
		}
		case PolygonName.rec:{
			return pRec;
		}
		case PolygonName.trapA:{
			return pTrapA;
		}
		case PolygonName.paraA:{
			return pParaA;
		}
		case PolygonName.paraB:{
			return pParaB;
		}
		case PolygonName.rhom:{
			return pRhom;
		}
		case PolygonName.rTrapA:{
			return pRTrapA;
		}
		case PolygonName.rTrapB:{
			return pRTrapB;
		}
		case PolygonName.trapB:{
			return pTrapB;
		}
		default: return 0;
		}
	}
}
