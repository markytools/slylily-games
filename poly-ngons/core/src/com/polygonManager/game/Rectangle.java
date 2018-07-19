package com.polygonManager.game;

import com.badlogic.gdx.utils.Array;
import com.polygons.game.PolySquare;
import com.polygons.game.PolyTriangle;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;
import com.polygons.game.PolygonSquareNum;

public class Rectangle extends Polygon {

	public Rectangle(){

	}

	public Rectangle(float xPos, float yPos, boolean reversed, int position){
		super (xPos, yPos, reversed, position, PolygonName.rec);
		createTrianglesAndSquares();
	}

	@Override
	protected void createTrianglesAndSquares(){
		Array<PolyTriangle> triangles = new Array<PolyTriangle>();
		
		Array<PolySquare> squares = new Array<PolySquare>();

		for (int squareCount = 0; squareCount < PolygonSquareNum.rec; squareCount++){
			if (position == 1){
				if (squareCount == 0){
					PolySquare square = new PolySquare(xPos, yPos, this);
					squares.add(square);
				}
				else {
					PolySquare square = new PolySquare(xPos + 1, yPos, this);
					squares.add(square);
				}
			}
			else {
				if (squareCount == 0){
					PolySquare square = new PolySquare(xPos, yPos, this);
					squares.add(square);
				}
				else {
					PolySquare square = new PolySquare(xPos, yPos + 1, this);
					squares.add(square);
				}
			}
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
