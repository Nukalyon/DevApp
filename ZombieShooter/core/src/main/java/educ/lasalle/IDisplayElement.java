package educ.lasalle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public interface IDisplayElement {
    public Sprite getSprite();
    public Rectangle getBox();
    public float getSize();
}
