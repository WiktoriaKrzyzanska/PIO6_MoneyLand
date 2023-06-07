package server;

import com.esotericsoftware.kryonet.Connection;
import model.views.Player;

public class ClientHandler {
    private Player player;
    private Connection connection;
    private Boolean playerIsReady;

    protected ClientHandler(Player player, Connection connection) {
        this.player = player;
        this.connection = connection;
        playerIsReady = false;
    }

    protected Player getPlayerFromServer() {
        return player;
    }

    protected void setPlayer(Player player) {
        this.player = player;
    }

    protected Connection getConnection() {
        return connection;
    }

    protected void setConnection(Connection connection) {
        this.connection = connection;
    }

    protected Boolean getPlayerIsReady() {
        return playerIsReady;
    }

    protected void setPlayerIsReady(Boolean playerIsReady) {
        this.playerIsReady = playerIsReady;
    }
}
