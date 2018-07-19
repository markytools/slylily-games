package com.connectcoins.connection;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HalfCoinAnimation extends Animation<TextureRegion> {
	public int coinNum;
	private TextureRegion[] lowerKeyframe;
	private boolean halfCoin = false;

	public HalfCoinAnimation(float frameDuration,
			TextureRegion[] keyFrames1, TextureRegion[] keyFrames2) {
		super(frameDuration, keyFrames1);
		
		if (keyFrames2 != null){
			halfCoin = true;
			this.lowerKeyframe = new TextureRegion[keyFrames2.length];
			for (int i = 0, n = keyFrames2.length; i < n; i++) {
				this.lowerKeyframe[i] = keyFrames2[i];
			}
		}
	}

	public TextureRegion getKeyFrame2(float stateTime){
		int frameNumber = getKeyFrameIndex(stateTime);
		return lowerKeyframe[frameNumber];
	}

	public boolean isHalfCoin() {
		return halfCoin;
	}
}