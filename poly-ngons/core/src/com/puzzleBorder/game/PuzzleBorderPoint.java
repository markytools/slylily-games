package com.puzzleBorder.game;

public class PuzzleBorderPoint {
	private float xPos;
	private float yPos;
	private byte bPPos;
	private PuzzleBorderPoly bPoly;
	
	public PuzzleBorderPoint(){
		
	}
	public PuzzleBorderPoint(float xPos, float yPos, byte bPPos, PuzzleBorderPoly bPoly){
		this.xPos = xPos;
		this.yPos = yPos;
		this.bPPos = bPPos;
		this.bPoly = bPoly;
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

	public byte getbPPos() {
		return bPPos;
	}

	public void setbPPos(byte bPPos) {
		this.bPPos = bPPos;
	}
	
	public PuzzleBorderPoly getBPoly(){
		return bPoly;
	}
}
