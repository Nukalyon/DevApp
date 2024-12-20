package educ.lasalle.controller;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.model.Player;

public class PlayerController {
    private Player player;
    private FitViewport view;

    public PlayerController(Player player, FitViewport view) {
        this.player = player;
        this.view = view;
    }

    public Sprite getSprite()
    {
        return this.player.getSprite();
    }

    public Rectangle getBox()
    {
        return this.player.getBox();
    }

    public float getSize()
    {
        return this.player.getSize();
    }

    public void update()
    {
        float worldWidth = this.view.getWorldWidth();
        Sprite ps = this.getSprite();
        float size = this.getSize();

        // Clamp x to values between 0 and worldWidth
        ps.setX(MathUtils.clamp(ps.getX(), 0, worldWidth - size));
        this.getBox().set(ps.getX(), ps.getY(), size, size);
    }

    public void receiveDamage(byte damage) {
        this.player.receiveDamage(damage);

    }

    public void initRotation() {
        this.player.initRotation();
    }

    public boolean isDead()
    {
        return this.player.isDead();
    }
}
