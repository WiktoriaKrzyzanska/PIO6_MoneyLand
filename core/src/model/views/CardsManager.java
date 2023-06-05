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

    City Cyprianka=new City(10,"Cyprianka", new Texture("cards/cyprianka.png"),300,300,0);
    City Badkowo=new City(300,"Badkowo", new Texture("cards/badkowo.png"),300,300,1);
    City Brzeziny=new City(300,"Brzeziny", new Texture("cards/brzeziny.png"),300,300,2);
    City Bydgoszcz=new City(300,"Bydgoszcz", new Texture("cards/bydgoszcz.png"),300,300,3);
    City Bialystok=new City(400,"Bialystok", new Texture("cards/bialystok.png"),300,300,4);
    City Czestochowa=new City(300,"Czestochowa", new Texture("cards/czestochowa.png"),300,300,5);
    City Kowal=new City(300,"Kowal", new Texture("cards/kowal.png"),300,300,6);
    City Krakow=new City(300,"Krakow", new Texture("cards/krakow.png"),300,300,7);
    City Leczyca=new City(300,"Leczyca", new Texture("cards/leczyca.png"),300,300,8);
    City Lodz=new City(300,"Lodz", new Texture("cards/lodz.png"),300,300,9);
    City Mielno=new City(300,"Mielno", new Texture("cards/mielno.png"),300,300,10);
    City Pabianice=new City(300,"Pabianice", new Texture("cards/pabianice.png"),300,300,11);
    City Warszawa=new City(300,"Warszawa", new Texture("cards/warszawa.png"),300,300,12);
    City Wloclawek=new City(300,"Wloclawek", new Texture("cards/wloclawek.png"),300,300,12);
    City Zgierz=new City(300,"Zgierz", new Texture("cards/zgierz.png"),300,300,13);

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
                    this.positionY + cardHeight + padding,
                    Kowal,
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 2 * cardHeight + padding,
               Cyprianka,
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 3 * cardHeight + padding,
                Badkowo,
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + padding,
                this.positionY + 4 * cardHeight + padding,
                Wloclawek,
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Leczyca,
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Mielno,
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Bialystok,
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                Czestochowa,
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 3 * cardHeight + padding,
                Bydgoszcz,
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX  + 4 * cardWidth + padding,
                this.positionY + 2 * cardHeight + padding,
                Krakow,
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + cardHeight + padding,
                Warszawa,
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + padding,
                Brzeziny,
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight - paddingBetweenCards,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + padding,
                Pabianice,
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + padding,
                Lodz,
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + cardWidth + padding,
                this.positionY + padding,
                Zgierz,
                this.font
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

}
