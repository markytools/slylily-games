package com.gameInputProcessors.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.polyngons.game.GameScreen;
import com.polyNgonConstants.game.PolyNGonsDimConstants;
import com.polyNgonConstants.game.PolygonState;
import com.polygons.game.Polygon;

public class PuzzleInputListener {
	private GameScreen gScreen;
	private Stage stage;
	private float lastX = 0, lastY = 0;
	private ActorGestureListener listener;
	private boolean enablePan;
	private boolean sendMessage;
	private boolean requestScan;
	
	private boolean pendNormalSound;

	public PuzzleInputListener(GameScreen gScreen, Stage stage){
		this.gScreen =  gScreen;
		this.stage = stage;

		enablePan = true;
		sendMessage = true;
		pendNormalSound = false;
		requestScan = false;
		createPuzzleListeners();
	}

	public void createPuzzleListeners(){
		listener = new ActorGestureListener(){
			@Override
			public void zoom(InputEvent event, float initialDistance,
					float distance) {
				enablePan = false;
				sendMessage = false;
				if (distance >= initialDistance){
					gScreen.game.cam.zoom -= .005f;
				} else gScreen.game.cam.zoom += .005f;
				gScreen.setCamBounds();
				super.zoom(event, initialDistance, distance);
			}
			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				if (count % 2 != 0){
					setSelectedPolyPos(x, y);
					gScreen.getPpManager().addPolyToPuzzle(x, y);
					if (sendMessage) for (PolygonActor pActor : gScreen.getPpManager().getPolysOnPuzzle().values()){
						boolean tappedMessage = pActor.sendTappedMessage(x, y);
						if (tappedMessage) break;
					}
					else sendMessage = true;
				}
				super.tap(event, x, y, count, button);
			}
		};
		stage.addListener(listener);
	}

	private void setSelectedPolyPos(float x, float y){
		if (!gScreen.gettUIManager().isDisableOtherInputs()){
			if (gScreen.gettUIManager().isDelayListener()){
				ArrayMap<Polygon, PolygonActor> stateMap = gScreen.getPpManager().getPolysOnPuzzle();
				Button currentSelectedB = gScreen.getUiManager().getUi().getCurrentSelectedB();
				try {
					if (!currentSelectedB.isChecked()) setSelectedPolygon(x, y, stateMap);
				}
				catch (Exception e){
					setSelectedPolygon(x, y, stateMap);
				}
			}
			else gScreen.gettUIManager().setDelayListener(true);
			gScreen.getPpManager().setSelectedPolyState();
		}
	}
	
	private void setSelectedPolygon(float x, float y, ArrayMap<Polygon, PolygonActor> stateMap){
		if (gScreen.selectedPolygon != null) {
			sendMessage = false;
			float newXPos = getSmallXPos(gScreen, x);
			float newYPos = getSmallYPos(gScreen, y);
	
			if (newXPos == gScreen.selectedPolygon.getPolyXPos() &&
					newYPos == gScreen.selectedPolygon.getPolyYPos()){
				if (stateMap.get(gScreen.selectedPolygon).getState() == PolygonState.FIXABLE){
					stateMap.get(gScreen.selectedPolygon).setState(PolygonState.FIXED);
					gScreen.selectedPolygon = null;
					pendNormalSound = true;
					requestScan = true;
					gScreen.getPpManager().setFinishedRegUpdate(true);
				}
				else if (stateMap.get(gScreen.selectedPolygon).getState() == PolygonState.UNFIXABLE) {
					gScreen.getUiManager().getgTips().setAndRenderTip("invalid");
					gScreen.game.gSoundManager.playSound("invalid");
				}
			}
			else {
				gScreen.selectedPolygon.setPolyXPos(getSmallXPos(gScreen, gScreen.game.touchPos.x));
				gScreen.selectedPolygon.setPolyYPos(getSmallYPos(gScreen, gScreen.game.touchPos.y));
				gScreen.getPpManager().getPolysOnPuzzle().get(gScreen.selectedPolygon).updateSpritePosition();
			}
			gScreen.getpCManager().setScanComplete(false);
		}
	}

	public void checkPuzzleInputs(){
		Array<PolygonActor> polyActors = gScreen.getPpManager().getPolysOnPuzzle().values().toArray();
		for (int i = polyActors.size; i > 0; i--){
			PolygonActor pActor = polyActors.get(i - 1);
			pActor.setBounds(pActor.getPolySprite().getActualX(), pActor.getPolySprite().getActualY(),
					pActor.getPolySprite().getActualWidth(), pActor.getPolySprite().getActualHeight());
		}

		checkIfPan();

		//		check For Taps
		if (sendMessage){
			PolygonActor pActor = checkIfNoTapTriggered(polyActors);
			if (gScreen.selectedPolygon == null) {
				if (pActor != null) {
					Polygon actorPoly = pActor.getActorPoly();
					ArrayMap<Polygon, PolygonActor> stateMap = gScreen.getPpManager().getPolysOnPuzzle();
					for (Polygon poly : stateMap.keys()){
						if (poly == actorPoly) {
							gScreen.game.gSoundManager.playSound("blip");
							gScreen.selectedPolygon = poly;
							swapValues(stateMap.indexOfKey(gScreen.selectedPolygon), stateMap.size - 1);
							gScreen.getPpManager().setSelectedPolyState();
							break;
						}
					}
				}
			}
		}
		resetActors(polyActors);
	}
	
	private void resetActors(Array<PolygonActor> polyActors) {
		for (PolygonActor actor : polyActors) {
			actor.resetActor();
			if (actor.getPolySprite() != null) actor.updateSpriteTexture();
		}
		sendMessage = true;
	}

	private PolygonActor checkIfNoTapTriggered(Array<PolygonActor> polyActors){
		for (PolygonActor pActor : polyActors)
			if (pActor.isTapTriggered()) {
				return pActor;
			}
		return null;
	}
	
	private void swapValues(int index1, int index2){
		ArrayMap<Polygon, PolygonActor> stateMap = gScreen.getPpManager().getPolysOnPuzzle();
		
		Polygon poly1 = stateMap.getKeyAt(index1);
		PolygonActor polyActor1 = stateMap.getValueAt(index1);

		Polygon poly2 = stateMap.getKeyAt(index2);
		PolygonActor polyActor2 = stateMap.getValueAt(index2);
		
		stateMap.setKey(index2, poly1);
		stateMap.setValue(index2, polyActor1);

		stateMap.setKey(index1, poly2);
		stateMap.setValue(index1, polyActor2);
	}

	private void checkIfPan(){
		if (!gScreen.panPuzzle) listener.getGestureDetector().reset();
		if (listener.getGestureDetector().isPanning()){
			sendMessage = false;
			if (enablePan){
				if (lastX == 0) lastX = gScreen.game.touchPos.x;
				if (lastY == 0) lastY = gScreen.game.touchPos.y;
				
				gScreen.game.cam.translate(-(gScreen.game.touchPos.x - lastX), -(gScreen.game.touchPos.y - lastY), 0);
			} else enablePan = true;
		}
		else {
			lastX = 0;
			lastY = 0;
		}
	}

	public static float getSmallXPos(GameScreen gScreen, float x){
		float xPos;

		if (x / PolyNGonsDimConstants.PUZZLE_SCALE > gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxX()){
			xPos = gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxX();
		} else xPos = (float) Math.floor(x / PolyNGonsDimConstants.PUZZLE_SCALE);
		return xPos;
	}


	public static float getSmallYPos(GameScreen gScreen, float y){
		float yPos;

		if (y / PolyNGonsDimConstants.PUZZLE_SCALE > gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxY()){
			yPos = gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxY();
		} else yPos = (float) Math.floor(y / PolyNGonsDimConstants.PUZZLE_SCALE);
		return yPos;
	}

	public boolean isSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(boolean sendMessage) {
		this.sendMessage = sendMessage;
	}

	public boolean isPendNormalSound() {
		return pendNormalSound;
	}

	public void setPendNormalSound(boolean pendNormalSound) {
		this.pendNormalSound = pendNormalSound;
	}

	public boolean isRequestScan() {
		return requestScan;
	}

	public void setRequestScan(boolean requestScan) {
		this.requestScan = requestScan;
	}
}
