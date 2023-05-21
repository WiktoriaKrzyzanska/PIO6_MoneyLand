package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.views.MenuScreen;

public class MoneyLandGame extends Game {

	MenuScreen menuScreen;
	public SpriteBatch batch;

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;


	public void create () {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);
		this.setScreen(menuScreen);
	}


	public void render () {
		super.render();
	}


	public void dispose () {
		batch.dispose();
	}
}
