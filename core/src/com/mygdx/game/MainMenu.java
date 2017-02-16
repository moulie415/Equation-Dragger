package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.prism.image.ViewPort;

/**
 * Created by henrymoule on 04/01/2017.
 */
public class MainMenu implements Screen {

    private Stage stage;
    private Label label;
    private Label.LabelStyle style;
    private BitmapFont font;
    private BitmapFont font2;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle buttonStyle;
    private TextButton start;
    private TextButton tutorial;
    private TextButton stats;
    private TextButton about;
    private Skin skin;
    private Game game;
    private Viewport viewport;
    private Camera camera;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;

    public MainMenu(Game game) {
        this.game = game;
    }

    @Override
    public void show() {

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;
        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here! (camera taken out)
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        font2 = new BitmapFont();
        style = new Label.LabelStyle(font, Color.BLACK);

        label = new Label("Hello world", style);
        label.setPosition(100, Gdx.graphics.getHeight() - 50);
        skin = new Skin();
        buttonAtlas = new TextureAtlas("buttons/button.pack");
        stage.addActor(label);
        skin.addRegions(buttonAtlas);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = Color.BLACK;
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.over = skin.getDrawable("buttonpressed");
        buttonStyle.down = skin.getDrawable("buttonpressed");
        buttonStyle.font = font;

        start = new TextButton("START", buttonStyle);
        start.setSize(300,100);

        tutorial = new TextButton("TUTORIAL", buttonStyle);
        tutorial.setSize(300,100);

        stats = new TextButton("STATS", buttonStyle);
        stats.setSize(300,100);

        about = new TextButton("ABOUT", buttonStyle);
        about.setSize(300,100);

        System.out.println(stage.getWidth());
        System.out.println(stage.getHeight());
        System.out.println(Gdx.graphics.getWidth());
        System.out.println(Gdx.graphics.getHeight());
        start.setPosition(500, 600 );
        tutorial.setPosition(500, 450);
        stats.setPosition(500, 300);
        about.setPosition(500, 150);

        stage.addActor(start);
        stage.addActor(tutorial);
        stage.addActor(stats);
        stage.addActor(about);

        start.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked");
                game.setScreen(new PlayScreen(game));


                return true;
            }
        });

        tutorial.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked");

                game.setScreen(new TestClass(game));

                return true;
            }
        });

        about.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked");

                game.setScreen(new About(game));

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) ) {
            if (Gdx.input.getY() > Gdx.graphics.getHeight()/2) {
                label.setPosition(label.getX(), label.getY() + 2);
            }
            else {
                label.setPosition(label.getX(), label.getY() - 2);
            }

        }

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);

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
        stage.dispose();

    }
}
