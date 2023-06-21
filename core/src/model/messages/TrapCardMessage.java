package model.messages;

public class TrapCardMessage {
    private int idCard;

    public TrapCardMessage(){};

    public TrapCardMessage(int idCard) {
        this.idCard = idCard;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }
}
