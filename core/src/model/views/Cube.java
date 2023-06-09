package model.views;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.MoneyLandGame;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class Cube creates an (mouse click) interactive game cube
 * You can add it to the game window by putting this in Screen class
 * cube = new Cube(x variable, y variable, width, height, stage);
 */
public class Cube extends Actor implements Disposable {
    private List<Texture> images;
    private Random random;
    private Image cubeImage;
    private Pawn pawn;
    Stage stageCube;
    private int numberOnCube;
    private int positionCube;
    private int numbePlayer;

    private boolean available = false;
    private GameScreen gameScreen;

    public Cube(float x, float y, int width, int height, Stage stage, int idPlayer, final GameScreen gameScreen) {

        this.gameScreen = gameScreen;

        this.stageCube = stage;
        positionCube = 0;
        numbePlayer = idPlayer;

        setPosition(x, y);
        setSize(width, height);
        random = new Random();
        loadImagesFromFolder("assets/cube");

        cubeImage = new Image(getRandomImage());
        cubeImage.setSize(width, height);
        cubeImage.setPosition(x, y);
        stageCube.addActor(cubeImage);

        cubeImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(available){
                    resetAvailable(); //disable cube for other clicks - only one click
                    if (button == 0) { // Left mouse button
                        Texture newImage = getRandomImage();
                        cubeImage.setDrawable(new Image(newImage).getDrawable());
                        gameScreen.moveMyPlayer(); //send info to game screen that player clicked on cube
                        return true;
                    }
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        numberOnCube = 0;
    }

    private void loadImagesFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".png");
            }
        });
        if (files != null && files.length > 0) {
            images = new ArrayList<>();
            for (File file : files) {
                images.add(new Texture(file.getPath()));
            }
        } else {
            Gdx.app.error("Cube", "No PNG images found in the folder: " + folderPath);
        }
    }

    private Texture getRandomImage() {
        int randomIndex = random.nextInt(images.size());
        numberOnCube = randomIndex + 1;  //+1 because index start at 0
        return images.get(randomIndex);
    }

    public int getNumberOnCube() {
        return numberOnCube;
    }

    public void resetAvailable() {
        this.available = false;
    }

    public void setAvailable() {
        this.available = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public void changeCube(int number){
        if(number<=0 || number>6) return;
        Texture newImage = images.get(number-1); //-1 because index start at 0
        cubeImage.setDrawable(new Image(newImage).getDrawable());
    }

    @Override
    public void dispose() {
        if(images!=null){
            for(int i=0; i<images.size(); ++i){
                images.get(i).dispose();
            }
        }
    }
}



