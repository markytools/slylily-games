package com.gameUI.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.springdynamics.game.GameScreen;
import com.springDynamicsConstants.game.GameState;

public class JumpController {
	private GameScreen gScreen;
	private Texture jPDisplay, jumpPowerNum;
	private Array<TextureRegion> numbers;
	private Stage uiStage;

	private Array<TextureRegionDrawable> upButtons, downButtons;
	private ActorGestureListener jpCntrlListener;
	private Button jpCntrlButton;
	private UIManager uiManager;

	public JumpController(GameScreen gScreen, UIManager uiManager){
		this.gScreen = gScreen;
		this.uiManager = uiManager;

		jPDisplay = gScreen.game.assetM.get(gScreen.game.assetID.get("jPSetter"));

		createJPButtonAssets();
		createJPNumDisplay();
	}

	private void createJPNumDisplay() {
		jumpPowerNum = gScreen.game.assetM.get(gScreen.game.assetID.get("jumpNumDisplay"));
		numbers = new Array<TextureRegion>();

		for (int i = 1; i <= 6; i++){
			numbers.add(new TextureRegion(gScreen.game.assetM.get(gScreen.game.assetID.get("jpNum" + i))));
		}
	}

	private void createJPButtonAssets() {
		upButtons = new Array<TextureRegionDrawable>();
		downButtons = new Array<TextureRegionDrawable>();

		TextureRegion jpReg = new TextureRegion(gScreen.game.assetM.get(gScreen.game.assetID.get("jPButtons")));
		TextureRegion[][] jpButtons = jpReg.split(60, 60);
		for (int i = 0; i < 2; i++){
			for (int i2 = 0; i2 < jpButtons[i].length; i2++){
				if (i == 0) upButtons.add(new TextureRegionDrawable(jpButtons[i][i2]));
				else downButtons.add(new TextureRegionDrawable(jpButtons[i][i2]));
			}
		}

		ButtonStyle bStyle1 = new ButtonStyle();
		bStyle1.up = upButtons.first();
		bStyle1.down = downButtons.first();

		jpCntrlButton = new Button(bStyle1);
		jpCntrlButton.addListener(jpCntrlListener = new ActorGestureListener(){
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (ifTouchedJumpController()){
					float change = MathUtils.clamp(jpCntrlButton.getY() + deltaY, 10, 310);
					jpCntrlButton.setY(change);
				}
				else jpCntrlButton.clearActions();
				super.pan(event, x, y, deltaX, deltaY);
			}
		});
		jpCntrlButton.setPosition(5, 10);

		uiStage = new Stage();
		uiStage.addActor(jpCntrlButton);
	}

	public void renderJumpController(){
		drawJumpNumber(jpCntrlButton.getY());
		changeButtonColor(jpCntrlButton.getY());
		gScreen.game.uiBatch.draw(jPDisplay, 0, 0);
	}

	private void drawJumpNumber(float buttonY) {
		float currentToggledJP = (buttonY + 20) / 60;
		setJumpPower((int)currentToggledJP);

		gScreen.game.uiBatch.draw(jumpPowerNum, 5, 385);
		gScreen.game.uiBatch.draw(numbers.get((int)currentToggledJP), 5, 385);
	}

	private void setJumpPower(float currentToggledJP) {
		gScreen.getwManager().getwListener().setCurrentJumpPower(currentToggledJP + 1);
	}

	private void changeButtonColor(float buttonY){
		switch ((int)((buttonY + 20) / 60)){
		case 0: {
			jpCntrlButton.getStyle().up = upButtons.get(0);
			jpCntrlButton.getStyle().down = downButtons.get(0);
		}; break;
		case 1: {
			jpCntrlButton.getStyle().up = upButtons.get(1);
			jpCntrlButton.getStyle().down = downButtons.get(1);
		}; break;
		case 2: {
			jpCntrlButton.getStyle().up = upButtons.get(2);
			jpCntrlButton.getStyle().down = downButtons.get(2);
		}; break;
		case 3: {
			jpCntrlButton.getStyle().up = upButtons.get(3);
			jpCntrlButton.getStyle().down = downButtons.get(3);
		}; break;
		case 4: {
			jpCntrlButton.getStyle().up = upButtons.get(4);
			jpCntrlButton.getStyle().down = downButtons.get(4);
		}; break;
		case 5: {
			jpCntrlButton.getStyle().up = upButtons.get(5);
			jpCntrlButton.getStyle().down = downButtons.get(5);
		}; break;
		default: break;
		}
	}

	public void setJumpControllerListener(){
		if (uiManager.getIgMenu().getCurrentToggle() != 0 || gScreen.gState == GameState.GAME_OVER) 
			jpCntrlButton.setTouchable(Touchable.disabled);
		else {
			autoMoveController();
			autoAdjustButton(jpCntrlButton.getY());
			jpCntrlButton.setTouchable(Touchable.enabled);
		}

		Gdx.input.setInputProcessor(uiStage);
		uiStage.act();
		uiStage.draw();
		uiStage.getViewport().setCamera(gScreen.game.uiCam);

	}

	private void autoMoveController() {
		if (ifTouchedJumpController()){
			if (Gdx.input.isTouched()){
				float touchedY = MathUtils.clamp(
						MathUtils.floor((gScreen.game.uiTouchPos.y - 10) / 60) * 60 + 10, 10, 310);
				jpCntrlButton.setY(touchedY);
			}
		}
	}

	private boolean ifTouchedJumpController(){
		if (gScreen.game.uiTouchPos.x >= 0 && gScreen.game.uiTouchPos.x < 70 &&
				gScreen.game.uiTouchPos.y >= 0 && gScreen.game.uiTouchPos.y < 380) return true;
		return false;
	}

	private void autoAdjustButton(float buttonY){
		if (!jpCntrlListener.getGestureDetector().isPanning()){
			float lockedYPos = MathUtils.floor((buttonY - 10 + 30) / 60) * 60 + 10;
			jpCntrlButton.setY(lockedYPos);
		}
	}

	public void dispose(){
		gScreen.game.assetM.unload(gScreen.game.assetID.get("jPSetter").fileName);
		gScreen.game.assetM.unload(gScreen.game.assetID.get("jumpNumDisplay").fileName);
		for (int i = 1; i <= 6; i++) gScreen.game.assetM.unload(gScreen.game.assetID.get("jpNum" + i).fileName);
		gScreen.game.assetM.unload(gScreen.game.assetID.get("jPButtons").fileName);
	}
}