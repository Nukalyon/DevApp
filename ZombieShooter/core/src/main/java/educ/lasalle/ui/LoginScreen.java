package educ.lasalle.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import educ.lasalle.ZombieShooter;
import educ.lasalle.manager.AssetManager;
import educ.lasalle.manager.UserManager;

import java.awt.event.KeyListener;


//https://libgdxinfo.wordpress.com/basic-label/
public class LoginScreen implements Screen {
    //Used to separate Labels and inputs
    private final int PIXEL_PADDING = 5;
    //Pixels between elements
    private final int SPACE_BETWEEN = 20;
    //Pixels to center the elements
    private int spaceTopAndBot;
    private int spaceCenterToStartButton;

    private Stage stage;
    private final ZombieShooter zombieShooter;
    private final FitViewport viewport;
    private Skin glassy;

    private Label login;
    private Label password;
    private TextField txtFieldLogin;
    private TextField txtFieldPassword;
    private TextButton btnConnect;
    private TextButton btnRegister;

    public LoginScreen(ZombieShooter zombieShooter, FitViewport viewport) {
        stage = new Stage(viewport);
        this.zombieShooter = zombieShooter;
        this.viewport = viewport;
        Gdx.input.setInputProcessor(stage);

        spaceTopAndBot = (viewport.getScreenHeight() - 3 * SPACE_BETWEEN) / 2;

        this.glassy = AssetManager.glassy;
        login = new Label("Login: ", glassy, "black");
        txtFieldLogin = new TextField("", glassy);
        password = new Label("Password: ", glassy, "black");
        txtFieldPassword = new TextField("", glassy);
        txtFieldPassword.setPasswordMode(true);
        txtFieldPassword.setPasswordCharacter('*');

        // https://stackoverflow.com/questions/21488311/how-to-create-a-button-in-libgdx
        btnConnect = new TextButton("Connect", glassy, "default");
        btnRegister = new TextButton("Register", glassy, "default");
    }

    @Override
    public void show() {
        //Zone Login
        login.setSize(200, 50);
        login.setPosition(stage.getWidth()/2 - (login.getWidth() + PIXEL_PADDING) , viewport.getScreenHeight() - spaceTopAndBot);
        stage.addActor(login);

        //Zone Input Login
        txtFieldLogin.setSize(250, 50);
        txtFieldLogin.setPosition(stage.getWidth()/2 +  PIXEL_PADDING, viewport.getScreenHeight() - spaceTopAndBot);
        stage.addActor(txtFieldLogin);

        //Zone Password
        password.setSize(200, 50);
        password.setPosition(stage.getWidth()/2 - (login.getWidth() + PIXEL_PADDING) , spaceTopAndBot);
        stage.addActor(password);

        //Zone Input Password
        txtFieldPassword.setSize(250, 50);
        txtFieldPassword.setPosition(stage.getWidth()/2 +  PIXEL_PADDING, spaceTopAndBot);
        stage.addActor(txtFieldPassword);

        //Zone Button Connect
        btnConnect.addListener(new ClickListener() {
            //Handle connection
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = txtFieldLogin.getText();
                String password = txtFieldPassword.getText();
                if(UserManager.authenticateUser(username,password))
                {
                    zombieShooter.setScreen(new GameScreen(zombieShooter, viewport));
                }

            }
        });

        btnConnect.getStyle().font.getData().setScale(0.5f);
        btnConnect.setSize(250, btnConnect.getPrefHeight());
        btnConnect.setPosition(stage.getWidth() / 2 , spaceTopAndBot - SPACE_BETWEEN - btnConnect.getPrefHeight());
        stage.addActor(btnConnect);

        btnRegister.addListener(new ClickListener() {
            //Handle connection
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = txtFieldLogin.getText();
                String password = txtFieldPassword.getText();
                UserManager.registerUser(username,password);
            }
        });

        btnRegister.getStyle().font.getData().setScale(0.5f);
        btnRegister.setSize(250, btnRegister.getPrefHeight());
        btnRegister.setPosition(stage.getWidth() / 2 , 175);
        stage.addActor(btnRegister);
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
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Handle pause
    }

    @Override
    public void resume() {
        // Handle resume
    }

    @Override
    public void hide() {
        // Handle hide
    }

    @Override
    public void dispose() {
    }
}
