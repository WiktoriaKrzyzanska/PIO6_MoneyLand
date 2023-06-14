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
    private final float width;
    private final float height;
    private final float positionX;
    private final float positionY;
    private final Color backgroundColor;
    private final ArrayList<Card> cards;

    City Cyprianka=new City(500,"Cyprianka", new Texture("cards/cyprianka.png"),300,200,1);
    City Badkowo=new City(500,"Badkowo", new Texture("cards/badkowo.png"),300,200,2);
    City Brzeziny=new City(1000,"Brzeziny", new Texture("cards/brzeziny.png"),300,300,3);
    City Bydgoszcz=new City(4000,"Bydgoszcz", new Texture("cards/bydgoszcz.png"),300,300,4);
    City Bialystok=new City(4000,"Bialystok", new Texture("cards/bialystok.png"),300,300,5);
    City Czestochowa=new City(3000,"Czestochowa", new Texture("cards/czestochowa.png"),300,300,6);
    City Kowal=new City(1000,"Kowal", new Texture("cards/kowal.png"),300,300,7);
    City Krakow=new City(4000,"Krakow", new Texture("cards/krakow.png"),300,300,8);
    City Leczyca=new City(2000,"Leczyca", new Texture("cards/leczyca.png"),300,300,9);
    City Lodz=new City(3000,"Lodz", new Texture("cards/lodz.png"),300,300,10);
    City Mielno=new City(1000,"Mielno", new Texture("cards/mielno.png"),300,300,11);
    City Pabianice=new City(2000,"Pabianice", new Texture("cards/pabianice.png"),300,300,12);
    City Warszawa=new City(5000,"Warszawa", new Texture("cards/warszawa.png"),300,400,13);
    City Wloclawek=new City(3000,"Wloclawek", new Texture("cards/wloclawek.png"),300,300,14);
    City Zgierz=new City(3000,"Zgierz", new Texture("cards/zgierz.png"),300,-300,15);


    public CardsManager(float width, float height, float positionX, float positionY) {
        this.width = width;
        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;
        this.cards = new ArrayList<>();
        float padding = 30;
        float paddingBetweenCards = 20;
        float cardWidth = (width - 2 * padding) / 5; // 5 cards in row
        float cardHeight = (height - 2 * padding) / 5; // 5 cards in column

        //config font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        parameter.color = Color.BLACK;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        //config color
        backgroundColor = new Color();
        backgroundColor.r = 176/255f;
        backgroundColor.g = 189/255f;
        backgroundColor.b = 95/255f;

        //create cards
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + padding,
                font,
                0
        ));
        cards.add(new Card(
                    cardWidth - paddingBetweenCards,
                    cardHeight - paddingBetweenCards,
                    this.positionX + padding,
                    this.positionY + cardHeight + padding,
                    Kowal,
                font,
                1
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 2 * cardHeight + padding,
               Cyprianka,
                font,
                2
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 3 * cardHeight + padding,
                Badkowo,
                font,
                3
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight,
                this.positionX + padding,
                this.positionY + 4 * cardHeight + padding,
                Wloclawek,
                font,
                4
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight,
                this.positionX + cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Leczyca,
                font,
                5
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Mielno,
                font,
                6
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Bialystok,
                font,
                7
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Czestochowa,
                font,
                8
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 3 * cardHeight + padding,
                Bydgoszcz,
                font,
                9
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards,
                this.positionX  + 4 * cardWidth + padding,
                this.positionY + 2 * cardHeight + padding,
                Krakow,
                font,
                10
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + cardHeight + padding,
                Warszawa,
                font,
                11
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + padding,
                Brzeziny,
                font,
                12
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + padding,
                Pabianice,
                font,
                13
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + padding,
                Lodz,
                font,
                14
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + cardWidth + padding,
                this.positionY + padding,
                Zgierz,
                font,
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
        for (Card card : cards) {
            card.draw(shapeRenderer, batch, stage); //can't be between methods begin() and end()
        }
    }

    public Card getCard(int cardNumber){
        if(cardNumber<0 || cardNumber>=16) return null;
        return cards.get(cardNumber);
    }
}
