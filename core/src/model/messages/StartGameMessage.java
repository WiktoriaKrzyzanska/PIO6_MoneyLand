package model.messages;

public class StartGameMessage {
    private int idPlayerWhoStart;
    private boolean amIStart;

    public StartGameMessage(){};
    public StartGameMessage(int idPlayerWhoStart, boolean amIStart) {
        this.idPlayerWhoStart = idPlayerWhoStart;
        this.amIStart = amIStart;
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
}
