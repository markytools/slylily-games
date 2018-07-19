package com.polyNGonsFunctions.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.polyngons.game.PolyNGons;

public class PolyNGonsAdManager {
	private PolyNGons game;
	
	private long interstitialDelay;
	private long delay;
	private boolean showAds;
	
	public PolyNGonsAdManager(PolyNGons game){
		this.game = game;
		
		showAds = !game.pUpdater.noAds();
		interstitialDelay = -1;
		delay = 5000;
	}
	
	public void disableAllAds(){
		showBannerAds(false);
		game.pUpdater.unlockNoAds();
		showAds = false;
	}
	
	public void showBannerAds(boolean show){
		if (showAds) game.requestAd.showAds(show);
		else game.requestAd.showAds(false);
	}
	
//	Run always
	public void showDelayedInterstitial(boolean repeat){
		if ((Gdx.input.isTouched() || Gdx.input.justTouched()) && interstitialDelay != -1){
			interstitialDelay = TimeUtils.millis();
		}
		
		if (interstitialDelay != -1 && TimeUtils.millis() - interstitialDelay >= delay){
			if (delay != 5000) delay = 5000;
			if (!repeat) interstitialDelay = -1;
			else interstitialDelay = TimeUtils.millis();
			if (game.requestAd != null && showAds) game.requestAd.showOrLoadAdmobInterstital();
		}
	}
	
// Run once
	public void initInterstitial(){
		interstitialDelay = TimeUtils.millis();
	}
	
	public void stopInterstitial(){
		interstitialDelay = -1;
	}
	
//	Randomize interstitial showing
	public void randomShowInterstitial(){
		int options = MathUtils.random(1, 5);
		
		if (game.requestAd != null){
			switch (options){
			case 1: {
				delay = 3000;
				initInterstitial();
			}; break;
			case 2: {
				delay = 6000;
				initInterstitial();
			}; break;
			case 3: {
				delay = 9000;
				initInterstitial();
			}; break;
			case 4: {
				delay = 12000;
				initInterstitial();
			}; break;
			default: break;
			}
		}
	}
}
