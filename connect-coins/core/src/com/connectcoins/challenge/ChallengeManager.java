package com.connectcoins.challenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.connectcoins.game.ConnectCoins;

public class ChallengeManager {
	private ConnectCoins game;
	private PuzzleData pData;
	private ChallengeUnlocker challengeUnlocker;

	public ChallengeManager(ConnectCoins game){
		this.game = game;
		challengeUnlocker = new ChallengeUnlocker(game);
	}
	
	public void setPuzzleData(int challengeNum, int puzzleNum){
		FileHandle puzzleTarget = Gdx.files.internal("puzzleData/challenge" + challengeNum + "/p" + puzzleNum + ".ccs");
		String fileData = Base64Coder.decodeString(puzzleTarget.readString());
		pData = game.getJson().fromJson(PuzzleData.class, fileData);
	}
	
	public ChallengeUnlocker getChallengeUnlocker() {
		return challengeUnlocker;
	}

	public PuzzleData getpData() {
		return pData;
	}
}