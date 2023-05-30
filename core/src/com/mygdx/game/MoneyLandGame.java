package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import model.views.LoadingScreen;
import model.views.LobbyIntroduction;
import model.views.GameScreen;
import model.views.MenuScreen;
import model.views.RulesScreen;
import model.views.Lobby;

import java.util.ArrayList;

public class MoneyLandGame extends Game {

	public OrthographicCamera camera;
	MenuScreen menuScreen;
	RulesScreen rulesScreen;
	GameScreen gameScreen;
	LobbyIntroduction lobbyIntroductionScreen;
	Lobby lobbyScreen;
	LoadingScreen loadingScreen;
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public ArrayList<String> listPlayers = new ArrayList<String>();

	public static final int WIDTH = 3200; //world width
	public static final int HEIGHT = 1600; //world height

	public static final int MENU_SCREEN = 0;
	public static final int RULES_SCREEN = 1;
	public static final int LOBBY_INTRODUCTION_SCREEN = 2;
	public static final int LOBBY = 3;
	public static final int LOADING = 4;
	public AssetManager manager;
	private AssetManager progress;
	public static final int GAME_SCREEN = 5;

	@Override
	public void create () {
		//config camera
		camera = new OrthographicCamera(WIDTH,HEIGHT);
		camera.position.set(WIDTH/2, HEIGHT / 2, 0);
		camera.update();

		//config SpriteBatch and ShapeRenderer
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();

		// other initialization code
		manager = new AssetManager(); // create an instance of the AssetManager
		this.setScreen(new LoadingScreen(this, manager));

		//set default screen
		menuScreen = new MenuScreen(this);
		this.setScreen(menuScreen);
	}


	public void render () {
		super.render();
	}


	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
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
			case GAME_SCREEN:
				if(gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;
			case LOBBY_INTRODUCTION_SCREEN:
				if(lobbyIntroductionScreen == null)lobbyIntroductionScreen = new LobbyIntroduction(this);
				this.setScreen(lobbyIntroductionScreen);
				break;
			case LOBBY:
				lobbyScreen = new Lobby(this);
				this.setScreen(lobbyScreen);
				break;
			case LOADING:
				if(loadingScreen == null)loadingScreen = new LoadingScreen(this,  manager);
				this.setScreen(loadingScreen);
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
