package com.polyNGonsAssetPack.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.polyngons.game.PolyNGons;
import com.polyNgonConstants.game.PolyNGonsTextureName;

public class PolyNGonsPolyAsset {
	public Texture triAN, triAF,triAUF, triBN, triBF,triBUF, sqN, sqF, sqUF, recN, recF,
	recUF, rhomN, rhomF, rhomUF, trapAN, trapAF, trapAUF, trapBN, trapBF, trapBUF, paraAN,
	paraAF, paraAUF, paraBN, paraBF, paraBUF, rTrapAN, rTrapAF, rTrapAUF, rTrapBN, rTrapBF,
	rTrapBUF;
	public Texture oneTria, twoTria, oneSq, perpendSq, threeSq, turn, emptySq;
	public Texture polySpace;

	private PolyNGons game;
	private PolyNGonsSpritePool pSpritePool;
	
	private TextureParameter param;
	private AssetManager assetM;
	private String[] polyName, polyStateID, polyPathID, bordName;
	private byte currentPolyTexture, currentBordTexture;

	public PolyNGonsPolyAsset(PolyNGons game, TextureParameter param){
		this.game = game;
		this.param = param;
		assetM = game.assetM;
		pSpritePool = new PolyNGonsSpritePool(this);

		createStringID();
	}

	private void createStringID(){
		polyName = new String[11];
		polyName[0] = "triA";
		polyName[1] = "triB";
		polyName[2] = "rec";
		polyName[3] = "sq";
		polyName[4] = "rhom";
		polyName[5] = "trapA";
		polyName[6] = "trapB";
		polyName[7] = "paraA";
		polyName[8] = "paraB";
		polyName[9] = "rTrapA";
		polyName[10] = "rTrapB";

		polyStateID = new String[3];
		polyStateID[0] = "N";
		polyStateID[1] = "F";
		polyStateID[2] = "UF";

		polyPathID = new String[3];
		polyPathID[0] = "n";
		polyPathID[1] = "f";
		polyPathID[2] = "uF";

		bordName = new String[8];
		bordName[0] = "oneTria";
		bordName[1] = "twoTria";
		bordName[2] = "oneSq";
		bordName[3] = "perpendSq";
		bordName[4] = "threeSq";
		bordName[5] = "turn";
		bordName[6] = "emptySq";
		bordName[7] = "polySpace";
	}

	public void setPolyTextures(byte textureName){
		if (currentPolyTexture != textureName){
			currentPolyTexture = textureName;
			
			String texturePath = null;
			switch (textureName){
			case PolyNGonsTextureName.T_SIMPLE: texturePath = "Simple"; break;
			case PolyNGonsTextureName.T_GOLDEN: texturePath = "Golden"; break;
			case PolyNGonsTextureName.T_BRICK: texturePath = "Brick"; break;
			case PolyNGonsTextureName.T_METALLIC: texturePath = "Metallic"; break;
			case PolyNGonsTextureName.T_BLACKANDWHITE: texturePath = "Black-White"; break;
			case PolyNGonsTextureName.T_SCRAP: texturePath = "Scrap"; break;
			case PolyNGonsTextureName.T_FRAME: texturePath = "Frame"; break;
			case PolyNGonsTextureName.T_BUBBLES: texturePath = "Bubbles"; break;
			case PolyNGonsTextureName.T_MOSAIC: texturePath = "Mosaic"; break;
			case PolyNGonsTextureName.T_GEM: texturePath = "Gem"; break;
			case PolyNGonsTextureName.T_GRASSWEAVED: texturePath = "Grass Weaved"; break;
			case PolyNGonsTextureName.T_CLAY: texturePath = "Clay"; break;
			case PolyNGonsTextureName.T_BURNT: texturePath = "Burnt"; break;
			case PolyNGonsTextureName.T_WOOD: texturePath = "Wood"; break;
			case PolyNGonsTextureName.T_CHOCOLATE: texturePath = "Chocolate"; break;
			case PolyNGonsTextureName.T_TRIUMPHANT: texturePath = "Triumphant"; break;
			case PolyNGonsTextureName.T_GENIUS: texturePath = "Genius"; break;
			default: texturePath = ""; break;
			}

			for (int i = 0; i < polyName.length; i++){
				for (int i2 = 0; i2 < polyStateID.length; i2++){
					if (game.assetID.get(polyName[i] + polyStateID[i2]) != null){
						if (!game.assetID.get(polyName[i] + polyStateID[i2]).fileName.equals(
								"polygons/textures/" + texturePath + "/" + polyName[i] + "/" + polyPathID[i2] + ".png")){
							String assetToUnload = game.assetID.get(polyName[i] + polyStateID[i2]).fileName;
							game.assetID.put(polyName[i] + polyStateID[i2], new AssetDescriptor<Texture>(
									Gdx.files.internal("polygons/textures/" + texturePath +
											"/" + polyName[i] + "/" + polyPathID[i2] + ".png"),
											Texture.class, param));
							unloadAndLoadToManager(assetToUnload, game.assetID.get(polyName[i] + polyStateID[i2]));
						}
					}
					else {
						String assetToUnload = null;
						game.assetID.put(polyName[i] + polyStateID[i2], new AssetDescriptor<Texture>(
								Gdx.files.internal("polygons/textures/" + texturePath +
										"/" + polyName[i] + "/" + polyPathID[i2] + ".png"),
										Texture.class, param));
						unloadAndLoadToManager(assetToUnload, game.assetID.get(polyName[i] + polyStateID[i2]));
					}
				}
			}

			setVarPolyTexture();
		}
	}

	public void setBorderTextures(byte borderName){
		if (currentBordTexture != borderName){
			currentBordTexture = borderName;

			String borderPath = null;
			switch (borderName){
			case PolyNGonsTextureName.B_SIMPLE: borderPath = "Simple"; break;
			case PolyNGonsTextureName.B_CONCRETE: borderPath = "Concrete"; break;
			case PolyNGonsTextureName.B_DYED: borderPath = "Dyed"; break;
			case PolyNGonsTextureName.B_SNOW: borderPath = "Snow"; break;
			case PolyNGonsTextureName.B_KINETIC: borderPath = "Kinetic"; break;
			case PolyNGonsTextureName.B_GRASS: borderPath = "Nature"; break;
			case PolyNGonsTextureName.B_FIERY: borderPath = "Fiery"; break;
			case PolyNGonsTextureName.B_BUTTERFLY: borderPath = "Butterfly"; break;
			case PolyNGonsTextureName.B_TRIUMPHANT: borderPath = "Triumphant"; break;
			case PolyNGonsTextureName.B_GENIUS: borderPath = "Genius"; break;
			default: borderPath = ""; break;
			}

			for (int i = 0; i < bordName.length; i++){
				if (i < 6){
					if (game.assetID.get(bordName[i]) != null){
						if (!game.assetID.get(bordName[i]).fileName.equals(
								"borders/textures/" + borderPath + "/" + bordName[i] + "/" + "1.png")){
							String assetToUnload = game.assetID.get(bordName[i]).fileName;
							game.assetID.put(bordName[i], new AssetDescriptor<Texture>(
									Gdx.files.internal("borders/textures/" + borderPath + "/" + bordName[i] +
											"/" + "1.png"), Texture.class, param));
							unloadAndLoadToManager(assetToUnload, game.assetID.get(bordName[i]));
						}
					}
					else {
						String assetToUnload = null;
						game.assetID.put(bordName[i], new AssetDescriptor<Texture>(
								Gdx.files.internal("borders/textures/" + borderPath + "/" + bordName[i] +
										"/" + "1.png"), Texture.class, param));
						unloadAndLoadToManager(assetToUnload, game.assetID.get(bordName[i]));
					}
				}
				else {
					if (game.assetID.get(bordName[i]) != null){
						if (!game.assetID.get(bordName[i]).fileName.equals(
								"borders/textures/" + borderPath + "/" + bordName[i] + ".png")){
							String assetToUnload = game.assetID.get(bordName[i]).fileName;
							game.assetID.put(bordName[i], new AssetDescriptor<Texture>(
									Gdx.files.internal("borders/textures/" + borderPath + "/" +
											bordName[i] + ".png"), Texture.class, param));
							unloadAndLoadToManager(assetToUnload, game.assetID.get(bordName[i]));
						}
					}
					else {
						String assetToUnload = null;
						game.assetID.put(bordName[i], new AssetDescriptor<Texture>(
								Gdx.files.internal("borders/textures/" + borderPath + "/" +
										bordName[i] + ".png"), Texture.class, param));
						unloadAndLoadToManager(assetToUnload, game.assetID.get(bordName[i]));
					}
				}
			}

			setVarBordTexture();
		}
	}

	private void unloadAndLoadToManager(String assetToUnload,
			AssetDescriptor<Texture> assetToLoad){
		boolean unload = (assetToUnload != null && assetToUnload != "") ? true : false;
		if (unload) assetM.unload(assetToUnload);
		assetM.load(assetToLoad);
		assetM.finishLoading();
	}

	private void setVarPolyTexture(){
		triAN = game.assetM.get(game.assetID.get("triAN"));
		triAF = game.assetM.get(game.assetID.get("triAF"));
		triAUF = game.assetM.get(game.assetID.get("triAUF"));
		triBN = game.assetM.get(game.assetID.get("triBN"));
		triBF = game.assetM.get(game.assetID.get("triBF"));
		triBUF = game.assetM.get(game.assetID.get("triBUF"));
		sqN = game.assetM.get(game.assetID.get("sqN"));
		sqF = game.assetM.get(game.assetID.get("sqF"));
		sqUF = game.assetM.get(game.assetID.get("sqUF"));
		recN = game.assetM.get(game.assetID.get("recN"));
		recF = game.assetM.get(game.assetID.get("recF"));
		recUF = game.assetM.get(game.assetID.get("recUF"));
		rhomN = game.assetM.get(game.assetID.get("rhomN"));
		rhomF = game.assetM.get(game.assetID.get("rhomF"));
		rhomUF = game.assetM.get(game.assetID.get("rhomUF"));
		trapAN = game.assetM.get(game.assetID.get("trapAN"));
		trapAF = game.assetM.get(game.assetID.get("trapAF"));
		trapAUF = game.assetM.get(game.assetID.get("trapAUF"));
		trapBN = game.assetM.get(game.assetID.get("trapBN"));
		trapBF = game.assetM.get(game.assetID.get("trapBF"));
		trapBUF = game.assetM.get(game.assetID.get("trapBUF"));
		paraAN = game.assetM.get(game.assetID.get("paraAN"));
		paraAF = game.assetM.get(game.assetID.get("paraAF"));
		paraAUF = game.assetM.get(game.assetID.get("paraAUF"));
		paraBN = game.assetM.get(game.assetID.get("paraBN"));
		paraBF = game.assetM.get(game.assetID.get("paraBF"));
		paraBUF = game.assetM.get(game.assetID.get("paraBUF"));
		rTrapAN = game.assetM.get(game.assetID.get("rTrapAN"));
		rTrapAF = game.assetM.get(game.assetID.get("rTrapAF"));
		rTrapAUF = game.assetM.get(game.assetID.get("rTrapAUF"));
		rTrapBN = game.assetM.get(game.assetID.get("rTrapBN"));
		rTrapBF = game.assetM.get(game.assetID.get("rTrapBF"));
		rTrapBUF = game.assetM.get(game.assetID.get("rTrapBUF"));
	}
	
	private void setVarBordTexture(){
		oneTria = game.assetM.get(game.assetID.get("oneTria"));
		twoTria = game.assetM.get(game.assetID.get("twoTria"));
		oneSq = game.assetM.get(game.assetID.get("oneSq"));
		perpendSq = game.assetM.get(game.assetID.get("perpendSq"));
		threeSq = game.assetM.get(game.assetID.get("threeSq"));
		turn = game.assetM.get(game.assetID.get("turn"));
		emptySq = game.assetM.get(game.assetID.get("emptySq"));
		polySpace = game.assetM.get(game.assetID.get("polySpace"));
	}

	public PolyNGonsSpritePool getpSpritePool() {
		return pSpritePool;
	}
}
