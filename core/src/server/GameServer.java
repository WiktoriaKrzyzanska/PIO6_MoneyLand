package server;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.MoneyLandGame;
import model.messages.*;
import model.views.Pawn;
import model.views.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameServer{

    private Server server;
    private AtomicBoolean serverReady = new AtomicBoolean(false);
    private ArrayList<ClientHandler> playersList;
    private ArrayList<ClientHandler> playersListBankrupts;
    private AtomicBoolean startGame = new AtomicBoolean(false);
    private final int MAX_PLAYERS = 5;
    private final int MIN_PLAYERS = 2;
    private int idPlayerMove;

    private ArrayList<Color> colors;
    private ArrayList<Object> otherPlayersColors;

    public void start(){
        server = new Server();

        //class register - is required from documentation; important: must be same order in client and here
        Kryo kryo = server.getKryo();
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


        server.start();

        //config colors for players
        setColors();

        try{
            server.bind(MoneyLandGame.portTCP, MoneyLandGame.portUDP);
            serverReady.set(true);
        }catch(IOException e){
            return;
        }

        //create list to contain players nick
        playersList = new ArrayList<>();

        //create list to contain players who bankrupt
        playersListBankrupts = new ArrayList<>();

        //listener for clients messages
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Player && !startGame.get() && playersList.size() <= MAX_PLAYERS) {
                    //get list of all other players
                    ArrayList<Player> otherPlayersList = new ArrayList<>();
                    for(int i=0; i<playersList.size(); ++i){
                        otherPlayersList.add(playersList.get(i).getPlayerFromServer());
                    }

                    connection.sendTCP(otherPlayersList); //send to player list of all other players

                    Player player = (Player)object;
                    player.setPlayerId(playersList.size()); //set player id

                    player.setColor(colors.get(player.getPlayerId())); //set player color

                    ClientHandler playerHandler = new ClientHandler(player,connection);
                    playersList.add(playerHandler);

                    //update for others players - new player
                    for(int i=0; i<playersList.size(); ++i){
                        Connection connection1 = playersList.get(i).getConnection();
                        if(connection1 != connection){
                            connection1.sendTCP(player);
                        }
                    }
                }
                else if(object instanceof String){
                    String message = (String)object;
                    if(message.equals("Ready for game")){
                        readyForGame(connection);
                        //losowanie karty która działa inaczej
                        int idTrapCard;
                        Random random = new Random();
                        idTrapCard = random.nextInt(16);
                        TrapCardMessage trapCardMessage = new TrapCardMessage(idTrapCard);
                        sendTrapCardToAll(trapCardMessage);
                    }
                }
                else if(object instanceof PlayerMoveMessage){
                    updatePlayerMove((PlayerMoveMessage)object);
                }
                else if(object instanceof EndMoveMessage){
                    nextPlayer();
                }
                else if(object instanceof BuyCardMessage){
                    BuyCardMessage message = (BuyCardMessage) object;
                    buyCard(message);
                }
                else if(object instanceof BuyTenementMessage){
                    BuyTenementMessage message = (BuyTenementMessage) object;
                    buyTenement(message);
                }
                else if(object instanceof TransferMessage){
                    TransferMessage message = (TransferMessage) object;
                    transferMoney(message);
                }
                else if(object instanceof CrossedStartMessage){
                    CrossedStartMessage message = (CrossedStartMessage) object;
                    crossStartField(message);
                }
            }
        });
    }

    public boolean serverIsReady(){
        return serverReady.get();
    }

    public void closeServer(){
        if(server != null){
            server.stop();
            server.close();
        }
    }

    protected void nextPlayer(){

        //search
        if(idPlayerMove+1<playersList.size()){
            idPlayerMove++;
        }else{
            idPlayerMove=0;
        }

        //send info for next player
        ClientHandler temp = playersList.get(idPlayerMove);
        temp.getConnection().sendTCP(new YourMoveMessage());

    }

    protected void readyForGame(Connection connection){
            //change player ready flag to true
            for(int i=0; i<playersList.size(); ++i){
                ClientHandler temp = playersList.get(i);
                if(temp.getConnection().equals(connection)){
                    temp.setPlayerIsReady(true);
                    break;
                }
            }

            //check do all players have ready flag set true
            boolean flag = true;
            for(int i=0; i<playersList.size(); ++i){
                ClientHandler temp = playersList.get(i);
                if(!temp.getPlayerIsReady()){
                    flag=false;
                    break;
                }
            }
            //all players are ready - send message to all players to lets play
            if(flag && playersList.size() >= MIN_PLAYERS){
                //draw id player who will start game
                Random random = new Random();
                idPlayerMove = random.nextInt(playersList.size());
                //send to all players
                for(int i=0; i<playersList.size(); ++i){
                    StartGameMessage startGameMessage = new StartGameMessage();
                    ClientHandler temp = playersList.get(i);

                    if(temp.getPlayerFromServer().getPlayerId() == idPlayerMove){
                        startGameMessage.setIdPlayerWhoStart(0); //it's not important
                        startGameMessage.setAmIStart(true);
                        startGameMessage.setIdMyPlayer(temp.getPlayerFromServer().getPlayerId());
                    }else{
                        startGameMessage.setIdPlayerWhoStart(idPlayerMove);
                        startGameMessage.setAmIStart(false);
                        startGameMessage.setIdMyPlayer(temp.getPlayerFromServer().getPlayerId());
                    }

                    temp.getConnection().sendTCP(startGameMessage);
                }
                startGame.set(true); //start game - no new players
            }
    }

    protected void updatePlayerMove(PlayerMoveMessage playerMoveMessage){
        //update info about player
        int id = playerMoveMessage.getIdPlayer();
        for(int i=0; i<playersList.size(); ++i){
            ClientHandler temp = playersList.get(i);
            if(id == temp.getPlayerFromServer().getPlayerId()){
                int delta = playerMoveMessage.getDelta();
                temp.getPlayerFromServer().updatePlayerPosition(delta);
                break;
            }
        }

        //send update to other players
        for(int i=0; i<playersList.size(); ++i){
            ClientHandler temp = playersList.get(i);
            if(id == temp.getPlayerFromServer().getPlayerId()){
                continue;
            }
            temp.getConnection().sendTCP(playerMoveMessage);
        }

        //update info for player who is bankrupt
        for(int i=0;i<playersListBankrupts.size(); ++i){
            //send info to all players
            ClientHandler clientHandler = playersListBankrupts.get(i);
            clientHandler.getConnection().sendTCP(playerMoveMessage);
        }
    }

    public void sendInfoAboutBankrupt(int id){
        BankruptPlayerMesssage bankruptPlayerMesssage = new BankruptPlayerMesssage(id);

        for(int i=0; i<playersList.size(); ++i){
            ClientHandler temp = playersList.get(i);
            temp.getConnection().sendTCP(bankruptPlayerMesssage);
        }
    }

    public void setColors(){
        colors = new ArrayList<>();
        colors.add(new Color(Color.BLUE));
        colors.add(new Color(Color.PINK));
        colors.add(new Color(Color.RED));
        colors.add(new Color(Color.GOLD));
        colors.add(new Color(Color.PURPLE));
    }

    protected void transferMoney(TransferMessage message){
        int money = (int)message.getAmount();

        int idPlayerBankrupt = -1;

        for(int i=0; i<playersList.size(); ++i){
            //send info to all players
            ClientHandler clientHandler = playersList.get(i);
            clientHandler.getConnection().sendTCP(message);
            //update status on server
            Player temp = clientHandler.getPlayerFromServer();
            int id = temp.getPlayerId();
            if(id == message.getIdPlayerFrom()){
                temp.subtractPlayerMoney(money);
                if(temp.isPlayerBankrupt()){

                    //when player is bankrupt - transfer to bankrupt list
                    playersListBankrupts.add(clientHandler);
                    playersList.remove(clientHandler);
                    clientHandler.getConnection().sendTCP(new YouAreBankruptMessage());
                    idPlayerBankrupt = id;

                    //update iterator and id - it's protection to not missed next player after change playerList
                    --i;
                    --idPlayerMove;

                }
            }else if(id == message.getIdPlayerTo()){
                temp.addPlayerMoney(money);
            }
        }

        //update info for player who is bankrupt
        for(int i=0;i<playersListBankrupts.size(); ++i){
            //send info to all players
            ClientHandler clientHandler = playersListBankrupts.get(i);
            clientHandler.getConnection().sendTCP(message);
        }

        //send info if we have bankrupt
        if(idPlayerBankrupt!=-1){
            sendInfoAboutBankrupt(idPlayerBankrupt);
        }
    }
    protected void buyTenement(BuyTenementMessage message){
        int money = (int)message.getAmount();

        int idPlayerBankrupt = -1;
        for(int i=0; i<playersList.size(); ++i){
            ClientHandler clientHandler = playersList.get(i);
            clientHandler.getConnection().sendTCP(message);

            //update status on server
            Player temp = clientHandler.getPlayerFromServer();
            int id = temp.getPlayerId();
            if(id == message.getIdPlayer()){
                temp.subtractPlayerMoney(money);
                if(temp.isPlayerBankrupt()){
                    //when player is bankrupt - transfer to bankrupt list
                    playersListBankrupts.add(clientHandler);
                    playersList.remove(clientHandler);
                    clientHandler.getConnection().sendTCP(new YouAreBankruptMessage());
                    idPlayerBankrupt = id;

                    //update iterator and id - it's protection to not missed next player after change playerList
                    --i;
                    --idPlayerMove;
                }
            }
        }

        //update info for player who is bankrupt
        for(int i=0;i<playersListBankrupts.size(); ++i){
            //send info to all players
            ClientHandler clientHandler = playersListBankrupts.get(i);
            clientHandler.getConnection().sendTCP(message);
        }

        //send info if we have bankrupt
        if(idPlayerBankrupt!=-1){
            sendInfoAboutBankrupt(idPlayerBankrupt);
        }
    }
    protected void buyCard(BuyCardMessage message){
        int money = (int)message.getAmount();

        int idPlayerBankrupt = -1;

        //update info
        for(int i=0; i<playersList.size(); ++i){
            ClientHandler clientHandler = playersList.get(i);
            clientHandler.getConnection().sendTCP(message);

            //update status on server
            Player temp = clientHandler.getPlayerFromServer();
            int id = temp.getPlayerId();
            if(id == message.getIdPlayer()){
                temp.subtractPlayerMoney(money);
                if(temp.isPlayerBankrupt()){
                    //when player is bankrupt - transfer to bankrupt list
                    playersListBankrupts.add(clientHandler);
                    playersList.remove(clientHandler);
                    clientHandler.getConnection().sendTCP(new YouAreBankruptMessage());
                    idPlayerBankrupt = id;

                    //update iterator and id - it's protection to not missed next player after change playerList
                    --i;
                    --idPlayerMove;
                }
            }
        }

        //update info for player who is bankrupt
        for(int i=0;i<playersListBankrupts.size(); ++i){
            //send info to all players
            ClientHandler clientHandler = playersListBankrupts.get(i);
            clientHandler.getConnection().sendTCP(message);
        }

        //send info if we have bankrupt
        if(idPlayerBankrupt!=-1){
            sendInfoAboutBankrupt(idPlayerBankrupt);
        }
    }
    protected void crossStartField(CrossedStartMessage message){
        //update status and send info to all players
        for(int i=0; i<playersList.size(); ++i){
            ClientHandler handler = playersList.get(i);
            Player player = handler.getPlayerFromServer();
            if(player.getPlayerId() == message.getIdPlayer()){
                player.addPlayerMoney(message.getAmount());
            }
            handler.getConnection().sendTCP(message);
        }

        //update info for player who is bankrupt
        for(int i=0;i<playersListBankrupts.size(); ++i){
            //send info to all players
            ClientHandler clientHandler = playersListBankrupts.get(i);
            clientHandler.getConnection().sendTCP(message);
        }
    }

    protected void sendTrapCardToAll(TrapCardMessage message){
        for(int i=0; i<playersList.size(); ++i){
            ClientHandler temp = playersList.get(i);
            temp.getConnection().sendTCP(message);
        }
    }
}
