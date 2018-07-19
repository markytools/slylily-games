package com.polyNGonsFunctions.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.polyngons.game.PolyNGons;

public class PolyBlockAnimation {
	private PolyNGons game;
	private Texture[] animationText;
	private Sprite sp;
	private int currentText;
	private long delayNextText;
	private boolean forward = true;
	private float rotation;
	
	public PolyBlockAnimation(PolyNGons game){
		this.game = game;
		
		createAnimations();
	}
	
	private void createAnimations(){
		animationText = new Texture[24];
		
		for (int i = 0; i < 24; i++) animationText[i] = game.assetM.get(game.assetID.get("animation" + i));
		sp = new Sprite(animationText[0]);
		currentText = 0;
		rotation = 0;
	}
	
	public void resetAnimation(){
		sp.setTexture(animationText[0]);
		sp.setRotation(0);
		sp.setScale(1, 1);
		delayNextText = TimeUtils.millis();
	}
	
	public void drawAnimation(SpriteBatch batch, int x, int y, float scaleX, float scaleY){
		sp.setTexture(animationText[currentText]);
		sp.setRotation(rotation);
		sp.setScale(scaleX, scaleY);
		sp.setPosition(x, y);
		sp.draw(batch);
		
		if (TimeUtils.millis() - delayNextText >= 50){
			delayNextText = TimeUtils.millis();
			if (forward){
				if (currentText + 1 >= 24) {
					setRotation();
					forward = false;
				}
				else currentText += 1;
			}
			else {
				if (currentText - 1 < 0) {
					setRotation();
					forward = true;
				}
				else currentText -= 1;
			}
		}
	}
	
	private void setRotation(){
		if (rotation - 90 <= -360) rotation = 0;
		else rotation -= 90;
	}
}
