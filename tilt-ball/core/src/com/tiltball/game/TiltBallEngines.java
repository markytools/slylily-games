package com.tiltball.game;

public class TiltBallEngines {
	public static final short SINGLE_PLAYER = 101;
	public static final short MULTIPLAYER = 102;
	public static final short PLAYER1_BALL = 103;
	public static final short PLAYER2_BALL = 104;
	public static final short BOXES = 105;
	
	public static final float WORLD_TO_BOX = 1/100f;
	public static final float BOX_TO_WORLD = 100f;
	
	public static final short LEFT_BODY = 1000;
	public static final short RIGHT_BODY = 1001;
	public static final short BOTTOM_BODY = 1002;
	public static final short TOP_BODY = 1003;
	
	public static final int MAP_WIDTH = 512;
	public static final int MAP_HEIGHT = 832;
	public static final int TILE_SIZE = 64;

	public static final float TIME_STEP = 1/60f;
}
