package com.connectcoins.functions;

import com.connectcoins.game.ConnectCoins;
//import com.connectcoins.multiplayerUtils.PlayerInfo;

public class GameModeConfig {
	public enum GameMode {
		TUTORIAL, NORMAL, CHALLENGE
	}
	
	public enum Boosters {
		FIRED_UP, SHINE_ALL_THE_WAY, LUCKY_HALF, LUCKY_THIRD, SUPER_BOOSTER
	}
	
	public GameMode mode;
//	public PlayerInfo currentPlayer;
	public int opponentFaceNum;
	public int challengeNum;
	public int levelNum;
	public int lastRank;	//Not the real rating, but the rank placement to be taken from appcloud. Use getUserRanking();

	public boolean isGameInvite;
	public int isHost;
	public static boolean FIRED_UP;
	public static boolean SHINE_ALL_THE_WAY;
	public static boolean LUCKY_THIRD;
	public static boolean LUCKY_HALF;
	
	private ConnectCoins game;
	
	public GameModeConfig(ConnectCoins game){
		this.game = game;
		isHost = 0;
	}

	public void disableAllBoosters(){
		FIRED_UP = false;
		SHINE_ALL_THE_WAY = false;
		LUCKY_THIRD = false;
		LUCKY_HALF = false;
	}

	public void enableBoosterForMultiplayer(){
		FIRED_UP = true;
		SHINE_ALL_THE_WAY = true;
		LUCKY_THIRD = true;
		LUCKY_HALF = true;
	}
	
	/**
	 * Call method once only. Automatically reduces boosters by one.
	 */
	public void updateBoosters(){
		BoosterChecker bChecker = game.pUpdater.acquireBoosterData();
		FIRED_UP = bChecker.hasFireUp;
		SHINE_ALL_THE_WAY = bChecker.hasShineAllTheWay;
		LUCKY_THIRD = bChecker.hasLuckyThird;
		LUCKY_HALF = bChecker.hasLuckyHalf;
	}
}