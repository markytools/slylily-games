package com.puzzleCreator.game;

import com.badlogic.gdx.math.MathUtils;
import com.polygonManager.game.ParallelogramA;
import com.polygonManager.game.ParallelogramB;
import com.polygonManager.game.RTrapezoidA;
import com.polygonManager.game.RTrapezoidB;
import com.polygonManager.game.Rectangle;
import com.polygonManager.game.Rhombus;
import com.polygonManager.game.Square;
import com.polygonManager.game.TrapezoidA;
import com.polygonManager.game.TrapezoidB;
import com.polygonManager.game.TriangleA;
import com.polygonManager.game.TriangleB;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;

public class PolygonByteCreator {
	
	public PolygonByteCreator(){
		
	}
	
	public int randomPos(byte polygonID){
		switch (polygonID){
		case PolygonName.triA: return MathUtils.random(3) + 1;
		case PolygonName.triB: return MathUtils.random(3) + 1;
		case PolygonName.sq: return MathUtils.random(3) + 1;
		case PolygonName.rhom: return MathUtils.random(1) + 1;
		case PolygonName.trapA: return MathUtils.random(3) + 1;
		case PolygonName.trapB: return MathUtils.random(3) + 1;
		case PolygonName.paraA: return MathUtils.random(1) + 1;
		case PolygonName.paraB: return MathUtils.random(1) + 1;
		case PolygonName.rec: return MathUtils.random(1) + 1;
		case PolygonName.rTrapA: return MathUtils.random(3) + 1;
		case PolygonName.rTrapB: return MathUtils.random(3) + 1;
		default: return 0;
		}
	}
	
	public boolean randRevearsed(byte polygonID){
		switch (polygonID){
		case PolygonName.triA: return false;
		case PolygonName.triB: return false;
		case PolygonName.sq: return false;
		case PolygonName.rhom: return MathUtils.randomBoolean();
		case PolygonName.trapA: return false;
		case PolygonName.trapB: return false;
		case PolygonName.paraA: return MathUtils.randomBoolean();
		case PolygonName.paraB: return MathUtils.randomBoolean();
		case PolygonName.rec: return false;
		case PolygonName.rTrapA: return MathUtils.randomBoolean();
		case PolygonName.rTrapB: return MathUtils.randomBoolean();
		default: return false;
		}
	}
	
	public Polygon createPolygon(byte polygonID, float xPos, float yPos, boolean reversed, int position){
		Polygon polygon = null;
		
		switch (polygonID){
		case PolygonName.triA:{
			polygon = new TriangleA(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.triB:{
			polygon = new TriangleB(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.rhom:{
			polygon = new Rhombus(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.rec:{
			polygon = new Rectangle(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.sq:{
			polygon = new Square(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.paraA:{
			polygon = new ParallelogramA(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.paraB:{
			polygon = new ParallelogramB(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.trapA:{
			polygon = new TrapezoidA(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.trapB:{
			polygon = new TrapezoidB(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.rTrapA:{
			polygon = new RTrapezoidA(xPos, yPos, reversed, position);
		}; break;
		case PolygonName.rTrapB:{
			polygon = new RTrapezoidB(xPos, yPos, reversed, position);
		}; break;
		default: break;
		}
		
		return polygon;
	}
}
