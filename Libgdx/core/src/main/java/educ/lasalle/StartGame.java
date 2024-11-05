package educ.lasalle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class StartGame extends ApplicationAdapter {
    private final String ASSET_UI_PATH = System.getProperty("user.dir") +"\\assets\\ui\\";

    SpriteBatch spriteBatch;
    FitViewport viewport;
    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Sound dropSound;
    Music music;
    Sprite bucketSprite;
    Vector2 touchPos;
    Array<Sprite> dropSprites;
    float dropTimer;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;

    short current_score;
    String score;
    BitmapFont bitMapFont;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        backgroundTexture = new Texture(ASSET_UI_PATH + "background.png");
        bucketTexture = new Texture(ASSET_UI_PATH + "bucket.png");
        dropTexture = new Texture(ASSET_UI_PATH + "drop.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal(ASSET_UI_PATH + "drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal( ASSET_UI_PATH + "music.mp3"));

        // Initialize the sprite based on the texture
        bucketSprite = new Sprite(bucketTexture);
        // Define the size of the sprite
        bucketSprite.setSize(1, 1);
        touchPos = new Vector2();
        bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();

        dropSprites = new Array<>();
        createDroplet();

        music.setLooping(true);
        music.setVolume(.05f);
        music.play();

        current_score = 0;
        score = "score: 0";
        bitMapFont = new BitmapFont(new FileHandle(ASSET_UI_PATH + "font.fnt"), new TextureRegion(backgroundTexture));
        //bitMapFont.getData().scale(3f);
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
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        //Affichage du score
        bitMapFont.draw(spriteBatch, score, 10, 10);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        // Sprites have their own draw method
        bucketSprite.draw(spriteBatch);

        // draw each sprite
        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(spriteBatch);
        }

        spriteBatch.end();
    }

    private void logic() {
        // Store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        // Store the bucket size for brevity
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        // Clamp x to values between 0 and worldWidth
        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));

        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta
        // Apply the bucket position and size to the bucketRectangle
        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);

        /* Gouttes jamais vidée hors écran
        // loop through each drop
        for (Sprite dropSprite : dropSprites) {
            dropSprite.translateY(-2f * delta); // move the drop downward every frame
        }
        */
        // Loop through the sprites backwards to prevent out of bounds errors
        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i); // Get the sprite from the list
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);
            // Apply the drop position and size to the dropRectangle
            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            // if the top of the drop goes below the bottom of the view, remove it
            if (dropSprite.getY() < -dropHeight)
                dropSprites.removeIndex(i);
            // Check if the bucket overlaps the drop
            else if (bucketRectangle.overlaps(dropRectangle)) {
                dropSprites.removeIndex(i); // Remove the drop
                dropSound.play(); // Play the sound
                current_score++;
                score = "score: " + score;
            }
        }

        dropTimer += delta;
        if(dropTimer >1f)
        {
            dropTimer = 0;
            createDroplet();
        }
    }

    private void input() {
        float speed = .25f;
        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucketSprite.translateX(speed * delta); // Move the bucket right
        }else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucketSprite.translateX(-speed * delta); // Move the bucket left
        }

        if (Gdx.input.isTouched()) { // If the user has clicked or tapped the screen
            // todo:React to the player touching the screen
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            bucketSprite.setCenterX(touchPos.x); // Change the horizontally centered position of the bucket
        }
    }

    private void createDroplet() {
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth)); // Randomize the drop's x position
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        bitMapFont.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
        //super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }
}
