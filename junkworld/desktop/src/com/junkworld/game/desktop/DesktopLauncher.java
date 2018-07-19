package com.junkworld.game.desktop; 	

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.junkworld.game.JunkWorld;

public class DesktopLauncher {
	private static DesktopLauncher application;
	public static void main(String[] args) {
		 if (application == null) {
	            application = new DesktopLauncher();
	        }
	
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();	
		cfg.title = "JunkWorld";
		cfg.width = 448;
		cfg.height = 700;
		cfg.resizable = false;
		new LwjglApplication(new JunkWorld(), cfg);
	}
}
