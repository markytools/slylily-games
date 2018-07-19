package com.polygons.game;

public class PolygonName {
	public static final byte triA = 101;
	public static final byte triB = 102;
	public static final byte rhom = 103;
	public static final byte sq = 104;
	public static final byte trapA = 105;
	public static final byte trapB = 106;
	public static final byte rTrapA = 107;
	public static final byte rTrapB = 108;
	public static final byte paraA = 109;
	public static final byte paraB = 110;
	public static final byte rec = 111;
	
	public static String getRealPolygonName(byte polyID){
		switch(polyID){
		case triA: return "Triangle A";
		case triB: return "Triangle B";
		case sq: return "Square";
		case rec: return "Rectangle";
		case rhom: return "Rhombus";
		case trapA: return "Trapezoid A";
		case trapB: return "Trapezoid B";
		case paraA: return "Parallelogram A";
		case paraB: return "Parallelogram B";
		case rTrapA: return "Right Trapezoid A";
		case rTrapB: return "Right Trapezoid B";
		default: return null;
		}
	}

	public static byte getPolygonID(String polyName){
		switch(polyName){
		case "triA": return triA;
		case "triB": return triB;
		case "sq": return sq;
		case "rec": return rec;
		case "rhom": return rhom;
		case "trapA": return trapA;
		case "trapB": return trapB;
		case "paraA": return paraA;
		case "paraB": return paraB;
		case "rTrapA": return rTrapA;
		case "rTrapB": return rTrapB;
		default: return 0;
		}
	}

	public static String getPolygonName(byte polyID){
		switch(polyID){
		case triA: return "triA";
		case triB: return "triB";
		case sq: return "sq";
		case rec: return "rec";
		case rhom: return "rhom";
		case trapA: return "trapA";
		case trapB: return "trapB";
		case paraA: return "paraA";
		case paraB: return "paraB";
		case rTrapA: return "rTrapA";
		case rTrapB: return "rTrapB";
		default: return null;
		}
	}
}
