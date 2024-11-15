package educ.lasalle.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.manager.AssetManager;

public class Zombie implements IDisplayElement {
    private Sprite zombieSprite;
    private final byte damage = 8;
    private Rectangle zombieBox;

    public Zombie() {
        zombieSprite = AssetManager.loadSprite(AssetManager.zombieTexture,
            new Vector2(AssetManager.zombieSize,AssetManager.zombieSize));
        zombieBox = new Rectangle(this.zombieSprite.getX(), this.zombieSprite.getY(),
            AssetManager.zombieSize, AssetManager.zombieSize);
    }

    public void initPosition(FitViewport viewport) {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Randomize the drop's x position
        this.zombieSprite.setX(MathUtils.random(0f, worldWidth - 1));
        this.zombieSprite.setY(worldHeight);
        this.zombieSprite.rotate90(true);
    }

    @Override
    public Sprite getSprite() {
        return this.zombieSprite;
    }

    @Override
    public Rectangle getBox() {
        return this.zombieBox;
    }

    @Override
    public float getSize() {
        return AssetManager.zombieSize;
    }

    public byte getDamage() {
        return damage;
    }
}
