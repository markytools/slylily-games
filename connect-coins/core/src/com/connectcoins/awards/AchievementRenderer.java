package com.connectcoins.awards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.connectcoins.game.ConnectCoins;

public class AchievementRenderer extends Actor {
	private ConnectCoins game;
	private TextureRegion imgBG1, imgBG2, img, ccSymbol, border;
	private boolean achieved, isCCReward;
	private String name, desc, reward;

	public AchievementRenderer(ConnectCoins game, String name, String desc, String reward, Texture img, boolean isCCReward, boolean achieved){
		this.game = game;
		this.name = name;
		this.img = new TextureRegion(img);
		this.desc = desc;
		this.reward = reward;
		this.isCCReward = isCCReward;
		this.achieved = achieved;
		imgBG1 = new TextureRegion(game.assetLoader.getTexture("achievementImgBG1"));
		imgBG2 = new TextureRegion(game.assetLoader.getTexture("achievementImgBG2"));
		ccSymbol = new TextureRegion(game.assetLoader.getTexture("ccSymbol"));
		border = new TextureRegion(game.assetLoader.getTexture("achievementBorder"));
		setSize(936, 360);
	}

	public AchievementRenderer(ConnectCoins game, String name, String desc, String reward, TextureRegion img, boolean isCCReward, boolean achieved){
		this.game = game;
		this.name = name;
		this.img = img;
		this.desc = desc;
		this.reward = reward;
		this.isCCReward = isCCReward;
		this.achieved = achieved;
		imgBG1 = new TextureRegion(game.assetLoader.getTexture("achievementImgBG1"));
		imgBG2 = new TextureRegion(game.assetLoader.getTexture("achievementImgBG2"));
		ccSymbol = new TextureRegion(game.assetLoader.getTexture("ccSymbol"));
		border = new TextureRegion(game.assetLoader.getTexture("achievementBorder"));
		setSize(936, 360);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		float x = getX();
		float y = getY();

		batch.draw(border, x, y, 936, 360);
		if (!achieved){
			batch.draw(imgBG1, x + 20, y + 20, 320, 320);

			BitmapFontCache descCache = game.fManager.centeredWrappedFont(game.fManager.largeFont4, (SpriteBatch) batch, Color.valueOf("190901"),
					desc, x + 360, y + 200, 550);
			ConnectCoins.glyphLayout.setText(descCache.getFont(), desc, descCache.getColor(), 550, Align.center, true);
			float alignY = ConnectCoins.glyphLayout.height / 2;
			game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.BLACK, 1, 1, name, x + 430, y + 320 - 32.5f * 32.5f / alignY,
					400, Align.center);

			if (isCCReward){
				ConnectCoins.glyphLayout.setText(game.fManager.largeFont3, reward);
				float rewardX = x + 650 - (ConnectCoins.glyphLayout.width / 2);
				float alignY2 = y + 83 + 32.5f * 32.5f / alignY;
				batch.draw(ccSymbol, rewardX - 60, alignY2 - 55, 70, 70);
				game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("042226"), 1, 1,
						reward, rewardX, alignY2, 650, Align.left);
			}
			else {
				ConnectCoins.glyphLayout.setText(game.fManager.largeFont3, reward);
				float rewardX = x + 645 - (ConnectCoins.glyphLayout.width / 2);
				float alignY2 = y + 83 + 32.5f * 32.5f / alignY;
				game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("042226"), 1, 1,
						reward, rewardX, alignY2, 650, Align.left);
			}
		}
		else {
			batch.draw(imgBG2, x + 20, y + 20, 320, 320);

			game.fManager.drawFont(game.fManager.largeFont4, (SpriteBatch) batch, Color.BLACK, 1.2f, 1.2f, name,
					x + 360, y + 235, 550, Align.center);
			game.fManager.drawFont(game.fManager.largeFont4, (SpriteBatch) batch, Color.valueOf("5b1f61"), 1.2f, 1.2f, "COMPLETED",
					x + 360, y + 165, 550, Align.center);
		}
		batch.draw(img, x + 70, y + 70, 220, 220);
	}

	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
}
