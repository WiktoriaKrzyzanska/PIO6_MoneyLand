package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class Lobby extends Shortcut {
    final MoneyLandGame parent;
    Stage stage;
    Texture title;
    ImageButton startButton;
    BitmapFont font;
    Label numberPlayer;
    private ArrayList<Label> playersNick;
    private int currentNumberOfPlayers;

    public Lobby(final MoneyLandGame game){
        super(game);
        parent = game;

        // config connect to server or create server and connect
        parent.serverHandler.setConnect();
        parent.serverHandler.setupConnectWithLobbyScreen(this);
        parent.serverHandler.sendMessage(parent.getPlayer()); //send message to server


        title = new Texture(Gdx.files.internal("title.png"));

        Texture buttonStart = new Texture("StartButton.png");
        Texture buttonHoverStart = new Texture("StartButtonClicked.png");

        font = new BitmapFont();
        font.getData().setScale(3f);

        numberPlayer = new Label( "Czekamy na graczy: "+(parent.sizePlayer()+1)+"/5", new Label.LabelStyle(font, Color.BLACK));


        ImageButton.ImageButtonStyle buttonStyleStart = new ImageButton.ImageButtonStyle();
        buttonStyleStart.up = new TextureRegionDrawable(new TextureRegion(buttonStart));
        buttonStyleStart.over = new TextureRegionDrawable(new TextureRegion(buttonHoverStart));

        startButton = new ImageButton(buttonStyleStart);
        startButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(MoneyLandGame.LOADING);
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
        stage = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT));

        stage.addActor(numberPlayer);


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
        inputMultiplexer.addProcessor((InputProcessor) this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
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
        stage.dispose();
        title.dispose();
        font.dispose();
    }
}
