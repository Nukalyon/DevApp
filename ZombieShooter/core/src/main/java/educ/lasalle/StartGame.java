package educ.lasalle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable.draw;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class StartGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    Music music;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = AssetManager.loadTexture("libgdx.png");
        music = AssetManager.loadMusic("background.mp3");
        music.setLooping(true);
        music.setVolume(.05f);
        music.play();
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void draw() {
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    private void input() {
    }

    private void logic() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        music.dispose();
    }
}
