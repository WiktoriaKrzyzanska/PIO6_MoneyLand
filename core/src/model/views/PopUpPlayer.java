package model.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PopUpPlayer extends Table {
    String title;
    ImageButton startButton;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;
    private final BitmapFont font;

        public PopUpPlayer(String title, ShapeRenderer shapeRenderer) {
            // Create a label for the title
            this.title = title;
            this.shapeRenderer = shapeRenderer;
            font = new BitmapFont();
            spriteBatch = new SpriteBatch();
            Texture buttonTexture = new Texture("StartButton.png");
            Texture buttonHoverTexture = new Texture("StartButtonClicked.png");

            ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
            buttonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonTexture));
            buttonStyle.over = new TextureRegionDrawable(new TextureRegion(buttonHoverTexture));

            startButton = new ImageButton(buttonStyle);

            // Create a text button for the exit
            startButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setVisible(false); // hide the table when exit button is clicked
                }
            });

            // Add the title and the exit button to the table
            add(startButton).width(100).height(50); // set the size of the second cell
            row(); // start a new row

            // Add any other actors or content to the table as needed
            // For example, you can add an image of your player pawn

        }
    }





