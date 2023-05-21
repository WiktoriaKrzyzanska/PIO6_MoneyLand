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
    Button volume;

    public MenuScreen(final MoneyLandGame game){
        parent = game;
        music = Gdx.audio.newMusic(Gdx.files.internal("Pim Stones -  Neon Lights.mp3"));
        music.setLooping(true);
        rightImage = new Texture(Gdx.files.internal("szefy_1.png"));
        title = new Texture(Gdx.files.internal("title.png"));
        stage = new Stage(new ScreenViewport());

        Texture volumeImg = new Texture(Gdx.files.internal("audio.png"));
        final Drawable drawable_audio = new TextureRegionDrawable(new TextureRegion(volumeImg));

        Texture silenceImg = new Texture(Gdx.files.internal("silence.png"));
        final Drawable drawable_silence = new TextureRegionDrawable(new TextureRegion(silenceImg));

        volume = new ImageButton (drawable_audio);
        volume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (music.isPlaying()) {
                    music.pause();
                    volume.remove();
                    volume = new ImageButton(drawable_silence);
                    volume.addListener(this);
                    stage.addActor(volume);
                } else {
                    music.play();
                    volume.remove();
                    volume = new ImageButton(drawable_audio);
                    volume.addListener(this);
                    stage.addActor(volume);
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
        volume.draw(parent.batch, 10f);
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
