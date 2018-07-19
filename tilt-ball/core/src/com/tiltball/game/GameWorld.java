package com.tiltball.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameWorld {
	protected World world = new World(new Vector2(), true);
	protected BodyDef bDef = new BodyDef();
	protected Body player1Body;
	protected Fixture player1Fixture;
	protected FixtureDef fDef = new FixtureDef();
	protected Array<Body> boxBodies = new Array<Body>();
	protected Array<Fixture> boxFixtures = new Array<Fixture>();
	
	protected static final float WORLD_TO_BOX = 0.01f;
	protected static final float BOX_TO_WORLD = 100f;
	protected static final float TIME_STEP = 1/300f;
}