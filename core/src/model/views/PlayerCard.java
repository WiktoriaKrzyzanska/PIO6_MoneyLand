package model.views;

import com.badlogic.gdx.graphics.Color;
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

public class PlayerCard implements Disposable {
    Player player;
    private final float rectHeight;
    private final float rectWidth;
    private final float rectPositionX;
    private final float rectPositionY;
    private int money;
    private final Label moneyLabel;
    private final Color playerColor;
    private final Color backgroundColor;
    private final float colorRectDimension;
    private final Stage stage;

    private ImageButton endMoveButton;
    private Texture  endMoveButtonAvailable;
    private Texture endMoveButtonNoAvailable;
    private Texture endMoveButtonAvailableHover;

    public PlayerCard(Player player,float rectWidth, float rectHeight, float rectPositionX, float rectPositionY, int paddingBetween, String nick, int money, Color color, BitmapFont fontForNick, BitmapFont fontForMoney, Stage stage) {
        this.player = player;
        this.rectHeight = rectHeight;
        this.rectWidth = rectWidth;
        this.rectPositionX = rectPositionX;
        this.rectPositionY = rectPositionY;
        this.money = money;
        this.playerColor = color;
        backgroundColor = new Color(252/255f,1f,231/255f,1f);
        colorRectDimension = 60;
        this.stage = stage;

        //setup nick
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = fontForNick;

        Label nick1 = new Label(nick, labelStyle);
        nick1.setSize(this.rectWidth - colorRectDimension, colorRectDimension);
        nick1.setPosition(this.rectPositionX + colorRectDimension, this.rectPositionY + this.rectHeight - colorRectDimension);
        nick1.setAlignment(Align.center);

        //setup money
        labelStyle.font = fontForMoney;
        this.moneyLabel = new Label(String.valueOf(money), labelStyle);
        this.moneyLabel.setSize(this.rectWidth - colorRectDimension, this.rectHeight - colorRectDimension);
        this.moneyLabel.setPosition(this.rectPositionX + colorRectDimension, this.rectPositionY);
        this.moneyLabel.setAlignment(Align.center);

        stage.addActor(nick1);
        stage.addActor(this.moneyLabel);

    }

    public void setEndMoveButton(final GameScreen board){

        endMoveButtonAvailable = new Texture("KoniecButton.png"); //to change
        endMoveButtonAvailableHover = new Texture("KoniecButtonClicked.png"); //to change
        endMoveButtonNoAvailable = new Texture("KoniecButton.png"); //to change

        //create start button style when it will be available
        ImageButton.ImageButtonStyle endMoveButtonStyleAvailable = new ImageButton.ImageButtonStyle();
        endMoveButtonStyleAvailable.up = new TextureRegionDrawable(new TextureRegion(endMoveButtonAvailable));
        endMoveButtonStyleAvailable.over = new TextureRegionDrawable(new TextureRegion(endMoveButtonAvailableHover));

        //create start button style when it's not available
        ImageButton.ImageButtonStyle endMoveButtonStyleNoAvailable = new ImageButton.ImageButtonStyle();
        endMoveButtonStyleNoAvailable.up = new TextureRegionDrawable(new TextureRegion(endMoveButtonNoAvailable));
        endMoveButtonStyleNoAvailable.over = new TextureRegionDrawable(new TextureRegion(endMoveButtonAvailableHover));
        endMoveButton = new ImageButton(endMoveButtonStyleNoAvailable);

        endMoveButton.setSize(200,100);
        endMoveButton.setPosition(rectPositionX + rectWidth - endMoveButton.getWidth(), rectPositionY);
        endMoveButton.setVisible(false);

        endMoveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.endMyMove();
            }
        });
        stage.addActor(endMoveButton);
    }

    public void showEndMoveButton(){
        if(endMoveButton!=null) endMoveButton.setVisible(true);
    }

    public void hideEndMoveButton(){
        if(endMoveButton!=null) endMoveButton.setVisible(false);
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
        moneyLabel.setText(player.getPlayerMoney() + " cebulionow");

    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public void dispose() {
        if(endMoveButtonAvailable != null) endMoveButtonAvailable.dispose();
        if(endMoveButtonNoAvailable != null) endMoveButtonNoAvailable.dispose();
        if(endMoveButtonAvailableHover != null) endMoveButtonAvailableHover.dispose();
    }

}
