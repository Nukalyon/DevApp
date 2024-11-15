package educ.lasalle.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.ZombieShooter;
import educ.lasalle.manager.AssetManager;

public class MainMenuScreen implements Screen {

    ZombieShooter zombieShooter;
    FitViewport viewport;

    private static final int BTN_WIDTH = 150;
    private static final int BTN_HEIGHT = 75;
    private static final int PLAY_Y = 310;
    private static final int EXIT_Y = 190;

    Rectangle playButton;
    Rectangle exitButton;

    Vector2 touchPos;

    public MainMenuScreen(ZombieShooter zombieShooter, FitViewport viewport) {
        this.zombieShooter = zombieShooter;
        this.viewport = viewport;

        touchPos = new Vector2();

        playButton = new Rectangle(0,PLAY_Y,BTN_WIDTH,BTN_HEIGHT);
        exitButton = new Rectangle(0,EXIT_Y,BTN_WIDTH,BTN_HEIGHT);
    }

    @Override
    public void show() {
        playButton.set(((float) ZombieShooter.SCREEN_WIDTH /2) - ((float) BTN_WIDTH /2), PLAY_Y, BTN_WIDTH, BTN_HEIGHT);
        exitButton.set(((float) ZombieShooter.SCREEN_WIDTH /2) - ((float) BTN_WIDTH /2), EXIT_Y, BTN_WIDTH, BTN_HEIGHT);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        update();
        draw();
    }

    private void draw() {
        zombieShooter.batch.begin();

        zombieShooter.batch.draw(AssetManager.background, 0,0, ZombieShooter.SCREEN_WIDTH, ZombieShooter.SCREEN_HEIGHT);
        zombieShooter.batch.draw(AssetManager.playButtonUnclicked, playButton.getX(), PLAY_Y, BTN_WIDTH, BTN_HEIGHT);
        zombieShooter.batch.draw(AssetManager.exitButtonUnclicked, exitButton.getX(), EXIT_Y, BTN_WIDTH, BTN_HEIGHT);

        zombieShooter.batch.end();
    }

    private void update() {
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            System.out.println("touchPos " + touchPos);
            if (playButton.contains(touchPos.x, touchPos.y)) {
                zombieShooter.setScreen(new GameScreen(zombieShooter, viewport)); // Assuming you have a GameScreen
            }
            if (exitButton.contains(touchPos.x, touchPos.y)) {
                // Exit the application
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    private void updatePlay() {

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

    }
}