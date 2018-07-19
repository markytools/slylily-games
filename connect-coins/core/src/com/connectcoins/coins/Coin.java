package com.connectcoins.coins;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Coin {
	public enum CoinType implements Serializable {
		WHOLE, HALF, THIRD;
		
		public String getStatus() {
	        return this.name();
	    }
	}

	public enum CoinValue implements Serializable {
		ONE, FIVE, TEN;

		public String getStatus() {
	        return this.name();
	    }
	}

	public enum CoinColor implements Serializable {
		BRONZE, SILVER, GOLD;

		public String getStatus() {
	        return this.name();
	    }
	}

	protected CoinType coinType;
	protected CoinValue coinValue;
	protected CoinColor coinColor;
	protected CoinColor topCoinColor;
	protected CoinColor bottomCoinColor;

	protected TextureRegion coinRegionWholeA;
	protected TextureRegion coinLowerHalfA;
	protected TextureRegion coinUpperHalfA;

	protected TextureRegion coinRegionWholeB;
	protected TextureRegion coinLowerHalfB;
	protected TextureRegion coinUpperHalfB;

	protected TextureRegion[] coinRegions;

	private boolean isConnected = false;

	protected boolean isShining = false;
	protected float shine = 1;
	private float rotation = 0;
	protected long startShineDelay = -1;
	protected long delay = -1;
	protected boolean startShine = false;
	protected boolean show = true;
	
	private String coinID;
	
	public Coin(){
		
	}

	public Coin(String coinID){
		this.coinID = coinID;
	}

	/**
	 * Use only for coin comparison.
	 */
	public Coin(CoinType coinType, CoinValue coinValue, CoinColor coinColor){
		this.coinType = coinType;
		this.coinValue = coinValue;
		this.coinColor = coinColor;
	}

	public void setWholeCoin(CoinValue coinValue, CoinColor coinColor){
		coinType = CoinType.WHOLE;
		this.coinValue = coinValue;
		this.coinColor = coinColor;

		switch (coinValue){
		case ONE: {
			switch (coinColor){
			case BRONZE: {
				coinRegionWholeA = CoinAssets.bronzeWhole1A; 
				coinRegionWholeB = CoinAssets.bronzeWhole1B;
			}; break;
			case SILVER: {
				coinRegionWholeA = CoinAssets.silverWhole1A; 
				coinRegionWholeB = CoinAssets.silverWhole1B;
			}; break;
			case GOLD: {
				coinRegionWholeA = CoinAssets.goldWhole1A; 
				coinRegionWholeB = CoinAssets.goldWhole1B;
			}; break;
			default: break;
			}

			coinRegions = new TextureRegion[1];
			coinRegions[0] = coinRegionWholeA;
		}; break;
		case FIVE: {
			switch (coinColor){
			case BRONZE: {
				coinRegionWholeA = CoinAssets.bronzeWhole5A; 
				coinRegionWholeB = CoinAssets.bronzeWhole5B;
			}; break;
			case SILVER: {
				coinRegionWholeA = CoinAssets.silverWhole5A; 
				coinRegionWholeB = CoinAssets.silverWhole5B;
			}; break;
			case GOLD: {
				coinRegionWholeA = CoinAssets.goldWhole5A; 
				coinRegionWholeB = CoinAssets.goldWhole5B;
			}; break;
			default: break;
			}

			coinRegions = new TextureRegion[1];
			coinRegions[0] = coinRegionWholeA;
		}; break;
		case TEN: {
			switch (coinColor){
			case BRONZE: {
				coinRegionWholeA = CoinAssets.bronzeWhole10A; 
				coinRegionWholeB = CoinAssets.bronzeWhole10B;
			}; break;
			case SILVER: {
				coinRegionWholeA = CoinAssets.silverWhole10A; 
				coinRegionWholeB = CoinAssets.silverWhole10B;
			}; break;
			case GOLD: {
				coinRegionWholeA = CoinAssets.goldWhole10A; 
				coinRegionWholeB = CoinAssets.goldWhole10B;
			}; break;
			default: break;
			}

			coinRegions = new TextureRegion[1];
			coinRegions[0] = coinRegionWholeA;
		}; break;
		default: break;
		}
	}

	public void setThirdCoin(CoinValue coinValue){
		coinType = CoinType.THIRD;
		this.coinValue = coinValue;

		switch (coinValue){
		case ONE: {
			coinRegionWholeA = CoinAssets.third1A;
			coinRegionWholeB = CoinAssets.third1B;

			coinRegions = new TextureRegion[1];
			coinRegions[0] = coinRegionWholeA;
		}; break;
		case FIVE: {
			coinRegionWholeA = CoinAssets.third5A;
			coinRegionWholeB = CoinAssets.third5B;

			coinRegions = new TextureRegion[1];
			coinRegions[0] = coinRegionWholeA;
		}; break;
		case TEN: {
			coinRegionWholeA = CoinAssets.third10A;
			coinRegionWholeB = CoinAssets.third10B;

			coinRegions = new TextureRegion[1];
			coinRegions[0] = coinRegionWholeA;
		}; break;
		default: break;
		}
	}

	public void setHalfCoin(CoinValue coinValue, CoinColor topColor, CoinColor bottomColor){
		coinType = CoinType.HALF;
		this.coinValue = coinValue;
		this.topCoinColor = topColor;
		this.bottomCoinColor = bottomColor;

		switch (coinValue){
		case ONE: {
			switch (topColor){
			case BRONZE: {
				switch (bottomColor){
				case BRONZE: {
					//					Throw Exception
				}; break;
				case SILVER: {
					coinUpperHalfA = CoinAssets.bronzeTop1A;
					coinLowerHalfA = CoinAssets.silverBottom1A;
					coinUpperHalfB = CoinAssets.bronzeTop1B;
					coinLowerHalfB = CoinAssets.silverBottom1B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case GOLD: {
					coinUpperHalfA = CoinAssets.bronzeTop1A;
					coinLowerHalfA = CoinAssets.goldBottom1A;
					coinUpperHalfB = CoinAssets.bronzeTop1B;
					coinLowerHalfB = CoinAssets.goldBottom1B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				default: break;
				}
			}; break;
			case SILVER: {
				switch (bottomColor){
				case BRONZE: {
					coinUpperHalfA = CoinAssets.silverTop1A;
					coinLowerHalfA = CoinAssets.bronzeBottom1A;
					coinUpperHalfB = CoinAssets.silverTop1B;
					coinLowerHalfB = CoinAssets.bronzeBottom1B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case SILVER: {
					//					Throw Exception
				}; break;
				case GOLD: {
					coinUpperHalfA = CoinAssets.silverTop1A;
					coinLowerHalfA = CoinAssets.goldBottom1A;
					coinUpperHalfB = CoinAssets.silverTop1B;
					coinLowerHalfB = CoinAssets.goldBottom1B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				default: break;
				}
			}; break;
			case GOLD: {
				switch (bottomColor){
				case BRONZE: {
					coinUpperHalfA = CoinAssets.goldTop1A;
					coinLowerHalfA = CoinAssets.bronzeBottom1A;
					coinUpperHalfB = CoinAssets.goldTop1B;
					coinLowerHalfB = CoinAssets.bronzeBottom1B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case SILVER: {
					coinUpperHalfA = CoinAssets.goldTop1A;
					coinLowerHalfA = CoinAssets.silverBottom1A;
					coinUpperHalfB = CoinAssets.goldTop1B;
					coinLowerHalfB = CoinAssets.silverBottom1B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case GOLD: {
					//					Throw Exception
				}; break;
				default: break;
				}
			}; break;
			default: break;
			}
		}; break;
		case FIVE: {
			switch (topColor){
			case BRONZE: {
				switch (bottomColor){
				case BRONZE: {
					//					Throw Exception
				}; break;
				case SILVER: {
					coinUpperHalfA = CoinAssets.bronzeTop5A;
					coinLowerHalfA = CoinAssets.silverBottom5A;
					coinUpperHalfB = CoinAssets.bronzeTop5B;
					coinLowerHalfB = CoinAssets.silverBottom5B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case GOLD: {
					coinUpperHalfA = CoinAssets.bronzeTop5A;
					coinLowerHalfA = CoinAssets.goldBottom5A;
					coinUpperHalfB = CoinAssets.bronzeTop5B;
					coinLowerHalfB = CoinAssets.goldBottom5B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				default: break;
				}
			}; break;
			case SILVER: {
				switch (bottomColor){
				case BRONZE: {
					coinUpperHalfA = CoinAssets.silverTop5A;
					coinLowerHalfA = CoinAssets.bronzeBottom5A;
					coinUpperHalfB = CoinAssets.silverTop5B;
					coinLowerHalfB = CoinAssets.bronzeBottom5B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case SILVER: {
					//					Throw Exception
				}; break;
				case GOLD: {
					coinUpperHalfA = CoinAssets.silverTop5A;
					coinLowerHalfA = CoinAssets.goldBottom5A;
					coinUpperHalfB = CoinAssets.silverTop5B;
					coinLowerHalfB = CoinAssets.goldBottom5B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				default: break;
				}
			}; break;
			case GOLD: {
				switch (bottomColor){
				case BRONZE: {
					coinUpperHalfA = CoinAssets.goldTop5A;
					coinLowerHalfA = CoinAssets.bronzeBottom5A;
					coinUpperHalfB = CoinAssets.goldTop5B;
					coinLowerHalfB = CoinAssets.bronzeBottom5B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case SILVER: {
					coinUpperHalfA = CoinAssets.goldTop5A;
					coinLowerHalfA = CoinAssets.silverBottom5A;
					coinUpperHalfB = CoinAssets.goldTop5B;
					coinLowerHalfB = CoinAssets.silverBottom5B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case GOLD: {
					//					Throw Exception
				}; break;
				default: break;
				}
			}; break;
			default: break;
			}
		}; break;
		case TEN: {
			switch (topColor){
			case BRONZE: {
				switch (bottomColor){
				case BRONZE: {
					//					Throw Exception
				}; break;
				case SILVER: {
					coinUpperHalfA = CoinAssets.bronzeTop10A;
					coinLowerHalfA = CoinAssets.silverBottom10A;
					coinUpperHalfB = CoinAssets.bronzeTop10B;
					coinLowerHalfB = CoinAssets.silverBottom10B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case GOLD: {
					coinUpperHalfA = CoinAssets.bronzeTop10A;
					coinLowerHalfA = CoinAssets.goldBottom10A;
					coinUpperHalfB = CoinAssets.bronzeTop10B;
					coinLowerHalfB = CoinAssets.goldBottom10B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				default: break;
				}
			}; break;
			case SILVER: {
				switch (bottomColor){
				case BRONZE: {
					coinUpperHalfA = CoinAssets.silverTop10A;
					coinLowerHalfA = CoinAssets.bronzeBottom10A;
					coinUpperHalfB = CoinAssets.silverTop10B;
					coinLowerHalfB = CoinAssets.bronzeBottom10B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case SILVER: {
					//					Throw Exception
				}; break;
				case GOLD: {
					coinUpperHalfA = CoinAssets.silverTop10A;
					coinLowerHalfA = CoinAssets.goldBottom10A;
					coinUpperHalfB = CoinAssets.silverTop10B;
					coinLowerHalfB = CoinAssets.goldBottom10B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				default: break;
				}
			}; break;
			case GOLD: {
				switch (bottomColor){
				case BRONZE: {
					coinUpperHalfA = CoinAssets.goldTop10A;
					coinLowerHalfA = CoinAssets.bronzeBottom10A;
					coinUpperHalfB = CoinAssets.goldTop10B;
					coinLowerHalfB = CoinAssets.bronzeBottom10B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case SILVER: {
					coinUpperHalfA = CoinAssets.goldTop10A;
					coinLowerHalfA = CoinAssets.silverBottom10A;
					coinUpperHalfB = CoinAssets.goldTop10B;
					coinLowerHalfB = CoinAssets.silverBottom10B;

					coinRegions = new TextureRegion[2];
					coinRegions[0] = coinUpperHalfA;
					coinRegions[1] = coinLowerHalfA;
				}; break;
				case GOLD: {
					//					Throw Exception
				}; break;
				default: break;
				}
			}; break;
			default: break;
			}
		}; break;
		default: break;
		}
	}

	public void render(SpriteBatch batch, float x, float y){
		switch (coinType){
		case WHOLE: {
			if (!isConnected) batch.draw(coinRegionWholeA, x, y, 216, 216);
			else batch.draw(coinRegionWholeB, x, y, 216, 216);
		}; break;
		case THIRD: {
			if (!isConnected) batch.draw(coinRegionWholeA, x, y, 216, 216);
			else batch.draw(coinRegionWholeB, x, y, 216, 216);
		}; break;
		case HALF: {
			if (!isConnected){
				batch.draw(coinUpperHalfA, x, y, 216, 216);
				batch.draw(coinLowerHalfA, x, y, 216, 216);
			}
			else {
				batch.draw(coinUpperHalfB, x, y, 216, 216);
				batch.draw(coinLowerHalfB, x, y, 216, 216);
			}
		}; break;
		default: break;
		}

		if (isShining){
			if (startShineDelay == -1){
				float shineIncrement = Gdx.graphics.getDeltaTime() * 3f;
				Color bColor = batch.getColor();
				rotation += shineIncrement * 1.5f;
				if (startShine){
					if (show){
						shine += shineIncrement;
						if (shine >= 1) show = false;
					}
					else {
						if (shine <= 0){
							if (delay == -1) delay = TimeUtils.millis();
							if (TimeUtils.millis() - delay > 4000){
								delay = -1;
								show = true;
							}
						}
						else shine -= shineIncrement;
					}
					shine = MathUtils.clamp(shine, 0, 1);
					batch.setColor(bColor.r, bColor.g, bColor.b, shine);
					if (shine > 0) batch.draw(CoinAssets.shineRegion, x, y, 108, 108, 216, 216, 1, 1, rotation);
				}
				batch.setColor(bColor);
			}
			else {
				if (TimeUtils.millis() - startShineDelay > 500) startShineDelay = -1;
			}
		}
	}

	public TextureRegion[] getCoinRegions() {
		return coinRegions;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public boolean isShining() {
		return isShining;
	}

	public void setShining(boolean isShining) {
		if (isShining){
			restartShiningAnim();
		}
		this.isShining = isShining;
	}
	
	public void restartShiningAnim(){
		startShine = true;
		show = true;
		shine = 0;
		rotation = 0;
		startShineDelay = TimeUtils.millis();
		startShineDelay = -1;
		delay = -1;
		
		isConnected = false;
	}
	
	public void resetCoin(){
		if (isShining) restartShiningAnim();
	}
	
	public CoinType getCoinType() {
		return coinType;
	}

	public CoinValue getCoinValue() {
		return coinValue;
	}

	public CoinColor getCoinColor() {
		return coinColor;
	}

	public CoinColor getTopCoinColor() {
		return topCoinColor;
	}

	public CoinColor getBottomCoinColor() {
		return bottomCoinColor;
	}

	public String getCoinID() {
		return coinID;
	}

	public void setCoinID(String coinID) {
		this.coinID = coinID;
	}
}
