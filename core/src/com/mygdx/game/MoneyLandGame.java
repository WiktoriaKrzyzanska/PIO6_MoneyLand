package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import model.views.*;
import server.GameServer;

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
	public ArrayList<Player> listOtherPlayers = new ArrayList<>();
	private Player player;

	public static final int WIDTH = 3200; //world width
	public static final int HEIGHT = 1600; //world height

	public static final int MENU_SCREEN = 0;
	public static final int RULES_SCREEN = 1;
	public static final int LOBBY_INTRODUCTION_SCREEN = 2;
	public static final int LOBBY = 3;
	public static final int LOADING = 4;
	public static final int GAME_SCREEN = 5;

	public AssetManager manager;
	private AssetManager progress;

	public GameServer gameServer;
	public static final String serverIP = "192.168.245.19";
	public static final int portTCP = 54555;
	public static final int portUDP = 54777;

	public ServerHandler serverHandler;

	private boolean iAmMove = false;
	private int idPlayerMove;

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

		//create serverHandler
		serverHandler = new ServerHandler(this);

	}


	public void render () {
		super.render();
	}


	public void dispose () {
		if(serverHandler!= null) serverHandler.closeConnect();
		if(gameServer!=null) gameServer.closeServer();
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

	public void addPlayer(Player player){
		listOtherPlayers.add(player);
	}

	public Player getOtherPlayer(int index){
		return listOtherPlayers.get(index);
	}

	public Player getOtherPlayerById(int playerId){
		for(int i=0; i<listOtherPlayers.size(); ++i){
			Player temp = listOtherPlayers.get(i);
			if(temp.getPlayerId() == playerId){
				return temp;
			}
		}
		return null;
	}
	public int sizePlayer(){
		return listOtherPlayers.size();
	}

	public void setServer(){
		if(gameServer == null){
			gameServer = new GameServer();
			gameServer.start();
		}
	}

	public boolean serverIsReady(){
		return gameServer.serverIsReady();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isiAmMoveGameScreen() {
		return iAmMove;
	}

	public void setiAmMoveGameScreen(boolean iAmMove) {
		this.iAmMove = iAmMove;
	}

	public int getIdPlayerMoveGameScreen() {
		return idPlayerMove;
	}

	public void setIdPlayerMoveGameScreen(int idPlayerMove) {
		this.idPlayerMove = idPlayerMove;
	}
}
