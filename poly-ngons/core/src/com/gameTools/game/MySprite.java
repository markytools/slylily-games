package com.gameTools.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class MySprite extends Sprite {

	public MySprite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MySprite(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	public MySprite(Texture texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	public float getActualX(){
		return MathUtils.round(getBoundingRectangle().x);
	}

	public float getActualY(){
		return MathUtils.round(getBoundingRectangle().y);
	}

	public float getActualWidth(){
		return MathUtils.round(getBoundingRectangle().width);
	}

	public float getActualHeight(){
		return MathUtils.round(getBoundingRectangle().height);
	}

	/*	Must be called only once.*/
	public void setProperPosition(float x, float y){
		setX(0);
		setY(0);
		setPosition(x - getActualX(), y - getActualY());
	}
}
