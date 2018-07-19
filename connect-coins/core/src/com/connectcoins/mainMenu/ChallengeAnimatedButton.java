package com.connectcoins.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.utils.AnimatedButton;

public abstract class ChallengeAnimatedButton extends AnimatedButton {
	private ConnectCoins game;
	private Circle circle;
	private int buttonNumber;
	
	public ChallengeAnimatedButton(ConnectCoins game,
			Array<? extends TextureRegion> buttonRegions, ButtonStyle style,
			BitmapFont font, float scaleX, float scaleY, String englishText,
			Color fontColor, Size size, ArrayMap<String, String> textPack,
			String currentLanguage, int buttonNumber) {
		super(buttonRegions, style, font, scaleX, scaleY, englishText, fontColor, size,
				textPack, currentLanguage, false,
				true);
		this.game = game;
		this.buttonNumber = buttonNumber;
		challengeNumOffsetY = -60;
		
		circle = new Circle();
		circle.x = 0;
		circle.y = 0;
		circle.radius = 94;
		clearListeners();
		createClickListener();
	}

	@Override
	protected void buttonClicked() {
		if (isClicked()){
			super.buttonClicked();
		}
	}

	public void setTargetX(float targetX){
		float recAlignX = 1080 * (buttonNumber / 20);
		float recPosX = ((buttonNumber % 20) % 5) * 216;
		float recAlignY = ((buttonNumber % 20) / 5) * 216;

		circle.x = targetX + recAlignX + recPosX + 108;
		circle.y = (1080 - (recAlignY)) + 108;
	}

	boolean isClicked(){
		float x = game.touchPos.x;
		float y = game.touchPos.y;
		float bottomX = circle.x - 108;
		float bottomY = circle.y - 108;
		float topX = circle.x + 108;
		float topY = circle.y + 108;
		if (x >= bottomX && x < topX && y >= bottomY && y < topY){
			if (Math.pow(x - circle.x, 2) + Math.pow(y - circle.y, 2) <= Math.pow(circle.radius, 2)){
				return true;
			}
		}
		return false;
	}

	@Override
	public abstract void onAnimationEnd();
}
