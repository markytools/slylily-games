package com.connectcoins.awards;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.awards.RewardsData.RewardsInfo;
import com.connectcoins.functions.ProfileUpdater;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen.GameState;

public class AchievementManager implements Disposable {
	public class AchievementUnlockedRenderer implements Disposable {
		private ConnectCoins game;
		private Texture ccSymbol;
		private TextureRegion achievementImg;
		private String name, reward, id;
		private boolean hasCCSymbol = true;

		public AchievementUnlockedRenderer(ConnectCoins game, TextureRegion img, String name, String reward, String id){
			this.game = game;
			this.achievementImg = img;
			this.id = id;
			this.name = name;
			this.reward = reward;

			ccSymbol = game.assetLoader.getTexture("ccSymbol");
		}

		public void render(SpriteBatch batch, float x, float y){
			batch.draw(achievementImg, x + 390, y + 1010, 300, 300);
			batch.draw(achievementFrame, x + 340, y + 960, 400, 400);
			batch.draw(banner, x + 220, y + 820, 640, 140);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, name, x + 440,
					y + 820 + 85, 200, Align.center);
			if (hasCCSymbol){
				ConnectCoins.glyphLayout.setText(game.fManager.smallFont, reward);
				float rewardX = 340 - (ConnectCoins.glyphLayout.width / 2);
				batch.draw(ccSymbol, x + rewardX + 113, y + 700, 96, 96);
				game.fManager.drawFont(game.fManager.largeFont2, batch, Color.valueOf("f1f4c9s"), 1, 1, reward,
						x + rewardX + 195, y + 780, 1000, Align.left);
			}
			else {
				game.fManager.drawFont(game.fManager.largeFont2, batch, Color.valueOf("f1f4c9s"), 1, 1, reward,
						x + 30, y + 780, 1000, Align.center);
			}
		}

		public void setHasCCSymbol(boolean hasCCSymbol) {
			this.hasCCSymbol = hasCCSymbol;
		}

		public void reset(){
			game = null;
			achievementFrame = null;
			achievementImg = null;
			banner = null;
			ccSymbol = null;
			name = null;
			reward = null;
		}

		public String getId() {
			return id;
		}

		@Override
		public void dispose() {
			game = null;
			achievementFrame = null;
			achievementImg = null;
			banner = null;
			ccSymbol = null;
			name = null;
			reward = null;
			id = null;
			hasCCSymbol = false;
		}
	}

	private ConnectCoins game;
	private Texture blackBG;
	private Animation<TextureRegion> title;
	private float titleStateTime = 0;
	private int currentAchievement = 0;
	private Button okButton;
	private AchievementVerification achVerification;
	private Array<AchievementUnlockedRenderer> achUnlockedRederers;
	private float currentAchievementX = 0;
	private float targetAchievementX, finalAchievementX;
	private boolean showAchievementUnlock = false;
	private boolean moveToNextAchievement = false;
	private HashMap<String, RewardsInfo> achievementRewards;
	private Sound achievementUnlockedSound;

	private TextureRegion achievementFrame, banner;
	private TextureAtlas achievementIcons;

	public AchievementManager(ConnectCoins game){
		this.game = game;
		this.achievementRewards = game.rewardsData.achievementRewards;

		achUnlockedRederers = new Array<AchievementUnlockedRenderer>();
		achVerification = new AchievementVerification(game, this);
		achievementUnlockedSound = game.assetLoader.getSound("achieveSound");

		achievementIcons = game.assetLoader.getTextureAtlas("achievementIcons");
		achievementFrame = achievementIcons.findRegion("achievementFrame");
		banner = achievementIcons.findRegion("banner");

		initialize();
	}

	private void initialize() {
		blackBG = game.assetLoader.getTexture("blackBG");
		TextureAtlas achievementTitleAtlas = game.assetLoader.getTextureAtlas("achievementUnlocked");
		Array<TextureRegion> achievementTitle = new Array<TextureRegion>();
		for (int i = 0; i < 20; i++){
			achievementTitle.add(achievementTitleAtlas.findRegion("8"));
		}
		for (int i = 0; i <= 8; i++){
			achievementTitle.add(achievementTitleAtlas.findRegion(String.valueOf(i)));
		}
		title = new Animation<TextureRegion>(0.05f, achievementTitle);
		title.setPlayMode(PlayMode.LOOP);

		TextureAtlas layoutsAtlas = game.assetLoader.getTextureAtlas("achievementIcons");

		ButtonStyle okBStyle = new ButtonStyle();
		okBStyle.up = new TextureRegionDrawable(layoutsAtlas.findRegion("ok1"));
		okBStyle.down = new TextureRegionDrawable(layoutsAtlas.findRegion("ok2"));
		okBStyle.disabled = new TextureRegionDrawable(layoutsAtlas.findRegion("ok2"));

		okButton = new Button(okBStyle);
		okButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Math.abs(currentAchievementX) % 1080 == 0){
					if (targetAchievementX == finalAchievementX){
//						game.miscellaneous.playNormalBClick1();
						rewardAchievement(achUnlockedRederers.get(currentAchievement).getId());
						ConnectCoins.setStageActorsVisible(game.stage, GameState.GAME_COMPLETION);
						invalidateAchievementManager();
					}
					else {
//						game.miscellaneous.playNormalBClick1();
						rewardAchievement(achUnlockedRederers.get(currentAchievement).getId());
						currentAchievement++;
						playAchievementSound();
						okButton.setDisabled(true);
						moveToNextAchievement = true;
						targetAchievementX -= 1080;
					}
				}
			}
		});
		okButton.setBounds(295, 510, 490, 142);
		okButton.setUserObject(GameState.ACHIEVEMENT_UNLOCKED);

		game.stage.addActor(okButton);

		//		testMethod();
	}

	private void rewardAchievement(String id){
		switch (id){
		case RewardsData.ULTRA_LUCKY: {
			game.pUpdater.unlockAchievement(ProfileUpdater.ULTRA_LUCKY_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.ULTRA_LUCKY).prize);
		}; break;
		case RewardsData.ONE_SHOT_LUCKY: {
			game.pUpdater.unlockAchievement(ProfileUpdater.ONE_SHOT_LUCKY_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.ONE_SHOT_LUCKY).prize);
		}; break;
		case RewardsData.LUCKY: {
			game.pUpdater.unlockAchievement(ProfileUpdater.LUCKY_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.LUCKY).prize);
		}; break;
		case RewardsData.BIG_FLUSH_JACKPOT: {
			game.pUpdater.unlockAchievement(ProfileUpdater.BIG_FLUSH_JACKPOT_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.BIG_FLUSH_JACKPOT).prize);
		}; break;
		case RewardsData.FLUSH_JACKPOT: {
			game.pUpdater.unlockAchievement(ProfileUpdater.FLUSH_JACKPOT_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.FLUSH_JACKPOT).prize);
		}; break;
		case RewardsData.FRUGAL: {
			game.pUpdater.unlockAchievement(ProfileUpdater.FRUGAL_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.FRUGAL).prize);
		}; break;
		case RewardsData.THRIFTY: {
			game.pUpdater.unlockAchievement(ProfileUpdater.THRIFTY_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.THRIFTY).prize);
		}; break;
		case RewardsData.WEALTHY: {
			game.pUpdater.unlockAchievement(ProfileUpdater.WEALTHY_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.WEALTHY).prize);
		}; break;
		case RewardsData.BRONZE_COLLECTOR: {
			game.pUpdater.unlockAchievement(ProfileUpdater.BRONZE_COLLECTOR_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.BRONZE_COLLECTOR).prize);
		}; break;
		case RewardsData.SILVER_COLLECTOR: {
			game.pUpdater.unlockAchievement(ProfileUpdater.SILVER_COLLECTOR_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.SILVER_COLLECTOR).prize);
		}; break;
		case RewardsData.GOLD_COLLECTOR: {
			game.pUpdater.unlockAchievement(ProfileUpdater.GOLD_COLLECTOR_CODE);
			game.pUpdater.addCC(achievementRewards.get(RewardsData.GOLD_COLLECTOR).prize);
		}; break;
		default: break;
		}
	}

	public void render(SpriteBatch batch){
		batch.draw(blackBG, -180, 0, 1440, 1920);
		titleStateTime += Gdx.graphics.getDeltaTime();
		TextureRegion achievementTextReg = title.getKeyFrame(titleStateTime);

		for (int i = 0; i < achUnlockedRederers.size; i++){
			AchievementUnlockedRenderer achUnlockedRederer = achUnlockedRederers.get(i);
			float x = currentAchievementX + 1080 * i;
			if (x >= -504 && x <= 504) achUnlockedRederer.render(batch, x, 0);
		}

		batch.draw(achievementTextReg, 150, 1400, 787, 241);
		okButton.draw(batch, 1);

		moveCurrentAchievement();
	}

	private void moveCurrentAchievement() {
		if (moveToNextAchievement){
			float nextCurrentX = currentAchievementX - Gdx.graphics.getDeltaTime() * 10000;
			if (nextCurrentX <= targetAchievementX){
				okButton.setDisabled(false);
				currentAchievementX = targetAchievementX;
				moveToNextAchievement = false;
			}
			else currentAchievementX = nextCurrentX;
		}
	}

	/**
	 * @param achievement ex: ProfileUpdater.ULTRA_LUCKY_CODE
	 * @param id ex: RewardsData.ULTRA_LUCKY
	 */
	public void addAchievement(String achievement, String id){
		showAchievementUnlock = true;

		switch (achievement){
		case ProfileUpdater.ULTRA_LUCKY_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("ultraLucky"), "Ultra Lucky", 
				String.valueOf(achievementRewards.get(RewardsData.ULTRA_LUCKY).prize), id)); break;
		case ProfileUpdater.ONE_SHOT_LUCKY_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("oneShotLucky"), "One Shot Lucky", 
				String.valueOf(achievementRewards.get(RewardsData.ONE_SHOT_LUCKY).prize), id)); break;
		case ProfileUpdater.LUCKY_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("lucky"), "Lucky", 
				String.valueOf(achievementRewards.get(RewardsData.LUCKY).prize), id)); break;
		case ProfileUpdater.BIG_FLUSH_JACKPOT_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("bigFlush"), "Big Flush Jackpot", 
				String.valueOf(achievementRewards.get(RewardsData.BIG_FLUSH_JACKPOT).prize), id)); break;
		case ProfileUpdater.FLUSH_JACKPOT_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("flush"), "Flush Jackpot", 
				String.valueOf(achievementRewards.get(RewardsData.FLUSH_JACKPOT).prize), id)); break;
		case ProfileUpdater.FRUGAL_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("frugal"), "Frugal", 
				String.valueOf(achievementRewards.get(RewardsData.FRUGAL).prize), id)); break;
		case ProfileUpdater.THRIFTY_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("thrifty"), "Thrifty", 
				String.valueOf(achievementRewards.get(RewardsData.THRIFTY).prize), id)); break;
		case ProfileUpdater.WEALTHY_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("wealthy"), "Wealthy", 
				String.valueOf(achievementRewards.get(RewardsData.WEALTHY).prize), id)); break;
		case ProfileUpdater.BRONZE_COLLECTOR_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("bronzeCollector"), "Bronze Collector", 
				String.valueOf(achievementRewards.get(RewardsData.BRONZE_COLLECTOR).prize), id)); break;
		case ProfileUpdater.SILVER_COLLECTOR_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("silverCollector"), "Silver Collector", 
				String.valueOf(achievementRewards.get(RewardsData.SILVER_COLLECTOR).prize), id)); break;
		case ProfileUpdater.GOLD_COLLECTOR_CODE: achUnlockedRederers.add(new AchievementUnlockedRenderer(
				game, achievementIcons.findRegion("goldCollector"), "Gold Collector",
				String.valueOf(achievementRewards.get(RewardsData.GOLD_COLLECTOR).prize), id)); break;
		default: break;
		}
	}
	/**
	 * 
	 * @param fullReset false if game is not multiplayer
	 */
	public void resetAll(boolean fullReset) {
		if (fullReset){
			titleStateTime = 0;
			currentAchievementX = 0;
			showAchievementUnlock = false;

			achVerification.reset();
		}
		else {

		}
	}

	public void calculateAnimXs(){
		finalAchievementX = 0 - (achUnlockedRederers.size - 1) * 1080;
		targetAchievementX = 0;
	}

	public void playAchievementSound(){
		achievementUnlockedSound.play(.3f);
	}

	public AchievementVerification getAchVerification() {
		return achVerification;
	}


	public boolean isShowAchievementUnlock() {
		return showAchievementUnlock;
	}

	/**
	 * Disables achievement
	 */
	public void invalidateAchievementManager(){
		showAchievementUnlock = false;
		achUnlockedRederers.clear();
		achVerification.reset();
	}

	public void setShowAchievementUnlock(boolean showAchievementUnlock) {
		this.showAchievementUnlock = showAchievementUnlock;
	}

	@Override
	public void dispose() {
		currentAchievementX = 0;
		targetAchievementX = 0;
		finalAchievementX = 0;
		currentAchievement = 0;
		game = null;
		blackBG = null;
		title = null;
		if (okButton != null) okButton.remove();
		okButton = null;
		if (achVerification != null) achVerification.dispose();
		achVerification = null;
		if (achUnlockedRederers != null){
			for (AchievementUnlockedRenderer a : achUnlockedRederers){
				if (a != null) a.dispose();
				a = null;
			}
			achUnlockedRederers.clear();
		}
		achUnlockedRederers = null;
		achievementRewards = null;
		achievementUnlockedSound = null;
		achievementFrame = null;
		banner = null;
		achievementIcons = null;
	}
}
