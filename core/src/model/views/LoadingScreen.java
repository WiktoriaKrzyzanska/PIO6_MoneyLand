package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MoneyLandGame;

public class LoadingScreen extends Shortcut {

    // Use constants for magic numbers and strings

    private static final float B_W = 300f;
    private static final float B_H = 20f;

    private static final String LOADING_TEXT = "Loading %d%%";

    private final MoneyLandGame parent;
    private final AssetManager manager;
    Texture title;
    private final ShapeRenderer shapeRenderer;

    private final SpriteBatch spriteBatch;
    private final BitmapFont font;
    private final BitmapFont fontString;
    final String text = "We're loading your game!";
    final float offset =  160f;


    float timer = 0f;


    /**
     This is an initializing method. It initializes parent and manager like in previous classes.
     Moreover, it creates new object for shape, sprite, font which are used to draw element    */
    public LoadingScreen(final MoneyLandGame game, AssetManager manager) {
        super(game);
        this.parent = game;
        this.manager = manager;
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        fontString = new BitmapFont();

    }
    @Override
    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor((InputProcessor) this);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

/**
 * Render method creates loading bar by drawing it. I tried to make every element have a gap but i still
 * have to fix it to look better. I made everything dependent on time 
 */

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255 / 255f, 242 / 255f, 130 / 255f, 1);
        final GlyphLayout layout = new GlyphLayout(fontString, text);
        float x = Gdx.graphics.getWidth()/2 - layout.width/2;
        float y = Gdx.graphics.getHeight()/2 + layout.height/2;
        title = new Texture(Gdx.files.internal("title.png"));
        float timeElapsed = 25f;
        timer += delta;
        float percentage = timer / timeElapsed * 100f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        fontString.getData().setScale(2, 2);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(x , y , B_W , B_H );

        shapeRenderer.setColor(Color.GREEN);
        if (percentage >= 100f) {
            percentage = 100f;
            //loading is ready -> change screen
            parent.changeScreen(MoneyLandGame.GAME_SCREEN);
        }
        shapeRenderer.rect(x , y, percentage / 100f  * B_W, B_H );
        shapeRenderer.end();
        spriteBatch.begin();
        font.setColor(Color.WHITE);
        fontString.setColor(Color.BLACK);

        font.draw(spriteBatch, String.format(LOADING_TEXT, (int) percentage), x , y +B_H / 2 + font.getLineHeight() / 2);
        spriteBatch.draw(title, Gdx.graphics.getWidth() /2 - title.getWidth()/2 , Gdx.graphics.getHeight()/2 - title.getHeight()/2 + offset );
        fontString.draw(spriteBatch, layout, x, y);
        spriteBatch.end();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        font.dispose();
        fontString.dispose();
        title.dispose();
    }
}
