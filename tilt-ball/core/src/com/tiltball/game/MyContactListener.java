package com.tiltball.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
	private boolean ballTouched = false;
	private Sound gameOver;
	private boolean soundPlayed = false;
	public MyContactListener(TiltBall game, Sound gameOver){
		this.gameOver = gameOver;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (fa.getUserData() == "topBorder" && fb.getUserData() == "player1" ||
				fb.getUserData() == "topBorder" && fa.getUserData() == "player1") {
			setBallTouched(true);
			if (!soundPlayed) {
				soundPlayed = true;
				gameOver.play();
//				game.adTimer.showBanners(true);
//				game.adTimer.randomShowInterstitial();
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	public boolean isBallTouched() {
		return ballTouched;
	}

	public void setBallTouched(boolean ballTouched) {
		this.ballTouched = ballTouched;
	}
	
	public void restart(){
		ballTouched = false;
		soundPlayed = false;
	}
	
	public void dispose(){
		gameOver = null;
	}
}
