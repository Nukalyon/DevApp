package educ.lasalle.controller;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.model.Zombie;

public class ZombieController {
    private Zombie zombie;
    private FitViewport view;

    public ZombieController(Zombie zombie, FitViewport view) {
        this.zombie = zombie;
        this.view = view;
    }

    public Sprite getSprite()
    {
        return this.zombie.getSprite();
    }

    public Rectangle getBox()
    {
        return this.zombie.getBox();
    }

    public float getSize()
    {
        return this.zombie.getSize();
    }

    public void initPosition()
    {
        this.zombie.initPosition(this.view);
    }

    public void update(float delta)
    {
        Sprite sp = this.getSprite();
        float size = this.getSize();
        sp.translateY(-200f * delta);
        this.getBox().set(sp.getX(), sp.getY(), size, size);
    }

    public byte getDamage()
    {
        return this.zombie.getDamage();
    }

    public void inflictDamage(PlayerController pc) {
        pc.receiveDamage(this.getDamage());
    }

    public boolean checkCollision(BulletController bc) {
        return bc.getBox().overlaps(this.getBox());
    }

    public boolean checkCollision(PlayerController pc) {
        return this.getBox().overlaps(pc.getBox());
    }
}
