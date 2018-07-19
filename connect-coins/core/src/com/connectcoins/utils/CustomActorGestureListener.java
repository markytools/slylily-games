package com.connectcoins.utils;

import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class CustomActorGestureListener extends ActorGestureListener {
	protected boolean enableTouchUp = true;
	
	public CustomActorGestureListener() {
		super();
	}

	public void setEnableTouchUp(boolean enableTouchUp) {
		this.enableTouchUp = enableTouchUp;
	}

	public void reset(){
		enableTouchUp = true;
	}
}
