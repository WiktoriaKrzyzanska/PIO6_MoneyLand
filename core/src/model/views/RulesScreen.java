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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MoneyLandGame;

public class RulesScreen implements Screen {
    final MoneyLandGame parent;
    Stage stage;
    Texture title;
    Texture rules;
    ImageButton backButton;

    public RulesScreen(final MoneyLandGame game){
        parent = game;
        title = new Texture(Gdx.files.internal("title.png"));
        rules = new Texture(Gdx.files.internal("Rules.png"));

        // Back Button config
        Texture buttonBack = new Texture("BackButton.png");
        Texture buttonHoverBack = new Texture("BackButtonClicked.png");

        ImageButton.ImageButtonStyle buttonStyleBack = new ImageButton.ImageButtonStyle();
        buttonStyleBack.up = new TextureRegionDrawable(new TextureRegion(buttonBack));
        buttonStyleBack.over = new TextureRegionDrawable(new TextureRegion(buttonHoverBack));

        backButton = new ImageButton(buttonStyleBack);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(MoneyLandGame.MENU_SCREEN);
            }
        });
        // Back Button config end

        stage = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT));
        stage.addActor(backButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255/255f, 242/255f, 130/255f, 1);

        parent.camera.update();
        parent.batch.setProjectionMatrix(parent.camera.combined); //  This line of code tells the renderer to use our camera to draw everything.

        parent.batch.begin();
        parent.batch.draw(title, MoneyLandGame.WIDTH/2 - 400, MoneyLandGame.HEIGHT - 200, 800, 200);
        parent.batch.draw(rules, MoneyLandGame.WIDTH/3, MoneyLandGame.HEIGHT/6, MoneyLandGame.WIDTH/3, MoneyLandGame.HEIGHT * 5/6 - 200);
        parent.batch.end();

        backButton.setPosition(stage.getViewport().getWorldWidth() * 0.5f - backButton.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.1f - backButton.getHeight() * 0.5f);

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
        title.dispose();
        rules.dispose();
        stage.dispose();
    }
}
