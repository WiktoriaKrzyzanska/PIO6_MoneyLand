package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MoneyLandGame;

public class MenuScreen implements Screen {

    final MoneyLandGame parent;
    Stage stage;
    Music music;
    Texture rightImage;
    Texture title;
    ImageButton startButton;

    public MenuScreen(final MoneyLandGame game){
        parent = game;
        music = Gdx.audio.newMusic(Gdx.files.internal("Pim Stones -  Neon Lights.mp3"));
        music.setLooping(true);
        rightImage = new Texture(Gdx.files.internal("szefy_1.png"));
        title = new Texture(Gdx.files.internal("title.png"));

        // Start Button config
        Texture buttonTexture = new Texture("StartButton.png");
        Texture buttonHoverTexture = new Texture("StartButtonClicked.png");

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        buttonStyle.over = new TextureRegionDrawable(new TextureRegion(buttonHoverTexture));

        startButton = new ImageButton(buttonStyle);
        //startButton.setPosition((MoneyLandGame.WIDTH-600)/2, MoneyLandGame.HEIGHT / 2 - startButton.getHeight() / 2);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //future go to next page code
            }
        });
        //startButton config end
        stage = new Stage(new ScreenViewport());
        stage.addActor(startButton);//startButton add
        Gdx.input.setInputProcessor(stage); //This tells the screen to send any input from the user to the stage so it can respond
    }

    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255/255f, 242/255f, 130/255f, 1); //background color

        parent.batch.begin();
        parent.batch.draw(rightImage, 400, 0, 600, MoneyLandGame.HEIGHT);
        parent.batch.draw(title, 0, 500, 400, 100);
        parent.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        startButton.setPosition(stage.getViewport().getWorldWidth() * 0.2f - startButton.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.5f - startButton.getHeight() * 0.5f);

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
        stage.dispose();
        music.dispose();
    }
}
