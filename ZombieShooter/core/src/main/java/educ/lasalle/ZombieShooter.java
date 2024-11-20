package educ.lasalle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.tools.javac.Main;
import educ.lasalle.manager.AssetManager;
import educ.lasalle.ui.LoginScreen;
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
        viewport = new FitViewport(640, 480);
        AssetManager.loadAllAssets();
        setScreen(new LoginScreen(this, viewport));
    }

    @Override
    public void render()
    {
        super.render();
    }
}
