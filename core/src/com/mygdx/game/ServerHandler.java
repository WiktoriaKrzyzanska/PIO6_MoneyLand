package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import model.messages.*;
import model.views.Card;
import model.views.GameScreen;
import model.views.Lobby;
import model.views.Player;

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
        kryo.register(Player.class);
        kryo.register(StartGameMessage.class);
        kryo.register(PlayerMoveMessage.class);
        kryo.register(EndMoveMessage.class);
        kryo.register(YourMoveMessage.class);
        kryo.register(BuyCardMessage.class);
        kryo.register(Color.class);

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

    public void setupConnectWithLobbyScreen(Lobby lobby){
        this.lobby = lobby;
    }
    public void setupConnectWithGameScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }
    public void listenerFromServer(){
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                //listener to get all other players nick from server
                if (object instanceof ArrayList) {
                    //add nicks to players list who already joined
                    ArrayList otherPlayers = (ArrayList) object;
                    for(int i=0; i<otherPlayers.size(); ++i){
                        Player otherPlayer = (Player)otherPlayers.get(i);
                        game.addPlayer(otherPlayer);
                    }
                }
                //listener for update when new player join to game
                else if(object instanceof Player){
                    Player newPlayer = (Player)object;
                    game.addPlayer(newPlayer);
                }
                //listener for messages from server
                else if (object instanceof StartGameMessage) {
                    StartGameMessage message = (StartGameMessage)object;

                    game.setIdPlayerMoveGameScreen(message.getIdPlayerWhoStart());
                    game.setiAmMoveGameScreen(message.isAmIStart());
                    game.getPlayer().setPlayerId(message.getIdMyPlayer());

                    if(lobby!=null) lobby.setChangeScreenToLoading();
                }

                //listener for move other player
                else if(object instanceof PlayerMoveMessage){
                    PlayerMoveMessage message = (PlayerMoveMessage) object;
                    Player player = game.getOtherPlayerById(message.getIdPlayer());
                    gameScreen.moveOtherPlayer(player, message.getDelta());
                }

                //listener for my turn
                else if(object instanceof YourMoveMessage){
                    game.setiAmMoveGameScreen(true);
                    gameScreen.myTurn();
                }
                //listener for buy card
                else if(object instanceof BuyCardMessage){
                    BuyCardMessage message = (BuyCardMessage) object;
                    Color color = null;
                    Player owner = null;
                    //update money status if i bought card
                    if(message.getIdPlayer() == game.getPlayer().getPlayerId()){
                        owner = game.getPlayer();
                        owner.subtractPlayerMoney((int)message.getAmount()); //update money status
                        color = owner.getColor();
                    }
                    //update money status if other player bought card
                    else{
                        for(int i=0; i<game.sizePlayer(); ++i){
                            owner = game.getOtherPlayer(i);
                            if(owner.getPlayerId() == message.getIdPlayer()){
                                owner.subtractPlayerMoney((int)message.getAmount());
                                color = owner.getColor();
                                break;
                            }
                        }
                    }

                    //update owner and card color
                    Card card = gameScreen.getCardsManager().getCard(message.getCardNumber());
                    card.setOwner(owner);
                    card.setRectTitleBackground(color);
                }
            }
        });
    }
    public void sendMessage(Object object){
        client.sendTCP(object);
    }

    public void closeConnect(){
        if(client != null) client.close();
    }
}
