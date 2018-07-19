package com.polygonParts.game;


public class Edge {
	protected Point point1;
	protected Point point2;
	protected byte edgePos;
	protected float edgeXPos;
	protected float edgeYPos;
	
	public Edge(){
		
	}
	
	public Edge(float xPos, float yPos, byte edgePos){
		this.edgeXPos = xPos;
		this.edgeYPos = yPos;
		this.edgePos = edgePos;
		
		switch (edgePos){
		case EdgePosition.V1:{
			point1 = new Point(xPos, yPos, 1, this);
			point2 = new Point(xPos, yPos, 2, this);
		}; break;
		case EdgePosition.V2:{
			point1 = new Point(xPos, yPos, 3, this);
			point2 = new Point(xPos, yPos, 4, this);
		}; break;
		case EdgePosition.H1:{
			point1 = new Point(xPos, yPos, 2, this);
			point2 = new Point(xPos, yPos, 3, this);
		}; break;
		case EdgePosition.H2:{
			point1 = new Point(xPos, yPos, 1, this);
			point2 = new Point(xPos, yPos, 4, this);
		}; break;
		case EdgePosition.D1:{
			point1 = new Point(xPos, yPos, 2, this);
			point2 = new Point(xPos, yPos, 4, this);
		}; break;
		case EdgePosition.D2:{
			point1 = new Point(xPos, yPos, 1, this);
			point2 = new Point(xPos, yPos, 3, this);
		}; break;
		case EdgePosition.D3:{
			point1 = new Point(xPos, yPos, 2, this);
			point2 = new Point(xPos, yPos, 4, this);
		}; break;
		case EdgePosition.D4:{
			point1 = new Point(xPos, yPos, 1, this);
			point2 = new Point(xPos, yPos, 3, this);
		}; break;
		default: break;
		}
	}
	
	public Point getPoint1(){
		return point1;
	}
	
	public Point getPoint2(){
		return point2;
	}
	
	public byte getEdgePos(){
		return edgePos;
	}
	
	public float getEdgeXPos(){
		return edgeXPos;
	}
	
	public float getEdgeYPos(){
		return edgeYPos;
	}

	public void setXPos(float xPos){
		this.edgeXPos = xPos;
		point1.setXPos(xPos);
		point2.setXPos(xPos);
	}
	
	public void setYPos(float yPos){
		this.edgeYPos = yPos;
		point1.setYPos(yPos);
		point2.setYPos(yPos);
	}
}
