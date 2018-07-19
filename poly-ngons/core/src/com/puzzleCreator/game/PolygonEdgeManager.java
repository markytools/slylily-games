package com.puzzleCreator.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.polyngons.game.GameScreen;
import com.polygonParts.game.Edge;
import com.polygonParts.game.EdgePosition;
import com.polygonParts.game.Point;

public class PolygonEdgeManager {
	private Array<Edge> polygonEdges;
	private Array<Edge> outerEdges, innerEdges;
	private Array<Point> outerEdgePoints;
	private PolygonPointManager pointManager;
	private long maxGenerationTime;
	public PolygonEdgeManager(GameScreen gScreen){
		innerEdges = new Array<Edge>();
		outerEdgePoints = new Array<Point>();
		pointManager = new PolygonPointManager();
	}

	public void createOuterEdges(){

		Edge[] edgeArray = new Edge[polygonEdges.size];

		for (int i = polygonEdges.size; i > 0; i--){
			edgeArray[i - 1] = polygonEdges.get(i - 1);
		}

		Array<Edge> outerEdges = new Array<Edge>();
		
		for (int edgeInd1 = edgeArray.length; edgeInd1 > 0; edgeInd1--){
			for (int edgeInd2 = edgeArray.length; edgeInd2 > 0; edgeInd2--){
				if (edgeArray[edgeInd1 - 1] != edgeArray[edgeInd2 - 1]){
					if (edgeArray[edgeInd1 - 1] != null && edgeArray[edgeInd2 - 1] != null){
						if (checkIfEdgesMatch(edgeArray[edgeInd1 - 1], edgeArray[edgeInd2 - 1])){
							polygonEdges.removeValue(edgeArray[edgeInd1 - 1], true);
							polygonEdges.removeValue(edgeArray[edgeInd2 - 1], true);
							edgeArray[edgeInd1 - 1] = null;
							edgeArray[edgeInd2 - 1] = null;
							break;
						}
					}
				}
			}
		}
		
		outerEdges.addAll(polygonEdges);

		maxGenerationTime = TimeUtils.millis();

		//		sort edges
		Array<Edge> edgeTemp = new Array<Edge>();

		Edge selectedEdge = outerEdges.first();
		edgeTemp.add(selectedEdge);
		outerEdges.removeValue(selectedEdge, true);
		Point selectedPoint = selectedEdge.getPoint2();

		do {
			for (Edge edge : outerEdges){
				if (PolygonPointManager.pointMatches(edge.getPoint1(), selectedPoint) &&
						!edge.getPoint1().getPointEdge().equals(selectedPoint.getPointEdge())){
					selectedPoint = edge.getPoint2();
					edgeTemp.add(edge);
					outerEdges.removeValue(edge, true);
					break;
				}
				else if (PolygonPointManager.pointMatches(edge.getPoint2(), selectedPoint) &&
						!edge.getPoint2().getPointEdge().equals(selectedPoint.getPointEdge())){
					selectedPoint = edge.getPoint1();
					edgeTemp.add(edge);
					outerEdges.removeValue(edge, true);
					break;
				}
			}
//			System.out.println(TimeUtils.millis() - maxGenerationTime);
			if (TimeUtils.millis() - maxGenerationTime >= 500){
				pointManager.getMultiPoints().add(new Point());
				break;
			}
		}
		while (outerEdges.size > 0);
		outerEdges.addAll(edgeTemp);

		this.outerEdges = null;
		this.outerEdges = outerEdges;
		//		
	}

	private boolean checkIfEdgesMatch(Edge edge1, Edge edge2){
		switch (edge1.getEdgePos()){
		case EdgePosition.V1:{
			if ((edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					edge2.getEdgePos() == EdgePosition.V2) ||
					(edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					edge2.getEdgePos() == EdgePosition.V1)){
				return true;
			}
			else return false;
		}
		case EdgePosition.V2:{
			if ((edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					edge2.getEdgePos() == EdgePosition.V1) ||
					(edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					edge2.getEdgePos() == EdgePosition.V2)){
				return true;
			}
			else return false;
		}
		case EdgePosition.H1:{
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos() &&
					edge2.getEdgePos() == EdgePosition.H2) ||
					(edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					edge2.getEdgePos() == EdgePosition.H1)){
				return true;
			}
			else return false;
		}
		case EdgePosition.H2:{
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos() &&
					edge2.getEdgePos() == EdgePosition.H1) ||
					(edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					edge2.getEdgePos() == EdgePosition.H2)){
				return true;
			}
			else return false;
		}
		case EdgePosition.D1 :{
			if (edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					(edge2.getEdgePos() == EdgePosition.D3 || edge2.getEdgePos() == EdgePosition.D1)){
				return true;
			}
			else return false;
		}
		case EdgePosition.D2 :{
			if (edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					(edge2.getEdgePos() == EdgePosition.D2 || edge2.getEdgePos() == EdgePosition.D4)){
				return true;
			}
			else return false;
		}
		case EdgePosition.D3 :{
			if (edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					(edge2.getEdgePos() == EdgePosition.D3 || edge2.getEdgePos() == EdgePosition.D1)){
				return true;
			}
			else return false;
		}
		case EdgePosition.D4 :{
			if (edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					(edge2.getEdgePos() == EdgePosition.D2 || edge2.getEdgePos() == EdgePosition.D4)){
				return true;
			}
			else return false;
		}
		default: return false;
		}
	}

	public void sortEdgePoints(boolean checkInnerEdges){
		if (!(TimeUtils.millis() - maxGenerationTime >= 500)){
			outerEdgePoints.clear();
			for (Edge edge : outerEdges){
				outerEdgePoints.add(edge.getPoint1());
				outerEdgePoints.add(edge.getPoint2());
			}

			if (checkInnerEdges){
				pointManager.sortPoints(outerEdgePoints);
			}
		}
	}

	public void createInnerEdges(Array<Point> multiPoints) {
		Array<Edge> edgeOrder1 = getEdgeOrder(multiPoints);
		Array<Edge> edgeOrder2 = getEdgeOrder(multiPoints);

		if (edgeOrder1.size > edgeOrder2.size){
			outerEdges.addAll(edgeOrder1);
			innerEdges.addAll(edgeOrder2);
		}
		else {
			outerEdges.addAll(edgeOrder2);
			innerEdges.addAll(edgeOrder1);
		}
	}

	private Array<Edge> getEdgeOrder(Array<Point> multiPoints){
		Array<Edge> edgeTemp = new Array<Edge>();

		Point selectedPoint = multiPoints.first();
		multiPoints.removeValue(selectedPoint, true);
		Point nextPoint;
		Edge selectedEdge = selectedPoint.getPointEdge();
		edgeTemp.add(selectedEdge);
		if (selectedEdge.getPoint1() == selectedPoint){
			nextPoint = selectedEdge.getPoint2();
		}
		else nextPoint = selectedEdge.getPoint1();

		for (Point point : outerEdgePoints){
			if (PolygonPointManager.pointMatches(point, nextPoint) &&
					point.getPointEdge() != nextPoint.getPointEdge()){
				edgeTemp.add(point.getPointEdge());
				if (point.getPointEdge().getPoint1() == point){
					nextPoint = point.getPointEdge().getPoint2();
				} else nextPoint = point.getPointEdge().getPoint1();
				if (multiPoints.contains(nextPoint, true)){
					multiPoints.removeValue(nextPoint, true);
					break;
				}
			}
		}

		return edgeTemp;
	}

	public Array<Edge> getOuterEdges(){
		return outerEdges;
	}

	public PolygonPointManager getPointManager(){
		return pointManager;
	}

	public Array<Edge> getInnerEdges() {
		return innerEdges;
	}

	public Array<Edge> getPolygonEdges() {
		return polygonEdges;
	}

	public void setPolygonEdges(Array<Edge> polygonEdges) {
		this.polygonEdges = polygonEdges;
	}

}
