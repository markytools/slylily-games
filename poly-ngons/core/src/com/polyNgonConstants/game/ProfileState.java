package com.polyNgonConstants.game;

public class ProfileState {
	public byte[] dUnlocks;
	public byte[] d1PUnlocks;
	public byte[] d2PUnlocks;
	public byte[] d3PUnlocks;
	public byte[] d4PUnlocks;
	public byte[] d5PUnlocks;
	public byte[] d6PUnlocks;
	public byte[] d7PUnlocks;
	public byte[] d8PUnlocks;
	public byte[] d9PUnlocks;
	public byte[] d10PUnlocks;
	public byte[] d11PUnlocks;
	public byte[] d12PUnlocks;
	public byte[] d13PUnlocks;
	public byte[] d14PUnlocks;
	public byte[] d15PUnlocks;
	public byte[] d16PUnlocks;
	public byte[] d17PUnlocks;
	public byte[] d18PUnlocks;
	public byte[] d19PUnlocks;
	public byte[] d20PUnlocks;

	public float rating;
	public float ratingToSubtract;
	
	public int[] currentPuzzle;
	public int[] highestPuzzle;
	
	public byte currentPolyTexture;
	public byte currentBordTexture;
	
	public boolean noAds;

	public ProfileState(){
		initializeVars();
		setGameJunctions();
		rating = 5;
		ratingToSubtract = 0;

		for (int i = 0; i < currentPuzzle.length; i++){
			currentPuzzle[i] = 1;
		}
		for (int i = 0; i < currentPuzzle.length; i++){
			highestPuzzle[i] = 1;
		}
		
		currentPolyTexture = PolyNGonsTextureName.T_SIMPLE;
		currentBordTexture = PolyNGonsTextureName.B_SIMPLE;
		noAds = false;
	}

	private void initializeVars() {
		dUnlocks = new byte[20];
		d1PUnlocks = new byte[50];
		d2PUnlocks = new byte[80];
		d3PUnlocks = new byte[120];
		d4PUnlocks = new byte[150];
		d5PUnlocks = new byte[180];
		d6PUnlocks = new byte[200];
		d7PUnlocks = new byte[230];
		d8PUnlocks = new byte[250];
		d9PUnlocks = new byte[280];
		d10PUnlocks = new byte[300];
		d11PUnlocks = new byte[330];
		d12PUnlocks = new byte[350];
		d13PUnlocks = new byte[380];
		d14PUnlocks = new byte[400];
		d15PUnlocks = new byte[360];
		d16PUnlocks = new byte[320];
		d17PUnlocks = new byte[280];
		d18PUnlocks = new byte[240];
		d19PUnlocks = new byte[200];
		d20PUnlocks = new byte[200];
		
		currentPuzzle = new int[20];
		highestPuzzle = new int[20];
	}

	private void setGameJunctions(){
		for (int i = 0; i < dUnlocks.length; i++){
			if (i == 0) dUnlocks[i] = 1;
			else dUnlocks[i] = 0;
		}
//		0 = Locked, 1 = Not Solved, 2 = Solved
		for (int i = 0; i < d1PUnlocks.length; i++){
			if (i == 0) d1PUnlocks[i] = 1;
			else d1PUnlocks[i] = 0;
		}
		for (int i = 0; i < d2PUnlocks.length; i++){
			if (i == 0) d2PUnlocks[i] = 1;
			else d2PUnlocks[i] = 0;
		}
		for (int i = 0; i < d3PUnlocks.length; i++){
			if (i == 0) d3PUnlocks[i] = 1;
			else d3PUnlocks[i] = 0;
		}
		for (int i = 0; i < d4PUnlocks.length; i++){
			if (i == 0) d4PUnlocks[i] = 1;
			else d4PUnlocks[i] = 0;
		}
		for (int i = 0; i < d5PUnlocks.length; i++){
			if (i == 0) d5PUnlocks[i] = 1;
			else d5PUnlocks[i] = 0;
		}
		for (int i = 0; i < d6PUnlocks.length; i++){
			if (i == 0) d6PUnlocks[i] = 1;
			else d6PUnlocks[i] = 0;
		}
		for (int i = 0; i < d7PUnlocks.length; i++){
			if (i == 0) d7PUnlocks[i] = 1;
			else d7PUnlocks[i] = 0;
		}
		for (int i = 0; i < d8PUnlocks.length; i++){
			if (i == 0) d8PUnlocks[i] = 1;
			else d8PUnlocks[i] = 0;
		}
		for (int i = 0; i < d9PUnlocks.length; i++){
			if (i == 0) d9PUnlocks[i] = 1;
			else d9PUnlocks[i] = 0;
		}
		for (int i = 0; i < d10PUnlocks.length; i++){
			if (i == 0) d10PUnlocks[i] = 1;
			else d10PUnlocks[i] = 0;
		}
		for (int i = 0; i < d11PUnlocks.length; i++){
			if (i == 0) d11PUnlocks[i] = 1;
			else d11PUnlocks[i] = 0;
		}
		for (int i = 0; i < d12PUnlocks.length; i++){
			if (i == 0) d12PUnlocks[i] = 1;
			else d12PUnlocks[i] = 0;
		}
		for (int i = 0; i < d13PUnlocks.length; i++){
			if (i == 0) d13PUnlocks[i] = 1;
			else d13PUnlocks[i] = 0;
		}
		for (int i = 0; i < d14PUnlocks.length; i++){
			if (i == 0) d14PUnlocks[i] = 1;
			else d14PUnlocks[i] = 0;
		}
		for (int i = 0; i < d15PUnlocks.length; i++){
			if (i == 0) d15PUnlocks[i] = 1;
			else d15PUnlocks[i] = 0;
		}
		for (int i = 0; i < d16PUnlocks.length; i++){
			if (i == 0) d16PUnlocks[i] = 1;
			else d16PUnlocks[i] = 0;
		}
		for (int i = 0; i < d17PUnlocks.length; i++){
			if (i == 0) d17PUnlocks[i] = 1;
			else d17PUnlocks[i] = 0;
		}
		for (int i = 0; i < d18PUnlocks.length; i++){
			if (i == 0) d18PUnlocks[i] = 1;
			else d18PUnlocks[i] = 0;
		}
		for (int i = 0; i < d19PUnlocks.length; i++){
			if (i == 0) d19PUnlocks[i] = 1;
			else d19PUnlocks[i] = 0;
		}
		for (int i = 0; i < d20PUnlocks.length; i++){
			if (i == 0) d20PUnlocks[i] = 1;
			else d20PUnlocks[i] = 0;
		}
	}
}
