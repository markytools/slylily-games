package com.tiltBallRenderUtils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.tiltball.game.MyTiledMap;

public class TiledMapAssetUtility {
	protected Map<String, TiledMap> tiledMaps = new HashMap<String, TiledMap>();
	protected Map<String, MyTiledMap> myTiledMaps = new HashMap<String, MyTiledMap>();
	protected final String[] mapUnits = {"AB", "BC", "CD", "DE", "EF", "FG"};
	protected AssetManager manager;
	protected int currentPlayerNum;
	protected int count = 0;
	
	public TiledMapAssetUtility(int currentState){
		this.manager = new AssetManager();
		this.currentPlayerNum = currentState;
		
		this.manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		loadTiledMaps(manager);
		this.manager.finishLoading();
		createTiledMapUnits(manager);
	}

	public void loadTiledMaps(AssetManager manager){
		for (int unit1 = 0; unit1 < 6; unit1++){
			if (currentPlayerNum == 1) manager.load(currentPlayerNum + " Player/" + mapUnits[unit1] + ".tmx", TiledMap.class);
			for (int unit2 = 0; unit2 < 6; unit2++){
				if (unit1 == unit2) continue;
				for (int mapNum = 1; mapNum <= 5; mapNum++){
					manager.load(currentPlayerNum + " Player/" + mapUnits[unit1] + "-" + mapUnits[unit2] + "/" + mapNum + ".tmx", TiledMap.class);
				}
			}
		}
	}

	public void createTiledMapUnits(AssetManager manager){
		for (int unit1 = 0; unit1 < 6; unit1++){
			if (currentPlayerNum == 1) {
				tiledMaps.put(mapUnits[unit1], 
					manager.get(currentPlayerNum + " Player/" + mapUnits[unit1] + ".tmx", TiledMap.class));
				
			}
			for (int unit2 = 0; unit2 < 6; unit2++){
				if (unit1 == unit2) continue;
				for (int mapNum = 1; mapNum <= 5; mapNum++){
					tiledMaps.put(mapUnits[unit1] + "-" + mapUnits[unit2] + " " + mapNum, 
							manager.get(currentPlayerNum + " Player/" + mapUnits[unit1] + "-" + mapUnits[unit2] + "/" + mapNum + ".tmx", TiledMap.class));
				}
			}
		}
	}
	
	public MyTiledMap createMyTiledMap(String key){
		
		TiledMap tiledMap = tiledMaps.get(key);

		MyTiledMap map = new MyTiledMap();
		map.getLayers().add(tiledMap.getLayers().get(0));
		map.getTileSets().addTileSet(tiledMap.getTileSets().getTileSet(0));
		
		myTiledMaps.put(key, map);
		
		return map;
	}
	
	public String getMapKey(MyTiledMap tiledMap){
		for (String key : myTiledMaps.keySet()){
			if (tiledMap.equals(myTiledMaps.get(key))) return key;
		}
		return null;
	}
	
	public AssetManager getManager() { 
		return manager;
	}
	
	public void restart(){
		count = 0;
	}
	
	public void dispose(){
		for (String key : tiledMaps.keySet()){
			if (tiledMaps.get(key) != null) tiledMaps.get(key).dispose();
		}
		tiledMaps.clear();
		tiledMaps = null;
		
		for (String key : myTiledMaps.keySet()){
			if (myTiledMaps.get(key) != null) myTiledMaps.get(key).dispose();
		}
		myTiledMaps.clear();
		myTiledMaps = null;
		manager.dispose();
		manager = null;
		count = 0;
	}

}