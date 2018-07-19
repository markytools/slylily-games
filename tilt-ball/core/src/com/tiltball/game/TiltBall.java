package com.tiltball.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.handlers.MyAdActivity;
import com.handlers.TiltBallPurchaser;
import com.tiltBallScreens.LoadingScreen;

public class TiltBall extends Game {
	private MyAdActivity myAdRequest;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private Vector3 touchPos;
	private Stage stage;
	private Texture brownTrans;
	
	public TiltBallPurchaser tBPurchaser;
//	public AdTimer adTimer;
	public final MyAdActivity adActivity;
	
	public TiltBall(MyAdActivity adActivity){
		this.adActivity = adActivity;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 512, 832);
		touchPos = new Vector3();
		stage = new Stage();
		
		brownTrans = new Texture(Gdx.files.internal("brownTrans.png"));
		
//		adTimer = new AdTimer(this);
//		tBPurchaser = new TiltBallPurchaser(this);
				
		this.setScreen(new LoadingScreen(this, myAdRequest));
	}

	@Override
	public void render () {
		setTouchProjection();
		super.render();
	}
	
	private void setTouchProjection() {
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.update();
		cam.unproject(touchPos);
		batch.setProjectionMatrix(cam.combined);
		
		stage.getViewport().setCamera(cam);
	}

	public SpriteBatch getBatch(){
		return batch;
	}
	
	public OrthographicCamera getCam(){
		return cam;
	}
	
	public Vector3 getTouchPos(){
		return touchPos;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public Texture getBrownTrans(){
		return brownTrans;
	}

	@Override
	public void dispose() {
//		PurchaseSystem.dispose();
		super.dispose();
	}
}
