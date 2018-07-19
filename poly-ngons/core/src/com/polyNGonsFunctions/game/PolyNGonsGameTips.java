package com.polyNGonsFunctions.game;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gameTools.game.ShadedBitmapFont;
import com.polyngons.game.GameScreen;
import com.polyNgonConstants.game.PolyNGonsGameSelection;

public class PolyNGonsGameTips {
	private GameScreen gScreen;
	private HashMap<String, String> tips;
	private Array<String> renderOnce;
	private String currentTip;
	private ShadedBitmapFont font;
	private long tipDelay;

	public PolyNGonsGameTips(GameScreen gScreen){
		this.gScreen = gScreen;

		createTips();
	}

	private void createTips() {
		if (gScreen.game.gSelection != PolyNGonsGameSelection.TUTORIAL){
			currentTip = "";
			tipDelay = -1;

			renderOnce = new Array<String>();

			tips = new HashMap<String, String>();
			tips.put("solvingP", "Solving puzzles can unlock more textures for your polygons. See \"Poly N-gons\".");
			tips.put("pChallenge", "In puzzle challenge, quitting results in a loss of points. Be sure to solve the puzzle!");
			tips.put("arrows", "If there is a flicking arrow on the right side, there are more than four available polygons. You can" +
					" pan the HUD to scroll it.");
			tips.put("invalid", "If the marker is red, you cannot put the polygon there because it is invalid. Try to move it" +
					" in a valid, empty space.");
			tips.put("unflip", "Unable to flip. Rhombus, Parallelograms A and B, and Right Trapezoids A " +
					"and B are the only polygons that can be flipped.");
			tips.put("remove", "If no polygon is selected, the remove button removes the last polygon you had placed.");
			tips.put("pan", "If the puzzle is too large, you can always pan it to move the camera. Likewise, you can also pinch open or" +
					" close to zoom.");
		}

		font = gScreen.game.bLayout.getCustomFont();
	}

	public void renderTips(){
		if (gScreen.game.gSelection != PolyNGonsGameSelection.TUTORIAL &&
				gScreen.game.getUser().getBoolean("pHighlight") && !currentTip.isEmpty()){
			if (tipDelay != -1){
				gScreen.uiBatch.draw(gScreen.game.assetM.get(gScreen.game.assetID.get("tipLayout")), 130, 30);
				if (currentTip != null) font.drawWrappedFont(gScreen.uiBatch, currentTip, 150, 81, 465, BitmapFont.HAlignment.LEFT,
						.9f, 1f, Color.WHITE);
				setDelay();
			}
		}
	}

	public void setAndRenderOnce(String tip){
		if (gScreen.game.gSelection != PolyNGonsGameSelection.TUTORIAL &&
				gScreen.game.getUser().getBoolean("pHighlight") && !renderOnce.contains(tip, false) && !tip.isEmpty()){
			currentTip = tips.get(tip);
			renderOnce.add(tip);
			tipDelay = TimeUtils.millis();
		}
	}

	//	Just call this to render tip
	public void setAndRenderTip(String tip){
		if (gScreen.game.gSelection != PolyNGonsGameSelection.TUTORIAL &&
				gScreen.game.getUser().getBoolean("pHighlight") && !tip.isEmpty()){
			currentTip = tips.get(tip);
			tipDelay = TimeUtils.millis();
		}
	}

	private void setDelay(){
		if (TimeUtils.millis() - tipDelay >= 7000){
			tipDelay = -1;
			currentTip = "";
		}
	}

	public long getTipDelay() {
		return tipDelay;
	}

	public void setTipDelay(long tipDelay) {
		this.tipDelay = tipDelay;
	}

	public String getCurrentTip() {
		return currentTip;
	}

	public void setCurrentTip(String currentTip) {
		this.currentTip = currentTip;
	}
}
