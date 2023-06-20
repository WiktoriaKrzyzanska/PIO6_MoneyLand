package model.views;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MoneyLandGame;

public class PopUpPawn extends Table {

    private String path;

    private final SpriteBatch spriteBatch;
    private final BitmapFont font;
    private String text;
    private Stage stage;
    private Texture texture;
    private GlyphLayout centerText;

    public PopUpPawn(String text, boolean defaultVisible, int numberPlayer) {
        this.text = text;
        font = new BitmapFont();
        spriteBatch = new SpriteBatch();
        stage = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT), spriteBatch);
        setVisible(defaultVisible);
        Texture buttonTexture = new Texture("OkButton.png");
        Texture buttonHoverTexture = new Texture("OkButtonClicked.png");
        int width = (int) getWidth();
        int height = (int) getHeight();
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(244/255f, 255/255f, 175/255f,1);
        pixmap.fill();
        texture = new Texture(pixmap);
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        buttonStyle.over = new TextureRegionDrawable(new TextureRegion(buttonHoverTexture));

        switch(numberPlayer) {
            case 0:
                path = "assets/pawn/1.png";
                break;
            case 1:
                path = "assets/pawn/2.png";
                break;
            case 2:
                path = "assets/pawn/3.png";
                break;
            case 3:
                path = "assets/pawn/4.png";
                break;
            case 4:
                path = "assets/pawn/5.png";
                break;
            default:
                break;
        }

        Texture pawnTexture = new Texture(path);
        Image pawnImage = new Image(pawnTexture);

        pawnImage.setSize(MoneyLandGame.WIDTH/7, MoneyLandGame.WIDTH/7);
        pawnImage.setPosition((MoneyLandGame.WIDTH/20)*12, (MoneyLandGame.WIDTH/40)*7);

        ImageButton nextButton = new ImageButton(buttonStyle);
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        add(nextButton).width(150).height(100);
        nextButton.setPosition(500,500);
        row();

        stage.addActor(pawnImage);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        centerText = new GlyphLayout();
        centerText.setText(font, text);

        float textWidth = centerText.width;
        float textHeight = centerText.height;

        float x = getX() + getWidth() / 2 - textWidth / 2;
        float y = getY() + getHeight() / 2 + textHeight / 2;

        super.draw(batch, parentAlpha);
        if (isVisible()) {
            spriteBatch.setProjectionMatrix(getStage().getCamera().combined);
            spriteBatch.begin();
            spriteBatch.draw(texture, getX(), getY(), getWidth(), getHeight());
            font.setColor(Color.WHITE);
            int scaleX = 2;
            int scaleY = 2;
            font.getData().setScale(scaleX, scaleY);
            int OFFSET = 100;
            font.draw(spriteBatch, text, x , y + OFFSET);
            spriteBatch.end();
            stage.act();
            stage.draw();
        }
    }



    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        stage.dispose();
        texture.dispose();
    }
}

