package educ.lasalle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Zombie implements IDisplayElement{
    private final Texture zombieTexture;
    private final Sprite zombieSprite;
    private final float zombieSize = 1f;
    private final byte damage = 8;
    private Rectangle zombieBox;

    public Zombie() {
        zombieTexture = AssetManager.loadTexture("zombie.png");
        zombieSprite = new Sprite(zombieTexture);
        zombieSprite.setSize(zombieSize, zombieSize);
        zombieBox = new Rectangle(zombieSprite.getX(), zombieSprite.getY(), zombieSize, zombieSize);
    }

    public void initPosition(FitViewport viewport) {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Randomize the drop's x position
        zombieSprite.setX(MathUtils.random(0f, worldWidth - 1));
        zombieSprite.setY(worldHeight);
        zombieSprite.rotate90(true);
    }

    public void inflictDamage(Player player) {
        player.receiveDamage(this.damage);
    }

    public boolean checkCollision(Bullet bullet) {
        return bullet.getBox().overlaps(this.zombieBox);
    }

    public boolean checkCollision(Player player) {
        return this.zombieBox.overlaps(player.getBox());
    }

    public void update(float delta) {
        this.zombieSprite.translateY(-2f * delta);
        this.zombieBox.set(
            this.zombieSprite.getX(),
            this.zombieSprite.getY(),
            zombieSize,
            zombieSize
        );
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
        return this.zombieSize;
    }

    public byte getDamage() {
        return damage;
    }
}
