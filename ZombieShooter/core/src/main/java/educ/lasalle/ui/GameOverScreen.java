package educ.lasalle.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.ZombieShooter;
import educ.lasalle.manager.AssetManager;
import educ.lasalle.manager.ScoreManager;
import educ.lasalle.manager.UserManager;

public class GameOverScreen implements Screen {
    //Used to separate Labels and inputs
    private final int PIXEL_PADDING = 5;
    //Pixels between elements
    private final int SPACE_BETWEEN = 20;
    //Pixels to center the elements
    private int spaceTopAndBot;

    private Stage stage;
    private final ZombieShooter zombieShooter;
    private final FitViewport viewport;
    private final ScoreManager scoreManager;
    private Skin glassy;

    private Label yourScore;
    private TextButton btnExit;
    private TextButton btnRedo;

    public GameOverScreen(ZombieShooter zombieShooter, FitViewport viewport, ScoreManager scoreManager) {
        this.stage = new Stage(viewport);
        this.zombieShooter = zombieShooter;
        this.viewport = viewport;
        this.scoreManager = scoreManager;
        Gdx.input.setInputProcessor(stage);

        spaceTopAndBot = (viewport.getScreenHeight() - 3 * SPACE_BETWEEN) / 2;

        this.glassy = AssetManager.glassy;
        yourScore = new Label("Votre score: " + scoreManager.getScore(), glassy, "black");
        btnRedo = new TextButton("Recommencer", glassy, "default");
        btnExit = new TextButton("Quitter", glassy, "default");
    }

    @Override
    public void show() {
        //Zone textuelle
        yourScore.setSize(200, 50);
        yourScore.setPosition(stage.getWidth()/2 - (yourScore.getWidth() /2) , viewport.getScreenHeight() - spaceTopAndBot);
        stage.addActor(yourScore);

        // Zone des buttons
        btnRedo.addListener(new ClickListener() {
            //Handle connection
            @Override
            public void clicked(InputEvent event, float x, float y) {
                UserManager.registerScore(scoreManager.getScore());
                zombieShooter.setScreen(new GameScreen(zombieShooter, viewport));
            }
        });

        btnRedo.getStyle().font.getData().setScale(0.5f);
        btnRedo.setSize(250, btnRedo.getPrefHeight());
        btnRedo.setPosition(stage.getWidth() / 2 - btnRedo.getWidth() / 2, stage.getHeight() /2 - btnRedo.getHeight());
        stage.addActor(btnRedo);

        btnExit.addListener(new ClickListener() {
            //Handle connection
            @Override
            public void clicked(InputEvent event, float x, float y) {
                UserManager.registerScore(scoreManager.getScore());
                Gdx.app.exit();
            }
        });

        btnExit.getStyle().font.getData().setScale(0.5f);
        btnExit.setSize(250, btnExit.getPrefHeight());
        btnExit.setPosition(stage.getWidth() / 2 - btnExit.getWidth() / 2 , stage.getHeight() / 2 - btnExit.getHeight() * 2);
        stage.addActor(btnExit);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
