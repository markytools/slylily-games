package com.springDynamicsConstants.game;

public class PrecalculatedUnits {
	public float jumpPower;
	public float vel0X;
	public float vel0Y;
	public float maxHeight;
	public float maxWidth;
	public float flatTime;
	
	public PrecalculatedUnits(float jumpPower, float vel0x, float vel0y,
			float maxHeight, float maxWidth, float flatTime) {
		super();
		this.jumpPower = jumpPower;
		vel0X = vel0x;
		vel0Y = vel0y;
		this.maxHeight = maxHeight;
		this.maxWidth = maxWidth;
		this.flatTime = flatTime;
	}
}
