package com.springdynamics.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.springdynamics.game.SpringDynamics;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 500;
		DesktopLauncher dLauncher = new DesktopLauncher();
		new LwjglApplication(new SpringDynamics(), config);
	}
}
