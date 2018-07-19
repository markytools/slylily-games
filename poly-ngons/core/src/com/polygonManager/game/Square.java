package com.polygonManager.game;

import com.badlogic.gdx.utils.Array;
import com.polygons.game.PolySquare;
import com.polygons.game.PolyTriangle;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;
import com.polygons.game.PolygonSquareNum;

public class Square extends Polygon {

	public Square(){

	}

	public Square(float xPos, float yPos, boolean reversed, int position){
		super (xPos, yPos, reversed, position, PolygonName.sq);
		createTrianglesAndSquares();
	}

	public Square(float xPos, float yPos,int position, boolean reversed){
		this.xPos = xPos;
		this.yPos = yPos;
		this.position = position;
		this.reversed = reversed;

		createTrianglesAndSquares();
	}

	@Override
	protected void createTrianglesAndSquares(){
		Array<PolyTriangle> triangles = new Array<PolyTriangle>();
		
		Array<PolySquare> squares = new Array<PolySquare>();

		for (int squareCount = 0; squareCount < PolygonSquareNum.sq; squareCount++){
			
			PolySquare square = new PolySquare(xPos, yPos, this);
			squares.add(square);
		}

		this.triangles = triangles;
		this.squares = squares;
	}
	
	public Array<PolyTriangle> triangles(){
		return triangles;
	}
	
	public Array<PolySquare> squares(){
		return squares;
	}
}
