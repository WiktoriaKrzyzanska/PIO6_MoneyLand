package model.views;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;

public interface PopUp extends Cullable {
    void render(float delta);

    void draw(Batch batch, float parentAlpha);

    void dispose();
}
