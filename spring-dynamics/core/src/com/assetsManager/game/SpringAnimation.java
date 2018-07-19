package com.assetsManager.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gameWorld.game.WorldListener;
import com.springdynamics.game.GameScreen;
import com.springDynamicsConstants.game.Dimensions;

public class SpringAnimation {
	private GameScreen gScreen;
	private Array<TextureRegion> springBodyAnim;
	private Array<TextureRegion> springAnim;
	private Array<TextureRegion> springRightHand;

	private int currentSpringReg;
	private int currentSpringBodyReg;
	private Rectangle springRec;
	
	private final int NOT_MOVING = 0;
	private final int UPWARD_MOVING = 1;
	private final int DOWNWARD_MOVING = 2;
	private int springDirection;
	private int lastSpringDirection;
	private boolean springDirChanged;
	private long lastTime = TimeUtils.millis();
	private float currentTimeOfDir = 0;
	private float maxTimeOfDir;

	public SpringAnimation(GameScreen gScreen){
		this.gScreen = gScreen;

		createSpringAssets();
		createSpringSensor();
	}

	private void createSpringAssets(){
		springBodyAnim = new Array<TextureRegion>();
		springAnim = new Array<TextureRegion>();
		springRightHand = new Array<TextureRegion>();

		for (int i = 1; i <= 4; i++){
			springBodyAnim.add(new TextureRegion(gScreen.game.assetM.get(gScreen.game.assetID.get("springBody" + i))));
		}

		for (int i = 1; i <= 5; i++){
			springAnim.add(new TextureRegion(gScreen.game.assetM.get(gScreen.game.assetID.get("spring" + i))));
		}

		for (int i = 1; i <= 4; i++){
			springRightHand.add(new TextureRegion(gScreen.game.assetM.get(gScreen.game.assetID.get("rightHand" + i))));
		}

		currentSpringReg = 4;
		currentSpringBodyReg = 1;
		springDirChanged = false;
	}

	private void createSpringSensor() {
		springRec = new Rectangle();

		springRec.setSize(springAnim.get(4).getRegionWidth() / 2, springAnim.get(4).getRegionHeight() / 2);
		springDirection = NOT_MOVING;
	}

	public void renderLeSpring(SpriteBatch batch){
		updateSpringAnimation();
		
		float xPos = (gScreen.getwManager().getSpring().getPosition().x * Dimensions.BOX_TO_WORLD) - 23.5f;
		float yPos = (gScreen.getwManager().getSpring().getPosition().y * Dimensions.BOX_TO_WORLD) - 40;
		float yAlign = 0;

		switch (currentSpringBodyReg + 1){
		case 1: yAlign = 7; break;
		case 2: yAlign = 8; break;
		case 3: yAlign = 8; break;
		case 4: yAlign = 9; break;
		default: break;
		}

		float springAlign = 12f * ((currentSpringReg) / 4);
		
		batch.draw(springBodyAnim.get(currentSpringBodyReg), xPos, yPos + (springAnim.get(currentSpringReg).getRegionHeight() / 2) -
				yAlign - springAlign, springBodyAnim.get(currentSpringBodyReg).getRegionWidth() / 2, springBodyAnim.get(currentSpringBodyReg).getRegionHeight() / 2);
		batch.draw(springAnim.get(currentSpringReg), xPos, yPos - springAlign, springAnim.get(currentSpringReg).getRegionWidth() / 2,
				springAnim.get(currentSpringReg).getRegionHeight() / 2);
		batch.draw(springRightHand.get(currentSpringBodyReg), xPos, yPos + (springAnim.get(currentSpringReg).getRegionHeight() / 2) -
				yAlign - 2 - springAlign, springRightHand.get(currentSpringBodyReg).getRegionWidth() / 2,
				springRightHand.get(currentSpringBodyReg).getRegionHeight() / 2);
	}
	
	public void updateSpringAnimation(){
		setSpringSensor();
		setSpringAnimation();
		setSpringBodyAnimation();
	}

	private void setSpringSensor(){
		float xPos = (gScreen.getwManager().getSpring().getPosition().x * Dimensions.BOX_TO_WORLD) - 23.5f;
		float yPos = (gScreen.getwManager().getSpring().getPosition().y * Dimensions.BOX_TO_WORLD) - 40;

		springRec.setPosition(xPos, yPos - (springRec.height - springAnim.get(0).getRegionHeight() / 2));
	}

	private void setSpringAnimation(){
		for (Rectangle obsRec : gScreen.getwManager().getBodyRecs().values()){
			if (gScreen.getwManager().getwListener().isFailedBounce() && springRec.overlaps(obsRec)){
				float diff = obsRec.y + obsRec.height - springRec.y;
				float springDiff = 23;
				if (diff > 0){
//					System.out.println(obsRec.y + obsRec.height + " ---- " + springRec.y + " --- " + diff);
					if (diff > 0 && diff <= springDiff * (1f / 4)) currentSpringReg = 2;
					else if (diff > springDiff * (1f / 4) && diff <= springDiff * (2f / 4)) currentSpringReg = 1;
					else if (diff > springDiff * (2f / 4) && diff <= springDiff * (3f / 4)) currentSpringReg = 0;
					else currentSpringReg = 0;
					break;
				}
			}
			else currentSpringReg = 4;
		}
	}
	
	private void setSpringBodyAnimation(){
		Body leSpring = gScreen.getwManager().getSpring();
		
		if (leSpring.getLinearVelocity().x <= 0) {
			springDirection = NOT_MOVING;
			springDirChanged();
		}
		else if (leSpring.getLinearVelocity().y > 0) {
			springDirection = UPWARD_MOVING;
			springDirChanged();
		}
		else if (leSpring.getLinearVelocity().y <= 0) {
			springDirection = DOWNWARD_MOVING;
			springDirChanged();
		}
		
		if (springDirChanged && lastSpringDirection != 0){
			springDirChanged = false;
			currentTimeOfDir = 0;
			lastTime = TimeUtils.millis();
		}
		
		WorldListener wListener = gScreen.getwManager().getwListener();
		switch (springDirection){
		case 0: {
//			Game Over
		}; break;
		case 1: {
			maxTimeOfDir = wListener.getjPPrecalculations().get(wListener.getLastUsedJumpPower()).flatTime / 2;
			currentTimeOfDir = (TimeUtils.millis() - lastTime) / 1000f;
			
			if (maxTimeOfDir >= .6f){
				if (currentTimeOfDir >= 0 && currentTimeOfDir < maxTimeOfDir * (1f / 3)) currentSpringBodyReg = 1;
				else if (currentTimeOfDir >= maxTimeOfDir * (1f / 3) && currentTimeOfDir < maxTimeOfDir * (2f / 3)) currentSpringBodyReg = 1;
				else if (currentTimeOfDir >= maxTimeOfDir * (2f / 3) && currentTimeOfDir < maxTimeOfDir * (3f / 3)) currentSpringBodyReg = 2;
			}
			else {
				if (currentTimeOfDir >= 0 && currentTimeOfDir < maxTimeOfDir * (1f / 3)) currentSpringBodyReg = 1;
				else if (currentTimeOfDir >= maxTimeOfDir * (1f / 3) && currentTimeOfDir < maxTimeOfDir * (2f / 3)) currentSpringBodyReg = 1;
				else if (currentTimeOfDir >= maxTimeOfDir * (2f / 3) && currentTimeOfDir < maxTimeOfDir * (3f / 3)) currentSpringBodyReg = 1;
			}
		}; break;
		case 2: {
			currentSpringBodyReg = 0;
		}; break;
		default: break;
		}
	}
	
	private void springDirChanged(){
		if (lastSpringDirection != springDirection){
			lastSpringDirection = springDirection;
			springDirChanged = true;
		}
	}
	
	public void dispose(){
		for (int i = 1; i <= 4; i++) gScreen.game.assetM.unload(gScreen.game.assetID.get("springBody" + i).fileName);
		for (int i = 1; i <= 5; i++) gScreen.game.assetM.unload(gScreen.game.assetID.get("spring" + i).fileName);
		for (int i = 1; i <= 4; i++) gScreen.game.assetM.unload(gScreen.game.assetID.get("rightHand" + i).fileName);
	}
}
