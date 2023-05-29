package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.views.LobbyIntroduction;
import model.views.MenuScreen;
import model.views.RulesScreen;
import model.views.Lobby;

import java.util.ArrayList;

public class MoneyLandGame extends Game {

	MenuScreen menuScreen;
	RulesScreen rulesScreen;
	LobbyIntroduction lobbyIntroductionScreen;
	Lobby lobbyScreen;
	public SpriteBatch batch;
	public ArrayList<String> listPlayers = new ArrayList<String>();
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	public static final int MENU_SCREEN = 0;
	public static final int RULES_SCREEN = 1;
	public static final int LOBBY_INTRODUCTION_SCREEN = 2;
	public static final int LOBBY = 3;
	@Override
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
			case LOBBY_INTRODUCTION_SCREEN:
				if(lobbyIntroductionScreen == null)lobbyIntroductionScreen = new LobbyIntroduction(this);
				this.setScreen(lobbyIntroductionScreen);
				break;
			case LOBBY:
				if(lobbyScreen == null)lobbyScreen = new Lobby(this);
				this.setScreen(lobbyScreen);
				break;
			default:
				break;
		}
	}

	public void addPlayer(String name){
		listPlayers.add(name);
	}
	public String getPlayer(int index){
		return listPlayers.get(index);
	}
	public int sizePlayer(){
		return listPlayers.size();
	}
}
