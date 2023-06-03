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
                    new Texture("cards/kowal.png"),
                "Kowal",
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 2 * cardHeight + padding,
                new Texture("cards/cyprianka.png"),
                "Cyprianka",
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 3 * cardHeight + padding,
                new Texture("cards/badkowo.png"),
                "Badkowo",
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/wloclawek.png"),
                "Wloclawek",
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/leczyca.png"),
                "Leczyca",
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/mielno.png"),
                "Mielno",
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/bialystok.png"),
                "Bialystok",
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/czestochowa.png"),
                "Czestochowa",
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 3 * cardHeight + padding,
                new Texture("cards/bydgoszcz.png"),
                "Bydgoszcz",
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX  + 4 * cardWidth + padding,
                this.positionY + 2 * cardHeight + padding,
                new Texture("cards/krakow.png"),
                "Krakow",
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + cardHeight + padding,
                new Texture("cards/warszawa.png"),
                "Warszawa",
                this.font
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + padding,
                new Texture("cards/brzeziny.png"),
                "Brzeziny",
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight - paddingBetweenCards,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + padding,
                new Texture("cards/pabianice.png"),
                "Pabianice",
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + padding,
                new Texture("cards/lodz.png"),
                "Lodz",
                this.font
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + cardWidth + padding,
                this.positionY + padding,
                new Texture("cards/zgierz.png"),
                "Zgierz",
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
