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
    Label numberPlayer, namePlayerOne, namePlayerTwo, namePlayerThree, namePlayerFour, namePlayerFive;

    public Lobby(final MoneyLandGame game){
        parent = game;
        title = new Texture(Gdx.files.internal("title.png"));

        Texture buttonStart = new Texture("StartButton.png");
        Texture buttonHoverStart = new Texture("StartButtonClicked.png");

        font = new BitmapFont();
        font.getData().setScale(3f);

        numberPlayer = new Label( "Czekamy na graczy: "+parent.sizePlayer()+"/5", new Label.LabelStyle(font, Color.BLACK));

        try{
            namePlayerOne = new Label( "1. "+parent.getPlayer(0), new Label.LabelStyle(font, Color.BLACK));
            namePlayerTwo = new Label( "2. "+parent.getPlayer(1), new Label.LabelStyle(font, Color.BLACK));
            namePlayerThree = new Label( "3. "+parent.getPlayer(2), new Label.LabelStyle(font, Color.BLACK));
            namePlayerFour = new Label( "4. "+parent.getPlayer(3), new Label.LabelStyle(font, Color.BLACK));
            namePlayerFive = new Label( "5. "+parent.getPlayer(4), new Label.LabelStyle(font, Color.BLACK));
        }catch (Exception ignored){
        }

        ImageButton.ImageButtonStyle buttonStyleStart = new ImageButton.ImageButtonStyle();
        buttonStyleStart.up = new TextureRegionDrawable(new TextureRegion(buttonStart));
        buttonStyleStart.over = new TextureRegionDrawable(new TextureRegion(buttonHoverStart));

        startButton = new ImageButton(buttonStyleStart);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(MoneyLandGame.LOADING);
            }
        });

        stage = new Stage(new ScreenViewport());

        stage.addActor(numberPlayer);

        try{
            stage.addActor(namePlayerOne);
            stage.addActor(namePlayerTwo);
            stage.addActor(namePlayerThree);
            stage.addActor(namePlayerFour);
            stage.addActor(namePlayerFive);
        }catch(Exception ignored){
        }

        stage.addActor(startButton);
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
        parent.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        numberPlayer.setPosition(stage.getViewport().getWorldWidth() * 0.5f - numberPlayer.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.7f - numberPlayer.getHeight() * 0.5f);

        try{
            namePlayerOne.setPosition(stage.getViewport().getWorldWidth() * 0.35f, stage.getViewport().getWorldHeight() * 0.6f - namePlayerOne.getHeight() * 0.5f);
            namePlayerTwo.setPosition(stage.getViewport().getWorldWidth() * 0.35f, stage.getViewport().getWorldHeight() * 0.5f - namePlayerTwo.getHeight() * 0.5f);
            namePlayerThree.setPosition(stage.getViewport().getWorldWidth() * 0.35f, stage.getViewport().getWorldHeight() * 0.4f - namePlayerThree.getHeight() * 0.5f);
            namePlayerFour.setPosition(stage.getViewport().getWorldWidth() * 0.35f, stage.getViewport().getWorldHeight() * 0.3f - namePlayerFour.getHeight() * 0.5f);
            namePlayerFive.setPosition(stage.getViewport().getWorldWidth() * 0.35f, stage.getViewport().getWorldHeight() * 0.2f - namePlayerFive.getHeight() * 0.5f);
        }catch(Exception ignored){
        }

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
