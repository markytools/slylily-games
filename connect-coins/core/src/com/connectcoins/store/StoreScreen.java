package com.connectcoins.store;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.assets.AssetScreenSpace;
import com.connectcoins.coins.CoinAssets.CoinCurrency;
import com.connectcoins.functions.GameModeConfig.Boosters;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.languages.LanguageManager;
import com.connectcoins.mainMenu.MainMenuScreen;
import com.connectcoins.mainMenu.MainMenuScreen.MainMenuScreenState;
import com.connectcoins.store.ItemButton.ItemButtonType;
import com.connectcoins.store.ItemButton.ItemTypeState;
import com.connectcoins.utils.AnimatedButton;
import com.connectcoins.utils.ButtonAssets;
import com.connectcoins.utils.ScalableFontButton.Size;

public class StoreScreen implements Disposable {
	public enum StoreScreenState {
		NORMAL
	}

//	public enum ItemCategory {
//		UNLOCKABLES = 1, BOOSTERS = 2, TEXTURES = 3
//	}
	private final int UNLOCKABLES = 1, BOOSTERS = 2, TEXTURES = 3;

	private PurchaseSoundPlayer purchaseSoundPlayer;
	private ConnectCoins game;
	private Texture ccSymbol;
	private TextureRegion buyButtonPlace, itemsOuterFrame, descFrame, title;
	private boolean renderOuterLayout = false;
	private int currentAmount = 0;
	private ButtonGroup<Button> upperButtons;
	private Button amountLeftB, amountRightB;
	private ButtonGroup<ItemButton> unlockablesGroup, boostersGroup, texturesGroup;
	private ScrollPane unlockablesPane, boostersPane, texturesPane;
	private long totalCCCollected = 0;
	private BoosterItemButton currentBoosterItemButton;
	private int selectedItemCatagory;
	private Array<ItemButton> coinTextureButtons;
	private Array<BoosterItemButton> boosterItemButtons;

	private ItemConstants itemConstants = new ItemConstants();
	private Array<Actor> actorsToUnload;

	public StoreScreen(ConnectCoins game, final MainMenuScreen mMScreen, PurchaseSoundPlayer purchaseSoundPlayer){
		this.game = game;
		this.purchaseSoundPlayer = purchaseSoundPlayer;
		totalCCCollected = game.pUpdater.getTotalCC();
		selectedItemCatagory = game.pUpdater.getSelectedItemCategory();
		buyButtonPlace = new TextureRegion(game.assetLoader.getTexture("buyButtonPlace"));
		ccSymbol = game.assetLoader.getTexture("ccSymbol");
		coinTextureButtons = new Array<ItemButton>();
		boosterItemButtons = new Array<BoosterItemButton>();
		actorsToUnload = new Array<Actor>();

		ButtonStyle style2 = new ButtonStyle();
		style2.up = new TextureRegionDrawable(new TextureRegion(ButtonAssets.averageButton.get(0)));
		style2.down = new TextureRegionDrawable(new TextureRegion(ButtonAssets.averageButton.get(0)));
		AnimatedButton backButton = new AnimatedButton(ButtonAssets.averageButton, style2, game.fManager.largeFont, 1.1f, 1.1f,
				"Back", Color.valueOf("281500"), Size.AVERAGE, LanguageManager.button_CURRENT, LanguageManager.currentLanguage, true,
				true) {
			@Override
			public void onAnimationEnd() {
				unload();
				setRenderOuterLayout(false);
				mMScreen.setStageDrawings(MainMenuScreenState.NORMAL);
				mMScreen.mMSState = MainMenuScreenState.NORMAL;
			}
		};
		backButton.setBounds(20, 20, 500, 100);
		backButton.setUserObject(StoreScreenState.NORMAL);
		backButton.setButtonIcons(game.buttonIconAssetManager, "back");

		game.stage.addActor(backButton);
	}

	public void initStoreAssets() {
		TextureAtlas storeAssetsAtlas = game.assetLoader.getTextureAtlas("storeAssetsAtlas");
		itemsOuterFrame = storeAssetsAtlas.findRegion("itemsOuterFrame");
		descFrame = storeAssetsAtlas.findRegion("descFrame");
		title = storeAssetsAtlas.findRegion("connectCoinStore");

		ButtonStyle itemBStyle = new ButtonStyle();
		itemBStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("itemButtonLayout1")));
		itemBStyle.checked = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("itemButtonLayout2")));
		itemBStyle.disabled = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("itemButtonLayout3")));

		ButtonStyle ccBuyBStyle = new ButtonStyle();
		ccBuyBStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("ccBuy1")));
		ccBuyBStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("ccBuy2")));
		ccBuyBStyle.disabled = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("ccBuy3")));

		ButtonStyle iAPBuyBStyle = new ButtonStyle();
		iAPBuyBStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("iAPBuy1")));
		iAPBuyBStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("iAPBuy2")));
		iAPBuyBStyle.disabled = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("iAPBuy3")));

		ButtonStyle setButtonBStyle = new ButtonStyle();
		setButtonBStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("setButton1")));
		setButtonBStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("setButton2")));
		setButtonBStyle.disabled = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("setButton3")));

		Button ccBuyB, iAPBuyB, setB;
		ItemButtonType itemButtonType;
		ItemTypeState itemTypeState;
		TextureRegion reg1;
		ItemPrice itemPrice;

		final Sound setCoinTextureSound = game.assetLoader.getSound("setCoinTextureSound");

		ScrollPaneStyle sPStyle = new ScrollPaneStyle();
		sPStyle.vScroll = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("storeVScrollBar")));
		sPStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("topPVScrollBarKnob")));
		sPStyle.background = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("storeOuterFrameBG")));

		//////////////////////////////////////////////////
		//////////////////////////////////////////////////
		//////////////////////////////////////////////////

		//////////////////////////////////////////////////
		//////////////////////////////////////////////////
		//////////////////////////////////////////////////	

		final Table boostersLayoutTable = new Table();
		boostersLayoutTable.setSize(1020, 1600);

		final BoosterItemButton firedUp;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.FIRED_UP);
		firedUp = new BoosterItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/firedUp"),
				itemConstants.FIRED_UP, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar(), Boosters.FIRED_UP);
		firedUp.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resetBoosterButtons();
				if (firedUp.isChecked()){
					if (firedUp.getAmountLeft() == 0) currentAmount = 0;
					else currentAmount = 1;
				}
			}
		});
		firedUp.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		boosterItemButtons.add(firedUp);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(currentAmount * 200);
				game.pUpdater.addBooster(Boosters.FIRED_UP, currentAmount);
				updateTotalCollectedCC();
				firedUp.updateItemButtons(totalCCCollected);
				firedUp.updateAmount();
				if (currentAmount > firedUp.getAmountLeft()){
					currentAmount = firedUp.getAmountLeft();
				}
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		boostersLayoutTable.add(firedUp).top().left();

		final BoosterItemButton shineAllTheWay;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.SHINE_ALL_THE_WAY);
		shineAllTheWay = new BoosterItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/shineAllTheWay"),
				itemConstants.SHINE_ALL_THE_WAY, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar(), Boosters.SHINE_ALL_THE_WAY);
		shineAllTheWay.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		boosterItemButtons.add(shineAllTheWay);
		shineAllTheWay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resetBoosterButtons();
				if (shineAllTheWay.isChecked()){
					if (shineAllTheWay.getAmountLeft() == 0) currentAmount = 0;
					else currentAmount = 1;
				}
			}
		});
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(currentAmount * 175);
				game.pUpdater.addBooster(Boosters.SHINE_ALL_THE_WAY, currentAmount);
				updateTotalCollectedCC();
				shineAllTheWay.updateItemButtons(totalCCCollected);
				shineAllTheWay.updateAmount();
				if (currentAmount > shineAllTheWay.getAmountLeft()){
					currentAmount = shineAllTheWay.getAmountLeft();
				}
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		boostersLayoutTable.row();
		boostersLayoutTable.add(shineAllTheWay).top().left();

		final BoosterItemButton luckyHalf;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.LUCKY_HALF);
		luckyHalf = new BoosterItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/luckyHalf"),
				itemConstants.LUCKY_HALF, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar(), Boosters.LUCKY_HALF);
		luckyHalf.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		boosterItemButtons.add(luckyHalf);
		luckyHalf.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resetBoosterButtons();
				if (luckyHalf.isChecked()){
					if (luckyHalf.getAmountLeft() == 0) currentAmount = 0;
					else currentAmount = 1;
				}
			}
		});
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(currentAmount * 100);
				game.pUpdater.addBooster(Boosters.LUCKY_HALF, currentAmount);
				updateTotalCollectedCC();
				luckyHalf.updateItemButtons(totalCCCollected);
				luckyHalf.updateAmount();
				if (currentAmount > luckyHalf.getAmountLeft()){
					currentAmount = luckyHalf.getAmountLeft();
				}
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		boostersLayoutTable.row();
		boostersLayoutTable.add(luckyHalf).top().left();

		final BoosterItemButton luckyThird;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.LUCKY_THIRD);
		luckyThird = new BoosterItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/luckyThird"),
				itemConstants.LUCKY_THIRD, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar(), Boosters.LUCKY_THIRD);
		luckyThird.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		boosterItemButtons.add(luckyThird);
		luckyThird.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resetBoosterButtons();
				if (luckyThird.isChecked()){
					if (luckyThird.getAmountLeft() == 0) currentAmount = 0;
					else currentAmount = 1;
				}
			}
		});
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(currentAmount * 50);
				game.pUpdater.addBooster(Boosters.LUCKY_THIRD, currentAmount);
				updateTotalCollectedCC();
				luckyThird.updateItemButtons(totalCCCollected);
				luckyThird.updateAmount();
				if (currentAmount > luckyThird.getAmountLeft()){
					currentAmount = luckyThird.getAmountLeft();
				}
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		boostersLayoutTable.row();
		boostersLayoutTable.add(luckyThird).top().left();

		final BoosterItemButton superBooster;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.SUPER_BOOSTER);
		superBooster = new BoosterItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/luckyThird"),
				itemConstants.SUPER_BOOSTER, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar(), Boosters.SUPER_BOOSTER);
		superBooster.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		boosterItemButtons.add(superBooster);
		superBooster.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				resetBoosterButtons();
				if (superBooster.isChecked()){
					if (superBooster.getAmountLeft() == 0) currentAmount = 0;
					else currentAmount = 1;
				}
			}
		});
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(currentAmount * 500);
				game.pUpdater.addBooster(Boosters.SUPER_BOOSTER, currentAmount);
				updateTotalCollectedCC();
				superBooster.updateItemButtons(totalCCCollected);
				superBooster.updateAmount();
				if (currentAmount > superBooster.getAmountLeft()){
					currentAmount = superBooster.getAmountLeft();
				}
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		boostersLayoutTable.row();
		boostersLayoutTable.add(superBooster).top().left();

		boostersGroup = new ButtonGroup<ItemButton>();
		boostersGroup.add(firedUp, shineAllTheWay, luckyHalf, luckyThird, superBooster);
		boostersGroup.setMaxCheckCount(1);
		boostersGroup.setMinCheckCount(0);
		boostersGroup.uncheckAll();

		boostersPane = new ScrollPane(boostersLayoutTable, sPStyle);
		boostersPane.setBounds(30, 563, 1020, 792);
		boostersPane.setUserObject(StoreScreenState.NORMAL);
		boostersPane.setFadeScrollBars(false);
		boostersPane.setVariableSizeKnobs(false);
		boostersPane.setOverscroll(false, false);

		//////////////////////////////////////////////////
		//////////////////////////////////////////////////
		//////////////////////////////////////////////////

		final Table texturesLayoutTable = new Table();
		texturesLayoutTable.setSize(1020, 1600);

		final ItemButton normalB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.NORMAL);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-NORMAL"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		normalB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.NORMAL,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.NORMAL)) normalB.setItemAsCurrent(true);
		normalB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(normalB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(0);
				game.pUpdater.unlockCoinTexture(CoinCurrency.NORMAL);
				normalB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.NORMAL);
				resetCurrentSetCoinTexture();
				normalB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.add(normalB).top().left();

		final ItemButton cnyB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.CNY);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-CNY"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		cnyB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.CNY,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.CNY)) cnyB.setItemAsCurrent(true);
		cnyB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(cnyB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(45000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.CNY);
				cnyB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.CNY);
				resetCurrentSetCoinTexture();
				cnyB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(cnyB).top().left();

		final ItemButton phpB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.PHP);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-PHP"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		phpB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.PHP,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.PHP)) phpB.setItemAsCurrent(true);
		phpB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(phpB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(50000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.PHP);
				phpB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.PHP);
				resetCurrentSetCoinTexture();
				phpB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(phpB).top().left();

		final ItemButton sgpB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.SGP);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-SGP"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		sgpB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.SGP,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.SGP)) sgpB.setItemAsCurrent(true);
		sgpB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(sgpB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(50000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.SGP);
				sgpB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.SGP);
				resetCurrentSetCoinTexture();
				sgpB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(sgpB).top().left();

		final ItemButton usdB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.USD);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-USD"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		usdB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.USD,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.USD)) usdB.setItemAsCurrent(true);
		usdB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(usdB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(50000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.USD);
				usdB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.USD);
				resetCurrentSetCoinTexture();
				usdB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(usdB).top().left();

		final ItemButton audB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.AUD);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-AUD"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		audB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.AUD,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.AUD)) audB.setItemAsCurrent(true);
		audB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(audB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(75000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.AUD);
				audB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.AUD);
				resetCurrentSetCoinTexture();
				audB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(audB).top().left();

		final ItemButton cadB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.CAD);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-CAD"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		cadB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.CAD,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.CAD)) cadB.setItemAsCurrent(true);
		cadB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(cadB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(75000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.CAD);
				cadB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.CAD);
				resetCurrentSetCoinTexture();
				cadB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(cadB).top().left();

		final ItemButton euroB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.EURO);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-EURO"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		euroB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.EURO,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.EURO)) euroB.setItemAsCurrent(true);
		euroB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(euroB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(75000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.EURO);
				euroB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.EURO);
				resetCurrentSetCoinTexture();
				euroB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(euroB).top().left();

		final ItemButton inrB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.INR);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-INR"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		inrB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.INR,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.INR)) inrB.setItemAsCurrent(true);
		inrB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(inrB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(75000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.INR);
				inrB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.INR);
				resetCurrentSetCoinTexture();
				inrB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(inrB).top().left();

		final ItemButton jpyB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.JPY);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-JPY"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		jpyB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.JPY,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.JPY)) jpyB.setItemAsCurrent(true);
		jpyB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(jpyB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(100000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.JPY);
				jpyB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.JPY);
				resetCurrentSetCoinTexture();
				jpyB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(jpyB).top().left();

		final ItemButton krwB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.KRW);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-KRW"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		krwB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.KRW,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.KRW)) krwB.setItemAsCurrent(true);
		krwB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(krwB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(100000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.KRW);
				krwB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.KRW);
				resetCurrentSetCoinTexture();
				krwB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(krwB).top().left();

		final ItemButton mxnB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.MXN);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-MXN"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		mxnB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.MXN,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.MXN)) mxnB.setItemAsCurrent(true);
		mxnB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(mxnB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(100000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.MXN);
				mxnB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.MXN);
				resetCurrentSetCoinTexture();
				mxnB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(mxnB).top().left();

		final ItemButton brlB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.BRL);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-BRL"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		brlB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.BRL,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.BRL)) brlB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(2)){
			brlB.setDisabled(true);
			brlB.setHowToUnlockMsg("Unlock Challenge 2");
		}
		brlB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(brlB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(150000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.BRL);
				brlB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.BRL);
				resetCurrentSetCoinTexture();
				brlB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(brlB).top().left();

		final ItemButton gbpB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.GBP);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-GBP"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		gbpB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.GBP,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.GBP)) gbpB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(2)){
			gbpB.setDisabled(true);
			gbpB.setHowToUnlockMsg("Unlock Challenge 2");
		}
		gbpB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(gbpB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(150000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.GBP);
				gbpB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.GBP);
				resetCurrentSetCoinTexture();
				gbpB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(gbpB).top().left();

		final ItemButton rubB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.RUB);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-RUB"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		rubB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.RUB,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.RUB)) rubB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(2)){
			rubB.setDisabled(true);
			rubB.setHowToUnlockMsg("Unlock Challenge 2");
		}
		rubB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(rubB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(150000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.RUB);
				rubB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.RUB);
				resetCurrentSetCoinTexture();
				rubB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(rubB).top().left();

		final ItemButton sekB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.SEK);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-SEK"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		sekB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.SEK,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.SEK)) sekB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(2)){
			sekB.setDisabled(true);
			sekB.setHowToUnlockMsg("Unlock Challenge 2");
		}
		sekB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(sekB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(150000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.SEK);
				sekB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.SEK);
				resetCurrentSetCoinTexture();
				sekB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(sekB).top().left();

		final ItemButton chfB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.CHF);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-CHF"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		chfB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.CHF,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.CHF)) chfB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(3)){
			chfB.setDisabled(true);
			chfB.setHowToUnlockMsg("Unlock Challenge 3");
		}
		chfB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(chfB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(200000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.CHF);
				chfB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.CHF);
				resetCurrentSetCoinTexture();
				chfB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(chfB).top().left();

		final ItemButton nzdB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.NZD);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-NZD"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		nzdB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.NZD,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.NZD)) nzdB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(3)){
			nzdB.setDisabled(true);
			nzdB.setHowToUnlockMsg("Unlock Challenge 3");
		}
		nzdB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(nzdB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(200000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.NZD);
				nzdB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.NZD);
				resetCurrentSetCoinTexture();
				nzdB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(nzdB).top().left();

		final ItemButton cloverB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.CLOVER);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-clover"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		cloverB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.CLOVER,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.CLOVER)) cloverB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(4)){
			cloverB.setDisabled(true);
			cloverB.setHowToUnlockMsg("Unlock Challenge 4");
		}
		cloverB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(cloverB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(300000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.CLOVER);
				cloverB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.CLOVER);
				resetCurrentSetCoinTexture();
				cloverB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(cloverB).top().left();

		final ItemButton diamondB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.DIAMOND);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-diamond"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		diamondB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.DIAMOND,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.DIAMOND)) diamondB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(4)){
			diamondB.setDisabled(true);
			diamondB.setHowToUnlockMsg("Unlock Challenge 4");
		}
		diamondB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(diamondB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(300000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.DIAMOND);
				diamondB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.DIAMOND);
				resetCurrentSetCoinTexture();
				diamondB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(diamondB).top().left();

		final ItemButton heartB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.HEART);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-heart"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		heartB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.HEART,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.HEART)) heartB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(4)){
			heartB.setDisabled(true);
			heartB.setHowToUnlockMsg("Unlock Challenge 4");
		}
		heartB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(heartB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(300000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.HEART);
				heartB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.HEART);
				resetCurrentSetCoinTexture();
				heartB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(heartB).top().left();

		final ItemButton spadeB;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState =ItemTypeState.TEXTURE_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(ItemConstants.SPADE);
		reg1 = new TextureRegion(game.assetLoader.getTexture("coinPic-spade"));
		reg1.setRegion((int) (35f * reg1.getRegionWidth() / 216f), (int) (34f * reg1.getRegionWidth() / 216f),
				(int) (146f * reg1.getRegionWidth() / 216f), (int) (146f * reg1.getRegionWidth() / 216f));
		spadeB = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState,
				reg1, ItemConstants.SPADE,
				itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		if (game.pUpdater.getCurrentCoinTexture().equals(CoinCurrency.SPADE)) spadeB.setItemAsCurrent(true);
		if (!game.pUpdater.challengeUnlocked(4)){
			spadeB.setDisabled(true);
			spadeB.setHowToUnlockMsg("Unlock Challenge 4");
		}
		spadeB.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		coinTextureButtons.add(spadeB);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(300000);
				game.pUpdater.unlockCoinTexture(CoinCurrency.SPADE);
				spadeB.updateCoinTexture(1);
				updateTotalCollectedCC();
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCoinTextureSound.play();
				game.pUpdater.setCurrentCoinTexture(CoinCurrency.SPADE);
				resetCurrentSetCoinTexture();
				spadeB.setItemAsCurrent(true);
			}
		});
		texturesLayoutTable.row();
		texturesLayoutTable.add(spadeB).top().left();

		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////

		final Table unlockablesLayoutTable = new Table();
		unlockablesLayoutTable.setSize(1020, 1600);

		final ItemButton noAdsItemButton;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.ITEM_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.NO_ADS);
		noAdsItemButton = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState, storeAssetsAtlas.findRegion("itemImages/noAds"),
				itemConstants.NO_ADS, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				purchaseSoundPlayer.playPurchaseSound();
				game.pUpdater.subtractCC(200000);
				game.pUpdater.unlockAds();
				updateTotalCollectedCC();
//				game.adManager.updateNoAds();
				noAdsItemButton.setItemBought(true);
			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.appWarpManager.hasInternetConnection()){
//					game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_NO_ADS,
//							new MyPurchaseListener() {
//						@Override
//						public void onPurchaseSuccess() {
//							purchaseSoundPlayer.playPurchaseSound();
//							game.pUpdater.unlockAds();
//							game.adManager.updateNoAds();
//							noAdsItemButton.setItemBought(true);
//						}
//					});
//				}
//				else {
//					game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
//				}
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		noAdsItemButton.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		unlockablesLayoutTable.add(noAdsItemButton).top().left();

		final ItemButton challenge2;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.ITEM_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.CHALLENGE_2);
		challenge2 = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState, storeAssetsAtlas.findRegion("itemImages/challenges2"),
				itemConstants.CHALLENGE_2, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		challenge2.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.pUpdater.challengeUnlocked(1)){
//					if (game.appWarpManager.hasInternetConnection()){
//						game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_UNLOCK_CHALLENGE_2,
//								new MyPurchaseListener() {
//							@Override
//							public void onPurchaseSuccess() {
//								purchaseSoundPlayer.playPurchaseSound();
//								game.pUpdater.unlockChallenge(2);
//								challenge2.setItemBought(true);
//
//								brlB.setDisabled(false);
//								gbpB.setDisabled(false);
//								rubB.setDisabled(false);
//								sekB.setDisabled(false);
//							}
//						});
//					}
//					else game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
//				}
//				else game.appWarpManager.showToast("Unlock Challenge 1 first.", Miscellaneous.ToastStyle.WARNING);
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		unlockablesLayoutTable.row();
		unlockablesLayoutTable.add(challenge2).top().left();

		final ItemButton challenge3;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.ITEM_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.CHALLENGE_3);
		challenge3 = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState, storeAssetsAtlas.findRegion("itemImages/challenges3"),
				itemConstants.CHALLENGE_3, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		challenge3.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.pUpdater.challengeUnlocked(2)){
//					if (game.appWarpManager.hasInternetConnection()){
//						game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_UNLOCK_CHALLENGE_3,
//								new MyPurchaseListener() {
//							@Override
//							public void onPurchaseSuccess() {
//								purchaseSoundPlayer.playPurchaseSound();
//								game.pUpdater.unlockChallenge(3);
//								challenge3.setItemBought(true);
//
//								brlB.setDisabled(false);
//								gbpB.setDisabled(false);
//								rubB.setDisabled(false);
//								sekB.setDisabled(false);
//
//								chfB.setDisabled(false);
//								nzdB.setDisabled(false);
//							}
//						});
//					}
//					else game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
//				}
//				else game.appWarpManager.showToast("Unlock Challenge 2 first.", Miscellaneous.ToastStyle.WARNING);
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		unlockablesLayoutTable.row();
		unlockablesLayoutTable.add(challenge3).top().left();

		final ItemButton challenge4;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.ITEM_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.CHALLENGE_4);
		challenge4 = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState, storeAssetsAtlas.findRegion("itemImages/challenges4"),
				itemConstants.CHALLENGE_4, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		challenge4.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.pUpdater.challengeUnlocked(3)){
//					if (game.appWarpManager.hasInternetConnection()){
//						game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_UNLOCK_CHALLENGE_4,
//								new MyPurchaseListener() {
//							@Override
//							public void onPurchaseSuccess() {
//								purchaseSoundPlayer.playPurchaseSound();
//								game.pUpdater.unlockChallenge(4);
//								challenge4.setItemBought(true);
//
//								brlB.setDisabled(false);
//								gbpB.setDisabled(false);
//								rubB.setDisabled(false);
//								sekB.setDisabled(false);
//
//								chfB.setDisabled(false);
//								nzdB.setDisabled(false);
//
//								spadeB.setDisabled(false);
//								heartB.setDisabled(false);
//								diamondB.setDisabled(false);
//								cloverB.setDisabled(false);
//							}
//						});
//					}
//					else game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
//				}
//				else game.appWarpManager.showToast("Unlock Challenge 3 first.", Miscellaneous.ToastStyle.WARNING);
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		unlockablesLayoutTable.row();
		unlockablesLayoutTable.add(challenge4).top().left();

		final ItemButton challenge5;
		itemButtonType = ItemButtonType.BOUGHT;
		itemTypeState = ItemTypeState.ITEM_BOUGHT;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.CHALLENGE_5);
		challenge5 = new ItemButton(itemBStyle, game, itemButtonType, itemTypeState, storeAssetsAtlas.findRegion("itemImages/challenges5"),
				itemConstants.CHALLENGE_5, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		challenge5.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.pUpdater.challengeUnlocked(4)){
//					if (game.appWarpManager.hasInternetConnection()){
//						game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_UNLOCK_CHALLENGE_5,
//								new MyPurchaseListener() {
//							@Override
//							public void onPurchaseSuccess() {
//								purchaseSoundPlayer.playPurchaseSound();
//								game.pUpdater.unlockChallenge(5);
//								challenge5.setItemBought(true);
//							}
//						});
//					}
//					else game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
//				}
//				else game.appWarpManager.showToast("Unlock Challenge 4 first.", Miscellaneous.ToastStyle.WARNING);
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		unlockablesLayoutTable.row();
		unlockablesLayoutTable.add(challenge5).top().left();

		ItemButton handfulOfCC;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.HANDFUL_OF_CC);
		handfulOfCC = new ItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/handfulOfCC"),
				itemConstants.HANDFUL_OF_CC, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		handfulOfCC.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.appWarpManager.hasInternetConnection()){
//					game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_HANDFUL_CC,
//							new MyPurchaseListener() {
//						@Override
//						public void onPurchaseSuccess() {
//							purchaseSoundPlayer.playPurchaseSound();
//							game.pUpdater.addCC(50000);
//							totalCCCollected = game.pUpdater.getTotalCC();
//						}
//					});
//				}
//				else game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		unlockablesLayoutTable.row();
		unlockablesLayoutTable.add(handfulOfCC).top().left();

		ItemButton pouchOfCC;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.POUCH_OF_CC);
		pouchOfCC = new ItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/pouchOfCC"),
				itemConstants.POUCH_OF_CC, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		pouchOfCC.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.appWarpManager.hasInternetConnection()){
//					game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_POUCH_CC,
//							new MyPurchaseListener() {
//						@Override
//						public void onPurchaseSuccess() {
//							purchaseSoundPlayer.playPurchaseSound();
//							game.pUpdater.addCC(120000);
//							totalCCCollected = game.pUpdater.getTotalCC();
//						}
//					});
//				}
//				else game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		unlockablesLayoutTable.row();
		unlockablesLayoutTable.add(pouchOfCC).top().left();

		ItemButton bagOfCC;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.BAG_OF_CC);
		bagOfCC = new ItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/bagOfCC"),
				itemConstants.BAG_OF_CC, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		bagOfCC.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.appWarpManager.hasInternetConnection()){
//					game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_BAG_CC,
//							new MyPurchaseListener() {
//						@Override
//						public void onPurchaseSuccess() {
//							purchaseSoundPlayer.playPurchaseSound();
//							game.pUpdater.addCC(250000);
//							totalCCCollected = game.pUpdater.getTotalCC();
//						}
//					});
//				}
//				else game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		unlockablesLayoutTable.row();
		unlockablesLayoutTable.add(bagOfCC).top().left();

		ItemButton sackOfCC;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.SACK_OF_CC);
		sackOfCC = new ItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/sackOfCC"),
				itemConstants.SACK_OF_CC, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		sackOfCC.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.appWarpManager.hasInternetConnection()){
//					game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_SACK_CC,
//							new MyPurchaseListener() {
//						@Override
//						public void onPurchaseSuccess() {
//							purchaseSoundPlayer.playPurchaseSound();
//							game.pUpdater.addCC(550000);
//							totalCCCollected = game.pUpdater.getTotalCC();
//						}
//					});
//				}
//				else game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		unlockablesLayoutTable.row();
		unlockablesLayoutTable.add(sackOfCC).top().left();

		ItemButton chestOfCC;
		ccBuyB = new Button(ccBuyBStyle);
		ccBuyB.setBounds(659, 53, 167, 167);
		ccBuyB.setUserObject(StoreScreenState.NORMAL);
		iAPBuyB = new Button(iAPBuyBStyle);
		iAPBuyB.setBounds(872, 53, 167, 167);
		iAPBuyB.setUserObject(StoreScreenState.NORMAL);
		setB = new Button(setButtonBStyle);
		setB.setBounds(872, 53, 167, 167);
		setB.setUserObject(StoreScreenState.NORMAL);
		itemPrice = itemConstants.itemPrices.get(itemConstants.CHEST_OF_CC);
		chestOfCC = new ItemButton(itemBStyle, game, ItemButtonType.UNLIMITED, ItemTypeState.UNLIMITED, storeAssetsAtlas.findRegion("itemImages/chestOfCC"),
				itemConstants.CHEST_OF_CC, itemPrice.getDescription(), itemPrice.getPriceInCC(), itemPrice.getPriceInDollar());
		chestOfCC.setBottomButtons(ccBuyB, iAPBuyB, setB, totalCCCollected);
		ccBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		iAPBuyB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				if (game.appWarpManager.hasInternetConnection()){
//					game.connectCoinsPurchaser.requestPurchase(ConnectCoinsPurchaser.productID_CHEST_CC,
//							new MyPurchaseListener() {
//						@Override
//						public void onPurchaseSuccess() {
//							purchaseSoundPlayer.playPurchaseSound();
//							game.pUpdater.addCC(1150000);
//							totalCCCollected = game.pUpdater.getTotalCC();
//						}
//					});
//				}
//				else game.appWarpManager.showToast("No Internet Connection", Miscellaneous.ToastStyle.ERROR);
			}
		});
		setB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		unlockablesLayoutTable.row();
		unlockablesLayoutTable.add(chestOfCC).top().left();

		////FACES

		unlockablesGroup = new ButtonGroup<ItemButton>();
		unlockablesGroup.add(noAdsItemButton, challenge2, challenge3, challenge4, challenge5, handfulOfCC, pouchOfCC, bagOfCC,
				sackOfCC, chestOfCC);
		unlockablesGroup.setMaxCheckCount(1);
		unlockablesGroup.setMinCheckCount(0);
		unlockablesGroup.uncheckAll();

		unlockablesPane = new ScrollPane(unlockablesLayoutTable, sPStyle);
		unlockablesPane.setBounds(30, 563, 1020, 792);
		unlockablesPane.setUserObject(StoreScreenState.NORMAL);
		unlockablesPane.setFadeScrollBars(false);
		unlockablesPane.setVariableSizeKnobs(false);
		unlockablesPane.setOverscroll(false, false);

		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////
		/////////////////////////////////////////////////////

		resetCurrentSetCoinTexture();
		switch (game.pUpdater.getCurrentCoinTexture()){
		case CoinCurrency.SPADE: spadeB.setItemAsCurrent(true); break;
		case CoinCurrency.HEART: heartB.setItemAsCurrent(true); break;
		case CoinCurrency.DIAMOND: diamondB.setItemAsCurrent(true); break;
		case CoinCurrency.CLOVER: cloverB.setItemAsCurrent(true); break;
		case CoinCurrency.USD: usdB.setItemAsCurrent(true); break;
		case CoinCurrency.SGP: sgpB.setItemAsCurrent(true); break;
		case CoinCurrency.SEK: sekB.setItemAsCurrent(true); break;
		case CoinCurrency.RUB: rubB.setItemAsCurrent(true); break;
		case CoinCurrency.PHP: phpB.setItemAsCurrent(true); break;
		case CoinCurrency.NZD: nzdB.setItemAsCurrent(true); break;
		case CoinCurrency.MXN: mxnB.setItemAsCurrent(true); break;
		case CoinCurrency.KRW: krwB.setItemAsCurrent(true); break;
		case CoinCurrency.JPY: jpyB.setItemAsCurrent(true); break;
		case CoinCurrency.INR: inrB.setItemAsCurrent(true); break;
		case CoinCurrency.EURO: euroB.setItemAsCurrent(true); break;
		case CoinCurrency.CNY: cnyB.setItemAsCurrent(true); break;
		case CoinCurrency.CHF: chfB.setItemAsCurrent(true); break;
		case CoinCurrency.CAD: cadB.setItemAsCurrent(true); break;
		case CoinCurrency.BRL: brlB.setItemAsCurrent(true); break;
		case CoinCurrency.AUD: audB.setItemAsCurrent(true); break;
		case CoinCurrency.NORMAL: normalB.setItemAsCurrent(true); break;
		default: break;
		}

		texturesGroup = new ButtonGroup<ItemButton>();
		texturesGroup.add(spadeB, heartB, diamondB, cloverB, usdB, sgpB, sekB, rubB, phpB, nzdB, mxnB, krwB, jpyB, inrB, gbpB, euroB,
				cnyB, chfB, cadB, brlB, audB, normalB);
		texturesGroup.setMaxCheckCount(1);
		texturesGroup.setMinCheckCount(0);
		texturesGroup.uncheckAll();

		texturesPane = new ScrollPane(texturesLayoutTable, sPStyle);
		texturesPane.setBounds(30, 563, 1020, 792);
		texturesPane.setUserObject(StoreScreenState.NORMAL);
		texturesPane.setFadeScrollBars(false);
		texturesPane.setVariableSizeKnobs(false);
		texturesPane.setOverscroll(false, false);

		//////////////////////////////////////////////////
		//////////////////////////////////////////////////
		//////////////////////////////////////////////////

		ButtonStyle amountLeftBStyle = new ButtonStyle();
		amountLeftBStyle.up = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("amountButton1")));
		amountLeftBStyle.down = new TextureRegionDrawable(new TextureRegion(game.assetLoader.getTexture("amountButton2")));
		amountLeftB = new Button(amountLeftBStyle);
		amountLeftB.setBounds(720, 320, 100, 100);
		amountLeftB.setUserObject(StoreScreenState.NORMAL);
		amountLeftB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.miscellaneous.playNormalBClick1();
				if (currentBoosterItemButton != null){
					if (currentAmount - 1 >= 1) currentAmount--;
					else {
						int amountLeft = currentBoosterItemButton.getAmountLeft();
						if (amountLeft != 0){
							currentAmount = amountLeft;
						}
					}
				}
			}
		});

		TextureRegion amountRightReg1 = new TextureRegion(new TextureRegion(game.assetLoader.getTexture("amountButton1")));
		TextureRegion amountRightReg2 = new TextureRegion(new TextureRegion(game.assetLoader.getTexture("amountButton2")));
		amountRightReg1.flip(true, false);
		amountRightReg2.flip(true, false);
		ButtonStyle amountRightBStyle = new ButtonStyle();
		amountRightBStyle.up = new TextureRegionDrawable(amountRightReg1);
		amountRightBStyle.down = new TextureRegionDrawable(amountRightReg2);
		amountRightB = new Button(amountRightBStyle);
		amountRightB.setBounds(924, 320, 100, 100);
		amountRightB.setUserObject(StoreScreenState.NORMAL);
		amountRightB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.miscellaneous.playNormalBClick1();
				if (currentBoosterItemButton != null){
					int maxAmount = currentBoosterItemButton.getAmountLeft();
					if (currentAmount + 1 <= maxAmount) currentAmount++;
					else {
						int amountLeft = currentBoosterItemButton.getAmountLeft();
						if (amountLeft != 0){
							currentAmount = 1;
						}
					}
				}
			}
		});

		for (ItemButton b : boostersGroup.getButtons()){
			b.updateItemButtons(totalCCCollected);
			game.stage.addActor(b.getBuyButton1());
			game.stage.addActor(b.getBuyButton2());
			game.stage.addActor(b.getSetButton());

			actorsToUnload.add(b.getBuyButton1());
			actorsToUnload.add(b.getBuyButton2());
			actorsToUnload.add(b.getSetButton());
		}
		for (ItemButton b : texturesGroup.getButtons()){
			b.updateItemButtons(totalCCCollected);
			game.stage.addActor(b.getBuyButton1());
			game.stage.addActor(b.getBuyButton2());
			game.stage.addActor(b.getSetButton());

			actorsToUnload.add(b.getBuyButton1());
			actorsToUnload.add(b.getBuyButton2());
			actorsToUnload.add(b.getSetButton());
		}
		for (ItemButton b : unlockablesGroup.getButtons()){
			b.updateItemButtons(totalCCCollected);
			game.stage.addActor(b.getBuyButton1());
			game.stage.addActor(b.getBuyButton2());
			game.stage.addActor(b.getSetButton());

			actorsToUnload.add(b.getBuyButton1());
			actorsToUnload.add(b.getBuyButton2());
			actorsToUnload.add(b.getSetButton());
		}

		game.stage.addActor(unlockablesPane);
		game.stage.addActor(boostersPane);
		game.stage.addActor(texturesPane);
		game.stage.addActor(amountLeftB);
		game.stage.addActor(amountRightB);

		actorsToUnload.add(unlockablesPane);
		actorsToUnload.add(boostersPane);
		actorsToUnload.add(texturesPane);
		actorsToUnload.add(amountLeftB);
		actorsToUnload.add(amountRightB);
	}

	public void initCategoryButtons(){
		TextureAtlas storeAssetsAtlas = game.assetLoader.getTextureAtlas("storeAssetsAtlas");

		ButtonStyle unlockablesBStyle = new ButtonStyle();
		unlockablesBStyle.up = new TextureRegionDrawable(storeAssetsAtlas.findRegion("upperButtons/unlockables1"));
		unlockablesBStyle.checked = new TextureRegionDrawable(storeAssetsAtlas.findRegion("upperButtons/unlockables2"));
		final Button unlockablesB = new Button(unlockablesBStyle);
		unlockablesB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.miscellaneous.playNormalBClick1();
				game.pUpdater.setSelectedItemCategory(UNLOCKABLES);
				selectedItemCatagory = game.pUpdater.getSelectedItemCategory();
			}
		});
		unlockablesB.setBounds(31, 1370, 300, 75);
		unlockablesB.setUserObject(StoreScreenState.NORMAL);

		ButtonStyle boosterBStyle = new ButtonStyle();
		boosterBStyle.up = new TextureRegionDrawable(storeAssetsAtlas.findRegion("upperButtons/boosters1"));
		boosterBStyle.checked = new TextureRegionDrawable(storeAssetsAtlas.findRegion("upperButtons/boosters2"));
		final Button boosterB = new Button(boosterBStyle);
		boosterB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.miscellaneous.playNormalBClick1();
				game.pUpdater.setSelectedItemCategory(BOOSTERS);
				selectedItemCatagory = game.pUpdater.getSelectedItemCategory();
			}
		});
		boosterB.setBounds(393, 1370, 300, 75);
		boosterB.setUserObject(StoreScreenState.NORMAL);

		ButtonStyle texturesBStyle = new ButtonStyle();
		texturesBStyle.up = new TextureRegionDrawable(storeAssetsAtlas.findRegion("upperButtons/textures1"));
		texturesBStyle.checked = new TextureRegionDrawable(storeAssetsAtlas.findRegion("upperButtons/textures2"));
		final Button texturesB = new Button(texturesBStyle);
		texturesB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				game.miscellaneous.playNormalBClick1();
				game.pUpdater.setSelectedItemCategory(TEXTURES);
				selectedItemCatagory = game.pUpdater.getSelectedItemCategory();
			}
		});
		texturesB.setBounds(749, 1370, 300, 75);
		texturesB.setUserObject(StoreScreenState.NORMAL);

		ButtonGroup<Button> categoryGroup = new ButtonGroup<Button>();
		categoryGroup.add(boosterB, unlockablesB, texturesB);
		categoryGroup.setMaxCheckCount(1);
		categoryGroup.setMinCheckCount(1);

		upperButtons = new ButtonGroup<Button>();
		upperButtons.add(boosterB);
		upperButtons.add(unlockablesB);
		upperButtons.add(texturesB);
		upperButtons.setMaxCheckCount(1);
		upperButtons.setMinCheckCount(1);

		game.stage.addActor(boosterB);
		game.stage.addActor(unlockablesB);
		game.stage.addActor(texturesB);
		actorsToUnload.add(boosterB);
		actorsToUnload.add(unlockablesB);
		actorsToUnload.add(texturesB);

		switch (game.pUpdater.getSelectedItemCategory()){
		case BOOSTERS: boosterB.setChecked(true); break;
		case TEXTURES: texturesB.setChecked(true); break;
		case UNLOCKABLES: unlockablesB.setChecked(true); break;
		default: break;
		}
	}

	public void updateTotalCollectedCC(){
		totalCCCollected = game.pUpdater.getTotalCC();
	}

	public void render(SpriteBatch batch){
		setItemCategory();
		batch.draw(title, 0, 1469, 1080, 500);
		batch.draw(descFrame, 0, 0, 1080, 1920);
		renderCC(batch, totalCCCollected);
		switch (selectedItemCatagory){
		case BOOSTERS: {
			if (boostersGroup.getChecked() != null){
				if (boostersGroup.getChecked().getBuyButton1().isVisible()) 
					batch.draw(buyButtonPlace, boostersGroup.getChecked().getBuyButton1().getX() - 13,
							boostersGroup.getChecked().getBuyButton1().getY() - 9, 188, 188);
				if (boostersGroup.getChecked().getBuyButton2().isVisible() || boostersGroup.getChecked().getSetButton().isVisible()) 
					batch.draw(buyButtonPlace, 859, 44, 188, 188);
			}
		}; break;
		case TEXTURES: {
			if (texturesGroup.getChecked() != null){
				if (texturesGroup.getChecked().getBuyButton1().isVisible()) 
					batch.draw(buyButtonPlace, texturesGroup.getChecked().getBuyButton1().getX() - 13,
							texturesGroup.getChecked().getBuyButton1().getY() - 9, 188, 188);
				if (texturesGroup.getChecked().getBuyButton2().isVisible() || texturesGroup.getChecked().getSetButton().isVisible()) 
					batch.draw(buyButtonPlace, 859, 44, 188, 188);
			}
		}; break;
		case UNLOCKABLES: {
			if (unlockablesGroup.getChecked() != null){
				if (unlockablesGroup.getChecked().getBuyButton1().isVisible()) 
					batch.draw(buyButtonPlace, unlockablesGroup.getChecked().getBuyButton1().getX() - 13,
							unlockablesGroup.getChecked().getBuyButton1().getY() - 9, 188, 188);
				if (unlockablesGroup.getChecked().getBuyButton2().isVisible() || unlockablesGroup.getChecked().getSetButton().isVisible()) 
					batch.draw(buyButtonPlace, 859, 44, 188, 188);
			}
		}; break;
		default: break;
		}
	}

	private void setItemCategory() {
		for (ItemButton b : boostersGroup.getButtons()) b.updateItemButtons(totalCCCollected);
		for (ItemButton b : texturesGroup.getButtons()) b.updateItemButtons(totalCCCollected);
		for (ItemButton b : unlockablesGroup.getButtons()) b.updateItemButtons(totalCCCollected);
		//		unlockablesPane.setVisible(true);
		//		boostersPane.setVisible(true);
		//		texturesPane.setVisible(true);
		switch (selectedItemCatagory){
		case BOOSTERS: {
			unlockablesPane.setVisible(false);
			boostersPane.setVisible(true);
			texturesPane.setVisible(false);
		}; break;
		case TEXTURES: {
			unlockablesPane.setVisible(false);
			boostersPane.setVisible(false);
			texturesPane.setVisible(true);
		}; break;
		case UNLOCKABLES: {
			unlockablesPane.setVisible(true);
			boostersPane.setVisible(false);
			texturesPane.setVisible(false);
		}; break;
		default: break;
		}
	}

	public void renderOuterLayout(SpriteBatch batch){
		batch.draw(itemsOuterFrame, 0, 0, 1080, 1920);
		for (Button b : upperButtons.getButtons()) b.draw(batch, 1);
		switch (selectedItemCatagory){
		case BOOSTERS: {
			for (ItemButton b : boostersGroup.getButtons()) b.setCurrentCategory(true);
			for (ItemButton b : unlockablesGroup.getButtons()) b.setCurrentCategory(false);
			for (ItemButton b : texturesGroup.getButtons()) b.setCurrentCategory(false);
			if (boostersGroup.getChecked() == null){
				currentBoosterItemButton = null;
				renderDescription(batch, itemConstants.BOOSTERS_DESC, false, 0);
			}
			else {
				currentBoosterItemButton = (BoosterItemButton) boostersGroup.getChecked();
				currentBoosterItemButton.setAmountToPurchase(currentAmount);
				currentBoosterItemButton.temporarilySetPriceCC(currentAmount);
				boolean displayAmount = currentAmount != 0;
				renderDescription(batch, boostersGroup.getChecked().getDescription(), displayAmount, currentAmount);
			}

			drawBuyAndSetButtons(batch, false, true, false);

			//			for (ItemButton b : texturesGroup.getButtons()){
			//				b.getBuyButton1().setVisible(false);
			//				b.getBuyButton2().setVisible(false);
			//				b.getSetButton().setVisible(false);
			//			}
			//			for (ItemButton b : boostersGroup.getButtons()){
			//				if (b.getBuyButton1().isVisible()) b.getBuyButton1().draw(batch, 1);
			//				if (b.getBuyButton2().isVisible()) b.getBuyButton2().draw(batch, 1);
			//				if (b.getSetButton().isVisible()) b.getSetButton().draw(batch, 1);
			//			}
			//			for (ItemButton b : unlockablesGroup.getButtons()){
			//				b.getBuyButton1().setVisible(false);
			//				b.getBuyButton2().setVisible(false);
			//				b.getSetButton().setVisible(false);
			//			}
		}; break;
		case TEXTURES: {
			for (ItemButton b : boostersGroup.getButtons()) b.setCurrentCategory(false);
			for (ItemButton b : unlockablesGroup.getButtons()) b.setCurrentCategory(false);
			for (ItemButton b : texturesGroup.getButtons()) b.setCurrentCategory(true);
			currentBoosterItemButton = null;
			if (texturesGroup.getChecked() == null){
				renderDescription(batch, itemConstants.TEXTURE_DESC, false, 0);
			}
			else {
				renderDescription(batch, texturesGroup.getChecked().getDescription(), false, 0);
			}
			drawBuyAndSetButtons(batch, true, false, false);
		}; break;
		case UNLOCKABLES: {
			for (ItemButton b : boostersGroup.getButtons()) b.setCurrentCategory(false);
			for (ItemButton b : unlockablesGroup.getButtons()) b.setCurrentCategory(true);
			for (ItemButton b : texturesGroup.getButtons()) b.setCurrentCategory(false);
			currentBoosterItemButton = null;
			if (unlockablesGroup.getChecked() == null){
				renderDescription(batch, itemConstants.UNLOCKABLES_DESC, false, 0);
			}
			else {
				renderDescription(batch, unlockablesGroup.getChecked().getDescription(), false, 0);
			}
			drawBuyAndSetButtons(batch, false, false, true);
		}; break;
		default: {
			currentBoosterItemButton = null;
		}; break;
		}
	}

	private void resetBoosterButtons(){
		for (BoosterItemButton bItemButton : boosterItemButtons){
			bItemButton.temporarilySetPriceCC(1);
		}
	}

	private void drawBuyAndSetButtons(SpriteBatch batch, boolean drawTextureGroup, boolean drawBoostersGroupd,
			boolean drawUnlockablesGroup){
		for (ItemButton b : texturesGroup.getButtons()){
			if (drawTextureGroup){
				b.setCurrentCategory(true);
				if (b.getBuyButton1().isVisible()) b.getBuyButton1().draw(batch, 1);
				if (b.getBuyButton2().isVisible()) b.getBuyButton2().draw(batch, 1);
				if (b.getSetButton().isVisible()) b.getSetButton().draw(batch, 1);
			}
			else {
				b.setCurrentCategory(false);
				b.getBuyButton1().setVisible(false);
				b.getBuyButton2().setVisible(false);
				b.getSetButton().setVisible(false);
			}
		}


		for (ItemButton b : boostersGroup.getButtons()){
			if (drawBoostersGroupd){
				b.setCurrentCategory(true);
				if (b.getBuyButton1().isVisible()) b.getBuyButton1().draw(batch, 1);
				if (b.getBuyButton2().isVisible()) b.getBuyButton2().draw(batch, 1);
				if (b.getSetButton().isVisible()) b.getSetButton().draw(batch, 1);
			}
			else {
				b.setCurrentCategory(false);
				b.getBuyButton1().setVisible(false);
				b.getBuyButton2().setVisible(false);
				b.getSetButton().setVisible(false);
			}
		}

		for (ItemButton b : unlockablesGroup.getButtons()){
			if (drawUnlockablesGroup){
				b.setCurrentCategory(true);
				if (b.getBuyButton1().isVisible()) b.getBuyButton1().draw(batch, 1);
				if (b.getBuyButton2().isVisible()) b.getBuyButton2().draw(batch, 1);
				if (b.getSetButton().isVisible()) b.getSetButton().draw(batch, 1);
			}
			else {
				b.setCurrentCategory(false);
				b.getBuyButton1().setVisible(false);
				b.getBuyButton2().setVisible(false);
				b.getSetButton().setVisible(false);
			}
		}
	}

	public void renderCC(SpriteBatch batch, long totalCC){
		ConnectCoins.glyphLayout.setText(game.fManager.smallFont, String.valueOf(totalCC));
		float totalCCX = 175 - (ConnectCoins.glyphLayout.width / 2);
		batch.draw(ccSymbol, totalCCX, 160, 96, 96);
		game.fManager.drawSmallFont(batch, Color.valueOf("402003"), 1.5f, 1.5f, String.valueOf(totalCC), totalCCX + 70,
				268, 0, Align.left);
	}

	public void renderDescription(SpriteBatch batch, String desc, boolean withAmount, int amount){
		float y;
		Color prevColor = game.fManager.largeFont4.getColor();
		game.fManager.largeFont4.setColor(Color.valueOf("281500"));
		BitmapFontCache cache = game.fManager.largeFont4.getCache();
		if (!withAmount){
//			cache.setWrappedText(desc, 0, 0, 900);
			cache.setText(desc, 0, 0, 900, Align.center, true);
			ConnectCoins.glyphLayout.setText(cache.getFont(), desc, cache.getColor(), 900, Align.center, true);
			y = 420 + ConnectCoins.glyphLayout.height / 2;
//			cache.setWrappedText(desc, 90, y, 900, Align.center);
			cache.setText(desc, 90, y, 900, Align.center, true);
			cache.draw(batch);

			amountLeftB.setVisible(false);
			amountRightB.setVisible(false);
		}
		else {
//			cache.setWrappedText(desc, 0, 0, 650);
			cache.setText(desc, 0, 0, 650, Align.center, true);
			ConnectCoins.glyphLayout.setText(cache.getFont(), desc, cache.getColor(), 650, Align.center, true);
			y = 411 + ConnectCoins.glyphLayout.height / 2;
//			cache.setWrappedText(desc, 45, y + 4, 650, Align.center);
			cache.setText(desc, 45, y + 4, 650, Align.center, true);
			cache.draw(batch);

			game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("000000"), 1, 1, "AMOUNT",
					875, 471, 0, Align.center);
			game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("000000"), 1.3f, 1.3f,
					String.valueOf(amount), 870, 410, 0, Align.center);

			amountLeftB.setVisible(true);
			amountRightB.setVisible(true);
		}
		game.fManager.largeFont4.setColor(prevColor);
	}

	private void resetCurrentSetCoinTexture(){
		for (ItemButton itemButton : coinTextureButtons) itemButton.setItemAsCurrent(false);
	}

	public boolean isRenderOuterLayout() {
		return renderOuterLayout;
	}

	public void setRenderOuterLayout(boolean renderOuterLayout) {
		this.renderOuterLayout = renderOuterLayout;
	}

	private void unload(){
		game.assetLoader.loadScreenAssets(AssetScreenSpace.MAIN_MENU_ASSETS, null, true, false);
		coinTextureButtons.clear();
		boosterItemButtons.clear();
		for (Actor actor : actorsToUnload) actor.remove();
		actorsToUnload.clear();
	}

	@Override
	public void dispose() {
		purchaseSoundPlayer.dispose();
		purchaseSoundPlayer = null;
		game = null;
		//		title.dispose();
		//		itemsOuterFrame.dispose();
		//		descFrame.dispose();
		//		ccSymbol.dispose();
		//		buyButtonPlace.dispose();
		title = null;
		itemsOuterFrame = null;
		descFrame = null;
		ccSymbol = null;
		buyButtonPlace = null;
		renderOuterLayout = false;
		currentAmount = 0;
		upperButtons = null;
		amountLeftB = null;
		amountRightB = null;
		unlockablesGroup = null;
		boostersGroup = null;
		texturesGroup = null;
		unlockablesPane = null;
		boostersPane = null;
		texturesPane = null;
		totalCCCollected = 0;
		if (currentBoosterItemButton != null) currentBoosterItemButton.dispose();
		currentBoosterItemButton = null;
//		selectedItemCatagory = null;
		for (ItemButton iB : coinTextureButtons){
			iB.remove();
			iB.dispose();
			iB = null;
		}
		coinTextureButtons.clear();
		coinTextureButtons = null;
		for (BoosterItemButton bI : boosterItemButtons){
			bI.dispose();
			bI.remove();
			bI = null;
		}
		boosterItemButtons.clear();
		boosterItemButtons = null;
		itemConstants = null;
	}
}
