package com.gameTools.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ScalableFontButton extends Button {

	private BitmapFont font;
	private float scaleX, scaleY;
	private ShaderProgram sProg, defaultShader;
	private String text;
	private Color color;
	private boolean bigButton;
	
	public ScalableFontButton() {
		super();
		// TODO Auto-generated constructor stub
	}



	public ScalableFontButton(Actor child, ButtonStyle style, BitmapFont font, float scaleX,
			float scaleY, String text, ShaderProgram sProg, ShaderProgram defaultShader) {
		super(child, style);
		this.font = font;
		this.scaleX = scaleX / 1.5f;
		this.scaleY = scaleY / 1.1f;
		this.text = text;
		this.sProg = sProg;
		this.defaultShader = defaultShader;
		color = getColor();
		// TODO Auto-generated constructor stub
	}



	public ScalableFontButton(ButtonStyle style, BitmapFont font, float scaleX,
			float scaleY, String text, ShaderProgram sProg) {
		super(style);
		this.font = font;
		this.scaleX = scaleX / 1.5f;
		this.scaleY = scaleY / 1.1f;
		this.text = text;
		this.sProg = sProg;
		color = getColor();
		// TODO Auto-generated constructor stub
	}



	public ScalableFontButton(Drawable up, Drawable down, ShaderProgram sProg,
			Drawable checked, BitmapFont font, float scaleX, float scaleY, String text) {
		super(up, down, checked);
		this.font = font;
		this.scaleX = scaleX / 1.5f;
		this.scaleY = scaleY / 1.1f;
		this.text = text;
		this.sProg = sProg;
		color = getColor();
		// TODO Auto-generated constructor stub
	}



	public ScalableFontButton(Drawable up, Drawable down, BitmapFont font, float scaleX,
			float scaleY, String text, ShaderProgram sProg) {
		super(up, down);
		this.font = font;
		this.scaleX = scaleX / 1.5f;
		this.scaleY = scaleY / 1.1f;
		this.text = text;
		this.sProg = sProg;
		color = getColor();
		// TODO Auto-generated constructor stub
	}



	public ScalableFontButton(Drawable up, BitmapFont font, float scaleX,
			float scaleY, String text, ShaderProgram sProg) {
		super(up);
		this.font = font;
		this.scaleX = scaleX / 1.5f;
		this.scaleY = scaleY / 1.1f;
		this.text = text;
		this.sProg = sProg;
		color = getColor();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		font.setScale(scaleX, scaleY);
		font.setColor(color);
		batch.setShader(sProg);
		sProg.setUniformf("u_spread", 1);
		sProg.setUniformf("u_scale", scaleY);
		float offSetYPos = (bigButton) ? 0 : 0;
		font.drawMultiLine(batch, text, getX() + (getWidth() / 2), getY() + ((getHeight() * font.getScaleY()) / 2) - offSetYPos,
				0, BitmapFont.HAlignment.CENTER);
		batch.setShader(defaultShader);
	}

	public boolean isBigButton() {
		return bigButton;
	}

	public void setBigButton(boolean bigButton) {
		this.bigButton = bigButton;
	}
}
