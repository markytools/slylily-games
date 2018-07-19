package com.gameTools.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class CustomSelectBox extends SelectBox<String> {
	private final TextBounds bounds = new TextBounds();

	public CustomSelectBox(SelectBoxStyle style) {
		super(style);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		validate();

		Drawable background;
		if (getStyle().backgroundDisabled != null)
			background = getStyle().backgroundDisabled;
		else if (getScrollPane().hasParent() && getStyle().backgroundOpen != null)
			background = getStyle().backgroundOpen;
		else if (getStyle().backgroundOver != null)
			background = getStyle().backgroundOver;
		else
			background = getStyle().background;
		final BitmapFont font = getStyle().font;
		final Color fontColor = (getStyle().disabledFontColor != null) ? 
				getStyle().disabledFontColor : getStyle().fontColor;

		Color color = getColor();
		float x = getX();
		float y = getY();
		float width = getWidth();
		float height = getHeight();

		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		background.draw(batch, x, y, width, height);
		String selected = this.getSelected() != null ? this.getSelected() : getSelection().first();
		if (selected != null) {
			float availableWidth = width - background.getLeftWidth() - background.getRightWidth();
			String string = selected.toString();
			int numGlyphs = font.computeVisibleGlyphs(string, 0, string.length(), availableWidth);
			bounds.set(font.getBounds(string));
			height -= background.getBottomHeight() + background.getTopHeight();
			float textY = (int)(height / 2 + background.getBottomHeight() + bounds.height / 2);
			font.setColor(fontColor.r, fontColor.g, fontColor.b, fontColor.a * parentAlpha);
			font.draw(batch, string, x + background.getLeftWidth() + 10, y + textY, 0, numGlyphs);
		}
	}
}

