package com.polygons.game;

import com.badlogic.gdx.utils.Array;

public abstract class Polygon {
	protected float xPos;
	protected float yPos;
	protected int position;
	protected boolean reversed;
	protected byte name;
	protected Array<PolyTriangle> triangles;
	protected Array<PolySquare> squares;
	
	public Polygon(){
		
	}
	
	public Polygon(float xPos, float yPos, boolean reversed, int position, byte name){
		this.xPos = xPos;
		this.yPos = yPos;
		this.position = position;
		this.reversed = reversed;
		this.name = name;
	}

	protected abstract void createTrianglesAndSquares();
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
		createTrianglesAndSquares();
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
		createTrianglesAndSquares();
	}

	public void setName(byte name) {
		this.name = name;
	}

	public byte getName(){
		return name;
	}
	
	public Array<PolyTriangle> triangles(){
		return triangles;
	}
	
	public Array<PolySquare> squares(){
		return squares;
	}
	
	public void setPolyXPos(float xPos){
		float xSpaces = this.xPos - xPos;
		
		for (PolyTriangle tria : triangles){
			tria.setXPos(tria.xPos - xSpaces);
		}
		
		for (PolySquare sq : squares){
			sq.setXPos(sq.xPos - xSpaces);
		}
		
		this.xPos = xPos;
	}

	public void setPolyYPos(float yPos){
		float ySpaces = this.yPos - yPos;
		
		for (PolyTriangle tria : triangles){
			tria.setYPos(tria.yPos - ySpaces);
		}
		
		for (PolySquare sq : squares){
			sq.setYPos(sq.yPos - ySpaces);
		}
		
		this.yPos = yPos;
	}

	public float getPolyXPos(){ return xPos; }
	public float getPolyYPos(){ return yPos; }
}