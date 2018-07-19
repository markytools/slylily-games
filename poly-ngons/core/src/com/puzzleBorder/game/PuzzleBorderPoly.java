package com.puzzleBorder.game;

import com.polygonParts.game.Point;
import com.polygonParts.game.PointPosition;

public class PuzzleBorderPoly {
	private float xPos, yPos;
	private byte pBorderName;
	private int position;
	private boolean revearsed;
	private Point pb1;
	private Point pb2;

	public PuzzleBorderPoly(){

	}

	public PuzzleBorderPoly(float xPos, float yPos, int position, boolean revearsed, byte name){
		this.xPos = xPos;
		this.yPos = yPos;
		this.position = position;
		this.revearsed = revearsed;
		this.pBorderName = name;

		createBPoint();
	}

	private void createBPoint(){
		switch(pBorderName){
		case PuzzleBorderName.oneTria:{
			switch (position){
			case 1: {
				pb1 = new Point(xPos, yPos, PointPosition.TL, null);
				pb2 = new Point(xPos, yPos, PointPosition.BR, null);
			}; break;
			case 2: {
				pb1 = new Point(xPos, yPos, PointPosition.BL, null);
				pb2 = new Point(xPos, yPos, PointPosition.TR, null);
			}; break;
			case 3: {
				pb1 = new Point(xPos, yPos, PointPosition.TL, null);
				pb2 = new Point(xPos, yPos, PointPosition.BR, null);
			}; break;
			case 4: {
				pb1 = new Point(xPos, yPos, PointPosition.BL, null);
				pb2 = new Point(xPos, yPos, PointPosition.TR, null);
			}; break;
			default: break;
			}
		}; break;
		case PuzzleBorderName.twoTria:{
			if (!revearsed){
				switch (position){
				case 1: {
					pb1 = new Point(xPos, yPos, PointPosition.BL, null);
					pb2 = new Point(xPos, yPos, PointPosition.BR, null);
				}; break;
				case 2: {
					pb1 = new Point(xPos, yPos, PointPosition.BL, null);
					pb2 = new Point(xPos, yPos, PointPosition.TL, null);
				}; break;
				case 3: {
					pb1 = new Point(xPos, yPos, PointPosition.TL, null);
					pb2 = new Point(xPos, yPos, PointPosition.TR, null);
				}; break;
				case 4: {
					pb1 = new Point(xPos, yPos, PointPosition.TR, null);
					pb2 = new Point(xPos, yPos, PointPosition.BR, null);
				}; break;
				default: break;
				}
			}
			else {
				switch (position){
				case 1: {
					pb1 = new Point(xPos, yPos, PointPosition.BL, null);
					pb2 = new Point(xPos, yPos, PointPosition.BR, null);
				}; break;
				case 2: {
					pb1 = new Point(xPos, yPos, PointPosition.BR, null);
					pb2 = new Point(xPos, yPos, PointPosition.TR, null);
				}; break;
				case 3: {
					pb1 = new Point(xPos, yPos, PointPosition.TL, null);
					pb2 = new Point(xPos, yPos, PointPosition.TR, null);
				}; break;
				case 4: {
					pb1 = new Point(xPos, yPos, PointPosition.TL, null);
					pb2 = new Point(xPos, yPos, PointPosition.BL, null);
				}; break;
				default: break;
				}
			}
		}; break;
		case PuzzleBorderName.oneSq:{
			switch (position){
			case 1: {
				pb1 = new Point(xPos, yPos, PointPosition.BL, null);
				pb2 = new Point(xPos, yPos, PointPosition.TL, null);
			}; break;
			case 2: {
				pb1 = new Point(xPos, yPos, PointPosition.TL, null);
				pb2 = new Point(xPos, yPos, PointPosition.TR, null);
			}; break;
			case 3: {
				pb1 = new Point(xPos, yPos, PointPosition.TR, null);
				pb2 = new Point(xPos, yPos, PointPosition.BR, null);
			}; break;
			case 4: {
				pb1 = new Point(xPos, yPos, PointPosition.BL, null);
				pb2 = new Point(xPos, yPos, PointPosition.BR, null);
			}; break;
			default: break;
			}
		}; break;
		case PuzzleBorderName.perpendSq:{
			switch (position){
			case 1: {
				pb1 = new Point(xPos, yPos, PointPosition.TL, null);
				pb2 = new Point(xPos, yPos, PointPosition.BR, null);
			}; break;
			case 2: {
				pb1 = new Point(xPos, yPos, PointPosition.BL, null);
				pb2 = new Point(xPos, yPos, PointPosition.TR, null);
			}; break;
			case 3: {
				pb1 = new Point(xPos, yPos, PointPosition.TL, null);
				pb2 = new Point(xPos, yPos, PointPosition.BR, null);
			}; break;
			case 4: {
				pb1 = new Point(xPos, yPos, PointPosition.BL, null);
				pb2 = new Point(xPos, yPos, PointPosition.TR, null);
			}; break;
			default: break;
			}
		}; break;
		case PuzzleBorderName.threeSq:{
			switch (position){
			case 1: {
				pb1 = new Point(xPos, yPos, PointPosition.TR, null);
				pb2 = new Point(xPos, yPos, PointPosition.BR, null);
			}; break;
			case 2: {
				pb1 = new Point(xPos, yPos, PointPosition.BL, null);
				pb2 = new Point(xPos, yPos, PointPosition.BR, null);
			}; break;
			case 3: {
				pb1 = new Point(xPos, yPos, PointPosition.TL, null);
				pb2 = new Point(xPos, yPos, PointPosition.BL, null);
			}; break;
			case 4: {
				pb1 = new Point(xPos, yPos, PointPosition.TL, null);
				pb2 = new Point(xPos, yPos, PointPosition.TR, null);
			}; break;
			default: break;
			}
		}; break;
		default: break;
		}
	}

	public float xPos(){ return xPos; }
	public float yPos(){ return yPos; }
	public void setXPos(float xPos){
		float moveXPos = xPos - this.xPos;
		this.xPos += moveXPos;
		if (pb1 != null){
			pb1.setXPos(pb1.getXPos() + moveXPos);
		}
		if (pb2 != null){
			pb2.setXPos(pb2.getXPos() + moveXPos);
		}
	}
	public void setYPos(float yPos){ 
		float moveYPos = yPos - this.yPos;
		this.yPos += moveYPos;
		if (pb1 != null){
			pb1.setYPos(pb1.getYPos() + moveYPos);
		}
		if (pb2 != null){
			pb2.setYPos(pb2.getYPos() + moveYPos);
		}
	}
	public int getPos(){ return position; }
	public boolean isRevearsed(){ return revearsed; }
	public byte bName(){ return pBorderName; }

	public Point getPb1() {
		return pb1;
	}

	public Point getPb2() {
		return pb2;
	}
	
}
