package educ.lasalle.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
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

    static final int GAME_RUNNING = 0;
    static final int GAME_PAUSED = 1;
    static final int GAME_OVER = 2;

    ZombieShooter zombieShooter;
    FitViewport viewport;

    private static final int BTN_WIDTH = 150;
    private static final int BTN_HEIGHT = 75;
    private static final int RESUME_Y = 310;
    private static final int EXIT_Y = 190;

    private Rectangle resumeButton;
    private Rectangle exitButton;

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

        resumeButton = new Rectangle(0,RESUME_Y,BTN_WIDTH,BTN_HEIGHT);
        exitButton = new Rectangle(0,EXIT_Y,BTN_WIDTH,BTN_HEIGHT);
    }

    @Override
    public void show() {
        state = GAME_RUNNING;
        touchPos = new Vector2();

        createPlayer();
        zombieControllers = new ArrayList<>();
        createZombie();
        bulletControllers = new ArrayList<>();
        createBullet();

        scoreFont = new BitmapFont();
        initScore(scoreFont);

        // Pour le menu pause
        //pauseButton.set(((float) ZombieShooter.SCREEN_WIDTH /2) - ((float) BTN_WIDTH /2), RESUME_Y, BTN_WIDTH, BTN_HEIGHT);
        resumeButton.set(((float) ZombieShooter.SCREEN_WIDTH /2) - ((float) BTN_WIDTH /2), RESUME_Y, BTN_WIDTH, BTN_HEIGHT);
        //System.out.println("Resume button : " + resumeButton);
        exitButton.set(((float) ZombieShooter.SCREEN_WIDTH /2) - ((float) BTN_WIDTH /2), EXIT_Y, BTN_WIDTH, BTN_HEIGHT);
        //System.out.println("Exit button : " + resumeButton);
    }

    @Override
    public void render(float delta) {
        draw();
        update(delta);
    }

    private void update(float delta) {
        if (delta > 0.1f) delta = 0.1f;
        switch (state)
        {
            case GAME_RUNNING:
                updateRunning(delta);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_OVER:
                updateOver();
                break;
            default:
                break;
        }
    }

    private void updateRunning(float delta) {
        AssetManager.playSound();
        input(delta);
        logicBullet(delta);
        logicZombie(delta);
    }

    private void updatePaused() {
        AssetManager.stopSound();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            state = GAME_RUNNING;
        }
    }

    private void updateOver() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        if (state == GAME_RUNNING) state = GAME_PAUSED;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        disposeArray(zombieControllers);
        disposeArray(bulletControllers);
        scoreFont.dispose();
    }

    private void createPlayer() {
        playerController = new PlayerController(new Player(), viewport);
        playerController.initRotation();
    }

    private void initScore(BitmapFont scoreFont) {
        scoreFont.setColor(Color.WHITE);
        scoreFont.setUseIntegerPositions(false);
        scoreFont.getData().setScale(0.015f);
        scoreManager = new ScoreManager();
    }

    private void logicBullet(float delta) {
        //Déplacement des balles
        for (int i = bulletControllers.size() - 1; i >= 0; i--) {

            BulletController bci = bulletControllers.get(i);
            bci.update(delta);

            // TODO, check bullet going off screen
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
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        viewport.getCamera().update();
        zombieShooter.batch.setProjectionMatrix(viewport.getCamera().combined);

        //zombieShooter.batch.disableBlending();
        zombieShooter.batch.enableBlending();
        zombieShooter.batch.begin();
        /*************** START DRAW *******************/
        switch(state)
        {
            case GAME_RUNNING:
                drawRunning();
                break;
            case GAME_PAUSED:
                ScreenUtils.clear(Color.NAVY);
                drawPaused();
                break;
            case GAME_OVER:
                break;
            default:
                break;
        }
        /*************** END DRAW *******************/
        zombieShooter.batch.end();
    }

    private void drawPaused() {
        // TODO: Doesn't draw, flush batch ?
        scoreFont.draw(zombieShooter.batch, "Score: " + scoreManager.getScore(), 0, viewport.getWorldHeight());
        zombieShooter.batch.draw(AssetManager.resumeButtonUnclicked, resumeButton.getX(), RESUME_Y, BTN_WIDTH, BTN_HEIGHT);
        zombieShooter.batch.draw(AssetManager.exitButtonUnclicked, exitButton.getX(), EXIT_Y, BTN_WIDTH, BTN_HEIGHT);
    }

    private void drawRunning() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        zombieShooter.batch.draw(AssetManager.background, 0, 0, worldWidth, worldHeight);
        zombieShooter.batch.draw(
            playerController.getSprite(),
            playerController.getSprite().getX(),
            playerController.getSprite().getY(),
            playerController.getSprite().getWidth(),
            playerController.getSprite().getHeight()
        );

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
    }

    private void input(float delta) {
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
            state = GAME_PAUSED;
        }
    }

    private void logicZombie(float delta) {
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
