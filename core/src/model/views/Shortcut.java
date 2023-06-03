package model.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.mygdx.game.MoneyLandGame;

/**
 * When we use some class we add extends Shortcut instead od implements Screen
 * We also in our class initialization have to call our method by -  super(game);
 * In method show we have to create InputMultiplexer to handle multi inputs
 *
 */
public abstract class Shortcut extends InputAdapter implements Screen {
    // make the field private or protected
    private MoneyLandGame gameplay;
    public Shortcut (MoneyLandGame gameplay) {
        this.gameplay = gameplay;
    }
    // add a getter method if needed
    public MoneyLandGame getGameplay () {
        return gameplay;
    }
    @Override
    public boolean keyDown (int keycode) {
        if(keycode == Input.Keys.ESCAPE)
            Gdx.app.exit();
        return false;
    }
    // override the other methods of the Screen interface
    @Override
    public void show () {
        // add your initialization code here
    }
    @Override
    public void render (float delta) {
        // add your rendering code here
    }
    @Override
    public void resize (int width, int height) {
        // add your resizing code here
    }
    @Override
    public void pause () {
        // add your pause code here
    }
    @Override
    public void resume () {
        // add your resume code here
    }
    @Override
    public void hide () {
        // add your hide code here
    }
    @Override
    public void dispose () {
        // add your dispose code here
    }
}
