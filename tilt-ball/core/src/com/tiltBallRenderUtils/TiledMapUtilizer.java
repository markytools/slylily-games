package com.tiltBallRenderUtils;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tiltball.game.GameWorld;
import com.tiltball.game.MyTiledMap;
import com.tiltball.game.TiltBallEngines;

public class TiledMapUtilizer extends GameWorld {

	protected Array<String> mapUnits;
	protected Random gen = new Random();
	private boolean newLastPos = false;
	private boolean destroyLastPos = false;

	public TiledMapUtilizer(){
		mapUnits = new Array<String>();
		mapUnits.add("AB");
		mapUnits.add("BC");
		mapUnits.add("CD");
		mapUnits.add("DE");
		mapUnits.add("EF");
		mapUnits.add("FG");
	}

	public void createNewTileMap(Array<TiledMapRenderer> mapRenderer, TiledMapAssetUtility mapAssetUtils, float border,
			float lastMapPos, int currentState, Vector2 mapMoveSpeed, World world, BodyDef bDef, FixtureDef fDef,
			Array<Body> boxBodies, Array<Fixture> boxFixtures){

		if (border + 8320 >= lastMapPos || border + 8320 + mapMoveSpeed.y > lastMapPos){

			MyTiledMap tiledMap;
			setNewLastPos(true);

			if (currentState == 1){
				if (lastMapPos == 832){
					String value = mapUnits.random();
					
					tiledMap = mapAssetUtils.createMyTiledMap(value);
					tiledMap.y = lastMapPos;

					mapRenderer.add(new TiledMapRenderer(tiledMap));

					TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");
					float tileSize = layer.getTileHeight();

					createWorldTiles(layer, tileSize, lastMapPos, world, bDef, fDef, boxBodies, boxFixtures);

				}
				else {
					String value = mapAssetUtils.getMapKey((MyTiledMap) mapRenderer.get(mapRenderer.size - 1).getMap());
					String randUnit1 = "";
					String randUnit2 = mapUnits.random();
					String mapUnit = String.valueOf(gen.nextInt(5) + 1);
					if (!value.contains("-")){
						while (value.equals(randUnit2)){
							randUnit2 = mapUnits.random();
						}
						randUnit1 = value;
					}
					else {
						while (value.substring(3, 5).equals(randUnit2)){
							randUnit2 = mapUnits.random();
						}
						randUnit1 = value.substring(3, 5);
					}
					tiledMap = mapAssetUtils.createMyTiledMap(randUnit1 + "-" + randUnit2 + " " + mapUnit);
					tiledMap.y = lastMapPos;

					mapRenderer.add(new TiledMapRenderer(tiledMap));

					TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");
					float tileSize = layer.getTileHeight();

					createWorldTiles(layer, tileSize, lastMapPos, world, bDef, fDef, boxBodies, boxFixtures);

				}
			}
		}
	}

	protected void createWorldTiles(TiledMapTileLayer layer, float tileSize, float lastMapPos, World world, BodyDef bDef, FixtureDef fDef,
			Array<Body> boxBodies, Array<Fixture> boxFixtures){
		PolygonShape tileShape = new PolygonShape();
		
		
		for (int row = 0; row < layer.getHeight(); row++){
			for (int col = 0; col < layer.getWidth(); col++){
				Cell cell = layer.getCell(col, row);

				if (cell == null) continue;
				if (cell.getTile() == null) continue;
				
				bDef.type = BodyType.StaticBody;
				bDef.position.set(((col + 0.5f) * tileSize) * WORLD_TO_BOX, (((row + 0.5f) * tileSize) + lastMapPos) * WORLD_TO_BOX);

				tileShape.setAsBox((tileSize / 2) * WORLD_TO_BOX, (tileSize / 2) * WORLD_TO_BOX);
				fDef.friction = 0;
				fDef.shape = tileShape;
				fDef.restitution = 0;
				fDef.isSensor = false;
				fDef.filter.categoryBits = TiltBallEngines.BOXES;

				Body boxBody = world.createBody(bDef);
				boxBodies.add(boxBody);
				
				boxFixtures.add(boxBody.createFixture(fDef));
			}
		}

	}
	
	public void destroyTileMap(Array<TiledMapRenderer> mapRenderer, float border,
			float lastMapPos, float destroyMapPos, World world, Array<Body> boxBodies, Array<Fixture> boxFixtures){
		if (border - 832 > destroyMapPos){
			destroyLastPos = true;
			TiledMapRenderer mr = mapRenderer.first();
			mapRenderer.removeValue(mr, true);
			mr.getMap().dispose();
			mr.dispose();
		}
	}

	public boolean isNewLastPos() {
		return newLastPos;
	}

	public void setNewLastPos(boolean newLastPos) {
		this.newLastPos = newLastPos;
	}

	public boolean isDestroyLastPos() {
		return destroyLastPos;
	}

	public void setDestroyLastPos(boolean destroyLastPos) {
		this.destroyLastPos = destroyLastPos;
	}
	
	public void restart(){
		newLastPos = false;
		destroyLastPos = false;
	}
	
	public void dispose(){
		mapUnits.clear();
		mapUnits = null;
		gen = null;
		newLastPos = false;
		destroyLastPos = false;
	}
}
