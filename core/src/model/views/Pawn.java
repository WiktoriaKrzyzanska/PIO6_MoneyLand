package model.views;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.MoneyLandGame;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pawn extends Actor {
    private Image pawnImage;
    private String path;
    private float x;
    private float y;
    private float width;
    private float height;
    private float padding;
    private float paddingBetweenCards;
    private float xWidth;
    private float yHeight;
    public Pawn (Stage stageCube, int numberPlayer, int positionCube) {

        padding = 30;
        paddingBetweenCards = 20;

        xWidth = ((MoneyLandGame.WIDTH - MoneyLandGame.WIDTH/3) - 2 * padding) / 5; // 5 cards in row
        yHeight = ((MoneyLandGame.HEIGHT - MoneyLandGame.HEIGHT/6) - 2 * padding) / 5; // 5 cards in column

        width = (xWidth - paddingBetweenCards)/5;
        height = MoneyLandGame.HEIGHT/15;

        switch(numberPlayer) {
            case 1:
                path = "assets/pawn/1.png";
                break;
            case 2:
                path = "assets/pawn/2.png";
                break;
            case 3:
                path = "assets/pawn/3.png";
                break;
            case 4:
                path = "assets/pawn/4.png";
                break;
            case 5:
                path = "assets/pawn/5.png";
                break;
            default:
                break;
        }
        switch(positionCube) {
            case 0:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            case 1:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + yHeight + padding;
                break;
            case 2:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + 2 * yHeight + padding;
                break;
            case 3:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + 3 * yHeight + padding;
                break;
            case 4:
                x = MoneyLandGame.WIDTH/6 + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 5:
                x = MoneyLandGame.WIDTH/6 + xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 6:
                x = MoneyLandGame.WIDTH/6 + 2 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 7:
                x = MoneyLandGame.WIDTH/6 + 3 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 8:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + 4 * yHeight + padding;
                break;
            case 9:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + 3 * yHeight + padding;
                break;
            case 10:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + 2 * yHeight + padding;
                break;
            case 11:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + yHeight + padding;
                break;
            case 12:
                x = MoneyLandGame.WIDTH/6 + 4 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            case 13:
                x = MoneyLandGame.WIDTH/6 + 3 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            case 14:
                x = MoneyLandGame.WIDTH/6 + 2 * xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            case 15:
                x = MoneyLandGame.WIDTH/6 + xWidth + padding + ((xWidth - paddingBetweenCards)/5) * (numberPlayer - 1);
                y = MoneyLandGame.HEIGHT/6 + padding;
                break;
            default:
                break;
        }

        setPosition(x, y);
        setSize(width, height);

        pawnImage = new Image(new Texture(path));
        pawnImage.setSize(width, height);
        pawnImage.setPosition(x, y);
        stageCube.addActor(pawnImage);
    }

    public void removePawn() {
        pawnImage.remove();
    }
}



