package com.connectcoins.challenge;

import java.util.HashMap;

import com.connectcoins.game.ConnectCoins;

public class ChallengeUnlocker {
	public class ChallengeReward {
		public int challengeNum;
		public int puzzleReward;
		public int puzzleAlreadyCompletedReward;
		public int challengeBonusReward;
		
		public ChallengeReward(int challengeNum, int puzzleReward, int puzzleAlreadyCompletedReward, int challengeBonusReward) {
			this.challengeNum = challengeNum;
			this.puzzleReward = puzzleReward;
			this.puzzleAlreadyCompletedReward = puzzleAlreadyCompletedReward;
			this.challengeBonusReward = challengeBonusReward;
		}
	}
	
	public class ChallengeRewardData {
		public CHALLENGE_REWARD_TYPE rewardType;
		public String message;
		
		public ChallengeRewardData(CHALLENGE_REWARD_TYPE rewardType, ChallengeReward reward){
			this.rewardType = rewardType;
			
			switch(rewardType){
			case NONE: message = ""; break;
			case PUZZLE_COMPLETED: message = "Puzzle already completed."; break;
			case CHALLENGE_UNLOCKED: message = "Challenge " + (reward.challengeNum + 1) + " Unlocked."; break;
			case CHALLENGE_COMPLETED: message = "Challenge " + reward.challengeNum 
					+ " Completed. Bonus 'CC." + reward.challengeBonusReward + "' reward."; break;
			default: break;
			}
		}
	}
	
	public static final String CHALLENGE_1 = "3hajksd";
	public static final String CHALLENGE_2 = "3hsagksd";
	public static final String CHALLENGE_3 = "33t36hd";
	public static final String CHALLENGE_4 = "3haiiig";
	public static final String CHALLENGE_5 = "6tg8ksd";
	
	public enum CHALLENGE_REWARD_TYPE {
		NONE, PUZZLE_COMPLETED, CHALLENGE_UNLOCKED, CHALLENGE_COMPLETED
	}
	
	private ConnectCoins game;
	private HashMap<String, ChallengeReward> challengeReward;
	private int recentCCReward;

	public ChallengeUnlocker(ConnectCoins game){
		this.game = game;
		
		challengeReward = new HashMap<String, ChallengeReward>();
		challengeReward.put(CHALLENGE_1, new ChallengeReward(1, 500, 1, 20000));
		challengeReward.put(CHALLENGE_2, new ChallengeReward(2, 1000, 2, 40000));
		challengeReward.put(CHALLENGE_3, new ChallengeReward(3, 1500, 3, 60000));
		challengeReward.put(CHALLENGE_4, new ChallengeReward(4, 2000, 4, 80000));
		challengeReward.put(CHALLENGE_5, new ChallengeReward(5, 2500, 5, 100000));
	}
	
	/**
	 * 
	 * @param currentChallenge the current challenge number
	 * @return the CC reward after completing the challenge
	 */
	public ChallengeRewardData unlockChallenge(int currentChallenge, int currentChallengeLevel){
		String challengeNumID = null;
		switch (currentChallenge){
		case 1: challengeNumID = CHALLENGE_1; break;
		case 2: challengeNumID = CHALLENGE_2; break;
		case 3: challengeNumID = CHALLENGE_3; break;
		case 4: challengeNumID = CHALLENGE_4; break;
		case 5: challengeNumID = CHALLENGE_5; break;
		default: break;
		}
		ChallengeReward reward = challengeReward.get(challengeNumID);
		ChallengeRewardData rewardData = null;
		if (!game.pUpdater.isLevelUnlocked(currentChallenge, currentChallengeLevel)){
			game.pUpdater.unlockLevel(currentChallenge, currentChallengeLevel);

			int puzzleUnlockCount = game.pUpdater.getChallengeLevelCompletedCount(currentChallenge);
			if (puzzleUnlockCount == 100){
				rewardData = new ChallengeRewardData(CHALLENGE_REWARD_TYPE.CHALLENGE_COMPLETED, reward);
				recentCCReward = reward.puzzleReward + reward.challengeBonusReward;
			}
			else if (puzzleUnlockCount >= 75){
				if (currentChallenge != 5 && !game.pUpdater.challengeUnlocked(currentChallenge + 1)){
					rewardData = new ChallengeRewardData(CHALLENGE_REWARD_TYPE.CHALLENGE_UNLOCKED, reward);
					game.pUpdater.unlockChallenge(currentChallenge + 1);
				}
				else {
					rewardData = new ChallengeRewardData(CHALLENGE_REWARD_TYPE.NONE, reward);
				}
				recentCCReward = reward.puzzleReward;
			}
			else {
				rewardData = new ChallengeRewardData(CHALLENGE_REWARD_TYPE.NONE, reward);
				recentCCReward = reward.puzzleReward;
			}
		}
		else {
			rewardData = new ChallengeRewardData(CHALLENGE_REWARD_TYPE.PUZZLE_COMPLETED, reward);
			recentCCReward = reward.puzzleAlreadyCompletedReward;
		}
		return rewardData;
	}

	public int getRecentCCReward() {
		return recentCCReward;
	}
}