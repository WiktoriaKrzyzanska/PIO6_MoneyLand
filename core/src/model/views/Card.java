package model.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;


public class Card implements Disposable {
    float cardWidth;
    float cardHeight;
    float cardPositionX;
    float cardPositionY;
    Texture cityPhoto;
    float photoWidth;
    float photoHeight;
    float photoPositionX;
    float photoPositionY;
    float boxTextWidth;
    float boxTextHeight;
    float boxTextPositionX;
    float boxTextPositionY;
    Label cityName;
    float cityNamePositionX;
    float cityNamePositionY;
    Color rectBackground;
    Color rectTitleBackground;
    private Player owner;
    private City city;
    private int idCard;



    public Card(float cardWidth, float cardHeight, float cardPositionX, float cardPositionY, BitmapFont font, int id) {

        this.idCard = id;

        float partHeight = cardHeight / 10;

        //config main card
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        this.cardPositionX = cardPositionX;
        this.cardPositionY = cardPositionY;

        //config box with city name
        this.boxTextWidth = this.cardWidth;
        this.boxTextHeight = 2 * partHeight;  // 2/10 card
        this.boxTextPositionX = this.cardPositionX;
        this.boxTextPositionY = this.cardPositionY + 8 * partHeight;

        //config city photo
        this.photoWidth = this.cardWidth;
        this.photoHeight = 6 * partHeight; // 6/10 card
        this.photoPositionX = this.cardPositionX;
        this.photoPositionY = this.cardPositionY +  2 * partHeight;

        //config colors
        rectBackground = new Color();
        rectBackground.r = 252/255f;
        rectBackground.g = 255/255f;
        rectBackground.b = 231/255f;


        //config city name
        cityNamePositionX = this.boxTextPositionX;
        cityNamePositionY = this.boxTextPositionY;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        this.cityName = new Label("START", labelStyle);
        this.cityName.setSize(this.boxTextWidth, this.boxTextHeight);
        this.cityName.setPosition(cityNamePositionX,cityNamePositionY);
        this.cityName.setAlignment(Align.center);

        owner = null;
    }

    public Card(float cardWidth, float cardHeight, float cardPositionX, float cardPositionY, City city, BitmapFont font, int id) {

        this.city = city;
        this.idCard = id;

        float partHeight = cardHeight / 10;

        //config main card
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        this.cardPositionX = cardPositionX;
        this.cardPositionY = cardPositionY;

        //config box with city name
        this.boxTextWidth = this.cardWidth;
        this.boxTextHeight = 2 * partHeight;  // 2/10 card
        this.boxTextPositionX = this.cardPositionX;
        this.boxTextPositionY = this.cardPositionY + 8 * partHeight;

        //config city photo
        this.cityPhoto = city.photo;
        this.photoWidth = this.cardWidth;
        this.photoHeight = 6 * partHeight; // 6/10 card
        this.photoPositionX = this.cardPositionX;
        this.photoPositionY = this.cardPositionY +  2 * partHeight;

        //config colors
        rectBackground = new Color();
        rectBackground.r = 252/255f;
        rectBackground.g = 255/255f;
        rectBackground.b = 231/255f;
        rectTitleBackground = new Color(rectBackground);


        //config city name
        cityNamePositionX = this.boxTextPositionX;
        cityNamePositionY = this.boxTextPositionY;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        this.cityName = new Label(city.CityName, labelStyle);
        this.cityName.setSize(this.boxTextWidth, this.boxTextHeight);
        this.cityName.setPosition(cityNamePositionX,cityNamePositionY);
        this.cityName.setAlignment(Align.center);

        owner = null;
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, Stage stage){
        if(shapeRenderer == null || batch == null) return;

        //draw rectangles
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //main rectangle
        shapeRenderer.setColor(rectBackground);
        shapeRenderer.rect(cardPositionX, cardPositionY, cardWidth, cardHeight);
        //rectangle with city name
        if(rectTitleBackground != null){
            shapeRenderer.setColor(rectTitleBackground);
            shapeRenderer.rect(boxTextPositionX, boxTextPositionY,boxTextWidth,boxTextHeight);
        }

        shapeRenderer.end();

        //draw textures
        batch.begin();
        if(cityPhoto!=null) batch.draw(cityPhoto, photoPositionX, photoPositionY, photoWidth, photoHeight);
        batch.end();

        //draw text
        stage.addActor(cityName);

    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }


    public City getCity() {
        return city;
    }

    public void setRectTitleBackground(Color rectTitleBackground) {
        if(rectTitleBackground == null) return;
        this.rectTitleBackground = rectTitleBackground;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public void setDefaultRectTitleBackground(){
        rectBackground = new Color();
        rectBackground.r = 252/255f;
        rectBackground.g = 255/255f;
        rectBackground.b = 231/255f;
        rectTitleBackground = new Color(rectBackground);
    }
    @Override
    public void dispose() {
        cityPhoto.dispose();
    }
}
