package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MoneyLandGame;
import model.messages.*;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addListener;

import java.util.ArrayList;

public class GameScreen extends Shortcut {
    final MoneyLandGame parent;
    Stage stage;
    Stage stage2;
    CardsManager cardsManager;

    private TextButton showPopUpButton;
    private PopUpAction popUp;

    private float cubeRectPosX;
    private float cubeRectPosY;
    private float cubeRectWith;
    private float cubeRectHeight;
    private Cube cube;
    private ImageButton menuButton;
    private Texture menuButtonTexture;
    private Texture menuButtonHoverTexture;
    private Sound faliureSound;

    //attributes for show rectangle with other players info (left side screen)
    private float rectHeightOtherPlayerInfo;
    private float rectWidthOtherPlayerInfo;
    private float rectOtherPlayerInfoPositionX;
    private float rectOtherPlayerInfoPositionY;
    private int paddingBetweenPlayersInfo;

    private ArrayList<PlayerCard> otherPlayerCards;
    private PlayerCard playerOwner;
    private BitmapFont fontForPlayersNick;
    private BitmapFont fontForMoneyOnPlayersCards;
    private ShapeRenderer shapeRenderer;
    final String text = "We're loading your game!";

    PopUpPown popUpPlayer;
    PopUpEnd popUpEndLose;
    PopUpEnd popUpEndWin;

    PopUpInformation popUpRules;
    PopUpInformation popUpMoney;
    PopUpInformation popUpFirstPlayer;
    PopUpInformation popUpTrapCard;
    PopUpInformation popUpZgierz;
    ImageButton startButton;

    private Pawn myPawn;
    private ArrayList<Pawn> otherPlayersPawns;
    private BuyCard buyCard;
    private BuyTenementCard buyTenementCard;
    private boolean visibleBuyCard;
    private boolean visibleBuyTenementCard;
    private PopUpInformation popUpInformationFee;
    private PopUpInformation popUpInformationCrossedStart;
    private PopUpInformation popUpLoser;
    private boolean isVisiblePopUpFee = false;
    private final int PRIZE_START_FIELD = 500;


    public GameScreen(MoneyLandGame game){
        super(game);
        parent = game;
        parent.serverHandler.setupConnectWithGameScreen(this);

        float leftSideWidth = MoneyLandGame.WIDTH/6;
        float boardWidth = MoneyLandGame.WIDTH - MoneyLandGame.WIDTH/3;
        float boardHeight = MoneyLandGame.HEIGHT - MoneyLandGame.HEIGHT/6;
        float rightSideWidth = MoneyLandGame.WIDTH/6;

        stage = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT));
        stage2 = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT));
        shapeRenderer = new ShapeRenderer ();

        String pawnName=null;
        switch (parent.getPlayer().getPlayerId()){
            case 0:pawnName="STRONG";break;
            case 1:pawnName="TYSKIE";break;
            case 2:pawnName="TATRA";break;
            case 3:pawnName="WARKA";break;
            case 4:pawnName="LECH";break;
        }
        //set popups when game start
        String Welcome = "Czesc " + parent.getPlayer().getPlayerName() + "! Twoj pionek to "+pawnName;
        String Rules = "Zasady";
        String Cebulion = "Na poczatek gry dostajesz 500 cebulionow." +
                "Wydawaj je madrze";
        String InformationWhoStarts = new String();

        if(parent.isiAmMoveGameScreen()){
            InformationWhoStarts = "Zaczynasz gre :)";
        }else{
            for(int i=0; i<parent.sizePlayer(); ++i){
                Player temp = parent.getOtherPlayer(i);
                if(temp.getPlayerId() == parent.getIdPlayerMoveGameScreen()){
                    InformationWhoStarts = "Gre zaczyna "+temp.getPlayerName();
                }
            }
        }

        popUpFirstPlayer = new PopUpInformation(InformationWhoStarts, true);
        popUpMoney = new PopUpInformation(Cebulion, true);
        popUpRules = new PopUpInformation( Rules, true);
        popUpPlayer = new PopUpPawn( Welcome, true, parent.getPlayer().getPlayerId());
        popUpTrapCard=new PopUpInformation("Jedno pole dziala inaczej niz pozostale ;)",true);
        popUpZgierz=new PopUpInformation("Ups! Kupiles Zgierz :( przygotuj sie na straty",false);
        popUpLoser = new PopUpInformation("Przegrałeś biedaku", false);
        popUpFirstPlayer.setVisible(true);
        popUpPlayer.setVisible(true);
        popUpRules.setVisible(true);

        popUpMoney.setVisible(true);
        popUpTrapCard.setVisible(true);
        //create part with cards
        cardsManager = new CardsManager(boardWidth,  boardHeight, leftSideWidth, MoneyLandGame.HEIGHT/6);

        //config rectangle for cube and create cube
        cubeRectWith =  rightSideWidth;
        cubeRectHeight = MoneyLandGame.HEIGHT/4;
        cubeRectPosX = MoneyLandGame.WIDTH - cubeRectWith;
        cubeRectPosY = MoneyLandGame.HEIGHT/6;
        cube = new Cube(cubeRectPosX + cubeRectWith/3,cubeRectPosY + cubeRectHeight/4 ,(int)cubeRectWith/3,(int)cubeRectWith/3,stage,4, this);

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
        popUpTrapCard.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
        popUpTrapCard.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
        popUpZgierz.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
        popUpZgierz.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
        popUpLoser.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
        popUpLoser.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
        stage2.addActor(popUpZgierz);
        stage2.addActor(popUpLoser);
        stage2.addActor(popUpFirstPlayer);
        stage2.addActor(popUpTrapCard);
        stage2.addActor(popUpMoney);
        stage2.addActor(popUpRules);

        stage2.addActor (popUpPlayer);

        stage.addActor(menuButton);

        //config fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            //for players nick
            parameter.size = 40;
            parameter.color = Color.BLACK;
            fontForPlayersNick = generator.generateFont(parameter);
            //for money on players cards
            parameter.size = 30;
            parameter.color = Color.BLACK;
            fontForMoneyOnPlayersCards = generator.generateFont(parameter);
        generator.dispose();

        //create players cards
        otherPlayerCards = new ArrayList<>();
        int padding = 20;
        int startMoney = 300;

        paddingBetweenPlayersInfo = 50;
        float rectHeightOtherPlayerInfo = (MoneyLandGame.HEIGHT - menuButton.getHeight() - 15 - (MoneyLandGame.HEIGHT - boardHeight)) * 1/6;

        playerOwner = new PlayerCard(
                parent.getPlayer(),
                leftSideWidth * 3/2, //width
                MoneyLandGame.HEIGHT - boardHeight, //height
                0, //positionX
                0, //position y
                paddingBetweenPlayersInfo, //padding
                parent.getPlayer().getPlayerName(),    //nick
                startMoney, //money
                parent.getPlayer().getColor(), //player color
                fontForPlayersNick,  //font
                fontForMoneyOnPlayersCards, //font
                stage
        );

        for(int i=0; i<parent.sizePlayer(); ++i){
                //other players
                Player temp = parent.getOtherPlayer(i);
                PlayerCard playerCard = new PlayerCard(
                        temp,
                        leftSideWidth - 2 * padding, //width
                        rectHeightOtherPlayerInfo, //height
                        padding, //positionX
                        MoneyLandGame.HEIGHT - menuButton.getHeight() - 15 - rectHeightOtherPlayerInfo - (i * rectHeightOtherPlayerInfo) - ((i + 1) * paddingBetweenPlayersInfo), //position y
                        paddingBetweenPlayersInfo, //padding
                        parent.getOtherPlayer(i).getPlayerName(),    //nick
                        startMoney, //money
                        temp.getColor(),  //player color
                        fontForPlayersNick,  //font
                        fontForMoneyOnPlayersCards, //font
                        stage
                );
                otherPlayerCards.add(playerCard);
        }

        Gdx.input.setInputProcessor(stage); //This tells the screen to send any input from the user to the stage so it can respond

        //create pawns; pawn number == id player from server
        otherPlayersPawns = new ArrayList<>();
        for(int i=0; i<parent.sizePlayer(); ++i){
            Player temp = parent.getOtherPlayer(i);
            int playerId = temp.getPlayerId();
            Pawn pawn = new Pawn(stage,playerId,0);
            otherPlayersPawns.add(pawn);
        }
        myPawn = new Pawn(stage, parent.getPlayer().getPlayerId(), 0);

        //setup endMoveButton
        playerOwner.setEndMoveButton(this);

        //create buy button to use
        buyCard = new BuyCard(rightSideWidth,boardHeight/2, MoneyLandGame.WIDTH - rightSideWidth, MoneyLandGame.HEIGHT - boardHeight/2, fontForPlayersNick, this.stage, this);
        visibleBuyCard = false;

        buyTenementCard = new BuyTenementCard(rightSideWidth,boardHeight/2, MoneyLandGame.WIDTH - rightSideWidth, MoneyLandGame.HEIGHT - boardHeight/2, fontForPlayersNick, this.stage, this);
        visibleBuyTenementCard = false;


        //create pop up for fee
        popUpInformationFee = new PopUpInformation("", false);
        popUpInformationFee.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
        popUpInformationFee.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
        stage2.addActor(popUpInformationFee);

        //create pop up for prize when player cross start field
        popUpInformationCrossedStart =  new PopUpInformation("Soltys placi Ci "+String.valueOf(PRIZE_START_FIELD)+" cebulionow", false);
        popUpInformationCrossedStart.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
        popUpInformationCrossedStart.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
        stage2.addActor(popUpInformationCrossedStart);

        //enable cube when i start game
        if(parent.isiAmMoveGameScreen()){
            myTurn();
        }

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

        //draw player rectangle
        playerOwner.draw(parent.shapeRenderer); //it must be between begin() and end()

        //draw rectangles with other players info
        for(int i=0; i<otherPlayerCards.size(); ++i){
            PlayerCard temp = otherPlayerCards.get(i);
            temp.draw(parent.shapeRenderer);  //it must be between begin() and end()
        }

        //draw rectangle for buy card
        if(visibleBuyCard){
            buyCard.draw(parent.shapeRenderer);
        }

        if(parent.getPlayer().isPlayerBankrupt()){

            popUpLoser.setVisible(true);
        }

        if(visibleBuyTenementCard){
            buyTenementCard.draw(parent.shapeRenderer);

        }

        parent.shapeRenderer.end();


        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage2.act(delta);
        stage.draw();
        stage2.draw ();

        if(parent.getPlayer().getPlayerMoney() <= 0){
            popUpEndLose = new PopUpEnd( "Przegrases", true, parent);
            popUpEndLose.setVisible(true);
            popUpEndLose.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
            popUpEndLose.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
            stage2.addActor(popUpEndLose);
        }

        int number = 0;
        for(int i=0; i<parent.sizePlayer(); ++i){
            if(parent.getOtherPlayer(i).getPlayerMoney() <= 0){
                number++;
            }
        }

        boolean win = false;
        if(number == parent.sizePlayer()){
            win = true;
        }

        if(win){
            popUpEndWin = new PopUpEnd( "Wygrales", true, parent);
            popUpEndWin.setVisible(true);
            popUpEndWin.setPosition(MoneyLandGame.WIDTH/4, MoneyLandGame.HEIGHT/4);
            popUpEndWin.setSize(MoneyLandGame.WIDTH/2, MoneyLandGame.HEIGHT/2);
            stage2.addActor(popUpEndWin);
        }
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

    public void myTurn(){
        //enable cube
        cube.setAvailable(); //cube has got listener which calls moveMyPlayer when player clicks on cube
        playerOwner.showEndMoveButton();
    }
    public void moveMyPlayer(){
        int numberOnCube = cube.getNumberOnCube(); //get number on cube
        int oldPosition = parent.getPlayer().getPlayerPosition();
        parent.getPlayer().updatePlayerPosition(numberOnCube); //update position
        myPawn.changePosition(parent.getPlayer().getPlayerPosition()); //update pawn position
        PlayerMoveMessage playerMoveMessage = new PlayerMoveMessage(numberOnCube, parent.getPlayer().getPlayerId()); //create message about my move
        parent.serverHandler.sendMessage(playerMoveMessage); //send message to server

        //get info about card and check if player crossed start
        boolean startField = false;
        if(parent.getPlayer().getPlayerPosition() == 0 || oldPosition+numberOnCube>=16){
            //player crossed 'start' field
            CrossedStartMessage crossedStartMessage = new CrossedStartMessage(parent.getPlayer().getPlayerId(), PRIZE_START_FIELD);
            parent.serverHandler.sendMessage(crossedStartMessage);
            //popUp
            popUpInformationCrossedStart.showPopUp();

            if(parent.getPlayer().getPlayerPosition() == 0) startField=true;
        }

        //because player can crossed start field and stand on another card
        if(!startField){
            Card card = cardsManager.getCard(parent.getPlayer().getPlayerPosition());
            if(card == null) return;
            Player cardOwner = card.getOwner();
            //when card has got owner -> you have to pay

            //when card hasnt got tenement -> you can buy it
            if(cardOwner!=null&&cardOwner.getPlayerId()==parent.getPlayer().getPlayerId()&& !card.getCity().isTenementPlaced){
                buyTenementCard.change(card);
                card.getCity().placeTenement();
                setVisibleBuyTenementCard();
            }else if(cardOwner != null&&cardOwner.getPlayerId()!=parent.getPlayer().getPlayerId()){
                int myId = parent.getPlayer().getPlayerId();
                int ownerId = cardOwner.getPlayerId();
                float amount=card.getCity().rentAmount;
                TransferMessage transferMessage = new TransferMessage(myId, ownerId, amount);
                //popUp
                if(card.getCity().isTenementPlaced){
                    amount*=1.1;
                }

                popUpInformationFee.setText("Stoisz na polu "+card.cityName.getText()+".\n Oddajesz "+String.valueOf(amount)+" cebulionow");
                popUpInformationFee.showPopUp();
                //send info to server
                parent.serverHandler.sendMessage(transferMessage);
            }else if(cardOwner==null){
                //when card hasn't got owner -> you can buy this card
                buyCard.change(card);
                setVisibleBuyCard();
            }
        }
    }

    public void endMyMove(){
        buyCard.reset(); //hide button, city name, description
        buyTenementCard.reset();
        resetVisibleBuyCard(); //disable buy card
        resetVisibleBuyTenementCard();
        cube.resetAvailable(); //disable cube
        parent.setiAmMoveGameScreen(false); //end my move
        playerOwner.hideEndMoveButton();
        parent.serverHandler.sendMessage(new EndMoveMessage()); //send info to server
    }

    public void moveOtherPlayer(Player player, int delta){
        //change cube
        cube.changeCube(delta);

        //change position
        player.updatePlayerPosition(delta);
        for(int i=0; i<otherPlayersPawns.size(); ++i){
            Pawn pawn = otherPlayersPawns.get(i);
            if(pawn.getNumberPlayer() == player.getPlayerId()){
                pawn.changePosition(player.getPlayerPosition());
                break;
            }
        }

    }

    public void buyCard(Card card){
        if(card==null) return;
        City city = card.getCity();
        BuyCardMessage buyCardMessage = new BuyCardMessage(card.getIdCard(),city.getPrice(), parent.getPlayer().getPlayerId());
        if(city.CityName.equals("Zgierz"))
        {
            faliureSound=Gdx.audio.newSound(Gdx.files.internal("FailSound.mp3"));
            faliureSound.play(1.0f);
            //faliureSound.setLooping(s,false);
            popUpZgierz.setVisible(true);
            popUpZgierz.showPopUp();
        }
        parent.serverHandler.sendMessage(buyCardMessage);
        buyCard.reset();
        resetVisibleBuyCard();
    }
    public void buyTenement(Card card){
        if(card==null) return;
        City city = card.getCity();
        BuyTenementMessage buyTenementMessage = new BuyTenementMessage(card.getIdCard(),city.getTenementPrice(), parent.getPlayer().getPlayerId());
        parent.serverHandler.sendMessage(buyTenementMessage);
        buyTenementCard.reset();
        resetVisibleBuyTenementCard();
    }

    public boolean isVisibleBuyCard() {
        return visibleBuyCard;
    }
    public boolean isVisibleBuyTenementCard() {
        return visibleBuyTenementCard;
    }

    public void setVisibleBuyCard() {
        this.visibleBuyCard = true;
    }

    public void resetVisibleBuyCard() {
        this.visibleBuyCard = false;
    }

    public void setVisibleBuyTenementCard() {
        this.visibleBuyTenementCard = true;
    }

    public void resetVisibleBuyTenementCard() {
        this.visibleBuyTenementCard = false;
    }


    public CardsManager getCardsManager() {
        return cardsManager;
    }
}
