package com.gameObstacles.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gameWorld.game.WorldListener;
import com.gameWorld.game.WorldManager;
import com.springdynamics.game.GameScreen;
import com.springDynamicsConstants.game.Dimensions;
import com.springDynamicsConstants.game.PrecalculatedUnits;
import com.springDynamicsConstants.game.WorldConstants;

public class ObstacleManager {
	private GameScreen gScreen;
	private WorldListener wListener;
	private Array<Body> worldBodies;
	private Array<Float> powerRange;
	private World world;

	private Body precBody, sdwBody;
	private boolean createConvergingWay;

	public ObstacleManager(GameScreen gScreen, WorldListener wListener, World world,
			Array<Body> worldBodies){
		this.gScreen= gScreen;
		this.wListener = wListener;
		this.worldBodies = worldBodies;
		this.world = world;

		powerRange = new Array<Float>();
		createConvergingWay = false;
	}

	public void createSingleWayObstacle(World world, BodyDef bDef, Body startingBody){
		float bPosX = startingBody.getPosition().x * Dimensions.BOX_TO_WORLD;
		float bPosY = startingBody.getPosition().y * Dimensions.BOX_TO_WORLD;
		float minTime, maxTime, randomTime, powerSelected;
		float smallestTime, largestTime;

		powerRange.clear();

		if (bPosY + 65 <= 635){
			for (float i = 1; i <= 6; i++){
				powerRange.add(i);
			}
			minTime = 3 / 4f;
			maxTime = 5 / 4f;
		}
		else {
			for (float i = 1; i <= 6; i++){
				powerRange.add(i);
			}
			minTime = 1f;
			maxTime = 5 / 4f;
		}

		powerSelected = powerRange.random();

		PrecalculatedUnits pUnits = wListener.getjPPrecalculations().get(powerSelected);
		if (powerSelected == 1){
			randomTime = pUnits.flatTime;
		}
		else {
			if (bPosY + pUnits.maxHeight <=  635){
				smallestTime = minTime * pUnits.flatTime;
				largestTime = (float) Math.min(maxTime * pUnits.flatTime,
						Math.sqrt((bPosY + pUnits.maxHeight - 25) / (-WorldManager.gravity * 100 / 2)) 
						+ (pUnits.flatTime / 2));
			}
			else {
				smallestTime = (float) Math.max(minTime * pUnits.flatTime,
						Math.sqrt((bPosY + pUnits.maxHeight - 635) / (-WorldManager.gravity * 100 / 2)) 
						+ (pUnits.flatTime / 2));
				largestTime = (float) Math.min(maxTime * pUnits.flatTime,
						Math.sqrt((bPosY + pUnits.maxHeight - 25) / (-WorldManager.gravity * 100 / 2)) 
						+ (pUnits.flatTime / 2));
			}
			randomTime = MathUtils.random(smallestTime, largestTime);
		}

		float newObsX = bPosX + pUnits.vel0X * randomTime;
		float newObsY = bPosY + (pUnits.vel0Y * randomTime + 
				((WorldManager.gravity * randomTime * randomTime) / 2)) * Dimensions.BOX_TO_WORLD;

		createBoxUnit(bDef, newObsX * Dimensions.WORLD_TO_BOX, newObsY * Dimensions.WORLD_TO_BOX,
				powerSelected, startingBody);
	}

	public void createNormalWayObstacle(World world, BodyDef bDef, Body startingBody){
		float bPosX = startingBody.getPosition().x * Dimensions.BOX_TO_WORLD;
		float bPosY = startingBody.getPosition().y * Dimensions.BOX_TO_WORLD;
		float randomTime, powerSelected;

		powerRange.clear();

		if (bPosY + 65 <= 635){
			for (float i = 1; i <= 6; i++){
				powerRange.add(i);
			}
		}
		else {
			for (float i = 1; i <= 6; i++){
				powerRange.add(i);
			}
		}

		powerSelected = powerRange.random();

		PrecalculatedUnits pUnits = wListener.getjPPrecalculations().get(powerSelected);
		randomTime = pUnits.flatTime;

		float newObsX = bPosX + pUnits.vel0X * randomTime;
		float newObsY = bPosY + (pUnits.vel0Y * randomTime + 
				((WorldManager.gravity * randomTime * randomTime) / 2)) * Dimensions.BOX_TO_WORLD;

		createBoxUnit(bDef, newObsX * Dimensions.WORLD_TO_BOX, newObsY * Dimensions.WORLD_TO_BOX,
				powerSelected, startingBody);
	}

	public void createSeparableDoubleWayObstacle(World world, BodyDef bDef, Body startingBody){
		float bPosX = startingBody.getPosition().x * Dimensions.BOX_TO_WORLD;
		float bPosY = startingBody.getPosition().y * Dimensions.BOX_TO_WORLD;
		float upperJP, lowerJP;
		float lowerT1, upperT1;
		float lowerY1, upperY1;
		float lowerX1;
		float otherUpperTime, smallestUpperT2;

		lowerJP = MathUtils.random(3) + 1;
		upperJP = MathUtils.random((int)lowerJP + 2, 6);

		PrecalculatedUnits lowerPUnits = wListener.getjPPrecalculations().get(lowerJP);

		float minHighJP = lowerJP + 2;
		while (upperJP >= minHighJP){
			PrecalculatedUnits upperPUnits = wListener.getjPPrecalculations().get(upperJP);

			lowerT1 = (float) Math.min((5f * lowerPUnits.flatTime) / 4, lowerPUnits.flatTime / 2 
					+ Math.sqrt((25 - bPosY - lowerPUnits.maxHeight) / (50 * WorldManager.gravity)));
			lowerX1 = bPosX + lowerPUnits.vel0X * lowerT1;
			upperT1 = (lowerPUnits.vel0X * lowerT1) / upperPUnits.vel0X;

			otherUpperTime = (float) ((bPosY + upperPUnits.maxHeight < 635) ? 0 : (upperPUnits.flatTime / 2) 
					+ Math.sqrt((635 - bPosY - upperPUnits.maxHeight) / (50 * WorldManager.gravity)));
			smallestUpperT2 = Math.max(3f / 4 * upperPUnits.flatTime, otherUpperTime);

			lowerY1 = bPosY + (lowerPUnits.vel0Y * lowerT1 + (WorldManager.gravity * getSquare(lowerT1)) / 2) * 
					Dimensions.BOX_TO_WORLD;
			upperY1 = bPosY + (upperPUnits.vel0Y * upperT1 + (WorldManager.gravity * getSquare(upperT1)) / 2) * 
					Dimensions.BOX_TO_WORLD;
			
			if (lowerX1 - bPosX >= smallestUpperT2 * upperPUnits.vel0X &&
					upperY1 - lowerY1 > 250){
				float upperY2 = bPosY + Dimensions.BOX_TO_WORLD * (upperPUnits.vel0Y * smallestUpperT2 
						+ (WorldManager.gravity * getSquare(smallestUpperT2)) / 2);
				float lowerT2 = smallestUpperT2 * upperPUnits.vel0X / lowerPUnits.vel0X;
				float lowerY2 = bPosY + Dimensions.BOX_TO_WORLD * (lowerPUnits.vel0Y * lowerT2 
						+ (WorldManager.gravity * getSquare(lowerT2)) / 2);

				if (upperY2 - lowerY2 >= 250){
					float randomUpperTime = MathUtils.random(smallestUpperT2, upperT1);
					float x = bPosX + randomUpperTime * upperPUnits.vel0X;
					float y2 = bPosY + Dimensions.BOX_TO_WORLD * (randomUpperTime * upperPUnits.vel0Y 
							+ (WorldManager.gravity * getSquare(randomUpperTime)) / 2);
					float t1 = randomUpperTime * upperPUnits.vel0X / lowerPUnits.vel0X;
					float y1 = bPosY + Dimensions.BOX_TO_WORLD * (t1 * lowerPUnits.vel0Y 
							+ (WorldManager.gravity * getSquare(t1)) / 2);

					createDoubleBoxUnit(bDef, x * Dimensions.WORLD_TO_BOX, y1 * Dimensions.WORLD_TO_BOX,
							x * Dimensions.WORLD_TO_BOX, y2 * Dimensions.WORLD_TO_BOX,
							upperJP, startingBody);
				}
				break;
			}
			else upperJP -= 1;
		}
	}
	
	public void createConvergingWayObstacle(World world, BodyDef bDef, Body startingBody1,
			Body startingBody2){
		float bPosX = startingBody1.getPosition().x * Dimensions.BOX_TO_WORLD;
		float bPosY1 = startingBody1.getPosition().y * Dimensions.BOX_TO_WORLD;
		float bPosY2 = startingBody2.getPosition().y * Dimensions.BOX_TO_WORLD;
		float lowerJP, upperJP;

//		Y1 = for higherJP
//		Y2 = for lowerJP
//		lowerJP here is the upper path --- (OPPOSITE)
		upperJP = MathUtils.random(3, 6);
		lowerJP = MathUtils.random(1, (int)upperJP - 2);
		
		PrecalculatedUnits upperPUnits = wListener.getjPPrecalculations().get(upperJP);
		float minLowerJP = upperJP - 2;
		while (lowerJP <= minLowerJP){
			PrecalculatedUnits lowerPUnits = wListener.getjPPrecalculations().get(lowerJP);
			float a = WorldManager.gravity * (getSquare(upperPUnits.vel0X) 
					- getSquare(lowerPUnits.vel0X)) / 2;
			float b = lowerPUnits.vel0X * (lowerPUnits.vel0Y * upperPUnits.vel0X 
					- lowerPUnits.vel0X * upperPUnits.vel0Y);
			float c = -getSquare(lowerPUnits.vel0X) * (bPosY1 - bPosY2) / 100;
			float t1 = solveRoot(a, b, c);
			float x = upperPUnits.vel0X * t1 + bPosX;
			
			if (x - bPosX > upperPUnits.vel0X * upperPUnits.flatTime * (1f / 2)){
				float y = bPosY1 + Dimensions.BOX_TO_WORLD * (upperPUnits.vel0Y * t1 
						+ (WorldManager.gravity * getSquare(t1)) / 2);
				createBoxUnit(bDef, x * Dimensions.WORLD_TO_BOX, y * Dimensions.WORLD_TO_BOX,
						lowerJP, startingBody2);
				createConvergingWay = false;
				break;
			}
			else lowerJP += 1;
		}
	}
	
	private float solveRoot(float a, float b, float c){
		return (float) ((-b - Math.sqrt(getSquare(b) - 4 * a * c)) / (2 * a));
	}

	private float getSquare(float num){
		return num * num;
	}

	private void createBoxUnit(BodyDef bDef, float x, float y, float jumpPower, Body startingBody){
		wListener.addArtificBody(startingBody, jumpPower);

		bDef.type = BodyType.StaticBody;
		bDef.position.set(new Vector2(x, y));

		precBody = world.createBody(bDef);
		precBody.setUserData(WorldConstants.B_TOUCHABLE);

		PolygonShape groundBox = new PolygonShape();  
		groundBox.setAsBox(25 * Dimensions.WORLD_TO_BOX, 25 * Dimensions.WORLD_TO_BOX);
		precBody.createFixture(groundBox, 0.0f); 
		groundBox.dispose();

		gScreen.getwManager().getBodyRecs().put(precBody, new Rectangle(x * Dimensions.BOX_TO_WORLD - 25,
				y * Dimensions.BOX_TO_WORLD - 25, 50, 50));
		worldBodies.add(precBody);
	}

	private void createDoubleBoxUnit(BodyDef bDef, float x1, float y1, float x2, float y2,
			float jumpPower, Body startingBody){
		wListener.addArtificBody(startingBody, jumpPower);

		bDef.type = BodyType.StaticBody;
		bDef.position.set(new Vector2(x1, y1));

		precBody  = world.createBody(bDef);
		precBody.setUserData(WorldConstants.B_TOUCHABLE);

		PolygonShape groundBox1 = new PolygonShape();  
		groundBox1.setAsBox(25 * Dimensions.WORLD_TO_BOX, 25 * Dimensions.WORLD_TO_BOX);
		precBody.createFixture(groundBox1, 0.0f); 
		groundBox1.dispose();

		gScreen.getwManager().getBodyRecs().put(precBody, new Rectangle(x1 * Dimensions.BOX_TO_WORLD - 25,
				y1 * Dimensions.BOX_TO_WORLD - 25, 50, 50));
		worldBodies.add(precBody);

		bDef.type = BodyType.StaticBody;
		bDef.position.set(new Vector2(x2, y2));

		sdwBody = world.createBody(bDef);
		sdwBody.setUserData(WorldConstants.B_TOUCHABLE);

		PolygonShape groundBox2 = new PolygonShape();  
		groundBox2.setAsBox(25 * Dimensions.WORLD_TO_BOX, 25 * Dimensions.WORLD_TO_BOX);
		sdwBody.createFixture(groundBox2, 0.0f); 
		groundBox2.dispose();

		gScreen.getwManager().getBodyRecs().put(sdwBody, new Rectangle(x2 * Dimensions.BOX_TO_WORLD - 25,
				y2 * Dimensions.BOX_TO_WORLD - 25, 50, 50));
		worldBodies.add(sdwBody);
		createConvergingWay = true;
	}

	public void removeWorldBodies(){
		Body firstBody = worldBodies.first();

		if (firstBody.getPosition().x * Dimensions.BOX_TO_WORLD <
				gScreen.getwManager().getSpring().getPosition().x * Dimensions.BOX_TO_WORLD - 200){
			gScreen.getwManager().getBodyRecs().removeKey(firstBody);
			worldBodies.removeIndex(0);
			world.destroyBody(firstBody);
		}
	}

	public Body getPrecBody() {
		return precBody;
	}

	public void setPrecBody(Body precBody) {
		this.precBody = precBody;
	}

	public Body getSdwBody() {
		return sdwBody;
	}

	public void setSdwBody(Body sdwBody) {
		this.sdwBody = sdwBody;
	}

	public boolean isCreateConvergingWay() {
		return createConvergingWay;
	}

	public void setCreateConvergingWay(boolean createConvergingWay) {
		this.createConvergingWay = createConvergingWay;
	}
}