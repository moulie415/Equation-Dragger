package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;

/**
 * Created by henrymoule on 04/01/2017.
 */
public class MainMenu implements Screen {

    private Stage stage;
    private BitmapFont font;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle buttonStyle;

    private TextButton start;
    private TextButton tutorial;
    private TextButton stats;
    private TextButton about;
    private Skin skin;
    private Game game;
    private Player player;
    private Viewport viewport;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;
    private Sound click;
    private Label title;
    private Label.LabelStyle style;

    public MainMenu(Game game, Player player ) {
        this.game = game;
        this.player = player;
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/button-click.wav"));
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

        style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.BLACK;

        title = new Label("Equation Dragger", style);

        skin = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        //buttonAtlas = new TextureAtlas("buttons/button.pack");
        buttonAtlas = skin.getAtlas();
        skin.addRegions(buttonAtlas);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = Color.BLACK;
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.over = skin.getDrawable("button-pressed-over");
        buttonStyle.down = skin.getDrawable("button-pressed");
        //buttonStyle.font = skin.getFont("font");
        buttonStyle.font = font;

        start = new TextButton("START", buttonStyle);
        start.setSize(350,100);

        tutorial = new TextButton("TUTORIAL", buttonStyle);
        tutorial.setSize(350,100);

        stats = new TextButton("STATS", buttonStyle);
        stats.setSize(350,100);

        about = new TextButton("ABOUT", buttonStyle);
        about.setSize(350,100);


        title.setPosition(425, 625);
        start.setPosition(500, 500 );
        tutorial.setPosition(500, 375);
        stats.setPosition(500, 250);
        about.setPosition(500, 125);

        stage.addActor(title);
        stage.addActor(start);
        stage.addActor(tutorial);
        stage.addActor(stats);
        stage.addActor(about);

        start.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                game.setScreen(new SectionScreen(game, player));


                return true;
            }
        });

        tutorial.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                game.setScreen(new Tutorial(game, player));

                return true;
            }
        });

        about.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();

                game.setScreen(new About(game, player));

                return true;
            }
        });

        stats.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                click.play();
                game.setScreen(new Stats(game, player));

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
        try {
            player.savePlayer(player);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        stage.dispose();
        click.dispose();


    }
}
