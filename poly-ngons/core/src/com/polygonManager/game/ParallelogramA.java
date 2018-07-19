package com.polygonManager.game;

import com.badlogic.gdx.utils.Array;
import com.polygons.game.PolySquare;
import com.polygons.game.PolyTriangle;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;
import com.polygons.game.PolygonSquareNum;
import com.polygons.game.PolygonTriangleNum;

public class ParallelogramA extends Polygon {

	public ParallelogramA(){

	}

	public ParallelogramA(float xPos, float yPos, boolean reversed, int position){
		super (xPos, yPos, reversed, position, PolygonName.paraA);
		createTrianglesAndSquares();
	}

	@Override
	protected void createTrianglesAndSquares(){
		Array<PolyTriangle> triangles = new Array<PolyTriangle>();
		
		for (int triangleCount = 0; triangleCount < PolygonTriangleNum.paraA; triangleCount++){
			switch (position){
			case 1: {
				if (!reversed){
					if (triangleCount == 0){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos, 4, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 1){
						PolyTriangle triangle = new PolyTriangle(xPos + 2, yPos, 2, this);
						triangles.add(triangle);
					}
				}
				else {
					if (triangleCount == 0){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos, 3, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 1){
						PolyTriangle triangle = new PolyTriangle(xPos + 2, yPos, 1, this);
						triangles.add(triangle);
					}
				}
			}; break;
			case 2: {
				if (!reversed){
					if (triangleCount == 0){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos, 3, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 1){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos + 2, 1, this);
						triangles.add(triangle);
					}
				}
				else {
					if (triangleCount == 0){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos, 2, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 1){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos + 2, 4, this);
						triangles.add(triangle);
					}
				}
			}; break;
			default: break;
			}
		}
		
		Array<PolySquare> squares = new Array<PolySquare>();
		
		for (int squareCount = 0; squareCount < PolygonSquareNum.paraA; squareCount++){
			switch (position){
			case 1: {
				PolySquare square = new PolySquare(xPos + 1, yPos, this);
				squares.add(square);
			}; break;
			case 2: {
				PolySquare square = new PolySquare(xPos, yPos + 1, this);
				squares.add(square);
			}; break;
			default: break;
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
