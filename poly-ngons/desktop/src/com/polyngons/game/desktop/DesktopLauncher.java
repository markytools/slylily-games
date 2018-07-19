package com.polyngons.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.polyngons.game.PolyNGons;
import com.polyNGonsFunctions.game.IActivityRequestHandler;


public class DesktopLauncher implements IActivityRequestHandler {
    private static DesktopLauncher application;
	public static void main (String[] arg) {
        if (application == null) {
            application = new DesktopLauncher();
        }
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 500;
		config.vSyncEnabled = true;
		config.title = "Poly N-gons";
		config.resizable = true;
		
		new LwjglApplication(new PolyNGons(application), config);
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showOrLoadAdmobInterstital() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getTotalRam() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void showToast(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showRateDialog() {
		// TODO Auto-generated method stub
		
	}
}