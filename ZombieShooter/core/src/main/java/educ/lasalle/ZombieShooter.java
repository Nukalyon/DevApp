package educ.lasalle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.manager.AssetManager;
import educ.lasalle.ui.MainMenuScreen;

public class ZombieShooter extends Game {

    // ref : https://www.youtube.com/watch?v=b_fKQkwbuqA
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;

    // Used by all screens
    public SpriteBatch batch;
    public FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(ZombieShooter.SCREEN_WIDTH, ZombieShooter.SCREEN_HEIGHT);
        AssetManager.loadAllAssets();
        setScreen(new MainMenuScreen(this, viewport));
    }

    @Override
    public void render()
    {
        super.render();
    }
}
