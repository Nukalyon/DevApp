package educ.lasalle.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import educ.lasalle.ZombieShooter;

public class AssetManager {
    private static final String ASSET_IMG_PATH = System.getProperty("user.dir") +"\\assets\\images\\";
    private static final String ASSET_SND_PATH = System.getProperty("user.dir") +"\\assets\\sounds\\";

    // Ramener toutes les TextureRegion, Sound, Music, Animation ici en public static
    public static Texture background;

    public static Texture loadTexture(String filename){
        return new Texture(ASSET_IMG_PATH + filename);
    }

    public static Music loadMusic(String filename) {
        return Gdx.audio.newMusic(Gdx.files.internal(ASSET_SND_PATH + filename));
    }

    public static void loadAllAssets()
    {
        background = loadTexture("background.png");

    }
}
