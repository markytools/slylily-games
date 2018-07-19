package com.junkworld.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class TrashCanCollectionScreen implements Screen {
	protected JunkWorld game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture trashCan1, trashCan2, trashCan3, trashCan4, okButton;
	private TextureRegion trashCan1Region, trashCan2Region, trashCan3Region, trashCan4Region;
	private Rectangle trashCan1Layer, trashCan2Layer, trashCan3Layer, trashCan4Layer, okButtonLayer;
	private Actor selectTrashCan1, selectTrashCan2, selectTrashCan3, selectTrashCan4;
	private Stage stage;
	
	public TrashCanCollectionScreen (final JunkWorld game){
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 512, 800);
		batch = new SpriteBatch();
		
		trashCan1 = new Texture(Gdx.files.internal("gameScreenAssets/trashCan1/trashcanempty.png"));
		trashCan2 = new Texture(Gdx.files.internal("gameScreenAssets/trashCan2/trashcanempty.png"));
		trashCan3 = new Texture(Gdx.files.internal("gameScreenAssets/trashCan3/trashcanempty.png"));
		trashCan4 = new Texture(Gdx.files.internal("gameScreenAssets/trashCan4/trashcanempty.png"));
		okButton = new Texture(Gdx.files.internal("gameScreenAssets/backgroundImages/okButton.png"));
		trashCan1Region = new TextureRegion(trashCan1);
		trashCan2Region = new TextureRegion(trashCan2);
		trashCan3Region = new TextureRegion(trashCan3);
		trashCan4Region = new TextureRegion(trashCan4);
		
		trashCan1Layer = new Rectangle(64, 500, 128, 128);
		trashCan2Layer = new Rectangle(64 + 128, 500, 128, 128);
		trashCan3Layer = new Rectangle(64 + 256, 500, 128, 128);
		trashCan4Layer = new Rectangle(64, 500 - 128, 128, 128);
		okButtonLayer = new Rectangle(192, 200, 128, 50);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		stage.act();
		stage.draw();
		Gdx.input.setInputProcessor(stage);
		
		selectTrashCan1.setBounds(trashCan1Layer.x, trashCan1Layer.y, trashCan1Layer.width, trashCan1Layer.height);
		selectTrashCan2.setBounds(trashCan2Layer.x, trashCan2Layer.y, trashCan2Layer.width, trashCan2Layer.height);
		selectTrashCan3.setBounds(trashCan3Layer.x, trashCan3Layer.y, trashCan3Layer.width, trashCan3Layer.height);
		selectTrashCan4.setBounds(trashCan4Layer.x, trashCan4Layer.y, trashCan4Layer.width, trashCan4Layer.height);
		
		batch.begin();
		batch.draw(trashCan1Region, trashCan1Layer.x, trashCan1Layer.y);
		batch.draw(trashCan2Region, trashCan2Layer.x, trashCan2Layer.y);
		batch.draw(trashCan3Region, trashCan3Layer.x, trashCan3Layer.y);
		batch.draw(trashCan4Region, trashCan4Layer.x, trashCan4Layer.y);
		batch.draw(okButton, okButtonLayer.x, okButtonLayer.y);
		batch.end();
		
		disposabe();
	}
	
	private void disposabe() {
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
	public void hide() {
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
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
