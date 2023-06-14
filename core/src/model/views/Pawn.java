package model.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.MoneyLandGame;

public class Pawn extends Actor implements Disposable {
    private final Image pawnImage;
    private final Texture pawnTexture;
    private String path;
    private float x;
    private float y;
    private final float width;
    private final float height;
    private final float padding;
    private final float paddingBetweenCards;
    private final float xWidth;
    private final float yHeight;

    private final int numberPlayer;

    public Pawn (Stage stageCube, int numberPlayer, int positionCube) {

        padding = 30;
        paddingBetweenCards = 20;

        xWidth = ((MoneyLandGame.WIDTH - MoneyLandGame.WIDTH/3) - 2 * padding) / 5; // 5 cards in row
        yHeight = ((MoneyLandGame.HEIGHT - MoneyLandGame.HEIGHT/6) - 2 * padding) / 5; // 5 cards in column

        width = (xWidth - paddingBetweenCards)/5;
        height = MoneyLandGame.HEIGHT/15;

        this.numberPlayer = numberPlayer;

        switch(this.numberPlayer) {
            case 0:
                path = "assets/pawn/1.png";
                break;
            case 1:
                path = "assets/pawn/2.png";
                break;
            case 2:
                path = "assets/pawn/3.png";
                break;
            case 3:
                path = "assets/pawn/4.png";
                break;
            case 4:
                path = "assets/pawn/5.png";
                break;
            default:
                break;
        }

        pawnTexture = new Texture(path);
        pawnImage = new Image(pawnTexture);

        int positionOnBoard = 0;
        changePosition(positionOnBoard);

        //setPosition(x, y);
        //setSize(width, height);

        stageCube.addActor(pawnImage);
    }

    public void removePawn() {
        pawnImage.remove();
    }

    public void changePosition(int position){
        switch(position) {
            case 0:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            case 1:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + yHeight + padding;
                break;
            case 2:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + 2 * yHeight + padding;
                break;
            case 3:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + 3 * yHeight + padding;
                break;
            case 4:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 5:
                x = MoneyLandGame.WIDTH/6 + xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 6:
                x = MoneyLandGame.WIDTH/6 + 2 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 7:
                x = MoneyLandGame.WIDTH/6 + 3 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 8:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 9:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + 3 * yHeight + padding;
                break;
            case 10:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + 2 * yHeight + padding;
                break;
            case 11:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + yHeight + padding;
                break;
            case 12:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            case 13:
                x = MoneyLandGame.WIDTH/6 + 3 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            case 14:
                x = MoneyLandGame.WIDTH/6 + 2 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            case 15:
                x = MoneyLandGame.WIDTH/6 + xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            default:
                break;
        }

        //setPosition(x, y);
        //setSize(width, height);

        pawnImage.setSize(width, height);
        pawnImage.setPosition(x, y);
    }

    public int getNumberPlayer() {
        return numberPlayer;
    }

    @Override
    public void dispose() {
        if(pawnTexture!=null) pawnTexture.dispose();
    }
}



