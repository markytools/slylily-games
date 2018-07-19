package com.connectcoins.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.connectcoins.game.ConnectCoins;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 640;
		config.width = 360;
		DesktopLauncher d = new DesktopLauncher();
		new LwjglApplication(new ConnectCoins(), config);
	}
}