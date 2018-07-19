package com.gameTools.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShadedBitmapFont extends BitmapFont {
	private ShaderProgram sProg, defaultShader;

	public ShadedBitmapFont() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShadedBitmapFont(FileHandle fontFile, TextureRegion region,
			boolean flip, ShaderProgram sProg, ShaderProgram defaultShader) {
		super(fontFile, region, flip);
		this.sProg = sProg;
		this.defaultShader = defaultShader;
		// TODO Auto-generated constructor stub
	}

	public ShadedBitmapFont(FileHandle fontFile, TextureRegion region, ShaderProgram sProg) {
		super(fontFile, region);
		this.sProg = sProg;
		// TODO Auto-generated constructor stub
	}

	public void drawFont(Batch batch, CharSequence str, float x, float y, float scaleX, float scaleY,
			Color color) {
		setScale(scaleX, scaleY);
		setColor(color);
		batch.setShader(sProg);
		sProg.setUniformf("u_spread", 1);
		sProg.setUniformf("u_scale", scaleY);
		draw(batch, str, x, y);
		batch.setShader(defaultShader);
	}
	
	public void drawFont(Batch batch, CharSequence str, float x, float y,
			int start, int end, float scaleX, float scaleY, Color color) {
		setScale(scaleX, scaleY);
		setColor(color);
		batch.setShader(sProg);
		sProg.setUniformf("u_spread", 1);
		sProg.setUniformf("u_scale", scaleY);
		draw(batch, str, x, y, start, end);
		batch.setShader(defaultShader);
	}
	
	public void drawMultiLineFont(Batch batch, CharSequence str, float x,
			float y, float scaleX, float scaleY, Color color) {
		setScale(scaleX, scaleY);
		setColor(color);
		batch.setShader(sProg);
		sProg.setUniformf("u_spread", 1);
		sProg.setUniformf("u_scale", scaleY);
		drawMultiLine(batch, str, x, y);
		batch.setShader(defaultShader);
	}
	
	public void drawMultiLineFont(Batch batch, CharSequence str, float x, float y,
			float alignmentWidth, HAlignment alignment, float scaleX, float scaleY, Color color) {
		setScale(scaleX, scaleY);
		setColor(color);
		batch.setShader(sProg);
		sProg.setUniformf("u_spread", 1);
		sProg.setUniformf("u_scale", scaleY);
		drawMultiLine(batch, str, x, y, alignmentWidth, alignment);
		batch.setShader(defaultShader);
	}
	
	public void drawWrappedFont(Batch batch, CharSequence str, float x,
			float y, float wrapWidth, float scaleX, float scaleY, Color color) {
		setScale(scaleX, scaleY);
		setColor(color);
		batch.setShader(sProg);
		sProg.setUniformf("u_spread", 1);
		sProg.setUniformf("u_scale", scaleY);
		drawWrapped(batch, str, x, y, wrapWidth);
		batch.setShader(defaultShader);
	}
	
	public void drawWrappedFont(Batch batch, CharSequence str, float x, float y, float wrapWidth, 
			HAlignment alignment, float scaleX, float scaleY, Color color) {
		setScale(scaleX, scaleY);
		setColor(color);
		batch.setShader(sProg);
		sProg.setUniformf("u_spread", 1);
		sProg.setUniformf("u_scale", scaleY);
		drawWrapped(batch, str, x, y, wrapWidth, alignment);
		batch.setShader(defaultShader);
	}
}
