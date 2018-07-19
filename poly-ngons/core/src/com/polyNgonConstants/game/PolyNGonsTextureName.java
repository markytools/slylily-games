package com.polyNgonConstants.game;

public class PolyNGonsTextureName {
	public static final byte T_SIMPLE = -127;
	public static final byte T_GOLDEN = -128;
	public static final byte T_BRICK = -126;
	public static final byte T_METALLIC = -125;
	public static final byte T_BLACKANDWHITE = -124;
	public static final byte T_SCRAP = -123;
	public static final byte T_FRAME = -122;
	public static final byte T_BUBBLES = -121;
	public static final byte T_MOSAIC = -120;
	public static final byte T_GEM = -119;
	public static final byte T_GRASSWEAVED = -118;
	public static final byte T_CLAY = -117;
	public static final byte T_BURNT = -116;
	public static final byte T_WOOD = -115;
	public static final byte T_CHOCOLATE = -114;
	public static final byte T_TRIUMPHANT = -113;
	public static final byte T_GENIUS = -112;

	public static final byte B_SIMPLE = -111;
	public static final byte B_CONCRETE = -110;
	public static final byte B_DYED = -109;
	public static final byte B_SNOW = -108;
	public static final byte B_KINETIC = -107;
	public static final byte B_GRASS = -106;
	public static final byte B_FIERY = -105;
	public static final byte B_BUTTERFLY = -104;
	public static final byte B_TRIUMPHANT = -103;
	public static final byte B_GENIUS = -102;
	
	public static final String T_SIMPLE_CODE = "None";
	public static final String T_GOLDEN_CODE = "dssvd";
	public static final String T_BRICK_CODE = "sadad";
	public static final String T_METALLIC_CODE = "kh53r";
	public static final String T_BLACKANDWHITE_CODE = "4eg53";
	public static final String T_SCRAP_CODE = "hfcvb";
	public static final String T_FRAME_CODE = "jr86y";
	public static final String T_BUBBLES_CODE = "mhjtc";
	public static final String T_MOSAIC_CODE = "14ds2";
	public static final String T_GEM_CODE = "xfsrh";
	public static final String T_GRASSWEAVED_CODE = "o97ik";
	public static final String T_CLAY_CODE = "adsas";
	public static final String T_BURNT_CODE = "ghyy5";
	public static final String T_WOOD_CODE = "te4dc";
	public static final String T_CHOCOLATE_CODE = "vb54e";
	public static final String T_TRIUMPHANT_CODE = "yy20s";
	public static final String T_GENIUS_CODE = "gsw4d";
	
	public static final String B_SIMPLE_CODE = "None";
	public static final String B_CONCRETE_CODE = "54fdd";
	public static final String B_DYED_CODE = "gdwt5";
	public static final String B_SNOW_CODE = "fjyik";
	public static final String B_KINETIC_CODE = "2wadr";
	public static final String B_GRASS_CODE = "j6hgt";
	public static final String B_FIERY_CODE = "7sda2";
	public static final String B_BUTTERFLY_CODE = "55533";
	public static final String B_TRIUMPHANT_CODE = "ma2ff";
	public static final String B_GENIUS_CODE = "2qwaf";

	public static final byte BORDER_TEXTURE = 89;
	public static final byte POLYGON_TEXTURE = 90;
	
	public static String byteToStringTexture(byte textureID){
		switch (textureID){
		case T_SIMPLE: return "Simple";
		case T_GOLDEN: return "Golden";
		case T_BRICK: return "Brick";
		case T_METALLIC: return "Metallic";
		case T_BLACKANDWHITE: return "Black and White";
		case T_SCRAP: return "Scrap";
		case T_FRAME: return "Frame";
		case T_BUBBLES: return "Bubbles";
		case T_MOSAIC: return "Mosaic";
		case T_GEM: return "Gem";
		case T_GRASSWEAVED: return "Grass Weaved";
		case T_CLAY: return "Clay";
		case T_BURNT: return "Burnt";
		case T_WOOD: return "Wood";
		case T_CHOCOLATE: return "Chocolate";
		case T_TRIUMPHANT: return "Triumphant";
		case T_GENIUS: return "Genius";
		
		case B_SIMPLE: return "Simple";
		case B_CONCRETE: return "Concrete";
		case B_DYED: return "Dyed";
		case B_SNOW: return "Snow";
		case B_KINETIC: return "Kinetic";
		case B_GRASS: return "Grass";
		case B_FIERY: return "Fiery";
		case B_BUTTERFLY: return "Butterfly";
		case B_TRIUMPHANT: return "Triumphant";
		case B_GENIUS: return "Genius";
		default: return null;
		}
	}
}
