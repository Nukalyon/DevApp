package educ.lasalle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.manager.AssetManager;
import educ.lasalle.ui.MainMenuScreen;

public class ZombieShooter extends Game {

    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 480;

    // Used by all screens
    public SpriteBatch batch;
    public FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(10, 7);
        AssetManager.loadAllAssets();
        setScreen(new MainMenuScreen(this, viewport));
    }

    @Override
    public void render()
    {
        super.render();
    }
}
