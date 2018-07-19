package com.connectcoins.combo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.connectcoins.audio.GameScreenAudioAssets;
import com.connectcoins.awards.CoinsComboData;
import com.connectcoins.challenge.ChallengeConnectionData;
import com.connectcoins.coins.Coin;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.gameCompletion.Scorer;
import com.connectcoins.listeners.Scoreboard;
import com.connectcoins.slots.SlotManager;
import com.connectcoins.ui.SpecialUI;

public class ComboScorer implements Disposable {
	public static final String BIG_FLUSH = "BIG FLUSH!!!";
	public static final String FLUSH = "FLUSH!!";
	public static final String SMALL_FLUSH = "SMALL FLUSH!";
	public static final String FIVE_OF_A_KIND = "FIVE OF A KIND";
	public static final String FOUR_OF_A_KIND = "FOUR OF A KIND";
	public static final String THREE_OF_A_KIND = "THREE OF A KIND";
	public static final String NO_COMBO = "NO COMBO";
	public static final String ON_FIRE = "ON FIRE";
	public static final String ILLUMINATE = "ILLUMINATEE";

	private static final Color bronzeCoinColor = Color.valueOf("ffdfbf");
	private static final Color bronzeTextColor = Color.valueOf("ebd5aa");
	private static final Color silverCoinColor = Color.valueOf("ffffff");
	private static final Color silverTextColor = Color.valueOf("dcdcdc");
	private static final Color goldCoinColor = Color.valueOf("ffea03");
	private static final Color goldTextColor = Color.valueOf("ffed54");

	public static final int BIG_FLUSH_SCORE = 300;
	public static final int FLUSH_SCORE = 250;
	public static final int SMALL_FLUSH_SCORE = 220;
	public static final int FIVE_OF_A_KIND_SCORE = 120;
	public static final int FOUR_OF_A_KIND_SCORE = 100;
	public static final int THREE_OF_A_KIND_SCORE = 80;

	public static final int BIG_FLUSH_EXTRA = 7;
	public static final int FLUSH_EXTRA = 6;
	public static final int SMALL_FLUSH_EXTRA = 5;
	public static final int FIVE_OF_A_KIND_EXTRA = 4;
	public static final int FOUR_OF_A_KIND_EXTRA = 3;
	public static final int THREE_OF_A_KIND_EXTRA = 2;
	public static final int NO_COMBO_EXTRA = 1;

	////
	////	USE THIS FOR OPPONENT COMBO DATA
	////	USE THIS FOR OPPONENT COMBO DATA
	////
	public static final int BIG_FLUSH_CC = 15;
	public static final int FLUSH_CC = 10;
	public static final int SMALL_FLUSH_CC = 7;
	public static final int FIVE_OF_A_KIND_CC = 6;
	public static final int FOUR_OF_A_KIND_CC = 4;
	public static final int THREE_OF_A_KIND_CC = 2;
	public static final int NO_COMBO_CC = 1;

	public enum ComboValue {
		GOLD, SILVER, BRONZE
	}

	public class CoinScoreRenderer implements Disposable {
		private static final float targetX = 500;
		private static final float targetY = 1600;

		private ConnectCoins game;
		private CoinScoreRendererTracker cSRTracker;
		private ComboValue comboValue;
		private Coin coin;
		private String score;
		private float stateTime = 0;
		private float upLimitY;
		private Animation<TextureRegion> animation;
		private float cosAngle, sinAngle;
		private float scoreBoundX;
		private float scoreBoundY;
		private long delay = -1;
		private float multiplier = 1;
		private boolean reachedDest = false;

		public CoinScoreRenderer(ConnectCoins game, CoinScoreRendererTracker cSRTracker, ComboValue comboValue, Coin coin,
				Animation<TextureRegion> animation, float x, float y, String score, String shine){
			this.game = game;
			this.cSRTracker = cSRTracker;
			this.coin = coin;
			this.comboValue = comboValue;
			this.animation = animation;
			if (Integer.parseInt(shine) != 1) this.score = score + "X" + shine;
			else this.score = score;


			upLimitY = y + 50;

			ConnectCoins.glyphLayout.setText(game.fManager.largeFont, this.score);
			scoreBoundX = x - 21 - (ConnectCoins.glyphLayout.width / 2);
			scoreBoundY = y;

			float deltaX = targetX - scoreBoundX;
			float deltaY = targetY - scoreBoundY;
			cosAngle = (float) Math.cos(MathUtils.atan2(deltaY, deltaX));
			sinAngle = (float) Math.sin(MathUtils.atan2(deltaY, deltaX));
		}

		public void render(SpriteBatch batch){
			stateTime += Gdx.graphics.getDeltaTime();

			float moveUp = Gdx.graphics.getDeltaTime() * 90;
			if (scoreBoundY < upLimitY) scoreBoundY += moveUp;

			float speed = Gdx.graphics.getDeltaTime() * 1500 * multiplier;
			if (animation.isAnimationFinished(stateTime)){
				if (delay == -1) delay = TimeUtils.millis();
				if (delay != -1 && TimeUtils.millis() - delay > 200){
					float deltaX = cosAngle * speed;
					float deltaY = sinAngle * speed;
					multiplier += .05f;

					if (deltaX < 0){
						if (scoreBoundX + deltaX < targetX){
							reachedDest = true;
							scoreBoundX = targetX;
						}
						else scoreBoundX += deltaX;
					}
					else if (deltaX > 0){
						if (scoreBoundX + deltaX > targetX){
							reachedDest = true;
							scoreBoundX = targetX;
						}
						else scoreBoundX += deltaX;
					}

					if (deltaY < 0){
						if (scoreBoundY + deltaY < targetY){
							reachedDest = true;
							scoreBoundY = targetY;
						}
						else scoreBoundY += deltaY;
					}
					else if (deltaY > 0){
						if (scoreBoundY + deltaY > targetY){
							reachedDest = true;
							scoreBoundY = targetY;
						}
						else scoreBoundY += deltaY;
					}
				}
			}

			Color starCoinColor = null, textColor = null;
			switch (comboValue){
			case GOLD: {
				starCoinColor = goldCoinColor;
				textColor = goldTextColor;
			}; break;
			case SILVER: {
				starCoinColor = silverCoinColor;
				textColor = silverTextColor;
			}; break;
			case BRONZE: {
				starCoinColor = bronzeCoinColor;
				textColor = bronzeTextColor;
			}; break;
			default: break;
			}

			Color bColor = batch.getColor();
			batch.setColor(starCoinColor);
			batch.draw(scoreSymbolAnim.getKeyFrame(stateTime), scoreBoundX, scoreBoundY, 80, 80);
			game.fManager.drawDataFont(game.batch, textColor, .95f, .95f, score, scoreBoundX + 60, scoreBoundY + 68, 200,
					Align.left);
			game.fManager.drawDataFont(game.batch, textColor, .95f, .95f, score, scoreBoundX + 60, scoreBoundY + 68, 200,
					Align.left);
			batch.setColor(bColor);
		}

		public void removeFromTracker(){
			if (this.cSRTracker.started) this.cSRTracker.coinCount--;
		}

		public CoinScoreRendererTracker getcSRTracker() {
			return cSRTracker;
		}

		public Coin getCoin() {
			return coin;
		}

		@Override
		public void dispose() {
			game = null;
			cSRTracker.dispose();
			comboValue = null;
			coin = null;
			score = null;
			stateTime = 0;
			upLimitY = 0;
			animation = null;
			cosAngle = 0;
			sinAngle = 0;
			scoreBoundX = 0;
			scoreBoundY = 0;
			delay = 0;
			multiplier = 0;
			reachedDest = false;
		}
	}

	private class CoinScoreRendererTracker implements Disposable {
		public String comboName;
		public int totalScore = 0;
		public int totalCC = 0;
		public int coinCount = 0;
		public boolean started = false;
		public Array<Coin> connectedCoins;
		public int totalOneCoins;
		public int totalFiveCoins;
		public int totalTenCoins;
		public int totalShinyCoins;

		public CoinScoreRendererTracker(Array<Coin> connectedCoins){
			this.connectedCoins = connectedCoins;
			totalOneCoins = 0;
			totalFiveCoins = 0;
			totalTenCoins = 0;
			totalShinyCoins = 0;
		}
		
		public void reset(){
			connectedCoins = null;
			comboName = null;
			totalScore = 0;
			totalCC = 0;
			coinCount = 0;
			started = false;
			totalOneCoins = 0;
			totalFiveCoins = 0;
			totalTenCoins = 0;
			totalShinyCoins = 0;
		}

		@Override
		public void dispose() {
			comboName = null;
			totalScore = 0;
			totalCC = 0;
			coinCount = 0;
			started = false;
			connectedCoins = null;
			totalOneCoins = 0;
			totalFiveCoins = 0;
			totalTenCoins = 0;
			totalShinyCoins = 0;
		}
	}
	
	private ConnectCoins game;
	private ChallengeConnectionData challengeCData;
	private Array<ComboRenderer> comboRenderers;
	private Array<CoinScoreRenderer> coinScoreRenderers;
	private Array<CoinScoreRendererTracker> coinSRTrackers;
	private Animation<TextureRegion> scoreSymbolAnim;
	private ComboAssets comboAssets;
	private SpecialUI specialUI;
	private int comboForIllum = 0;
	private int currentPlayedIlluminationAudio;
	private Array<Sound> illuminationSounds;
	private boolean enableIllumination = true;
	private Scoreboard scoreboard;
	private Scorer scorer;

	public ComboScorer(ConnectCoins game, ChallengeConnectionData challengeCData, Scorer scorer){
		this.game = game;
		this.scorer = scorer;
		this.challengeCData = challengeCData;

		currentPlayedIlluminationAudio = 0;
		illuminationSounds = new Array<Sound>();
		illuminationSounds.add(GameScreenAudioAssets.illuminate1);
		illuminationSounds.add(GameScreenAudioAssets.illuminate2);
		illuminationSounds.add(GameScreenAudioAssets.illuminate3);
		illuminationSounds.add(GameScreenAudioAssets.illuminate4);
		illuminationSounds.add(GameScreenAudioAssets.illuminate5);
		illuminationSounds.add(GameScreenAudioAssets.illuminate6);
		illuminationSounds.add(GameScreenAudioAssets.illuminate7);
		illuminationSounds.add(GameScreenAudioAssets.illuminate8);
		comboAssets = new ComboAssets(game);
		init();
	}

	private void init() {
		comboRenderers = new Array<ComboRenderer>();
		coinScoreRenderers = new Array<CoinScoreRenderer>();
		coinSRTrackers = new Array<CoinScoreRendererTracker>();

		TextureAtlas starCoinAtlas = game.assetLoader.getTextureAtlas("starCoin");
		Array<TextureRegion> starCoinRegionsArray = new Array<TextureRegion>();
		for (int i = 0; i < 31; i++){
			starCoinRegionsArray.add(starCoinAtlas.findRegion(String.valueOf(i)));
		}
		scoreSymbolAnim = new Animation<TextureRegion>(0.015f, starCoinRegionsArray, PlayMode.NORMAL);
	}

	public CoinsComboData addCoinConnection(Array<Coin> connectedCoins){
		CoinsComboData coinsComboData = null;
		float sumX = 0, sumY = 0;
		float averageX = 0, averageY = 0;
		for (Coin coin : connectedCoins) sumX += SlotManager.getSlotX(coin.getCoinID());
		averageX = sumX / connectedCoins.size;
		for (Coin coin : connectedCoins) sumY += SlotManager.getSlotY(coin.getCoinID());
		averageY = sumY / connectedCoins.size;
		averageY += 120;

		while (true){
			Array<Coin> comboCoins;
			CoinScoreRendererTracker cSRTracker = new CoinScoreRendererTracker(connectedCoins);
			comboCoins = ComboManager.hasBigFlush(connectedCoins);
			int shinyCoinCount = 0;
			for (Coin coin : connectedCoins){
				if (coin.isShining()) shinyCoinCount++;
			}

			if (comboCoins != null){
				int realScore = 0;
				int bonusScore = 0;
				increaseComboForIllum();
				boolean isOnFire = specialUI.addHeatPoints(6f/12);
				GameScreenAudioAssets.illuminate8.play(1);
				coinsComboData = new CoinsComboData(connectedCoins, Scoreboard.BIG_FLUSH);
				comboRenderers.add(new ComboRenderer(game, averageX, averageY, BIG_FLUSH));
				for (Coin connectedCoin : connectedCoins){
					for (Coin comboCoin : comboCoins){
						if (connectedCoin == comboCoin && !hasCoinScoreRenderer(connectedCoin)){
							int score = BIG_FLUSH_SCORE * (shinyCoinCount + 1);
							int cc = BIG_FLUSH_CC;
							cSRTracker.coinCount++;
							cSRTracker.totalScore += score;
							realScore += score;
							if (cSRTracker.totalCC == 0) cSRTracker.totalCC = cc * (shinyCoinCount + 1);
							if (cSRTracker.comboName == null) cSRTracker.comboName = Scoreboard.BIG_FLUSH;
							addCoinInfoToTracker(cSRTracker, connectedCoin);
							addCoinScoreRenderer(ComboValue.GOLD, cSRTracker, connectedCoin, String.valueOf(BIG_FLUSH_SCORE),
									String.valueOf(shinyCoinCount + 1));
						}
					}
				}
				for (Coin connectedCoin : connectedCoins){
					if (!hasCoinScoreRenderer(connectedCoin)){
						int score = BIG_FLUSH_EXTRA * (shinyCoinCount + 1);
						cSRTracker.coinCount++;
						cSRTracker.totalScore += score;
						bonusScore += score;
						addCoinInfoToTracker(cSRTracker, connectedCoin);
						addCoinScoreRenderer(ComboValue.GOLD, cSRTracker, connectedCoin, String.valueOf(BIG_FLUSH_CC),
								String.valueOf(shinyCoinCount + 1));
					}
				}
				sendComboData(connectedCoins, BIG_FLUSH_CC, isOnFire, scoreboard.getTotalScore() + cSRTracker.totalScore);
				scorer.addOneCombo(BIG_FLUSH, realScore, bonusScore);
				cSRTracker.started = true;
				coinSRTrackers.add(cSRTracker);
				break;
			}
			comboCoins = ComboManager.hasFlush(connectedCoins);
			if (comboCoins != null){
				int realScore = 0;
				int bonusScore = 0;
				increaseComboForIllum();
				boolean isOnFire = specialUI.addHeatPoints(5f/12);
				GameScreenAudioAssets.illuminate7.play(.9f);
				coinsComboData = new CoinsComboData(connectedCoins, Scoreboard.FLUSH);
				comboRenderers.add(new ComboRenderer(game, averageX, averageY, FLUSH));
				for (Coin connectedCoin : connectedCoins){
					for (Coin comboCoin : comboCoins){
						if (connectedCoin == comboCoin && !hasCoinScoreRenderer(connectedCoin)){
							int score = FLUSH_SCORE * (shinyCoinCount + 1);
							int cc = FLUSH_CC;
							cSRTracker.coinCount++;
							cSRTracker.totalScore += score;
							realScore += score;
							if (cSRTracker.totalCC == 0) cSRTracker.totalCC = cc * (shinyCoinCount + 1);
							if (cSRTracker.comboName == null) cSRTracker.comboName = Scoreboard.FLUSH;
							addCoinInfoToTracker(cSRTracker, connectedCoin);
							addCoinScoreRenderer(ComboValue.GOLD, cSRTracker, connectedCoin, String.valueOf(FLUSH_SCORE),
									String.valueOf(shinyCoinCount + 1));
						}
					}
				}
				for (Coin connectedCoin : connectedCoins){
					if (!hasCoinScoreRenderer(connectedCoin)){
						int score = FLUSH_EXTRA * (shinyCoinCount + 1);
						cSRTracker.coinCount++;
						cSRTracker.totalScore += score;
						bonusScore += score;
						addCoinInfoToTracker(cSRTracker, connectedCoin);
						addCoinScoreRenderer(ComboValue.GOLD, cSRTracker, connectedCoin, String.valueOf(FLUSH_EXTRA),
								String.valueOf(shinyCoinCount + 1));
					}
				}
				sendComboData(connectedCoins, FLUSH_CC, isOnFire, scoreboard.getTotalScore() + cSRTracker.totalScore);
				scorer.addOneCombo(FLUSH, realScore, bonusScore);
				cSRTracker.started = true;
				coinSRTrackers.add(cSRTracker);
				break;
			}
			comboCoins = ComboManager.hasSmallFlush(connectedCoins);
			if (comboCoins != null){
				int realScore = 0;
				int bonusScore = 0;
				increaseComboForIllum();
				boolean isOnFire = specialUI.addHeatPoints(4f/12);
				GameScreenAudioAssets.illuminate6.play(.8f);
				coinsComboData = new CoinsComboData(connectedCoins, Scoreboard.SMALL_FLUSH);
				comboRenderers.add(new ComboRenderer(game, averageX, averageY, SMALL_FLUSH));
				for (Coin connectedCoin : connectedCoins){
					for (Coin comboCoin : comboCoins){
						if (connectedCoin == comboCoin && !hasCoinScoreRenderer(connectedCoin)){
							int score = SMALL_FLUSH_SCORE * (shinyCoinCount + 1);
							int cc = SMALL_FLUSH_CC;
							cSRTracker.coinCount++;
							cSRTracker.totalScore += score;
							realScore += score;
							if (cSRTracker.totalCC == 0) cSRTracker.totalCC = cc * (shinyCoinCount + 1);
							if (cSRTracker.comboName == null) cSRTracker.comboName = Scoreboard.SMALL_FLUSH;
							addCoinInfoToTracker(cSRTracker, connectedCoin);
							addCoinScoreRenderer(ComboValue.SILVER, cSRTracker, connectedCoin, String.valueOf(SMALL_FLUSH_SCORE),
									String.valueOf(shinyCoinCount + 1));
						}
					}
				}
				for (Coin connectedCoin : connectedCoins){
					if (!hasCoinScoreRenderer(connectedCoin)){
						int score = SMALL_FLUSH_EXTRA * (shinyCoinCount + 1);
						cSRTracker.coinCount++;
						cSRTracker.totalScore += score;
						bonusScore += score;
						addCoinInfoToTracker(cSRTracker, connectedCoin);
						addCoinScoreRenderer(ComboValue.SILVER, cSRTracker, connectedCoin, String.valueOf(SMALL_FLUSH_EXTRA),
								String.valueOf(shinyCoinCount + 1));
					}
				}
				sendComboData(connectedCoins, SMALL_FLUSH_CC, isOnFire, scoreboard.getTotalScore() + cSRTracker.totalScore);
				scorer.addOneCombo(SMALL_FLUSH, realScore, bonusScore);
				cSRTracker.started = true;
				coinSRTrackers.add(cSRTracker);
				break;
			}
			comboCoins = ComboManager.hasFiveOfAKind(connectedCoins);
			if (comboCoins != null){
				int realScore = 0;
				int bonusScore = 0;
				resetIllum(true);
				boolean isOnFire = specialUI.addHeatPoints(3f/12);
				GameScreenAudioAssets.illuminate5.play(.7f);
				coinsComboData = new CoinsComboData(connectedCoins, Scoreboard.FIVE_OF_A_KIND);
				comboRenderers.add(new ComboRenderer(game, averageX, averageY, FIVE_OF_A_KIND));
				for (Coin connectedCoin : connectedCoins){
					for (Coin comboCoin : comboCoins){
						if (connectedCoin == comboCoin && !hasCoinScoreRenderer(connectedCoin)){
							int score = FIVE_OF_A_KIND_SCORE * (shinyCoinCount + 1);
							int cc = FIVE_OF_A_KIND_CC;
							cSRTracker.coinCount++;
							cSRTracker.totalScore += score;
							realScore += score;
							if (cSRTracker.totalCC == 0) cSRTracker.totalCC = cc * (shinyCoinCount + 1);
							if (cSRTracker.comboName == null) cSRTracker.comboName = Scoreboard.FIVE_OF_A_KIND;
							addCoinInfoToTracker(cSRTracker, connectedCoin);
							addCoinScoreRenderer(ComboValue.SILVER, cSRTracker, connectedCoin, String.valueOf(FIVE_OF_A_KIND_SCORE), 
									String.valueOf(shinyCoinCount + 1));
						}
					}
				}
				for (Coin connectedCoin : connectedCoins){
					if (!hasCoinScoreRenderer(connectedCoin)){
						int score = FIVE_OF_A_KIND_EXTRA * (shinyCoinCount + 1);
						cSRTracker.coinCount++;
						cSRTracker.totalScore += score;
						bonusScore += score;
						addCoinInfoToTracker(cSRTracker, connectedCoin);
						addCoinScoreRenderer(ComboValue.SILVER, cSRTracker, connectedCoin, String.valueOf(FIVE_OF_A_KIND_EXTRA),
								String.valueOf(shinyCoinCount + 1));
					}
				}
				sendComboData(connectedCoins, FIVE_OF_A_KIND_CC, isOnFire, scoreboard.getTotalScore() + cSRTracker.totalScore);
				scorer.addOneCombo(FIVE_OF_A_KIND, realScore, bonusScore);
				cSRTracker.started = true;
				coinSRTrackers.add(cSRTracker);
				break;
			}
			comboCoins = ComboManager.hasFourOfAKind(connectedCoins);
			if (comboCoins != null){
				int realScore = 0;
				int bonusScore = 0;
				resetIllum(true);
				boolean isOnFire = specialUI.addHeatPoints(2.5f/12);
				GameScreenAudioAssets.illuminate4.play(.6f);
				coinsComboData = new CoinsComboData(connectedCoins, Scoreboard.FOUR_OF_A_KIND);
				comboRenderers.add(new ComboRenderer(game, averageX, averageY, FOUR_OF_A_KIND));
				for (Coin connectedCoin : connectedCoins){
					for (Coin comboCoin : comboCoins){
						if (connectedCoin == comboCoin && !hasCoinScoreRenderer(connectedCoin)){
							int score = FOUR_OF_A_KIND_SCORE * (shinyCoinCount + 1);
							int cc = FOUR_OF_A_KIND_CC;
							cSRTracker.coinCount++;
							cSRTracker.totalScore += score;
							realScore += score;
							if (cSRTracker.totalCC == 0) cSRTracker.totalCC = cc * (shinyCoinCount + 1);
							if (cSRTracker.comboName == null) cSRTracker.comboName = Scoreboard.FOUR_OF_A_KIND;
							addCoinInfoToTracker(cSRTracker, connectedCoin);
							addCoinScoreRenderer(ComboValue.BRONZE, cSRTracker, connectedCoin, String.valueOf(FOUR_OF_A_KIND_SCORE),
									String.valueOf(shinyCoinCount + 1));
						}
					}
				}
				for (Coin connectedCoin : connectedCoins){
					if (!hasCoinScoreRenderer(connectedCoin)){
						int score = FOUR_OF_A_KIND_EXTRA * (shinyCoinCount + 1);
						cSRTracker.coinCount++;
						cSRTracker.totalScore += score;
						bonusScore += score;
						addCoinInfoToTracker(cSRTracker, connectedCoin);
						addCoinScoreRenderer(ComboValue.BRONZE, cSRTracker, connectedCoin, String.valueOf(FOUR_OF_A_KIND_EXTRA),
								String.valueOf(shinyCoinCount + 1));
					}
				}
				sendComboData(connectedCoins, FOUR_OF_A_KIND_CC, isOnFire, scoreboard.getTotalScore() + cSRTracker.totalScore);
				scorer.addOneCombo(FOUR_OF_A_KIND, realScore, bonusScore);
				cSRTracker.started = true;
				coinSRTrackers.add(cSRTracker);
				break;
			}
			comboCoins = ComboManager.hasThreeOfAKind(connectedCoins);
			if (comboCoins != null){
				int realScore = 0;
				int bonusScore = 0;
				resetIllum(true);
				boolean isOnFire = specialUI.addHeatPoints(2f/12);
				GameScreenAudioAssets.illuminate3.play(.5f);
				coinsComboData = new CoinsComboData(connectedCoins, Scoreboard.THREE_OF_A_KIND);
				comboRenderers.add(new ComboRenderer(game, averageX, averageY, THREE_OF_A_KIND));
				for (Coin connectedCoin : connectedCoins){
					for (Coin comboCoin : comboCoins){
						if (connectedCoin == comboCoin && !hasCoinScoreRenderer(connectedCoin)){
							int score = THREE_OF_A_KIND_SCORE * (shinyCoinCount + 1);
							int cc = THREE_OF_A_KIND_CC;
							cSRTracker.coinCount++;
							cSRTracker.totalScore += score;
							realScore += score;
							if (cSRTracker.totalCC == 0) cSRTracker.totalCC = cc * (shinyCoinCount + 1);
							if (cSRTracker.comboName == null) cSRTracker.comboName = Scoreboard.THREE_OF_A_KIND;
							addCoinInfoToTracker(cSRTracker, connectedCoin);
							addCoinScoreRenderer(ComboValue.BRONZE, cSRTracker, connectedCoin, String.valueOf(THREE_OF_A_KIND_SCORE),
									String.valueOf(shinyCoinCount + 1));
						}
					}
				}
				for (Coin connectedCoin : connectedCoins){
					if (!hasCoinScoreRenderer(connectedCoin)){
						int score = THREE_OF_A_KIND_EXTRA * (shinyCoinCount + 1);
						cSRTracker.coinCount++;
						cSRTracker.totalScore += score;
						bonusScore += score;
						addCoinInfoToTracker(cSRTracker, connectedCoin);
						addCoinScoreRenderer(ComboValue.BRONZE, cSRTracker, connectedCoin, String.valueOf(THREE_OF_A_KIND_EXTRA),
								String.valueOf(shinyCoinCount + 1));
					}
				}
				sendComboData(connectedCoins, THREE_OF_A_KIND_CC, isOnFire, scoreboard.getTotalScore() + cSRTracker.totalScore);
				scorer.addOneCombo(THREE_OF_A_KIND, realScore, bonusScore);
				cSRTracker.started = true;
				coinSRTrackers.add(cSRTracker);
				break;
			}

			int realScore = 0;
			resetIllum(false);
			if (coinsComboData == null) coinsComboData = new CoinsComboData(connectedCoins, Scoreboard.NONE);
			for (Coin connectedCoin : connectedCoins){
				if (!hasCoinScoreRenderer(connectedCoin)){
					int score = NO_COMBO_EXTRA * (shinyCoinCount + 1);
					cSRTracker.coinCount++;
					cSRTracker.totalScore += score;
					realScore += score;
					if (cSRTracker.comboName == null) cSRTracker.comboName = Scoreboard.NONE;
					addCoinInfoToTracker(cSRTracker, connectedCoin);
					addCoinScoreRenderer(ComboValue.BRONZE, cSRTracker, connectedCoin, String.valueOf(NO_COMBO_EXTRA),
							String.valueOf(shinyCoinCount + 1));
				}
			}
			sendComboData(connectedCoins, NO_COMBO_CC, false, scoreboard.getTotalScore() + cSRTracker.totalScore);
			scorer.addOneCombo(NO_COMBO, realScore, 0);
			cSRTracker.started = true;
			coinSRTrackers.add(cSRTracker);
			break;
		}
		
		return coinsComboData;
	}
	
	private void sendComboData(Array<Coin> connectedCoins, int combo, boolean isOnFire, int totalScore){
//		if (game.appWarpManager.hasInternetConnection() && game.gMConfig.mode == GameMode.MULTIPLAYER){
//			String[] connectedCoinIds = new String[connectedCoins.size];
//			for (int i = 0; i < connectedCoins.size; i++){
//				connectedCoinIds[i] = connectedCoins.get(i).getCoinID();
//			}
//			int illuminate = 0;
//			if (getComboForIllum() >= 8) illuminate = 1;
//			MultiplayerCoinDataFull mCDataFull = new MultiplayerCoinDataFull(AWDataConstants.T_COINS_DATA_2, connectedCoinIds, combo,
//					((isOnFire) ? 1 : 0), illuminate, totalScore);
//			String jsonStr = game.getJson().toJson(mCDataFull);
//			System.out.println(jsonStr.length());
//			
//			try {
//				WarpController.getInstance().sendGameUpdate(jsonStr, true);
//			}
//			catch (NoClassDefFoundError e){
//				e.printStackTrace();
//			}
//		}
	}
	
	private void addCoinInfoToTracker(CoinScoreRendererTracker cSRTracker, Coin coin){
		switch (coin.getCoinValue()){
		case ONE: cSRTracker.totalOneCoins++; break;
		case FIVE: cSRTracker.totalFiveCoins++; break;
		case TEN: cSRTracker.totalTenCoins++; break;
		default: break;
		}
		if (coin.isShining()) cSRTracker.totalShinyCoins++;
	}

	private void addCoinScoreRenderer(ComboValue comboValue, CoinScoreRendererTracker cSRTracker, Coin coin, String points,
			String shine){
		coinScoreRenderers.add(new CoinScoreRenderer(game, cSRTracker, comboValue, coin, scoreSymbolAnim, 
				SlotManager.getSlotX(coin.getCoinID()) + 105, SlotManager.getSlotY(coin.getCoinID()) + 130, points, shine));
		
	}

	private boolean hasCoinScoreRenderer(Coin coin){
		for (CoinScoreRenderer coinScoreRenderer : coinScoreRenderers){
			if (coinScoreRenderer.getCoin() == coin) return true;
		}
		return false;
	}

	public void render(SpriteBatch batch){
		for (CoinScoreRenderer coinScoreRenderer : coinScoreRenderers){
			coinScoreRenderer.render(batch);
		}
		for (CoinScoreRenderer coinScoreRenderer : coinScoreRenderers){
			if (coinScoreRenderer.reachedDest){
				coinScoreRenderer.removeFromTracker();
				CoinScoreRendererTracker cSRTracker = coinScoreRenderer.getcSRTracker();
				if (cSRTracker.coinCount == 0){
					scoreboard.addToScoreBoard(cSRTracker.comboName, cSRTracker.totalScore, cSRTracker.totalCC,
							cSRTracker.totalOneCoins, cSRTracker.totalFiveCoins, cSRTracker.totalTenCoins, cSRTracker.totalShinyCoins);
					challengeCData.addData(cSRTracker.connectedCoins, cSRTracker.totalScore, cSRTracker.totalCC);
				}
				coinScoreRenderers.removeValue(coinScoreRenderer, true);
				continue;
			}
		}
		coinScoreRenderers.shrink();

		for (ComboRenderer comboRenderer : comboRenderers){
			comboRenderer.render(batch);
		}
		for (ComboRenderer comboRenderer : comboRenderers){
			if (comboRenderer.getAnimAlpha() <= 0){
				comboRenderers.removeValue(comboRenderer, true);
			}
		}
		comboRenderers.shrink();
	}
	
	public boolean scoreRenderersIsEmpty(){
		return coinScoreRenderers.size == 0 && comboRenderers.size == 0;
	}

	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

	public void setSpecialUI(SpecialUI specialUI) {
		this.specialUI = specialUI;
	}
	
	public int getComboForIllum() {
		return comboForIllum;
	}

	private void increaseComboForIllum(){
		if (game.gMConfig.mode != GameMode.CHALLENGE){
			if (enableIllumination){
				comboForIllum++;
				if (currentPlayedIlluminationAudio + 1 >= illuminationSounds.size){
					GameScreenAudioAssets.illuminate.play(1);
					currentPlayedIlluminationAudio = 0;
				}
				else currentPlayedIlluminationAudio++;
			}
		}
	}
	
	public void resetIllum(boolean playSounds){
		comboForIllum = 0;
		currentPlayedIlluminationAudio = 0;
//		if (playSounds) GameScreenAudioAssets.noIlluminate.play();
	}
	
	public boolean isEnableIllumination() {
		return enableIllumination;
	}

	public void setEnableIllumination(boolean enableIllumination) {
		this.enableIllumination = enableIllumination;
		if (enableIllumination == false) resetIllum(false);
	}
	
	public void resetAll(){
		comboForIllum = 0;
		currentPlayedIlluminationAudio = 0;
		for (ComboRenderer c : comboRenderers){
			c.reset();
		}
		comboRenderers.clear();
		comboRenderers.shrink();
		coinScoreRenderers.clear();
		coinScoreRenderers.shrink();
		for (CoinScoreRendererTracker cR : coinSRTrackers){
			cR.reset();
		}
		coinSRTrackers.clear();
		coinSRTrackers.shrink();
	}

	@Override
	public void dispose() {
		game = null;
		challengeCData = null;
		comboRenderers.clear();
		comboRenderers = null;
		for (CoinScoreRenderer cR : coinScoreRenderers){
			cR.dispose();
			cR = null;
		}
		coinScoreRenderers.clear();
		coinScoreRenderers = null;
		for (CoinScoreRendererTracker cRT : coinSRTrackers){
			cRT.dispose();
			cRT = null;
		}
		coinSRTrackers.clear();
		coinSRTrackers = null;
		scoreSymbolAnim = null;
		comboAssets.dispose();
		comboAssets = null;
		specialUI = null;
		comboForIllum = 0;
		currentPlayedIlluminationAudio = 0;
		illuminationSounds.clear();
		illuminationSounds = null;
		enableIllumination = false;
		scoreboard = null;
		scorer = null;
	}
}