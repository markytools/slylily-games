package com.puzzleCreator.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.polyngons.game.GameScreen;
import com.polygonParts.game.Edge;
import com.polygonParts.game.EdgePosition;
import com.polygons.game.PolySquare;
import com.polygons.game.PolyTriangle;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;
import com.puzzleBorder.game.PuzzleBorderCreator;
import com.puzzleCreatorPrioritizer.game.FixablePolygon;
import com.puzzleCreatorPrioritizer.game.Priority135Degree;
import com.puzzleCreatorPrioritizer.game.Priority45Degree;
import com.puzzleCreatorPrioritizer.game.PriorityConstants;
import com.puzzleCreatorPrioritizer.game.PriorityPuzzleCreator;
import com.puzzleCreatorPrioritizer.game.PriorityRightAngle;
import com.puzzleCreatorPrioritizer.game.PrioritySlantedRightAngle;
import com.puzzleCreatorPrioritizer.game.PrioritySlantedSquareEdges;
import com.puzzleCreatorPrioritizer.game.PrioritySquareEdges;

public class PolyNGonGenerator {
	private GameScreen gScreen;
	private Polygon centerPoly;
	private PolygonByteCreator polyCreator;
	private PolygonEdgeManager edgeManager;
	private FixablePolygon fPoly;
	private PuzzleBorderCreator bCreator;

	private Array<Polygon> puzzlePolygons;
	private Array<Byte> polygonName;
	private Array<Byte> prioritiesDisabled;

	private PolygonSizeType triASizeType;
	private PolygonSizeType sqSizeType;
	private PolygonSizeType recSizeType;
	private int triALimit, sqLimit, recLimit;

	private float maxGenerationTime;

	//	puzzle dimensions
	private final float centerXPos = 0;
	private final float centerYPos = 0;
	private int usedPoints;

	private boolean triAUnlocked;
	private boolean sqUnlocked;
	private boolean triBUnlocked;
	private boolean recUnlocked;
	private boolean rhomUnlocked;
	private boolean paraAUnlocked;
	private boolean trapAUnlocked;
	private boolean rTrapAUnlocked;
	private boolean trapBUnlocked;
	private boolean rTrapBUnlocked;
	private boolean paraBUnlocked;

	private boolean regeneratePuzzle;
	private boolean continuePuzzle;

	private boolean repeatCheck;

	public PolyNGonGenerator(GameScreen gScreen){
		this.gScreen = gScreen;
		generatorReset();
	}

	public void generatePuzzle (boolean triAUnlocked, boolean sqUnlocked, boolean triBUnlocked, boolean recUnlocked,
			boolean rhomUnlocked, boolean paraAUnlocked, boolean trapAUnlocked, boolean rTrapAUnlocked, boolean trapBUnlocked, 
			boolean rTrapBUnlocked, boolean paraBUnlocked, int points){
		this.triAUnlocked = triAUnlocked;
		this.sqUnlocked = sqUnlocked;
		this.triBUnlocked = triBUnlocked;
		this.recUnlocked = recUnlocked;
		this.rhomUnlocked = rhomUnlocked;
		this.paraAUnlocked = paraAUnlocked;
		this.trapAUnlocked = trapAUnlocked;
		this.rTrapAUnlocked = rTrapAUnlocked;
		this.trapBUnlocked = trapBUnlocked;
		this.rTrapBUnlocked = rTrapBUnlocked;
		this.paraBUnlocked = paraBUnlocked;
		this.usedPoints = points;
		if (this.triAUnlocked) polygonName.add(PolygonName.triA);
		if (this.triBUnlocked) polygonName.add(PolygonName.triB);
		if (this.recUnlocked) polygonName.add(PolygonName.rec);
		if (this.sqUnlocked) polygonName.add(PolygonName.sq);
		if (this.rhomUnlocked) polygonName.add(PolygonName.rhom);
		if (this.paraAUnlocked) polygonName.add(PolygonName.paraA);
		if (this.paraBUnlocked) polygonName.add(PolygonName.paraB);
		if (this.trapAUnlocked) polygonName.add(PolygonName.trapA);
		if (this.trapBUnlocked) polygonName.add(PolygonName.trapB);
		if (this.rTrapAUnlocked) polygonName.add(PolygonName.rTrapA);
		if (this.rTrapBUnlocked) polygonName.add(PolygonName.rTrapB);

		maxGenerationTime = TimeUtils.millis();

		createCenter();

		regeneratePuzzle = false;
		repeatCheck = true;
	}

	public void continuePuzzle(){
		if (this.usedPoints > 0) {
			continuePuzzle = true;
			createStackingPolygons();
			limitPolygons();
		}
		else {
			continuePuzzle = false;
			finalizePuzzle();
		}
	}

	public void finalizePuzzle(){

		Array<Float> xPoses = new Array<Float>();
		Array<Float> yPoses = new Array<Float>();
		//		adjustPuzzleGrid();
		specOuterEdges();
		if (edgeManager.getPointManager().getMultiPoints().size == 0){
			//			createInnerEdges();
			//			bCreator = new PuzzleBorderCreator(edgeManager.getOuterEdges(), edgeManager.getInnerEdges(), puzzlePolygons);
			bCreator = new PuzzleBorderCreator(edgeManager.getOuterEdges(), puzzlePolygons);
			bCreator.createPuzzleBorders();
			if (!bCreator.checkIfCorrectBorders()) refixRegeneration(true);
			else {
				bCreator.createPuzzleGrid(puzzlePolygons);
				for (Polygon poly : puzzlePolygons){
					xPoses.add(poly.getPolyXPos());
					yPoses.add(poly.getPolyYPos());
					printPolyDesc(poly);
				}
				xPoses.sort();
				yPoses.sort();
				System.out.println("max X: " + xPoses.peek() + "      max Y: " + yPoses.peek());
			}
		}
		else refixRegeneration(true);
	}

	public void generatorReset(){
		puzzlePolygons = new Array<Polygon>();
		edgeManager = new PolygonEdgeManager(gScreen);
		polyCreator = new PolygonByteCreator();
		fPoly = new FixablePolygon();

		polygonName = new Array<Byte>();
		prioritiesDisabled = new Array<Byte>();
		new Array<Edge>();
		regeneratePuzzle = true;
		continuePuzzle = true;
		repeatCheck = true;
		maxGenerationTime = TimeUtils.millis();

		setLimitedPolygonSize();
	}

	private void setLimitedPolygonSize() {
		if (triASizeType == PolygonSizeType.LIMITED) triALimit = MathUtils.random(4, 8);
		if (sqSizeType == PolygonSizeType.LIMITED) sqLimit = MathUtils.random(5, 9);
		if (recSizeType == PolygonSizeType.LIMITED) recLimit = MathUtils.random(6, 10);
	}

	private void refixRegeneration(boolean restartRegeneration){
		System.out.println(puzzlePolygons.size + "   IS THE SIZE");
		if (puzzlePolygons.size - 1 == 0 || checkMaxGeneration()) generatorReset();
		else {
			if (restartRegeneration){
				float floatV1 = 8;
				float floatV2 = 9;
				float count =  puzzlePolygons.size * (floatV1 / floatV2);
				while (count <= 1){
					floatV1 -= (floatV1 / 4);
					floatV2 -= (floatV2 / 4);
					count = puzzlePolygons.size * (floatV1 / floatV2);
				}

				while (puzzlePolygons.size > count){
					removePolygon(puzzlePolygons, puzzlePolygons.peek());
				}

				prioritiesDisabled.clear();
				continuePuzzle = true;
			}
			else {
				float floatV1 = 8;
				float floatV2 = 9;
				float count = puzzlePolygons.size * (floatV1 / floatV2);
				while (count <= 1){
					floatV1 -= (floatV1 / 4);
					floatV2 -= (floatV2 / 4);
					count = puzzlePolygons.size * (floatV1 / floatV2);
				}

				while (puzzlePolygons.size > count){
					removePolygon(puzzlePolygons, puzzlePolygons.peek());
					specOuterEdges();
					repeatCheck = false;
				}

				if (prioritiesDisabled.contains(PriorityConstants.pSVHEdges, true)) 
					prioritiesDisabled.clear();
				continuePuzzle = true;
			}
		}
	}

	public void createCenter(){
		byte selectedPoly = polygonName.random();

		centerPoly = polyCreator.createPolygon(selectedPoly, centerXPos, centerYPos, polyCreator.randRevearsed(selectedPoly),
				polyCreator.randomPos(selectedPoly));

		addPolygon(puzzlePolygons, centerPoly);

		Array<Edge> polygonEdges = new Array<Edge>();

		for (PolyTriangle triangle : centerPoly.triangles()){
			for (Edge edge : triangle.getPolyEdges()){
				polygonEdges.add(edge);
			}
		}

		for (PolySquare square : centerPoly.squares()){
			for (Edge edge : square.getPolyEdges()){
				polygonEdges.add(edge);
			}
		}

		edgeManager.setPolygonEdges(polygonEdges);
	}

	public void createStackingPolygons() {
		if (repeatCheck) specOuterEdges();
		else repeatCheck = true;
		if (edgeManager.getPointManager().getMultiPoints().size == 0){
			createFixablePolygons(edgeManager.getOuterEdges());
		}
		else {
			//			System.out.println("Multipoints Detected");
			//			usedPoints = 0;
			//			generatorReset();
			//			regeneratePuzzle = true;
			refixRegeneration(false);
			//			createCenter();
			//			puzzlePolygons.clear();
			//			createInnerEdges();
			//			createFixablePolygons(edgeManager.getInnerEdges());
		}
	}

	private void createFixablePolygons(Array<Edge> fixableEdges){
		Array<Array<Polygon>> fPolygons;

		PriorityPuzzleCreator priority = checkPriorities(fixableEdges);

		if (priority == null){

			Edge selectedEdge = fixableEdges.random();

			fPolygons = fPoly.getFixablePolygons(PriorityConstants.pSVHEdges, selectedEdge.getEdgeXPos(),
					selectedEdge.getEdgeYPos(), selectedEdge, triAUnlocked, sqUnlocked, triBUnlocked, recUnlocked, rhomUnlocked,
					paraAUnlocked, trapAUnlocked, rTrapAUnlocked, trapBUnlocked, rTrapBUnlocked, paraBUnlocked);
			System.out.println("Edge xPos:" + selectedEdge.getEdgeXPos() + "  " + "Edge yPos: " + selectedEdge.getEdgeYPos());
		}
		else {
			fPolygons = fPoly.getFixablePolygons(priority.priorityType, priority.xPos,
					priority.yPos, null, triAUnlocked, sqUnlocked, triBUnlocked, recUnlocked, rhomUnlocked, paraAUnlocked,
					trapAUnlocked, rTrapAUnlocked, trapBUnlocked, rTrapBUnlocked, paraBUnlocked);
			System.out.println("Pr xPos: " + priority.xPos + "    " + "Pr yPos: " + priority.yPos);
		}

		Array<Polygon> polySet = fPolygons.random();

		if (polySet != null){
			if (fPoly.isUseAllPoly()){
				for (Polygon poly : polySet){
					if (checkIfPolygonFits(poly)){
						if (poly == polySet.peek()) prioritiesDisabled.clear();
						addPolygon(puzzlePolygons, poly);
					}
					else {
						refixRegeneration(false);
						break;
					}
				}
			}
			else {
				Polygon selectedPoly = null;

				while (polySet.size != 0){
					selectedPoly = polySet.random();

					if (checkIfPolygonFits(selectedPoly)) break;
					else {
						polySet.removeValue(selectedPoly, true);
					}
				}

				if (checkIfPolygonFits(selectedPoly) && selectedPoly != null) {
					prioritiesDisabled.clear();
					addPolygon(puzzlePolygons, selectedPoly);
				}
				else {
					refixRegeneration(false);
				}
			}
		}
		else 
			refixRegeneration(false);
	}

	private boolean checkMaxGeneration(){
		if (TimeUtils.millis() - maxGenerationTime >= 180000) {
			return true;
		}
		else return false;
	}

	private void specOuterEdges(){
		if (puzzlePolygons.size > 0){
			Array<Edge> polygonEdges = new Array<Edge>();
			for (Polygon poly : puzzlePolygons){
				for (PolyTriangle triangle : poly.triangles()){
					for (Edge edge : triangle.getPolyEdges()){
						polygonEdges.add(edge);
					}
				}

				for (PolySquare square : poly.squares()){
					for (Edge edge : square.getPolyEdges()){
						polygonEdges.add(edge);
					}
				}
			}
			edgeManager.setPolygonEdges(polygonEdges);
			edgeManager.createOuterEdges();
			edgeManager.sortEdgePoints(true);
		}
	}

	@SuppressWarnings("unused")
	private void createInnerEdges(){
		edgeManager.createInnerEdges(edgeManager.getPointManager().getMultiPoints());

	}

	private PriorityPuzzleCreator checkPriorities(Array<Edge> puzzleEdges){

		Array<PrioritySlantedSquareEdges> dSlntdSqrdEdge = checkSlantedSquaredEdgeSpace(puzzleEdges);
		if (dSlntdSqrdEdge.size != 0 && 
				!prioritiesDisabled.contains(PriorityConstants.pSlntdSqrEdge, false)){
			System.out.println("p1");
			prioritiesDisabled.add(PriorityConstants.pSlntdSqrEdge);
			return dSlntdSqrdEdge.random();
		}
		else {
			Array<PrioritySquareEdges> dSqrdEdge = checkSquaredEdgeSpace(puzzleEdges);
			if (dSqrdEdge.size != 0 && 
					!prioritiesDisabled.contains(PriorityConstants.pSqrEdge, false)){
				System.out.println("p2");
				prioritiesDisabled.add(PriorityConstants.pSqrEdge);
				return dSqrdEdge.random();
			}
			else {
				Array<Priority45Degree> d45s = check45DegreeSpace(puzzleEdges);
				if (d45s.size != 0 && 
						!prioritiesDisabled.contains(PriorityConstants.p45dgree, false)){
					System.out.println("p3");
					prioritiesDisabled.add(PriorityConstants.p45dgree);
					return d45s.random();
				} 
				else {
					Array<Priority135Degree> d135s = check135DegreeSpace(puzzleEdges);
					if (d135s.size != 0 && 
							!prioritiesDisabled.contains(PriorityConstants.p135dgree, false)){
						System.out.println("p4");
						prioritiesDisabled.add(PriorityConstants.p135dgree);
						return d135s.random();
					} 
					else {
						Array<PrioritySlantedRightAngle> dSlntdRtAngl = checkSlntdRightAngleSpace(puzzleEdges);
						if (dSlntdRtAngl.size != 0 && 
								!prioritiesDisabled.contains(PriorityConstants.pSlntdRghtAngl, false)){
							System.out.println("p5");
							prioritiesDisabled.add(PriorityConstants.pSlntdRghtAngl);
							return dSlntdRtAngl.random();
						}
						else {
							Array<PriorityRightAngle> dRtAngl = checkRightAngleSpace(puzzleEdges);
							if (dRtAngl.size != 0 && 
									!prioritiesDisabled.contains(PriorityConstants.pRghtAngl, false)){
								System.out.println("p6");
								prioritiesDisabled.add(PriorityConstants.pRghtAngl);
								return dRtAngl.random();
							}
							else {
								System.out.println("p7");
								prioritiesDisabled.add(PriorityConstants.pSVHEdges);
								return null;
							}
						}
					}
				}
			}
		}
	}

	//	prioritizers
	public PriorityPuzzleCreator checkInnerEmptyShapes(Array<Edge> innerEdges){
		return null;
	}

	public Array<Priority45Degree> check45DegreeSpace(Array<Edge> edges){
		Array<Priority45Degree> d45s = new Array<Priority45Degree>();
		Array<Edge> edgeTemp = new Array<Edge>();
		edgeTemp.addAll(edges);


		for (int i = 0; i < edgeTemp.size; i++){
			int i2;

			if (i == edgeTemp.size - 1){
				i2 = 0;
			}
			else {
				i2 = i + 1;
			}

			Edge edge1 = edgeTemp.get(i);
			Edge edge2 = edgeTemp.get(i2);
			Priority45Degree p45Dgr1 = checkIf45DegreeSpace(edge1, edge2);
			if (p45Dgr1 != null){
				d45s.add(p45Dgr1);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				continue;
			}
			Priority45Degree p45Dgr2 = checkIf45DegreeSpace(edge2, edge1);
			if (p45Dgr2 != null){
				d45s.add(p45Dgr2);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				continue;
			}
		}

		return d45s;
	}

	private Priority45Degree checkIf45DegreeSpace(Edge edge1, Edge edge2){
		switch (edge1.getEdgePos()){
		case EdgePosition.D1:{
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.H2)){
				return new Priority45Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos(), PriorityConstants.p45dgree3);
			}
			if ((edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.V1)){
				return new Priority45Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos(), PriorityConstants.p45dgree8);
			}
		}; break;
		case EdgePosition.D2:{
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.H1)){
				return new Priority45Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos(), PriorityConstants.p45dgree1);
			}
			if ((edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.V1)){
				return new Priority45Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos(), PriorityConstants.p45dgree6);
			}
		}; break;
		case EdgePosition.D3:{
			if ((edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.V2)){
				return new Priority45Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos(), PriorityConstants.p45dgree4);
			}
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.H1)){
				return new Priority45Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos(), PriorityConstants.p45dgree7);
			}
		}; break;
		case EdgePosition.D4:{
			if ((edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.V2)){
				return new Priority45Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos(), PriorityConstants.p45dgree2);
			}
			if ((edge1.getEdgeXPos() == edge2.getEdgeXPos()) &&
					(edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos()) &&
					(edge2.getEdgePos() == EdgePosition.H2)){
				return new Priority45Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos(), PriorityConstants.p45dgree5);
			}
		}; break;
		default: break;
		}

		return null;
	}

	public Array<PrioritySlantedSquareEdges> checkSlantedSquaredEdgeSpace(Array<Edge> edges){
		Array<PrioritySlantedSquareEdges> dSlntdSqrdEdgs = new Array<PrioritySlantedSquareEdges>();
		Array<Edge> edgeTemp = new Array<Edge>();
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

			Edge edge1 = edgeTemp.get(i);
			Edge edge2 = edgeTemp.get(i2);
			Edge edge3 = edgeTemp.get(i3);
			PrioritySlantedSquareEdges slntdSqrs = checkIfSlntdSqrdEdges(edge1, edge2, edge3);
			if (slntdSqrs != null){
				dSlntdSqrdEdgs.add(slntdSqrs);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySlantedSquareEdges slntdSqrs2 = checkIfSlntdSqrdEdges(edge1, edge3, edge2);
			if (slntdSqrs2 != null){
				dSlntdSqrdEdgs.add(slntdSqrs2);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySlantedSquareEdges slntdSqrs3 = checkIfSlntdSqrdEdges(edge2, edge1, edge3);
			if (slntdSqrs3 != null){
				dSlntdSqrdEdgs.add(slntdSqrs3);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySlantedSquareEdges slntdSqrs4 = checkIfSlntdSqrdEdges(edge3, edge1, edge2);
			if (slntdSqrs4 != null){
				dSlntdSqrdEdgs.add(slntdSqrs4);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySlantedSquareEdges slntdSqrs5 = checkIfSlntdSqrdEdges(edge2, edge3, edge1);
			if (slntdSqrs5 != null){
				dSlntdSqrdEdgs.add(slntdSqrs5);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySlantedSquareEdges slntdSqrs6 = checkIfSlntdSqrdEdges(edge3, edge2, edge1);
			if (slntdSqrs6 != null){
				dSlntdSqrdEdgs.add(slntdSqrs6);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
		}

		return dSlntdSqrdEdgs;
	}

	private PrioritySlantedSquareEdges checkIfSlntdSqrdEdges(Edge edge1, Edge edge2, Edge edge3){

		if (edge1.getEdgePos() == EdgePosition.D2 &&
				edge2.getEdgePos() == EdgePosition.D3 &&
				edge3.getEdgePos() == EdgePosition.D4 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() + 1 == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge3.getEdgeYPos()){
			PrioritySlantedSquareEdges pSquareEdges =  new PrioritySlantedSquareEdges(edge1.getEdgeXPos(), edge1.getEdgeYPos() - 1,
					PriorityConstants.pSlntdSqrEdge1);
			return pSquareEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D1 &&
				edge2.getEdgePos() == EdgePosition.D4 &&
				edge3.getEdgePos() == EdgePosition.D3 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() + 1 == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge3.getEdgeYPos()){
			PrioritySlantedSquareEdges pSquareEdges =  new PrioritySlantedSquareEdges(edge1.getEdgeXPos(), edge1.getEdgeYPos() + 1,
					PriorityConstants.pSlntdSqrEdge2);
			return pSquareEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D2 &&
				edge2.getEdgePos() == EdgePosition.D1 &&
				edge3.getEdgePos() == EdgePosition.D4 &&
				edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() + 1 == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge3.getEdgeYPos()){
			PrioritySlantedSquareEdges pSquareEdges =  new PrioritySlantedSquareEdges(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					PriorityConstants.pSlntdSqrEdge3);
			return pSquareEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D1 &&
				edge2.getEdgePos() == EdgePosition.D2 &&
				edge3.getEdgePos() == EdgePosition.D3 &&
				edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() + 1 == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge3.getEdgeYPos()){
			PrioritySlantedSquareEdges pSquareEdges =  new PrioritySlantedSquareEdges(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					PriorityConstants.pSlntdSqrEdge4);
			return pSquareEdges;
		}
		else return null;
	}

	public Array<PrioritySquareEdges> checkSquaredEdgeSpace(Array<Edge> edges){
		Array<PrioritySquareEdges> dSqrdEdgs = new Array<PrioritySquareEdges>();

		Array<Edge> edgeTemp = new Array<Edge>();
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

			Edge edge1 = edgeTemp.get(i);
			Edge edge2 = edgeTemp.get(i2);
			Edge edge3 = edgeTemp.get(i3);
			PrioritySquareEdges sqrs = checkIfSqrdEdges(edge1, edge2, edge3);
			if (sqrs != null){
				dSqrdEdgs.add(sqrs);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySquareEdges sqrs2 = checkIfSqrdEdges(edge1, edge3, edge2);
			if (sqrs2 != null){
				dSqrdEdgs.add(sqrs2);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySquareEdges sqrs3 = checkIfSqrdEdges(edge2, edge1, edge3);
			if (sqrs3 != null){
				dSqrdEdgs.add(sqrs3);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySquareEdges sqrs4 = checkIfSqrdEdges(edge3, edge1, edge2);
			if (sqrs4 != null){
				dSqrdEdgs.add(sqrs4);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySquareEdges sqrs5 = checkIfSqrdEdges(edge2, edge3, edge1);
			if (sqrs5 != null){
				dSqrdEdgs.add(sqrs5);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
			PrioritySquareEdges sqrs6 = checkIfSqrdEdges(edge3, edge2, edge1);
			if (sqrs6 != null){
				dSqrdEdgs.add(sqrs6);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				edgeTemp.removeValue(edge3, true);
				continue;
			}
		}

		return dSqrdEdgs;
	}

	private PrioritySquareEdges checkIfSqrdEdges(Edge edge1, Edge edge2, Edge edge3){

		if (edge1.getEdgePos() == EdgePosition.V2 &&
				edge2.getEdgePos() == EdgePosition.H1 &&
				edge3.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() + 2 == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge3.getEdgeYPos()){
			PrioritySquareEdges pSquareEdges =  new PrioritySquareEdges(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					PriorityConstants.pSqrEdge1);
			return pSquareEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.H1 &&
				edge2.getEdgePos() == EdgePosition.V2 &&
				edge3.getEdgePos() == EdgePosition.H2 &&
				edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() + 2 == edge3.getEdgeYPos()){
			PrioritySquareEdges pSquareEdges =  new PrioritySquareEdges(edge1.getEdgeXPos(), edge1.getEdgeYPos() + 1,
					PriorityConstants.pSqrEdge2);
			return pSquareEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.V2 &&
				edge2.getEdgePos() == EdgePosition.H2 &&
				edge3.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() + 2 == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge3.getEdgeYPos()){
			PrioritySquareEdges pSquareEdges =  new PrioritySquareEdges(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					PriorityConstants.pSqrEdge3);
			return pSquareEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.H1 &&
				edge2.getEdgePos() == EdgePosition.V1 &&
				edge3.getEdgePos() == EdgePosition.H2 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos() &&
				edge1.getEdgeXPos() == edge3.getEdgeXPos() &&
				edge1.getEdgeYPos() + 2 == edge3.getEdgeYPos()){
			PrioritySquareEdges pSquareEdges =  new PrioritySquareEdges(edge1.getEdgeXPos(), edge1.getEdgeYPos() + 1,
					PriorityConstants.pSqrEdge4);
			return pSquareEdges;
		}
		else return null;
	}

	public Array<Priority135Degree> check135DegreeSpace(Array<Edge> edges){
		Array<Priority135Degree> d135s = new Array<Priority135Degree>();
		Array<Edge> edgeTemp = new Array<Edge>();
		edgeTemp.addAll(edges);

		for (int i = 0; i < edgeTemp.size; i++){
			int i2;

			if (i == edgeTemp.size - 1){
				i2 = 0;
			}
			else {
				i2 = i + 1;
			}

			Edge edge1 = edgeTemp.get(i);
			Edge edge2 = edgeTemp.get(i2);
			Priority135Degree p135Degree1 = checkIf135DegreeEdges(edge1, edge2);
			if (p135Degree1 != null){
				d135s.add(p135Degree1);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				continue;
			}
			Priority135Degree p135Degree2 = checkIf135DegreeEdges(edge2, edge1);
			if (p135Degree2 != null){
				d135s.add(p135Degree2);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				continue;
			}
		}

		return d135s;
	}

	public Priority135Degree checkIf135DegreeEdges(Edge edge1, Edge edge2){
		if (edge1.getEdgePos() == EdgePosition.D2 &&
				edge2.getEdgePos() == EdgePosition.H2 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge2.getEdgeYPos() - 1){
			Priority135Degree p135Edges =  new Priority135Degree(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					PriorityConstants.p135dgree1);
			return p135Edges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D1 &&
				edge2.getEdgePos() == EdgePosition.H1 &&
				edge1.getEdgeXPos() == edge2.getEdgeXPos() - 1 &&
				edge1.getEdgeYPos() == edge2.getEdgeYPos() + 1){
			Priority135Degree p135Edges =  new Priority135Degree(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					PriorityConstants.p135dgree2);
			return p135Edges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D2 &&
				edge2.getEdgePos() == EdgePosition.V2 &&
				edge1.getEdgeXPos() == edge2.getEdgeXPos() + 1 &&
				edge1.getEdgeYPos() == edge2.getEdgeYPos() + 1){
			Priority135Degree p135Edges =  new Priority135Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos() - 1,
					PriorityConstants.p135dgree3);
			return p135Edges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D3 &&
				edge2.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			Priority135Degree p135Edges =  new Priority135Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos() - 1,
					PriorityConstants.p135dgree4);
			return p135Edges;
		}
		else if (edge1.getEdgePos() == EdgePosition.H1 &&
				edge2.getEdgePos() == EdgePosition.D4 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos()){
			Priority135Degree p135Edges =  new Priority135Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos() + 1,
					PriorityConstants.p135dgree5);
			return p135Edges;
		}
		else if (edge1.getEdgePos() == EdgePosition.H2 &&
				edge2.getEdgePos() == EdgePosition.D3 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			Priority135Degree p135Edges =  new Priority135Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos() - 1,
					PriorityConstants.p135dgree6);
			return p135Edges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D2 &&
				edge2.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos()){
			Priority135Degree p135Edges =  new Priority135Degree(edge1.getEdgeXPos(), edge1.getEdgeYPos() + 1,
					PriorityConstants.p135dgree7);
			return p135Edges;
		}
		else if (edge1.getEdgePos() == EdgePosition.V2 &&
				edge2.getEdgePos() == EdgePosition.D1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			Priority135Degree p135Edges =  new Priority135Degree(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					PriorityConstants.p135dgree8);
			return p135Edges;
		}
		else return null;
	}

	public Array<PrioritySlantedRightAngle> checkSlntdRightAngleSpace(Array<Edge> edges){
		Array<PrioritySlantedRightAngle> dSlntdRtAngls = new Array<PrioritySlantedRightAngle>();
		Array<Edge> edgeTemp = new Array<Edge>();
		edgeTemp.addAll(edges);

		for (int i = 0; i < edgeTemp.size; i++){
			int i2;

			if (i == edgeTemp.size - 1){
				i2 = 0;
			}
			else {
				i2 = i + 1;
			}

			Edge edge1 = edgeTemp.get(i);
			Edge edge2 = edgeTemp.get(i2);
			PrioritySlantedRightAngle pRtAngld1 = checkIfSlntdRtAngldEdges(edge1, edge2);
			if (pRtAngld1 != null){
				dSlntdRtAngls.add(pRtAngld1);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				continue;
			}
			PrioritySlantedRightAngle pRtAngld2 = checkIfSlntdRtAngldEdges(edge2, edge1);
			if (pRtAngld2 != null){
				dSlntdRtAngls.add(pRtAngld2);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				continue;
			}
		}

		return dSlntdRtAngls;
	}

	private PrioritySlantedRightAngle checkIfSlntdRtAngldEdges(Edge edge1, Edge edge2){
		if (edge1.getEdgePos() == EdgePosition.D2 &&
				edge2.getEdgePos() == EdgePosition.D3 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge2.getEdgeYPos()){
			PrioritySlantedRightAngle pRtAngldEdges =  new PrioritySlantedRightAngle(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					PriorityConstants.pSlntdRghtAngl1);
			return pRtAngldEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D4 &&
				edge2.getEdgePos() == EdgePosition.D3 &&
				edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos()){
			PrioritySlantedRightAngle pRtAngldEdges =  new PrioritySlantedRightAngle(edge1.getEdgeXPos(), edge1.getEdgeYPos(),
					PriorityConstants.pSlntdRghtAngl2);
			return pRtAngldEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D1 &&
				edge2.getEdgePos() == EdgePosition.D4 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() == edge2.getEdgeYPos()){
			PrioritySlantedRightAngle pRtAngldEdges =  new PrioritySlantedRightAngle(edge1.getEdgeXPos(), edge1.getEdgeYPos(),
					PriorityConstants.pSlntdRghtAngl3);
			return pRtAngldEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.D2 &&
				edge2.getEdgePos() == EdgePosition.D1 &&
				edge1.getEdgeXPos() == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			PrioritySlantedRightAngle pRtAngldEdges =  new PrioritySlantedRightAngle(edge1.getEdgeXPos(), edge1.getEdgeYPos(),
					PriorityConstants.pSlntdRghtAngl4);
			return pRtAngldEdges;
		}
		else return null;
	}

	public Array<PriorityRightAngle> checkRightAngleSpace(Array<Edge> edges){
		Array<PriorityRightAngle> dRtAngls = new Array<PriorityRightAngle>();
		Array<Edge> edgeTemp = new Array<Edge>();
		edgeTemp.addAll(edges);

		for (int i = 0; i < edgeTemp.size; i++){
			int i2;

			if (i == edgeTemp.size - 1){
				i2 = 0;
			}
			else {
				i2 = i + 1;
			}

			Edge edge1 = edgeTemp.get(i);
			Edge edge2 = edgeTemp.get(i2);
			PriorityRightAngle pRtAngld1 = checkIfRtAngldEdges(edge1, edge2);
			if (pRtAngld1 != null){
				dRtAngls.add(pRtAngld1);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				continue;
			}
			PriorityRightAngle pRtAngld2 = checkIfRtAngldEdges(edge2, edge1);
			if (pRtAngld2 != null){
				dRtAngls.add(pRtAngld2);
				edgeTemp.removeValue(edge1, true);
				edgeTemp.removeValue(edge2, true);
				continue;
			}
		}

		return dRtAngls;
	}

	private PriorityRightAngle checkIfRtAngldEdges(Edge edge1, Edge edge2){
		if (edge1.getEdgePos() == EdgePosition.H2 &&
				edge2.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			PriorityRightAngle pRtAngldEdges =  new PriorityRightAngle(edge1.getEdgeXPos(), edge1.getEdgeYPos() - 1,
					PriorityConstants.pRghtAngl1);
			return pRtAngldEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.H1 &&
				edge2.getEdgePos() == EdgePosition.V1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() + 1 == edge2.getEdgeYPos()){
			PriorityRightAngle pRtAngldEdges =  new PriorityRightAngle(edge1.getEdgeXPos(), edge1.getEdgeYPos() + 1,
					PriorityConstants.pRghtAngl2);
			return pRtAngldEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.V2 &&
				edge2.getEdgePos() == EdgePosition.H1 &&
				edge1.getEdgeXPos() + 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			PriorityRightAngle pRtAngldEdges =  new PriorityRightAngle(edge1.getEdgeXPos() + 1, edge1.getEdgeYPos(),
					PriorityConstants.pRghtAngl3);
			return pRtAngldEdges;
		}
		else if (edge1.getEdgePos() == EdgePosition.H2 &&
				edge2.getEdgePos() == EdgePosition.V2 &&
				edge1.getEdgeXPos() - 1 == edge2.getEdgeXPos() &&
				edge1.getEdgeYPos() - 1 == edge2.getEdgeYPos()){
			PriorityRightAngle pRtAngldEdges =  new PriorityRightAngle(edge1.getEdgeXPos(), edge1.getEdgeYPos() - 1,
					PriorityConstants.pRghtAngl4);
			return pRtAngldEdges;
		}
		else return null;
	}

	public boolean checkIfPolygonFits(Polygon addedPoly){

		for (Polygon poly : puzzlePolygons){
			if (poly != null && addedPoly != null) { 
				for (PolyTriangle tri1 : addedPoly.triangles()){
					for (PolyTriangle tri2 : poly.triangles()) 
						if (!checkIfNoOtherTriangles(tri1, tri2)) return false;
					for (PolySquare sq2 : poly.squares())
						if (tri1.xPos == sq2.xPos && tri1.yPos == sq2.yPos) return false;
				}

				for (PolySquare sq1 : addedPoly.squares()){
					for (PolyTriangle tri2 : poly.triangles()) 
						if (sq1.xPos == tri2.xPos && sq1.yPos == tri2.yPos) return false;
					for (PolySquare sq2 : poly.squares())
						if (sq1.xPos == sq2.xPos && sq1.yPos == sq2.yPos) return false;
				}
			}
		}

		return true;
	}

	public boolean checkIfNoOtherTriangles(PolyTriangle tria1, PolyTriangle tria2){
		if (tria1.xPos == tria2.xPos && tria1.yPos == tria2.yPos){
			switch (tria1.position){
			case 1: {
				if (tria2.position == 3) return true;
				else return false;
			}
			case 2: {
				if (tria2.position == 4) return true;
				else return false;
			}
			case 3: {
				if (tria2.position == 1) return true;
				else return false;
			}
			case 4: {
				if (tria2.position == 2) return true;
				else return false;
			}
			default: return false;
			}
		}
		else return true;
	}

	private void addPolygon(Array<Polygon> puzzlePoly, Polygon poly){
		puzzlePoly.add(poly);
		usedPoints -= PolyPoints.getPolyPoints(poly.getName());
	}

	private void removePolygon(Array<Polygon> puzzlePoly, Polygon poly){
		puzzlePoly.removeIndex(puzzlePoly.size - 1);
		usedPoints += PolyPoints.getPolyPoints(poly.getName());
	}

	private void limitPolygons(){
		if (triAUnlocked){
			if (triASizeType == PolygonSizeType.LIMITED) {
				int count = 0;
				for (Polygon poly : puzzlePolygons) {
					if (poly.getName() == PolygonName.triA) count++;
					if (count >= triALimit){
						triAUnlocked = false;
						if (breakOuterLoop()) break;
					}
				}
			}
		}

		if (sqUnlocked){
			if (sqSizeType == PolygonSizeType.LIMITED) {
				int count = 0;
				for (Polygon poly : puzzlePolygons) {
					if (poly.getName() == PolygonName.sq) count++;
					if (count >= sqLimit){
						sqUnlocked = false;
						if (breakOuterLoop()) break;
					}
				}
			}
		}

		if (recUnlocked){
			if (recSizeType == PolygonSizeType.LIMITED) {
				int count = 0;
				for (Polygon poly : puzzlePolygons) {
					if (poly.getName() == PolygonName.rec) count++;
					if (count >= recLimit){
						recUnlocked = false;
						if (breakOuterLoop()) break;
					}
				}
			}
		}
	}

	private boolean breakOuterLoop(){
		if (!triBUnlocked){
			triBUnlocked = true;
			return true;
		}
		else if (!rhomUnlocked){
			rhomUnlocked = true;
			return true;
		}
		else if (!paraAUnlocked){
			paraAUnlocked = true;
			return true;
		}
		else if (!trapAUnlocked){
			trapAUnlocked = true;
			return true;
		}
		else if (!rTrapAUnlocked){
			rTrapAUnlocked = true;
			return true;
		}
		else if (!trapBUnlocked){
			trapBUnlocked = true;
			return true;
		}
		else if (!rTrapBUnlocked){
			rTrapBUnlocked = true;
			return true;
		}
		else if (!paraBUnlocked){
			paraBUnlocked = true;
			return true;
		}
		else return true;
	}

	public Array<Polygon> getPuzzlePolygon(){
		return puzzlePolygons;
	}

	public PuzzleBorderCreator getPuzzleBorderCreator(){
		return bCreator;
	}

	public final static void printPolyDesc(Polygon poly){
//		StringBuilder polyDesc = new StringBuilder("ID:" + PolygonName.getPolygonName(poly.getName()) +
//				" pos:" + String.valueOf(poly.getPosition()) + " rev:" + String.valueOf(poly.isReversed()) +
//				" xPos:" + poly.getPolyXPos() + " yPos:" + poly.getPolyYPos() + "  ");
		//		FOR EMERGENCIES ONLY!!!
		//		for (PolyTriangle tri : poly.triangles()){
		//			polyDesc.append(" polyTri" + poly.triangles().indexOf(tri, true) + "  triXPos:" + tri.xPos +
		//					"  triYPos:" + tri.yPos + " triPos:" + tri.position + "   ");
		//		}
		//		for (PolySquare sq : poly.squares()){
		//			polyDesc.append(" polySq" + poly.squares().indexOf(sq, true) + "  sqXPos:" + sq.xPos +
		//					"  sqYPos:" + sq.yPos + "   ");
		//		}
//		System.out.println(polyDesc);
	}

	public boolean isRegeneratePuzzle(){ return regeneratePuzzle; }
	public boolean isContinuePuzzle(){ return continuePuzzle; }

	public PolygonByteCreator getPolyCreator() {
		return polyCreator;
	}

	public void setPuzzlePolygons(Array<Polygon> puzzlePolygons) {
		this.puzzlePolygons = puzzlePolygons;
	}

	public void setTriASizeType(PolygonSizeType triASizeType) {
		this.triASizeType = triASizeType;
	}

	public void setSqSizeType(PolygonSizeType sqSizeType) {
		this.sqSizeType = sqSizeType;
	}

	public void setRecSizeType(PolygonSizeType recSizeType) {
		this.recSizeType = recSizeType;
	}

	public PolygonSizeType getTriASizeType() {
		return triASizeType;
	}

	public PolygonSizeType getSqSizeType() {
		return sqSizeType;
	}

	public PolygonSizeType getRecSizeType() {
		return recSizeType;
	}
}
