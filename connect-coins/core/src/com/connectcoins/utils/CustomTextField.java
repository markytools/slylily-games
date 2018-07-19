package com.connectcoins.utils;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class CustomTextField extends TextField {
	
	public CustomTextField(String text, TextFieldStyle style) {
		super(text, style);
	}
	
	public boolean isTouched(Vector3 touchpos){
		float x = getX();
		float y = getY();
		float x2 = getX() + getWidth();
		float y2 = getY() + getHeight();
		if (touchpos.x >= x && touchpos.x < x2 && touchpos.y >= y && touchpos.y < y2){
			return true;
		}
		return false;
	}
}
