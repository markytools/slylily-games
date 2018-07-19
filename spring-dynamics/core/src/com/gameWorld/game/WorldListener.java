package com.gameWorld.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.springdynamics.game.GameScreen;
import com.springDynamicsConstants.game.Dimensions;
import com.springDynamicsConstants.game.PrecalculatedUnits;
import com.springDynamicsConstants.game.WorldConstants;

public class WorldListener implements ContactListener {
	private GameScreen gScreen;

	//	1 ------ 12 power
	private float currentJumpPower;
	private float currentJumpTime;
	private float lastUsedJumpPower;
	private boolean changeTime;
	private boolean failedBounce;

	private Array<JumpPowerTester> jPTesters;

	private ArrayMap<Float, PrecalculatedUnits> jPPrecalculations;


	public WorldListener(GameScreen gScreen){
		this.gScreen = gScreen;

		currentJumpPower = 2;
		currentJumpTime = 1;
		changeTime = true;
		failedBounce = false;

		jPTesters = new Array<JumpPowerTester>();

		jPPrecalculations = new ArrayMap<Float, PrecalculatedUnits>();
		for (float i = 1; i <= 12; i++){
			float maxX = i * 50;
			float time = (1.1f * ((i - 1f) / 11)) + 1f;
			float vel0Y = (-WorldManager.gravity * time) / 2;

			float halfTime = time / 2;
			jPPrecalculations.put(i, new PrecalculatedUnits(i, maxX / time, vel0Y,
					(vel0Y * halfTime + (WorldManager.gravity * halfTime * halfTime) / 2) 
					* Dimensions.BOX_TO_WORLD, maxX, time));
		}
	}

	@Override
	public void beginContact(Contact contact) {
		if (!gScreen.isStartXMove()) gScreen.setStartXMove(true);
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
		if (bodyA.getUserData() == "Spring"){
			if (bodyB.getUserData() == WorldConstants.B_TOUCHABLE) applyJump(bodyA, bodyB);
			else applyLimitedJump(bodyA, bodyB);
		}
		else if (bodyB.getUserData() == "Spring"){
			if (bodyA.getUserData() == WorldConstants.B_TOUCHABLE) applyJump(bodyB, bodyA);
			else applyLimitedJump(bodyB, bodyA);
		}
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	private void applyJump(Body bodyA, Body bodyB){
		if (bodyA.getPosition().y * Dimensions.BOX_TO_WORLD - 15 >= bodyB.getPosition().y * Dimensions.BOX_TO_WORLD + 25){
//			updateJumpPower(bodyB);  
			lastUsedJumpPower = currentJumpPower;
			if (gScreen.getStartTime() == - 1) gScreen.setStartTime(TimeUtils.millis());
			else {
				long currentTime = TimeUtils.millis();
				gScreen.setStartTime(currentTime);
			}

			int randBoing = MathUtils.random(5) + 1;
			gScreen.game.assetM.get(gScreen.game.soundID.get("bSound" + String.valueOf(randBoing))).play();
			Vector2 springBodyPos = bodyA.getPosition();
			Vector2 obstaclePos = bodyB.getPosition();
			bodyA.setLinearVelocity((jumpVelX(getTargetDisplacementX(obstaclePos.x * Dimensions.BOX_TO_WORLD,
					springBodyPos.x * Dimensions.BOX_TO_WORLD) - 
					(springBodyPos.x * Dimensions.BOX_TO_WORLD))) * Dimensions.WORLD_TO_BOX, 
					jumpVelY());
		}
		else failedBounce = true;
	}

	private void applyLimitedJump(Body bodyA, Body bodyB){
		float springPos = bodyA.getPosition().y * Dimensions.BOX_TO_WORLD - 30;
		float boxPos = bodyB.getPosition().y * Dimensions.BOX_TO_WORLD + 25;

		if (springPos > boxPos && !bodyA.getUserData().equals(WorldConstants.UNCOLLIDABLE)) if (bodyA.getLinearVelocity().y <= 0) {
//			updateJumpPower(bodyB); 

			if (gScreen.getStartTime() == - 1) gScreen.setStartTime(TimeUtils.millis());
			else {
				long currentTime = TimeUtils.millis();
				gScreen.setStartTime(currentTime);
			}

			Vector2 springBodyPos = bodyA.getPosition();
			Vector2 obstaclePos = bodyB.getPosition();
			bodyA.setLinearVelocity((jumpVelX(getTargetDisplacementX(obstaclePos.x * Dimensions.BOX_TO_WORLD,
					springBodyPos.x * Dimensions.BOX_TO_WORLD) - 
					(springBodyPos.x * Dimensions.BOX_TO_WORLD))) * Dimensions.WORLD_TO_BOX, 
					jumpVelY());
		}
	}

	private float jumpVelX(float distX){
		return distX / jumpTime();
	}

	private float jumpVelY(){
		return (-WorldManager.gravity * jumpTime()) / 2;
	}

	private float jumpTime(){
		if (changeTime){
			changeTime = false;
			currentJumpTime = ((2.1f - 1f) * ((currentJumpPower - 1) / 11)) + 1f;
		}
		return currentJumpTime;
	}

	private float getDisplacementToTackleX(float jumpPower){
		return jumpPower * 50;
	}

	private float getTargetDisplacementX(float obstacleX, float currentXPos){
		float realDispX;
		float diff = currentXPos - obstacleX;
		float diffMult = diff / 25;

		if (diffMult > 0){
			realDispX = obstacleX + (MathUtils.floor(diffMult) * 25);
			if (diff % 25 >= 12.5) realDispX += 25;
		}
		else if (diffMult < 0){
			realDispX = obstacleX + (MathUtils.ceil(diffMult) * 25);
			if (diff % 25 >= 12.5) realDispX += 25;
		}
		else realDispX = currentXPos;

		return realDispX + getDisplacementToTackleX(currentJumpPower);
	}

	//	Method to enable or disable automatic setting of jump power.
//	private void updateJumpPower(Body collidedBody){
//		if (jPTesters.size != 0 && collidedBody == jPTesters.first().artificBody){
//			setCurrentJumpPower(jPTesters.first().jumpPower);
//			jPTesters.removeIndex(0);
//		}
//	}

	public void addArtificBody(Body artificBody, float jumpPower) {
		jPTesters.add(new JumpPowerTester(artificBody, jumpPower));
	}

	//	Call this method when changing the jump power!!!
	public void setCurrentJumpPower(float jPower){
		changeTime = true;
		currentJumpPower = jPower;
	}

	public ArrayMap<Float, PrecalculatedUnits> getjPPrecalculations() {
		return jPPrecalculations;
	}


	public void setjPPrecalculations(
			ArrayMap<Float, PrecalculatedUnits> jPPrecalculations) {
		this.jPPrecalculations = jPPrecalculations;
	}

	public float getCurrentJumpPower() {
		return currentJumpPower;
	}

	public float getLastUsedJumpPower() {
		return lastUsedJumpPower;
	}

	public boolean isFailedBounce() {
		return failedBounce;
	}

	private class JumpPowerTester{
		public JumpPowerTester(Body artificBody, float jumpPower) {
			super();
		}
	}
}
