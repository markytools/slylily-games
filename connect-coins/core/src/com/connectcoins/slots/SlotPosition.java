package com.connectcoins.slots;

import com.badlogic.gdx.utils.Disposable;

public class SlotPosition implements Disposable {
	public enum AdjacentSlotPos {
		BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT, LEFT, RIGHT, TOP, TOP_LEFT, TOP_RIGHT, NONE
	}
	
	public static final float slotPosA = 0;
	public static final float slotPosB = 216;
	public static final float slotPosC = 432;
	public static final float slotPosD = 648;
	public static final float slotPosE = 864;

	public static final float slotPos1 = 1026;
	public static final float slotPos2 = 810;
	public static final float slotPos3 = 594;
	public static final float slotPos4 = 378;
	public static final float slotPos5 = 162;
	
	/**
	 * Format: A1, B1,... , E1
	 */
	public String slotPosID;
	public float x, y;
	
	public SlotPosition(String slotPosID, float x, float y) {
		super();
		this.slotPosID = slotPosID;
		this.x = x;
		this.y = y;
	}
	
	public AdjacentSlotPos getAdjacentPos(Slot slot){
		String slotID = slotPosID;

		char adjacentCol1 = (char) ((int)(slotID.charAt(0)) - 1);
		char adjacentCol2 = slotID.charAt(0);
		char adjacentCol3 = (char) ((int)(slotID.charAt(0)) + 1);
		int adjacentRow1 = Integer.parseInt(String.valueOf(slotID.charAt(1))) - 1;
		int adjacentRow2 = Integer.parseInt(String.valueOf(slotID.charAt(1)));
		int adjacentRow3 = Integer.parseInt(String.valueOf(slotID.charAt(1))) + 1;
		
		String bottomRightSlot = String.valueOf(adjacentCol1) + String.valueOf(adjacentRow3);
		String bottomLeftSlot = String.valueOf(adjacentCol3) + String.valueOf(adjacentRow3);
		String bottomSlot = String.valueOf(adjacentCol2) + String.valueOf(adjacentRow3);
		String rightSlot = String.valueOf(adjacentCol1) + String.valueOf(adjacentRow2);
		String leftSlot = String.valueOf(adjacentCol3) + String.valueOf(adjacentRow2);
		String topRightSlot = String.valueOf(adjacentCol1) + String.valueOf(adjacentRow1);
		String topLeftSlot = String.valueOf(adjacentCol3) + String.valueOf(adjacentRow1);
		String topSlot = String.valueOf(adjacentCol2) + String.valueOf(adjacentRow1);
		
		
		String slotID2 = slot.getsPos().slotPosID;
		
		if (bottomRightSlot.equals(slotID2)){
			return AdjacentSlotPos.BOTTOM_RIGHT;
		}
		else if (bottomLeftSlot.equals(slotID2)){
			return AdjacentSlotPos.BOTTOM_LEFT;
		}
		else if (bottomSlot.equals(slotID2)){
			return AdjacentSlotPos.BOTTOM;
		}
		else if (rightSlot.equals(slotID2)){
			return AdjacentSlotPos.RIGHT;
		}
		else if (leftSlot.equals(slotID2)){
			return AdjacentSlotPos.LEFT;
		}
		else if (topRightSlot.equals(slotID2)){
			return AdjacentSlotPos.TOP_RIGHT;
		}
		else if (topLeftSlot.equals(slotID2)){
			return AdjacentSlotPos.TOP_LEFT;
		}
		else if (topSlot.equals(slotID2)){
			return AdjacentSlotPos.TOP;
		}
		else return AdjacentSlotPos.NONE;
	}

	@Override
	public void dispose() {
		slotPosID = null;
	}
}