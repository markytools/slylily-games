package com.gameTools.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ScalableFontLabel extends Label {
	private ShaderProgram sProg, defaultShader;
	private float scaleX, scaleY;

	public ScalableFontLabel(CharSequence text, LabelStyle style, ShaderProgram sProg,
			ShaderProgram defaultShader){
		super(text, style);
		this.sProg = sProg;
		this.defaultShader = defaultShader;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		validate();
		Color color = getColor();
		color.a *= parentAlpha;
		if (getStyle().background != null) {
			batch.setColor(color.r, color.g, color.b, color.a);
			getStyle().background.draw(batch, getX(), getY(), getWidth(), getHeight());
		}
		if (getStyle().fontColor != null) color.mul(getStyle().fontColor);

		getBitmapFontCache().getFont().setColor(getColor());
		getBitmapFontCache().getFont().setScale(scaleX, scaleY);
		
		batch.setShader(sProg);
		sProg.setUniformf("u_spread", 1);
		sProg.setUniformf("u_scale", scaleY);
		getBitmapFontCache().getFont().drawMultiLine(batch, getText(), getX(), getY(), 20,
				BitmapFont.HAlignment.CENTER);
		batch.setShader(defaultShader);
	}
	
	public void setFontScales(float scaleX, float scaleY){
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
}
