package educ.lasalle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private Texture playertexture;
    private Sprite playerSprite;
    private int posX;
    private int posY;

    public Player(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
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
}
