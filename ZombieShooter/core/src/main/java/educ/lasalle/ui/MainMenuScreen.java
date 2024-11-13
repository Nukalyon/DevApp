package educ.lasalle.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import educ.lasalle.ZombieShooter;
import educ.lasalle.manager.AssetManager;

public class MainMenuScreen implements Screen {

    ZombieShooter zombieShooter;

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

    public MainMenuScreen(ZombieShooter zombieShooter) {
        this.zombieShooter = zombieShooter;
        touchPos = new Vector2();
        playButtonClicked = AssetManager.loadTexture("playClicked.png");
        playButtonUnclicked = AssetManager.loadTexture("playUnclicked.png");
        playButton = new Rectangle();
        exitButtonClicked = AssetManager.loadTexture("quitClicked.png");
        exitButtonUnclicked = AssetManager.loadTexture("quitUnclicked.png");
        exitButton = new Rectangle();
    }

    @Override
    public void show() {
        playButton.set(((float) ZombieShooter.SCREEN_WIDTH /2) - ((float) BTN_WIDTH /2), PLAY_Y, BTN_WIDTH, BTN_HEIGHT);
        System.out.println(playButton.toString());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int x = (ZombieShooter.SCREEN_WIDTH /2) - (BTN_WIDTH/2);

        zombieShooter.batch.begin();

        updatePlay();
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

        zombieShooter.batch.end();
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
