package com.puzzleBorder.game;

import com.badlogic.gdx.utils.Array;
import com.gameTools.game.PermutationOfEdges;
import com.polygonParts.game.Edge;
import com.polygonParts.game.EdgePosition;
import com.polygonParts.game.Point;
import com.polygons.game.PolySquare;
import com.polygons.game.PolyTriangle;
import com.polygons.game.Polygon;
import com.puzzleCreator.game.PolygonPointManager;

public class PuzzleBorderCreator {
	private Array<PuzzleBorderPoly> turnBorders;
	private Array<PuzzleBorderPoly> normalBorders;
	private Array<PuzzleBorderPoly> innerBorders;
	private Array<PuzzleBorderPoly> emptyBorders;
	private PermutationOfEdges pEdges;
	private Array<Edge> outerEdges;
	private Array<Edge> innerEdges;
	private Array<Polygon> puzzlePoly;
	private float maxX;
	private float maxY;

	public PuzzleBorderCreator(Array<Edge> outerEdges, Array<Polygon> puzzlePoly){
		normalBorders = new Array<PuzzleBorderPoly>();
		innerBorders = new Array<PuzzleBorderPoly>();
		emptyBorders = new Array<PuzzleBorderPoly>();
		turnBorders = new Array<PuzzleBorderPoly>();
		pEdges = new PermutationOfEdges();
		this.outerEdges = outerEdges;
		this.puzzlePoly = puzzlePoly;
	}

	public PuzzleBorderCreator(Array<Edge> outerEdges, Array<Edge> innerEdges, Array<Polygon> puzzlePoly){
		normalBorders = new Array<PuzzleBorderPoly>();
		emptyBorders = new Array<PuzzleBorderPoly>();
		this.outerEdges = outerEdges;
		this.innerEdges = innerEdges;
		this.puzzlePoly = puzzlePoly;
	}

	public void createPuzzleBorders(){
		//				sort edges
		//		Array<Edge> edgeTemp = new Array<Edge>();
		//		Array<Edge> edgeTemp2 = new Array<Edge>();
		//		edgeTemp2.addAll(outerEdges);
		//
		//		Edge selectedEdge = edgeTemp2.first();
		//		edgeTemp.add(selectedEdge);
		//		edgeTemp2.removeValue(selectedEdge, true);
		//		Point selectedPoint = selectedEdge.getPoint2();
		//
		//		while (edgeTemp2.size > 0){
		//			for (Edge edge : edgeTemp2){
		//				if (PolygonPointManager.pointMatches(edge.getPoint1(), selectedPoint)){
		//					selectedPoint = edge.getPoint2();
		//					edgeTemp.add(edge);
		//					edgeTemp2.removeValue(edge, true);
		//					break;
		//				}
		//				else if (PolygonPointManager.pointMatches(edge.getPoint2(), selectedPoint)){
		//					selectedPoint = edge.getPoint1();
		//					edgeTemp.add(edge);
		//					edgeTemp2.removeValue(edge, true);
		//					break;
		//				}
		//			}
		//		}
		//				

		for (int i = 0; i < outerEdges.size; i++){
			normalBorders.add(null);
		}

		Array<Edge> b45Dgrs = check45DegreeEdges(outerEdges);
		if (!checkIfNoEdges(b45Dgrs)){
			Array<Edge> bSqrdEdges = checkSqrdEdges(b45Dgrs);
			if (!checkIfNoEdges(bSqrdEdges)){
				Array<Edge> bRtAngldEdges = checkRtAngldEdges(bSqrdEdges);
				if (!checkIfNoEdges(bRtAngldEdges)){
					addBorders(bRtAngldEdges);
				}
			}
		}

		addAdditionalBorders();
	}

	public boolean checkIfCorrectBorders() {

		for (int i = 0; i < normalBorders.size; i++){
			for (int i2 = 0; i2 < normalBorders.size; i2++){
				if (i != i2){
					PuzzleBorderPoly b1 = normalBorders.get(i);
					PuzzleBorderPoly b2 = normalBorders.get(i2);
					if (b1.xPos() == b2.xPos() &&
							b1.yPos() == b2.yPos()) return false;
				}
			}
		}
		return true;
	}

	private void addAdditionalBorders(){
		addBorderTurns();
		shrinkBorder();
	}

	private void shrinkBorder() {
		for (int i = 0; i < normalBorders.size; i++){
			int i2;
			int i3;

			if (i == normalBorders.size - 1){
				i2 = 0;
				i3 = 1;
			}
			else if (i == normalBorders.size - 2){
				i2 = normalBorders.size - 1;
				i3 = 0;
			}
			else {
				i2 = i + 1;
				i3 = i + 2;
			}

			if (normalBorders.get(i) == normalBorders.get(i2)){
				normalBorders.set(i2, null);
			}
			if (normalBorders.get(i) == normalBorders.get(i3)){
				normalBorders.set(i3, null);
			}
		}

		Array<PuzzleBorderPoly> border2 = new Array<PuzzleBorderPoly>();
		for (PuzzleBorderPoly bPoly : normalBorders){
			if (bPoly != null) border2.add(bPoly);
		}

		normalBorders.clear();
		normalBorders.addAll(border2);

		for (int i = 0; i < normalBorders.size; i++){
			for (int i2 = 0; i2 < normalBorders.size; i2++){
				if (normalBorders.get(i) == normalBorders.get(i2) &&
						i != i2){
					normalBorders.removeIndex(i2);
				}
			}
		}
	}

	public void createInnerBorders(){
		//		sort edges
		Array<Edge> edgeTemp = new Array<Edge>();
		Array<Edge> edgeTemp2 = new Array<Edge>();
		edgeTemp2.addAll(innerEdges);

		Edge selectedEdge = edgeTemp2.first();
		edgeTemp.add(selectedEdge);
		edgeTemp2.removeValue(selectedEdge, true);
		Point selectedPoint = selectedEdge.getPoint2();

		while (edgeTemp2.size > 0){
			for (Edge edge : edgeTemp2){
				if (PolygonPointManager.pointMatches(edge.getPoint1(), selectedPoint)){
					selectedPoint = edge.getPoint2();
					edgeTemp.add(edge);
					edgeTemp2.removeValue(edge, true);
					break;
				}
				else if (PolygonPointManager.pointMatches(edge.getPoint2(), selectedPoint)){
					selectedPoint = edge.getPoint1();
					edgeTemp.add(edge);
					edgeTemp2.removeValue(edge, true);
					break;
				}
			}
		}
		//		

		for (int i = 0; i < edgeTemp.size; i++){
			innerBorders.add(null);
		}

		Array<Edge> triaEdges = checkTriangledEdges(edgeTemp);
		if (!checkIfNoEdges(triaEdges)){
			Array<Edge> cSqrdEdges = checkCompleteSqrdEdges(triaEdges);
			if (!checkIfNoEdges(cSqrdEdges)){
				Array<Edge> b45Dgrs = check45DegreeEdges(cSqrdEdges);
				if (!checkIfNoEdges(b45Dgrs)){
					Array<Edge> bSqrdEdges = checkSqrdEdges(b45Dgrs);
					if (!checkIfNoEdges(bSqrdEdges)){
						Array<Edge> bRtAngldEdges = checkRtAngldEdges(bSqrdEdges);
						if (!checkIfNoEdges(bRtAngldEdges)){
							addBorders(bRtAngldEdges);
						}
					}
				}
			}
		}
	}

	private Array<Edge> checkTriangledEdges(Array<Edge> edges){
		Array<Edge> edgeTemp = new Array<Edge>();
		Array<PuzzleBorderPoly> bTemp = new Array<PuzzleBorderPoly>();
		edgeTemp.addAll(edges);

		for (int i = 0; i < edgeTemp.size; i++){
			int i2;
			int i3;

			if (i == edgeTemp.size - 1){
				i2 = 0;
				i3 = 1;
			}
			else if (i == edgeTemp.size - 2){
				i2 = edgeTemp.size - 1;
				i3 = 0;
			}
			else {
				i2 = i + 1;
				i3 = i + 2;
			}

			bTemp.add(checkIfTriangledEdges(edgeTemp.get(i), edgeTemp.get(i2), edgeTemp.get(i3)));
			bTemp.add(checkIfTriangledEdges(edgeTemp.get(i), edgeTemp.get(i3), edgeTemp.get(i2)));
			bTemp.add(checkIfTriangledEdges(edgeTemp.get(i2), edgeTemp.get(i), edgeTemp.get(i3)));
			bTemp.add(checkIfTriangledEdges(edgeTemp.get(i3), edgeTemp.get(i), edgeTemp.get(i2)));
			bTemp.add(checkIfTriangledEdges(edgeTemp.get(i2), edgeTemp.get(i3), edgeTemp.get(i)));
			bTemp.add(checkIfTriangledEdges(edgeTemp.get(i3), edgeTemp.get(i2), edgeTemp.get(i)));

			for (PuzzleBorderPoly bord : bTemp){
				if (bord != null){
					innerBorders.set(i, bord);
					innerBorders.set(i2, bord);
					innerBorders.set(i3, bord);
					edgeTemp.set(i, null);
					edgeTemp.set(i2, null);
					edgeTemp.set(i3, null);
					break;
				}
			}
		}

		return edgeTemp;
	}


	private Array<Edge> checkCompleteSqrdEdges(Array<Edge> edges){
		Array<Edge> edgeTemp = new Array<Edge>();
		Array<Edge> edgeTemp2 = new Array<Edge>();
		edgeTemp.addAll(edges);

		for (int i = 0; i < edgeTemp.size; i++){
			edgeTemp2.clear();
			int i2;
			int i3;
			int i4;

			if (i == edgeTemp.size - 1){
				i2 = 0;
				i3 = 1;
				i4 = 2;
			}
			else if (i == edgeTemp.size - 2){
				i2 = edgeTemp.size - 1;
				i3 = 0;
				i4 = 1;
			}
			else if (i == edgeTemp.size - 3){
				i2 = edgeTemp.size - 2;
				i3 = edgeTemp.size - 1;
				i4 = 0;
			}
			else {
				i2 = i + 1;
				i3 = i + 2;
				i4 = i + 3;
			}

			edgeTemp2.add(edgeTemp.get(i));
			edgeTemp2.add(edgeTemp.get(i2));
			edgeTemp2.add(edgeTemp.get(i3));
			edgeTemp2.add(edgeTemp.get(i4));

			Array<Array<Edge>> permutedEdges = pEdges.permuteObjects(edgeTemp2);

			for (Array<Edge> arrangedEdges : permutedEdges){
				Edge edge1 = arrangedEdges.get(0);
				Edge edge2 = arrangedEdges.get(0);
				Edge edge3 = arrangedEdges.get(0);
				Edge edge4 = arrangedEdges.get(0);

				PuzzleBorderPoly cSqrdEdges = checkIfCompletelySqrdEdges(edge1, edge2, edge3, edge4);
				if (cSqrdEdges != null) {
					innerBorders.set(i, cSqrdEdges);
					innerBorders.set(i2, cSqrdEdges);
					innerBorders.set(i3, cSqrdEdges);
					innerBorders.set(i4, cSqrdEdges);
					edgeTemp.set(i, null);
					edgeTemp.set(i2, null);
					edgeTemp.set(i3, null);
					edgeTemp.set(i4, null);
					break;
				}
			}
		}

		return edgeTemp;
	}

	private PuzzleBorderPoly checkIfTriangledEdges(Edge edge1, Edge edge2, Edge edge3){
		switch (edge1.getEdgePos()){
		case EdgePosition.D1: {
			if (edge2.getEdgePos() == EdgePosition.V2 &&
					edge3.getEdgePos() == EdgePosition.H1 &&
					edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					edge1.getEdgeXPos() == edge3.getEdgeXPos() &&
					edge1.getEdgeYPos() - 1 == edge3.getEdgeYPos()){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 3, false, PuzzleBorderName.threeTria);
			}
		}; break;
		case EdgePosition.D2: {
			if (edge2.getEdgePos() == EdgePosition.V2 &&
					edge3.getEdgePos() == EdgePosition.H2 &&
					edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					edge1.getEdgeXPos() == edge3.getEdgeXPos() &&
					edge1.getEdgeYPos() + 1 == edge3.getEdgeYPos()){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 4, false, PuzzleBorderName.threeTria);
			}
		}; break;
		case EdgePosition.D3: {
			if (edge2.getEdgePos() == EdgePosition.V1 &&
					edge3.getEdgePos() == EdgePosition.H2 &&
					edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
					edge1.getEdgeXPos() == edge3.getEdgeXPos() &&
					edge1.getEdgeYPos() + 1 == edge3.getEdgeYPos()){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 1, false, PuzzleBorderName.threeTria);
			}
		}; break;
		case EdgePosition.D4: {
			if (edge2.getEdgePos() == EdgePosition.H1 &&
					edge3.getEdgePos() == EdgePosition.V1 &&
					edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
					edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos() &&
					edge1.getEdgeXPos() + 1 == edge3.getEdgeXPos() &&
					edge1.getEdgeYPos() == edge3.getEdgeYPos()){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 2, false, PuzzleBorderName.threeTria);
			}
		}; break;
		default: return null;
		}

		return null;
	}

	private PuzzleBorderPoly checkIfCompletelySqrdEdges(Edge edge1, Edge edge2, Edge edge3, Edge edge4){
		if (edge1.getEdgePos() == EdgePosition.V2 &&
				edge2.getEdgePos() == EdgePosition.H2 &&
				edge3.getEdgePos() == EdgePosition.V1 &&
				edge4.getEdgePos() == EdgePosition.H1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() + 2 == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge3.getEdgeYPos() &&
				edge1.getEdgeXPos() + 1 == edge4.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge4.getEdgeYPos()){
			return new PuzzleBorderPoly(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(), 1, false, PuzzleBorderName.fourSq);
		}

		return null;
	}

	private Array<Edge> check45DegreeEdges(Array<Edge> edges){
		Array<Edge> edgeTemp = new Array<Edge>();
		edgeTemp.addAll(edges);

		for (int i = 0; i < edgeTemp.size; i++){
			int ind2 = i + 1;
			if (ind2 >= edgeTemp.size){
				ind2 -= edgeTemp.size;
			}

			if (edgeTemp.get(i) != null &&
					edgeTemp.get(ind2) != null){
				PuzzleBorderPoly b45Dgrs = checkIf45Degrees(edgeTemp.get(i), edgeTemp.get(ind2));
				if (b45Dgrs != null) {
					normalBorders.set(i, b45Dgrs);
					normalBorders.set(ind2, b45Dgrs);
					edgeTemp.set(i, null);
					edgeTemp.set(ind2, null);
					continue;
				}
				PuzzleBorderPoly b45Dgrs2 = checkIf45Degrees(edgeTemp.get(ind2), edgeTemp.get(i));
				if (b45Dgrs2 != null) {
					normalBorders.set(i, b45Dgrs2);
					normalBorders.set(ind2, b45Dgrs2);
					edgeTemp.set(i, null);
					edgeTemp.set(ind2, null);
					continue;
				}
			}
		}

		return edgeTemp;
	}

	private PuzzleBorderPoly checkIf45Degrees(Edge edge1, Edge edge2){
		switch (edge1.getEdgePos()){
		case EdgePosition.D1:{
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.H2)){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 2, true, PuzzleBorderName.twoTria);
			}
			if ((edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.V1)){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 3, false, PuzzleBorderName.twoTria);
			}
		}; break;
		case EdgePosition.D2:{
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.H1)){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 4, false, PuzzleBorderName.twoTria);
			}
			if ((edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.V1)){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 1, true, PuzzleBorderName.twoTria);
			}
		}; break;
		case EdgePosition.D3:{
			if ((edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.V2)){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 1, false, PuzzleBorderName.twoTria);
			}
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.H1)){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 4, true, PuzzleBorderName.twoTria);
			}
		}; break;
		case EdgePosition.D4:{
			if ((edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.V2)){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 3, true, PuzzleBorderName.twoTria);
			}
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.H2)){
				return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos(), 2, false, PuzzleBorderName.twoTria);
			}
		}; break;
		default: break;
		}

		return null;
	}

	private Array<Edge> checkSqrdEdges(Array<Edge> edges){
		Array<Edge> edgeTemp = new Array<Edge>();
		edgeTemp.addAll(edges);

		for (int i = 0; i < edgeTemp.size; i++){
			if (i == 0){
				if (edgeTemp.get(0) != null &&
						edgeTemp.get(1) != null &&
						edgeTemp.peek() != null){
					Edge edge1 = edgeTemp.peek();
					Edge edge2 = edgeTemp.get(0);
					Edge edge3 = edgeTemp.get(1);

					PuzzleBorderPoly bSqrdEdges = checkIfSqrdEdges(edge1, edge2, edge3);
					PuzzleBorderPoly bSqrdEdges2 = checkIfSqrdEdges(edge1, edge3, edge2);
					PuzzleBorderPoly bSqrdEdges3 = checkIfSqrdEdges(edge2, edge1, edge3);
					PuzzleBorderPoly bSqrdEdges4 = checkIfSqrdEdges(edge3, edge1, edge2);
					PuzzleBorderPoly bSqrdEdges5 = checkIfSqrdEdges(edge2, edge3, edge1);
					PuzzleBorderPoly bSqrdEdges6 = checkIfSqrdEdges(edge3, edge2, edge1);
					if (bSqrdEdges != null) {
						normalBorders.set(edgeTemp.size - 1, bSqrdEdges);
						normalBorders.set(0, bSqrdEdges);
						normalBorders.set(1, bSqrdEdges);
						edgeTemp.set(edgeTemp.size - 1, null);
						edgeTemp.set(0, null);
						edgeTemp.set(1, null);
					}
					else if (bSqrdEdges2 != null) {
						normalBorders.set(edgeTemp.size - 1, bSqrdEdges2);
						normalBorders.set(0, bSqrdEdges2);
						normalBorders.set(1, bSqrdEdges2);
						edgeTemp.set(edgeTemp.size - 1, null);
						edgeTemp.set(0, null);
						edgeTemp.set(1, null);
					}
					else if (bSqrdEdges3 != null) {
						normalBorders.set(edgeTemp.size - 1, bSqrdEdges3);
						normalBorders.set(0, bSqrdEdges3);
						normalBorders.set(1, bSqrdEdges3);
						edgeTemp.set(edgeTemp.size - 1, null);
						edgeTemp.set(0, null);
						edgeTemp.set(1, null);
					}
					else if (bSqrdEdges4 != null) {
						normalBorders.set(edgeTemp.size - 1, bSqrdEdges4);
						normalBorders.set(0, bSqrdEdges4);
						normalBorders.set(1, bSqrdEdges4);
						edgeTemp.set(edgeTemp.size - 1, null);
						edgeTemp.set(0, null);
						edgeTemp.set(1, null);
					}
					else if (bSqrdEdges5 != null) {
						normalBorders.set(edgeTemp.size - 1, bSqrdEdges5);
						normalBorders.set(0, bSqrdEdges5);
						normalBorders.set(1, bSqrdEdges5);
						edgeTemp.set(edgeTemp.size - 1, null);
						edgeTemp.set(0, null);
						edgeTemp.set(1, null);
					}
					else if (bSqrdEdges6 != null) {
						normalBorders.set(edgeTemp.size - 1, bSqrdEdges6);
						normalBorders.set(0, bSqrdEdges6);
						normalBorders.set(1, bSqrdEdges6);
						edgeTemp.set(edgeTemp.size - 1, null);
						edgeTemp.set(0, null);
						edgeTemp.set(1, null);
					}
				}
			}
			else if (i == edgeTemp.size - 1){
				if (edgeTemp.get(i - 1) != null &&
						edgeTemp.get(i) != null &&
						edgeTemp.get(0) != null){
					Edge edge1 = edgeTemp.get(i - 1);
					Edge edge2 = edgeTemp.get(i);
					Edge edge3 = edgeTemp.get(0);

					PuzzleBorderPoly bSqrdEdges = checkIfSqrdEdges(edge1, edge2, edge3);
					PuzzleBorderPoly bSqrdEdges2 = checkIfSqrdEdges(edge1, edge3, edge2);
					PuzzleBorderPoly bSqrdEdges3 = checkIfSqrdEdges(edge2, edge1, edge3);
					PuzzleBorderPoly bSqrdEdges4 = checkIfSqrdEdges(edge3, edge1, edge2);
					PuzzleBorderPoly bSqrdEdges5 = checkIfSqrdEdges(edge2, edge3, edge1);
					PuzzleBorderPoly bSqrdEdges6 = checkIfSqrdEdges(edge3, edge2, edge1);
					if (bSqrdEdges != null) {
						normalBorders.set(i - 1, bSqrdEdges);
						normalBorders.set(i, bSqrdEdges);
						normalBorders.set(0, bSqrdEdges);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(0, null);
					}
					else if (bSqrdEdges2 != null) {
						normalBorders.set(i - 1, bSqrdEdges2);
						normalBorders.set(i, bSqrdEdges2);
						normalBorders.set(0, bSqrdEdges2);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(0, null);
					}
					else if (bSqrdEdges3 != null) {
						normalBorders.set(i - 1, bSqrdEdges3);
						normalBorders.set(i, bSqrdEdges3);
						normalBorders.set(0, bSqrdEdges3);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(0, null);
					}
					else if (bSqrdEdges4 != null) {
						normalBorders.set(i - 1, bSqrdEdges4);
						normalBorders.set(i, bSqrdEdges4);
						normalBorders.set(0, bSqrdEdges4);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(0, null);
					}
					else if (bSqrdEdges5 != null) {
						normalBorders.set(i - 1, bSqrdEdges5);
						normalBorders.set(i, bSqrdEdges5);
						normalBorders.set(0, bSqrdEdges5);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(0, null);
					}
					else if (bSqrdEdges6 != null) {
						normalBorders.set(i - 1, bSqrdEdges6);
						normalBorders.set(i, bSqrdEdges6);
						normalBorders.set(0, bSqrdEdges6);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(0, null);
					}
				}
			}
			else {
				if (edgeTemp.get(i - 1) != null &&
						edgeTemp.get(i) != null &&
						edgeTemp.get(i + 1) != null){
					Edge edge1 = edgeTemp.get(i - 1);
					Edge edge2 = edgeTemp.get(i);
					Edge edge3 = edgeTemp.get(i + 1);

					PuzzleBorderPoly bSqrdEdges = checkIfSqrdEdges(edge1, edge2, edge3);
					PuzzleBorderPoly bSqrdEdges2 = checkIfSqrdEdges(edge1, edge3, edge2);
					PuzzleBorderPoly bSqrdEdges3 = checkIfSqrdEdges(edge2, edge1, edge3);
					PuzzleBorderPoly bSqrdEdges4 = checkIfSqrdEdges(edge3, edge1, edge2);
					PuzzleBorderPoly bSqrdEdges5 = checkIfSqrdEdges(edge2, edge3, edge1);
					PuzzleBorderPoly bSqrdEdges6 = checkIfSqrdEdges(edge3, edge2, edge1);
					if (bSqrdEdges != null) {
						normalBorders.set(i - 1, bSqrdEdges);
						normalBorders.set(i, bSqrdEdges);
						normalBorders.set(i + 1, bSqrdEdges);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(i + 1, null);
					}
					else if (bSqrdEdges2 != null) {
						normalBorders.set(i - 1, bSqrdEdges2);
						normalBorders.set(i, bSqrdEdges2);
						normalBorders.set(i + 1, bSqrdEdges2);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(i + 1, null);
					}
					else if (bSqrdEdges3 != null) {
						normalBorders.set(i - 1, bSqrdEdges3);
						normalBorders.set(i, bSqrdEdges3);
						normalBorders.set(i + 1, bSqrdEdges3);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(i + 1, null);
					}
					else if (bSqrdEdges4 != null) {
						normalBorders.set(i - 1, bSqrdEdges4);
						normalBorders.set(i, bSqrdEdges4);
						normalBorders.set(i + 1, bSqrdEdges4);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(i + 1, null);
					}
					else if (bSqrdEdges5 != null) {
						normalBorders.set(i - 1, bSqrdEdges5);
						normalBorders.set(i, bSqrdEdges5);
						normalBorders.set(i + 1, bSqrdEdges5);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(i + 1, null);
					}
					else if (bSqrdEdges6 != null) {
						normalBorders.set(i - 1, bSqrdEdges6);
						normalBorders.set(i, bSqrdEdges6);
						normalBorders.set(i + 1, bSqrdEdges6);
						edgeTemp.set(i - 1, null);
						edgeTemp.set(i, null);
						edgeTemp.set(i + 1, null);
					}
				}
			}
		}

		return edgeTemp;
	}

	private PuzzleBorderPoly checkIfSqrdEdges(Edge edge1, Edge edge2, Edge edge3){

		if (edge1.getEdgePos() == EdgePosition.V2 &&
				edge2.getEdgePos() == EdgePosition.H1 &&
				edge3.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() + 2 == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge3.getEdgeYPos()){
			return new PuzzleBorderPoly(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					4, false, PuzzleBorderName.threeSq);
		}
		else if (edge1.getEdgePos() == EdgePosition.H1 &&
				edge2.getEdgePos() == EdgePosition.V2 &&
				edge3.getEdgePos() == EdgePosition.H2 &&
				edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() + 2 == edge3.getEdgeYPos()){
			return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos() + 1,
					1, false, PuzzleBorderName.threeSq);
		}
		else if (edge1.getEdgePos() == EdgePosition.V2 &&
				edge2.getEdgePos() == EdgePosition.H2 &&
				edge3.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() + 2 == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge3.getEdgeYPos()){
			return new PuzzleBorderPoly(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					2, false, PuzzleBorderName.threeSq);
		}
		else if (edge1.getEdgePos() == EdgePosition.H1 &&
				edge2.getEdgePos() == EdgePosition.V1 &&
				edge3.getEdgePos() == EdgePosition.H2 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() + 2 == edge3.getEdgeYPos()){
			return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos() + 1,
					3, false, PuzzleBorderName.threeSq);
		}
		else return null;
	}

	private Array<Edge> checkRtAngldEdges(Array<Edge> edges){
		Array<Edge> edgeTemp = new Array<Edge>();
		edgeTemp.addAll(edges);

		for (int i = 0; i < edgeTemp.size; i++){
			if (i == edgeTemp.size - 1){
				if (edgeTemp.get(i) != null &&
						edgeTemp.first() != null){
					PuzzleBorderPoly bRtAngldEdges = checkIfRtAngldEdges(edgeTemp.get(i), edgeTemp.first());
					PuzzleBorderPoly bRtAngldEdges2 = checkIfRtAngldEdges(edgeTemp.first(), edgeTemp.get(i));
					if (bRtAngldEdges != null) {
						normalBorders.set(0, bRtAngldEdges);
						normalBorders.set(i, bRtAngldEdges);
						edgeTemp.set(i, null);
						edgeTemp.set(0, null);
					}
					else if (bRtAngldEdges2 != null) {
						normalBorders.set(0, bRtAngldEdges2);
						normalBorders.set(i, bRtAngldEdges2);
						edgeTemp.set(i, null);
						edgeTemp.set(0, null);
					}
				}
			}
			else {
				if (edgeTemp.get(i) != null &&
						edgeTemp.get(i + 1) != null){
					PuzzleBorderPoly bRtAngldEdges = checkIfRtAngldEdges(edgeTemp.get(i), edgeTemp.get(i + 1));
					PuzzleBorderPoly bRtAngldEdges2 = checkIfRtAngldEdges(edgeTemp.get(i + 1), edgeTemp.get(i));
					if (bRtAngldEdges != null) {
						normalBorders.set(i, bRtAngldEdges);
						normalBorders.set(i + 1, bRtAngldEdges);
						edgeTemp.set(i, null);
						edgeTemp.set(i + 1, null);
					}
					else if (bRtAngldEdges2 != null) {
						normalBorders.set(i, bRtAngldEdges2);
						normalBorders.set(i + 1, bRtAngldEdges2);
						edgeTemp.set(i, null);
						edgeTemp.set(i + 1, null);
					}
				}
			}
		}

		return edgeTemp;
	}

	private PuzzleBorderPoly checkIfRtAngldEdges(Edge edge1, Edge edge2){
		if (edge1.getEdgePos() == EdgePosition.H2 &&
				edge2.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos() - 1,
					3, false, PuzzleBorderName.perpendSq);
		}
		else if (edge1.getEdgePos() == EdgePosition.H1 &&
				edge2.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos()){
			return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos() + 1,
					4, false, PuzzleBorderName.perpendSq);
		}
		else if (edge1.getEdgePos() == EdgePosition.V2 &&
				edge2.getEdgePos() == EdgePosition.H1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			return new PuzzleBorderPoly(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					1, false, PuzzleBorderName.perpendSq);
		}
		else if (edge1.getEdgePos() == EdgePosition.H2 &&
				edge2.getEdgePos() == EdgePosition.V2 &&
				edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			return new PuzzleBorderPoly(edge1.getEdgeXPos(), edge1.getEdgeYPos() - 1,
					2, false, PuzzleBorderName.perpendSq);
		}
		else return null;
	}

	private void addBorders(Array<Edge> edges){
		for (Edge edge : edges){
			if (edge != null){
				switch (edge.getEdgePos()){
				case EdgePosition.V1: {
					normalBorders.set(edges.indexOf(edge, true), new PuzzleBorderPoly(edge.getEdgeXPos() - 1, edge.getEdgeYPos(),
							3, false, PuzzleBorderName.oneSq));
				}; break;
				case EdgePosition.V2: {
					normalBorders.set(edges.indexOf(edge, true), new PuzzleBorderPoly(edge.getEdgeXPos() + 1, edge.getEdgeYPos(),
							1, false, PuzzleBorderName.oneSq));
				}; break;
				case EdgePosition.H1: {
					normalBorders.set(edges.indexOf(edge, true), new PuzzleBorderPoly(edge.getEdgeXPos(), edge.getEdgeYPos() + 1,
							4, false, PuzzleBorderName.oneSq));
				}; break;
				case EdgePosition.H2: {
					normalBorders.set(edges.indexOf(edge, true), new PuzzleBorderPoly(edge.getEdgeXPos(), edge.getEdgeYPos() - 1,
							2, false, PuzzleBorderName.oneSq));
				}; break;
				case EdgePosition.D1: {
					normalBorders.set(edges.indexOf(edge, true), new PuzzleBorderPoly(edge.getEdgeXPos(), edge.getEdgeYPos(),
							3, false, PuzzleBorderName.oneTria));
				}; break;
				case EdgePosition.D2: {
					normalBorders.set(edges.indexOf(edge, true), new PuzzleBorderPoly(edge.getEdgeXPos(), edge.getEdgeYPos(),
							4, false, PuzzleBorderName.oneTria));
				}; break;
				case EdgePosition.D3: {
					normalBorders.set(edges.indexOf(edge, true), new PuzzleBorderPoly(edge.getEdgeXPos(), edge.getEdgeYPos(),
							1, false, PuzzleBorderName.oneTria));
				}; break;
				case EdgePosition.D4: {
					normalBorders.set(edges.indexOf(edge, true), new PuzzleBorderPoly(edge.getEdgeXPos(), edge.getEdgeYPos(),
							2, false, PuzzleBorderName.oneTria));
				}; break;
				default: break;
				}
			}
		}
	}

	public void createPuzzleGrid(Array<Polygon> puzzlePoly){
		Array<Float> xPoses = new Array<Float>();
		Array<Float> yPoses = new Array<Float>();
		float maxX;
		float maxY;

		for (PuzzleBorderPoly pBord : normalBorders){
			xPoses.add(pBord.xPos());
			yPoses.add(pBord.yPos());
		}
		for (PuzzleBorderPoly pBord : turnBorders){
			xPoses.add(pBord.xPos());
			yPoses.add(pBord.yPos());
		}

		xPoses.sort();
		yPoses.sort();

		if (xPoses.peek() - xPoses.first() > 9){
			maxX = xPoses.peek() - xPoses.first() + 6;
		}
		else maxX = 15;
		if (yPoses.peek() - yPoses.first() > 6){
			maxY = yPoses.peek() - yPoses.first() + 6;
		}
		else maxY = 12;

		this.maxX = maxX;
		this.maxY = maxY;

		int moveXPoints = (int) ((maxX / 2) - ((xPoses.peek() - xPoses.first()) / 2));
		int moveYPoints = (int) ((maxY / 2) - ((yPoses.peek() - yPoses.first()) / 2));

		if (xPoses.first() < moveXPoints){
			for (Polygon poly : puzzlePoly){
				poly.setPolyXPos(poly.getPolyXPos() - (xPoses.first() - moveXPoints));
			}

			for (PuzzleBorderPoly pBord : normalBorders){
				pBord.setXPos(pBord.xPos() - (xPoses.first() - moveXPoints));
			}
			for (PuzzleBorderPoly pBord : turnBorders){
				pBord.setXPos(pBord.xPos() - (xPoses.first() - moveXPoints));
			}
		}

		if (yPoses.first() < moveYPoints){
			for (Polygon poly : puzzlePoly){
				poly.setPolyYPos(poly.getPolyYPos() - (yPoses.first() - moveYPoints));
			}

			for (PuzzleBorderPoly pBord : normalBorders){
				pBord.setYPos(pBord.yPos() - (yPoses.first() - moveYPoints));
			}
			for (PuzzleBorderPoly pBord : turnBorders){
				pBord.setYPos(pBord.yPos() - (yPoses.first() - moveYPoints));
			}
		}

		fillBorders(maxX, maxY);
	}

	private void fillBorders(float maxX, float maxY){

		for (int xPos = 0; xPos <= maxX; xPos++){
			for (int yPos = 0; yPos <= maxY; yPos++){
				if (!checkIfCellHasPolygon(xPos, yPos) &&
						!checkIfCellHasBorder(xPos, yPos)){
					emptyBorders.add(new PuzzleBorderPoly(xPos, yPos, 1, false, PuzzleBorderName.emptySq));
				}
			}
		}
	}

	private boolean checkIfCellHasPolygon (float x, float y){
		for (Polygon poly : puzzlePoly){
			for (PolyTriangle tria : poly.triangles()){
				if (tria.xPos == x &&
						tria.yPos == y) return true;
			}

			for (PolySquare sq : poly.squares()){
				if (sq.xPos == x &&
						sq.yPos == y) return true;
			}
		}
		return false;
	}

	private boolean checkIfCellHasBorder(float x, float y){

		for (PuzzleBorderPoly pBord : normalBorders){
			if (pBord.xPos() == x &&
					pBord.yPos() == y) return true;
		}
		return false;
	}

	private void addBorderTurns(){
		//		check for two turns
		Array<PuzzleBorderPoly> bTemp = new Array<PuzzleBorderPoly>();

		bTemp.addAll(normalBorders);

		for (int i = 0; i < bTemp.size; i++){
			int i2;
			if (i == bTemp.size - 1){
				i2 = 0;
			} else i2 = i + 1;

			boolean borderAdded = checkTwoBorderTurns(bTemp.get(i), bTemp.get(i2));
			if (borderAdded) continue;
			boolean borderAdded2 = checkTwoBorderTurns(bTemp.get(i2), bTemp.get(i));
			if (borderAdded2) continue;
		}

		//		check for one turn
		for (int i = 0; i < bTemp.size; i++){
			int i2;
			if (i == bTemp.size - 1){
				i2 = 0;
			} else i2 = i + 1;

			boolean borderAdded = checkOneBorder(bTemp.get(i), bTemp.get(i2));
			if (borderAdded) continue;
			boolean borderAdded2 = checkOneBorder(bTemp.get(i2), bTemp.get(i));
			if (borderAdded2) continue;
		}
	}

	private boolean checkTwoBorderTurns(PuzzleBorderPoly b1, PuzzleBorderPoly b2){
		if (b1.xPos() + 1 == b2.xPos() &&
				b1.yPos() == b2.yPos()){

			if ((b1.bName() == PuzzleBorderName.oneTria &&
					b1.getPos() == 2 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 3 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 3 &&
					b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.oneSq &&
					b1.getPos() == 3 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.perpendSq &&
					b1.getPos() == 4 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.threeSq &&
					b1.getPos() == 4 &&
					!b1.isRevearsed())){
				if ((b2.bName() == PuzzleBorderName.oneTria &&
						b2.getPos() == 3 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 3 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 3 &&
						b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.oneSq &&
						b2.getPos() == 1 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.perpendSq &&
						b2.getPos() == 1 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.threeSq &&
						b2.getPos() == 4 &&
						!b2.isRevearsed())){
					turnBorders.add(new PuzzleBorderPoly(b1.xPos(), b1.yPos() + 1, 4, false, PuzzleBorderName.turn));
					turnBorders.add(new PuzzleBorderPoly(b1.xPos() + 1, b1.yPos() + 1, 1, false, PuzzleBorderName.turn));
					return true;
				}
			}


			if ((b1.bName() == PuzzleBorderName.oneTria &&
					b1.getPos() == 1 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 1 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 1 &&
					b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.oneSq &&
					b1.getPos() == 3 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.perpendSq &&
					b1.getPos() == 3 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.threeSq &&
					b1.getPos() == 2 &&
					!b1.isRevearsed())){
				if ((b2.bName() == PuzzleBorderName.oneTria &&
						b2.getPos() == 4 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 1 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 1 &&
						b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.oneSq &&
						b2.getPos() == 1 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.perpendSq &&
						b2.getPos() == 2 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.threeSq &&
						b2.getPos() == 2 &&
						!b2.isRevearsed())){
					turnBorders.add(new PuzzleBorderPoly(b1.xPos(), b1.yPos() - 1, 3, false, PuzzleBorderName.turn));
					turnBorders.add(new PuzzleBorderPoly(b1.xPos() + 1, b1.yPos() - 1, 2, false, PuzzleBorderName.turn));
					return true;
				}
			}
		}
		else if (b1.xPos() == b2.xPos() &&
				b1.yPos() - 1 == b2.yPos()){

			if ((b1.bName() == PuzzleBorderName.oneTria &&
					b1.getPos() == 3 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 4 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 2 &&
					b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.oneSq &&
					b1.getPos() == 4 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.perpendSq &&
					b1.getPos() == 1 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.threeSq &&
					b1.getPos() == 1 &&
					!b1.isRevearsed())){
				if ((b2.bName() == PuzzleBorderName.oneTria &&
						b2.getPos() == 4 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 4 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 2 &&
						b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.oneSq &&
						b2.getPos() == 2 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.perpendSq &&
						b2.getPos() == 2 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.threeSq &&
						b2.getPos() == 1 &&
						!b2.isRevearsed())){
					turnBorders.add(new PuzzleBorderPoly(b1.xPos() + 1, b1.yPos(), 1, false, PuzzleBorderName.turn));
					turnBorders.add(new PuzzleBorderPoly(b1.xPos() + 1, b1.yPos() - 1, 2, false, PuzzleBorderName.turn));
					return true;
				}
			}


			if ((b1.bName() == PuzzleBorderName.oneTria &&
					b1.getPos() == 2 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 2 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 4 &&
					b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.oneSq &&
					b1.getPos() == 4 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.perpendSq &&
					b1.getPos() == 4 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.threeSq &&
					b1.getPos() == 3 &&
					!b1.isRevearsed())){
				if ((b2.bName() == PuzzleBorderName.oneTria &&
						b2.getPos() == 1 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 2 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 4 &&
						b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.oneSq &&
						b2.getPos() == 2 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.perpendSq &&
						b2.getPos() == 3 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.threeSq &&
						b2.getPos() == 3 &&
						!b2.isRevearsed())){
					turnBorders.add(new PuzzleBorderPoly(b1.xPos() - 1, b1.yPos(), 4, false, PuzzleBorderName.turn));
					turnBorders.add(new PuzzleBorderPoly(b1.xPos() - 1, b1.yPos() - 1, 3, false, PuzzleBorderName.turn));
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkOneBorder(PuzzleBorderPoly b1, PuzzleBorderPoly b2){
		if (b1.xPos() + 1 == b2.xPos() &&
				b1.yPos() - 1 == b2.yPos()){

			if ((b1.bName() == PuzzleBorderName.oneTria &&
					b1.getPos() == 3) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 4 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 2 &&
					b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.oneSq &&
					b1.getPos() == 4) ||
					(b1.bName() == PuzzleBorderName.perpendSq &&
					b1.getPos() == 1) ||
					(b1.bName() == PuzzleBorderName.threeSq &&
					b1.getPos() == 1)){
				if ((b2.bName() == PuzzleBorderName.oneTria &&
						b2.getPos() == 3) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 3 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 3 &&
						b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.oneSq &&
						b2.getPos() == 1) ||
						(b2.bName() == PuzzleBorderName.perpendSq &&
						b2.getPos() == 1) ||
						(b2.bName() == PuzzleBorderName.threeSq &&
						b2.getPos() == 4)){
					turnBorders.add(new PuzzleBorderPoly(b1.xPos() + 1, b1.yPos(), 1, false, PuzzleBorderName.turn));
					return true;
				}
			}


			if ((b1.bName() == PuzzleBorderName.oneTria &&
					b1.getPos() == 1) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 1 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 1 &&
					b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.oneSq &&
					b1.getPos() == 3) ||
					(b1.bName() == PuzzleBorderName.perpendSq &&
					b1.getPos() == 3) ||
					(b1.bName() == PuzzleBorderName.threeSq &&
					b1.getPos() == 2)){
				if ((b2.bName() == PuzzleBorderName.oneTria &&
						b2.getPos() == 1) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 2 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 4 &&
						b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.oneSq &&
						b2.getPos() == 2) ||
						(b2.bName() == PuzzleBorderName.perpendSq &&
						b2.getPos() == 3) ||
						(b2.bName() == PuzzleBorderName.threeSq &&
						b2.getPos() == 3)){
					turnBorders.add(new PuzzleBorderPoly(b1.xPos(), b1.yPos() - 1, 3, false, PuzzleBorderName.turn));
					return true;
				}
			}
		}
		else if (b1.xPos() + 1 == b2.xPos() &&
				b1.yPos() + 1 == b2.yPos()){

			if ((b1.bName() == PuzzleBorderName.oneTria &&
					b1.getPos() == 4) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 4 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 2 &&
					b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.oneSq &&
					b1.getPos() == 2) ||
					(b1.bName() == PuzzleBorderName.perpendSq &&
					b1.getPos() == 2) ||
					(b1.bName() == PuzzleBorderName.threeSq &&
					b1.getPos() == 1)){
				if ((b2.bName() == PuzzleBorderName.oneTria &&
						b2.getPos() == 4) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 1 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 1 &&
						b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.oneSq &&
						b2.getPos() == 1) ||
						(b2.bName() == PuzzleBorderName.perpendSq &&
						b2.getPos() == 2) ||
						(b2.bName() == PuzzleBorderName.threeSq &&
						b2.getPos() == 2)){
					turnBorders.add(new PuzzleBorderPoly(b1.xPos() + 1, b1.yPos(), 2, false, PuzzleBorderName.turn));
					return true;
				}
			}


			if ((b1.bName() == PuzzleBorderName.oneTria &&
					b1.getPos() == 2) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 3 &&
					!b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.twoTria &&
					b1.getPos() == 3 &&
					b1.isRevearsed()) ||
					(b1.bName() == PuzzleBorderName.oneSq &&
					b1.getPos() == 3) ||
					(b1.bName() == PuzzleBorderName.perpendSq &&
					b1.getPos() == 4) ||
					(b1.bName() == PuzzleBorderName.threeSq &&
					b1.getPos() == 4)){
				if ((b2.bName() == PuzzleBorderName.oneTria &&
						b2.getPos() == 2) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 2 &&
						!b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.twoTria &&
						b2.getPos() == 4 &&
						b2.isRevearsed()) ||
						(b2.bName() == PuzzleBorderName.oneSq &&
						b2.getPos() == 4) ||
						(b2.bName() == PuzzleBorderName.perpendSq &&
						b2.getPos() == 4) ||
						(b2.bName() == PuzzleBorderName.threeSq &&
						b2.getPos() == 3)){
					turnBorders.add(new PuzzleBorderPoly(b1.xPos(), b1.yPos() + 1, 4, false, PuzzleBorderName.turn));
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkIfNoEdges(Array<Edge> edges){
		for (int i = 0; i < edges.size; i++){
			if (edges.get(i) != null) return false;
		}

		return true;
	}

	public Array<PuzzleBorderPoly> getBorders(){ return normalBorders; }
	public Array<PuzzleBorderPoly> getEmptyBorders() {
		return emptyBorders;
	}

	public float getMaxX() {
		return maxX;
	}

	public float getMaxY() {
		return maxY;
	}

	public Array<PuzzleBorderPoly> getInnerBorders() {
		return innerBorders;
	}

	public Array<PuzzleBorderPoly> getTurnBorders() {
		return turnBorders;
	}

}
