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




    public void setTenementPlaced(boolean tenementPlaced) {
        isTenementPlaced = tenementPlaced;
    }

    public void setRentAmount(float rentAmount) {
        this.rentAmount = rentAmount;
    }

    public float getPrice() {
        return price;
    }


    public float getTenementPrice() {
        return tenementPrice;
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

    public void placeTenement(){
        if(!this.isTenementPlaced){
            this.isTenementPlaced=true;
            this.setRentAmount((float)(getRentAmount()*1.1)); //czynsz wzrasta o 10% w przypadku gdy zostanie postawiona kamienica
        }
    }
}
