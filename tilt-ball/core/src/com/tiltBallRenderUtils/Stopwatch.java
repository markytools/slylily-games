package com.tiltBallRenderUtils;

import com.badlogic.gdx.utils.TimeUtils;

public class Stopwatch {
	private long previousTime;
	private long currentTime;
	
	public void start(){
		previousTime = TimeUtils.millis();
	}
	
	public long getCurrentTime(){
		currentTime = TimeUtils.millis();
		return currentTime - previousTime;
	}
}