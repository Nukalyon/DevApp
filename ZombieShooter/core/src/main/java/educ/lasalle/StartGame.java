package educ.lasalle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class StartGame extends ApplicationAdapter {
    FitViewport viewport;
    Music music;
    private SpriteBatch batch;
    private Texture image;
    Vector2 touchPos;
    Player player;
    ArrayList<Sprite> zbSprites;
    ArrayList<Sprite> bullets;
    float dropTimer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        image = AssetManager.loadTexture("libgdx.png");
        music = AssetManager.loadMusic("background.mp3");
        initMusic(music);
        touchPos = new Vector2();

        player = new Player(0, 0);
        zbSprites = new ArrayList<>();
        createZombie();
    }

    private void initMusic(Music music) {
        music.setLooping(true);
        music.setVolume(.15f);
        music.play();
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        //batch.draw(image, 140, 210);
        player.getPlayerSprite().draw(batch);

        //float worldWidth = viewport.getWorldWidth();
        //float worldHeight = viewport.getWorldHeight();

        // draw each sprite
        for (Sprite zb : zbSprites) {
            zb.draw(batch);
        }

        batch.end();
    }

    private void input() {
        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta
        float speed = 7f;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            // Move the player right
            player.getPlayerSprite().translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            // Move the player left
            player.getPlayerSprite().translateX(-speed * delta); // Move the bucket left
        }

        if (Gdx.input.isTouched()) { // If the user has clicked or tapped the screen
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            player.getPlayerSprite().setCenterX(touchPos.x); // Change the horizontally centered position of the bucket
        }
    }

    private void logic() {
        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        for (int i = zbSprites.size() - 1; i >= 0; i--) {
            Sprite zbSprite = zbSprites.get(i); // Get the sprite from the list
            float zbWidth = zbSprite.getWidth();
            float zbHeight = zbSprite.getHeight();

            zbSprite.translateY(-2f * delta);
            // Apply the drop position and size to the dropRectangle
            //dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            // if the top of the drop goes below the bottom of the view, remove it
            if (zbSprite.getY() < -zbHeight)
                zbSprites.remove(i);
            /*
                // Check if the bucket overlaps the drop
            else if (bucketRectangle.overlaps(dropRectangle)) {
                zbSprites.remove(i); // Remove the drop
            }
             */
        }

        dropTimer += delta;
        if (dropTimer > 1f) {
            dropTimer = 0;
            createZombie();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        //super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        music.dispose();
        disposeArray(zbSprites);
        disposeArray(bullets);
        player = null;
    }

    private void disposeArray(ArrayList<Sprite> array) {
        if (array != null && !array.isEmpty()) {
            array.clear();
        }
    }

    private void createZombie() {
        Zombie zombie = new Zombie();
        zombie.initPosition(viewport);
        zbSprites.add(zombie.getZombieSprite());
    }
}
