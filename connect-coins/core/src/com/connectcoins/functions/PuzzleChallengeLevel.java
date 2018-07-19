package com.connectcoins.functions;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.connectcoins.coins.Coin;

public class PuzzleChallengeLevel {
	private int levelNum;
	private int challengeNum;
	private ArrayMap<String, Array<Coin>> coinQueueMap;
	
	public PuzzleChallengeLevel(){
		
	}
	
	public PuzzleChallengeLevel(int levelNum, int challengeNum,
			ArrayMap<String, Array<Coin>> coinQueueMap) {
		super();
		this.levelNum = levelNum;
		this.challengeNum = challengeNum;
		this.coinQueueMap = coinQueueMap;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public int getChallengeNum() {
		return challengeNum;
	}

	public ArrayMap<String, Array<Coin>> getCoinQueueMap() {
		return coinQueueMap;
	}
}