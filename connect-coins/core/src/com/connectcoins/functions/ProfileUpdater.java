package com.connectcoins.functions;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.connectcoins.coins.CoinAssets.CoinCurrency;
import com.connectcoins.functions.GameModeConfig.Boosters;
import com.connectcoins.game.ConnectCoins;

import java.util.Date;

public class ProfileUpdater {
	private JsonValue pValue;
	private ConnectCoins game;
	private String userProf;

	public static final String ULTRA_LUCKY_CODE = "ultraLucky";
	public static final String ONE_SHOT_LUCKY_CODE = "oneShotLucky";
	public static final String LUCKY_CODE = "lucky";
	public static final String BIG_FLUSH_JACKPOT_CODE = "bigFlushJackpot";
	public static final String FLUSH_JACKPOT_CODE = "flushJackpot";
	public static final String FRUGAL_CODE = "frugal";
	public static final String THRIFTY_CODE = "thrifty";
	public static final String WEALTHY_CODE = "wealthy";
	public static final String BRONZE_COLLECTOR_CODE = "bronzeCollector";
	public static final String SILVER_COLLECTOR_CODE = "silverCollector";
	public static final String GOLD_COLLECTOR_CODE = "goldCollector";

	public ProfileUpdater(ConnectCoins game){
		this.game = game;
		userProf = game.getLoadedProf();
		pValue = new JsonReader().parse(userProf);
	}
	
	public boolean checkIfAchievementIsUnlocked(String achievement){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (achievement){
//		case ULTRA_LUCKY_CODE: return pState.ultraLucky == 1;
//		case ONE_SHOT_LUCKY_CODE: return pState.oneShotLucky == 1;
//		case LUCKY_CODE: return pState.lucky == 1;
//		case BIG_FLUSH_JACKPOT_CODE: return pState.bigFlushJackpot == 1;
//		case FLUSH_JACKPOT_CODE: return pState.flushJackpot == 1;
//		case FRUGAL_CODE: return pState.frugal == 1;
//		case THRIFTY_CODE: return pState.thrifty == 1;
//		case WEALTHY_CODE: return pState.wealthy == 1;
//		case BRONZE_COLLECTOR_CODE: return pState.bronzeCollector == 1;
//		case SILVER_COLLECTOR_CODE: return pState.silverCollector == 1;
//		case GOLD_COLLECTOR_CODE: return pState.goldCollector == 1;
//		default: break;
//		}
		return true;
	}
	
	public void unlockAchievement(String achievement){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (achievement){
//		case ULTRA_LUCKY_CODE: pState.ultraLucky = 1; break;
//		case ONE_SHOT_LUCKY_CODE: pState.oneShotLucky = 1; break;
//		case LUCKY_CODE: pState.lucky = 1; break;
//		case BIG_FLUSH_JACKPOT_CODE: pState.bigFlushJackpot = 1; break;
//		case FLUSH_JACKPOT_CODE: pState.flushJackpot = 1; break;
//		case FRUGAL_CODE: pState.frugal = 1; break;
//		case THRIFTY_CODE: pState.thrifty = 1; break;
//		case WEALTHY_CODE: pState.wealthy = 1; break;
//		case BRONZE_COLLECTOR_CODE: pState.bronzeCollector = 1; break;
//		case SILVER_COLLECTOR_CODE: pState.silverCollector = 1; break;
//		case GOLD_COLLECTOR_CODE: pState.goldCollector = 1; break;
//		default: break;
//		}
//		updateReferences(pState);
	}
	
	public void addCC(int amount){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.totalCCCollected += amount;
//		updateReferences(pState);
	}
	
	public void unlockCoinTexture(String coinCurrency){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (coinCurrency){
//		case CoinCurrency.NORMAL: pState.normal = 1; break;
//		case CoinCurrency.AUD: pState.aud = 1; break;
//		case CoinCurrency.BRL: pState.brl = 1; break;
//		case CoinCurrency.CAD: pState.cad = 1; break;
//		case CoinCurrency.CHF: pState.chf = 1; break;
//		case CoinCurrency.CNY: pState.cny = 1; break;
//		case CoinCurrency.EURO: pState.euro = 1; break;
//		case CoinCurrency.GBP: pState.gbp = 1; break;
//		case CoinCurrency.INR: pState.inr = 1; break;
//		case CoinCurrency.JPY: pState.jpy = 1; break;
//		case CoinCurrency.KRW: pState.krw = 1; break;
//		case CoinCurrency.MXN: pState.mxn = 1; break;
//		case CoinCurrency.NZD: pState.nzd = 1; break;
//		case CoinCurrency.PHP: pState.php = 1; break;
//		case CoinCurrency.RUB: pState.rub = 1; break;
//		case CoinCurrency.SEK: pState.sek = 1; break;
//		case CoinCurrency.SGP: pState.sgp = 1; break;
//		case CoinCurrency.USD: pState.usd = 1; break;
//		case CoinCurrency.CLOVER: pState.clover = 1; break;
//		case CoinCurrency.DIAMOND: pState.diamond = 1; break;
//		case CoinCurrency.HEART: pState.heart = 1; break;
//		case CoinCurrency.SPADE: pState.spade = 1; break;
//		default: break;
//		}
//		updateReferences(pState);
	}
	
	/**
	 * Automatically reduces the boosters' data by one.
	 * @return booster data
	 */
	public BoosterChecker acquireBoosterData(){
//		boolean hasOnFiredUp = false;
//		boolean hasShineAllTheWay = false;
//		boolean hasLuckyThird = false;
//		boolean hasLuckyHalf = false;
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		if (pState.superBooster - 1 >= 0){
//			pState.superBooster--;
//			hasOnFiredUp = true;
//			hasShineAllTheWay = true;
//			hasLuckyThird = true;
//			hasLuckyHalf = true;
//		}
//		else {
//			if (pState.firedUp - 1 >= 0){
//				pState.firedUp--;
//				hasOnFiredUp = true;
//			}
//			if (pState.shineAllTheWay - 1 >= 0){
//				pState.shineAllTheWay--;
//				hasShineAllTheWay = true;
//			}
//			if (pState.luckyHalf - 1 >= 0){
//				pState.luckyHalf--;
//				hasLuckyHalf = true;
//			}
//			if (pState.luckyThird - 1 >= 0){
//				pState.luckyThird--;
//				hasLuckyThird = true;
//			}
//		}
//		updateReferences(pState);
		return new BoosterChecker(true, true, true, true);
	}
	
	public int getChallengeLevelCompletedCount(int challengeNum){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		int completed = 0;
//		int[] challengePuzzles = null;
//		switch (challengeNum){
//		case 1: challengePuzzles = pState.challenge1; break;
//		case 2: challengePuzzles = pState.challenge2; break;
//		case 3: challengePuzzles = pState.challenge3; break;
//		case 4: challengePuzzles = pState.challenge4; break;
//		case 5: challengePuzzles = pState.challenge5; break;
//		default: break;
//		}
//		for (int i = 0; i < 100; i++){
//			if (challengePuzzles[i] == 1) completed++;
//		}
		return 100;
	}

	public Date freeCCTimerUp(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		if (pState.nextFreeCCTime == -1){
//			return new Date();
//		}
//		else {
//			Date cal = new Date();
////			SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.getDefault());
//			cal.setTime(pState.nextFreeCCTime);
////				cal.setTime(sdf.parse(Base64Coder.decodeString(pState.nextFreeCCTime)).getTime());
////				cal.setTime();
//			return cal;
//		}
		return new Date();
	}

	/**
	 * Extends the timer to the next 8hrs.
	 */
	public void extendNextFreeCCTimer(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		Date cal = new Date();
////		Calendar cal = Calendar.getInstance();
//		final long hoursInMillis = 60L * 60L * 1000L;
//		Date newDate = new Date(cal.getTime() + 
//		                        (8L * hoursInMillis)); // Adds 2 hours
////		cal.add(Calendar.HOUR_OF_DAY, 8);
////		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
////		String dueDateStr = Base64Coder.encodeString(dateFormat.format(newDate)); // renders as 11/29/2009
//		pState.nextFreeCCTime = (int) newDate.getTime();
//		updateReferences(pState);
	}
	
	public int getTotalBooster(Boosters booster){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		int totalBooster = 0;
//		switch (booster){
//		case FIRED_UP: totalBooster = pState.firedUp; break;
//		case SHINE_ALL_THE_WAY: totalBooster = pState.shineAllTheWay; break;
//		case LUCKY_HALF: totalBooster = pState.luckyHalf; break;
//		case LUCKY_THIRD: totalBooster = pState.luckyThird; break;
//		case SUPER_BOOSTER: totalBooster = pState.superBooster; break;
//		default: break;
//		}
		return 100;
	}
	
	public void addBooster(Boosters booster, int amount){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (booster){
//		case FIRED_UP: pState.firedUp += amount; break;
//		case SHINE_ALL_THE_WAY: pState.shineAllTheWay += amount; break;
//		case LUCKY_HALF: pState.luckyHalf += amount; break;
//		case LUCKY_THIRD: pState.luckyThird += amount; break;
//		case SUPER_BOOSTER: pState.superBooster += amount; break;
//		default: break;
//		}
//		updateReferences(pState);
	}
	
	public void setLeastNumberOfConnections(int challengeNum, int challengeLevel, int numOfConnections){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (challengeNum){
//		case 1: {
//			if (pState.challenge1LeastConnections[challengeLevel - 1] > numOfConnections ||
//					pState.challenge1LeastConnections[challengeLevel - 1] == -1){
//				pState.challenge1LeastConnections[challengeLevel - 1] = numOfConnections;
//			}
//		}; break;
//		case 2: {
//			if (pState.challenge2LeastConnections[challengeLevel - 1] > numOfConnections ||
//					pState.challenge2LeastConnections[challengeLevel - 1] == -1){
//				pState.challenge2LeastConnections[challengeLevel - 1] = numOfConnections;
//			}
//		}; break;
//		case 3: {
//			if (pState.challenge3LeastConnections[challengeLevel - 1] > numOfConnections ||
//					pState.challenge3LeastConnections[challengeLevel - 1] == -1){
//				pState.challenge3LeastConnections[challengeLevel - 1] = numOfConnections;
//			}
//		}; break;
//		case 4: {
//			if (pState.challenge4LeastConnections[challengeLevel - 1] > numOfConnections ||
//					pState.challenge4LeastConnections[challengeLevel - 1] == -1){
//				pState.challenge4LeastConnections[challengeLevel - 1] = numOfConnections;
//			}
//		}; break;
//		case 5: {
//			if (pState.challenge5LeastConnections[challengeLevel - 1] > numOfConnections ||
//					pState.challenge5LeastConnections[challengeLevel - 1] == -1){
//				pState.challenge5LeastConnections[challengeLevel - 1] = numOfConnections;
//			}
//		}; break;
//		default: break;
//		}
//		updateReferences(pState);
	}
	
	public int getLeastNumberOfConnections(int challengeNum, int challengeLevel){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (challengeNum){
//		case 1: return pState.challenge1LeastConnections[challengeLevel - 1];
//		case 2: return pState.challenge2LeastConnections[challengeLevel - 1];
//		case 3: return pState.challenge3LeastConnections[challengeLevel - 1];
//		case 4: return pState.challenge4LeastConnections[challengeLevel - 1];
//		case 5: return pState.challenge5LeastConnections[challengeLevel - 1];
//		default: return -1;
//		}
		return 1;
	}
	
	public int getGraphics(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
		return 3;
	}
	
	/**
	 * @param graphics 1 - low, 3 - high
	 */
	public void setGraphics(int graphics){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.graphics = graphics;
//		updateReferences(pState);
	}

	public void unlockChallenge(int num){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.challenges[num - 1] = 1;
//		updateReferences(pState);
	}

	public boolean challengeUnlocked(int challengeNum){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
		return true;
	}

	public int getSelectedItemCategory(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
		return 1;
	}

	public void setSelectedItemCategory(int itemCategory){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.currentCategory = itemCategory;
//		updateReferences(pState);
	}
	
	public String getCurrentCoinTexture(){
		return "NORMAL";
	}
	
	public void setCurrentCoinTexture(String coinTextureID){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.currentCoinTexture = coinTextureID;
//		updateReferences(pState);
	}
	
	public boolean allLevelUnlocked(int challengeNum){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (challengeNum){
//		case 1: {
//			for (int i = 0; i < 100; i++){
//				if (pState.challenge1[i] == 0) return false;
//			}
//		}; break;
//		case 2: {
//			for (int i = 0; i < 100; i++){
//				if (pState.challenge2[i] == 0) return false;
//			}
//		}; break;
//		case 3: {
//			for (int i = 0; i < 100; i++){
//				if (pState.challenge3[i] == 0) return false;
//			}
//		}; break;
//		case 4: {
//			for (int i = 0; i < 100; i++){
//				if (pState.challenge4[i] == 0) return false;
//			}
//		}; break;
//		case 5: {
//			for (int i = 0; i < 100; i++){
//				if (pState.challenge5[i] == 0) return false;
//			}
//		}; break;
//		default: break;
//		}
		return true;
	}
	
	public boolean isLevelUnlocked(int challengeNum, int level){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (challengeNum){
//		case 1: return pState.challenge1[level - 1] == 1;
//		case 2: return pState.challenge2[level - 1] == 1;
//		case 3: return pState.challenge3[level - 1] == 1;
//		case 4: return pState.challenge4[level - 1] == 1;
//		case 5: return pState.challenge5[level - 1] == 1;
//		default: break;
//		}
		return true;
	}
	
	public void unlockAds(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.noAds = 1;
//		updateReferences(pState);
	}
	
	public boolean noAds(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
		return true;
	}
	
	public boolean isMusicOn(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
		return true;
	}
	
	public void turnMusic(boolean on){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		if (on) pState.music = 1;
//		else pState.music = 0;
//		updateReferences(pState);
	}

	public void unlockLevel(int challengeNum, int level){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (challengeNum){
//		case 1: pState.challenge1[level - 1] = 1; break;
//		case 2: pState.challenge2[level - 1] = 1; break;
//		case 3: pState.challenge3[level - 1] = 1; break;
//		case 4: pState.challenge4[level - 1] = 1; break;
//		case 5: pState.challenge5[level - 1] = 1; break;
//		default: break;
//		}
//		updateReferences(pState);
	}

//	public ProfileState getProfileState(){
//		return game.getJson().fromJson(ProfileState.class, userProf);
//	}

	public void setChallengeX(int challengeNum, float x){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		switch (challengeNum){
//		case 1: pState.challenge1X = x; break;
//		case 2: pState.challenge2X = x; break;
//		case 3: pState.challenge3X = x; break;
//		case 4: pState.challenge4X = x; break;
//		case 5: pState.challenge5X = x; break;
//		default: break;
//		}
//		updateReferences(pState);
	}

	public int getChallengeX(int challengeNum){
		return 1;
	}
	
	public String getCurrentLanguage(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
		return "English";
	}
	
	public void setLanguage(String language){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.language = language;
//		updateReferences(pState);
	}
	
	public boolean vibrationOn(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
		return true;
	}
	
	public void setVibration(byte on){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.vibration = on;
//		updateReferences(pState);
	}
	
	public boolean isBGAnimationOn(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
		return true;
	}
	
	public void setBGAnimation(byte on){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.bgAnim = on;
//		updateReferences(pState);
	}
	
	public int getTotalCC(){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
		return 99999999;
	}
	
	public void subtractCC(long amount){
//		ProfileState pState = game.getJson().fromJson(ProfileState.class, userProf);
//		pState.totalCCCollected -= amount;
//		updateReferences(pState);
	}

//	/**
//	 * When changing values in the save profile, call this ALWAYS.
//	 * @param newValues
//	 */
//	public void updateReferences(ProfileState newValues){
//		game.writeString(game.getJson().toJson(newValues));
//		pValue = new JsonReader().parse(game.getLoadedProf());
//		userProf = game.getLoadedProf();
//	}

	public JsonValue getPValue(){ return pValue; }
}
