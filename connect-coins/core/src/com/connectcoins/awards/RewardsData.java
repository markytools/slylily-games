package com.connectcoins.awards;

import java.util.HashMap;

public class RewardsData {
	public class RewardsInfo {
		public int prize;
		public String info;
		
		public RewardsInfo(int prize, String info) {
			super();
			this.prize = prize;
			this.info = info;
		}
	}
	
	public static final String ULTRA_LUCKY = "ULTRA LUCKY";
	public static final String ONE_SHOT_LUCKY = "ONE SHOT LUCKY";
	public static final String LUCKY = "LUCKY";
	public static final String BIG_FLUSH_JACKPOT = "BIG FLUSH JACKPOT";
	public static final String FLUSH_JACKPOT = "FLUSH JACKPOT";
	public static final String FRUGAL = "FRUGAL";
	public static final String THRIFTY = "THRIFTY";
	public static final String WEALTHY = "WEALTHY";
	public static final String BRONZE_COLLECTOR = "BRONZE COLLECTOR";
	public static final String SILVER_COLLECTOR = "SILVER COLLECTOR";
	public static final String GOLD_COLLECTOR = "GOLD COLLECTOR";
	
	public HashMap<String, RewardsInfo> achievementRewards;
	
	public RewardsData(){
		achievementRewards = new HashMap<String, RewardsInfo>();
		achievementRewards.put(ULTRA_LUCKY, new RewardsInfo(40000, "Connect a big flush with 1 or more shiny coins 16 times."));
		achievementRewards.put(ONE_SHOT_LUCKY, new RewardsInfo(40000, "Connect a flush with 1 or more shiny coins 18 times."));
		achievementRewards.put(LUCKY, new RewardsInfo(40000, "Connect a small flush with 1 or more shiny coins 20 times."));
		achievementRewards.put(BIG_FLUSH_JACKPOT, new RewardsInfo(40000, "Score 8 big flushes in a row."));
		achievementRewards.put(FLUSH_JACKPOT, new RewardsInfo(30000, "Score 8 flushes in a row."));
		achievementRewards.put(FRUGAL, new RewardsInfo(20000, "Connect three or more 1 coins 15 times in a row"));
		achievementRewards.put(THRIFTY, new RewardsInfo(30000, "Connect three or more 5 coins 15 times in a row"));
		achievementRewards.put(WEALTHY, new RewardsInfo(40000, "Connect three or more 10 coins 15 times in a row"));
		achievementRewards.put(BRONZE_COLLECTOR, new RewardsInfo(20000, "Connect three or more bronze coins 20 times in a row"));
		achievementRewards.put(SILVER_COLLECTOR, new RewardsInfo(30000, "Connect three or more silver coins 20 times in a row"));
		achievementRewards.put(GOLD_COLLECTOR, new RewardsInfo(40000, "Connect three or more gold coins 20 times in a row"));
	}
}
