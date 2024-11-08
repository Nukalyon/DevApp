package educ.lasalle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

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

    /*
    public void update(FitViewport viewport) {
        float worldWidth = viewport.getWorldWidth();

        // Clamp x to values between 0 and worldWidth
        this.playerSprite.setX(MathUtils.clamp(this.playerSprite.getX(), 0, worldWidth - this.playerSize));

        // Apply the bucket position and size to the bucketRectangle
        this.playerRectangle.set(
            this.playerSprite.getX(),
            this.playerSprite.getY(),
            this.playerSize,
            this.playerSize
        );
    }
     */

}
