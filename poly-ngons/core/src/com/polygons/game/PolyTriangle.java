package com.polygons.game;

import com.badlogic.gdx.utils.Array;
import com.polygonParts.game.Edge;
import com.polygonParts.game.EdgePosition;

public class PolyTriangle {
	public float xPos;
	public float yPos;
	public int position;
	public Array<Edge> polyEdges;
	public Polygon parentPoly;
	
	public PolyTriangle(){
		
	}

	public PolyTriangle(float xPos, float yPos, int position, Polygon parentPoly) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.position = position;
		this.parentPoly = parentPoly;
		
		createEdges();
	}
	
	private void createEdges(){
		polyEdges = new Array<Edge>();
		
		switch (position){
		case 1:{
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.V1));
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.H2));
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.D1));
		}; break;
		case 2:{
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.V1));
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.H1));
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.D2));
		}; break;
		case 3:{
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.V2));
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.H1));
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.D3));
		}; break;
		case 4:{
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.V2));
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.H2));
			polyEdges.add(new Edge(xPos, yPos, EdgePosition.D4));
		}; break;
		default: break;
		}
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
