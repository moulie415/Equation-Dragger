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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by henrymoule on 01/03/2017.
 */
public class Stats implements Screen {
    private Player player;
    private Game game;
    private Label stats;
    private Label attempts;
    private Label correct;
    private Label wrong;
    private Label ratio;
    private Label totalPoints;
    private Skin crispy;
    private Button close;
    private Label.LabelStyle style;
    private Button.ButtonStyle buttonStyle;
    private BitmapFont font;
    private Stage stage;
    private Viewport viewport;
    private int VIRTUAL_WIDTH;
    private int VIRTUAL_HEIGHT;
    private Sound click;
    private Sound never;

    public Stats(Game game, Player player) {
        this.game = game;
        this.player = player;

        VIRTUAL_WIDTH = 1280;
        VIRTUAL_HEIGHT = 720;
        //camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT); //notice cam param here! (camera taken out)
        stage = new Stage(viewport);

        crispy = new Skin(Gdx.files.internal("clean-crispy/skin/clean-crispy-ui.json"));
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/HITMARKER.mp3"));
        never = Gdx.audio.newSound(Gdx.files.internal("sounds/never_done_that.mp3"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"), false);
        style = new Label.LabelStyle(font, Color.BLACK);

        stats = new Label("YOUR STATS:", style);
        stats.setPosition(400, 600);

        stage.addActor(stats);

        attempts = new Label("Attempts: " + player.getAttempts(), style);
        attempts.setPosition(400, 500);
        stage.addActor(attempts);


        correct = new Label("Correct answers: " + player.getCorrectCount(), style);
        correct.setPosition(400, 400);
        stage.addActor(correct);


        wrong = new Label("Wrong answers: " + player.getWrongCount(), style);
        wrong.setPosition(400, 300);
        stage.addActor(wrong);

        ratio = new Label("Ratio: " + player.getRatio(), style);
        ratio.setPosition(400, 200);
        stage.addActor(ratio);

        totalPoints = new Label("Total points: " + (player.getPoints(1)+player.getPoints(2)
                +player.getPoints(3)), style);
        totalPoints.setPosition(400, 100);
        stage.addActor(totalPoints);

        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = crispy.getDrawable("button-close");
        buttonStyle.over = crispy.getDrawable("button-close-over");
        buttonStyle.down = crispy.getDrawable("button-close-pressed");
        close = new Button(buttonStyle);

        close.setSize(50,50);

        stage.addActor(close);

        close.setPosition(50, 650);

    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        never.play();

        close.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                click.play();
                never.dispose();
                System.out.println("closed");
                game.setScreen(new MainMenu(game, player));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        stage.dispose();
        never.dispose();

    }
}
