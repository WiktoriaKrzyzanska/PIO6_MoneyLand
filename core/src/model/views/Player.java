package model.views;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String name;
    private int cardNumber;
    private int money;
    private List<City> ownedCities; //soon to be changed to List<City>
    Color color;

    public Player(){
        id = 0;
        cardNumber = 0;
        money = 5000;
        ownedCities = new ArrayList<>();
    }
    public Player(String name){
        this.name = name;
        id = 0;
        cardNumber = 0;
        money = 5000;
        ownedCities = new ArrayList<>();
        color = Color.FOREST; //default
    }

    public Player(int id, String name, int cardNumber, int money, List<City> ownedCities){
        this.id=id;
        this.name=name;
        this.cardNumber=cardNumber;
        this.money=money;
        this.ownedCities=ownedCities;
        color = Color.FOREST; //default
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

    public List<City> getPlayerOwnedCities() {
        return ownedCities;
    }

    public void setPlayerOwnedCities(List<City> ownedCities) {
        this.ownedCities = ownedCities;
    }

    public void updatePlayerPosition(int delta){
        this.cardNumber += delta;
        if(this.cardNumber >= 16){
            this.cardNumber = this.cardNumber - 16;
        }
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
