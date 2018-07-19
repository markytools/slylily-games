package com.connectcoins.assets;

public class AssetLabel {
	public enum FileDest {
		INTERNAL, EXTERNAL
	}

	private AssetScreenSpace assetScreen;
	private String assetID;

	private FileDest fileDest;
	
	public AssetLabel(AssetScreenSpace assetScreen, String assetID, FileDest fileDest){
		this.assetScreen = assetScreen;
		this.assetID = assetID;
		this.fileDest = fileDest;
	}

	public AssetScreenSpace getAssetScreen() {
		return assetScreen;
	}

	public String getAssetID() {
		return assetID;
	}

	public FileDest getFileDest() {
		return fileDest;
	}
}
