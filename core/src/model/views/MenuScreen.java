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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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

public class MenuScreen implements Screen {

    final MoneyLandGame parent;
    Stage stage;
    Music music;
    Texture rightImage;
    Texture title;
    ImageButton startButton;
    ImageButton rulesButton;
    ImageButton volume;
    ImageButton.ImageButtonStyle volumeStyle;
    ImageButton.ImageButtonStyle volumeSilenceStyle;

    float leftSideWidth;
    float rightSideWidth;

    public MenuScreen(final MoneyLandGame game){
        parent = game;

        //create stage
        stage = new Stage(new ScreenViewport());

        //set sides width (screen is divied to 2 part)
        leftSideWidth = stage.getViewport().getWorldWidth()/3;
        rightSideWidth = stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/3;

        //music config
        music = Gdx.audio.newMusic(Gdx.files.internal("Pim Stones -  Neon Lights.mp3"));
        music.setLooping(true);

        //set graphics
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
                parent.changeScreen(MoneyLandGame.LOBBY_INTRODUCTION_SCREEN);
            }
        });

        //startButton config end
        stage = new Stage(new ScreenViewport());

        // config sound button
        Texture volumeTexture = new Texture(Gdx.files.internal("audio.png"));
        volumeStyle = new ImageButton.ImageButtonStyle();
        volumeStyle.up = new TextureRegionDrawable(new TextureRegion(volumeTexture));

        Texture silenceTexture = new Texture(Gdx.files.internal("silence.png"));
        volumeSilenceStyle = new ImageButton.ImageButtonStyle();
        volumeSilenceStyle.up = new TextureRegionDrawable(new TextureRegion(silenceTexture));

        volume = new ImageButton (volumeStyle);
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

        // Rules Button config
        Texture buttonRules = new Texture("ZasadyButton.png");
        Texture buttonHoverRules = new Texture("ZasadyButtonClicked.png");

        ImageButton.ImageButtonStyle buttonStyleRules = new ImageButton.ImageButtonStyle();
        buttonStyleRules.up = new TextureRegionDrawable(new TextureRegion(buttonRules));
        buttonStyleRules.over = new TextureRegionDrawable(new TextureRegion(buttonHoverRules));

        rulesButton = new ImageButton(buttonStyleRules);
        rulesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(MoneyLandGame.RULES_SCREEN);
            }
        });

        // Rules Button config end

        //add actors to stage
        stage.addActor(volume);
        stage.addActor(rulesButton);
        stage.addActor(startButton);

        Gdx.input.setInputProcessor(stage); //This tells the screen to send any input from the user to the stage so it can respond
    }

    @Override
    public void show() {
        music.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255/255f, 242/255f, 130/255f, 1); //background color

        parent.camera.update();
        parent.batch.setProjectionMatrix(parent.camera.combined); //  This line of code tells the renderer to use our camera to draw everything.

        parent.batch.begin();
        parent.batch.draw(rightImage, MoneyLandGame.WIDTH/3, 0, MoneyLandGame.WIDTH - MoneyLandGame.WIDTH/3, MoneyLandGame.HEIGHT);
        parent.batch.draw(title, 0, MoneyLandGame.HEIGHT - MoneyLandGame.HEIGHT / 4, MoneyLandGame.WIDTH/3, MoneyLandGame.HEIGHT/8);
        parent.batch.end();

        startButton.setPosition(leftSideWidth * 0.5f - startButton.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.5f - startButton.getHeight() * 0.5f);
        rulesButton.setPosition(leftSideWidth * 0.5f - rulesButton.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.4f - rulesButton.getHeight() * 0.5f);
        volume.setPosition(5,5);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        //update sides width
        leftSideWidth = stage.getViewport().getWorldWidth()/3;
        rightSideWidth = stage.getViewport().getWorldWidth() - stage.getViewport().getWorldWidth()/3;
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
        rightImage.dispose();
        title.dispose();
        stage.dispose();
        music.dispose();
    }
}
