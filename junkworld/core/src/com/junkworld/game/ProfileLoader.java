package com.junkworld.game;

import com.badlogic.gdx.utils.Array;

public class ProfileLoader {
	private String profile;
	private int level;
	private int junkCoins;
	private int highestScoreEver;
	private int currentXP;
	
	private String currentJob;
	private int triesLeft;
	private int fee;
	private int currentRound;
	private int jobHighestPerfectCombo;
	private int jobTotalPerfectDump;
	private int jobTotalDump;
	private int jobTotalUnofficialDump;
	private int jobTotalNotDumped;
	private int survivalMinute;
	private int survivalSeconds;
	
	private float[] jobRoundRatings;
	private Array<String> jobChangeTimer;
	private Array<Boolean> hasJob;
	private Array<Integer> gameMode;
	private Array<Integer> timer;
	private Array<Integer> garbageGoal;
	private Array<Integer> garbageAmount;
	private Array<Integer> rounds;
	private Array<float[]> spawnTime;

	private int kitchenCount;
	private int basementCount;
	private int garageCount;
	private int backyardCount;
	private int sidewalkCount;
	private int factoryCount;
	private int highwayCount;
	private int parkCount;
	private int sewerCount;
	
	private boolean acornAlbum , appleAlbum , bananaPeelAlbum , branchAlbum , deadMouseAlbum , eggShellAlbum , featherAlbum , 
			fishBoneAlbum , flowerAlbum , grassAlbum , hairAlbum , hayAlbum , leavesAlbum , manureAlbum , rootsAlbum ;
	private boolean bottleAlbum , canAlbum , cardboardAlbum , cerealBoxAlbum , dirtyShirtAlbum , envelopeAlbum , funnelAlbum ,
			hangerAlbum , newspaperAlbum , paperAlbum , pillBottleAlbum , plasticBagAlbum , tyreAlbum , vaseAlbum ,
			waffleIronAlbum ;
	private boolean aerosolCanAlbum , brokenBulbAlbum , brokenGlassAlbum , chewingGumAlbum , cigaretteAlbum , deadBatteryAlbum ,
			dirtyDiaperAlbum , insecticideSprayAlbum , leftoverCakeAlbum , leftoverChickenAlbum , nailAlbum , paintCanAlbum , 
			syringeAlbum , tornPaperAlbum , usedMotorOilAlbum ;
	
	private boolean timeAttack, dumpingJob, survival, kitchen, basement, garage, backyard, sidewalk, factory, highway, park, sewer, 
	diff2, diff3, diff4, diff5, diff6, diff7, diff8, diff9, diff10;
	
	private int defaultGameMode, defaultTheme, defaultDiff;
	
	public ProfileLoader(){
		
	}
	
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevel() {
		return level;
	}
	public int getJunkCoins() {
		return junkCoins;
	}
	public void setJunkCoins(int junkCoins) {
		this.junkCoins = junkCoins;
	}
	public int getHighestScoreEver() {
		return highestScoreEver;
	}
	public void setHighestScoreEver(int highestScoreEver) {
		this.highestScoreEver = highestScoreEver;
	}
	public String getCurrentJob() {
		return currentJob;
	}
	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;	
	}

	public int getCurrentXP() {
		return currentXP;
	}

	public void setCurrentXP(int currentXP) {
		this.currentXP = currentXP;
	}

	public Array<Boolean> getHasJob() {
		return hasJob;
	}

	public void setHasJob(Array<Boolean> hasJob) {
		this.hasJob = hasJob;
	}

	public Array<Integer> getGameMode() {
		return gameMode;
	}

	public void setGameMode(Array<Integer> gameMode) {
		this.gameMode = gameMode;
	}

	public Array<Integer> getTimer() {
		return timer;
	}

	public void setTimer(Array<Integer> timer) {
		this.timer = timer;
	}

	public Array<Integer> getGarbageAmount() {
		return garbageAmount;
	}

	public void setGarbageAmount(Array<Integer> garbageAmount) {
		this.garbageAmount = garbageAmount;
	}

	public Array<Integer> getRounds() {
		return rounds;
	}

	public void setRounds(Array<Integer> rounds) {
		this.rounds = rounds;
	}

	public Array<String> getJobChangeTimer() {
		return jobChangeTimer;
	}

	public void setJobChangeTimer(Array<String> jobChangeTimer) {
		this.jobChangeTimer = jobChangeTimer;
	}

	public Array<Integer> getGarbageGoal() {
		return garbageGoal;
	}

	public void setGarbageGoal(Array<Integer> garbageGoal) {
		this.garbageGoal = garbageGoal;
	}

	public int getTriesLeft() {
		return triesLeft;
	}

	public void setTriesLeft(int triesLeft) {
		this.triesLeft = triesLeft;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	public Array<float[]> getSpawnTime() {
		return spawnTime;
	}

	public void setSpawnTime(Array<float[]> spawnTime) {
		this.spawnTime = spawnTime;
	}

	public boolean isTimeAttack() {
		return timeAttack;
	}

	public void setTimeAttack(boolean timeAttack) {
		this.timeAttack = timeAttack;
	}

	public boolean isDumpingJob() {
		return dumpingJob;
	}

	public void setDumpingJob(boolean dumpingJob) {
		this.dumpingJob = dumpingJob;
	}

	public boolean isSurvival() {
		return survival;
	}

	public void setSurvival(boolean survival) {
		this.survival = survival;
	}

	public boolean isKitchen() {
		return kitchen;
	}

	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}

	public boolean isBasement() {
		return basement;
	}

	public void setBasement(boolean basement) {
		this.basement = basement;
	}

	public boolean isGarage() {
		return garage;
	}

	public void setGarage(boolean garage) {
		this.garage = garage;
	}

	public boolean isBackyard() {
		return backyard;
	}

	public void setBackyard(boolean backyard) {
		this.backyard = backyard;
	}

	public boolean isSidewalk() {
		return sidewalk;
	}

	public void setSidewalk(boolean sidewalk) {
		this.sidewalk = sidewalk;
	}

	public boolean isFactory() {
		return factory;
	}

	public void setFactory(boolean factory) {
		this.factory = factory;
	}

	public boolean isHighway() {
		return highway;
	}

	public void setHighway(boolean highway) {
		this.highway = highway;
	}

	public boolean isPark() {
		return park;
	}

	public void setPark(boolean park) {
		this.park = park;
	}

	public boolean isSewer() {
		return sewer;
	}

	public void setSewer(boolean sewer) {
		this.sewer = sewer;
	}

	public boolean isDiff2() {
		return diff2;
	}

	public void setDiff2(boolean diff2) {
		this.diff2 = diff2;
	}

	public boolean isDiff3() {
		return diff3;
	}

	public void setDiff3(boolean diff3) {
		this.diff3 = diff3;
	}

	public boolean isDiff4() {
		return diff4;
	}

	public void setDiff4(boolean diff4) {
		this.diff4 = diff4;
	}

	public boolean isDiff5() {
		return diff5;
	}

	public void setDiff5(boolean diff5) {
		this.diff5 = diff5;
	}

	public boolean isDiff6() {
		return diff6;
	}

	public void setDiff6(boolean diff6) {
		this.diff6 = diff6;
	}

	public boolean isDiff7() {
		return diff7;
	}

	public void setDiff7(boolean diff7) {
		this.diff7 = diff7;
	}

	public boolean isDiff8() {
		return diff8;
	}

	public void setDiff8(boolean diff8) {
		this.diff8 = diff8;
	}

	public boolean isDiff9() {
		return diff9;
	}

	public void setDiff9(boolean diff9) {
		this.diff9 = diff9;
	}

	public boolean isDiff10() {
		return diff10;
	}

	public void setDiff10(boolean diff10) {
		this.diff10 = diff10;
	}

	public int getDefaultGameMode() {
		return defaultGameMode;
	}

	public void setDefaultGameMode(int defaultGameMode) {
		this.defaultGameMode = defaultGameMode;
	}

	public int getDefaultTheme() {
		return defaultTheme;
	}

	public void setDefaultTheme(int defaultTheme) {
		this.defaultTheme = defaultTheme;
	}

	public int getDefaultDiff() {
		return defaultDiff;
	}

	public void setDefaultDiff(int defaultDiff) {
		this.defaultDiff = defaultDiff;
	}
	public int getJobHighestPerfectCombo() {
		return jobHighestPerfectCombo;
	}

	public void setJobHighestPerfectCombo(int jobHighestPerfectCombo) {
		this.jobHighestPerfectCombo = jobHighestPerfectCombo;
	}

	public int getJobTotalPerfectDump() {
		return jobTotalPerfectDump;
	}

	public void setJobTotalPerfectDump(int jobTotalPerfectDump) {
		this.jobTotalPerfectDump = jobTotalPerfectDump;
	}

	public int getJobTotalDump() {
		return jobTotalDump;
	}

	public void setJobTotalDump(int jobTotalDump) {
		this.jobTotalDump = jobTotalDump;
	}

	public int getJobTotalUnofficialDump() {
		return jobTotalUnofficialDump;
	}

	public void setJobTotalUnofficialDump(int jobTotalUnofficialDump) {
		this.jobTotalUnofficialDump = jobTotalUnofficialDump;
	}

	public float[] getJobRoundRatings() {
		return jobRoundRatings;
	}

	public void setJobRoundRatings(float[] jobRoundRatings) {
		this.jobRoundRatings = jobRoundRatings;
	}

	public int getJobTotalNotDumped() {
		return jobTotalNotDumped;
	}

	public void setJobTotalNotDumped(int jobTotalNotDumped) {
		this.jobTotalNotDumped = jobTotalNotDumped;
	}

	public int getSurvivalMinute() {
		return survivalMinute;
	}

	public void setSurvivalMinute(int survivalMinute) {
		this.survivalMinute = survivalMinute;
	}

	public int getSurvivalSeconds() {
		return survivalSeconds;
	}

	public void setSurvivalSeconds(int survivalSeconds) {
		this.survivalSeconds = survivalSeconds;
	}

	public boolean isAcornAlbum() {
		return acornAlbum;
	}

	public void setAcornAlbum(boolean acornAlbum) {
		this.acornAlbum = acornAlbum;
	}

	public boolean isAppleAlbum() {
		return appleAlbum;
	}

	public void setAppleAlbum(boolean appleAlbum) {
		this.appleAlbum = appleAlbum;
	}

	public boolean isBananaPeelAlbum() {
		return bananaPeelAlbum;
	}

	public void setBananaPeelAlbum(boolean bananaPeelAlbum) {
		this.bananaPeelAlbum = bananaPeelAlbum;
	}

	public boolean isBranchAlbum() {
		return branchAlbum;
	}

	public void setBranchAlbum(boolean branchAlbum) {
		this.branchAlbum = branchAlbum;
	}

	public boolean isDeadMouseAlbum() {
		return deadMouseAlbum;
	}

	public void setDeadMouseAlbum(boolean deadMouseAlbum) {
		this.deadMouseAlbum = deadMouseAlbum;
	}

	public boolean isEggShellAlbum() {
		return eggShellAlbum;
	}

	public void setEggShellAlbum(boolean eggShellAlbum) {
		this.eggShellAlbum = eggShellAlbum;
	}

	public boolean isFeatherAlbum() {
		return featherAlbum;
	}

	public void setFeatherAlbum(boolean featherAlbum) {
		this.featherAlbum = featherAlbum;
	}

	public boolean isFishBoneAlbum() {
		return fishBoneAlbum;
	}

	public void setFishBoneAlbum(boolean fishBoneAlbum) {
		this.fishBoneAlbum = fishBoneAlbum;
	}

	public boolean isFlowerAlbum() {
		return flowerAlbum;
	}

	public void setFlowerAlbum(boolean flowerAlbum) {
		this.flowerAlbum = flowerAlbum;
	}

	public boolean isGrassAlbum() {
		return grassAlbum;
	}

	public void setGrassAlbum(boolean grassAlbum) {
		this.grassAlbum = grassAlbum;
	}

	public boolean isHairAlbum() {
		return hairAlbum;
	}

	public void setHairAlbum(boolean hairAlbum) {
		this.hairAlbum = hairAlbum;
	}

	public boolean isHayAlbum() {
		return hayAlbum;
	}

	public void setHayAlbum(boolean hayAlbum) {
		this.hayAlbum = hayAlbum;
	}

	public boolean isLeavesAlbum() {
		return leavesAlbum;
	}

	public void setLeavesAlbum(boolean leavesAlbum) {
		this.leavesAlbum = leavesAlbum;
	}

	public boolean isManureAlbum() {
		return manureAlbum;
	}

	public void setManureAlbum(boolean manureAlbum) {
		this.manureAlbum = manureAlbum;
	}

	public boolean isRootsAlbum() {
		return rootsAlbum;
	}

	public void setRootsAlbum(boolean rootsAlbum) {
		this.rootsAlbum = rootsAlbum;
	}

	public boolean isBottleAlbum() {
		return bottleAlbum;
	}

	public void setBottleAlbum(boolean bottleAlbum) {
		this.bottleAlbum = bottleAlbum;
	}

	public boolean isCanAlbum() {
		return canAlbum;
	}

	public void setCanAlbum(boolean canAlbum) {
		this.canAlbum = canAlbum;
	}

	public boolean isCardboardAlbum() {
		return cardboardAlbum;
	}

	public void setCardboardAlbum(boolean cardboardAlbum) {
		this.cardboardAlbum = cardboardAlbum;
	}

	public boolean isCerealBoxAlbum() {
		return cerealBoxAlbum;
	}

	public void setCerealBoxAlbum(boolean cerealBoxAlbum) {
		this.cerealBoxAlbum = cerealBoxAlbum;
	}

	public boolean isDirtyShirtAlbum() {
		return dirtyShirtAlbum;
	}

	public void setDirtyShirtAlbum(boolean dirtyShirtAlbum) {
		this.dirtyShirtAlbum = dirtyShirtAlbum;
	}

	public boolean isEnvelopeAlbum() {
		return envelopeAlbum;
	}

	public void setEnvelopeAlbum(boolean envelopeAlbum) {
		this.envelopeAlbum = envelopeAlbum;
	}

	public boolean isFunnelAlbum() {
		return funnelAlbum;
	}

	public void setFunnelAlbum(boolean funnelAlbum) {
		this.funnelAlbum = funnelAlbum;
	}

	public boolean isHangerAlbum() {
		return hangerAlbum;
	}

	public void setHangerAlbum(boolean hangerAlbum) {
		this.hangerAlbum = hangerAlbum;
	}

	public boolean isNewspaperAlbum() {
		return newspaperAlbum;
	}

	public void setNewspaperAlbum(boolean newspaperAlbum) {
		this.newspaperAlbum = newspaperAlbum;
	}

	public boolean isPaperAlbum() {
		return paperAlbum;
	}

	public void setPaperAlbum(boolean paperAlbum) {
		this.paperAlbum = paperAlbum;
	}

	public boolean isPillBottleAlbum() {
		return pillBottleAlbum;
	}

	public void setPillBottleAlbum(boolean pillBottleAlbum) {
		this.pillBottleAlbum = pillBottleAlbum;
	}

	public boolean isPlasticBagAlbum() {
		return plasticBagAlbum;
	}

	public void setPlasticBagAlbum(boolean plasticBagAlbum) {
		this.plasticBagAlbum = plasticBagAlbum;
	}

	public boolean isTyreAlbum() {
		return tyreAlbum;
	}

	public void setTyreAlbum(boolean tyreAlbum) {
		this.tyreAlbum = tyreAlbum;
	}

	public boolean isVaseAlbum() {
		return vaseAlbum;
	}

	public void setVaseAlbum(boolean vaseAlbum) {
		this.vaseAlbum = vaseAlbum;
	}

	public boolean isWaffleIronAlbum() {
		return waffleIronAlbum;
	}

	public void setWaffleIronAlbum(boolean waffleIronAlbum) {
		this.waffleIronAlbum = waffleIronAlbum;
	}

	public boolean isAerosolCanAlbum() {
		return aerosolCanAlbum;
	}

	public void setAerosolCanAlbum(boolean aerosolCanAlbum) {
		this.aerosolCanAlbum = aerosolCanAlbum;
	}

	public boolean isBrokenBulbAlbum() {
		return brokenBulbAlbum;
	}

	public void setBrokenBulbAlbum(boolean brokenBulbAlbum) {
		this.brokenBulbAlbum = brokenBulbAlbum;
	}

	public boolean isBrokenGlassAlbum() {
		return brokenGlassAlbum;
	}

	public void setBrokenGlassAlbum(boolean brokenGlassAlbum) {
		this.brokenGlassAlbum = brokenGlassAlbum;
	}

	public boolean isChewingGumAlbum() {
		return chewingGumAlbum;
	}

	public void setChewingGumAlbum(boolean chewingGumAlbum) {
		this.chewingGumAlbum = chewingGumAlbum;
	}

	public boolean isCigaretteAlbum() {
		return cigaretteAlbum;
	}

	public void setCigaretteAlbum(boolean cigaretteAlbum) {
		this.cigaretteAlbum = cigaretteAlbum;
	}

	public boolean isDeadBatteryAlbum() {
		return deadBatteryAlbum;
	}

	public void setDeadBatteryAlbum(boolean deadBatteryAlbum) {
		this.deadBatteryAlbum = deadBatteryAlbum;
	}

	public boolean isDirtyDiaperAlbum() {
		return dirtyDiaperAlbum;
	}

	public void setDirtyDiaperAlbum(boolean dirtyDiaperAlbum) {
		this.dirtyDiaperAlbum = dirtyDiaperAlbum;
	}

	public boolean isInsecticideSprayAlbum() {
		return insecticideSprayAlbum;
	}

	public void setInsecticideSprayAlbum(boolean insecticideSprayAlbum) {
		this.insecticideSprayAlbum = insecticideSprayAlbum;
	}

	public boolean isLeftoverCakeAlbum() {
		return leftoverCakeAlbum;
	}

	public void setLeftoverCakeAlbum(boolean leftoverCakeAlbum) {
		this.leftoverCakeAlbum = leftoverCakeAlbum;
	}

	public boolean isLeftoverChickenAlbum() {
		return leftoverChickenAlbum;
	}

	public void setLeftoverChickenAlbum(boolean leftoverChickenAlbum) {
		this.leftoverChickenAlbum = leftoverChickenAlbum;
	}

	public boolean isNailAlbum() {
		return nailAlbum;
	}

	public void setNailAlbum(boolean nailAlbum) {
		this.nailAlbum = nailAlbum;
	}

	public boolean isPaintCanAlbum() {
		return paintCanAlbum;
	}

	public void setPaintCanAlbum(boolean paintCanAlbum) {
		this.paintCanAlbum = paintCanAlbum;
	}

	public boolean isSyringeAlbum() {
		return syringeAlbum;
	}

	public void setSyringeAlbum(boolean syringeAlbum) {
		this.syringeAlbum = syringeAlbum;
	}

	public boolean isTornPaperAlbum() {
		return tornPaperAlbum;
	}

	public void setTornPaperAlbum(boolean tornPaperAlbum) {
		this.tornPaperAlbum = tornPaperAlbum;
	}

	public boolean isUsedMotorOilAlbum() {
		return usedMotorOilAlbum;
	}

	public void setUsedMotorOilAlbum(boolean usedMotorOilAlbum) {
		this.usedMotorOilAlbum = usedMotorOilAlbum;
	}

	public int getKitchenCount() {
		return kitchenCount;
	}

	public void setKitchenCount(int kitchenCount) {
		this.kitchenCount = kitchenCount;
	}

	public int getBasementCount() {
		return basementCount;
	}

	public void setBasementCount(int basementCount) {
		this.basementCount = basementCount;
	}

	public int getGarageCount() {
		return garageCount;
	}

	public void setGarageCount(int garageCount) {
		this.garageCount = garageCount;
	}

	public int getBackyardCount() {
		return backyardCount;
	}

	public void setBackyardCount(int backyardCount) {
		this.backyardCount = backyardCount;
	}

	public int getSidewalkCount() {
		return sidewalkCount;
	}

	public void setSidewalkCount(int sidewalkCount) {
		this.sidewalkCount = sidewalkCount;
	}

	public int getFactoryCount() {
		return factoryCount;
	}

	public void setFactoryCount(int factoryCount) {
		this.factoryCount = factoryCount;
	}

	public int getHighwayCount() {
		return highwayCount;
	}

	public void setHighwayCount(int highwayCount) {
		this.highwayCount = highwayCount;
	}

	public int getParkCount() {
		return parkCount;
	}

	public void setParkCount(int parkCount) {
		this.parkCount = parkCount;
	}

	public int getSewerCount() {
		return sewerCount;
	}

	public void setSewerCount(int sewerCount) {
		this.sewerCount = sewerCount;
	}
	
	
	
}
