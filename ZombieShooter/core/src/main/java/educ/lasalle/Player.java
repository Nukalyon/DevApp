package educ.lasalle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Player implements IDisplayElement{
    private Texture playertexture;
    private Sprite playerSprite;
    private byte lifePoint;   //Value between -128 and 127
    private final float playerSize = 1f;
    private Rectangle playerRectangle;

    public Player() {
        this.lifePoint = 100;
        playertexture = AssetManager.loadTexture("player.png");
        playerSprite = new Sprite(playertexture);
        playerSprite.setSize(playerSize, playerSize);
        playerSprite.rotate90(false);
        playerRectangle = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSize, playerSize);
    }

    public void receiveDamage(byte amount) {
        this.lifePoint -= amount;
        System.out.println(this.lifePoint);
        if (this.lifePoint <= 0) {
            // Game Over
            this.lifePoint = 0;
        }
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
        return this.playerSize;
    }

}
