package com.connectcoins.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

public class GlowButton extends Button implements Disposable {
	private TextureRegionDrawable[] animationRegs;
	private Animation<TextureRegion> bAnim;
	private float bAnimStatetime = 0;
	private boolean active = false;
	private long lastGlow = -1;
	
	public GlowButton(ButtonStyle style, TextureRegionDrawable[] animationRegs) {
		super(style);
		this.animationRegs = animationRegs;
		
		Array<TextureRegion> bAnimRegs = new Array<TextureRegion>();
		for (int i = 0; i < animationRegs.length; i++) bAnimRegs.add(animationRegs[i].getRegion());
		bAnim = new Animation<TextureRegion>(.05f, bAnimRegs);
	}
	
	public GlowButton(ButtonStyle style, TextureRegionDrawable[] animationRegs, float speed) {
		super(style);
		this.animationRegs = animationRegs;
		
		Array<TextureRegion> bAnimRegs = new Array<TextureRegion>();
		for (int i = 0; i < animationRegs.length; i++) bAnimRegs.add(animationRegs[i].getRegion());
		bAnim = new Animation<TextureRegion>(speed, bAnimRegs);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		validate();

		boolean isPressed = isPressed();
		boolean isDisabled = isDisabled();

		Drawable background = null;
		if (isDisabled && getStyle().disabled != null)
			background = getStyle().disabled;
		else if (isPressed && getStyle().down != null)
			background = getStyle().down;
		else if (isChecked() && getStyle().checked != null)
			background = (getStyle().checkedOver != null && isOver()) ? getStyle().checkedOver : getStyle().checked;
		else if (isOver() && getStyle().over != null)
			background = getStyle().over;
		else if (getStyle().up != null){
			if (active){
				if (lastGlow == -1){
					bAnimStatetime += Gdx.graphics.getDeltaTime();
					background = animationRegs[bAnim.getKeyFrameIndex(bAnimStatetime)];
					if (bAnim.isAnimationFinished(bAnimStatetime)) lastGlow = TimeUtils.millis();
				}
				else {
					background = getStyle().up;
					if (TimeUtils.millis() - lastGlow >= 2000){
						lastGlow = -1;
						bAnimStatetime = 0;
					}
				}
			}
			else {
				background = getStyle().up;
				bAnimStatetime = 0;
			}
		}
		setBackground(background);

		float offsetX = 0, offsetY = 0;
		if (isPressed && !isDisabled) {
			offsetX = getStyle().pressedOffsetX;
			offsetY = getStyle().pressedOffsetY;
		} else {
			offsetX = getStyle().unpressedOffsetX;
			offsetY = getStyle().unpressedOffsetY;
		}

		Array<Actor> children = getChildren();
		for (int i = 0; i < children.size; i++)
			children.get(i).moveBy(offsetX, offsetY);
		super.draw(batch, parentAlpha);
		for (int i = 0; i < children.size; i++)
			children.get(i).moveBy(-offsetX, -offsetY);

		Stage stage = getStage();
		if (stage != null && stage.getActionsRequestRendering() && isPressed != getClickListener().isPressed())
			Gdx.graphics.requestRendering();
	}

	public void resetGlow(){
		bAnimStatetime = 0;
		active = false;
		lastGlow = -1;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void dispose() {
		animationRegs = null;
		bAnim = null;
		bAnimStatetime = 0;
		active = false;
		lastGlow = 0;
	}
}
