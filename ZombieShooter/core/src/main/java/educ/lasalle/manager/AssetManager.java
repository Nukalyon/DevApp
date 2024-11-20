package educ.lasalle.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetManager {
    private static final String ASSET_IMG_PATH = System.getProperty("user.dir") +"\\assets\\images\\";
    private static final String ASSET_SND_PATH = System.getProperty("user.dir") +"\\assets\\sounds\\";
    private static final String ASSET_SKIN_PATH = System.getProperty("user.dir") +"\\assets\\skin\\";

    public static Texture background;
    public static Texture image;

    public static Texture playButtonUnclicked;
    public static Texture exitButtonUnclicked;
    public static Texture resumeButtonUnclicked;

    public static Texture bulletTexture;
    public static final float bulletSize = .35f;

    public static Texture zombieTexture;
    public static final  float zombieSize = 1f;

    public static Texture playertexture;
    public static final float playerSize = 1f;

    public static Music music;
    public static float timestampMusic;

    public static Skin glassy;



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

    public static Skin loadSkin(String folder, String filename)
    {
        return new Skin(Gdx.files.internal(ASSET_SKIN_PATH + folder + "\\" +filename));
    }

    public static void loadAllAssets()
    {
        background = loadTexture("background.png");
        image = loadTexture("libgdx.png");
        playButtonUnclicked = loadTexture("playUnclicked.png");
        exitButtonUnclicked = loadTexture("quitUnclicked.png");
        resumeButtonUnclicked = loadTexture("resume.png");

        zombieTexture = loadTexture("zombie.png");
        bulletTexture = loadTexture("bullet.png");

        playertexture = loadTexture("player.png");

        music = loadMusic("background.mp3");
        music.setLooping(true);
        music.setVolume(.05f);

        glassy = loadSkin("glassy","glassy-ui.json");
    }

    public static void playSound() {
        if(!music.isPlaying()) {
            music.setPosition(timestampMusic);
            music.play();
        }
    }
    public static void stopSound()
    {
        if(music.isPlaying()) {
            timestampMusic = music.getPosition();
            music.stop();
        }
    }
}
