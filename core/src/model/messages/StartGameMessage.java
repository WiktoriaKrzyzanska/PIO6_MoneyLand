package model.messages;

public class StartGameMessage {
    private int idPlayerWhoStart;
    private boolean amIStart;
    private int idMyPlayer;

    public StartGameMessage(){};
    public StartGameMessage(int idPlayerWhoStart, boolean amIStart, int idMyPlayer) {
        this.idPlayerWhoStart = idPlayerWhoStart;
        this.amIStart = amIStart;
        this.idMyPlayer = idMyPlayer;
    }

    public int getIdPlayerWhoStart() {
        return idPlayerWhoStart;
    }

    public void setIdPlayerWhoStart(int idPlayerWhoStart) {
        this.idPlayerWhoStart = idPlayerWhoStart;
    }

    public boolean isAmIStart() {
        return amIStart;
    }

    public void setAmIStart(boolean amIStart) {
        this.amIStart = amIStart;
    }

    public int getIdMyPlayer() {
        return idMyPlayer;
    }

    public void setIdMyPlayer(int idMyPlayer) {
        this.idMyPlayer = idMyPlayer;
    }
}
