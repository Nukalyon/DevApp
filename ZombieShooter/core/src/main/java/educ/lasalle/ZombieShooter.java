package educ.lasalle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.manager.AssetManager;
import educ.lasalle.ui.LoginScreen;

public class ZombieShooter extends Game {

    public static final int SCREEN_WIDTH = 1440;
    public static final int SCREEN_HEIGHT = 900;

    // Used by all screens
    public SpriteBatch batch;
    public FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        AssetManager.loadAllAssets();
        setScreen(new LoginScreen(this, viewport));
    }

    @Override
    public void render()
    {
        super.render();
    }
}
