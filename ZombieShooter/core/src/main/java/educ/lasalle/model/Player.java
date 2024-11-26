package educ.lasalle.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import educ.lasalle.manager.AssetManager;

public class Player implements IDisplayElement {
    private Sprite playerSprite;
    private byte lifePoint;   //Value between -128 and 127
    private Rectangle playerRectangle;

    public Player() {
        this.lifePoint = 40;
        playerSprite = AssetManager.loadSprite(AssetManager.playertexture, new Vector2(AssetManager.playerSize,AssetManager.playerSize));
        playerRectangle = new Rectangle(this.playerSprite.getX(), this.playerSprite.getY(), AssetManager.playerSize, AssetManager.playerSize);
    }

    public void receiveDamage(byte amount) {
        this.lifePoint -= amount;
        System.out.println(this.lifePoint);
        if (this.lifePoint <= 0) {
            // Game Over
            this.lifePoint = 0;
        }
    }

    public boolean isDead(){
        return this.lifePoint <= 0;
    }

    @Override
    public Sprite getSprite() {
        return this.playerSprite;
    }

    @Override
    public Rectangle getBox() {
        return this.playerRectangle;
    }

    public float getSize(){
        return AssetManager.playerSize;
    }

    public void initRotation() {
        getSprite().rotate90(false);
    }
}
