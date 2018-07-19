package com.polygons.game;

import com.badlogic.gdx.utils.Array;
import com.polygonParts.game.Edge;
import com.polygonParts.game.EdgePosition;

public class PolySquare {
	public float xPos;
	public float yPos;
	public Array<Edge> polyEdges;
	public Polygon parentPoly;
	
	public PolySquare(){
		
	}

	public PolySquare(float xPos, float yPos, Polygon parentPoly) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.parentPoly = parentPoly;
		
		createEdges();
	}

	private void createEdges() {
		polyEdges = new Array<Edge>();
		polyEdges.add(new Edge(xPos, yPos, EdgePosition.V1));
		polyEdges.add(new Edge(xPos, yPos, EdgePosition.V2));
		polyEdges.add(new Edge(xPos, yPos, EdgePosition.H1));
		polyEdges.add(new Edge(xPos, yPos, EdgePosition.H2));
	}
	
	public Array<Edge> getPolyEdges(){
		return polyEdges;
	}
	
	public void setXPos(float xPos){
		this.xPos = xPos;
		for (Edge edge : polyEdges){
			edge.setXPos(xPos);
		}
	}
	
	public void setYPos(float yPos){
		this.yPos = yPos;
		for (Edge edge : polyEdges){
			edge.setYPos(yPos);
		}
	}
}
