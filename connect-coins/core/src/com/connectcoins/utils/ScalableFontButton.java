package com.connectcoins.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class ScalableFontButton extends Button {
	public enum Size {
		AVERAGE, LARGE, VERY_SMALL
	}
	
	public static class ButtonFontScale {
		public float scaleX, scaleY;
		public float offsetX, offsetY;
		
		public ButtonFontScale(float scaleX, float scaleY, float offsetX,
				float offsetY) {
			super();
			this.scaleX = scaleX;
			this.scaleY = scaleY;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
		}
		
		public void setData(float scaleX, float scaleY, float offsetX,
				float offsetY){
			this.scaleX = scaleX;
			this.scaleY = scaleY;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
		}
	}

	public static final ButtonFontScale verySmallFontScale = new ButtonFontScale(0, 0, -5, -27);
	public static final ButtonFontScale averageFontScale = new ButtonFontScale(0, 0, 3, -22);
	public static final ButtonFontScale largeFontScale = new ButtonFontScale(0, 0, 10, -15);

	protected BitmapFont font;
	protected String text;
	protected Size size;
	protected float challengeNumOffsetY = 0;
	private float scaleX, scaleY;
	private Color color, prevColor;
	
	private boolean drawSuper = true;
	
	public ScalableFontButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScalableFontButton(ButtonStyle style, BitmapFont font, float scaleX,
			float scaleY, String text, Color fontColor, Size size) {
		super(style);
		this.font = font;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.text = text;
		this.color = fontColor;
		this.size = size;
	}

	public ScalableFontButton(Drawable up, Drawable down, Drawable checked, BitmapFont font, float scaleX, float scaleY, String text,
			Size size) {
		super(up, down, checked);
		this.font = font;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.text = text;
		this.size = size;
		color = getColor();
		// TODO Auto-generated constructor stub
	}



	public ScalableFontButton(Drawable up, Drawable down, BitmapFont font, float scaleX,
			float scaleY, String tex, Size size) {
		super(up, down);
		this.font = font;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.size = size;
		color = getColor();
		// TODO Auto-generated constructor stub
	}


	public ScalableFontButton(Drawable up, BitmapFont font, float scaleX,
			float scaleY, String text, Size size) {
		super(up);
		this.font = font;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.text = text;
		this.size = size;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (drawSuper) super.draw(batch, parentAlpha);
		
		prevColor = font.getColor();
		font.setColor(color);
		float prevScaleX = font.getScaleX();
		float prevScaleY = font.getScaleY();
		
		float offSetXPos = 0, offSetYPos = 0;
		switch (size){
		case AVERAGE: {
			offSetXPos = averageFontScale.offsetX;
			offSetYPos = averageFontScale.offsetY;
			font.getData().setScale(scaleX + averageFontScale.scaleX, scaleY + averageFontScale.scaleY);
		}; break;
		case LARGE: {
			offSetXPos = largeFontScale.offsetX;
			offSetYPos = largeFontScale.offsetY;
			font.getData().setScale(scaleX + largeFontScale.scaleX, scaleY + largeFontScale.scaleY);
		}; break;
		case VERY_SMALL: {
			offSetXPos = verySmallFontScale.offsetX;
			offSetYPos = verySmallFontScale.offsetY;
			font.getData().setScale(scaleX + verySmallFontScale.scaleX, scaleY + verySmallFontScale.scaleY);
		}; break;
		default: {
			font.getData().setScale(scaleX, scaleY);
		}; break;
		}
//		font.drawMultiLine(batch, text, getX() + (getWidth() / 2) + offSetXPos,
//				getY() + ((getHeight() * font.getScaleY()) / 2) - offSetYPos + challengeNumOffsetY,
//				0, Align.center);
		font.draw(batch, text, getX() + (getWidth() / 2) + offSetXPos,
				getY() + ((getHeight() * font.getScaleY()) / 2) - offSetYPos + challengeNumOffsetY,
				0, Align.center, false);


		font.setColor(prevColor);
		font.getData().setScale(prevScaleX, prevScaleY);
	}

	public String getText() {
		return text;
	}

	public boolean isDrawSuper() {
		return drawSuper;
	}

	public void setDrawSuper(boolean drawSuper) {
		this.drawSuper = drawSuper;
	}
}
