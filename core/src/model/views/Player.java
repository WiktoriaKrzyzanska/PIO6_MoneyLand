package model.views;

import java.util.List;

public class Player {
    private int id;
    private String name;
    private int cardNumber;
    private int money;
    private List<Integer> ownedCities; //soon to be changed to List<City>

    public Player(int id, String name, int cardNumber, int money, List<Integer> ownedCities){
        this.id=id;
        this.name=name;
        this.cardNumber=cardNumber;
        this.money=money;
        this.ownedCities=ownedCities;
    }

    public int getPlayerId(){
        return id;
    }
    public void setPlayerId(int id){
        this.id=id;
    }
    public String getPlayerName() {
        return name;
    }

    public void setPlayerName(String name) {
        this.name = name;
    }

    public int getPlayerPosition() {
        return cardNumber;
    }

    public void setPlayerPosition(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getPlayerMoney() {
        return money;
    }

    public void setPlayerMoney(int money) {
        this.money = money;
    }
    public void addPlayerMoney(int toAdd){
        this.money+=toAdd;
    }
    public void subtractPlayerMoney(int toSub){
        this.money-=toSub;
    }

    public List<Integer> getPlayerOwnedCities() {
        return ownedCities;
    }

    public void setPlayerOwnedCities(List<Integer> ownedCities) {
        this.ownedCities = ownedCities;
    }


}
