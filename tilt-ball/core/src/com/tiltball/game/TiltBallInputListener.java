package com.tiltball.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class TiltBallInputListener implements InputProcessor {
	private boolean backKeyPressed = false;

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK){
			backKeyPressed = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isBackKeyPressed() {
		return backKeyPressed;
	}

	public void setBackKeyPressed(boolean backKeyPressed) {
		this.backKeyPressed = backKeyPressed;
	}

}
