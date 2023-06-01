package model.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class PlayerCard {
    private float rectHeight;
    private float rectWidth;
    private float rectPositionX;
    private float rectPositionY;
    private int paddingBetween;
    private Label nick;
    private int money;
    private Label moneyLabel;
    private Color playerColor;
    private Color backgroundColor;
    private float colorRectDimension;
    private Stage stage;

    public PlayerCard(float rectWidth, float rectHeight, float rectPositionX, float rectPositionY, int paddingBetween, String nick, int money, Color color, BitmapFont fontForNick, BitmapFont fontForMoney, Stage stage) {
        this.rectHeight = rectHeight;
        this.rectWidth = rectWidth;
        this.rectPositionX = rectPositionX;
        this.rectPositionY = rectPositionY;
        this.paddingBetween = paddingBetween;
        this.money = money;
        this.playerColor = color;
        backgroundColor = new Color(252/255f,1f,231/255f,1f);
        colorRectDimension = 60;
        this.stage = stage;

        //setup nick
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = fontForNick;

        this.nick = new Label(nick, labelStyle);
        this.nick.setSize(this.rectWidth - colorRectDimension, colorRectDimension);
        this.nick.setPosition(this.rectPositionX + colorRectDimension, this.rectPositionY + this.rectHeight - colorRectDimension);
        this.nick.setAlignment(Align.center);

        //setup money
        labelStyle.font = fontForMoney;
        this.moneyLabel = new Label(String.valueOf(money), labelStyle);
        this.moneyLabel.setSize(this.rectWidth - colorRectDimension, this.rectHeight - colorRectDimension);
        this.moneyLabel.setPosition(this.rectPositionX + colorRectDimension, this.rectPositionY);
        this.moneyLabel.setAlignment(Align.center);

        stage.addActor(this.nick);
        stage.addActor(this.moneyLabel);

    }

    public void draw(ShapeRenderer shapeRenderer){
        //main rectangle
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.rect(
                rectPositionX,
                rectPositionY,
                rectWidth,
                rectHeight
        );

        //small rectangle with color
        shapeRenderer.setColor(playerColor);
        shapeRenderer.rect(
                rectPositionX,
                rectPositionY + rectHeight - colorRectDimension,
                colorRectDimension,
                colorRectDimension
        );

        //update money status
        moneyLabel.setText(money + " cebulionow");


    }


}
