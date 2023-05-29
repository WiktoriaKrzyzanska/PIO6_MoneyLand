package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MoneyLandGame;

public class LoadingScreen implements Screen {
    final MoneyLandGame parent;
    Stage stage;
    Texture title;
    Texture start;
    ImageButton startButton;

    public LoadingScreen(final MoneyLandGame game){
        parent = game;
        title = new Texture(Gdx.files.internal("title.png"));
        start = new Texture(Gdx.files.internal("StartButton.png"));

        // Start Button config
        Texture buttonStart = new Texture("title.png");
        Texture buttonHoverStart = new Texture("StartButton.png");
        ImageButton.ImageButtonStyle buttonStyleStart = new ImageButton.ImageButtonStyle();
        buttonStyleStart.up = new TextureRegionDrawable(new TextureRegion(buttonStart));
        buttonStyleStart.over = new TextureRegionDrawable(new TextureRegion(buttonHoverStart));
        startButton = new ImageButton(buttonStyleStart);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(MoneyLandGame. LOADING); // change this to the screen you want to show after clicking start
            }
        });
// Start Button config end
        stage = new Stage(new ScreenViewport());
        stage.addActor(startButton); // add the start button to the stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255/255f, 242/255f, 130/255f, 1);

        parent.batch.begin();
        parent.batch.draw(title, 300, 550, 400, 100);
        parent.batch.draw(start, 350, 120, 300, 400);
        parent.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        startButton.setSize(200, 100); // change this to the size you want for the start button
        startButton.setPosition(stage.getViewport().getWorldWidth() * 0.5f - startButton.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.1f - startButton.getHeight() * 0.5f);
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
