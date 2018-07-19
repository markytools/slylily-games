package com.connectcoins.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Align;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.languages.LanguageManager;
import com.connectcoins.languages.LanguageUtils;

public class FontManager {
	public BitmapFont largeFont, largeFont2, largeFont3, largeFont4;
	public BitmapFont smallFont;
	public BitmapFont tutorialFont;
	public BitmapFont dataFont;
	public BitmapFont displayFont;
	public BitmapFont multiplayerRFnt;

	public ShaderProgram fontShader, smallFontShader;
	private LanguageManager langManager;
	private boolean isWebGL = Gdx.app.getType() == ApplicationType.WebGL;

	public FontManager(LanguageManager langManager) {
		this.langManager = langManager;

		Texture texture5 = new Texture(
				Gdx.files.internal("utils/fonts/displayFont.png"), !isWebGL);
		if (!isWebGL) texture5.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);
		displayFont = new BitmapFont(
				Gdx.files.internal("utils/fonts/displayFont.fnt"),
				new TextureRegion(texture5));
		
//		 Texture texture2 = new
//		 Texture(Gdx.files.internal("utils/fonts/displayFont.png"), true);
//		 texture2.setFilter(TextureFilter.MipMapLinearNearest,
//		 TextureFilter.MipMapLinearNearest);
//		
//		 displayFont = new
//		 BitmapFont(Gdx.files.internal("utils/fonts/displayFont.fnt"), new
//		 TextureRegion(texture2));
//		 displayFont.setColor(Color.valueOf("142058"));
	}

	/**
	 * Call this after zip files are downloaded and installed.
	 */
	public void setup(){
		updateFontsForLanguage(getFilteredStringsFromLanguage());
		langManager.unloadAll();

		Texture textureB = new Texture(
				Gdx.files.internal("utils/fonts/largeFont2.png"), !isWebGL);
		if (!isWebGL) textureB.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);
		largeFont2 = new BitmapFont(
				Gdx.files.internal("utils/fonts/largeFont2.fnt"),
				new TextureRegion(textureB));

		Texture textureC = new Texture(
				Gdx.files.internal("utils/fonts/largeFont3.png"), !isWebGL);
		if (!isWebGL) textureC.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);
		largeFont3 = new BitmapFont(
				Gdx.files.internal("utils/fonts/largeFont3.fnt"),
				new TextureRegion(textureC));

		Texture textureD = new Texture(
				Gdx.files.internal("utils/fonts/largeFont4.png"), !isWebGL);
		if (!isWebGL) textureD.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);
		largeFont4 = new BitmapFont(
				Gdx.files.internal("utils/fonts/largeFont4.fnt"),
				new TextureRegion(textureD));

		Texture texture2 = new Texture(
				Gdx.files.internal("utils/fonts/smallFont.png"), !isWebGL);
		if (!isWebGL) texture2.setFilter(TextureFilter.MipMap,
				TextureFilter.Linear);
		smallFont = new BitmapFont(
				Gdx.files.internal("utils/fonts/smallFont.fnt"),
				new TextureRegion(texture2));

		Texture texture4 = new Texture(
				Gdx.files.internal("utils/fonts/dataFont.png"), !isWebGL);
		if (!isWebGL) texture4.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);
		dataFont = new BitmapFont(
				Gdx.files.internal("utils/fonts/dataFont.fnt"),
				new TextureRegion(texture4));

//		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("utils/fonts/arialbd.ttf"));
//		FreeTypeFontParameter fParam = new FreeTypeFontParameter();
//		fParam.genMipMaps = true;
//		fParam.magFilter = TextureFilter.MipMapLinearLinear;
//		fParam.minFilter = TextureFilter.MipMapLinearLinear;
//		fParam.color = Color.valueOf("fed410");
//		fParam.size = 48;
//		multiplayerRFnt - THIS IS ACTUALLY A FREETYPEFONT
//		multiplayerRFnt = gen.generateFont(fParam);
		Texture texture5 = new Texture(
				Gdx.files.internal("utils/fonts/arialbd.png"), !isWebGL);
		if (!isWebGL) texture5.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);
		multiplayerRFnt = new BitmapFont(
				Gdx.files.internal("utils/fonts/arialbd.fnt"),
				new TextureRegion(texture5));
	}
	
	public void initTutorialFont(String characters){
		if (tutorialFont != null) tutorialFont.dispose();
//		FreeTypeFontGenerator cjkGen = new FreeTypeFontGenerator(Gdx.files.internal("utils/fonts/Noto Sans Hans Bold.ttc"));
//		FreeTypeFontParameter largeFontParam = new FreeTypeFontParameter();
//		largeFontParam.genMipMaps = true;
//		largeFontParam.magFilter = TextureFilter.MipMapLinearLinear;
//		largeFontParam.minFilter = TextureFilter.MipMapLinearLinear;
//		largeFontParam.color = Color.WHITE;
//		largeFontParam.size = 55;
//		
//		largeFontParam.characters = characters;
//		tutorialFont = cjkGen.generateFont(largeFontParam);
		
		Texture tutorialInitTexture = new Texture(
				Gdx.files.internal("utils/fonts/notosans.png"), !isWebGL);
		if (!isWebGL)tutorialInitTexture.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);
		tutorialFont = new BitmapFont(
				Gdx.files.internal("utils/fonts/notosans.fnt"),
				new TextureRegion(tutorialInitTexture));
//		cjkGen.dispose();
	}
	
	public String getFilteredStringsFromLanguage(){
		String characters = "";
		characters += LanguageManager.ENGLISH;
		characters += "Very Low";
		characters += "Low";
		characters += "Average";
		characters += "High";
		
		for (int i = 0; i < LanguageManager.tutorial_en.size; i++){
			characters += LanguageManager.tutorial_en.get(i);
		}
		
		for (int i = 0; i < LanguageManager.button_en.size; i++){
			characters += LanguageManager.button_en.getValueAt(i);
		}

		for (int i = 0; i < LanguageManager.options_en.size; i++){
			characters += LanguageManager.options_en.getValueAt(i);
		}
		for (int i = 1; i <= 100; i++){
			characters += String.valueOf(i);
		}
		
		return LanguageUtils.filterSameCharacters(characters);
	}

	public void updateFontsForLanguage(String characters){
//		FreeTypeFontGenerator cjkGen = new FreeTypeFontGenerator(Gdx.files.internal("utils/fonts/Noto Sans Hans Bold.ttc"));
//		FreeTypeFontParameter largeFontParam = new FreeTypeFontParameter();
//		largeFontParam.genMipMaps = true;
//		largeFontParam.magFilter = TextureFilter.MipMapLinearLinear;
//		largeFontParam.minFilter = TextureFilter.MipMapLinearLinear;
//		largeFontParam.color = Color.WHITE;
//		largeFontParam.size = 64;
//		
//		largeFontParam.characters = characters;
//		largeFont = cjkGen.generateFont(largeFontParam);
		
		Texture updateFontsForLanguageTexture = new Texture(
				Gdx.files.internal("utils/fonts/notosans2.png"), true);
		updateFontsForLanguageTexture.setFilter(TextureFilter.MipMapLinearLinear,
				TextureFilter.Linear);
		largeFont = new BitmapFont(
				Gdx.files.internal("utils/fonts/notosans2.fnt"),
				new TextureRegion(updateFontsForLanguageTexture));
//		cjkGen.dispose();
	}
	
	public void drawSmallFont(SpriteBatch batch, Color color, float scaleX, float scaleY, String text,
			float x, float y, float alignment, int fontAlignment){
		float prevScaleX = smallFont.getScaleX();
		float prevScaleY = smallFont.getScaleY();
		Color prevColor = smallFont.getColor();
		smallFont.getData().setScale(scaleX, scaleY);
		smallFont.setColor(color);
		
//		smallFont.drawMultiLine(batch, text, x, y, alignment, fontAlignment);
		smallFont.draw(batch, text, x, y, alignment, fontAlignment, false);
		smallFont.getData().setScale(prevScaleX, prevScaleY);
		smallFont.setColor(prevColor);
	}
	
	public void drawDataFont(SpriteBatch batch, Color color, float scaleX, float scaleY, String text,
			float x, float y, float alignment, int fontAlignment){
		float prevScaleX = dataFont.getScaleX();
		float prevScaleY = dataFont.getScaleY();
		Color prevColor = dataFont.getColor();
		dataFont.getData().setScale(scaleX, scaleY);
		dataFont.setColor(color);

//		dataFont.drawMultiLine(batch, text, x, y, alignment, fontAlignment);
		dataFont.draw(batch, text, x, y, alignment, fontAlignment, false);
		dataFont.getData().setScale(prevScaleX, prevScaleY);
		dataFont.setColor(prevColor);
	}

	public void drawDisplayFont(SpriteBatch batch, Color color, float scaleX, float scaleY, String text,
			float x, float y, float alignment, int fontAlignment){
		float prevScaleX = displayFont.getScaleX();
		float prevScaleY = displayFont.getScaleY();
		Color prevColor = displayFont.getColor();
		displayFont.getData().setScale(scaleX, scaleY);
		displayFont.setColor(color);
		
//		displayFont.drawMultiLine(batch, text, x, y, alignment, fontAlignment);
		displayFont.draw(batch, text, x, y, alignment, fontAlignment, false);
		displayFont.getData().setScale(prevScaleX, prevScaleY);
		displayFont.setColor(prevColor);
	}

	public void drawFont(BitmapFont font, SpriteBatch batch, Color color, float scaleX, float scaleY, String text,
			float x, float y, float alignment, int fontAlignment){
		float prevScaleX = font.getScaleX();
		float prevScaleY = font.getScaleY();
		Color prevColor = font.getColor();
		font.getData().setScale(scaleX, scaleY);
		font.setColor(color);
		
//		font.drawMultiLine(batch, text, x, y + 40, alignment, fontAlignment);
		font.draw(batch, text, x, y + 40, alignment, fontAlignment, false);
		font.getData().setScale(prevScaleX, prevScaleY);
		font.setColor(prevColor);
	}

	public void drawWrappedFont(BitmapFont font, SpriteBatch batch, Color color, float scaleX, float scaleY, String text,
			float x, float y, float alignment, int fontAlignment){
		float prevScaleX = font.getScaleX();
		float prevScaleY = font.getScaleY();
		Color prevColor = font.getColor();
		font.getData().setScale(scaleX, scaleY);
		font.setColor(color);
		
//		font.drawWrapped(batch, text, x, y + 40, alignment, fontAlignment);
		font.draw(batch, text, x, y + 40, alignment, fontAlignment, true);
		font.getData().setScale(prevScaleX, prevScaleY);
		font.setColor(prevColor);
	}
	
	public BitmapFontCache centeredWrappedFont(BitmapFont font, SpriteBatch batch, Color color, String text,
			float x, float y, float alignment){

		BitmapFontCache cache = font.getCache();
		Color prevColor = cache.getColor();
		cache.setColor(color);
//		cache.setWrappedText(text, 0, 0, alignment);
		ConnectCoins.glyphLayout.setText(cache.getFont(), text, color, alignment, Align.center, true);
		float vYPos = ConnectCoins.glyphLayout.height / 2;
//		cache.setWrappedText(text, x, y + vYPos, alignment, Align.center);
		cache.setText(text, x, y + vYPos, alignment, Align.center, true);
		cache.draw(batch);
		cache.setColor(prevColor);
		return cache;
	}
}