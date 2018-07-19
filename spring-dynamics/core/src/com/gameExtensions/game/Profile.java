package com.gameExtensions.game;

public class Profile {
	public int longestDistTraveled = 0;
	public boolean finishedTutorial = false;
	public boolean queueHighScore = false;
	public boolean noAds = false;
	
	public Profile() {
		noAds = false;
		longestDistTraveled =  0;
		finishedTutorial = false;
		queueHighScore = false;
	}
}