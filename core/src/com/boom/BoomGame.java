package com.boom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.boom.domain.GameManager;
import com.boom.screen.LoadingScreen;

public class BoomGame extends Game {

	@Override
	public void create () {
		Box2D.init();
		GameManager.getInstance().init();
		setScreen(new LoadingScreen(this));
	}
}
