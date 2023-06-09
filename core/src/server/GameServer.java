package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.MoneyLandGame;
import model.messages.StartGameMessage;
import model.views.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameServer{

    private Server server;
    private AtomicBoolean serverReady = new AtomicBoolean(false);
    private ArrayList<ClientHandler> playersList;
    private AtomicBoolean startGame = new AtomicBoolean(false);
    private final int MAX_PLAYERS = 5;
    private final int MIN_PLAYERS = 2;
    private int idPlayerMove;

    public void start(){
        server = new Server();

        //class register - is required from documentation; important: must be same order in client and here
        Kryo kryo = server.getKryo();
        kryo.register(ArrayList.class);
        kryo.register(Player.class);
        kryo.register(StartGameMessage.class);

        server.start();
        try{
            server.bind(MoneyLandGame.portTCP, MoneyLandGame.portUDP);
            serverReady.set(true);
        }catch(IOException e){
            return;
        }

        //create list to contain players nick
        playersList = new ArrayList<>();

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
                                }else{
                                    startGameMessage.setIdPlayerWhoStart(idPlayerMove);
                                    startGameMessage.setAmIStart(false);
                                }

                                temp.getConnection().sendTCP(startGameMessage);
                            }
                            startGame.set(true); //start game - no new players
                        }

                    }
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
        if(idPlayerMove+1<playersList.size()){
            idPlayerMove++;
        }else{
            idPlayerMove=0;
        }
    }
}
