package com.polyNGonsFunctions.game;

public class PolygonDescriptor {
	private byte polyName;
	private float xPos;
	private float yPos;
	private boolean revearsed;
	private int position;
	
	public PolygonDescriptor(){
		
	}

	public PolygonDescriptor(byte polyName, float xPos, float yPos,
			boolean revearsed, int position) {
		super();
		this.polyName = polyName;
		this.xPos = xPos;
		this.yPos = yPos;
		this.revearsed = revearsed;
		this.position = position;
	}

	public byte getPolyName() {
		return polyName;
	}

	public void setPolyName(byte polyName) {
		this.polyName = polyName;
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

	public boolean isRevearsed() {
		return revearsed;
	}

	public void setRevearsed(boolean revearsed) {
		this.revearsed = revearsed;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
}
