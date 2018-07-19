package com.tiltball.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TiltBallAssetsManager {
	protected SpriteBatch batch = new SpriteBatch();
	protected OrthographicCamera mainCam;
	protected AssetManager assetManager = new AssetManager();
	protected Vector3 touchPos = new Vector3();
	protected Stage stage;
	protected World world = new World(new Vector2(), true);
	protected BodyDef bDef = new BodyDef();
	protected FixtureDef fDef = new FixtureDef();
}
