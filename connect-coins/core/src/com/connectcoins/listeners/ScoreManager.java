package com.connectcoins.listeners;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.gameCompletion.ScoreCoinsData;
import com.connectcoins.listeners.Scoreboard.ScoreboardState;

public class ScoreManager extends ScoreCoinsData implements Disposable {
	private String comboName;
	private String score;
	private String cc;
	private ConnectCoins game;
	private ScoreboardState sBState;
	private float challScoreOffsetX;

	public ScoreManager(ScoreboardState sBState, float challScoreOffsetX, String comboName, String score, String cc,
			ConnectCoins game, int totalOneCoins, int totalFiveCoins, int totalTenCoins, int totalShinyCoins) {
		super(totalOneCoins, totalFiveCoins, totalTenCoins, totalShinyCoins);
		this.comboName = comboName;
		this.challScoreOffsetX = challScoreOffsetX;
		this.sBState = sBState;
		this.score = score;
		this.cc = cc;
		this.game = game;
	}

	public void render(SpriteBatch batch, float y){
		switch (sBState){
		case NORMAL: {
			String scoreDisplay = (Integer.parseInt(score) != 0) ? score : Scoreboard.NONE;
			String ccDisplay = (Integer.parseInt(cc) != 0) ? cc : Scoreboard.NONE;

			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, comboName, 52, y, 400,
					Align.center);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, scoreDisplay, 375, y, 400,
					Align.center);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, ccDisplay, 661, y, 400,
					Align.center);
		}; break;
		case CHALLENGE: {
			String scoreDisplay = (Integer.parseInt(score) != 0) ? score : Scoreboard.NONE;

			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, comboName, 52, y, 400,
					Align.center);
			game.fManager.drawSmallFont(batch, Color.valueOf("000000"), .8f, .8f, scoreDisplay, 375 + challScoreOffsetX, y, 400,
					Align.center);
		}; break;
		default: break;
		}
	}

	@Override
	public void dispose() {
		comboName = null;
		score = null;
		cc = null;
		game = null;
		sBState = null;
	}
}
