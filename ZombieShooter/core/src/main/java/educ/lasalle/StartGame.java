package educ.lasalle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
    Texture background;
    Vector2 touchPos;
    Player player;
    ArrayList<Zombie> zombies;
    ArrayList<Bullet> bullets;
    float dropTimer;
    Rectangle playerRectangle;
    Rectangle zombieRectangle;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        background = AssetManager.loadTexture("background.png");
        image = AssetManager.loadTexture("libgdx.png");
        music = AssetManager.loadMusic("background.mp3");
        initMusic(music);
        touchPos = new Vector2();

        player = new Player(0, 0);
        zombies = new ArrayList<>();
        createZombie();

        playerRectangle = new Rectangle();
        zombieRectangle = new Rectangle();
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
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();
        batch.draw(background, 0, 0, worldWidth, worldHeight);
        //batch.draw(image, 140, 210);
        player.getPlayerSprite().draw(batch);

        //float worldWidth = viewport.getWorldWidth();
        //float worldHeight = viewport.getWorldHeight();

        // draw each sprite
        for (Zombie zb : zombies) {
            zb.getZombieSprite().draw(batch);
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
        // Store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        //float worldHeight = viewport.getWorldHeight();
        // Store the bucket size for brevity
        float playerWidth = player.getPlayerSprite().getWidth();
        float playerHeight = player.getPlayerSprite().getHeight();

        // Clamp x to values between 0 and worldWidth
        player.getPlayerSprite().setX(MathUtils.clamp(player.getPlayerSprite().getX(), 0, worldWidth - playerWidth));

        // Apply the bucket position and size to the bucketRectangle
        playerRectangle.set(player.getPlayerSprite().getX(), player.getPlayerSprite().getY(), playerWidth, playerHeight);
        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        for (int i = zombies.size() - 1; i >= 0; i--) {

            // Get the sprite from the list
            Sprite zbSprite = zombies.get(i).getZombieSprite();

            zbSprite.translateY(-2f * delta);

            float zbWidth = zbSprite.getWidth();
            float zbHeight = zbSprite.getHeight();

            // Apply the zombie position and size to the zombieRectangle
            zombieRectangle.set(zbSprite.getX(), zbSprite.getY(), zbWidth, zbHeight);

            // if the top of the zombie goes below the bottom of the view, remove it
            if (zbSprite.getY() < -1)
                zombies.remove(i);
                // Check if the player overlaps the player
            else if (playerRectangle.overlaps(zombieRectangle)) {
                zombies.get(i).inflictDamage(player);
                zombies.remove(i); // Remove the zombie
            }
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
        disposeArray(zombies);
        disposeArray(bullets);
        player = null;
        background = null;
    }

    private <T> void disposeArray(ArrayList<T> array) {
        if (array != null && !array.isEmpty()) {
            array.clear();
        }
    }

    private void createZombie() {
        Zombie zombie = new Zombie();
        zombie.initPosition(viewport);
        zombies.add(zombie);
    }
}
