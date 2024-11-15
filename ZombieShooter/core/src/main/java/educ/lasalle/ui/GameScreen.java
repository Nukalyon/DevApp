package educ.lasalle.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.ZombieShooter;
import educ.lasalle.controller.BulletController;
import educ.lasalle.controller.PlayerController;
import educ.lasalle.controller.ZombieController;
import educ.lasalle.manager.AssetManager;
import educ.lasalle.manager.CooldownManager;
import educ.lasalle.manager.ScoreManager;
import educ.lasalle.model.Bullet;
import educ.lasalle.model.Player;
import educ.lasalle.model.Zombie;

import java.util.ArrayList;

public class GameScreen implements Screen {

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;

    ZombieShooter zombieShooter;
    FitViewport viewport;

    Vector2 touchPos;
    PlayerController playerController;
    ArrayList<ZombieController> zombieControllers;
    ArrayList<BulletController> bulletControllers;
    float dropTimer;
    int state;

    BitmapFont scoreFont;
    ScoreManager scoreManager;

    public GameScreen(ZombieShooter zombieShooter, FitViewport viewport) {
        this.zombieShooter = zombieShooter;
        this.viewport = viewport;
    }

    @Override
    public void show() {
        state = GAME_READY;
        initMusic(AssetManager.music);
        touchPos = new Vector2();

        createPlayer();
        zombieControllers = new ArrayList<>();
        createZombie();
        bulletControllers = new ArrayList<>();
        createBullet();

        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.WHITE);
        scoreFont.setUseIntegerPositions(false);
        scoreFont.getData().setScale(0.015f);
        scoreManager = new ScoreManager();

    }

    @Override
    public void render(float delta) {
        switch (state)
        {
            case GAME_READY:
                //resume();
                input();
                logicBullet();
                logicZombie();
                draw();
                break;
            case GAME_PAUSED:
                AssetManager.music.stop();
                displayPauseMenu();
                //pause();
                break;
            default:
                break;
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //image.dispose();
        //music.dispose();
        disposeArray(zombieControllers);
        disposeArray(bulletControllers);
        //playerController = null;
        //background = null;
        scoreFont.dispose();
    }

    private void createPlayer() {
        playerController = new PlayerController(new Player(), viewport);
        playerController.initRotation();
    }

    private void initMusic(Music music) {
        music.setLooping(true);
        music.setVolume(.05f);
        music.play();
    }

    private void logicBullet() {
        // retrieve the current delta
        float delta = Gdx.graphics.getDeltaTime();
        //Déplacement des balles
        for (int i = bulletControllers.size() - 1; i >= 0; i--) {

            BulletController bci = bulletControllers.get(i);
            bci.update(delta);

            // if the top of the bullet goes beyond the top of the view, remove it
            if (bci.getSprite().getY() > ZombieShooter.SCREEN_HEIGHT) {
                bulletControllers.remove(i);
            }

            // Check collision with zombie
            for (ZombieController zbc : zombieControllers) {
                if (zbc.checkCollision(bulletControllers.get(i))) {
                    zombieControllers.remove(zbc);
                    bulletControllers.remove(i);
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
        zombieShooter.batch.setProjectionMatrix(viewport.getCamera().combined);

        zombieShooter.batch.begin();
        /*************** START DRAW *******************/
        zombieShooter.batch.draw(AssetManager.background, 0, 0, worldWidth, worldHeight);
        zombieShooter.batch.draw(playerController.getSprite(), playerController.getSprite().getX(), playerController.getSprite().getY(),
            playerController.getSprite().getWidth(), playerController.getSprite().getHeight());

        //playerController.getSprite().draw(zombieShooter.batch);
        scoreFont.draw(zombieShooter.batch, "Score: " + scoreManager.getScore(), 0, worldHeight);

        // Draw all zombies
        for (ZombieController zbc : zombieControllers) {
            zbc.getSprite().draw(zombieShooter.batch);
        }
        // Draw all bullets
        for (BulletController btc: bulletControllers)
        {
            btc.getSprite().draw(zombieShooter.batch);
        }
        /*************** END DRAW *******************/
        zombieShooter.batch.end();
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

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            // open pause menu
            state = (state == GAME_PAUSED ? GAME_RUNNING : GAME_PAUSED);
        }
    }

    private void displayPauseMenu() {

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

    private void createZombie() {
        Zombie zombie = new Zombie();
        ZombieController zombieController = new ZombieController(zombie, viewport);
        zombieController.initPosition();

        zombieControllers.add(zombieController);
        //System.out.println("Zombie Count: " + zombieControllers.size());
    }

    private void createBullet(){
        // Initialisation de la bullet
        Bullet bullet = new Bullet();
        BulletController bulletController = new BulletController(bullet, viewport);
        bulletController.initPosition(playerController.getSprite());

        bulletControllers.add(bulletController);
        //System.out.println("Bullet Count: " + bulletControllers.size());
    }

    private <T> void disposeArray(ArrayList<T> array) {
        if (array != null && !array.isEmpty()) {
            array.clear();
        }
    }
}
