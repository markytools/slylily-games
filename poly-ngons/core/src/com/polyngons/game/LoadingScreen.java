package com.polyngons.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;
import com.gameTools.game.ShadedBitmapFont;
import com.polyNGonsFunctions.game.PolyBlockAnimation;

public class LoadingScreen {
	private PolyNGons game;
	private ShadedBitmapFont font;
	private int dot, currentLoadDisplay;
	private long delayDot, totalLoadingTime;
	private boolean startDelay;
	private PolyBlockAnimation blockAnimation;
	
	public LoadingScreen(PolyNGons game){
		this.game = game;
		createFont();
		dot = 4;
		startDelay = false;
		currentLoadDisplay = 0;
		
		blockAnimation = new PolyBlockAnimation(game);
	}
	
	private void createFont(){
		font = game.bLayout.getCustomFont();
	}
	
	public void render(){
		if (startDelay){
			createFont();
			totalLoadingTime = TimeUtils.millis();
			startDelay = false;
			delayDot = TimeUtils.millis();
			currentLoadDisplay = 0;
		}
		game.batch.draw(game.splashImage, 0, 0, 800, 500);
		if (TimeUtils.millis() - delayDot >= 500){
			delayDot = TimeUtils.millis();
			dot = (dot + 1 < 4) ? dot + 1 : 1;
		}
		
		if (TimeUtils.millis() - totalLoadingTime >= 5000) {
			totalLoadingTime = TimeUtils.millis();
			if (currentLoadDisplay == 0) currentLoadDisplay = 1;
			else currentLoadDisplay = 0;
		}

		if (currentLoadDisplay == 0){
			font.drawWrappedFont(game.batch, "Generating Puzzle " + getDot(dot),
					100, 135, 500, 2.2f, 2.2f, Color.WHITE);
		}
		else {
			font.drawWrappedFont(game.batch, "More difficult puzzles may take a while to load, please wait " + getDot(dot),
					100, 158, 500, 2f, 2f, Color.WHITE);
		}
		blockAnimation.drawAnimation(game.batch, 600, 60, .8f, .8f);
	}
	
	private String getDot(int dot){
		switch (dot){
		case 1: return ".";
		case 2: return "..";
		case 3: return "...";
		default: return null;
		}
	}
	
	public void reset(){
		dot = 1;
		startDelay = true;
		blockAnimation.resetAnimation();
		currentLoadDisplay = 0;
	}

	public PolyBlockAnimation getBlockAnimation() {
		return blockAnimation;
	}
}
