package com.gameWorld.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.gameObstacles.game.ObstacleManager;
import com.springdynamics.game.GameScreen;
import com.springDynamicsConstants.game.Dimensions;
import com.springDynamicsConstants.game.WorldConstants;

public class WorldManager {
	public World world;
	public GameScreen gScreen;
	
	private Array<Body> worldBodies;
	private WorldListener wListener;
	private Body spring, ground;
	private boolean disableSDW = true;
	
	private ObstacleManager obsManager;
	
	private Body precBody;
	private BodyDef bDef;
	private ArrayMap<Body, Rectangle> bodyRecs;
	
	public static float gravity = -6.81f;
	
	public WorldManager(GameScreen gScreen){
		this.gScreen = gScreen;
		world = new World(new Vector2(0, gravity), false);
		setWorldBodies(new Array<Body>());
		worldBodies = new Array<Body>();
		bodyRecs = new ArrayMap<Body, Rectangle>();
		
		createWorldObjects();
		createObstacles();
	}

	private void createWorldObjects() {
		
		bDef = new BodyDef();
		bDef.type = BodyType.DynamicBody;
		
		bDef.position.set((25) * Dimensions.WORLD_TO_BOX, (200 + 20) * Dimensions.WORLD_TO_BOX);

		spring = world.createBody(bDef);
		spring.setUserData("Spring");

		PolygonShape spriteShape = new PolygonShape();
		spriteShape.setAsBox(6.5f * Dimensions.WORLD_TO_BOX, 40 * Dimensions.WORLD_TO_BOX);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = spriteShape;
		fixtureDef.friction = 0f;
		fixtureDef.density = 0f;
		fixtureDef.restitution = 0;

		spring.createFixture(fixtureDef);
		spriteShape.dispose();

		for (int i = 1; i <= 15; i++){
			createTouchableBoxUnitBody(bDef, (i * 50 - 25) * Dimensions.WORLD_TO_BOX, 100 * Dimensions.WORLD_TO_BOX);
		}
		
//		bDef.type = BodyType.KinematicBody;
//		bDef.position.set(500 * Dimensions.WORLD_TO_BOX, 150 * Dimensions.WORLD_TO_BOX);
//
//		PolygonShape movingGround = new PolygonShape();  
//		movingGround.setAsBox(100 * Dimensions.WORLD_TO_BOX, 10 * Dimensions.WORLD_TO_BOX);
//		movingBody = world.createBody(bDef);
//		movingBody.createFixture(movingGround, 0);
//		movingGround.dispose();
//		
//		movingBody.setLinearVelocity(-100 * Dimensions.WORLD_TO_BOX, 0);
		
		world.setContactListener(wListener = new WorldListener(gScreen));
	}
	
	private void createObstacles() {
		obsManager = new ObstacleManager(gScreen, wListener, world, worldBodies);
	}

	public void actWorld(){
		if (spring.getPosition().x * Dimensions.BOX_TO_WORLD >= 200 && spring.getLinearVelocity().x == 0 ||
				spring.getPosition().y * Dimensions.BOX_TO_WORLD <= 0){
			spring.setUserData(WorldConstants.UNCOLLIDABLE);
			if (spring.getLinearVelocity().y * Dimensions.BOX_TO_WORLD > 0){
				spring.setLinearVelocity(spring.getLinearVelocity().x, 0);
			}
		}
		
		if (precBody != null && spring.getPosition().x * Dimensions.BOX_TO_WORLD >= 
				precBody.getPosition().x * Dimensions.BOX_TO_WORLD - 800){
			int randomObsCreation;
			if (disableSDW){
				disableSDW = false;
				randomObsCreation = 1;
			}
			else randomObsCreation = MathUtils.random(2);
			if (obsManager.isCreateConvergingWay()){
				obsManager.createConvergingWayObstacle(world, bDef, precBody, obsManager.getSdwBody());
			}
			else {
				if (randomObsCreation == 1) obsManager.createSingleWayObstacle(world, bDef, precBody);
				else if (randomObsCreation == 2) obsManager.createNormalWayObstacle(world, bDef, precBody);
				else obsManager.createSeparableDoubleWayObstacle(world, bDef, precBody);
			}
			this.precBody = obsManager.getPrecBody();
		}
	}
	
	private void createTouchableBoxUnitBody(BodyDef bDef, float x, float y){
		bDef.type = BodyType.StaticBody;
		bDef.position.set(new Vector2(x, y));  

		precBody = world.createBody(bDef);
		precBody.setUserData(WorldConstants.B_TOUCHABLE);

		PolygonShape groundBox = new PolygonShape();  
		groundBox.setAsBox(25 * Dimensions.WORLD_TO_BOX, 25 * Dimensions.WORLD_TO_BOX);
		precBody.createFixture(groundBox, 0.0f); 
		groundBox.dispose();

		bodyRecs.put(precBody, new Rectangle(x * Dimensions.BOX_TO_WORLD - 25, y * Dimensions.BOX_TO_WORLD - 25, 50, 50));
		worldBodies.add(precBody);
	}
	
	public Array<Body> getWorldBodies() {
		return worldBodies;
	}

	public void setWorldBodies(Array<Body> worldBodies) {
		this.worldBodies = worldBodies;
	}

	public Body getSpring() {
		return spring;
	}

	public Body getGround() {
		return ground;
	}

	public WorldListener getwListener() {
		return wListener;
	}

	public ObstacleManager getObsManager() {
		return obsManager;
	}

	public ArrayMap<Body, Rectangle> getBodyRecs() {
		return bodyRecs;
	}
	
	public void dispose(){
		world.clearForces();
		world.dispose();
	}
}
