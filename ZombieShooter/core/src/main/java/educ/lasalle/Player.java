package educ.lasalle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Player {
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

    public Sprite getPlayerSprite() {
        return this.playerSprite;
    }

    public void receiveDamage(byte amount) {
        this.lifePoint -= amount;
    }

    public Rectangle getBoundingBox() {
        return this.playerRectangle;
    }

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
    public boolean isDead(){
        return this.lifePoint <= 0;
    }
}
