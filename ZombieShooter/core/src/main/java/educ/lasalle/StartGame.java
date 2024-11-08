package educ.lasalle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.*;
import java.util.ArrayList;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class StartGame extends ApplicationAdapter {
    FitViewport viewport;
    Music music;
    private SpriteBatch batch;
    private Texture image;
    Texture background;
    Vector2 touchPos;
    Player player;
    ArrayList<Zombie> zombies;
    ArrayList<Bullet> bullets;
    float dropTimer;
    BitmapFont font;

    private ScoreManager scoreManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        background = AssetManager.loadTexture("background.png");
        image = AssetManager.loadTexture("libgdx.png");
        music = AssetManager.loadMusic("background.mp3");
        initMusic(music);
        touchPos = new Vector2();

        player = new Player();
        zombies = new ArrayList<>();
        createZombie();
        bullets = new ArrayList<>();
        createBullet();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.015f);
        scoreManager = new ScoreManager();
    }

    private void initMusic(Music music) {
        music.setLooping(true);
        music.setVolume(.05f);
        music.play();
    }

    @Override
    public void render() {
        input();
        logicBullet();
        logicZombie();
        draw();
        if (player.isDead()) {
            //Set game over screen
        }
    }

    private void logicBullet() {
        // retrieve the current delta
        float delta = Gdx.graphics.getDeltaTime();
        //Déplacement des balles
        for (int i = bullets.size() - 1; i >= 0; i--) {

            bullets.get(i).update(delta);

            // if the top of the bullet goes below the top of the view, remove it
            if (bullets.get(i).getBulletSprite().getY() < -1) {
                bullets.remove(i);
            }

            // Check collision with zombie
            for (Zombie zombie : zombies) {
                if (zombie.checkCollision(bullets.get(i))) {
                    zombies.remove(zombie);
                    bullets.remove(i);
                    scoreManager.increaseScore(1);
                    break;
                }
            }
        }

        CooldownManager.updateCooldownBullet(delta);
        if(CooldownManager.canShoot)
        {
            createBullet();
        }
    }

    private void draw() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(background, 0, 0, worldWidth, worldHeight);
        player.getPlayerSprite().draw(batch);
        font.draw(batch, "Score: " + scoreManager.getScore(), 0, viewport.getWorldHeight());
        // Draw all zombies
        for (Zombie zb : zombies) {
            zb.getZombieSprite().draw(batch);
        }
        // Draw all bullets
        for (Bullet bt: bullets)
        {
            bt.getBulletSprite().draw(batch);
        }

        batch.end();
    }

    private void input() {
        float delta = Gdx.graphics.getDeltaTime();
        float speed = 7f;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.getPlayerSprite().translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.getPlayerSprite().translateX(-speed * delta); // Move the bucket left
        }

        // If the user has clicked or tapped the screen
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            player.getPlayerSprite().setCenterX(touchPos.x);
        }
    }

    private void logicZombie() {
        // retrieve the current delta
        float delta = Gdx.graphics.getDeltaTime();
        player.update(viewport);

        //Déplacement des zombies
        for (int i = zombies.size() - 1; i >= 0; i--) {

            zombies.get(i).update(delta);

            // if the top of the zombie goes below the bottom of the view, remove it
            if (zombies.get(i).getZombieSprite().getY() < -1) {
                zombies.remove(i);
            } else if (zombies.get(i).checkCollision(player)) {
                zombies.get(i).inflictDamage(player);
                zombies.remove(i);
            }
        }

        dropTimer += delta;
        if (dropTimer > 1f) {
            dropTimer = 0;
            createZombie();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        music.dispose();
        disposeArray(zombies);
        disposeArray(bullets);
        player = null;
        background = null;
        font.dispose();
    }

    private <T> void disposeArray(ArrayList<T> array) {
        if (array != null && !array.isEmpty()) {
            array.clear();
        }
    }

    private void createZombie() {
        Zombie zombie = new Zombie();
        zombie.initPosition(viewport);
        zombies.add(zombie);
    }

    private void createBullet(){
        Bullet bullet = new Bullet();
        bullet.initPosition(player.getPlayerSprite());
        bullets.add(bullet);
    }
}
