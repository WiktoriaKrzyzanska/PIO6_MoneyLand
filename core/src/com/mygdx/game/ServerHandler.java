package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import model.views.GameScreen;
import model.views.Lobby;

import java.util.ArrayList;

public class ServerHandler {
    private Client client;
    private MoneyLandGame game;
    private Lobby lobby;
    private GameScreen gameScreen;

    public ServerHandler(MoneyLandGame parent){
        game = parent;
    }

    public void setConnect(){
        client = new Client();

        //class register - is required from documentation; important: must be same order in server and here
        Kryo kryo = client.getKryo();
        kryo.register(ArrayList.class);

        client.start();
        boolean thisIsServer = false;

        //try connect to server - if attempt will fail that means server not exists
        try{
            client.connect(5000, MoneyLandGame.serverIP, MoneyLandGame.portTCP, MoneyLandGame.portUDP);
        }catch(Exception e){
            //create server
            game.setServer();
            thisIsServer = true;
        }

        //when it is first player after create server it must connect to server
        if(thisIsServer){
            try{
                while(!game.serverIsReady()){}; //wait to server will be ready
                client.connect(5000, MoneyLandGame.serverIP, MoneyLandGame.portTCP, MoneyLandGame.portUDP);
            }catch(Exception e){
                Gdx.app.log("Exception", "Connect error");
            }
        }

        //turn on listener messages from server
        listenerFromServer();

    }

    public void listenerFromServer(){
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                //listener to get all other players nick from server
                if (object instanceof ArrayList) {
                    //add nicks to players list who already joined
                    ArrayList otherPlayersNick = (ArrayList) object;
                    for(int i=0; i<otherPlayersNick.size(); ++i){
                        String otherPlayerNick = (String)otherPlayersNick.get(i);
                        game.addPlayer(otherPlayerNick);
                    }
                }
                //listener for update when new player join to game
                else if(object instanceof String){
                    String newPlayerNick = (String)object;
                    game.addPlayer(newPlayerNick);
                }
            }
        });
    }
    public void sendMessage(Object object){
        client.sendTCP(object);
    }

    public void setupConnectWithLobbyScreen(Lobby lobby){
        this.lobby = lobby;
    }
    public void setupConnectWithGameScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public void closeConnect(){
        if(client != null) client.close();
    }
}
