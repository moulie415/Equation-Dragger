package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;


/**
 * Created by henrymoule on 06/03/2017.
 */
public class SectionScreen implements Screen {
    private Stage stage;
    private Player player;
    private Game game;
    private Skin crispy;
    private BitmapFont font;
    private Viewport viewport;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;
    private Button close;
    private TextButton section1;
    private TextButton section2;
    private TextButton section3;
    private TextButton.TextButtonStyle sectionStyle;
    private Button.ButtonStyle buttonStyle;
    private Skin skin;

    public SectionScreen(Game game, Player player) {
        this.game = game;
        this.player = player;
        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;

        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here!
        stage = new Stage(viewport);

        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        font.setColor(Color.BLACK);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = crispy.getDrawable("button-close");
        buttonStyle.over = crispy.getDrawable("button-close-over");
        buttonStyle.down = crispy.getDrawable("button-close-pressed");


        sectionStyle = new TextButton.TextButtonStyle();
        sectionStyle.up = crispy.getDrawable("button");
        sectionStyle.over = crispy.getDrawable("button-over");
        sectionStyle.down = crispy.getDrawable("button-pressed");
        sectionStyle.font = font;

        close = new Button(buttonStyle);

        close.setSize(50,50);

        stage.addActor(close);

        close.setPosition(50, 650);

        section1 = new TextButton("Section 1: Simple Equations", sectionStyle);
        section1.setPosition(100, 500);
        section2 = new TextButton("Section 2: Simultaneous Equations", sectionStyle);
        section2.setPosition(100, 400);
        if (!player.getSection(1)) {
            section2.setColor(Color.GRAY);
        }
        section3 = new TextButton("Section 3: Quadratic Equations", sectionStyle);
        section3.setPosition(100, 300);
        if (!player.getSection(2)) {
            section3.setColor(Color.GRAY);
        }

        stage.addActor(section1);
        stage.addActor(section2);
        stage.addActor(section3);


    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


        final SectionDialog dialog = new SectionDialog("", skin);
        close.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("closed");
                game.setScreen(new MainMenu(game, player));
                return true;
            }

        });
        section1.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayScreen(game, player));
                return true;
            }

        });
        section2.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (player.getSection(1)) {
                    game.setScreen(new PlayScreen2(game, player));
                }
                else {
                    dialog.show(stage);

                }
                return true;
            }

        });
        section3.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (player.getSection(2)) {

                }
                else {
                    dialog.show(stage);

                }
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
        stage.getViewport().update(width,height,false);

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
