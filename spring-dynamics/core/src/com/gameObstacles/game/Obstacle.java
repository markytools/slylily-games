package com.gameObstacles.game;

public class Obstacle {
	private float xPos;
	private float yPos;
	private byte obsName;
	
	public Obstacle(float xPos, float yPos, byte obsName) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.obsName = obsName;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public byte getObsName() {
		return obsName;
	}

	public void setObsName(byte obsName) {
		this.obsName = obsName;
	}
}
