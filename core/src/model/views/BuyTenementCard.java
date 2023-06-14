package model.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

public class BuyTenementCard implements Disposable {
    private final float width;
    private final float height;
    private final float positionX;
    private final float positionY;
    private Card card;
    private final Texture buyButtonHoverTexture;
    private final ImageButton buyButton;
    private final Label cardName;
    private final Label description;

    public BuyTenementCard(float width, float height, float positionX, float positionY, BitmapFont font, Stage stage, final GameScreen gameScreen) {
        this.width = width;
        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;

        //config font
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        cardName = new Label("",labelStyle);
        cardName.setSize(width,height/5);
        cardName.setPosition(positionX, positionY + height - height/5);
        cardName.setAlignment(Align.center);
        cardName.setVisible(false);
        stage.addActor(cardName);

        description = new Label("", labelStyle);
        description.setSize(width,height - height/5 - height/11);
        description.setPosition(positionX, positionY + height/4);
        description.setAlignment(Align.center);
        description.setVisible(false);
        stage.addActor(description);

        //config button
        Texture buyButtonTexture = new Texture("KupujeButton.png"); //to change
        buyButtonHoverTexture = new Texture("KupujeButtonClicked.png"); //to change
        ImageButton.ImageButtonStyle buyButtonStyle = new ImageButton.ImageButtonStyle();
        buyButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buyButtonTexture));
        buyButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buyButtonHoverTexture));

        buyButton = new ImageButton(buyButtonStyle);
        buyButton.setSize(width*2/3, height/11);
        buyButton.setPosition(positionX + width * 1/6, positionY + height * 1/12);
        buyButton.setVisible(false);

        buyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameScreen.isVisibleBuyTenementCard()){
                    buyButton.setVisible(false); //security for only one click
                    gameScreen.buyTenement(card);
                }
            }
        });

        stage.addActor(buyButton);
    }

    public void draw(ShapeRenderer shapeRenderer){
        //draw main rectangle
        shapeRenderer.setColor(252/255f, 1f, 231/255f,1);
        shapeRenderer.rect(positionX,positionY,width,height);

        //draw rectangle with city name
        shapeRenderer.setColor(64/255f, 1f, 94/255f,1);
        float cityRectHeight = height/5;
        shapeRenderer.rect(positionX,positionY + height - cityRectHeight ,width,cityRectHeight);

        //draw buy button, city name
        if(!buyButton.isVisible() || !cardName.isVisible() || !description.isVisible()){
            buyButton.setVisible(true);
            cardName.setVisible(true);
            description.setVisible(true);
        }

    }

    public void reset(){
        cardName.setVisible(false);
        buyButton.setVisible(false);
        description.setVisible(false);
    }

    public void change(Card card){
        this.card = card;
        cardName.setText(card.cityName.getText());
        description.setText("Cena kamienicy: \n"+ card.getCity().tenementPrice +" cebulionow");
    }

    @Override
    public void dispose() {
        buyButtonHoverTexture.dispose();
        buyButtonHoverTexture.dispose();
    }
}
