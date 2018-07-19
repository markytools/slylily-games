package com.polyNGonsFunctions.game;

public interface IActivityRequestHandler {
	public void showAds(boolean show);
	public void showOrLoadAdmobInterstital();
	public long getTotalRam();
	public void showToast(String msg);
	public void showRateDialog();
}