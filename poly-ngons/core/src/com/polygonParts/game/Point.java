package com.polygonParts.game;


public class Point {
	protected float xPos;
	protected float yPos;
	protected int position;
	protected byte positionDesc;
	protected Edge pointEdge;
	
	public Point() {
		// TODO Auto-generated constructor stub
	}
	
	public Point(float xPos, float yPos, int position, Edge pointEdge) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.position = position;
		this.pointEdge = pointEdge;
		
		assignDescrip();
	}
	

	private void assignDescrip(){
		switch (position){
		case 1: this.positionDesc = PointPosition.BL; break;
		case 2: this.positionDesc = PointPosition.TL; break;
		case 3: this.positionDesc = PointPosition.TR; break;
		case 4: this.positionDesc = PointPosition.BR; break;
		default: break;
		}
	}
	
	public Edge getPointEdge(){
		return pointEdge;
	}
	
	public float getXPos(){
		return xPos;
	}
	
	public float getYPos(){
		return yPos;
	}
	
	public byte getPositionDesc(){
		return positionDesc;
	}

	public void setXPos(float xPos){
		this.xPos = xPos;
	}
	
	public void setYPos(float yPos){
		this.yPos = yPos;
	}
}
