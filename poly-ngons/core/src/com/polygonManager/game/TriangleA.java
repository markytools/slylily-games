package com.polygonManager.game;

import com.badlogic.gdx.utils.Array;
import com.polygons.game.PolySquare;
import com.polygons.game.PolyTriangle;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;
import com.polygons.game.PolygonTriangleNum;

public class TriangleA extends Polygon {
	public TriangleA(){

	}

	public TriangleA(float xPos, float yPos, boolean reversed, int position){
		super (xPos, yPos, reversed, position, PolygonName.triA);
		createTrianglesAndSquares();
	}

	@Override
	protected void createTrianglesAndSquares(){
		Array<PolyTriangle> triangles = new Array<PolyTriangle>();

		for (int triangleCount = 0; triangleCount < PolygonTriangleNum.triA; triangleCount++){
			PolyTriangle triangle = new PolyTriangle(xPos, yPos, position, this);
			triangles.add(triangle);
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