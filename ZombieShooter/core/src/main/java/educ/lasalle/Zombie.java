package educ.lasalle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Zombie {
    private Texture zombieTexture;
    private Sprite zombieSprite;
    private int posX;
    private int posY;

    public Zombie() {
        zombieTexture = AssetManager.loadTexture("zombie.png");
        zombieSprite = new Sprite(zombieTexture);
        zombieSprite.setSize(1,1);
    }

    public Sprite getZombieSprite() {
        return this.zombieSprite;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void initPosition(FitViewport viewport) {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        // Randomize the drop's x position
        zombieSprite.setX(MathUtils.random(0f, worldWidth - 1));
        zombieSprite.setY(worldHeight);
        zombieSprite.rotate90(true);
    }
}
