package com.polyNGonsPuzzleSupplier.game;

import java.io.BufferedReader;
import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;

public class PuzzleDescriptionOutputStreamer {

	public PuzzleDescriptionOutputStreamer(){

	}

	public String getDifficultyDesc(int diffNum){
		FileHandle filePath = Gdx.files.internal("gameManagers/d" + diffNum + ".txt");
		String pDesc = null;

		BufferedReader bF = new BufferedReader(filePath.reader());
		try {
			pDesc = bF.readLine();
			bF.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (pDesc != null){
			pDesc = Base64Coder.decodeString(pDesc);
		}
		return pDesc;
	}
}
