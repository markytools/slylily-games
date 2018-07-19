package com.connectcoins.coins;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.game.ConnectCoins;

public class CoinAssets implements Disposable {
	public static class CoinCurrency {
		public static final String NORMAL = "NORMAL";
		public static final String AUD = "AUD";
		public static final String BRL = "BRL";
		public static final String CAD = "CAD";
		public static final String CHF = "CHF";
		public static final String CNY = "CNY";
		public static final String EURO = "EURO";
		public static final String GBP = "GBP";
		public static final String INR = "INR";
		public static final String JPY = "JPY";
		public static final String KRW = "KRW";
		public static final String MXN = "MXN";
		public static final String NZD = "NZD";
		public static final String PHP = "PHP";
		public static final String RUB = "RUB";
		public static final String SEK = "SEK";
		public static final String SGP = "SGP";
		public static final String USD = "USD";
		public static final String CLOVER = "clover";
		public static final String DIAMOND = "diamond";
		public static final String HEART = "heart";
		public static final String SPADE = "spade";
	}

	private ConnectCoins game;

	public static TextureRegion bronzeTop1A;
	public static TextureRegion bronzeTop5A;
	public static TextureRegion bronzeTop10A;
	public static TextureRegion silverTop1A;
	public static TextureRegion silverTop5A;
	public static TextureRegion silverTop10A;
	public static TextureRegion goldTop1A;
	public static TextureRegion goldTop5A;
	public static TextureRegion goldTop10A;

	public static TextureRegion bronzeBottom1A;
	public static TextureRegion bronzeBottom5A;
	public static TextureRegion bronzeBottom10A;
	public static TextureRegion silverBottom1A;
	public static TextureRegion silverBottom5A;
	public static TextureRegion silverBottom10A;
	public static TextureRegion goldBottom1A;
	public static TextureRegion goldBottom5A;
	public static TextureRegion goldBottom10A;


	public static TextureRegion third1A;
	public static TextureRegion third5A;
	public static TextureRegion third10A;


	public static TextureRegion bronzeWhole1A;
	public static TextureRegion bronzeWhole5A;
	public static TextureRegion bronzeWhole10A;
	public static TextureRegion goldWhole1A;
	public static TextureRegion goldWhole5A;
	public static TextureRegion goldWhole10A;
	public static TextureRegion silverWhole1A;
	public static TextureRegion silverWhole5A;
	public static TextureRegion silverWhole10A;




	public static TextureRegion bronzeTop1B;
	public static TextureRegion bronzeTop5B;
	public static TextureRegion bronzeTop10B;
	public static TextureRegion silverTop1B;
	public static TextureRegion silverTop5B;
	public static TextureRegion silverTop10B;
	public static TextureRegion goldTop1B;
	public static TextureRegion goldTop5B;
	public static TextureRegion goldTop10B;

	public static TextureRegion bronzeBottom1B;
	public static TextureRegion bronzeBottom5B;
	public static TextureRegion bronzeBottom10B;
	public static TextureRegion silverBottom1B;
	public static TextureRegion silverBottom5B;
	public static TextureRegion silverBottom10B;
	public static TextureRegion goldBottom1B;
	public static TextureRegion goldBottom5B;
	public static TextureRegion goldBottom10B;


	public static TextureRegion third1B;
	public static TextureRegion third5B;
	public static TextureRegion third10B;


	public static TextureRegion bronzeWhole1B;
	public static TextureRegion bronzeWhole5B;
	public static TextureRegion bronzeWhole10B;
	public static TextureRegion goldWhole1B;
	public static TextureRegion goldWhole5B;
	public static TextureRegion goldWhole10B;
	public static TextureRegion silverWhole1B;
	public static TextureRegion silverWhole5B;
	public static TextureRegion silverWhole10B;


	public static TextureRegion shineRegion;


	public CoinAssets(ConnectCoins game){
		this.game = game;
	}

	public void setCoinAssets(String coinCurrency){
		game.assetLoader.assetType.setCurrentCoinAtlas(coinCurrency, false);

		bronzeTop1A = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeTopHalf1A");
		bronzeTop5A = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeTopHalf5A");
		bronzeTop10A = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeTopHalf10A");
		silverTop1A = game.assetLoader.assetType.getCoinRegion("halfCoins/silverTopHalf1A");
		silverTop5A = game.assetLoader.assetType.getCoinRegion("halfCoins/silverTopHalf5A");
		silverTop10A = game.assetLoader.assetType.getCoinRegion("halfCoins/silverTopHalf10A");
		goldTop1A = game.assetLoader.assetType.getCoinRegion("halfCoins/goldTopHalf1A");
		goldTop5A = game.assetLoader.assetType.getCoinRegion("halfCoins/goldTopHalf5A");
		goldTop10A = game.assetLoader.assetType.getCoinRegion("halfCoins/goldTopHalf10A");

		bronzeBottom1A = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeBottomHalf1A");
		bronzeBottom5A = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeBottomHalf5A");
		bronzeBottom10A = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeBottomHalf10A");
		silverBottom1A = game.assetLoader.assetType.getCoinRegion("halfCoins/silverBottomHalf1A");
		silverBottom5A = game.assetLoader.assetType.getCoinRegion("halfCoins/silverBottomHalf5A");
		silverBottom10A = game.assetLoader.assetType.getCoinRegion("halfCoins/silverBottomHalf10A");
		goldBottom1A = game.assetLoader.assetType.getCoinRegion("halfCoins/goldBottomHalf1A");
		goldBottom5A = game.assetLoader.assetType.getCoinRegion("halfCoins/goldBottomHalf5A");
		goldBottom10A = game.assetLoader.assetType.getCoinRegion("halfCoins/goldBottomHalf10A");

		third1A = game.assetLoader.assetType.getCoinRegion("thirdCoins/1A");
		third5A = game.assetLoader.assetType.getCoinRegion("thirdCoins/5A");
		third10A = game.assetLoader.assetType.getCoinRegion("thirdCoins/10A");

		bronzeWhole1A = game.assetLoader.assetType.getCoinRegion("wholeCoins/bronze1A");
		bronzeWhole5A = game.assetLoader.assetType.getCoinRegion("wholeCoins/bronze5A");
		bronzeWhole10A = game.assetLoader.assetType.getCoinRegion("wholeCoins/bronze10A");
		silverWhole1A = game.assetLoader.assetType.getCoinRegion("wholeCoins/silver1A");
		silverWhole5A = game.assetLoader.assetType.getCoinRegion("wholeCoins/silver5A");
		silverWhole10A = game.assetLoader.assetType.getCoinRegion("wholeCoins/silver10A");
		goldWhole1A = game.assetLoader.assetType.getCoinRegion("wholeCoins/gold1A");
		goldWhole5A = game.assetLoader.assetType.getCoinRegion("wholeCoins/gold5A");
		goldWhole10A = game.assetLoader.assetType.getCoinRegion("wholeCoins/gold10A");



		bronzeTop1B = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeTopHalf1B");
		bronzeTop5B = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeTopHalf5B");
		bronzeTop10B = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeTopHalf10B");
		silverTop1B = game.assetLoader.assetType.getCoinRegion("halfCoins/silverTopHalf1B");
		silverTop5B = game.assetLoader.assetType.getCoinRegion("halfCoins/silverTopHalf5B");
		silverTop10B = game.assetLoader.assetType.getCoinRegion("halfCoins/silverTopHalf10B");
		goldTop1B = game.assetLoader.assetType.getCoinRegion("halfCoins/goldTopHalf1B");
		goldTop5B = game.assetLoader.assetType.getCoinRegion("halfCoins/goldTopHalf5B");
		goldTop10B = game.assetLoader.assetType.getCoinRegion("halfCoins/goldTopHalf10B");

		bronzeBottom1B = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeBottomHalf1B");
		bronzeBottom5B = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeBottomHalf5B");
		bronzeBottom10B = game.assetLoader.assetType.getCoinRegion("halfCoins/bronzeBottomHalf10B");
		silverBottom1B = game.assetLoader.assetType.getCoinRegion("halfCoins/silverBottomHalf1B");
		silverBottom5B = game.assetLoader.assetType.getCoinRegion("halfCoins/silverBottomHalf5B");
		silverBottom10B = game.assetLoader.assetType.getCoinRegion("halfCoins/silverBottomHalf10B");
		goldBottom1B = game.assetLoader.assetType.getCoinRegion("halfCoins/goldBottomHalf1B");
		goldBottom5B = game.assetLoader.assetType.getCoinRegion("halfCoins/goldBottomHalf5B");
		goldBottom10B = game.assetLoader.assetType.getCoinRegion("halfCoins/goldBottomHalf10B");

		third1B = game.assetLoader.assetType.getCoinRegion("thirdCoins/1B");
		third5B = game.assetLoader.assetType.getCoinRegion("thirdCoins/5B");
		third10B = game.assetLoader.assetType.getCoinRegion("thirdCoins/10B");

		bronzeWhole1B = game.assetLoader.assetType.getCoinRegion("wholeCoins/bronze1B");
		bronzeWhole5B = game.assetLoader.assetType.getCoinRegion("wholeCoins/bronze5B");
		bronzeWhole10B = game.assetLoader.assetType.getCoinRegion("wholeCoins/bronze10B");
		silverWhole1B = game.assetLoader.assetType.getCoinRegion("wholeCoins/silver1B");
		silverWhole5B = game.assetLoader.assetType.getCoinRegion("wholeCoins/silver5B");
		silverWhole10B = game.assetLoader.assetType.getCoinRegion("wholeCoins/silver10B");
		goldWhole1B = game.assetLoader.assetType.getCoinRegion("wholeCoins/gold1B");
		goldWhole5B = game.assetLoader.assetType.getCoinRegion("wholeCoins/gold5B");
		goldWhole10B = game.assetLoader.assetType.getCoinRegion("wholeCoins/gold10B");

		shineRegion = new TextureRegion(game.assetLoader.getTexture("shine"));

	}

	@Override
	public void dispose() {
		game = null;
		bronzeTop1A = null;
		bronzeTop5A = null;
		bronzeTop10A = null;
		silverTop1A = null;
		silverTop5A = null;
		silverTop10A = null;
		goldTop1A = null;
		goldTop5A = null;
		goldTop10A = null;

		bronzeBottom1A = null;
		bronzeBottom5A = null;
		bronzeBottom10A = null;
		silverBottom1A = null;
		silverBottom5A = null;
		silverBottom10A = null;
		goldBottom1A = null;
		goldBottom5A = null;
		goldBottom10A = null;


		third1A = null;
		third5A = null;
		third10A = null;


		bronzeWhole1A = null;
		bronzeWhole5A = null;
		bronzeWhole10A = null;
		goldWhole1A = null;
		goldWhole5A = null;
		goldWhole10A = null;
		silverWhole1A = null;
		silverWhole5A = null;
		silverWhole10A = null;




		bronzeTop1B = null;
		bronzeTop5B = null;
		bronzeTop10B = null;
		silverTop1B = null;
		silverTop5B = null;
		silverTop10B = null;
		goldTop1B = null;
		goldTop5B = null;
		goldTop10B = null;

		bronzeBottom1B = null;
		bronzeBottom5B = null;
		bronzeBottom10B = null;
		silverBottom1B = null;
		silverBottom5B = null;
		silverBottom10B = null;
		goldBottom1B = null;
		goldBottom5B = null;
		goldBottom10B = null;


		third1B = null;
		third5B = null;
		third10B = null;


		bronzeWhole1B = null;
		bronzeWhole5B = null;
		bronzeWhole10B = null;
		goldWhole1B = null;
		goldWhole5B = null;
		goldWhole10B = null;
		silverWhole1B = null;
		silverWhole5B = null;
		silverWhole10B = null;
	}
}