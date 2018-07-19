package com.gameInputProcessors.game;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.gameTools.game.MySprite;
import com.polyngons.game.GameScreen;
import com.polyNgonConstants.game.PolyNGonsDimConstants;
import com.polyNgonConstants.game.PolygonState;
import com.polygons.game.Polygon;
import com.polygons.game.PolygonName;

public class PolygonActor {
	private GameScreen gScreen;
	private Pixmap actorImage;
	private Polygon actorPoly;
	private PolygonState state;
	private MySprite polySprite;
	private AssetManager assetM;
	private HashMap<String, AssetDescriptor<Texture>> assetID;
	private float lastPolyXPos;
	private float lastPolyYPos;
	private float originalWidth;
	private float originalHeight;
	private float x;
	private float y;
	private float width;
	private float height;
	private boolean tapTriggered;

	public PolygonActor(GameScreen gScreen, Pixmap actorImage, Polygon poly){
		this.gScreen = gScreen;
		this.actorImage = actorImage;
		assetM = gScreen.game.assetM;
		assetID =  gScreen.game.assetID;
		actorPoly = poly;
		lastPolyXPos = poly.getPolyXPos();
		lastPolyYPos = poly.getPolyYPos();

		state = PolygonState.NULL;
		tapTriggered = false;
		updateOriginalDim();
	}

	private void updateOriginalDim(){
		originalWidth = actorImage.getWidth();
		originalHeight = actorImage.getHeight();
	}

	/* Returns if on transparent clicked
	 */
	public boolean checkIfTriggered(float x, float y){
		if (x >= this.x && x < this.x + width &&
				y >= this.y && y < this.y + height){

			int imageX = (int)(((x % (actorPoly.getPolyXPos() *
					PolyNGonsDimConstants.PUZZLE_SCALE)) * (100 / PolyNGonsDimConstants.PUZZLE_SCALE)));

			int imageY = (int)(originalHeight - (((y % (actorPoly.getPolyYPos() *
					PolyNGonsDimConstants.PUZZLE_SCALE)) * (100 / PolyNGonsDimConstants.PUZZLE_SCALE)))); 
			// relative pointer location

			int pixel = actorImage.getPixel(imageX, imageY);
			if ((pixel & 0x000000ff) != 0){
				return true;
			}
		}
		return false;
	}

	public void updateSpriteTexture(){
		switch (state){
		case FIXED:{
			polySprite.setTexture(assetM.get(assetID.get(PolygonName.getPolygonName(actorPoly.getName()) + "N")));
		}; break;
		case FIXABLE:{
			polySprite.setTexture(assetM.get(assetID.get(PolygonName.getPolygonName(actorPoly.getName()) + "F")));
		}; break;
		case UNFIXABLE:{
			polySprite.setTexture(assetM.get(assetID.get(PolygonName.getPolygonName(actorPoly.getName()) + "UF")));
		}; break;
		default: break;
		}

		switch (actorPoly.getPosition()){
		case 1: polySprite.setRotation(0); break;
		case 2: polySprite.setRotation(-90); break;
		case 3: polySprite.setRotation(-180); break;
		case 4: polySprite.setRotation(-270); break;
		default: break;
		}

		if (!actorPoly.isReversed()) polySprite.setFlip(false, false);
		else {
			if (actorPoly.getPosition() % 2 != 0) polySprite.setFlip(true, false);
			else polySprite.setFlip(false, true);
		}

		updateSpritePosition();
	}

	public void updateSpritePosition(){
		if (!(lastPolyXPos == actorPoly.getPolyXPos() &&
				lastPolyYPos == actorPoly.getPolyYPos())){
			lastPolyXPos = actorPoly.getPolyXPos();
			lastPolyYPos = actorPoly.getPolyYPos();
		}

		float polyWidth = gScreen.getPpManager().getPolyWidth(actorPoly);
		float polyHeight = gScreen.getPpManager().getPolyHeight(actorPoly);
		if (lastPolyXPos + polyWidth > gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxX() - 2){
			lastPolyXPos -= lastPolyXPos + polyWidth - (gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxX() - 2);
		}
		if (lastPolyYPos + polyHeight > gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxY() + 1){
			lastPolyYPos -= lastPolyYPos + polyHeight - (gScreen.getPuzzleGen().getPuzzleBorderCreator().getMaxY() + 1);
		}

		actorPoly.setPolyXPos(lastPolyXPos);
		actorPoly.setPolyYPos(lastPolyYPos);

		float newXPos = actorPoly.getPolyXPos() * PolyNGonsDimConstants.PUZZLE_SCALE;
		float newYPos = actorPoly.getPolyYPos() * PolyNGonsDimConstants.PUZZLE_SCALE;

		polySprite.setProperPosition(newXPos, newYPos);
	}

	public boolean sendTappedMessage(float x, float y){
		tapTriggered = (checkIfTriggered(x, y)) ? true : false;
		return tapTriggered;
	}

	public void resetActor(){
		tapTriggered = false;
	}

	public boolean isTapTriggered() {
		return tapTriggered;
	}

	public Polygon getActorPoly() {
		return actorPoly;
	}

	public PolygonState getState() {
		return state;
	}

	public void setState(PolygonState state) {
		this.state = state;
	}

	public float getOriginalWidth() {
		return originalWidth;
	}

	public float getOriginalHeight() {
		return originalHeight;
	}

	public MySprite getPolySprite() {
		return polySprite;
	}

	public void setPolySprite(MySprite polySprite) {
		this.polySprite = polySprite;
	}

	public void setActorImage(Pixmap actorImage) {
		this.actorImage = actorImage;
		updateOriginalDim();
	}

	//	constant running
	public void setBounds(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
