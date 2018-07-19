package com.gameUI.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.springdynamics.game.GameScreen;
import com.springdynamics.game.MainMenuScreen;
import com.springdynamics.game.SpringDynamics;
import com.springDynamicsConstants.game.GameState;

public class InGameMenu {
	public enum InGameMenuState {
		
	}
	
	private GameScreen gScreen;
	private SpringDynamics game;
	private Button menu, back, quit, yes, no;
	private Stage stage;

	private int currentToggle;
	private final int NO_TOGGLE = 0;
	private final int NORMAL_TOGGLE = 1;
	private final int QUIT_TOGGLE = 2;

	public InGameMenu(GameScreen gScreen){
		this.gScreen = gScreen;
		game = gScreen.game;

		currentToggle = NO_TOGGLE;
		createInGameMenu();
	}

	private void createInGameMenu() {
		ButtonStyle style1 = new ButtonStyle();
		style1.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("menu1"))));
		style1.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("menu2"))));
		menu = new Button(style1);
		menu.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				currentToggle = NORMAL_TOGGLE;
				gScreen.gState = GameState.IN_GAME_MENU;
				super.clicked(event, x, y);
			}
		});
		menu.setBounds(5, 450, 150, 40);

		ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("back1"))));
		style2.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("back2"))));
		back = new Button(style2);
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				currentToggle = NO_TOGGLE;
				gScreen.gState = GameState.IN_GAME;
				menu.setTouchable(Touchable.enabled);
				yes.setTouchable(Touchable.disabled);
				no.setTouchable(Touchable.disabled);
				back.setTouchable(Touchable.disabled);
				quit.setTouchable(Touchable.disabled);
				super.clicked(event, x, y);
			}
		});
		back.setBounds(250, 230, 300, 50);

		ButtonStyle style3 = new ButtonStyle();
		style3.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("quit1"))));
		style3.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("quit2"))));
		quit = new Button(style3);
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("yes");
				currentToggle = QUIT_TOGGLE;
				super.clicked(event, x, y);
			}
		});
		quit.setBounds(250, 160, 300, 50);

		ButtonStyle style4 = new ButtonStyle();
		style4.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("yes1"))));
		style4.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("yes2"))));
		yes = new Button(style4);
		yes.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainMenuScreen(game));
				gScreen.dispose();
				super.clicked(event, x, y);
			}
		});
		yes.setBounds(250, 230, 300, 50);

		ButtonStyle style5 = new ButtonStyle();
		style5.up = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("no1"))));
		style5.down = new TextureRegionDrawable(new TextureRegion(game.assetM.get(game.assetID.get("no2"))));
		no = new Button(style5);
		no.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				currentToggle = NORMAL_TOGGLE;
				super.clicked(event, x, y);
			}
		});
		no.setBounds(250, 160, 300, 50);

		stage = new Stage();
		stage.addActor(menu);
		stage.addActor(quit);
		stage.addActor(back);
		stage.addActor(yes);
		stage.addActor(no);
	}

	public void renderInGameMenu(boolean disable){
		menu.draw(game.uiBatch, 1);
		if (!disable){
			switch (currentToggle){
			case NO_TOGGLE: {
				menu.setTouchable(Touchable.enabled);
				yes.setTouchable(Touchable.disabled);
				no.setTouchable(Touchable.disabled);
				back.setTouchable(Touchable.disabled);
				quit.setTouchable(Touchable.disabled);
			}; break;
			case NORMAL_TOGGLE: {
				menu.setTouchable(Touchable.disabled);
				yes.setTouchable(Touchable.disabled);
				no.setTouchable(Touchable.disabled);
				back.setTouchable(Touchable.enabled);
				quit.setTouchable(Touchable.enabled);
				game.uiBatch.draw(game.assetM.get(game.assetID.get("InGameMenuBG")), 200, 50);
				game.uiBatch.draw(game.assetM.get(game.assetID.get("menuTitle")), 230, 350);
				back.draw(game.uiBatch, 1);
				quit.draw(game.uiBatch, 1);
			}; break;
			case QUIT_TOGGLE: {
				menu.setTouchable(Touchable.disabled);
				back.setTouchable(Touchable.disabled);
				quit.setTouchable(Touchable.disabled);
				yes.setTouchable(Touchable.enabled);
				no.setTouchable(Touchable.enabled);
				game.uiBatch.draw(game.assetM.get(game.assetID.get("InGameMenuBG")), 200, 50);
				yes.draw(game.uiBatch, 1);
				no.draw(game.uiBatch, 1);
				game.fontManager.getFont().draw(game.uiBatch, "Are you sure you want to quit?", 223, 410, 350, Align.center, true);
			}; break;
			default: break;
			}
		}
	}

	public void setListeners(){
		Gdx.input.setInputProcessor(stage);
		stage.act();
		stage.getViewport().setCamera(game.uiCam);
	}

	public int getCurrentToggle() {
		return currentToggle;
	}
	
	public void dispose(){
		game.assetM.unload(game.assetID.get("menu1").fileName);
		game.assetM.unload(game.assetID.get("menu2").fileName);
		game.assetM.unload(game.assetID.get("back1").fileName);
		game.assetM.unload(game.assetID.get("yes1").fileName);
		game.assetM.unload(game.assetID.get("yes2").fileName);
		game.assetM.unload(game.assetID.get("no1").fileName);
		game.assetM.unload(game.assetID.get("no2").fileName);
		game.assetM.unload(game.assetID.get("InGameMenuBG").fileName);
		stage.dispose();
	}
}
