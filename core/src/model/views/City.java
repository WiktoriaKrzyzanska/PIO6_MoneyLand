package model.views;

import com.badlogic.gdx.graphics.Texture;

public class City {
    float price;
    String CityName;
    int id;
    boolean isBought;
    Texture photo;
    boolean isTenementPlaced;
    float tenementPrice;        //kwota za jaką mozna postawic kamienice
    float rentAmount;    //kwota czynszu

    public City(float price, String cityName, Texture photo, float tenementPrice, float rentAmount, int id) {
        this.price = price;
        CityName = cityName;
        this.photo = photo;
        this.id=id;
        this.isBought=false;
        this.isTenementPlaced=false;
        this.tenementPrice = tenementPrice;
        this.rentAmount = rentAmount;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTenementPlaced() {
        return isTenementPlaced;
    }

    public void setTenementPlaced(boolean tenementPlaced) {
        isTenementPlaced = tenementPlaced;
    }

    public void setRentAmount(float rentAmount) {
        this.rentAmount = rentAmount;
    }


    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }


    public float getPrice() {
        return price;
    }

    public Texture getPhoto() {
        return photo;
    }

    public void setPhoto(Texture photo) {
        this.photo = photo;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTenementPrice() {
        return tenementPrice;
    }

    public void setTenementPrice(float tenementPrice) {
        this.tenementPrice = tenementPrice;
    }

    public float getRentAmount() {
        return rentAmount;
    }


    public boolean buyCity(){
        if(!this.isBought){
            this.isBought=true;
            return true; //oznacza ze udalo sie kupic
        }else{
            return false;
        }
    }

    public boolean placeTenement(){
        if(!this.isTenementPlaced){
            this.isTenementPlaced=true;
            this.rentAmount*=1.1; //czynsz wzrasta o 10% w przypadku gdy zostanie postawiona kamienica
            return true;
            //oznacza ze udalo sie postawic kamienice
        }else{
            return false;
            //oznacza ze nie ma mozliwosci postaiwenia kamienicy
        }
    }
}
