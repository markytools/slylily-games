package com.connectcoins.tutorial;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Align;
import com.connectcoins.game.ConnectCoins;

public class TutorialScrollPane extends ScrollPane {
	private String text;
	private BitmapFont font;
	private BitmapFontCache cache;
	
	public TutorialScrollPane(Actor widget, ScrollPaneStyle style, BitmapFont font) {
		super(widget, style);
		this.font = font;
		this.cache = font.getCache();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (text != null && !text.isEmpty()){
			Color prevColor = font.getColor();
			font.setColor(Color.valueOf("000000"));
			cache.draw(batch);
			font.setColor(prevColor);
		}
	}
	
	private void autoAdjustSize(){
		@SuppressWarnings("deprecation")
		Actor widget = getWidget();
//		cache.setWrappedText(text, 0, 0, 780);
		cache.setText(text, 0, 0, 780, Align.center, true);
		ConnectCoins.glyphLayout.setText(cache.getFont(), text, cache.getColor(), 780, Align.center, true);
		float cacheHeight = ConnectCoins.glyphLayout.height;
		if (cacheHeight > 320){
			widget.setHeight(340 + (cacheHeight - 320));
		}
		else widget.setHeight(340);
		
		float y = widget.getY() + 170 - (cacheHeight / 2);
//		cache.setWrappedText(text, widget.getX() + 200, y, 780, Align.center);
		cache.setText(text, widget.getX() + 200, y, 780, Align.center, true);
	}

	public void setText(String text) {
		this.text = text;
		autoAdjustSize();
	}

}
