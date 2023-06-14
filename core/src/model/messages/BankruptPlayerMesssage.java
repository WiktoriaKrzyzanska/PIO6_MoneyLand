package model.messages;

public class BankruptPlayerMesssage {
    private int idBankruptPlayer;

    public BankruptPlayerMesssage(){};

    public BankruptPlayerMesssage(int idBankruptPlayer) {
        this.idBankruptPlayer = idBankruptPlayer;
    }

    public int getIdBankruptPlayer() {
        return idBankruptPlayer;
    }

    public void setIdBankruptPlayer(int idBankruptPlayer) {
        this.idBankruptPlayer = idBankruptPlayer;
    }
}
