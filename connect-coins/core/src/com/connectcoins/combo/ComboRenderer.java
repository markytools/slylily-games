package com.connectcoins.combo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.connectcoins.game.ConnectCoins;

public class ComboRenderer {
	protected ConnectCoins game;
	protected float x;
	protected float y;
	protected float animAlpha;
	protected String text;
	protected TextureRegion comboPopupReg;
	protected float upLimit;
	protected float upwardSpeed = 100f;
	protected float alphaAmountDecrease = 2f;
	protected float regionWidth, regionHeight;
	
	public ComboRenderer(ConnectCoins game, float x, float y, String text) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.text = text;

		animAlpha = 1;
		this.y += 50;
		this.upLimit = this.y + 40;

		switch (text){
		case ComboScorer.BIG_FLUSH: {
			comboPopupReg = ComboAssets.bigFlushPopup;
			regionWidth = 471;
			regionHeight = 161;
		}; break;
		case ComboScorer.FLUSH: {
			comboPopupReg = ComboAssets.flushPopup;
			regionWidth = 298;
			regionHeight = 161;
		}; break;
		case ComboScorer.SMALL_FLUSH: {
			comboPopupReg = ComboAssets.smallFlushPopup;
			regionWidth = 588;
			regionHeight = 161;
		}; break;
		case ComboScorer.FIVE_OF_A_KIND: {
			comboPopupReg = ComboAssets.fiveOfAKindPopup;
			regionWidth = 688;
			regionHeight = 162;
		}; break;
		case ComboScorer.FOUR_OF_A_KIND: {
			comboPopupReg = ComboAssets.fourOfAKindPopup;
			regionWidth = 712;
			regionHeight = 161;
		}; break;
		case ComboScorer.THREE_OF_A_KIND: {
			comboPopupReg = ComboAssets.threeOfAKindPopup;
			regionWidth = 770;
			regionHeight = 162;
		}; break;
		default: break;
		}

		this.x -= regionWidth * .3f;
	}

	public void render(SpriteBatch batch){
		this.x = MathUtils.clamp(x, 0, 1080 - regionWidth);
		float deltaTime = Gdx.graphics.getDeltaTime();
		float moveUp = deltaTime * upwardSpeed;
		y += moveUp;
		if (y >= upLimit){
			float alphaAmountToDecrease = deltaTime * alphaAmountDecrease;
			float diff = animAlpha - alphaAmountToDecrease;
			if (!(animAlpha == 1 && diff == 0)){
				animAlpha = diff;
				animAlpha = MathUtils.clamp(animAlpha, 0, 1);
			}
		}

		Color bColor = game.batch.getColor();
		float prevAlpha = bColor.a;
		game.batch.setColor(bColor.r, bColor.g, bColor.b, animAlpha);
		game.batch.draw(comboPopupReg, x, y, regionWidth, regionHeight);
		game.batch.setColor(bColor.r, bColor.g, bColor.b, prevAlpha);

		//			switch (comboValue){
		//			case BRONZE: {
		//				if (text.equals(FLUSH)) game.fManager.drawPopupFont(game.batch, new Color(1, 234f / 255, 192f / 255, animAlpha),
		//						2f, 2f, text, x, y, 200, Align.center);
		//				else game.fManager.drawPopupFont(game.batch, new Color(1, 234f / 255, 192f / 255, animAlpha),
		//						2.1f, 2.1f, text, x, y, 200, Align.center);
		//
		//				if (text.equals(FLUSH)) game.fManager.drawPopupFont(game.batch, new Color(1, 234f / 255, 192f / 255, animAlpha),
		//						2f, 2f, text, x, y, 200, Align.center);
		//				else game.fManager.drawPopupFont(game.batch, new Color(1, 234f / 255, 192f / 255, animAlpha),
		//						2.1f, 2.1f, text, x, y, 200, Align.center);
		//			}; break;
		//			case SILVER: {
		//				if (text.equals(FLUSH)) game.fManager.drawPopupFont(game.batch, new Color(bColor.r, bColor.g, bColor.b, animAlpha),
		//						2.2f, 2.2f, text, x, y, 200, Align.center);
		//				else game.fManager.drawPopupFont(game.batch, new Color(bColor.r, bColor.g, bColor.b, animAlpha),
		//						2.3f, 2.3f, text, x, y, 200, Align.center);
		//
		//				if (text.equals(FLUSH)) game.fManager.drawPopupFont(game.batch, new Color(bColor.r, bColor.g, bColor.b, animAlpha),
		//						2.2f, 2.2f, text, x, y, 200, Align.center);
		//				else game.fManager.drawPopupFont(game.batch, new Color(bColor.r, bColor.g, bColor.b, animAlpha),
		//						2.3f, 2.3f, text, x, y, 200, Align.center);
		//			}; break;
		//			case GOLD: {
		//				if (text.equals(FLUSH)) game.fManager.drawPopupFont(game.batch, new Color(255f / 255, 234f / 255, 0 / 255, animAlpha),
		//						2.4f, 2.4f, text, x, y, 200, Align.center);
		//				else game.fManager.drawPopupFont(game.batch, new Color(255f / 255, 234f / 255, 0 / 255, animAlpha),
		//						2.6f, 2.6f, text, x, y, 200, Align.center);
		//				
		//				if (text.equals(FLUSH)) game.fManager.drawPopupFont(game.batch, new Color(255f / 255, 234f / 255, 0 / 255, animAlpha),
		//						2.4f, 2.4f, text, x, y, 200, Align.center);
		//				else game.fManager.drawPopupFont(game.batch, new Color(255f / 255, 234f / 255, 0 / 255, animAlpha),
		//						2.6f, 2.6f, text, x, y, 200, Align.center);
		//			}; break;
		//			default: break;
		//			}
	}

	public float getAnimAlpha() {
		return animAlpha;
	}
	
	public void reset(){
		x = 0;
		y = 0;
		animAlpha = 0;
		text = null;
		comboPopupReg = null;
		upLimit = 0;
		upwardSpeed = 100;
		alphaAmountDecrease = 2f;
		regionWidth = 0;
		regionHeight = 0;
	}
}