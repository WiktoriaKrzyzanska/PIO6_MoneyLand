package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
    ImageButton volume;

    ImageButton.ImageButtonStyle volumeStyle;
    ImageButton.ImageButtonStyle volumeSilenceStyle;


    public MenuScreen(final MoneyLandGame game){
        parent = game;
        music = Gdx.audio.newMusic(Gdx.files.internal("Pim Stones -  Neon Lights.mp3"));
        music.setLooping(true);
        rightImage = new Texture(Gdx.files.internal("szefy_1.png"));
        title = new Texture(Gdx.files.internal("title.png"));
        stage = new Stage(new ScreenViewport());

        // config sound button
        Texture volumeTexture = new Texture(Gdx.files.internal("audio.png"));
        volumeStyle = new ImageButton.ImageButtonStyle();
        volumeStyle.up = new TextureRegionDrawable(new TextureRegion(volumeTexture));

        Texture silenceTexture = new Texture(Gdx.files.internal("silence.png"));
        volumeSilenceStyle = new ImageButton.ImageButtonStyle();
        volumeSilenceStyle.up = new TextureRegionDrawable(new TextureRegion(silenceTexture));

        volume = new ImageButton (volumeStyle);
        volume.setPosition(5,5);
        volume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (music.isPlaying()) {
                    music.pause();
                    volume.setStyle(volumeSilenceStyle);
                } else {
                    music.play();
                    volume.setStyle(volumeStyle);
                }
            }
        });

        stage.addActor(volume);
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
