package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MoneyLandGame;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Lobby extends Shortcut {
    final MoneyLandGame parent;
    private Stage stage;
    private Texture title;
    private Texture texture;
    private Texture backgroundTexture;
    private ImageButton startButton;
    private final ImageButton.ImageButtonStyle startButtonStyleAvailable;
    private final Texture buttonStart;
    private final Texture buttonHoverStart;
    private final Texture buttonStartNotAvailable;
    private BitmapFont font;
    private Camera camera;
    private Sprite backgroundSprite;
    private Label numberPlayer;
    private final ArrayList<Label> playersNick;
    private int currentNumberOfPlayers;
    private final AtomicBoolean changeScreenToLoading = new AtomicBoolean(false);
    private final int MIN_PLAYERS = 2;


    public Lobby(final MoneyLandGame game){
        super(game);
        parent = game;
        stage = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT));

        // config connect to server or create server and connect
        parent.serverHandler.setConnect();
        parent.serverHandler.setupConnectWithLobbyScreen(this);
        parent.serverHandler.sendMessage(parent.getPlayer()); //send message to server


        camera = new OrthographicCamera();
        camera.viewportHeight = MoneyLandGame.HEIGHT;
        camera.viewportWidth = MoneyLandGame.WIDTH;
        camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);
        camera.update();
        backgroundTexture = new Texture("LobbyIntroduction.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT);


        title = new Texture(Gdx.files.internal("title.png"));
        int width =MoneyLandGame.WIDTH ;
        int height = (int) (MoneyLandGame.HEIGHT * 0.75f);
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(244/255f, 255/255f, 175/255f,1);
        pixmap.fill();
        texture = new Texture(pixmap);

        buttonStart = new Texture("StartButton.png");
        buttonHoverStart = new Texture("StartButtonClicked.png");
        buttonStartNotAvailable = new Texture("StartButton.png");

        font = new BitmapFont();
        font.getData().setScale(3f);

        numberPlayer = new Label( "Czekamy na graczy: "+(parent.sizePlayer()+1)+"/5", new Label.LabelStyle(font, Color.BLACK));
        numberPlayer.setPosition(stage.getViewport().getWorldWidth() * 0.5f - numberPlayer.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.7f - numberPlayer.getHeight() * 0.5f);
        stage.addActor(numberPlayer);

        //create start button style when it will be available
        startButtonStyleAvailable = new ImageButton.ImageButtonStyle();
        startButtonStyleAvailable.up = new TextureRegionDrawable(new TextureRegion(buttonStart));
        startButtonStyleAvailable.over = new TextureRegionDrawable(new TextureRegion(buttonHoverStart));

        //create start button style when it's not available
        ImageButton.ImageButtonStyle startButtonStyleNoAvailable = new ImageButton.ImageButtonStyle();
        startButtonStyleNoAvailable.up = new TextureRegionDrawable(new TextureRegion(buttonStartNotAvailable));

        startButton = new ImageButton(startButtonStyleNoAvailable);
        startButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(parent.sizePlayer() + 1 >= MIN_PLAYERS){
                    parent.serverHandler.sendMessage("Ready for game"); //send message to server that player is ready
                }
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        });


        //create list of players nick
        playersNick = new ArrayList<>();
        Label labelOwnerPlayer = new Label( "1. "+parent.getPlayer().getPlayerName(), new Label.LabelStyle(font, Color.BLACK));
        labelOwnerPlayer.setPosition(stage.getViewport().getWorldWidth() * 0.5f - numberPlayer.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.6f - numberPlayer.getHeight() * 0.5f);
        playersNick.add(labelOwnerPlayer);

        for(int i=0; i<parent.sizePlayer(); ++i){
            Label label = new Label( i+2+". "+parent.getOtherPlayer(i).getPlayerName(), new Label.LabelStyle(font, Color.BLACK));
            label.setPosition(stage.getViewport().getWorldWidth() * 0.5f - numberPlayer.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * (0.6f - 0.1f * (i+1)) - numberPlayer.getHeight() * 0.5f);
            playersNick.add(label);
        }
        currentNumberOfPlayers = parent.sizePlayer() + 1;

        //add players nicks to stage
        for(int i=0; i<currentNumberOfPlayers; ++i){
            stage.addActor(playersNick.get(i));
        }

        stage.addActor(startButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(255/255f, 242/255f, 130/255f, 1);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float textureWidth = screenWidth ;
        float textureHeight = screenHeight  ;
        float textureX = ( textureWidth -1250) ;
        float textureY = (textureHeight - 1055) ;


        //check if there are min players to start game - change button
        if(parent.sizePlayer()+1 >= MIN_PLAYERS){
            startButton.setStyle(startButtonStyleAvailable);
        }

        //start game - message from server
        if(changeScreenToLoading.get()){
            parent.changeScreen(MoneyLandGame.LOADING);
        }

        ScreenUtils.clear(255/255f, 242/255f, 130/255f, 1);

        //check if new player join to game
        if(parent.sizePlayer() != currentNumberOfPlayers-1){
            //add new player nick to list
            for(int i=currentNumberOfPlayers-1; i< parent.sizePlayer(); ++i){
                Label label = new Label( i+2+". "+parent.getOtherPlayer(i).getPlayerName(), new Label.LabelStyle(font, Color.BLACK));
                label.setPosition(stage.getViewport().getWorldWidth() * 0.5f - numberPlayer.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * (0.6f - 0.1f * (i+1)) - numberPlayer.getHeight() * 0.5f);
                playersNick.add(label);
            }
            //add new player nick to stage
            for(int i=currentNumberOfPlayers-1; i<parent.sizePlayer(); ++i){
                stage.addActor(playersNick.get(i+1));
            }

            currentNumberOfPlayers = parent.sizePlayer() + 1;
            numberPlayer.setText("Czekamy na graczy: "+ currentNumberOfPlayers+"/5");
        }

        parent.camera.update();
        parent.batch.setProjectionMatrix(parent.camera.combined);

        parent.batch.begin();
        backgroundSprite.draw(parent.batch);
        parent.batch.draw(texture, textureX, textureY, textureWidth, MoneyLandGame.HEIGHT);
        parent.batch.draw(title, MoneyLandGame.WIDTH/2 - 400, MoneyLandGame.HEIGHT - 400, 800, 200);
        parent.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        startButton.setPosition(stage.getViewport().getWorldWidth() * 0.5f - startButton.getWidth() * 0.5f, stage.getViewport().getWorldHeight() * 0.1f - startButton.getHeight() * 0.5f);
        startButton.setSize(width*0.2f,height*0.2f);
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
        buttonStart.dispose();
        buttonHoverStart.dispose();
        buttonStartNotAvailable.dispose();
        stage.dispose();
        title.dispose();
        font.dispose();
    }

    public void setChangeScreenToLoading() {
        this.changeScreenToLoading.set(true);
    }
}
