package educ.lasalle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

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
    PlayerController playerController;
    ArrayList<ZombieController> zombieControllers;
    ArrayList<BulletController> bulletControllers;
    float dropTimer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        background = AssetManager.loadTexture("background.png");
        image = AssetManager.loadTexture("libgdx.png");
        music = AssetManager.loadMusic("background.mp3");
        initMusic(music);
        touchPos = new Vector2();

        createPlayer();
        zombieControllers = new ArrayList<>();
        createZombie();
        bulletControllers = new ArrayList<>();
        createBullet();

    }

    private void createPlayer() {
        player = new Player();
        playerController = new PlayerController(player, viewport);
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
    }

    private void logicBullet() {
        // retrieve the current delta
        float delta = Gdx.graphics.getDeltaTime();
        //Déplacement des balles
        for (int i = bulletControllers.size() - 1; i >= 0; i--) {

            BulletController bci = bulletControllers.get(i);
            bci.update(delta);

            // if the top of the bullet goes beyond the top of the view, remove it
            if (bci.getSprite().getY() < -1) {
                bulletControllers.remove(i);
            }

            // Check collision with zombie
            for (ZombieController zbc : zombieControllers) {
                if (zbc.checkCollision(bulletControllers.get(i))) {
                    zombieControllers.remove(zbc);
                    bulletControllers.remove(i);
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
        playerController.getSprite().draw(batch);

        // Draw all zombies
        for (ZombieController zbc : zombieControllers) {
            zbc.getSprite().draw(batch);
        }
        // Draw all bullets
        for (BulletController btc: bulletControllers)
        {
            btc.getSprite().draw(batch);
        }

        batch.end();
    }

    private void input() {
        float delta = Gdx.graphics.getDeltaTime();
        float speed = 7f;
        Sprite playerSprite = playerController.getSprite();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerSprite.translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerSprite.translateX(-speed * delta); // Move the bucket left
        }

        // If the user has clicked or tapped the screen
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            playerSprite.setCenterX(touchPos.x);
        }
    }

    private void logicZombie() {
        // retrieve the current delta
        float delta = Gdx.graphics.getDeltaTime();
        playerController.update();

        //Déplacement des zombies
        for (int i = zombieControllers.size() - 1; i >= 0; i--) {

            ZombieController zci = zombieControllers.get(i);
            zci.update(delta);

            // if the top of the zombie goes below the bottom of the view, remove it
            if (zci.getSprite().getY() < -1) {
                zombieControllers.remove(i);
            } else if (zci.checkCollision(playerController)) {
                zci.inflictDamage(playerController);
                zombieControllers.remove(i);
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
        disposeArray(zombieControllers);
        disposeArray(bulletControllers);
        playerController = null;
        background = null;
    }

    private <T> void disposeArray(ArrayList<T> array) {
        if (array != null && !array.isEmpty()) {
            array.clear();
        }
    }

    private void createZombie() {
        Zombie zombie = new Zombie();
        ZombieController zombieController = new ZombieController(zombie, viewport);
        zombieController.initPosition();

        zombieControllers.add(zombieController);
    }

    private void createBullet(){
        // Initialisation de la bullet
        Bullet bullet = new Bullet();
        BulletController bulletController = new BulletController(bullet, viewport);
        bulletController.initPosition(playerController.getSprite());

        bulletControllers.add(bulletController);
    }
}
