package com.connectcoins.tutorial;

import com.badlogic.gdx.utils.Disposable;
import com.connectcoins.tutorial.TutorialManager.TutorialMessageType;

public class TutorialMessage implements Disposable {
	private String message;
	private TutorialMessageType tMsgType;
	
	public TutorialMessage(){
		
	}

	public TutorialMessage(String message, TutorialMessageType tMsgType) {
		super();
		this.message = message;
		this.tMsgType = tMsgType;
	}

	public String getMessage() {
		return message;
	}

	public TutorialMessageType gettMsgType() {
		return tMsgType;
	}

	@Override
	public void dispose() {
		message = null;
		tMsgType = null;
	}
}