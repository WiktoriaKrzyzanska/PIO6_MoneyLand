package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MoneyLandGame;

public class LobbyIntroduction implements Screen {
    MoneyLandGame parent;
    Stage stage;
    Texture title;
    BitmapFont font;
    ImageButton backButton, nextButton;
    TextField textField;
    Label nameLabel;

    public LobbyIntroduction(final MoneyLandGame game) {
        parent = game;
        title = new Texture(Gdx.files.internal("title.png"));
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(1f);

        NinePatchDrawable textFieldBackground = new NinePatchDrawable(new NinePatch(new Texture(Gdx.files.internal("back.9.png")), 10, 10, 10, 10));
        textFieldBackground.setMinWidth(200f);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = textFieldBackground;
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.BLACK;

        textField = new TextField("", textFieldStyle);
        textField.setPosition((Gdx.graphics.getWidth() - textField.getWidth()) / 2f, (Gdx.graphics.getHeight() - textField.getHeight()) / 2f);

        nameLabel = new Label("Podaj nick", new Label.LabelStyle(font, Color.BLACK));
        nameLabel.setFontScale(1.5f);
        nameLabel.setPosition(textField.getX(), textField.getY() + textField.getHeight() + 10);

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

        Texture buttonNext = new Texture("NextButton.png");
        Texture buttonHoverNext = new Texture("NextButtonClicked.png");

        ImageButton.ImageButtonStyle buttonStyleNext = new ImageButton.ImageButtonStyle();
        buttonStyleNext.up = new TextureRegionDrawable(new TextureRegion(buttonNext));
        buttonStyleNext.over = new TextureRegionDrawable(new TextureRegion(buttonHoverNext));

        nextButton = new ImageButton(buttonStyleNext);
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String nickname = textField.getText();
                //parent.changeScreen(MoneyLandGame.MENU_SCREEN);
            }
        });

        stage = new Stage(new ScreenViewport());
        stage.addActor(textField);
        stage.addActor(nameLabel);
        stage.addActor(backButton);
        stage.addActor(nextButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255 / 255f, 242 / 255f, 130 / 255f, 1);

        parent.batch.begin();
        parent.batch.draw(title, 300, 550, 400, 100);
        parent.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        textField.setPosition((stage.getWidth() - textField.getWidth()) / 2f, (stage.getHeight() - textField.getHeight()) / 2f);
        nameLabel.setPosition(textField.getX(), textField.getY() + textField.getHeight() + 10);
        backButton.setPosition(stage.getViewport().getWorldWidth() * 0.5f - backButton.getWidth() * 0.5f - 100, stage.getViewport().getWorldHeight() * 0.1f - backButton.getHeight() * 0.5f);
        nextButton.setPosition(stage.getViewport().getWorldWidth() * 0.5f - nextButton.getWidth() * 0.5f + 100, stage.getViewport().getWorldHeight() * 0.1f - nextButton.getHeight() * 0.5f);
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