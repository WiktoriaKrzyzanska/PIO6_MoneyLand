package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MoneyLandGame;

public class MenuScreen implements Screen {

    final MoneyLandGame parent;
    Stage stage;
    Music music;
    Texture rightImage;
    Texture title;

    public MenuScreen(final MoneyLandGame game){
        parent = game;
        music = Gdx.audio.newMusic(Gdx.files.internal("Pim Stones -  Neon Lights.mp3"));
        music.setLooping(true);
        rightImage = new Texture(Gdx.files.internal("szefy_1.png"));
        title = new Texture(Gdx.files.internal("title.png"));

        stage = new Stage(new ScreenViewport());
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
