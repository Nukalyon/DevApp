package educ.lasalle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet {
    private Texture bulletTexture;
    private Sprite bulletSprite;
    private int posX;
    private int posY;

    public Bullet(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        bulletTexture = AssetManager.loadTexture("bullet.png");
        bulletSprite = new Sprite(bulletTexture);
        bulletSprite.setSize(1,1);
    }

    public Sprite getBulletSprite() {
        return this.bulletSprite;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
}
