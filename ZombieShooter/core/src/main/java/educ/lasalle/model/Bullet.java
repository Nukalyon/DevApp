package educ.lasalle.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import educ.lasalle.manager.AssetManager;

public class Bullet implements IDisplayElement {
    private Sprite bulletSprite;
    private Rectangle bulletRectangle;

    public Bullet(){
        bulletSprite = AssetManager.loadSprite(AssetManager.bulletTexture, new Vector2(AssetManager.bulletSize, AssetManager.bulletSize));
        bulletRectangle = new Rectangle(this.bulletSprite.getX(), this.bulletSprite.getY(),
            AssetManager.bulletSize, AssetManager.bulletSize);

    }

    public void initPosition(Sprite playerSprite) {
        this.bulletSprite.setX(playerSprite.getX() + .5f*playerSprite.getWidth());
        this.bulletSprite.setY(playerSprite.getY() + playerSprite.getHeight());
        this.bulletSprite.rotate90(false);
    }

    @Override
    public Sprite getSprite() {
        return this.bulletSprite;
    }

    @Override
    public Rectangle getBox() {
        return this.bulletRectangle;
    }

    @Override
    public float getSize() {
        return AssetManager.bulletSize;
    }
}
