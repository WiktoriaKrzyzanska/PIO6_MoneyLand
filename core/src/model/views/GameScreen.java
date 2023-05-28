package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MoneyLandGame;

public class GameScreen implements Screen {
    final MoneyLandGame parent;
    Stage stage;

    public GameScreen(MoneyLandGame game){
        parent = game;

        //create stage
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage); //This tells the screen to send any input from the user to the stage so it can respond

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255/255f, 242/255f, 130/255f, 1); //background color

        parent.camera.update();
        parent.batch.setProjectionMatrix(parent.camera.combined); //  This line of code tells the renderer to use our camera to draw everything.


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
        stage.dispose();

    }
}
