package com.connectcoins.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class FrontUI {
	public String uiID;

	public FrontUI(){
		
	}
	
	public abstract void renderFrontUI(SpriteBatch batch);
	
	public String getUiID() {
		return uiID;
	}

	public void setUiID(String uiID) {
		this.uiID = uiID;
	}
}
