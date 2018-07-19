package com.puzzleCreatorPrioritizer.game;

public class PriorityPuzzleCreator {
	public float xPos;
	public float yPos;
	public byte priorityType;
	
	public PriorityPuzzleCreator(){
		
	}
	
	public PriorityPuzzleCreator(float xPos, float yPos, byte priorityType){
		this.xPos = xPos;
		this.yPos = yPos;
		this.priorityType = priorityType;
	}
}
