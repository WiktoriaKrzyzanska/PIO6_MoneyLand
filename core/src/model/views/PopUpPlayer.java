package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MoneyLandGame;

public class PopUpPlayer extends Group {

    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;
    private final BitmapFont font;
    private ImageButton startButton;
    String text;
    Stage stage;

    public PopUpPlayer(ShapeRenderer shapeRenderer, String text) {
        this.shapeRenderer = shapeRenderer;
        this.text = text;
        stage = new Stage();
        font = new BitmapFont();
        spriteBatch = new SpriteBatch();
        setVisible(true);
        Texture buttonTexture = new Texture("StartButton.png");
        Texture buttonHoverTexture = new Texture("StartButtonClicked.png");

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        buttonStyle.over = new TextureRegionDrawable(new TextureRegion(buttonHoverTexture));

        startButton = new ImageButton(buttonStyle);
        startButton.setPosition(1000,1000);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }

        });

        stage.addActor(startButton);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isVisible()) {
            shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(244/255f, 255/255f, 175/255f,1);
            shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
            shapeRenderer.end();
            spriteBatch.begin();
            font.setColor(Color.BLACK);
            font.getData().setScale(2, 2);
            font.draw(spriteBatch, text, getX() , getY() + 300);
            spriteBatch.end();
        }
    }

    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
    }
}



