package educ.lasalle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import educ.lasalle.ui.GameScreen;
import educ.lasalle.ui.MainMenuScreen;

public class ZombieShooter extends Game {

    // ref : https://www.youtube.com/watch?v=b_fKQkwbuqA
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;

    // Used by all screens
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render()
    {
        super.render();
    }
}
