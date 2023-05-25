package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.views.MenuScreen;
import model.views.RulesScreen;

public class MoneyLandGame extends Game {

	public OrthographicCamera camera;
	MenuScreen menuScreen;
	RulesScreen rulesScreen;
	public SpriteBatch batch;

	public static final int WIDTH = 400; //world width
	public static final int HEIGHT = 200; //world height

	public static final int MENU_SCREEN = 0;
	public static final int RULES_SCREEN = 1;

	@Override
	public void create () {
		//config Camera
		camera = new OrthographicCamera(WIDTH,HEIGHT);
		camera.position.set(WIDTH/2, HEIGHT / 2, 0);
		camera.update();

		//config SpriteBatch
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

	public void changeScreen(int screen){
		switch(screen) {
			case MENU_SCREEN:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case RULES_SCREEN:
				if(rulesScreen == null) rulesScreen = new RulesScreen(this);
				this.setScreen(rulesScreen);
				break;
			default:
				break;
		}
	}
}
