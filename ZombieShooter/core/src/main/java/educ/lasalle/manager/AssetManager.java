package educ.lasalle.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class AssetManager {
    private static final String ASSET_IMG_PATH = System.getProperty("user.dir") +"\\assets\\images\\";
    private static final String ASSET_SND_PATH = System.getProperty("user.dir") +"\\assets\\sounds\\";

    // Ramener toutes les Texture, Sound, Music, Animation ici en public static
    // Les Texture Sprite seront dans leurs classes
    public static Texture background;
    public static Texture image;

    public static Texture playButtonClicked;
    public static Texture playButtonUnclicked;
    public static Texture exitButtonClicked;
    public static Texture exitButtonUnclicked;

    public static Texture bulletTexture;
    public static final float bulletSize = .35f;

    public static Texture zombieTexture;
    public static final  float zombieSize = 1f;

    public static Texture playertexture;
    public static final float playerSize = 1f;

    public static Music music;


    public static Texture loadTexture(String filename){
        return new Texture(ASSET_IMG_PATH + filename);
    }

    public static Music loadMusic(String filename) {
        return Gdx.audio.newMusic(Gdx.files.internal(ASSET_SND_PATH + filename));
    }
    public static Sprite loadSprite(Texture texture, Vector2 sizes)
    {
        Sprite temp = new Sprite(texture);
        temp.setSize(sizes.x, sizes.y);
        return temp;
    }

    public static void loadAllAssets()
    {
        background = loadTexture("background.png");
        image = loadTexture("libgdx.png");
        playButtonClicked = loadTexture("playClicked.png");
        playButtonUnclicked = loadTexture("playUnclicked.png");
        exitButtonClicked = loadTexture("quitClicked.png");
        exitButtonUnclicked = loadTexture("quitUnclicked.png");

        zombieTexture = loadTexture("zombie.png");
        bulletTexture = loadTexture("bullet.png");

        playertexture = loadTexture("player.png");

        music = loadMusic("background.mp3");
    }
}
