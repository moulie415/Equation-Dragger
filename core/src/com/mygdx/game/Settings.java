package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.io.IOException;


/**
 * Created by henrymoule on 06/03/2017.
 */
public class Settings implements Screen {
    private Stage stage;
    private Game game;
    private Player player;
    private Skin crispy;
    private Viewport viewport;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;
    private Button close;
    private Button.ButtonStyle buttonStyle;
    private CheckBox.CheckBoxStyle checkBoxStyle;
    private CheckBox disableSplash;
    private CheckBox instructional;
    private Sound click;
    private Sound damn;
    private BitmapFont font;

    public Settings(Game game, Player player) {
        this.game = game;
        this.player = player;
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/HITMARKER.mp3"));
        damn = Gdx.audio.newSound(Gdx.files.internal("sounds/damn_son.mp3"));

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


        font = new BitmapFont(Gdx.files.internal("small.fnt"), false);

        checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = font;
        checkBoxStyle.fontColor = Color.BLACK;

        checkBoxStyle.checkboxOn = crispy.getDrawable("checkbox-pressed");
        checkBoxStyle.checkboxOff = crispy.getDrawable("checkbox");
        disableSplash = new CheckBox("Disable splash screens", checkBoxStyle);
        instructional = new CheckBox("Disable main menu instructional on launch", checkBoxStyle);
        if (!player.getSplash()) {
            disableSplash.setChecked(true);
        }
        else {
            disableSplash.setChecked(false);
        }
        if (!player.getInstructional()) {
            instructional.setChecked(true);
        }
        else {
            instructional.setChecked(false);
        }



        instructional.setPosition(400, 550);
        disableSplash.setPosition(400, 600);


        close = new Button(buttonStyle);

        close.setSize(50,50);
        close.setPosition(50, 650);

        stage.addActor(close);
        stage.addActor(disableSplash);
        stage.addActor(instructional);


    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        damn.play();


        close.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    player.savePlayer(player);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("closed");
                click.play();
                game.setScreen(new MainMenu(game, player));
                return true;
            }

        });
        disableSplash.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!disableSplash.isChecked()) {
                    player.setSplash(false);
                }
                else {
                    player.setSplash(true);
                }
                try {
                    player.savePlayer(player);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("closed");
                click.play();
                return true;
            }

        });
        instructional.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!instructional.isChecked()) {
                    player.setInstructional(false);
                }
                else {
                    player.setInstructional(true);
                }
                try {
                    player.savePlayer(player);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("closed");
                click.play();
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
        try {
            player.savePlayer(player);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        stage.dispose();
        click.dispose();
        damn.dispose();

    }

}

