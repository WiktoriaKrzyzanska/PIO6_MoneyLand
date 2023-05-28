package model.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    String cityName;
    float cityNamePositionX;
    float cityNamePositionY;
    Color rectBackground;
    Color rectTitleBackground;

    public Card(float cardWidth, float cardHeight, float cardPositionX, float cardPositionY, Texture cityPhoto, String cityName) {

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

        //config city name
        this.cityName = cityName;

        //config city photo
        this.cityPhoto = cityPhoto;
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
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch){
        if(shapeRenderer == null || batch == null) return;

        //draw rectangles
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //main rectangle
        shapeRenderer.setColor(rectBackground);
        shapeRenderer.rect(cardPositionX, cardPositionY, cardWidth, cardHeight);
        //rectangle with city name
        shapeRenderer.setColor(rectTitleBackground);
        shapeRenderer.rect(boxTextPositionX, boxTextPositionY,boxTextWidth,boxTextHeight);
        shapeRenderer.end();

        //draw textures
        batch.begin();
        batch.draw(cityPhoto, photoPositionX, photoPositionY, photoWidth, photoHeight);
        batch.end();
    }

    @Override
    public void dispose() {
        cityPhoto.dispose();
    }
}
