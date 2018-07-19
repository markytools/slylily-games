package com.polyNGonsUI.game;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.polyngons.game.GameScreen;

public class GameAnimation {
	private GameScreen gScreen;
	private AssetManager m;
	private HashMap<String, AssetDescriptor<Texture>> assetMap;
	private Array<Sprite> arrowUpMap, arrowDownMap;
	private long arrowUpFTime, arrowDownFTime;
	private long arrowUpAlphaTime, arrowDownAlphaTime;
	private float arrowUpAlpha, arrowDownAlpha;
	private Sprite currenUpSprite, currenDownSprite;

	public GameAnimation(){

	}

	public GameAnimation(GameScreen gScreen){
		this.gScreen = gScreen;
		arrowUpMap = new Array<Sprite>();
		arrowDownMap = new Array<Sprite>();

		m = gScreen.game.assetM;
		assetMap = gScreen.game.assetID;
		createArrowAnimations();
	}

	private void createArrowAnimations(){
		arrowUpMap.add(new Sprite(m.get(assetMap.get("arrow1"))));
		arrowUpMap.add(new Sprite(m.get(assetMap.get("arrow2"))));
		arrowUpMap.add(new Sprite(m.get(assetMap.get("arrow3"))));

		arrowDownMap.add(new Sprite(m.get(assetMap.get("arrow1"))));
		arrowDownMap.add(new Sprite(m.get(assetMap.get("arrow2"))));
		arrowDownMap.add(new Sprite(m.get(assetMap.get("arrow3"))));
		
		currenDownSprite = arrowDownMap.first();
		currenDownSprite.setOriginCenter();

		currenUpSprite = arrowUpMap.first();
		currenUpSprite.setOriginCenter();

		arrowUpFTime = TimeUtils.millis();
		arrowDownFTime = TimeUtils.millis();
		
		arrowUpAlphaTime = TimeUtils.millis();
		arrowDownAlphaTime = TimeUtils.millis();
		
		arrowUpAlpha = .6f;
		arrowDownAlpha = .6f;
	}

	public void renderArrowsUp(){
		if (timePast(arrowUpAlphaTime, 1000)){
			arrowUpAlphaTime = TimeUtils.millis();
			if (arrowUpAlpha == .3f) arrowUpAlpha = .6f;
			else arrowUpAlpha = .3f;
		}
		
		if (timePast(arrowUpFTime, 340)){
			arrowUpFTime = TimeUtils.millis();
			currenUpSprite = getAnimation(arrowUpMap, currenUpSprite);
		}
		
		currenUpSprite.setBounds(650, 350, 150, 150);
		currenUpSprite.setRotation(180);
		currenUpSprite.draw(gScreen.uiBatch, arrowUpAlpha);
	}

	public void renderArrowsDown(){
		if (timePast(arrowDownAlphaTime, 1000)){
			arrowDownAlphaTime = TimeUtils.millis();
			if (arrowDownAlpha == .3f) arrowDownAlpha = .6f;
			else arrowDownAlpha = .3f;
		}
		
		if (timePast(arrowDownFTime, 340)){
			arrowDownFTime = TimeUtils.millis();
			currenDownSprite = getAnimation(arrowDownMap, currenDownSprite);
		}
		
		currenDownSprite.setBounds(650, 0, 150, 150);
		currenDownSprite.draw(gScreen.uiBatch, arrowDownAlpha);
	}

	private boolean timePast(long lastTime, long delay){
		if (TimeUtils.millis() - lastTime >= delay) return true;
		else return false;
	}

	private Sprite getAnimation(Array<Sprite> map, Sprite currentSprite){
		for (Sprite sp : map){
			if (sp == currentSprite){
				int i = map.indexOf(sp, true);
				if (i == map.size - 1) i = 0;
				else i++;
				
				return map.get(i);
			}
		}

		return null;
	}
}