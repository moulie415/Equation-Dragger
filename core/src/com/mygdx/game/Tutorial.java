package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;


/**
 * Created by henrymoule on 06/03/2017.
 */
public class Tutorial implements Screen {
    private Stage stage;
    private Game game;
    private Player player;
    private Skin crispy;
    private Viewport viewport;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;
    private Button close;
    private Texture image;
    private Button.ButtonStyle buttonStyle;
    private Sound click;
    private Music darude;

    public Tutorial(Game game, Player player) {
        this.game = game;
        this.player = player;
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/HITMARKER.mp3"));
        darude = Gdx.audio.newMusic(Gdx.files.internal("sounds/darude_dankstorm.mp3"));

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;

        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here!
        stage = new Stage(viewport);

        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));


        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = crispy.getDrawable("button-close");
        buttonStyle.over = crispy.getDrawable("button-close-over");
        buttonStyle.down = crispy.getDrawable("button-close-pressed");



        close = new Button(buttonStyle);

        close.setSize(50,50);

        stage.addActor(close);

        close.setPosition(50, 650);

        image = new Texture(Gdx.files.internal("images/tutorial.png"));
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        darude.play();


        close.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("closed");
                click.play();
                darude.dispose();
                game.setScreen(new MainMenu(game, player));
                return true;
            }

        });



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().draw(image, 100, 100);
        stage.getBatch().end();
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
        click.dispose();
        darude.dispose();

    }

}

