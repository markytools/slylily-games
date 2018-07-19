package com.polyNGonsUI.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ArrayMap;
import com.polyngons.game.GameScreen;

public class PTextureReference {

	private Texture pRegFixed;
	private Texture pRegFixable;
	private Texture pRegUnfixable;
	private int position;
	private boolean revearsed;
	private byte pName;
	private float polyH, polyW;
	private Pixmap pixmap;
	private GameScreen gScreen;
	
	public PTextureReference(){
		
	}

	public PTextureReference(GameScreen gScreen, int position, boolean revearsed, byte pName, float polyW, float polyH) {
		this.gScreen = gScreen;
		this.position = position;
		this.revearsed = revearsed;
		this.pName = pName;
		this.polyW = polyW;
		this.polyH = polyH;
	}
	
	private void polyActor(){
		ArrayMap<String, Texture> textStringMap = gScreen.getGSpCreator().getTextStringMap();
		Pixmap normalPixmap = new Pixmap(gScreen.game.assetID.get(textStringMap.getKey(pRegFixed, true)).file);
		this.pixmap = rotatePixmap(normalPixmap);
		normalPixmap.dispose();
	}
	
	public Pixmap rotatePixmap (Pixmap pm){
		Pixmap rotated = null;
		switch (position){
		case 1:{
			int width = pm.getWidth();
			int height = pm.getHeight();
			rotated = new Pixmap(width, height, pm.getFormat());
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++){
					int pmX = x;
					int pmY = y;
					if (!revearsed) rotated.drawPixel(pmX, pmY, pm.getPixel(x, y));
					else rotated.drawPixel(pmX, pmY, pm.getPixel((width - 1) - x, y));
				}
		}; break;
		case 2:{
			int width = pm.getHeight();
			int height = pm.getWidth();
			rotated = new Pixmap(width, height, pm.getFormat());
			for (int x = 0; x < height; x++)
				for (int y = 0; y < width; y++){
					int pmX = (width - 1) - y;
					int pmY = x;
					if (!revearsed) rotated.drawPixel(pmX, pmY, pm.getPixel(x, y));
					else rotated.drawPixel(pmX, pmY, pm.getPixel(x, (width - 1) - y));
				}
		}; break;
		case 3:{
			int width = pm.getWidth();
			int height = pm.getHeight();
			rotated = new Pixmap(width, height, pm.getFormat());
			for (int x = 0; x < width; x++)
				for (int y = 0; y < height; y++){
					int pmX = (width - 1) - x;
					int pmY = (height - 1) - y;
					if (!revearsed) rotated.drawPixel(pmX, pmY, pm.getPixel(x, y));
					else rotated.drawPixel(pmX, pmY, pm.getPixel((width - 1) - x, y));
				}
		}; break;
		case 4:{
			int width = pm.getHeight();
			int height = pm.getWidth();
			rotated = new Pixmap(width, height, pm.getFormat());
			for (int x = 0; x < height; x++)
				for (int y = 0; y < width; y++){
					int pmX = y;
					int pmY = (height - 1) - x;
					if (!revearsed) rotated.drawPixel(pmX, pmY, pm.getPixel(x, y));
					else rotated.drawPixel(pmX, pmY, pm.getPixel(x, (width - 1) - y));
				}
		}; break;
		default: break;
		}
		return rotated;
	}

	public Texture getpRegFixed() {
		return pRegFixed;
	}

	public void setpRegFixed(Texture pRegFixed) {
		this.pRegFixed = pRegFixed;
		polyActor();
	}

	public Texture getpRegFixable() {
		return pRegFixable;
	}

	public void setpRegFixable(Texture pRegFixable) {
		this.pRegFixable = pRegFixable;
	}

	public Texture getpRegUnfixable() {
		return pRegUnfixable;
	}

	public void setpRegUnfixable(Texture pRegUnfixable) {
		this.pRegUnfixable = pRegUnfixable;
	}

	public int getPosition() {
		return position;
	}
	public boolean isRevearsed() {
		return revearsed;
	}
	public byte getpName() {
		return pName;
	}

	public float getPolyH() {
		return polyH;
	}
	public float getPolyW() {
		return polyW;
	}

	public Pixmap getPixmap() {
		return pixmap;
	}
	
	public void dispose(){
		pixmap.dispose();
	}
}
