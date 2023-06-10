package model.messages;

public class CrossedStartMessage {
    private int idPlayer;
    private int amount;

    public CrossedStartMessage(){};
    public CrossedStartMessage(int idPlayer, int amount) {
        this.idPlayer = idPlayer;
        this.amount = amount;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
