package educ.lasalle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Bullet implements IDisplayElement{
    private Texture bulletTexture;
    private Sprite bulletSprite;
    private final float bulletSize = .35f;
    private Rectangle bulletRectangle;

    public Bullet(){
        bulletTexture = AssetManager.loadTexture("bullet.png");
        bulletSprite = new Sprite(bulletTexture);
        bulletSprite.setSize(bulletSize, bulletSize);
        bulletRectangle = new Rectangle(bulletSprite.getX(), bulletSprite.getY(), bulletSize, bulletSize);
    }

    public void initPosition(Sprite playerSprite) {
        bulletSprite.setX(playerSprite.getX() + .5f*playerSprite.getWidth());
        bulletSprite.setY(playerSprite.getY() + playerSprite.getHeight());
        bulletSprite.rotate90(false);
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
        return this.bulletSize;
    }
    /*
    public void update(float delta) {
        this.bulletSprite.translateY(2f * delta);
        this.bulletRectangle.set(
            this.bulletSprite.getX(),
            this.bulletSprite.getY(),
            bulletSize,
            bulletSize
        );
    }
     */
}
