package model.views;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

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
public class Cube extends Actor {
    private List<Texture> images;
    private Random random;
    private Image cubeImage;

    public Cube(float x, float y, int width, int height, Stage stage) {
        setPosition(x, y);
        setSize(width, height);
        random = new Random();
        loadImagesFromFolder("assets/cube");

        cubeImage = new Image(getRandomImage());
        cubeImage.setSize(width, height);
        cubeImage.setPosition(x, y);
        stage.addActor(cubeImage);

        cubeImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == 0) { // Left mouse button
                    Texture newImage = getRandomImage();
                    cubeImage.setDrawable(new Image(newImage).getDrawable());
                    return true;
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
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
        return images.get(randomIndex);
    }
}


