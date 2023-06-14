package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MoneyLandGame;

public class PopUpEnd extends Table {
    final MoneyLandGame parent;
    private final SpriteBatch spriteBatch;
    private final BitmapFont font;
    private ImageButton menuButton;
    String text;
    Stage stage;
    Texture texture;
    GlyphLayout centerText;
    private int OFFSET = 100;
    private int scaleX = 2;
    private int scaleY = 2;

    public PopUpEnd(String text, boolean defaultVisible, final MoneyLandGame game) {
        parent = game;
        this.text = text;
        font = new BitmapFont();
        spriteBatch = new SpriteBatch();
        stage = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT), spriteBatch);
        setVisible(defaultVisible);
        Texture buttonTexture = new Texture("MenuButton.png");
        Texture buttonHoverTexture = new Texture("MenuButtonClicked.png");
        int width = (int) getWidth();
        int height = (int) getHeight();
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(244/255f, 255/255f, 175/255f,1);
        pixmap.fill();
        texture = new Texture(pixmap);
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        buttonStyle.over = new TextureRegionDrawable(new TextureRegion(buttonHoverTexture));;

        menuButton = new ImageButton(buttonStyle);
        add(menuButton).width(150).height(100);
        menuButton.setPosition(500,500);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(MoneyLandGame.MENU_SCREEN);
            }
        });

        row();
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
            font.getData().setScale(scaleX, scaleY);
            font.draw(spriteBatch, text, x , y + OFFSET);
            spriteBatch.end();
            stage.act();
            stage.draw();
        }
    }

    public void setText(String text){
        this.text = text;
    }

    public void showPopUp(){
        setVisible(true);
    }

    public void hidePopUp(){
        setVisible(false);
    }

    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        stage.dispose();
        texture.dispose();
    }
}