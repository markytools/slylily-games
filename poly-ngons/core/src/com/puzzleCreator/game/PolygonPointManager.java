package com.puzzleCreator.game;

import com.badlogic.gdx.utils.Array;
import com.polygonParts.game.Point;
import com.polygonParts.game.PointPosition;

public class PolygonPointManager {
	private Array<Point> normalPoints;
	private Array<Point> multiPoints;

	public PolygonPointManager(){
		normalPoints = new Array<Point>();
		multiPoints = new Array<Point>();
	}

	public void sortPoints(Array<Point> edgePoints){
		
		multiPoints.clear();

		Point[] pointArray = new Point[edgePoints.size];

		for (int i = edgePoints.size; i > 0; i--){
			pointArray[i - 1] = edgePoints.get(i - 1);
		}

		outerloop:
			for (int array1 = pointArray.length; array1 > 0; array1--){
				int count = 0;
				for (int array2 = pointArray.length; array2 > 0; array2--){
					if (pointArray[array1 - 1] != null &  pointArray[array2 - 1] != null &
							pointArray[array1 - 1] != pointArray[array2 - 1]){
						if (pointArray[array1 - 1].getPointEdge() != pointArray[array2 - 1].getPointEdge() &
								!pointArray[array1 - 1].getPointEdge().equals(pointArray[array2 - 1].getPointEdge())){
							if (PolygonPointManager.pointMatches(pointArray[array1 - 1], pointArray[array2 - 1])) count++;
						}
					}

					if (count >= 2) {
						multiPoints.add(new Point());
						break outerloop;
					}
				}
			}
	}

	public static boolean pointMatches(Point point1, Point point2){
		switch (point1.getPositionDesc()){
		case PointPosition.BL : {
			if ((point1.getXPos() - 1 == point2.getXPos() &&
					point1.getYPos() == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.BR) ||
					(point1.getXPos() - 1 == point2.getXPos() &&
					point1.getYPos() - 1 == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.TR) ||
					(point1.getXPos() == point2.getXPos() &&
					point1.getYPos() - 1 == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.TL) ||
					(point1.getXPos() == point2.getXPos() &&
					point1.getYPos() == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.BL)){
				return true;
			}
			else return false;
		} 
		case PointPosition.TL : {
			if ((point1.getXPos() == point2.getXPos() &&
					point1.getYPos() + 1 == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.BL) ||
					(point1.getXPos() - 1 == point2.getXPos() &&
					point1.getYPos() + 1 == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.BR) ||
					(point1.getXPos() - 1 == point2.getXPos() &&
					point1.getYPos() == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.TR) ||
					(point1.getXPos() == point2.getXPos() &&
					point1.getYPos() == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.TL)){
				return true;
			}
			else return false;
		} 
		case PointPosition.TR : {
			if ((point1.getXPos() == point2.getXPos() &&
					point1.getYPos() + 1 == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.BR) ||
					(point1.getXPos() + 1 == point2.getXPos() &&
					point1.getYPos() + 1 == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.BL) ||
					(point1.getXPos() + 1 == point2.getXPos() &&
					point1.getYPos() == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.TL) ||
					(point1.getXPos() == point2.getXPos() &&
					point1.getYPos() == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.TR)){
				return true;
			}
			else return false;
		} 
		case PointPosition.BR : {
			if ((point1.getXPos() == point2.getXPos() &&
					point1.getYPos() - 1 == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.TR) ||
					(point1.getXPos() + 1 == point2.getXPos() &&
					point1.getYPos() - 1 == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.TL) ||
					(point1.getXPos() + 1 == point2.getXPos() &&
					point1.getYPos() == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.BL) ||
					(point1.getXPos() == point2.getXPos() &&
					point1.getYPos() == point2.getYPos() &&
					point2.getPositionDesc() == PointPosition.BR)){
				return true;
			}
			else return false;
		} 
		default: return false;
		}
	}

	public Array<Point> getNormalPoints(){
		return normalPoints;
	}

	public Array<Point> getMultiPoints(){
		return multiPoints;
	}
}
