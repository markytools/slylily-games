package com.gameTools.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.polyngons.game.PolyNGons;

public class CustomButtonLayout {
	private PolyNGons game;
	private BitmapFont selectBoxFont;
	private Texture texture2;
	
	public CustomButtonLayout(PolyNGons game){
		this.game = game;

		game.fontShader = new ShaderProgram(Gdx.files.internal("utilities/shaders/font.vert"),
				Gdx.files.internal("utilities/shaders/font.frag"));
		if (!game.fontShader.isCompiled()) {
		    Gdx.app.error("fontShader", "compilation failed:\n" + game.fontShader.getLog());
		}
		
		Texture texture = new Texture(Gdx.files.internal("utilities/fonts/myFont.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		game.setFont(new ShadedBitmapFont(Gdx.files.internal("utilities/fonts/myFont.fnt"),
				new TextureRegion(texture), false, game.fontShader, game.defaultShader));
		
		createMoreAppsButton();
	}
	
	public void createAdditionalFont(){
		if (selectBoxFont == null){
			if (texture2 == null){
				texture2 = new Texture(Gdx.files.internal("utilities/sbFont/font.png"), true);
				texture2.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.MipMapLinearNearest);
			}
			
			selectBoxFont = new BitmapFont(Gdx.files.internal("utilities/sbFont/font.fnt"),
					new TextureRegion(texture2));
		}
	}
	
	public void disposeAdditionalFont(){
		if (selectBoxFont != null){
			selectBoxFont.dispose();
		}
	}
	
	public ScalableFontButton createMoreAppsButton() {
		if (!game.assetM.isLoaded(game.assetID.get("bBig1").fileName)) game.assetM.load(game.assetID.get("bBig1"));
		if (!game.assetM.isLoaded(game.assetID.get("bBig2").fileName)) game.assetM.load(game.assetID.get("bBig2"));
		game.assetM.finishLoading();
		final ScalableFontButton moreAppsButton = createImageTextButton(game.assetM.get(game.assetID.get("bBig1")),
				game.assetM.get(game.assetID.get("bBig2")), "No Ads", 1.9f);
		moreAppsButton.setBounds(497, 170, 140, 70);
		moreAppsButton.setBigButton(true);
		moreAppsButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (game.pUpdater.noAds()){
					game.requestAd.showToast("Item already purchased.");
					game.pAdManager.disableAllAds();
				}
				else {
//					game.pNGonsPurchaser.purchaseNoAd();
				}
				super.clicked(event, x, y);
			}
		});
		return moreAppsButton;
	}
	
	public ScalableFontButton createRestoreButton() {
		if (!game.assetM.isLoaded(game.assetID.get("bBig1").fileName)) game.assetM.load(game.assetID.get("bBig1"));
		if (!game.assetM.isLoaded(game.assetID.get("bBig2").fileName)) game.assetM.load(game.assetID.get("bBig2"));
		game.assetM.finishLoading();
		final ScalableFontButton moreAppsButton = createImageTextButton(game.assetM.get(game.assetID.get("bBig1")),
				game.assetM.get(game.assetID.get("bBig2")), "Restore", 1.9f);
		moreAppsButton.setBounds(647, 170, 140, 70);
		moreAppsButton.setBigButton(true);
		moreAppsButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.pNGonsPurchaser.restoreAllPurchase();
				super.clicked(event, x, y);
			}
		});
		return moreAppsButton;
	}

	public ShadedBitmapFont getCustomFont(){
		return game.getFont();
	}
	
	/** Create standard game button button */
	public ScalableFontButton createImageTextButton(Texture imageUp, Texture imageDown,
			String text, float scale){
		
		ButtonStyle bStyle = new ButtonStyle();
		bStyle.up = new TextureRegionDrawable(new TextureRegion(imageUp));
		bStyle.down = new TextureRegionDrawable(new TextureRegion(imageDown));
		
		ScalableFontButton button = new ScalableFontButton(bStyle, getCustomFont(),
				scale, scale, text, game.fontShader);
		
		return button;
	}
	
	
	public void dispose(){
		
	}

	public BitmapFont getSelectBoxFont() {
		return selectBoxFont;
	}

	public void setSelectBoxFont(BitmapFont selectBoxFont) {
		this.selectBoxFont = selectBoxFont;
	}
}
