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
    private final int TIMEOUT = 5000;


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
        kryo.register(BuyTenementMessage.class);
        kryo.register(Color.class);
        kryo.register(TransferMessage.class);
        kryo.register(CrossedStartMessage.class);
        kryo.register(YouAreBankruptMessage.class);
        kryo.register(BankruptPlayerMesssage.class);
        kryo.register(TrapCardMessage.class);

        client.start();
        boolean thisIsServer = false;

        //try connect to server - if attempt will fail that means server not exists
        try{
            client.connect(TIMEOUT, MoneyLandGame.serverIP, MoneyLandGame.portTCP, MoneyLandGame.portUDP);
        }catch(Exception e){
            //create server
            game.setServer();
            thisIsServer = true;
        }

        //when it is first player after create server it must connect to server
        if(thisIsServer){
            try{
                while(!game.serverIsReady()){}; //wait to server will be ready
                client.connect(TIMEOUT, MoneyLandGame.serverIP, MoneyLandGame.portTCP, MoneyLandGame.portUDP);
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
                    startGame(message);
                }

                //listener for move other player
                else if(object instanceof PlayerMoveMessage){
                    PlayerMoveMessage message = (PlayerMoveMessage) object;
                    Player player = game.getOtherPlayerById(message.getIdPlayer());
                    if(!player.isPlayerBankrupt()) {
                        gameScreen.moveOtherPlayer(player, message.getDelta());
                    }
                }

                //listener for my turn
                else if(object instanceof YourMoveMessage){
                    game.setiAmMoveGameScreen(true);
                    gameScreen.myTurn();
                }
                //listener for buy card
                else if(object instanceof BuyCardMessage){
                    BuyCardMessage message = (BuyCardMessage) object;
                    buyCard(message);

                }
                else if (object instanceof BuyTenementMessage){
                    BuyTenementMessage message = (BuyTenementMessage) object;
                    buyTenement(message);
                }
                //listener for money transfer
                else if(object instanceof TransferMessage){
                    TransferMessage message = (TransferMessage) object;
                    moneyTransfer(message);
                }
                //listener when player crossed start field
                else if(object instanceof CrossedStartMessage){
                    CrossedStartMessage message = (CrossedStartMessage) object;
                    crossStartField(message);
                }

                else if(object instanceof YouAreBankruptMessage){
                    gameScreen.endMyMove();
                }

                else if(object instanceof BankruptPlayerMesssage){
                    BankruptPlayerMesssage messsage = (BankruptPlayerMesssage) object;
                    removeBankruptCards(messsage.getIdBankruptPlayer());
                }
                else if(object instanceof TrapCardMessage){
                    TrapCardMessage message = (TrapCardMessage) object;
                    int idTrapCard = message.getIdCard();
                    game.setIdTrapCard(idTrapCard);
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

    protected void removeBankruptCards(int id){
        for(int i=0; i<gameScreen.getCardsManager().getSizeCards(); ++i){
            Card card = gameScreen.getCardsManager().getCard(i);
            Player player = card.getOwner();
            if(player!=null){
                if(player.getPlayerId() == id){
                    card.setOwner(null);
                    card.setDefaultRectTitleBackground();
                }
            }
        }
    }

    protected void startGame(StartGameMessage message){
        game.setIdPlayerMoveGameScreen(message.getIdPlayerWhoStart());
        game.setiAmMoveGameScreen(message.isAmIStart());
        game.getPlayer().setPlayerId(message.getIdMyPlayer());

        if(lobby!=null) lobby.setChangeScreenToLoading();
    }

    protected void buyCard(BuyCardMessage message){
        Color color = null;
        Player owner = null;
        //update money status if i bought card
        if(message.getIdPlayer() == game.getPlayer().getPlayerId()){
            owner = game.getPlayer();
            owner.subtractPlayerMoney((int)message.getAmount()); //update money status
            if(owner.isPlayerBankrupt()){
                System.out.println("Bankrupt");

            }

            color = owner.getColor();
        }
        //update money status if other player bought card
        else{
            for(int i=0; i<game.sizePlayer(); ++i){
                owner = game.getOtherPlayer(i);
                if(owner.getPlayerId() == message.getIdPlayer()){
                    owner.subtractPlayerMoney((int)message.getAmount());
                    if(owner.isPlayerBankrupt()){
                        System.out.println("Bankrupt");
                    }
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

    protected void buyTenement(BuyTenementMessage message){
        Player owner = null;
        //update money status if i bought card
        if(message.getIdPlayer() == game.getPlayer().getPlayerId()){
            owner = game.getPlayer();
            owner.subtractPlayerMoney((int)message.getAmount()); //update money status
        }
        //update money status if other player bought card
        else{
            for(int i=0; i<game.sizePlayer(); ++i){
                owner = game.getOtherPlayer(i);
                if(owner.getPlayerId() == message.getIdPlayer()){
                    owner.subtractPlayerMoney((int)message.getAmount());
                    break;
                }
            }
        }

        //update owner and card color
        Card card = gameScreen.getCardsManager().getCard(message.getCardNumber());
        card.getCity().setTenementPlaced(true);
    }

    protected void moneyTransfer(TransferMessage message){
        int money = (int)message.getAmount();

        //update for my player
        Player me = game.getPlayer();
        if(me.getPlayerId() == message.getIdPlayerFrom()){
            me.subtractPlayerMoney(money);
            if(me.isPlayerBankrupt()){
                System.out.println("Bankrupt");
            }
        }else if(me.getPlayerId() == message.getIdPlayerTo()){
            me.addPlayerMoney(money);
        }

        //update for others player
        for(int i=0; i<game.sizePlayer(); ++i){
            Player temp = game.getOtherPlayer(i);
            int id = temp.getPlayerId();
            if(id == message.getIdPlayerFrom()){
                temp.subtractPlayerMoney(money);
                if(temp.isPlayerBankrupt()){
                    System.out.println("Bankrupt");
                }
            }else if(id == message.getIdPlayerTo()){
                temp.addPlayerMoney(money);
            }
        }
    }

    protected void crossStartField(CrossedStartMessage message){
        //if i crossed start field
        if(message.getIdPlayer() == game.getPlayer().getPlayerId()){
            game.getPlayer().addPlayerMoney(message.getAmount());
        }else{
            //if other player crossed start field
            for(int i=0; i<game.sizePlayer(); ++i){
                Player temp = game.getOtherPlayer(i);
                if(temp.getPlayerId() == message.getIdPlayer()){
                    temp.addPlayerMoney(message.getAmount());
                    break;
                }
            }
        }
    }
}
