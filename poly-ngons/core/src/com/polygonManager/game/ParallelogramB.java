package com.polygonManager.game;

import com.badlogic.gdx.utils.Array;
import com.polygons.game.PolySquare;
import com.polygons.game.PolyTriangle;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;
import com.polygons.game.PolygonTriangleNum;

public class ParallelogramB extends Polygon {

	public ParallelogramB(){

	}

	public ParallelogramB(float xPos, float yPos, boolean reversed, int position){
		super (xPos, yPos, reversed, position, PolygonName.paraB);
		createTrianglesAndSquares();
	}

	@Override
	protected void createTrianglesAndSquares(){
		Array<PolyTriangle> triangles = new Array<PolyTriangle>();
		
		for (int triangleCount = 0; triangleCount < PolygonTriangleNum.paraB; triangleCount++){
			switch (position){
			case 1: {
				if (!reversed){
					if (triangleCount == 0){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos, 2, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 1){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos + 1, 4, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 2){
						PolyTriangle triangle = new PolyTriangle(xPos + 1, yPos + 1, 2, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 3){
						PolyTriangle triangle = new PolyTriangle(xPos + 1, yPos + 2, 4, this);
						triangles.add(triangle);
					}
				}
				else {
					if (triangleCount == 0){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos + 2, 1, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 1){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos + 1, 3, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 2){
						PolyTriangle triangle = new PolyTriangle(xPos + 1, yPos + 1, 1, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 3){
						PolyTriangle triangle = new PolyTriangle(xPos + 1, yPos, 3, this);
						triangles.add(triangle);
					}
				}
			}; break;
			case 2: {
				if (!reversed){
					if (triangleCount == 0){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos + 1, 3, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 1){
						PolyTriangle triangle = new PolyTriangle(xPos + 1, yPos + 1, 1, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 2){
						PolyTriangle triangle = new PolyTriangle(xPos + 1, yPos, 3, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 3){
						PolyTriangle triangle = new PolyTriangle(xPos + 2, yPos, 1, this);
						triangles.add(triangle);
					}
				}
				else {
					if (triangleCount == 0){
						PolyTriangle triangle = new PolyTriangle(xPos, yPos, 4, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 1){
						PolyTriangle triangle = new PolyTriangle(xPos + 1, yPos, 2, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 2){
						PolyTriangle triangle = new PolyTriangle(xPos + 1, yPos + 1, 4, this);
						triangles.add(triangle);
					}
					else if (triangleCount == 3){
						PolyTriangle triangle = new PolyTriangle(xPos + 2, yPos + 1, 2, this);
						triangles.add(triangle);
					}
				}
			}; break;
			default: break;
			}
		}
		
		Array<PolySquare> squares = new Array<PolySquare>();
		
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
