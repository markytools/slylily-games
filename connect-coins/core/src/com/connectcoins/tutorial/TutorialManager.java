package com.connectcoins.tutorial;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.functions.GameModeConfig.GameMode;
import com.connectcoins.game.ConnectCoins;
import com.connectcoins.game.GameScreen;
import com.connectcoins.languages.LanguageManager;

public class TutorialManager implements Disposable {
	public enum TutorialMessageType {
		FIRST, NORMAL, ACTION, OUTER, OUTER_EXIT, EXIT
	}
	
	private GameScreen gScreen;
	private ConnectCoins game;
	private int currentTutorialMsg = 0;
	private Array<TutorialMessage> tutorialMsgs;
	private Button prevButton, nextButton;

	public TutorialManager(GameScreen gScreen){
		this.gScreen = gScreen;
		this.game = gScreen.game;
		
		if (gScreen.game.gMConfig.mode == GameMode.TUTORIAL){
			currentTutorialMsg = 0;
			gScreen.getSlotManager().enableAllSlots(false);
			tutorialMsgs = new Array<TutorialMessage>();
			setupMsgs();
		}
	}
	
	private void setupMsgs() {
		Array<String> tutorial_CURRENT = LanguageManager.tutorial_CURRENT;
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(0),
				TutorialMessageType.FIRST));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(1),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(2),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(3),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(4),
				TutorialMessageType.ACTION));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(5),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(6),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(7),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(8),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(9),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(10),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(11),
				TutorialMessageType.ACTION));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(12),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(13),
				TutorialMessageType.ACTION));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(14),
				TutorialMessageType.OUTER));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(15),
				TutorialMessageType.OUTER));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(16),
				TutorialMessageType.OUTER_EXIT));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(17),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(18),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(19),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(20),
				TutorialMessageType.ACTION));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(21),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(22),
				TutorialMessageType.ACTION));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(23),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(24),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(25),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(26),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(27),
				TutorialMessageType.NORMAL));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(28),
				TutorialMessageType.ACTION));
		tutorialMsgs.add(new TutorialMessage(
				tutorial_CURRENT.get(29),
				TutorialMessageType.EXIT));
	}

	public void resetAll() {
		if (gScreen.game.gMConfig.mode == GameMode.TUTORIAL){
			currentTutorialMsg = 0;
			gScreen.getSlotManager().enableAllSlots(false);
		}
	}

	public void nextMsg(int currentMsg){
		if (currentMsg == currentTutorialMsg) currentTutorialMsg++;
	}
	
	public void prevMsg(int currentMsg){
		if (currentMsg == currentTutorialMsg) currentTutorialMsg--;
	}
	
	public int getCurrentMsgNum(){
		return currentTutorialMsg;
	}
	
	public TutorialMessage getCurrentMsg(){
		return tutorialMsgs.get(currentTutorialMsg);
	}
	
	public void resetCurrentMsg(){
		currentTutorialMsg = 0;
	}

	public Array<TutorialMessage> getTutorialMsgs() {
		return tutorialMsgs;
	}
	
	public Button getPrevButton() {
		return prevButton;
	}

	public void setPrevButton(Button prevButton) {
		this.prevButton = prevButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public void setNextButton(Button nextButton) {
		this.nextButton = nextButton;
	}

	@Override
	public void dispose() {
		if (game.gMConfig.mode == GameMode.TUTORIAL){
			gScreen = null;
			game = null;
			for (TutorialMessage tM : tutorialMsgs){
				tM.dispose();
				tM = null;
			}
			tutorialMsgs.clear();
			tutorialMsgs = null;
			prevButton = null;
			nextButton = null;
		}
	}
}