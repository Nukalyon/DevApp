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
    private final String ASSET_IMG_PATH = System.getProperty("user.dir") +"\\assets\\images\\";
    private final String ASSET_SND_PATH = System.getProperty("user.dir") +"\\assets\\sounds\\";
    private SpriteBatch batch;
    private Texture image;
    Music music;
    private Long soundId;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        music = Gdx.audio.newMusic(Gdx.files.internal(ASSET_SND_PATH + "background.mp3"));
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
