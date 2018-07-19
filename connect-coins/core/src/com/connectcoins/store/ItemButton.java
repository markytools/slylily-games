package com.connectcoins.store;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
//import com.connectcoins.awards.StatisticsRenderer;
import com.connectcoins.game.ConnectCoins;

public class ItemButton extends Button implements Disposable {
	public enum ItemButtonType {
		NOT_BOUGHT, BOUGHT, UNLIMITED, DISABLED
	}

	public enum ItemTypeState {
		TEXTURE_NOT_BOUGHT, TEXTURE_BOUGHT, ITEM_NOT_BOUGHT, ITEM_BOUGHT, UNLIMITED
	}

	protected ConnectCoins game;
	protected String description, name;
	protected ItemButtonType itemBType;
	protected ItemTypeState itemTypeState;
	protected TextureRegion itemPic;
	protected Texture ccTexture, dollarTexture, setImage;
	protected int priceInCC;
	protected float priceInDollars;
	protected Button buyButton1, buyButton2, setButton;
	protected boolean setAsCurrent = false;
	protected ItemButtonType prevItemBType;
	protected boolean isCurrentCategory = false;
	protected String howToUnlockMsg;
	
	protected boolean itemSettable = false;

	public ItemButton(ButtonStyle style, ConnectCoins game, ItemButtonType itemBType, ItemTypeState itemTypeState, Texture itemPic,
			String name, String description, float priceInCC, float priceInDollars){
		super(style);
		this.game = game;
		this.name = name;
		this.description = description;
		this.itemBType = itemBType;
		this.itemTypeState = itemTypeState;
		this.itemPic = new TextureRegion(itemPic);
		this.priceInCC = (int) priceInCC;
		this.priceInDollars = priceInDollars;
		ccTexture = game.assetLoader.getTexture("ccSymbol");
		dollarTexture = game.assetLoader.getTexture("dollarSymbol");
		setImage = game.assetLoader.getTexture("setCurrentItemImg");
		setSize(970, 160);
	}



	public ItemButton(ButtonStyle style, ConnectCoins game, ItemButtonType itemBType, ItemTypeState itemTypeState, TextureRegion itemPic,
			String name, String description, float priceInCC, float priceInDollars) {
		super(style);
		this.game = game;
		this.name = name;
		this.description = description;
		this.itemBType = itemBType;
		this.itemTypeState = itemTypeState;
		this.itemPic = itemPic;
		this.priceInCC = (int) priceInCC;
		this.priceInDollars = priceInDollars;
		ccTexture = game.assetLoader.getTexture("ccSymbol");
		dollarTexture = game.assetLoader.getTexture("dollarSymbol");
		setImage = game.assetLoader.getTexture("setCurrentItemImg");
		setSize(970, 160);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		float x = getX();
		float y = getY();
		
		switch (itemBType){
		case NOT_BOUGHT: {
			batch.draw(itemPic, x + 16, y + 15, 130, 130);
			game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("402003"), 1, 1, name,
					x + 545, y + 138, 0, Align.center);
			if (priceInDollars == 0){
				ConnectCoins.glyphLayout.setText(game.fManager.largeFont3, String.valueOf(priceInCC));
				float priceInCCX = x + 660 - (ConnectCoins.glyphLayout.width / 2);
				float priceInCCY = y + 50;
				batch.draw(ccTexture, priceInCCX - 160, priceInCCY - 38, 70, 70);
				game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("093762"), 1, 1, 
						String.valueOf(priceInCC), priceInCCX - 100, priceInCCY + 16, 0, Align.left);
			}
			else if (priceInCC == 0){
				ConnectCoins.glyphLayout.setText(game.fManager.largeFont3, String.valueOf(priceInDollars));
				float priceInCCX = x + 660 - (ConnectCoins.glyphLayout.width / 2);
				float priceInCCY = y + 50;
				batch.draw(dollarTexture, priceInCCX - 160, priceInCCY - 38, 43, 70);
				game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("093762"), 1, 1, 
						String.valueOf(priceInDollars), priceInCCX - 100, priceInCCY + 16, 0, Align.left);
			}
			else {
				float priceX, priceY, dollarX;
//				String df1 = String.format("%d", priceInCC);
//				String df2 = String.format("%.2f", priceInDollars);
//				DecimalFormat df1 = new DecimalFormat("0");
//				DecimalFormat df2 = new DecimalFormat("0.00");
				String ccPrice = getFloatStr(String.valueOf(priceInCC), 0);
				String orStr = "  or     ";
				String dollarPrice = getFloatStr(String.valueOf(priceInDollars), 2);
				BitmapFontCache cache = game.fManager.largeFont3.getCache();
				cache.setText(ccPrice + orStr + dollarPrice, x, y);

				ConnectCoins.glyphLayout.setText(cache.getFont(), ccPrice + orStr + dollarPrice);
				priceX = x + 550 - (ConnectCoins.glyphLayout.width) / 2;
				priceY = y + 50;
				cache.clear();
				cache.setText(ccPrice + orStr, 0, 0);
				ConnectCoins.glyphLayout.setText(cache.getFont(), ccPrice + orStr);
				dollarX = priceX + ConnectCoins.glyphLayout.width - game.fManager.largeFont3.getSpaceWidth();
				cache.clear();

//				cache.setMultiLineText(ccPrice + orStr + dollarPrice, priceX + 20, priceY + 56, 0, Align.left);
				cache.setText(ccPrice + orStr + dollarPrice, priceX + 20, priceY + 56, 0, Align.left, false);
				cache.setColors(Color.valueOf("093762"), 0, ccPrice.length());
				cache.setColors(Color.valueOf("1e0962"), ccPrice.length(), ccPrice.length() + orStr.length());
				cache.setColors(Color.valueOf("620922"), ccPrice.length() + orStr.length(),
						ccPrice.length() + orStr.length() + dollarPrice.length());
				cache.draw(batch);
				batch.draw(ccTexture, priceX - 38, priceY - 38, 70, 70);
				batch.draw(dollarTexture, dollarX - 9, priceY - 40, 43, 70);
			}
		}; break;
		case BOUGHT: {
			batch.draw(itemPic, x + 16, y + 15, 130, 130);
			game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("402003"), 1, 1, name,
					x + 545, y + 94, 0, Align.center);
			if (setAsCurrent){
				batch.draw(setImage, x + 700, y, 220, 160);
			}
		}; break;
		case UNLIMITED: {
//			if (amountLeft != 0){
//				game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("402003"), 1, 1,
//						name + "(" + String.valueOf(amountLeft) + " left)", x + 545, y + 138, 0, Align.center);
//			}
//			else {
//				game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("402003"), 1, 1,
//						name, x + 545, y + 138, 0, Align.center);
//			}
			batch.draw(itemPic, x + 16, y + 15, 130, 130);
			game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("402003"), 1, 1,
					name, x + 545, y + 138, 0, Align.center);
			if (priceInDollars == 0){
				ConnectCoins.glyphLayout.setText(game.fManager.largeFont3, String.valueOf(priceInCC));
				float priceInCCX = x + 660 - (ConnectCoins.glyphLayout.width / 2);
				float priceInCCY = y + 50;
				batch.draw(ccTexture, priceInCCX - 160, priceInCCY - 38, 70, 70);
				game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("093762"), 1, 1, 
						String.valueOf(priceInCC), priceInCCX - 100, priceInCCY + 16, 0, Align.left);
			}
			else if (priceInCC == 0){
				ConnectCoins.glyphLayout.setText(game.fManager.largeFont3, String.valueOf(priceInDollars));
				float priceInCCX = x + 660 - (ConnectCoins.glyphLayout.width / 2);
				float priceInCCY = y + 50;
				batch.draw(dollarTexture, priceInCCX - 160, priceInCCY - 38, 43, 70);
				game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("093762"), 1, 1, 
						String.valueOf(priceInDollars), priceInCCX - 100, priceInCCY + 16, 0, Align.left);
			}
			else {
				float priceX, priceY, dollarX;
//				DecimalFormat df1 = new DecimalFormat("0");
//				DecimalFormat df2 = new DecimalFormat("0.00");
				String ccPrice = getFloatStr(String.valueOf(priceInCC), 0);
				String orStr = "  or     ";
				String dollarPrice = getFloatStr(String.valueOf(priceInDollars), 2);
				BitmapFontCache cache = game.fManager.largeFont3.getCache();
				cache.setText(ccPrice + orStr + dollarPrice, x, y);

				ConnectCoins.glyphLayout.setText(cache.getFont(), ccPrice + orStr + dollarPrice);
				priceX = x + 550 - (ConnectCoins.glyphLayout.width) / 2;
				priceY = y + 50;
				cache.clear();
				cache.setText(ccPrice + orStr, 0, 0);
				ConnectCoins.glyphLayout.setText(cache.getFont(), ccPrice + orStr);
				dollarX = priceX + ConnectCoins.glyphLayout.width - game.fManager.largeFont3.getSpaceWidth();
				cache.clear();

//				cache.setMultiLineText(ccPrice + orStr + dollarPrice, priceX + 20, priceY + 56, 0, Align.left);
				cache.setText(ccPrice + orStr + dollarPrice, priceX + 20, priceY + 56, 0, Align.left, false);
				cache.setColors(Color.valueOf("093762"), 0, ccPrice.length());
				cache.setColors(Color.valueOf("1e0962"), ccPrice.length(), ccPrice.length() + orStr.length());
				cache.setColors(Color.valueOf("620922"), ccPrice.length() + orStr.length(),
						ccPrice.length() + orStr.length() + dollarPrice.length());
				cache.draw(batch);
				batch.draw(ccTexture, priceX - 38, priceY - 38, 70, 70);
				batch.draw(dollarTexture, dollarX - 9, priceY - 40, 43, 70);
			}
		}; break;
		case DISABLED: {
			game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("402003"), 1, 1, name,
					x + 545, y + 138, 0, Align.center);
			if (howToUnlockMsg != null) game.fManager.drawFont(game.fManager.largeFont3, (SpriteBatch) batch, Color.valueOf("402003"),
					1, 1, howToUnlockMsg, x + 545, y + 66, 0, Align.center);
		}; break;
		default: break;
		}
	}
	
	@Override
	public void setDisabled(boolean isDisabled) {
		if (isDisabled){
			prevItemBType = itemBType;
			itemBType = ItemButtonType.DISABLED;
		}
		else {
			if (prevItemBType != null){
				itemBType = prevItemBType;
				prevItemBType = null;
			}
		}
		super.setDisabled(isDisabled);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void updateCoinTexture(int normal){
		itemBType = (normal == 1) ? ItemButtonType.BOUGHT : ItemButtonType.NOT_BOUGHT;
		itemTypeState = (normal == 1) ? ItemTypeState.TEXTURE_BOUGHT : ItemTypeState.TEXTURE_NOT_BOUGHT;
	}

	public void updateFaceStates(boolean faceUnlocked){
		itemBType = (faceUnlocked) ? ItemButtonType.BOUGHT : ItemButtonType.NOT_BOUGHT;
		itemTypeState = (faceUnlocked) ? ItemTypeState.TEXTURE_BOUGHT : ItemTypeState.TEXTURE_NOT_BOUGHT;
	}

	public void updateTimedBoosterStates(boolean active){
		itemBType = (active) ? ItemButtonType.BOUGHT : ItemButtonType.NOT_BOUGHT;
		itemTypeState = (active) ? ItemTypeState.ITEM_BOUGHT : ItemTypeState.ITEM_NOT_BOUGHT;
	}

	/**
	 * This method unlocks items that are not settable!
	 */
	public void setItemBought(boolean bought){
		itemBType = (bought) ? ItemButtonType.BOUGHT : ItemButtonType.NOT_BOUGHT;
		itemTypeState = (bought) ? ItemTypeState.ITEM_BOUGHT : ItemTypeState.ITEM_NOT_BOUGHT;
	}

	/**
	 * 
	 * @param buyButton1 - cc purchase
	 * @param buyButton2 - real money purchase
	 * @param setButton - sets the button to active
	 * @param totalCC
	 */
	public void setBottomButtons(Button buyButton1, Button buyButton2, Button setButton, long totalCC){
		this.buyButton1 = buyButton1;
		this.buyButton2 = buyButton2;
		this.setButton = setButton;
		updateItemButtons(totalCC);
	}

	public void updateItemButtons(long totalCC){
		if (isChecked() && isCurrentCategory){
			switch (itemTypeState){
			case TEXTURE_NOT_BOUGHT: {
				buyButton1.setVisible(true);
				buyButton2.setVisible(true);
				setButton.setVisible(false);
			}; break;
			case TEXTURE_BOUGHT: {
				buyButton1.setVisible(false);
				buyButton2.setVisible(false);
				setButton.setVisible(true);
			}; break;
			case ITEM_NOT_BOUGHT: {
				buyButton1.setVisible(true);
				buyButton2.setVisible(true);
				setButton.setVisible(false);
			}; break;
			case ITEM_BOUGHT: {
				buyButton1.setVisible(false);
				buyButton2.setVisible(false);
				if (itemSettable) setButton.setVisible(true);
				else setButton.setVisible(false);
			}; break;
			case UNLIMITED: {
				buyButton1.setVisible(true);
				buyButton2.setVisible(true);
				setButton.setVisible(false);
			}; break;
			default: {
				buyButton1.setVisible(false);
				buyButton2.setVisible(false);
				setButton.setVisible(false);
			}; break;
			}
		}
		else {
			buyButton1.setVisible(false);
			buyButton2.setVisible(false);
			setButton.setVisible(false);
		}
		if (priceInDollars == 0){
			buyButton1.setBounds(872, 53, 167, 167);
			buyButton2.setVisible(false);
		}
		else if (priceInCC == 0){
			buyButton1.setVisible(false);
			buyButton2.setBounds(872, 53, 167, 167);
		}
		if (totalCC < priceInCC){
			buyButton1.setDisabled(true);
			buyButton1.setTouchable(Touchable.disabled);
		}
		else {
			buyButton1.setDisabled(false);
			buyButton1.setTouchable(Touchable.enabled);
		}
	}
	
	public void setItemAsCurrent(boolean set){
		this.setAsCurrent = set;
	}

	public boolean isCurrentCategory() {
		return isCurrentCategory;
	}

	public void setCurrentCategory(boolean isCurrentCategory) {
		this.isCurrentCategory = isCurrentCategory;
	}

	public Button getBuyButton1() {
		return buyButton1;
	}

	public Button getBuyButton2() {
		return buyButton2;
	}

	public Button getSetButton() {
		return setButton;
	}

	public String getDescription() {
		return description;
	}

	public void setHowToUnlockMsg(String howToUnlockMsg) {
		this.howToUnlockMsg = howToUnlockMsg;
	}
	
	public void setItemSettable(boolean itemSettable) {
		this.itemSettable = itemSettable;
	}
	
	public static String getFloatStr(String s, int decimalPlace) {
		if (decimalPlace < 0) throw new IllegalArgumentException(
				"DecimalPlace must be greater than 0.");
		int pointInd = -1;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length() + decimalPlace; i++) {
			if (decimalPlace == 0) {
				if (s.charAt(i) == '.') break;
				if (i < s.length()) sb.append(s.charAt(i));
			}
			else {
				if (pointInd != -1) {
					if (i < s.length()) sb.append(s.charAt(i));
					else sb.append('0');
					pointInd++;
				}
				else {
					if (i < s.length()) sb.append(s.charAt(i));
					else {
						sb.append('.');
						pointInd = 0;
					}
				}
				if (pointInd >= decimalPlace) break;
				if (i < s.length()) if (s.charAt(i) == '.') pointInd = 0;
			}
		}
		return sb.toString();
	}

	@Override
	public void dispose() {
		game = null;
		description = null;
		name = null;
		itemBType = null;
		itemTypeState = null;
//		itemPic.getTexture().dispose();
//		ccTexture.dispose();
//		dollarTexture.dispose();
		ccTexture = null;
		dollarTexture = null;
		priceInCC = 0;
		priceInDollars = 0;
		buyButton1.remove();
		buyButton2.remove();
		setButton.remove();
		buyButton1 = null;
		buyButton2 = null;
		setButton = null;
//		setImage.dispose();
		setImage = null;
		setAsCurrent = false;
		prevItemBType = null;
		isCurrentCategory = false;
		howToUnlockMsg = null;
	}

}
