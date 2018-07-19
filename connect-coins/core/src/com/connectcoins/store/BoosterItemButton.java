package com.connectcoins.store;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.functions.GameModeConfig.Boosters;
import com.connectcoins.game.ConnectCoins;

public class BoosterItemButton extends ItemButton implements Disposable {
	private Boosters booster;
	private int amountLeft;
	private int amountToPurchase;
	private String itemName;
	private int itemPriceInCC;
	private int amount;
	
	public BoosterItemButton(ButtonStyle style, ConnectCoins game,
			ItemButtonType itemBType, ItemTypeState itemTypeState,
			Texture itemPic, String name, String description, float priceInCC,
			float priceInDollars, Boosters booster) {
		super(style, game, itemBType, itemTypeState, itemPic, name, description,
				priceInCC, priceInDollars);
		this.booster = booster;
		this.itemName = name;
		this.itemPriceInCC = (int) priceInCC;
		amount = game.pUpdater.getTotalBooster(booster);
		amountLeft = 99 - amount;
		amountToPurchase = -1;
	}

	public BoosterItemButton(ButtonStyle style, ConnectCoins game,
			ItemButtonType itemBType, ItemTypeState itemTypeState,
			TextureRegion itemPic, String name, String description,
			float priceInCC, float priceInDollars, Boosters booster) {
		super(style, game, itemBType, itemTypeState, itemPic, name, description,
				priceInCC, priceInDollars);
		this.booster = booster;
		this.itemName = name;
		this.itemPriceInCC = (int) priceInCC;
		amount = game.pUpdater.getTotalBooster(booster);
		amountLeft = 99 - amount;
		amountToPurchase = -1;
	}

	@Override
	public void updateItemButtons(long totalCC) {
		super.updateItemButtons(totalCC);
		
		if (amountLeft == 0){
			buyButton1.setDisabled(true);
			buyButton1.setTouchable(Touchable.disabled);
		}
		else {
			buyButton1.setDisabled(false);
			buyButton1.setTouchable(Touchable.enabled);
		}
		if (totalCC < priceInCC){
			buyButton1.setDisabled(true);
			buyButton1.setTouchable(Touchable.disabled);
		}
		else {
			buyButton1.setDisabled(false);
			buyButton1.setTouchable(Touchable.enabled);
		}
		if (amountToPurchase == -1 || amountToPurchase == 0){
			buyButton1.setDisabled(true);
			buyButton1.setTouchable(Touchable.disabled);
		}
		if (amount > 0){
			name = itemName + " (" + String.valueOf(amount) + ")";
		}
		else name = itemName;
	}
	
	public void temporarilySetPriceCC(int currentAmount){
		if (currentAmount == 0){
			priceInCC = itemPriceInCC;
		}
		else priceInCC = itemPriceInCC * currentAmount;
	}

	public void setAmountToPurchase(int amountToPurchase) {
		this.amountToPurchase = amountToPurchase;
	}
	
	public void updateAmount(){
		this.amount = game.pUpdater.getTotalBooster(booster);
		this.amountLeft = 99 - amount;
	}
	
	public int getAmountLeft(){
		return amountLeft;
	}
	
	public Boosters getBooster() {
		return booster;
	}

	@Override
	public void dispose() {
		booster = null;
		amountLeft = 0;
		amountToPurchase = 0;
		itemName = null;
		itemPriceInCC = 0;
		amount = 0;
	}
}
