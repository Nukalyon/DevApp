package educ.lasalle.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.ZombieShooter;
import educ.lasalle.manager.AssetManager;

public class MainMenuScreen implements Screen {

    ZombieShooter zombieShooter;
    FitViewport viewport;

    private static final int BTN_WIDTH = 200;
    private static final int BTN_HEIGHT = 100;
    private static final int PLAY_Y = 100;
    private static final int EXIT_Y = 300;

    Texture playButtonClicked;
    Texture playButtonUnclicked;
    Rectangle playButton;

    Texture exitButtonClicked;
    Texture exitButtonUnclicked;
    Rectangle exitButton;

    Vector2 touchPos;

    public MainMenuScreen(ZombieShooter zombieShooter, FitViewport viewport) {
        this.zombieShooter = zombieShooter;
        this.viewport = viewport;

        touchPos = new Vector2();

        playButtonClicked = AssetManager.loadTexture("playClicked.png");
        playButtonUnclicked = AssetManager.loadTexture("playUnclicked.png");
        playButton = new Rectangle(0,PLAY_Y,BTN_WIDTH,BTN_HEIGHT);
        exitButtonClicked = AssetManager.loadTexture("quitClicked.png");
        exitButtonUnclicked = AssetManager.loadTexture("quitUnclicked.png");
        exitButton = new Rectangle(0,EXIT_Y,BTN_WIDTH,BTN_HEIGHT);
    }

    @Override
    public void show() {
        playButton.set(((float) ZombieShooter.SCREEN_WIDTH /2) - ((float) BTN_WIDTH /2), PLAY_Y, BTN_WIDTH, BTN_HEIGHT);
        exitButton.set(((float) ZombieShooter.SCREEN_WIDTH /2) - ((float) BTN_WIDTH /2), EXIT_Y, BTN_WIDTH, BTN_HEIGHT);
        //System.out.println(playButton.toString());
    }

    @Override
    public void render(float delta) {
        //update();
        draw();
    }

    private void draw() {
        zombieShooter.batch.begin();

        zombieShooter.batch.draw(AssetManager.background, 0,0, ZombieShooter.SCREEN_WIDTH, ZombieShooter.SCREEN_HEIGHT);

        zombieShooter.batch.end();
    }

    private void update() {
        int x = (ZombieShooter.SCREEN_WIDTH /2) - (BTN_WIDTH/2);
        if(Gdx.input.justTouched())
        {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            if(playButton.contains(touchPos.x, touchPos.y))
            {
                zombieShooter.batch.draw(playButtonClicked, x, PLAY_Y, BTN_WIDTH, BTN_HEIGHT);
            }
            else  {
                zombieShooter.batch.draw(playButtonUnclicked, x, PLAY_Y, BTN_WIDTH, BTN_HEIGHT);
            }
        }

    }

    @Override
    public void resize(int width, int height) {

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
