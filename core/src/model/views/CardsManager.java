package model.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;


public class CardsManager {
    private float width;
    private float height;
    private float positionX;
    private float positionY;
    private Color backgroundColor;
    private ArrayList<Card> cards;

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
        padding = 3;
        paddingBetweenCards = 1;
        this.cardWidth = (width - 2 * padding) / 5; // 5 cards in row
        this.cardHeight = (height - 2 * padding) / 5; // 5 cards in column

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
                "Kowal"
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 2 * cardHeight + padding,
                new Texture("cards/cyprianka.png"),
                "Cyprianka"
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + padding,
                this.positionY + 3 * cardHeight + padding,
                new Texture("cards/badkowo.png"),
                "Bądkowo"
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/wloclawek.png"),
                "Włocławek"
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/leczyca.png"),
                "Łęczyca"
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/mielno.png"),
                "Mielno"
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/bialystok.png"),
                "Białystok"
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 4 * cardHeight + padding,
                new Texture("cards/czestochowa.png"),
                "Częstochowa"
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + 3 * cardHeight + padding,
                new Texture("cards/bydgoszcz.png"),
                "Bydgoszcz"
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX  + 4 * cardWidth + padding,
                this.positionY + 2 * cardHeight + padding,
                new Texture("cards/krakow.png"),
                "Kraków"
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + cardHeight + padding,
                new Texture("cards/warszawa.png"),
                "Warszawa"
        ));
        cards.add(new Card(
                cardWidth,
                cardHeight - paddingBetweenCards ,
                this.positionX + 4 * cardWidth + padding,
                this.positionY + padding,
                new Texture("cards/brzeziny.png"),
                "Brzeziny"
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards ,
                cardHeight - paddingBetweenCards,
                this.positionX + 3 * cardWidth + padding,
                this.positionY + padding,
                new Texture("cards/pabianice.png"),
                "Pabianice"));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + 2 * cardWidth + padding,
                this.positionY + padding,
                new Texture("cards/lodz.png"),
                "Łódź"
        ));
        cards.add(new Card(
                cardWidth - paddingBetweenCards,
                cardHeight - paddingBetweenCards,
                this.positionX + cardWidth + padding,
                this.positionY + padding,
                new Texture("cards/zgierz.png"),
                "Zgierz"
        ));
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch){
        if(batch==null || shapeRenderer==null) return;

        //rectangle for all cards
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.rect(positionX, positionY, width, height);
        shapeRenderer.end();

        //draw all cards
        for(int i=0; i<cards.size(); ++i){
            cards.get(i).draw(shapeRenderer, batch); //can't be between methods begin() and end()
        }
    }
}
