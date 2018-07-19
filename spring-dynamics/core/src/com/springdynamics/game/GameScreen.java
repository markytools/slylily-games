package com.springdynamics.game;

import com.assetsManager.game.SpringAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.gameUI.game.UIManager;
import com.gameWorld.game.WorldManager;
import com.springDynamicsConstants.game.Dimensions;
import com.springDynamicsConstants.game.GameState;
import com.springDynamicsConstants.game.WorldConstants;

public class GameScreen implements Screen {
	public SpringDynamics game;
	public OrthographicCamera gameCam;
	public Vector3 gameTouchPos;
	public SpriteBatch gameBatch;
	
	public GameState gState;
	
	private UIManager uiManager;
	private WorldManager wManager;
	private float accumulator = 0, rVelocity;
	private SpringAnimation spAnim;
	
	private Texture boxSprite;
	private boolean startXMove = false;
	private Rectangle springRec;
	
	private long startTime = -1;
	
	private int currentDist;
	
	public GameScreen(SpringDynamics game){
		this.game = game;

		gameCam = new OrthographicCamera();
		gameCam.setToOrtho(false, 800, 500);
		gameTouchPos = new Vector3();
		gameBatch = new SpriteBatch();
		manageGScreenAssets();

		wManager = new WorldManager(this);
		uiManager = new UIManager(this);

		boolean isWebGL = Gdx.app.getType() == ApplicationType.WebGL;
		boxSprite = new Texture(Gdx.files.internal("box2.png"), !isWebGL);
		if (!isWebGL) boxSprite.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.MipMapLinearNearest);
	}

	private void manageGScreenAssets() {
		game.assetLoader.loadGameScreenAssets();
		game.assetM.finishLoading();
		
		spAnim = new SpringAnimation(this);
	}

	@Override
	public void render(float delta) {
		updateGameCam();
		updateGameMechanics();
		renderDrawings();
		setGameListeners();
		doPhysicsStep(delta);

		wManager.getObsManager().removeWorldBodies();
	}

	private void renderDrawings() {
		uiManager.renderSky();
		
		gameBatch.begin();
		drawSpring(gameBatch);
		for (Body box : wManager.getWorldBodies()){
			if (box.getUserData() == WorldConstants.B_TOUCHABLE)
				gameBatch.draw(boxSprite, box.getPosition().x * Dimensions.BOX_TO_WORLD - 25,
						box.getPosition().y * Dimensions.BOX_TO_WORLD - 25, 50, 50);
			else gameBatch.draw(boxSprite, box.getPosition().x * Dimensions.BOX_TO_WORLD - 25,
					box.getPosition().y * Dimensions.BOX_TO_WORLD - 25, 50, 50);
		}
		gameBatch.end();
		wManager.actWorld();
		
		uiManager.renderUI();
	}

	private void updateGameMechanics() {
		currentDist = (int) ((wManager.getSpring().getPosition().x * Dimensions.BOX_TO_WORLD - 25) / 50);
		uiManager.getgOver().checkIfGameOver();
	}
	
	private void drawSpring(SpriteBatch batch) {
		spAnim.renderLeSpring(gameBatch);
	}

	private void updateGameCam(){
		gameTouchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		gameCam.unproject(gameTouchPos);

		gameCam.position.x = wManager.getSpring().getPosition().x * Dimensions.BOX_TO_WORLD + 300;
		gameCam.position.y = Math.max(250, wManager.getSpring().getPosition().y * Dimensions.BOX_TO_WORLD - 150);
		gameCam.update();
		gameBatch.setProjectionMatrix(gameCam.combined);
	}

	private void setGameListeners() {
		uiManager.setUIListeners();
	}

	private void doPhysicsStep(float deltaTime) {
	    // fixed time step
	    // max frame time to avoid spiral of death (on slow devices)
	    float frameTime = Math.min(deltaTime, 0.25f);
	    accumulator += frameTime;
	    while (accumulator >= WorldConstants.TIME_STEP) {
	    	wManager.world.step(WorldConstants.TIME_STEP, WorldConstants.VELOCITY_ITERATIONS, WorldConstants.POSITION_ITERATIONS);
	        accumulator -= WorldConstants.TIME_STEP;
	    }
	}
	
	public WorldManager getwManager() {
		return wManager;
	}

	public float getrVelocity() {
		return rVelocity;
	}

	public void setrVelocity(float rVelocity) {
		this.rVelocity = rVelocity;
	}

	public boolean isStartXMove() {
		return startXMove;
	}

	public void setStartXMove(boolean startXMove) {
		this.startXMove = startXMove;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Rectangle getSpringRec() {
		return springRec;
	}

	public int getCurrentDist() {
		return currentDist;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		uiManager.dispose();
		wManager.dispose();
		spAnim.dispose();
		boxSprite.dispose();
		
		for (int i = 1; i <= 6; i++) game.assetM.unload(game.soundID.get("bSound" + i).fileName);
		game.assetM.unload(game.soundID.get("gOverSound").fileName);
	}

}
