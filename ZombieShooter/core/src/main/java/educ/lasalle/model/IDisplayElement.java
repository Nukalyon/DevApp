package educ.lasalle.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public interface IDisplayElement {
    Sprite getSprite();
    Rectangle getBox();
    float getSize();
}
