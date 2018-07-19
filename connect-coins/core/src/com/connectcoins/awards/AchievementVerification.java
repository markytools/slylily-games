package com.connectcoins.awards;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.coins.Coin;
import com.connectcoins.coins.Coin.CoinColor;
import com.connectcoins.coins.Coin.CoinValue;
import com.connectcoins.functions.ProfileUpdater;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.listeners.Scoreboard;

public class AchievementVerification implements Disposable {
	private ConnectCoins game;
	private AchievementManager achievementManager;

	private int ultraLuckyCount = 0;
	private int oneShotLuckyCount = 0;
	private int luckyCount = 0;
	private int bigFlushJackpotCount = 0;
	private int flushJackpotCount = 0;
	private int frugalCount = 0;
	private int thriftyCount = 0;
	private int wealthyCount = 0;
	private int bronzeCollectorCount = 0;
	private int silverCollectorCount = 0;
	private int goldCollectorCount = 0;

	private boolean checkForAchievements = true;
	private boolean isDisabled = false;
	
	public AchievementVerification(ConnectCoins game, AchievementManager achievementManager){
		this.game = game;
		this.achievementManager = achievementManager;
		initAchievementsData();
	}

	private void initAchievementsData(){
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.ULTRA_LUCKY_CODE)) ultraLuckyCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.ONE_SHOT_LUCKY_CODE)) oneShotLuckyCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.LUCKY_CODE)) luckyCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.BIG_FLUSH_JACKPOT_CODE)) bigFlushJackpotCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.FLUSH_JACKPOT_CODE)) flushJackpotCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.FRUGAL_CODE)) frugalCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.THRIFTY_CODE)) thriftyCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.WEALTHY_CODE)) wealthyCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.BRONZE_COLLECTOR_CODE)) bronzeCollectorCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.SILVER_COLLECTOR_CODE)) silverCollectorCount = -1;
		if (game.pUpdater.checkIfAchievementIsUnlocked(ProfileUpdater.GOLD_COLLECTOR_CODE)) goldCollectorCount = -1;
	}

	public void verifyAchievements(CoinsComboData coinsComboData){
		if (!isDisabled){
			if (checkForAchievements){
				if (ultraLuckyCount != -1) if (checkUltraLucky(coinsComboData)) ultraLuckyCount++;
				if (oneShotLuckyCount != -1) if (checkOneShotLucky(coinsComboData)) oneShotLuckyCount++;
				if (luckyCount != -1) if (checkLucky(coinsComboData)) luckyCount++;
				if (bigFlushJackpotCount != -1) 
					if (checkBigFlushJackpot(coinsComboData)) bigFlushJackpotCount++;
					else bigFlushJackpotCount = 0;
				if (flushJackpotCount != -1) 
					if (checkFlushJackpot(coinsComboData)) flushJackpotCount++;
					else flushJackpotCount = 0;
				if (frugalCount != -1) 
					if (checkFrugal(coinsComboData)) frugalCount++;
					else frugalCount = 0;
				if (thriftyCount != -1) 
					if (checkThrifty(coinsComboData)) thriftyCount++;
					else thriftyCount = 0;
				if (wealthyCount != -1) 
					if (checkWealthy(coinsComboData)) wealthyCount++;
					else wealthyCount = 0;
				if (bronzeCollectorCount != -1) 
					if (checkBronzeCollector(coinsComboData)) bronzeCollectorCount++;
					else bronzeCollectorCount = 0;
				if (silverCollectorCount != -1) 
					if (checkSilverCollector(coinsComboData)) silverCollectorCount++;
					else silverCollectorCount = 0;
				if (goldCollectorCount != -1) 
					if (checkGoldCollector(coinsComboData)) goldCollectorCount++;
					else goldCollectorCount = 0;

				if (ultraLuckyCount >= 16){
					ultraLuckyCount = -1;
					achievementManager.addAchievement(ProfileUpdater.ULTRA_LUCKY_CODE, RewardsData.ULTRA_LUCKY);
				}
				if (oneShotLuckyCount >= 18){
					oneShotLuckyCount = -1;
					achievementManager.addAchievement(ProfileUpdater.ONE_SHOT_LUCKY_CODE, RewardsData.ONE_SHOT_LUCKY);
				}
				if (luckyCount >= 20){	//EDITED
					luckyCount = -1;
					achievementManager.addAchievement(ProfileUpdater.LUCKY_CODE, RewardsData.LUCKY);
				}
				if (bigFlushJackpotCount >= 8){
					bigFlushJackpotCount = -1;
					achievementManager.addAchievement(ProfileUpdater.BIG_FLUSH_JACKPOT_CODE, RewardsData.BIG_FLUSH_JACKPOT);
				}
				if (flushJackpotCount >= 8){
					flushJackpotCount = -1;
					achievementManager.addAchievement(ProfileUpdater.FLUSH_JACKPOT_CODE, RewardsData.FLUSH_JACKPOT);
				}
				if (frugalCount >= 15){	//EDITED
					frugalCount = -1;
					achievementManager.addAchievement(ProfileUpdater.FRUGAL_CODE, RewardsData.FRUGAL);
				}
				if (thriftyCount >= 15){	//EDITED
					thriftyCount = -1;
					achievementManager.addAchievement(ProfileUpdater.THRIFTY_CODE, RewardsData.THRIFTY);
				}
				if (wealthyCount >= 15){
					wealthyCount = -1;
					achievementManager.addAchievement(ProfileUpdater.WEALTHY_CODE, RewardsData.WEALTHY);
				}
				if (bronzeCollectorCount >= 20){
					bronzeCollectorCount = -1;
					achievementManager.addAchievement(ProfileUpdater.BRONZE_COLLECTOR_CODE, RewardsData.BRONZE_COLLECTOR);
				}
				if (silverCollectorCount >= 20){
					silverCollectorCount = -1;
					achievementManager.addAchievement(ProfileUpdater.SILVER_COLLECTOR_CODE, RewardsData.SILVER_COLLECTOR);
				}
				if (goldCollectorCount >= 20){
					goldCollectorCount = -1;
					achievementManager.addAchievement(ProfileUpdater.GOLD_COLLECTOR_CODE, RewardsData.GOLD_COLLECTOR);
				}
			}
		}
	}

	private boolean checkUltraLucky(CoinsComboData coinsComboData){
		if (coinsComboData != null && coinsComboData.getCombo().equals(Scoreboard.BIG_FLUSH)){
			Array<Coin> coins = coinsComboData.getCoins();
			for (Coin coin : coins){
				if (coin.isShining()){
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkOneShotLucky(CoinsComboData coinsComboData){
		if (coinsComboData != null && coinsComboData.getCombo().equals(Scoreboard.FLUSH)){
			Array<Coin> coins = coinsComboData.getCoins();
			for (Coin coin : coins){
				if (coin.isShining()){
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkLucky(CoinsComboData coinsComboData){
		if (coinsComboData != null && coinsComboData.getCombo().equals(Scoreboard.SMALL_FLUSH)){
			Array<Coin> coins = coinsComboData.getCoins();
			for (Coin coin : coins){
				if (coin.isShining()){
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkBigFlushJackpot(CoinsComboData coinsComboData){
		if (coinsComboData != null && coinsComboData.getCombo().equals(Scoreboard.BIG_FLUSH)){
			return true;
		}
		return false;
	}

	private boolean checkFlushJackpot(CoinsComboData coinsComboData){
		if (coinsComboData != null && coinsComboData.getCombo().equals(Scoreboard.FLUSH)){
			return true;
		}
		return false;
	}

	private boolean checkFrugal(CoinsComboData coinsComboData){
		if (coinsComboData != null){
			Array<Coin> coins = coinsComboData.getCoins();
			int validCoin = 0;
			for (Coin coin : coins){
				if (coin.getCoinValue() == CoinValue.ONE) validCoin++;
			}
			if (validCoin >= 3) return true;
		}
		return false;
	}

	private boolean checkThrifty(CoinsComboData coinsComboData){
		if (coinsComboData != null){
			Array<Coin> coins = coinsComboData.getCoins();
			int validCoin = 0;
			for (Coin coin : coins){
				if (coin.getCoinValue() == CoinValue.FIVE) validCoin++;
			}
			if (validCoin >= 3) return true;
		}
		return false;
	}

	private boolean checkWealthy(CoinsComboData coinsComboData){
		if (coinsComboData != null){
			Array<Coin> coins = coinsComboData.getCoins();
			int validCoin = 0;
			for (Coin coin : coins){
				if (coin.getCoinValue() == CoinValue.TEN) validCoin++;
			}
			if (validCoin >= 3) return true;
		}
		return false;
	}

	private boolean checkBronzeCollector(CoinsComboData coinsComboData){
		if (coinsComboData != null){
			Array<Coin> coins = coinsComboData.getCoins();
			int validCoin = 0;
			for (Coin coin : coins){
				if (checkIfCoinHasColor(coin, CoinColor.BRONZE)) validCoin++;
			}
			if (validCoin >= 3) return true;
		}
		return false;
	}

	private boolean checkSilverCollector(CoinsComboData coinsComboData){
		if (coinsComboData != null){
			Array<Coin> coins = coinsComboData.getCoins();
			int validCoin = 0;
			for (Coin coin : coins){
				if (checkIfCoinHasColor(coin, CoinColor.SILVER)) validCoin++;
			}
			if (validCoin >= 3) return true;
		}
		return false;
	}

	private boolean checkGoldCollector(CoinsComboData coinsComboData){
		if (coinsComboData != null){
			Array<Coin> coins = coinsComboData.getCoins();
			int validCoin = 0;
			for (Coin coin : coins){
				if (checkIfCoinHasColor(coin, CoinColor.GOLD)) validCoin++;
			}
			if (validCoin >= 3) return true;
		}
		return false;
	}

	private boolean checkIfCoinHasColor(Coin coin, CoinColor coinColor){
		switch (coin.getCoinType()){
		case WHOLE: {
			if (coin.getCoinColor() == coinColor) return true;
		}; break;
		case HALF: {
			if (coin.getBottomCoinColor() == coinColor || coin.getTopCoinColor() == coinColor) return true;
		}; break;
		case THIRD: {
			return true;
		}
		default: break;
		}
		return false;
	}

	public void reset(){
		ultraLuckyCount = 0;
		oneShotLuckyCount = 0;
		luckyCount = 0;
		bigFlushJackpotCount = 0;
		flushJackpotCount = 0;
		frugalCount = 0;
		thriftyCount = 0;
		wealthyCount = 0;
		bronzeCollectorCount = 0;
		silverCollectorCount = 0;
		goldCollectorCount = 0;

		checkForAchievements = true;
		isDisabled = false;

		initAchievementsData();
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public void setCheckForAchievements(boolean checkForAchievements) {
		this.checkForAchievements = checkForAchievements;
	}

	@Override
	public void dispose() {
		isDisabled = false;
		game = null;
		achievementManager = null;
	}
}
