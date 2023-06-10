package model.messages;

public class PlayerMoveMessage {
    private int delta;
    int idPlayer;

    public PlayerMoveMessage() {};

    public PlayerMoveMessage(int delta, int idPlayer) {
        this.delta = delta;
        this.idPlayer = idPlayer;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
