package com.mygdx.game;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by hen10 on 11/03/2017.
 */
public class SplashScreen implements Screen {

    private SpriteBatch batch;
    private Sprite splash;
    private Sprite splash2;
    private Sprite splash3;
    private Sprite splash4;
    private Skin skin;
    private Button skip;
    private TextureAtlas buttonAtlas;
    private Button.ButtonStyle style;
    private TweenManager tweenManager;
    private Game game;
    private Player player;
    private Music song;
    private Texture hitmarker;
    private float mouseX, mouseY;
    private boolean hmVisible;
    private Sound click;
    private Stage stage;
    private Viewport viewport;
    private int VIRTUAL_WIDTH, VIRTUAL_HEIGHT;


    public SplashScreen(Game game, Player player) {
        this.game = game;
        this.player = player;
        stage = new Stage();
        song = Gdx.audio.newMusic(Gdx.files.internal("sounds/mlg_universal.mp3"));
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/HITMARKER.mp3"));
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/skip.atlas"));
        skin.addRegions(buttonAtlas);

        style =  new Button.ButtonStyle();
        style.up = skin.getDrawable("skip");
        style.down = skin.getDrawable("skip");
        style.over = skin.getDrawable("skip");



        skip = new Button(style);
        skip.setSize(50, 50);
        skip.setPosition(1100, 50);
        stage.addActor(skip);
        song = Gdx.audio.newMusic(Gdx.files.internal("sounds/mlg_universal.mp3"));
        hitmarker = new Texture(Gdx.files.internal("images/hitmarker.png"));
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/HITMARKER.mp3"));
        song.play();

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);

        stage.getBatch().begin();
        splash.draw(stage.getBatch());
        splash2.draw(stage.getBatch());
        splash3.draw(stage.getBatch());
        splash4.draw(stage.getBatch());
        if (hmVisible) {
            stage.getBatch().draw(hitmarker, mouseX-10, mouseY-10, 20, 20);
        }
        stage.getBatch().end();

        tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;
        // apply preferences
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here! (camera taken out)
        stage = new Stage(viewport);

        batch = new SpriteBatch();

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        splash = new Sprite(new Texture("images/splash1.png"));
        splash2 = new Sprite(new Texture("images/splash2.png"));
        splash3 = new Sprite(new Texture("images/splash3.png"));
        splash4 = new Sprite(new Texture("images/splash4.png"));
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        splash2.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        splash3.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        splash4.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Timeline.createSequence()
                .push(Tween.set(splash, SpriteAccessor.ALPHA).target(0)) // First, set all objects to their initial positions
                .push(Tween.set(splash2, SpriteAccessor.ALPHA).target(0)) // First, set all objects to their initial positions
                .push(Tween.set(splash3, SpriteAccessor.ALPHA).target(0)) // First, set all objects to their initial positions
                .push(Tween.set(splash4, SpriteAccessor.ALPHA).target(0)) // First, set all objects to their initial positions
                .pushPause(0.5f)

                .push(Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1,2))
                .push(Tween.to(splash2, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1,2))
                .push(Tween.to(splash3, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1,2))
                .push(Tween.to(splash4, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1,2))
                .start(tweenManager).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new MainMenu(game, player));
            }
        });    // and finally start it!*/
        tweenManager.update(Float.MIN_VALUE); // update once avoid short flash of splash before animation
        skip.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                game.setScreen(new MainMenu(game, player));


                return true;
            }
        });

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                // Some stuff
                hmVisible = true;
                mouseX = x;
                mouseY = y;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                hmVisible = false;
            }
        });
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        click.dispose();
        song.dispose();
        splash.getTexture().dispose();
    }

}

