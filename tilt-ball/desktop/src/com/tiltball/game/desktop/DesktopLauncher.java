package com.tiltball.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.handlers.MyAdActivity;
import com.tiltball.game.TiltBall;

public class DesktopLauncher implements MyAdActivity {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 500;
		config.height = 800;
		new LwjglApplication(new TiltBall(new DesktopLauncher()), config);
	}

	@Override
	public void showBanners(boolean show) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showInterstials() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showToast(String messages) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showRateDialog() {
		// TODO Auto-generated method stub
		
	}
}
