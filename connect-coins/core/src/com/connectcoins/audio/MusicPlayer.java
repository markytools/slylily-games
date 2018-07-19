package com.connectcoins.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.connectcoins.game.ConnectCoins;

public class MusicPlayer {
	private enum MusicPlayerState{
		READY, DELAY, NO_MUSIC, LOADING, START_PLAY, PLAYING
	}
	
	private ConnectCoins game;
	private MusicPlayerState mPlayerState;
	private Music currentMusic;
	private Array<String> gameMusics;
	private int lastMusicNum;
	private int newMusic;
	private int playCount;
	private long nextMusicDelay;
	
	public static Sound glitter;

	public MusicPlayer(ConnectCoins game){
		this.game = game;
		
		gameMusics = new Array<String>();
		for (int i = 1; i <= 15; i++){
			gameMusics.add("music" + i);
		}
		
		lastMusicNum = -1;
		selectRandomMusic();
		nextMusicDelay = -1;
		playCount = 0;
		
		mPlayerState = MusicPlayerState.READY;
		
		glitter = game.assetLoader.getSound("glitterSound");
	}
	
	public void playMusic(){
		switch (mPlayerState){
		case READY: {
			
		}; break;
		case DELAY: {
			if (nextMusicDelay != -1){
				if (TimeUtils.millis() - nextMusicDelay >= 2500) mPlayerState = MusicPlayerState.NO_MUSIC;
			}
		}; break;
		case NO_MUSIC: {
			setMusic();
		}; break;
		case LOADING: {
			if (game.assetLoader.manager.update()){
				currentMusic = game.assetLoader.getMusic("music" + newMusic);
				mPlayerState = MusicPlayerState.PLAYING;
			}
		}; break;
		case START_PLAY: {
			if (playCount == 2){
				playCount = 0;
				nextMusicDelay = TimeUtils.millis();
				selectRandomMusic();
				mPlayerState = MusicPlayerState.DELAY;
			}
			else {
				if (game.pUpdater.isMusicOn()) currentMusic.setVolume(.05f);
				else currentMusic.setVolume(0);
				currentMusic.play();
				playCount++;
				mPlayerState = MusicPlayerState.PLAYING;
			}
		}; break;
		case PLAYING: {
			if (!currentMusic.isPlaying()) mPlayerState = MusicPlayerState.START_PLAY;
		}; break;
		default: break;
		}
	}
	
	public void startPlayer(){
		if (mPlayerState == MusicPlayerState.READY){
			mPlayerState = MusicPlayerState.DELAY;
			nextMusicDelay = TimeUtils.millis();
		}
	}
	
	public void stopPlayer(){
		if (mPlayerState != MusicPlayerState.READY && currentMusic != null){
			currentMusic.stop();
			mPlayerState = MusicPlayerState.READY;
			selectRandomMusic();
			playCount = 0;
			nextMusicDelay = -1;
		}
	}
	
	private void selectRandomMusic(){
		newMusic = MathUtils.random(1, gameMusics.size);
		while (lastMusicNum == newMusic){
			newMusic = MathUtils.random(1, gameMusics.size);
		}
	}

	private void setMusic(){
		game.assetLoader.loadAndUnloadMusic("music" + newMusic);
		lastMusicNum = newMusic;
		mPlayerState = MusicPlayerState.LOADING;
	}
}
