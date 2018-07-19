package com.connectcoins.store;

import java.util.HashMap;

public class ItemConstants {
	final String NO_ADS = "No Ads";
	final String CHALLENGE_2 = "Challenge 2";
	final String CHALLENGE_3 = "Challenge 3";
	final String CHALLENGE_4 = "Challenge 4";
	final String CHALLENGE_5 = "Challenge 5";
	final String HANDFUL_OF_CC = "Handful of CC";
	final String POUCH_OF_CC = "Pouch of CC";
	final String BAG_OF_CC = "Bag of CC";
	final String SACK_OF_CC = "Sack of CC";
	final String CHEST_OF_CC = "Chest of CC";

	final String FIRED_UP = "Fired Up";
	final String SHINE_ALL_THE_WAY = "Shine All The Way";
	final String LUCKY_HALF = "Lucky Half";
	final String LUCKY_THIRD = "Lucky Third";
	final String SUPER_BOOSTER = "Super Booster";
	
	final String TEXTURE_DESC = "Unlockable Connect Coins textures both for single and multiplayer games.";
	final String UNLOCKABLES_DESC = "Items to unlock certain game features.";
	final String BOOSTERS_DESC = "Boosters to help increase game rewards.";
	
	final String NORMAL_FACE_DESC = "Face for multiplayer.";

	public static final String NORMAL = "Normal";
	public static final String AUD = "Australian Dollar";
	public static final String BRL = "Brazilian Real";
	public static final String CAD = "Canadian Dollar";
	public static final String CHF = "Swiss Franc";
	public static final String CNY = "Chinese Yuan";
	public static final String EURO = "Euro";
	public static final String GBP = "British Pound";
	public static final String INR = "Indian Rupee";
	public static final String JPY = "Japanese Yen";
	public static final String KRW = "Korean Won";
	public static final String MXN = "Mexican Peso";
	public static final String NZD = "New Zealand Dollar";
	public static final String PHP = "Philippine Peso";
	public static final String RUB = "Russian Ruble";
	public static final String SEK = "Swedish Krona";
	public static final String SGP = "Singaporean Dollar";
	public static final String USD = "US Dollar";
	public static final String CLOVER = "Clover";
	public static final String DIAMOND = "Diamond";
	public static final String HEART = "Heart";
	public static final String SPADE = "Spade";
	
//	public static final String FACE_1 = "Smile";
//	public static final String FACE_2 = "Smirk";
//	public static final String FACE_3 = "Yawn";
//	public static final String FACE_4 = "Shock";
//	public static final String FACE_5 = "Playful";
//	public static final String FACE_6 = "Irritated";
//	public static final String FACE_7 = "Grin";
//	public static final String FACE_8 = "Heart Eyes";
//	public static final String FACE_9 = "Thumbs Up";
//	public static final String FACE_10 = "Double Thumbs Up";
//	public static final String FACE_11 = "Pig";
//	public static final String FACE_12 = "Upset";
//	public static final String FACE_13 = "Evil";
//	public static final String FACE_14 = "Dead";
//	public static final String FACE_15 = "Thumbs Down";
//	public static final String FACE_16 = "Cheeky";
//	public static final String FACE_17 = "Indecisive";
//	public static final String FACE_18 = "Annoyed";
//	public static final String FACE_19 = "Surprised";
//	public static final String FACE_20 = "Crying";
//	public static final String FACE_21= "Sneer";
//	public static final String FACE_22 = "Idea";
//	public static final String FACE_23 = "Cat";
//	public static final String FACE_24 = "Angry";
//	public static final String FACE_25 = "Sigh";
//	public static final String FACE_26 = "Furious";
//	public static final String FACE_27 = "Grimace";
//	public static final String FACE_28 = "Whistling";
//	public static final String FACE_29 = "Radioactive";
//	public static final String FACE_30 = "Sad";
//	public static final String FACE_31 = "Butt";
//	public static final String FACE_32 = "Music";
//	public static final String FACE_33 = "Rich";
//	public static final String FACE_34 = "Asleep";
//	public static final String FACE_35 = "Anthrax";
//	public static final String FACE_36 = "Love";
//	public static final String FACE_37 = "Clown";
//	public static final String FACE_38 = "Angel";
//	public static final String FACE_39 = "Call";
//	public static final String FACE_40 = "Girl Face";
//	public static final String FACE_41 = "She-Cow";
//	public static final String FACE_42 = "Devil";
//	public static final String FACE_43 = "Murdered";
//	public static final String FACE_44 = "Shhh";
//	public static final String FACE_45 = "Cool";
//	public static final String FACE_46 = "Lips Sealed";
//	public static final String FACE_47 = "He-Cow";
//	public static final String FACE_48 = "Pirate";
//	public static final String FACE_49 = "Sick";
//	public static final String FACE_50 = "Blush";
//	public static final String FACE_51 = "Gasping";
//	public static final String FACE_52 = "Injured";
//	public static final String FACE_53 = "Stop";
//	public static final String FACE_54 = "Decline";
//	public static final String FACE_55 = "Hello";
//	public static final String FACE_56 = "Hault";
//	public static final String FACE_57 = "Builder";
//	public static final String FACE_58 = "Smile Teeth";
//	public static final String FACE_59 = "Vampire";
	
	HashMap<String, ItemPrice> itemPrices;
	
	public ItemConstants(){
		itemPrices = new HashMap<String, ItemPrice>();
		itemPrices.put(NO_ADS, new ItemPrice(200000, 0.99f, "Removes all in-game ads."));
		itemPrices.put(CHALLENGE_2, new ItemPrice(0, 0.99f, "Unlocks Challenge 2."));
		itemPrices.put(CHALLENGE_3, new ItemPrice(0, 0.99f, "Unlocks Challenge 3."));
		itemPrices.put(CHALLENGE_4, new ItemPrice(0, 0.99f, "Unlocks Challenge 4."));
		itemPrices.put(CHALLENGE_5, new ItemPrice(0, 0.99f, "Unlocks Challenge 5."));
		itemPrices.put(HANDFUL_OF_CC, new ItemPrice(0, 0.99f, "Buy 50000  CCs."));
		itemPrices.put(POUCH_OF_CC, new ItemPrice(0, 1.99f, "Buy 120000 CCs."));
		itemPrices.put(BAG_OF_CC, new ItemPrice(0, 3.99f, "Buy 250000 CCs."));
		itemPrices.put(SACK_OF_CC, new ItemPrice(0, 7.99f, "Buy 550000 CCs."));
		itemPrices.put(CHEST_OF_CC, new ItemPrice(0, 14.99f, "Buy 1150000 CCs."));
		
		itemPrices.put(FIRED_UP, new ItemPrice(200, 0, "Increases the duration of ON FIRE by 2 seconds. (SINGLE PLAYER Only)"));
		itemPrices.put(SHINE_ALL_THE_WAY, new ItemPrice(175, 0, "Doubles the chance of spawning shiny coins. (SINGLE PLAYER Only)"));
		itemPrices.put(LUCKY_HALF, new ItemPrice(100, 0, "Doubles the chance of spawning half coins. (SINGLE PLAYER Only)"));
		itemPrices.put(LUCKY_THIRD, new ItemPrice(50, 0, "Doubles the chance of spawning third coins. (SINGLE PLAYER Only)"));
		itemPrices.put(SUPER_BOOSTER, new ItemPrice(500, 0, "‘Fired Up’, ‘Shine All The Way’, ‘Lucky Half’, and "
				+ "‘Lucky Third’ all in one boost. (SINGLE PLAYER Only)"));
		
		itemPrices.put(NORMAL, new ItemPrice(0, 0, "Default Connect Coins texture."));
		itemPrices.put(AUD, new ItemPrice(75000, 0, "Australlian one dollar featuring five kangaroos that are native animals of the country."));
		itemPrices.put(BRL, new ItemPrice(150000, 0, "Brazillian one real coin with the Crux or Southern Cross, a constellation in the southern sky."));
		itemPrices.put(CAD, new ItemPrice(75000, 0, "Canadian one dollar with Common or Great Northern Loon."));
		itemPrices.put(CHF, new ItemPrice(200000, 0, "Swiss one franc coin along with the standing female personification of Switzerland, Helvetia."));
		itemPrices.put(CNY, new ItemPrice(45000, 0, "Chinese one yuan picturing the state seal."));
		itemPrices.put(EURO, new ItemPrice(75000, 0, "One euro depicting the European Union countries."));
		itemPrices.put(GBP, new ItemPrice(150000, 0, "British one pound featuring the profile of Queen Elizabeth II of the United Kingdom."));
		itemPrices.put(INR, new ItemPrice(75000, 0, "Indian five rupee coin showing the Hindu goddess, Vaishno Devi."));
		itemPrices.put(JPY, new ItemPrice(100000, 0, "Japanese 50 yen coin featuring a view of a chrysanthemum."));
		itemPrices.put(KRW, new ItemPrice(100000, 0, "50 Korean won with date of minting and bank title."));
		itemPrices.put(MXN, new ItemPrice(100000, 0, "Mexican five peso coin with the coat of arms of Mexico picturing a golden eagle."));
		itemPrices.put(NZD, new ItemPrice(200000, 0, "New Zealand two dollar coin showing an Eastern Great Egret native to the country."));
		itemPrices.put(PHP, new ItemPrice(50000, 0, "Philippine peso with logo of the Central Bank of the Philippines."));
		itemPrices.put(RUB, new ItemPrice(150000, 0, "Russian ruble showing the double-head eagle emblem of the Central Bank of Russia."));
		itemPrices.put(SEK, new ItemPrice(150000, 0, "Swedish one krona coin with a picture of King Carl XVI Gustaf of Sweden"));
		itemPrices.put(SGP, new ItemPrice(50000, 0, "Singaporean one dollar with the coat of arms of the country."));
		itemPrices.put(USD, new ItemPrice(50000, 0, "US half dollar coin featuring Pres. John Kennedy."));
		itemPrices.put(CLOVER, new ItemPrice(300000, 0, "Clover textured coin."));
		itemPrices.put(DIAMOND, new ItemPrice(300000, 0, "Diamond textured coin."));
		itemPrices.put(HEART, new ItemPrice(300000, 0, "Heart textured coin."));
		itemPrices.put(SPADE, new ItemPrice(300000, 0, "Spade textured coin."));
	}
}
