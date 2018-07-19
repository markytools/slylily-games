package com.puzzleCreatorPrioritizer.game;

import java.util.Random;

import com.badlogic.gdx.utils.Array;
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
import com.polygonParts.game.Edge;
import com.polygonParts.game.EdgePosition;
import com.polygons.game.Polygon;

public class FixablePolygon {
	private Array<Polygon> set;
	private Array<Array<Polygon>> fixablePolygons;
	private Random gen;
	private boolean useAllPoly;

	public FixablePolygon(){
		set = new Array<Polygon>();
		fixablePolygons = new Array<Array<Polygon>>();
		gen = new Random();
		useAllPoly = false;
	}

	public Array<Array<Polygon>> getFixablePolygons(byte pr, float prXPos, float prYPos, Edge selectedEdge, boolean triAUnlocked,
			boolean sqUnlocked, boolean triBUnlocked, boolean recUnlocked, boolean rhomUnlocked, boolean paraAUnlocked,
			boolean trapAUnlocked, boolean rTrapAUnlocked, boolean trapBUnlocked, boolean rTrapBUnlocked, boolean paraBUnlocked){
		fixablePolygons.clear();
		set.clear();

		switch (pr){
		case PriorityConstants.p45dgree1:{
			System.out.println("a");
			if (triAUnlocked) set.add(new TriangleA(prXPos, prYPos, false, 4));
			if (triBUnlocked) set.add(new TriangleB(prXPos, prYPos, false, 1));
			if (rhomUnlocked) set.add(new Rhombus(prXPos, prYPos, false, 1));
			if (trapAUnlocked) set.add(new TrapezoidA(prXPos, prYPos, false, 1));
			if (trapBUnlocked) set.add(new TrapezoidB(prXPos, prYPos, false, 3));
			if (paraAUnlocked) set.add(new ParallelogramA(prXPos, prYPos, false, 1));
			if (paraBUnlocked) set.add(new ParallelogramB(prXPos, prYPos, true, 2));
			if (rTrapAUnlocked) set.add(new RTrapezoidA(prXPos, prYPos, true, 1));
			if (rTrapBUnlocked) set.add(new RTrapezoidB(prXPos, prYPos, false, 3));
			useAllPoly = false;
			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p45dgree2:{
			System.out.println("b");
			if (triAUnlocked) set.add(new TriangleA(prXPos, prYPos, false, 2));
			if (triBUnlocked) set.add(new TriangleB(prXPos, prYPos, false, 2));
			if (rhomUnlocked) set.add(new Rhombus(prXPos, prYPos, true, 2));
			if (trapAUnlocked) set.add(new TrapezoidA(prXPos, prYPos, false, 2));
			if (trapBUnlocked) set.add(new TrapezoidB(prXPos, prYPos, false, 1));
			if (paraAUnlocked) set.add(new ParallelogramA(prXPos, prYPos, true, 2));
			if (paraBUnlocked) set.add(new ParallelogramB(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set.add(new RTrapezoidA(prXPos, prYPos, false, 2));
			if (rTrapBUnlocked) set.add(new RTrapezoidB(prXPos, prYPos, true, 2));
			useAllPoly = false;
			fixablePolygons.add(set);

		}; break;
		case PriorityConstants.p45dgree3:{
			System.out.println("c");
			if (triAUnlocked) set.add(new TriangleA(prXPos, prYPos, false, 3));
			if (triBUnlocked) set.add(new TriangleB(prXPos, prYPos, false, 3));
			if (rhomUnlocked) set.add(new Rhombus(prXPos, prYPos, true, 1));
			if (trapAUnlocked) set.add(new TrapezoidA(prXPos, prYPos, false, 3));
			if (trapBUnlocked) set.add(new TrapezoidB(prXPos, prYPos - 1, false, 2));
			if (paraAUnlocked) set.add(new ParallelogramA(prXPos, prYPos, true, 1));
			if (paraBUnlocked) set.add(new ParallelogramB(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set.add(new RTrapezoidA(prXPos, prYPos, false, 3));
			if (rTrapBUnlocked) set.add(new RTrapezoidB(prXPos, prYPos - 1, true, 1));
			useAllPoly = false;
			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p45dgree4:{
			System.out.println("d");
			if (triAUnlocked) set.add(new TriangleA(prXPos, prYPos, false, 1));
			if (triBUnlocked) set.add(new TriangleB(prXPos, prYPos - 1, false, 2));
			if (rhomUnlocked) set.add(new Rhombus(prXPos, prYPos - 1, false, 2));
			if (trapAUnlocked) set.add(new TrapezoidA(prXPos, prYPos - 2, false, 2));
			if (trapBUnlocked) set.add(new TrapezoidB(prXPos, prYPos - 1, false, 4));
			if (paraAUnlocked) set.add(new ParallelogramA(prXPos, prYPos - 2, false, 2));
			if (paraBUnlocked) set.add(new ParallelogramB(prXPos, prYPos - 2, true, 1));
			if (rTrapAUnlocked) set.add(new RTrapezoidA(prXPos, prYPos - 1, true, 4));
			if (rTrapBUnlocked) set.add(new RTrapezoidB(prXPos, prYPos - 2, false, 4));
			useAllPoly = false;
			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p45dgree5:{
			System.out.println("e");
			if (triAUnlocked) set.add(new TriangleA(prXPos, prYPos, false, 2));
			if (triBUnlocked) set.add(new TriangleB(prXPos - 1, prYPos, false, 3));
			if (rhomUnlocked) set.add(new Rhombus(prXPos - 1, prYPos, false, 1));
			if (trapAUnlocked) set.add(new TrapezoidA(prXPos - 2, prYPos, false, 3));
			if (trapBUnlocked) set.add(new TrapezoidB(prXPos - 1, prYPos - 1, false, 1));
			if (paraAUnlocked) set.add(new ParallelogramA(prXPos - 2, prYPos, false, 1));
			if (paraBUnlocked) set.add(new ParallelogramB(prXPos - 2, prYPos - 1, true, 2));
			if (rTrapAUnlocked) set.add(new RTrapezoidA(prXPos - 1, prYPos, true, 3));
			if (rTrapBUnlocked) set.add(new RTrapezoidB(prXPos - 2, prYPos - 1, false, 1));
			useAllPoly = false;
			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p45dgree6:{
			System.out.println("f");
			if (triAUnlocked) set.add(new TriangleA(prXPos, prYPos, false, 4));
			if (triBUnlocked) set.add(new TriangleB(prXPos, prYPos - 1, false, 4));
			if (rhomUnlocked) set.add(new Rhombus(prXPos, prYPos - 1, true, 2));
			if (trapAUnlocked) set.add(new TrapezoidA(prXPos, prYPos - 2, false, 4));
			if (trapBUnlocked) set.add(new TrapezoidB(prXPos - 1, prYPos - 1, false, 3));
			if (paraAUnlocked) set.add(new ParallelogramA(prXPos, prYPos - 2, true, 2));
			if (paraBUnlocked) set.add(new ParallelogramB(prXPos - 1, prYPos - 2, false, 1));
			if (rTrapAUnlocked) set.add(new RTrapezoidA(prXPos, prYPos - 1, false, 4));
			if (rTrapBUnlocked) set.add(new RTrapezoidB(prXPos - 1, prYPos - 2, true, 4));
			useAllPoly = false;
			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p45dgree7:{
			System.out.println("g");
			if (triAUnlocked) set.add(new TriangleA(prXPos, prYPos, false, 1));
			if (triBUnlocked) set.add(new TriangleB(prXPos - 1, prYPos, false, 1));
			if (rhomUnlocked) set.add(new Rhombus(prXPos - 1, prYPos, true, 1));
			if (trapAUnlocked) set.add(new TrapezoidA(prXPos - 2, prYPos, false, 1));
			if (trapBUnlocked) set.add(new TrapezoidB(prXPos - 1, prYPos, false, 4));
			if (paraAUnlocked) set.add(new ParallelogramA(prXPos - 2, prYPos, true, 1));
			if (paraBUnlocked) set.add(new ParallelogramB(prXPos - 2, prYPos, false, 2));
			if (rTrapAUnlocked) set.add(new RTrapezoidA(prXPos - 1, prYPos, false, 1));
			if (rTrapBUnlocked) set.add(new RTrapezoidB(prXPos - 2, prYPos, true, 3));
			useAllPoly = false;
			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p45dgree8:{
			System.out.println("h");
			if (triAUnlocked) set.add(new TriangleA(prXPos, prYPos, false, 3));
			if (triBUnlocked) set.add(new TriangleB(prXPos, prYPos, false, 4));
			if (rhomUnlocked) set.add(new Rhombus(prXPos, prYPos, false, 2));
			if (trapAUnlocked) set.add(new TrapezoidA(prXPos, prYPos, false, 4));
			if (trapBUnlocked) set.add(new TrapezoidB(prXPos - 1, prYPos, false, 2));
			if (paraAUnlocked) set.add(new ParallelogramA(prXPos, prYPos, false, 2));
			if (paraBUnlocked) set.add(new ParallelogramB(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set.add(new RTrapezoidA(prXPos, prYPos, true, 2));
			if (rTrapBUnlocked) set.add(new RTrapezoidB(prXPos - 1, prYPos, false, 2));
			useAllPoly = false;
			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.pSlntdSqrEdge1:{
			System.out.println("i");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();
			Array<Array<Polygon>> setOfSets1 = new Array<Array<Polygon>>();
			Array<Array<Polygon>> setOfSets2 = new Array<Array<Polygon>>();

			if (triBUnlocked) set1.add(new TriangleB(prXPos, prYPos + 1, false, 1));
			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos + 1, false, 4));
			if (triAUnlocked) set2.add(new TriangleA(prXPos + 1, prYPos + 1, false, 1));

			if (triBUnlocked) set3.add(new TriangleB(prXPos + 1, prYPos, false, 2));
			if (triAUnlocked) set4.add(new TriangleA(prXPos + 1, prYPos + 1, false, 1));
			if (triAUnlocked) set4.add(new TriangleA(prXPos + 1, prYPos, false, 2));

			setOfSets1.add(set1);
			setOfSets1.add(set2);
			setOfSets2.add(set3);
			setOfSets2.add(set4);

			useAllPoly = true;
			float num = gen.nextFloat() * 10;
			if (num <= 1){
				fixablePolygons.add(setOfSets1.random());
			}
			else if (num > 1 &&
					num <= 2){
				fixablePolygons.add(setOfSets2.random());
			} else if (num > 2 &&
					num <= 6){
				if (triAUnlocked && triBUnlocked){
					Array<Array<Polygon>> diamondPolySets = getDiamondPolygon(prXPos, prYPos, PriorityConstants.pSlntdSqrEdge1);
					Array<Polygon> innerSet = new Array<Polygon>();
					for (Array<Polygon> polySet : diamondPolySets){
						for (Polygon poly : polySet){
							innerSet.add(poly);
						}
					}
					fixablePolygons.add(innerSet);
				}
			} else if (num > 6 &&
					num <= 8){
				Array<Polygon> polySet = setOfSets1.random();
				Array<Polygon> innerSet = new Array<Polygon>();
				for (Polygon poly : polySet){
					if (triAUnlocked && triBUnlocked){
						innerSet.add(poly);
						innerSet.add(new TriangleA(prXPos + 1, prYPos, false, 2));
						fixablePolygons.add(innerSet);
					}
				}
			} else if (num > 8){
				Array<Polygon> polySet = setOfSets2.random();
				Array<Polygon> innerSet = new Array<Polygon>();
				for (Polygon poly : polySet){
					if (triAUnlocked && triBUnlocked){
						innerSet.add(poly);
						innerSet.add(new TriangleA(prXPos, prYPos + 1, false, 4));
						fixablePolygons.add(innerSet);
					}
				}
			}
		}; break;
		case PriorityConstants.pSlntdSqrEdge2:{
			System.out.println("j");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();
			Array<Array<Polygon>> setOfSets1 = new Array<Array<Polygon>>();
			Array<Array<Polygon>> setOfSets2 = new Array<Array<Polygon>>();

			if (triBUnlocked) set1.add(new TriangleB(prXPos, prYPos - 1, false, 3));
			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos - 1, false, 3));
			if (triAUnlocked) set2.add(new TriangleA(prXPos + 1, prYPos - 1, false, 2));

			if (triBUnlocked) set3.add(new TriangleB(prXPos + 1, prYPos - 1, false, 2));
			if (triAUnlocked) set4.add(new TriangleA(prXPos + 1, prYPos - 1, false, 2));
			if (triAUnlocked) set4.add(new TriangleA(prXPos + 1, prYPos, false, 1));

			setOfSets1.add(set1);
			setOfSets1.add(set2);
			setOfSets2.add(set3);
			setOfSets2.add(set4);

			useAllPoly = true;
			float num = gen.nextFloat() * 10;
			if (num <= 1){
				fixablePolygons.add(setOfSets1.random());
			}
			else if (num > 1 &&
					num <= 2){
				fixablePolygons.add(setOfSets2.random());
			} else if (num > 2 &&
					num <= 6){
				if (triAUnlocked && triBUnlocked){
					Array<Array<Polygon>> diamondPolySets = getDiamondPolygon(prXPos, prYPos, PriorityConstants.pSlntdSqrEdge2);
					Array<Polygon> innerSet = new Array<Polygon>();
					for (Array<Polygon> polySet : diamondPolySets){
						for (Polygon poly : polySet){
							innerSet.add(poly);
						}
					}
					fixablePolygons.add(innerSet);
				}
			} else if (num > 6 &&
					num <= 8){
				Array<Polygon> polySet = setOfSets1.random();
				Array<Polygon> innerSet = new Array<Polygon>();
				for (Polygon poly : polySet){
					if (triAUnlocked && triBUnlocked){
						innerSet.add(poly);
						innerSet.add(new TriangleA(prXPos + 1, prYPos, false, 1));
						fixablePolygons.add(innerSet);
					}
				}
			} else if (num > 8){
				Array<Polygon> polySet = setOfSets2.random();
				Array<Polygon> innerSet = new Array<Polygon>();
				for (Polygon poly : polySet){
					if (triAUnlocked && triBUnlocked){
						innerSet.add(poly);
						innerSet.add(new TriangleA(prXPos, prYPos - 1, false, 3));
						fixablePolygons.add(innerSet);
					}
				}
			}
		}; break;
		case PriorityConstants.pSlntdSqrEdge3:{
			System.out.println("k");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();
			Array<Array<Polygon>> setOfSets1 = new Array<Array<Polygon>>();
			Array<Array<Polygon>> setOfSets2 = new Array<Array<Polygon>>();

			if (triBUnlocked) set1.add(new TriangleB(prXPos - 1, prYPos - 1, false, 4));
			if (triAUnlocked) set2.add(new TriangleA(prXPos - 1, prYPos - 1, false, 3));
			if (triAUnlocked) set2.add(new TriangleA(prXPos - 1, prYPos, false, 4));

			if (triBUnlocked) set3.add(new TriangleB(prXPos - 1, prYPos - 1, false, 3));
			if (triAUnlocked) set4.add(new TriangleA(prXPos - 1, prYPos - 1, false, 3));
			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos - 1, false, 2));

			setOfSets1.add(set1);
			setOfSets1.add(set2);
			setOfSets2.add(set3);
			setOfSets2.add(set4);

			useAllPoly = true;
			float num = gen.nextFloat() * 10;
			if (num <= 1){
				fixablePolygons.add(setOfSets1.random());
			}
			else if (num > 1 &&
					num <= 2){
				fixablePolygons.add(setOfSets2.random());
			} else if (num > 2 &&
					num <= 6){
				if (triAUnlocked && triBUnlocked){
					Array<Array<Polygon>> diamondPolySets = getDiamondPolygon(prXPos, prYPos, PriorityConstants.pSlntdSqrEdge1);
					Array<Polygon> innerSet = new Array<Polygon>();
					for (Array<Polygon> polySet : diamondPolySets){
						for (Polygon poly : polySet){
							innerSet.add(poly);
						}
					}
					fixablePolygons.add(innerSet);
				}
			} else if (num > 6 &&
					num <= 8){
				Array<Polygon> polySet = setOfSets1.random();
				Array<Polygon> innerSet = new Array<Polygon>();
				for (Polygon poly : polySet){
					if (triAUnlocked && triBUnlocked){
						innerSet.add(poly);
						innerSet.add(new TriangleA(prXPos, prYPos - 1, false, 2));
						fixablePolygons.add(innerSet);
					}
				}
			} else if (num > 8){
				Array<Polygon> polySet = setOfSets2.random();
				Array<Polygon> innerSet = new Array<Polygon>();
				for (Polygon poly : polySet){
					if (triAUnlocked && triBUnlocked){
						innerSet.add(poly);
						innerSet.add(new TriangleA(prXPos - 1, prYPos, false, 4));
						fixablePolygons.add(innerSet);
					}
				}
			}
		}; break;
		case PriorityConstants.pSlntdSqrEdge4:{
			System.out.println("l");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();
			Array<Array<Polygon>> setOfSets1 = new Array<Array<Polygon>>();
			Array<Array<Polygon>> setOfSets2 = new Array<Array<Polygon>>();

			if (triBUnlocked) set1.add(new TriangleB(prXPos - 1, prYPos, false, 4));
			if (triAUnlocked) set2.add(new TriangleA(prXPos - 1, prYPos, false, 3));
			if (triAUnlocked) set2.add(new TriangleA(prXPos - 1, prYPos + 1, false, 4));

			if (triBUnlocked) set3.add(new TriangleB(prXPos - 1, prYPos + 1, false, 1));
			if (triAUnlocked) set4.add(new TriangleA(prXPos - 1, prYPos + 1, false, 4));
			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos + 1, false, 1));

			setOfSets1.add(set1);
			setOfSets1.add(set2);
			setOfSets2.add(set3);
			setOfSets2.add(set4);

			useAllPoly = true;
			float num = gen.nextFloat() * 10;
			if (num <= 1){
				fixablePolygons.add(setOfSets1.random());
			}
			else if (num > 1 &&
					num <= 2){
				fixablePolygons.add(setOfSets2.random());
			} else if (num > 2 &&
					num <= 6){
				if (triAUnlocked && triBUnlocked){
					Array<Array<Polygon>> diamondPolySets = getDiamondPolygon(prXPos, prYPos, PriorityConstants.pSlntdSqrEdge1);
					Array<Polygon> innerSet = new Array<Polygon>();
					for (Array<Polygon> polySet : diamondPolySets){
						for (Polygon poly : polySet){
							innerSet.add(poly);
						}
					}
					fixablePolygons.add(innerSet);
				}
			} else if (num > 6 &&
					num <= 8){
				Array<Polygon> polySet = setOfSets1.random();
				Array<Polygon> innerSet = new Array<Polygon>();
				for (Polygon poly : polySet){
					if (triAUnlocked && triBUnlocked){
						innerSet.add(poly);
						innerSet.add(new TriangleA(prXPos, prYPos + 1, false, 1));
						fixablePolygons.add(innerSet);
					}
				}
			} else if (num > 8){
				Array<Polygon> polySet = setOfSets2.random();
				Array<Polygon> innerSet = new Array<Polygon>();
				for (Polygon poly : polySet){
					if (triAUnlocked && triBUnlocked){
						innerSet.add(poly);
						innerSet.add(new TriangleA(prXPos - 1, prYPos, false, 3));
						fixablePolygons.add(innerSet);
					}
				}
			}
		}; break;
		case PriorityConstants.pSqrEdge1:{
			System.out.println("m");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();

			if (sqUnlocked) set1.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set1.add(new Rectangle(prXPos, prYPos, false, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, false, 4));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, true, 4));
			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos, false, 1));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos, false, 4));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 < 6){
				set.addAll(set1);
			} else set.addAll(set2); 

			fixablePolygons.add(set);

		}; break;
		case PriorityConstants.pSqrEdge2:{
			System.out.println("n");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();

			if (sqUnlocked) set1.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set1.add(new Rectangle(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, true, 3));
			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos, false, 1));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos, false, 2));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 < 6){
				set.addAll(set1);
			} else set.addAll(set2); 

			fixablePolygons.add(set);

		}; break;
		case PriorityConstants.pSqrEdge3:{
			System.out.println("o");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();

			if (sqUnlocked) set1.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set1.add(new Rectangle(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos - 1, true, 2));
			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos, false, 2));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos, false, 3));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 < 6){
				set.addAll(set1);
			} else set.addAll(set2); 

			fixablePolygons.add(set);

		}; break;
		case PriorityConstants.pSqrEdge4:{
			System.out.println("p");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();

			if (sqUnlocked) set1.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set1.add(new Rectangle(prXPos - 1, prYPos, false, 1));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos - 1, prYPos, false, 3));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos - 1, prYPos, true, 1));
			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos, false, 3));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos, false, 4));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 < 6){
				set.addAll(set1);
			} else set.addAll(set2); 

			fixablePolygons.add(set);

		}; break;
		case PriorityConstants.p135dgree1:{
			System.out.println("q");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();

			if (rhomUnlocked) set1.add(new Rhombus(prXPos - 1, prYPos, false, 1));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos - 1, prYPos, false, 1));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos - 1, prYPos - 1, false, 1));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos - 1, prYPos, false, 1));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos - 2, prYPos - 1, true, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos - 1, prYPos, true, 1));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos - 1, prYPos - 1, false, 1));

			if (sqUnlocked) set2.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, true, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 3));

			if (triAUnlocked) set3.add(new TriangleA(prXPos - 1, prYPos, false, 4));
			if (triBUnlocked) set3.add(new TriangleB(prXPos - 1, prYPos - 1, false, 4));
			if (rhomUnlocked) set3.add(new Rhombus(prXPos - 1, prYPos - 1, true, 2));
			if (trapAUnlocked) set3.add(new TrapezoidA(prXPos - 1, prYPos - 2, false, 4));
			if (trapBUnlocked) set3.add(new TrapezoidB(prXPos - 2, prYPos - 1, false, 3));
			if (paraAUnlocked) set3.add(new ParallelogramA(prXPos - 1, prYPos - 2, true, 2));
			if (paraBUnlocked) set3.add(new ParallelogramB(prXPos - 2, prYPos - 2, false, 1));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos - 1, prYPos - 1, false, 4));
			if (rTrapBUnlocked) set3.add(new RTrapezoidB(prXPos - 2, prYPos - 2, true, 4));

			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos, false, 3));
			if (triBUnlocked) set4.add(new TriangleB(prXPos, prYPos, false, 3));
			if (rhomUnlocked) set4.add(new Rhombus(prXPos, prYPos, true, 1));
			if (trapAUnlocked) set4.add(new TrapezoidA(prXPos, prYPos, false, 3));
			if (trapBUnlocked) set4.add(new TrapezoidB(prXPos, prYPos - 1, false, 2));
			if (paraAUnlocked) set4.add(new ParallelogramA(prXPos, prYPos, true, 1));
			if (paraBUnlocked) set4.add(new ParallelogramB(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set4.add(new RTrapezoidA(prXPos, prYPos, false, 3));
			if (rTrapBUnlocked) set4.add(new RTrapezoidB(prXPos, prYPos - 1, true, 1));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 == 1 ||
					num + 1 == 2){
				set.addAll(set1);
			}
			else if (num + 1 == 3 ||
					num + 1 == 4 ||
					num + 1 == 5 ||
					num + 1 == 6){
				set.addAll(set2);
			}
			else if (num + 1 == 7 ||
					num + 1 == 8){
				set.addAll(set3);
			}
			else if (num + 1 == 9 ||
					num + 1 == 10){
				set.addAll(set4);
			}

			fixablePolygons.add(set);

		}; break;
		case PriorityConstants.p135dgree2:{
			System.out.println("r");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();

			if (rhomUnlocked) set1.add(new Rhombus(prXPos - 1, prYPos, true, 1));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos - 1, prYPos, false, 3));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos - 1, prYPos, false, 4));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos - 1, prYPos, true, 1));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos - 2, prYPos, false, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos - 1, prYPos, false, 3));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos - 1, prYPos, true, 3));

			if (sqUnlocked) set2.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, false, 4));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 3));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 4));

			if (triAUnlocked) set3.add(new TriangleA(prXPos - 1, prYPos, false, 3));
			if (triBUnlocked) set3.add(new TriangleB(prXPos - 1, prYPos, false, 4));
			if (rhomUnlocked) set3.add(new Rhombus(prXPos - 1, prYPos, false, 2));
			if (trapAUnlocked) set3.add(new TrapezoidA(prXPos - 1, prYPos, false, 4));
			if (trapBUnlocked) set3.add(new TrapezoidB(prXPos - 2, prYPos, false, 2));
			if (paraAUnlocked) set3.add(new ParallelogramA(prXPos - 1, prYPos, false, 2));
			if (paraBUnlocked) set3.add(new ParallelogramB(prXPos - 2, prYPos, true, 1));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos - 1, prYPos, true, 2));
			if (rTrapBUnlocked) set3.add(new RTrapezoidB(prXPos - 2, prYPos, false, 2));

			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos, false, 4));
			if (triBUnlocked) set4.add(new TriangleB(prXPos, prYPos, false, 1));
			if (rhomUnlocked) set4.add(new Rhombus(prXPos, prYPos, false, 1));
			if (trapAUnlocked) set4.add(new TrapezoidA(prXPos, prYPos, false, 1));
			if (trapBUnlocked) set4.add(new TrapezoidB(prXPos, prYPos, false, 3));
			if (paraAUnlocked) set4.add(new ParallelogramA(prXPos, prYPos, false, 1));
			if (paraBUnlocked) set4.add(new ParallelogramB(prXPos, prYPos, true, 2));
			if (rTrapAUnlocked) set4.add(new RTrapezoidA(prXPos, prYPos, true, 1));
			if (rTrapBUnlocked) set4.add(new RTrapezoidB(prXPos, prYPos, false, 3));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 == 1 ||
					num + 1 == 2){
				set.addAll(set1);
			}
			else if (num + 1 == 3 ||
					num + 1 == 4 ||
					num + 1 == 5 ||
					num + 1 == 6){
				set.addAll(set2);
			}
			else if (num + 1 == 7 ||
					num + 1 == 8){
				set.addAll(set3);
			}
			else if (num + 1 == 9 ||
					num + 1 == 10){
				set.addAll(set4);
			}

			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p135dgree3:{
			System.out.println("s");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();

			if (rhomUnlocked) set1.add(new Rhombus(prXPos, prYPos, true, 2));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos, prYPos - 1, false, 4));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos, prYPos, false, 1));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos, prYPos - 1, true, 2));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, false, 4));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos, prYPos - 1, true, 2));

			if (sqUnlocked) set2.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, true, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 3));

			if (triAUnlocked) set3.add(new TriangleA(prXPos, prYPos + 1, false, 4));
			if (triBUnlocked) set3.add(new TriangleB(prXPos, prYPos + 1, false, 1));
			if (rhomUnlocked) set3.add(new Rhombus(prXPos, prYPos + 1, false, 1));
			if (trapAUnlocked) set3.add(new TrapezoidA(prXPos, prYPos + 1, false, 1));
			if (trapBUnlocked) set3.add(new TrapezoidB(prXPos, prYPos + 1, false, 3));
			if (paraAUnlocked) set3.add(new ParallelogramA(prXPos, prYPos + 1, false, 1));
			if (paraBUnlocked) set3.add(new ParallelogramB(prXPos, prYPos + 1, true, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos + 1, true, 1));
			if (rTrapBUnlocked) set3.add(new RTrapezoidB(prXPos, prYPos + 1, false, 3));

			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos, false, 1));
			if (triBUnlocked) set4.add(new TriangleB(prXPos, prYPos - 1, false, 2));
			if (rhomUnlocked) set4.add(new Rhombus(prXPos, prYPos - 1, false, 2));
			if (trapAUnlocked) set4.add(new TrapezoidA(prXPos, prYPos - 2, false, 2));
			if (trapBUnlocked) set4.add(new TrapezoidB(prXPos, prYPos - 1, false, 4));
			if (paraAUnlocked) set4.add(new ParallelogramA(prXPos, prYPos - 2, false, 2));
			if (paraBUnlocked) set4.add(new ParallelogramB(prXPos, prYPos - 2, true, 1));
			if (rTrapAUnlocked) set4.add(new RTrapezoidA(prXPos, prYPos - 1, true, 4));
			if (rTrapBUnlocked) set4.add(new RTrapezoidB(prXPos, prYPos - 2, false, 4));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 == 1 ||
					num + 1 == 2){
				set.addAll(set1);
			}
			else if (num + 1 == 3 ||
					num + 1 == 4 ||
					num + 1 == 5 ||
					num + 1 == 6){
				set.addAll(set2);
			}
			else if (num + 1 == 7 ||
					num + 1 == 8){
				set.addAll(set3);
			}
			else if (num + 1 == 9 ||
					num + 1 == 10){
				set.addAll(set4);
			}

			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p135dgree4:{
			System.out.println("t");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();

			if (rhomUnlocked) set1.add(new Rhombus(prXPos, prYPos, false, 2));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos, prYPos - 1, false, 2));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos - 1, prYPos, false, 2));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos, prYPos - 1, false, 2));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, true, 4));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos - 1, prYPos - 1, false, 2));

			if (sqUnlocked) set2.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos - 1, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos - 1, prYPos, false, 3));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, true, 2));

			if (triAUnlocked) set3.add(new TriangleA(prXPos, prYPos + 1, false, 1));
			if (triBUnlocked) set3.add(new TriangleB(prXPos - 1, prYPos + 1, false, 1));
			if (rhomUnlocked) set3.add(new Rhombus(prXPos - 1, prYPos + 1, true, 1));
			if (trapAUnlocked) set3.add(new TrapezoidA(prXPos - 2, prYPos + 1, false, 1));
			if (trapBUnlocked) set3.add(new TrapezoidB(prXPos - 1, prYPos + 1, false, 4));
			if (paraAUnlocked) set3.add(new ParallelogramA(prXPos - 2, prYPos + 1, true, 1));
			if (paraBUnlocked) set3.add(new ParallelogramB(prXPos - 2, prYPos + 1, false, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos - 1, prYPos + 1, false, 1));
			if (rTrapBUnlocked) set3.add(new RTrapezoidB(prXPos - 2, prYPos + 1, true, 3));

			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos, false, 4));
			if (triBUnlocked) set4.add(new TriangleB(prXPos, prYPos - 1, false, 4));
			if (rhomUnlocked) set4.add(new Rhombus(prXPos, prYPos - 1, true, 2));
			if (trapAUnlocked) set4.add(new TrapezoidA(prXPos, prYPos - 2, false, 4));
			if (trapBUnlocked) set4.add(new TrapezoidB(prXPos - 1, prYPos - 1, false, 3));
			if (paraAUnlocked) set4.add(new ParallelogramA(prXPos, prYPos - 2, true, 2));
			if (paraBUnlocked) set4.add(new ParallelogramB(prXPos - 1, prYPos - 2, false, 1));
			if (rTrapAUnlocked) set4.add(new RTrapezoidA(prXPos, prYPos - 1, false, 4));
			if (rTrapBUnlocked) set4.add(new RTrapezoidB(prXPos - 1, prYPos - 2, true, 4));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 == 1 ||
					num + 1 == 2){
				set.addAll(set1);
			}
			else if (num + 1 == 3 ||
					num + 1 == 4 ||
					num + 1 == 5 ||
					num + 1 == 6){
				set.addAll(set2);
			}
			else if (num + 1 == 7 ||
					num + 1 == 8){
				set.addAll(set3);
			}
			else if (num + 1 == 9 ||
					num + 1 == 10){
				set.addAll(set4);
			}

			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p135dgree5:{
			System.out.println("u");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();

			if (rhomUnlocked) set1.add(new Rhombus(prXPos, prYPos, false, 1));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos - 1, prYPos, false, 3));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos, prYPos, false, 3));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos - 1, prYPos, false, 1));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos, prYPos, true, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, true, 3));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos - 1, prYPos, false, 3));

			if (sqUnlocked) set2.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos - 1, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos - 1, prYPos, false, 3));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, false, 4));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 4));

			if (triAUnlocked) set3.add(new TriangleA(prXPos + 1, prYPos, false, 2));
			if (triBUnlocked) set3.add(new TriangleB(prXPos + 1, prYPos, false, 2));
			if (rhomUnlocked) set3.add(new Rhombus(prXPos + 1, prYPos, true, 2));
			if (trapAUnlocked) set3.add(new TrapezoidA(prXPos + 1, prYPos, false, 2));
			if (trapBUnlocked) set3.add(new TrapezoidB(prXPos + 1, prYPos, false, 1));
			if (paraAUnlocked) set3.add(new ParallelogramA(prXPos + 1, prYPos, true, 2));
			if (paraBUnlocked) set3.add(new ParallelogramB(prXPos + 1, prYPos, false, 1));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos + 1, prYPos, false, 2));
			if (rTrapBUnlocked) set3.add(new RTrapezoidB(prXPos + 1, prYPos, true, 2));

			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos, false, 1));
			if (triBUnlocked) set4.add(new TriangleB(prXPos - 1, prYPos, false, 1));
			if (rhomUnlocked) set4.add(new Rhombus(prXPos - 1, prYPos, true, 1));
			if (trapAUnlocked) set4.add(new TrapezoidA(prXPos - 2, prYPos, false, 1));
			if (trapBUnlocked) set4.add(new TrapezoidB(prXPos - 1, prYPos, false, 4));
			if (paraAUnlocked) set4.add(new ParallelogramA(prXPos - 2, prYPos, true, 1));
			if (paraBUnlocked) set4.add(new ParallelogramB(prXPos - 2, prYPos, false, 2));
			if (rTrapAUnlocked) set4.add(new RTrapezoidA(prXPos - 1, prYPos, false, 1));
			if (rTrapBUnlocked) set4.add(new RTrapezoidB(prXPos - 2, prYPos, true, 3));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 == 1 ||
					num + 1 == 2){
				set.addAll(set1);
			}
			else if (num + 1 == 3 ||
					num + 1 == 4 ||
					num + 1 == 5 ||
					num + 1 == 6){
				set.addAll(set2);
			}
			else if (num + 1 == 7 ||
					num + 1 == 8){
				set.addAll(set3);
			}
			else if (num + 1 == 9 ||
					num + 1 == 10){
				set.addAll(set4);
			}

			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p135dgree6:{
			System.out.println("v");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();

			if (rhomUnlocked) set1.add(new Rhombus(prXPos, prYPos, true, 1));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos - 1, prYPos, false, 1));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos, prYPos - 1, false, 2));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos - 1, prYPos, true, 1));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, false, 1));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos - 1, prYPos - 1, true, 1));

			if (sqUnlocked) set2.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos - 1, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos - 1, prYPos, false, 3));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, true, 2));

			if (triAUnlocked) set3.add(new TriangleA(prXPos + 1, prYPos, false, 1));
			if (triBUnlocked) set3.add(new TriangleB(prXPos + 1, prYPos - 1, false, 2));
			if (rhomUnlocked) set3.add(new Rhombus(prXPos + 1, prYPos - 1, false, 2));
			if (trapAUnlocked) set3.add(new TrapezoidA(prXPos + 1, prYPos - 2, false, 2));
			if (trapBUnlocked) set3.add(new TrapezoidB(prXPos + 1, prYPos - 1, false, 4));
			if (paraAUnlocked) set3.add(new ParallelogramA(prXPos + 1, prYPos - 2, false, 2));
			if (paraBUnlocked) set3.add(new ParallelogramB(prXPos + 1, prYPos - 2, true, 1));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos + 1, prYPos - 1, true, 4));
			if (rTrapBUnlocked) set3.add(new RTrapezoidB(prXPos + 1, prYPos - 2, false, 4));

			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos, false, 2));
			if (triBUnlocked) set4.add(new TriangleB(prXPos - 1, prYPos, false, 3));
			if (rhomUnlocked) set4.add(new Rhombus(prXPos - 1, prYPos, false, 1));
			if (trapAUnlocked) set4.add(new TrapezoidA(prXPos - 2, prYPos, false, 3));
			if (trapBUnlocked) set4.add(new TrapezoidB(prXPos - 1, prYPos - 1, false, 1));
			if (paraAUnlocked) set4.add(new ParallelogramA(prXPos - 2, prYPos, false, 1));
			if (paraBUnlocked) set4.add(new ParallelogramB(prXPos - 2, prYPos - 1, true, 2));
			if (rTrapAUnlocked) 	set4.add(new RTrapezoidA(prXPos - 1, prYPos, true, 3));
			if (rTrapBUnlocked) set4.add(new RTrapezoidB(prXPos - 2, prYPos - 1, false, 1));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 == 1 ||
					num + 1 == 2){
				set.addAll(set1);
			}
			else if (num + 1 == 3 ||
					num + 1 == 4 ||
					num + 1 == 5 ||
					num + 1 == 6){
				set.addAll(set2);
			}
			else if (num + 1 == 7 ||
					num + 1 == 8){
				set.addAll(set3);
			}
			else if (num + 1 == 9 ||
					num + 1 == 10){
				set.addAll(set4);
			}

			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p135dgree7:{
			System.out.println("w");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();

			if (rhomUnlocked) set1.add(new Rhombus(prXPos, prYPos - 1, true, 2));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos, prYPos - 1, false, 2));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos - 1, prYPos - 1, false, 3));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos, prYPos - 1, true, 2));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos - 1, prYPos - 2, false, 1));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos - 1, false, 2));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos - 1, prYPos - 1, true, 4));

			if (sqUnlocked) set2.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos - 1, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos - 1, prYPos, false, 3));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, false, 4));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 4));

			if (triAUnlocked) set3.add(new TriangleA(prXPos, prYPos - 1, false, 2));
			if (triBUnlocked) set3.add(new TriangleB(prXPos - 1, prYPos - 1, false, 3));
			if (rhomUnlocked) set3.add(new Rhombus(prXPos - 1, prYPos - 1, false, 1));
			if (trapAUnlocked) set3.add(new TrapezoidA(prXPos - 2, prYPos - 1, false, 3));
			if (trapBUnlocked) set3.add(new TrapezoidB(prXPos - 1, prYPos - 2, false, 1));
			if (paraAUnlocked) set3.add(new ParallelogramA(prXPos - 2, prYPos - 1, false, 1));
			if (paraBUnlocked) set3.add(new ParallelogramB(prXPos - 2, prYPos - 2, true, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos - 1, prYPos - 1, true, 3));
			if (rTrapBUnlocked) set3.add(new RTrapezoidB(prXPos - 2, prYPos - 2, false, 1));

			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos, false, 3));
			if (triBUnlocked) set4.add(new TriangleB(prXPos, prYPos, false, 4));
			if (rhomUnlocked) set4.add(new Rhombus(prXPos, prYPos, false, 2));
			if (trapAUnlocked) set4.add(new TrapezoidA(prXPos, prYPos, false, 4));
			if (trapBUnlocked) set4.add(new TrapezoidB(prXPos - 1, prYPos, false, 2));
			if (paraAUnlocked) set4.add(new ParallelogramA(prXPos, prYPos, false, 2));
			if (paraBUnlocked) set4.add(new ParallelogramB(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set4.add(new RTrapezoidA(prXPos, prYPos, true, 2));
			if (rTrapBUnlocked) set4.add(new RTrapezoidB(prXPos - 1, prYPos, false, 2));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 == 1 ||
					num + 1 == 2){
				set.addAll(set1);
			}
			else if (num + 1 == 3 ||
					num + 1 == 4 ||
					num + 1 == 5 ||
					num + 1 == 6){
				set.addAll(set2);
			}
			else if (num + 1 == 7 ||
					num + 1 == 8){
				set.addAll(set3);
			}
			else if (num + 1 == 9 ||
					num + 1 == 10){
				set.addAll(set4);
			}

			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.p135dgree8:{
			System.out.println("x");
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();
			Array<Polygon> set4 = new Array<Polygon>();

			if (rhomUnlocked) set1.add(new Rhombus(prXPos, prYPos - 1, false, 2));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos, prYPos - 1, false, 4));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos, prYPos - 1, false, 4));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos, prYPos - 1, false, 2));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos, prYPos - 2, true, 1));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos - 1, true, 2));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos, prYPos - 1, false, 4));

			if (sqUnlocked) set2.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos, false, 1));
			if (recUnlocked) set2.add(new Rectangle(prXPos, prYPos, false, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, false, 4));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 3));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 4));

			if (triAUnlocked) set3.add(new TriangleA(prXPos, prYPos - 1, false, 3));
			if (triBUnlocked) set3.add(new TriangleB(prXPos, prYPos - 1, false, 3));
			if (rhomUnlocked) set3.add(new Rhombus(prXPos, prYPos - 1, true, 1));
			if (trapAUnlocked) set3.add(new TrapezoidA(prXPos, prYPos - 1, false, 3));
			if (trapBUnlocked) set3.add(new TrapezoidB(prXPos, prYPos - 2, false, 2));
			if (paraAUnlocked) set3.add(new ParallelogramA(prXPos, prYPos - 1, true, 1));
			if (paraBUnlocked) set3.add(new ParallelogramB(prXPos, prYPos - 2, false, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos - 1, false, 3));
			if (rTrapBUnlocked) set3.add(new RTrapezoidB(prXPos, prYPos - 2, true, 1));

			if (triAUnlocked) set4.add(new TriangleA(prXPos, prYPos, false, 2));
			if (triBUnlocked) set4.add(new TriangleB(prXPos, prYPos, false, 2));
			if (rhomUnlocked) set4.add(new Rhombus(prXPos, prYPos, true, 2));
			if (trapAUnlocked) set4.add(new TrapezoidA(prXPos, prYPos, false, 2));
			if (trapBUnlocked) set4.add(new TrapezoidB(prXPos, prYPos, false, 1));
			if (paraAUnlocked) set4.add(new ParallelogramA(prXPos, prYPos, true, 2));
			if (paraBUnlocked) set4.add(new ParallelogramB(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set4.add(new RTrapezoidA(prXPos, prYPos, false, 2));
			if (rTrapBUnlocked) set4.add(new RTrapezoidB(prXPos, prYPos, true, 2));

			useAllPoly = false;
			int num = gen.nextInt(10);
			if (num + 1 == 1 ||
					num + 1 == 2){
				set.addAll(set1);
			}
			else if (num + 1 == 3 ||
					num + 1 == 4 ||
					num + 1 == 5 ||
					num + 1 == 6){
				set.addAll(set2);
			}
			else if (num + 1 == 7 ||
					num + 1 == 8){
				set.addAll(set3);
			}
			else if (num + 1 == 9 ||
					num + 1 == 10){
				set.addAll(set4);
			}

			fixablePolygons.add(set);
		}; break;
		case PriorityConstants.pSlntdRghtAngl1:{
			System.out.println("y");
			Array<Array<Polygon>> setOfSets = new Array<Array<Polygon>>();
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();

			if (triAUnlocked) set1.add(new TriangleA(prXPos - 1, prYPos, false, 4));
			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos, false, 1));
			if (triBUnlocked) set2.add(new TriangleB(prXPos - 1, prYPos, false, 1));
			setOfSets.add(set1);
			setOfSets.add(set2);

			int num = gen.nextInt(2);
			if (num == 0){
				useAllPoly = true;
				Array<Polygon> polySet = setOfSets.random();
				fixablePolygons.add(polySet);
			}
			else {
				useAllPoly = false;
				fixablePolygons.add(set1);
			}
		}; break;
		case PriorityConstants.pSlntdRghtAngl2:{
			System.out.println("z");
			Array<Array<Polygon>> setOfSets = new Array<Array<Polygon>>();
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();

			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos + 1, false, 1));
			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos, false, 2));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos, false, 2));
			setOfSets.add(set1);
			setOfSets.add(set2);

			int num = gen.nextInt(2);
			if (num == 0){
				useAllPoly = true;
				Array<Polygon> polySet = setOfSets.random();
				fixablePolygons.add(polySet);
			}
			else {
				useAllPoly = false;
				fixablePolygons.add(set1);
			}
		}; break;
		case PriorityConstants.pSlntdRghtAngl3:{
			System.out.println("aa");
			Array<Array<Polygon>> setOfSets = new Array<Array<Polygon>>();
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();

			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos, false, 3));
			if (triAUnlocked) set1.add(new TriangleA(prXPos + 1, prYPos, false, 2));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos, false, 3));
			setOfSets.add(set1);
			setOfSets.add(set2);

			int num = gen.nextInt(2);
			if (num == 0){
				useAllPoly = true;
				Array<Polygon> polySet = setOfSets.random();
				fixablePolygons.add(polySet);
			}
			else {
				useAllPoly = false;
				fixablePolygons.add(set1);
			}
		}; break;
		case PriorityConstants.pSlntdRghtAngl4:{
			System.out.println("ab");
			Array<Array<Polygon>> setOfSets = new Array<Array<Polygon>>();
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();

			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos, false, 4));
			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos - 1, false, 3));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos - 1, false, 4));
			setOfSets.add(set1);
			setOfSets.add(set2);

			int num = gen.nextInt(2);
			if (num == 0){
				useAllPoly = true;
				Array<Polygon> polySet = setOfSets.random();
				fixablePolygons.add(polySet);
			}
			else {
				useAllPoly = false;
				fixablePolygons.add(set1);
			}
		}; break;
		case PriorityConstants.pRghtAngl1:{
			System.out.println("ac");
			Array<Array<Polygon>> setOfSets = new Array<Array<Polygon>>();
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();

			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos, false, 2));
			if (triBUnlocked) set1.add(new TriangleB(prXPos - 1, prYPos, false, 3));
			if (rhomUnlocked) set1.add(new Rhombus(prXPos - 1, prYPos, false, 1));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos - 2, prYPos, false, 3));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos - 1, prYPos - 1, false, 1));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos - 2, prYPos, false, 1));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos - 2, prYPos - 1, true, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos - 1, prYPos, true, 3));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos - 2, prYPos - 1, false, 1));

			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos, false, 4));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos - 1, false, 4));
			if (rhomUnlocked) set2.add(new Rhombus(prXPos, prYPos - 1, true, 2));
			if (trapAUnlocked) set2.add(new TrapezoidA(prXPos, prYPos - 2, false, 4));
			if (trapBUnlocked) set2.add(new TrapezoidB(prXPos - 1, prYPos - 1, false, 3));
			if (paraAUnlocked) set2.add(new ParallelogramA(prXPos, prYPos - 2, true, 2));
			if (paraBUnlocked) set2.add(new ParallelogramB(prXPos - 1, prYPos - 2, false, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, false, 4));
			if (rTrapBUnlocked) set2.add(new RTrapezoidB(prXPos - 1, prYPos - 2, true, 4));

			setOfSets.add(set1);
			setOfSets.add(set2);

			if (sqUnlocked) set3.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set3.add(new Rectangle(prXPos - 1, prYPos, false, 1));
			if (recUnlocked) set3.add(new Rectangle(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos - 1, prYPos, false, 3));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos - 1, true, 2));

			useAllPoly = false;
			int num = gen.nextInt(2);
			if (num == 0){
				Array<Polygon> polySet = setOfSets.random();
				fixablePolygons.add(polySet);
			}
			else {
				fixablePolygons.add(set3);
			}
		}; break;
		case PriorityConstants.pRghtAngl2:{
			System.out.println("ad");
			Array<Array<Polygon>> setOfSets = new Array<Array<Polygon>>();
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();

			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos, false, 1));
			if (triBUnlocked) set1.add(new TriangleB(prXPos - 1, prYPos, false, 1));
			if (rhomUnlocked) set1.add(new Rhombus(prXPos - 1, prYPos, true, 1));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos - 2, prYPos, false, 1));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos - 1, prYPos, false, 4));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos - 2, prYPos, true, 1));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos - 2, prYPos, false, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos - 1, prYPos, false, 1));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos - 2, prYPos, true, 3));

			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos, false, 3));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos, false, 4));
			if (rhomUnlocked) set2.add(new Rhombus(prXPos, prYPos, false, 2));
			if (trapAUnlocked) set2.add(new TrapezoidA(prXPos, prYPos, false, 4));
			if (trapBUnlocked) set2.add(new TrapezoidB(prXPos - 1, prYPos, false, 2));
			if (paraAUnlocked) set2.add(new ParallelogramA(prXPos, prYPos, false, 2));
			if (paraBUnlocked) set2.add(new ParallelogramB(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 2));
			if (rTrapBUnlocked) set2.add(new RTrapezoidB(prXPos - 1, prYPos, false, 2));

			setOfSets.add(set1);
			setOfSets.add(set2);

			if (sqUnlocked) set3.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set3.add(new Rectangle(prXPos - 1, prYPos, false, 1));
			if (recUnlocked) set3.add(new Rectangle(prXPos, prYPos, false, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos - 1, prYPos, false, 3));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos, false, 4));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos - 1, prYPos, true, 1));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos, true, 4));

			useAllPoly = false;
			int num = gen.nextInt(2);
			if (num == 0){
				Array<Polygon> polySet = setOfSets.random();
				fixablePolygons.add(polySet);
			}
			else {
				fixablePolygons.add(set3);
			}
		}; break;
		case PriorityConstants.pRghtAngl3:{
			System.out.println("ae");
			Array<Array<Polygon>> setOfSets = new Array<Array<Polygon>>();
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();

			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos, false, 2));
			if (triBUnlocked) set1.add(new TriangleB(prXPos, prYPos, false, 2));
			if (rhomUnlocked) set1.add(new Rhombus(prXPos, prYPos, true, 2));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos, prYPos, false, 2));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos, prYPos, false, 1));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos, prYPos, true, 2));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, false, 2));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos, prYPos, true, 2));

			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos, false, 4));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos, false, 1));
			if (rhomUnlocked) set2.add(new Rhombus(prXPos, prYPos, false, 1));
			if (trapAUnlocked) set2.add(new TrapezoidA(prXPos, prYPos, false, 1));
			if (trapBUnlocked) set2.add(new TrapezoidB(prXPos, prYPos, false, 3));
			if (paraAUnlocked) set2.add(new ParallelogramA(prXPos, prYPos, false, 1));
			if (paraBUnlocked) set2.add(new ParallelogramB(prXPos, prYPos, true, 2));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos, true, 1));
			if (rTrapBUnlocked) set2.add(new RTrapezoidB(prXPos, prYPos, false, 3));

			setOfSets.add(set1);
			setOfSets.add(set2);

			if (sqUnlocked) set3.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set3.add(new Rectangle(prXPos, prYPos, false, 1));
			if (recUnlocked) set3.add(new Rectangle(prXPos, prYPos, false, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos, false, 4));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos, true, 3));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos, true, 4));

			useAllPoly = false;
			int num = gen.nextInt(2);
			if (num == 0){
				Array<Polygon> polySet = setOfSets.random();
				fixablePolygons.add(polySet);
			}
			else {
				fixablePolygons.add(set3);
			}
		}; break;
		case PriorityConstants.pRghtAngl4:{
			System.out.println("af");
			Array<Array<Polygon>> setOfSets = new Array<Array<Polygon>>();
			Array<Polygon> set1 = new Array<Polygon>();
			Array<Polygon> set2 = new Array<Polygon>();
			Array<Polygon> set3 = new Array<Polygon>();

			if (triAUnlocked) set1.add(new TriangleA(prXPos, prYPos, false, 3));
			if (triBUnlocked) set1.add(new TriangleB(prXPos, prYPos, false, 3));
			if (rhomUnlocked) set1.add(new Rhombus(prXPos, prYPos, true, 1));
			if (trapAUnlocked) set1.add(new TrapezoidA(prXPos, prYPos, false, 3));
			if (trapBUnlocked) set1.add(new TrapezoidB(prXPos, prYPos - 1, false, 2));
			if (paraAUnlocked) set1.add(new ParallelogramA(prXPos, prYPos, true, 1));
			if (paraBUnlocked) set1.add(new ParallelogramB(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set1.add(new RTrapezoidA(prXPos, prYPos, false, 3));
			if (rTrapBUnlocked) set1.add(new RTrapezoidB(prXPos, prYPos - 1, true, 1));

			if (triAUnlocked) set2.add(new TriangleA(prXPos, prYPos, false, 1));
			if (triBUnlocked) set2.add(new TriangleB(prXPos, prYPos - 1, false, 2));
			if (rhomUnlocked) set2.add(new Rhombus(prXPos, prYPos - 1, false, 2));
			if (trapAUnlocked) set2.add(new TrapezoidA(prXPos, prYPos - 2, false, 2));
			if (trapBUnlocked) set2.add(new TrapezoidB(prXPos, prYPos - 1, false, 4));
			if (paraAUnlocked) set2.add(new ParallelogramA(prXPos, prYPos - 2, false, 2));
			if (paraBUnlocked) set2.add(new ParallelogramB(prXPos, prYPos - 2, true, 1));
			if (rTrapAUnlocked) set2.add(new RTrapezoidA(prXPos, prYPos - 1, true, 4));
			if (rTrapBUnlocked) set2.add(new RTrapezoidB(prXPos, prYPos - 2, false, 4));

			setOfSets.add(set1);
			setOfSets.add(set2);

			if (sqUnlocked) set3.add(new Square(prXPos, prYPos, false, 1));
			if (recUnlocked) set3.add(new Rectangle(prXPos, prYPos, false, 1));
			if (recUnlocked) set3.add(new Rectangle(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos, false, 1));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos - 1, false, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos - 1, true, 2));
			if (rTrapAUnlocked) set3.add(new RTrapezoidA(prXPos, prYPos, true, 3));

			useAllPoly = false;
			int num = gen.nextInt(2);
			if (num == 0){
				Array<Polygon> polySet = setOfSets.random();
				fixablePolygons.add(polySet);
			}
			else {
				fixablePolygons.add(set3);
			}
		}; break;
		case PriorityConstants.pSVHEdges:{
			Array<Polygon> set = new Array<Polygon>();

			switch (selectedEdge.getEdgePos()){
			case EdgePosition.V1:{
				System.out.println("1");
				useAllPoly = false;
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 3));
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 4));

				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 4));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 4));

				if (sqUnlocked) set.add(new Square(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 1));

				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), false, 1));
				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 2));
				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 2));

				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 2));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 2));

				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 2));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 4));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 4));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 2, false, 4));

				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), false, 2));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 1, false, 3));

				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 2));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 2));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 2));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 2, true, 2));

				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 2, false, 1));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), true, 1));

				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), false, 3));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 4));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 4));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), true, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), true, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), true, 4));

				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), false, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 1, false, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 1, true, 4));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 2, true, 4));

				fixablePolygons.add(set);
			}; break;
			case EdgePosition.V2:{
				System.out.println("2");
				useAllPoly = false;
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 1));
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 2));

				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 2));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, false, 2));

				if (sqUnlocked) set.add(new Square(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 1));

				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 1));
				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 2));
				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, false, 2));

				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, false, 2));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), true, 2));

				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 2));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, false, 2));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 2, false, 2));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, false, 4));

				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 1));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, false, 4));

				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, false, 2));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 2, false, 2));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, true, 2));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), true, 2));

				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 1));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 2, true, 1));

				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, false, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), false, 4));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, true, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), true, 3));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, true, 4));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), true, 4));

				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 2, false, 4));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, false, 4));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos() - 1, true, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() + 1, selectedEdge.getEdgeYPos(), true, 2));

				fixablePolygons.add(set);
			}; break;
			case EdgePosition.H1:{
				System.out.println("3");
				useAllPoly = false;
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 1));
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 4));

				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 1));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, false, 1));

				if (sqUnlocked) set.add(new Square(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 1));

				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 1));
				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, false, 1));
				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 2));

				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 1));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, true, 1));

				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, false, 1));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() + 1, false, 1));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 1));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, false, 3));

				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 3));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, false, 4));

				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, false, 1));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 1));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, true, 1));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() + 1, true, 1));

				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() + 1, false, 2));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, true, 2));

				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, false, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, false, 3));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 4));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, true, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, true, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, true, 3));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, true, 4));

				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() + 1, false, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, false, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() + 1, true, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() + 1, true, 3));

				fixablePolygons.add(set);
			}; break;
			case EdgePosition.H2:{
				System.out.println("4");
				useAllPoly = false;
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 2));
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 3));

				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 3));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 3));

				if (sqUnlocked) set.add(new Square(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 1));

				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 1));
				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 1));
				if (recUnlocked) set.add(new Rectangle(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 2));

				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 1));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 1));

				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 1));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 1, false, 3));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 3));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 3));

				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 2, false, 1));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 2));

				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 1));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 1, false, 1));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 1));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 1));

				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 2));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 2, true, 2));

				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 3));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 3));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, true, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 3));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 3));

				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 2, false, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 2, false, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 2, true, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, true, 1));

				fixablePolygons.add(set);
			}; break;
			case EdgePosition.D1:{
				System.out.println("5");
				useAllPoly = false;
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 3));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 3));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 1));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 3));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 2));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 1));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 1));

				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 3));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 4));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 2));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 4));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 1));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 2));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), true, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 2));

				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 2));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 1));
				
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 4));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), true, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 4));

				fixablePolygons.add(set);
			}; break;
			case EdgePosition.D2:{
				System.out.println("6");
				useAllPoly = false;
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 4));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 1));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 1));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 1));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 3));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 1));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 3));

				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 4));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 4));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 2));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 4));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 3));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, true, 2));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 2, false, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 4));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 2, true, 4));

				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 1));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, true, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 4));

				fixablePolygons.add(set);
			}; break;
			case EdgePosition.D3:{
				System.out.println("7");
				useAllPoly = false;
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 1));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 2));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 2));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 2));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 4));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 2));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, true, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 4));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 4));

				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 1));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 2));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 2));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 2));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, false, 4));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), true, 1));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, true, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 1, true, 4));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos() - 2, false, 4));

				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 2));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 2, false, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 1, false, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 4));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 1, true, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 2, true, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 3));

				fixablePolygons.add(set);
			}; break;
			case EdgePosition.D4:{
				System.out.println("8");
				useAllPoly = false;
				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 2));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 2));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 2));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 2));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 1));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 2));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 1));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), true, 2));

				if (triAUnlocked) set.add(new TriangleA(selectedEdge.getEdgeXPos(), selectedEdge.getEdgeYPos(), false, 2));
				if (triBUnlocked) set.add(new TriangleB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 3));
				if (rhomUnlocked) set.add(new Rhombus(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 1));
				if (trapAUnlocked) set.add(new TrapezoidA(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), false, 3));
				if (trapBUnlocked) set.add(new TrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 1));
				if (paraAUnlocked) set.add(new ParallelogramA(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), false, 1));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 1, true, 2));
				if (rTrapAUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), true, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos() - 1, false, 1));

				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, false, 1));
				if (paraBUnlocked) set.add(new ParallelogramB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), true, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidA(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), false, 3));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), false, 4));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 2, selectedEdge.getEdgeYPos(), true, 1));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos() - 1, true, 2));
				if (rTrapBUnlocked) set.add(new RTrapezoidB(selectedEdge.getEdgeXPos() - 1, selectedEdge.getEdgeYPos(), true, 4));

				fixablePolygons.add(set);
			}; break;
			default: break;
			}
		}; break;
		default: break;
		}
		return fixablePolygons;
	}

	private Array<Array<Polygon>> getDiamondPolygon(float xPos, float yPos, byte pos){
		float newXPos = 0;
		float newYPos = 0;

		switch (pos){
		case PriorityConstants.pSlntdSqrEdge1:{
			newXPos = xPos;
			newYPos = yPos;
		}; break;
		case PriorityConstants.pSlntdSqrEdge2:{
			newXPos = xPos;
			newYPos = yPos - 1;
		}; break;
		case PriorityConstants.pSlntdSqrEdge3:{
			newXPos = xPos - 1;
			newYPos = yPos - 1;
		}; break;
		case PriorityConstants.pSlntdSqrEdge4:{
			newXPos = xPos - 1;
			newYPos = yPos;
		}; break;
		default: break;
		}

		Array<Array<Polygon>> diamondPoly = new Array<Array<Polygon>>();

		//		DP1
		Array<Array<Polygon>> g1A = new Array<Array<Polygon>>();
		Array<Array<Polygon>> g1B = new Array<Array<Polygon>>();
		Array<Polygon> set1 = new Array<Polygon>();
		Array<Polygon> set2 = new Array<Polygon>();
		Array<Polygon> set3 = new Array<Polygon>();
		Array<Polygon> set4 = new Array<Polygon>();

		set1.add(new TriangleB(newXPos, newYPos, false, 4));
		set2.add(new TriangleA(newXPos, newYPos, false, 3));
		set2.add(new TriangleA(newXPos, newYPos + 1, false, 4));
		g1A.add(set1);
		g1A.add(set2);

		set3.add(new TriangleB(newXPos + 1, newYPos, false, 2));
		set4.add(new TriangleA(newXPos + 1, newYPos, false, 2));
		set4.add(new TriangleA(newXPos + 1, newYPos + 1, false, 1));
		g1B.add(set3);
		g1B.add(set4);

		//		DP2
		Array<Array<Polygon>> g2A = new Array<Array<Polygon>>();
		Array<Array<Polygon>> g2B = new Array<Array<Polygon>>();
		Array<Polygon> set5 = new Array<Polygon>();
		Array<Polygon> set6 = new Array<Polygon>();
		Array<Polygon> set7 = new Array<Polygon>();
		Array<Polygon> set8 = new Array<Polygon>();

		set5.add(new TriangleB(newXPos, newYPos + 1, false, 1));
		set6.add(new TriangleA(newXPos, newYPos + 1, false, 4));
		set6.add(new TriangleA(newXPos + 1, newYPos + 1, false, 1));
		g2A.add(set5);
		g2A.add(set6);

		set7.add(new TriangleB(newXPos, newYPos, false, 3));
		set8.add(new TriangleA(newXPos, newYPos, false, 3));
		set8.add(new TriangleA(newXPos + 1, newYPos, false, 2));
		g2B.add(set7);
		g2B.add(set8);

		int num = gen.nextInt(2);
		if (num == 0){
			diamondPoly.add(g1A.random());
			diamondPoly.add(g1B.random());
		}
		else {
			diamondPoly.add(g2A.random());
			diamondPoly.add(g2B.random());
		}

		return diamondPoly;
	}

	public boolean isUseAllPoly(){
		return useAllPoly;
	}
}