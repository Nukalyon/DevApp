package educ.lasalle.controller;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.model.Bullet;

public class BulletController {
    private Bullet bullet;
    private FitViewport view;

    public BulletController(Bullet bullet, FitViewport view) {
        this.bullet = bullet;
        this.view = view;
    }

    public Sprite getSprite()
    {
        return this.bullet.getSprite();
    }

    public Rectangle getBox()
    {
        return this.bullet.getBox();
    }

    public float getSize()
    {
        return this.bullet.getSize();
    }

    public void initPosition(Sprite sp)
    {
        this.bullet.initPosition(sp);
    }

    public void update(float delta)
    {
        Sprite sp = this.getSprite();
        float size = this.getSize();
        sp.translateY(200f * delta);
        this.getBox().set(sp.getX(), sp.getY(), size, size);
    }
}
