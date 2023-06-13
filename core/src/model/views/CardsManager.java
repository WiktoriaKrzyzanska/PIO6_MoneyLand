package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;


public class CardsManager {
    private float width;
    private float height;
    private float positionX;
    private float positionY;
    private Color backgroundColor;
    private ArrayList<Card> cards;

    private BitmapFont font;

    private float cardWidth;
    private float cardHeight;
    private float padding;
    private float paddingBetweenCards;

    City Cyprianka=new City(10,"Cyprianka", new Texture("cards/cyprianka.png"),15,300,1);
    City Badkowo=new City(300,"Badkowo", new Texture("cards/badkowo.png"),15,300,2);
    City Brzeziny=new City(300,"Brzeziny", new Texture("cards/brzeziny.png"),15,300,3);
    City Bydgoszcz=new City(300,"Bydgoszcz", new Texture("cards/bydgoszcz.png"),15,300,4);
    City Bialystok=new City(400,"Bialystok", new Texture("cards/bialystok.png"),15,300,5);
    City Czestochowa=new City(300,"Czestochowa", new Texture("cards/czestochowa.png"),15,300,6);
    City Kowal=new City(300,"Kowal", new Texture("cards/kowal.png"),15,15,7);
    City Krakow=new City(300,"Krakow", new Texture("cards/krakow.png"),15,15,8);
    City Leczyca=new City(300,"Leczyca", new Texture("cards/leczyca.png"),15,15,9);
    City Lodz=new City(300,"Lodz", new Texture("cards/lodz.png"),15,15,10);
    City Mielno=new City(300,"Mielno", new Texture("cards/mielno.png"),15,15,11);
    City Pabianice=new City(300,"Pabianice", new Texture("cards/pabianice.png"),15,300,12);
    City Warszawa=new City(300,"Warszawa", new Texture("cards/warszawa.png"),15,300,13);
    City Wloclawek=new City(300,"Wloclawek", new Texture("cards/wloclawek.png"),15,300,14);
    City Zgierz=new City(300,"Zgierz", new Texture("cards/zgierz.png"),15,300,15);

    public CardsManager(float width, float height, float positionX, float positionY) {
        this.width = width;
        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;
        this.cards = new ArrayList<>();
        padding = 30;
        paddingBetweenCards = 20;
        this.cardWidth = (width - 2 * padding) / 5; // 5 cards in row
        this.cardHeight = (height - 2 * padding) / 5; // 5 cards in column

        //config font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();

        //config color
        backgroundColor = new Color();
        backgroundColor.r = 176/255f;
        backgroundColor.g = 189/255f;
        backgroundColor.b = 95/255f;

        //create cards
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight - paddingBetweenCards ,
                this.positionX + padding,
                this.positionY + padding,
                this.font,
                0
        ));
        cards.add(new Card(
                    cardWidth - paddingBetweenCards ,
                    cardHeight - paddingBetweenCards ,
                    this.positionX + padding,
                    this.positionY + cardHeight + padding,
                    Kowal,
                this.font,
                1
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 2 * cardHeight + padding,
               Cyprianka,
                this.font,
                2
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 3 * cardHeight + padding,
                Badkowo,
                this.font,
                3
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + padding,
                this.positionY + 4 * cardHeight + padding,
                Wloclawek,
                this.font,
                4
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Leczyca,
                this.font,
                5
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Mielno,
                this.font,
                6
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Bialystok,
                this.font,
                7
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Czestochowa,
                this.font,
                8
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 3 * cardHeight + padding,
                Bydgoszcz,
                this.font,
                9
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX  + 4 * cardWidth + padding,
                this.positionY + 2 * cardHeight + padding,
                Krakow,
                this.font,
                10
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + cardHeight + padding,
                Warszawa,
                this.font,
                11
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + padding,
                Brzeziny,
                this.font,
                12
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight - paddingBetweenCards,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + padding,
                Pabianice,
                this.font,
                13
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + padding,
                Lodz,
                this.font,
                14
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + cardWidth + padding,
                this.positionY + padding,
                Zgierz,
                this.font,
                15

        ));
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, Stage stage){
        if(batch==null || shapeRenderer==null) return;

        //rectangle for all cards
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.rect(positionX, positionY, width, height);
        shapeRenderer.end();

        //draw all cards
        for(int i=0; i<cards.size(); ++i){
            cards.get(i).draw(shapeRenderer, batch, stage); //can't be between methods begin() and end()
        }
    }

    public Card getCard(int cardNumber){
        if(cardNumber<0 || cardNumber>=16) return null;
        return cards.get(cardNumber);
    }
}
