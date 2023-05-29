package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MoneyLandGame;

public class Lobby implements Screen {
    final MoneyLandGame parent;
    Stage stage;
    Texture title;
    ImageButton startButton;
    BitmapFont font;
    Label numberPlayer, namePlayerOne;

    public Lobby(final MoneyLandGame game){
        parent = game;
        title = new Texture(Gdx.files.internal("title.png"));

        Texture buttonStart = new Texture("StartButton.png");
        Texture buttonHoverStart = new Texture("StartButtonClicked.png");

        font = new BitmapFont();
        font.getData().setScale(3f);

        numberPlayer = new Label( "Czekamy na graczy "+parent.sizePlayer()+"/5", new Label.LabelStyle(font, Color.BLACK));
        namePlayerOne = new Label( parent.getPlayer(0), new Label.LabelStyle(font, Color.BLACK));


        System.out.println(parent.sizePlayer());

        for(int i = 0; i < parent.sizePlayer(); i++){
            System.out.println(parent.getPlayer(i));
        }

        ImageButton.ImageButtonStyle buttonStyleStart = new ImageButton.ImageButtonStyle();
        buttonStyleStart.up = new TextureRegionDrawable(new TextureRegion(buttonStart));
        buttonStyleStart.over = new TextureRegionDrawable(new TextureRegion(buttonHoverStart));

        startButton = new ImageButton(buttonStyleStart);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(MoneyLandGame.MENU_SCREEN);
            }
        });


        stage = new Stage(new ScreenViewport());

        stage.addActor(numberPlayer);
        stage.addActor(namePlayerOne);

        stage.addActor(startButton);
        //Gdx.input.setInputProcessor(stage);
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
        parent.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        numberPlayer.setPosition(stage.getViewport().getWorldWidth() * 0.5f - numberPlayer.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.7f - numberPlayer.getHeight() * 0.5f);
        namePlayerOne.setPosition(stage.getViewport().getWorldWidth() * 0.5f - namePlayerOne.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.6f - namePlayerOne.getHeight() * 0.5f);

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
