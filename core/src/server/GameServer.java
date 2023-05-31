package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.MoneyLandGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameServer{

    private Server server;
    private AtomicBoolean serverReady = new AtomicBoolean(false);
    private ArrayList<String> playersList;
    private Connection[] otherPlayers;

    public void start(){
        server = new Server();

        //class register - is required from documentation; important: must be same order in client and here
        Kryo kryo = server.getKryo();
        kryo.register(ArrayList.class);

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
                if (object instanceof String) {
                    String playerNick = (String)object;
                    System.out.println("Player " + playerNick + " is connected");
                    playersList.add(playerNick);
                    connection.sendTCP(playersList); //send to player list of all other players

                    //update for others players - new player
                    otherPlayers = server.getConnections();
                    for(int i=0; i< otherPlayers.length; ++i){
                        Connection connection1 = otherPlayers[i];
                        if(connection1 != connection){
                            connection1.sendTCP(playerNick);
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
