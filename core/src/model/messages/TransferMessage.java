package model.messages;

public class TransferMessage {
    private int idPlayerFrom;
    private int idPlayerTo;
    private float amount;

    public TransferMessage(){};
    public TransferMessage(int idPlayerFrom, int idPlayerTo, float amount) {
        this.idPlayerFrom = idPlayerFrom;
        this.idPlayerTo = idPlayerTo;
        this.amount = amount;
    }

    public int getIdPlayerFrom() {
        return idPlayerFrom;
    }

    public void setIdPlayerFrom(int idPlayerFrom) {
        this.idPlayerFrom = idPlayerFrom;
    }

    public int getIdPlayerTo() {
        return idPlayerTo;
    }

    public void setIdPlayerTo(int idPlayerTo) {
        this.idPlayerTo = idPlayerTo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
