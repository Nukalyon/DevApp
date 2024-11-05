package educ.lasalle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private Texture playertexture;
    private Sprite playerSprite;
    private int posX;
    private int posY;
    private byte lifePoint;   //Value between -128 and 127

    public Player(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.lifePoint = 100;
        playertexture = AssetManager.loadTexture("player.png");
        playerSprite = new Sprite(playertexture);
        playerSprite.setSize(1,1);
        playerSprite.rotate90(false);
    }

    public Sprite getPlayerSprite() {
        return this.playerSprite;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void receiveDamage(byte amount) {
        this.lifePoint -= amount;
        System.out.println(this.lifePoint);
        if(this.lifePoint <= 0)
        {
            // Game Over
            this.lifePoint = 0;
        }
    }
}
