package com.connectcoins.languages;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.connectcoins.game.ConnectCoins;

public class LanguageManager {
	public static final String ENGLISH = "English";
	
	public static Array<String> tutorial_en;	//English
	
	public static ArrayMap<String, String> button_en;

	public static ArrayMap<String, String> options_en;

	public static Array<String> tutorial_CURRENT;
	public static ArrayMap<String, String> button_CURRENT;
	public static ArrayMap<String, String> options_CURRENT;
	public static String currentLanguage;
	
	public LanguageManager(ConnectCoins game){
		tutorial_en = new Array<String>();
		
		button_en = new ArrayMap<String, String>();
		
		options_en = new ArrayMap<String, String>();
		
		tutorial_CURRENT = new Array<String>();
		button_CURRENT = new ArrayMap<String, String>();
		options_CURRENT = new ArrayMap<String, String>();

		setupTutorialLanguagePacks();
	}
	
	private void setupTutorialLanguagePacks(){
		loadAllPack();
	}
	
	public void loadAllPack() {
		loadLanguagePack(LanguageManager.ENGLISH);
	}

	public void loadLanguagePack(String language){
		tutorial_CURRENT.clear();
		button_CURRENT.clear();
		options_CURRENT.clear();
		switch (language){
		case ENGLISH: {
			tutorial_en.add("Welcome to Connect Coins. The objective of this game is to have the highest score by connecting coins to create combos.");
			tutorial_en.add("You will be doing this in a limited amount of time. But for the sake of this tutorial, you will be given unlimited time to connect the coins.");
			tutorial_en.add("All in all, there are 25 coins.  Connecting multiple coins is just simple. Just touch a coin, then drag your finger on another nearby coin.");
			tutorial_en.add("You can do this vertically, horizontally, or diagonally, as long as that coin has not been connected yet. Do this successively, then release your finger whenever you want to in order to submit the connected coins.");
			tutorial_en.add("You can only have a maximum of 5 connected coins. Connect at least 3 coins right now.");
			tutorial_en.add("Good. Now we move on to the coins. As you can see, each coin represents a certain amount of data.");
			tutorial_en.add("The first data is its value. There are 3 coin values: 1, 5, and 10. The second data is the color. There are also 3 colors: BRONZE, SILVER, and GOLD. Each coin contains at least one of each of these data.");
			tutorial_en.add("However as you can see, some coins have two and even three colors. Coins with 2 colors are called HALF coins. Coins with all 3 colors are called THIRD coins.");
			tutorial_en.add("A HALF coin has 2 colors, one on its top, and another different one on its bottom. For now, just think that this coin can be represented into both of these two colors.");
			tutorial_en.add("The THIRD coin has all the three colors in it, so it is able to represent any of the 3 colors. You only need to know this now because you will be able to use this when you have already known the combos.");
			tutorial_en.add("If you see glitters in a coin, that means that it is a shiny coin. Depending on the number of shiny coins that you have, each will multiply the total score of that connection by 1 plus the total number of shiny coins.");
			tutorial_en.add("So if you have connected five shiny coins, then the total score of that connection will be 1 + 5 = 6 TIMES HIGHER! Now create a  connection that has a shiny coin.");
			tutorial_en.add("Great. Now we know how to connect the coins. So now we move on to the combos.");
			tutorial_en.add("Click the heat bar layout below the coins to open up the game menu. Then click combo list to open the combo panel. Alternatively, you can directly open the combo panel by long pressing it.");
			tutorial_en.add("This is the combo panel. It lists all of the possible combos after connecting the coins. There are a total of six possible combos and are rated from highest to lowest.");
			tutorial_en.add("Upon releasing the connected coins, it automatically chooses the highest possible combo from this list as your score or none if no combo is detected.");
			tutorial_en.add("Combos are essential to obtaining a big score. A better combo means a higher score for each of the coins. So before proceeding, please familiarize yourself with the combos on the list. Once done, you may exit back.");
			tutorial_en.add("Now that you know about the different combos, we will now explain the uses of the HALF and the THIRD coin.");
			tutorial_en.add("Because a HALF coin has 2 colors, upon creating a connection, the game automatically selects the best combo that can be formed. It will use either the top or the bottom color whichever of which creates the best combo.");
			tutorial_en.add("The THIRD coin is just similar to the HALF coin, only that it has all the three colors, making it very convenient to use.");
			tutorial_en.add("Create a combo with a HALF or THIRD coin.");
			tutorial_en.add("Good job. Next is the ON FIRE mode. The bar at the bottom represents heat. When scoring a combo, heat is generated and the bar increases in length. Of course, the better the combo, the greater the heat bar increases.");
			tutorial_en.add("Once it reaches the end, you are now ON FIRE. When ON FIRE, try creating a connection with only one coin and see what happens.");
			tutorial_en.add("Well done. ON FIRE mode greatly reduces the time to connect the coins because it automatically creates a connection by selecting nearby coins to form the best possible combo that can be formed.");
			tutorial_en.add("ON FIRE mode is only active for a limited time, so use it to your full advantage.");
			tutorial_en.add("The last thing that you need to know about the game is how to illuminate your coins. Illumination is a very easy and powerful action that you can do to greatly boost your score.");
			tutorial_en.add("Now we know that shiny coins multiply your score by a certain number, would you not like to have a lot of them? Illumination exactly does that. It makes all visible coins on the screen shine. So how do we do that?");
			tutorial_en.add("Although not required, it is recommended that you hear the game sounds so that you will be able to know your counting point before illumination happens.");
			tutorial_en.add("Illumination is achieved by connecting 8 combo FLUSHES (Small, Normal, Big) CONSECUTIVELY in a row. After that, your coins will then be illuminated. Try illuminating your coins now.");
			tutorial_en.add("Nicely done! You have now finished the tutorial and are ready to play the game. You can either exit or still continue without having any score. GOOD LUCK connecting coins!");
			
			button_en.put("Play", "Play");
			button_en.put("Challenges", "Challenges");
			button_en.put("Multiplayer", "Multiplayer");
			button_en.put("Store", "Store");
			button_en.put("Awards", "Awards");
			button_en.put("Combo List", "Combo List");
			button_en.put("Options", "Options");
			button_en.put("Tutorial", "Tutorial");
			button_en.put("Quit", "Quit");
			button_en.put("Quit Game", "Quit Game");
			button_en.put("Play Again", "Play Again");
			button_en.put("Post Score", "Post Score");
			button_en.put("Back", "Back");
			button_en.put("Challenge 1", "Challenge 1");
			button_en.put("Challenge 2", "Challenge 2");
			button_en.put("Challenge 3", "Challenge 3");
			button_en.put("Challenge 4", "Challenge 4");
			button_en.put("Challenge 5", "Challenge 5");
			button_en.put("Next Puzzle", "Next Puzzle");
			button_en.put("Best Scores", "Best Scores");
			button_en.put("Achievements", "Achievements");
			button_en.put("Statistics", "Statistics");
			button_en.put("Rate Game", "Rate Game");
			button_en.put("More Apps", "More Apps");
			button_en.put("Share", "Share");
			button_en.put("Find a Match", "Find a Match");
			button_en.put("Custom Match", "Custom Match");
			button_en.put("Player Info", "Player Info");
			button_en.put("Top Players", "Top Players");
			button_en.put("Undo", "Undo");
			button_en.put("Restart", "Restart");
			button_en.put("Sign Out", "Sign Out");
			button_en.put("Players", "Players");
			button_en.put("Invite", "Invite");
			button_en.put("Message", "Message");
			button_en.put("Faces", "Faces");
			button_en.put("Befriend", "Befriend");
			button_en.put("Unfriend", "Unfriend");
			button_en.put("Rematch", "Rematch");
			button_en.put("Extras", "Extras");
			button_en.put("Like", "Like");
			button_en.put("Follow", "Follow");
			button_en.put("+15000 CC", "+15000 CC");
			button_en.put("+40000 CC", "+40000 CC");
			button_en.put("Post FB", "Post FB");
			button_en.put("Tweet TW", "Tweet TW");
			
			options_en.put("Language", "Language");
			options_en.put("Graphics", "Graphics");
			options_en.put("Vibration", "Vibration");
			options_en.put("Background Animation", "Background Animation");
			options_en.put("Profile Face", "Profile Face");
			options_en.put("Restart required", "Restart required");
			options_en.put("Music", "Music");
			
			tutorial_CURRENT = new Array<String>(tutorial_en);
			button_CURRENT = new ArrayMap<String, String>(button_en);
			options_CURRENT = new ArrayMap<String, String>(options_en);
			
			currentLanguage = ENGLISH;
		}; break;
		default: break;
		}
	}
	
	public void unloadAll(){	
		tutorial_en.clear();
		
		button_en.clear();

		options_en.clear();
		
		tutorial_CURRENT.clear();
		button_CURRENT.clear();
		options_CURRENT.clear();
	}
	
	public void load_en(){
		tutorial_en.add("");
	}
	
	public void unload_en(){
		tutorial_en.clear();
		button_en.clear();
	}
	
	public String getCurrentLanguage() {
		return currentLanguage;
	}
}
