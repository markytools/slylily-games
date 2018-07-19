package com.gameExtensions.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.springdynamics.game.SpringDynamics;

public class FontManager {
	private BitmapFont font;
	
	public FontManager(SpringDynamics game){
		createFont();
	}
	private void createFont() {
		boolean isWebGL = Gdx.app.getType() == ApplicationType.WebGL;
		Texture textFont = new Texture(Gdx.files.internal("fonts/font.png"), !isWebGL);
		if (!isWebGL) textFont.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.MipMapLinearNearest);
		font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"),
				new TextureRegion(textFont));
		font.setColor(Color.valueOf("a97017"));
	}
	
	public BitmapFont getFont() {
		return font;
	}
}