package educ.lasalle.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

    private static final float BTN_WIDTH = 200f;
    private static final float BTN_HEIGHT = 100f;
    private static final float RESUME_Y = 310f;
    private static final float EXIT_Y = 190f;

    private Rectangle resumeButton;
    private Rectangle exitButton;

    Vector2 touchPos;
    PlayerController playerController;
    ArrayList<ZombieController> zombieControllers;
    ArrayList<BulletController> bulletControllers;
    float dropTimer;
    int state;
    private boolean isEscapePressed = false;

    BitmapFont scoreFont;
    ScoreManager scoreManager;

    public GameScreen(ZombieShooter zombieShooter, FitViewport viewport) {
        this.zombieShooter = zombieShooter;
        this.viewport = viewport;
        touchPos = new Vector2();
        state = GAME_RUNNING;
        resumeButton = new Rectangle(0,RESUME_Y,BTN_WIDTH,BTN_HEIGHT);
        exitButton = new Rectangle(0,EXIT_Y,BTN_WIDTH,BTN_HEIGHT);
    }

    @Override
    public void show() {
        createPlayer();
        zombieControllers = new ArrayList<>();
        createZombie();
        bulletControllers = new ArrayList<>();
        createBullet();

        scoreFont = new BitmapFont();
        initScore(scoreFont);

        // Pour le menu pause
        resumeButton.set(viewport.getWorldWidth() / 2 - (BTN_WIDTH / 2), RESUME_Y, BTN_WIDTH, BTN_HEIGHT);
        exitButton.set(viewport.getWorldWidth() / 2 - (BTN_WIDTH / 2), EXIT_Y, BTN_WIDTH, BTN_HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        draw();
        update(delta);
        CooldownManager.updateCooldownEscape(delta);
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
                updateGameOver();
                break;
            default:
                break;
        }
    }

    private void updateGameOver() {
        AssetManager.stopSound();
        zombieShooter.setScreen(new GameOverScreen(zombieShooter, viewport, scoreManager));
    }

    private void updateRunning(float delta) {
        if (state == GAME_RUNNING) {
            AssetManager.playSound();
            input(delta);
            logicBullet(delta);
            logicZombie(delta);
            if(playerController.isDead())
            {
                state = GAME_OVER;
            }
        }
    }

    private void updatePaused() {
        AssetManager.stopSound();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            state = GAME_RUNNING;  // Reprendre le jeu
        }

        // Check if the user clicks on the Resume or Exit buttons
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (resumeButton.contains(touchPos)) {
                state = GAME_RUNNING;
            }

            if (exitButton.contains(touchPos)) {
                Gdx.app.exit();
            }
        }
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
        scoreFont.getData().setScale(1.5f);
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
                continue;
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
        //viewport.apply();
        zombieShooter.batch.setProjectionMatrix(viewport.getCamera().combined);

        zombieShooter.batch.begin();
        switch(state) {
            case GAME_RUNNING:
                drawRunning();
                break;
            case GAME_PAUSED:
                drawPaused();
                break;
            case GAME_OVER:
                drawGameOver();
                break;
            default:
                break;
        }
        zombieShooter.batch.end();
    }

    private void drawGameOver() {
        //Change to GameOverScreen
        //Serialize score
    }


    private void drawPaused() {
        zombieShooter.batch.draw(AssetManager.background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        scoreFont.draw(zombieShooter.batch, "Score: " + scoreManager.getScore(), 0, viewport.getWorldHeight());

        // Draw the Resume and Exit buttons
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

        if (state == GAME_RUNNING) {
            // Move player when the game is running
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                playerSprite.translateX(speed * delta);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                playerSprite.translateX(-speed * delta); // Move player to the left
            }

            // If the user presses Escape, pause the game
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                //CooldownManager.tryPressEscape();

                if (CooldownManager.canPressEscape()) {
                    if (state == GAME_RUNNING) {
                        state = GAME_PAUSED;
                    } else if (state == GAME_PAUSED) {
                        state = GAME_RUNNING;
                    }
                }
            }

            // If the user touches the screen, move the player to the touch position
            if (Gdx.input.isTouched()) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY());
                viewport.unproject(touchPos);
                playerSprite.setCenterX(touchPos.x);
            }
        }
    }

    private void logicZombie(float delta) {
        playerController.update();

        //Déplacement des zombies
        for (int i = zombieControllers.size() - 1; i >= 0; i--) {

            ZombieController zci = zombieControllers.get(i);
            zci.update(delta);

            // if the top of the zombie goes below the bottom of the view, remove it and inflict damages
            if (zci.getSprite().getY() < -1) {
                zombieControllers.remove(i);
                zci.inflictDamage(playerController);
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
    }

    private void createBullet(){
        // Initialisation de la bullet
        Bullet bullet = new Bullet();
        BulletController bulletController = new BulletController(bullet, viewport);
        bulletController.initPosition(playerController.getSprite());

        bulletControllers.add(bulletController);
    }

    private <T> void disposeArray(ArrayList<T> array) {
        if (array != null && !array.isEmpty()) {
            array.clear();
        }
    }
}
