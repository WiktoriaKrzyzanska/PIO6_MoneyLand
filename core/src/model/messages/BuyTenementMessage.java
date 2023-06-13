package model.messages;

public class BuyTenementMessage {
    private int cardNumber;
    private float amount;
    private int idPlayer;

    public BuyTenementMessage(){};
    public BuyTenementMessage(int cardNumber, float amount, int idPlayer) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.idPlayer = idPlayer;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
