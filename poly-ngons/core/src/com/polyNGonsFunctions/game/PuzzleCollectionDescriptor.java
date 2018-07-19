package com.polyNGonsFunctions.game;

import com.badlogic.gdx.utils.Array;

public class PuzzleCollectionDescriptor {
	private int difficulty;
	private int puzzleNum;
	private Array<PolygonDescriptor> polyDescriptor;
	
	public PuzzleCollectionDescriptor(){
		
	}

	public PuzzleCollectionDescriptor(int difficulty, int puzzleNum) {
		super();
		this.difficulty = difficulty;
		this.puzzleNum = puzzleNum;
	}

	public PuzzleCollectionDescriptor(int difficulty, int puzzleNum,
			Array<PolygonDescriptor> polyDescriptor) {
		super();
		this.difficulty = difficulty;
		this.puzzleNum = puzzleNum;
		this.polyDescriptor = polyDescriptor;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getPuzzleNum() {
		return puzzleNum;
	}

	public void setPuzzleNum(int puzzleNum) {
		this.puzzleNum = puzzleNum;
	}

	public Array<PolygonDescriptor> getPuzzlePolygons() {
		return polyDescriptor;
	}

	public void setPuzzlePolygons(Array<PolygonDescriptor> polyDescriptor) {
		this.polyDescriptor = polyDescriptor;
	}
}
