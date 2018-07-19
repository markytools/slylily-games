package com.connectcoins.connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.connectcoins.coins.Coin;
import com.connectcoins.coins.CoinQueue;
import com.connectcoins.combo.ComboScorer;
import com.connectcoins.functions.GameModeConfig;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.GameScreen;
import com.connectcoins.listeners.CoinActor;
import com.connectcoins.slots.Slot;
import com.connectcoins.slots.SlotManager;
import com.connectcoins.tutorial.TutorialManager;

public class CoinSubmission implements Disposable {
	private boolean RECREATE_COINS = true;
	private boolean activateIlluminate = true;

	private TutorialManager tutorialManager;	
	private GameScreen gScreen;
	private ComboScorer comboScorer;
	private SlotManager slotManager;
	private CoinQueue coinQueue;
	private ArrayMap<CoinAnimation, CoinActor> coinSlotMap;
	private Array<CoinAnimation> coinAnimations;
	private Array<Boolean> animInitializer;
	private float[] stateTimes;
	private boolean animPlaying = false;

	public CoinSubmission(GameScreen gScreen, ConnectionManager cManager, ComboScorer comboScorer, SlotManager slotManager,
			CoinQueue coinQueue, TutorialManager tutorialManager){
		this.gScreen = gScreen;
		this.tutorialManager = tutorialManager;
		this.comboScorer = comboScorer;
		this.slotManager = slotManager;
		this.coinQueue = coinQueue;
		setup();
	}

	private void setup() {
		coinAnimations = new Array<CoinAnimation>();
		animInitializer = new Array<Boolean>();
		coinSlotMap = new ArrayMap<CoinAnimation, CoinActor>();
	}

	public void readyCoinAnimation(){
		animInitializer.clear();
		stateTimes = new float[coinAnimations.size];
		for (int i = 0; i < coinAnimations.size; i++){
			animInitializer.add(Boolean.valueOf(false));
			stateTimes[i] = 0;
		}
	}

	public void startInitializers(){
		for (int i = 0; i < animInitializer.size; i++){
			final int index = i;
			Timer t = new Timer();
			t.scheduleTask(new Task() {
				@Override
				public void run() {
					animInitializer.set(index, true);
				}
			}, ((i + 1) * 80) / 1000.0f);
			t.start();
			//			new Timer().schedule(new TimerTask(){
			//				@Override
			//				public void run() {
			//					animInitializer.set(index, true);
			//				}
			//			}, (i + 1) * 80);
		}
		setAnimPlaying(true);
	}

	public void renderAnimation(SpriteBatch batch, GameModeConfig gModeConfig){
		if (animPlaying){
			//			for (int i = animInitializer.size - 1; i >= 0; i--){
			for (int i = 0; i < animInitializer.size; i++){
				Boolean startAnim = animInitializer.get(i);
				if (startAnim){
					float stateTime = stateTimes[i] += Gdx.graphics.getDeltaTime();

					CoinAnimation hCAnim = coinAnimations.get(i);
					HalfCoinAnimation currentCoin = hCAnim.currentCoin;
					HalfCoinAnimation nextCoin = hCAnim.nextCoin;
					CoinActor cActor = coinSlotMap.get(hCAnim);
					Slot slot = cActor.getSlot();
					float x = hCAnim.x;
					float y = hCAnim.y;

					int index;
					if (hCAnim.renderCurrentCoin){
						if (slot.queueForRecreate){
							slot.queueForRecreate = false;
							if (RECREATE_COINS){
								//								if (gModeConfig.mode == GameMode.MULTIPLAYER){
								//									coinQueue.recreateCoinForMultiplayer(slot, 1, gModeConfig.isHost == 1);
								//								}
								//								else {
								coinQueue.recreateCoin(slot, 1);
								//								}
							}
							else coinQueue.removeCoins(slot, 1);
						}
						if (slot.renderCoins) slot.renderCoins = false;
						index = currentCoin.getKeyFrameIndex(stateTime);
						if (!currentCoin.isAnimationFinished(stateTime)){
							TextureRegion currentCRegion = currentCoin.getKeyFrames()[index];
							getScale(true, index);

							float xOffset = getWidthU2(true, index);

							batch.draw(currentCRegion, x + (216 - xOffset) / 2, y, 0, 0, xOffset, 216, 1, 1, 0);
							if (currentCoin.isHalfCoin()){
								TextureRegion nextCRegion = currentCoin.getKeyFrame2(stateTime);
								batch.draw(nextCRegion, x + (216 - xOffset) / 2, y, 0, 0, xOffset, 216, 1, 1, 0);
							}
						}
						else {
							hCAnim.renderCurrentCoin = false;
							stateTimes[i] = 0;
						}
					}
					else {
						if (!RECREATE_COINS){
							slot.renderCoins = true;
							animInitializer.set(i, Boolean.valueOf(false));
							if (i == animInitializer.size - 1){
								animInitializer.clear();
								coinAnimations.clear();
								coinSlotMap.clear();
								setAnimPlaying(false);
							}
						}
						if (nextCoin != null){
							index = nextCoin.getKeyFrameIndex(stateTime);
							if (!nextCoin.isAnimationFinished(stateTime)){
								TextureRegion currentCRegion = nextCoin.getKeyFrames()[index];
								getScale(false, index);

								float xOffset = getWidthU2(false, index);
								batch.draw(currentCRegion, x + (216 - xOffset) / 2, y, 0, 0, xOffset, 216, 1, 1, 0);
								if (nextCoin.isHalfCoin()){
									TextureRegion nextCRegion = nextCoin.getKeyFrame2(stateTime);
									batch.draw(nextCRegion, x + (216 - xOffset) / 2, y, 0, 0, xOffset, 216, 1, 1, 0);
								}
							}
							else {
								slot.renderCoins = true;
								animInitializer.set(i, Boolean.valueOf(false));
								if (i == animInitializer.size - 1){
									animInitializer.clear();
									coinAnimations.clear();
									coinSlotMap.clear();
									setAnimPlaying(false);
								}
							}
						}
					}
				}
			}
		}
		else {
			if (activateIlluminate){
				if (comboScorer.getComboForIllum() >= 8){
					if (tutorialManager.getCurrentMsgNum() == 28){
						tutorialManager.nextMsg(28);
						gScreen.setResetTutorialPane(true);
					}
					comboScorer.resetIllum(false);
					slotManager.shineAllTopCoins();
				}
			}
		}

		comboScorer.render(batch);
	}

	private float getWidthU2(boolean currentCoin, int index){
		if (currentCoin){
			switch (index){
			case 0: return 216;
			case 1: return 173;
			case 2: return 130;
			case 3: return 86;
			case 4: return 43;
			case 5: return 11;
			default: return 1;
			}
		}
		else {
			switch (index){
			case 5: return 216;
			case 4: return 173;
			case 3: return 130;
			case 2: return 86;
			case 1: return 43;
			case 0: return 11;
			default: return 1;
			}
		}
	}

	private float getScale(boolean currentCoin, int index){
		if (currentCoin){
			switch (index){
			case 0: return 1;
			case 1: return 1.1f;
			case 2: return 1.2f;
			case 3: return 1.3f;
			case 4: return 1.4f;
			case 5: return 1.5f;
			default: return 1;
			}
		}
		else {
			switch (index){
			case 5: return 1;
			case 4: return 1.1f;
			case 3: return 1.2f;
			case 2: return 1.3f;
			case 1: return 1.4f;
			case 0: return 1.5f;
			default: return 1;
			}
		}
	}

	public void setQueuedCoins(CoinActor cActor, int coinNum){
		Array<Coin> coinQueue = cActor.getSlot().getCoinQueue();
		Coin nextCoin = (coinQueue.size != 1) ? coinQueue.get(1) : null;
		createCoinAnimation(cActor, coinQueue.get(0), nextCoin, coinNum, cActor.getX(), cActor.getY());
	}

	private void createCoinAnimation(CoinActor cActor, Coin currentCoin, Coin nextCoin, int coinNum, float x, float y){
		TextureRegion[] coinRegions1 = currentCoin.getCoinRegions();
		TextureRegion[] coinRegions2 = null;
		if (nextCoin != null){
			coinRegions2 = nextCoin.getCoinRegions();
		}

		CoinAnimation cAnim;
		coinAnimations.add(cAnim = new CoinAnimation(addCoinAnimation(coinRegions1, coinNum), addCoinAnimation(coinRegions2, coinNum)));
		cAnim.x = x;
		cAnim.y = y;

		coinSlotMap.put(cAnim, cActor);
		cActor.setCoinConnected(true);
	}

	public static HalfCoinAnimation addCoinAnimation(TextureRegion[] coinRegion, int coinNum){
		if (coinRegion != null){
			HalfCoinAnimation halfCoinAnim;
			float frameDuration = .010f;
			if (coinRegion.length == 1){
				TextureRegion[] keyFrames1 = new TextureRegion[6];
				for (int i = 0; i < 6; i++){
					keyFrames1[i] = coinRegion[0];
				}
				halfCoinAnim = new HalfCoinAnimation(frameDuration, keyFrames1, null);
			}
			else {
				TextureRegion[] keyFrames1 = new TextureRegion[6];
				TextureRegion[] keyFrames2 = new TextureRegion[6];
				for (int i = 0; i < 6; i++){
					keyFrames1[i] = coinRegion[0];
				}
				for (int i = 0; i < 6; i++){
					keyFrames2[i] = coinRegion[1];
				}
				halfCoinAnim = new HalfCoinAnimation(frameDuration, keyFrames1, keyFrames2);
			}

			halfCoinAnim.coinNum = coinNum;

			return halfCoinAnim;
		}
		return null;
	}

	public void resetAll(){
		RECREATE_COINS = true;
		activateIlluminate = true;
		animPlaying = false;
		stateTimes = null;

		for (CoinAnimation cAnim : coinAnimations) cAnim.reset();
		coinAnimations.clear();
		coinAnimations.shrink();

		//		for (Boolean b : animInitializer) b = null;
		animInitializer.clear();
		animInitializer.shrink();
		for (int i = 0; i < coinSlotMap.size; i++){
			coinSlotMap.getKeyAt(i).reset();
		}
		coinSlotMap.clear();
		coinSlotMap.shrink();
	}

	public boolean isAnimPlaying() {
		return animPlaying;
	}

	public void setAnimPlaying(boolean animPlaying) {
		this.animPlaying = animPlaying;
	}

	public void setRECREATE_COINS(boolean rECREATE_COINS) {
		this.RECREATE_COINS = rECREATE_COINS;
	}

	public void setActivateIlluminate(boolean activateIlluminate) {
		this.activateIlluminate = activateIlluminate;
		if (activateIlluminate == false) comboScorer.resetIllum(false);
	}

	@Override
	public void dispose() {
		RECREATE_COINS = true;
		activateIlluminate = true;

		gScreen = null;
		tutorialManager = null;
		comboScorer = null;
		slotManager = null;
		coinQueue = null;
		coinSlotMap.clear();
		coinSlotMap = null;
		coinAnimations.clear();
		coinAnimations = null;
		animInitializer.clear();
		animInitializer = null;
		stateTimes = null;
		animPlaying = false;
	}
}
