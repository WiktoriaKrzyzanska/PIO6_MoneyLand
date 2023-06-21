package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MoneyLandGame;
import model.messages.*;


import java.util.ArrayList;

public class GameScreen extends Shortcut {
    final MoneyLandGame parent;
    private Stage stage;
    private Stage stage2;
    private CardsManager cardsManager;


    private final float cubeRectPosX;
    private final float cubeRectPosY;
    private final float cubeRectWith;
    private final float cubeRectHeight;
    private final Cube cube;
    private final ImageButton menuButton;
    private final Texture menuButtonTexture;
    private final Texture menuButtonHoverTexture;

    private final ArrayList<PlayerCard> otherPlayerCards;
    private final PlayerCard playerOwner;

    private PopUpPawn popUpPlayer;
    private PopUpInformation popUpRules;
    private PopUpInformation popUpMoney;
    private PopUpInformation popUpFirstPlayer;
    private PopUpInformation popUpTrapCard;
    private PopUpInformation popUpZgierz;


    private final Pawn myPawn;
    private final ArrayList<Pawn> otherPlayersPawns;
    private final BuyCard buyCard;
    private final BuyTenementCard buyTenementCard;
    private boolean visibleBuyCard;
    private boolean visibleBuyTenementCard;
    private final PopUpInformation popUpInformationFee;
    private final PopUpInformation popUpInformationCrossedStart;
    private final PopUpEnd popUpLoser;
    private final PopUpEnd popUpWin;
    private final boolean isVisiblePopUpFee = false;
    private final int PRIZE_START_FIELD = 500;
    private final int HOW_MANY_PLAYERS = 5;
    private final float LEFT_SIZE_WIDTH = MoneyLandGame.WIDTH/6;
    private final float RIGHT_SIDE_WIDTH = MoneyLandGame.WIDTH/6;
    private final float BOARD_PADDING_BOTTOM = MoneyLandGame.HEIGHT/6;
    private final float BOARD_WIDTH = MoneyLandGame.WIDTH - LEFT_SIZE_WIDTH - RIGHT_SIDE_WIDTH;
    private final float BOARD_HEIGHT = MoneyLandGame.HEIGHT - BOARD_PADDING_BOTTOM;

    private final float MENU_BUTTON_PADDING_LEFT = 40;
    private final float MENU_BUTTOM_WIDTH = LEFT_SIZE_WIDTH - 2 * MENU_BUTTON_PADDING_LEFT;
    private final float MENU_BUTTOM_HEIGHT = BOARD_HEIGHT / (2 * HOW_MANY_PLAYERS);
    private final float POPUP_WIDTH = MoneyLandGame.WIDTH/2;
    private final float POPUP_HEIGHT = MoneyLandGame.HEIGHT/2;
    private final float POPUP_MARGIN_LEFT = MoneyLandGame.WIDTH/4;
    private final float POPUP_MARGIN_BOTTOM = MoneyLandGame.HEIGHT/4;
    private String cityNameTrap;




    public GameScreen(MoneyLandGame game){
        super(game);
        parent = game;
        parent.serverHandler.setupConnectWithGameScreen(this);

        stage = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT));
        stage2 = new Stage(new StretchViewport(MoneyLandGame.WIDTH,MoneyLandGame.HEIGHT));
        ShapeRenderer shapeRenderer = new ShapeRenderer();

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
        String Cebulion = "Na poczatek gry dostajesz 5000 cebulionow." +
                "Wydawaj je madrze";
        String InformationWhoStarts = "";
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
        popUpLoser = new PopUpEnd("Przegrales biedaku", false, parent);
        popUpWin = new PopUpEnd("Wygrales", false, parent);
        popUpFirstPlayer.setVisible(true);
        popUpPlayer.setVisible(true);
        popUpRules.setVisible(true);

        popUpMoney.setVisible(true);
        popUpTrapCard.setVisible(true);

        //create part with cards
        cardsManager = new CardsManager(BOARD_WIDTH,  BOARD_HEIGHT, LEFT_SIZE_WIDTH, BOARD_PADDING_BOTTOM);

        //ustawianie karty
        cityNameTrap = cardsManager.getCard(parent.getIdTrapCard()).getCity().CityName;
        Gdx.app.log("Dziwne miasto",cityNameTrap);
        cardsManager.getCard(parent.getIdTrapCard()).getCity().setRentAmount(-300);


        //config rectangle for cube and create cube
        cubeRectWith =  RIGHT_SIDE_WIDTH;
        cubeRectHeight = MoneyLandGame.HEIGHT/4;
        cubeRectPosX = MoneyLandGame.WIDTH - cubeRectWith;
        cubeRectPosY = BOARD_PADDING_BOTTOM;
        cube = new Cube(cubeRectPosX + cubeRectWith/3,cubeRectPosY + cubeRectHeight/4 ,(int)cubeRectWith/3,(int)cubeRectWith/3,stage,4, this);

        //config menu button
        menuButtonTexture = new Texture(Gdx.files.internal("MenuButton.png"));
        menuButtonHoverTexture = new Texture("MenuButtonClicked.png");

        ImageButton.ImageButtonStyle buttonStyleMenu = new ImageButton.ImageButtonStyle();
        buttonStyleMenu.up = new TextureRegionDrawable(new TextureRegion(menuButtonTexture));
        buttonStyleMenu.over = new TextureRegionDrawable(new TextureRegion(menuButtonHoverTexture));

        int paddingBetweenMenuButtonAndCard = 15;
        menuButton = new ImageButton(buttonStyleMenu);
        menuButton.setWidth(MENU_BUTTOM_WIDTH);
        menuButton.setHeight(MENU_BUTTOM_HEIGHT - paddingBetweenMenuButtonAndCard);
        menuButton.setPosition(LEFT_SIZE_WIDTH / 2 - (menuButton.getWidth()/3), MoneyLandGame.HEIGHT - menuButton.getHeight()*0.6f -100 + paddingBetweenMenuButtonAndCard );
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.changeScreen(MoneyLandGame.MENU_SCREEN);
            }
        });


        popUpFirstPlayer.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpFirstPlayer.setSize(POPUP_WIDTH, POPUP_HEIGHT);
        popUpMoney.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpMoney.setSize(POPUP_WIDTH, POPUP_HEIGHT);
        popUpRules.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpRules.setSize(POPUP_WIDTH, POPUP_HEIGHT);
        popUpPlayer.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpPlayer.setSize(POPUP_WIDTH, POPUP_HEIGHT);
        popUpTrapCard.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpTrapCard.setSize(POPUP_WIDTH, POPUP_HEIGHT);
        popUpZgierz.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpZgierz.setSize(POPUP_WIDTH, POPUP_HEIGHT);
        popUpLoser.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpLoser.setSize(POPUP_WIDTH, POPUP_HEIGHT);
        popUpWin.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpWin.setSize(POPUP_WIDTH, POPUP_HEIGHT);

        stage2.addActor(popUpZgierz);
        stage2.addActor(popUpLoser);
        stage2.addActor(popUpFirstPlayer);
        stage2.addActor(popUpTrapCard);
        stage2.addActor(popUpMoney);
        stage2.addActor(popUpRules);
        stage2.addActor(popUpWin);

        stage2.addActor (popUpPlayer);

        stage.addActor(menuButton);

        //config fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            //for players nick
            parameter.size = 40;
            parameter.color = Color.BLACK;
        BitmapFont fontForPlayersNick = generator.generateFont(parameter);
            //for money on players cards
            parameter.size = 30;
            parameter.color = Color.BLACK;
        BitmapFont fontForMoneyOnPlayersCards = generator.generateFont(parameter);
        generator.dispose();

        //create players cards
        otherPlayerCards = new ArrayList<>();
        int padding = 20;
        int startMoney = 5000;
        int paddingBetweenPlayersInfo = 50;
        float rectHeightOtherPlayerInfo = (BOARD_HEIGHT - MENU_BUTTOM_HEIGHT) / HOW_MANY_PLAYERS;

        playerOwner = new PlayerCard(
                parent.getPlayer(),
                LEFT_SIZE_WIDTH * 3/2, //width
                MoneyLandGame.HEIGHT - BOARD_HEIGHT, //height
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
                        LEFT_SIZE_WIDTH - 2 * padding, //width
                        rectHeightOtherPlayerInfo, //height
                        padding, //positionX
                        MoneyLandGame.HEIGHT - menuButton.getHeight() - rectHeightOtherPlayerInfo - (i * rectHeightOtherPlayerInfo) - ((i + 1) * paddingBetweenPlayersInfo), //position y
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
        buyCard = new BuyCard(RIGHT_SIDE_WIDTH,BOARD_HEIGHT/2, MoneyLandGame.WIDTH - RIGHT_SIDE_WIDTH, MoneyLandGame.HEIGHT - BOARD_HEIGHT/2, fontForPlayersNick, this.stage, this);
        visibleBuyCard = false;

        buyTenementCard = new BuyTenementCard(RIGHT_SIDE_WIDTH,BOARD_HEIGHT/2, MoneyLandGame.WIDTH - RIGHT_SIDE_WIDTH, MoneyLandGame.HEIGHT - BOARD_HEIGHT/2, fontForPlayersNick, this.stage, this);
        visibleBuyTenementCard = false;


        //create pop up for fee
        popUpInformationFee = new PopUpInformation("", false);
        popUpInformationFee.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpInformationFee.setSize(POPUP_WIDTH, POPUP_HEIGHT);
        stage2.addActor(popUpInformationFee);

        //create pop up for prize when player cross start field
        popUpInformationCrossedStart =  new PopUpInformation("Soltys placi Ci "+ PRIZE_START_FIELD +" cebulionow", false);
        popUpInformationCrossedStart.setPosition(POPUP_MARGIN_LEFT, POPUP_MARGIN_BOTTOM);
        popUpInformationCrossedStart.setSize(POPUP_WIDTH, POPUP_HEIGHT);
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
        inputMultiplexer.addProcessor(this);
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
        for (PlayerCard temp : otherPlayerCards) {
            temp.draw(parent.shapeRenderer);  //it must be between begin() and end()
        }

        //draw rectangle for buy card
        if(visibleBuyCard){
            buyCard.draw(parent.shapeRenderer);
        }

        if(parent.getPlayer().isPlayerBankrupt()){
            popUpLoser.setVisible(true);
        }

        int number = 0;
        for(int i=0; i<parent.sizePlayer(); ++i){
            if(parent.getOtherPlayer(i).isPlayerBankrupt()){
                number++;
            }

        }

        if(number == parent.sizePlayer()){
            popUpWin.setVisible(true);
        }

        if(visibleBuyTenementCard){
            buyTenementCard.draw(parent.shapeRenderer);

        }

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

                popUpInformationFee.setText("Stoisz na polu "+card.cityName.getText()+".\n Oddajesz "+ amount +" cebulionow");
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
        for (Pawn pawn : otherPlayersPawns) {
            if (pawn.getNumberPlayer() == player.getPlayerId()) {
                pawn.changePosition(player.getPlayerPosition());
                break;
            }
        }

    }

    public void buyCard(Card card){
        if(card==null) return;
        City city = card.getCity();
        BuyCardMessage buyCardMessage = new BuyCardMessage(card.getIdCard(),city.getPrice(), parent.getPlayer().getPlayerId());
        if(city.CityName.equals(cityNameTrap))
        {
            Sound faliureSound = Gdx.audio.newSound(Gdx.files.internal("FailSound.mp3"));
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
