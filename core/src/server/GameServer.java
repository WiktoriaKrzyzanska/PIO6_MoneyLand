package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.MoneyLandGame;
import model.views.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameServer{

    private Server server;
    private AtomicBoolean serverReady = new AtomicBoolean(false);
    private ArrayList<ClientHandler> playersList;

    public void start(){
        server = new Server();

        //class register - is required from documentation; important: must be same order in client and here
        Kryo kryo = server.getKryo();
        kryo.register(ArrayList.class);
        kryo.register(Player.class);

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
                if (object instanceof Player) {
                    //get list of all other players
                    ArrayList<Player> otherPlayersList = new ArrayList<>();
                    for(int i=0; i<playersList.size(); ++i){
                        otherPlayersList.add(playersList.get(i).getPlayerFromServer());
                    }

                    connection.sendTCP(otherPlayersList); //send to player list of all other players

                    Player player = (Player)object;
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
}
