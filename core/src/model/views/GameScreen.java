package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MoneyLandGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addListener;

public class GameScreen extends Shortcut {
    final MoneyLandGame parent;
    Stage stage;
    Stage stage2;
    CardsManager cardsManager;

    private float cubeRectPosX;
    private float cubeRectPosY;
    private float cubeRectWith;
    private float cubeRectHeight;
    private Cube cube;

    private ImageButton menuButton;
    private Texture menuButtonTexture;
    private Texture menuButtonHoverTexture;
    private ShapeRenderer shapeRenderer;
    final String text = "We're loading your game!";
    PopUpInformation popUpPlayer;
    PopUpInformation popUpRules;
    PopUpInformation popUpMoney;
    PopUpInformation popUpFirstPlayer;
    ImageButton startButton;
    public GameScreen(MoneyLandGame game){
        super(game);
        parent = game;
        parent.serverHandler.setupConnectWithGameScreen(this);

        float leftSideWidth = MoneyLandGame.WIDTH/6;
        float boardWidth = MoneyLandGame.WIDTH - MoneyLandGame.WIDTH/3;
        float rightSideWidth = MoneyLandGame.WIDTH/6;

        stage = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT));
        stage2 = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT));
        shapeRenderer = new ShapeRenderer ();


        String Welcome = "Czesc " + parent.getPlayerNick() + "!  Oto Twoj pionek";
        String Rules = "Zasady";
        String Cebulion = "Na poczatek gry dosatjesz 500 cebulionow." +
                "Wydawaj je madrze";
        String InformationWhoStarts = "Gre zaczyna Player "; //when we have a method we have to change it



        popUpFirstPlayer = new PopUpInformation(InformationWhoStarts);
        popUpMoney = new PopUpInformation(Cebulion);
        popUpRules = new PopUpInformation( Rules);
        popUpPlayer = new PopUpInformation( Welcome);

        popUpFirstPlayer.setVisible(true);
        popUpPlayer.setVisible(true);
        popUpRules.setVisible(true);
        popUpMoney.setVisible(true);

        //create part with cards
        cardsManager = new CardsManager(boardWidth,  MoneyLandGame.HEIGHT - MoneyLandGame.HEIGHT/6, leftSideWidth, MoneyLandGame.HEIGHT/6);

        //config rectangle for cube and create cube
        cubeRectWith =  rightSideWidth;
        cubeRectHeight = MoneyLandGame.HEIGHT/4;
        cubeRectPosX = MoneyLandGame.WIDTH - cubeRectWith;
        cubeRectPosY = MoneyLandGame.HEIGHT/6;
        cube = new Cube(cubeRectPosX + cubeRectWith/3,cubeRectPosY + cubeRectHeight/4 ,(int)cubeRectWith/3,(int)cubeRectWith/3,stage);

        //config menu button
        menuButtonTexture = new Texture(Gdx.files.internal("MenuButton.png"));
        menuButtonHoverTexture = new Texture("MenuButtonClicked.png");

        ImageButton.ImageButtonStyle buttonStyleMenu = new ImageButton.ImageButtonStyle();
        buttonStyleMenu.up = new TextureRegionDrawable(new TextureRegion(menuButtonTexture));
        buttonStyleMenu.over = new TextureRegionDrawable(new TextureRegion(menuButtonHoverTexture));

        menuButton = new ImageButton(buttonStyleMenu);
        int menuButtonPadding = 40;
        menuButton.setWidth(leftSideWidth - 2 * menuButtonPadding);
        menuButton.setHeight(MoneyLandGame.HEIGHT - MoneyLandGame.HEIGHT * 19/20);
        menuButton.setPosition(leftSideWidth / 2 - (menuButton.getWidth()/3), MoneyLandGame.HEIGHT - menuButton.getHeight()*0.6f -100);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(MoneyLandGame.MENU_SCREEN);
            }
        });


        popUpFirstPlayer.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
        popUpFirstPlayer.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
        popUpMoney.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
        popUpMoney.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
        popUpRules.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
        popUpRules.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
        popUpPlayer.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
        popUpPlayer.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);

        stage2.addActor(popUpFirstPlayer);
        stage2.addActor(popUpMoney);
        stage2.addActor(popUpRules);
        stage2.addActor (popUpPlayer);

        stage.addActor(menuButton);



    }

    @Override
    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(stage2);
        inputMultiplexer.addProcessor((InputProcessor) this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(255/255f, 242/255f, 130/255f, 1); //background color

        parent.camera.update();
        parent.shapeRenderer.setProjectionMatrix(parent.camera.combined); //  This line of code tells the renderer to use our camera to draw everything.
        parent.batch.setProjectionMatrix(parent.camera.combined); //  This line of code tells the renderer to use our camera to draw everything.

        //draw cards
        cardsManager.draw(parent.shapeRenderer, parent.batch, stage); //can't be between methods begin() and end()

        //draw rectangle for cube
        parent.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        parent.shapeRenderer.setColor(252/255f,1f,231/255f,1f);
        parent.shapeRenderer.rect(cubeRectPosX, cubeRectPosY,cubeRectWith,cubeRectHeight);
        parent.shapeRenderer.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage2.act(delta);
        stage.draw();
        stage2.draw ();

    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage2.getViewport().update(width, height, true);
        menuButton.setSize(width*0.2f,height*0.2f);
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
        stage2.dispose();
        menuButtonTexture.dispose();
        menuButtonHoverTexture.dispose();

    }
}
